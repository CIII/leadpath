package com.pony.leadtypes;

import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.models.AttributeModel;
import com.pony.models.LeadModel;
import com.pony.models.UserProfileModelImpl;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationException;
import com.pony.validation.ValidationResponse;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 7/6/13
 * Time: 9:51 PM
 */
public class NewCar extends LeadType {
	private static final Log LOG = LogFactory.getLog(LeadType.class);
	
    public static final String MAKE = "make";
    public static final String MODEL = "model";
    public static final String TRIM = "trim";
    public static final String ZIP = "zipcode";

    static {
        EXCLUDED_ATTRIBUTES.add("username");
        EXCLUDED_ATTRIBUTES.add("password");
        EXCLUDED_ATTRIBUTES.add("email");
        EXCLUDED_ATTRIBUTES.add("listid");
        EXCLUDED_ATTRIBUTES.add("ref");
        EXCLUDED_ATTRIBUTES.add("domtok");
        EXCLUDED_ATTRIBUTES.add("lead_ref");
        EXCLUDED_ATTRIBUTES.add("arrivalid");
        EXCLUDED_ATTRIBUTES.add("asid");
    }

    private NewCar(Long id) {
        super(id);
    }

    public static NewCar create(Long id) {
        return new NewCar(id);
    }

    public String toString() {
        return "LeadType:NewCar(" + getId() + ")";
    }

    @Override
    public ValidationResponse validate(IPublisherContext publisherContext) throws ValidationException {
        LOG.debug("ctx=" + publisherContext);

        if (!(publisherContext.getLeadType() instanceof NewCar)) {
            return ValidationResponse.WRONG_TYPE;
        }

        // if this is a poll for a previous ping request, we just need to check the leadid is present
        if (publisherContext.isPoll()) {
            return publisherContext.getLeadId() == null ? ValidationResponse.create(11, "No reference for poll") : ValidationResponse.NOOP;
        }

        Lead lead = publisherContext.getLead();

        if (lead == null) {
            return ValidationResponse.create(12, "No valid lead data provided");
        }

        String make = lead.getAttributeValue(MAKE);
        String model = lead.getAttributeValue(MODEL);
        String zipcode = lead.getAttributeValue(ZIP);

        if (publisherContext.isPing()) {
            // in case of a ping, we need at least zip make model
            // TODO: chec this against our list of make model combinations

            if (make == null || "".equals(make) || model == null || "".equals(model) || zipcode == null || "".equals(zipcode)) {
                return ValidationResponse.create(13, "invalid make / model / zip");
            }
        }
        else if (publisherContext.isPost()) {
            // is this in reference to a ping:
            if (publisherContext.getLeadId() != null) {
                // there need to be buyer references!
                if (publisherContext.getBuyerIds().size() == 0) {
                    return ValidationResponse.create(14, "post in reference to a ping, but no dealers selected!");
                }
            }
        }

        return ValidationResponse.NOOP;
    }

    @Override
    public Lead parseLead(UserProfile userProfile, Arrival arrival, HttpServletRequest request, Map<String, String> params) {
        Map<String, String> attributes = parseLeadAttributes(request, params);
        return NewCarLead.create(null, getId(), userProfile, arrival, attributes);
    }

    @Override
    public Lead parseLead(UserProfile userProfile, Arrival arrival, HttpServletRequest request) {
        return parseLead(userProfile, arrival, request, null);
    }

    @Override
    public Lead persistLead(UserProfile userProfile, Arrival arrival, Lead lead) throws SQLException, NamingException {

        if (!(lead instanceof NewCarLead)) {
            throw new IllegalArgumentException("not a NewCar lead");
        }

        Long id = LeadModel.persist(lead);

        Lead newCarLead = NewCarLead.create(id, getId(), userProfile, arrival, lead.toMap());

        if (userProfile != null && userProfile.getId() != null) {
            AttributeModel.persistProfileAttributes(userProfile, lead);
        }
        else {
            // this must be a ping and we don't have an email yet, so store the ping values separately.
            AttributeModel.persistPingAttributes(lead);
        }

        return newCarLead;
    }

    @Override
    public Lead findLead(Long leadId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select id, user_profile_id, arrival_id, lead_type_id from leads where id =?");
            stmt.setLong(1, leadId);

            Long userProfileId = null, arrivalId = null, leadTypeId;
            UserProfile up = null;
            Map<String, String> attributes = new HashMap<String, String>();

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userProfileId = rs.getLong("user_profile_id");
                arrivalId = rs.getLong("arrival_id");
                leadTypeId = rs.getLong("lead_type_id");

                if (!this.getId().equals(leadTypeId)) {
                    throw new IllegalArgumentException("leadId=" + leadId + " is not of this lead type! (" + getId() + ")");
                }
            }

            // if there is a user profile, read the profile attributes and merge them into the attribute map
            if (userProfileId != null) {
                up = UserProfileModelImpl.findStatic(userProfileId);
                if (up != null) {
                    attributes.putAll(UserProfileModelImpl.readProfileAttributesStatic(userProfileId));
                }
            }
            if (up == null) {
                // see if there are ping Attributes
                attributes.putAll(AttributeModel.readPingAttributes(leadId));
            }

            return new NewCarLead(leadId, getId(), up, arrivalId, attributes);
        }
        finally {
            close(stmt);
            close(con);
        }
        //

    }
}
