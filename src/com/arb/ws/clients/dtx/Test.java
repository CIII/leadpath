package com.arb.ws.clients.dtx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 6/30/13
 * Time: 6:16 PM
 */
public class Test {
	private static final Log LOG = LogFactory.getLog(Test.class);

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
//        testParsingPingMessage();

        Lead lead = new Lead();
        List<Dealer> dealers = ping(lead);
//
        for (Dealer dealer : dealers) {
            post(lead, dealer);
        }
//
        sell();
    }

    private static void sell() {
        try {
            PLXSoap12Stub binding = (PLXSoap12Stub) new PLXLocator().getPLXSoap12();
            assert (binding != null);

            String userName = "", password = "";
            boolean debug = true;

            String message = SELL_MESSAGE.replace("{}", "");

            // Time out after 10 seconds
            binding.setTimeout(10000);

            // Test operation
            String response = binding.NEWCAR_SellGX(userName, password, message, debug);
            //TODO: parse it
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if (jre.getLinkedCause() != null) {
                jre.getLinkedCause().printStackTrace();
            }
            throw new Error("JAX-RPC ServiceException caught: " + jre);
        }
        catch (RemoteException e) {
            LOG.error(e);
        }


    }

    /**
     * post in reference to a previous ping
     *
     * @param lead
     * @param dealer
     * @return
     */
    private static String post(Lead lead, Dealer dealer) {
        try {
            PLXSoap12Stub binding = (PLXSoap12Stub) new PLXLocator().getPLXSoap12();

            // Time out after 10 seconds
            binding.setTimeout(10000);

            String user = "0000-3030", password = "3030EDO";
            boolean debug = true;

            String genMethod = "SEARCH_PAID"; // SEARCH_ORGANIC, EMAIL, CONQUEST, OTHER

            /* TODO: Note that when posting the lead to DT (NEWCAR_SELLGX) you should pass the price that is returned
            in the NEWCAR_PINGGX_RESPONSE in the MINIMUM_PRICE field,
            this will ensure that your lead doesn't sell for less than that price.
            */

            String message = POST_MESSAGE.replace("{DTX_GEN_ID}", user);
            message = message.replace("{LEAD_GEN_METHOD}", genMethod);
            message = message.replace("{LEAD_SOURCE_IP}", lead.get("ip_address"));
            message = message.replace("{SOURCE_ID}", "wiserauto.com"); // the originating site or channel
            message = message.replace("{Reservation_ID}", dealer.getReservationId());
            message = message.replace("{MINIMUM_PRICE}", dealer.getPrice());
            message = message.replace("{YEAR}", lead.get("year"));
            message = message.replace("{MAKE}", lead.get("make"));
            message = message.replace("{MODEL}", lead.get("model"));
            message = message.replace("{TRIM}", lead.get("trim", ""));
            message = message.replace("{FIRST_NAME}", lead.get("first_name", ""));
            message = message.replace("{LAST_NAME}", lead.get("last_name", ""));
            message = message.replace("{HOME_PHONE}", lead.get("home_phone", ""));
            message = message.replace("{EMAIL}", lead.get("email", ""));
            message = message.replace("{WORK_PHONE}", lead.get("work_phone", ""));
            message = message.replace("{MOBILE_PHONE}", lead.get("mobile_phone", ""));
            message = message.replace("{ADDRESS}", lead.get("address", ""));
            message = message.replace("{CITY}", lead.get("city", ""));
            message = message.replace("{STATE}", lead.get("state", ""));
            message = message.replace("{ZIP_POSTAL_CODE}", lead.get("zipcode", ""));

            LOG.info("post: sending\n" + message + "\n");

            // Test operation
            String results = binding.NEWCAR_PostGX(user, password, message, debug);

            LOG.info("post response=\n" + results);

            // parse results
            Document doc = parseToXmlDoc(results);

            // Create a XPathFactory
            XPathFactory xFactory = XPathFactory.newInstance();

            // Create a XPath object
            XPath xpath = xFactory.newXPath();

            // Compile the XPath expression

            XPathExpression expr = xpath.compile("//DTX_LEAD_ID/text()");
            String dtxLeadId = (String) expr.evaluate(doc, XPathConstants.STRING);

            LOG.debug("leadID=" + dtxLeadId);

            expr = xpath.compile("//SUCCESS/text()");
            String status = (String) expr.evaluate(doc, XPathConstants.STRING);

            LOG.debug("success?" + status);

            if (!"true".equalsIgnoreCase(status)) {
                expr = xpath.compile("//ERROR/text()");
                StringBuilder msg = new StringBuilder();
                String error = (String) expr.evaluate(doc, XPathConstants.STRING);
                if (error != null) {
                    msg.append(error);
                }

                expr = xpath.compile("//ERROR_MESSAGE/text()");
                error = (String) expr.evaluate(doc, XPathConstants.STRING);

                if (error != null && !"".equals(error)) {
                    if (msg.length() > 0) {
                        msg.append(": ");
                    }
                    msg.append(error);
                }
                LOG.debug("ErrorMessage=" + msg);

                return null;
            }

            return parsePostResponse(doc, xpath);
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if (jre.getLinkedCause() != null) {
                jre.getLinkedCause().printStackTrace();
            }
            throw new Error("JAX-RPC ServiceException caught: " + jre);
        }
        catch (RemoteException e) {
            LOG.error(e);
        }
        catch (ParserConfigurationException e) {
            LOG.error(e);
        }
        catch (SAXException e) {
            LOG.error(e);
        }
        catch (XPathExpressionException e) {
            LOG.error(e);
        }
        catch (IOException e) {
            LOG.error(e);
        }

        return null;
    }

    private static String parsePostResponse(Document doc, XPath xpath) throws XPathExpressionException {
        /*
        <NEWCAR_POSTGX_RESPONSE xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns="www.detroittradingexchange.com/SellerMessages">
          <DTX_LEAD_ID>1366</DTX_LEAD_ID>
          <SUCCESS>true</SUCCESS>
          <CACHED_RESPONSE>false</CACHED_RESPONSE>
          <BUYER_ID>0000-2333</BUYER_ID>
          <PRICE>20.8333</PRICE>
          <REASON/>
          <REASON_MESSAGE/>
          <ERROR/>
          <ERROR_MESSAGE/>
          <DealerList>
            <Dealer>
                <Reservation_ID>1362|0000-2333|e3eeb2a1-2449-4100-9738-e8595b787c7f|3299</Reservation_ID>
                <Price>20.8333</Price>
                <Distance>0</Distance>
                <DealerGroup id="2333" max_post="3">
                <Contact/>
            </Dealer>
          </DealerList>
        </NEWCAR_POSTGX_RESPONSE>
         */

        XPathExpression expr = xpath.compile("//DTX_LEAD_ID/text()");
        return (String) expr.evaluate(doc, XPathConstants.STRING);
    }

    private static List<Dealer> ping(Lead lead) {

        List<Dealer> dealers = new ArrayList<Dealer>();

        PLXSoap12Stub binding;
        try {
            binding = (PLXSoap12Stub) new PLXLocator().getPLXSoap12();

            assert (binding != null);

            // Time out after 10 seconds
            binding.setTimeout(10000);

            // Test operation
            String user = "0000-3030", password = "3030EDO";
            boolean debug = true;

            String genMethod = "SEARCH_PAID"; // SEARCH_ORGANIC, EMAIL, CONQUEST, OTHER

            /*
            Your DT ID is : 0000-3030
            Password is:    3030EDO
             */
            String message = PING_MESSAGE.replace("{DTX_GEN_ID}", user);
            message = message.replace("{LEAD_GEN_METHOD}", genMethod);
            message = message.replace("{LEAD_SOURCE_IP}", lead.get("ip_address"));
            message = message.replace("{SOURCE_ID}", "1234993"); // the originating site or channel
            message = message.replace("{GEN_LEAD_ID}", "999878"); // our lead match id
            message = message.replace("{MINIMUM_PRICE}", "1.00");
            message = message.replace("{YEAR}", lead.get("year"));
            message = message.replace("{MAKE}", lead.get("make"));
            message = message.replace("{MODEL}", lead.get("model"));
            message = message.replace("{TRIM}", lead.get("trim"));
            message = message.replace("{ZIP_POSTAL_CODE}", lead.get("zipcode"));

            LOG.debug("sending:\r\n" + message);

            String response = binding.NEWCAR_PingGX(user, password, message, debug);
            LOG.debug(response);

            // parse results
            Document doc = parseToXmlDoc(response);

            // Create a XPathFactory
            XPathFactory xFactory = XPathFactory.newInstance();

            // Create a XPath object
            XPath xpath = xFactory.newXPath();

            // Compile the XPath expression
            XPathExpression expr = xpath.compile("//SUCCESS/text()");
            String status = (String) expr.evaluate(doc, XPathConstants.STRING);
            LOG.debug("success?" + status);

            if (!"true".equalsIgnoreCase(status)) {
                expr = xpath.compile("//ERROR_MESSAGE/text()");
                String error = (String) expr.evaluate(doc, XPathConstants.STRING);
                LOG.debug("ErrorMessage=" + error);

                return dealers;
            }

            dealers.addAll(parsePingResponse(doc, xpath));

            // debug:
            for (Dealer dealer : dealers) {
                LOG.debug(dealer);
            }
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if (jre.getLinkedCause() != null) {
                jre.getLinkedCause().printStackTrace();
            }
            throw new Error("JAX-RPC ServiceException caught: " + jre);
        }
        catch (RemoteException e) {
            LOG.error(e);
        }
        catch (ParserConfigurationException e) {
            LOG.error(e);
        }
        catch (XPathExpressionException e) {
            LOG.error(e);
        }
        catch (SAXException e) {
            LOG.error(e);
        }
        catch (IOException e) {
            LOG.error(e);
        }

        return dealers;
    }

    private static Document parseToXmlDoc(String response) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder;
        Document doc;

        builder = factory.newDocumentBuilder();
        doc = builder.parse(new ByteArrayInputStream(response.getBytes("UTF-8")));
        return doc;
    }

    private static List<Dealer> parsePingResponse(Document doc, XPath xpath) throws XPathExpressionException {
        List<Dealer> dealers = new ArrayList<Dealer>();

        XPathExpression expr = xpath.compile("//DealerList/Dealer");

        NodeList dealerNodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        if (dealerNodes != null && dealerNodes.getLength() > 0) {
            XPathExpression buyerExpr = xpath.compile("./BUYER_ID/text()");
            XPathExpression reservationExpr = xpath.compile("./Reservation_ID/text()");
            XPathExpression priceExpr = xpath.compile("./Price/text()");
            XPathExpression nameExpr = xpath.compile("./Name/text()");
            XPathExpression stateExpr = xpath.compile("./State/text()");
            XPathExpression cityExpr = xpath.compile("./City/text()");
            XPathExpression streetExpr = xpath.compile("./Street/text()");
            XPathExpression zipcodeExpr = xpath.compile("./Postalcode/text()");
            XPathExpression distanceExpr = xpath.compile("./Distance/text()");
            XPathExpression dealerGroupExpr = xpath.compile("./DealerGroup");
            XPathExpression contactNameExpr = xpath.compile("./Contact/Name/text()");
            XPathExpression contactPhoneExpr = xpath.compile("./Contact/Phone/text()");

            for (int i = 0; i < dealerNodes.getLength(); i++) {
                String buyerId = (String) buyerExpr.evaluate(dealerNodes.item(i), XPathConstants.STRING);
                String reservationId = (String) reservationExpr.evaluate(dealerNodes.item(i), XPathConstants.STRING);
                String price = (String) priceExpr.evaluate(dealerNodes.item(i), XPathConstants.STRING);
                String name = (String) nameExpr.evaluate(dealerNodes.item(i), XPathConstants.STRING);
                String state = (String) stateExpr.evaluate(dealerNodes.item(i), XPathConstants.STRING);
                String city = (String) cityExpr.evaluate(dealerNodes.item(i), XPathConstants.STRING);
                String street = (String) streetExpr.evaluate(dealerNodes.item(i), XPathConstants.STRING);
                String zipcode = (String) zipcodeExpr.evaluate(dealerNodes.item(i), XPathConstants.STRING);
                String distance = (String) distanceExpr.evaluate(dealerNodes.item(i), XPathConstants.STRING);

                Dealer dealer = new Dealer(buyerId, reservationId, price, name, state, city, street, zipcode, distance);

                String dealerGroupId = null, dealerGroupMaxPosts = null, contactName = null, contactPhone = null;
                Node groupNode = (Node) dealerGroupExpr.evaluate(dealerNodes.item(i), XPathConstants.NODE);
                if (groupNode != null) {
                    Node groupId = groupNode.getAttributes().getNamedItem("id");
                    if (groupId != null) {
                        dealerGroupId = groupId.getNodeValue();
                        dealer.setGroupId(dealerGroupId);
                    }

                    Node groupMaxPosts = groupNode.getAttributes().getNamedItem("max_post");
                    if (groupMaxPosts != null) {
                        dealerGroupMaxPosts = groupMaxPosts.getNodeValue();
                        dealer.setGroupMaxPosts(dealerGroupMaxPosts);
                    }

                    contactName = (String) contactNameExpr.evaluate(dealerNodes.item(i), XPathConstants.STRING);
                    contactPhone = (String) contactPhoneExpr.evaluate(dealerNodes.item(i), XPathConstants.STRING);
                    dealer.setContactName(contactName);
                    dealer.setContactPhone(contactPhone);
                }

                dealers.add(dealer);
            }

            /*
    <DTX_LEAD_ID>1187</DTX_LEAD_ID>
       <SUCCESS>true</SUCCESS>
       <CACHED_RESPONSE>false</CACHED_RESPONSE>
       <PRICE>17.8333</PRICE>
       <DealerList>
           <Dealer>
               <BUYER_ID>0000-2127</BUYER_ID>
               <Reservation_ID>1187|0000-2333|87a71bce-5150-4e03-8e29- c10601b02f9b|3297</Reservation_ID>
               <Price>17.8333</Price>
               <Name>DTS Test Dealer 1</Name>
               <State>MI</State>
               <City>Southfield</City>
               <Street>2000 Town Center STE 1300</Street>
               <Postalcode>48075</Postalcode>
               <Distance>0</Distance>
               <DealerGroup id="2333" max_post="3">
                   <Contact>
                       <Name>Matt McDonald</Name>
                       <Phone/>
                   </Contact>
               </Dealer>
               <Dealer> ...
            */
        }

        return dealers;
    }


    private static final String PING_MESSAGE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><NEWCAR_PINGGX_REQUEST xmlns=\"www.detroittradingexchange.com/SellerMessages\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"www.detroittradingexchange.com/SellerMessages http://www.detroittradingexchange.com/SellerMessages/NewCar_PingGX_request.xsd\">\n" +
            "<Lead_Metadata>\n" +
            "<DTX_GEN_ID>{DTX_GEN_ID}</DTX_GEN_ID>" +
            "<LEAD_GEN_METHOD>{LEAD_GEN_METHOD}</LEAD_GEN_METHOD>" +
            "<LEAD_SOURCE_IP>{LEAD_SOURCE_IP}</LEAD_SOURCE_IP>" +
            "<SOURCE_ID>{SOURCE_ID}</SOURCE_ID><CAMPAIGN_ID/>\n" +
            "<GEN_LEAD_ID/>\n" +
            "<SELF_GENERATED_LEAD>True</SELF_GENERATED_LEAD>\n" +
            "</Lead_Metadata>" +
            "<Sales_Instructions>\n" +
            "<MINIMUM_PRICE>{MINIMUM_PRICE}</MINIMUM_PRICE>" +
            "<REQUIRE_ZIP_BACK>true</REQUIRE_ZIP_BACK>" +
            "<PING_TIMEOUT_SECONDS>10</PING_TIMEOUT_SECONDS>\n" +
            "</Sales_Instructions>" +
            "<Lead_Data>\n" +
            "<VEHICLE_INFO>" +
            "<YEAR>{YEAR}</YEAR>\n" +
            "<MAKE>{MAKE}</MAKE>" +
            "<MODEL>{MODEL}</MODEL>" +
            "<TRIM>{TRIM}</TRIM>" +
            "<INTERIOR_COLOR/><EXTERIOR_COLOR/>\n" +
            "</VEHICLE_INFO>\n" +
            "<PURCHASE_INFO>" +
            "<FINANCING/>" +
            "<TIME_FRAME_DAYS/>" +
            "</PURCHASE_INFO>\n" +
            "<PERSONAL_INFO><FIRST_NAME/><LAST_NAME/>" +
            "</PERSONAL_INFO>\n" +
            "<CONTACT_INFO><HOME_PHONE/><EMAIL/><WORK_PHONE/><WORK_PHONE_EXT/><MOBILE_PHONE/><CONTACT_TIME/>\n" +
            "</CONTACT_INFO>\n" +
            "<RESIDENCE_INFO><ADDRESS/><CITY/><STATE_PROVINCE/><ZIP_POSTAL_CODE>{ZIP_POSTAL_CODE}</ZIP_POSTAL_CODE><COUNTRY/>\n" +
            "</RESIDENCE_INFO>\n" +
            "<COMMENTS/>\n" +
            "<BUYER_DETAILS><FIRST_TIME_BUYER/><TRADE_IN>\n" +
            "<YEAR/><MAKE/><MODEL/><TRIM/><MILEAGE/></TRADE_IN>\n" +
            "</BUYER_DETAILS>\n" +
            "</Lead_Data>" +
            "</NEWCAR_PINGGX_REQUEST>";

    private static String POST_MESSAGE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><NEWCAR_POSTGX_REQUEST xmlns=\"www.detroittradingexchange.com/SellerMessages\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"www.detroittradingexchange.com/SellerMessages http://www.detroittradingexchange.com/SellerMessages/NewCar_PostGX_request.xsd\">\n" +
            "<Lead_Metadata>\n" +
            "<DTX_GEN_ID>{DTX_GEN_ID}</DTX_GEN_ID>" +
            "<LEAD_GEN_METHOD>{LEAD_GEN_METHOD}</LEAD_GEN_METHOD>" +
            "<LEAD_SOURCE_IP>{LEAD_SOURCE_IP}</LEAD_SOURCE_IP>" +
            "<SOURCE_ID>{SOURCE_ID}</SOURCE_ID><CAMPAIGN_ID></CAMPAIGN_ID>\n" +
            "<GEN_LEAD_ID></GEN_LEAD_ID>\n" +
            "<SELF_GENERATED_LEAD>true</SELF_GENERATED_LEAD>\n" +
            "</Lead_Metadata>\n" +
            "<Post_Instructions>" +
            "<Reservation_ID>{Reservation_ID}</Reservation_ID>" +
            "</Post_Instructions>\n" +
            "<Lead_Data>" +
            "<VEHICLE_INFO>" +
            "<YEAR>{YEAR}</YEAR>" +
            "<MAKE>{MAKE}</MAKE>" +
            "<MODEL>{MODEL}</MODEL>" +
            "<TRIM>{TRIM}</TRIM>" +
            "<INTERIOR_COLOR></INTERIOR_COLOR>" +
            "<EXTERIOR_COLOR></EXTERIOR_COLOR>" +
            "</VEHICLE_INFO>\n" +
            "<PURCHASE_INFO>" +
            "<FINANCING></FINANCING>\n" +
            "<TIME_FRAME_DAYS></TIME_FRAME_DAYS>" +
            "</PURCHASE_INFO>\n" +
            "<PERSONAL_INFO>" +
            "<FIRST_NAME>{FIRST_NAME}</FIRST_NAME>" +
            "<LAST_NAME>{LAST_NAME}</LAST_NAME>" +
            "</PERSONAL_INFO>\n" +
            "<CONTACT_INFO>" +
            "<HOME_PHONE>{HOME_PHONE}</HOME_PHONE>" +
            "<EMAIL>{EMAIL}</EMAIL>" +
            "<WORK_PHONE>{WORK_PHONE}</WORK_PHONE>" +
            "<WORK_PHONE_EXT/>" +
            "<MOBILE_PHONE>{MOBILE_PHONE}</MOBILE_PHONE>" +
            "<CONTACT_TIME>Any</CONTACT_TIME>\n" +
            "</CONTACT_INFO>\n" +
            "<RESIDENCE_INFO>" +
            "<ADDRESS>{ADDRESS}</ADDRESS>" +
            "<CITY>{CITY}</CITY>" +
            "<STATE_PROVINCE>{STATE}</STATE_PROVINCE>" +
            "<ZIP_POSTAL_CODE>{ZIP_POSTAL_CODE}</ZIP_POSTAL_CODE>" +
            "<COUNTRY>USA</COUNTRY>" +
            "</RESIDENCE_INFO>\n" +
            "<COMMENTS></COMMENTS>\n" +
            "<BUYER_DETAILS>" +
            "<FIRST_TIME_BUYER>false</FIRST_TIME_BUYER>" +
            "<TRADE_IN>\n" +
            "<YEAR></YEAR><MAKE></MAKE><MODEL></MODEL><TRIM></TRIM><MILEAGE></MILEAGE>\n" +
            "</TRADE_IN>\n" +
            "</BUYER_DETAILS>\n" +
            "</Lead_Data>\n" +
            "</NEWCAR_POSTGX_REQUEST>\n";

    private static String SELL_MESSAGE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    private static class Lead {

        static Map<String, String> attributes = new HashMap<String, String>();

        static {
            attributes.put("ip_address", "66.94.234.13");
//            "{SOURCE_ID}", "1234993"); // the originating site or channel
//            "{GEN_LEAD_ID}", "999878"); // our lead match id
//            "{MINIMUM_PRICE}", "1.00");
            attributes.put("year", "2013");
            attributes.put("make", "Audi");
            attributes.put("model", "A4");
            attributes.put("trim", "Premium");
            attributes.put("zipcode", "48187");
            attributes.put("state", "MI");
            attributes.put("city", "Canton");
            attributes.put("first_name", "Joe");
            attributes.put("last_name", "Tester");
            attributes.put("email", "joe@testme.com");
            attributes.put("address", "100 Test Drive");
            attributes.put("home_phone","248-352-1313");
        }

        /* valid test zips:
       48007,48009,48012,48015,48017,48021,48025,48026,48030,48033,48034,48035,48036,48037,48038,48043,48044,48046,
       48066,48067,48068,48069,48070,48071,48072,48073,48075,48076,48080,48081,48082,48083,48084,48085,48086,
       48088,48089,48090,48091,48092,48093,48098,48099,48101,48120,48121,48122,48123,48124,48125,48126,48127,48128,
       48135,48136,48141,48146,48150,48151,48152,48153,48154,48167,48168,48170,48174,48175,48180,48184,48185,
       48186,48187,48188,48192,48193,48195,48201,48202,48203,48204,48205,48206,48207,48208,48209,48210,48211,48212,
       48213,48214,48215,48216,48217,48218,48219,48220,48221,48222,48223,48224,48225,48226,48227,48228,48229,
       48230,48231,48232,48233,48234,48235,48236,48237,48238,48239,48240,48242,48243,48244,48255,48260,48264,48265,
       48266,48267,48268,48269,48272,48275,48277,48278,48279,48288,48301,48302,48303,48304,48306,48307,48308,
       48309,48310,48311,48312,48313,48314,48315,48316,48317,48318,48320,48321,48322,48323,48324,48325,48326,48327,
       48328,48329,48330,48331,48332,48333,48334,48335,48336,48340,48341,48342,48343,48359,48360,48374,48375,48376,
       48377,48381,48382,48386,48387,48390,48391,48393,48397
        */

        public String get(String attributeName) {
            return attributes.get(attributeName);
        }

        public String get(String attributeName, String defaultValue) {
            String value = get(attributeName);
            if (value != null) {
                return value;
            }
            return defaultValue;
        }
    }

    private static class Dealer {
        private final String buyerId, reservationId, price, name, state, city, street, zipcode, distance;
        private String groupId;
        private String maxPosts;
        private String contactName;
        private String contactPhone;

        private Dealer(String buyerId, String reservationId, String price, String name, String state, String city, String street, String zipcode, String distance) {
            this.buyerId = buyerId;
            this.reservationId = reservationId;
            this.price = price;
            this.name = name;
            this.state = state;
            this.city = city;
            this.street = street;
            this.zipcode = zipcode;
            this.distance = distance;
        }

        public String getBuyerId() {
            return buyerId;
        }

        public String getReservationId() {
            return reservationId;
        }

        public String getPrice() {
            return price;
        }

        public String getName() {
            return name;
        }

        public String getState() {
            return state;
        }

        public String getCity() {
            return city;
        }

        public String getStreet() {
            return street;
        }

        public String getZipcode() {
            return zipcode;
        }

        public String getDistance() {
            return distance;
        }

        public String getGroupId() {
            return groupId;
        }

        public String getMaxPosts() {
            return maxPosts;
        }

        public String getContactName() {
            return contactName;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Dealer dealer = (Dealer) o;

            if (!buyerId.equals(dealer.buyerId)) {
                return false;
            }
            if (!reservationId.equals(dealer.reservationId)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = buyerId.hashCode();
            result = 31 * result + reservationId.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Dealer: buyer=" + buyerId + " reservationId=" + reservationId + " price=" + price + " zip=" + zipcode + " name=" + name;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public void setGroupMaxPosts(String maxPosts) {
            this.maxPosts = maxPosts;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }
    }
}
