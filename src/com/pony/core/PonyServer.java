package com.pony.core;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.pony.advertiser.AdvertiserService;
import com.pony.email.EmailService;
import com.pony.publisher.PublisherService;
import com.pony.rules.RuleService;
import com.pony.validation.ValidationService;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 6:41 PM
 */
@Singleton
public class PonyServer {
    private ValidationService vService;
    private PublisherService pService;
    private RuleService rService;
    private AdvertiserService aService;
    private EmailService eService;
    
    public PonyServer() {}
    
    @Inject PonyServer(AdvertiserService aService, RuleService rService, PublisherService pService) {
    	this.aService = aService;
    	this.rService = rService;
    	this.pService = pService;
    }

    public PublisherService getPublisherService() {
        return pService;
    }

    public AdvertiserService getAdvertiserService() {
        return aService;
    }

    public EmailService getEmailService() {
        return eService;
    }

    public ValidationService getValidationService() {
		return vService;
	}

	public PonyServer start() {
        vService = new ValidationService();
        pService.addValidationService(vService);

        eService = new EmailService();

        pService.addRuleService(rService);
        pService.addAdvertiserService(aService);
        pService.addEmailService(eService);

        rService.addAdvertiserService(aService);

        aService.addRuleService(rService);
        aService.addPublisherService(pService);

        rService.start();
        vService.start();
        aService.start();
        pService.start();
        eService.start();

        return this;
    }

    public void stop() {
        eService.stop();
        pService.stop();
        aService.stop();
        rService.stop();
        vService.stop();

        pService = null;
        aService = null;
        rService = null;
        vService = null;
    }
}
