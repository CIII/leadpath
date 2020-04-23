package com.tapquality.processors.whitepages;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pony.PonyException;

@Singleton
public class WhitepagesDAO {
	private static final String URL = "https://proapi.whitepages.com/3.3/lead_verify.json";
	private static final Log LOG = LogFactory.getLog(WhitepagesDAO.class);

	@Inject	@Named("whitepages.apikey") String apiKey;
	
	public WhitepagesDAO() {}
		
	public WhitepagesDAO(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public WhitepagesResponse query(WhitepagesRequest request) throws PonyException {
		
		// Construct the query string from the request
	    Map<String, Method> parameterNames = new HashMap<>();
	    try {
	    	parameterNames.put("name", WhitepagesRequest.class.getMethod("getFirstName"));
	    	parameterNames.put("firstname", WhitepagesRequest.class.getMethod("getFirstName"));
	    	parameterNames.put("lastName", WhitepagesRequest.class.getMethod("getLastName"));
	    	parameterNames.put("phone", WhitepagesRequest.class.getMethod("getPhone"));
	    	parameterNames.put("email_address", WhitepagesRequest.class.getMethod("getEmailAddress"));
	    	parameterNames.put("ip_address", WhitepagesRequest.class.getMethod("getIpAddress"));
	    	parameterNames.put("address.street_line_1", WhitepagesRequest.class.getMethod("getAddressStreet1"));
	    	parameterNames.put("address.street_line_2", WhitepagesRequest.class.getMethod("getAddressStreet2"));
	    	parameterNames.put("address.city", WhitepagesRequest.class.getMethod("getAddressCity"));
	    	parameterNames.put("address.post_code", WhitepagesRequest.class.getMethod("getAddressPostalCode"));
	    	parameterNames.put("address.state_code", WhitepagesRequest.class.getMethod("getAddressStateCode"));
	    	parameterNames.put("address.country_code", WhitepagesRequest.class.getMethod("getAddressCountryCode"));
	    } catch (NoSuchMethodException | SecurityException e) {
	    	String errMsg = "Failure to retrieve method objects from WhitepagesRequest. This represents a compile-time error.";
			LOG.error(errMsg, e);
			throw new PonyException(errMsg, e);
		}
	    
	    StringBuilder url = new StringBuilder(URL);
	    url.append("?");
	    url.append("api_key=");
	    url.append(this.apiKey);
	    for(String parameter : parameterNames.keySet()) {
	    	Method getter = parameterNames.get(parameter);
	    	try {
	    		Object value = getter.invoke(request);
	    		if(value != null) {
	    			url.append("&");
	    			url.append(parameter);
	    			url.append("=");
	    			url.append(URLEncoder.encode(value.toString(), "UTF-8"));
	    		}
	    	} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | UnsupportedEncodingException e) {
				String errMsg = "Failure to invoke method on the request. This represents a compile-time error that persisted to runtime.";
				LOG.error(errMsg, e);
				throw new PonyException(errMsg, e);
			}
	    }

		// TODO: Make the request
	    try {
	    	HttpGet getRequest = new HttpGet(url.toString());
	    	HttpClient client = new DefaultHttpClient();
	    	HttpResponse response = client.execute(getRequest);
	    	HttpEntity responseEntity = response.getEntity();
	    	String responseString = EntityUtils.toString(responseEntity);
	    
	    	// Construct the WhitepagesResposne from the response string.
	    	WhitepagesResponse wpResponse = new ObjectMapper().readValue(responseString, WhitepagesResponse.class);
	    	return wpResponse;
	    } catch (ClientProtocolException e) {
			String errMsg = "Failure to call whitepages.";
			LOG.error(errMsg, e);
			throw new PonyException(errMsg, e);
	    } catch (JsonParseException | JsonMappingException e) {
	    	String errMsg = "Failure after querying whitepages. Data was retrieved, but it could not be parsed.";
	    	LOG.error(errMsg, e);
	    	throw new PonyException(errMsg, e);
		} catch (IOException e) {
			String errMsg = "Unknown failure during the query to whitepages. This could have been raised in a number of places during the query.";
			LOG.error(errMsg, e);
			throw new PonyException(errMsg, e);
		}
	}
}
