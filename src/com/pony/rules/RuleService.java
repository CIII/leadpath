package com.pony.rules;

import com.pony.PonyException;
import com.pony.advertiser.*;
import com.pony.core.PonyService;
import com.pony.publisher.PublisherService;
import com.pony.routing.AdvertiserPostContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Copyright 2011 PonyMash LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 5:19 PM
 */
public class RuleService extends PonyService {
	private static final Log LOG = LogFactory.getLog(RuleService.class);
	
    private AdvertiserService advertiserService;
    private PublisherService publisherService;
    private final List<Rule> rules = new ArrayList<Rule>();
    
    @Inject RuleService(Set<Rule> rules) {
    	this.rules.addAll(rules);
    }

    @Override
    protected void addRuleService(RuleService service) {
        //noop
    }

    @Override
    public void addAdvertiserService(AdvertiserService service) {
        if (advertiserService != null) {
            throw new IllegalStateException("advertiserService already set");
        }
        this.advertiserService = service;
    }

    @Override
    protected void addPublisherService(PublisherService service) {
        if (publisherService != null) {
            throw new IllegalStateException("publisherService already set");
        }
        this.publisherService = service;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        rules.clear();
    }

    /**
     * check any pre routing conditions and manipulate the candidates accordingly (sort, filter, add, ...)
     *
     * @param postContext the post context containing the routing candidates
     * @param vResponse   lead validation response (from preceding validation response) to take into consideration
     * @return a rule response, indicating weather or not we want to continue with the post (phase)
     */
    public RuleResponse beforePost(AdvertiserPostContext postContext, ValidationResponse vResponse) throws PonyException {
        List<RuleResponse> responses = new ArrayList<RuleResponse>();

        // pre post phase
        for (Rule rule : rules) {
        	LOG.info("Rule: " + rule.getClass().getName());
            RuleResponse ruleResponse = rule.beforePost(postContext, vResponse);
            responses.add(ruleResponse);
            if (ruleResponse.stopPhase()) {
                LOG.info("stopPhase: " + postContext + ": rule=" + rule + " stopped phase for " + ruleResponse);
                break;
            }
            else if (ruleResponse.stop()) {
                LOG.info("stop: " + postContext + ": rule=" + rule + " stopped phase for " + ruleResponse);
                return RuleResponse.STOP_ALL_RESPONSE;
            }
        }

        return RuleResponse.createBeforePostResponse(responses);
    }

    /**
     * Now that we attempted to actually post to the candidate, allow the rules to check the post response and react
     *
     * @param postContext        the context with all the candidates
     * @param candidate          the current candidate we just attempted to post to
     * @param advertiserResponse the response we received from the posting destination
     * @return the rule response, indicating weather or not we want to continue with the post phase
     */
    public RuleResponse afterPost(AdvertiserPostContext postContext, RoutingCandidate candidate, Disposition advertiserResponse) {
        Map<String, RuleResponse> responses = new TreeMap<String, RuleResponse>();

        LOG.info("rule_afterPost:");
        for (Rule rule : rules) {
            RuleResponse ruleResponse = rule.afterPost(postContext, candidate, advertiserResponse);
            responses.put(rule.getName(), ruleResponse);
            if (ruleResponse.stopPhase()) {
                LOG.info("stopPhase: " + rule + ": " + ruleResponse);
                break;
            }
            else if (ruleResponse.stop()) {
                LOG.info("stop: " + rule + ": " + ruleResponse);
                return ruleResponse;
            }
            else if (ruleResponse.restack()) {
                LOG.info("restack: " + rule + ": " + ruleResponse);
                return ruleResponse;
            }
        }

        return new AfterPostRuleResponse(responses);
    }

    public RuleResponse beforeDisposition(DispositionContext dispositionContext) {
        //todo
        return RuleResponse.NOOP;
    }

    @Override
    public String toString() {
        return "RuleService{" +
                "rules=" + rules +
                '}';
    }

    public RuleResponse beforePing(PingContext pingContext, ValidationResponse validationResponse) {
        LOG.info("RuleService before ping...." + pingContext);
// filter out anything that we don't want to actually ping
        for (Rule rule : rules) {
            RuleResponse ruleResponse = rule.beforePing(pingContext, validationResponse);
            //TODO
        }

        return RuleResponse.NOOP;
    }

    public RuleResponse afterPing(PingContext pingContext) {
        // filter the rejected candidates
        List<RoutingCandidate> toBeRemoved = new ArrayList<RoutingCandidate>();

        for (RoutingCandidate candidate : pingContext.getRoutingCandidates()) {
            // first check if this is a candidate we already pinged, and if so, check what we received
            for (Disposition pingDisposition : candidate.getResponseDispositions()) {
                if (!pingDisposition.isAccepted()) {
                    toBeRemoved.add(candidate);
                }
            }
        }
        pingContext.removeCandidates(toBeRemoved);

        // now give the rules a chance
        for (Rule rule : rules) {
            rule.afterPing(pingContext);
        }
        return RuleResponse.NOOP;
    }

    public RuleResponse poll(AdvertiserPollContext pollContext) {
        for (Rule rule : rules) {
            RuleResponse ruleResponse = rule.poll(pollContext);
            if (ruleResponse.stop()) {
                LOG.info("poll: stop: " + rule + ": " + ruleResponse);
                return ruleResponse;
            }
        }

        return RuleResponse.NOOP;
    }
}
