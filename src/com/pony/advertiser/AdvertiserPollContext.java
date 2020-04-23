package com.pony.advertiser;

import com.pony.core.PonyPhase;
import com.pony.models.IoModel;
import com.pony.models.LeadMatchModel;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationException;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 9/13/13
 * Time: 3:51 PM
 */
public class AdvertiserPollContext {
    private final IPublisherContext publisherContext;
    private final Map<Io, Disposition> orderDispositions = new HashMap<Io, Disposition>();

    private List<Buyer> preferredBuyers = new ArrayList<Buyer>();

    private AdvertiserPollContext(IPublisherContext publisherContext, Map<Io, Disposition> orderDispositions) {
        this.publisherContext = publisherContext;
        this.orderDispositions.putAll(orderDispositions);
    }

    public static AdvertiserPollContext create(IPublisherContext publisherContext) throws NamingException, SQLException, ValidationException {
        // read the matches and dispositions for the lead reference
        List<Disposition> dispositions = LeadMatchModel.findDispositions(PonyPhase.PING, publisherContext.getLeadId());
        Map<Io, Disposition> orderDispositions = new HashMap<Io, Disposition>();

        // now read the Io for each disposition
        for (Disposition disposition : dispositions) {
            orderDispositions.put(IoModel.findByDispositionId(disposition.getId()).getIo(), disposition);
        }

        return new AdvertiserPollContext(publisherContext, orderDispositions);
    }

    public IPublisherContext getPublisherContext() {
        return publisherContext;
    }

    public Map<Io, Disposition> getOrderDispositions() {
        return orderDispositions;
    }

    public List<Buyer> getPreferredBuyers() {
        return Collections.unmodifiableList(preferredBuyers);
    }

    public void setPreferredBuyer(Buyer buyer) {
        buyer.setPreferred(true);
        preferredBuyers.add(buyer);
    }
}
