package com.pony.advertiser.writers;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Buyer;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
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
 * Date: 6/30/13
 * Time: 4:16 PM
 */
public class ReplyWriter extends AdvertiserWriter {
	private static final Log LOG = LogFactory.getLog(ReplyWriter.class);
	
    private static final String PING_URL = "http://www.imotors.com/prxml/PingPREx2.asp";

    private static final String DEFAULT_USER = "AcquisitionLabs";
    private static final String DEFAULT_PWD = "ACQ!labs";

    @Override
    public boolean supportsPhase(PonyPhase phase) {
        if (phase == PonyPhase.POST || phase == PonyPhase.PING) {
            return true;
        }

        return false;
    }

    @Override
    public Disposition ping(Long leadMatchId, PingContext pingContext, RoutingCandidate candidate) {

        Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());

        Lead lead = pingContext.getLead();

        String user = DEFAULT_USER, password = DEFAULT_PWD;

        if (map.get("user") != null) {
            user = map.get("user");
        }

        if (map.get("password") != null) {
            password = map.get("password");
        }

        Long msgId = null;
        StringBuilder query = new StringBuilder();

        try {
            query.append("Make=").append(URLEncoder.encode(lead.getAttributeValue("make"), "utf-8")).append("&Model=").append(URLEncoder.encode(lead.getAttributeValue("model"), "utf-8")).append("&Zip=").append(URLEncoder.encode(lead.getAttributeValue("zipcode"), "utf-8")).append("&source=").append(URLEncoder.encode(user, "utf-8")).append("&password=").append(URLEncoder.encode(password, "utf-8"));
            msgId = logPingMessage(leadMatchId, query.toString());
        }
        catch (UnsupportedEncodingException e) {
            LOG.error(e);
        }

        Document doc;

        try {
            doc = httpGetWithXmlResponse(PING_URL + "?" + query.toString(), new ArrayList<NameValuePair>());
        }
        catch (SAXException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (ParserConfigurationException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (IOException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, e.getMessage());
        }

        if (doc == null) {
            return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, "unable to parse ping response format");
        }

        if (msgId != null) {
            // record the raw response
            logResponseMessage(msgId, serialize(doc));
        }

        // Create a XPathFactory
        XPathFactory xFactory = XPathFactory.newInstance();

        // Create a XPath object
        XPath xpath = xFactory.newXPath();

        try {
            XPathExpression expr = xpath.compile("//PingPR/@Accept");
            Node n = (Node) expr.evaluate(doc, XPathConstants.NODE);

            if (!"true".equalsIgnoreCase(n.getNodeValue())) {
                expr = xpath.compile("//PingPR/@Error");
                String error = (String) expr.evaluate(doc, XPathConstants.STRING);
                if (error != null && !"".equals(error)) {
                    return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, error);
                }

                return Disposition.create(PonyPhase.PING, Disposition.Status.NO_COVERAGE);
            }

            String price = map.get("price");
            BigDecimal bdPrice;
            if (price != null) {
                bdPrice = new BigDecimal(price);
            }
            else {
                bdPrice = new BigDecimal(10.0); // default
            }

            return parsePingResponse(doc, xpath, bdPrice);
        }
        catch (XPathExpressionException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, e.getMessage());
        }
    }

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {

        Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());

        String year = map.get("year");
        Calendar cal = Calendar.getInstance();
        if (year == null || "".equals(year)) {
            year = "" + cal.get(Calendar.YEAR);
        }

        TimeZone tz = TimeZone.getDefault();
        int offSet = tz.getRawOffset() / (1000 * 60 * 60);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss" + String.format("%03d:00", offSet));

        String user = DEFAULT_USER;

        if (map.get("user") != null) {
            user = map.get("user");
        }

        Disposition pingData = candidate.getDisposition();
        Buyer buyer = candidate.getBuyer();
        Lead lead = publisherContext.getLead();

        String reservationId = null;
        if (buyer != null) {
            reservationId = buyer.getReservationId();
        }

        if (reservationId == null && pingData != null) {
            reservationId = pingData.getExternalId();
        }

        String message = TEST_LEAD;

        message = message.replace("{user}", user);
        message = message.replace("{reservation-id}", (reservationId != null ? reservationId : ""));
        message = message.replace("{date}", df.format(cal.getTime()));   // 2006-04-12T18:25:11-07:00
        message = message.replace("{year}", lead.getAttributeValue("year", year));
        message = message.replace("{make}", lead.getAttributeValue("make"));
        message = message.replace("{model}", lead.getAttributeValue("model"));
        message = message.replace("{trim}", lead.getAttributeValue("trim", ""));
        message = message.replace("{first_name}", lead.getAttributeValue("first_name"));
        message = message.replace("{last_name}", lead.getAttributeValue("last_name"));
        message = message.replace("{email}", publisherContext.getUserProfile().getEmail());

        String phone = lead.getAttributeValue("mobile_phone");
        if (phone == null) {
            phone = lead.getAttributeValue("home_phone");
        }
        if (phone == null) {
            phone = lead.getAttributeValue("work_phone");
        }
        message = message.replace("{phone}", phone);

        message = message.replace("{street}", lead.getAttributeValue("address"));
        message = message.replace("{city}", lead.getAttributeValue("city"));
        message = message.replace("{state}", lead.getAttributeValue("state"));
        message = message.replace("{zip}", lead.getAttributeValue("zipcode"));

        message = message.replace("{vendor}", "Next Phase Media, Inc.");
        message = message.replace("{provider}", "Next Phase Media, Inc.");

        Long msgId = logPostMessage(leadMatchId, message);

        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("ADF", message));

        Document doc;
        try {
            doc = httpPostWithXmlResponse("https://www.imotors.com/PRXML/SubmitADFEx2.asp", formParams);
            if (doc == null) {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to parse post response");
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

        if (msgId != null) {
            // record the raw response
            logResponseMessage(msgId, serialize(doc));
        }

        /*   sample responses
        If there is an unexpected error at any point:
        <ADFResponse Success="False" Error="Unspecified" />

                Profanity: Blacklist level 2:
        <ADFResponse Success="False" Error="Contains unacceptable language" />

                No ADF root element, means the ADF document is completely invalid:
        <ADFResponse Success="False" Error="No adf document element found" />

                Three errors possible while checking for the source of the lead:
        <ADFResponse Success="False" Error="id is required" />
        <ADFResponse Success="False" Error="source is required" />
        <ADFResponse Success="False" Error="invalid source" />

                Data missing errors:
        <ADFResponse Success="False" Error="make is required" />
        <ADFResponse Success="False" Error="model is required" />
        <ADFResponse Success="False" Error="postalcode is required" />

                Duplicate entry error:
        <ADFResponse Success="False" Error="Lead is duplicated" />

                Franchise:
        <ADFResponse Success="False" Error="no matching franchise found" />

                Additional post responses if the token is valid.
        <ADFResponse Success="False" Error="Token '896-56326585' does not match or is expired" />
        <ADFResponse Success="False" Error="Token provided may not be null" />

                When a lead is successfully sent:
        <ADFResponse Success="True" LeadID="XXXXX" />
*/
        try {
            XPathFactory xFactory = XPathFactory.newInstance();

            // Create a XPath object
            XPath xpath = xFactory.newXPath();

            XPathExpression expr = xpath.compile("//ADFResponse");
            Node n = (Node) expr.evaluate(doc, XPathConstants.NODE);
            String success = n.getAttributes().getNamedItem("Success").getNodeValue();

            if (!"true".equalsIgnoreCase(success)) {
                // read the error
                Node error = n.getAttributes().getNamedItem("Error");
                if (error != null) {
                    String msg = error.getNodeValue();
                    return Disposition.createPost(Disposition.Status.REJECTED, null, null, msg);
                }
            }
            else {
                // read the lead id
                Node leadID = n.getAttributes().getNamedItem("LeadID");
                if (leadID != null) {
                    String externalLeadId = leadID.getNodeValue();
                    return Disposition.createPost(Disposition.Status.ACCEPTED, null, externalLeadId, null);
                }
            }
        }
        catch (XPathExpressionException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }

        return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "Unable to interpret post response");
    }


    private static Disposition parsePingResponse(Document doc, XPath xpath, BigDecimal price) throws XPathExpressionException {

        /* SUCCESS
        <?xml version="1.0" encoding="UTF-16" ?>
<PingPR Accept="True">
<DealerList numDealers="1">
<Dealer>
<Id>389</Id>
<Name>TEST ACCOUNT: ben dealer</Name>
  				<Country>USA</Country>
<State>ca</State>
  				<City>modesto</City>
  				<Street>1121 hawthronve</Street>
  				<PostalCode>00646</PostalCode>
  				<Distance>5</Distance>
  				<reservationID>568-3698563</reservationID>
  			</Dealer>
  		</DealerList>
  </PingPR>
        */


        /* FAIL
        <?xml version="1.0" encoding="windows-1252" ?>
  <PingPR Accept="False" Error="Invalid UserName Password Combination" />

<?xml version="1.0" encoding="windows-1252" ?>
  <PingPR Accept=" False" />
         */


        // Compile the XPath expression
        XPathExpression expr = xpath.compile("//DealerList/Dealer");
        NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        if (nl.getLength() == 0) {
            // we have an accepted ping response
            return Disposition.create(PonyPhase.PING, Disposition.Status.NO_COVERAGE);
        }

        // we have an accepted ping response
        Disposition acceptDisposition = Disposition.create(PonyPhase.PING, Disposition.Status.ACCEPTED);
        for (int i = 0; i < nl.getLength(); i++) {

            XPathExpression idExpr = xpath.compile("./Id/text()");

            String dealerId = (String) idExpr.evaluate(nl.item(i), XPathConstants.STRING);

            XPathExpression nameExpr = xpath.compile("./Name/text()");
            String name = (String) nameExpr.evaluate(nl.item(i), XPathConstants.STRING);

            XPathExpression postalCodeExpr = xpath.compile("./PostalCode/text()");
            String zipCode = (String) postalCodeExpr.evaluate(nl.item(i), XPathConstants.STRING);

            Buyer buyer = Buyer.create(dealerId, name, zipCode);

//            XPathExpression countryExpr = xpath.compile("./Country/text()");

            XPathExpression stateExpr = xpath.compile("./State/text()");
            buyer.setState((String) stateExpr.evaluate(nl.item(i), XPathConstants.STRING));

            XPathExpression cityExpr = xpath.compile("./City/text()");
            buyer.setCity((String) cityExpr.evaluate(nl.item(i), XPathConstants.STRING));

            XPathExpression streetExpr = xpath.compile("./Street/text()");
            buyer.setAddress((String) streetExpr.evaluate(nl.item(i), XPathConstants.STRING));

            XPathExpression distanceExpr = xpath.compile("./Distance/text()");
            buyer.setDistance((String) distanceExpr.evaluate(nl.item(i), XPathConstants.STRING));

            XPathExpression reservationIdExpr = xpath.compile("./reservationID/text()");
            buyer.setReservationId((String) reservationIdExpr.evaluate(nl.item(i), XPathConstants.STRING));

            buyer.setPrice(price);

//            String dealerGroupId = null, dealerGroupMaxPosts = null, contactName = null, contactPhone = null;

            acceptDisposition.addBuyer(buyer);
        }

        return acceptDisposition;
    }

    private static String TEST_LEAD = "<?xml version=\"1.0\" ?>" +
            "<?adf version=\"1.0\" ?>" +
            "<adf>" +
            "<prospect status=\"new\">" +
            "<id sequence=\"1\" source=\"{user}\">{reservation-id}</id>" +
            "<requestdate>{date}</requestdate>" +                        // 2006-04-12T18:25:11-07:00
            "<vehicle interest=\"buy\" status=\"new\">" +
            "<year>{year}</year>" +
            "<make>{make}</make>" +
            "<model>{model}</model>" +
            "<trim>{trim}</trim>" +
            "<transmission>auto</transmission>" +
            "<colorcombination>" +
            "<interiorcolor>no preference</interiorcolor>" +
            "<exteriorcolor>no preference</exteriorcolor>" +
            "<preference>1</preference>" +
            "</colorcombination>" +
            "<finance>" +
            "<method>undecided</method>" +
            "<amount>0.0</amount>" +
            "</finance>" +
            "</vehicle>" +
            "<customer>" +
            "<contact>" +
            "<name part=\"first\">{first_name}</name>" +
            "<name part=\"last\">{last_name}</name>" +
            "<email preferredcontact=\"1\">{email}</email>" +
            "<phone type=\"voice\" time=\"morning\" preferredcontact=\"0\">{phone}</phone>" +
            "<address>" +
            "<street line=\"1\">{street}</street>" +
            "<city>{city}</city>" +
            "<regioncode>{state}</regioncode>" +
            "<postalcode>{zip}</postalcode>" +
            "<country>US</country>" +
            "</address>" +
            "</contact>" +
            "<timeframe>Within 48 Hours</timeframe>" +
            "<comments>" +
            "</comments>" +
            "</customer>" +
            "<vendor>" +
            "<vendorname>{vendor}</vendorname>" +
            "</vendor>" +
            "<provider>" +
            "<name part=\"full\">{provider}</name>" +
            "</provider>" +
            "</prospect>" +
            "</adf>";
}
