package com.pony.advertiser.writers.solar;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.advertiser.Disposition.Status;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationResponse;

public class DummyWriterWithPriceRequest extends DummyWriter {
	@Override
	public boolean supportsPhase(PonyPhase phase) {
        if (phase == PonyPhase.REQUEST_PRICE) {
            return true;
        } else {
        	return super.supportsPhase(phase);
        }
	}

	private static final Log LOG = LogFactory.getLog(DummyWriterWithPriceRequest.class);
	
	@Override
	public Disposition requestPrice(PonyPhase phase, ValidationResponse validationResponse, RoutingCandidate candidate,
			Lead lead, Long leadMatchId) {
		LOG.warn("In DummyWriterWithPriceRequest#post");
		try {
			String price = (String)writerProperties.get(this.name + "price");
			// TODO: Not to self: make requiresExclusivity configurable instead of "false"
			Disposition disposition = Disposition.create(phase, Status.ACCEPTED, false, new BigDecimal(price), "Dummy Writer");
			return disposition;
		} catch (RuntimeException e) {
			LOG.error("Unknown exception in DummyWriterWithPriceRequest.", e);
			return Disposition.createPost(Status.REJECTED, Disposition.DispositionCategory.ERROR, null, null, e.getMessage());
		}
	}
}
