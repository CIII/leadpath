package com.tapquality.processors.whitepages;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressCheckResponse {
	@JsonProperty("error")
	protected WhitepagesErrorMessage error = null;
	@JsonProperty("warnings")
	protected List<String> warnings = null;
	@JsonProperty("is_valid")
	protected Boolean valid = null;
	@JsonProperty("diagnostics")
	protected List<String> diagnostics = null;
	@JsonProperty("address_contact_score")
	protected Integer addressContactScore = null;
	@JsonProperty("is_active")
	protected Boolean active = null;
	@JsonProperty("address_to_name")
	protected String addressToName = null;
	@JsonProperty("resident_name")
	protected String residentName = null;
	@JsonProperty("resident_age_range")
	protected String residentAgeRange = null;
	@JsonProperty("resident_gender")
	protected String residentGender = null;
	@JsonProperty("type")
	protected String type = null;
	@JsonProperty("is_commercial")
	protected Boolean commercial = null;
	@JsonProperty("resident_phone")
	protected String residentPhone = null;
	
	public AddressCheckResponse() {}

	public WhitepagesErrorMessage getError() {
		return error;
	}

	public void setError(WhitepagesErrorMessage error) {
		this.error = error;
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

	public List<String> getDiagnostics() {
		return diagnostics;
	}

	public void setDiagnostics(List<String> diagnostics) {
		this.diagnostics = diagnostics;
	}

	public Integer getAddressContactScore() {
		return addressContactScore;
	}

	public void setAddressContactScore(Integer addressContactScore) {
		this.addressContactScore = addressContactScore;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getAddressToName() {
		return addressToName;
	}

	public void setAddressToName(String addressToName) {
		this.addressToName = addressToName;
	}

	public String getResidentName() {
		return residentName;
	}

	public void setResidentName(String residentName) {
		this.residentName = residentName;
	}

	public String getResidentAgeRange() {
		return residentAgeRange;
	}

	public void setResidentAgeRange(String residentAgeRange) {
		this.residentAgeRange = residentAgeRange;
	}

	public String getResidentGender() {
		return residentGender;
	}

	public void setResidentGender(String residentGender) {
		this.residentGender = residentGender;
	}

	public String getAddressType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean isCommercial() {
		return commercial;
	}

	public void setCommercial(Boolean commercial) {
		this.commercial = commercial;
	}

	public String getResidentPhone() {
		return residentPhone;
	}

	public void setResidentPhone(String residentPhone) {
		this.residentPhone = residentPhone;
	}
}
