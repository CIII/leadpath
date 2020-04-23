package com.pony.advertiser;

import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.models.LeadMatchModel;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.naming.NamingException;
import javax.net.ssl.SSLHandshakeException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/12/11
 * Time: 11:05 PM
 */
public abstract class AdvertiserWriter {
	private static final Log LOG = LogFactory.getLog(AdvertiserWriter.class);
	
    protected static final String NO_SEND = "noSend";
    protected Properties writerProperties;
    protected String name;

    protected static Document httpPostWithXmlResponse(String url, List<NameValuePair> formParams) throws IOException, SAXException, ParserConfigurationException {

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
        HttpResponse response = httpclient.execute(httppost);

        return parseToXmlDoc(response.getEntity().getContent());
    }

    public static Document httpGetWithXmlResponse(String url, List<NameValuePair> formParams) throws IOException, SAXException, ParserConfigurationException {

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

        HttpResponse response = httpClient.execute(httpGet);

        int httpStatus = response.getStatusLine().getStatusCode();

        // Get hold of the response entity and read out the response to clear the socket
        HttpEntity entity = response.getEntity();

        Header encoding = entity.getContentEncoding();
        if (encoding != null && "gzip".equals(encoding.getValue())) {
//            InputStream gzipStream = new GZIPInputStream(entity.getContent());
//            Reader decoder = new InputStreamReader(gzipStream, encoding.getValue());
            return parseToXmlDoc(new GZIPInputStream(entity.getContent()), encoding.getValue());
        }

        return parseToXmlDoc(entity.getContent());
    }

    protected static String[] httpGetWithStringResponse(String url, List<NameValuePair> formParams) throws IOException {
        String[] r;

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
            if (query.length() == 0) {
                query.append("?");
            }
            else {
                query.append("&");
            }
            query.append(pair.getName()).append("=").append(URLEncoder.encode(pair.getValue(), "utf-8"));
        }

        HttpGet get = new HttpGet(url + query);
        HttpResponse response = httpClient.execute(get);

        int httpStatus = response.getStatusLine().getStatusCode();

        // Get hold of the response entity and read out the response to clear the socket
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            List<String> lines = new LinkedList<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                String l = line.trim();
                if (!"".equals(l)) {
                    lines.add(l);
                }
            }

            r = new String[lines.size()];
            lines.toArray(r);
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }

        return r;
    }

    protected static String[] httpPostWithStringResponse(String url, List<NameValuePair> formParams) throws IOException {
        String[] r;

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
        HttpResponse response = httpclient.execute(httppost);

        int httpStatus = response.getStatusLine().getStatusCode();

        // Get hold of the response entity and read out the response to clear the socket
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            List<String> lines = new LinkedList<String>();
            String line;
            while ((line = reader.readLine()) != null) {
                String l = line.trim();
                if (!"".equals(l)) {
                    lines.add(l);
                }
            }

            r = new String[lines.size()];
            lines.toArray(r);
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }

        return r;
    }

    public boolean supportsPhase(PonyPhase phase) {
        if (phase == PonyPhase.POST) {
            return true;
        }

        return false; // default is: we only support post
    }

    public Disposition ping(Long leadMatchId, PingContext pingContext, RoutingCandidate candidate) {
        return Disposition.createPing(Disposition.Status.UNSUPPORTED);
    }

    public Disposition requestPrice(PonyPhase phase, ValidationResponse validationResponse, RoutingCandidate candidate, Lead lead, Long leadMatchId) {
        return Disposition.create(phase, Disposition.Status.NO_COVERAGE, candidate.getIo().isExclusive(), candidate.getIo().getVpl());
    }

    public abstract Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate);

    public void setProperties(String name, Properties writerProperties) {
    	this.name = name;
    	this.writerProperties = writerProperties;
    }
    
    /**
     * Parse the content of the provided String to a map of key value pairs.
     * <p/>
     * Note: the provided String is assumed to have the folowing format:
     * key1=value1;key2=value2;....
     *
     * @param sourceId the string to parse
     * @return a Map of <String,String>
     */
    public static Map<String, String> parseStringToMap(String sourceId) {
        Map<String, String> names = new HashMap<String, String>();

        if (sourceId == null) {
            return names;
        }

        // the source has the form: key=value;key=value;key=value;....
        StringTokenizer tokens = new StringTokenizer(sourceId, ";");
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            int index = token.indexOf("=");
            if (index > 0 && index < token.length()) {
                String key = token.substring(0, index);
                String value = token.substring(index + 1);
                names.put(key.trim(), value.trim());
            }
        }

        return names;
    }

    public static Long logPingMessage(Long leadMatchId, String message) {
        try {
            return LeadMatchModel.persistMessage(leadMatchId, PonyPhase.PING, message);
        }
        catch (NamingException e) {
            LOG.error(e);
        }
        catch (SQLException e) {
            LOG.error(e);
        }

        return null;
    }

    public static Long logPhaseMessage(Long leadMatchId, PonyPhase phase, String message) {
        try {
            return LeadMatchModel.persistMessage(leadMatchId, phase, message);
        }
        catch (NamingException e) {
            LOG.error(e);
        }
        catch (SQLException e) {
            LOG.error(e);
        }

        return null;
    }

    public static void logResponseMessage(Long msgId, String[] responseMsg) {
        StringBuilder response = new StringBuilder();
        for (String l : responseMsg) {
            response.append(l).append("\r\n");
        }

        logResponseMessage(msgId, response.toString());
    }

    public static void logResponseMessage(Long msgId, String responseMsg) {
        try {
            // record the raw response
            LeadMatchModel.persistMessageResponse(msgId, responseMsg);
        }
        catch (NamingException e) {
            LOG.error(e);
        }
        catch (SQLException e) {
            LOG.error(e);
        }
    }

    public static Long logPostMessage(Long leadMatchId, String message) {
        try {
            return LeadMatchModel.persistMessage(leadMatchId, PonyPhase.POST, message);
        }
        catch (NamingException e) {
            LOG.error(e);
        }
        catch (SQLException e) {
            LOG.error(e);
        }

        return null;
    }

    public static Long logPostMessage(Long leadMatchId, String url, List<NameValuePair> formParams) {
        StringBuilder msg = new StringBuilder();
        msg.append("lead post to[").append(url).append("]\r\n");
        for (NameValuePair pair : formParams) {
            msg.append(pair.getName()).append("=").append(pair.getValue()).append("\r\n");
        }

        return logPhaseMessage(leadMatchId, PonyPhase.POST, msg.toString());
    }

    public static Long logPingMessage(Long leadMatchId, String url, List<NameValuePair> formParams) {
        StringBuilder msg = new StringBuilder();
        msg.append("lead ping to[").append(url).append("]\r\n");
        for (NameValuePair pair : formParams) {
            msg.append(pair.getName()).append("=").append(pair.getValue()).append("\r\n");
        }

        return logPhaseMessage(leadMatchId, PonyPhase.PING, msg.toString());
    }

    public static String serialize(Document doc) {
        try {
            OutputFormat format = new OutputFormat(doc);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(doc);
            return out.toString();
        }
        catch (IOException e) {
            LOG.error(e);
        }

        return null;
    }

    public static Document parseToXmlDoc(String response) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder;
        Document doc;

        builder = factory.newDocumentBuilder();
        doc = builder.parse(new ByteArrayInputStream(response.trim().getBytes("UTF-8")));
        return doc;
    }

    public static Document parseToXmlDoc(InputStream in, String encoding) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder;
        Document doc;

        builder = factory.newDocumentBuilder();
        doc = builder.parse(in, encoding);
        return doc;

    }

    public static Document parseToXmlDoc(InputStream in) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder;
        Document doc;

        builder = factory.newDocumentBuilder();
        doc = builder.parse(in);
        return doc;
    }
}
