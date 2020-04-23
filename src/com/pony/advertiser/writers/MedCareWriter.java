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
 * Date: 3/6/14
 * Time: 1:58 PM
 */
public class MedCareWriter extends CouponHoundPingWriter {
	private static final Log LOG = LogFactory.getLog(MedCareWriter.class);

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {

        /*
        Token=--------qxKHl0C2rloF8FphlEOCx5TIDrwU1GyNb7PH3K4DAEHDd4QjDeg7Iw4uMcd3EQb--------;Products=SA
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

            List<NameValuePair> formParams = new ArrayList<NameValuePair>();

            //City
            formParams.add(new BasicNameValuePair("City", lead.getAttributeValue("city")));

            // DOB YYYY-MM-DD
            if (lead.getAttributeValue("dob") != null) {
                // we store in : MM/dd/yyyy
                String d = lead.getAttributeValue("dob");
                String[] tokens = d.split("/");
                if (tokens.length == 3) {
                    formParams.add(new BasicNameValuePair("dob", tokens[2] + "-" + tokens[0] + "-" + tokens[1]));
                }
            }

//            Email
            formParams.add(new BasicNameValuePair("Email", up.getEmail()));

            // FirstName
            String firstName = lead.getAttributeValue("fname");
            if (firstName == null) {
                firstName = lead.getAttributeValue("first_name");
            }
            formParams.add(new BasicNameValuePair("FirstName", firstName));

            // LastName
            String lastName = lead.getAttributeValue("lname");
            if (lastName == null) {
                lastName = lead.getAttributeValue("last_name");
            }
            formParams.add(new BasicNameValuePair("LastName", lastName));

            // Phone1 (no -)
            String phone = lead.getAttributeValue("mobile_phone");
            if (phone == null) {
                // mobile wins over not mobile
                phone = lead.getAttributeValue("phone_number");
            }
            if (phone != null) {
                formParams.add(new BasicNameValuePair("Phone1", phone));
            }

//            State
            formParams.add(new BasicNameValuePair("State", lead.getAttributeValue("state")));

            // Street1
            formParams.add(new BasicNameValuePair("Street1", lead.getAttributeValue("address")));

            // Zip (5 digit)
            String zip = lead.getAttributeValue("zip");
            if (zip == null) {
                zip = lead.getAttributeValue("zip_code");
            }
            formParams.add(new BasicNameValuePair("Zip", zip));

//            Submit_IP
            // if there is an IP attribute being submitted, we use that, otherwise we'll try to get the IP from the posting party
            String ip = lead.getAttributeValue("ip");
            if (ip == null) {
                ip = arrival.getIpAddress();
            }
            formParams.add(new BasicNameValuePair("Submit_IP", ip));

            // Submit_Date
            DateFormat df = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
            formParams.add(new BasicNameValuePair("Submit_Date", df.format(Calendar.getInstance().getTime())));

//            SubID
            if (code != null) {
                formParams.add(new BasicNameValuePair("SubID", code));
            }

            // write out the tokenized sourceId
            // Token=--------qxKHl0C2rloF8FphlEOCx5TIDrwU1GyNb7PH3K4DAEHDd4QjDeg7Iw4uMcd3EQb--------;Products=SA
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

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
            {
                ErrorCode: "200"
                Message: "JA67538T: Lead has been accepted."
            }
             */

            String result = null, externalLeadId = null, comment = null;
            for (String line : lines) {
                // sample: {"ErrorCode":"422","Message":"One or more parameters did not meet validation requirements.","Errors":[{"FieldName":"phone1","Message":"Value is not a valid U.S. Phone number. (Invalid Area Code and/or Prefix)"}]}
                // sample2: {"ErrorCode":"200","Message":"MA67805H: Lead has been accepted."}

                // split by "," , then ":", replace "
                if (line.startsWith("{") && line.length() > 1) {
                    String[] elements = line.split(",");
                    if (elements.length > 0) {
                        int i = 0;
                        for (String element : elements) {
                            if (i++ > 1) {
                                break; // only the first two tokens
                            }
                            // read from quote to quote
                            StringBuilder tag = new StringBuilder();
                            boolean inside = false, isKey = false;
                            String key = null, value = null;
                            for (char c : element.toCharArray()) {
                                if (c == '\"') {
                                    if (inside) {
                                        if (isKey) {
                                            key = tag.toString();
                                            tag = new StringBuilder();
                                        }
                                        else {
                                            value = tag.toString();
                                            tag = new StringBuilder();
//                                            System.out.println("key=" + key + "; value=" + value);
                                            if ("errorcode".equals(key.trim().toLowerCase())) {
                                                result = value;
                                            }
                                            else if ("message".equals(key.trim().toLowerCase())) {
                                                comment = value;
                                            }
                                        }
                                    }
                                    else {
                                        isKey = !isKey;
                                    }

                                    inside = !inside;
                                }
                                else {
                                    if (c == ':' && !inside) {
                                        tag = new StringBuilder();
                                        inside = false;
                                    }
                                    else {
                                        if (inside) {
                                            tag.append(c);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            String reason = result + ": " + comment;
            if ("200".equalsIgnoreCase(result)) {
                return Disposition.createPost(Disposition.Status.ACCEPTED, null, externalLeadId, reason);
            }
            else {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, reason);
            }
        }
        catch (IOException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
    }

    public static void main(String[] args) {
        String linex = "{\"ErrorCode\":\"422\",\"Message\":\"One or more parameters did not meet validation requirements.\",\"Errors\":[{\"FieldName\":\"phone1\",\"Message\":\"Value is not a valid U.S. Phone number. (Invalid Area Code and/or Prefix)\"}]}";
        String line = "{\"ErrorCode\":\"200\",\"Message\":\"MA67805H: Lead has been accepted.\"}";

        // split by "," , then ":", replace "
        if (line.startsWith("{") && line.length() > 1) {
            String[] elements = line.split(",");
            if (elements.length > 0) {
                int i = 0;
                for (String element : elements) {
                    if (i++ > 1) {
                        break; // only the first two tokens
                    }
                    // read from quote to quote
                    StringBuilder tag = new StringBuilder();
                    boolean inside = false, isKey = false;
                    String key = null, value = null;
                    for (char c : element.toCharArray()) {
                        if (c == '\"') {
                            if (inside) {
                                if (isKey) {
                                    key = tag.toString();
                                    tag = new StringBuilder();
                                }
                                else {
                                    value = tag.toString();
                                    tag = new StringBuilder();
                                    LOG.debug("key=" + key + "; value=" + value);
                                }
                            }
                            else {
                                isKey = !isKey;
                            }

                            inside = !inside;
                        }
                        else {
                            if (c == ':' && !inside) {
//                                key = tag.toString();
                                tag = new StringBuilder();
//                                isKey = false;
                                inside = false;
                            }
                            else {
                                if (inside) {
                                    tag.append(c);
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}
