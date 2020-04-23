package com.pony.rules;

import java.util.Map;
import java.util.TreeMap;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 10/30/11
 * Time: 10:07 PM
 */
public class AfterPostRuleResponse extends RuleResponse {
    private final Map<String, RuleResponse> responses = new TreeMap<String, RuleResponse>();

    public AfterPostRuleResponse(Map<String, RuleResponse> responses) {
        super(false, false, false);
        this.responses.putAll(responses);
    }

    public Map<String, RuleResponse> getResponses() {
        return responses;
    }
}
