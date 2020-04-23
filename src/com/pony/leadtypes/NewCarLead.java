package com.pony.leadtypes;

import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;

import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 7/6/13
 * Time: 10:09 PM
 */
public class NewCarLead extends Lead {
    protected NewCarLead(Long id, Long leadTypeId, UserProfile userProfile, Long arrivalId, Map<String, String> attributes) {
        super(id, leadTypeId, userProfile == null ? null : userProfile.getId(), arrivalId, attributes);
    }

    public static Lead create(Long id, Long leadTypeId, UserProfile userProfile, Arrival arrival, Map<String, String> attributes) {
        if (arrival == null) {
            return null;
        }
        return new NewCarLead(id, leadTypeId, userProfile, arrival.getId(), attributes);
    }


    @Override
    public String toString() {
        return "NewCarLead:id=" + getId() + ":userProfileId=" + getUserProfileId() + ":arrivalId=" + getArrivalId();
    }
}
