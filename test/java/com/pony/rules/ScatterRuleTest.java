package com.pony.rules;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.hamcrest.Description;
import org.hamcrest.core.IsInstanceOf;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Expectation;
import org.jmock.api.Invocation;
import org.junit.Test;

import com.pony.PonyException;
import com.pony.advertiser.IRoutingCandidateFactory;
import com.pony.advertiser.Io;
import com.pony.advertiser.RoutingCandidate;
import com.pony.advertiser.RoutingCandidateFactory;
import com.pony.advertiser.RoutingCandidateSet;
import com.pony.advertiser.RoutingCandidateTree;
import com.pony.routing.AdvertiserPostContext;
import com.pony.routing.IAdvertiserPostContext;

public class ScatterRuleTest {

	@SuppressWarnings("serial")
	@Test
	public void testBeforePostAdvertiserPostContextValidationResponse() throws Exception, PonyException {
		Io io1 = new Io(new Long(0), new Long(0), "io1", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate1 = RoutingCandidate.create(io1);
		Io io3 = new Io(new Long(0), new Long(0), "io3", new Long(0), new BigDecimal(5.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate3 = RoutingCandidate.create(io3);
		final List<RoutingCandidate> list = new ArrayList<>();
		list.add(candidate1);
		list.add(candidate3);
		final RoutingCandidateSet routingCandidateSet = new RoutingCandidateSet();
		
		Mockery context = new Mockery();
		final IAdvertiserPostContext mockContext = context.mock(IAdvertiserPostContext.class);
		final IRoutingCandidateFactory mockFactory = context.mock(IRoutingCandidateFactory.class);
		context.checking(new Expectations() {{
			oneOf(mockContext).getRoutingCandidates(); will(returnValue(list));
			oneOf(mockContext).resetCandidateStack(with(any(RoutingCandidateTree.class)));
			
			allowing(mockFactory).newRoutingCandidateSet(); will(returnValue(routingCandidateSet));
			allowing(mockFactory).newRoutingCandidateTree(); will(returnValue(new RoutingCandidateTree()));
		}});
		
		ScatterRule rule = new ScatterRule();
		Random random = new Random() {
			@Override
			public double nextDouble() {
				return 0.1;
			}
		};
		rule.beforePost(mockContext, null, random, mockFactory);
		
		RoutingCandidate candidate;
		Iterator<RoutingCandidate> it = routingCandidateSet.iterator();
		candidate = it.next();
		assertTrue(candidate3 == candidate || candidate1 == candidate);
		candidate = it.next();
		assertTrue(candidate3 == candidate || candidate1 == candidate);
		assertFalse(it.hasNext());
		context.assertIsSatisfied();
	}

}
