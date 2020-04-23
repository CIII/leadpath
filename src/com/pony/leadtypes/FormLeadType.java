package com.pony.leadtypes;

import com.pony.email.validation.AddressValidator;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.models.AttributeModel;
import com.pony.models.LeadModel;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationException;
import com.pony.validation.ValidationResponse;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;

/**
 * A FormLeadType is a generic lead type class to use for all lead types that are not specifically handled in their own class.
 * <p/>
 * PonyLeads 2013.
 * User: martin
 * Date: 2/8/13
 * Time: 5:18 PM
 */
public class FormLeadType extends LeadType {

    static {
        EXCLUDED_ATTRIBUTES.add("utf8");
        EXCLUDED_ATTRIBUTES.add("authenticity_token");
        EXCLUDED_ATTRIBUTES.add("form_impression_id");
    }

    protected final String name;

    public FormLeadType(Long id) {
        super(id);
        name = null;
    }

    private FormLeadType(Long id, String name) {
        super(id);
        this.name = name;
    }

    public static FormLeadType create(Long id, String name) {
        return new FormLeadType(id, name);
    }

    public String toString() {
        return "LeadType:Form(" + getId() + ":" + name + ")";
    }

    @Override
    public Lead parseLead(UserProfile userProfile, Arrival arrival, HttpServletRequest request, Map<String, String> params) {
        Map<String, String> attributes = parseLeadAttributes(request, params);
        return FormLead.create(null, getId(), userProfile, arrival, attributes);
    }

    @Override
    public Lead parseLead(UserProfile userProfile, Arrival arrival, HttpServletRequest request) {
        return parseLead(userProfile, arrival, request, null);
    }

    @Override
    public Lead persistLead(UserProfile userProfile, Arrival arrival, Lead lead) throws SQLException, NamingException {
        if (!(lead instanceof FormLead)) {
            throw new IllegalArgumentException("not a form lead");
        }
        if (userProfile.getId() == null) {
            throw new IllegalArgumentException("requires user_profile to be stored first!");
        }

        Long id = LeadModel.persist(lead);

        Lead formLead = FormLead.create(id, getId(), userProfile, arrival, lead.toMap());

        AttributeModel.persistProfileAttributes(userProfile, lead);

        return formLead;
    }

    @Override
    public Lead findLead(Long leadId) {
        //TODO
        return null;
    }

    @Override
    public ValidationResponse validate(IPublisherContext publisherContext) throws ValidationException {
        if (!AddressValidator.isValid(publisherContext.getUserProfile().getEmail(), false)) {
            return ValidationResponse.INVALID_EMAIL;
        }
        return ValidationResponse.NOOP;
    }
}
