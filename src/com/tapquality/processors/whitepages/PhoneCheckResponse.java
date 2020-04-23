package com.tapquality.processors.whitepages;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneCheckResponse {
	@JsonProperty("error")
	protected WhitepagesErrorMessage error = null;
	@JsonProperty("warnings")
	protected List<String> warnings = null;
	@JsonProperty("is_valid")
	protected Boolean valid = null;
	@JsonProperty("phone_contact_score")
	protected Integer phoneContactScore = null;
	@JsonProperty("is_connected")
	protected Boolean connected = null;
	@JsonProperty("phone_to_name")
	protected String phoneToName = null;
	@JsonProperty("subscriber_name")
	protected String subscriberName = null;
	@JsonProperty("subscriber_age_range")
	protected String subscriberAgeRange = null;
	@JsonProperty("subscriber_gender")
	protected String subscriberGender = null;
	@JsonProperty("subscriber_address")
	protected WhitepagesAddressResponse subscriberAddress = null;
	@JsonProperty("country_code")
	protected String countryCode = null;
	@JsonProperty("line_type")
	protected String lineType = null;
	@JsonProperty("carrier")
	protected String carrier = null;
	@JsonProperty("is_prepaid")
	protected Boolean prepaid = null;
	@JsonProperty("is_commercial")
	protected Boolean commercial = null;
	
	public PhoneCheckResponse() {}

	public WhitepagesErrorMessage getError() {
		return error;
	}

	public void setErrorMessage(WhitepagesErrorMessage error) {
		this.error = error ;
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	public Boolean isValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public Integer getPhoneContactScore() {
		return phoneContactScore;
	}

	public void setPhoneContactScore(Integer phoneContactScore) {
		this.phoneContactScore = phoneContactScore;
	}

	public Boolean isConnected() {
		return connected;
	}

	public void setConnected(Boolean connected) {
		this.connected = connected;
	}

	public String getPhoneToName() {
		return phoneToName;
	}

	public void setPhoneToName(String phoneToName) {
		this.phoneToName = phoneToName;
	}

	public String getSubscriberName() {
		return subscriberName;
	}

	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}

	public String getSubscriberAgeRange() {
		return subscriberAgeRange;
	}

	public void setSubscriberAgeRange(String subscriberAgeRange) {
		this.subscriberAgeRange = subscriberAgeRange;
	}

	public String getSubscriberGender() {
		return subscriberGender;
	}

	public void setSubscriberGender(String subscriberGender) {
		this.subscriberGender = subscriberGender;
	}

	public WhitepagesAddressResponse getSubscriberAddress() {
		return subscriberAddress;
	}

	public void setSubscriberAddress(WhitepagesAddressResponse subscriberAddress) {
		this.subscriberAddress = subscriberAddress;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public Boolean isPrepaid() {
		return prepaid;
	}

	public void setPrepaid(Boolean prepaid) {
		this.prepaid = prepaid;
	}

	public Boolean isCommercial() {
		return commercial;
	}

	public void setCommercial(Boolean commercial) {
		this.commercial = commercial;
	}
}
