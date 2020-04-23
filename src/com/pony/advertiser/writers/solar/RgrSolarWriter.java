package com.pony.advertiser.writers.solar;

import com.pony.advertiser.*;
import com.pony.core.PonyPhase;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadMatch;
import com.pony.lead.UserProfile;
import com.pony.models.*;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.tools.MathTool;
import com.pony.validation.ValidationResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by martin on 5/29/16.
 */
public class RgrSolarWriter extends AdvertiserWriter {
	private static final Log LOG = LogFactory.getLog(RgrSolarWriter.class);
	
    /*
    Online reporting URL:  www.reallygreatrate.com/publisher
    User name:  Akshay1
    Password:  Solar123

    Publisher_id=915
    Production URL:  https://www.reallygreatrate.com/formdov.php?
    Include "response=Yes" at end of post string for real-time response.

    I think I sent you the posting specs, I'll resend in a few.

    Matt
    Matt Schaub
    310-540-8900
    RGR Marketing
    2041 Rosecrans Avenue Suite 320
    El Segundo, CA 90245
     */

    /*
    -- DB changes for this client:
    insert into advertisers (name, created_at) values('RGR', now());
    insert into advertiser_writers (lead_type_id,advertiser_id,class_name,created_at) values( -1 , (select id from advertisers where name ='RGR'), 'com.pony.advertiser.writers.solar.RgrSolarWriter', now());

    insert into orders (code, advertiser_id, lead_type_id, source_id, target_url, status, created_at) values('RGR:Solar', (select id from advertisers where name ='RGR'), -1, 'publisher_id=915', 'https://www.reallygreatrate.com/formdov.php', 1, now());
    insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('RGR:SolarFullForm-Link', (select id from publisher_lists where ext_list_id = 'solar_full_form'), (select id from orders where code = 'RGR:Solar'), 1, now());

     */

    public Disposition requestPrice(PonyPhase phase, ValidationResponse validationResponse, RoutingCandidate candidate, Lead lead, Long leadMatchId) {

//        return Disposition.create(phase, Disposition.Status.ACCEPTED, candidate.getIo().isExclusive(), candidate.getIo().getVpl());

        // read pricing for this lead (check both levels )
        // select rpt_adjusted from lg_exclusive_pricing where zip_code = '02421' and state='MA';
        // rgr_exclusive_pricing , rgr_semi_exclusive_pricing
        //
        // select price from rgr_exclusive_pricing where zip_code =? and state = ?
        // todo: make the factor configurable via the source_id
        double factor = 0.7d;

        String zip = lead.getAttributeValue("zip");
//        String state = lead.getAttributeValue("state");

//        System.out.println("RGRWriter: looking up price for " + zip + "; " + state);
        LOG.info("RGRWriter: looking up price for " + zip);

        Connection con = null;
        PreparedStatement stmt = null;

        Disposition exclusiveDisposition = null, semiDisposition = null;

        try {
            con = Model.connectX();
//            stmt = con.prepareStatement("select price from rgr_exclusive_pricing where zip_code=? and state=?");
            stmt = con.prepareStatement("select price, state from rgr_exclusive_pricing where zip_code=?");
            stmt.setString(1, zip);
//            stmt.setString(2, state);
            ResultSet rs = stmt.executeQuery();
            if (rs.first()) {
                final BigDecimal price = rs.getBigDecimal("price");
                final BigDecimal priceF = price.multiply(new BigDecimal(factor)).setScale(2, RoundingMode.HALF_DOWN);
//                logPhaseMessage(leadMatchId, PonyPhase.REQUEST_PRICE, String.format("factor=%f; table lookup price=%.2f; final price=%.2f", factor, price.doubleValue(), priceF.doubleValue()));
                String comment = String.format("exclusive: factor=%f; table lookup price=%.2f; final price=%.2f", factor, price.doubleValue(), priceF.doubleValue());
                exclusiveDisposition = Disposition.create(phase, Disposition.Status.ACCEPTED, candidate.getIo().isExclusive(), priceF, comment);
                exclusiveDisposition.setState(rs.getString("state"));
            }
            Model.close(stmt);

            // -- ========
            // check semi
//            stmt = con.prepareStatement("select price from rgr_semi_exclusive_pricing where zip_code=? and state=?");
            stmt = con.prepareStatement("select price, state from rgr_semi_exclusive_pricing where zip_code=?");
            stmt.setString(1, zip);
//            stmt.setString(2, state);
            rs = stmt.executeQuery();
            if (rs.first()) {
                final BigDecimal price = rs.getBigDecimal("price");
                final BigDecimal priceF = price.multiply(new BigDecimal(factor)).setScale(2, RoundingMode.HALF_DOWN);
//                logPhaseMessage(leadMatchId, PonyPhase.REQUEST_PRICE, String.format("factor=%f; table lookup price=%.2f; final price=%.2f", factor, price.doubleValue(), priceF.doubleValue()));
                String comment = String.format("semi: factor=%f; table lookup price=%.2f; final price=%.2f", factor, price.doubleValue(), priceF.doubleValue());
                semiDisposition = Disposition.create(phase, Disposition.Status.ACCEPTED, candidate.getIo().isExclusive(), priceF, comment);
                semiDisposition.setState(rs.getString("state"));
            }
        } catch (SQLException e) {
            LOG.error(e);
        } catch (NamingException e) {
            LOG.error(e);
        } finally {
            Model.close(stmt);
            Model.close(con);
        }

        /*
        -- Greg 2016-06-25
        In the case of users with utility bill values > $150,
        we should compare LG highestRPT150 against 0.7*[semi-exclusive],
        fall back to exclusive if null.
        I'd suggest we compare the max the max value but based on my simulation of the rules we're already biasing towards RGR in a lot of markets.
        I don't want to tip the scales any further.
        For < $150, only compare the [RGR exclusive]*0.7 to LG RPTMadj.
         */

        // <option value="$51-100">$51-100</option>
        String eb = lead.getAttributeValue("electric_bill");
        if (eb != null) {
            eb = cleanElectricBillString(eb);
        }

        if (eb != null) {
            double bill = Double.valueOf(eb);
            if (bill > 150d) {
                if (semiDisposition != null) {
                    return semiDisposition;
                }
                return exclusiveDisposition;
            } else {
                if (exclusiveDisposition != null) {
                    return exclusiveDisposition;
                }
                return semiDisposition;
            }
        } else {
            // no bill value? (just in case)
            if (semiDisposition == null && exclusiveDisposition != null) {
                return exclusiveDisposition;
            } else if (exclusiveDisposition == null && semiDisposition != null) {
                return semiDisposition;
            } else if (semiDisposition != null && exclusiveDisposition != null) {
                // if we have both, use the later value
                if (semiDisposition.getPrice().doubleValue() > exclusiveDisposition.getPrice().doubleValue()) {
                    return semiDisposition;
                }
                return exclusiveDisposition;
            }
        }

        return Disposition.create(phase, Disposition.Status.REJECTED, candidate.getIo().isExclusive(), candidate.getIo().getVpl());
    }

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {

        /*
        Publisher_id=915
        Production URL:  https://www.reallygreatrate.com/formdov.php?
        Include "response=Yes" at end of post string for real-time response.
         */

        Map<String, String> sourceIdMap = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());

        Lead lead = publisherContext.getLead();

        // look up utility name from mapping (LG -> RGR; site used LG names)
//        String utilityName = "Other"; // default
        String utilityName = lookupUtility(lead);

        // get the posted state; if no state is posted, check if we have the state in the price disposition coming from the zip code based price lookup
        String state = lead.getAttributeValue("state");
        if (state == null) {
            Disposition priceDisposition = candidate.getResponseDisposition(PonyPhase.REQUEST_PRICE);
            if (priceDisposition != null) {
                state = priceDisposition.getState();
            }
        }

        if (state == null) {
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "missing required field [state]");
        }

        String[] requiredFields = new String[]{"last_name", "city", "phone_home", "electric_company", "electric_bill"};
        for (String required : requiredFields) {
            if (lead.getAttributeValue(required) == null) {
                // missing required field
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "missing required field [" + required + "]");
            }
        }

//        String campaignId = map.get("campid");

//        // test: send email
//        if (campaignId == null && map.get("test") != null) {
//            EmailWriter writer = new EmailWriter();
//            return writer.post(leadMatchId, publisherContext, validationResponse, candidate);
//        }

        // post url : http://receiver.ceeleads.info/leads/post2
        String url = candidate.getIo().getTargetUrl();

        Arrival arrival = publisherContext.getArrival();
        UserProfile up = publisherContext.getUserProfile();
//        String code = candidate.getIo().getCode();

        List<NameValuePair> formParams = new ArrayList<NameValuePair>();

//        Include â€œresponse=Yesâ€� at end of post string for real-time response.
        formParams.add(new BasicNameValuePair("response", "Yes"));

        // write out the tokenized sourceId
        for (Map.Entry<String, String> entry : sourceIdMap.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        if (!sourceIdMap.containsKey("publisher_id")) {
            formParams.add(new BasicNameValuePair("publisher_id", "915")); // default
        }

        formParams.add(new BasicNameValuePair("type", "SOLAR"));

        formParams.add(new BasicNameValuePair("first_name", lead.getAttributeValue("first_name")));

        formParams.add(new BasicNameValuePair("last_name", lead.getAttributeValue("last_name")));

        formParams.add(new BasicNameValuePair("city", lead.getAttributeValue("city")));

        formParams.add(new BasicNameValuePair("state", state));

        formParams.add(new BasicNameValuePair("zip", lead.getAttributeValue("zip")));

        formParams.add(new BasicNameValuePair("street_address", lead.getAttributeValue("street")));

        formParams.add(new BasicNameValuePair("home_phone", lead.getAttributeValue("phone_home")));

        formParams.add(new BasicNameValuePair("email_address", up.getEmail()));

        formParams.add(new BasicNameValuePair("upload_type", "RGR"));

        final TimeZone tz = TimeZone.getTimeZone("America/New_York");
        Calendar cal = Calendar.getInstance(tz);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(tz);
        formParams.add(new BasicNameValuePair("lead_date", df.format(cal.getTime())));

        // Roof Shade: No Shade, A little shade, Uncertain, A lot of Shade
        // since we don't collect this: default to Uncertain
        formParams.add(new BasicNameValuePair("field_1", "Uncertain"));

        // electricity provider
        formParams.add(new BasicNameValuePair("field_2", utilityName));

        // home owner (Y,N)
        String owner = "OWN".equals(lead.getAttributeValue("property_ownership")) ? "Y" : "N";
        formParams.add(new BasicNameValuePair("field_3", owner));

        // todo: Power Bill (no $ or commas)
        // we post the interval with $ sign, like: <option value="$51-100">$51-100</option>
        String eb = lead.getAttributeValue("electric_bill");
        if (eb != null) {
            eb = cleanElectricBillString(eb);
            formParams.add(new BasicNameValuePair("field_4", eb));
        }

        // publisher lead id
        formParams.add(new BasicNameValuePair("field_10", leadMatchId.toString()));

        String ip = lead.getAttributeValue("ip");
        if (ip == null) {
            ip = lead.getAttributeValue("ip_address");
            if (ip == null) {
                ip = arrival.getIpAddress();
            }
        }
        formParams.add(new BasicNameValuePair("ipaddress", ip));

        Long msgId = logPostMessage(leadMatchId, url, formParams);

        // allow to get to here for test, without actually sending the lead out!
        if (sourceIdMap.containsKey("is_test") || ("Mickey".equalsIgnoreCase(lead.getAttributeValue("first_name")) && "Mouse".equalsIgnoreCase(lead.getAttributeValue("last_name")))) {
            LOG.info("RGRSolarWriter: bailing test call with utility=" + utilityName + "; d=" + candidate.getDisposition());
            return Disposition.create(PonyPhase.POST, Disposition.Status.TEST_DETECTED, "test call");
        }

        // trusted form token
        String xxTrustedFormCertUrl = lead.getAttributeValue("xxTrustedFormCertUrl");
        if (xxTrustedFormCertUrl == null) {
            // can't send without a trusted form cert url (see http://trustedform.com/)
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "No xxTrustedFormCertUrl provided");
        }
        formParams.add(new BasicNameValuePair("xxTrustedFormCertUrl", xxTrustedFormCertUrl));
        formParams.add(new BasicNameValuePair("field_5", xxTrustedFormCertUrl));

        // lead id token
        final String leadidToken = lead.getAttributeValue("leadid_token");
        if (leadidToken == null) {
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "No leadid_token provided");
        }
        formParams.add(new BasicNameValuePair("field_13", leadidToken));

        try {

  /*
<html><body>status=success<br>id=1234567</body></html>

The format of a rejected lead will be:
<html><body>status=Failed<br>Reason=Duplicate from publisher<br>id=1234567</body></html>

The format of a flagged lead will be:
<html><body>status=Flagged<br>Reason=Address in correct<br>id=1234567</body></html>
   */

            String[] lines = httpPostWithStringResponse(url, formParams);
            if (lines == null || lines.length == 0) {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to parse post response");
            }

            if (msgId != null) {
                // record the raw response
                logResponseMessage(msgId, lines);
            }

            // example response: {"leadId":"0968B3CC","status":"POST_VALID"}
            //                   {"leadId":null,"status":"INVALID_CAMPAIGN"}
            //                   {"leadId":null,"status":"POST_REJECT"}
            for (String line : lines) {
                if (line.contains("status=Failed")) {
                    return Disposition.createPost(Disposition.Status.REJECTED, null, null, line);
                } else if (line.contains("status=Flagged")) {
                    return Disposition.createPost(Disposition.Status.REJECTED, null, null, line);
                } else if (line.contains("status=success")) {
                    // <html><body>status=success<br>id=1234567</body></html>
//                    String response = line.replace("<html><body>status=success<br>", "").replace("</body></html>", "");
                    String response = line.replace("</html>", "").replace("<html>", "").replace("</body>", "").replace("<body>", "").replace("<br>", "").replace("status=success", "");
                    String[] tokens = response.split("=");
                    String externalLeadId = null;
                    if (tokens.length == 2 && "id".equals(tokens[0])) {
                        externalLeadId = tokens[1];
                    }

                    // what's the price?
                    BigDecimal price = null;
                    Disposition priceDisposition = candidate.getResponseDisposition(PonyPhase.REQUEST_PRICE);
                    if (priceDisposition != null && priceDisposition.getPrice() != null) {
                        price = priceDisposition.getPrice();
                    }

                    return Disposition.createPost(Disposition.Status.ACCEPTED, null, externalLeadId, price, null);
                }
            }
        } catch (IOException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }

        return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "response mismatch");
    }

	private String cleanElectricBillString(String eb) {
		String[] tokens = eb.split("-");
		if (tokens.length == 2) {
		    eb = tokens[1]; // pick the end of the interval
		}
		eb = eb.replace("$", "");
		eb = eb.replace("+", "");
		eb = eb.replace(",", "");
		return eb;
	}

    private String lookupUtility(Lead lead) {
        String state = lead.getAttributeValue("state");
        String lgName = lead.getAttributeValue("electric_company");

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = Model.connectX();
            stmt = con.prepareStatement("select rgr_name from utility_lookup where state = ? and lg_name =?");
            stmt.setString(1, state);
            stmt.setString(2, lgName);
            ResultSet rs = stmt.executeQuery();
            if (rs.first()) {
                return rs.getString("rgr_name");
            }
        } catch (SQLException e) {
            LOG.error(e);
        } catch (NamingException e) {
            LOG.error(e);
        } finally {
            Model.close(stmt);
            Model.close(con);
        }

        return "Other";
    }

    public static void main(String[] args) {

        final BigDecimal price = new BigDecimal("57.00");
        final double factor = 0.7d;

        LOG.info(price.multiply(new BigDecimal(factor), new MathContext(2, RoundingMode.HALF_DOWN)));

        BigDecimal n = price.multiply(new BigDecimal(factor)).setScale(2, RoundingMode.HALF_DOWN);
        LOG.info(n);
    }
}
