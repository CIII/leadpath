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
import java.util.TimeZone;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 4/16/14
 * Time: 12:30 PM
 */
public class MocanMediaWriter extends CouponHoundPingWriter {
	private static final Log LOG = LogFactory.getLog(MocanMediaWriter.class);

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {

        /*
        username: couponhound password: XAx482t!22 campaign id: 418
        Post URL: http://post.consent2call.com/postdata.aspx
         */

        // an, ap, cid come via the source_id

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

            formParams.add(new BasicNameValuePair("em", up.getEmail()));

            String firstName = lead.getAttributeValue("fname");
            if (firstName == null) {
                firstName = lead.getAttributeValue("first_name");
            }
            formParams.add(new BasicNameValuePair("fn", firstName));

            String lastName = lead.getAttributeValue("lname");
            if (lastName == null) {
                lastName = lead.getAttributeValue("last_name");
            }
            formParams.add(new BasicNameValuePair("ln", lastName));

            String phone = lead.getAttributeValue("mobile_phone");
            if (phone == null) {
                phone = lead.getAttributeValue("phone_number");
            }
            if (phone != null) {
                formParams.add(new BasicNameValuePair("ph", phone));
            }

            formParams.add(new BasicNameValuePair("ad", lead.getAttributeValue("address")));
            formParams.add(new BasicNameValuePair("cty", lead.getAttributeValue("city")));
            formParams.add(new BasicNameValuePair("st", lead.getAttributeValue("state")));

            String zip = lead.getAttributeValue("zip");
            if (zip == null) {
                zip = lead.getAttributeValue("zip_code");
            }
            formParams.add(new BasicNameValuePair("zp", zip));

            formParams.add(new BasicNameValuePair("sc", publisherContext.getPublisher().getName()));

            //stm yyyy-MM-dd HH:mm:ss
            Calendar cal = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
            formParams.add(new BasicNameValuePair("stm", df.format(cal.getTime())));

            String ip = lead.getAttributeValue("ip");
            if (ip == null) {
                ip = arrival.getIpAddress();
            }
            formParams.add(new BasicNameValuePair("ip", ip));

            String gender = lead.getAttributeValue("gender");
            if (gender == null) {
                gender = "F";
            }
            formParams.add(new BasicNameValuePair("gn", gender));


            Long msgId = logPostMessage(leadMatchId, url, formParams);

            String[] response = httpGetWithStringResponse(url, formParams);
            if (response == null || response.length == 0) {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to parse post response");
            }

            StringBuilder r = new StringBuilder();
            boolean success = false;

            if (msgId != null) {
                // record the raw response
                for (String l : response) {
                    r.append(l).append("\r\n");
                    if ("success".equalsIgnoreCase(l.trim())) {
                        success = true;
                    }
                }
                logResponseMessage(msgId, r.toString());
            }

            String externalLeadId = null;

            if (success) {
                return Disposition.createPost(Disposition.Status.ACCEPTED, null, externalLeadId, r.toString());
            }
            else {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, r.toString());
            }
        }
        catch (IOException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
    }
}
