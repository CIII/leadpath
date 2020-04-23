package com.tapquality.dispositioneventpublisher;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.pony.advertiser.Disposition;
import com.pony.lead.Arrival;
import com.pony.models.ArrivalModel;
import com.pony.publisher.PostFormat;
import com.pony.publisher.PublisherException;

public class LynxDispositionEventPublisher implements IDispositionEventPublisher {
	private static final Log LOG = LogFactory.getLog(LynxDispositionEventPublisher.class);
	protected String lynxPath;
	protected ArrivalModel arrivalModel;
	
	@Inject LynxDispositionEventPublisher(@Named("lynxPath") String lynxPath, ArrivalModel arrivalModel) {
		this.lynxPath = lynxPath;
		this.arrivalModel = arrivalModel;
	}

	@Override
	public boolean publishDispositions(Long leadId, Collection<Disposition> dispositions) {
		boolean returnValue = false;
		String url = lynxPath + "/post_disposition";

		try {
			// Find arrival associated with lead match, but arrival can have
			// multiple leadmatches
			PostFormat format = new PostFormat(null);

			Arrival arrival = this.arrivalModel.findByLeadId(leadId);
			JSONObject document = format.constructDispositionEventMessage(arrival.getExternalId(), dispositions, leadId);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			LOG.debug("Host: " + url);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setHeader("Authorization", "Token token=9432fe20-25b0-4731-9de3-5fb3bce0f565");
			StringEntity entity = new StringEntity(document.toString());
			LOG.debug("Body: " + document.toString());
			httpPost.setEntity(entity);

			try {
				HttpResponse response = httpClient.execute(httpPost);
				if (response.getStatusLine().getStatusCode() != 200) {
					LOG.error("Unsuccessful response when reporting to Lynx with leadId " + leadId + ": " + response.getStatusLine().toString());
				} else {
					returnValue = true;
				}
			} catch (IOException e) {
				LOG.error("Unable to report to Lynx with leadId: " + leadId, e);
			} catch (Throwable e) {
				LOG.error("This is usually a version error from httpclient being the wrong version.", e);
			}
		} catch (UnsupportedEncodingException | PublisherException e) {
			LOG.error("Error encoding body for leadId: " + leadId, e);
		} catch (NamingException | SQLException e) {
			LOG.error("Error finding information to publish for leadId: " + leadId, e);
		}
		return returnValue;
	}

}
