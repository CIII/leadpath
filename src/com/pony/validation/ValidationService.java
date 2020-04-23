package com.pony.validation;

import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 6:42 PM
 */
public class ValidationService {
    public void start() {

    }

    public void stop() {

    }

    public ValidationResponse post(IPublisherContext context) throws ValidationException {
        // get the lead type and have that validate the lead data
        return context.getLeadType().validate(context);
    }

    public ValidationResponse ping(IPublisherContext context) throws ValidationException {
        // get the lead type and have that validate the lead data
        return context.getLeadType().validate(context);
    }

    public ValidationResponse email(IPublisherContext context) throws ValidationException {
        return context.getLeadType().validate(context);
    }

    public ValidationResponse poll(IPublisherContext context) throws ValidationException {
        return context.getLeadType().validate(context);
    }
}
