package com.pony.advertiser.writers.solar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.Disposition.DispositionCategory;
import com.pony.advertiser.Io;
import com.pony.advertiser.RoutingCandidate;
import com.pony.lead.Lead;
import com.pony.publisher.IPublisherContext;
import com.pony.validation.ValidationResponse;

public class MailchimpWriter extends AdvertiserWriter {
	protected static String URL = "https://mandrillapp.com/api/1.0/messages/send-template.json";
	protected static Log LOG = LogFactory.getLog(MailchimpWriter.class);
	protected static final Pattern phonePattern = Pattern.compile("(\\d\\d\\d)(\\d\\d\\d)(\\d\\d\\d\\d)");
	
	@Override
	public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse,
			RoutingCandidate candidate) {
		LOG.debug("Lead Match ID: " + leadMatchId);
		// Retrieve the order to get the email address and order name
		Io order = candidate.getIo();
		
		// Dissect the lead into the mergevars
		JSONObject postBody;
		try {
			postBody = constructJSON(publisherContext, order);
		} catch (JSONException e) {
			LOG.error("Exception constructing JSON message to Mandrill.", e);
			return Disposition.createPost(Disposition.Status.REJECTED, Disposition.DispositionCategory.ERROR, e.getMessage());
		}

		// Submit the lead to Mandrill
		HttpResponse response;
		String postBodyString = postBody.toString();
		Long msgId = logPostMessage(leadMatchId, postBodyString);
		try {
			response = postToMandrill(postBodyString);
		} catch (ClientProtocolException e) {
			LOG.error("Exception posting a lead to Mandrill.", e);
			logResponseMessage(msgId, "Exception posting a lead to Mandrill: " + e.toString());
			return Disposition.createPost(Disposition.Status.REJECTED, Disposition.DispositionCategory.ERROR, "Exception posting the response to Mandrill. " + e.getMessage());
		} catch (IOException e) {
			LOG.error("Exception posting a lead to Mandrill.", e);
			logResponseMessage(msgId, "Exception posting a lead to Mandrill: " + e.toString());
			return Disposition.createPost(Disposition.Status.REJECTED, Disposition.DispositionCategory.ERROR, "Exception posting the response to Mandrill. " + e.getMessage());
		}
		
		String returnBody = null;
		try {
			if(response.getStatusLine().getStatusCode() == 200) {
				returnBody = EntityUtils.toString(response.getEntity());
				logResponseMessage(msgId, returnBody);
				JSONArray returnMessage = new JSONArray(returnBody);
				JSONObject returnItem = returnMessage.getJSONObject(0);
				String returnStatus = returnItem.getString("status");
				if(returnStatus == "rejected" || returnStatus == "bounced") {
					return Disposition.createPost(Disposition.Status.REJECTED, Disposition.DispositionCategory.ERROR, returnBody);
				} else {
					return Disposition.createPost(Disposition.Status.ACCEPTED, null, null, candidate.getIo().getVpl(), returnBody);
				}
			} else {
				logResponseMessage(msgId, "Non 200 response from Mandrill: " + response.getStatusLine().getStatusCode());
				return Disposition.createPost(Disposition.Status.REJECTED, DispositionCategory.ERROR, EntityUtils.toString(response.getEntity()));
			}
		} catch (IOException e) {
			LOG.error("Exception retrieving the response from Mandrill); the disposition was not created but the email may have been sent.", e);
			return Disposition.createPost(Disposition.Status.REJECTED, Disposition.DispositionCategory.ERROR, e.getMessage());
		} catch (JSONException e) {
			LOG.error("Exception retrieving the response from Mandrill); the disposition was not created but the email may have been sent.", e);
			return Disposition.createPost(Disposition.Status.REJECTED, Disposition.DispositionCategory.ERROR, e.getMessage());
		}
	}

	JSONObject constructJSON(IPublisherContext publisherContext, Io order) throws JSONException {
		Map<String, JSONArray> message = new HashMap<String, JSONArray>();
		// recipient
		List<JSONObject> recipients = new ArrayList<JSONObject>();
		String[] emails = order.getEmail().split(",");
		for(String email : emails) {
			Map<String, String> recipient = new HashMap<String, String>();
			recipient.put("email", email);
			JSONObject recipientObject = new JSONObject(recipient);
			recipients.add(recipientObject);
		}
		//recipient.put("name", order.getCode());
		JSONArray recipientsArray = new JSONArray(recipients);
		message.put("to", recipientsArray);
		
		Lead lead = publisherContext.getLead();
		List<JSONObject> mergeVars = new ArrayList<JSONObject>();
		for(String variable : lead.getAttributeNames()) {
			Map<String, String> var = new HashMap<String, String>();
			if(lead.getAttributeValue(variable) != null) {
				String value;
				if("phone_home".equals(variable)) {
					String tempValue = lead.getAttributeValue(variable);
					Matcher phoneMatcher = phonePattern.matcher(tempValue);
					if(phoneMatcher.matches()) {
						value = "(" + phoneMatcher.group(1) + ") " + phoneMatcher.group(2) + "-" + phoneMatcher.group(3); 
					} else {
						value = tempValue;
					}
				} else {
					value = lead.getAttributeValue(variable);
				}
				var.put("name", variable);
				var.put("content", value);
				mergeVars.add(new JSONObject(var));
			}
		}
		Map<String, String> var = new HashMap<String, String>();
		var.put("name", "lead_email");
		var.put("content", publisherContext.getUserProfile().getEmail());
		mergeVars.add(new JSONObject(var));
		JSONArray mergeVarsArray = new JSONArray(mergeVars);
		message.put("global_merge_vars", mergeVarsArray);
		JSONObject messageObject = new JSONObject(message);
		messageObject.put("track_opens", Boolean.TRUE);
		
		Map<String, String> envelope = new HashMap<String, String>();
		envelope.put("key", "hSaI0j3dVCSILJPVA_gEwA");
		envelope.put("template_name", "lead-delivery-011117");
		JSONObject envelopeObject = new JSONObject(envelope);
		envelopeObject.put("template_content", new JSONArray());
		envelopeObject.put("message", messageObject);

		return envelopeObject;
	}

	protected HttpResponse postToMandrill(String body) throws ClientProtocolException, IOException {
		return postToMandrill(body, URL, new DefaultHttpClient());
	}
	
	HttpResponse postToMandrill(String body, String url, HttpClient client) throws ClientProtocolException, IOException  {
		LOG.debug("Body: " + body);
		HttpPost httppost = new HttpPost(url);
		StringEntity postBody = new StringEntity(body);
		httppost.setEntity(postBody);
		return client.execute(httppost);
	}
}
