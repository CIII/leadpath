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
 * Time: 2:42 PM
 */
public class ArcaMaxWriter extends CouponHoundPingWriter {
	private static final Log LOG = LogFactory.getLog(ArcaMaxWriter.class);

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {

        // http://www.arcamax.com/cgi-bin/autosub?email=YOUREMAIL&fname=FNAME&lname=LNAME&listid.1=15&listid.2=52&source=3725&ipaddr=IP&ts=DATE/TIME

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

            //email=YOUREMAIL&fname=FNAME&lname=LNAME&listid.1=15&listid.2=52&source=3725&ipaddr=IP&ts=DATE/TIME
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


            // if there is an IP attribute being submitted, we use that, otherwise we'll try to get the IP from the posting party
            String ip = lead.getAttributeValue("ip");
            if (ip == null) {
                ip = arrival.getIpAddress();
            }
            formParams.add(new BasicNameValuePair("ipaddr", ip));


            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            formParams.add(new BasicNameValuePair("ts", df.format(Calendar.getInstance().getTime())));

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
            /*
             */
            String msg = response[0];
            String externalLeadId = null;
            if (response.length > 1) {
                externalLeadId = response[1];
            }

            if ("success".equalsIgnoreCase(msg) || "SUBPEND".equalsIgnoreCase(msg)) {
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
