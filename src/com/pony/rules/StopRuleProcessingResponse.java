package com.pony.rules;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 10/30/11
 * Time: 9:40 PM
 */
public class StopRuleProcessingResponse extends RuleResponse {
    private String comment;

    protected StopRuleProcessingResponse(boolean stopPhase, boolean stopAll, String comment) {
        super(stopPhase, stopAll, false);
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "StopRuleProcessingResponse{stopPhase=" + stopPhase() + " stopAll=" + stop() +
                " comment=" + comment + "}";
    }
}
