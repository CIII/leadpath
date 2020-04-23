package com.pony.advertiser.writers.solar.zoho;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

@Root(name="result")
@Convert(ZohoResultConverter.class)
public class ZohoResult {

	private String message;
	private String id;

	public ZohoResult(String id, String message) {
		this.id = id;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getId() {
		return id;
	}
}
