package com.tapquality.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pony.PonyException;
import com.pony.advertiser.Advertiser;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.Io;
import com.pony.advertiser.factory.AdvertiserFactory;
import com.pony.models.AdvertiserModel;
import com.pony.models.LeadMatchModel;
import com.tapquality.dispositions.guess.Guess;
import com.tapquality.dispositions.guess.GuessEngine;
import com.tapquality.dispositions.guess.GuessException;

@SuppressWarnings("serial")
@Singleton
public class AdvertisersAdminServlet extends TapQualityServlet {
	private static final Log LOG = LogFactory.getLog(AdvertisersAdminServlet.class);
	@Inject
	protected Map<String, AdvertiserFactory> factories;

	enum PUT_ACTIONS {
		SAVE_ADVERTISER, UPLOAD_DISPOSITIONS, ASSESS_DISPOSITIONS, UNKNOWN
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getRequestURI().replace(req.getServletPath(), "").replace("/", "");
		try {
			Long advertiserId = Long.parseLong(path);
			AdvertiserModel.toggleActivation(advertiserId);
		} catch (NumberFormatException e) {
			LOG.error(path + " is not a valid ID of an advertiser.");
			resp.setStatus(500);
		} catch (PonyException e) {
			LOG.error("Error deactivating advertiser " + path + ".", e);
			resp.setStatus(500);
		}
		resp.setStatus(200);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getRequestURI().replace(req.getServletPath(), "");
		switch (path) {
		case "":
		case "/true":
			try {
				List<Map<String, String>> advertisers = AdvertiserModel.getAllWithOrders("/true".equals(path));
				JSONArray advertiserArray = new JSONArray(advertisers);

				resp.setContentType("application/json");
				resp.getWriter().write(advertiserArray.toString());
			} catch (PonyException e) {
				LOG.error("Error retrieving advertisers for administration.", e);
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
		String baseURL = req.getRequestURI().replace(req.getServletPath(), "");
		String[] parts = baseURL.split("\\/");

		int action;
		if ("create".equals(parts[1])) {
			action = 1;
		} else {
			action = 0;
		}

		switch (action) {
		case 0:
			// Error condition
			break;
		case 1:
			try {
				JSONObject body = parseRequestBody(req);
				Advertiser advertiser = createAdvertiser(body);
				Io io = createOrder(body);
				Map<String, String> advertiserData = AdvertiserModel.persistAdvertiser(advertiser, io);
				writeData(resp, advertiserData);
			} catch (JSONException e) {
				LOG.error("Error extracting the advertiser and order data from the request during creation.", e);
				resp.setStatus(500);
			} catch (PonyException e) {
				LOG.error("Error persisting advertiser during creation.", e);
				resp.setStatus(500);
			}
			break;
		default:
			// TODO: Error condition
			break;
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String baseURL = req.getRequestURI().replace(req.getServletPath(), "");
		String[] parts = baseURL.split("\\/");
		String advertiserIdString = parts[1];
		Long advertiserId;
		try {
			advertiserId = Long.parseLong(advertiserIdString);
		} catch (NumberFormatException e) {
			String errMsg = "The advertiser ID provided was not in the form of a number.";
			LOG.warn(errMsg, e);
			throw new ServletException(errMsg, e);
		}

		PUT_ACTIONS action;
		if (parts.length > 2) {
			switch (parts[2]) {
			case "upload-dispositions":
				action = PUT_ACTIONS.UPLOAD_DISPOSITIONS;
				break;
			case "assess-dispositions":
				action = PUT_ACTIONS.ASSESS_DISPOSITIONS;
				break;
			default:
				action = PUT_ACTIONS.UNKNOWN;
			}
		} else {
			action = PUT_ACTIONS.SAVE_ADVERTISER;
		}

		switch (action) {
		case SAVE_ADVERTISER:
			try {
				JSONObject body = parseRequestBody(req);
				Advertiser advertiser = createAdvertiser(body, advertiserId);
				Io io = createOrder(body);
				Map<String, String> advertiserData = AdvertiserModel.persistAdvertiser(advertiser, io);
				writeData(resp, advertiserData);
			} catch (NumberFormatException e) {
				LOG.error("Error parsing ID " + advertiserId + " during persisting.", e);
				resp.setStatus(500);
			} catch (JSONException e) {
				LOG.error("Error extracting the advertiser and order data from the request during creation.", e);
				resp.setStatus(500);
			} catch (PonyException e) {
				LOG.error("Error persisting advertiser during creation.", e);
				resp.setStatus(500);
			}
			break;
		case UPLOAD_DISPOSITIONS:
			StringBuilder returnMessage = processDispositionUpload(req, advertiserId);
			JSONObject returnValue = new JSONObject();
			try {
				if (returnMessage.length() > 0) {
					resp.setStatus(HttpServletResponse.SC_OK);
					returnValue.put("success", false);
					returnValue.put("errors", returnMessage.toString());
				} else {
					resp.setStatus(HttpServletResponse.SC_OK);
					returnValue.put("success", true);
				}

				resp.getWriter().write(returnValue.toString());

			} catch (JSONException e) {
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resp.getWriter().write("Error serializing response to JSON.");
			}
		case ASSESS_DISPOSITIONS:
			try {
				// Parse out each record
				Iterable<CSVRecord> records = getRecordsFromUpload(req, true);
				CSVRecord headers = null;
				Advertiser advertiser = AdvertiserModel.find(advertiserId);
				AdvertiserFactory factory = this.factories.get(advertiser.getName());
				GuessEngine engine = factory.getDispositionGuessEngine();
				List<Map<String, Object>> columns = new ArrayList<>();
				List<Map<String, Object>> rows = new ArrayList<>();
				for (CSVRecord record : records) {
					if (headers == null) {
						headers = record;
						for (int i = 0; i < record.size(); i++) {
							final String columnName = record.get(i);
							final Boolean isIndexed = engine.isIndexed(record.get(i));
							Map<String, Object> column = new HashMap<String, Object>() {{
								put("name", columnName);
								put("isIndexed", isIndexed);
							}};
							columns.add(column);
						}
						continue;
					}
					List<Object> rowData = new ArrayList<>();
					for (int i = 0; i < record.size(); i++) {
						rowData.add(record.get(i));
					}
					
					Guess guess = engine.guess(headers, record);

					Map<String, Object> row = new HashMap<>();
					row.put("data", rowData);
					row.put("status", guess.getStatus());
					if (guess.getGuess() != null) {
						row.put("guess", guess.getGuess());
					}
					if (guess.getErrorText() != null) {
						row.put("error", guess.getErrorText());
					}
					rows.add(row);
				}
				Map<String, Object> returnData = new HashMap<>();
				returnData.put("columns", columns);
				returnData.put("rows", rows);
				writeData(resp, returnData);
			} catch (FileUploadException e) {
				String errMsg = "Exception parsing records from uploaded a CSV.";
				LOG.warn(errMsg, e);
				throw new ServletException(errMsg, e);
			} catch (SQLException e) {
				String errMsg = "Exception querying the database for the provided advertiser.";
				LOG.warn(errMsg, e);
				throw new ServletException(errMsg, e);
			} catch (NamingException e) {
				String errMsg = "NamingException querying the database. This is the likely a configuration error and must be addressed quickly.";
				LOG.warn(errMsg, e);
				throw new ServletException(errMsg, e);
			} catch (GuessException e) {
				String errMsg = "Exception generating a disposition guess.";
				LOG.warn(errMsg, e);
				throw new ServletException(errMsg, e);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * @param req
	 *            The request
	 * @param advertiserId
	 *            The ID of the advertiser having dispositions recorded to it.
	 * @return The StringBuilder containing the error messages, if any.
	 * @throws IOException
	 * @throws ServletException
	 *             Exceptions that seem to be a functional problems. Data
	 *             problems are recorded in the return value to be reported to
	 *             the user.
	 */
	private StringBuilder processDispositionUpload(HttpServletRequest req, Long advertiserId)
			throws IOException, ServletException {

		StringBuilder returnMessage = new StringBuilder();

		try {
			ObjectMapper objectMapper = getMapper();
			Reader bodyReader = req.getReader();
			StringBuilder bodyBuilder = new StringBuilder();
			char[] buffer = new char[1000];
			int position = 1;
			while ((position = bodyReader.read(buffer, 0, 1000)) > 0) {
				bodyBuilder.append(buffer, 0, position);
			}
			List<Map<String, String>> dispositionsData = objectMapper.readValue(bodyBuilder.toString(), new TypeReference<List<Map<String, String>>>(){});
			
			Advertiser advertiser;
			try {
				advertiser = AdvertiserModel.find(advertiserId);
				if (advertiser == null) {
					throw new NullPointerException("Could not identify advertiser " + advertiserId);
				}
			} catch (SQLException | NamingException e) {
				String errMsg = "Exception retrieving advertiser " + advertiserId + " from the database.";
				recordError(returnMessage, e, errMsg);
				throw new ServletException(errMsg, e);
			}
			
			String advertiserName = advertiser.getName();
			AdvertiserFactory factory = this.factories.get(advertiserName);
			if (factory == null) {
				throw new NullPointerException(
						"The advertiser referenced was not initialized with a factory.");
			}

			for (Map<String, String> dispositionData: dispositionsData) {
				String externalId = "unknown";
				try {
					Disposition newDisposition = factory.getDispositionDataParser().createDisposition(dispositionData, advertiser, factory, returnMessage);
					
					LeadMatchModel.persistDisposition(advertiserId, newDisposition.getLeadMatchId(), newDisposition,
							newDisposition.getPhase());
					
					factory.getDispositionGuessEngine().recordGuess(dispositionData);
				} catch (IllegalArgumentException | PonyException e) {
					recordError(returnMessage, null, e.getMessage());
					continue;/*
				} catch (SQLException | NamingException e) {
					String errMsg = "Exception processing lead match " + externalId
							+ ". Not saved and skipping to the next item.";
					recordError(returnMessage, e, errMsg);
					continue;*/
				} catch (Throwable e) {
					String errMsg = "Unknown exception processing lead match " + externalId
							+ ". Not saved and skipping to the next item.";
					recordError(returnMessage, e, errMsg);
				}
			}

		} finally {}

		return returnMessage;
	}

	private void recordError(StringBuilder returnMessage, Throwable e, String errMsg) {
		if(e == null) {
			LOG.warn(errMsg);
		} else {
			LOG.warn(errMsg, e);
		}
		returnMessage.append(errMsg);
		returnMessage.append("\n");
	}

	private Iterable<CSVRecord> getRecordsFromUpload(HttpServletRequest req, boolean includeHeader)
			throws FileUploadException, IOException {
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location
		// is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		List<FileItem> items = upload.parseRequest(req);

		if (items.size() == 0) {
			String errMsg = "No files were uploaded.";
			throw new IllegalArgumentException(errMsg);
		}
		FileItem item = items.get(0);

		Reader inputReader;
		if (item.isInMemory()) {
			String contents = item.getString();
			inputReader = new StringReader(contents);
		} else {
			InputStream inputStream = item.getInputStream();
			inputReader = new InputStreamReader(inputStream);
		}

		CSVFormat format = CSVFormat.EXCEL
				.withHeader(new String[] { "External ID", "Type", "Price", "Comment" });
		if (!includeHeader) {
			format = format.withSkipHeaderRecord();
		}
		Iterable<CSVRecord> records = format.withIgnoreSurroundingSpaces().parse(inputReader);
		return records;
	}

	private void writeData(HttpServletResponse resp, Map<String, String> advertiserData) throws IOException {
		JSONObject advertiserObject = new JSONObject(advertiserData);

		resp.setContentType("application/json");
		resp.getWriter().write(advertiserObject.toString());
	}

	private Io createOrder(JSONObject body) throws JSONException {
		Io io = Io.create(body.isNull("lead_type_id") ? -1 : new Long(body.getLong("lead_type_id")),
				body.isNull("order_id") ? 0 : new Long(body.getLong("order_id")), body.getString("code"),
				body.isNull("id") ? 0 : new Long(body.getLong("id")),
				body.isNull("vpl") ? new BigDecimal(0.0) : new BigDecimal(body.getDouble("vpl")),
				body.isNull("status") ? 1 : body.getInt("status"),
				body.isNull("cap_daily") ? 0L : new Long(body.getLong("cap_daily")),
				body.isNull("cap_monthly") ? 0L : new Long(body.getLong("cap_monthly")),
				body.isNull("cap_total") ? 0L : new Long(body.getInt("cap_total")),
				body.isNull("source_id") ? "" : body.getString("source_id"),
				body.isNull("is_exclusive") ? false : body.getBoolean("is_exclusive"),
				body.isNull("pixel_id") ? null : new Long(body.getLong("pixel_id")),
				body.isNull("target_url") ? "" : body.getString("target_url"),
				body.isNull("weight") ? 100 : body.getInt("weight"),
				body.isNull("email") ? "" : body.getString("email"));
		return io;
	}

	private Advertiser createAdvertiser(JSONObject body) throws JSONException {
		return this.createAdvertiser(body, body.isNull("id") ? 0 : body.getLong("id"));
	}

	private Advertiser createAdvertiser(JSONObject body, Long advertiserId) throws JSONException {
		Advertiser advertiser = Advertiser.create(advertiserId == null ? 0 : advertiserId, body.getString("name"));
		return advertiser;
	}

	private JSONObject parseRequestBody(HttpServletRequest req) throws IOException, JSONException {
		InputStream stream = req.getInputStream();
		Reader reader = new InputStreamReader(stream);
		StringBuilder bodyBuilder = new StringBuilder();
		char[] buffer = new char[1000];
		int position = 1;
		while ((position = reader.read(buffer, 0, 1000)) > 0) {
			bodyBuilder.append(buffer, 0, position);
		}
		JSONObject body = new JSONObject(bodyBuilder.toString());
		return body;
	}

	@Override
	protected ObjectMapper getMapper() {
		return new ObjectMapper();
	}
}
