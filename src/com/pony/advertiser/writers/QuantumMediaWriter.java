package com.pony.advertiser.writers;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 <div id='required sql'>
    insert into advertisers values(null, 'QuantumMedia', now(), now());
    insert into advertiser_writers values(null, -1, (select id from advertisers where name='QuantumMedia'), 'com.pony.advertiser.writers.QuantumMediaWriter', now(), now());
    insert into publisher_lists values(null, -1, 'QuantumMedia', 'QuantumMedia', 1, 1, 1, now(), null);
    insert into publisher_list_members values(null, (select id from publisher_lists where name = 'QuantumMedia'), (select id from publishers where name = 'coupon-hound.com'), 0, 1, now(), null);
    insert into orders values(null, 'QuantumMedia', (select id from advertisers where name = 'QuantumMedia'), -1, 0, 'provider=TEST;tag=TEST', 0, 1, 0, 0, 0, null, 'http://post.quantum3media.com/submit/form.php', now(), null);
    insert into publisher_list_orders values(null, 'CH-ping-link', (select id from publisher_lists where name = 'CH-ping'), (select id from orders where code = 'QuantumMedia'), 1, now(), null);
    insert into publisher_list_orders values(null, 'QuantumMedia', (select id from publisher_lists where name = 'QuantumMedia'), (select id from orders where code = 'QuantumMedia'), 1, now(), null);
 </div>
 * ArbVentures 2013.
 * User: martin
 * Date: 5/15/14
 * Time: 6:05 PM
 */
public class QuantumMediaWriter extends CouponHoundPingWriter {

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {

        //  http://post.quantum3media.com/submit/form.php
        //  Required parameters are: provider, first_name, number, opt_in_TCPA and tag

        //To test, set the provider parameter to the value of TEST (as in example above). 
        // If everything passes, you will get a "The request was successfully processed" as your reply.

        Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());

        if (map.get(NO_SEND) != null) {
            return Disposition.createPost(Disposition.Status.ACCEPTED, Disposition.DispositionCategory.NO_MATCH, "configured to not send.");
        }

        try {
            String url = candidate.getIo().getTargetUrl();

            Lead lead = publisherContext.getLead();

            Arrival arrival = publisherContext.getArrival();
            UserProfile up = publisherContext.getUserProfile();
            String code = candidate.getIo().getCode();

            List<NameValuePair> formParams = new ArrayList<NameValuePair>();

            // source_id expected to contain 'provider=xxxx;tag=yyyy'
            // write out the tokenized sourceId
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // number
            String phone = lead.getAttributeValue("cellPhone");
            if (phone == null || "".equals(phone)) {
                // mobile wins over not mobile
                phone = lead.getAttributeValue("landPhone");
            }
            if (phone == null || "".equals(phone)) {
                phone = lead.getAttributeValue("mobile_phone");
            }
            if (phone == null || "".equals(phone)) {
                phone = lead.getAttributeValue("phone");
            }
            if (phone == null) {
                phone = lead.getAttributeValue("phone_number");
            }
            formParams.add(new BasicNameValuePair("number", phone));

            String firstName = lead.getAttributeValue("fname");
            if (firstName == null) {
                firstName = lead.getAttributeValue("first_name");
            }
            formParams.add(new BasicNameValuePair("first_name", firstName));

            String lastName = lead.getAttributeValue("lname");
            if (lastName == null) {
                lastName = lead.getAttributeValue("last_name");
            }
            formParams.add(new BasicNameValuePair("last_name", lastName));

            String zip = lead.getAttributeValue("zip");
            if (zip == null) {
                zip = lead.getAttributeValue("zip_code");
            }
            formParams.add(new BasicNameValuePair("zip", zip));
            formParams.add(new BasicNameValuePair("street", lead.getAttributeValue("address")));
            formParams.add(new BasicNameValuePair("city", lead.getAttributeValue("city")));
            formParams.add(new BasicNameValuePair("state", lead.getAttributeValue("state")));

            formParams.add(new BasicNameValuePair("email", up.getEmail()));

            // if there is an IP attribute being submitted, we use that, otherwise we'll try to get the IP from the posting party
            String ip = lead.getAttributeValue("ip");
            if (ip == null) {
                ip = arrival.getIpAddress();
            }
            formParams.add(new BasicNameValuePair("ip", ip));

            // Enter your affiliate/SubID code here to track
            if (code != null) {
                formParams.add(new BasicNameValuePair("sub_id", code));
            }

            formParams.add(new BasicNameValuePair("url", publisherContext.getPublisher().getName()));
            formParams.add(new BasicNameValuePair("opt_in_TCPA", "Yes"));

            Long msgId = logPostMessage(leadMatchId, url, formParams);

            String[] lines = httpGetWithStringResponse(url, formParams);
            if (lines == null || lines.length == 0) {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to parse post response");
            }

            if (msgId != null) {
                // record the raw response
                logResponseMessage(msgId, lines);
            }

            // parse the response
            for (String line : lines) {
                if (line.contains("successfully processed")) {
                    String externalLeadId = null;
                    return Disposition.createPost(Disposition.Status.ACCEPTED, null, externalLeadId, line);
                }
                else if (line.length() > 0) {
                    return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, line);
                }
            }

            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to interpret response");
        }
        catch (IOException e) {
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
    }
}
