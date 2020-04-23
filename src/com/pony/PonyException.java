package com.pony;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/12/11
 * Time: 9:50 PM
 */
@SuppressWarnings("serial")
public class PonyException extends Throwable {
    public PonyException(Throwable throwable) {
        super(throwable);
    }

    public PonyException(String message) {
        super(message);
    }
    
    public PonyException(String message, Throwable throwable) {
    	super(message, throwable);
    }
}
