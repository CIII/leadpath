package com.pony.validation;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Io;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;

public class TestFilter extends Filter {
	private static final Log LOG = LogFactory.getLog(TestFilter.class);
	
	protected Io io;
	protected PonyPhase phase;
	
	public TestFilter(Io io, PonyPhase phase) {
		this.io = io;
		this.phase = phase;
	}

	@Override
	public boolean pass(Publisher publisher, PublisherList publisherList, Lead lead, PonyPhase phase) {
        Map<String, String> map = AdvertiserWriter.parseStringToMap(this.io.getSourceId());

        if (map.containsKey("is_test") || ("Mickey".equalsIgnoreCase(lead.getAttributeValue("first_name")) && "Mouse".equalsIgnoreCase(lead.getAttributeValue("last_name")))) {
        	LOG.info("Test detected. Removing lead from all orders.");
        	return false;
        } else {
        	return true;
        }
	}

}
