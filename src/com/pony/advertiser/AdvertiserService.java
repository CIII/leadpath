package com.pony.advertiser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pony.PonyException;
import com.pony.core.PonyPhase;
import com.pony.core.PonyService;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadMatch;
import com.pony.lead.LeadType;
import com.pony.models.*;
import com.pony.publisher.*;
import com.pony.routing.AdvertiserPostContext;
import com.pony.routing.PingContext;
import com.pony.rules.RuleResponse;
import com.pony.rules.RuleService;
import com.pony.validation.ValidationException;
import com.pony.validation.ValidationResponse;
import com.tapquality.dispositioneventpublisher.IDispositionEventPublisher;
import com.tapquality.formatter.ContextLeadMessage;
import com.tapquality.processors.EmailSchedulingTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Copyright 2011 PonyMash LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 5:19 PM
 */
public class AdvertiserService extends PonyService {
	private static final Log LOG = LogFactory.getLog(AdvertiserService.class);
	
    private RuleService ruleService;
    private PublisherService publisherService;
    private ExecutorService executor;
    private Map<String, AdvertiserWriter> advertiserWriters = null;
    private IDispositionEventPublisher dispositionEventPublisher;
    private Properties writerProperties = null;
    protected String lynxPath;
    protected ArrivalModel arrivalModel;

    @Inject AdvertiserService(
    		Map<String, AdvertiserWriter> advertiserWriters,
    		IDispositionEventPublisher dispositionEventPublisher,
    		@Named("writerProperties") Properties advertiserWriterProperties,
    		@Named("lynxPath") String lynxPath,
    		ArrivalModel arrivalModel
    		) {
    	this.advertiserWriters = advertiserWriters;
    	this.dispositionEventPublisher = dispositionEventPublisher;
    	this.writerProperties = advertiserWriterProperties;
    	this.lynxPath = lynxPath;
    	this.arrivalModel = arrivalModel;
    }
    
    @Override
    public void addRuleService(RuleService service) {
        if (ruleService != null) {
            throw new IllegalStateException("ruleService already set");
        }
        this.ruleService = service;
    }

    @Override
    protected void addAdvertiserService(AdvertiserService service) {
        //noop
    }

    @Override
    public void addPublisherService(PublisherService service) {
        if (publisherService != null) {
            throw new IllegalStateException("publisherService already set");
        }
        this.publisherService = service;
    }

    @Override
    public void start() {
        executor = Executors.newFixedThreadPool(100);
    }

    @Override
    public void stop() {
        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            LOG.error("Interruption during stop of the AdvertiserService.", e);
        }
        executor.shutdownNow();
        executor = null;
    }

    /**
     * combine ping and post into one call
     *
     * @param publisherContext
     * @param validationResponse
     * @return
     */
    public Map<RoutingCandidate, Disposition> pingAndPost(PublisherContext publisherContext, ValidationResponse validationResponse) throws PonyException {

        Map<RoutingCandidate, Disposition> pingDispositions = ping(publisherContext, validationResponse);

        return post(publisherContext, validationResponse, pingDispositions);
    }


    /**
     * Post a lead to one or more advertisers.
     *
     * @param publisherContext   info about the lead to post
     * @param validationResponse validation information for the lead
     * @return a map of routing candidates and the responses we received for the candidate posts
     * @throws PonyException
     */
    public synchronized Map<RoutingCandidate, Disposition> post(IPublisherContext publisherContext, ValidationResponse validationResponse)
            throws PonyException {

        Map<RoutingCandidate, Disposition> dispositions = post(publisherContext, validationResponse, null);
        publishLeadsRecord(publisherContext, dispositions);
        return dispositions;
    }

	private void publishLeadsRecord(IPublisherContext publisherContext,
			Map<RoutingCandidate, Disposition> dispositions) {
		if(publisherContext.getLead() != null &&
        		!("Mickey".equalsIgnoreCase(publisherContext.getLead().getAttributeValue("first_name")) &&
        				"Mouse".equalsIgnoreCase(publisherContext.getLead().getAttributeValue("last_name")))) {
        	Log log = LogFactory.getLog("leads");
        	log.error(new ContextLeadMessage(publisherContext.getLead(), dispositions).toString());
        }
	}


    private Map<RoutingCandidate, Disposition> post(IPublisherContext publisherContext, ValidationResponse validationResponse, Map<RoutingCandidate, Disposition> pingDispositions) throws PonyException {

        LOG.debug("post: " + publisherContext);
        // what do we have to post?
        PublisherList publisherList = publisherContext.getPublisherList();
        Publisher publisher = publisherContext.getPublisher();
        LeadType leadType = publisherContext.getLeadType();
        Arrival arrival = publisherContext.getArrival();
        String externalArrivalId = arrival.getExternalId();
        Lead lead = publisherContext.getLead();
        int maxLeadUnits = publisherContext.getPublisherList().getMaxLeadUnits();

        // where can we send it
        List<RoutingCandidate> candidates = new LinkedList<RoutingCandidate>();

        // if we already pinged our partners during this request, we can use the results
        if (pingDispositions != null) {
            candidates.addAll(getRoutingCandidates(pingDispositions));
            LOG.debug("dispositionCandidates:" + candidates.size());
        }
        else if (publisherContext.getBuyerIds().size() > 0) {
            // is this a post that references a previous ping (in earlier request)? (i.e. the user selected buyers from a list assembled from the ping data...)
            candidates.addAll(createBuyerCandidates(publisherContext));
            LOG.debug("buyerCandidates:" + candidates.size());
        }
        else {

            // is this a re-post?
            if (publisherContext.isRePost()) {
                RoutingCandidate candidate = getRoutingCandidate(((PublisherContext.RePostContext) publisherContext).getLeadMatchId());
                if (candidate != null) {
                    candidates.add(candidate);
                    maxLeadUnits = 1;
                }
                LOG.debug("repostCandidates:" + candidates.size());
            }
            else {
                // no ping yet, we determine the candidates based on io and filters
                candidates = getRoutingCandidates(publisherContext, PonyPhase.POST);
                LOG.debug("orderCandidates:" + candidates.size());
            }
        }

        AdvertiserPostContext postContext = AdvertiserPostContext.create(arrival, publisher, publisherList, leadType, lead, maxLeadUnits, candidates);
        int retries = 0;
        int MAX_RETRIES = 3;
        while (retries < MAX_RETRIES && postContext.hasMoreCandidates()) {
            LOG.debug("retry:" + retries);
            // give the rules a chance to have a say before we get started talking to advertisers
            RuleResponse ruleResponse = ruleService.beforePost(postContext, validationResponse);

            if (ruleResponse.stop()) {
                break;
            }

            // if the rules decide that we still want to route this lead, start routing and after each attempt,
            // give the rules a chance to react, based on the post outcome for the candidate
            Map<Long, BigDecimal> pixelBuyerRevenues = new HashMap<Long, BigDecimal>();
            Map<Long, Long> pixelIds = new HashMap<Long, Long>();

            while (postContext.hasMoreCandidates()) {
                RoutingCandidate candidate = postContext.getNextCandidate();
                LOG.debug("candidate:" + candidate);
                try {
                    Disposition postDisposition = post(publisherContext, postContext, validationResponse, candidate);
                    LOG.debug("   postDisposition:" + postDisposition);

                    if (postDisposition.isAccepted()) {
                        // if this post is in response to a ping (and poll), then don't worry, and simply post everything the user selected
                        if (candidate.getBuyer() != null) {
                            // if this is one of the selected buyers in a post in reference to a ping,
                            // we need to add up revenue for this arrival before we fire the success pixel
                            // note: the pixel id can be different per order, so we need to separate those out
                            BigDecimal buyerPrice = candidate.getBuyer().getPrice();
                            if (candidate.getIo().getPixelId() > 0L && (buyerPrice.doubleValue() > 0.0d)) {
                                BigDecimal pixelBuyerRevenue = pixelBuyerRevenues.get(candidate.getLeadMatchId());
                                if (pixelBuyerRevenue == null) {
                                    pixelBuyerRevenue = new BigDecimal(0.00);
                                }
                                pixelBuyerRevenue = pixelBuyerRevenue.add(buyerPrice);
                                pixelBuyerRevenues.put(candidate.getLeadMatchId(), pixelBuyerRevenue);
                                pixelIds.put(candidate.getLeadMatchId(), candidate.getIo().getPixelId());
                            }
                            continue;
                        }
                    }

                    RuleResponse afterResponse = ruleService.afterPost(postContext, candidate, postDisposition);
                    if (afterResponse.stop()) {
                        break;
                    }

                    if (afterResponse.restack()) {
                        retries++;
                        break;
                    }
                }
                catch (NamingException e) {
                    //TODO: better error handling
                    LOG.error("SEVERE: Error on post for:" + candidate, e);
                }
                catch (SQLException e) {
                    //TODO: better error handling
                    LOG.error("SEVERE: Error on post for:" + candidate, e);
                }
                catch (Throwable e) {
                    LOG.error("SEVERE: Error on post for:" + candidate, e);
                }
            }
        }

        return postContext.getDispositions();
    }

   // Used for reporting arrival and corresponding lead matches object
    public static class ArrivalLeadMatchesMessage {
        private Arrival arrival;
        private List<MessageLead> leads;

        public ArrivalLeadMatchesMessage(Arrival arrival, List<LeadMatch> leadMatches) {
        	this.arrival = arrival;
        	
        	Map<Long, MessageLead> messageLeads = new HashMap<>();
        	for(LeadMatch match : leadMatches) {
        		MessageLead messageLead = messageLeads.get(match.getLeadId());
        		if(messageLead == null) {
        			List<LeadMatch> newList = new ArrayList<>();
        			newList.add(match);
        			messageLead = new MessageLead(match.getLeadId(), newList);
        			messageLeads.put(match.getLeadId(), messageLead);
        		} else {
        			messageLead.getLeadMatches().add(match);
        		}
        	}
        	
        	this.leads = new ArrayList<>();
        	this.leads.addAll(messageLeads.values());
        }

        public Arrival getArrival(){
            return this.arrival;
        }

        public List<MessageLead> getLeads(){
            return this.leads;
        }
        
        private static class MessageLead {
        	private final Long leadId;
        	private final List<LeadMatch> leadMatches;
        	
        	public MessageLead(Long leadId, List<LeadMatch> leadMatches) {
        		this.leadId = leadId;
        		this.leadMatches = leadMatches;
        	}

			public Long getLeadId() {
				return leadId;
			}

			public List<LeadMatch> getLeadMatches() {
				return leadMatches;
			}
        }
    }

    /**
     * Fire report to lynx with all leadmatches associated with lead
     * @param leadMatch
     */
    public void fireLynxDisposition(LeadMatch leadMatch){

        try {

            String url = this.lynxPath + "/post_disposition";

            //Find arrival associated with lead match, but arrival can have multiple leadmatches

            Arrival arrival = this.arrivalModel.findByLeadMatchId(leadMatch.getId());
            List<LeadMatch> leadMatches = LeadMatchModel.findByArrivalID(arrival.getId());

            LOG.debug("Matches: " + leadMatches.toString());
            ArrivalLeadMatchesMessage lm = new ArrivalLeadMatchesMessage(arrival,leadMatches);
            ObjectMapper mapper = new ObjectMapper();
            String leadMatchesString = mapper.writeValueAsString(lm);
            LOG.info("Reporting to Lynx with disposition: " + leadMatchesString);

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", "Token token=9432fe20-25b0-4731-9de3-5fb3bce0f565");
            StringEntity entity = new StringEntity(leadMatchesString);
            httpPost.setEntity(entity);

            try {
                HttpResponse response = httpClient.execute(httpPost);
                if(response.getStatusLine().getStatusCode() != 200){
                    LOG.error("Unsuccessful response when reporting to Lynx with disposition " + leadMatchesString + ": " + response.getStatusLine().toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
                LOG.error("Unable to report to Lynx with disposition: " + leadMatchesString);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void getArrivalDisposition(HttpServletRequest req, HttpServletResponse resp, String external_id,
                                             Long publisher_id){

        //Need the publisher to distinguish between colliding external ids
        try {
            Publisher publisher = PublisherModel.find(publisher_id);
            Arrival arrival = this.arrivalModel.findByExternalId(publisher, external_id);
            List<LeadMatch> leadmatches = LeadMatchModel.findByArrivalID(arrival.getId());

            ArrivalLeadMatchesMessage message = new ArrivalLeadMatchesMessage(arrival, leadmatches);
            ObjectMapper mapper = new ObjectMapper();
            String leadMatchesString = mapper.writeValueAsString(message);
            LOG.info("Responding with report: " + leadMatchesString);

            resp.setHeader("Content-type", "application/json");
            resp.getWriter().write(leadMatchesString);
            resp.getWriter().flush();
            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

    }

    public void getLeadDisposition(long leadId){
        try {
            Long leadIdLong = new Long(leadId);
            Collection<Disposition> dispositions = LeadMatchModel.findDispositions(PonyPhase.POST, leadIdLong);
            LOG.info("Publishing disposition info for lead id: " + leadId);
            dispositionEventPublisher.publishDispositions(leadIdLong, dispositions);
        } catch (SQLException | NamingException e) {
            LOG.error(e.getMessage());
        }
    }


    /**
     * find the buyer candidate (dispositions) from the ping phase
     *
     * @param publisherContext
     * @return
     */
    private List<RoutingCandidate> createBuyerCandidates(IPublisherContext publisherContext) throws PonyException {
        List<RoutingCandidate> candidates = new ArrayList<RoutingCandidate>();

        try {
            List<Disposition> leadMatchDispositions = LeadMatchModel.findDispositions(PonyPhase.PING, publisherContext.getLeadId(), publisherContext.getBuyerIds());

            for (Disposition lmDisposition : leadMatchDispositions) {
                // get the IO and the price and external id from the ping response
                Io io = IoModel.findByDispositionId(lmDisposition.getId()).getIo();
                // for now to be even: we create one candidate per buyer
                if (lmDisposition.getBuyers().size() > 0) {
                    for (Buyer buyer : lmDisposition.getBuyers()) {
                        candidates.add(RoutingCandidate.create(io, lmDisposition, buyer));
                    }
                }
                else {
                    // Note: this would be an error: the passed in ids need to have a match!
                    throw new IllegalStateException("unable to match the disposition buyers!");
                    //candidates.add(RoutingCandidate.create(io, lmDisposition));
                }
            }
        }
        catch (NamingException e) {
            throw new PonyException(e);
        }
        catch (SQLException e) {
            throw new PonyException(e);
        }
        catch (ValidationException e) {
            throw new PonyException(e);
        }

        return candidates;
    }

    /**
     * async version of the post. use separate thread to post.
     *
     * @param publisherContext
     * @param vResponse
     * @param leadId
     */
    public void notifyPost(IPublisherContext publisherContext, ValidationResponse vResponse, Long leadId) throws PonyException {
        //todo : write to persistent queue and return (make sure workers are  up ??)
        LOG.info("notify");
        if (!executor.isShutdown()) {
            executor.submit(new PostWorker(publisherContext, vResponse));
            return;
        }

        throw new PonyException("Worker pool is shut down");
    }

    /**
     * get qualified candidates to route the lead to, based on the previous ping(s)
     *
     * @param pingDispositions
     * @return
     */
    public List<RoutingCandidate> getRoutingCandidates(Map<RoutingCandidate, Disposition> pingDispositions) throws PonyException {
        List<RoutingCandidate> candidates = new ArrayList<RoutingCandidate>();

        for (Map.Entry<RoutingCandidate, Disposition> entry : pingDispositions.entrySet()) {
            if (entry.getValue().isAccepted()) {
                RoutingCandidate candidate = entry.getKey();
                candidates.add(candidate);
            }
        }
        return candidates;
    }

    public RoutingCandidate getRoutingCandidate(Long leadMatchId) throws PonyException {
        try {
            final IoModel ioModel = IoModel.findByLeadMatchId(leadMatchId);
            if (ioModel != null) {
                return RoutingCandidate.create(ioModel.getIo());
            }
        }
        catch (SQLException e) {
            throw new PonyException(e);
        }
        catch (NamingException e) {
            throw new PonyException(e);
        }

        return null;
    }

    /**
     * get qualified candidates to route the lead to (without previous ping)
     *
     * @param publisherContext
     * @param phase
     * @return
     * @throws PonyException
     */
    public List<RoutingCandidate> getRoutingCandidates(IPublisherContext publisherContext, PonyPhase phase) throws PonyException {
        List<RoutingCandidate> candidates = new ArrayList<RoutingCandidate>();

        try {
            LeadType leadType = publisherContext.getLeadType();

            List<IoModel> ioList = new LinkedList<IoModel>();
            if (publisherContext.getChannel().getPublisherList().isDirect()) {
                ioList.addAll(IoModel.findDirect(leadType, publisherContext.getPublisherList()));
            }
            else {
                ioList.addAll(IoModel.findAll(leadType));
            }

            IoModel.applyCaps(ioList);
            // find all open ios for this lead type, and make sure we have cap left (filter out the ones that are caped)
            for (IoModel io : ioList) {
                if (io.getIo().matchLead(publisherContext, phase)) {
                    candidates.add(RoutingCandidate.create(io.getIo()));
                }
            }
        }
        catch (SQLException e) {
            throw new PonyException(e);
        }
        catch (NamingException e) {
            throw new PonyException(e);
        }
        catch (ValidationException e) {
            throw new PonyException(e);
        }
        return candidates;
    }

    /**
     * Post to one candidate
     *
     * @param postCtx
     * @param candidate
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    private Disposition post(IPublisherContext publisherContext, AdvertiserPostContext postCtx, ValidationResponse validationResponse, RoutingCandidate candidate)
            throws NamingException, SQLException {

        Long advertiserId = candidate.getIo().getAdvertiserId();

        Disposition pingDisposition = candidate.getDisposition();
        Disposition priceDisposition;
        Disposition postDisposition = null;

        Long leadMatchId = null;

        // if this post is in reference to a ping, we already have a lead match, so re-use that id
        if (pingDisposition != null) {
            LOG.info("found ping postDisposition:" + pingDisposition);
            assert pingDisposition.isAccepted(); // we wouldn't get here if the ping wasn't accepted
            leadMatchId = pingDisposition.getLeadMatchId();
        }

        try {
            if (publisherContext.isRePost()) {
                leadMatchId = ((PublisherContext.RePostContext) publisherContext).getLeadMatchId();
                // todo: update the lead match to re-post?
            }
            else if (pingDisposition == null) {
                // write the match to the db
                // Note: this is happening if we post cold, i.e. there was no ping, and we just made the decision to post to this candidate
                leadMatchId = LeadMatchModel.persist(postCtx.getLead(), candidate, LeadMatch.PRE_ROUTE);
                priceDisposition = candidate.getResponseDisposition(PonyPhase.REQUEST_PRICE);
                if (priceDisposition != null && priceDisposition.isAccepted() && priceDisposition.getPrice() != null) {
                    // now that we have a lead match persisted, we can write any price request responses (if there were any)
                    AdvertiserWriter.logPhaseMessage(leadMatchId, PonyPhase.REQUEST_PRICE, priceDisposition.getComment());

                    // log the disposition
                    LeadMatchModel.persistDisposition(candidate.getIo().getAdvertiserId(), leadMatchId, priceDisposition, priceDisposition.getPhase());
                } else {
                	Disposition errorDisposition = candidate.getErrorResponseDisposition(PonyPhase.REQUEST_PRICE);
                	AdvertiserWriter.logPhaseMessage(leadMatchId, PonyPhase.REQUEST_PRICE, errorDisposition.getComment());
                	
                	LeadMatchModel.persistDisposition(candidate.getIo().getAdvertiserId(), leadMatchId, errorDisposition, errorDisposition.getPhase());
                }
            }

            // load the writer class for the advertiser_id, and call post on it
            Advertiser advertiser = AdvertiserModel.find(advertiserId);
            String advertiserName = advertiser.getName();
            AdvertiserWriter writer = getWriter(advertiserName);

            postDisposition = writer.post(leadMatchId, publisherContext, validationResponse, candidate);
        }
        catch (Throwable e) {
            LOG.error("Unknown exception posting to candidate." , e);
            postDisposition = Disposition.createPost(Disposition.Status.REJECTED, Disposition.DispositionCategory.ERROR, e.getMessage());
        }

        if (postDisposition == null) {
            postDisposition = Disposition.createPost(Disposition.Status.REJECTED, Disposition.DispositionCategory.ERROR, "no suitable writer found");
        }

        // update state of things
        // is this a post in reference to a ping and poll (targeted at a selected buyer)?
        if (pingDisposition != null && candidate.getBuyer() != null) {
            // in that case we need to update that level
            LeadMatchModel.updateBuyerDisposition(candidate.getBuyer(), postDisposition);
        }
        LeadMatchModel.persistDisposition(advertiserId, leadMatchId, postDisposition, PonyPhase.POST);
        LeadModel.updateStatus(postCtx.getLead().getId(), postDisposition, PonyPhase.POST);

        postCtx.addDisposition(candidate, postDisposition);

        return postDisposition;
    }

    // TODO: add a PingResponse class to encapsulate the returns (so we can pass on any pingContext values there)
    public Map<RoutingCandidate, Disposition> ping(IPublisherContext publisherContext, ValidationResponse vResponse) throws PonyException {

        // what do we have to sell?
        LeadType leadType = publisherContext.getLeadType();
        Arrival arrival = publisherContext.getArrival();
        Lead lead = publisherContext.getLead();
        int maxLeadUnits = publisherContext.getPublisherList().getMaxLeadUnits();

        // where can we send it
        PingContext pingContext = PingContext.create(arrival, leadType, lead, maxLeadUnits, getRoutingCandidates(publisherContext, PonyPhase.PING));

        // give the rules a chance before we start
        RuleResponse ruleResponse = ruleService.beforePing(pingContext, vResponse);

        if (ruleResponse.stop()) {
            return pingContext.getDispositions();
        }

        try {
            waitForPings(pingContext);

            // the rules need another chance to change the results before we give up control
            ruleResponse = ruleService.afterPing(pingContext);
            // TODO: anything with the ruleResponse?

            return pingContext.getDispositions();
        }
        catch (NamingException e) {
            throw new PonyException(e);
        }
        catch (SQLException e) {
            throw new PonyException(e);
        }
    }

    public PollResponse poll(IPublisherContext publisherContext) throws NamingException, SQLException, ValidationException {
        // allow the rules to sort and filter / limit them
        AdvertiserPollContext advertiserPollContext = AdvertiserPollContext.create(publisherContext);
        ruleService.poll(advertiserPollContext);
        return PollResponse.create(advertiserPollContext);
    }

    private Map<RoutingCandidate, Disposition> waitForPings(PingContext pingContext) throws NamingException, SQLException {
        LOG.info("*** wait for pings\r\n");
        //TODO: make async
        while (pingContext.hasMoreCandidates()) {
            RoutingCandidate candidate = pingContext.getNextCandidate();
            pingCandidate(pingContext, candidate);
        }

        return pingContext.getDispositions();
    }

    private Disposition pingCandidate(PingContext pingContext, RoutingCandidate candidate) throws NamingException, SQLException {

        //  actually ping the partner
        Disposition disposition = null;
        Long leadMatchId = null;

        try {
            // write the match to the db
            leadMatchId = LeadMatchModel.persist(pingContext.getLead(), candidate, LeadMatch.PRE_PING);
            // load the writer class for the advertiser_id, and call post on it
            Advertiser advertiser = AdvertiserModel.find(candidate.getIo().getAdvertiserId());
            AdvertiserWriter writer = getWriter(advertiser.getName());
            if (writer.supportsPhase(PonyPhase.PING)) {
            	disposition = writer.ping(leadMatchId, pingContext, candidate);
            }
            else {
            	disposition = Disposition.create(PonyPhase.PING, Disposition.Status.UNSUPPORTED);
            }
        }
        catch (Throwable e) {
            LOG.error(e);
            disposition = Disposition.createPing(Disposition.Status.REJECTED, Disposition.DispositionCategory.ERROR, e.getMessage());
        }

        if (disposition == null) {
            disposition = Disposition.createPing(Disposition.Status.REJECTED, Disposition.DispositionCategory.ERROR, "no suitable writer found");
        }

        LeadMatchModel.persistDisposition(candidate.getIo().getAdvertiserId(), leadMatchId, disposition, PonyPhase.PING);
        LeadModel.updateStatus(pingContext.getLead().getId(), disposition, PonyPhase.PING);

        pingContext.addDisposition(candidate, disposition);

        return disposition;
    }

    public Long notifyPing(IPublisherContext publisherContext, ValidationResponse vResponse) throws PonyException {
        LOG.info("notifyPing for " + publisherContext);
        // put in queue
        if (!executor.isShutdown()) {
            executor.submit(new PingWorker(publisherContext, vResponse));

            return publisherContext.getLead().getId();
        }

        throw new PonyException("Worker pool is shut down");
    }
    
	private AdvertiserWriter getWriter(String advertiserName) {
		AdvertiserWriter writer = advertiserWriters.get(advertiserName);
		writer.setProperties(advertiserName, this.writerProperties);
		return writer;
	}

    class PingWorker implements Runnable {
        private final IPublisherContext publisherContext;
        private final ValidationResponse validationResponse;

        private PingWorker(IPublisherContext publisherContext, ValidationResponse validationResponse) {
            this.publisherContext = publisherContext;
            this.validationResponse = validationResponse;
        }

        public void run() {
            LOG.info("starting worker now for " + publisherContext);
            //TODO: execute the ping
            try {
                Map<RoutingCandidate, Disposition> dispositions = ping(publisherContext, validationResponse);

                //TODO? update the lead state ?
            }
            catch (PonyException e) {
                LOG.error(e);
            }
        }
    }

    class PostWorker implements Runnable {
        private final IPublisherContext publisherContext;
        private final ValidationResponse validationResponse;
        private EmailSchedulingTask emailScheduler = new EmailSchedulingTask(); 
        
        private PostWorker(IPublisherContext publisherContext, ValidationResponse validationResponse) {
            this.publisherContext = publisherContext;
            this.validationResponse = validationResponse;
        }

        public void run() {
            LOG.info("starting worker now for " + publisherContext);
            //TODO: execute the ping
            try {
                Map<RoutingCandidate, Disposition> dispositions = post(publisherContext, validationResponse);
                emailScheduler.scheduleEmailsForDispositions(publisherContext, dispositions.values());
                //TODO? update the lead state ?
                
                LOG.info("Starting publishing the dispositions.");
                if(dispositionEventPublisher.publishDispositions(publisherContext.getLeadId(), dispositions.values())) {
                	LOG.info("Successfully published event");
                } else {
                	LOG.error("Unknown failure publishing event");
                }
            }
            catch (PonyException e) {
                LOG.error("Pony Exception during execution of the PostWorker.", e);
            }
            catch (RuntimeException e) {
                LOG.error("Unknown RuntimeException during execution of the PostWorker.", e);
            }
        }
    }
}
