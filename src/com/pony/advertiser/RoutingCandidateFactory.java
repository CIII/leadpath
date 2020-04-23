package com.pony.advertiser;

public class RoutingCandidateFactory implements IRoutingCandidateFactory {
	/* (non-Javadoc)
	 * @see com.pony.advertiser.IRoutingCandidateFactory#newRoutingCandidateSet()
	 */
	@Override
	public RoutingCandidateSet newRoutingCandidateSet() {
		return new RoutingCandidateSet();
	}
	
	/* (non-Javadoc)
	 * @see com.pony.advertiser.IRoutingCandidateFactory#newRoutingCandidateTree()
	 */
	@Override
	public RoutingCandidateTree newRoutingCandidateTree() {
		return new RoutingCandidateTree();
	}
}
