package com.pony.rules;

import com.pony.advertiser.Disposition;

import java.util.LinkedList;
import java.util.List;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 7:03 PM
 */
public abstract class RuleResponse {
    private final boolean stopPhase, stopAll, restack;
    public static final RuleResponse STOP_ALL_RESPONSE = new StopRuleProcessingResponse(false, true, "full stop");
    public static final RuleResponse NOOP = new NoopRuleResponse(false, false);
    public static final RuleResponse RESTACK = new RestackRuleResponse();
    public static final RuleResponse NO_MORE_UNITS = new StopRuleProcessingResponse(false, true, "no more units");
    public static final RuleResponse EXCLSIVE_AND_DONE = new StopRuleProcessingResponse(false, true, "exclusive and done");
    public static final RuleResponse NOTHING_LEFT_IN_STACK = new StopRuleProcessingResponse(false, true, "nothing left in stack");

    private final List<Disposition> dispositions = new LinkedList<Disposition>();

    protected RuleResponse(boolean stopPhase, boolean stopAll, boolean restack) {
        this.stopPhase = stopPhase;
        this.stopAll = stopAll;
        this.restack = restack;
    }

    public boolean stopPhase() {
        return stopPhase;
    }

    public boolean stop() {
        return stopAll;
    }

    public boolean restack() {
        return restack;
    }

    public static RuleResponse createBeforePostResponse(List<RuleResponse> responses) {
        return new BeforePostRuleResponse(responses);
    }

    public List<Disposition> getDispositions() {
        return dispositions;
    }

    /**
     * create a stop all response with the dispositions passed into the response
     * Note: this is intended to be used for poll requests and sorted dispositions
     *
     * @param dispositions
     * @return
     */
    public static RuleResponse create(List<Disposition> dispositions) {
        StopAllResponse response = new StopAllResponse();
        response.addDispositions(dispositions);
        return response;
    }

    public static RuleResponse createStopPhaseResponse() {
        return new StopPhaseResponse();
    }

    protected void addDispositions(List<Disposition> dispositions) {
        if (dispositions != null) {
            this.dispositions.addAll(dispositions);
        }
    }

    public static class StopAllResponse extends RuleResponse {
        private StopAllResponse() {
            super(false, true, false);
        }
    }

    public static class StopPhaseResponse extends RuleResponse {
        private StopPhaseResponse() {
            super(true, false, false);
        }
    }

    private static class NoopRuleResponse extends RuleResponse {
        protected NoopRuleResponse(boolean stopPhase, boolean stopAll) {
            super(stopPhase, stopAll, false);
        }
    }

    private static class RestackRuleResponse extends RuleResponse {
        protected RestackRuleResponse() {
            super(false, false, true);
        }
    }
}
