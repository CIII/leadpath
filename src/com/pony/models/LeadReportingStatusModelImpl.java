package com.pony.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.PonyException;

public class LeadReportingStatusModelImpl extends Model implements LeadReportingStatusModel {
	private static final Log LOG = LogFactory.getLog(LeadReportingStatusModelImpl.class);
	
	protected LeadReportingStatusModelImpl() {
		super(0L);
	}

	protected LeadReportingStatusModelImpl(Long id) {
		super(id);
	}

	@Override
	public Timestamp[] getStartingPoint() throws PonyException {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		Timestamp[] returnValue = {null, null};
		
		try {
			conn = Model.connectReport();
			stmt = conn.prepareStatement("SELECT last_run, CURRENT_TIMESTAMP() as `current_time` FROM lead_reporting_status;");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Timestamp lastRun = rs.getTimestamp("last_run");
				if(rs.wasNull()) lastRun = null;
				Timestamp currentTime = rs.getTimestamp("current_time");
				returnValue[0] = lastRun;
				returnValue[1] = currentTime;
			}
		} catch (SQLException e) {
			LOG.error("SQLException retrieving the reporting metadata.", e);
			throw new PonyException("SQLException retrieving the reporting metadata.", e);
		} catch (NamingException e) {
			LOG.error("NamingException retrieving the connection from the environment.", e);
			throw new PonyException("NamingException retrieving the connection from the environment.", e);
		} finally {
			close(stmt);
			close(conn);
		}

		return returnValue;
	}

	@Override
	public void migrateData(Timestamp startTime) throws PonyException {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = Model.connectReport();
			stmt = conn.prepareStatement(
					"INSERT INTO lead_reporting "
					+ "SELECT "
					+ "advertisers.name,"
					+ "lead_matches.lead_id,"
					+ "lead_matches.id,"
					+ "lead_matches.order_id,"
					+ "arrivals.external_id AS lynx_session_id,"
					+ "CASE WHEN lead_matches.status = 4 OR lead_matches.status = -2 THEN 1 ELSE 0 END AS attempt,"
					+ "CASE WHEN lead_matches.status = 4 THEN 1 ELSE 0 END AS success,"
					+ "CASE WHEN lead_matches.status = -1 THEN 1 ELSE 0 END AS rejected,"
					+ "CASE WHEN lead_matches.status = -2 THEN 1 ELSE 0 END AS returned,"
					+ "CASE WHEN lead_matches.status = 4 THEN lead_matches.price ELSE 0 END as price,"
					+ "CASE WHEN lead_matches.status = 4 THEN 1 WHEN lead_matches.status = -2 THEN -1 ELSE 0 END as conf_part,"
					+ "zip_codes.value AS zip_code,"
					+ "states.value AS state,"
					+ "first_names.value AS first_name,"
					+ "last_names.value AS last_name,"
					+ "user_profiles.email,"
					+ "cities.value AS city,"
					+ "electric_bills.value AS electric_bill,"
					+ "utm_sources.value AS utm_source,"
					+ "phone_scores.value AS phone_score,"
					+ "address_scores.value AS address_score,"
					+ "email_scores.value AS email_score,"
					+ "credit_ranges.value AS credit_range,"
					+ "phone_is_valids.value AS phone_is_valid,"
					+ "phone_is_commercials.value AS phone_is_commercial,"
					+ "IFNULL(times_sold.times_sold, 0) AS times_sold,"
					+ "IFNULL(CASE WHEN times_sold.times_sold > 0 THEN 1 ELSE 0 END, 0) AS conu,"
					+ "CASE WHEN advertisers.name = 'BostonSolar' THEN 1 ELSE 0 END AS sold_boston_solar,"
					+ "CASE WHEN advertisers.name = 'NECE' THEN 1 ELSE 0 END AS sold_nece,"
					+ "CASE WHEN advertisers.name = 'MediaAlpha' THEN 1 ELSE 0 END AS sold_ma,"
					+ "lead_matches.updated_at as last_updated, "
					+ "lead_matches.created_at as created_at, "
					+ "keywords.value, "
					+ "orders.is_bind_eligible, "
					+ "IFNULL(contacted_dispositions.contacted, 0) as contacted, "
					+ "IFNULL(appointments_set.appointment_set, 0) as appointment_set, "
					+ "IFNULL(contracts_signed.contract_signed, 0) as contract_signed "
					+ "FROM (SELECT lead_id, order_id, MAX(updated_at) as recently_updated FROM pony_leads.lead_matches GROUP BY lead_id, order_id) as recently_updated "
					+ "JOIN pony_leads.lead_matches ON recently_updated.lead_id = lead_matches.lead_id AND recently_updated.order_id = lead_matches.order_id "
					+ "JOIN pony_leads.orders ON lead_matches.order_id = orders.id "
					+ "JOIN pony_leads.advertisers ON orders.advertiser_id = advertisers.id "
					+ "JOIN pony_leads.leads ON lead_matches.lead_id = leads.id "
					+ "JOIN pony_leads.arrivals ON leads.arrival_id = arrivals.id "
					+ "JOIN (SELECT id FROM pony_leads.`attributes` WHERE name = 'zip') AS zip_attribute "
					+ "JOIN (SELECT id FROM pony_leads.`attributes` WHERE name = 'state') AS state_attribute "
					+ "JOIN (SELECT id FROM pony_leads.`attributes` WHERE name = 'first_name') AS first_name_attribute "
					+ "JOIN (SELECT id FROM pony_leads.`attributes` WHERE name = 'last_name') AS last_name_attribute "
					+ "JOIN (SELECT id FROM pony_leads.`attributes` WHERE name = 'city') AS city_attribute "
					+ "JOIN (SELECT id FROM pony_leads.`attributes` WHERE name = 'electric_bill') AS electric_bill_attribute "
					+ "JOIN (SELECT id FROM pony_leads.`attributes` WHERE name = 'utm_source') AS utm_source_attribute "
					+ "JOIN (SELECT id FROM pony_leads.`attributes` WHERE name = 'phone_checks.phone_contact_score') AS phone_score_attribute "
					+ "JOIN (SELECT id FROM pony_leads.`attributes` WHERE name = 'credit_range') AS credit_range_attribute "
					+ "JOIN (SELECT id FROM pony_leads.`attributes` WHERE name = 'phone_checks.is_valid') AS phone_is_valid_attribute "
					+ "JOIN (SELECT id FROM pony_leads.`attributes` WHERE name = 'phone_checks.is_commercial') AS phone_is_commercial_attribute "
					+ "JOIN (SELECT id FROM pony_leads.`attributes` WHERE name = 'address_checks.address_contact_score') AS address_score_attribute "
					+ "JOIN (SELECT id FROM pony_leads.`attributes` WHERE name = 'email_address_checks.email_contact_score') AS email_score_attribute "
					+ "LEFT OUTER JOIN pony_leads.user_profiles ON leads.user_profile_id = user_profiles.id "
					+ "LEFT OUTER JOIN pony_leads.profile_attributes AS zip_codes ON leads.user_profile_id = zip_codes.user_profile_id AND zip_codes.attribute_id = zip_attribute.id "
					+ "LEFT OUTER JOIN pony_leads.profile_attributes AS states ON leads.user_profile_id = states.user_profile_id AND states.attribute_id = state_attribute.id "
					+ "LEFT OUTER JOIN pony_leads.profile_attributes AS first_names ON leads.user_profile_id = first_names.user_profile_id AND first_names.attribute_id = first_name_attribute.id "
					+ "LEFT OUTER JOIN pony_leads.profile_attributes AS last_names ON leads.user_profile_id = last_names.user_profile_id AND last_names.attribute_id = last_name_attribute.id "
					+ "LEFT OUTER JOIN pony_leads.profile_attributes AS cities ON leads.user_profile_id = cities.user_profile_id AND cities.attribute_id = city_attribute.id "
					+ "LEFT OUTER JOIN pony_leads.profile_attributes AS electric_bills ON leads.user_profile_id = electric_bills.user_profile_id AND electric_bills.attribute_id = electric_bill_attribute.id "
					+ "LEFT OUTER JOIN pony_leads.profile_attributes AS utm_sources ON leads.user_profile_id = utm_sources.user_profile_id AND utm_sources.attribute_id = utm_source_attribute.id "
					+ "LEFT OUTER JOIN pony_leads.profile_attributes AS phone_scores ON leads.user_profile_id = phone_scores.user_profile_id AND phone_scores.attribute_id = phone_score_attribute.id "
					+ "LEFT OUTER JOIN pony_leads.profile_attributes AS credit_ranges ON leads.user_profile_id = credit_ranges.user_profile_id AND credit_ranges.attribute_id = credit_range_attribute.id "
					+ "LEFT OUTER JOIN pony_leads.profile_attributes AS phone_is_valids ON leads.user_profile_id = phone_is_valids.user_profile_id AND phone_is_valids.attribute_id = phone_is_valid_attribute.id "
					+ "LEFT OUTER JOIN pony_leads.profile_attributes AS phone_is_commercials ON leads.user_profile_id = phone_is_commercials.user_profile_id AND phone_is_commercials.attribute_id = phone_is_commercial_attribute.id "
					+ "LEFT OUTER JOIN pony_leads.profile_attributes AS address_scores ON leads.user_profile_id = address_scores.user_profile_id AND address_scores.attribute_id = address_score_attribute.id "
					+ "LEFT OUTER JOIN pony_leads.profile_attributes AS email_scores ON leads.user_profile_id = email_scores.user_profile_id AND email_scores.attribute_id = email_score_attribute.id "
					+ "LEFT OUTER JOIN (SELECT lead_id, SUM(CASE WHEN status=4 THEN 1 WHEN status=-2 THEN -1 ELSE 0 END) AS times_sold FROM pony_leads.lead_matches GROUP BY lead_id) AS times_sold ON times_sold.lead_id = lead_matches.lead_id "
					+ "LEFT OUTER JOIN easiersolar.sessions ON arrivals.external_id = sessions.id "
					+ "JOIN (SELECT * FROM easiersolar.`attributes` WHERE name='utm_term') keyword_attribute "
					+ "LEFT OUTER JOIN easiersolar.session_attributes as keywords ON keywords.session_id = sessions.id AND keywords.attribute_id = keyword_attribute.id "
					+ "left outer join (select lead_match_id, MAX(1) as contacted from pony_leads.advertiser_dispositions where pony_phase = 7 and status in (5, 9, 10, 14, 15) group by lead_match_id) as contacted_dispositions on contacted_dispositions.lead_match_id = lead_matches.id "
					+ "left outer join (select lead_match_id, MAX(1) as appointment_set from pony_leads.advertiser_dispositions where pony_phase = 7 and status in (9, 10, 14, 15) group by lead_match_id) as appointments_set on appointments_set.lead_match_id = lead_matches.id "
					+ "left outer join (select lead_match_id, MAX(1) as contract_signed from pony_leads.advertiser_dispositions where pony_phase = 7 and status in (9, 10) group by lead_match_id) as contracts_signed on contracts_signed.lead_match_id = lead_matches.id "
					+ "WHERE recently_updated.recently_updated > ? "
					+ "ON DUPLICATE KEY UPDATE advertiser_name=VALUES(advertiser_name), lead_id=VALUES(lead_id), attempt=VALUES(attempt), success=VALUES(success), returned=VALUES(returned), price=VALUES(price), conf_part=VALUES(conf_part), zip_code=VALUES(zip_code), state=VALUES(state), last_updated=VALUES(last_updated), created_at=VALUES(created_at), is_bind_eligible=VALUES(is_bind_eligible), contacted=VALUES(contacted), appointment_set=VALUES(appointment_set), contract_signed=VALUES(contract_signed);");
			if(startTime != null) {
				stmt.setTimestamp(1, startTime);
			} else {
				stmt.setTimestamp(1, new Timestamp(0));
			}
			stmt.execute();
			LOG.debug("output: " + stmt.getUpdateCount());
			// TODO: Check if it was successful.
		} catch (SQLException e) {
			LOG.error("SQLException migrating the data to the reporting table.", e);
			throw new PonyException("SQLException migrating the data to the reporting table.", e);
		} catch (NamingException e) {
			LOG.error("NamingException migrating the data to the reporting table.", e);
			throw new PonyException("NamingException migrating the data to the reporting table.", e);
		} finally {
			close(stmt);
			close(conn);
		}
	}

	@Override
	public void setEndTime(Timestamp endTime) {
		assert endTime != null;

		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = Model.connectReport();
			stmt = conn.prepareStatement("UPDATE lead_reporting_status SET last_run = ?;");
			stmt.setTimestamp(1,  endTime);
			stmt.execute();
		} catch (SQLException e) {
			LOG.error("SQLException setting the metadata for reporting. The prior data may be reprocessed.", e);
		} catch (NamingException e) {
			LOG.error("NamingException setting the metadata for reporting. The prior data may be reprocessed.", e);
		} finally {
			close(stmt);
			close(conn);
		}
	}

}
