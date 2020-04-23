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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 2/11/14
 * Time: 9:28 AM
 */
public class TmgInteractiveWriter extends CouponHoundPingWriter {
	private static final Log LOG = LogFactory.getLog(TmgInteractiveWriter.class);

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {

        // http://process.tmginteractive.com/10391/Camp10391.aspx?First_Name=&Last_Name=&Email=&Address=&Address_2=&Zip=&State=&Country=&IP_ADDRESS=&PixelRes=&USER_PLATFORM=&publisherId=161710&cCampId=10391
        // Date Format (DOB etc..) :- mm-dd-yyyy

        // You can pass ID 161710, which is your pub ID
        /*
        publisherId  161710
        cCampId = 10485 (holiday); 10391 (harpers)
        */

        // Note: this code assumes the following params in the sourceId: listid.1= listid.2= source=
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

            // write out the tokenized sourceId
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }


            formParams.add(new BasicNameValuePair("Email", up.getEmail()));

            String firstName = lead.getAttributeValue("fname");
            if (firstName == null) {
                firstName = lead.getAttributeValue("first_name");
            }
            formParams.add(new BasicNameValuePair("First_Name", firstName));

            String lastName = lead.getAttributeValue("lname");
            if (lastName == null) {
                lastName = lead.getAttributeValue("last_name");
            }
            formParams.add(new BasicNameValuePair("Last_Name", lastName));

            String phone = lead.getAttributeValue("mobile_phone");
            if (phone == null) {
                phone = lead.getAttributeValue("phone_number");
            }
            if (phone != null) {
                formParams.add(new BasicNameValuePair("Phone", phone));
            }

            if (lead.getAttributeValue("dob") != null) {
                formParams.add(new BasicNameValuePair("Date_Of_Birth", lead.getAttributeValue("dob").replace("/", "-"))); // Date_Of_Birth=MM/dd/yyyy
            }

            formParams.add(new BasicNameValuePair("Address", lead.getAttributeValue("address")));
            formParams.add(new BasicNameValuePair("Address_2", ""));
            formParams.add(new BasicNameValuePair("City", lead.getAttributeValue("city")));
            formParams.add(new BasicNameValuePair("State", lead.getAttributeValue("state")));

            String zip = lead.getAttributeValue("zip");
            if (zip == null) {
                zip = lead.getAttributeValue("zip_code");
            }
            formParams.add(new BasicNameValuePair("Zip", zip));

            formParams.add(new BasicNameValuePair("Country", ""));
            formParams.add(new BasicNameValuePair("Base_Add_On_1", ""));
            formParams.add(new BasicNameValuePair("PixelRes", ""));
            formParams.add(new BasicNameValuePair("USER_PLATFORM", ""));

            // if there is an IP attribute being submitted, we use that, otherwise we'll try to get the IP from the posting party
            String ip = lead.getAttributeValue("ip");
            if (ip == null) {
                ip = arrival.getIpAddress();
            }
            formParams.add(new BasicNameValuePair("IP_ADDRESS", ip));


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

            if (msg.toUpperCase().contains("LEADSTATUS=TRUE")) {
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
