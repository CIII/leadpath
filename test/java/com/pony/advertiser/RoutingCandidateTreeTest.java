package com.pony.advertiser;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.junit.Test;

import com.pony.advertiser.RoutingCandidateTree.RoutingCandidateTreeIterator;

public class RoutingCandidateTreeTest {

	@Test
	public void testAddingSets() {
		Io io1 = new Io(new Long(0), new Long(0), "io1", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate1 = RoutingCandidate.create(io1);
		Io io2 = new Io(new Long(0), new Long(0), "io2", new Long(0), new BigDecimal(3.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate2 = RoutingCandidate.create(io2);
		Io io3 = new Io(new Long(0), new Long(0), "io3", new Long(0), new BigDecimal(5.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate3 = RoutingCandidate.create(io3);
		
		RoutingCandidateSet set1 = new RoutingCandidateSet();
		set1.add(candidate2);
		set1.add(candidate1);
		RoutingCandidateSet set2 = new RoutingCandidateSet();
		set2.add(candidate3);
		RoutingCandidateSet set3 = new RoutingCandidateSet();
		set3.add(candidate3);
		set3.add(candidate1);
		
		RoutingCandidateTree tree = new RoutingCandidateTree();
		tree.addRoutingCandidateSet(set1);
		tree.addRoutingCandidateSet(set2);
		tree.addRoutingCandidateSet(set3);
		
		Iterator<RoutingCandidate> it = tree.iterator();
		RoutingCandidate candidate = it.next();
		assertSame(candidate3, candidate);
		candidate = it.next();
		assertSame(candidate2, candidate);
		candidate = it.next();
		assertSame(candidate1, candidate);
		assertFalse(it.hasNext());
		assertNull(it.next());
	}
	
	@Test
	public void testSuccessfulDisposition() {
		Io io1 = new Io(new Long(0), new Long(0), "io1", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate1 = RoutingCandidate.create(io1);
		Io io2 = new Io(new Long(0), new Long(0), "io2", new Long(0), new BigDecimal(3.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate2 = RoutingCandidate.create(io2);
		Io io3 = new Io(new Long(0), new Long(0), "io3", new Long(0), new BigDecimal(5.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate3 = RoutingCandidate.create(io3);
		
		RoutingCandidateSet set2 = new RoutingCandidateSet(); // 5
		set2.add(candidate3);
		RoutingCandidateSet set3 = new RoutingCandidateSet(); // 6
		set3.add(candidate3);
		set3.add(candidate1);
		RoutingCandidateSet set1 = new RoutingCandidateSet(); // 4
		set1.add(candidate2);
		set1.add(candidate1);
		
		RoutingCandidateTree tree = new RoutingCandidateTree();
		tree.addRoutingCandidateSet(set1);
		tree.addRoutingCandidateSet(set2);
		tree.addRoutingCandidateSet(set3);

		RoutingCandidateTreeIterator it = tree.iterator();
		RoutingCandidate candidate = it.next();
		assertSame(candidate3, candidate);
		it.successfulDisposition();
		candidate = it.next();
		assertSame(candidate1, candidate);
		assertFalse(it.hasNext());
		assertNull(it.next());
	}
	
	@Test
	public void testWithSimiliarNonDuplicates() {
		Io io1 = new Io(new Long(0), new Long(0), "io1", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate1 = RoutingCandidate.create(io1);
		Io io2 = new Io(new Long(0), new Long(0), "io2", new Long(0), new BigDecimal(3.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate2 = RoutingCandidate.create(io2);
		Io io3 = new Io(new Long(0), new Long(0), "io3", new Long(0), new BigDecimal(3.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate3 = RoutingCandidate.create(io3);
		
		RoutingCandidateSet set2 = new RoutingCandidateSet(); // 4
		set2.add(candidate2);
		set2.add(candidate1);
		RoutingCandidateSet set3 = new RoutingCandidateSet(); // 4
		set3.add(candidate3);
		set3.add(candidate1);
		RoutingCandidateSet set1 = new RoutingCandidateSet(); // 6
		set1.add(candidate2);
		set1.add(candidate3);
		
		RoutingCandidateTree tree = new RoutingCandidateTree();
		tree.addRoutingCandidateSet(set1);
		tree.addRoutingCandidateSet(set2);
		tree.addRoutingCandidateSet(set3);

		RoutingCandidateTreeIterator it = tree.iterator();
		RoutingCandidate candidate = it.next();
		assertTrue(candidate == candidate3 || candidate == candidate2);
		candidate = it.next();
		assertTrue(candidate == candidate3 || candidate == candidate2);
		candidate = it.next();
		assertSame(candidate1, candidate);
		assertFalse(it.hasNext());
		assertNull(it.next());
	}

	
	@Test
	public void testSuccessfulDispositionWithSimiliarNonDuplicates() {
		Io io1 = new Io(new Long(0), new Long(0), "io1", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate1 = RoutingCandidate.create(io1);
		Io io2 = new Io(new Long(0), new Long(0), "io2", new Long(0), new BigDecimal(3.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate2 = RoutingCandidate.create(io2);
		Io io3 = new Io(new Long(0), new Long(0), "io3", new Long(0), new BigDecimal(3.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate3 = RoutingCandidate.create(io3);
		
		RoutingCandidateSet set2 = new RoutingCandidateSet(); // 4
		set2.add(candidate2);
		set2.add(candidate1);
		RoutingCandidateSet set3 = new RoutingCandidateSet(); // 4
		set3.add(candidate3);
		set3.add(candidate1);
		RoutingCandidateSet set1 = new RoutingCandidateSet(); // 6
		set1.add(candidate2);
		set1.add(candidate3);
		
		RoutingCandidateTree tree = new RoutingCandidateTree();
		tree.addRoutingCandidateSet(set1);
		tree.addRoutingCandidateSet(set2);
		tree.addRoutingCandidateSet(set3);

		RoutingCandidateTreeIterator it = tree.iterator();
		RoutingCandidate candidate = it.next();
		assertTrue(candidate == candidate3 || candidate == candidate2);
		it.successfulDisposition();
		candidate = it.next();
		assertTrue(candidate == candidate3 || candidate == candidate2);
		it.successfulDisposition();
		assertFalse(it.hasNext());
		assertNull(it.next());
	}
	
	@Test
	public void testShuffling() {
		Io io1 = new Io(new Long(0), new Long(0), "io1", new Long(0), new BigDecimal(1.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate1 = RoutingCandidate.create(io1);
		Io io2 = new Io(new Long(0), new Long(0), "io2", new Long(0), new BigDecimal(3.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate2 = RoutingCandidate.create(io2);
		Io io3 = new Io(new Long(0), new Long(0), "io3", new Long(0), new BigDecimal(3.0), 0, new Long(0), "", false, new Long(0), "");
		RoutingCandidate candidate3 = RoutingCandidate.create(io3);
		
		RoutingCandidateSet set2 = new RoutingCandidateSet(); // 4
		set2.add(candidate2);
		set2.add(candidate1);
		RoutingCandidateSet set3 = new RoutingCandidateSet(); // 4
		set3.add(candidate3);
		set3.add(candidate1);
		RoutingCandidateSet set1 = new RoutingCandidateSet(); // 6
		set1.add(candidate2);
		set1.add(candidate3);
		
		RoutingCandidateTree tree = new RoutingCandidateTree();
		tree.addRoutingCandidateSet(set1);
		tree.addRoutingCandidateSet(set2);
		tree.addRoutingCandidateSet(set3);

		Random rand = new java.util.Random();
		rand.nextDouble();
		rand.nextBoolean();
		Queue<RoutingCandidateSet> innerSet = tree.getCandidateSet();
		List<RoutingCandidateSet> list = new ArrayList<>(innerSet);
		Collections.shuffle(list, rand);
		tree.setCandidateSet(new LinkedList<RoutingCandidateSet>(list));
		
		Iterator<RoutingCandidate> iterator = tree.iterator();
		RoutingCandidate testCandidate1 = iterator.next();
		RoutingCandidate testCandidate2 = iterator.next();
		RoutingCandidate testCandidate3 = iterator.next();
		assertFalse(iterator.hasNext());
		assertNull(iterator.next());
		
		//fail("Insufficient checks for success");
	}

}
