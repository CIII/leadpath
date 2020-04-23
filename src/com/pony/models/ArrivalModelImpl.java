package com.pony.models;

import com.pony.lead.Arrival;
import com.pony.lead.UserProfile;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;
import com.pony.validation.ValidationResponse;

import javax.inject.Inject;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/29/11
 * Time: 9:40 PM
 */
public class ArrivalModelImpl extends Model implements ArrivalModel {

    private static final String COLUMNS = "a.id, a.publisher_id, a.publisher_list_id, a.user_profile_id, a.ip_address, a.user_agent, a.referrer, a.arrival_source_id, a.external_id, a.robot_id";

    @Inject protected ArrivalModelImpl() {
    	super(0L);
    }
    
    protected ArrivalModelImpl(Long id) {
        super(id);
    }

    @Override
	public Arrival find(Long id) throws NamingException, SQLException {
    	return findStatic(id);
    }
    
    public static Arrival findStatic(Long id) throws NamingException, SQLException {
        if (id == null) {
            return null;
        }
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select " + COLUMNS + " from arrivals a where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Arrival.create(rs);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;

    }

    @Override
	public Arrival find(Long publisherId, Long publisherListId, String externalId) throws NamingException, SQLException {
    	return findStatic(publisherId, publisherListId, externalId);
    }
    
    public static Arrival findStatic(Long publisherId, Long publisherListId, String externalId) throws NamingException, SQLException {

        if (publisherId == null || publisherListId == null || externalId == null) {
            return null;
        }

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select " + COLUMNS + " from arrivals a where publisher_id = ? and publisher_list_id = ? and external_id = ?");
            stmt.setLong(1, publisherId);
            stmt.setLong(2, publisherListId);
            stmt.setString(3, externalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Arrival.create(rs);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;

    }

    @Override
	public Arrival findByLeadId(Long leadId) throws NamingException, SQLException {
    	return findByLeadIdStatic(leadId);
    }
    
    public static Arrival findByLeadIdStatic(Long leadId) throws NamingException, SQLException {
        if (leadId == null) {
            return null;
        }
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select " + COLUMNS + " from arrivals a join leads l on l.arrival_id = a.id where l.id = ?");
            stmt.setLong(1, leadId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Arrival.create(rs);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    @Override
	public Arrival findByLeadMatchId(Long leadMatchId) throws NamingException, SQLException {
        if (leadMatchId == null) {
            return null;
        }
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select " + COLUMNS + " from arrivals a join leads l on l.arrival_id = a.id join lead_matches lm on l.id = lm.lead_id where lm.id = ?");
            stmt.setLong(1, leadMatchId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Arrival.create(rs);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    @Override
	public Arrival findByExternalId(Publisher publisher, String external_id) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            // SELECT *
            // FROM arrivals
            // WHERE publisher_id = ?
            // AND external_id =?
            stmt = con.prepareStatement("select " + COLUMNS + " from arrivals a WHERE publisher_id=? AND external_id=?;");
            stmt.setLong(1, publisher.getId());
            stmt.setString(2, external_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Arrival.create(rs);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    @Override
	public Arrival create(Arrival a) throws SQLException, NamingException {
    	return createStatic(a);
    }
    
    public static Arrival createStatic(Arrival a) throws SQLException, NamingException {
        Connection con = null;
        try {
            con = connectX();
            return createStatic(con, a);
        }
        finally {
            close(con);
        }
    }

    @Override
	public Arrival create(Connection con, Arrival a) throws SQLException {
    	return createStatic(con, a);
    }
    
    public static Arrival createStatic(Connection con, Arrival a) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("insert ignore into arrivals (publisher_id, user_profile_id, ip_address, user_agent, referrer, created_at, publisher_list_id, arrival_source_id, external_id, robot_id) values(?,?,?,?,?,now(), ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id), last_seen_at=now()");
            stmt.setLong(1, a.getPublisherId());

            if (a.getUserProfileId() != null) {
                stmt.setLong(2, a.getUserProfileId());
            }
            else {
                stmt.setString(2, null);
            }

            stmt.setString(3, a.getIpAddress());
            stmt.setString(4, a.getUserAgent());
            stmt.setString(5, a.getReferrer());
            stmt.setLong(6, a.getPublisherListId());
            stmt.setString(7, a.getArrivalSourceId());
            stmt.setString(8, a.getExternalId());
            stmt.setString(9, a.getRobotId());

            Long id = executeWithLastId(stmt);
            a.setId(id);

            return a;
        }
        finally {
            close(stmt);
        }
    }

    private static Arrival find(Connection con, Arrival a) throws SQLException {
        PreparedStatement stmt = null;
        try {
            if (a == null || a.getId() == null) {
                return null;
            }
            stmt = con.prepareStatement("select " + COLUMNS + " from arrivals a where id= ?");
            stmt.setLong(1, a.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Arrival arrival = Arrival.create(rs);
                arrival.setId(rs.getLong("id"));

                return arrival;
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    @Override
	public Arrival findByPublisherAndUserProfile(Publisher publisher, UserProfile userProfile) throws NamingException, SQLException {
    	return findByPublisherAndUserProfileStatic(publisher, userProfile);
    }
    
    public static Arrival findByPublisherAndUserProfileStatic(Publisher publisher, UserProfile userProfile) throws NamingException, SQLException {
        Connection con = null;
        try {
            con = connectX();
            return findByPublisherAndUserProfileStatic(con, publisher, userProfile);
        }
        finally {
            close(con);
        }
    }

    @Override
	public Arrival findByPublisherAndUserProfile(Connection con, Publisher publisher, UserProfile userProfile) throws SQLException {
    	return findByPublisherAndUserProfileStatic(con, publisher, userProfile);
    }
    
    public static Arrival findByPublisherAndUserProfileStatic(Connection con, Publisher publisher, UserProfile userProfile) throws SQLException {
        PreparedStatement stmt = null;
        try {
            if (publisher == null || publisher.getId() == null || userProfile == null || userProfile.getId() == null) {
                return null;
            }

            stmt = con.prepareStatement("select " + COLUMNS + " from arrivals a where publisher_id = ? and user_profile_id = ?");
            stmt.setLong(1, publisher.getId());
            stmt.setLong(2, userProfile.getId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Arrival.create(rs);
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    @Override
	public void touch(Arrival arrival) throws NamingException, SQLException {
    	touchStatic(arrival);
    }
    
    public static void touchStatic(Arrival arrival) throws NamingException, SQLException {

        if (arrival == null) {
            throw new IllegalArgumentException("no arrival to touch");
        }
        if (arrival.getId() == null) {
            throw new IllegalArgumentException("no arrival.id to touch");
        }

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("update arrivals set last_seen_at = now() where id = ?");
            stmt.setLong(1, arrival.getId());
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    @Override
	public void touch(Arrival arrival, ValidationResponse vResponse) throws NamingException, SQLException {
    	touchStatic(arrival, vResponse);
    }
    
    public static void touchStatic(Arrival arrival, ValidationResponse vResponse) throws NamingException, SQLException {
        if (arrival == null) {
            throw new IllegalArgumentException("no arrival to touch");
        }
        if (arrival.getId() == null) {
            throw new IllegalArgumentException("no arrival.id to touch");
        }
        if (vResponse == null) {
            throw new IllegalArgumentException("no ValidationResponse to touch the arrival with");
        }

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("update arrivals set last_seen_at = now(), validation_code = ? where id = ?");
            stmt.setInt(1, vResponse.getCode());
            stmt.setLong(2, arrival.getId());
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    /**
     * read arrivals from a starting id up to #count rows in order of arrival id
     *
     * @param publisherList
     * @param minId         the starting arrival id
     * @param count         max number of arrivals to read
     * @return an ordered list (by arrival id asc) of arrivals
     */
    @Override
	public List<Arrival> findRange(PublisherList publisherList, Long minId, int count) throws NamingException, SQLException {
    	return findRangeStatic(publisherList, minId, count);
    }
    
    public static List<Arrival> findRangeStatic(PublisherList publisherList, Long minId, int count) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        List<Arrival> arrivals = new LinkedList<Arrival>();
        try {
            con = connectX();
            stmt = con.prepareStatement("select " + COLUMNS + " from arrivals a where id >=? and publisher_list_id = ? order by a.id asc limit ?");
            stmt.setLong(1, minId);
            stmt.setLong(2, publisherList.getId());
            stmt.setInt(3, count);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Arrival arrival = Arrival.create(rs);
                arrivals.add(arrival);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return arrivals;
    }

    /**
     * read the arrivals for all messages to the provided publisher list that were opened (within days), and have not received a message since sendFrequencyDays
     *
     * @param publisherList     the list that we look at the messages for
     * @param openWithinDays    number of days to look back (from today) for an open event for the email address of an arrival
     * @param sendFrequencyDays number of days to block. If there was a message sent within this number of days, we cannot send another one yet
     * @param maxRowCount       cut off at this number of arrivals found
     * @return
     */
    @Override
	public List<Arrival> findRange(PublisherList publisherList, int openWithinDays, int sendFrequencyDays, int maxRowCount) throws NamingException, SQLException {
    	return findRangeStatic(publisherList, openWithinDays, sendFrequencyDays, maxRowCount);
    }
    
    public static List<Arrival> findRangeStatic(PublisherList publisherList, int openWithinDays, int sendFrequencyDays, int maxRowCount) throws NamingException, SQLException {

        Connection con = null;
        PreparedStatement stmt = null;

        List<Arrival> arrivals = new LinkedList<Arrival>();
        try {
            con = connectX();
            // select
//            stmt = con.prepareStatement("select " + COLUMNS + " from arrivals a where id >=? and publisher_list_id = ? order by a.id asc limit ?");
            stmt = con.prepareStatement("select distinct " + COLUMNS + " from arrivals a join (select up.id user_profile_id from user_profiles up join messages m on m.user_profile_id = up.id left join opens o on o.message_id = m.id group by up.id having datediff(curdate(), max(m.created_at)) >= ? and datediff(curdate(), max(o.created_at)) > ?)o on a.user_profile_id = o.user_profile_id where a.publisher_list_id = ? order by a.user_profile_id desc limit ?");
            stmt.setInt(1, sendFrequencyDays);
            stmt.setInt(2, openWithinDays);
            stmt.setLong(3, publisherList.getId());
            stmt.setLong(4, maxRowCount);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Arrival arrival = Arrival.create(rs);
                arrivals.add(arrival);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return arrivals;
    }
}
