package com.pony.rules;

import com.pony.advertiser.RoutingCandidate;
import com.pony.publisher.PublisherContext;

import java.util.LinkedList;
import java.util.List;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 10/30/11
 * Time: 9:48 PM
 */
public class RuleContext {
    private final PublisherContext publisherContext;

    public RuleContext(PublisherContext context) {
        this.publisherContext = context;
    }

    public PublisherContext getPublisherContext() {
        return publisherContext;
    }
}
