package com.pony.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pony.PonyException;
import com.pony.advertiser.AdvertiserPollContext;
import com.pony.advertiser.Buyer;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.routing.AdvertiserPostContext;
import com.pony.routing.IAdvertiserPostContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;

public class CreateCandidatesFromBuyers extends Rule {

    public CreateCandidatesFromBuyers() {
        super("CreateCandidatesFromBuyers");
    }

	@Override
	public RuleResponse beforePing(PingContext pingContext, ValidationResponse validationResponse) {
		return RuleResponse.NOOP;
	}

	@Override
	public RuleResponse afterPing(PingContext pingContext) {
		return RuleResponse.NOOP;
	}

	@Override
	public RuleResponse beforePost(IAdvertiserPostContext postContext, ValidationResponse validationResponse)
			throws PonyException {
        List<RoutingCandidate> candidates = postContext.getRoutingCandidates();
        List<RoutingCandidate> candidatesToAdd = new ArrayList<>();
        Iterator<RoutingCandidate> iterator = candidates.iterator();
        while(iterator.hasNext()) {
        	RoutingCandidate candidate = iterator.next();
        	Disposition priceDisposition = candidate.getResponseDisposition(PonyPhase.REQUEST_PRICE);
        	if(priceDisposition != null && priceDisposition.hasBuyers()) {
        		iterator.remove();
        		for (Buyer buyer : priceDisposition.getBuyers()) {
        			// I do not use the disposition here because the price disposition is not really a ping disposition,
        			// and it was not saved (there isn't a leadMatchId if there wasn't a previous ping; this may be a
        			// bug), and it would therefore be broken.
        			RoutingCandidate newCandidate = RoutingCandidate.create(candidate.getIo(), null, buyer);
        			newCandidate.addResponseDisposition(PonyPhase.REQUEST_PRICE, priceDisposition);
        			candidatesToAdd.add(newCandidate);
        		}
        	}
        }

        candidates.addAll(candidatesToAdd);
        
		return RuleResponse.NOOP;
	}

	@Override
	public RuleResponse afterPost(AdvertiserPostContext postContext, RoutingCandidate routingCandidate,
			Disposition advertiserResponse) {
		return RuleResponse.NOOP;
	}

	@Override
	public RuleResponse poll(AdvertiserPollContext pollContext) {
		return RuleResponse.NOOP;
	}

}
