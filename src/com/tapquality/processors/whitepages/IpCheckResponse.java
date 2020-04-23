package com.tapquality.processors.whitepages;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IpCheckResponse {
	@JsonProperty("error")
	protected WhitepagesErrorMessage error = null;
	@JsonProperty("warnings")
	protected List<String> warnings = null;
	@JsonProperty("is_valid")
	protected Boolean valid = null;
	@JsonProperty("is_proxy")
	protected Boolean proxy = null;
	@JsonProperty("geolocation")
	protected Geolocation geolocation = null;
	@JsonProperty("distance_from_address")
	protected Integer distanceFromAddress = null;
	@JsonProperty("distance_from_phone")
	protected Integer distanceFromPhone = null;
	@JsonProperty("connection_type")
	protected String connectionType = null;
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
	public Boolean isProxy() {
		return proxy;
	}
	public void setProxy(Boolean proxy) {
		this.proxy = proxy;
	}
	public Geolocation getGeolocation() {
		return geolocation;
	}
	public void setGeolocation(Geolocation geolocation) {
		this.geolocation = geolocation;
	}
	public Integer getDistanceFromAddress() {
		return distanceFromAddress;
	}
	public void setDistanceFromAddress(Integer distanceFromAddress) {
		this.distanceFromAddress = distanceFromAddress;
	}
	public Integer getDistanceFromPhone() {
		return distanceFromPhone;
	}
	public void setDistanceFromPhone(Integer distanceFromPhone) {
		this.distanceFromPhone = distanceFromPhone;
	}
	public String getConnectionType() {
		return connectionType;
	}
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}
}
