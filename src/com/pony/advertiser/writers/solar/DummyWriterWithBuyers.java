package com.pony.advertiser.writers.solar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.advertiser.Buyer;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.advertiser.Disposition.Status;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationResponse;

public class DummyWriterWithBuyers extends DummyWriter {
	
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
	public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse,
			RoutingCandidate candidate) {
		LOG.warn("In DummyWriter#post");
		try {
			Disposition disposition = Disposition.createPost(Status.ACCEPTED, null, null, candidate.getBuyer().getPrice(), "Dummy Writer");
			return disposition;
		} catch (RuntimeException e) {
			LOG.error("Unknown Exception in the DummyWriter.", e);
			return Disposition.createPost(Status.REJECTED, Disposition.DispositionCategory.ERROR, null, null, e.getMessage());
		}
	}

	
	@Override
	public Disposition requestPrice(PonyPhase phase, ValidationResponse validationResponse, RoutingCandidate candidate,
			Lead lead, Long leadMatchId) {
		LOG.warn("In DummyWriterWithBuyers#post");
		try {
			int i = 1;
			String price;
			List<Buyer> buyers = new ArrayList<>();
			while((price = writerProperties.getProperty(this.name + "price." + i)) != null) {
				Buyer buyer = Buyer.create(this.name + i, this.name + i, "00000");
				buyer.setPrice(new BigDecimal(price));
				buyers.add(buyer);
				i += 1;
			}
			// TODO: Not to self: make requiresExclusivity configurable instead of "false"
			Disposition disposition = Disposition.create(phase, Status.ACCEPTED, false, candidate.getIo().getVpl(), "Dummy Writer");
			for(Buyer buyer : buyers) {
				disposition.addBuyer(buyer);
			}
			return disposition;
		} catch (RuntimeException e) {
			LOG.error("Unknown exception in DummyWriterWithBuyers.", e);
			return Disposition.createPost(Status.REJECTED, Disposition.DispositionCategory.ERROR, null, null, e.getMessage());
		}
	}

}
