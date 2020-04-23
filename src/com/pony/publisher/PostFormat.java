package com.pony.publisher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pony.advertiser.Buyer;
import com.pony.advertiser.Disposition;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.models.IoModel;

public class PostFormat extends PublisherFormat {
	private static final Log LOG = LogFactory.getLog(PostFormat.class);

	public PostFormat(IPublisherContext publisherContext) {
		super(publisherContext);
	}

	@Override
	public void format(PublisherResponse publisherResponse, HttpServletResponse servletResponse) throws IOException {
		Arrival arrival;
		try {
			List<Disposition> dispositions = publisherResponse.getDispositions();
			arrival = publisherContext.getArrival();
			Lead lead = publisherContext.getLead();
			JSONObject document = constructDispositionEventMessage(arrival == null || arrival.getExternalId() == null ? null : arrival.getExternalId().toString(), dispositions, lead == null ? null : lead.getId());
			servletResponse.getWriter().write(document.toString());
		} catch (PublisherException e) {
			LOG.error("Error retrieving arrival to format response.", e);
			servletResponse.getWriter().write("Error getting arrival to format response");
		}

	}

	public JSONObject constructDispositionEventMessage(String arrivalExternalId, Collection<Disposition> dispositions, Long leadId) throws PublisherException {
		JSONObject jsonDocument = new JSONObject();
		JSONObject leadObject = new JSONObject();
		JSONArray listingArray = new JSONArray();
		Iterator<Disposition> iterator = dispositions.iterator();

		try {
			while (iterator.hasNext()) {
				Disposition disposition = iterator.next();
				if (disposition.getStatus() == Disposition.Status.ACCEPTED) {
					LOG.debug("Found accepted disposition");
					if (disposition.hasBuyers()) {
						// Output Buyer listing
						LOG.debug("Found buyer in disposition");
						List<Buyer> buyers = disposition.getBuyers();
						Iterator<Buyer> buyerIterator = buyers.iterator();
						while (buyerIterator.hasNext()) {
							Buyer buyer = null;
							try {
								buyer = buyerIterator.next();
								JSONObject buyerInfo = new JSONObject();
								buyerInfo.put("type", "buyer");
								buyerInfo.put("name", buyer.getName());
								buyerInfo.put("price", buyer.getPrice());
								listingArray.put(buyerInfo);
							} catch (JSONException e) {
								LOG.warn("Exception formatting buyer: " + buyer.getBuyerId());
								throw e;
							}
						}
					} else {
						// Output order listing
						try {
							LOG.debug("Found order disposition");
							JSONObject orderInfo = new JSONObject();
							IoModel ioModel = IoModel.findByLeadMatchId(disposition.getLeadMatchId());
							orderInfo.put("type", "order");
							LOG.debug("Order code: " + ioModel.getIo().getCode());
							orderInfo.put("code", ioModel.getIo().getCode());
							orderInfo.put("price", disposition.getPrice());
							listingArray.put(orderInfo);
						} catch (SQLException | NamingException | JSONException e) {
							LOG.warn("Exception formatting order disposition: " + disposition.getId());
							throw e;
						}
					}
				}
			}
			LOG.debug("Final listings array: " + listingArray.toString());
			leadObject.put("id", leadId == null ? null : leadId.toString());
			if(listingArray.length() > 0) {
				leadObject.put("listings", listingArray);
			}
			jsonDocument.put("id", arrivalExternalId);
			jsonDocument.put("lead", leadObject);
		} catch (SQLException | NamingException | JSONException e) {
			LOG.error("Exception formatting output.", e);
			throw new PublisherException("Exception formatting output.", e);
		}

		return jsonDocument;

	}
}
