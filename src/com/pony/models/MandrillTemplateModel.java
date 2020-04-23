package com.pony.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.PonyException;
import com.tapquality.email.MandrillTemplate;

public class MandrillTemplateModel extends Model{
	private static final Log LOG = LogFactory.getLog(MandrillTemplateModel.class);
	
	protected MandrillTemplateModel(Long id) {
		super(id);
	}
	
	public static MandrillTemplate getTemplateForAdvertiser(Long advertiserId, String domain) throws PonyException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = connectX();
			stmt = conn.prepareStatement("SELECT * FROM mandrill_templates where advertiser_id = ? and domain = ?");
			stmt.setLong(1, advertiserId);
			stmt.setString(2, domain);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				MandrillTemplate template = new MandrillTemplate();
				template.setName(rs.getString("template_name"));
				template.setId(rs.getLong("id"));
				template.setBuyerId(rs.getLong("buyer_id"));
				template.setDelayMinutes(rs.getInt("delay_minutes"));
				template.setDomain(rs.getString("domain"));
				template.setOrder(rs.getInt("order"));
				return template;
			} else {
				return null;
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
	}
	
	public static MandrillTemplate getTemplateForBuyer(Long buyerId, String domain) throws PonyException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = connectX();
			stmt = conn.prepareStatement("SELECT * FROM mandrill_templates where buyer_id = ? and domain = ?");
			stmt.setLong(1, buyerId);
			stmt.setString(2, domain);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				MandrillTemplate template = new MandrillTemplate();
				template.setName(rs.getString("template_name"));
				template.setId(rs.getLong("id"));
				template.setBuyerId(rs.getLong("buyer_id"));
				template.setDelayMinutes(rs.getInt("delay_minutes"));
				template.setDomain(rs.getString("domain"));
				template.setOrder(rs.getInt("order"));
				return template;
			} else {
				return null;
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
	}
}
