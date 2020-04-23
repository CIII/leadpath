package com.pony.advertiser;

import com.pony.core.PonyPhase;

import java.math.BigDecimal;
import java.util.*;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 10/30/11
 * Time: 10:15 PM
 */
public class RoutingCandidate {
    private final Io io;
    private final Disposition disposition;
    private final Buyer buyer;

    private Long leadMatchId;

    private final Map<PonyPhase, Disposition> responseDispositions = new HashMap<PonyPhase, Disposition>();
    private final Map<PonyPhase, Disposition> errorResponseDispositions = new HashMap<PonyPhase, Disposition>();

    private RoutingCandidate(Io io, Disposition disposition, Buyer buyer) {
        this.io = io;
        this.disposition = disposition;
        this.buyer = buyer;
        if (disposition != null) {
            this.leadMatchId = disposition.getLeadMatchId();
        }
    }

    public static RoutingCandidate create(Io io, Disposition disposition, Buyer buyer) {
        return new RoutingCandidate(io, disposition, buyer);
    }

    public static RoutingCandidate create(Io io, Disposition disposition) {
        return new RoutingCandidate(io, disposition, null);
    }

    public static RoutingCandidate create(Io io) {
        return new RoutingCandidate(io, null, null);
    }

    /**
     * this is a way for the advertiser writer to signal additional information up the chain
     *
     * @param phase
     * @param disposition
     */
    public void addResponseDisposition(PonyPhase phase, Disposition disposition) {
        if (phase != null && disposition != null && !responseDispositions.containsKey(phase)) {
            this.responseDispositions.put(phase, disposition);
        }
        else {
            throw new IllegalStateException("already set: phase=" + phase + "; disposition=" + disposition);
        }
    }
    
	public void addErrorResponseDisposition(PonyPhase phase, Disposition disposition) {
		if (phase != null && disposition != null && !errorResponseDispositions.containsKey(phase)) {
			this.errorResponseDispositions.put(phase, disposition);
		} else {
			throw new IllegalStateException("Alread set: phase=" + phase + "; disposition=" + disposition);
		}
		
	}

    public Io getIo() {
        return io;
    }

    /**
     * Get all dispositions from the advertiser (for all phases we might have gone through already in this request)
     *
     * @return
     */
    public List<Disposition> getResponseDispositions() {
        return Collections.unmodifiableList(new ArrayList<Disposition>(responseDispositions.values()));
    }

    /**
     * get the disposition from the advertiser that was returned in one of the phases (ping ?)
     *
     * @param phase
     * @return
     */
    public Disposition getResponseDisposition(PonyPhase phase) {
        return responseDispositions.get(phase);
    }
    
    public Disposition getErrorResponseDisposition(PonyPhase phase) {
    	return errorResponseDispositions.get(phase);
    }

    /**
     * If this candidate was created with a disposition from a previous phase, return that here
     *
     * @return
     */
    public Disposition getDisposition() {
        return disposition;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoutingCandidate candidate = (RoutingCandidate) o;

        if (buyer != null ? !buyer.equals(candidate.buyer) : candidate.buyer != null) {
            return false;
        }
        if (disposition != null ? !disposition.equals(candidate.disposition) : candidate.disposition != null) {
            return false;
        }
        if (!io.equals(candidate.io)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = io.hashCode();
        result = 31 * result + (disposition != null ? disposition.hashCode() : 0);
        result = 31 * result + (buyer != null ? buyer.hashCode() : 0);
        return result;
    }

    public Long getLeadMatchId() {
        return leadMatchId;
    }

    public void setLeadMatchId(Long leadMatchId) {
        this.leadMatchId = leadMatchId;
    }

    @Override
    public String toString() {
        return "RoutingCandidate{" + "io=" + io + "; pingDisposition=" + disposition + "; buyer=" + buyer + "; priceDisposition=" + responseDispositions.get(PonyPhase.REQUEST_PRICE) + "}";
    }
    
    public BigDecimal getSortPrice() {
    	double price = this.getIo().getVpl().doubleValue();
    	
    	Disposition disposition = this.getResponseDisposition(PonyPhase.REQUEST_PRICE);
    	if(disposition != null && disposition.getPrice() != null) {
    		price = disposition.getPrice().doubleValue();
    	}
    	
    	if(this.buyer != null && buyer.getPrice() != null) {
    		price = this.buyer.getPrice().doubleValue();
    	}
    	
    	BigDecimal returnValue = new BigDecimal(price * (io.getWeight() / 100));
    	return returnValue;
    }
}
