package com.pony.advertiser.dispositioner;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.pony.advertiser.Advertiser;
import com.pony.advertiser.AdvertiserService;
import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.factory.AdvertiserFactory;
import com.pony.core.PonyPhase;
import com.pony.lead.Arrival;
import com.pony.lead.LeadMatch;
import com.pony.models.AdvertiserModel;
import com.pony.models.ArrivalModel;
import com.pony.models.IoModel;
import com.pony.models.LeadMatchModel;
import com.pony.tools.MathTool;

public class LeadGenesisDispositioner extends Dispositioner {
    private static final Log LOG = LogFactory.getLog(LeadGenesisDispositioner.class);
    protected AdvertiserService service;
    protected ArrivalModel arrivalModel;

    @Inject
    @Named("LeadGenesisDispositioner.timezone")
    String timezone;
    
    @Inject
    Map<String, AdvertiserFactory> factories;

    @Inject
    LeadGenesisDispositioner(AdvertiserService service, ArrivalModel arrivalModel) {
        this.service = service;
        this.arrivalModel = arrivalModel;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.pony.advertiser.writers.solar.Dispositioner#checkForDispositions(com.
     * pony.advertiser.Advertiser)
     */
    @Override
    public void checkForDispositions(Map<String, String[]> parameters) throws Exception {
        Advertiser advertiser = AdvertiserModel.find(20L);

        final String url = "http://login.leadgenesis.com/affiliates/api/8/reports.asmx/Conversions?api_key=BK9ORQCvofAdPzMczfnfA&affiliate_id=1208&offer_id=0&currency_id=0&conversion_type=all&exclude_bot_traffic=FALSE&start_at_row=0&row_limit=0&disposition_type=";
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        TimePeriod beginToEnd = getTimePeriod(parameters);

        params.add(new BasicNameValuePair("start_date", beginToEnd.begin));
        params.add(new BasicNameValuePair("end_date", beginToEnd.end));

        List<LeadMatch> matches = LeadMatchModel.findLeadpostsInRange(beginToEnd.begin, beginToEnd.end, advertiser);
        Set<Long> leadMatchIds = new HashSet<>();
        for(LeadMatch match: matches){
            leadMatchIds.add(match.getId());
        }
        LOG.info(String.format("[LG Check Disposition]: Begin check between %s and %s", beginToEnd.begin, beginToEnd.end));
        try {
            Document response = AdvertiserWriter.httpGetWithXmlResponse(url, params);
            if (response != null) {

                // get externalId and price

                // Create a XPathFactory
                XPathFactory xFactory = XPathFactory.newInstance();

                // Create a XPath object
                XPath xpath = xFactory.newXPath();

                // Compile the XPath expression

                XPathExpression expr = xpath.compile("//conversions/conversion");
                // String result = (String) expr.evaluate(doc,
                // XPathConstants.STRING);
                //
                // expr =
                // xpath.compile("//CardHolderProcessingResponse/Messaging/text()");
                // String message = (String) expr.evaluate(doc,
                // XPathConstants.STRING);
                NodeList conversions = (NodeList) expr.evaluate(response, XPathConstants.NODESET);

                if (conversions != null && conversions.getLength() > 0) {
                    XPathExpression priceExpr = xpath.compile("./price/text()");
                    XPathExpression conversionExpr = xpath.compile("./conversion_id/text()");
                    XPathExpression dispositionExpr = xpath.compile("./disposition/text()");
                    XPathExpression offerIdExpr = xpath.compile("./offer_id/text()");
                    XPathExpression offerNameExpr = xpath.compile("./offer_name/text()");

                    for (int i = 0; i < conversions.getLength(); i++) {
                        String conversionId = (String) conversionExpr.evaluate(conversions.item(i),
                                XPathConstants.STRING);
                        String price = (String) priceExpr.evaluate(conversions.item(i), XPathConstants.STRING);
                        String disposition = (String) dispositionExpr.evaluate(conversions.item(i),
                                XPathConstants.STRING);
                        String offerId = (String) offerIdExpr.evaluate(conversions.item(i), XPathConstants.STRING);
                        String offerName = (String) offerNameExpr.evaluate(conversions.item(i), XPathConstants.STRING);
                        String comment = String.format("offer_id=%s; offer_name=%s; disposition=%s", offerId, offerName,
                                disposition);
                        if (conversionId != null && price != null) {

                            LOG.info(String.format("\r\n*** advertiser disposition: conversionId=%s ; price=%s",
                                    conversionId, price));

                            BigDecimal conversionPrice = MathTool.getBigDecimalPrice(price);

                            // look up the match and if found update the price,
                            // and create a disposition
                            LeadMatch leadMatch = LeadMatchModel.findByExternalId(advertiser, conversionId, this.factories.get(advertiser.getName()).getExternalIdMatcher());

                            if(leadMatch != null) leadMatchIds.remove(leadMatch.getId());
                            // If there is a different price
                            if (leadMatch != null && !leadMatch.getPrice().equals(conversionPrice)) {
                                Disposition d = Disposition.create(PonyPhase.ADVERTISER_DISPOSITION,
                                        Disposition.Status.ACCEPTED, false, conversionPrice, comment);
                                LeadMatchModel.persistDisposition(advertiser.getId(), leadMatch.getId(), d,
                                        PonyPhase.ADVERTISER_DISPOSITION);
                                IoModel ioModel = IoModel.findByLeadMatchId(leadMatch.getId());
                                if (ioModel != null && ioModel.getIo() != null
                                        && ioModel.getIo().getPixelId() != null) {
                                    Arrival arrival = arrivalModel.findByLeadMatchId(leadMatch.getId());

                                    Long pixelId = ioModel.getIo().getPixelId();
                                    String arrivalSourceId = arrival.getArrivalSourceId();
                                    String externalArrivalId = arrival.getExternalId();
                                    String param = null;
                                    //service.fireLynxDisposition(leadMatch);
                                }
                            }
                        }
                    }
                }
            }
            LOG.info("[LG Check Disposition] Need to populate 0 price dispositions");
            for(Long leadMatchId: leadMatchIds){

                try{
                Disposition d = Disposition.create(PonyPhase.ADVERTISER_DISPOSITION, Disposition.Status.ACCEPTED,
                        false, BigDecimal.ZERO, null);
                LeadMatchModel.persistDisposition(advertiser.getId(), leadMatchId, d,
                        PonyPhase.ADVERTISER_DISPOSITION);
                IoModel ioModel = IoModel.findByLeadMatchId(leadMatchId);
                if (ioModel != null && ioModel.getIo() != null
                        && ioModel.getIo().getPixelId() != null) {
                    Arrival arrival = arrivalModel.findByLeadMatchId(leadMatchId);

                    Long pixelId = ioModel.getIo().getPixelId();
                    String arrivalSourceId = arrival.getArrivalSourceId();
                    String externalArrivalId = arrival.getExternalId();
                    String param = null;
                    LeadMatch leadMatch = LeadMatchModel.find(leadMatchId);
                    //service.fireLynxDisposition(leadMatch);
                }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            LOG.info(String.format("[LG Check Disposition]: End check between %s and %s", beginToEnd.begin, beginToEnd.end));
        } catch (IOException | SAXException | ParserConfigurationException | XPathExpressionException | SQLException | NamingException e) {
            LOG.error("Exception while checking dispositions with LeadGenesisDispositioner.", e);
        }

		/*
         * response format: <conversion_response
		 * xmlns:xsd="http://www.w3.org/2001/XMLSchema"
		 * xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		 * xmlns="http://cakemarketing.com/affiliates/api/8/">
		 * <success>true</success> <row_count>21</row_count> <summary>
		 * <price>493.5800</price> <order_total>0</order_total>
		 * <currency_symbol>$</currency_symbol> </summary> <conversions>
		 * <conversion> <conversion_id>74777CA5</conversion_id>
		 * <event_id>2</event_id>
		 * <event_name>Conversion/Install/Lead</event_name>
		 * <conversion_date>2016-06-20T11:19:39.033</conversion_date>
		 * <offer_id>347</offer_id> <offer_name>Solar WebForm H+P Outside
		 * Brand</offer_name> <campaign_id>3433</campaign_id>
		 * <creative_id>1880</creative_id> <subid_1/> <subid_2/> <subid_3/>
		 * <subid_4/> <subid_5/> <price>0.0000</price> <order_total
		 * xsi:nil="true"/> <disposition/> <disposition_type/> <test>true</test>
		 * <currency_symbol>$</currency_symbol> </conversion> <conversion> ...
		 */

    }

    private String get30DaysBack() {
        // TimeZone accountTz = TimeZone.getTimeZone(account.getTimeZone());
        TimeZone localTz = TimeZone.getTimeZone(this.timezone);
        Calendar cal = Calendar.getInstance(localTz);
        cal.add(Calendar.DAY_OF_YEAR, -30);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(localTz);

        return df.format(cal.getTime());
    }

    private String getCurrentDate() {
        // TimeZone accountTz = TimeZone.getTimeZone(account.getTimeZone());
        TimeZone localTz = TimeZone.getTimeZone(this.timezone);
        Calendar cal = Calendar.getInstance(localTz);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(localTz);

        return df.format(cal.getTime());
    }

}
