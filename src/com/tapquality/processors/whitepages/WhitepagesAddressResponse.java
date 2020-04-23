package com.tapquality.processors.whitepages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WhitepagesAddressResponse {
	@JsonProperty("street_line_1")
	protected String streetLine1 = null;
	@JsonProperty("street_line_2")
	protected String streetLine2 = null;
	@JsonProperty("city")
	protected String city = null;
	@JsonProperty("postal_code")
	protected String postalCode = null;
	@JsonProperty("state_code")
	protected String stateCode = null;
	@JsonProperty("country_name")
	protected String countryName = null;
	@JsonProperty("country_code")
	protected String countryCode = null;
	
	public String getStreetLine1() {
		return streetLine1;
	}
	public void setStreetLine1(String streetLine1) {
		this.streetLine1 = streetLine1;
	}
	public String getStreetLine2() {
		return streetLine2;
	}
	public void setStreetLine2(String streetLine2) {
		this.streetLine2 = streetLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
