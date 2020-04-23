package com.pony.leadtypes;

import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;

import java.util.Map;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 2/8/13
 * Time: 5:24 PM
 */
public class FormLead extends Lead {
    protected FormLead(Long id, Long leadTypeId, Long userProfileId, Long arrivalId, Map<String, String> attributes) {
        super(id, leadTypeId, userProfileId, arrivalId, attributes);
    }

    public static Lead create(Long id, Long leadTypeId, UserProfile userProfile, Arrival arrival, Map<String, String> attributes) {
        return new FormLead(id, leadTypeId, userProfile.getId(), arrival.getId(), attributes);
    }
}
