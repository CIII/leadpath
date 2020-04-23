package com.tapquality.email.subscribe;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import javax.net.ssl.SSLHandshakeException;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pony.PonyException;

/**
 * Services for interacting with MailChimp
 * 
 * @author dmcguire
 *
 */
public class MailChimpClient{
	private static final Log LOG = LogFactory.getLog(MailChimpClient.class);
	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Enumeration to hold the available MailChimp lists that a member may be subscribed to
	 * @author dmcguire
	 *
	 */
	public enum MailChimpList{
		EasierSolarInsights("mailchimp.lists.easiersolarinsights"),
		EasierSolarDeals("mailchimp.lists.easiersolardeals"),
 		SolarProDeals("mailchimp.lists.solarprodeals");
		
		private final String listUrl;
		private final String apiKey;
		
		private MailChimpList(String propertyName){
			try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("mailchimp.properties")){
		        Properties mcProps = new Properties();
		        mcProps.load(is);
		        String mailChimpUrlBase = mcProps.getProperty("mailchimp.url.base");
		        String propKey = mcProps.getProperty(propertyName);
		        listUrl = mailChimpUrlBase + "/lists/" + propKey + "/members";
		        apiKey = mcProps.getProperty("mailchimp.apikey");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		public String getUrl(){
			return listUrl;
		}
		public String getApiKey(){
			return apiKey;
		}
	}
	
	/**
	 * Add a MailChimp member to a subscription list.  Post url is specified by {@link #MailChimpClient.MailChimpList}
	 * 
	 * @param member
	 * @param list
	 * @return HttpResponse body
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws PonyException 
	 * @throws ParseException 
	 */
	public String addMemberToList(MailChimpMember member, MailChimpList list) throws ClientProtocolException, IOException, ParseException, PonyException{
		if(member.getEmailAddress() == null || member.getStatus() == null){
			throw new IllegalArgumentException("Not a valid member - email and status are required");
		}
		LOG.info("subscribing user to MailChimp: " + member.getEmailAddress());
		String memberStr = mapper.writeValueAsString(member);
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.reset();
			md5.update(member.getEmailAddress().toLowerCase().getBytes());
			BigInteger hash = new BigInteger(1, md5.digest());
			HttpResponse mcResponse = executePut(list.getUrl() + "/" + hash.toString(16), list.getApiKey(), memberStr);
			if(mcResponse.getStatusLine().getStatusCode() != HttpServletResponse.SC_OK){
				throw new PonyException("Failed to subscribe to mailchimp: " + EntityUtils.toString(mcResponse.getEntity()));
			}
			
			return EntityUtils.toString(mcResponse.getEntity());
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Failed to find MD5 algorithm.", e);
			throw new PonyException("Failed to find MD5 algorithm.", e);
		}
		
	}
	
	private HttpResponse executePut(String url, String apiKey, String body) throws ClientProtocolException, IOException{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPut httpPut = new HttpPut(url);
		httpPut.setHeader("Authorization", "apiKey " + apiKey);
		httpPut.setEntity(new ByteArrayEntity(body.getBytes()));
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
		return httpClient.execute(httpPut);
	}
}
