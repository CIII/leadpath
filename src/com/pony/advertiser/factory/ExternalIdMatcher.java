package com.pony.advertiser.factory;

import java.util.Collection;

import org.jooq.Condition;

import com.tapquality.db.tables.LeadMatches;

public interface ExternalIdMatcher {
	Collection<Condition> getPredicate(Collection<Condition> conditions, LeadMatches leadMatches, String externalId);
}
