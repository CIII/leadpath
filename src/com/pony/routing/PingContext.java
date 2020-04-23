package com.pony.routing;

import com.pony.advertiser.Buyer;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 6/26/13
 * Time: 4:40 PM
 */
public class PingContext {
    private final Arrival arrival;
    private final LeadType leadType;
    private final Lead lead;
    private final List<RoutingCandidate> routingCandidates = new LinkedList<RoutingCandidate>();
    private final Stack<RoutingCandidate> candidateStack = new Stack<RoutingCandidate>();
    private final int maxLeadUnits;

    private final Map<RoutingCandidate, Disposition> dispositions = new HashMap<RoutingCandidate, Disposition>();
    private final Map<Long, List<Disposition>> advertiserDispositions = new HashMap<Long, List<Disposition>>();

    private Buyer preferredBuyer;

    private PingContext(Arrival arrival, LeadType leadType, Lead lead, int maxLeadUnits, List<RoutingCandidate> routingCandidates) {
        this.arrival = arrival;
        this.leadType = leadType;
        this.lead = lead;
        this.maxLeadUnits = maxLeadUnits;
        this.routingCandidates.addAll(routingCandidates);
    }

    public static PingContext create(Arrival arrival, LeadType leadType, Lead lead, int maxLeadUnits, List<RoutingCandidate> routingCandidates) {
        return new PingContext(arrival, leadType, lead, maxLeadUnits, routingCandidates);
    }

    public Arrival getArrival() {
        return arrival;
    }

    public LeadType getLeadType() {
        return leadType;
    }

    public Lead getLead() {
        return lead;
    }

    public List<RoutingCandidate> getRoutingCandidates() {
        return routingCandidates;
    }

    public Stack<RoutingCandidate> getCandidateStack() {
        return candidateStack;
    }

    public int getMaxLeadUnits() {
        return maxLeadUnits;
    }

    public Map<RoutingCandidate, Disposition> getDispositions() {
        return dispositions;
    }

    public void removeCandidate(RoutingCandidate candidate) {
        advertiserDispositions.remove(candidate.getIo().getAdvertiserId());

        candidateStack.remove(candidate);
    }

    public boolean hasMoreCandidates() {
        return !candidateStack.empty();
    }

    public RoutingCandidate getNextCandidate() {
        return candidateStack.pop();
    }

    public void addDisposition(RoutingCandidate candidate, Disposition disposition) {
        dispositions.put(candidate, disposition);
    }

    public void removeCandidates(List<RoutingCandidate> candidates) {
        candidateStack.removeAll(candidates);
    }

    public Disposition getDisposition(RoutingCandidate routingCandidate) {
        return dispositions.get(routingCandidate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PingContext that = (PingContext) o;

        if (!arrival.equals(that.arrival)) {
            return false;
        }
        if (!lead.equals(that.lead)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = arrival.hashCode();
        result = 31 * result + lead.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PingContext: a=" + arrival + " l=" + lead + " stack size=" + candidateStack.size();
    }

    public void resetCandidateStack(Stack<RoutingCandidate> stack) {
        // make sure the elements in the stack are valid and allowed (filter the ones we don't approve of)
        List<RoutingCandidate> toBeRemoved = new ArrayList<RoutingCandidate>();
        for (RoutingCandidate candidate : stack) {
            if (!routingCandidates.contains(candidate)) {
                toBeRemoved.add(candidate);
            }
            else if (dispositions.get(candidate) != null) {
                // we already tried this one
                toBeRemoved.add(candidate);
            }
        }
        stack.removeAll(toBeRemoved);

        candidateStack.clear();
        candidateStack.addAll(stack);
    }

    public void setPreferredBuyer(Buyer buyer) {
        preferredBuyer = buyer;
    }

    public Buyer getPreferredBuyer() {
        return preferredBuyer;
    }
}
