package com.pony.advertiser.writers;

import com.pony.advertiser.AdvertiserService;
import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.callcenter.Broker;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.models.AdvertiserModel;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import javax.naming.NamingException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * receive a lead from AMN and store it for the cc crm
 * <p/>
 * post to our Five9
 * <p/>
 * order.source_id=pixel_id=12345;crm_url=https://www.tapnexus.com
 * <p/>
 * test post format: https://www.tapnexus.com/post?listid=ccc&domtok=amn-123&ref=amn&first_name=Kamel&last_name=B&email=kamel@bexil.com&zip=01880&city=Wakefield&state=MA&credit_grade=good&ltv=90
 * <p/>
 * ArbVentures 2013.
 * User: martin
 * Date: 3/7/14
 * Time: 11:53 AM
 */
public class CastleMortgageWriter extends AdvertiserWriter {
	private static final Log LOG = LogFactory.getLog(CastleMortgageWriter.class);

    private static final String CMC_URL = "http://theexpress.amnetcastleexpress.com";

    private static final List<String> MORTGAGE_ATTRIBUTES = new ArrayList<String>();

    static {
        MORTGAGE_ATTRIBUTES.add("product");
        MORTGAGE_ATTRIBUTES.add("loan_amt");
    }

    @Override
    public boolean supportsPhase(PonyPhase phase) {
        return phase == PonyPhase.POST;
    }

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {
        // receive lead and fire 'lead-arrival' pixel to SiteBrain
        // post to our Five9 , and build CRM url to pass to Five9 for call center user to get back to this record

        Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());

        if (map.get(NO_SEND) != null) {
            return Disposition.createPost(Disposition.Status.ACCEPTED, Disposition.DispositionCategory.NO_MATCH, "configured to not send.");
        }

        try {
            String amnJobCode = publisherContext.getLead().getAttributeValue("amn_job_code");
            fireLeadArrivalPixel(leadMatchId, map, amnJobCode);

            if (map.get("NO_FIVE9") != null) {
                return postToEmail(publisherContext, leadMatchId, validationResponse, map, candidate);
            }
            else if (!isInsideCallCenterHours() || isTestLead(publisherContext)) {
                return postToCastle(publisherContext, leadMatchId, candidate);
            }
            else {
                return postToPercipioFive9(publisherContext, leadMatchId, map, candidate);
            }
        }
        catch (IllegalArgumentException e) {
            return Disposition.createPost(Disposition.Status.REJECTED, null, e.getMessage());
        }
        catch (IOException e) {
            return Disposition.createPost(Disposition.Status.REJECTED, null, e.getMessage());
        }
        catch (SQLException e) {
            return Disposition.createPost(Disposition.Status.REJECTED, null, e.getMessage());
        }
        catch (NamingException e) {
            return Disposition.createPost(Disposition.Status.REJECTED, null, e.getMessage());
        }
    }

    private Disposition postToEmail(IPublisherContext publisherContext, Long leadMatchId, ValidationResponse validationResponse, Map<String, String> map, RoutingCandidate candidate) {

        // send email
        final String testEmail = map.get(EmailWriter.TO);
        if (testEmail != null) {
            // fire pixel to signal the test split

            return new EmailWriter().post(leadMatchId, publisherContext, validationResponse, candidate);
        }

        return Disposition.createPost(Disposition.Status.REJECTED, null, null, "email not sent; missing 'to='");
    }

    private boolean isTestLead(IPublisherContext publisherContext) {
        return "test".equals(publisherContext.getLead().getAttributeValue("firstName")) || "test".equals(publisherContext.getLead().getAttributeValue("lastName"));
    }

    private Disposition postToCastle(IPublisherContext publisherContext, Long leadMatchId, RoutingCandidate candidate) throws IOException, NamingException, SQLException {
        String cmcPortalId = writeToCastleCRM(publisherContext.getLead(), leadMatchId, publisherContext.getUserProfile(), candidate);
        if (cmcPortalId != null && !"".equals(cmcPortalId)) {
            return writeToCastleFive9(publisherContext.getLead(), leadMatchId, cmcPortalId, "AL No Contact");
        }

        return Disposition.createPost(Disposition.Status.REJECTED, null, null, "No id from CMC portal received");
    }

    private void fireLeadArrivalPixel(Long leadMatchId, Map<String, String> sourceIdMap, String amnJobCode) {
        // http get to SB
        if (sourceIdMap.get("lead_arrival_pixel_id") == null) {
            throw new IllegalArgumentException("No pixel_id defined for this order");
        }

        Long pixelId = Long.valueOf(sourceIdMap.get("lead_arrival_pixel_id"));
    }

    private Disposition postToPercipioFive9(IPublisherContext publisherContext, Long leadMatchId, Map<String, String> sourceIdMap, RoutingCandidate candidate) throws IOException {
        String crmLeadUrl = buildCrmLeadUrl(leadMatchId, sourceIdMap);
        LOG.info("crmUrl=" + crmLeadUrl);

        // http post to external system  (our Five9)

        Lead lead = publisherContext.getLead();

        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        // custom field for Five9
//        formParams.add(new BasicNameValuePair("AL_crm_lead_url", crmLeadUrl));
        formParams.add(new BasicNameValuePair("collegenotes", crmLeadUrl));

        // Five9 identifiers
        formParams.add(new BasicNameValuePair("F9domain", sourceIdMap.get("F9domain")));
        formParams.add(new BasicNameValuePair("F9list", sourceIdMap.get("F9list")));
        formParams.add(new BasicNameValuePair("F9retResults", "1"));

        // This will make collegenotes the primary field for the data being posted.
        // If you re-post new data with same collegenotes value, the original record will get updated.
        // Note: just in case: this is to make sure we don't overwrite Percipio records (that might share the same phone number)
        formParams.add(new BasicNameValuePair("F9key", "collegenotes"));

        // first_name
        String firstName = lead.getAttributeValue("fname");
        if (firstName == null) {
            firstName = lead.getAttributeValue("firstName");
        }
        formParams.add(new BasicNameValuePair("first_name", firstName));

        // last_name
        String lastName = lead.getAttributeValue("lname");
        if (lastName == null) {
            lastName = lead.getAttributeValue("lastName");
        }
        formParams.add(new BasicNameValuePair("last_name", lastName));

        // number1
        String phone = lead.getAttributeValue("cellPhone");
        if (phone == null || "".equals(phone)) {
            // mobile wins over not mobile
            phone = lead.getAttributeValue("landPhone");
        }
        if (phone != null) {
            formParams.add(new BasicNameValuePair("number1", phone));
        }

        // number2
        if (lead.getAttributeValue("landPhone") != null) {
            formParams.add(new BasicNameValuePair("number2", lead.getAttributeValue("landPhone")));
        }

        // State
        formParams.add(new BasicNameValuePair("state", lead.getAttributeValue("state")));

        // city
        formParams.add(new BasicNameValuePair("city", lead.getAttributeValue("city")));

        // Street
        formParams.add(new BasicNameValuePair("street", lead.getAttributeValue("address")));

        // Zip
        String zip = lead.getAttributeValue("zip");
        if (zip == null) {
            zip = lead.getAttributeValue("zipcode");
        }
        formParams.add(new BasicNameValuePair("zip", zip));

        String url = "https://api.five9.com/web2campaign/AddToList";
        Long msgId = logPostMessage(leadMatchId, url, formParams);

        String[] lines = httpPostWithStringResponse(url, formParams);
        if (lines == null || lines.length == 0) {
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to parse post response");
        }

        if (msgId != null) {
            // record the raw response
            logResponseMessage(msgId, lines);
        }

        // parse response
        for (String line : lines) {
            // do we have a success?
            if (!"".equals(line.trim())) {

                // <INPUT readonly id="F9errCode" name="F9errCode" value="0" size="10">
                int pos = line.indexOf("id=\"F9errCode\"");
                if (pos >= 0) {
                    // look for the closing tag
                    int endPos = line.indexOf(">", pos + 1);
                    int successPos = line.indexOf("value=\"0\"", pos + 1);

                    if (successPos < 0 || successPos > endPos) {
                        return Disposition.createPost(Disposition.Status.REJECTED, null, null, "F9Error > 0");
                    }
                }
            }
        }
        // fire pixel to track lead to CRM transfer

        return Disposition.createPost(Disposition.Status.ACCEPTED, null, null, "F9 accepted");
    }

    private String buildCrmLeadUrl(Long leadMatchId, Map<String, String> sourceIdMap) {
        String crmURL = sourceIdMap.get("crm_url");
        if (crmURL == null) {
            // in case the root url is directly configured in Five9, we just send the unique lead id (and the final CRM url will be assembled inside of Five9)
            return leadMatchId.toString();
        }

        if (crmURL.contains("lpCRM")) {
            return crmURL + (crmURL.endsWith("/") ? "" : "/") + leadMatchId;
        }

        return crmURL + "/lpCRM/cc/" + leadMatchId;
    }

    // if our call center is closed (outside 9AM CST - 5 PM PST), send directly to CMC (Five9 plus Portal)
    private static boolean isInsideCallCenterHours() {

//        DateFormat dfCentral = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//        DateFormat dfPacific = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//
//        dfCentral.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
//        dfPacific.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        DateFormat dfEast = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        dfEast.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        return dfEast.getCalendar().get(Calendar.HOUR_OF_DAY) >= 9 &&
                dfEast.getCalendar().get(Calendar.HOUR_OF_DAY) < 16;
    }


    private Disposition writeToCastleFive9(Lead lead, Long leadMatchId, String crmResponse, String listId) throws IOException {
        LOG.info("writing to CMC Five9: " + lead + "; " + crmResponse);

        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 3) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    // Retry if the server dropped connection on us
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {
                    // Do not retry on SSL handshake exception
                    return false;
                }
                HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }

        };
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.setHttpRequestRetryHandler(myRetryHandler);

        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
//        formParams.add(new BasicNameValuePair("leadKey", lead.getMatchId().toString()));

        formParams.add(new BasicNameValuePair("castle_crm_url", CMC_URL + "/BrokerPortal/Loan/Summary/A_" + crmResponse.trim()));
        formParams.add(new BasicNameValuePair("F9domain", "Castle Mortgage"));
        formParams.add(new BasicNameValuePair("F9list", listId == null ? "AL List" : listId));
        formParams.add(new BasicNameValuePair("F9retResults", "1"));

        formParams.add(new BasicNameValuePair("first_name", lead.getAttributeValue("firstName")));
        formParams.add(new BasicNameValuePair("last_name", lead.getAttributeValue("lastName")));
        formParams.add(new BasicNameValuePair("street", lead.getAttributeValue("address")));
        formParams.add(new BasicNameValuePair("city", lead.getAttributeValue("city")));
        formParams.add(new BasicNameValuePair("state", lead.getAttributeValue("state")));
        formParams.add(new BasicNameValuePair("zip", lead.getAttributeValue("zipcode")));

        String phone = lead.getAttributeValue("cellPhone");
        if (phone == null || "".equals(phone)) {
            phone = lead.getAttributeValue("landPhone");
        }
        formParams.add(new BasicNameValuePair("number1", phone));

        if (lead.getAttributeValue("landPhone") != null && !"".equals(lead.getAttributeValue("landPhone"))) {
            formParams.add(new BasicNameValuePair("number2", lead.getAttributeValue("landPhone")));
        }

        formParams.add(new BasicNameValuePair("lead_source", lead.getAttributeValue("amn_job_code")));

        //and date_lead_received. Lead source format is a string and date field format is mm/dd/yyyy.
        DateFormat dfCentral = new SimpleDateFormat("MM/dd/yyyy");
        dfCentral.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        String day = dfCentral.format(Calendar.getInstance().getTime());
        formParams.add(new BasicNameValuePair("date_lead_received", day));

        String url = "https://api.five9.com/web2campaign/AddToList";
        Long msgId = logPostMessage(leadMatchId, url, formParams);

        String[] lines = httpPostWithStringResponse(url, formParams);
        if (lines == null || lines.length == 0) {
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to parse post response");
        }

        if (msgId != null) {
            // record the raw response
            logResponseMessage(msgId, lines);
        }

        // parse response
        for (String line : lines) {
            // do we have a success?
            if (!"".equals(line.trim())) {

                // <INPUT readonly id="F9errCode" name="F9errCode" value="0" size="10">
                int pos = line.indexOf("id=\"F9errCode\"");
                if (pos >= 0) {
                    // look for the closing tag
                    int endPos = line.indexOf(">", pos + 1);
                    int successPos = line.indexOf("value=\"0\"", pos + 1);

                    if (successPos < 0 || successPos > endPos) {
                        return Disposition.createPost(Disposition.Status.REJECTED, null, null, "F9Error > 0");
                    }
                }
            }
        }

        // fire pixel to track direct lead transfer

        return Disposition.createPost(Disposition.Status.ACCEPTED, null, null, "F9 accepted");
    }

    private String writeToCastleCRM(Lead lead, Long leadMatchId, UserProfile userProfile, RoutingCandidate candidate) throws IOException, NamingException, SQLException {

        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 3) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    // Retry if the server dropped connection on us
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {
                    // Do not retry on SSL handshake exception
                    return false;
                }
                HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }

        };
        httpClient.setHttpRequestRetryHandler(myRetryHandler);

        // send to broker that is assigned to this state
        Broker broker = AdvertiserModel.findBrokerForLead(candidate.getIo().getAdvertiserId(), lead.getAttributeValue("state"));

        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("brokerID", broker == null ? "1" : broker.getBrokerId())); // call center contact to post to
        formParams.add(new BasicNameValuePair("token", "TODO: from Kamel"));
        formParams.add(new BasicNameValuePair("leadKey", leadMatchId.toString()));

        formParams.add(new BasicNameValuePair("BorrowerFirstName", lead.getAttributeValue("firstName")));
        formParams.add(new BasicNameValuePair("BorrowerLastName", lead.getAttributeValue("lastName")));
        formParams.add(new BasicNameValuePair("BorrowerHomePhone", lead.getAttributeValue("landPhone")));
        formParams.add(new BasicNameValuePair("BorrowerCellPhone", lead.getAttributeValue("cellPhone")));
        formParams.add(new BasicNameValuePair("BorrowerEmail", userProfile.getEmail()));
        formParams.add(new BasicNameValuePair("CoBorrowerFirstName", ""));
        formParams.add(new BasicNameValuePair("CoBorrowerLastName", ""));
        formParams.add(new BasicNameValuePair("CoBorrowerHomePhone", ""));
        formParams.add(new BasicNameValuePair("CoBorrowerCellPhone", ""));
        formParams.add(new BasicNameValuePair("CoBorrowerEmail", ""));
        formParams.add(new BasicNameValuePair("MailAddress", lead.getAttributeValue("address")));
        formParams.add(new BasicNameValuePair("MailCity", lead.getAttributeValue("city")));
        formParams.add(new BasicNameValuePair("MailState", lead.getAttributeValue("state")));
        formParams.add(new BasicNameValuePair("MailZip", lead.getAttributeValue("zipcode")));

        formParams.add(new BasicNameValuePair("PropertyAddress", coalesce(lead.getAttributeValue("prop_address"), lead.getAttributeValue("address"))));
        formParams.add(new BasicNameValuePair("PropertyCity", coalesce(lead.getAttributeValue("prop_city"), lead.getAttributeValue("city"))));
        formParams.add(new BasicNameValuePair("PropertyState", coalesce(lead.getAttributeValue("prop_st"), lead.getAttributeValue("state"))));
        formParams.add(new BasicNameValuePair("PropertyZip", coalesce(lead.getAttributeValue("prop_zip"), lead.getAttributeValue("zipcode"))));
        formParams.add(new BasicNameValuePair("PropertyArea", lead.getAttributeValue("prop_area")));

        formParams.add(new BasicNameValuePair("AddCash", lead.getAttributeValue("add_cash")));
        formParams.add(new BasicNameValuePair("Age", lead.getAttributeValue("age")));
        formParams.add(new BasicNameValuePair("AgentFound", lead.getAttributeValue("agent_found")));

        formParams.add(new BasicNameValuePair("BalanceOne", lead.getAttributeValue("bal_one")));
        formParams.add(new BasicNameValuePair("BalanceTwo", lead.getAttributeValue("bal_two")));

        formParams.add(new BasicNameValuePair("Bankruptcy", lead.getAttributeValue("bkcy")));
        formParams.add(new BasicNameValuePair("CreditGrade", lead.getAttributeValue("cred_grade")));
        formParams.add(new BasicNameValuePair("DownPayment", lead.getAttributeValue("down_pmt")));
        formParams.add(new BasicNameValuePair("EstimatedValue", lead.getAttributeValue("est_val")));
        formParams.add(new BasicNameValuePair("FirstTimeBuyer", lead.getAttributeValue("first_buyer")));
        formParams.add(new BasicNameValuePair("Income", lead.getAttributeValue("income")));
        formParams.add(new BasicNameValuePair("InterestRate", lead.getAttributeValue("int")));
        formParams.add(new BasicNameValuePair("LoanAmount", lead.getAttributeValue("loan_amt")));
        formParams.add(new BasicNameValuePair("LoanPurpose", lead.getAttributeValue("loan_purp")));
        formParams.add(new BasicNameValuePair("LoanTerm", lead.getAttributeValue("loan_term")));
        formParams.add(new BasicNameValuePair("LoanType", lead.getAttributeValue("loan_type")));
        formParams.add(new BasicNameValuePair("LTV", lead.getAttributeValue("ltv")));
        formParams.add(new BasicNameValuePair("LTVFuture", lead.getAttributeValue("ltv_future")));
        formParams.add(new BasicNameValuePair("MinPay", lead.getAttributeValue("min_pay")));
        formParams.add(new BasicNameValuePair("MortgageFirst", lead.getAttributeValue("mtg_one")));
        formParams.add(new BasicNameValuePair("MortgageFirstInterest", lead.getAttributeValue("mtg_one_int")));
        formParams.add(new BasicNameValuePair("MortgageSecond", lead.getAttributeValue("mtg_two")));
        formParams.add(new BasicNameValuePair("MortgageSecondInterest", lead.getAttributeValue("mtg_two_int")));
        formParams.add(new BasicNameValuePair("Occupancy", lead.getAttributeValue("occ_stat")));
        formParams.add(new BasicNameValuePair("OutstandingCC", lead.getAttributeValue("outstanding_cc")));
        formParams.add(new BasicNameValuePair("PropertyDesc", lead.getAttributeValue("prop_desc")));
        formParams.add(new BasicNameValuePair("PropertyPurpose", lead.getAttributeValue("prop_purp")));
        formParams.add(new BasicNameValuePair("PropertyType", lead.getAttributeValue("prop_type")));
        formParams.add(new BasicNameValuePair("PurposeContract", lead.getAttributeValue("purchase_contract")));
        formParams.add(new BasicNameValuePair("PurchaseDate", lead.getAttributeValue("purchase_date")));
        formParams.add(new BasicNameValuePair("SpecHome", lead.getAttributeValue("spec_home")));
        formParams.add(new BasicNameValuePair("TimePosted", lead.getAttributeValue("time_posted")));

//      For the new fields I added two lead_source
        formParams.add(new BasicNameValuePair("lead_source", lead.getAttributeValue("amn_job_code")));

        //and date_lead_received. Lead source format is a string and date field format is mm/dd/yyyy.
        DateFormat dfCentral = new SimpleDateFormat("MM/dd/yyyy");
        dfCentral.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
        String day = dfCentral.format(Calendar.getInstance().getTime());
        formParams.add(new BasicNameValuePair("date_lead_received", day));

        StringBuilder disposition = new StringBuilder();

        // Execute the request
        String url = CMC_URL + "/Lead/SaveLead";
        Long msgId = logPostMessage(leadMatchId, url, formParams);

        String[] lines = httpPostWithStringResponse(url, formParams);

        if (msgId != null) {
            // record the raw response
            logResponseMessage(msgId, lines);
        }

        for (String line : lines) {
            // do we have an id?
            if (!"".equals(line.trim())) {
                disposition.append(line);
            }
        }

        return disposition.toString();
    }

    private static String coalesce(String str1, String str2) {
        if (str1 != null && !"".equals(str1)) {
            return str1;
        }

        return str2 == null ? "" : str2;
    }


    public static void main(String[] args) {
        DateFormat dfEst = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        dfEst.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        DateFormat dfPst = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        dfPst.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        Date tpst = dfPst.getCalendar().getTime();
        Date test = dfEst.getCalendar().getTime();

        LOG.debug("PST: " + dfPst.getCalendar().get(Calendar.HOUR_OF_DAY));
        LOG.debug("EST: " + dfEst.getCalendar().get(Calendar.HOUR_OF_DAY));

        LOG.debug("PST time = " + dfPst.format(Calendar.getInstance().getTime()));

        LOG.debug("inside EST ? " + isInsideCallCenterHours());
    }
}
