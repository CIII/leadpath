package com.pony.publisher;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2011 PonyMash LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 5:38 PM
 */
@SuppressWarnings("serial")
public class PublisherException extends Throwable {
	protected List<String[]> errorCodes = new ArrayList<>();
	
    public PublisherException(String msg) {
        super(msg);
    }

    public PublisherException(Throwable e) {
        super(e);
    }
    
    public PublisherException(String msg, Throwable e) {
    	super(msg, e);
    }
    
    public void addCode(String code, String message) {
    	errorCodes.add(new String[]{code, message});
    }
    
    public List<String[]> getCodes() {
    	return new ArrayList<>(errorCodes);
    }
}
