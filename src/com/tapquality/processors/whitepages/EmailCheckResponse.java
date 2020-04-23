package com.tapquality.processors.whitepages;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailCheckResponse {
	@JsonProperty("error")
	protected WhitepagesErrorMessage error = null;
	@JsonProperty("warnings")
	protected List<String> warnings = null;
	@JsonProperty("is_valid")
	protected Boolean valid = null;
	@JsonProperty("diagnostics")
	protected List<String> diagnostics = null;
	@JsonProperty("email_contact_score")
	protected Integer emailContactScore = null;
	@JsonProperty("is_disposable")
	protected Boolean disposable = null;
	@JsonProperty("email_to_name")
	protected String emailToName = null;
	@JsonProperty("registered_name")
	protected String registeredName = null;
	
	public EmailCheckResponse() {}

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

	public Integer getEmailContactScore() {
		return emailContactScore;
	}

	public void setEmailContactScore(Integer emailContactScore) {
		this.emailContactScore = emailContactScore;
	}

	public Boolean isDisposable() {
		return disposable;
	}

	public void setDisposable(Boolean disposable) {
		this.disposable = disposable;
	}

	public String getEmailToName() {
		return emailToName;
	}

	public void setEmailToName(String emailToName) {
		this.emailToName = emailToName;
	}

	public String getRegisteredName() {
		return registeredName;
	}

	public void setRegisteredName(String registeredName) {
		this.registeredName = registeredName;
	}
}
