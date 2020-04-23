package com.pony.advertiser;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.AbstractQueue;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

public class RoutingCandidateSet extends AbstractQueue<RoutingCandidate> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5254944355373380501L;
	
	private Queue<RoutingCandidate> innerQueue = new PriorityQueue<RoutingCandidate>(11, new Comparator<RoutingCandidate>() {

			@Override
			public int compare(RoutingCandidate o1, RoutingCandidate o2) {
				int returnValue = o2.getSortPrice().compareTo(o1.getSortPrice());
				return returnValue;
			}

		});
	
	protected BigDecimal totalPrice = BigDecimal.ZERO;
	
	public RoutingCandidateSet() {}
	
	public RoutingCandidateSet(Class<? extends Queue<RoutingCandidate>> cls) throws InstantiationException, IllegalAccessException {
		this.innerQueue = cls.newInstance();
	}
	
	public RoutingCandidateSet(RoutingCandidateSet c) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		this.innerQueue = c.innerQueue.getClass().getConstructor(c.innerQueue.getClass()).newInstance(c.innerQueue);
		totalPrice = c.getTotalPrice();
	}

	@Override
	public boolean add(RoutingCandidate e) {
		boolean returnValue = super.add(e);
		if(returnValue) {
			this.totalPrice = this.totalPrice.add(e.getSortPrice());
		}
		return returnValue;
	}

	@Override
	public boolean remove(Object o) {
		boolean returnValue = super.remove(o);
		return returnValue;
	}
	
	@Override
	public RoutingCandidate poll() {
		RoutingCandidate returnValue = innerQueue.poll();
		if(returnValue != null) {
			this.totalPrice = this.totalPrice.subtract(returnValue.getSortPrice());
		}
		return returnValue;
	}

	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	@Override
	public boolean offer(RoutingCandidate e) {
		return innerQueue.offer(e);
	}

	@Override
	public RoutingCandidate peek() {
		return innerQueue.peek();
	}

	@Override
	public Iterator<RoutingCandidate> iterator() {
		return new Iterator<RoutingCandidate>() {
			private Iterator<RoutingCandidate> iterator = innerQueue.iterator();
			private RoutingCandidate lastDelivered = null;
			
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public RoutingCandidate next() {
				lastDelivered = iterator.next();
				return lastDelivered;
			}

			@Override
			public void remove() {
				iterator.remove();
				totalPrice = totalPrice.subtract(lastDelivered.getSortPrice());
			}
			
		};
	}

	@Override
	public int size() {
		return innerQueue.size();
	}
}
