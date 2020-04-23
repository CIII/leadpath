package com.pony.advertiser.writers;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Buyer;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;

import java.util.Map;

/**
 * Test writer to use for accept and reject messages for pings and posts.
 * Responses can be configured in the source id string of the order.
 * examples:
 * category=duplicate;success=false
 * category=no match;success=false
 * success=true
 * <p/>
 * ArbVentures 2013.
 * User: martin
 * Date: 7/30/13
 * Time: 9:48 AM
 * <p/>
 * SQL to create a new lead type, advertiser, publisher channel and post order
 * <p/>
 * # create a new lead type
 * insert into lead_types values(null, 'New Cars', now() , null);
 * <p/>
 * # create a new publisher and the channel they can post to
 * insert into publishers values(null, 'Wiser Auto', '', '', 1, 'wiserauto.com', md5('wiserOrNot'), 0, now(), null);
 * insert into publisher_lists values(null, (select id from lead_types where name ='New Cars'), 'New Cars', 'new_cars', 4, 1, now(), null);
 * insert into publisher_list_members values(null, (select id from publisher_lists where name = 'New Cars'), (select id from publishers where name = 'Wiser Auto'),0,1,now(),null);
 * <p/>
 * # create the buyer side: we need the advertiser, an order and the writer to transport the data to and from them
 * insert into advertisers values(null, 'Reply.com', now(), null);
 * insert into orders values(null, 'reply-1', (select id from advertisers where name ='Reply.com'), (select id from lead_types where name = 'New Cars'), 0, null, 0, 1, 0,0,0,null, now(),null);
 * insert into advertiser_writers values(null, (select id from lead_types where name = 'New Cars'), (select id from advertisers where name ='Reply.com'), 'com.pony.advertiser.writers.ReplyWriter', now(), null);
 */
public class TestWriter extends AdvertiserWriter {

    @Override
    public boolean supportsPhase(PonyPhase phase) {
        return true;
    }

    @Override
    public Disposition ping(Long leadMatchId, PingContext pingContext, RoutingCandidate candidate) {

        // access source id to get dynamic params from there
        Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());
        // example: category=duplicate;success=false
        // or:      success=true

        Lead lead = pingContext.getLead();
        String ipAddress = pingContext.getArrival().getIpAddress();
        String jobCode = candidate.getIo().getCode();

        String message = "this would be the message to send to the affiliate";

        Long msgId = logPingMessage(leadMatchId, message);

        // ===== actual ping would happen here

        String rawResponse = "This would be the response message from the advertiser";

        // log the raw response
        if (msgId != null) {
            logResponseMessage(msgId, rawResponse);
        }

        // success
        if ("true".equalsIgnoreCase(map.get("success"))) {
            Disposition acceptDealerSelect = Disposition.create(PonyPhase.PING, Disposition.Status.ACCEPTED);

            // if we have individual buyers:
            String dealerId = "dealer#1", dealerName = "dealer One", zipCode = "02421";
            Buyer buyer = Buyer.create(dealerId, dealerName, zipCode);
            buyer.setReservationId("res-#1");
            buyer.setPrice("9.99");
            acceptDealerSelect.addBuyer(buyer);

            // more buyers ....

            return acceptDealerSelect;
        }
        else {
            // error , reject
            Disposition.DispositionCategory category = null;
            if (map.get("category") != null) {
                category = Disposition.DispositionCategory.parse(map.get("category"));
            }

            return Disposition.createPing(Disposition.Status.REJECTED, category, "error message here");
        }
    }

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {
        Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());

        // if this post is in response to a ping, check if there is any data from that ping that we can leverage now
        Disposition pingData = candidate.getDisposition();

        Lead lead = publisherContext.getLead();

        String email = publisherContext.getUserProfile().getEmail();
        String lastName = lead.getAttributeValue("last_name");

        String targetUrl = candidate.getIo().getTargetUrl();

        Long msgId = logPostMessage(leadMatchId, "post message");

        // ===== actual post would be here

        if (msgId != null) {
            logResponseMessage(msgId, "post response message");
        }

        // success
        if ("true".equalsIgnoreCase(map.get("success"))) {
            Disposition.DispositionCategory category = null;
            String externalLeadId = "advertiser-unique-id-for-this-lead";
            return Disposition.createPost(Disposition.Status.ACCEPTED, category, externalLeadId, "what a success");
        }
        else {
            Disposition.DispositionCategory category = null;
            return Disposition.createPost(Disposition.Status.REJECTED, category, "post was not successful");
        }
    }
}
