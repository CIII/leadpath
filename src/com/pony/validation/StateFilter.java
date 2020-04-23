package com.pony.validation;

import com.pony.advertiser.Io;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 6/26/13
 * Time: 2:44 PM
 */
public class StateFilter extends Filter {
    private final Io io;
    private final boolean allow;
    private final String states;

    private StateFilter(Io io, boolean allow, String states) {
        this.io = io;
        this.allow = allow;
        this.states = states.toUpperCase();
    }

    @Override
    public boolean pass(Publisher publisher, PublisherList publisherList, Lead lead, PonyPhase phase) {
        String state = lead.getAttributeValue("state");
        // if this order is configured to only allow the listed states, and there is no state presented, then deny
        if (allow && (state == null || "".equals(state))) {
            return false;
        }
        boolean included = states.contains(state.toUpperCase());
        return allow && included || !allow && !included;
    }

    public static Filter create(Io io, boolean allow, String states) {
        if (io == null || states == null || "".equals(states)) {
            throw new IllegalArgumentException("Io and/or states can not be null");
        }
        return new StateFilter(io, allow, states);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StateFilter that = (StateFilter) o;
        return io.equals(that.io);
    }

    @Override
    public int hashCode() {
        return io.hashCode();
    }

    @Override
    public String toString() {
        return "StateFilter{" +
                "io=" + io +
                ", allow=" + allow +
                ", states='" + states + '\'' +
                '}';
    }
}
