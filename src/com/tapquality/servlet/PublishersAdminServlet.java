package com.tapquality.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pony.PonyException;
import com.pony.models.PublisherModel;
import com.pony.publisher.Publisher;
import com.tapquality.db.serializers.PublishersRecordSerializer;
import com.tapquality.db.tables.records.PublishersRecord;

@Singleton
@SuppressWarnings("serial")
public class PublishersAdminServlet extends TapQualityServlet {
	private static final Log LOG = LogFactory.getLog(PublishersAdminServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getRequestURI().replace(req.getServletPath(), "");
		switch (path) {
		case "":
		case "/true":
			try {
				List<Map<String, String>> publishers = PublisherModel.getAllPublishers(true);
				JSONArray publisherArray = new JSONArray(publishers);
			
				writeData(resp, publisherArray);
			} catch (PonyException e) {
				LOG.error("Error retrieving publishers for administration.", e);
				resp.setStatus(500);
			}
			break;
		default:
			// TODO: Get a specific advertiser
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String baseURL = req.getRequestURI().replace(req.getServletPath(),  "");
		String [] parts = baseURL.split("\\/");
		
		switch (parts[1]) {
		case "create":
			try {
				JSONObject body = parseRequestBody(req);
				Publisher publisher = createPublisher(body);
				PublishersRecord publisherData = PublisherModel.persistPublisher(publisher);
				writeData(resp, publisherData);
			} catch (PonyException | IllegalArgumentException e) {
				String errMsg = "Exception persisting the publisher data.";
				LOG.error(errMsg, e);
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resp.getWriter().println("{\"errors\": [\"" + e.getMessage() + "\"]}");
			} catch (JSONException e) {
				String errMsg = "Exception parsing the publisher data from the client. Aborting and not saving.";
				LOG.error(errMsg, e);
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resp.getWriter().println("{\"errors\": [\"" + e.getMessage() + "\"]}");
			}
			break;
		}
	}
	
	private void writeData(HttpServletResponse resp, JSONArray publisherArray) throws IOException {
		writeData(resp, publisherArray.toString());
	}
	
	/*private void writeData(HttpServletResponse resp, JSONObject publisherObject) throws IOException {
		writeData(resp, publisherObject.toString());
	}*/
	
	@Override
	protected ObjectMapper getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(PublishersRecord.class, new PublishersRecordSerializer());
		mapper.registerModule(module);
		return mapper;
	}

	private Publisher createPublisher(JSONObject body) throws JSONException {
		return this.createPublisher(body, body.isNull("id") ? 0 : body.getLong("id"));
	}

	private Publisher createPublisher(JSONObject body, Long publisherId) throws JSONException {
		Publisher publisher = null;
		// TODO: Handle null pointers
		String password;
		if (body.isNull("password")) {
			password = null;
		} else {
			password = body.getString("password");
		}
		String domainToken;
		if (body.isNull("domain_token")) {
			domainToken = null;
		} else {
			domainToken = body.getString("domain_token");
		}
		String domainName;
		if (body.isNull("domain_name")) {
			domainName = null;
		} else {
			domainName = body.getString("domain_name");
		}
		String userName;
		if (body.isNull("user_name")) {
			userName = null;
		} else {
			userName = body.getString("user_name");
		}
		String name;
		if (body.isNull("name")) {
			name = null;
		} else {
			name = body.getString("name");
		}
		Boolean allowDuplicates;
		if (body.isNull("allow_duplicates")) {
			allowDuplicates = null;
		} else {
			allowDuplicates = body.getBoolean("allow_duplicates");
		}
		Boolean extendedValidation;
		if (body.isNull("extended_validation")) {
			extendedValidation = null;
		} else {
			extendedValidation = body.getBoolean("extended_validation");
		}
		publisher = new Publisher(publisherId == null ? 0 : publisherId, name, allowDuplicates, extendedValidation, domainName, userName, password, domainToken);
		return publisher;
	}

	private JSONObject parseRequestBody(HttpServletRequest req) throws IOException, JSONException {
		StringBuilder bodyBuilder = getBodyText(req);
		JSONObject body = new JSONObject(bodyBuilder.toString());
		return body;
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String baseURL = req.getRequestURI().replace(req.getServletPath(),  "");
		String [] parts = baseURL.split("\\/");
		String advertiserIdString = parts[1];
		Long advertiserId;
		try {
			advertiserId = Long.parseLong(advertiserIdString);
		} catch (NumberFormatException e) {
			throw new ServletException("Publisher ID is not in the form of a number.");
		}
		
		try {
			JSONObject body = parseRequestBody(req);
			Publisher publisher = createPublisher(body, advertiserId);
			//List<Integer> orders = getOrders(body);
			PublishersRecord publisherData = PublisherModel.persistPublisher(publisher);
			writeData(resp, publisherData);
		} catch (PonyException e) {
			String errMsg = "Exception persisting the publisher data.";
			LOG.error(errMsg, e);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().println("{\"errors\": [\"" + errMsg + "\"]}");
		} catch (JSONException e) {
			String errMsg = "Exception parsing the publisher data from the client. Aborting and not saving.";
			LOG.error(errMsg, e);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().println("{\"errors\": [\"" + errMsg + "\"]}");
		}
	}

	private List<Integer> getOrders(JSONObject body) throws JSONException {
		List<Integer> orders = new ArrayList<>();
		if (body.isNull("orders")) {
			JSONArray ordersArray = body.getJSONArray("orders");
			int length = ordersArray.length();
			for (int i = 0; i < length; i++) {
				Integer orderInt = ordersArray.getInt(i);
				orders.add(orderInt);
			}
		}
		
		return orders;
	}
}
