package com.pony.advertiser.writers;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.leadtypes.PaydayLead;
import com.pony.models.LeadMatchModel;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.naming.NamingException;
import javax.net.ssl.SSLHandshakeException;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The price points you will be posting the leads at (minimum_price). We suggest 2, 20 and 40 because we have the most buyers here.
 * <p/>
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/28/11
 * Time: 7:57 PM
 */
public class LeadPilePaydayWriter extends AdvertiserWriter {
	private static final Log LOG = LogFactory.getLog(LeadPilePaydayWriter.class);
	
    private static final String PROD_URL = "https://www.leadpile.com/cgi-bin/openmarket";
    private static final String TEST_URL = "http://test.leadpile.com/cgi-bin/openmarket";
    private static final String POST_URL = PROD_URL;

    private static final String testProducerId = "116794713249";
    private static final String testProducerKey = "vgdY9inDOeMWHl5NoalqMupWAIqMHgif";

    private static final String prodProducerId = "1322508104384";
    private static final String prodProducerKey = "XqrCwroh64eohZ575LJVjQq35CBjr5dH";

    private static final String PRODUCER_ID = prodProducerId;
    private static final String PRODUCER_KEY = prodProducerKey;

    public LeadPilePaydayWriter() {
        super();
    }

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {
        // Note! The price points you will be posting the leads at (minimum_price). We suggest 2, 20 and 40 because we have the most buyers here.

        // Production POST URL:
        /*
        https://www.leadpile.com/cgi-bin/openmarket
        All requests will be made via HTTP POST using application/x-www-form-urlencoded (as opposed to multipart/form-data which is not supported) in the following format:
        */

        // Test POST URL:
        /*
        http://test.leadpile.com/cgi-bin/openmarket
        When submitting test leads please use the following credentials:
        POST Field 	Value
        producer_id 	116794713249
        producer_key 	vgdY9inDOeMWHl5NoalqMupWAIqMHgif

        All submitted test leads with minimum_price greater than 5.00 will be accepted, but not sold. All valid test leads with minimum_price less than 5.00 will be accepted and sold.
        For all valid test leads with state set to AZ and minimum_price less than 5.00, the lead will be sold and a redirect URL will be provided. All other states will not provide a redirect URL.
        */

        /*
        Testing Credentials:
        Test POST URL: http://test.leadpile.com/cgi-bin/openmarket

        When submitting test leads please use the following credentials:
        producer_id 116794713249
        producer_key vgdY9inDOeMWHl5NoalqMupWAIqMHgif

        All submitted test leads with minimum_price greater than 5.00 will be accepted, but not sold. All valid test leads with minimum_price less than 5.00 will be accepted and sold.

        For all valid test leads with state set to AZ and minimum_price less than 5.00, the lead will be sold and a redirect URL will be provided. All other states will not provide a redirect URL.
        After you have received a lead accepted or lead sold response, go ahead and change your credentials to the following:

        // ------------------------------------
        Live Credentials:
        Production POST URL: https://www.leadpile.com/cgi-bin/openmarket
        Your Producer ID: 1322508104384
        your Producer Key: XqrCwroh64eohZ575LJVjQq35CBjr5dH
         */

        Disposition.DispositionCategory category = null;
        String comment = null;

        Lead lead = publisherContext.getLead();
        Arrival arrival = publisherContext.getArrival();

        if (!(lead instanceof PaydayLead)) {
            return Disposition.createPost(Disposition.Status.REJECTED, category, null, "Unsupported type for leadId:" + lead.getId());
        }
        try {
            PaydayLead pLead = (PaydayLead) lead;
            List<NameValuePair> formparams = transformLead(arrival, pLead, candidate.getIo().getVpl());
            UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            HttpPost httppost = new HttpPost(POST_URL);
            httppost.setEntity(requestEntity);

            Long postId = log(lead.getId(), candidate, httppost.getURI(), formparams);

            DefaultHttpClient httpclient = new DefaultHttpClient();

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

            httpclient.setHttpRequestRetryHandler(myRetryHandler);
            // Execute the request
            HttpResponse response = httpclient.execute(httppost);

            // Examine the response status
            log(lead.getId(), response.getStatusLine());

            // Get hold of the response entity
            HttpEntity entity = response.getEntity();

            // If the response does not enclose an entity, there is no need
            // to worry about connection release
            if (entity != null) {
                try {
                    return parseResponse(lead, entity, postId);

                }
                catch (IOException ex) {
                    comment = ex.getLocalizedMessage();

                    // In case of an IOException the connection will be released
                    // back to the connection manager automatically
                    throw ex;

                }
                catch (RuntimeException ex) {
                    comment = ex.getLocalizedMessage();
                    // In case of an unexpected exception you may want to abort
                    // the HTTP request in order to shut down the underlying
                    // connection and release it back to the connection manager.
                    httppost.abort();
                    //throw ex;

                }
                finally {


                }
            }

        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }


        return Disposition.createPost(Disposition.Status.REJECTED, category, null, comment);
    }

    private void log(Long leadId, StatusLine statusLine) {
        LOG.info(this.getClass().getName() + ":" + Calendar.getInstance().getTime().toString() + ":leadId=" + leadId + ":" + statusLine);
    }

    private Long log(Long leadId, RoutingCandidate candidate, URI uri, List<NameValuePair> formparams)
            throws NamingException, SQLException {
        //TODO log to appender

        StringBuilder msg = new StringBuilder();
        LOG.info(this.getClass().getName() + ":" + Calendar.getInstance().getTime().toString() + ":leadId=" + leadId + ":uri=" + uri);
        for (NameValuePair p : formparams) {
            if ("social_security_number".equals(p.getName()) || "account_number".equals(p.getName())) {
                // skip sensitive data to not have it in any logs
                continue;
            }
            LOG.info(p.getName() + "=" + p.getValue());
            msg.append(p.getName()).append("=").append(p.getValue()).append("\r\n");
        }

        return LeadMatchModel.persistMessage(candidate.getLeadMatchId(), PonyPhase.POST, msg.toString());
    }

    private void log(Long leadId, Document doc, Long postId) throws TransformerException, NamingException, SQLException {
        LOG.info(this.getClass().getName() + ":" + Calendar.getInstance().getTime().toString() + ":leadId=" + leadId + ":response=");

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(System.out); //TODO: use logger
        transformer.transform(source, result);

        // stream message to a buffer (to insert into db)
        StringWriter writer = new StringWriter();
        StreamResult r2 = new StreamResult(writer);
        transformer.transform(source, r2);

        LeadMatchModel.persistMessageResponse(postId, writer.toString());
    }

    private Disposition parseResponse(Lead lead, HttpEntity entity, Long postId) throws IOException, JAXBException {
        Disposition.DispositionCategory category = null;
        String comment = null;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);

            DocumentBuilder builder;
            Document doc;
            XPathExpression expr;

            builder = factory.newDocumentBuilder();
            doc = builder.parse(entity.getContent());

            log(lead.getId(), doc, postId);

            // Create a XPathFactory
            XPathFactory xFactory = XPathFactory.newInstance();

            // Create a XPath object
            XPath xpath = xFactory.newXPath();

            // Compile the XPath expression
            expr = xpath.compile("/response/status/text()");
            String status = (String) expr.evaluate(doc, XPathConstants.STRING);
            //            System.out.println("Status=" + status);

            expr = xpath.compile("/response/errors/error");
            NodeList errors = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            // Cast the result to a DOM NodeList
            for (int i = 0; i < errors.getLength(); i++) {
                //TODO: determine the category from the error(s)
                //                System.out.println(errors.item(i).getTextContent());
                if (comment == null) {
                    comment = errors.item(i).getTextContent();
                }
            }

            expr = xpath.compile("/response/id/text()");
            String id = (String) expr.evaluate(doc, XPathConstants.STRING);
            //            System.out.println("id=" + id);

            expr = xpath.compile("/response/sale/price/text()");
            Double price = (Double) expr.evaluate(doc, XPathConstants.NUMBER);
            //            System.out.println("price=" + price);

            expr = xpath.compile("/response/sale//redirect/text()");
            String redirect = (String) expr.evaluate(doc, XPathConstants.STRING);
            //            System.out.println("redirect=" + redirect);

            if ("accept".equals(status)) {
                return Disposition.createPost(Disposition.Status.ACCEPTED, category, id, new BigDecimal(price), redirect);
            }
            else if (!"reject".equals(status)) {
                return Disposition.createPost(Disposition.Status.UNSOLD, category, id, comment);
            }
            return Disposition.createPost(Disposition.Status.REJECTED, category, id, comment);
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
        catch (TransformerConfigurationException e) {
            LOG.error(e);
        }
        catch (TransformerException e) {
            LOG.error(e);
        }
        catch (SQLException e) {
            LOG.error(e);
        }
        catch (NamingException e) {
            LOG.error(e);
        }
        finally {

            // Closing the input stream will trigger connection release
            //            instream.close();
        }

        /*
        ACCEPTED LEAD with REAL TIME SALE
        <?xml version="1.0"?>
<response>
<status>accept</status>
<id>123456789</id>
<sale>
<price>12.00</price>
<redirect>http://redirect-example.com</redirect>
</sale>
</response>
         */

        /*
        CORRECT POST; no errors; NO REAL TIME SALE
<?xml version="1.0"?>
<response>
<status>lead received; NO REAL TIME SALE</status>
<id>123456789</id>
</response>
         */

        /*
        CORRECT POST WITH TIMEOUT; no errors; NO REAL TIME SALE
        <?xml version="1.0"?>
<response>
<status>lead received; NO REAL TIME SALE</status>
<id>123456789</id>
<errors>
<error>Out of Time</error>
</errors>
</response>
         */

        /*
        POST WITH ERRORS; lead rejected due to errors
        <?xml version="1.0"?>
<response>
<status>reject</status>
<errors>
<error>Error message 1</error>
<error>Error message 2</error>
</errors>
</response>
         */

        //        Disposition.DispositionCategory category = null;
        //        String comment = null;
        //
        return Disposition.createPost(Disposition.Status.REJECTED, category, null, comment);
    }

    private List<NameValuePair> transformLead(Arrival arrival, PaydayLead lead, BigDecimal mimimumPrice) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String NOT_COLLECTED = "not collected";

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();

        formparams.add(new BasicNameValuePair("producer_id", PRODUCER_ID));
        formparams.add(new BasicNameValuePair("producer_key", PRODUCER_KEY));
        formparams.add(new BasicNameValuePair("lead_type", "payday"));
        //formparams.add(new BasicNameValuePair("additional_lead_types", ""));
        formparams.add(new BasicNameValuePair("timeout_seconds", "60"));

        String minPrice = NumberFormat.getNumberInstance().format(mimimumPrice.doubleValue());
        formparams.add(new BasicNameValuePair("minimum_price", minPrice));

        formparams.add(new BasicNameValuePair("lead_ip", (arrival == null ? "127.0.0.1" : arrival.getIpAddress())));

        formparams.add(new BasicNameValuePair("state", lead.getState()));

        formparams.add(new BasicNameValuePair("first_name", lead.getFirstName()));
        formparams.add(new BasicNameValuePair("last_name", lead.getLastName()));
        formparams.add(new BasicNameValuePair("email", lead.getEmail()));
        formparams.add(new BasicNameValuePair("home_phone", lead.getHomePhone()));
        formparams.add(new BasicNameValuePair("work_phone", lead.getWorkPhone()));
        formparams.add(new BasicNameValuePair("postal_code", lead.getZipCode()));
        formparams.add(new BasicNameValuePair("address", lead.getAddress()));
        formparams.add(new BasicNameValuePair("city", lead.getCity()));

        //Does the applicant own or rent their home ? : own | rent
        formparams.add(new BasicNameValuePair("housing", "rent")); // DEFAULT

        //Is the applicant currently working in the us ? yes | no
        formparams.add(new BasicNameValuePair("working_in_us", "yes")); // DEFAULT

        //Is the applicant employed by the military? no | yes
        formparams.add(new BasicNameValuePair("active_military", lead.isActiveMilitary() ? "yes" : "no"));

        //Best time to contact applicant: anytime | morning | afternoon | evening
        formparams.add(new BasicNameValuePair("best_time_to_call", "evening")); //DEFAULT

        formparams.add(new BasicNameValuePair("monthly_income", lead.getMonthlyIncome()));

        //Does the applicant have a bank account? checking | savings |no
        formparams.add(new BasicNameValuePair("has_bank_account", ("-1".equals(lead.getAccountType()) ? "no" : lead.getAccountType())));

        // Does the applicant have direct deposit ? yes no
        formparams.add(new BasicNameValuePair("direct_deposit", (lead.isDirectDeposit() ? "yes" : "no")));

        // Pay period: weekly | bi - weekly | twice_monthly | monthly
        String period = lead.getPayFrequency();
        if ("bi_weekly".equals(period)) {
            period = "bi-weekly";
        }
        formparams.add(new BasicNameValuePair("pay_period", period));

        if (lead.getNextPayDate() != null) {
            formparams.add(new BasicNameValuePair("next_pay_date", df.format(lead.getNextPayDate())));
        }

        if (lead.getFollowingPayDate() != null) {
            formparams.add(new BasicNameValuePair("second_pay_date", df.format(lead.getFollowingPayDate())));
        }
        //500 | 400 | 300 | 200 | 100
        formparams.add(new BasicNameValuePair("requested_loan_amount", "400")); //DEFAULT

        // Main source of income: employment | benefits
        Map<String, String> incomeSources = new HashMap<String, String>();
        incomeSources.put("1", "employment");
        incomeSources.put("0", "benefits");

        String incomeSource = lead.getIncomeSource();
        if (incomeSource == null) {
            incomeSource = "1"; //default
        }
        formparams.add(new BasicNameValuePair("income_type", incomeSources.get(incomeSource)));

        formparams.add(new BasicNameValuePair("months_at_residence", lead.getTimeAtAddress()));

        formparams.add(new BasicNameValuePair("occupation", lead.getJobTitle()));

        formparams.add(new BasicNameValuePair("employer", lead.getCompanyName()));

        formparams.add(new BasicNameValuePair("supervisor_name", NOT_COLLECTED));   //DEFAULT

        formparams.add(new BasicNameValuePair("supervisor_phone", "1234567890")); //DEFAULT

        // Months with current employer<integer>
        formparams.add(new BasicNameValuePair("months_employed", lead.getTimeAtEmployer()));

        formparams.add(new BasicNameValuePair("bank_name", lead.getBankName()));

        formparams.add(new BasicNameValuePair("account_number", lead.getAccountNumber()));
        formparams.add(new BasicNameValuePair("routing_number", lead.getRoutingNumber()));
        formparams.add(new BasicNameValuePair("bank_phone", "1234567890")); //DEFAULT

        formparams.add(new BasicNameValuePair("driving_license_state", lead.getDriversLicenseState()));
        formparams.add(new BasicNameValuePair("driving_license_number", lead.getDriversLicenseNumber()));
        formparams.add(new BasicNameValuePair("mother_maiden_name", lead.getLastName())); //DEFAULT

        if (lead.getDateOfBirth() != null) {
            formparams.add(new BasicNameValuePair("birth_date", df.format(lead.getDateOfBirth())));
        }
        formparams.add(new BasicNameValuePair("social_security_number", lead.getSocialSecurityNumber()));

        formparams.add(new BasicNameValuePair("reference_1_first_name", NOT_COLLECTED));
        formparams.add(new BasicNameValuePair("reference_1_last_name", NOT_COLLECTED));
        formparams.add(new BasicNameValuePair("reference_1_relationship", NOT_COLLECTED));
        formparams.add(new BasicNameValuePair("reference_1_phone", "1234567890"));

        formparams.add(new BasicNameValuePair("reference_2_first_name", NOT_COLLECTED));
        formparams.add(new BasicNameValuePair("reference_2_last_name", NOT_COLLECTED));
        formparams.add(new BasicNameValuePair("reference_2_relationship", NOT_COLLECTED));
        formparams.add(new BasicNameValuePair("reference_2_phone", "1234567890"));

        return formparams;
    }
}
