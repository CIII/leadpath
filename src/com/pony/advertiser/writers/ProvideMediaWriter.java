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
 * ArbVentures 2013.
 * User: martin
 * Date: 4/11/14
 * Time: 11:40 AM
 */
public class ProvideMediaWriter extends CouponHoundPingWriter {

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {

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
            formParams.add(new BasicNameValuePair("psid", code));

            // if there is an IP attribute being submitted, we use that, otherwise we'll try to get the IP from the posting party
            String ip = lead.getAttributeValue("ip");
            if (ip == null) {
                ip = arrival.getIpAddress();
            }
            formParams.add(new BasicNameValuePair("ip", ip));

            formParams.add(new BasicNameValuePair("email", up.getEmail()));

            String firstName = lead.getAttributeValue("fname");
            if (firstName == null) {
                firstName = lead.getAttributeValue("first_name");
            }
            formParams.add(new BasicNameValuePair("fname", firstName));

            String lastName = lead.getAttributeValue("lname");
            if (lastName == null) {
                lastName = lead.getAttributeValue("last_name");
            }
            formParams.add(new BasicNameValuePair("lname", lastName));

            // mobile wins over not mobile
            String phone = lead.getAttributeValue("mobile_phone");
            if (phone == null) {
                phone = lead.getAttributeValue("phone_number");
            }
            if (phone != null) {
                formParams.add(new BasicNameValuePair("phone1", phone));
            }

            formParams.add(new BasicNameValuePair("address", lead.getAttributeValue("address")));

            formParams.add(new BasicNameValuePair("city", lead.getAttributeValue("city")));

            formParams.add(new BasicNameValuePair("state", lead.getAttributeValue("state")));

            String zip = lead.getAttributeValue("zip");
            if (zip == null) {
                zip = lead.getAttributeValue("zip_code");
            }
            formParams.add(new BasicNameValuePair("zip", zip));

            String gender = lead.getAttributeValue("gender");
            if (gender == null) {
                gender = "F";
            }
            formParams.add(new BasicNameValuePair("gender", gender));

            if (lead.getAttributeValue("dob") != null) {
                formParams.add(new BasicNameValuePair("dob", lead.getAttributeValue("dob")));

                // CH formats dates like this: MM/dd/yyyy, but Provide wants it like YYYY-MM-DD

                String dob = lead.getAttributeValue("dob");
                if (dob.length() >= 10) {
                    formParams.add(new BasicNameValuePair("dob", dob.substring(6, 4) + "-" + dob.substring(3, 2) + "-" + dob.substring(0, 2)));
                }
            }

            formParams.add(new BasicNameValuePair("cv2", "xxxxxx"));

            Long msgId = logPostMessage(leadMatchId, url, formParams);

            String[] lines = httpPostWithStringResponse(url, formParams);
            if (lines == null || lines.length == 0) {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to parse post response");
            }

            if (msgId != null) {
                // record the raw response
                logResponseMessage(msgId, lines);
            }

            /*
                success:
                __success__
                __lead_received__
                __city_state_zip_validated__
                __phone_validated__ __phone1__

                fail:
                __failed__ __duplicate_email_oid356__ __duplicate_email_oid356__
             */
            boolean foundSuccess = false;
            String msg = null;
            for (String line : lines) {
                if ("__success__".equals(line.trim())) {
                    foundSuccess = true;
                }
                else if (line.contains("__failed__")) {
                    msg = line;
                }
            }

            if (foundSuccess) {
                return Disposition.createPost(Disposition.Status.ACCEPTED);
            }
            else {
                return Disposition.createPost(Disposition.Status.REJECTED, null, msg);
            }
        }
        catch (IOException e) {
            return Disposition.createPost(Disposition.Status.REJECTED, null, e.getMessage());
        }
    }
}
