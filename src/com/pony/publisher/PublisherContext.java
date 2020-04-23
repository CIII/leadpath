package com.pony.publisher;

import com.pony.PonyException;
import com.pony.advertiser.AdvertiserService;
import com.pony.core.TestContext;
import com.pony.email.ResendMessagePhase;
import com.pony.email.validation.AddressValidator;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.models.ArrivalModelImpl;
import com.pony.models.UserProfileModelImpl;
import com.pony.validation.ValidationResponse;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.*;

/**
 * This class acts as the DTO for the information surrounding a lead submission that is used during the processing of
 * the lead. It does not contain information about the origin of the lead (which is primarily concerned with
 * authorizing the lead submission and with associating the lead a list of orders); that is the
 * {@link PublisherChannel}. And it only includes information about the distribution of the lead to buyers insofar as
 * the request itself included a list of buyers to consider; if the list of buyers was derived from the
 * {@link PublisherList}, then that comes entirely from the {@link AdvertiserService}.
 * 
 * Almost all of the logic, building relationships between the {@link Arrival}s, {@link Lead}s, and {@link UserProfile}s
 * is done in the constructor of this object, and the type of the request is represented by the sub-type of this object
 * that was instantiated.
 * 
 * Copyright 2011 PonyMash LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 5:22 PM
 */
public abstract class PublisherContext implements IPublisherContext {
	private static final Log LOG = LogFactory.getLog(PublisherContext.class);
	
    protected final PublisherChannel channel;
    protected Lead lead;
    protected final List<Long> buyerIds = new ArrayList<Long>();
    protected UserProfile userProfile;
    protected Arrival arrival;
    protected final String path;
    protected boolean wait;
    protected Long messageId;
    /**
     * This returns the exception that accumulates validation or other errors during processing. It is stored this way,
     * carried along during processing, so that all validation errors can be accumulated and then returned.
     */
    protected PublisherException exceptionInProcess = new PublisherException("Validation exceptions processing request.");
    /**
     * This stores the validation response for the validation of the post message.
     */
    protected ValidationResponse validationResponse = null;

    private boolean isEmailDup = false;
    private boolean isPublisherDup = false;

    protected final Map<String, String> params = new HashMap<String, String>();

    public static final String HTTP_WAIT_FOR_RESPONSE = "WAIT_FOR_RESPONSE";
    public static final String LEAD_REF = "lead_ref";
    public static final String BUYER_ID = "buyer_id";

    protected TestContext testContext;
    
    protected PublisherResponse response;

    /**
     * Parse the 'email' request parameter and check if we already have this one.
     * If we already have it, check if the same publisher sent it again.
     * If it's not a DUP, create an arrival and parse the lead (based on the LeadType determined by the PublisherChannel)
     * and persist the profile attributes provided in the http request.
     *
     * @param path
     * @param pc
     * @param request
     * @throws SQLException
     * @throws NamingException
     */
    protected PublisherContext(String path, PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException, PonyException {

        this(path, pc);

        // read the channel info and lookup what publisher, and lead type is being sent,
        // then parse the lead info

        String w = request.getParameter(HTTP_WAIT_FOR_RESPONSE);
        if (w == null) {
            w = request.getHeader(HTTP_WAIT_FOR_RESPONSE);
        }
        wait = (w == null) ? false : Boolean.valueOf(w); // default is not to wait
        LOG.debug("Wait for response: " + wait);

        // read the channel info and lookup what publisher, and lead type is being sent,
        // then parse the lead info

        // for test publisher, create test context to hold additional test info parameters (if any present, otherwise don't create the context)
        if (pc.getPublisher().isTest()) {
            testContext = TestContext.create(request);
        }

        // This section is about connecting the lead referred to in the post with the lead that has been under
        //   construction during previous pings. It is not determining whether this lead is a duplicate of previously
        //   submitted leads. That is done in the PostContext constructor after this. [jec:2016-06-26]
        // 1. Found the user profile by email and phone
        // 2. Found the email, name or phone, name match ?
        // 3. Found the name and address
        // 4. email match
        // 5. phone match

        userProfile = null;
        UserProfile dup = null;
        String email = UserProfile.parse(request);
        if (email != null) {

            // check if there is a frequent typo in the domain name, and if so 'auto-correct' the domain
            email = UserProfileModelImpl.autoCorrectStatic(email);

            // DUP check 1: do we know this email address already?
            dup = UserProfileModelImpl.findByEmailStatic(email);
            if (dup != null) {
                userProfile = dup;
                if (!dup.isTrap() && !pc.getPublisher().allowDuplicates()) {
                    isEmailDup = true;
                }
                UserProfileModelImpl.touchStatic(dup);
            }
            else {
                userProfile = UserProfileModelImpl.createStatic(email);
            }

            // DUP check 2: did the same publisher send this email before?
            // exclude trap addresses from this so we can send tests
            if (dup != null && !dup.isTrap() && !pc.getPublisher().allowDuplicates()) {
                Arrival a = ArrivalModelImpl.findByPublisherAndUserProfileStatic(pc.getPublisher(), dup);
                if (a != null) {
                    isPublisherDup = true;
                    arrival = a;
                    lead = null;
                    ArrivalModelImpl.touchStatic(a, ValidationResponse.DUP);
                    return;
                }
            }
        }

        LeadType leadType = pc.getLeadType();
        
        // TODO: This is incomplete. If it is a repost, it will have a lead_match, with reference to a lead already making this unnecessary. For now, punting. [jec:2016/06/22]
        // if this is a post in reference to a ping, we already have an arrival and a lead, and need to update
        // if this is a 'naked' post, we need to create everything now
        String leadIdRef = request.getParameter(LEAD_REF);
        if (leadIdRef != null) {
            Long leadId = Long.valueOf(leadIdRef);
            arrival = ArrivalModelImpl.findByLeadIdStatic(leadId);

            if (arrival != null && userProfile != null && (arrival.getUserProfileId() == null || arrival.getUserProfileId() <= 0L)) {
                arrival.updateUserProfile(userProfile);
            }

            try {
            	lead = leadType.findLead(leadId);
            	if (lead != null) {
            		lead.mergeAttributes(request);
            		if(userProfile == null) { // In the event of a repost, we would not be given an email in the request so we would not get a user profile. Find the one we already have. If it is a repost, we should have a leadMatchId, so we must have an arrival and up.
            			// TODO: This requires more logic; if we are re-routing an entire lead, we may or may not have a user user-profile-id. [jec:2017-06-22]
            			userProfile = UserProfileModelImpl.findStatic(lead.getUserProfileId());
            		} else { // In the event we found a user profile previously, update the lead to link to that profile.
            			leadType.updateLead(userProfile, lead);
            		}

            		String[] buyerIdRefs = request.getParameterValues(BUYER_ID);
            		// these are our destination candidates
            		if (buyerIdRefs != null) {
            			for (String buyerId : buyerIdRefs) {
            				String[] ids = buyerId.trim().split(",");
            				for (String id : ids) {
            					buyerIds.add(Long.valueOf(id.trim()));
            				}
            			}
            		}
            	}
            } catch (PonyException e) {
            	throw new PonyException("Failure to find and populate the lead referenced. Failing the request.", e);
            }
        }
        else {
            // this is new, or new from this publisher
            Arrival a = Arrival.parse(pc, userProfile, request);

            // if an external id was provided, try first to find an arrival for it (from this publisher and channel)
            if (arrival == null && a.getExternalId() != null) {
                arrival = ArrivalModelImpl.findStatic(a.getPublisherId(), a.getPublisherListId(), a.getExternalId());
            }

            // not found? , create it
            if (arrival == null) {
                arrival = ArrivalModelImpl.createStatic(a);
            }
            else if (userProfile == null && arrival.getUserProfileId() != null) {
                // no email sent, but we have one for the arrival
                userProfile = UserProfileModelImpl.findStatic(arrival.getUserProfileId());
            }

            // if its a dup , but not from the same publisher (i.e. we actually made it here), still mark it as such (this is not the first arrival we create for this user profile)
            if (dup != null && !dup.isTrap() && !isPublisherDup && !pc.getPublisher().allowDuplicates()) {
                ArrivalModelImpl.touchStatic(arrival, ValidationResponse.DUP);
            }

            lead = leadType.parseLead(userProfile, arrival, request);
            lead = leadType.persistLead(userProfile, arrival, lead);

//            System.out.println("\r\n-- @@@@ --> PublisherContext: lead=" + lead + "; arrivalId=" + arrival.getId() + "; up=" + userProfile);
        }
    }

    protected PublisherContext(String path, PublisherChannel pc) {
        this.path = path;
        channel = pc;
    }

    protected PublisherContext(String path, PublisherChannel pc, Arrival arrival, UserProfile userProfile, Lead lead) {
        this(path, pc);

        this.arrival = arrival;
        this.userProfile = userProfile;
        this.lead = lead;
        wait = false;
    }

    /**
     * Form posts are encoded for the form they post to, so we need to parse the form fields from a different 'encoding'.
     * We still use the standard mechanism as a fallback
     *
     * @param path
     * @param pc
     * @param request
     * @param params
     */
    public PublisherContext(String path, PublisherChannel pc, HttpServletRequest request, Map<String, String> params) throws NamingException, SQLException {

        this(path, pc);

        String w = request.getParameter(HTTP_WAIT_FOR_RESPONSE);
        if (w == null) {
            w = request.getHeader(HTTP_WAIT_FOR_RESPONSE);
        }
        wait = (w != null && Boolean.valueOf(w)); // default is not to wait

        // read the channel info and lookup what publisher, and lead type is being sent,
        // then parse the lead info

        String email = params.get(UserProfile.EMAIL);

        if (email == null) {
            UserProfile.parse(request);
        }

        if (email == null) {
            userProfile = null;
            lead = null;
            arrival = null;
            return;
        }

        // for test publisher, create test context to hold additional test info parameters (if any present, otherwise don't create the context)
        if (pc.getPublisher().isTest()) {
            testContext = TestContext.create(request);
        }

        // check if there is a frequent typo in the domain name, and if so 'auto-correct' the domain
        email = UserProfileModelImpl.autoCorrectStatic(email);

        // DUP check 1: do we know this email address already?
        UserProfile dup = UserProfileModelImpl.findByEmailStatic(email);
        if (dup != null) {
            userProfile = dup;
            if (!dup.isTrap() && !pc.getPublisher().allowDuplicates()) {
                isEmailDup = true;
            }
            UserProfileModelImpl.touchStatic(dup);
        }
        else {
            userProfile = UserProfileModelImpl.createStatic(email);
        }

        // DUP check 2: did the same publisher send this email before?
        // exclude trap addresses from this so we can send tests
        if (dup != null && !dup.isTrap() && !pc.getPublisher().allowDuplicates()) {
            Arrival a = ArrivalModelImpl.findByPublisherAndUserProfileStatic(pc.getPublisher(), dup);
            if (a != null) {
                isPublisherDup = true;
                arrival = a;
                lead = null;
                ArrivalModelImpl.touchStatic(a, ValidationResponse.DUP);
                return;
            }
        }

        // this is new, or new from this publisher
        Arrival a = Arrival.parse(pc, userProfile, request);
        arrival = ArrivalModelImpl.createStatic(a);

        // if its a dup , but not from the same publisher (i.e. we actually made it here), still mark it as such (this is not the first arrival we create for this user profile)
        if (dup != null && !dup.isTrap() && !isPublisherDup && !pc.getPublisher().allowDuplicates()) {
            ArrivalModelImpl.touchStatic(arrival, ValidationResponse.DUP);
        }

        lead = pc.getLeadType().parseLead(userProfile, arrival, request, params);
        pc.getLeadType().persistLead(userProfile, arrival, lead);
    }

    public static PublisherContext parse(HttpServletRequest request) throws PublisherException {
        try {
            PublisherChannel pc = PublisherChannel.parse(request);
            if (pc == null) {
                throw new PublisherException("Invalid publisher info provided");
            }

            return parse(pc, request);
        }
        catch (NamingException e) {
            throw new PublisherException(e);
        }
        catch (SQLException e) {
            throw new PublisherException(e);
        }
    }

    public static PublisherContext create(String path, PublisherChannel pc, Arrival arrival, UserProfile userProfile, Lead lead) throws NamingException, SQLException {
        if (path.startsWith(EmailContext.PATH)) {
            return createEmailContext(pc, arrival, userProfile, lead);
        }
        return null;
    }

    /**
     * Use the PublisherChannel class to parse the details from the request and check the provided publisher and list information.
     * Find out what path this request was sent to and delegate accordingly.
     * We know '/ping' , '/post' , '/validate', '/inbound.php' (email)
     *
     * @param request the http request
     * @return the parsed information as a handy object to pass along
     * @throws PublisherException
     */
    public static PublisherContext parse(PublisherChannel pc, HttpServletRequest request) throws PublisherException {
        try {
            if (request.getServletPath().startsWith(PingContext.PATH)) {
                return createPingContext(pc, request);
            }
            else if (request.getServletPath().startsWith(PollContext.PATH)) {
                return createPollContext(pc, request);
            }
            else if (request.getServletPath().startsWith(PostContext.PATH)) {
                return createPostContext(pc, request);
            }
            else if (request.getServletPath().startsWith(RePostContext.PATH)) {
                return createRePostContext(pc, request);
            }
            else if (request.getServletPath().startsWith(ValidationContext.PATH)) {
                return createValidationContext(pc, request);
            }
            else if (request.getServletPath().startsWith(EmailContext.PATH)) {
                return createEmailContext(pc, request);
            }
            else if (request.getServletPath().startsWith(SubscriptionContext.PATH)) {
                return createSubscriptionContext(pc, request);
            }
            else if (request.getServletPath().startsWith(AsyncEmailContext.PATH)) {
                return createAsyncEmailContext(pc, request);
            }
            else if (request.getServletPath().startsWith(ResendContext.PATH)) {
                return createResendContext(pc, request);
            }
            else if (request.getServletPath().startsWith(NewsletterContext.PATH)) {
                return createNewsletterContext(pc, request);
            }
//            else if (request.getServletPath().startsWith(FormContext.PATH)) {
//                return createFormContext(pc, request, params);
//            }
        }
        catch (PonyException e) {
        	String errMsg = "Exception parsing request and creating PublisherContext.";
        	LOG.error(errMsg, e);
        	throw new PublisherException(errMsg, e);
        }
        catch (NamingException e) {
            throw new PublisherException(e);
        }
        catch (SQLException e) {
            throw new PublisherException(e);
        }

        throw new PublisherException("unknown path:" + request.getServletPath());
    }

    public static PublisherContext parse(PublisherChannel pc, HttpServletRequest request, Map<String, String> params) throws PublisherException {
        try {
//            if (request.getServletPath().startsWith(PingContext.PATH)) {
//                return createPingContext(pc, request);
//            }
//            else if (request.getServletPath().startsWith(AdvertiserPostContext.PATH)) {
//                return createPostContext(pc, request);
//            }
//            else if (request.getServletPath().startsWith(ValidationContext.PATH)) {
//                return createValidationContext(pc, request);
//            }
//            else if (request.getServletPath().startsWith(EmailContext.PATH)) {
//                return createEmailContext(pc, request);
//            }
//            else if (request.getServletPath().startsWith(ResendContext.PATH)) {
//                return createResendContext(pc, request);
//            }
//            else
            if (request.getServletPath().startsWith(FormContext.PATH)) {
                return createFormContext(pc, request, params);
            }
        }
        catch (NamingException e) {
            throw new PublisherException(e);
        }
        catch (SQLException e) {
            throw new PublisherException(e);
        }

        throw new PublisherException("unknown path:" + request.getServletPath());
    }

    @Override
	public boolean isEmailDup() {
        return isEmailDup;
    }

    @Override
	public boolean isPublisherDup() {
        return isPublisherDup;
    }

    @Override
	public PublisherChannel getChannel() {
        return channel;
    }

    @Override
	public LeadType getLeadType() {
        return channel.getLeadType();
    }

    @Override
	public Lead getLead() {
        return lead;
    }

    @Override
	public void setLead(Lead lead) {
		this.lead = lead;
	}

	@Override
	public UserProfile getUserProfile() {
        return userProfile;
    }

    @Override
	public Arrival getArrival() {
        return arrival;
    }

    @Override
	public List<Long> getBuyerIds() {
        return buyerIds;
    }

    @Override
	public Long getMessageId() {
        return messageId;
    }

    @Override
	public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Override
	public Map<String, String> getParams() {
        return Collections.unmodifiableMap(params);
    }

    private static PublisherContext createValidationContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException, PonyException {
        return new ValidationContext(pc, request);
    }

    private static PublisherContext createPostContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException, PonyException {
        return new PostContext(pc, request);
    }

    private static PublisherContext createRePostContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException, PonyException {
        String match = request.getParameter("lead_match_id");
        if (match != null) {
            return new RePostContext(pc, request, Long.valueOf(match));
        }

        return null;
    }

    private static PublisherContext createFormContext(PublisherChannel pc, HttpServletRequest request, Map<String, String> params) throws SQLException, NamingException {
        return new FormContext(pc, request, params);
    }

    private static PublisherContext createPingContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException {
        return new PingContext(pc, request);
    }

    private static PublisherContext createPollContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException, PonyException {
        return new PollContext(pc, request);
    }

    private static EmailContext createEmailContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException, PublisherException, PonyException {
        EmailContext emailContext = new EmailContext(pc, request);
        if (emailContext.getUserProfile() == null) {
            // not a valid context (no email provided in the url)
            throw new PublisherException("No valid email provided");
        }

        return emailContext;
    }
    
    private static SubscriptionContext createSubscriptionContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException, PonyException, PublisherException {
    	SubscriptionContext subContext = new SubscriptionContext(pc, request);
        if (subContext.getUserProfile() == null) {
            // not a valid context (no email provided in the url)
            throw new PublisherException("No valid email provided");
        }

        return subContext;
    }

    private static PublisherContext createNewsletterContext(PublisherChannel pc, HttpServletRequest request) throws NamingException, SQLException {
        return new NewsletterContext(pc, request);
    }

    private static AsyncEmailContext createAsyncEmailContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException, PublisherException {
        return new AsyncEmailContext(pc, request);
    }

    private static PublisherContext createResendContext(PublisherChannel pc, HttpServletRequest request) throws NamingException, SQLException, PonyException {
        return new ResendContext(pc, request);
    }

    private static PublisherContext createEmailContext(PublisherChannel publisherChannel, Arrival arrival, UserProfile userProfile, Lead lead) throws NamingException, SQLException {
        return new EmailContext(publisherChannel, arrival, userProfile, lead);
    }

    @Override
	public boolean isPing() {
        return false;
    }

    @Override
	public boolean isPost() {
        return false;
    }

    @Override
	public boolean isRePost() {
        return false;
    }

    @Override
	public boolean isValidation() {
        return false;
    }

    @Override
	public boolean isPoll() {
        return false;
    }

    @Override
	public boolean isEmail() {
        return false;
    }

    @Override
	public boolean isSubscription() {
		return false;
	}

    @Override
	public boolean isResend() {
        return false;
    }

    @Override
	public boolean isForm() {
        return false;
    }

    @Override
	public boolean waitForResponse() {
        return wait;
    }

    @Override
	public Publisher getPublisher() {
        return channel.getPublisher();
    }

    @Override
	public PublisherList getPublisherList() {
        return channel.getPublisherList();
    }

    @Override
	public boolean isTest() {
        return testContext != null;
    }

    @Override
	public TestContext getTestContext() {
        return testContext;
    }

    @Override
	public boolean refreshCache() {
        if (testContext != null) {
            return testContext.isRefresh();
        }
        return false;
    }

    @Override
	public Long getLeadId() {
        return lead != null ? lead.getId() : null;
    }
    
    /**
     * Gain access to the exception object itself so that it can be thrown.
     * 
     * @return
     */
    @Override
	public PublisherException getException() {
    	return exceptionInProcess;
    }
    
    @Override
	public ValidationResponse getValidationResponse() {
		return validationResponse;
	}

	@Override
	public void setValidationResponse(ValidationResponse validationResponse) {
		this.validationResponse = validationResponse;
	}

	/**
     * Add the error code to the exception being constructed during processing.
     * 
     * @param code
     */
    @Override
	public void addErrorCode(String code, String message) {
    	exceptionInProcess.addCode(code, message);
    }

    @Override
	public PublisherResponse getPublisherResponse() {
		return response;
	}

	@Override
	public void setPublisherResponse(PublisherResponse response) {
		this.response = response;
	}

	public static class RePostContext extends PublisherContext {
        private static final String PATH = "/repost";
        private final Long leadMatchId;

        private RePostContext(PublisherChannel pc, HttpServletRequest request, Long leadMatchId) throws SQLException, NamingException, PonyException {
            super(PATH, pc, request);
            this.leadMatchId = leadMatchId;
        }

        public Long getLeadMatchId() {
            return leadMatchId;
        }

        @Override
        public boolean isRePost() {
            return true;
        }

        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append("RePostContext for ").append(PATH).append(":").append(getChannel()).append(" leadId=").append(getLeadId()).append(" leadMatchId=").append(leadMatchId);

            return s.toString();
        }
    }

    public static class PostContext extends PublisherContext {
        private static final String PATH = "/post";

        public PostContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException, PonyException {
            super(PATH, pc, request);
            
            // TODO: If I do the duplication detection here, it will be used everywhere regardless, and will still be post-specific.
            //  Doing it in the advertiser service blurs the lines, I think. Possibly. The routing based on duplication
            //  is done there, so maybe it doesn't.
            
            // What are all the kinds of duplicate?
            // 1. Found the user profile by email and phone
            // 2. Found the email, name or phone, name match ?
            // 3. Found the name and address
            // 4. email match
            // 5. phone match
            
        }

        @Override
        public boolean isPost() {
            return true;
        }

        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append("PostContext for ").append(PATH).append(":").append(getChannel()).append(" leadId=").append(getLeadId()).append(" buyerId=");
            for (Long id : getBuyerIds()) {
                s.append(id).append(",");
            }

            return s.toString();
        }
    }

    private static class FormContext extends PublisherContext {
        private static final String PATH = "/form";

        public FormContext(PublisherChannel pc, HttpServletRequest request, Map<String, String> params) throws SQLException, NamingException {
            super(PATH, pc, request, params);
        }

        @Override
        public boolean isForm() {
            return true;
        }

        public String toString() {
            return "FormContext for " + PATH + ":" + getChannel() + ": arrival=" + arrival + ": userProfile=" + userProfile + ": lead=" + lead;
        }
    }

    private static class ValidationContext extends PublisherContext {
        private static final String PATH = "/validate";

        public ValidationContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException, PonyException {
            super(PATH, pc, request);
        }

        @Override
        public boolean isValidation() {
            return true;
        }

        public String toString() {
            return "ValidationContext for " + PATH + ":" + getChannel();
        }
    }

    public static class AsyncEmailContext extends PublisherContext {

        public static final String PATH = "/async_email";
        private int rowCount = 5; // default to reading 5 messages at a time (unless otherwise specified via the maxRows parameter)
        private int delayFactor = 1; // default the delay between sends

        protected AsyncEmailContext(PublisherChannel pc, HttpServletRequest request) {
            super(PATH, pc);
            String maxRows = request.getParameter("maxRows");
            if (maxRows != null) {
                try {
                    this.rowCount = Integer.valueOf(maxRows);
                }
                catch (NumberFormatException e) {
                    // ignore
                }
            }

            String delayFactor = request.getParameter("delay");
            if (delayFactor != null) {
                try {
                    this.delayFactor = Integer.valueOf(delayFactor);
                }
                catch (NumberFormatException e) {
                    // ignore
                }
            }
        }

        @Override
        public boolean isEmail() {
            return true;
        }

        public int getRowCount() {
            return rowCount;
        }

        public int getDelayFactor() {
            return delayFactor;
        }

        @Override
        public String toString() {
            return "AsyncEmailContext{" + getChannel() + ": " +
                    "rowCount=" + rowCount +
                    '}';
        }
    }

    public static class EmailContext extends PublisherContext {
        public static final String PATH = "/email";

        private EmailContext(PublisherChannel pc, Arrival arrival, UserProfile userProfile, Lead lead) throws SQLException, NamingException {
            super(PATH, pc, arrival, userProfile, lead);
        }

        /**
         * @see PublisherContext
         */
        private EmailContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException, PonyException {
            super(PATH, pc, request);
        }

        @Override
        public boolean isEmail() {
            return true;
        }

        public String toString() {
            return "EmailContext for " + PATH + ":" + getChannel();
        }
    }
    
    public static class SubscriptionContext extends PublisherContext {
    	public static final String PATH = "/subscribe";
    	
    	/**
    	 * Constructs publisher context. See {@link #PublisherContext(PublisherChannel, HttpServletRequest)}
    	 * @param pc
    	 * @param arrival
    	 * @param userProfile
    	 * @param lead
    	 * @throws SQLException
    	 * @throws NamingException
    	 */
        private SubscriptionContext(PublisherChannel pc, Arrival arrival, UserProfile userProfile, Lead lead) throws SQLException, NamingException {
            super(PATH, pc, arrival, userProfile, lead);
        }

        /**
         * Constructs publisher context. See {@link #PublisherContext(PublisherChannel, HttpServletRequest)}
         * @param pc
         * @param request
         * @throws SQLException
         * @throws NamingException
         */
        private SubscriptionContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException, PonyException {
            super(PATH, pc, request);
        }
        
        @Override
        public boolean isSubscription(){
        	return true;
        }
        
        public String toString(){
        	return "SubscriptionContext for "+PATH+": " + getChannel();
        }
    }

    public static class PingContext extends PublisherContext {
        private static final String PATH = "/ping";

        protected PingContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException {
            super(PATH, pc);

            // parse the rest
            String w = request.getParameter(HTTP_WAIT_FOR_RESPONSE);
            if (w == null) {
                w = request.getHeader(HTTP_WAIT_FOR_RESPONSE);
            }
            wait = (w != null && Boolean.valueOf(w)); // default is not to wait

            // read the channel info and lookup what publisher, and lead type is being sent,
            // then parse the lead info

            // for test publisher, create test context to hold additional test info parameters (if any present, otherwise don't create the context)
            if (pc.getPublisher().isTest()) {
                testContext = TestContext.create(request);
            }

            userProfile = null; // we don't have an email address yet

            // is there one in the posted data?
            String email = UserProfile.parse(request);
            if (email != null && AddressValidator.isValid(email)) {
                email = UserProfileModelImpl.autoCorrectStatic(email);

                // DUP check 1: do we know this email address already?
                UserProfile dup = UserProfileModelImpl.findByEmailStatic(email);
                if (dup != null) {
                    userProfile = dup;
                    UserProfileModelImpl.touchStatic(dup);
                }
                else {
                    userProfile = UserProfileModelImpl.createStatic(email);
                }
            }

            // this is new, or new from this publisher
            Arrival a = Arrival.parse(pc, userProfile, request);
            arrival = ArrivalModelImpl.createStatic(a);

            lead = pc.getLeadType().parseLead(userProfile, arrival, request, null);

            // check if this is the first ping or not. We might get pinged several times for the same arrival / user_profile
            lead = pc.getLeadType().persistLead(userProfile, arrival, lead);
        }

        @Override
        public boolean isPing() {
            return true;
        }

        public String toString() {
            return "PingContext for " + PATH + ":" + getChannel();
        }
    }

    private static class PollContext extends PublisherContext {
        private static final String PATH = "/poll";

        private PollContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException, PonyException {
            super(PATH, pc);

            // the client gives us the ref back to get the ping responses for
            String leadRef = request.getParameter(LEAD_REF);

            userProfile = null;
            lead = null;
            arrival = null;

            if (leadRef != null) {
                Long leadRefId = Long.valueOf(leadRef);
                LeadType leadType = pc.getLeadType();

                arrival = ArrivalModelImpl.findByLeadIdStatic(leadRefId);
                lead = leadType.findLead(leadRefId);
                if (lead != null) {
                    lead.mergeAttributes(request);
                }
            }
            wait = true;

            // read the channel info and lookup what publisher, and lead type is being sent,
            // then parse the lead info

            // for test publisher, create test context to hold additional test info parameters (if any present, otherwise don't create the context)
            if (pc.getPublisher().isTest()) {
                testContext = TestContext.create(request);
            }
        }

        @Override
        public boolean isPoll() {
            return true;
        }

        public String toString() {
            return "PollContext for " + PATH + ":" + getChannel() + " leadId=" + (lead == null ? "null" : lead.getId());
        }
    }

    public static class ResendContext extends PublisherContext {
        private static final String PATH = "/resend";

        private final String resendSequenceName;
        private final int hoursBack;

        protected ResendContext(PublisherChannel pc, HttpServletRequest request) throws SQLException, NamingException, PonyException {
            super(PATH, pc, request);

            // see what resend is requested

            int hoursBack = ResendMessagePhase.START.getHoursToPreviousPhase(); //default
            String hours = request.getParameter("hoursback");
            if (hours != null) {
                try {
                    hoursBack = Integer.valueOf(hours.trim());
                }
                catch (NumberFormatException e) {
                    // ignore (use default)
                }
            }

            this.resendSequenceName = pc.getPublisherList().getExternalId();
            this.hoursBack = hoursBack;
        }

        public String getResendSequenceName() {
            return resendSequenceName;
        }

        public int getHoursBack() {
            return hoursBack;
        }

        @Override
        public boolean isResend() {
            return true;
        }

        public String toString() {
            return "ResendContext for " + PATH + ": sequence=" + getResendSequenceName() + ": hours=" + getHoursBack() + ": " + getChannel();
        }
    }

    public static class NewsletterContext extends PublisherContext {
        public static final String PATH = "/newsletter";

        private int delayFactor = 1; // default the delay between sends
        private int rowCount = 1000;  // default the max number of arrivals we will attempt to send in one run

        private static final List<String> EXCLUDED_TOKENS = new ArrayList<String>();

        static {
            EXCLUDED_TOKENS.add("maxRows");
            EXCLUDED_TOKENS.add("delay");
            EXCLUDED_TOKENS.add("ref");
            EXCLUDED_TOKENS.add("domtok");
            EXCLUDED_TOKENS.add("username");
            EXCLUDED_TOKENS.add("password");
            EXCLUDED_TOKENS.add("email");
            EXCLUDED_TOKENS.add("listid");
        }

        private NewsletterContext(PublisherChannel pc, HttpServletRequest request) {
            super(PATH, pc);

            String maxRows = request.getParameter("maxRows");
            if (maxRows != null) {
                try {
                    rowCount = Integer.valueOf(maxRows);
                }
                catch (NumberFormatException e) {
                    // ignore
                }
            }

            String delay = request.getParameter("delay");
            if (delay != null) {
                try {
                    delayFactor = Integer.valueOf(delay);
                }
                catch (NumberFormatException e) {
                    // ignore
                }
            }

            // get the rest of the posted params and hold on to them, so we can use them for template subs in the messages
            Enumeration en = request.getParameterNames();
            while (en.hasMoreElements()) {
                String param = (String) en.nextElement();

                if (EXCLUDED_TOKENS.contains(param)) {
                    continue;
                }

                params.put(param, request.getParameter(param));
            }
        }

        public int getDelayFactor() {
            return delayFactor;
        }

        public int getRowCount() {
            return rowCount;
        }

        @Override
        public boolean isEmail() {
            return true;
        }

        @Override
        public String toString() {
            return "NewsletterContext{" +
                    "delayFactor=" + delayFactor +
                    ", rowCount=" + rowCount +
                    '}';
        }
    }
}
