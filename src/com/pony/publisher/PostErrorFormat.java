package com.pony.publisher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pony.advertiser.Disposition;

public class PostErrorFormat extends PostFormat {
	private static final Log LOG = LogFactory.getLog(PostErrorFormat.class);

	@Override
	public JSONObject constructDispositionEventMessage(String arrivalExternalId, Collection<Disposition> dispositions,
			Long leadId) throws PublisherException {
		JSONObject jsonDocument = super.constructDispositionEventMessage(arrivalExternalId, dispositions, leadId);
		List<Map<String, String>> errorsArray = new ArrayList<>();
		for(String[] errors : this.publisherContext.getException().getCodes()) {
			Map<String, String> errorObject = new HashMap<>();
			errorObject.put(errors[0], errors[1]);
			errorsArray.add(errorObject);
		}
		JSONArray errors = new JSONArray(errorsArray);
		try {
			jsonDocument.put("errors", errors);
		} catch (JSONException e) {
			LOG.error("Exception formatting errors in response.", e);
			throw new PublisherException("Exception formatting errors in response.", e);
		}
		return jsonDocument;
	}

	public PostErrorFormat(IPublisherContext publisherContext) {
		super(publisherContext);
	}

}
