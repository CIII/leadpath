package com.pony.rules;

import com.pony.advertiser.AdvertiserPollContext;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.routing.AdvertiserPostContext;
import com.pony.routing.IAdvertiserPostContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * check if there are any dispositions for the lead (from a specific partner) that exclude the lead from being routed to that partner (or somewhere else?)
 * ArbVentures 2013.
 * User: martin
 * Date: 6/26/13
 * Time: 2:21 PM
 */
public class DispositionRule extends Rule {

    public DispositionRule() {
        super("DispositionRule");
    }

    @Override
    public RuleResponse beforePing(PingContext pingContext, ValidationResponse validationResponse) {
        return RuleResponse.NOOP;
    }

    /**
     * after the ping happened, and we have a response, can we react to what the partner signals
     */
    @Override
    public RuleResponse afterPing(PingContext pingContext) {
        return RuleResponse.NOOP;
    }

    @Override
    public RuleResponse beforePost(IAdvertiserPostContext postContext, ValidationResponse validationResponse) {

        // get the dispositions we already have
        Map<RoutingCandidate, Disposition> dispositions = postContext.getDispositions();

        List<RoutingCandidate> toBeRemoved = new ArrayList<RoutingCandidate>();
        List<RoutingCandidate> candidates = postContext.getRoutingCandidates();
        for (RoutingCandidate candidate : candidates) {
            // did we already sold this lead there, or we tried to send it there, but they had an issue and we can try again
            Disposition disposition = dispositions.get(candidate);
            if (disposition != null && disposition.getPhase() == PonyPhase.POST && (disposition.isAccepted() || !disposition.getCategory().equals(Disposition.DispositionCategory.RETRY))) {
                toBeRemoved.add(candidate);
            }
        }
        candidates.removeAll(toBeRemoved);

        return RuleResponse.NOOP;
    }

    @Override
    public RuleResponse afterPost(AdvertiserPostContext postContext, RoutingCandidate routingCandidate, Disposition advertiserResponse) {
        //TODO: add buyer.isPreferred(true) handing
        return RuleResponse.NOOP;
    }

    @Override
    public RuleResponse poll(AdvertiserPollContext pollContext) {
        // Note: sorting and cutoff right now happens in CarPublisherFormat
        return RuleResponse.NOOP;
    }
}
