package com.pony.advertiser.writers.solar;

import com.pony.advertiser.*;
import com.pony.core.PonyPhase;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.models.ArrivalModel;
import com.pony.models.Model;
import com.pony.publisher.IPublisherContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by martin on 6/19/16.
 * <p/>
 * 6/29/16:
 * Important Note: For future test leads we want to send to LG, please use first or last name "ckmtest".
 */
public class LeadGenesisWriter extends AdvertiserWriter {
	private static final String DEFAULT_CAMPKEY = "vnf46GBEtIw";
	private static final String DEFAULT_CAMPID = "3433";
	private static final String VERTICAL_ID = "92";
	private static final String URL = "https://api.leadgenesis.info/v1/coverage/?";
	private static final String API_KEY = "BK9ORQCvofAdPzMczfnfA";
	private static final Log LOG = LogFactory.getLog(LeadGenesisWriter.class);
    /*
    -- DB changes for this client:
    insert into advertisers (name, created_at) values('LeadGenesis', now());
    insert into advertiser_writers (lead_type_id,advertiser_id,class_name,created_at) values( -1 , (select id from advertisers where name ='LeadGenesis'), 'com.pony.advertiser.writers.solar.LeadGenesisWriter', now());

    insert into orders (code, advertiser_id, lead_type_id, source_id, target_url, status, created_at) values('LeadGenesis:Solar', (select id from advertisers where name ='LeadGenesis'), -1, 'test=1;camp_id=3433;camp_key=vnf46GBEtIw', 'https://api.leadgenesis.info/v1/leads/', 1, now());
    insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('LeadGenesis:SolarFullForm-Link', (select id from publisher_lists where ext_list_id = 'solar_full_form'), (select id from orders where code = 'LeadGenesis:Solar'), 1, now());
     */
	protected ArrivalModel arrivalModel;

    @Inject public LeadGenesisWriter(ArrivalModel arrivalModel) {
        super();
        this.arrivalModel = arrivalModel;
    }

    @Override
    public boolean supportsPhase(PonyPhase phase) {
        return PonyPhase.PING == phase || PonyPhase.POST == phase;
    }

    @Override
    public Disposition ping(Long leadMatchId, PingContext pingContext, RoutingCandidate candidate) {
        // Ping API key : BK9ORQCvofAdPzMczfnfA
        // http://api.leadgenesis.info/v1/coverage/
        // example: https://api.leadgenesis.info/v1/coverage/?api_key=i5z06avo2g&vertical_id=92&homeowner=YES&zip_code=90210&electric_provider=Ambit+Energy&electric_bill=$201-300

        try {


            final String apiKey = API_KEY;

            String url = URL;

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("vertical_id", VERTICAL_ID));
            params.add(new BasicNameValuePair("api_key", apiKey));

            Lead lead = pingContext.getLead();

            final String ownership = "OWN".equals(lead.getAttributeValue("property_ownership")) ? "YES" : "NO";
            params.add(new BasicNameValuePair("homeowner", ownership));
            params.add(new BasicNameValuePair("zip_code", lead.getAttributeValue("zip")));
            params.add(new BasicNameValuePair("electric_provider", lead.getAttributeValue("electric_company")));
            params.add(new BasicNameValuePair("electric_bill", lead.getAttributeValue("electric_bill")));

            for(NameValuePair pair: params){
                if (pair.getValue() == null) {
                    return Disposition.create(PonyPhase.PING, Disposition.Status.NO_COVERAGE);
                }
            }

            // log the sent message
            Long msgId = logPingMessage(leadMatchId, url, params);

            String[] lines = httpPostWithStringResponse(url, params);
            /* response:
                {"coverage":true}
             */
            if (lines != null && lines.length > 0) {
                logResponseMessage(msgId, lines);

                for (String line : lines) {
                    if (line.contains("coverage") && line.contains("true")) {
                        return Disposition.create(PonyPhase.PING, Disposition.Status.ACCEPTED);
                    }
                }
            }
        }
        catch (IOException e) {
            LOG.error(e);
        }

        return Disposition.create(PonyPhase.PING, Disposition.Status.NO_COVERAGE);
    }

    /**
     * request a price for this lead's zip code, using the 'lg_exclusive_pricing' table.
     * Note: to poulate the table from the provided csv file, do the following steps:
     * <p/>
     * in excel: save as tab delimited, then vi: :%s/<Ctrl-V><Ctrl-M>/\r/g
     * Where <Ctrl-V><Ctrl-M> means type Ctrl+V then Ctrl+M.
     * save file and import:
     * mysql -u root -p pony_leads -e "load data local infile '/home/ec2-user/LG_Rev_Share_Zip_List_Wk25_06-20-16.txt' into table lg_exclusive_pricing fields terminated by '\t' optionally enclosed by '\"' ignore 1 lines (state, county, zip_code, highest_rpt_100, highest_rpt_150, county_rpm, power_adj, rpt_adjusted) set created_at=now()"
     * <p/>
     * then make sure we lpad the zip codes if need be:
     * -- pad the zip code with leading zeros to length=5
     * update lg_exclusive_pricing set zip_code = LPAD(zip_code, 5, '0');
     *
     * @param phase
     * @param validationResponse
     * @param candidate
     * @param lead
     * @param leadMatchId
     * @return
     */
    public Disposition requestPrice(PonyPhase phase, ValidationResponse validationResponse, RoutingCandidate candidate, Lead lead, Long leadMatchId) {

        // read pricing for this lead (check both levels )
        String zip = lead.getAttributeValue("zip");
//        String state = lead.getAttributeValue("state");

//        System.out.println("LGWriter: looking up price for " + zip + "; " + state);
        LOG.info("LGWriter: looking up price for " + zip);

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = Model.connectX();
//            stmt = con.prepareStatement("select rpt_adjusted price from lg_exclusive_pricing where zip_code = ? and state=?");
            stmt = con.prepareStatement("select rpt_adjusted price, state from lg_exclusive_pricing where zip_code = ?");
            stmt.setString(1, zip);
//            stmt.setString(2, state);
            ResultSet rs = stmt.executeQuery();
            if (rs.first()) {
                final BigDecimal price = rs.getBigDecimal("price");
//                logPhaseMessage(leadMatchId, PonyPhase.REQUEST_PRICE, String.format("table lookup price=%.2f", price.doubleValue()));
                String comment = String.format("table lookup price=%.2f", price.doubleValue());
                final Disposition disposition = Disposition.create(phase, Disposition.Status.ACCEPTED, candidate.getIo().isExclusive(), price, comment);
                disposition.setState(rs.getString("state"));
                return disposition;
            }
        }
        catch (SQLException e) {
            LOG.error("Exception writing to Lead Genesis", e);
        }
        catch (NamingException e) {
            LOG.error("Exception writing to Lead Genesis", e);
        }
        finally {
            Model.close(stmt);
            Model.close(con);
        }

        return Disposition.create(phase, Disposition.Status.REJECTED, candidate.getIo().isExclusive(), candidate.getIo().getVpl());
    }

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {
        // https://api.leadgenesis.info/v1/leads/
//        camp_id=3433
//        camp_key=vnf46GBEtIw
        try {

            Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());

            Lead lead = publisherContext.getLead();

            String[] requiredFields = new String[]{"last_name", "city", "state", "phone_home", "electric_company", "electric_bill"};
            for (String required : requiredFields) {
                if (lead.getAttributeValue(required) == null) {
                    // missing required field
                    return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "missing required field [" + required + "]");
                }
            }

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            String url = candidate.getIo().getTargetUrl();

            // test=1;camp_id=3433;camp_key=vnf46GBEtIw
            if (map.containsKey("test") && map.get("test").equals("1")) {
                params.add(new BasicNameValuePair("test", "1"));
            }

            String campId = DEFAULT_CAMPID;
            if (map.containsKey("camp_id")) {
                campId = map.get("camp_id");
            }

            String campKey = DEFAULT_CAMPKEY;
            if (map.containsKey("camp_key")) {
                campKey = map.get("camp_key");
            }

            params.add(new BasicNameValuePair("camp_id", campId));
            params.add(new BasicNameValuePair("camp_key", campKey));


            Disposition pingData = candidate.getDisposition();
            if (pingData != null && pingData.getExternalId() != null) {
                params.add(new BasicNameValuePair("ping_id", pingData.getExternalId()));
            }

            Arrival arrival = publisherContext.getArrival();
            UserProfile up = publisherContext.getUserProfile();

            if (up != null) {
                params.add(new BasicNameValuePair("email_address", up.getEmail()));
            }
            else {
                params.add(new BasicNameValuePair("email_address", lead.getAttributeValue("email")));
            }

            String ip = lead.getAttributeValue("ip");
            if (ip == null) {
                ip = lead.getAttributeValue("ip_address");
                if (ip == null) {
                    ip = arrival.getIpAddress();
                }
            }
            params.add(new BasicNameValuePair("ip_address", ip));

            params.add(new BasicNameValuePair("first_name", lead.getAttributeValue("first_name")));
            params.add(new BasicNameValuePair("last_name", lead.getAttributeValue("last_name")));
            params.add(new BasicNameValuePair("phone_home", lead.getAttributeValue("phone_home")));
            params.add(new BasicNameValuePair("street_address", lead.getAttributeValue("street")));
            params.add(new BasicNameValuePair("zip_code", lead.getAttributeValue("zip")));
            // https://leadgenesis.info/providers/json.php
            params.add(new BasicNameValuePair("electric_provider", lead.getAttributeValue("electric_company")));
            params.add(new BasicNameValuePair("electric_bill_monthly", lead.getAttributeValue("electric_bill")));

            //homeowner: YES NO COMMERCIAL
            final String ownership = "OWN".equals(lead.getAttributeValue("property_ownership")) ? "YES" : "NO";
            params.add(new BasicNameValuePair("homeowner", ownership));

            //source: domain
            params.add(new BasicNameValuePair("source", publisherContext.getPublisher().getName()));

            // TODO: roof_shade: No Shade A Little Shade A Lot Of Shade Uncertain
            params.add(new BasicNameValuePair("roof_shade", "Uncertain")); // default

            Long msgId = logPostMessage(leadMatchId, url, params);

            // allow to get to here for test, without actually sending the lead out!
            if (map.containsKey("is_test") || ("Mickey".equalsIgnoreCase(lead.getAttributeValue("first_name")) && "Mouse".equalsIgnoreCase(lead.getAttributeValue("last_name")))) {
                LOG.info("LG Solar: bailing test call; d=" + candidate.getDisposition());
                return Disposition.create(PonyPhase.POST, Disposition.Status.TEST_DETECTED, "test call");
            }

            // optin_cert:  example = https://cert.trustedform.com/d23d06cc752939c07b3c04dbda524a6cec46c1a0
            //          Download Active Prospect’s Trusted Form script from
            //          http://activeprospect.com/products/trustedform/script/
            String xxTrustedFormCertUrl = lead.getAttributeValue("xxTrustedFormCertUrl");
            if (xxTrustedFormCertUrl == null) {
                // can't send without a trusted form cert url (see http://trustedform.com/)
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "No xxTrustedFormCertUrl provided");
            }
            params.add(new BasicNameValuePair("optin_cert", xxTrustedFormCertUrl));

            // leadid_token
            final String leadidToken = lead.getAttributeValue("leadid_token");
            if (leadidToken == null) {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "No leadid_token provided");
            }
            params.add(new BasicNameValuePair("leadid_token", leadidToken));

            Document doc = httpPostWithXmlResponse(url, params);
            if (doc != null) {

                if (msgId != null) {
                    // record the raw response
                    logResponseMessage(msgId, serialize(doc));
                }

                // Create a XPathFactory
                XPathFactory xFactory = XPathFactory.newInstance();

                // Create a XPath object
                XPath xpath = xFactory.newXPath();

                // Compile the XPath expression

                XPathExpression expr = xpath.compile("//result/msg/text()");
                String result = (String) expr.evaluate(doc, XPathConstants.STRING);

                expr = xpath.compile("/result/leadid/text()");
                String externalLeadId = (String) expr.evaluate(doc, XPathConstants.STRING);

                expr = xpath.compile("/result/price/text()");
                String price = (String) expr.evaluate(doc, XPathConstants.STRING);

                BigDecimal p = null;
                if (price != null) {
                    price = price.replace("$", "");
                    try {
                        p = new BigDecimal(price);
                    }
                    catch (Throwable e) {
                        // ignore
                    }
                }

                if ("success".equalsIgnoreCase(result.trim())) {
                    // what's the price?
                    Disposition priceDisposition = candidate.getResponseDisposition(PonyPhase.REQUEST_PRICE);
                    if (priceDisposition != null && priceDisposition.getPrice() != null) {
                        if (p == null) {
                            p = priceDisposition.getPrice();
                        }
                        else if (priceDisposition.getPrice().doubleValue() != p.doubleValue()) {
                            // the mapping shows a better price then the one they gave us at post time. Now what?
                            LOG.warn(String.format("WARNING: price from post is different from the table lookup! post=%.2f; lookup=%.2f", p.doubleValue(), priceDisposition.getPrice().doubleValue()));
                        }
                    }

                    return Disposition.createPost(Disposition.Status.ACCEPTED, null, externalLeadId, p, result);
                }
                else {
                    expr = xpath.compile("//result/errors/error/text()");
                    String reason = (String) expr.evaluate(doc, XPathConstants.STRING);
                    return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, reason);
                }
            }
        /*
        <result>
            <code>0</code>
            <msg>success</msg>
            <leadid>AB34567F</leadid>
            <price>$1.00</price>
        </result>

        <result>
            <code>1</code>
            <msg>error</msg>
            <errors>
                <error>descriptive error message goes here</error>
            </errors>
        </result>
        */
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

        return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED);
    }


    public static void main(String[] args) {
        final String url = "https://login.leadgenesis.com/affiliates/api/8/reports.asmx/Conversions?api_key=BK9ORQCvofAdPzMczfnfA&affiliate_id=1208&offer_id=0&currency_id=0&conversion_type=all&exclude_bot_traffic=FALSE&start_at_row=0&row_limit=0&disposition_type=";
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        //params.add(new BasicNameValuePair("start_date", get30DaysBack()));
        //params.add(new BasicNameValuePair("end_date", getCurrentDate()));

        try {
            Document response = httpGetWithXmlResponse(url, params);
            if (response != null) {

                // get externalId and price

                // Create a XPathFactory
                XPathFactory xFactory = XPathFactory.newInstance();

                // Create a XPath object
                XPath xpath = xFactory.newXPath();

                // Compile the XPath expression

                XPathExpression expr = xpath.compile("//conversions/conversion");
//                String result = (String) expr.evaluate(doc, XPathConstants.STRING);
//
//                expr = xpath.compile("//CardHolderProcessingResponse/Messaging/text()");
//                String message = (String) expr.evaluate(doc, XPathConstants.STRING);
                NodeList conversions = (NodeList) expr.evaluate(response, XPathConstants.NODESET);

                if (conversions != null && conversions.getLength() > 0) {
                    XPathExpression priceExpr = xpath.compile("./price/text()");
                    XPathExpression conversionExpr = xpath.compile("./conversion_id/text()");
                    XPathExpression dispositionExpr = xpath.compile("./disposition/text()");
                    XPathExpression offerIdExpr = xpath.compile("./offer_id/text()");
                    XPathExpression offerNameExpr = xpath.compile("./offer_name/text()");

                    for (int i = 0; i < conversions.getLength(); i++) {
                        String conversionId = (String) conversionExpr.evaluate(conversions.item(i), XPathConstants.STRING);
                        String price = (String) priceExpr.evaluate(conversions.item(i), XPathConstants.STRING);
                        String disposition = (String) dispositionExpr.evaluate(conversions.item(i), XPathConstants.STRING);
                        String offerId = (String) offerIdExpr.evaluate(conversions.item(i), XPathConstants.STRING);
                        String offerName = (String) offerNameExpr.evaluate(conversions.item(i), XPathConstants.STRING);

                        String comment = String.format("offer_id=%s; offer_name=%s; disposition=%s", offerId, offerName, disposition);
                        LOG.debug(comment);

                        if (conversionId != null && price != null) {

                            LOG.info(String.format("\r\n*** advertiser disposition: conversionId=%s ; price=%s", conversionId, price));

                            BigDecimal conversionPrice = new BigDecimal(price.trim());
                            // look up the match and if found update the price, and create a disposition
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            LOG.error(e);
        }
        catch (SAXException e) {
            LOG.error(e);
        }
        catch (ParserConfigurationException e) {
            LOG.error(e);
        }
        catch (XPathExpressionException e) {
            LOG.error(e);
        }
    }
}
