package com.tapquality.processors.whitepages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhitepagesErrorMessage {
	@JsonProperty("message")
	protected String message = null;
	@JsonProperty("name")
	protected String name = null;
	
	public WhitepagesErrorMessage() {}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
