package com.pony.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.PonyException;
import com.pony.lead.UserProfile;
import com.tapquality.email.mandrill.MandrillEmail;

public class MandrillEmailModel extends Model{
	private static final Log LOG = LogFactory.getLog(MandrillEmailModel.class);

	protected MandrillEmailModel(Long id) {
		super(id);
	}
	
	public static void insertEmail(MandrillEmail email, UserProfile user) throws PonyException {
		insertEmail(email, user, false);
	}
	
	public static void insertEmail(MandrillEmail email, UserProfile user, boolean upsert) throws PonyException {
		String operation = upsert ? "REPLACE" : "INSERT";
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = connectX();
			if(user != null) {
				stmt = conn.prepareStatement(String.format("%s INTO mandrill_emails (user_profile_id, mandrill_id, state, subject, opens, clicks, sender, template, email, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), now())", operation));
				stmt.setLong(1, user.getId());
				stmt.setString(2, email.getMandrillId());
				stmt.setString(3, email.getState());
				stmt.setString(4, email.getSubject());
				stmt.setInt(5, email.getOpens());
				stmt.setInt(6, email.getClicks());
				stmt.setString(7, email.getSender());
				stmt.setString(8, email.getTemplate());
				stmt.setString(9, email.getEmail());
			} else {
				stmt = conn.prepareStatement(String.format("%s INTO mandrill_emails (mandrill_id, state, subject, opens, clicks, sender, template, email, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, now(), now())", operation));
				stmt.setString(1, email.getMandrillId());
				stmt.setString(2, email.getState());
				stmt.setString(3, email.getSubject());
				stmt.setInt(4, email.getOpens());
				stmt.setInt(5, email.getClicks());
				stmt.setString(6, email.getSender());
				stmt.setString(7, email.getTemplate());
				stmt.setString(8, email.getEmail());
			}
			
			stmt.execute();
		} catch (NamingException e) {
    		LOG.error(CONNECTION_EX_MSG, e);
    		throw new PonyException(CONNECTION_EX_MSG, e);
    	} catch (SQLException e) {
    		LOG.error(NAMING_EX_MSG, e);
    		throw new PonyException(NAMING_EX_MSG, e);
		} finally {
    		close(stmt);
    		close(conn);
    	}
	}
	
	
	public static List<MandrillEmail> getEmails() throws PonyException{
		Connection conn = null;
		PreparedStatement stmt = null;
		List<MandrillEmail> emails = new ArrayList<>();
		try {
			conn = connectX();
			stmt = conn.prepareStatement("SELECT * FROM mandrill_emails JOIN user_profiles on user_profile_id = user_profiles.id;");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				MandrillEmail email = new MandrillEmail();
				email.setId(rs.getLong("id"));
				email.setMandrillId(rs.getString("mandrill_id"));
				email.setOpens(rs.getInt("opens"));
				email.setClicks(rs.getInt("clicks"));
				email.setSender(rs.getString("sender"));
				email.setState(rs.getString("state"));
				email.setSubject(rs.getString("subject"));
				email.setTemplate(rs.getString("template"));
				email.setEmail(rs.getString("email"));
				emails.add(email);
			}
		} catch (NamingException e) {
    		LOG.error(CONNECTION_EX_MSG, e);
    		throw new PonyException(CONNECTION_EX_MSG, e);
    	} catch (SQLException e) {
    		LOG.error(NAMING_EX_MSG, e);
    		throw new PonyException(NAMING_EX_MSG, e);
		} finally {
    		close(stmt);
    		close(conn);
    	}

		return emails;
	}

	public static void updateEmail(MandrillEmail updatedEmail) throws PonyException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = connectX();
			stmt = conn.prepareStatement("UPDATE mandrill_emails SET state = ?, subject = ?, opens = ?, clicks = ?, sender = ?, template = ?, email = ?, updated_at = now() where id = ?");
			stmt.setString(1, updatedEmail.getState());
			stmt.setString(2, updatedEmail.getSubject());
			stmt.setInt(3, updatedEmail.getOpens());
			stmt.setInt(4, updatedEmail.getClicks());
			stmt.setString(5, updatedEmail.getSender());
			stmt.setString(6, updatedEmail.getTemplate());
			stmt.setLong(7, updatedEmail.getId());
			stmt.setString(8, updatedEmail.getEmail());
			stmt.execute();
		} catch (NamingException e) {
    		LOG.error(CONNECTION_EX_MSG, e);
    		throw new PonyException(CONNECTION_EX_MSG, e);
    	} catch (SQLException e) {
    		LOG.error(NAMING_EX_MSG, e);
    		throw new PonyException(NAMING_EX_MSG, e);
		} finally {
    		close(stmt);
    		close(conn);
    	}
	}
	
	public static void deleteEmail(MandrillEmail email) throws PonyException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = connectX();
			stmt = conn.prepareStatement("DELETE FROM mandrill_emails where mandrill_id = ?");
			stmt.setString(1, email.getMandrillId());
			stmt.execute();
		} catch (NamingException e) {
    		LOG.error(CONNECTION_EX_MSG, e);
    		throw new PonyException(CONNECTION_EX_MSG, e);
    	} catch (SQLException e) {
    		LOG.error(NAMING_EX_MSG, e);
    		throw new PonyException(NAMING_EX_MSG, e);
		} finally {
			close(stmt);
			close(conn);
		}
	}
}
