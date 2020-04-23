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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 2/4/14
 * Time: 5:36 PM
 */
public class PrizmMediaWriter extends CouponHoundPingWriter {
	private static final Log LOG = LogFactory.getLog(PrizmMediaWriter.class);
	
    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {

        // URL : http://www.homecareerleads.com/xfer/index.php

        /*
            all required: firstname, lastname, address, dob, city, state, zip phone, email, carrier, tstamp, pub, leadtype, ip
         */

        // LEadtype: NONMEDICARE
        // Pub ID: asc_pi_pub
        Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());

        if (map.get(NO_SEND) != null) {
            return Disposition.createPost(Disposition.Status.ACCEPTED, Disposition.DispositionCategory.NO_MATCH, "configured to not send.");
        }

        try {
            String url = candidate.getIo().getTargetUrl();

            Lead lead = publisherContext.getLead();

            Arrival arrival = publisherContext.getArrival();
            UserProfile up = publisherContext.getUserProfile();
//            String code = candidate.getIo().getCode();

            List<NameValuePair> formParams = new ArrayList<NameValuePair>();

            // write out the tokenized sourceId
            // Note: we assume these contain keys and values for leadtype and pub
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            // firstname, lastname, address, dob, city, state, zip phone, email, carrier, tstamp, ip
            formParams.add(new BasicNameValuePair("email", up.getEmail()));

            String firstName = lead.getAttributeValue("fname");
            if (firstName == null) {
                firstName = lead.getAttributeValue("first_name");
            }
            formParams.add(new BasicNameValuePair("firstname", firstName));

            String lastName = lead.getAttributeValue("lname");
            if (lastName == null) {
                lastName = lead.getAttributeValue("last_name");
            }
            formParams.add(new BasicNameValuePair("lastname", lastName));


            // if there is an IP attribute being submitted, we use that, otherwise we'll try to get the IP from the posting party
            String ip = lead.getAttributeValue("ip");
            if (ip == null) {
                ip = arrival.getIpAddress();
            }
            formParams.add(new BasicNameValuePair("ip", ip));


            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            formParams.add(new BasicNameValuePair("tstamp", df.format(Calendar.getInstance().getTime())));

            formParams.add(new BasicNameValuePair("address", lead.getAttributeValue("address")));
            formParams.add(new BasicNameValuePair("dob", lead.getAttributeValue("dob")));
            formParams.add(new BasicNameValuePair("city", lead.getAttributeValue("city")));
            formParams.add(new BasicNameValuePair("state", lead.getAttributeValue("state")));

            String zip = lead.getAttributeValue("zip");
            if (zip == null) {
                zip = lead.getAttributeValue("zip_code");
            }
            formParams.add(new BasicNameValuePair("zip", zip));

            String phone = lead.getAttributeValue("mobile_phone");
            if (phone == null) {
                phone = lead.getAttributeValue("phone_number");
            }
            if (phone != null) {
                formParams.add(new BasicNameValuePair("phone", phone));
            }

            formParams.add(new BasicNameValuePair("carrier", "private"));


            Long msgId = logPostMessage(leadMatchId, url, formParams);

            String[] response = httpGetWithStringResponse(url, formParams);
            if (response == null || response.length == 0) {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to parse post response");
            }

            if (msgId != null) {
                // record the raw response
                logResponseMessage(msgId, response);
            }

            // parse the response
            String msg = response[0];
            String externalLeadId = null;
            if (msg != null && msg.contains("LeadID")) {
                //SUCCESS : LeadID : 3888041, publisher : asc_pi_pub SUCCESS
                try {
                    externalLeadId = msg.substring(msg.indexOf(":", msg.indexOf("LeadID")) + 1, msg.indexOf(",")).trim();
                }
                catch (RuntimeException e) {
                    // ignore
                }
            }

            if (msg.toLowerCase().contains("success")) {
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

    public static void main(String[] args) {
        String msg = "SUCCESS : LeadID : 3888041, publisher : asc_pi_pub SUCCESS";
        String externalLeadId;
        if (msg.contains("LeadID")) {
            //SUCCESS : LeadID : 3888041, publisher : asc_pi_pub SUCCESS
            externalLeadId = msg.substring(msg.indexOf(":", msg.indexOf("LeadID")) + 1, msg.indexOf(",")).trim();
            LOG.debug(externalLeadId);
        }
    }
}
