package com.pony.advertiser.writers;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.routing.PingContext;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 4/22/14
 * Time: 4:28 PM
 */
public abstract class CouponHoundPingWriter extends AdvertiserWriter {

    @Override
    public final boolean supportsPhase(PonyPhase phase) {
        return (PonyPhase.PING == phase || PonyPhase.POST == phase);
    }

    @Override
    public Disposition ping(Long leadMatchId, PingContext pingContext, RoutingCandidate candidate) {

        // TODO: Apply filters

        return Disposition.createPing(Disposition.Status.ACCEPTED);
    }
}
