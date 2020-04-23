package com.pony.advertiser.writers.solar;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.advertiser.Disposition.Status;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationResponse;

/**
 * This class was made available in order for testing purposes. It allows test systems to assign a writer that does not
 * interact with paying clients. It should not be used in production.
 * 
 * @author Jonathan Card
 *
 */
public class DummyWriter extends AdvertiserWriter {
	private static final Log LOG = LogFactory.getLog(DummyWriter.class);
	
	@Override
	public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse,
			RoutingCandidate candidate) {
		LOG.warn("In DummyWriter#post");
		try {
			String price = (String)writerProperties.get(this.name + "price");
			if(price == null) {
				LOG.debug(writerProperties.toString());
				throw new IllegalArgumentException("Writer price is not set.");
			}
			Disposition disposition = Disposition.createPost(Status.ACCEPTED, null, null, new BigDecimal(price), "Dummy Writer");
			return disposition;
		} catch (RuntimeException e) {
			LOG.error("Unknown Exception in the DummyWriter.", e);
			return Disposition.createPost(Status.REJECTED, Disposition.DispositionCategory.ERROR, null, null, e.getMessage());
		}
	}

}
