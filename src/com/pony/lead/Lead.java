package com.pony.lead;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.PonyException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

/**
 * Copyright 2011 PonyMash LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 6:06 PM
 */
public abstract class Lead {
    private Long id;
    private final Long leadTypeId;
    private Long userProfileId;
    private final Long arrivalId;
    private final Map<String, String> attributes = new HashMap<String, String>();
    private static Pattern phonePattern = Pattern.compile("\\(?(\\d\\d\\d)\\)?[- ]?(\\d\\d\\d)[- ]?(\\d\\d\\d\\d)");
    private static final Log LOG = LogFactory.getLog(Lead.class);

    public Lead() throws PonyException {
    	leadTypeId = 0L;
    	userProfileId = 0L;
    	arrivalId = 0L;
    }
    
    /**
     * I am making this public as it is driving me crazy using static factory methods all the time. [jec:2017-01-12]
     * @param id
     * @param leadTypeId
     * @param userProfileId
     * @param arrivalId
     * @param attributes
     */
    public Lead(Long id, Long leadTypeId, Long userProfileId, Long arrivalId, Map<String, String> attributes) {
        this.id = id;
        this.leadTypeId = leadTypeId;
        this.userProfileId = userProfileId;
        this.arrivalId = arrivalId;

        // filter attributes we don't want in the map
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            if (!"email".equals(entry.getKey())) {
                this.attributes.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Lead lead = (Lead) o;
        return !(id != null ? !id.equals(lead.id) : lead.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Map<String, String> toMap() {
        return Collections.unmodifiableMap(attributes);
    }

    public Long getId() {
        return id;
    }

    public Long getLeadTypeId() {
        return leadTypeId;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public Long getArrivalId() {
        return arrivalId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttributeValue(String attributeName) {
        return attributes.get(attributeName);
    }

    public String getAttributeValue(String attributeName, String defaultValue) {
        String v = getAttributeValue(attributeName);
        if (v == null || "".equals(v)) {
            v = defaultValue;
        }
        return v;
    }

    public void mergeAttributes(HttpServletRequest request) {
        Map<String, String> newAttributes = LeadType.parseLeadAttributes(request, null);
        mergeAttributes(newAttributes);
    }

    public void mergeAttributes(Map<String, String> newAttributes) {
        for (Map.Entry<String, String> entry : newAttributes.entrySet()) {
            if (!"".equals(entry.getValue()) && !"email".equals(entry.getKey())) {
            	try {
            		// The next few lines relates to a could of issues that are impeding each other. We have, in the
            		//   past, received electric bill values that had the dollar sign encoded as %24. This was happening
            		//   sporadically and the source was never found, so we were decoding it here. However, this
            		//   introduced a further bug because one of the legal values was "$801+" and the "+" was decoded to
            		//   " ", further breaking downstream processing expecting the "+". The replacement of "+" with
            		//   "%2B" is to encode the plus sign, so that it is decoded correctly. When you rationalize this,
            		//   dear reader, and I hope you do, please check the logs for the output message to see if the bad
            		//   values from Leadpath are still coming in before just throwing away the decoding.
            		String initialValue = entry.getValue().replace("+", "%2B");
            		String value = URLDecoder.decode(initialValue, "UTF-8");
            		if (!entry.getValue().equals(value)) {
            			LOG.warn("URLDecoding changed the value of " + entry.getValue() + " to " + value + ". Further investigation of this discrepancy as needed.");
            		}
            		if ("electric_bill".equals(entry.getKey())) value = "$" + value.replaceAll("\\$", "");
            		if ("phone_home".equals(entry.getKey())) {
            			Matcher m = phonePattern.matcher(entry.getValue());
            			if(m.matches()) {
            				value = m.group(1) + m.group(2) + m.group(3);
            			} else {
            				continue;
            			}
            		} 
            		this.attributes.put(entry.getKey(), value);
            	} catch (UnsupportedEncodingException e) {
            		LOG.warn("Encoding exception parsing attribute " + entry.getKey(), e);
            	}
            }
        }
        
        // TODO: This is possibly a temporary measure to compensate for the fact that we seem to be forgetting the value
        //   of "property_ownership" on the form. We would prefer not to have this.
        if (this.attributes.get("property_ownership") == null) {
        	this.attributes.put("property_ownership", "OWN");
        }
        if (this.attributes.get("electric_bill") == null) {
        	this.attributes.put("property_ownership", "$151-200");
        }
        if (this.attributes.get("electric_company") == null) {
        	this.attributes.put("electric_company", "Other");
        }
    }
    
    public Set<String> getAttributeNames() {
    	return this.attributes.keySet();
    }
}
