package com.pony.advertiser.factory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.PonyException;
import com.pony.advertiser.Advertiser;
import com.pony.advertiser.Disposition;
import com.pony.lead.LeadMatch;
import com.pony.models.LeadMatchModel;

public class MediaAlphaDispositionParser extends AbstractDispositionParser {
	private static Log LOG = LogFactory.getLog(MediaAlphaDispositionParser.class);

	@Override
	public Disposition createDisposition(Map<String, String> data, Advertiser advertiser, AdvertiserFactory factory, StringBuilder returnMessage) throws PonyException {
		String externalId;

		externalId = data.get("LeadiD UID");
		if (externalId == null || "".equals(externalId)) {
			throw new IllegalArgumentException("No externalId provided. Skipping.");
		}
		String typeString;
		typeString = data.get("guess");
		if (typeString == null || "".equals(typeString)) {
			throw new IllegalArgumentException("No type provided for external ID " + externalId + ". Skipping.");
		}
		String priceString;
		priceString = data.get("Price");
		if (priceString == null || "".equals(priceString)) {
			throw new IllegalArgumentException("No price provided for external ID " + externalId + ". Skipping.");
		}
		StringBuilder comment = new StringBuilder();
		String leadStatusRaw = data.getOrDefault("Lead Status", "");
		String unqualifiedReason = data.getOrDefault("Unqualified Reason", "");
		String stage = data.getOrDefault("Stage", "");
		comment.append(leadStatusRaw);
		comment.append(unqualifiedReason);
		comment.append(" - ");
		comment.append(stage);
		comment.append(" - ");
		
		Disposition.Status status;
		status = TYPES.get(typeString.toLowerCase());
		if (status == null) {
			throw new IllegalArgumentException("No known status " + typeString + " for externalID " + externalId
					+ ". Skipping.");
		}
		BigDecimal price;
		try {
			price = new BigDecimal(priceString);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Price could not be parsed into a number for external ID " + externalId + ". Skipping external ID "
					+ externalId + ".");
		}

		LeadMatch leadMatch;
		try {
			leadMatch = LeadMatchModel.findByExternalId(advertiser, externalId,
					factory.getExternalIdMatcher());
		} catch (SQLException e) {
			String errMsg = "SQLException querying for lead matches during the upload of a disposition.";
			LOG.warn(errMsg, e);
			throw new PonyException(errMsg, e);
		} catch (NamingException e) {
			String errMsg = "NamingException querying for lead matches. This is likely a configuration error and must be addressed immediately.";
			LOG.warn(errMsg, e);
			throw new PonyException(errMsg, e);
		}
		
		if (leadMatch == null) {
			throw new IllegalArgumentException("Could not identify lead match " + externalId + ".");
		}

		return createNewDisposition(externalId, price, comment.toString(), status, leadMatch);
	}

}
