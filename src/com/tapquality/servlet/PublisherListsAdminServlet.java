package com.tapquality.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.Result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pony.PonyException;
import com.pony.models.PublisherListModel;
import com.pony.publisher.PublisherList;
import com.tapquality.db.deserializers.PublisherListDeserializer;
import com.tapquality.db.serializers.PublisherListMembersRecordSerializer;
import com.tapquality.db.serializers.PublisherListOrdersRecordSerializer;
import com.tapquality.db.serializers.PublisherListsRecordSerializer;
import com.tapquality.db.tables.records.PublisherListMembersRecord;
import com.tapquality.db.tables.records.PublisherListOrdersRecord;
import com.tapquality.db.tables.records.PublisherListsRecord;

@Singleton
@SuppressWarnings("serial")
public class PublisherListsAdminServlet extends TapQualityServlet {
	private static final Log LOG = LogFactory.getLog(PublisherListsAdminServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getRequestURI().replace(req.getServletPath(), "");
		switch (path) {
		case "":
		case "/true":
			try {
				List<Map<String, Object>> publisherListArray = new ArrayList<>();
				Result<PublisherListsRecord> publisherLists = PublisherListModel.getAllPublisherLists(false);
				for (PublisherListsRecord record : publisherLists) {
					Map<String, Object> publisherList = new HashMap<>();
					publisherList.put("data", record);
					publisherList.put("orders", PublisherListModel.getAllOrders(record.getId().longValue()));
					publisherList.put("publishers", PublisherListModel.getAllPublishers(record.getId().longValue()));
					publisherListArray.add(publisherList);
				}
				writeData(resp, publisherListArray);
			} catch (PonyException e) {
				String errMsg = "Error retrieving publisher lists for administration.";
				LOG.error(errMsg, e);
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resp.getWriter().println("{\"errors\": [\"" + errMsg + "\"]}");
			}
			break;
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI().replace(req.getServletPath(), "");
		String[] parts = url.split("\\/");
		
		switch (parts[1]) {
		case "create":
			try {
				PublisherList publisherList = parseRequestBody(req);
				PublisherListsRecord publisherListData = PublisherListModel.persistPublisherList(publisherList);
				writeData(resp, publisherListData);
			} catch (PonyException e) {
				String errMsg = "Exception creating a new publisherList.";
				LOG.error(errMsg, e);
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resp.getWriter().println("{\"errors\": [\"" + e.getMessage() + "\"]}");
			}
			break;
		}
	}
	
	private PublisherList parseRequestBody(HttpServletRequest request) throws IOException {
		StringBuilder bodyBuilder = getBodyText(request);
		ObjectMapper mapper = getMapper();
		return mapper.readValue(bodyBuilder.toString(), PublisherList.class);
	}

	@Override
	protected ObjectMapper getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(PublisherList.class, new PublisherListDeserializer());
		module.addSerializer(PublisherListsRecord.class, new PublisherListsRecordSerializer());
		module.addSerializer(PublisherListMembersRecord.class, new PublisherListMembersRecordSerializer());
		module.addSerializer(PublisherListOrdersRecord.class, new PublisherListOrdersRecordSerializer());
		mapper.registerModule(module);
		return mapper;
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI().replace(req.getServletPath(),  "");
		String[] parts = url.split("\\/");

		String publisherListIdString = parts[1];
		Long publisherListId;
		try {
			publisherListId = Long.parseLong(publisherListIdString);
		} catch (NumberFormatException e) {
			String errMsg = "PublisherList ID must be in the form of an integer.";
			LOG.error(errMsg, e);
			throw new ServletException(errMsg, e);
		}
		
		try {
			PublisherList publisherList = parseRequestBody(req);
			publisherList.setId(publisherListId);
			PublisherListsRecord publisherListData = PublisherListModel.persistPublisherList(publisherList);
			Map<String, Object> publisherListObject = new HashMap<>();
			publisherListObject.put("data", publisherListData);
			publisherListObject.put("orders", PublisherListModel.getAllOrders(publisherListData.getId().longValue()));
			publisherListObject.put("publishers", PublisherListModel.getAllPublishers(publisherListData.getId().longValue()));

			writeData(resp, publisherListObject);
		} catch (PonyException e) {
			String errMsg = "Exception persisting the publisher list data.";
			LOG.error(errMsg, e);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().println("{\"errors\": [\"" + e.getMessage() + "\"]}");
		}
	}

}
