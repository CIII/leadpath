package com.tapquality.lead.duplicates;

import com.pony.PonyException;
import com.pony.lead.Lead;

public class RecentDuplicate extends DuplicateLead {

	public RecentDuplicate() throws PonyException {
	}

	public RecentDuplicate(Lead lead) throws PonyException {
		super(lead);
	}
}
