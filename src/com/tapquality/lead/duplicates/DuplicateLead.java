package com.tapquality.lead.duplicates;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.pony.PonyException;
import com.pony.lead.Lead;

public class DuplicateLead extends Lead implements Duplicate {
	protected Lead internalLead;
	
	public DuplicateLead() throws PonyException {
		
	}
	
	public DuplicateLead(Lead lead) throws PonyException {
		this.internalLead = lead;
	}

	public boolean equals(Object o) {
		return internalLead.equals(o);
	}

	public int hashCode() {
		return internalLead.hashCode();
	}

	public Map<String, String> toMap() {
		return internalLead.toMap();
	}

	public Long getId() {
		return internalLead.getId();
	}

	public Long getLeadTypeId() {
		return internalLead.getLeadTypeId();
	}

	public Long getUserProfileId() {
		return internalLead.getUserProfileId();
	}

	public void setUserProfileId(Long userProfileId) {
		internalLead.setUserProfileId(userProfileId);
	}

	public Long getArrivalId() {
		return internalLead.getArrivalId();
	}

	public void setId(Long id) {
		internalLead.setId(id);
	}

	public String getAttributeValue(String attributeName) {
		return internalLead.getAttributeValue(attributeName);
	}

	public String getAttributeValue(String attributeName, String defaultValue) {
		return internalLead.getAttributeValue(attributeName, defaultValue);
	}

	public void mergeAttributes(HttpServletRequest request) {
		internalLead.mergeAttributes(request);
	}

	public void mergeAttributes(Map<String, String> newAttributes) {
		internalLead.mergeAttributes(newAttributes);
	}

	public Set<String> getAttributeNames() {
		return internalLead.getAttributeNames();
	}

	public String toString() {
		return internalLead.toString();
	}
}
