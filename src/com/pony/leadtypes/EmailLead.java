package com.pony.leadtypes;

import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;

import java.util.Map;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 6/6/12
 * Time: 10:19 PM
 */
public class EmailLead extends Lead {

    private EmailLead(Long id, Long leadTypeId, Long userProfileId, Long arrivalId, Map<String, String> attributes) {
        super(id, leadTypeId, userProfileId, arrivalId, attributes);
    }

    public static EmailLead create(Long leadTypeId, UserProfile userProfile, Arrival arrival, Map<String, String> attributes) {
        return create(null, leadTypeId, userProfile.getId(), arrival.getId(), attributes);
    }

    public static EmailLead create(Long id, Long leadTypeId, Long userProfileId, Long arrivalId, Map<String, String> attributes) {
        return new EmailLead(id, leadTypeId, userProfileId, arrivalId, attributes);
    }

    public String toString() {
        StringBuilder m = new StringBuilder().append("EmailLead:{id:").append(getId());
        for (Map.Entry<String, String> entry : toMap().entrySet()) {
            m.append(",").append(entry.getKey()).append(":").append(entry.getValue());
        }
        m.append("}");

        return m.toString();
    }
}
