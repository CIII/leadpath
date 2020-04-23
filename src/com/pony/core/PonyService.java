package com.pony.core;

import com.pony.advertiser.AdvertiserService;
import com.pony.publisher.PublisherService;
import com.pony.rules.RuleService;

/**
 * Copyright 2011 PonyMash LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 5:19 PM
 */
public abstract class PonyService {
    protected abstract void addRuleService(RuleService service);

    protected abstract void addAdvertiserService(AdvertiserService service);

    protected abstract void addPublisherService(PublisherService service);

    protected abstract void start();
    protected abstract void stop();
}
