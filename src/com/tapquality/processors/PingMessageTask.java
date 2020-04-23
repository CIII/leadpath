package com.tapquality.processors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.PonyException;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherException;
import com.pony.publisher.PublisherService;

public class PingMessageTask implements ProcessingTask {
	private static final Log LOG = LogFactory.getLog(PingMessageTask.class);
	
	protected PublisherService publisherService;
	
	public PingMessageTask(PublisherService publisherService) {
		this.publisherService = publisherService;
	}
	
	@Override
	public IPublisherContext process(IPublisherContext publisherContext) throws PublisherException {
		try {
			return publisherService.pingInternal(publisherContext);
		} catch (PonyException e) {
			LOG.error("Unknown PonyException in PingMessageTask.", e);
			throw new PublisherException("Unknown PonyException in PingMessageTask.", e);
		}
	}

}
