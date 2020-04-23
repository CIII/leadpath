package com.pony.models;

import com.pony.PonyException;
import com.pony.advertiser.Advertiser;
import com.pony.advertiser.Buyer;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.advertiser.factory.ExternalIdMatcher;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.lead.LeadMatch;
import com.tapquality.db.tables.LeadMatches;
import com.tapquality.db.tables.Orders;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Name;
import org.jooq.impl.DSL;
import org.jooq.Record;
import org.jooq.Record6;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.Table;
import org.jooq.TableField;

import javax.management.Query;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/13/11
 * Time: 12:19 AM
 */
public class LeadMatchModel extends Model {
    private static final Log LOG = LogFactory.getLog(LeadMatchModel.class);
    protected static Table<Record> leadMatches = DSL.table(DSL.name("lead_matches")).as("lm");

    protected static SelectSelectStep<Record6<Integer, Integer, Integer, Byte, BigDecimal, String>> getSelectStatement(LeadMatches leadMatches, DSLContext create) {
    	return create.select(
    			leadMatches.ID,
    			leadMatches.LEAD_ID,
    			leadMatches.ORDER_ID,
    			leadMatches.STATUS,
    			leadMatches.PRICE,
    			leadMatches.EXTERNAL_ID);
    }
    
    protected LeadMatchModel(Long id) {
        super(id);
    }

    public static Long persist(Lead lead, RoutingCandidate candidate, LeadMatch.Status matchStatus) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("insert into lead_matches (lead_id, order_id, status, created_at) values(?,?,?,now())");
            stmt.setLong(1, lead.getId());
            stmt.setLong(2, candidate.getIo().getId());
            stmt.setInt(3, matchStatus.getStatus());

            Long leadMatchId = executeWithLastId(stmt);
            candidate.setLeadMatchId(leadMatchId);

            return leadMatchId;
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    /**
     * record the disposition, and update the lead match accordingly
     *
     * @param advertiserId
     * @param leadMatchId
     * @param disposition
     * @param phase
     * @throws NamingException
     * @throws SQLException
     */
    public static void persistDisposition(Long advertiserId, Long leadMatchId, Disposition disposition, PonyPhase phase)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null, stmt2 = null;

        boolean shouldInsert = true;
        if(phase == PonyPhase.ADVERTISER_DISPOSITION){
            LeadMatch match = LeadMatchModel.find(leadMatchId);
            if(match.getPrice().compareTo(disposition.getPrice()) == 0){
                shouldInsert = false;
            }
        }
        if(shouldInsert) {
            try {
                con = connectX();
                stmt = con.prepareStatement("insert into advertiser_dispositions (advertiser_id, lead_match_id, status, category, pony_phase, comment, price, created_at) values(?,?,?,?,?,?,?,now())");
                stmt.setLong(1, advertiserId);
                stmt.setLong(2, leadMatchId);
                stmt.setLong(3, disposition.getStatus().getStatus());
                stmt.setInt(4, (disposition.getCategory() == null ? -1 : disposition.getCategory().getId()));
                stmt.setInt(5, phase.getCode());
                stmt.setString(6, ((disposition.getComment() != null && disposition.getComment().length() > 255) ? disposition.getComment().substring(0, 254) : disposition.getComment()));
                stmt.setBigDecimal(7, disposition.getPrice());

                Long dispositionId = executeWithLastId(stmt);
                close(stmt);
                disposition.setId(dispositionId);
                disposition.setLeadMatchId(leadMatchId);

                // if there are buyers listed in this disposition, store those as well
                if (disposition.getBuyers().size() > 0) {
                    stmt = con.prepareStatement("insert ignore into buyers(buyer_id, name, state, zip, city, street, contact_name, contact_phone, created_at) values (?,?,?,?,?,?,?,?, now()) ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id), updated_at=now()");
                    stmt2 = con.prepareStatement("insert ignore into advertiser_disposition_buyers(advertiser_disposition_id, buyer_id, reservation_id, price, distance, group_id, max_posts, longitude, latitude, program_id, buyer_code, created_at) values(?,?,?,?,?,?,?,?,?,?,?, now())");

                    for (Buyer buyer : disposition.getBuyers()) {
                        stmt.setString(1, buyer.getBuyerId());
                        stmt.setString(2, buyer.getName());
                        stmt.setString(3, buyer.getState());
                        stmt.setString(4, buyer.getZipcode() == null ? "" : buyer.getZipcode());
                        stmt.setString(5, buyer.getCity());
                        stmt.setString(6, buyer.getAddress());
                        stmt.setString(7, buyer.getContactName());
                        stmt.setString(8, buyer.getContactPhone());
                        Long buyerId = executeWithLastId(stmt);

                        stmt2.setLong(1, dispositionId);
                        stmt2.setLong(2, buyerId);
                        stmt2.setString(3, buyer.getReservationId());
                        stmt2.setBigDecimal(4, buyer.getPrice());
                        stmt2.setString(5, buyer.getDistance());
                        stmt2.setString(6, buyer.getGroupId());
                        stmt2.setString(7, buyer.getMaxPosts());
                        stmt2.setString(8, buyer.getLongitude());
                        stmt2.setString(9, buyer.getLatitude());
                        stmt2.setString(10, buyer.getProgramId());
                        stmt2.setString(11, buyer.getBuyerCode());
                        stmt2.executeUpdate();
                    }
                    close(stmt2);
                    close(stmt);
                }

                // update the match
                if (PonyPhase.ADVERTISER_DISPOSITION.equals(phase)) {  // we're getting an async feedback call from the advertiser (or feed pull) for an already posted match
                    stmt = con.prepareStatement("update lead_matches set status=?, price=?, updated_at=now() where id=?");
                    stmt.setInt(1, LeadMatch.mapPingStateToMatchState(phase, disposition.getStatus()).getStatus());
                    stmt.setBigDecimal(2, disposition.getPrice());
                    stmt.setLong(3, leadMatchId);
                // if rejected, we update price to 0. We can't rely on requested price as our records, there are advertisers that would not update us for rejected leads
                }else if(PonyPhase.POST.equals(phase) && disposition.getStatus().equals(Disposition.Status.REJECTED)){
                    stmt = con.prepareStatement("update lead_matches set status=?, price=?, updated_at=now() where id=?");
                    stmt.setInt(1, LeadMatch.mapPingStateToMatchState(phase, disposition.getStatus()).getStatus());
                    stmt.setBigDecimal(2, BigDecimal.ZERO);
                    stmt.setLong(3, leadMatchId);
                } else if (PonyPhase.OUTCOME.equals(phase)) {
                	stmt = con.prepareStatement("UPDATE lead_matches set status=?, price=price + ?, updated_at=now() WHERE id = ?");
                	stmt.setInt(1, LeadMatch.mapPingStateToMatchState(phase,  disposition.getStatus()).getStatus());
                	stmt.setBigDecimal(2, disposition.getPrice());
                	stmt.setLong(3, leadMatchId);
                }else{
                    stmt = con.prepareStatement("update lead_matches set status=?, external_id=?, price=?, updated_at=now() where id=?");
                    stmt.setInt(1, LeadMatch.mapPingStateToMatchState(phase, disposition.getStatus()).getStatus());
                    stmt.setString(2, disposition.getExternalId());
                    stmt.setBigDecimal(3, disposition.getPrice());
                    stmt.setLong(4, leadMatchId);
                }

                stmt.executeUpdate();
            } finally {
                close(stmt2);
                close(stmt);
                close(con);
            }
        }
    }

    public static Long persistMessage(Long leadMatchId, PonyPhase phase, String msg) throws NamingException, SQLException {
        // create the lead_posts entry
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("insert into lead_posts (lead_match_id, pony_phase, sent_message, created_at) values(?,?,?,now())");
            stmt.setLong(1, leadMatchId);
            stmt.setInt(2, phase.getCode());
            stmt.setString(3, msg);

            return executeWithLastId(stmt);
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    public static void persistMessageResponse(Long postId, String result) throws NamingException, SQLException {

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("update lead_posts set response_message = ? where id = ?");
            stmt.setString(1, result);
            stmt.setLong(2, postId);
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    /**
     * read the matches and dispositions for the lead reference
     * (used during the poll phase)
     *
     * @param phase
     * @param leadId
     * @return
     */
    public static List<Disposition> findDispositions(PonyPhase phase, Long leadId) throws NamingException, SQLException {

        List<Disposition> dispositions = new ArrayList<Disposition>();
        if (leadId == null) {
            return dispositions;
        }

        Connection con = null;
        PreparedStatement stmt = null;


        try {
            con = connectX();
            stmt = con.prepareStatement("select b.buyer_id, d.id disposition_id, d.status, db.id disposition_buyer_id, db.reservation_id, db.price, db.longitude, db.latitude, db.program_id, db.buyer_code, b.name, b.zip, b.name, b.state, b.street, b.city, db.distance, d.lead_match_id from lead_matches lm join advertiser_dispositions d on d.lead_match_id = lm.id left outer join advertiser_disposition_buyers db on db.advertiser_disposition_id = d.id left outer join buyers b on b.id = db.buyer_id where lm.lead_id = ? and d.status=? and d.pony_phase=? order by d.id");
            stmt.setLong(1, leadId);
            stmt.setInt(2, Disposition.Status.ACCEPTED.getStatus()); // only accepted ones
            stmt.setLong(3, phase.getCode());

            ResultSet rs = stmt.executeQuery();
            Disposition disposition = null;
            while (rs.next()) {
                Long dId = rs.getLong("disposition_id");
                // Note: the sql is order by disposition_id!!!
                if (disposition == null || !disposition.getId().equals(dId)) {
                    disposition = Disposition.create(PonyPhase.POLL, Disposition.Status.ACCEPTED);
                    disposition.setId(dId);
                    dispositions.add(disposition);
                    long leadMatchId = rs.getLong("lead_match_id");
                    disposition.setLeadMatchId(leadMatchId);
                }

                String reservationId = rs.getString("reservation_id");
                String buyerId = rs.getString("buyer_id");
                if (reservationId != null || buyerId != null) {
                    Buyer buyer = Buyer.create(buyerId, rs.getString("name"), rs.getString("zip"));
                    buyer.setReservationId(reservationId);
                    buyer.setAddress(rs.getString("street"));
                    buyer.setPrice(rs.getString("price"));
                    buyer.setCity(rs.getString("city"));
                    buyer.setDistance(rs.getString("distance"));
                    buyer.setState(rs.getString("state"));
                    buyer.setId(rs.getLong("disposition_buyer_id"));
                    buyer.setLongitude(rs.getString("longitude"));
                    buyer.setLatitude(rs.getString("latitude"));
                    buyer.setBuyerCode(rs.getString("buyer_code"));
                    buyer.setProgramId(rs.getString("program_id"));
                    disposition.addBuyer(buyer);
                }
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return dispositions;
    }

    public static List<Disposition> findDispositions(PonyPhase phase, Long leadId, List<Long> dispositionBuyerIds) throws NamingException, SQLException {

        assert dispositionBuyerIds != null;
        if (dispositionBuyerIds.size() == 0) {
            return Collections.emptyList();
        }

        Connection con = null;
        PreparedStatement stmt = null;
        List<Disposition> dispositions = new ArrayList<Disposition>();
        try {
            con = connectX();

            StringBuilder sql = new StringBuilder();
            sql.append("select b.buyer_id, lm.id lead_match_id, d.id disposition_id, d.status, db.id disposition_buyer_id, db.reservation_id, db.price, db.longitude, db.latitude, db.program_id, db.buyer_code, b.name, b.zip, b.name, b.state, b.street, b.city, db.distance from lead_matches lm join advertiser_dispositions d on d.lead_match_id = lm.id left outer join advertiser_disposition_buyers db on db.advertiser_disposition_id = d.id left outer join buyers b on b.id = db.buyer_id where lm.lead_id = ? and d.status=? and d.pony_phase=?");

            // limit to the list of advertiser disposition buyer ids
            sql.append(" and db.id in(");
            for (int i = 0; i < dispositionBuyerIds.size(); i++) {
                if (i > 0) {
                    sql.append(",");
                }
                sql.append(dispositionBuyerIds.get(i));
            }
            sql.append(") order by d.id");

            stmt = con.prepareStatement(sql.toString());

            stmt.setLong(1, leadId);
            stmt.setInt(2, Disposition.Status.ACCEPTED.getStatus()); // only accepted ones
            stmt.setLong(3, phase.getCode());

            ResultSet rs = stmt.executeQuery();
            Disposition disposition = null;
            while (rs.next()) {
                Long dId = rs.getLong("disposition_id");
                // Note: the sql is order by disposition_id!!!
                if (disposition == null || !disposition.getId().equals(dId)) {
                    disposition = Disposition.create(PonyPhase.POLL, Disposition.Status.ACCEPTED);
                    disposition.setId(dId);
                    dispositions.add(disposition);
                    disposition.setLeadMatchId(rs.getLong("lead_match_id"));
                }

                String reservationId = rs.getString("reservation_id");
                String buyerId = rs.getString("buyer_id");
                if (reservationId != null || buyerId != null) {
                    Buyer buyer = Buyer.create(buyerId, rs.getString("name"), rs.getString("zip"));
                    buyer.setReservationId(reservationId);
                    buyer.setAddress(rs.getString("street"));
                    buyer.setPrice(rs.getString("price"));
                    buyer.setCity(rs.getString("city"));
                    buyer.setDistance(rs.getString("distance"));
                    buyer.setState(rs.getString("state"));
                    buyer.setId(rs.getLong("disposition_buyer_id"));
                    buyer.setLongitude(rs.getString("longitude"));
                    buyer.setLatitude(rs.getString("latitude"));
                    buyer.setBuyerCode(rs.getString("buyer_code"));
                    buyer.setProgramId(rs.getString("program_id"));
                    disposition.addBuyer(buyer);
                }
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return dispositions;
    }

    private static String getNDaysBack(int n) {
//        TimeZone accountTz = TimeZone.getTimeZone(account.getTimeZone());
        TimeZone localTz = TimeZone.getTimeZone("America/New_York");
        Calendar cal = Calendar.getInstance(localTz);
        cal.add(Calendar.DAY_OF_YEAR, -1 * n);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(localTz);

        return df.format(cal.getTime());
    }

    public static List<LeadMatch> findLeadpostsWithinNDays(int n, Advertiser advertiser) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stmt = null;

        List<LeadMatch> matches;
        try {

            con = connectX();
            // Should select the newest lead match records
            //SELECT
            //  lm2.id, lm2.external_id, lm2.order_id, lm2.price
            //FROM lead_matches lm2
            //JOIN (
            //  SELECT
            //      lm.external_id, MAX(lm.updated_at) latest
            //  FROM lead_posts lp
            //  JOIN lead_matches lm ON lp.lead_match_id = lm.id
            //  JOIN orders o ON lm.order_id = o.id
            //  WHERE o.advertiser_id = ? AND
            //      lp.created_at > ? AND
            //      lm.external_id IS NOT NULL
            //  GROUP BY lm.external_id, o.advertiser_id
            //  ) latest_lm ON lm2.external_id = latest_lm.external_id AND lm2.updated_at = latest_lm.latest;"

            stmt = con.prepareStatement("SELECT lm2.id, lm2.external_id, lm2.order_id, lm2.price, lm2.lead_id FROM lead_matches lm2 JOIN (SELECT lm.external_id, MAX(lm.updated_at) latest FROM lead_posts lp JOIN lead_matches lm ON lp.lead_match_id = lm.id JOIN orders o ON lm.order_id = o.id WHERE o.advertiser_id = ? AND lp.created_at > ? AND lm.external_id IS NOT NULL GROUP BY lm.external_id, o.advertiser_id) latest_lm ON lm2.external_id = latest_lm.external_id AND lm2.updated_at = latest_lm.latest;");
            stmt.setLong(1, advertiser.getId());
            stmt.setString(2, getNDaysBack(n));
            ResultSet rs = stmt.executeQuery();
            matches = new LinkedList<LeadMatch>();
            while(rs.next()){
                matches.add(LeadMatch.create(rs));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return matches;
    }

    public static List<LeadMatch> findLeadpostsInRange(String begin, String end, Advertiser advertiser) throws SQLException, NamingException{
        Connection con = null;
        PreparedStatement stmt = null;

        List<LeadMatch> matches;
        try {

            con = connectX();
            // Should select the newest lead match records
            //SELECT
            //  lm2.id, lm2.external_id, lm2.order_id, lm2.price
            //FROM lead_matches lm2
            //JOIN (
            //  SELECT
            //      lm.external_id, MAX(lm.updated_at) latest
            //  FROM lead_posts lp
            //  JOIN lead_matches lm ON lp.lead_match_id = lm.id
            //  JOIN orders o ON lm.order_id = o.id
            //  WHERE o.advertiser_id = ? AND
            //      lp.created_at >= ? AND
            //      lp.created_at < ? AND
            //      lm.external_id IS NOT NULL
            //  GROUP BY lm.external_id, o.advertiser_id
            //  ) latest_lm ON lm2.external_id = latest_lm.external_id AND lm2.updated_at = latest_lm.latest;"

            stmt = con.prepareStatement("SELECT lm2.id, lm2.external_id, lm2.order_id, lm2.price, lm2.lead_id FROM lead_matches lm2 JOIN (SELECT lm.external_id, MAX(lm.updated_at) latest FROM lead_posts lp JOIN lead_matches lm ON lp.lead_match_id = lm.id JOIN orders o ON lm.order_id = o.id WHERE o.advertiser_id = ? AND lp.created_at >= ? AND lp.created_at < ? AND lm.external_id IS NOT NULL GROUP BY lm.external_id, o.advertiser_id) latest_lm ON lm2.external_id = latest_lm.external_id AND lm2.updated_at = latest_lm.latest;");
            stmt.setLong(1, advertiser.getId());
            stmt.setString(2,begin);
            stmt.setString(3, end);
            ResultSet rs = stmt.executeQuery();
            matches = new LinkedList<LeadMatch>();
            while(rs.next()){
                matches.add(LeadMatch.create(rs));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return matches;
    }
    public static List<LeadMatch> findByArrivalID(Long arrivalID) throws SQLException, NamingException{

        Connection con = null;
        PreparedStatement stmt = null;

        List<LeadMatch> matches;
        try {

            con = connectX();
//        SELECT
//            lm.id,
//            lm.external_id,
//            lm.price,
//            lm.order_id
//        FROM
//            lead_matches lm
//        JOIN
//            (SELECT
//                lm.id lm_id,
//                MAX(lm.updated_at) latest
//            FROM
//                arrivals a
//            JOIN leads l ON a.id = l.arrival_id
//            JOIN lead_matches lm ON l.id=lm.lead_id
//            WHERE
//                a.id = ?
//            AND lm.status = 4
//            GROUP BY
//            lm.id) lm_latest ON lm.id=lm_latest.lm_id AND lm.updated_at=lm_latest.latest

            stmt = con.prepareStatement("SELECT " + LeadMatch.COLUMNS + " FROM lead_matches lm JOIN (SELECT lm.id lm_id, MAX(lm.updated_at) latest FROM arrivals a JOIN leads l ON a.id = l.arrival_id JOIN lead_matches lm ON l.id=lm.lead_id WHERE a.id = ? AND lm.status = 4 GROUP BY lm.id) lm_latest ON lm.id=lm_latest.lm_id AND lm.updated_at=lm_latest.latest");
            stmt.setLong(1, arrivalID);
            ResultSet rs = stmt.executeQuery();
            matches = new LinkedList<LeadMatch>();
            while(rs.next()){
                matches.add(LeadMatch.create(rs));
            }
            LOG.info(matches.toString());
        }
        finally {
            close(stmt);
            close(con);
        }

        return matches;

    }
    /**
     * update the buyer disposition based in the result we have (from the post)
     *
     * @param buyer
     * @param postDisposition
     */
    public static void updateBuyerDisposition(Buyer buyer, Disposition postDisposition) throws NamingException, SQLException {
        if (buyer == null || buyer.getId() == null || postDisposition == null) {
            return;
        }

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            //
            con = connectX();
            stmt = con.prepareStatement("update advertiser_disposition_buyers set status = ?, external_lead_id = ?, updated_at = now() where id = ?");
            stmt.setLong(1, postDisposition.getStatus().getStatus());
            stmt.setString(2, postDisposition.getExternalId());
            stmt.setLong(3, buyer.getId());
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    public static void flagBuyerDispositionForPreferred(Buyer buyer) throws NamingException, SQLException {
        if (buyer == null || buyer.getId() == null || !buyer.isPreferred()) {
            return;
        }

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            //
            con = connectX();
            stmt = con.prepareStatement("update advertiser_disposition_buyers set is_preferred=1, updated_at = now() where id = ?");
            stmt.setLong(1, buyer.getId());
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    public static LeadMatch findByExternalId(Advertiser advertiser, String externalId, ExternalIdMatcher matcher) throws SQLException, NamingException {
    	assert matcher != null: "An external ID matcher must be provided.";
    	
        Connection con = null;
        PreparedStatement stmt = null;
        PreparedStatement broaderSearch = null;
        LeadMatch returnValue = null;
        DSLContext create = null;
        try {

            con = connectX();
            create = DSL.using(con, SQLDialect.MYSQL);

        	LeadMatches leadMatches = LeadMatches.LEAD_MATCHES.as("lm");
        	Orders orders = Orders.ORDERS.as("o");
            SelectSelectStep<Record6<Integer, Integer, Integer, Byte, BigDecimal, String>> selectStatement = getSelectStatement(leadMatches, create);
            SelectWhereStep<? extends Record> query = selectStatement.from(LeadMatches.LEAD_MATCHES.as("lm")).join(Orders.ORDERS.as("o")).on("o.id = lm.order_id");
            Collection<Condition> conditions = new ArrayList<>();
            conditions.add(orders.ADVERTISER_ID.eq(advertiser.getId().intValue()));
            conditions = matcher.getPredicate(conditions, leadMatches, externalId);
            query.where(conditions);
            ResultSet rs = query.fetchResultSet();
            if (rs.first()) {
                returnValue = LeadMatch.create(rs);
            }
        }
        finally {
            close(stmt);
            close(broaderSearch);
            close(con);
        }

        return returnValue;
    }

    public static LeadMatch find(long leadMatchId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {

            con = connectX();
            stmt = con.prepareStatement("select " + LeadMatch.COLUMNS + " from lead_matches lm WHERE id = ?");
            stmt.setLong(1, leadMatchId);
            ResultSet rs = stmt.executeQuery();
            if (rs.first()) {
                return LeadMatch.create(rs);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }
    
    public static List<Map<String, String>> getLeadMatches(long leadId) throws PonyException {
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	List<Map<String, String>> returnValue = new ArrayList<>();
    	
    	try {
    		conn = connectX();
    		stmt = conn.prepareStatement("SELECT lead_matches.id, advertisers.name, advertiser_dispositions.pony_phase, lead_matches.status, advertiser_dispositions.comment FROM lead_matches JOIN orders ON lead_matches.order_id = orders.id JOIN advertisers ON orders.advertiser_id = advertisers.id LEFT OUTER JOIN (SELECT * FROM advertiser_dispositions WHERE pony_phase = 3 ORDER BY created_at DESC LIMIT 1) AS advertiser_dispositions ON lead_matches.id = advertiser_dispositions.lead_match_id WHERE lead_matches.lead_id = ?");
    		stmt.setLong(1, leadId);
    		ResultSet rs = stmt.executeQuery();
    		while(rs.next()) {
    			Map<String, String> item = new HashMap<>();
    			item.put("id", Long.toString(rs.getLong("id")));
    			item.put("advertiser", rs.getString("name"));
    			item.put("pony_phase", rs.getString("pony_phase"));
    			item.put("status", Integer.toString(rs.getInt("status")));
    			item.put("comment", rs.getString("comment"));
    	
    			returnValue.add(item);
    		}
    	} catch (SQLException e) {
    		String errMsg = "SQLException thrown while retrieving lead matches.";
    		LOG.warn(errMsg, e);
    		throw new PonyException(errMsg, e);
    	} catch (NamingException e) {
    		String errMsg = "NamingException thrown while attempting to connect to the database.";
    		LOG.warn(errMsg, e);
    		throw new PonyException(errMsg, e);
    	} finally {
    		close(stmt);
    		close(conn);
    	}
    	
    	return returnValue;
    }

	public static List<LeadMatch> findDuplicateMatches(Long userProfileId, Long leadTypeId) throws PonyException {
		Connection conn = null;
		PreparedStatement stmt = null;
		List<LeadMatch> returnValue = new ArrayList<>();
		try {
			conn = connectX();
			stmt = conn.prepareStatement("SELECT " + LeadMatch.COLUMNS + " FROM lead_matches lm JOIN leads ON lm.lead_id = leads.id WHERE leads.user_profile_id = ? AND leads.lead_type_id = ? AND lm.status = 4 AND (lm.created_at > DATE_SUB(CURRENT_TIMESTAMP(), interval 30 DAY) OR lm.updated_at > DATE_SUB(CURRENT_TIMESTAMP(), interval 30 DAY));");
			stmt.setLong(1, userProfileId);
			stmt.setLong(2, leadTypeId);
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				LeadMatch match = LeadMatch.create(rs);
				returnValue.add(match);
			}
		} catch (NamingException e) {
			String errMsg = "NamingException connecting to the database. This likely means a long-term connection problem.";
			LOG.error(errMsg, e);
			throw new PonyException(errMsg, e);
		} catch (SQLException e) {
			String errMsg = "SQLException connecting to the database while looking for duplicate matches.";
			LOG.error(errMsg, e);
			throw new PonyException(errMsg, e);
		} finally {
			close(stmt);
			close(conn);
		}
		
		return returnValue;
	}
}
