package com.pony.advertiser;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * This class represents an interable group of candidates, delivered in the order that they should be offered to. The
 * reason that this is Iterable, but not a Collection, is that it is undefined how many candidates are in the
 * collection, even once it is fully populated. The reason for this is that the candidates are loaded in according to
 * sets that are ordered in the tree according to the maximum potential revenue. If any candidate in a
 * RoutingCandidateSet is accepted, then all RoutingCandidateSets that do NOT contain that candidate should be removed.
 * The remaining RoutingCandidateSets should remain sorted to maximize remaining prices. A candidate NOT being accepted
 * does not necessarily remove any sets from consideration, but the candidate should be removed from that candidate set,
 * and the next candidate should not necessarily come from the same set.
 * 
 * @author Jonathan Card
 *
 */
public class RoutingCandidateTree implements Iterable<RoutingCandidate> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9157020628194731701L;
	
	private RoutingCandidateTreeIterator iterator = null;
	private Queue<RoutingCandidateSet> candidateSets = new PriorityQueue<>(11, new Comparator<RoutingCandidateSet>() {

		@Override
		public int compare(RoutingCandidateSet o1, RoutingCandidateSet o2) {
			return o2.getTotalPrice().compareTo(o1.getTotalPrice());
		}
		
	});
	
	public RoutingCandidateTree() {}
	
	public RoutingCandidateTree(Class<? extends Queue<RoutingCandidateSet>> cls) throws InstantiationException, IllegalAccessException {
		this.candidateSets = cls.newInstance();
	}

	public void addRoutingCandidateSet(RoutingCandidateSet candidates) {
		candidateSets.add(candidates);
	}

	/**
	 * This object can only have 
	 */
	public RoutingCandidateTreeIterator iterator() {
		if(iterator == null) {
			iterator = new RoutingCandidateTreeIterator();
		}
		return iterator;
	}
	
	public int getCandidateSetsSize() {
		return candidateSets.size();
	}
	
	/**
	 * This returns a copy of the queue used internally, to prevent altering it by reference. This is provided so that
	 * rules can alter the sorting order by getting this copy, and replacing the queue with setCandidateSet. By removing
	 * changing the implementation class of the queue, the sorting algorithm is effectively disabled.
	 * 
	 * @return
	 */
	public Queue<RoutingCandidateSet> getCandidateSet() {
		return new LinkedList<>(this.candidateSets);
	}
	
	public void setCandidateSet(Queue<RoutingCandidateSet> newQueue) {
		this.candidateSets = newQueue;
	}

	public class RoutingCandidateTreeIterator implements Iterator<RoutingCandidate> {
		private RoutingCandidate deliveredItem = null;
		private Map<RoutingCandidateSet, Integer> deliveredFrom = null;
		
		@Override
		public boolean hasNext() {
			if(!RoutingCandidateTree.this.candidateSets.isEmpty()) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public RoutingCandidate next() {
			if(!candidateSets.isEmpty() && !candidateSets.peek().isEmpty()) {
				deliveredFrom = new HashMap<>();
				RoutingCandidateSet candidateSet = candidateSets.poll();
				deliveredFrom.put(candidateSet, 1);
				deliveredItem = candidateSet.poll();
				// TODO: Test the crap out of this to ensure I do not need to touch every set in the tree.
				Iterator<RoutingCandidateSet> iterator = candidateSets.iterator();
				Queue<RoutingCandidateSet> setsToAddBack = new LinkedList<>();
				while(iterator.hasNext()) {
					// For each candidate set being trimmed, remove it from the master set, remove the item being
					//   delivered to adjust the totalPrice, and re-add it to re-sort the master set.
					// For one of these, it will not need to be removed as it was the top set removed in poll() above.
					RoutingCandidateSet candidateSetToTrim = iterator.next();
					iterator.remove();
					boolean removed = candidateSetToTrim.remove(deliveredItem);
					if(removed) deliveredFrom.put(candidateSetToTrim, 1);
					if(!candidateSetToTrim.isEmpty()) {
						setsToAddBack.add(candidateSetToTrim);
					}
				}
				if(!candidateSet.isEmpty()) {
					candidateSets.add(candidateSet);
				}
				while(!setsToAddBack.isEmpty()) {
					candidateSets.add(setsToAddBack.poll());
				}
				return deliveredItem;
			} else {
				return null;
			}
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			throw new IllegalStateException("Remove is not valid for this iterator.");
		}
		
		public void successfulDisposition() {
			// TODO: When a disposition was successful, trim off all of the
			// subsequent RoutingCandidateSets and only iterate over the
			// RoutingCandidateSets under the disposition most recently delivered,
			// with that item removed.
			Iterator<RoutingCandidateSet> iterator = candidateSets.iterator();
			while(iterator.hasNext()) {
				RoutingCandidateSet candidateSetToRemove = iterator.next();
				if(deliveredFrom.get(candidateSetToRemove) != null || candidateSetToRemove.contains(deliveredItem)) {
					continue;
				} else {
					iterator.remove();
				}
			}
		}
	}
}
