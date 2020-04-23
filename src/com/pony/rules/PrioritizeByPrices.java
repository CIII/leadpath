package com.pony.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.pony.PonyException;
import com.pony.advertiser.AdvertiserPollContext;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.advertiser.RoutingCandidateSet;
import com.pony.advertiser.RoutingCandidateTree;
import com.pony.routing.AdvertiserPostContext;
import com.pony.routing.IAdvertiserPostContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;

// TODO: Utilize RoutingCandidateVplComparator into the RoutingCandidateSet stuff instead of my stuff.
public class PrioritizeByPrices extends Rule {

	public PrioritizeByPrices() {
		super("Prioritize by Prices");
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
        List<RoutingCandidate> candidates = new ArrayList<>(postContext.getRoutingCandidates());

        Queue<RoutingCandidateSet> sets = new LinkedList<>();
        Iterator<RoutingCandidate> iterator = candidates.iterator();
        while(iterator.hasNext()) {
        	RoutingCandidate candidate = iterator.next();
        	if(candidate.getIo().isExclusive() || (candidate.getBuyer() != null && "1".equals(candidate.getBuyer().getMaxPosts()))) {
        		RoutingCandidateSet newSet = new RoutingCandidateSet();
        		newSet.add(candidate);
        		sets.add(newSet);
        		iterator.remove();
        	}
        }
    	sets.addAll(buildStructureRecursively(postContext, candidates, 0));
    	
        RoutingCandidateTree newTree = new RoutingCandidateTree();
        while(!sets.isEmpty()) {
        	newTree.addRoutingCandidateSet(sets.poll());
        }
        
        postContext.resetCandidateStack(newTree);
        return RuleResponse.NOOP;
	}
	
	protected Queue<RoutingCandidateSet> buildStructureRecursively(IAdvertiserPostContext postContext, List<RoutingCandidate>candidates, int depth) throws PonyException {
		Queue<RoutingCandidateSet> newPrototypes;
		RoutingCandidateSet newSet;
		if(candidates.size() == 1 || depth == postContext.getMaxLeadUnits() - 1) {
			newPrototypes = new LinkedList<>();
			Iterator<RoutingCandidate> iterator = candidates.iterator();
			while(iterator.hasNext()) {
				RoutingCandidate candidate = iterator.next();
				newSet = new RoutingCandidateSet();
				newSet.add(candidate);
				newPrototypes.add(newSet);
			}
			return newPrototypes;
		} else {
			Iterator<RoutingCandidate> iterator = candidates.iterator();
			newPrototypes = new LinkedList<>();
			while(iterator.hasNext()) {
				RoutingCandidate candidate = iterator.next();
				iterator.remove();
				List<RoutingCandidate> newCandidates = new ArrayList<>(candidates);
				RoutingCandidateSet currentPrototype;
				Queue<RoutingCandidateSet> prototypes = buildStructureRecursively(postContext, newCandidates, depth + 1);
				prototypes.add(new RoutingCandidateSet()); // This is the one that will hold only the candidate.
				while(!prototypes.isEmpty()) {
					currentPrototype = prototypes.poll();
					if(currentPrototype.size() == postContext.getMaxLeadUnits()) {
						newPrototypes.add(currentPrototype);
					} else {
						try {
							newSet = new RoutingCandidateSet(currentPrototype);
						} catch (Exception e) {
							throw new PonyException(e);
						}
						newSet.add(candidate);
						newPrototypes.add(newSet);
					}
				}
			}
			return newPrototypes;
		}
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
