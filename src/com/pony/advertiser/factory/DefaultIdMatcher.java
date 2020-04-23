package com.pony.advertiser.factory;

import java.util.Collection;

import org.jooq.Condition;

import com.tapquality.db.tables.LeadMatches;

public class DefaultIdMatcher implements ExternalIdMatcher {

	@Override
	public Collection<Condition> getPredicate(Collection<Condition> conditions, LeadMatches leadMatches, String externalId) {
		conditions.add(leadMatches.EXTERNAL_ID.eq(externalId));
		return conditions;
	}

}
