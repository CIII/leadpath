package com.pony.publisher;

import com.pony.PonyException;
import com.pony.advertiser.AdvertiserService;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.DispositionContext;
import com.pony.advertiser.PollResponse;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyService;
import com.pony.email.EmailService;
import com.pony.email.EmailTargetQueue;
import com.pony.email.Resender;
import com.pony.email.SmtpException;
import com.pony.form.FormState;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.leadtypes.NewCar;
import com.pony.leadtypes.PonyLead;
import com.pony.models.ArrivalModelImpl;
import com.pony.models.EmailTargetQueueModel;
import com.pony.models.LeadModel;
import com.pony.models.PublisherListModel;
import com.pony.models.UserProfileModelImpl;
import com.pony.rules.RuleService;
import com.pony.validation.ValidationException;
import com.pony.validation.ValidationResponse;
import com.pony.validation.ValidationService;
import com.tapquality.email.subscribe.MailChimpClient;
import com.tapquality.email.subscribe.MailChimpMember;
import com.tapquality.email.subscribe.SubscriptionFormat;
import com.tapquality.email.subscribe.MailChimpClient.MailChimpList;
import com.tapquality.formatter.ValidationFailureMessage;
import com.tapquality.processors.DefaultValuesSetter;
import com.tapquality.processors.DetectDuplicates;
import com.tapquality.processors.ParameterScrubber;
import com.tapquality.processors.PingMessageTask;
import com.tapquality.processors.PostMessageTask;
import com.tapquality.processors.PostValidationTask;
import com.tapquality.processors.ProcessingTask;
import com.tapquality.processors.SubscribeUserTask;
import com.tapquality.processors.WhitepagesComponent;
import com.tapquality.processors.whitepages.WhitepagesDAO;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.pony.publisher.PublisherContext.PostContext;
import com.pony.publisher.PublisherContext.SubscriptionContext;

/**
 * Copyright 2011 PonyMash LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 5:19 PM
 */
public class PublisherService extends PonyService {
	private static final Log LOG = LogFactory.getLog(PublisherService.class);
    private RuleService ruleService;
    private AdvertiserService advertiserService;
    private ValidationService validationService;
    private EmailService emailService;
    
    @Inject
    protected WhitepagesDAO whitepagesDAO;
    
    public PublisherService() {
        super();
    }

    public void addValidationService(ValidationService service) {
        if (validationService != null) {
            throw new IllegalStateException("validationService already set");
        }
        this.validationService = service;
    }

    public void addPublisherService(PublisherService service) {
        //noop
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    public void addRuleService(RuleService service) {
        if (this.ruleService != null) {
            throw new IllegalStateException("ruleService already set");
        }
        this.ruleService = service;
    }

    public void addAdvertiserService(AdvertiserService service) {
        if (this.advertiserService != null) {
            throw new IllegalStateException("advertiserService already set");
        }
        this.advertiserService = service;
    }


    public void addEmailService(EmailService service) {
        if (this.emailService != null) {
            throw new IllegalStateException("emailService already set");
        }
        this.emailService = service;
    }
    
    private PublisherResponse ping(IPublisherContext publisherContext) throws PonyException {
        List<ProcessingTask> tasks = new ArrayList<>();
        tasks.add(new PostValidationTask(this.validationService));
        tasks.add(new PingMessageTask(this));
        try {
        	for(ProcessingTask task : tasks) {
        		publisherContext = task.process(publisherContext);
        	}
        } catch (PublisherException e) {
        	LOG.debug("Not valid: " + publisherContext.getValidationResponse().toString());
            PublisherResponse publisherResponse = PublisherResponse.createPostResponseNotValid(publisherContext.getValidationResponse());
            publisherContext.setPublisherResponse(publisherResponse);
        }
        
        PublisherResponse publisherResponse = publisherContext.getPublisherResponse();

        return publisherResponse;

    }

    public IPublisherContext pingInternal(IPublisherContext publisherContext) throws PonyException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOG.info(df.format(Calendar.getInstance().getTime()) + ": ping");
        //execute a ping
        try {
            ValidationResponse vResponse = validationService.ping(publisherContext);

            if (publisherContext.getArrival() != null) {
                ArrivalModelImpl.touchStatic(publisherContext.getArrival(), vResponse);
            }

            if (vResponse.isValid()) {
                if (publisherContext.waitForResponse()) {
                    Map<RoutingCandidate, Disposition> responses = advertiserService.ping(publisherContext, vResponse);

                    publisherContext.setPublisherResponse(PublisherResponse.createPingResponse(publisherContext.getLead(), vResponse, responses));
                }
                else {
                    //only write a lead id and hand back the id for future polling of that id

                    // send a signal to the processing queue
                    advertiserService.notifyPing(publisherContext, vResponse);

                    publisherContext.setPublisherResponse(PublisherResponse.createPingResponseNoWait(publisherContext, vResponse));
                }
            }
            else {
                publisherContext.setPublisherResponse(PublisherResponse.createPostResponseNotValid(vResponse));
            }
        }
        catch (ValidationException e) {
            throw new PonyException(e);
        }
        catch (SQLException e) {
            throw new PonyException(e);
        }
        catch (NamingException e) {
            throw new PonyException(e);
        }
        
        return publisherContext;
    }

    /**
     * look for lead with that id and return all data we have about it
     *
     * @param publisherContext
     * @return
     */
    private PublisherResponse poll(IPublisherContext publisherContext) throws PonyException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOG.debug(df.format(Calendar.getInstance().getTime()) + ": poll");
        try {
            ValidationResponse vResponse = validationService.poll(publisherContext);
            if (vResponse.isValid()) {
                PollResponse pollResponse = advertiserService.poll(publisherContext);
                return PublisherResponse.createPollResponse(vResponse, publisherContext.getLeadId(), pollResponse);
            }
            else {
                return PublisherResponse.createPollResponse(vResponse, publisherContext.getLeadId(), null);
            }
        }
        catch (ValidationException e) {
            LOG.error(e);
            throw new PonyException(e);
        }
        catch (SQLException e) {
            LOG.error(e);
            throw new PonyException(e);
        }
        catch (NamingException e) {
            LOG.error(e);
            throw new PonyException(e);
        }
    }

    private PublisherResponse resend(IPublisherContext context) throws SmtpException {
        PublisherContext.ResendContext resendContext = (PublisherContext.ResendContext) context;

        try {
            Resender resender = Resender.create(resendContext.getResendSequenceName(), resendContext.getHoursBack());
            resender.fetchNew(); // get all new ones (from the past <hoursBack> hours)
            long messageCount = resender.processPhases(emailService); // check, send, move to next phase (with start time)
            return PublisherResponse.createNoopResponse(context, messageCount);
        }
        catch (NamingException e) {
            throw new SmtpException(e);
        }
        catch (SQLException e) {
            throw new SmtpException(e);
        }
        catch (PonyException e) {
            throw new SmtpException(e);
        }

    }

    /**
     * Subscribe a user to MailChimp.
     * 
     * @param context
     * @return
     * @throws PublisherException 
     */
    public PublisherResponse subscribe(IPublisherContext context, MailChimpList list) throws PublisherException {
		try {
			Lead lead = context.getLead();
			String firstName = lead.getAttributeValue("first_name");
			String lastName = lead.getAttributeValue("last_name");
			if (!"mickey".equalsIgnoreCase(firstName) || !"mouse".equalsIgnoreCase(lastName)) {
				MailChimpClient mcClient = new MailChimpClient();
				MailChimpMember mcMember = new MailChimpMember();
				mcMember.setEmailAddress(context.getUserProfile().getEmail());
				mcMember.setStatus(MailChimpMember.MemberStatus.Subscribed);
				String resp = mcClient.addMemberToList(mcMember, list);
				return PublisherResponse.createSubscriptionResponse(resp);
			} else {
				return PublisherResponse.createSubscriptionResponse("none");
			}
		}	
		catch (IOException | PonyException e) {
			LOG.error("Exception subscribing the user to MailChimp list. Skipping this step and continuing the post.");
			return PublisherResponse.createSubscriptionResponse("none");
		}
	}

    private PublisherResponse email(IPublisherContext context) throws SmtpException {
        try {

            if (context instanceof PublisherContext.AsyncEmailContext) {
                return processAsyncEmails((PublisherContext.AsyncEmailContext) context);
            }
            else if (context instanceof PublisherContext.NewsletterContext) {
                return processNewsletterEmails((PublisherContext.NewsletterContext) context);
            }
            else {
                ValidationResponse vResponse = validationService.email(context);

                // log the results of the validation(s) in the arrival, if there is an arrival
                if (context.getArrival() != null) {
                    ArrivalModelImpl.touchStatic(context.getArrival(), vResponse);
                }

                // if we passed validation, we can start sending
                if (vResponse.isValid()) {
                    return emailService.send(context);
                }

                return PublisherResponse.createEmailResponseNotValid(context, vResponse);
            }
        }
        catch (ValidationException e) {
            throw new SmtpException(e);
        }
        catch (SQLException e) {
            throw new SmtpException(e);
        }
        catch (NamingException e) {
            throw new SmtpException(e);
        }
    }
    

    private PublisherResponse processNewsletterEmails(PublisherContext.NewsletterContext newsletterContext) throws NamingException, SQLException {
        //TODO
        /*
            -- email opens for resend
            select up.email, max(m.created_at) last_send, count(distinct m.id) d_opened_messages, count(distinct o.id) d_opens, sum(if(o.open_count =0, 1, o.open_count)) opens,  min(o.created_at) first_open, max(o.created_at) last_open, datediff(max(o.created_at), min(o.created_at)) first_last_open_datediff, datediff(min(o.created_at), min(m.created_at)) send_open_datediff from user_profiles up join messages m on m.user_profile_id = up.id join opens o on o.message_id = m.id where o.created_at >'2013-11-01'  group by up.id having datediff(curdate(), max(m.created_at)) >= 2 and datediff(curdate(), max(o.created_at)) > 1

            -- send to open delay frequency:
            select open_delay, count(*) from(select up.id, datediff(min(o.created_at), min(m.created_at)) open_delay from user_profiles up join messages m on m.user_profile_id = up.id join opens o on o.message_id = m.id where o.created_at >'2013-11-01'  group by up.id )x group by open_delay order by count(*) desc

            -- emails that were delivered and not spammed or unsubscribed from
            select count(*) from user_profiles up join messages m on m.user_profile_id = up.id left join bounces b on b.user_profile_id = up.id left join unsubscribes u on u.user_profile_id = up.id left join complaints c on c.user_profile_id = up.id left join md5_suppressions s1 on s1.md5_email = md5(up.email) where b.id is null and u.id is null and c.id is null and s1.id is null and datediff(curdate(), up.created_at) < 10 and m.status = 4
         */

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOG.info(df.format(Calendar.getInstance().getTime()) + " >> newsletter email start sending for: " + newsletterContext);

        // read transactions send queue for the incoming list id
        // : select max_arrival_id, from_publisher_list_id from email_target_queues where to_publisher_list_id = ?
        EmailTargetQueue queue = EmailTargetQueueModel.find(newsletterContext.getChannel().getPublisherList());

        // read n arrivals after max_arrival_id and update the max_arrival_id
        if (queue == null) {
            throw new IllegalStateException("Configuration mismatch: No email target queue setup for this channel: " + newsletterContext.getChannel());
        }

        PublisherList fromPublisherList = PublisherListModel.find(queue.getFromPublisherListId());
        LeadType fromLeadType = fromPublisherList.getLeadType();

        List<Arrival> arrivals = ArrivalModelImpl.findRangeStatic(fromPublisherList, queue.getOpenWithinDays(), queue.getSendFrequencyDays(), newsletterContext.getRowCount());

        int i = 0;
        int sent = 0, invalid = 0;
        Long userProfileId = null;

        for (Arrival arrival : arrivals) {
            try {
                // until I can figure this out, try to avoid DUPs
                if (userProfileId != null && userProfileId.equals(arrival.getUserProfileId())) {
                    LOG.debug("skipping " + arrival);
                    continue;
                }
                else {
                    userProfileId = arrival.getUserProfileId();
                }

                // for each arrival: read the other references we need
                UserProfile up = UserProfileModelImpl.findStatic(arrival.getUserProfileId());
                Map<String, String> attributes = UserProfileModelImpl.readProfileAttributesStatic(up.getId());

                // merge in the parameters that came in the newsletter post
                LOG.info(">> Newsletter Email for [" + up.getEmail() + "]: merging " + newsletterContext.getParams().size() + " global params to lead attributes");
                attributes.putAll(newsletterContext.getParams());

                // read the lead of the original lead type (as was specified in the email target queue
                Long leadId = LeadModel.find(fromLeadType.getId(), up.getId(), arrival.getId());
                if (leadId == null) {
                    LOG.info(">> Newsletter Email WARNING: no lead found for " + fromLeadType + ": " + arrival);
                    continue;
                }

                // create a dummy lead shell so the downstream code has one to chew on ... (not persisted since we already have one!)
                Lead lead = PonyLead.create(leadId, up, arrival, attributes);

                // create email context
                PublisherContext emailContext = PublisherContext.create(PublisherContext.EmailContext.PATH, newsletterContext.getChannel(), arrival, up, lead);

                ValidationResponse vResponse = validationService.email(emailContext);
                if (vResponse.isValid()) {
                    // since this a newsletter, and we created an 'artificial' lead above to satisfy the rest of the apis, we have a lead type of 'Pony' instead of 'Email'
                    // and so we need to do the email vaidation (check for unsubs, ...) here by hand
                    // TODO: can't we use lead type Email for the context ??
                    if (fromLeadType.validate(emailContext).isValid()) {
                        // send message
                        emailService.send(emailContext);
                        sent++;
                    }
                    else {
                        invalid++;
                    }
                }
                else {
                    invalid++;
                }
            }
            catch (SQLException e) {
                LOG.error(e);
            }
            catch (SmtpException e) {
                LOG.error(e);
            }
            catch (NamingException e) {
                LOG.error(e);
            }
            catch (ValidationException e) {
                invalid++;
                LOG.error(e);
            }

            // a little bit of random wait (to make it more transactional looking)
            if ((i++ % (Double.valueOf(Math.random() * 10).intValue() * 3 + 1)) == 0) {
                try {
                    Thread.sleep(Double.valueOf(Math.random() * 10).longValue() * 650L * newsletterContext.getDelayFactor());
                }
                catch (InterruptedException e) {
                    LOG.error(e);
                }
            }
            if (i > 10000) {
                i = 0;
            }
        }

        LOG.info(df.format(Calendar.getInstance().getTime()) + " >> newsletter email end of send for: " + newsletterContext + " ==> sent=" + sent + "; invalid=" + invalid);
        return null;
    }

    private PublisherResponse processAsyncEmails(PublisherContext.AsyncEmailContext asyncEmailContext) throws NamingException, SQLException {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOG.info(df.format(Calendar.getInstance().getTime()) + " >> AsyncEmail start sending for: " + asyncEmailContext);

        // read transactions send queue for the incoming list id
        // : select max_arrival_id, from_publisher_list_id from email_target_queues where to_publisher_list_id = ?
        EmailTargetQueue queue = EmailTargetQueueModel.find(asyncEmailContext.getChannel().getPublisherList());

        // read n arrivals after max_arrival_id and update the max_arrival_id
        if (queue == null) {
            throw new IllegalStateException("Configuration mismatch: No email target queue setup for this channel: " + asyncEmailContext.getChannel());
        }

        PublisherList fromPublisherList = PublisherListModel.find(queue.getFromPublisherListId());
        LeadType fromLeadType = fromPublisherList.getLeadType();

        List<Arrival> arrivals = ArrivalModelImpl.findRangeStatic(fromPublisherList, queue.getMaxArrivalId() + 1, asyncEmailContext.getRowCount());

        // update the max arrival id
        if (arrivals.size() > 0) {
            EmailTargetQueueModel.updateMaxArrivalId(queue, arrivals.get(arrivals.size() - 1).getId());
        }

        int i = 0;
        int sent = 0, invalid = 0;
        for (Arrival arrival : arrivals) {
            try {
                // for each arrival: read the other references we need
                UserProfile up = UserProfileModelImpl.findStatic(arrival.getUserProfileId());
                Map<String, String> attributes = UserProfileModelImpl.readProfileAttributesStatic(up.getId());

                // read the lead of the original lead type (as was specified in the email target queue
                Long leadId = LeadModel.find(fromLeadType.getId(), up.getId(), arrival.getId());
                if (leadId == null) {
                    LOG.info(">> AsyncEmail WARNING: no lead found for " + fromLeadType + ": " + arrival);
                    continue;
                }

                Lead lead = PonyLead.create(leadId, up, arrival, attributes);

                // create email context
                PublisherContext emailContext = PublisherContext.create(PublisherContext.EmailContext.PATH, asyncEmailContext.getChannel(), arrival, up, lead);

                ValidationResponse vresponse = validationService.email(emailContext);
                if (vresponse.isValid()) {
                    // send message
                    emailService.send(emailContext);
                    sent++;
                }
                else {
                    invalid++;
                }
            }
            catch (SQLException e) {
                LOG.error(e);
            }
            catch (SmtpException e) {
                LOG.error(e);
            }
            catch (NamingException e) {
                LOG.error(e);
            }
            catch (ValidationException e) {
                invalid++;
                LOG.error(e);
            }

            // a little bit of random wait (to make it more transactional looking)
            if ((i++ % (Double.valueOf(Math.random() * 10).intValue() * 3 + 1)) == 0) {
                try {
                    Thread.sleep(Double.valueOf(Math.random() * 10).longValue() * 650L * asyncEmailContext.getDelayFactor());
                }
                catch (InterruptedException e) {
                    LOG.error(e);
                }
            }
            if (i > 10000) {
                i = 0;
            }
        }

        LOG.info(df.format(Calendar.getInstance().getTime()) + " >> AsyncEmail end of send for: " + asyncEmailContext + " ==> sent=" + sent + "; invalid=" + invalid);
        return null;
    }

    private PublisherResponse post(IPublisherContext publisherContext) throws PonyException, PublisherException {
        List<ProcessingTask> tasks = new ArrayList<>();
        tasks.add(new ParameterScrubber());
        tasks.add(new DefaultValuesSetter());
        tasks.add(new WhitepagesComponent(whitepagesDAO));
        tasks.add(new DetectDuplicates());
        tasks.add(new PostValidationTask(this.validationService));
        tasks.add(new PostMessageTask(this));
        tasks.add(new SubscribeUserTask(this));
        try {
        	for(ProcessingTask task : tasks) {
        		publisherContext = task.process(publisherContext);
        	}
        } catch (PublisherException e) {
        	LogFactory.getLog("validation-log").error(new ValidationFailureMessage((PublisherContext)publisherContext, publisherContext.getValidationResponse()).toString());
        	LOG.info("Not valid: " + publisherContext.getValidationResponse().toString());
            PublisherResponse publisherResponse = PublisherResponse.createPostResponseNotValid(publisherContext.getValidationResponse());
            publisherContext.setPublisherResponse(publisherResponse);
        }
        
        PublisherResponse publisherResponse = publisherContext.getPublisherResponse();

        return publisherResponse;
    }

	public IPublisherContext postInternal(IPublisherContext publisherContext) throws PonyException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOG.info(df.format(Calendar.getInstance().getTime()) + ": post");
        //execute a post
        try {
            //ValidationResponse vResponse = validationService.post(publisherContext);
        	ValidationResponse vResponse = publisherContext.getValidationResponse();

            //if (vResponse.isValid()) {            	
                UserProfile userProfile = publisherContext.getUserProfile();

                // if the lead isn't already stored, do so now
                Long leadId = publisherContext.getLead().getId();
                if (leadId == null) {
                    Lead lead = publisherContext.getLeadType().persistLead(userProfile, publisherContext.getArrival(), publisherContext.getLead());
                    leadId = lead.getId();
                }
                
                if (publisherContext.waitForResponse()) {
                    Map<RoutingCandidate, Disposition> responses = advertiserService.post(publisherContext, vResponse);

                    // TODO: This, oddly, does not put the leadId into the response.
                    publisherContext.setPublisherResponse(PublisherResponse.createPostResponse(vResponse, responses));
                }
                else {
                    //only write a lead id and hand back the id for future polling of that id

                    // send a signal to the processing queue
                    advertiserService.notifyPost(publisherContext, vResponse, leadId);

                    // pass back the lead id as a handle to check back in later
                    publisherContext.setPublisherResponse(PublisherResponse.createPostResponseNoWait(leadId));
                }
            /*}
            else {
            	LogFactory.getLog("validation-log").error(new ValidationFailureMessage(publisherContext, vResponse).toString());
            	LOG.info("Not valid: " + vResponse.toString());
                return PublisherResponse.createPostResponseNotValid(vResponse);
            }*/
        }
        /*catch (ValidationException e) {
            throw new PonyException(e);
        }*/
        catch (SQLException | NamingException e) {
        	LOG.error("Exception persisting the lead.", e);
            throw new PonyException("Exception persisting the lead.", e);
        }
        
        return publisherContext;
	}

    private PublisherResponse validate(IPublisherContext context) {
        return PublisherResponse.createValidationResponse(context);
    }

    public PublisherResponse execute(IPublisherContext context) throws PublisherException, SmtpException {
        try {
            if (context.isPing()) {
                return ping(context);
            }
            else if (context.isPost()) {
                return post(context);
            }
            else if (context.isRePost()) {
                return rePost(context);
            }
            else if (context.isValidation()) {
                return validate(context);
            }
            else if (context.isPoll()) {
                return poll(context);
            }
            else if (context.isEmail()) {
                return email(context);
            }
            else if (context.isSubscription()) {
                return subscribe(context, MailChimpList.EasierSolarInsights);
            }
            else if (context.isResend()) {
                return resend(context);
            }
            else if (context.isForm()) {
                // Form posts are handled as posts
                return post(context);
            }
        }
        catch (PonyException e) {
            throw new PublisherException(e);
        }

        throw new PublisherException("unknown context type to execute:" + context.toString());
    }

    private PublisherResponse rePost(IPublisherContext publisherContext) throws PonyException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LOG.info(df.format(Calendar.getInstance().getTime()) + ": repost");
        //execute a post
        try {
            ValidationResponse vResponse = validationService.post(publisherContext);

            ArrivalModelImpl.touchStatic(publisherContext.getArrival(), vResponse);
            if (vResponse.isValid()) {
                Long leadId = publisherContext.getLead().getId();
                if (leadId == null) {
                    return PublisherResponse.NO_VALID_LEAD_REFERENCE;
                }

                if (publisherContext.waitForResponse()) {
                    Map<RoutingCandidate, Disposition> responses = advertiserService.post(publisherContext, vResponse);

                    return PublisherResponse.createPostResponse(vResponse, responses);
                }
                else {
                    //only write a lead id and hand back the id for future polling of that id

                    // send a signal to the processing queue
                    advertiserService.notifyPost(publisherContext, vResponse, leadId);

                    // pass back the lead id as a handle to check back in later
                    return PublisherResponse.createPostResponseNoWait(leadId);
                }
            }
            else {
                return PublisherResponse.createPostResponseNotValid(vResponse);
            }
        }
        catch (ValidationException e) {
            throw new PonyException(e);
        }
        catch (SQLException e) {
            throw new PonyException(e);
        }
        catch (NamingException e) {
            throw new PonyException(e);
        }
    }

    /**
     * format the response back to the publisher.
     *
     * @param context
     * @param publisherResponse
     * @param servletResponse
     */
    public void formatResponse(PublisherContext context, PublisherResponse publisherResponse, FormState formState, HttpServletResponse servletResponse)
            throws IOException {
//        //todo: take the response data and write an http response for it
//        // based on publisher and response, format a message back
//        Publisher publisher = context.getPublisher();
//        publisherResponse.getStatus();

        // TODO: if success, render a thank you .... if the form has one defined

        if (formState != null && formState.getForm().getClickoutFeed() != null) {
            // render the clickout feed
            formState.getForm().getClickoutFeed().render(formState, servletResponse);
        }
        else {
            PublisherFormat format = getPublisherFormat(context);
            format.format(publisherResponse, servletResponse);
        }
    }

    private PublisherFormat getPublisherFormat(PublisherContext context) {
        // cars specific response format
        // TODO: make this externalized and configurable by publisher: new class PublisherFormat ?
        if (context.getLeadType() instanceof NewCar) {
            return new CarPublisherFormat(context);
        } else if (((context instanceof PostContext) || (context instanceof PublisherContext.PingContext)) && !context.getValidationResponse().isValid()) {
        	return new PostErrorFormat(context);
        } else if (context instanceof PostContext) {
        	return new PostFormat(context);
        } else if (context instanceof SubscriptionContext){
        	return new SubscriptionFormat(context);
        }
        
        return new PublisherFormat(context);
    }

    /**
     * format the response back to the publisher.
     *
     * @param servletResponse
     */
    public void formatResponse(HttpServletResponse servletResponse)
            throws IOException {

        servletResponse.setContentType("text/plain");
        Writer out = servletResponse.getWriter();
        out.write("success");
        out.flush();
        out.close();
        
        servletResponse.setStatus(HttpServletResponse.SC_OK);
    }
    
    /**
     * Format a response in the case of an exception.  Returns the status as 500
     * 
     * @param servletResponse
     * @param e
     * @throws IOException 
     */
    public void formatResponse(HttpServletResponse servletResponse, Throwable e) throws IOException{
        servletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }

    /**
     * a disposition was made and needs to be passed on to the publisher (if they support this asynchronously)
     *
     * @param dispositionContext
     */
    public void dispose(DispositionContext dispositionContext) {
        //todo

    }

    @Override
    public String toString() {
        return "PublisherService{" +
                "ruleService=" + ruleService +
                ", advertiserService=" + advertiserService +
                ", validationService=" + validationService +
                ", emailService=" + emailService +
                '}';
    }

    public static void main(String[] args) {
        int i = 0, j = 0;

        while (j++ < 205) {
            LOG.debug("i=" + i + " j=" + j);
            if ((i++ % (Double.valueOf(Math.random() * 10).intValue() * 3 + 1)) == 0) {
                try {
                    Thread.sleep(Double.valueOf(Math.random() * 10).longValue() * 650L);
                }
                catch (InterruptedException e) {
                    LOG.error(e);
                }
            }
            if (i > 10000) {
                i = 0;
            }
        }
    }
}
