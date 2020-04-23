package com.pony.advertiser.writers.solar.zoho;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class ZohoError {

	@Element(name="code")
	private int code;
	
	@Element(name="message")
	private String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
