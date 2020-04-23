package com.pony.leadtypes;

import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ArbVentures 2014.
 * User: martin
 * Date: 9/18/14
 * Time: 4:28 PM
 */
public class RealEstateLead extends Lead {
    protected RealEstateLead(Long id, Long leadTypeId, UserProfile userProfile, Long arrivalId, Map<String, String> attributes) {
        super(id, leadTypeId, userProfile == null ? null : userProfile.getId(), arrivalId, attributes);
    }

    public static Lead create(Long id, Long leadTypeId, UserProfile userProfile, Arrival arrival, Map<String, String> attributes) {
        if (arrival == null) {
            return null;
        }
        return new RealEstateLead(id, leadTypeId, userProfile, arrival.getId(), attributes);
    }

    @Override
    public Map<String, String> toMap() {
        // sort the attributes?
        Map<String, String> sortedAttribs = new LinkedHashMap<String, String>();

        Map<String, String> attribs = super.toMap();

        // insert in specific order
        checkAndInsert(sortedAttribs, attribs, "first_name");
        checkAndInsert(sortedAttribs, attribs, "last_name");
        checkAndInsert(sortedAttribs, attribs, "phone_number");
        checkAndInsert(sortedAttribs, attribs, "zip_code");
        checkAndInsert(sortedAttribs, attribs, "created_at");

        checkAndInsert(sortedAttribs, attribs, "time_range");
        checkAndInsert(sortedAttribs, attribs, "home_budget");

        checkAndInsert(sortedAttribs, attribs, "target_zip_code_1");
        checkAndInsert(sortedAttribs, attribs, "target_distance_1");

        checkAndInsert(sortedAttribs, attribs, "target_zip_code_2");
        checkAndInsert(sortedAttribs, attribs, "target_distance_2");

        checkAndInsert(sortedAttribs, attribs, "target_zip_code_3");
        checkAndInsert(sortedAttribs, attribs, "target_distance_3");

        checkAndInsert(sortedAttribs, attribs, "xl_bag");

        return sortedAttribs;
    }

    private void checkAndInsert(Map<String, String> sortedAttribs, Map<String, String> attribs, String name) {
        if (attribs.containsKey(name)) {
            sortedAttribs.put(name, attribs.get(name));
        }
    }


    @Override
    public String toString() {
        return "RealEstateLead:id=" + getId() + ":userProfileId=" + getUserProfileId() + ":arrivalId=" + getArrivalId();
    }
}
