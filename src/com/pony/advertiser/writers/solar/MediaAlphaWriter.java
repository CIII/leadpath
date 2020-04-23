package com.pony.advertiser.writers.solar;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
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
import com.pony.advertiser.Buyer;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.Disposition.DispositionCategory;
import com.pony.advertiser.Disposition.Status;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.models.ArrivalModel;
import com.pony.models.UserProfileModel;
import com.pony.models.UserProfileModelImpl;
import com.pony.publisher.IPublisherContext;
import com.pony.validation.ValidationResponse;

public class MediaAlphaWriter extends AdvertiserWriter {
	@Inject @Named("mediaalpha.url") String url;
	@Inject @Named("mediaalpha.apitoken") String apiToken;
	@Inject @Named("mediaalpha.placementid.digital") String digitalPlacementId;
	@Inject @Named("mediaalpha.placementid.search") String searchPlacementId;
	
	protected final ArrivalModel arrivalModel;
	protected final UserProfileModel userProfileModel;
	
	@Inject MediaAlphaWriter(ArrivalModel arrivalModel, UserProfileModel userProfileModel) {
		this.arrivalModel = arrivalModel;
		this.userProfileModel = userProfileModel;
	}
	
	@Override
	public Disposition requestPrice(PonyPhase phase, ValidationResponse validationResponse, RoutingCandidate candidate,
			Lead lead, Long leadMatchId) {
		LOG.info("In request price: " + url);
		JSONObject message = null;
		Disposition returnDisposition;
		String errorText;
		try {
			message = generatePingMessage(lead);
			JSONObject leadDescriptor = generateLeadDescriptor(null, lead);
			message.put("data", leadDescriptor);
		
			String requestBody = message.toString();
			LOG.info(requestBody);
			//Long msgId = logPhaseMessage(candidate.getLeadMatchId(), PonyPhase.REQUEST_PRICE, requestBody);
			HttpResponse response = postToMediaAlpha(requestBody, "/ping.json");
			HttpEntity responseEntity = response.getEntity();
			String responseBody = EntityUtils.toString(responseEntity);
			LOG.info(responseBody);
			//logResponseMessage(msgId, responseBody);
			JSONObject responseObject = new JSONObject(responseBody);

			if(!responseObject.has("error")) {
				returnDisposition = Disposition.create(phase, Disposition.Status.ACCEPTED, candidate.getIo().isExclusive(), candidate.getIo().getVpl(), responseObject.getString("ping_id"), responseBody);
				if(responseObject.has("buyers")) {
					JSONArray buyers = responseObject.getJSONArray("buyers");
				
					for(int index = 0; index < buyers.length(); index++) {
						JSONObject buyerObject = buyers.getJSONObject(index);
						Buyer buyer = Buyer.create(buyerObject.getString("buyer"), buyerObject.getString("buyer"), null);
						buyer.setBuyerCode(buyerObject.getString("bid_id"));
						buyer.setPrice(new BigDecimal(buyerObject.getDouble("bid")));
						if(buyerObject.getString("type").equals("exclusive")) buyer.setMaxPosts("1"); else buyer.setMaxPosts(null);
						returnDisposition.addBuyer(buyer);
					}
				}
				returnDisposition.setLeadMatchId(candidate.getLeadMatchId());
			} else {
				returnDisposition = Disposition.create(phase, Disposition.Status.REJECTED, DispositionCategory.ERROR, responseBody);
			}
						
			return returnDisposition;
		} catch (IllegalArgumentException e) {
			LOG.error(errorText = "Error assembling the message to mediaalpha.", e);
		} catch (SQLException e) {
			LOG.error(errorText = "Error assembling the message to mediaalpha.", e);
		} catch (NamingException e) {
			LOG.error(errorText = "Error assembling the message to mediaalpha.", e);
		} catch (JSONException e) {
			LOG.error(errorText = "Error assembling the message to mediaalpha.", e);
		} catch (ClientProtocolException e) {
			LOG.error(errorText = "Error sending message to mediaalpha.", e);
		} catch (IOException e) {
			LOG.error(errorText = "Error sending message to mediaalpha.", e);
		}
		return Disposition.create(phase, Disposition.Status.REJECTED, DispositionCategory.ERROR, errorText);
	}

	private JSONObject generateLeadDescriptor(IPublisherContext context, Lead lead) throws SQLException, NamingException, JSONException {
		UserProfile userProfile;
		if(context != null) {
			userProfile = context.getUserProfile();
			LOG.debug("Getting the user profile from the context. Presumably this is a post. ID: " + userProfile != null ? userProfile.getId() : "null");
		} else {
			LOG.debug("Getting the user profile from the lead. Presumably this is a requestPrice. userProfileId: " + lead.getUserProfileId());
			userProfile = UserProfileModelImpl.findStatic(lead.getUserProfileId());
		}
		Map<String, String> leadMap = new HashMap<>();
		leadMap.put("zip", lead.getAttributeValue("zip"));
		leadMap.put("contact", lead.getAttributeValue("first_name") + " " + lead.getAttributeValue("last_name"));
		leadMap.put("email", userProfile.getEmail());
		String rawPhone = lead.getAttributeValue("phone_home");
		String phone = "(" + rawPhone.substring(0, 3) + ") " + rawPhone.substring(3, 6) + "-" + rawPhone.substring(6);
		leadMap.put("phone", phone);
		String street = lead.getAttributeValue("street");
		leadMap.put("address", street);
		leadMap.put("property_type", "Single Family Home Detached"); // TODO: This is hard-coded per Greg's instructions until we gather this.
		leadMap.put("roof_shade", "No shade"); // TODO: This is hard-coded per Greg's instructions until we gather this.
		leadMap.put("utility_company", lead.getAttributeValue("electric_company"));
		leadMap.put("leadid_id", lead.getAttributeValue("leadid_token"));
		// TODO: The TCPA stuff is hard-coded; it would be nice to get this submitted from Lynx so it stays up-to-date if we change it.
		Map<String, String> tcpaMap = new HashMap<>();
		tcpaMap.put("text", "By clicking the submit button, you agree to our privacy policy and authorize Easier Solar and its network of service providers to contact you at the phone number you entered using automated technology including auto-dialers, pre-recorded messages, and text messages, even if your phone is a mobile number or is currently listed on any state, federal, or corporate \"Do Not Call\" lists, and you are not required to give your consent as a condition of service.");
		tcpaMap.put("url", "http://www.easiersolar.com/privacy-policy");
		JSONObject tcpa = new JSONObject(tcpaMap);
		tcpa.put("call_consent", 1);
		tcpa.put("sms_consent", 1);
		JSONObject returnValue = new JSONObject(leadMap);
		returnValue.put("home_ownership", "OWN".equals(lead.getAttributeValue("property_ownership")) ? 1 : 0);
		String electricBill = lead.getAttributeValue("electric_bill");
		returnValue.put("electricity_bill", Integer.parseInt(electricBill.contains("-") ? electricBill.substring(electricBill.indexOf('-') + 1) : electricBill.replace("$", "").replace("+", "")));
		returnValue.put("tcpa", tcpa);
		return returnValue;
	}

	private JSONObject generatePingMessage(Lead lead) throws SQLException, NamingException, JSONException {
		Arrival arrival = this.arrivalModel.find(lead.getArrivalId());
		Map<String, String> messageMap = new HashMap<>();
		messageMap.put("api_token", this.apiToken);
		messageMap.put("placement_id", getPlacement(lead));
		messageMap.put("version", "18");
		Date currentTime = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messageMap.put("date_time", df.format(currentTime));
		messageMap.put("ip", lead.getAttributeValue("ip"));
		messageMap.put("ua", arrival.getUserAgent());
		messageMap.put("url", "http://www.easiersolar.com"); // TODO: This is us.
		messageMap.put("sub_1", "" + lead.getId());
		JSONObject returnValue = new JSONObject(messageMap);
		String localHourString = lead.getAttributeValue("local_hour");
		int localHour;
		Calendar easternTimeZone = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		if(localHourString != null) {
			try {
				localHour = Integer.parseInt(localHourString);
			} catch (NumberFormatException e) {
				localHour = easternTimeZone.get(Calendar.HOUR_OF_DAY);
			}
		} else {
			localHour = easternTimeZone.get(Calendar.HOUR_OF_DAY);
		}
		returnValue.put("local_hour", localHour);
		return returnValue;
	}
	
	private JSONObject generatePostMessage(RoutingCandidate candidate, Lead lead) throws JSONException {
		Map<String, String> messageMap = new HashMap<>();
		Disposition priceDisposition = candidate.getResponseDisposition(PonyPhase.REQUEST_PRICE);
		messageMap.put("api_token", this.apiToken);
		messageMap.put("placement_id", getPlacement(lead));
		messageMap.put("ping_id", priceDisposition.getExternalId());
		JSONObject returnValue = new JSONObject(messageMap);
		JSONArray bidArray = new JSONArray();
		bidArray.put(candidate.getBuyer().getBuyerCode());
		returnValue.put("bid_ids", bidArray);
		returnValue.put("version", 18);
		
		return returnValue;
	}

	private static final Log LOG = LogFactory.getLog(MediaAlphaWriter.class);

	@Override
	public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse,
			RoutingCandidate candidate) {
		try {
			Disposition priceDisposition = candidate.getResponseDisposition(PonyPhase.REQUEST_PRICE);
			if(priceDisposition == null) 
				return Disposition.createPost(Status.REJECTED, DispositionCategory.ERROR, null, null, "A request price disposition is required to post to MediaAlpha.");
			JSONObject request = generatePostMessage(candidate, publisherContext.getLead());
			JSONObject leadDescriptor = generateLeadDescriptor(publisherContext, publisherContext.getLead());
			request.put("data", leadDescriptor);
			LOG.debug(request.toString());
			Long msgId = logPostMessage(leadMatchId, request.toString());
			HttpResponse response = postToMediaAlpha(request.toString(), "/post.json");
			HttpEntity responseEntity = response.getEntity();
			String responseBody = EntityUtils.toString(responseEntity);
			logResponseMessage(msgId, responseBody);
			LOG.debug(responseBody);
			JSONObject responseObject = new JSONObject(responseBody);
			if("failed".equals(responseObject.getString("status"))) {
				JSONArray buyerResponses = responseObject.getJSONArray("buyers");
				JSONObject buyerObject = buyerResponses.getJSONObject(0);
				return Disposition.createPost(Status.REJECTED, DispositionCategory.ERROR, null, null, buyerObject.toString());
			} else {
				String leadidToken = publisherContext.getLead().getAttributeValue("leadid_token");
				return Disposition.createPost(Status.ACCEPTED, null, leadidToken, candidate.getBuyer().getPrice(), null); // TODO: The price is set wrong.
			}
		} catch (JSONException | IOException | NamingException | SQLException e) {
			return Disposition.createPost(Status.REJECTED, DispositionCategory.ERROR, null, null, e.getMessage());
		}
	}
	
	protected HttpResponse postToMediaAlpha(String body, String urlResource) throws ClientProtocolException, IOException {
		return postToMediaAlpha(body, this.url + urlResource, new DefaultHttpClient());
	}
	
	HttpResponse postToMediaAlpha(String body, String url, HttpClient client) throws ClientProtocolException, IOException  {
		LOG.debug("Body: " + body);
		HttpPost httppost = new HttpPost(url);
		StringEntity postBody = new StringEntity(body);
		httppost.setEntity(postBody);
		httppost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		return client.execute(httppost);
	}

	@Override
	public boolean supportsPhase(PonyPhase phase) {
		boolean returnValue = super.supportsPhase(phase);
		if(!returnValue) {
			if(phase == PonyPhase.REQUEST_PRICE) {
				returnValue = true;
			}
		}
		
		return returnValue;
	}
	
	private String getPlacement(Lead lead) {
		String medium = lead.getAttributeValue("utm_medium");
		if("sem".equals(medium)) {
			return this.searchPlacementId;
		} else {
			return this.digitalPlacementId;
		}
	}

}
