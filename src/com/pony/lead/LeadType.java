package com.pony.lead;

import com.pony.PonyException;
import com.pony.leadtypes.*;
import com.pony.models.*;
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
import java.util.*;

/**
 * Copyright 2011 PonyMash LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 6:08 PM
 */
public abstract class LeadType extends Model {
	private static final Log LOG = LogFactory.getLog(LeadType.class);

    public static final long PONY_LEAD_TYPE = -1L;

    protected static List<String> EXCLUDED_ATTRIBUTES = new ArrayList<String>();

    private static final String LEAD_TYPE = "lead_type";
    private static final String DEFAULT_LEAD_TYPE = "Email";

    protected LeadType(Long id) {
        super(id);
    }

    public static Map<String, String> parseLeadAttributes(HttpServletRequest request, Map<String, String> params) {
        // parse the request for lead type attributes and extract their values into a lead (EmailLead !!)
        // the params are in the query
        // examples:
        //source_url=welendcash.com&ipaddy=76.101.120.154&username=popmark&password=thomasb&listid=40&email=johngrieco1@hotmail.com&fname=&lname=grieco&signup_date=2012-07-01 00:00:00
        //username=leadkarma&password=doublenickels&fname=Danielle&state=NV&email=Jadabean08%40yahoo.com&source_url=insurancedesk.com&dob=01-04-1967&listid=12346&zip=89149&vertical=healthinsurance&lname=Joren&ipaddy=76.3.147.161&city=Las+Vegas&traffic_type=Affiliate+Display
        Map<String, String> attributes = new HashMap<String, String>();
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String attribute = (String) e.nextElement();
            LOG.debug("Attribute: " + attribute);
            // Note: attributes that are encoded to a specific form need to be skipped here (they follow this pattern <formId>[<attribute name>])
            if (!EXCLUDED_ATTRIBUTES.contains(attribute) && !attribute.contains("[")) {
                String attributeValue = request.getParameter(attribute);
                if (attributeValue != null) {
                    String val = attributeValue.trim();
                    if (!val.isEmpty()) {
                        attributes.put(attribute, attributeValue);
                    }
                }
            }
        }

        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (!EXCLUDED_ATTRIBUTES.contains(param.getKey())) {
                    attributes.put(param.getKey(), param.getValue());
                }
            }
        }

        return attributes;
    }

    public Lead parseLead(UserProfile userProfile, Arrival arrival, HttpServletRequest request) {
        return parseLead(userProfile, arrival, request, null);
    }

    /**
     * default implementation to create a PonyLead (a generic lead ; lead type id = -1).
     *
     * @param userProfile
     * @param arrival
     * @param request
     * @param params
     * @return
     */
    public Lead parseLead(UserProfile userProfile, Arrival arrival, HttpServletRequest request, Map<String, String> params) {
        // parse the request for lead type attributes and extract their values into a lead
        // the params are in the query
        // examples:
        //source_url=welendcash.com&ipaddy=76.101.120.154&username=popmark&password=thomasb&listid=40&email=johngrieco1@hotmail.com&fname=&lname=grieco&signup_date=2012-07-01 00:00:00
        //username=leadkarma&password=doublenickels&fname=Danielle&state=NV&email=Jadabean08%40yahoo.com&source_url=insurancedesk.com&dob=01-04-1967&listid=12346&zip=89149&vertical=healthinsurance&lname=Joren&ipaddy=76.3.147.161&city=Las+Vegas&traffic_type=Affiliate+Display
        Map<String, String> attributes = parseLeadAttributes(request, params);
        return PonyLead.create(null, userProfile, arrival, attributes);
    }

    public Lead persistLead(UserProfile userProfile, Arrival arrival, Lead lead) throws SQLException, NamingException {

        Long leadId;
        Lead existingLead;

        if (userProfile != null) {
            existingLead = readLead(userProfile, arrival);

            // if there is a lead with no user profile linked yet, then update that lead now
            // (there was a previous ping for the same external arrival_id from the same publisher and for the same publisher channel)
            if (existingLead == null) {
                existingLead = readLead(arrival);
                if (existingLead != null) {
                    LeadModel.updateUserProfile(userProfile, existingLead);
                    existingLead.setUserProfileId(userProfile.getId());
                }
            }
        }
        else {
            // look for an existing lead for this arrival that has no user_profile_id yet
            existingLead = readLead(arrival);
        }

        if (existingLead != null) {
            leadId = existingLead.getId();

            // merge newly posted attributes
            existingLead.mergeAttributes(lead.toMap());
            lead = existingLead;
        }
        else {
            leadId = LeadModel.persist(lead);
        }

        if (userProfile != null && userProfile.getId() != null) {
            AttributeModel.persistProfileAttributes(userProfile, lead);
        }
        else {
            // this must be a ping and we don't have an email yet, so store the ping values separately.
            AttributeModel.persistPingAttributes(lead);
        }

        return lead;
    }

    public void updateLead(UserProfile userProfile, Lead lead) throws NamingException, SQLException {
        // make sure we now have a link to the user profile (if this was a ping before, we had an arrival and a lead, but no user profile yet!!)
        LeadModel.updateUserProfile(userProfile, lead);
        // and now that we have the user profile, we can store the attributes as well.
        AttributeModel.persistProfileAttributes(userProfile, lead);
    }

    /**
     * lookup a lead that has only an arrival, and no user_profile!
     *
     * @param arrival
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public Lead readLead(Arrival arrival) throws NamingException, SQLException {

        assert arrival != null && arrival.getId() != null;

        // read all attributes for this profile and filter the ones not relevant to this lead type
        Long leadId = LeadModel.find(getId(), arrival.getId());
        if (leadId == null) {
            return null;
        }

        UserProfile userProfile = null;

        Map<String, String> attributeValues = AttributeModel.readPingAttributes(leadId);

        return PonyLead.create(leadId, userProfile, arrival, attributeValues);
    }


    public Lead readLead(UserProfile userProfile, Arrival arrival) throws NamingException, SQLException {

        assert userProfile != null && userProfile.getId() != null;
        assert arrival != null && arrival.getId() != null;

        // read all attributes for this profile and filter the ones not relevant to this lead type
        Long leadId = LeadModel.find(getId(), userProfile.getId(), arrival.getId());
        if (leadId == null) {
            return null;
        }

        Map<String, String> attributeValues = UserProfileModelImpl.readProfileAttributesStatic(userProfile.getId());

        // TODO: make generic
//        return EmailLead.create(leadId, getId(), userProfileId, arrivalId, attributeValues);
        return PonyLead.create(leadId, userProfile, arrival, attributeValues);
    }

    public Lead readLead(Long userProfileId, Long arrivalId) throws NamingException, SQLException {

        assert userProfileId != null;
        assert arrivalId != null;

        UserProfile userProfile = UserProfileModelImpl.findStatic(userProfileId);
        Arrival arrival = ArrivalModelImpl.findStatic(arrivalId);

        return readLead(userProfile, arrival);
    }

    public abstract Lead findLead(Long leadId) throws NamingException, SQLException, PonyException;

    public String labelLookup(String attributeName) {
        return attributeName;
    }

    // validate the lead from the channel before we store it
    public abstract ValidationResponse validate(IPublisherContext publisherContext) throws ValidationException;

    public static LeadType find(Connection con, Long id) throws NamingException, SQLException {
        return readInternal(con, id);
    }

    public static LeadType find(Long id) throws NamingException, SQLException {
        Connection con = null;
        try {
            con = connectX();
            return readInternal(con, id);
        }
        finally {
            close(con);
        }
    }

    public static LeadType find(String name) throws NamingException, SQLException {
        Connection con = null;
        try {
            con = connectX();
            return readInternal(con, name);
        }
        finally {
            close(con);
        }
    }

    private static LeadType readInternal(Connection con, String name) throws NamingException, SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, name from lead_types where name = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (name.equals("Email")) {
                    return new Email(rs.getLong("id"));
                }
                if (name.equals("Payday")) {
                    return new Payday(rs.getLong("id"));
                }
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    private static LeadType readInternal(Connection con, Long id) throws NamingException, SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, name from lead_types where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getString("name").equals("Email")) {
                    return new Email(rs.getLong("id"));
                }
                else if (rs.getString("name").equals("Payday")) {
                    return new Payday(rs.getLong("id"));
                }
                else {
                    if (rs.getLong("id") == PONY_LEAD_TYPE) {
                        return new Pony(); // super low level default lead
                    }
                    else if ("New Cars".equalsIgnoreCase(rs.getString("name"))) {
                        return NewCar.create(id);
                    }
                    else if ("RealEstate".equalsIgnoreCase(rs.getString("name"))) {
                        return RealEstate.create(id);
                    }
                    else {
                        return new FormLeadType(rs.getLong("id"));
                    }
                }
                //TODO: add other types..?
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    public static LeadType create(String name) throws NamingException, SQLException {
        //        insert ignore into lead_types values(null, 'ArbVenture Business Registration Form', now(), null);

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("insert ignore into lead_types values(null, ?, now(), null)");
            stmt.setString(1, name);
            Long id = executeWithLastId(stmt);
            if (id != null) {
                return FormLeadType.create(id, name);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }
}
