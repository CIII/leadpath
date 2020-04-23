package com.pony.models;

import com.pony.PonyException;
import com.pony.advertiser.Disposition;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.leadtypes.PonyLead;
import static com.tapquality.db.tables.Leads.*;
import static com.tapquality.db.tables.UserProfiles.*;
import static com.tapquality.db.tables.ProfileAttributes.*;
import static com.tapquality.db.tables.Attributes.*;

import com.tapquality.db.tables.Leads;
import com.tapquality.lead.duplicates.DistantDuplicateLead;
import com.tapquality.lead.duplicates.RecentDuplicate;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/10/11
 * Time: 10:14 PM
 */
public class LeadModel extends Model {
	private static final Log LOG = LogFactory.getLog(LeadModel.class);
	
    public static final Status REJECTED = new Status(1);
    public static final Status ROUTED = new Status(2);

    protected LeadModel(Long id) {
        super(id);
    }
    
    public static Lead find(Long leadId) throws PonyException {
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	Lead returnValue = null;
    	try {
    		conn = connectX();
    		stmt = conn.prepareStatement("SELECT id, user_profile_id, arrival_id, lead_type_id FROM leads WHERE id = ?");
    		stmt.setLong(1, leadId);
    		ResultSet rs = stmt.executeQuery();
    		if(rs.next()) {
    			returnValue = PonyLead.create(new Long(rs.getLong("id")), new Long(rs.getLong("user_profile_id")), new Long(rs.getLong("arrival_id")), new HashMap<String, String>());
    		}
    	} catch (NamingException e) {
    		String errMsg = "Exception establishing connection to the database. This is likely a configuration error, and will inhibit the operation of the entire service.";
    		LOG.error(errMsg, e);
    		throw new PonyException(errMsg, e);
    	} catch (SQLException e) {
    		String errMsg = "Exception connecting to database. Likely a connection error. It is likely this represents an infrastructure problem.";
    		LOG.error(errMsg, e);
    		throw new PonyException(errMsg, e);
		} finally {
    		close(stmt);
    		close(conn);
    	}
    	
    	return returnValue;
    }

    public static Long findUnrouted(Long leadTypeId, Long userProfileId, Long arrivalId) throws SQLException, NamingException {
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	try {
    		conn = connectX();
    		stmt = conn.prepareStatement("SELECT id FROM leads WHERE lead_type_id = ? AND user_profile_id = ? AND arrival_id = ? AND status = 0");
    		stmt.setLong(1, leadTypeId);
    		stmt.setLong(2,  userProfileId);
    		stmt.setLong(3, arrivalId);
    		ResultSet rs = stmt.executeQuery();
    		if (rs.next()) {
    			return rs.getLong("id");
    		}
    	} finally {
    		close(stmt);
    		close(conn);
    	}
    	
    	return null;
    }
    
    public static Long find(Long leadTypeId, Long userProfileId, Long arrivalId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select id from leads where lead_type_id = ? and user_profile_id= ? and arrival_id = ?");
            stmt.setLong(1, leadTypeId);
            stmt.setLong(2, userProfileId);
            stmt.setLong(3, arrivalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }

        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    /**
     * Find a lead with the provided arrival id and !!no user_profile!!
     *
     * @param leadTypeId
     * @param arrivalId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public static Long find(Long leadTypeId, Long arrivalId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select id from leads where lead_type_id = ? and arrival_id = ? and user_profile_id is null");
            stmt.setLong(1, leadTypeId);
            stmt.setLong(2, arrivalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static Long persist(Lead lead) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("insert ignore into leads (user_profile_id, arrival_id, lead_type_id, created_at) values(?,?,?,now()) ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id), updated_at=now()");

            if (lead.getUserProfileId() != null) {
                stmt.setLong(1, lead.getUserProfileId());
            }
            else {
                stmt.setString(1, null);
            }

            stmt.setLong(2, lead.getArrivalId());
            stmt.setLong(3, lead.getLeadTypeId());

            Long id = executeWithLastId(stmt);
            lead.setId(id);

            return id;
        }
        catch (NamingException e) {
            LOG.error(e);
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    /**
     * update the lead status based on the dispositon we get from the advertiser (did they accept or reject the lead)
     *
     * @param id
     * @param disposition
     * @param phase
     */
    public static void updateStatus(Long id, Disposition disposition, PonyPhase phase) throws NamingException, SQLException {

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("update leads set status = ?, updated_at=now() where id = ?");
            // TODO: map the status based on the phase we're in
            stmt.setInt(1, disposition.getStatus() == Disposition.Status.ACCEPTED ? ROUTED.getStatus() : REJECTED.getStatus());
            stmt.setLong(2, id);
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    /**
     * set the user_profile_id for a lead that so far has no user_profile_id.
     *
     * @param userProfile
     * @param lead
     * @throws NamingException
     * @throws SQLException
     */
    public static void updateUserProfile(UserProfile userProfile, Lead lead) throws NamingException, SQLException {
        //
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("update leads set user_profile_id = ?, updated_at=now() where id = ? and user_profile_id is null");
            stmt.setLong(1, userProfile.getId());
            stmt.setLong(2, lead.getId());

            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }
    
    public static List<Map<String, Object>> getMostRecentLeads(int page, int pageSize) throws PonyException {
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	List<Map<String, Object>> returnValue = new ArrayList<>();
    	
    	try {
    		conn = connectX();
    		DSLContext context = DSL.using(conn, SQLDialect.MYSQL);
    		ResultSet rs = context.select(LEADS.as("leads").ID, LEADS.as("leads").STATUS, USER_PROFILES.EMAIL, ATTRIBUTES.NAME.as("attribute_name"), PROFILE_ATTRIBUTES.VALUE.as("attribute_value"))
    			.from(context.select(LEADS.ID, LEADS.STATUS, LEADS.USER_PROFILE_ID).from(LEADS).orderBy(LEADS.UPDATED_AT.desc()).limit(page * pageSize, pageSize).asTable("leads"))
    			.join(USER_PROFILES).on(LEADS.as("leads").USER_PROFILE_ID.eq(USER_PROFILES.ID))
    			.join(PROFILE_ATTRIBUTES).on(LEADS.as("leads").USER_PROFILE_ID.eq(PROFILE_ATTRIBUTES.USER_PROFILE_ID))
    			.join(ATTRIBUTES).on(PROFILE_ATTRIBUTES.ATTRIBUTE_ID.eq(ATTRIBUTES.ID))
    			.orderBy(LEADS.as("leads").ID.desc(), ATTRIBUTES.NAME.asc())
    			.fetchResultSet();
    		Integer id = null;
    		Map<String, Object> newItem = null;
    		List<Map<String, String>> attributes = null;
    		while(rs.next()) {
    			Integer newId = rs.getInt("id");
    			if(!newId.equals(id)) {
    				newItem = new HashMap<>();
    				attributes = new ArrayList<>();
    				id = newId;
        			newItem.put("id", Integer.toString(id));
        			newItem.put("status", rs.getString("status"));
        			newItem.put("email", rs.getString("email"));
        			returnValue.add(newItem);
        			newItem.put("attributes", attributes);
    			}
    			
    			Map<String, String> attribute = new HashMap<>();
    			attribute.put("name", rs.getString("attribute_name"));
    			attribute.put("value", rs.getString("attribute_value"));
    			attributes.add(attribute);
    		}
    	} catch (SQLException e) {
    		LOG.warn("SQLException fetching most recent leads.", e);
    		throw new PonyException("SQLException fetching most recent leads.", e);
    	} catch (NamingException e) {
    		LOG.warn("NamingException fetching most recent leads.", e);
    		throw new PonyException("NamingException fetching most recent leads.", e);
    	} finally {
    		close(stmt);
    		close(conn);
    	}
    	
    	return returnValue;
    }

    private static class Status {
        private final int status;

        public Status(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Status status1 = (Status) o;

            return status == status1.status;
        }

        @Override
        public int hashCode() {
            return status;
        }
    }
    
    public static Lead checkDuplicateLead(Lead lead) throws PonyException {
    	Connection conn = null;
    	PreparedStatement recentDuplicates = null;
    	PreparedStatement distantDuplicates = null;
    	
    	try {
    		conn = connectX();
    		recentDuplicates = conn.prepareStatement("SELECT COUNT(lead_matches.id) FROM leads LEFT OUTER JOIN lead_matches ON leads.id = lead_matches.lead_id WHERE arrival_id = ? AND user_profile_id = ? AND lead_type_id = ? GROUP BY leads.id HAVING (MAX(lead_matches.updated_at) > DATE_SUB(CURRENT_TIMESTAMP(), interval 2 DAY)) OR (MAX(lead_matches.created_at) > DATE_SUB(CURRENT_TIMESTAMP(), interval 2 DAY));");
    		recentDuplicates.setLong(1, lead.getArrivalId());
    		recentDuplicates.setLong(2, lead.getUserProfileId());
    		recentDuplicates.setLong(3, lead.getLeadTypeId());
    		
    		ResultSet rs = recentDuplicates.executeQuery();
    		if(rs.next() && rs.getInt(1) > 0) { // If lead exists and the most recent lead_match is less than 2 days ago
    	    	return new RecentDuplicate(lead);
    		} else { // else if user_profile has a lead with the same lead type less than 30 days ago

    			distantDuplicates = conn.prepareStatement("SELECT COUNT(lead_matches.id) FROM leads JOIN lead_matches ON leads.id = lead_matches.lead_id WHERE user_profile_id = ? AND lead_type_id = ? GROUP BY leads.id HAVING (MAX(lead_matches.updated_at) > DATE_SUB(CURRENT_TIMESTAMP(), interval 30 DAY)) OR (MAX(lead_matches.created_at) > DATE_SUB(CURRENT_TIMESTAMP(), interval 30 DAY));");
    			distantDuplicates.setLong(1, lead.getUserProfileId());
    			distantDuplicates.setLong(2, lead.getLeadTypeId());
    			
    			rs = distantDuplicates.executeQuery();
    			if(rs.next() && rs.getInt(1) > 0) {
    		    	return new DistantDuplicateLead(lead);
    			} else { // else
    		    	return lead;
    			}
    		}
    	} catch (NamingException e) {
    		String errMsg = "Exception getting connection from the container. This likely a larger problem and should be addressed immediately.";
    		LOG.error(errMsg);
    		throw new PonyException(errMsg, e);
    	} catch (SQLException e) {
    		String errMsg = "SQLException looking for duplicate leads.";
    		LOG.error(errMsg);
    		throw new PonyException(errMsg, e);
    	} finally {
    		close(recentDuplicates);
    		close(distantDuplicates);
    		close(conn);
    	}
    	
    }
}
