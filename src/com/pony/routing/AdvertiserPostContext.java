package com.pony.routing;

import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.advertiser.RoutingCandidateSet;
import com.pony.advertiser.RoutingCandidateTree;
import com.pony.advertiser.RoutingCandidateTree.RoutingCandidateTreeIterator;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 1/15/12
 * Time: 4:22 PM
 */
public class AdvertiserPostContext implements IAdvertiserPostContext {
    private final Arrival arrival;
    private final LeadType leadType;
    private final Lead lead;
    private final Publisher publisher;
    private final PublisherList publisherList;

    private final List<RoutingCandidate> routingCandidates = new LinkedList<RoutingCandidate>();
    private RoutingCandidateTree candidateStack = new RoutingCandidateTree();
    private final Map<RoutingCandidate, Disposition> dispositions = new HashMap<RoutingCandidate, Disposition>();
    private final Map<Long, List<Disposition>> advertisers = new HashMap<Long, List<Disposition>>();
    
    private RoutingCandidateTreeIterator iterator = null;

    private final int maxLeadUnits;
    private int availableLeadUnits;


    private AdvertiserPostContext(Arrival arrival, Publisher publisher, PublisherList publisherList, LeadType leadType, Lead lead, int maxLeadUnits, List<RoutingCandidate> routingCandidates) {
        this.publisher = publisher;
        this.publisherList = publisherList;
        this.arrival = arrival;
        this.leadType = leadType;
        this.lead = lead;
        this.maxLeadUnits = maxLeadUnits;
        this.availableLeadUnits = maxLeadUnits;
        this.routingCandidates.addAll(routingCandidates);
        RoutingCandidateSet newSet = new RoutingCandidateSet();
        newSet.addAll(routingCandidates);
        this.candidateStack.addRoutingCandidateSet(newSet);
    }

    public static AdvertiserPostContext create(Arrival arrival, Publisher publisher, PublisherList publisherList, LeadType leadType, Lead lead, int maxLeadUnits, List<RoutingCandidate> candidates) {
        return new AdvertiserPostContext(arrival, publisher, publisherList, leadType, lead, maxLeadUnits, candidates);
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#getPublisher()
	 */
    @Override
	public Publisher getPublisher() {
        return publisher;
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#getPublisherList()
	 */
    @Override
	public PublisherList getPublisherList() {
        return publisherList;
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#getArrival()
	 */
    @Override
	public Arrival getArrival() {
        return arrival;
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#getLeadType()
	 */
    @Override
	public LeadType getLeadType() {
        return leadType;
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#getLead()
	 */
    @Override
	public Lead getLead() {
        return lead;
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#getMaxLeadUnits()
	 */
    @Override
	public int getMaxLeadUnits() {
        return maxLeadUnits;
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#getAvailableLeadUnits()
	 */
    @Override
	public int getAvailableLeadUnits() {
        return availableLeadUnits;
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#getRoutingCandidates()
	 */
    @Override
	public List<RoutingCandidate> getRoutingCandidates() {
        return routingCandidates;
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#equals(java.lang.Object)
	 */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdvertiserPostContext that = (AdvertiserPostContext) o;

        if (!arrival.equals(that.arrival)) {
            return false;
        }
        if (!lead.equals(that.lead)) {
            return false;
        }

        return true;
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#hashCode()
	 */
    @Override
    public int hashCode() {
        int result = arrival.hashCode();
        result = 31 * result + lead.hashCode();
        return result;
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#toString()
	 */
    @Override
    public String toString() {
        return "AdvertiserPostContext{" +
                "arrival=" + arrival +
                ", lead=" + lead + " dispositions=" + dispositions.size() +
                '}';
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#addDisposition(com.pony.advertiser.RoutingCandidate, com.pony.advertiser.Disposition)
	 */
    @Override
	public void addDisposition(RoutingCandidate candidate, Disposition disposition) {
        dispositions.put(candidate, disposition);

        Long advertiserId = candidate.getIo().getAdvertiserId();
        List<Disposition> aDispositions = advertisers.get(advertiserId);
        if (aDispositions == null) {
            aDispositions = new ArrayList<Disposition>();
            advertisers.put(advertiserId, aDispositions);
        }
        aDispositions.add(disposition);

        if (disposition.isAccepted()) {
        	// This will get into trouble if the candidate handed in is not the previously delivered candidate from the
        	// iterator.
        	this.iterator.successfulDisposition();
            availableLeadUnits = availableLeadUnits - 1;
        }
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#getDispositions()
	 */
    @Override
	public Map<RoutingCandidate, Disposition> getDispositions() {
        return Collections.unmodifiableMap(dispositions);
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#hasMoreCandidates()
	 */
    @Override
	public boolean hasMoreCandidates() {
    	if(iterator == null) {
    		iterator = this.candidateStack.iterator();
    	}
        return iterator.hasNext();
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#getNextCandidate()
	 */
    @Override
	public RoutingCandidate getNextCandidate() {
    	if(iterator == null) {
    		iterator = this.candidateStack.iterator();
    	}
        return iterator.next();
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#getCandidateStack()
	 */
    @Override
	public RoutingCandidateTree getCandidateStack(){
        return candidateStack;
    }

    /* (non-Javadoc)
	 * @see com.pony.routing.IAdvertiserPostContext#resetCandidateStack(com.pony.advertiser.RoutingCandidateTree)
	 */
    @Override
	public void resetCandidateStack(RoutingCandidateTree tree) {
        // make sure the elements in the stack are valid and allowed (filter the ones we don't approve of)
        candidateStack = tree;
        iterator = null;
    }
}
