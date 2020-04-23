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
 * Date: 3/13/14
 * Time: 11:41 AM
 */
public class VikingMagazinWriter extends CouponHoundPingWriter {
	private static final Log LOG = LogFactory.getLog(VikingMagazinWriter.class);

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {
        // http://www.vikingmagazine.com/vici/create_lead_as1.asp?primaryphone=5555555555&fname=something&lname=somethingelse&address1=123 1st street&address2=apt 1&city=Fairview&state=AK&zip5=55555&email=not@real.com&altphone=5555555555&url=www.website.com&date=1/1/1900&ip=192.168.1.1&subid=identifier&xxTrustedFormCertURL=url

        Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());

        if (map.get(NO_SEND) != null) {
            return Disposition.createPost(Disposition.Status.ACCEPTED, Disposition.DispositionCategory.NO_MATCH, "configured to not send.");
        }

        try {
            String url = candidate.getIo().getTargetUrl();

            Lead lead = publisherContext.getLead();

            String xxTrustedFormCertUrl = lead.getAttributeValue("xxTrustedFormCertUrl");
            if (xxTrustedFormCertUrl == null) {
                // can't send without a trusted form cert url (see http://trustedform.com/)
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "No xxTrustedFormCertUrl provided");
            }

            Arrival arrival = publisherContext.getArrival();
            UserProfile up = publisherContext.getUserProfile();
            String code = candidate.getIo().getCode();

            List<NameValuePair> formParams = new ArrayList<NameValuePair>();

            // write out the tokenized sourceId
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            formParams.add(new BasicNameValuePair("xxTrustedFormCertUrl", xxTrustedFormCertUrl));

            // Enter your affiliate/SubID code here to track
            if (code != null) {
                formParams.add(new BasicNameValuePair("subid", code));
            }

            // mobile wins over not mobile
            String phone = lead.getAttributeValue("mobile_phone");
            if (phone == null) {
                phone = lead.getAttributeValue("phone_number");
            }
            if (phone != null) {
                formParams.add(new BasicNameValuePair("primaryphone", phone));
            }

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


            formParams.add(new BasicNameValuePair("address1", lead.getAttributeValue("address")));

            formParams.add(new BasicNameValuePair("city", lead.getAttributeValue("city")));
            formParams.add(new BasicNameValuePair("state", lead.getAttributeValue("state")));

            String zip = lead.getAttributeValue("zip");
            if (zip == null) {
                zip = lead.getAttributeValue("zip_code");
            }
            formParams.add(new BasicNameValuePair("zip5", zip));

            formParams.add(new BasicNameValuePair("email", up.getEmail()));

            formParams.add(new BasicNameValuePair("url", publisherContext.getPublisher().getName()));

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            formParams.add(new BasicNameValuePair("timestamp", df.format(Calendar.getInstance().getTime())));
            formParams.add(new BasicNameValuePair("date", df.format(Calendar.getInstance().getTime())));

            // if there is an IP attribute being submitted, we use that, otherwise we'll try to get the IP from the posting party
            String ip = lead.getAttributeValue("ip");
            if (ip == null) {
                ip = arrival.getIpAddress();
            }
            formParams.add(new BasicNameValuePair("ip", ip));

            Long msgId = logPostMessage(leadMatchId, url, formParams);


            // post starts here
            String[] lines = httpPostWithStringResponse(url, formParams);
            if (lines == null || lines.length == 0) {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to parse post response");
            }

            if (msgId != null) {
                // record the raw response
                logResponseMessage(msgId, lines);
            }

            // ERROR: add_lead PHONE NUMBER IN DNC - 5555555555|5555

            StringBuilder comment = new StringBuilder();
            for (String line : lines) {
                if (line.toLowerCase().contains("success")) {
                    return Disposition.create(PonyPhase.POST, Disposition.Status.ACCEPTED, line);
                }
                else {
                    comment.append(line).append("\r\n");
                }
            }

            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, comment.toString());
        }
        catch (IOException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
    }
}
