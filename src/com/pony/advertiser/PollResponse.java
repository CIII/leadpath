package com.pony.advertiser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 9/13/13
 * Time: 3:50 PM
 */
public class PollResponse {
    private AdvertiserPollContext pollContext;

    private PollResponse(AdvertiserPollContext pollContext) {
        this.pollContext = pollContext;
    }

    public static PollResponse create(AdvertiserPollContext pollContext) {
        return new PollResponse(pollContext);
    }

    public Map<Io, Disposition> getOrderDispositions() {
        return pollContext.getOrderDispositions();
    }

    public List<Disposition> getDispositions() {
        List<Disposition> dispositions = new LinkedList<Disposition>();
        dispositions.addAll(pollContext.getOrderDispositions().values());
        return Collections.unmodifiableList(dispositions);
    }

    public List<Buyer> getPreferredBuyers() {
        return pollContext.getPreferredBuyers();
    }
}
