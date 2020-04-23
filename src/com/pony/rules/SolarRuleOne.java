package com.pony.rules;

import com.pony.PonyException;
import com.pony.advertiser.Advertiser;
import com.pony.advertiser.AdvertiserPollContext;
import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.models.AdvertiserModel;
import com.pony.models.AdvertiserWriterModel;
import com.pony.models.LeadMatchModel;
import com.pony.routing.AdvertiserPostContext;
import com.pony.routing.IAdvertiserPostContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;

import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

/**
 * This rule instructs each writer to request a price from the Advertiser before proceeding with any post. It then
 * performs a superfluous sorting, which is going to be over-written, anyway.
 * 
 * Created by martin on 6/23/16.
 */
public class SolarRuleOne extends Rule {
	private static final Log LOG = LogFactory.getLog(SolarRuleOne.class);
	
	protected Map<String, AdvertiserWriter> advertiserWriters = null;
	protected Properties writerProperties;
	
    public SolarRuleOne() {
        super("SolarRuleOne");
    }
    
    @Inject SolarRuleOne(Map<String, AdvertiserWriter> advertiserWriters, @Named("writerProperties") Properties advertiserWriterProperties) {
    	super("SolarRuleOne");
    	this.advertiserWriters = advertiserWriters;
    	this.writerProperties = advertiserWriterProperties;
    }

    @Override
    public RuleResponse beforePing(PingContext pingContext, ValidationResponse validationResponse) {
        return RuleResponse.NOOP;
    }

    @Override
    public RuleResponse afterPing(PingContext pingContext) {
        return RuleResponse.NOOP;
    }

    @Override
    public RuleResponse beforePost(IAdvertiserPostContext postContext, ValidationResponse validationResponse) throws PonyException {

        try {

            List<RoutingCandidate> candidates = postContext.getRoutingCandidates();
            for (RoutingCandidate candidate : candidates) {
                LOG.info("\r\nbeforePost; looking for price Dispositions: candidate=" + candidate);

                Long advertiserId = candidate.getIo().getAdvertiserId();
                AdvertiserWriter writer = getWriter(advertiserId);
                LOG.info("Get price with writer " + writer.getClass().getName());

                try {
                    Disposition priceDisposition = writer.requestPrice(PonyPhase.REQUEST_PRICE, validationResponse, candidate, postContext.getLead(), candidate.getLeadMatchId());
                    LOG.debug("--> priceDisposition=" + priceDisposition);

                    // TODO: Do I add a step to add a candidate for each buyer returned by the priceDisposition? Or do I add another rule for this?
                    
                    if (priceDisposition != null && priceDisposition.isAccepted()) {
                        candidate.addResponseDisposition(PonyPhase.REQUEST_PRICE, priceDisposition);
                    } else if (priceDisposition != null) {
                    	candidate.addErrorResponseDisposition(PonyPhase.REQUEST_PRICE, priceDisposition);
                    }
                } catch (Throwable e) {
                	LOG.error("Exception requesting price for writer " + candidate.getIo().getCode() + ".", e);
                	// TODO: I'm not sure whether to add a response disposition here depicting the error, because I
                	//   don't really have faith that we will check whether the disposition is valid or not when we
                	//   attempt to use it.
                }
            }
            
            return RuleResponse.NOOP;
        }
        catch (NamingException e) {
            LOG.error(e);
        }
        catch (SQLException e) {
            LOG.error(e);
        }

        return RuleResponse.NOOP;
    }

	private AdvertiserWriter getWriter(Long advertiserId) throws SQLException, NamingException {
		Advertiser advertiser = AdvertiserModel.find(advertiserId);
		AdvertiserWriter writer = this.advertiserWriters.get(advertiser.getName());
		if(writer == null) throw new NullPointerException("No writer for advertiser " + advertiser.getName());
		writer.setProperties(advertiser.getName(), this.writerProperties);
		return writer;
	}

    @Override
    public RuleResponse afterPost(AdvertiserPostContext postContext, RoutingCandidate routingCandidate, Disposition advertiserResponse) {
        return RuleResponse.NOOP;
    }

    @Override
    public RuleResponse poll(AdvertiserPollContext pollContext) {
        return RuleResponse.NOOP;
    }
}
