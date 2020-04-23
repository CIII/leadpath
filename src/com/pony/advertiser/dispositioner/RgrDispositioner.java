package com.pony.advertiser.dispositioner;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
import org.xml.sax.SAXException;

import com.pony.advertiser.Advertiser;
import com.pony.advertiser.AdvertiserService;
import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.core.PonyPhase;
import com.pony.lead.Arrival;
import com.pony.lead.LeadMatch;
import com.pony.models.AdvertiserModel;
import com.pony.models.ArrivalModel;
import com.pony.models.IoModel;
import com.pony.models.LeadMatchModel;
import com.pony.tools.MathTool;

public class RgrDispositioner extends Dispositioner {
    private static final Log LOG = LogFactory.getLog(RgrDispositioner.class);
    private Advertiser rgrAdvertiser;
    private AdvertiserService service;
    private ArrivalModel arrivalModel;

    @Inject
    RgrDispositioner(AdvertiserService service, ArrivalModel arrivalModel) {
        try {
            rgrAdvertiser = AdvertiserModel.findByName("RGR");
        } catch (SQLException | NamingException e) {
            LOG.error("Exception instantiating the RgrDispositioner.", e);
        }
        this.service = service;
        this.arrivalModel = arrivalModel;
    }

    /**
     * Pings RGR with individual lead_matches where there is a sales but no
     * disposition
     *
     * @param parameters Number of days back to check disposition
     */
    @Override
    public void checkForDispositions(Map<String, String[]> parameters) throws Exception {

        TimePeriod beginToEnd = getTimePeriod(parameters);

        LOG.info(String.format("[Rgr Check Disposition]: Begin check between %s and %s", beginToEnd.begin, beginToEnd.end));
        final String url = "http://www.reallygreatrate.com/services/pub_get_rev.php?publisher_id=915";
        List<LeadMatch> matches = LeadMatchModel.findLeadpostsInRange(beginToEnd.begin, beginToEnd.end, rgrAdvertiser);

        try {
            XPathFactory xFactory = XPathFactory.newInstance();
            XPath xpath = xFactory.newXPath();
            XPathExpression revenueExpression = xpath.compile("//revenue");
            XPathExpression statusExpression = xpath.compile("//status");

            for (LeadMatch leadMatch : matches) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("lead_id", leadMatch.getExternalId()));
                try {
                    Document response = AdvertiserWriter.httpGetWithXmlResponse(url, params);
                    LOG.info(String.format("[Rgr Check Disposition]: lead match %s, external id %s, response %s",
                            leadMatch.getId().toString(), leadMatch.getExternalId().toString(), toString(response)));
                    String revenue = (String) revenueExpression.evaluate(response, XPathConstants.STRING);
                    String status = (String) statusExpression.evaluate(response, XPathConstants.STRING);

                    if (status.trim().equalsIgnoreCase("matched") || status.trim().equalsIgnoreCase("credited")) {

                        BigDecimal bdRevenue;
                        try {
                            bdRevenue = MathTool.getBigDecimalPrice(revenue);
                        }catch(NumberFormatException e){
                            //Sometimes RGR passes back matched, and no revenue value
                            bdRevenue = BigDecimal.ZERO;
                        }

                        String comment = String.format("status=%s", status);
                        // If the price is different
                        if (!leadMatch.getPrice().equals(bdRevenue)) {
                            Disposition d = Disposition.create(PonyPhase.ADVERTISER_DISPOSITION,
                                    Disposition.Status.ACCEPTED, false, bdRevenue, comment);
                            LeadMatchModel.persistDisposition(rgrAdvertiser.getId(), leadMatch.getId(), d,
                                    PonyPhase.ADVERTISER_DISPOSITION);
                            LOG.info(String.format(
                                    "[Rgr Check Disposition]: Updated lead match %s " + "with disposition %s",
                                    leadMatch.getId().toString(), d.toString()));
                            IoModel ioModel = IoModel.findByLeadMatchId(leadMatch.getId());
                            if (ioModel != null && ioModel.getIo() != null && ioModel.getIo().getPixelId() != null) {
                                Arrival arrival = arrivalModel.findByLeadMatchId(leadMatch.getId());

                                Long pixelId = ioModel.getIo().getPixelId();
                                String arrivalSourceId = arrival.getArrivalSourceId();
                                String externalArrivalId = arrival.getExternalId();
                                String param = null;
                                //service.fireLynxDisposition(leadMatch);
                            }
                            LOG.info(String.format(
                                    "[Rgr Check Disposition]: Fired sitebrain pixel for " + "rgr lead match %s",
                                    leadMatch.getId().toString()));
                        } else {
                            LOG.info(String.format(
                                    "[Rgr Check Disposition]: Price already updated for lead match %s, " + "price %s",
                                    leadMatch.getId().toString(), bdRevenue.toString()));
                        }
                    } else {
                        // Should update leadmatch with price 0
                        Disposition d = Disposition.create(PonyPhase.ADVERTISER_DISPOSITION,
                                Disposition.Status.REJECTED, false, BigDecimal.ZERO,
                                "External id not matched in RGR database");
                        LeadMatchModel.persistDisposition(rgrAdvertiser.getId(), leadMatch.getId(), d,
                                PonyPhase.ADVERTISER_DISPOSITION);
                        System.out.println(
                                String.format("[Rgr Check Disposition]: Updated lead match %s " + "with disposition %s",
                                        leadMatch.getId().toString(), d.toString()));
                    }
                } catch (IOException | SAXException | ParserConfigurationException | SQLException | NamingException e) {
                    LOG.error("Exception thrown while checking for dispositions with RgrDispositioner", e);
                } catch (Exception e) {
                    LOG.error("Unknown exception thrown while checking for dispositions with RgrDispositioner", e);
                }
            }
        } catch (XPathExpressionException e) {
            LOG.error("XPathExpressionException while preparing the check dispositions with RgrDispositioner.", e);
        }
        LOG.info(String.format("[Rgr Check Disposition]: End check between %s and %s", beginToEnd.begin, beginToEnd.end));

    }

    public static String toString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }
}
