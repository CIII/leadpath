package com.pony.rules;

import com.pony.advertiser.AdvertiserPollContext;
import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Buyer;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.Io;
import com.pony.advertiser.RoutingCandidate;
import com.pony.lead.Lead;
import com.pony.models.LeadMatchModel;
import com.pony.routing.AdvertiserPostContext;
import com.pony.routing.IAdvertiserPostContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.Map;

/**
 * check if we need to set a preferred buyer for the ping responses we received.
 * <p/>
 * ArbVentures 2013.
 * User: martin
 * Date: 9/13/13
 * Time: 3:13 PM
 */
public class PreferredBuyerRule extends Rule {
	private static final Log LOG = LogFactory.getLog(PreferredBuyerRule.class);
	
    public PreferredBuyerRule() {
        super("PreferredBuyerRule");
    }

    @Override
    public RuleResponse beforePing(PingContext pingContext, ValidationResponse validationResponse) {
        return RuleResponse.NOOP;
    }

    @Override
    public RuleResponse afterPing(PingContext pingContext) {
        for (RoutingCandidate candidate : pingContext.getRoutingCandidates()) {
            for (Disposition pingDisposition : candidate.getResponseDispositions()) {
                Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());

                for (Buyer buyer : pingDisposition.getBuyers()) {
                    // one way to toggle this: add a flag to the source id to allow for external configuration of preferred orders
                    // TODO: check this once we have real world requirements
                    if (map.containsKey("preferred") && buyer.getPrice().doubleValue() > 10.0d) {
                        Buyer preferredBuyer = pingContext.getPreferredBuyer();
                        if (preferredBuyer == null || preferredBuyer.getPrice().doubleValue() < buyer.getPrice().doubleValue()) {
                            pingContext.setPreferredBuyer(buyer);
                        }
                    }
                }
            }
        }

        return RuleResponse.NOOP;
    }

    @Override
    public RuleResponse beforePost(IAdvertiserPostContext postContext, ValidationResponse validationResponse) {
        return RuleResponse.NOOP;
    }

    @Override
    public RuleResponse afterPost(AdvertiserPostContext postContext, RoutingCandidate routingCandidate, Disposition advertiserResponse) {
        return RuleResponse.NOOP;
    }

    /**
     * look into the buyers attached to the dispositions for the lead we are polling for, and see if there is any buyer there that we prefer.
     *
     * @param pollContext
     * @return rule response that can contain the newly organized dispositions.
     *         If the rule wants to be heard, it needs to return a rule response that stops the
     *         processing #RuleResponse.stop needs to return true, and the #getResponseDispositions() method needs to return the
     */
    @Override
    public RuleResponse poll(AdvertiserPollContext pollContext) {
        for (Map.Entry<Io, Disposition> entry : pollContext.getOrderDispositions().entrySet()) {
            Map<String, String> map = AdvertiserWriter.parseStringToMap(entry.getKey().getSourceId());
            if (!map.containsKey("preferred")) {
                // skip orders that don't care about preferred treatment
                continue;
            }

            Lead lead = pollContext.getPublisherContext().getLead();
            String zip = null, usedOrNew = null, decided = null;
            if (lead != null) {
                zip = lead.getAttributeValue("zipcode");
                decided = lead.getAttributeValue("decided"); // ready_to_buy, still_researching, not_yet_decided
                usedOrNew = lead.getAttributeValue("used_new"); // not_sure, new, used, new_and_used
            }

            //TODO: filter, push, ... based on zip, decided, usedOrNew (and price)

            for (Buyer buyer : entry.getValue().getBuyers()) {
                // one way to toggle this: add a flag to the source id to allow for external configuration of preferred orders
                // TODO: check this once we have real world requirements
                if (buyer.getPrice().doubleValue() > 10.0d || (zip != null && buyer.getPrice().doubleValue() > 8.0d && buyer.getZipcode().startsWith(zip.substring(0, 2)))) {
                    pollContext.setPreferredBuyer(buyer);
                    try {
                        LeadMatchModel.flagBuyerDispositionForPreferred(buyer);
                    }
                    catch (NamingException e) {
                        LOG.error(e);
                    }
                    catch (SQLException e) {
                        LOG.error(e);
                    }
                }
            }
        }

        return RuleResponse.NOOP;
    }
}
