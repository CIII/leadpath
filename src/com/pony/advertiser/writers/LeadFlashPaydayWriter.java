package com.pony.advertiser.writers;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.Io;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.leadtypes.PaydayLead;
import com.pony.models.LeadMatchModel;
import com.pony.models.Model;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 1/15/12
 * Time: 4:37 PM
 */
public class LeadFlashPaydayWriter extends AdvertiserWriter {
	private static final Log LOG = LogFactory.getLog(LeadFlashPaydayWriter.class);
    private static final String POST_URL = "https://www.502011tr021731axxxsb.com/api/leadpost.aspx";

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {
        /*
>> Posting URL: Use the URL as the action in your HTML form or a server post to transmit the data to our servers https://www.502011tr021731axxxsb.com/api/leadpost.aspx or http
>> Posting credentials
>> PonyMash Finance 1 (100 min) - 100%
>> Vendor ID:  606316
>> Vendor Pass:  543658774
>>
>> PonyMash Finance 2 (80 min) - 90%
>> Vendor ID:  606317
>> Vendor Pass:  543658775
>>
>> PonyMash Finance 3 (65 min) - 90%
>> Vendor ID:  606318
>> Vendor Pass:  543658776
>>
>> PonyMash Finance 4 (45 min) - 90%
>> Vendor ID:  606319
>> Vendor Pass:  543658777
>>
>> PonyMash Finance 5 (30 min) - 90%
>> Vendor ID:  606320
>> Vendor Pass:  543658778
>>
>> PonyMash Finance 6 (20 min) - 90%
>> Vendor ID:  606321
>> Vendor Pass:  543658779
>>
>> PonyMash Finance 7 (10 min) - 90%
>> Vendor ID:  606322
>> Vendor Pass:  543658780
>>
>> PonyMash Finance 8 (2 min) - 90%
>> Vendor ID:  606323
>> Vendor Pass:  543658781
>>
>> Transmission of Lead Data: Now that you have the posting credentials and URL, please review the Vendor-API -Profile to ensure your forms collect all required fields for the transmission of lead Data.
         */

        Lead lead = publisherContext.getLead();
        Arrival arrival = publisherContext.getArrival();

        Disposition.DispositionCategory category = null;
        String comment = null;

        if (!(lead instanceof PaydayLead)) {
            return Disposition.createPost(Disposition.Status.REJECTED, category, null, "Unsupported type for leadId:" + lead.getId());
        }
        try {
            PaydayLead pLead = (PaydayLead) lead;

            List<NameValuePair> formParams = transformLead(arrival, pLead, candidate);
            //            List<NameValuePair> formParams = transformLead(arrival, pLead, credentials, candidate.getIo().getVpl());

            UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
            HttpPost httppost = new HttpPost(POST_URL);
            httppost.setEntity(requestEntity);

            Long postId = log(lead.getId(), candidate, httppost.getURI(), formParams);

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

    private List<NameValuePair> transformLead(Arrival arrival, PaydayLead lead, RoutingCandidate candidate) {
        // example: source_id = vendor_id=11111;vendor_pass=ppppppp;test_app=1

        Io io = candidate.getIo();
        Map<String, String> credentials = Model.parseToMap(io.getSourceId());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("vendor_id", credentials.get("vendor_id")));
        formParams.add(new BasicNameValuePair("vendor_pass", credentials.get("vendor_pass")));
        formParams.add(new BasicNameValuePair("test_app", credentials.get("test_app")));

        // if this advertiser already received a post for this lead, look for an unsold one that has an id; id there is one, send that as lid on this re-post

        LOG.debug("$$$$$$ candidate:" + candidate);
        LOG.debug("$$$$$$ disposition:" + candidate.getResponseDispositions());
        LOG.debug("$$$$$$ disposition size=" + candidate.getResponseDispositions().size());

        if (candidate.getResponseDispositions().size() > 0) {
            //            ***Only the following fields should be passed when reposting a lead:***
            //             *	vendor_id
            //             *	vendor_pass
            //             *	Lid
            //             *	test_app
            //             *	reapply_token - Not required, use this only if you are instructed to do so.
            String lid = null;
//            for (Disposition d : candidate.getResponseDispositions()) {
//                System.out.println("######## d=" + d);
//                if (d.isUnsold() && d.getExternalId() != null) {
//                    lid = d.getExternalId();
//                    System.out.println("found id for re-post:" + lid);
//                    break;
//                }
//            }

            if (lid != null) {
                formParams.add(new BasicNameValuePair("Lid", lid));
                return formParams;
            }
        }

        formParams.add(new BasicNameValuePair("lead_type_id", "14"));
        formParams.add(new BasicNameValuePair("Tag", io.getCode()));

        formParams.add(new BasicNameValuePair("auto_redirect", "0"));

        formParams.add(new BasicNameValuePair("first_name", lead.getFirstName()));
        formParams.add(new BasicNameValuePair("last_name", lead.getLastName()));
        formParams.add(new BasicNameValuePair("street_addr1", lead.getAddress()));
        formParams.add(new BasicNameValuePair("street_addr2", lead.getAddress2()));
        formParams.add(new BasicNameValuePair("city", lead.getCity()));
        formParams.add(new BasicNameValuePair("state", lead.getState()));
        formParams.add(new BasicNameValuePair("Zip", lead.getZipCode()));
        formParams.add(new BasicNameValuePair("social_security", lead.getSocialSecurityNumber()));
        formParams.add(new BasicNameValuePair("phone_home", lead.getHomePhone() != null ? lead.getHomePhone() : lead.getWorkPhone()));
        //        formParams.add(new BasicNameValuePair("phone_cell", lead.getWorkPhone()));
        formParams.add(new BasicNameValuePair("phone_work", lead.getWorkPhone() != null ? lead.getWorkPhone() : lead.getHomePhone()));
        formParams.add(new BasicNameValuePair("Email", lead.getEmail()));

        if (lead.getDateOfBirth() != null) {
            formParams.add(new BasicNameValuePair("birth_date", df.format(lead.getDateOfBirth())));
        }

        formParams.add(new BasicNameValuePair("employer_name", lead.getCompanyName()));
        formParams.add(new BasicNameValuePair("pay_per_period", lead.getMonthlyIncome()));

        Map<String, String> frq = new HashMap<String, String>();
        frq.put("weekly", "WEEKLY");
        frq.put("bi_weekly", "BIWEEKLY");
        frq.put("twice_monthly", "TWICEMONTH");
        frq.put("monthly", "MONTHLY");

        formParams.add(new BasicNameValuePair("pay_frequency", frq.get(lead.getPayFrequency())));
        formParams.add(new BasicNameValuePair("direct_deposit", lead.isDirectDeposit() ? "1" : "0"));

        if (lead.getNextPayDate() != null) {
            formParams.add(new BasicNameValuePair("pay_day1", df.format(lead.getNextPayDate())));
        }

        if (lead.getFollowingPayDate() != null) {
            formParams.add(new BasicNameValuePair("pay_day2", df.format(lead.getFollowingPayDate())));
        }

        formParams.add(new BasicNameValuePair("bank_aba", lead.getRoutingNumber()));
        formParams.add(new BasicNameValuePair("bank_account", lead.getAccountNumber()));
        formParams.add(new BasicNameValuePair("bank_name", lead.getBankName()));
        formParams.add(new BasicNameValuePair("income_monthly", lead.getMonthlyIncome()));
        //        ref_name_01
        //        ref_phone_01
        //        ref_relation_01
        //        ref_name_02
        //        ref_phone_02
        //        ref_relation_02
        //        ref_name_03
        //        ref_phone_03
        //        ref_relation_03

        formParams.add(new BasicNameValuePair("own_home", "0")); //DEFAULT
        formParams.add(new BasicNameValuePair("drivers_license", lead.getDriversLicenseNumber()));
        formParams.add(new BasicNameValuePair("drivers_license_st", lead.getDriversLicenseState()));
        formParams.add(new BasicNameValuePair("client_url_root", "mycashfriend.com"));
        formParams.add(new BasicNameValuePair("client_ip_address", (arrival == null ? "127.0.0.1" : arrival.getIpAddress())));
        //formParams.add(new BasicNameValuePair("email_alternate
        formParams.add(new BasicNameValuePair("months_employed", lead.getTimeAtEmployer()));

        Map<String, String> incomeSources = new HashMap<String, String>();
        incomeSources.put("1", "EMPLOYMENT");
        incomeSources.put("0", "BENEFITS");
        String incomeSource = lead.getIncomeSource();
        if (incomeSource == null) {
            incomeSource = "1"; //default
        }
        formParams.add(new BasicNameValuePair("income_type", incomeSources.get(incomeSource)));

        formParams.add(new BasicNameValuePair("is_military", lead.isActiveMilitary() ? "1" : "0"));
        formParams.add(new BasicNameValuePair("is_live", "1"));

        formParams.add(new BasicNameValuePair("bank_account_type", lead.getAccountType().toUpperCase()));

        formParams.add(new BasicNameValuePair("requested_amount", "400"));
        formParams.add(new BasicNameValuePair("accepted_terms", "1"));

        formParams.add(new BasicNameValuePair("months_at_address", lead.getTimeAtAddress()));
        formParams.add(new BasicNameValuePair("months_at_bank", "12")); // DEFAULT

        // enforce xml response
        formParams.add(new BasicNameValuePair("delimiter", "xml"));

        return formParams;
    }

    private void log(Long leadId, StatusLine statusLine) {
        LOG.info(this.getClass().getName() + ":" + Calendar.getInstance().getTime().toString() + ":leadId=" + leadId + ":" + statusLine);
    }

    private Long log(Long leadId, RoutingCandidate candidate, URI uri, List<NameValuePair> formparams)
            throws NamingException, SQLException {
        //TODO log to appender

        StringBuilder msg = new StringBuilder();
        LOG.debug(this.getClass().getName() + ":" + Calendar.getInstance().getTime().toString() + ":leadId=" + leadId + ":uri=" + uri);
        for (NameValuePair p : formparams) {
            if ("social_security_number".equals(p.getName()) || "account_number".equals(p.getName())) {
                // skip sensitive data to not have it in any logs
                continue;
            }
            LOG.debug(p.getName() + "=" + p.getValue());
            msg.append(p.getName()).append("=").append(p.getValue()).append("\r\n");
        }

        return LeadMatchModel.persistMessage(candidate.getLeadMatchId(), PonyPhase.POST, msg.toString());
    }

    private void log(Long leadId, Document doc, Long postId) throws TransformerException, NamingException, SQLException {
        LOG.debug(this.getClass().getName() + ":" + Calendar.getInstance().getTime().toString() + ":leadId=" + leadId + ":response=");

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

        /*
       >> Response from Lead Data:  You may choose the delimiter to be used in our server response.  The current default symbols is Pipe â€œ|â€�.   Our response format  is the following:
       >>
       >> Status|LID|Redirect URL|Price|0  (Please see Vendor-API-LeadIntake)
       >>
       >> The server response can be provided in XML by posting an additional field called "delimiter".  The value for this field will be "xml" (delimiter=xml), we will return the response as an XML string in the following format:
       >> <lead_response>
       >> <status>accepted</status>
       >> <unique_id>52654783</unique_id>
       >> <message><![CDATA[http://www.redirectlinkhere.com]]></message>
       >> <price>80</price>
       >> <leadsourceid>0</leadsourceid>
       >> </lead_response>
       >>
       >> IMPORTANT NOTE:  When parsing the response, do not rely on what line something is located be sure to read the node.
        */

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
            expr = xpath.compile("/lead_response/status/text()");
            String status = (String) expr.evaluate(doc, XPathConstants.STRING);

            expr = xpath.compile("/lead_response/unique_id/text()");
            String id = (String) expr.evaluate(doc, XPathConstants.STRING);

            expr = xpath.compile("/lead_response/price/text()");
            Double price = (Double) expr.evaluate(doc, XPathConstants.NUMBER);

            expr = xpath.compile("/lead_response/message/text()");
            String message = (String) expr.evaluate(doc, XPathConstants.STRING);
            //                        System.out.println("redirect=" + redirect);

            if ("accepted".equalsIgnoreCase(status)) {
                return Disposition.createPost(Disposition.Status.ACCEPTED, category, id, new BigDecimal(price), message);
            }
            else if ("declined".equalsIgnoreCase(status)) {
                return Disposition.createPost(Disposition.Status.UNSOLD, category, id, message);
            }
            return Disposition.createPost(Disposition.Status.REJECTED, category, id, message);
        }
        catch (ParserConfigurationException e) {
            comment = e.getMessage();
            LOG.error(e);
        }
        catch (SAXException e) {
            comment = e.getMessage();
            LOG.error(e);
        }
        catch (XPathExpressionException e) {
            comment = e.getMessage();
            LOG.error(e);
        }
        catch (TransformerConfigurationException e) {
            comment = e.getMessage();
            LOG.error(e);
        }
        catch (TransformerException e) {
            comment = e.getMessage();
            LOG.error(e);
        }
        catch (SQLException e) {
            comment = e.getMessage();
            LOG.error(e);
        }
        catch (NamingException e) {
            comment = e.getMessage();
            LOG.error(e);
        }
        finally {
        }

        return Disposition.createPost(Disposition.Status.REJECTED, category, null, comment);
    }
}
