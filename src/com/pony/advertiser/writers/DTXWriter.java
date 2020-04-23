package com.pony.advertiser.writers;

import com.arb.ws.clients.dtx.PLXLocator;
import com.arb.ws.clients.dtx.PLXSoap12Stub;
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
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Map;

/**
 * sql to parse raw post response:
 * select id, lead_match_id, substr(response_message, instr(response_message, '<Reservation_ID>')+16, ( instr(response_message, '</Reservation_ID>') - (instr(response_message, '<Reservation_ID>')+16) ) ) reservation_id from lead_posts where instr(response_message, 'NEWCAR_POSTGX_RESPONSE') > 1 and instr(response_message, '<SUCCESS>true</SUCCESS>')>1;
 * <p/>
 * ArbVentures 2013.
 * User: martin
 * Date: 6/30/13
 * Time: 5:57 PM
 */
public class DTXWriter extends AdvertiserWriter {
	private static final Log LOG = LogFactory.getLog(DTXWriter.class);
	
    @Override
    public boolean supportsPhase(PonyPhase phase) {
        if (phase == PonyPhase.POST || phase == PonyPhase.PING) {
            return true;
        }

        return false;
    }

    @Override
    public Disposition ping(Long leadMatchId, PingContext pingContext, RoutingCandidate candidate) {

        try {
            PLXSoap12Stub binding = (PLXSoap12Stub) new PLXLocator().getPLXSoap12();

            assert (binding != null);

            // Test operation
            String user = "0000-3030", password = "3030EDO";
            boolean debug = false;

            Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());
            String year = map.get("year");
            if (year == null || "".equals(year)) {
                Calendar cal = Calendar.getInstance();
                year = "" + cal.get(Calendar.YEAR);
            }

            String genMethod = map.get("genMethod");
            if (genMethod == null || "".equals(genMethod)) {
                genMethod = "SEARCH_PAID"; // SEARCH_ORGANIC, EMAIL, CONQUEST, OTHER
            }

            String pingTimeout = map.get("pingTimeout");
            int ptime = 10000;
            if (pingTimeout != null && !"".equals(pingTimeout)) {
                ptime = Integer.valueOf(pingTimeout);
            }
            // Time out after 10 seconds
            binding.setTimeout(ptime);

            Lead lead = pingContext.getLead();

            /*
               Your DT ID is : 0000-3030
               Password is:    3030EDO
            */
            String message = PING_MESSAGE.replace("{DTX_GEN_ID}", user);
            message = message.replace("{LEAD_GEN_METHOD}", genMethod);
            message = message.replace("{LEAD_SOURCE_IP}", pingContext.getArrival().getIpAddress());
            message = message.replace("{SOURCE_ID}", candidate.getIo().getCode()); // the originating site or channel
            message = message.replace("{GEN_LEAD_ID}", leadMatchId.toString()); // our lead match id
            message = message.replace("{MINIMUM_PRICE}", "1.00");
            message = message.replace("{YEAR}", year);
            message = message.replace("{MAKE}", lead.getAttributeValue("make"));
            message = message.replace("{MODEL}", lead.getAttributeValue("model"));
            message = message.replace("{TRIM}", lead.getAttributeValue("trim", ""));
            message = message.replace("{ZIP_POSTAL_CODE}", lead.getAttributeValue("zipcode"));

            Long msgId = logPingMessage(leadMatchId, message);

            String response = binding.NEWCAR_PingGX(user, password, message, debug);

            if (msgId != null) {
                logResponseMessage(msgId, response);
            }

            // parse results
            Document doc = parseToXmlDoc(response);

            // Create a XPathFactory
            XPathFactory xFactory = XPathFactory.newInstance();

            // Create a XPath object
            XPath xpath = xFactory.newXPath();

            // Compile the XPath expression
            XPathExpression expr = xpath.compile("//SUCCESS/text()");
            String status = (String) expr.evaluate(doc, XPathConstants.STRING);

            if (!"true".equalsIgnoreCase(status)) {
                expr = xpath.compile("//ERROR_MESSAGE/text()");
                String error = (String) expr.evaluate(doc, XPathConstants.STRING);

                if (error != null && !"".equals(error)) {
                    return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, error);
                }

                return Disposition.create(PonyPhase.PING, Disposition.Status.NO_COVERAGE);
            }

            expr = xpath.compile("//DealerList/Dealer");
            NodeList dealerNodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            Disposition acceptDealerSelect;
            if (dealerNodes != null && dealerNodes.getLength() > 0) {
                acceptDealerSelect = Disposition.create(PonyPhase.PING, Disposition.Status.ACCEPTED);
                parsePingResponse(xpath, acceptDealerSelect, dealerNodes);
            }
            else {
                acceptDealerSelect = Disposition.create(PonyPhase.PING, Disposition.Status.NO_COVERAGE);
            }

            return acceptDealerSelect;
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if (jre.getLinkedCause() != null) {
                jre.getLinkedCause().printStackTrace();
            }
            return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, jre.getMessage());
        }
        catch (RemoteException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (ParserConfigurationException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (XPathExpressionException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (SAXException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (IOException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, e.getMessage());
        }
    }

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {
        try {
            PLXSoap12Stub binding = (PLXSoap12Stub) new PLXLocator().getPLXSoap12();

            String user = "0000-3030", password = "3030EDO";
            boolean debug = false;

            Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());
            String year = map.get("year");
            if (year == null || "".equals(year)) {
                Calendar cal = Calendar.getInstance();
                year = "" + cal.get(Calendar.YEAR);
            }

            String genMethod = map.get("genMethod");
            if (genMethod == null || "".equals(genMethod)) {
                genMethod = "SEARCH_PAID"; // SEARCH_ORGANIC, EMAIL, CONQUEST, OTHER
            }

            String postTimeout = map.get("postTimeout");
            int ptime = 40000;
            if (postTimeout != null && !"".equals(postTimeout)) {
                ptime = Integer.valueOf(postTimeout);
            }

            // Time out after xx seconds
            binding.setTimeout(ptime);

            /* TODO: Note that when posting the lead to DT (NEWCAR_SELLGX) you should pass the price that is returned
            in the NEWCAR_PINGGX_RESPONSE in the MINIMUM_PRICE field,
            this will ensure that your lead doesn’t sell for less than that price.
            */

            Disposition pingData = candidate.getDisposition();
            Buyer buyer = candidate.getBuyer();
            String reservationId = null;
            BigDecimal price = null;

            if (buyer != null) {
                reservationId = buyer.getReservationId();
                price = buyer.getPrice();
            }

            if (reservationId == null && pingData != null) {
                reservationId = pingData.getExternalId();
            }

            if (price == null && pingData != null) {
                price = pingData.getPrice();
            }

            Lead lead = publisherContext.getLead();
            String message = POST_MESSAGE.replace("{DTX_GEN_ID}", user);
            message = message.replace("{LEAD_GEN_METHOD}", genMethod);

            String ip = "127.0.0.1";
            if (publisherContext.getArrival() != null && publisherContext.getArrival().getIpAddress() != null) {
                ip = publisherContext.getArrival().getIpAddress();
            }
            message = message.replace("{LEAD_SOURCE_IP}", ip);
            message = message.replace("{SOURCE_ID}", candidate.getIo().getCode()); // the originating site or channel
            message = message.replace("{Reservation_ID}", (reservationId != null ? reservationId : ""));
            message = message.replace("{MINIMUM_PRICE}", (price != null ? price.toString() : "1.0"));
            message = message.replace("{YEAR}", year);
            message = message.replace("{MAKE}", lead.getAttributeValue("make"));
            message = message.replace("{MODEL}", lead.getAttributeValue("model"));
            message = message.replace("{TRIM}", lead.getAttributeValue("trim", ""));
            message = message.replace("{FIRST_NAME}", lead.getAttributeValue("first_name", ""));
            message = message.replace("{LAST_NAME}", lead.getAttributeValue("last_name", ""));
            message = message.replace("{HOME_PHONE}", lead.getAttributeValue("home_phone", ""));

            String email = "";
            if (publisherContext.getUserProfile() != null && publisherContext.getUserProfile().getEmail() != null) {
                email = publisherContext.getUserProfile().getEmail();
            }
            message = message.replace("{EMAIL}", email);

            message = message.replace("{WORK_PHONE}", lead.getAttributeValue("work_phone", ""));
            message = message.replace("{MOBILE_PHONE}", lead.getAttributeValue("mobile_phone", ""));
            message = message.replace("{ADDRESS}", lead.getAttributeValue("address", ""));
            message = message.replace("{CITY}", lead.getAttributeValue("city", ""));
            message = message.replace("{STATE}", lead.getAttributeValue("state", ""));
            message = message.replace("{ZIP_POSTAL_CODE}", lead.getAttributeValue("zipcode", ""));

            Long msgId = logPostMessage(leadMatchId, message);

            // Test operation
            String results = binding.NEWCAR_PostGX(user, password, message, debug);
            if (msgId != null) {
                logResponseMessage(msgId, results);
            }

            // parse results
            Document doc = parseToXmlDoc(results);

            // Create a XPathFactory
            XPathFactory xFactory = XPathFactory.newInstance();

            // Create a XPath object
            XPath xpath = xFactory.newXPath();

            // Compile the XPath expression

            XPathExpression expr = xpath.compile("//DTX_LEAD_ID/text()");
            String externalLeadId = (String) expr.evaluate(doc, XPathConstants.STRING);

            expr = xpath.compile("//SUCCESS/text()");
            String status = (String) expr.evaluate(doc, XPathConstants.STRING);

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

                return Disposition.createPost(Disposition.Status.REJECTED, null, externalLeadId, msg.toString());
            }
            else {
                // parse price they returned (if any)

                expr = xpath.compile("//PRICE/text()");
                String postPrice = (String) expr.evaluate(doc, XPathConstants.STRING);
                if (postPrice != null) {
                    return Disposition.createPost(Disposition.Status.ACCEPTED, null, externalLeadId, new BigDecimal(postPrice), null);
                }
                else {
                    return Disposition.createPost(Disposition.Status.ACCEPTED, null, externalLeadId, null);
                }
            }
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if (jre.getLinkedCause() != null) {
                jre.getLinkedCause().printStackTrace();
            }
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, jre.getMessage());
        }
        catch (RemoteException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (ParserConfigurationException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (SAXException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (XPathExpressionException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (IOException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
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

    private static void parsePingResponse(XPath xpath, Disposition acceptDisposition, NodeList dealerNodes) throws XPathExpressionException {

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


                Buyer buyer = Buyer.create(buyerId, name, zipcode);
                buyer.setReservationId(reservationId);
                buyer.setCity(city);
                buyer.setPrice(price);
                buyer.setState(state);
                buyer.setDistance(distance);
                buyer.setAddress(street);

                String dealerGroupId, dealerGroupMaxPosts, contactName, contactPhone;
                Node groupNode = (Node) dealerGroupExpr.evaluate(dealerNodes.item(i), XPathConstants.NODE);
                if (groupNode != null) {
                    Node groupId = groupNode.getAttributes().getNamedItem("id");
                    if (groupId != null) {
                        dealerGroupId = groupId.getNodeValue();
                        buyer.setGroupId(dealerGroupId);
                    }

                    Node groupMaxPosts = groupNode.getAttributes().getNamedItem("max_post");
                    if (groupMaxPosts != null) {
                        dealerGroupMaxPosts = groupMaxPosts.getNodeValue();
                        buyer.setMaxPosts(dealerGroupMaxPosts);
                    }

                    contactName = (String) contactNameExpr.evaluate(dealerNodes.item(i), XPathConstants.STRING);
                    contactPhone = (String) contactPhoneExpr.evaluate(dealerNodes.item(i), XPathConstants.STRING);
                    buyer.setContactName(contactName);
                    buyer.setContactPhone(contactPhone);
                }
                acceptDisposition.addBuyer(buyer);
            }
        }
    }
}