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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 4/23/14
 * Time: 9:19 AM
 */
public class AchieveFinancesWriter extends CouponHoundPingWriter {
    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {
/*
https://www.achieve-secure-application.com:8080/AchieveCardServiceWeb/AfsGet?Password={PASSWORD}&VendorId={VENDORID}&AffiliateId={AFFILIATEID}&VendorLeadId={VENDORLEADID}&SourceId={SOURCEID}&IpAddress={IPADDRESS}&Referrer={REFERRER}&FirstName={FIRSTNAME}&LastName={LASTNAME}&Address={ADDRESS}&City={CITY}&StateCode={STATECODE}&Zip={ZIP}&PrimaryPhone={PRIMARYPHONE}&AlternatePhone={ALTERNATEPHONE}&Email={EMAIL}&SSN={SSN}

Vendor ID: 457
Password: as540f5d
Affiliate ID is the field where you identify the website. Send us a test lead to make sure the posting is working.

==> source_id = VendorId=457;Password=as540f5d
==> url = https://www.achieve-secure-application.com:8080/AchieveCardServiceWeb/AfsGet
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

            // write out the tokenized sourceId
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            String checking = lead.getAttributeValue("has_checking");
            if (checking != null) {
                formParams.add(new BasicNameValuePair("AffiliateId", checking));
            }
            else {
                formParams.add(new BasicNameValuePair("AffiliateId", code));
            }
            formParams.add(new BasicNameValuePair("VendorLeadId", leadMatchId.toString()));

            // if there is an IP attribute being submitted, we use that, otherwise we'll try to get the IP from the posting party
            String ip = lead.getAttributeValue("ip");
            if (ip == null) {
                ip = arrival.getIpAddress();
            }
            formParams.add(new BasicNameValuePair("IpAddress", ip));

            formParams.add(new BasicNameValuePair("SourceId", code));
            formParams.add(new BasicNameValuePair("Referrer", publisherContext.getPublisher().getName()));

            String firstName = lead.getAttributeValue("fname");
            if (firstName == null) {
                firstName = lead.getAttributeValue("first_name");
            }
            formParams.add(new BasicNameValuePair("FirstName", firstName));

            String lastName = lead.getAttributeValue("lname");
            if (lastName == null) {
                lastName = lead.getAttributeValue("last_name");
            }
            formParams.add(new BasicNameValuePair("LastName", lastName));

            formParams.add(new BasicNameValuePair("Address", lead.getAttributeValue("address")));
            formParams.add(new BasicNameValuePair("City", lead.getAttributeValue("city")));
            formParams.add(new BasicNameValuePair("StateCode", lead.getAttributeValue("state")));

            String zip = lead.getAttributeValue("zip");
            if (zip == null) {
                zip = lead.getAttributeValue("zip_code");
            }
            formParams.add(new BasicNameValuePair("Zip", zip));

            // mobile wins over not mobile
            String phone = lead.getAttributeValue("mobile_phone");
            if (phone == null) {
                phone = lead.getAttributeValue("phone_number");
            }
            if (phone != null) {
                formParams.add(new BasicNameValuePair("PrimaryPhone", phone));
            }

//            formParams.add(new BasicNameValuePair("AlternatePhone", lead.getAttributeValue("")));
            formParams.add(new BasicNameValuePair("Email", up.getEmail()));

            if (lead.getAttributeValue("dob") != null) {
                // CH formats dates like this: MM/dd/yyyy
                formParams.add(new BasicNameValuePair("DOB", lead.getAttributeValue("dob")));
            }
            //SSN={SSN}

            Long msgId = logPostMessage(leadMatchId, url, formParams);

            Document doc;
            doc = httpGetWithXmlResponse(url, formParams);
            if (doc == null) {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to parse post response");
            }

            if (msgId != null) {
                // record the raw response
                logResponseMessage(msgId, serialize(doc));
            }


/*
   <CardHolderProcessingResponse><Messaging>Failed authentication verification.</Messaging><StatusCode>98</StatusCode><SubStatus i:nil="true"/></CardHolderProcessingResponse>

00 – Successful processing.
01 – Daily processing limit exceeded.
02 – Duplicate record found for the current card holder application.
03 – Failed the address verification system, unable to process the current card holder application.
04 – Failed profiler decision.
05 – Failed delivery point verification system.
06 – Failed fraud system.
97 – Failed data validation, the fields to correct will be listed in the Messaging provided.
98 – Failed authentication.
99 – A processing error occurred; contact the Achieve Card technical support to diagnose the issue.
*/

            // Create a XPathFactory
            XPathFactory xFactory = XPathFactory.newInstance();

            // Create a XPath object
            XPath xpath = xFactory.newXPath();

            // Compile the XPath expression

            XPathExpression expr = xpath.compile("//CardHolderProcessingResponse/StatusCode/text()");
            String result = (String) expr.evaluate(doc, XPathConstants.STRING);

            expr = xpath.compile("//CardHolderProcessingResponse/Messaging/text()");
            String message = (String) expr.evaluate(doc, XPathConstants.STRING);

            if ("00".equalsIgnoreCase(result.trim())) {
                return Disposition.createPost(Disposition.Status.ACCEPTED, null, null, message);
            }
            else {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, result + ":" + message);
            }
        }
        catch (SAXException e) {
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (ParserConfigurationException e) {
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (XPathExpressionException e) {
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (IOException e) {
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
    }
}
