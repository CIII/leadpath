package com.pony.advertiser.factory;

import java.util.Collection;

import org.jooq.Condition;

import com.tapquality.db.tables.LeadMatches;

public class SalesforceIdMatcher implements ExternalIdMatcher {

	public SalesforceIdMatcher() {}

	@Override
	public Collection<Condition> getPredicate(Collection<Condition> conditions, LeadMatches leadMatches, String externalId) {
		conditions.add(leadMatches.EXTERNAL_ID.like(externalId + "%"));
		return conditions;
	}

}
