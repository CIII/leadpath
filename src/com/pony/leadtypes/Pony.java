package com.pony.leadtypes;

import com.pony.PonyException;
import com.pony.email.validation.AddressValidator;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.models.ArrivalModelImpl;
import com.pony.models.LeadModel;
import com.pony.models.UserProfileModelImpl;
import com.pony.publisher.IPublisherContext;
import com.pony.validation.SimpleSolarValidator;
import com.pony.validation.ValidationException;
import com.pony.validation.ValidationResponse;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 6/18/13
 * Time: 10:38 AM
 */
public class Pony extends LeadType {
	private static final int MINIMUM_PHONE_CONTACT_SCORE = 3;
	private static final Log LOG = LogFactory.getLog(Pony.class);

    static {
        EXCLUDED_ATTRIBUTES.add("username");
        EXCLUDED_ATTRIBUTES.add("password");
        EXCLUDED_ATTRIBUTES.add("email");
        EXCLUDED_ATTRIBUTES.add("listid");
        EXCLUDED_ATTRIBUTES.add("ref");
        EXCLUDED_ATTRIBUTES.add("domtok");
        EXCLUDED_ATTRIBUTES.add("WAIT_FOR_RESPONSE");
    }

    public Pony() {
        super(-1L);
    }

    @Override
    public Lead findLead(Long leadId) throws PonyException, NamingException, SQLException {

        if (leadId == null) {
            return null;
        }

        Lead lead = LeadModel.find(leadId);

        if(lead != null) {
        	Long userProfileId = lead.getUserProfileId();

        	if (userProfileId == null) {
        		Arrival arrival = ArrivalModelImpl.findByLeadIdStatic(leadId);
        		if (arrival != null) {
        			userProfileId = arrival.getUserProfileId();
        		}
        	}

        	// Assert: User Profile ID != null unless I really don't know anything.
        
        	// read any attributes for the user profile
        	if (userProfileId != null) {
        		lead.mergeAttributes(UserProfileModelImpl.readProfileAttributesStatic(userProfileId));
        		// TODO: Should update the attributes here after merging.
        	}

        	// This doesn't really make sense? If it's there, it will be populated; if it's new it won't have gotten the new value. [jec:2017-06-23]
        	if (lead != null) {
        		lead.setId(leadId);
        	}
        }

        return lead;
    }

    public String toString() {
        return "LeadType:Pony(" + getId() + ")";
    }

    @Override
    public ValidationResponse validate(IPublisherContext context) throws ValidationException {
    	ValidationResponse response = ValidationResponse.NOOP;
        UserProfile userProfile = context.getUserProfile();

        // posts do require at least an email address
        if ((context.isPost() || context.isPing()) && (userProfile == null || userProfile.getEmail() == null)) {
            context.addErrorCode(ValidationResponse.MISSING_EMAIL.getName(), "Email is missing.");
            response = ValidationResponse.MISSING_EMAIL;
        }

        // if we get an address, make sure it passes at least a sniff test
        if (userProfile != null && userProfile.getEmail() != null && !AddressValidator.isValid(userProfile.getEmail(), false)) {
            context.addErrorCode(ValidationResponse.INVALID_EMAIL.getName(), "Email is invalid for some reason.");
            response = ValidationResponse.INVALID_EMAIL;
        }

        ValidationResponse mediaAlphaValidation = SimpleSolarValidator.validate(context);
        
        if(mediaAlphaValidation != ValidationResponse.NOOP) {
        	response = mediaAlphaValidation;
        }
        
        String phoneContactScore = context.getLead().getAttributeValue("phone_checks.phone_contact_score");
        try {
        	if(phoneContactScore != null && Integer.parseInt(phoneContactScore) > MINIMUM_PHONE_CONTACT_SCORE) {
        		context.addErrorCode(ValidationResponse.INVALID_PHONE_CONTACT_SCORE.getName(), "Phone contact score is too high.");
        		response = ValidationResponse.INVALID_PHONE_CONTACT_SCORE;
        	}
        } catch (NumberFormatException e) {
        	LOG.info("phone_contact_score was not a number. Not counting as a validation error.");
        }
        
        return response;
    }
}
