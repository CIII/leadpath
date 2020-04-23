package com.pony.advertiser.factory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.NamingException;

import com.pony.PonyException;
import com.pony.advertiser.Advertiser;
import com.pony.advertiser.Disposition;
import com.pony.lead.LeadMatch;
import com.pony.models.LeadMatchModel;

public class BostonSolarDispositionParser extends AbstractDispositionParser {

	@Override
	public Disposition createDisposition(Map<String, String> data, Advertiser advertiser, AdvertiserFactory factory,
			StringBuilder returnMessage) throws PonyException {
		String externalId;
		externalId = data.get("Lead ID");
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
		
		String comment;
		comment = data.getOrDefault("Customer Notes", "");
		
		Disposition.Status status;
		status = TYPES.get(typeString.toLowerCase());
		if (status == null) {
			throw new IllegalArgumentException("No known status " + typeString + " for externalID " + externalId + ". Skipping.");
		}
		
		BigDecimal price;
		try {
			price = new BigDecimal(priceString);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Price could not be parsed into a number for external ID " + externalId + ". Skipping.");
		}
		
		LeadMatch leadMatch;
		try {
			leadMatch = LeadMatchModel.findByExternalId(advertiser, externalId, factory.getExternalIdMatcher());
		} catch (SQLException e) {
			throw new PonyException("", e);
		} catch (NamingException e) {
			throw new PonyException("", e);
		}
		if (leadMatch == null) {
			throw new IllegalArgumentException("Could not identify lead match " + externalId + ". ");
		}
		
		return createNewDisposition(externalId, price, comment, status, leadMatch);
	}

}
