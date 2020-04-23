package com.pony.email;

import com.pony.PonyException;
import com.pony.advertiser.AdvertiserService;
import com.pony.advertiser.OfferSplit;
import com.pony.core.PonyService;
import com.pony.models.ArrivalModelImpl;
import com.pony.models.HostModel;
import com.pony.models.ListSplitModel;
import com.pony.models.MessageModel;
import com.pony.models.OfferSplitModel;
import com.pony.models.ResendMessageLogModel;
import com.pony.models.ResendMessagePhaseModel;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.publisher.PublisherList;
import com.pony.publisher.PublisherResponse;
import com.pony.publisher.PublisherService;
import com.pony.rules.RuleService;
import com.pony.validation.ValidationResponse;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 6/6/12
 * Time: 9:56 PM
 */
public class EmailService extends PonyService {
	private static final Log LOG = LogFactory.getLog(EmailService.class);

    // map for send counts per day and host
    private static final Map<Long, Host.SendCount> HOST_COUNTS = Collections.synchronizedMap(new HashMap<Long, Host.SendCount>());
    private static Date CACHE_TIMESTAMP;

    //TMP: map ext_list_id to offer id for first resend
    @Override
    protected void addRuleService(RuleService service) {

    }

    @Override
    protected void addAdvertiserService(AdvertiserService service) {

    }

    @Override
    protected void addPublisherService(PublisherService service) {

    }

    @Override
    public void start() {
        // init the cache time stamp for two ours ago to enforce a cache refresh on first hit
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -2);

        CACHE_TIMESTAMP = cal.getTime();
    }

    @Override
    public void stop() {
        HOST_COUNTS.clear();
    }

    /**
     * Check if the email address was previously sent by this publisher, has bounced, unsubscribed, or complained.
     * Determine what offer to send, and what creative and sending host to use.
     * Create the message and determine the SmtpProvider to handle the message transport.
     * Delegate the send to the SmtpProvider.
     *
     * @param context
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public PublisherResponse send(IPublisherContext context) throws SmtpException {
        // TODO (clarify): for now : we reject any dup (even if it's from a different publisher)
        if (context.isEmailDup()) {
            return PublisherResponse.createDupResponse(context);
        }

        // refresh the cache every hour:

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -1);

        // if it's older than an hour
        if (CACHE_TIMESTAMP.before(cal.getTime()) || context.refreshCache()) {
            LOG.info("EmailService: host send count refresh. cache size=" + HOST_COUNTS.size());
            // refresh from db
            try {
                HOST_COUNTS.clear();
                CACHE_TIMESTAMP = Calendar.getInstance().getTime();
                HOST_COUNTS.putAll(HostModel.getHostCounts(CACHE_TIMESTAMP));
            }
            catch (NamingException e) {
                //TODO: no idea what to do now ...
                LOG.error(e);
            }
            catch (SQLException e) {
                //TODO: no idea what to do now ...
                LOG.error(e);
            }
        }

        // ?? TODO: what about user_lists? do we need to check anything here?

        try {
            // determine target host and creative, create a message and send it to the host
            PublisherList publisherList = context.getPublisherList();

            Long listSplitId = ListSplitModel.determineSplitId(context, publisherList);
            if (listSplitId == null && !context.isTest()) {
                return PublisherResponse.createErrorResponse(context, PublisherResponse.NO_OFFER_AVAILABLE);
            }

            Long creativeId = null, hostId = null;
            Host host = null;
            Host.SendCount count = null;

            for (OfferSplit offerSplit : OfferSplitModel.determineSplits(context, listSplitId)) {
                creativeId = offerSplit.getCreativeId();
                hostId = offerSplit.getHostId();

                // per-day-and-host cache with counts to check against max daily
                host = HostModel.find(hostId);
                // if the host is limited (and this is not a test post)
                if (host.getMaxSendsDaily() > 0L && !context.isTest()) {
                    count = HOST_COUNTS.get(host.getId());
                    if (count == null) {
                        count = Host.createSendCount(host);
                        HOST_COUNTS.put(host.getId(), count);
                    }

                    if (count.getCount() >= host.getMaxSendsDaily()) {
                        ArrivalModelImpl.touchStatic(context.getArrival(), ValidationResponse.SEND_LIMIT_EXCEEDED);
                        creativeId = null;
                        hostId = null;

                        // see if there is another split we can use (one that has not yet reached its limit)
                        continue;
                    }
                    else {
                        // we found a host for this offer that has still capacity
                        break;
                    }
                }
                else {
                    break;
                }
            }

            if (creativeId == null && hostId == null) {
                return PublisherResponse.createErrorResponse(context, PublisherResponse.NO_OFFER_SPLIT);
            }

            // if the selected host is limited
            if (count != null && host != null) {
                if (count.getCount() < host.getMaxSendsDaily()) {
                    // increment host send counters
                    count.increment();
                    HOST_COUNTS.put(host.getId(), count);

                    // and make sure we clean out any validation status we might have set earlier (limit exceeded?)
                    ArrivalModelImpl.touchStatic(context.getArrival(), ValidationResponse.NOOP);
                }
                else {
                    return PublisherResponse.createLimitExceededResponse(host, count.getCount());
                }
            }

            // persist the message and pass the created id along, so others can refer to it
            LOG.info("creating message for: up=" + context.getUserProfile().getId() + ":host=" + hostId + ":creative=" + creativeId);
            Message message = MessageModel.create(context.getUserProfile().getId(), hostId, creativeId);
            context.setMessageId(message.getId());

            // get smtp_provider and delegate the actual send (transport) to it
            LOG.info("looking up provider for hostId=" + hostId);
            SmtpProvider smtpProvider = HostModel.getSmtpProvider(hostId);
            if (smtpProvider != null) {
                LOG.info("sending for hostId=" + hostId + "; provider=" + smtpProvider);
                SmtpResponse smtpResponse = smtpProvider.send(context, message);
                return PublisherResponse.createEmailResponse(context, smtpResponse);
            }
            else {
                return PublisherResponse.createErrorResponse(context, PublisherResponse.NO_SMTP_PROVIDER);
            }
        }
        catch (SQLException e) {
            throw new SmtpException(e);
        }
        catch (NamingException e) {
            throw new SmtpException(e);
        }
    }

    public void resend(Long hostId, ResendSequence sequence, List<ResendCandidate> candidates, long delayMinutes) throws PonyException {
        if (candidates.size() == 0) {
            LOG.info(" ==> nothing to do for host_id=" + hostId);
            return;
        }
        try {
            SmtpProvider smtpProvider = HostModel.getSmtpProvider(hostId);
            if (smtpProvider == null) {
                LOG.error("ERROR: no smtp provider configured for hostId=" + hostId);
                return;
            }

            Host host = HostModel.find(hostId);

            // this is where the message gets created and the resend_message_phase gets written

            Map<ResendCandidate, Message> messageCandidates = new HashMap<ResendCandidate, Message>();

            for (ResendCandidate candidate : candidates) {
                Long offerId = sequence.determineOfferId(candidate);
                if (offerId == null) {
                    // all others get retired now
                    // delete from resend_message_phases and skip here
                    ResendMessagePhaseModel.remove(sequence.getId(), candidate.getFirstMessageId());
                    continue;
                }

                // create the actual message

                // look for a creative and host to send from
                OfferSplit offerSplit = OfferSplitModel.determineSplit(offerId, hostId);
                if (offerSplit == null) {
                    // if we don't have another creative configured to send for this new offer from the already familiar (to the user) host,
                    // try to see if we have a creative for this offer through another host
                    offerSplit = OfferSplitModel.determineSplit(offerId);
                }

                if (offerSplit == null) {
                    LOG.warn("WARNING: no offer split found for resend of message_id=" + candidate.getFirstMessageId());
                    continue;
                }

                LOG.info("creating message for: up=" + candidate.getUserProfileId() + ":host=" + offerSplit.getHostId() + ":creative=" + offerSplit.getCreativeId());
                Message message = MessageModel.create(candidate.getUserProfileId(), offerSplit.getHostId(), offerSplit.getCreativeId());

                // create or update the resend_message_phase
                if (ResendMessagePhase.START.equals(candidate.getPhase())) {
                    ResendMessagePhaseModel.create(sequence, candidate, message);
                }
                else {
                    ResendMessagePhaseModel.update(sequence, candidate, message);
                }

                ResendMessageLogModel.create(sequence, candidate, message, delayMinutes);

                messageCandidates.put(candidate, message);
            }

            smtpProvider.send(host, messageCandidates, delayMinutes);
        }
        catch (SmtpException e) {
            throw new PonyException(e);
        }
        catch (SQLException e) {
            throw new PonyException(e);
        }
        catch (NamingException e) {
            throw new PonyException(e);
        }
    }

    public String toString() {
        return "EmailService {CASH_TIMESTAMP=" + CACHE_TIMESTAMP + "}";
    }
}
