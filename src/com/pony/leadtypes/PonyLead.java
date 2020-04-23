package com.pony.leadtypes;

import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;

import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 6/18/13
 * Time: 10:32 AM
 */
public class PonyLead extends Lead {
    /**
     * I am making this public as it is driving me crazy using static factory methods everywhere. [jec:2017-01-12]
     * @param id
     * @param leadTypeId
     * @param userProfileId
     * @param arrivalId
     * @param attributes
     */
	public PonyLead(Long id, Long leadTypeId, Long userProfileId, Long arrivalId, Map<String, String> attributes) {
        super(id, leadTypeId, userProfileId, arrivalId, attributes);
    }

    public static Lead create(Long id, UserProfile userProfile, Arrival arrival, Map<String, String> attributes) {
        if (arrival == null) {
            return null;
        }
        Long leadTypeId = LeadType.PONY_LEAD_TYPE;
        return new PonyLead(id, leadTypeId, userProfile == null ? null : userProfile.getId(), arrival.getId(), attributes);
    }
    
    public static Lead create(Long id, Long userProfileId, Arrival arrival, Map<String, String> attributes) {
    	if (arrival == null) {
    		return null;
    	}
    	Long leadTypeId = LeadType.PONY_LEAD_TYPE;
    	return new PonyLead(id, leadTypeId, userProfileId, arrival.getId(), attributes);
    }
    
    public static Lead create(Long id, Long userProfileId, Long arrivalId, Map<String, String> attributes) {
    	Long leadTypeId = LeadType.PONY_LEAD_TYPE;
    	return new PonyLead(id, leadTypeId, userProfileId, arrivalId, attributes);
    }

    public String toString() {
        return "PonyLead: id=" + getId() + "; up=" + getUserProfileId() + "; a=" + getArrivalId();
    }
}
