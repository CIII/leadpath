package com.pony.advertiser.writers.solar.zoho;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.convert.Registry;
import org.simpleframework.xml.convert.RegistryStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.Disposition.DispositionCategory;
import com.pony.advertiser.RoutingCandidate;
import com.pony.lead.Lead;
import com.pony.leadtypes.PonyLead;
import com.pony.models.UserProfileModel;
import com.pony.publisher.IPublisherContext;
import com.pony.validation.ValidationResponse;
import com.tapquality.lead.duplicates.DistantDuplicateLead;
import com.tapquality.lead.duplicates.RecentDuplicate;

public class ZohoWriter extends AdvertiserWriter {
	private static Log LOG = LogFactory.getLog(ZohoWriter.class);
	private UserProfileModel userProfileModel;

	@Inject
	ZohoWriter(UserProfileModel userProfileModel) {
		this.userProfileModel = userProfileModel;
	}

	@Override
	public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse,
			RoutingCandidate candidate) {
		return post(leadMatchId, publisherContext, validationResponse, candidate, new DefaultHttpClient());
	}

	Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse,
			RoutingCandidate candidate, HttpClient client) {
		Disposition returnValue;
		try {
			String url = this.writerProperties.getProperty(this.name + "url");
			String authtoken = this.writerProperties.getProperty(this.name + "authtoken");
			String test = this.writerProperties.getProperty(this.name + "istest");

			Registry registry = new Registry();
			Strategy strategy = new RegistryStrategy(registry);
			Serializer serializer = new Persister(strategy);
			Converter<Lead> converter = new ZohoLeadConverter(userProfileModel, "true".equals(test));
			registry.bind(PonyLead.class, converter);
			registry.bind(DistantDuplicateLead.class, converter);
			registry.bind(RecentDuplicate.class, converter);
			registry.bind(ZohoResult.class, ZohoResultConverter.class);
			StringWriter writer = new StringWriter();
			serializer.write(publisherContext.getLead(), writer);

			String body = writer.toString();
			LOG.debug("Body: " + body);
			Long msgId = logPostMessage(leadMatchId, "Sending Zoho message: " + body);
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("authtoken", authtoken));
			params.add(new BasicNameValuePair("scope", "crmapi"));
			params.add(new BasicNameValuePair("xmlData", body));
			String paramsString = URLEncodedUtils.format(params, "UTF-8");
			URI fullUrl = new URI(url + "?" + paramsString);
			HttpGet httpget = new HttpGet(fullUrl);
			
			HttpResponse response = client.execute(httpget);
			HttpEntity responseEntity = response.getEntity();
			String responseBody = EntityUtils.toString(responseEntity);
			logResponseMessage(msgId, responseBody);
			LOG.debug(responseBody);
			if (response.getStatusLine().getStatusCode() == 200) {
				ZohoResponse responseObj = serializer.read(ZohoResponse.class, responseBody);
				if (responseObj.getError() != null) {
					returnValue = Disposition.createPost(Disposition.Status.REJECTED, DispositionCategory.ERROR, responseObj.getError().getMessage());
				} else {
					returnValue = Disposition.createPost(Disposition.Status.ACCEPTED, null, responseObj.getResult().getId(), candidate.getIo().getVpl(), responseObj.getResult().getMessage());
				}
			} else {
				String errMsg = "Error connecting to Zoho. Status " + response.getStatusLine().getStatusCode() + ". " + responseBody;
				returnValue = Disposition.createPost(Disposition.Status.REJECTED, DispositionCategory.ERROR, errMsg);
			}
		} catch (ParseException | IOException e) {
			String errMsg = "Exception sending message to Zoho.";
			LOG.error(errMsg, e);
			returnValue = Disposition.createPost(Disposition.Status.REJECTED, DispositionCategory.ERROR, errMsg);
		} catch (Exception e) {
			String errMsg = "Exception sending message to Zoho. Most likely in constructing the message.";
			LOG.error(errMsg, e);
			;
			returnValue = Disposition.createPost(Disposition.Status.REJECTED, DispositionCategory.ERROR, errMsg);
		}

		return returnValue;
	}

}
