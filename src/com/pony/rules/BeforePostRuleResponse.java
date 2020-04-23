package com.pony.rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 10/30/11
 * Time: 10:04 PM
 */
public class BeforePostRuleResponse extends RuleResponse {
    private List<RuleResponse> ruleResponses = new ArrayList<RuleResponse>();

    protected BeforePostRuleResponse(List<RuleResponse> ruleResponses) {
        super(false, false, false);
        this.ruleResponses.addAll(ruleResponses);
    }

    public List<RuleResponse> getRuleResponses() {
        return ruleResponses;
    }
}
