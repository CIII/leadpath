package com.pony.validation.whitepages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;

public class WhitepagesRejectFilter extends AbstractWhitepagesFilter {
	private static final Log LOG = LogFactory.getLog(WhitepagesRejectFilter.class);

	public WhitepagesRejectFilter(String parameterName) {
		super(parameterName);
	}

	@Override
	public boolean pass(Publisher publisher, PublisherList publisherList, Lead lead, PonyPhase phase) {
		boolean pass = true;
		String testValue = lead.getAttributeValue(parameterName);
		if(this.values == null || this.values.size() == 0) throw new NullPointerException("This WhitepagesRejectFilter for " + parameterName + " is not properly initialized with acceptable values.");

		for(String rejectableValue : this.values) {
			if(rejectableValue.equals(testValue)) pass = false;
		}
		
		if(!pass) {
			LOG.debug("Whitepages reject filter: " + parameterName + ": rejected");
		}
		return pass;
	}

}
