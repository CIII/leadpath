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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 1/22/14
 * Time: 10:37 AM
 */
public class LeadConduitWriter extends CouponHoundPingWriter {
    private static final String NO_TRUSTED_FORM_CERT = "NO_TRUSTED_FORM";
    private static final Log LOG = LogFactory.getLog(LeadConduitWriter.class);

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {

        /*
        @see https://app.leadconduit.com/doc/posting?xxCampaignId=000q7cfmh&xxAccountId=054ynedgy&xxSiteId=none

        Account Name: 	Acquisition Sciences
        Account ID: 	054ynedgy
        Campaign Name: 	MyDailyMoment Recipe of the Day
        Campaign ID: 	000q7cfmh
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

            // check if we need to ignore the trusted form cert
            if (map.get(NO_TRUSTED_FORM_CERT) == null) {
                String xxTrustedFormCertUrl = lead.getAttributeValue("xxTrustedFormCertUrl");
                if (xxTrustedFormCertUrl == null) {
                    // can't send without a trusted form cert url (see http://trustedform.com/)
                    return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "No xxTrustedFormCertUrl provided");
                }
                formParams.add(new BasicNameValuePair("xxTrustedFormCertUrl", xxTrustedFormCertUrl));
            }

            // write out the tokenized sourceId
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }


            // Enter your affiliate/SubID code here to track
            if (code != null) {
                formParams.add(new BasicNameValuePair("subID", code));
            }

//            // allow for a switch to send all lead attributes
//            if (map.get("sendAll") != null) {
//                for (Map.Entry<String, String> entry : lead.toMap().entrySet()) {
//                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//                }
//            }

            formParams.add(new BasicNameValuePair("email", up.getEmail()));
            formParams.add(new BasicNameValuePair("x1", up.getEmail()));

            // if there is an IP attribute being submitted, we use that, otherwise we'll try to get the IP from the posting party
            String ip = lead.getAttributeValue("ip");
            if (ip == null) {
                ip = arrival.getIpAddress();
            }
            formParams.add(new BasicNameValuePair("IP", ip));
            formParams.add(new BasicNameValuePair("ip", ip));
            formParams.add(new BasicNameValuePair("ip_address", ip));

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            formParams.add(new BasicNameValuePair("timestamp", df.format(Calendar.getInstance().getTime())));
            formParams.add(new BasicNameValuePair("date", df.format(Calendar.getInstance().getTime())));

            formParams.add(new BasicNameValuePair("site_url", publisherContext.getPublisher().getName()));
            formParams.add(new BasicNameValuePair("source_url", publisherContext.getPublisher().getName()));
            formParams.add(new BasicNameValuePair("website", publisherContext.getPublisher().getName()));

            formParams.add(new BasicNameValuePair("address", lead.getAttributeValue("address")));
            formParams.add(new BasicNameValuePair("address1", lead.getAttributeValue("address")));
            formParams.add(new BasicNameValuePair("x4", lead.getAttributeValue("address")));

            formParams.add(new BasicNameValuePair("city", lead.getAttributeValue("city")));
            formParams.add(new BasicNameValuePair("state", lead.getAttributeValue("state")));

            if (lead.getAttributeValue("dob") != null) {
                formParams.add(new BasicNameValuePair("dob", lead.getAttributeValue("dob")));

                // CH formats dates like this: MM/dd/yyyy
                // x11 x12 x13
                String dob = lead.getAttributeValue("dob");
                if (dob.length() >= 10) {
                    formParams.add(new BasicNameValuePair("x11", dob.substring(0, 2)));
                    formParams.add(new BasicNameValuePair("x12", dob.substring(3, 5)));
                    formParams.add(new BasicNameValuePair("x13", dob.substring(6, 10)));
                }
            }

            String gender = lead.getAttributeValue("gender");
            if (gender == null) {
                gender = "F";
            }
            formParams.add(new BasicNameValuePair("gender", gender));

            String firstName = lead.getAttributeValue("fname");
            if (firstName == null) {
                firstName = lead.getAttributeValue("first_name");
            }
            formParams.add(new BasicNameValuePair("fname", firstName));
            formParams.add(new BasicNameValuePair("firstname", firstName));
            formParams.add(new BasicNameValuePair("first_name", firstName));
            formParams.add(new BasicNameValuePair("x2", firstName));

            String lastName = lead.getAttributeValue("lname");
            if (lastName == null) {
                lastName = lead.getAttributeValue("last_name");
            }
            formParams.add(new BasicNameValuePair("lname", lastName));
            formParams.add(new BasicNameValuePair("lastname", lastName));
            formParams.add(new BasicNameValuePair("last_name", lastName));
            formParams.add(new BasicNameValuePair("x3", lastName));

            String zip = lead.getAttributeValue("zip");
            if (zip == null) {
                zip = lead.getAttributeValue("zip_code");
            }
            formParams.add(new BasicNameValuePair("zip", zip));
            formParams.add(new BasicNameValuePair("zipcode", zip));
            formParams.add(new BasicNameValuePair("zip_code", zip));
            formParams.add(new BasicNameValuePair("postal_code", zip));
            formParams.add(new BasicNameValuePair("x8", zip));
            formParams.add(new BasicNameValuePair("ZipCodeSGA", zip));

            // mobile wins over not mobile
            String phone = lead.getAttributeValue("mobile_phone");
            if (phone == null) {
                phone = lead.getAttributeValue("phone_number");
            }
            if (phone != null) {
                formParams.add(new BasicNameValuePair("phone_main", phone));
                formParams.add(new BasicNameValuePair("phone_primary", phone));
                formParams.add(new BasicNameValuePair("phone_day", phone));
                formParams.add(new BasicNameValuePair("phone", phone));
            }

            if (lead.getAttributeValue("pets") != null) {
                // TODO: map the value? (Cat, Dog, Cat and Dog)
                Map<String, String> options = new HashMap<String, String>();
                options.put("0", "None");
                options.put("1", "Cat");
                options.put("2", "Dog");
                options.put("3", "Cat and Dog");

                String pets = options.get(lead.getAttributeValue("pets"));
                if (pets == null) {
                    pets = lead.getAttributeValue("pets");
                }
                formParams.add(new BasicNameValuePair("notes", pets));
            }

            formParams.add(new BasicNameValuePair("Consent", "Y"));

            if (lead.getAttributeValue("number_children") != null) {
                formParams.add(new BasicNameValuePair("NumberOfChildren", lead.getAttributeValue("number_children")));
            }

            /*
             <input type="hidden" name="xxRedir" value="http://yoursite.com/thankyou.aspx"/>
             <input type="hidden" name="xxRedirVerbose" value="true"/>
            */

            Long msgId = logPostMessage(leadMatchId, url, formParams);

            Document doc;
            doc = httpPostWithXmlResponse(url, formParams);
            if (doc == null) {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to parse post response");
            }

            if (msgId != null) {
                // record the raw response
                logResponseMessage(msgId, serialize(doc));
            }

            // parse the response
            /*
            success response:
            <?xml version="1.0"?>
                <!DOCTYPE response SYSTEM "http://app.leadconduit.com/dtd/response-v2-basic.dtd">
                <response>
                  <result>success</result>
                  <leadId>000drxhsn</leadId>
                  <url><![CDATA[https://app.leadconduit.com/leads?id=000drxhsn]]></url>
                </response>


             error response:
            <?xml version="1.0"?>
                <!DOCTYPE response SYSTEM "http://app.leadconduit.com/dtd/response-v2-basic.dtd">
                <response>
                  <result>failure</result>
                  <reason>duplicate</reason>
                  <reason>invalid phone_home</reason>
                  <leadId>000drxhso</leadId>
                  <url><![CDATA[https://app.leadconduit.com/leads?id=000drxhso]]></url>
                </response>
             */

            // Create a XPathFactory
            XPathFactory xFactory = XPathFactory.newInstance();

            // Create a XPath object
            XPath xpath = xFactory.newXPath();

            // Compile the XPath expression

            XPathExpression expr = xpath.compile("//response/result/text()");
            String result = (String) expr.evaluate(doc, XPathConstants.STRING);

            expr = xpath.compile("//leadId/text()");
            String externalLeadId = (String) expr.evaluate(doc, XPathConstants.STRING);

            if ("success".equalsIgnoreCase(result.trim()) || "queued".equalsIgnoreCase(result.trim())) {
                return Disposition.createPost(Disposition.Status.ACCEPTED, null, externalLeadId, result);
            }
            else {
                expr = xpath.compile("//reason/text()");
                String reason = (String) expr.evaluate(doc, XPathConstants.STRING);
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, reason);
            }
        }
        catch (SAXException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (ParserConfigurationException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (IOException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (XPathExpressionException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
    }


    public static void main(String[] args) {
        String dob = "12/31/1988";
        if (dob.length() >= 10) {
            LOG.debug(dob.substring(0, 2));
            LOG.debug(dob.substring(3, 5));
            LOG.debug(dob.substring(6, 10));
        }
    }
}