package com.pony.rules;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.pony.PonyException;
import com.pony.advertiser.Io;
import com.pony.advertiser.RoutingCandidate;
import com.pony.advertiser.RoutingCandidateTree;
import com.pony.routing.AdvertiserPostContext;

public class PrioritizeByPricesTest {

	@Test
	// Should create 1,2,3,4; 1,2,3; 1,2,4; 1,3,4; 2,3,4; 1,2; 1,3; 1,4; 2,3; 2,4; 3,4; 1; 2; 3; 4
	public void testLoadingRoutingSets() throws Exception, PonyException {
		Io io1 = new Io(new Long(0), new Long(0), "io1", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate1 = RoutingCandidate.create(io1);
		Io io2 = new Io(new Long(0), new Long(0), "io2", new Long(0), new BigDecimal(2.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate2 = RoutingCandidate.create(io2);
		Io io3 = new Io(new Long(0), new Long(0), "io3", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate3 = RoutingCandidate.create(io3);
		Io io4 = new Io(new Long(0), new Long(0), "io4", new Long(0), new BigDecimal(2.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate4 = RoutingCandidate.create(io4);

		List<RoutingCandidate> candidates = new ArrayList<>();
		candidates.add(candidate1);
		candidates.add(candidate2);
		candidates.add(candidate3);
		candidates.add(candidate4);
		AdvertiserPostContext newContext = AdvertiserPostContext.create(null, null, null, null, null, 4, candidates);
		
		Rule newRule = new PrioritizeByPrices();
		newRule.beforePost(newContext, null);
		RoutingCandidateTree newTree = newContext.getCandidateStack();
		assertEquals(15, newTree.getCandidateSetsSize());
	}
	
	// Should create 1,2; 1,4; 2,4; 1; 2; 4; 3 (3 is exclusive)
	@Test public void testLoadingRoutingSetsLimitedLeadUnits() throws Exception, PonyException {
		Io io1 = new Io(new Long(0), new Long(0), "io1", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate1 = RoutingCandidate.create(io1);
		Io io2 = new Io(new Long(0), new Long(0), "io2", new Long(0), new BigDecimal(2.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate2 = RoutingCandidate.create(io2);
		Io io3 = new Io(new Long(0), new Long(0), "io3", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", true, new Long(0), "");
		RoutingCandidate candidate3 = RoutingCandidate.create(io3);
		Io io4 = new Io(new Long(0), new Long(0), "io4", new Long(0), new BigDecimal(2.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate4 = RoutingCandidate.create(io4);

		List<RoutingCandidate> candidates = new ArrayList<>();
		candidates.add(candidate1);
		candidates.add(candidate2);
		candidates.add(candidate3);
		candidates.add(candidate4);
		AdvertiserPostContext newContext = AdvertiserPostContext.create(null, null, null, null, null, 2, candidates);
		
		Rule newRule = new PrioritizeByPrices();
		newRule.beforePost(newContext, null);
		RoutingCandidateTree newTree = newContext.getCandidateStack();
		assertEquals(7, newTree.getCandidateSetsSize());
	}
	
	// Should create 1; 2; 3; 4
	@Test public void testLoadingRoutingSetsSingleUnits() throws Exception, PonyException {
		Io io1 = new Io(new Long(0), new Long(0), "io1", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate1 = RoutingCandidate.create(io1);
		Io io2 = new Io(new Long(0), new Long(0), "io2", new Long(0), new BigDecimal(2.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate2 = RoutingCandidate.create(io2);
		Io io3 = new Io(new Long(0), new Long(0), "io3", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", true, new Long(0), "");
		RoutingCandidate candidate3 = RoutingCandidate.create(io3);
		Io io4 = new Io(new Long(0), new Long(0), "io4", new Long(0), new BigDecimal(2.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate4 = RoutingCandidate.create(io4);

		List<RoutingCandidate> candidates = new ArrayList<>();
		candidates.add(candidate1);
		candidates.add(candidate2);
		candidates.add(candidate3);
		candidates.add(candidate4);
		AdvertiserPostContext newContext = AdvertiserPostContext.create(null, null, null, null, null, 1, candidates);
		
		Rule newRule = new PrioritizeByPrices();
		newRule.beforePost(newContext, null);
		RoutingCandidateTree newTree = newContext.getCandidateStack();
		assertEquals(4, newTree.getCandidateSetsSize());
	}

}
