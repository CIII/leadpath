package com.tapquality.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pony.PonyException;
import com.pony.models.LeadMatchModel;
import com.pony.models.LeadModel;

@SuppressWarnings("serial")
@Singleton
public class LeadsAdminServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getRequestURI().replace(req.getServletPath(), "");
		LOG.debug("Path: " + path);
		
		try {
			switch(path) {
			case "/partials":
				break;
			case "/invalids":
				break;
			case "/leadMatches":
				break;
			case "/leads":
				// Fetch top 10 leads, ordered by updated_at, reversed
				List<Map<String, Object>> leads = LeadModel.getMostRecentLeads(0, 10);
				List<JSONObject> leadObjects = new ArrayList<>();
				for(Map<String, Object> item : leads) {
					item.put("type", "full-lead");
					switch ((String)item.get("status")) {
					case "0": 
						item.put("type", "not-routed");
						break;
					case "1":
						item.put("type", "rejected");
						break;
					case "2": 
						item.put("type", "routed");
						break;
					}
					JSONObject leadObject = new JSONObject(item);
					List<Map<String, String>> leadMatches = LeadMatchModel.getLeadMatches(Integer.parseInt((String)item.get("id")));
					for(Map<String, String> leadMatch : leadMatches) {
						switch (leadMatch.get("status")) {
						case "4":
							leadMatch.put("type", "success");
							break;
						case "-1":
							leadMatch.put("type", "failed");
							break;
						case "-2":
							leadMatch.put("type", "returned");
							break;
						}
					}
					leadObject.put("lead-matches", new JSONArray(leadMatches));
					leadObjects.add(leadObject);
				}
				JSONArray leadArray = new JSONArray(leadObjects);
				resp.setStatus(200);
				resp.getWriter().write(leadArray.toString());
				break;
			}
		} catch (JSONException e) {
			resp.setStatus(500);
			String errorMsg = "Exception while serializing to JSON.";
			LOG.error(errorMsg, e);
			resp.getWriter().write(errorMsg);
		} catch (PonyException e) {
			resp.setStatus(500);
			String errorMsg = "Exception fetching leads.";
			LOG.error(errorMsg, e);
			resp.getWriter().write(errorMsg);
		}
		
	}

	public static final Log LOG = LogFactory.getLog(LeadsAdminServlet.class);
}
