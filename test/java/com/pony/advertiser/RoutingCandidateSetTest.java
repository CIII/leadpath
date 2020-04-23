package com.pony.advertiser;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

public class RoutingCandidateSetTest {

	@Test
	public void testAddingRoutingCandidates() {
		Io io1 = new Io(new Long(0), new Long(0), "", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate1 = RoutingCandidate.create(io1);
		Io io2 = new Io(new Long(0), new Long(0), "", new Long(0), new BigDecimal(2.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate2 = RoutingCandidate.create(io2);
		
		RoutingCandidateSet set = new RoutingCandidateSet();
		set.add(candidate1);
		set.add(candidate2);
		
		Iterator<RoutingCandidate> it = set.iterator();
		assertSame(candidate2, it.next());
		assertSame(candidate1, it.next());
		assertFalse(it.hasNext());
		assertEquals(set.getTotalPrice().doubleValue(), 3.0, 0.1);
	}

	@Test
	public void testAddingRoutingCandidateList() {
		Io io1 = new Io(new Long(0), new Long(0), "", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate1 = RoutingCandidate.create(io1);
		Io io2 = new Io(new Long(0), new Long(0), "", new Long(0), new BigDecimal(2.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate2 = RoutingCandidate.create(io2);
		
		List<RoutingCandidate> list = new ArrayList<>();
		list.add(candidate2);
		list.add(candidate1);
		RoutingCandidateSet set = new RoutingCandidateSet();
		set.addAll(list);
		
		Iterator<RoutingCandidate> it = set.iterator();
		assertSame(candidate2, it.next());
		assertSame(candidate1, it.next());
		assertFalse(it.hasNext());
		assertEquals(3.0, set.getTotalPrice().doubleValue(), 0.1);
	}

	@Test
	public void testAddingRoutingCandidatesMultipleTimes() {
		Io io1 = new Io(new Long(0), new Long(0), "", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate1 = RoutingCandidate.create(io1);
		
		RoutingCandidateSet set = new RoutingCandidateSet();
		set.add(candidate1);
		set.add(candidate1);
		
		Iterator<RoutingCandidate> it = set.iterator();
		assertSame(candidate1, it.next());
		assertTrue(it.hasNext());
		assertEquals(2.0, set.getTotalPrice().doubleValue(), 0.1);
	}
	
	@Test
	public void testCopy() throws Exception {
		Io io1 = new Io(new Long(0), new Long(0), "", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate1 = RoutingCandidate.create(io1);
		Io io3 = new Io(new Long(0), new Long(0), "", new Long(0), new BigDecimal(5.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate3 = RoutingCandidate.create(io3);

		RoutingCandidateSet set3 = new RoutingCandidateSet();
		set3.add(candidate3);
		set3.add(candidate1);

		Object setClone = new RoutingCandidateSet(set3);
		assertTrue(setClone instanceof RoutingCandidateSet);
		RoutingCandidateSet setCloneTyped = (RoutingCandidateSet)setClone;
		assertNotSame(set3, setCloneTyped);
		Iterator<RoutingCandidate> it = setCloneTyped.iterator();
		RoutingCandidate first = it.next();
		assertSame(candidate3, first);
		RoutingCandidate second = it.next();
		assertSame(candidate1, second);
		assertFalse(it.hasNext());
		try {
			it.next();
			fail("Did no throw NoSuchElementException");
		} catch (NoSuchElementException e) {}
	}
	
	@Test
	public void testRemovingFromIterator() {
		Io io1 = new Io(new Long(0), new Long(0), "", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate1 = RoutingCandidate.create(io1);
		Io io2 = new Io(new Long(0), new Long(0), "", new Long(0), new BigDecimal(2.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate2 = RoutingCandidate.create(io2);
		Io io3 = new Io(new Long(0), new Long(0), "", new Long(0), new BigDecimal(3.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate3 = RoutingCandidate.create(io3);
		
		
		RoutingCandidateSet set = new RoutingCandidateSet();
		set.add(candidate1);
		set.add(candidate2);
		set.add(candidate3);

		Iterator<RoutingCandidate> it = set.iterator();
		RoutingCandidate candidate = it.next();
		assertSame(candidate3, candidate);
		it.remove();
		assertEquals(3.0, set.getTotalPrice().doubleValue(), 0.1);
	}

}
