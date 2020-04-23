package com.pony.validation.whitepages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;

public class WhitepagesAcceptFilter extends AbstractWhitepagesFilter {
	private static final Log LOG = LogFactory.getLog(WhitepagesAcceptFilter.class);

	public WhitepagesAcceptFilter(String parameterName) {
		super(parameterName);
	}

	@Override
	public boolean pass(Publisher publisher, PublisherList publisherList, Lead lead, PonyPhase phase) {
		boolean pass = false;
		String testValue = lead.getAttributeValue(parameterName);
		if(this.values == null || this.values.size() == 0) throw new NullPointerException("This WhitepagesAcceptFilter for " + parameterName + " is not properly initialized with acceptable values.");

		for(String acceptableValue : this.values) {
			if(acceptableValue.equals(testValue)) {
				pass = true;
				break;
			}
		}
		
		if(!pass) {
			LOG.debug("Whitepages accept filter: " + parameterName + ": rejected");
		}
		return pass;
	}
	
}
