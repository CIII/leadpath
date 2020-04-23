package com.pony.rules;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.PonyException;
import com.pony.advertiser.AdvertiserPollContext;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.lead.LeadMatch;
import com.pony.models.LeadMatchModel;
import com.pony.routing.AdvertiserPostContext;
import com.pony.routing.IAdvertiserPostContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;
import com.tapquality.lead.duplicates.DistantDuplicateLead;
import com.tapquality.lead.duplicates.Duplicate;
import com.tapquality.lead.duplicates.RecentDuplicate;

public class DuplicateRule extends Rule {
	private static final Log LOG = LogFactory.getLog(DuplicateRule.class);

	public DuplicateRule() {
		super("Duplicate Rule");
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
		if (postContext.getLead() instanceof Duplicate) {
			if (postContext.getLead() instanceof RecentDuplicate) {
				LOG.debug("This lead is a full duplicate submission. Removing all candidates and doing nothing.");
				Iterator<RoutingCandidate> candidatesIterator = postContext.getRoutingCandidates().iterator();
				while(candidatesIterator.hasNext()) {
					candidatesIterator.next();
					candidatesIterator.remove();
				}
			} else if (postContext.getLead() instanceof DistantDuplicateLead) {
				LOG.debug("This lead is a partial duplicate. Removing recent successful lead matches.");
				List<LeadMatch> leadMatches = LeadMatchModel.findDuplicateMatches(postContext.getLead().getUserProfileId(), postContext.getLead().getLeadTypeId());
				Iterator<LeadMatch> matchesIterator = leadMatches.iterator();
				while (matchesIterator.hasNext()) {
					LeadMatch match = matchesIterator.next();
					
					Iterator<RoutingCandidate> candidatesIterator = postContext.getRoutingCandidates().iterator();
					while (candidatesIterator.hasNext()) {
						RoutingCandidate candidate = candidatesIterator.next();
						
						if (candidate.getIo().getId() == match.getOrderId()) {
							LOG.debug("\tRemoving " + candidate.getIo().getCode());
							candidatesIterator.remove();
						}
					}
				}
			}
		}
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
