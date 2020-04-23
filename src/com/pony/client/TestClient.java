package com.pony.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLHandshakeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * test pings, posts, polls
 *
 * Solar:
 * leadpath:    tapnexus.com
 * sitebrain:   tapxs.com
 *
 *
 * ssh -i /work/AWS/solar.pem ec2-user@54.186.253.242
 *
 * <p/>
 * Created by martin on 10/7/16.
 */
public class TestClient {
	private static final Log LOG = LogFactory.getLog(TestClient.class);

    public static void main(String[] args) {

        // test solar ping
//        testSolarPing();

        // test solar post
//        testSolarPost();

        // test poll
//        testSolarPoll();
    }

    private static String testSolarPing() {
        String leadId = null;

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("zip", "02421"));
        params.add(new BasicNameValuePair("city", "Lexington"));
        params.add(new BasicNameValuePair("state", "MA"));
        params.add(new BasicNameValuePair("street", "100 Test Drive"));
        params.add(new BasicNameValuePair("phone_home", "6173431214"));

//        params.add(new BasicNameValuePair("first_name", "Mickey"));
//        params.add(new BasicNameValuePair("last_name", "Mouse"));
        params.add(new BasicNameValuePair("email", "mickey@mouse.com"));

//        params.add(new BasicNameValuePair("property_ownership", "property_ownership"));
//        params.add(new BasicNameValuePair("electric_bill", "electric_bill"));
//        params.add(new BasicNameValuePair("electric_company", "electric_company"));

//        params.add(new BasicNameValuePair("leadid_token", "universal_leadid"));
//        params.add(new BasicNameValuePair("xxTrustedFormCertUrl", "xxTrustedFormCertUrl"));

        params.add(new BasicNameValuePair("listid", "solar_full_form"));
        params.add(new BasicNameValuePair("domtok", "sunnyS0lar"));
        params.add(new BasicNameValuePair("ref", "www.easiersolar.com"));

        String url = "http://tapnexus.com/ping";

        try {

            BufferedReader reader = null;
            try {
                String readRef = null;
                HttpResponse resp = httpPost(url, readRef, params);
                reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
                List<String> lines = new LinkedList<String>();
                String line;
                while ((line = reader.readLine()) != null) {
                    String l = line.trim();
                    if (!"".equals(l)) {
                        lines.add(l);
                    }
                    LOG.debug(l);
                    // todo: parse lead id : {"lead_id":"2380","validation_code":"0","leadpath_listids":[]}
                }
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
            }

        }
        catch (IOException e) {
            LOG.error(e);
        }

        return leadId;
    }

    private static String testSolarPost() {
        String leadId = null;

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("zip", "02421"));
        params.add(new BasicNameValuePair("city", "Lexington"));
        params.add(new BasicNameValuePair("state", "MA"));
        params.add(new BasicNameValuePair("street", "100 Test Drive"));
        params.add(new BasicNameValuePair("phone_home", "6173431214"));

        params.add(new BasicNameValuePair("first_name", "Mickey"));
        params.add(new BasicNameValuePair("last_name", "Mouse"));
        params.add(new BasicNameValuePair("email", "mickey@mouse.com"));

//        params.add(new BasicNameValuePair("property_ownership", "property_ownership"));
//        params.add(new BasicNameValuePair("electric_bill", "electric_bill"));
//        params.add(new BasicNameValuePair("electric_company", "electric_company"));

//        params.add(new BasicNameValuePair("leadid_token", "universal_leadid"));
//        params.add(new BasicNameValuePair("xxTrustedFormCertUrl", "xxTrustedFormCertUrl"));

        params.add(new BasicNameValuePair("listid", "solar_full_form"));
        params.add(new BasicNameValuePair("domtok", "sunnyS0lar"));
        params.add(new BasicNameValuePair("ref", "www.easiersolar.com"));

        String url = "http://tapnexus.com/post";

        try {

            BufferedReader reader = null;
            try {
                String readRef = "2380";
                HttpResponse resp = httpPost(url, readRef, params);
                reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
                List<String> lines = new LinkedList<String>();
                String line;
                while ((line = reader.readLine()) != null) {
                    String l = line.trim();
                    if (!"".equals(l)) {
                        lines.add(l);
                    }
                    LOG.debug(l);
                }
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
            }

        }
        catch (IOException e) {
            LOG.error(e);
        }

        return leadId;
    }

    private static String testSolarPoll() {
        String leadId = null;

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("listid", "solar_full_form"));
        params.add(new BasicNameValuePair("domtok", "sunnyS0lar"));
        params.add(new BasicNameValuePair("ref", "www.easiersolar.com"));

        String url = "http://tapnexus.com/poll";

        try {

            BufferedReader reader = null;
            try {
                String readRef = "2380";
                HttpResponse resp = httpPost(url, readRef, params);
                reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
                List<String> lines = new LinkedList<String>();
                String line;
                while ((line = reader.readLine()) != null) {
                    String l = line.trim();
                    if (!"".equals(l)) {
                        lines.add(l);
                    }
                    LOG.debug(l);
                }
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
        catch (IOException e) {
            LOG.error(e);
        }

        return leadId;
    }

    private static HttpResponse httpPost(String url, String leadRef, List<NameValuePair> formParams) throws IOException {

        // are we posting in reference to a previous ping?
        // if so, add the ref, so LP can tie the new data to what's already there
        if (leadRef != null && !"".equals(leadRef)) {
            formParams.add(new BasicNameValuePair("lead_ref", leadRef));
        }

        UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(requestEntity);

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
        return httpclient.execute(httppost);
    }

    private static HttpResponse httpGet(String url, List<NameValuePair> formParams) throws IOException {
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

        // Execute the request
        StringBuilder query = new StringBuilder();

        for (NameValuePair pair : formParams) {
            if (pair.getValue() == null) {
                continue;
            }

            if (query.length() == 0 && !url.contains("?")) {
                query.append("?");
            }
            else {
                query.append("&");
            }
            query.append(pair.getName()).append("=").append(URLEncoder.encode(pair.getValue(), "utf-8"));
        }

        HttpGet httpGet = new HttpGet(url + query);
//        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
//        httpGet.setHeader("Accept-Language", "en-US,en;q=0.8");
//        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

        return httpClient.execute(httpGet);
    }
}
