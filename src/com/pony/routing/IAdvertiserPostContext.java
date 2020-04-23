package com.pony.routing;

import java.util.List;
import java.util.Map;

import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.advertiser.RoutingCandidateTree;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;

public interface IAdvertiserPostContext {

	Publisher getPublisher();

	PublisherList getPublisherList();

	Arrival getArrival();

	LeadType getLeadType();

	Lead getLead();

	int getMaxLeadUnits();

	int getAvailableLeadUnits();

	List<RoutingCandidate> getRoutingCandidates();

	boolean equals(Object o);

	int hashCode();

	String toString();

	void addDisposition(RoutingCandidate candidate, Disposition disposition);

	Map<RoutingCandidate, Disposition> getDispositions();

	boolean hasMoreCandidates();

	RoutingCandidate getNextCandidate();

	RoutingCandidateTree getCandidateStack();

	void resetCandidateStack(RoutingCandidateTree tree);

}