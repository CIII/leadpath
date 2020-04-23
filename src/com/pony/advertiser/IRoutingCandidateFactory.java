package com.pony.advertiser;

public interface IRoutingCandidateFactory {

	RoutingCandidateSet newRoutingCandidateSet();

	RoutingCandidateTree newRoutingCandidateTree();

}