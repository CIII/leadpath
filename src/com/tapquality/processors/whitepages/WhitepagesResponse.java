package com.tapquality.processors.whitepages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhitepagesResponse {
	@JsonProperty("error")
	protected WhitepagesErrorMessage error = null;
	@JsonProperty("phone_checks")
	protected PhoneCheckResponse phoneResponse = null;
	@JsonProperty("address_checks")
	protected AddressCheckResponse addressResponse = null;
	@JsonProperty("email_address_checks")
	protected EmailCheckResponse emailResponse = null;
	@JsonProperty("ip_address_checks")
	protected IpCheckResponse ipResponse = null;
	
	public WhitepagesResponse() {}

	public WhitepagesErrorMessage getError() {
		return error;
	}

	public void setError(WhitepagesErrorMessage error) {
		this.error = error;
	}

	public PhoneCheckResponse getPhoneResponse() {
		return phoneResponse;
	}

	public void setPhoneResponse(PhoneCheckResponse phoneResponse) {
		this.phoneResponse = phoneResponse;
	}

	public AddressCheckResponse getAddressResponse() {
		return addressResponse;
	}

	public void setAddressResponse(AddressCheckResponse addressResponse) {
		this.addressResponse = addressResponse;
	}

	public EmailCheckResponse getEmailResponse() {
		return emailResponse;
	}

	public void setEmailResponse(EmailCheckResponse emailResponse) {
		this.emailResponse = emailResponse;
	}

	public IpCheckResponse getIpResponse() {
		return ipResponse;
	}

	public void setIpResponse(IpCheckResponse ipResponse) {
		this.ipResponse = ipResponse;
	}
}
