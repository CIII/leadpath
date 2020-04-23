package com.pony.advertiser.writers.solar.zoho;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="response")
public class ZohoResponse {

	@Element(required=false)
	private ZohoError error;
	
	@Element(required=false)
	private ZohoResult result;
	
	@Attribute(name="uri")
	private String uri;

	public ZohoError getError() {
		return error;
	}

	public ZohoResult getResult() {
		return result;
	}

}
