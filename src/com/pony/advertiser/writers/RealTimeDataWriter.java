package com.pony.advertiser.writers;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 4/15/14
 * Time: 1:32 PM
 */
public class RealTimeDataWriter extends CouponHoundPingWriter {
	private static final Log LOG = LogFactory.getLog(RealTimeDataWriter.class);

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {

        //post URL: http://www.realtimedata.biz/Post.aspx
        /*
        // Note: this code assumes the following params in the sourceId:
        Source ID = 1696
        Source PWD = 06CA767908DC
         */
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
            Publisher publisher = publisherContext.getPublisher();

            List<NameValuePair> formParams = new ArrayList<NameValuePair>();

            // write out the tokenized sourceId
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

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


            String phone = lead.getAttributeValue("mobile_phone");
            if (phone == null) {
                phone = lead.getAttributeValue("phone_number");
            }
            if (phone != null) {
                formParams.add(new BasicNameValuePair("phone", phone));
            }

            formParams.add(new BasicNameValuePair("email", up.getEmail()));


            formParams.add(new BasicNameValuePair("address1", lead.getAttributeValue("address")));
            formParams.add(new BasicNameValuePair("city", lead.getAttributeValue("city")));
            formParams.add(new BasicNameValuePair("state", lead.getAttributeValue("state")));

            String zip = lead.getAttributeValue("zip");
            if (zip == null) {
                zip = lead.getAttributeValue("zip_code");
            }
            formParams.add(new BasicNameValuePair("postal_code", zip));

            if (lead.getAttributeValue("debt_amount") != null) {
                formParams.add(new BasicNameValuePair("debt_amount", lead.getAttributeValue("debt_amount")));
            }

            String ip = lead.getAttributeValue("ip");
            if (ip == null) {
                ip = arrival.getIpAddress();
            }
            formParams.add(new BasicNameValuePair("ip_address", ip));

            formParams.add(new BasicNameValuePair("source_url", publisher.getName()));

            ///###########

            Long msgId = logPostMessage(leadMatchId, url, formParams);

            String[] response = httpGetWithStringResponse(url, formParams);
            if (response == null || response.length == 0) {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to parse post response");
            }

            if (msgId != null) {
                // record the raw response
                StringBuilder r = new StringBuilder();
                for (String l : response) {
                    r.append(l).append("\r\n");
                }
                logResponseMessage(msgId, r.toString());
            }

            // parse the response
            // LEADSTATUS=FALSE    REASON:First name is blank
            String msg = response[0];
            String externalLeadId = null;

            // 0,Success
            if (msg.startsWith("0") && msg.toUpperCase().contains("SUCCESS")) {
                return Disposition.createPost(Disposition.Status.ACCEPTED, null, externalLeadId, msg);
            }
            else {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, msg);
            }
        }
        catch (IOException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
    }
}
