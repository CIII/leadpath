package com.tapquality.processors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.PonyException;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherException;
import com.pony.publisher.PublisherService;

public class PostMessageTask implements ProcessingTask {
	private static final Log LOG = LogFactory.getLog(PostMessageTask.class);

	protected PublisherService publisherService;
	
	public PostMessageTask(PublisherService service) {
		this.publisherService = service;
	}
	
	@Override
	public IPublisherContext process(IPublisherContext context) throws PublisherException {
		try {
			return publisherService.postInternal(context);
		} catch (PonyException e) {
			LOG.error("Unknown PonyException publishing context.", e);
			throw new PublisherException("Unknown SMTP exception publishing context.", e);
		}
	}

}
