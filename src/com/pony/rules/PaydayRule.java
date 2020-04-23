package com.pony.rules;

import com.pony.advertiser.Disposition;
import com.pony.advertiser.AdvertiserPollContext;
import com.pony.advertiser.RoutingCandidate;
import com.pony.routing.AdvertiserPostContext;
import com.pony.routing.IAdvertiserPostContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/12/11
 * Time: 6:53 PM
 */
public class PaydayRule extends Rule {
    public PaydayRule() {
        super("PaydayRule");
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
    public RuleResponse beforePost(IAdvertiserPostContext postContext, ValidationResponse validationResponse) {
        //todo
        return RuleResponse.NOOP;
    }

    @Override
    public RuleResponse afterPost(AdvertiserPostContext postContext, RoutingCandidate routingCandidate, Disposition advertiserResponse) {
        //todo
        return RuleResponse.NOOP;
    }

    @Override
    public RuleResponse poll(AdvertiserPollContext pollContext) {
        return RuleResponse.NOOP;
    }
}
