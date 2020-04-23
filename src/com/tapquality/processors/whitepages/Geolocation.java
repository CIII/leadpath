package com.tapquality.processors.whitepages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Geolocation {
	@JsonProperty("postal_code")
	protected String postalCode = null;
	@JsonProperty("city_name")
	protected String cityName = null;
	@JsonProperty("subdivision")
	protected String subdivision = null;
	@JsonProperty("country_name")
	protected String countryName = null;
	@JsonProperty("continent_code")
	protected String continentCode = null;
	@JsonProperty("country_code")
	protected String countryCode = null;
	
	public Geolocation() {}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getSubdivision() {
		return subdivision;
	}

	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getContinentCode() {
		return continentCode;
	}

	public void setContinentCode(String continentCode) {
		this.continentCode = continentCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
