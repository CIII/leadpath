package com.tapquality.processors;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.lead.Arrival;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherException;
import com.pony.publisher.PublisherService;
import com.tapquality.email.subscribe.MailChimpClient.MailChimpList;

public class SubscribeUserTask implements ProcessingTask {
	private static final Log LOG = LogFactory.getLog(SubscribeUserTask.class);
    private static final Map<String, MailChimpList> LISTS = new HashMap<>();
    
    static {
    	LISTS.put("easiersolar", MailChimpList.EasierSolarDeals);
    	LISTS.put("homesolar", MailChimpList.SolarProDeals);
    }


	protected PublisherService publisherService;
    
    public SubscribeUserTask(PublisherService service) {
    	this.publisherService = service;
    }


	@Override
	public IPublisherContext process(IPublisherContext context) throws PublisherException {
        Arrival arrival = context.getArrival();
        MailChimpList list = LISTS.get(arrival.getArrivalSourceId());
        if(list != null) {
        	publisherService.subscribe(context, LISTS.get(arrival.getArrivalSourceId()));
        } else {
        	LOG.info("No list found for arrival source id " + arrival.getArrivalSourceId());
        }
		return context;
	}

}
