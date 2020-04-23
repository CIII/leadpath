package com.pony.models;

import com.pony.PonyException;
import com.pony.lead.LeadType;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;
import com.pony.publisher.Status;
import com.tapquality.db.tables.records.PublisherListMembersRecord;
import com.tapquality.db.tables.records.PublisherListOrdersRecord;
import com.tapquality.db.tables.records.PublisherListsRecord;

import static com.tapquality.db.tables.PublisherListOrders.PUBLISHER_LIST_ORDERS;
import static com.tapquality.db.tables.PublisherLists.PUBLISHER_LISTS;
import static com.tapquality.db.tables.PublisherListMembers.PUBLISHER_LIST_MEMBERS;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectConnectByStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/10/11
 * Time: 8:32 PM
 */
public class PublisherListModel extends Model {
	private static final Log LOG = LogFactory.getLog(PublisherListModel.class);
    private static final String COLUMNS = "id, lead_type_id, name, ext_list_id, status, max_lead_units, is_direct";

    protected PublisherListModel(Long id) {
        super(id);
    }

    public static PublisherList find(Long id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select " + COLUMNS + " from publisher_lists where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String externalId = rs.getString("ext_list_id");
                Status status = Status.parse(rs.getInt("status"));
                LeadType leadType = LeadType.find(con, rs.getLong("lead_type_id"));
                int maxLeadUnits = rs.getInt("max_lead_units");
                boolean direct = rs.getBoolean("is_direct");
                return PublisherList.create(id, leadType, name, externalId, status, maxLeadUnits, direct);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    /**
     * find the publisher list, based on the external id, and lookup the LeadType assigned to this publisher list.
     *
     * @param externalId the ext_list_id to lookup
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public static PublisherList findByExternalId(String externalId) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select " + COLUMNS + " from publisher_lists where ext_list_id = ?");
            stmt.setString(1, externalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                Status status = Status.parse(rs.getInt("status"));
                LeadType leadType = LeadType.find(con, rs.getLong("lead_type_id"));
                int maxLeadUnits = rs.getInt("max_lead_units");
                boolean direct = rs.getBoolean("is_direct");
                return PublisherList.create(id, leadType, name, externalId, status, maxLeadUnits, direct);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static List<String> findExternalIdsForOrders(Publisher publisher, LeadType leadType, List<Long> orderCodes) throws NamingException, SQLException {
        List<String> extIds = new ArrayList<String>();

        if (orderCodes == null || orderCodes.size() == 0) {
            return extIds;
        }

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            StringBuilder sql = new StringBuilder();
            sql.append("select distinct ext_list_id from publisher_lists pl join publisher_list_members plm on plm.publisher_list_id = pl.id join publisher_list_orders plo on plo.publisher_list_id = pl.id join orders o on o.id = plo.order_id where plm.publisher_id = ? and pl.status = 1 and o.lead_type_id = ? and o.id in (");
            boolean f = true;
            for (Long id : orderCodes) {

                if (f) {
                    f = false;
                }
                else {
                    sql.append(",");
                }
                sql.append(id);
            }
            sql.append(")");

            stmt = con.prepareStatement(sql.toString());
            stmt.setLong(1, publisher.getId());
            stmt.setLong(2, leadType.getId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                extIds.add(rs.getString("ext_list_id"));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return extIds;
    }

    public static PublisherList create(LeadType leadType, String name, String externalId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("insert ignore into publisher_lists (lead_type_id, name, ext_list_id, created_at) values(?, ?, ?, now())");
            stmt.setLong(1, leadType.getId());
            stmt.setString(2, name);
            stmt.setString(3, externalId);

            Long id = executeWithLastId(stmt);
            if (id != null) {
                Status status = Status.parse(0); // create as paused

                return PublisherList.create(id, leadType, name, externalId, status, 1, false);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }
    
    public static Result<PublisherListsRecord> getAllPublisherLists(boolean includePaused) throws PonyException {
    	Connection conn = null;
    	    	
    	try {
    		conn = connectX();
    		DSLContext context = DSL.using(conn);
    		SelectWhereStep<PublisherListsRecord> step = context.selectFrom(PUBLISHER_LISTS);
    		SelectConnectByStep<PublisherListsRecord> step2 = step;
    		if (!includePaused) {
    			step2 = step.where(PUBLISHER_LISTS.STATUS.eq((byte) 1));
    		}
    		return step2.orderBy(PUBLISHER_LISTS.NAME.asc()).fetch();
    	} catch (NamingException e) {
    		String errMsg = "NamingException retrieving publisher lists. This is likely a configuration problem.";
			LOG.warn(errMsg, e);
			throw new PonyException(errMsg, e);
		} catch (SQLException e) {
			String errMsg = "SQLException retrieving publisher lists.";
			LOG.warn(errMsg, e);
			throw new PonyException(errMsg, e);
		} finally {
    		close(conn);
    	}
    }
    
    public static PublisherListsRecord persistPublisherList(PublisherList publisherList) throws PonyException {
    	Connection conn = null;
    	
    	PublisherListsRecord returnValue;
    	boolean isUpdate = publisherList.getId() == 0 ? false : true;
    	try {
    		conn = connectX();
    		DSLContext context = DSL.using(conn);
    		if (isUpdate) {
    			context.update(PUBLISHER_LISTS)
    					.set(PUBLISHER_LISTS.NAME, publisherList.getName())
    					.set(PUBLISHER_LISTS.IS_DIRECT, (byte) (publisherList.isDirect() ? 1 : 0))
    					.set(PUBLISHER_LISTS.MAX_LEAD_UNITS, publisherList.getMaxLeadUnits())
    					.where(PUBLISHER_LISTS.ID.eq(publisherList.getId().intValue())).execute();
    			returnValue = context.selectFrom(PUBLISHER_LISTS)
    					.where(PUBLISHER_LISTS.ID.eq(publisherList.getId().intValue()))
    					.fetchOne();
    		} else {
    			returnValue = context.insertInto(PUBLISHER_LISTS, PUBLISHER_LISTS.NAME, PUBLISHER_LISTS.IS_DIRECT, PUBLISHER_LISTS.MAX_LEAD_UNITS, PUBLISHER_LISTS.LEAD_TYPE_ID, PUBLISHER_LISTS.EXT_LIST_ID, PUBLISHER_LISTS.STATUS)
    					.values(publisherList.getName(), (byte) (publisherList.isDirect() ? 1 : 0), publisherList.getMaxLeadUnits(), publisherList.getLeadType().getId().intValue(), publisherList.getExternalId(), (byte) publisherList.getStatus().getStatus())
    					.returning()
    					.fetchOne();
    		}
    		
    		// Save orders
    		persistOrdersCollection(publisherList, context);
    		persistPublishersCollection(publisherList, context);

    		// TODO: Save publishers
    	} catch (NamingException e) {
    		String errMsg = "NamingException persisting a publisherList. This is likely a configuraiton problem and represents a permanent issue until fixed.";
    		LOG.warn(errMsg, e);
    		throw new PonyException(errMsg, e);
		} catch (SQLException e) {
			String errMsg = "SQLException persisting a publisherList.";
			LOG.warn(errMsg, e);;
			throw new PonyException(errMsg, e);
		} finally {
			close(conn);
		}
    	
    	return returnValue;
    }

	protected static void persistOrdersCollection(PublisherList publisherList, DSLContext context) {
		// Fetch all orders
		Result<Record1<Integer>> result = context.select(PUBLISHER_LIST_ORDERS.ORDER_ID)
				.from(PUBLISHER_LIST_ORDERS)
				.where(PUBLISHER_LIST_ORDERS.PUBLISHER_LIST_ID.eq(publisherList.getId().intValue()))
				.and(PUBLISHER_LIST_ORDERS.STATUS.eq((byte) 1))
				.fetch();
		List<Integer> existingOrders = new ArrayList<>();
		for (Record1<Integer> record : result) {
			existingOrders.add(record.getValue(PUBLISHER_LIST_ORDERS.ORDER_ID));
		}
		// Insert Orders
		if (publisherList.getOrders().size() > 0) {
			InsertSetMoreStep<PublisherListOrdersRecord> step2 = null;
			for (int i = 0; i < publisherList.getOrders().size(); i++) {
				if (step2 == null) {
					step2 = context.insertInto(PUBLISHER_LIST_ORDERS)
							.set(PUBLISHER_LIST_ORDERS.NAME, publisherList.getName() + ":" + i);
				} else {
					step2 = step2.newRecord()
							.set(PUBLISHER_LIST_ORDERS.NAME, publisherList.getName() + ":" + i);
				}
				step2 = step2.set(PUBLISHER_LIST_ORDERS.PUBLISHER_LIST_ID, publisherList.getId().intValue())
						.set(PUBLISHER_LIST_ORDERS.ORDER_ID, publisherList.getOrders().get(i))
						.set(PUBLISHER_LIST_ORDERS.STATUS, (byte) 1)
						.set(PUBLISHER_LIST_ORDERS.CREATED_AT, DSL.currentTimestamp())
						.set(PUBLISHER_LIST_ORDERS.UPDATED_AT, DSL.currentTimestamp());
				
				// Remove order from list of orders
				existingOrders.remove(publisherList.getOrders().get(i));
			}
			step2.onDuplicateKeyUpdate()
					.set(PUBLISHER_LIST_ORDERS.STATUS, (byte) 1)
					.set(PUBLISHER_LIST_ORDERS.UPDATED_AT, DSL.currentTimestamp())
					.execute();
		}
		
		// If there are remaining orders, set their status to 0
		if (existingOrders.size() > 0) {
			context.update(PUBLISHER_LIST_ORDERS).set(PUBLISHER_LIST_ORDERS.STATUS, (byte) Status.PAUSED.getStatus())
					.where(PUBLISHER_LIST_ORDERS.PUBLISHER_LIST_ID.eq(publisherList.getId().intValue()))
					.and(PUBLISHER_LIST_ORDERS.ORDER_ID.in(existingOrders))
					.execute();
		}
	}
	
	/**
	 * 
	 * @param publisherList
	 * @param context
	 */
	protected static void persistPublishersCollection(PublisherList publisherList, DSLContext context) {
		assert publisherList.getPublishers() != null : "PublisherList must be initialized with publishers.";
		
		// Fetch all orders
		Result<Record1<Integer>> result = context.select(PUBLISHER_LIST_MEMBERS.PUBLISHER_ID)
				.from(PUBLISHER_LIST_MEMBERS)
				.where(PUBLISHER_LIST_MEMBERS.PUBLISHER_LIST_ID.eq(publisherList.getId().intValue()))
				.and(PUBLISHER_LIST_MEMBERS.STATUS.eq((byte) 1))
				.fetch();
		List<Integer> existingPublishers = new ArrayList<>();
		for (Record1<Integer> record : result) {
			existingPublishers.add(record.getValue(PUBLISHER_LIST_MEMBERS.PUBLISHER_ID));
		}
		// Insert Orders
		if (publisherList.getPublishers().size() > 0) {
			InsertSetMoreStep<PublisherListMembersRecord> step2 = null;
			for (int i = 0; i < publisherList.getPublishers().size(); i++) {
				if (step2 == null) {
					step2 = context.insertInto(PUBLISHER_LIST_MEMBERS)
							.set(PUBLISHER_LIST_MEMBERS.PUBLISHER_LIST_ID, publisherList.getId().intValue());
				} else {
					step2 = step2.newRecord()
							.set(PUBLISHER_LIST_ORDERS.PUBLISHER_LIST_ID, publisherList.getId().intValue());
				}
				step2 = step2.set(PUBLISHER_LIST_MEMBERS.PUBLISHER_ID, publisherList.getPublishers().get(i))
						.set(PUBLISHER_LIST_MEMBERS.STATUS, (byte) 1)
						.set(PUBLISHER_LIST_MEMBERS.CREATED_AT, DSL.currentTimestamp())
						.set(PUBLISHER_LIST_MEMBERS.UPDATED_AT, DSL.currentTimestamp());
				
				// Remove order from list of orders
				existingPublishers.remove(publisherList.getPublishers().get(i));
			}
			step2.onDuplicateKeyUpdate()
					.set(PUBLISHER_LIST_MEMBERS.STATUS, (byte) 1)
					.set(PUBLISHER_LIST_MEMBERS.UPDATED_AT, DSL.currentTimestamp())
					.execute();
		}
		
		// If there are remaining orders, set their status to 0
		if (existingPublishers.size() > 0) {
			context.update(PUBLISHER_LIST_MEMBERS).set(PUBLISHER_LIST_MEMBERS.STATUS, (byte) Status.PAUSED.getStatus())
					.where(PUBLISHER_LIST_MEMBERS.PUBLISHER_LIST_ID.eq(publisherList.getId().intValue()))
					.and(PUBLISHER_LIST_MEMBERS.PUBLISHER_ID.in(existingPublishers))
					.execute();
		}
	}

    
    public static Result<PublisherListOrdersRecord> getAllOrders(Long publisherListId) throws PonyException {
    	Connection conn = null;
    	
    	try {
    		conn = connectX();
        	DSLContext context = DSL.using(conn);
        	
        	return context.selectFrom(PUBLISHER_LIST_ORDERS)
        			.where(PUBLISHER_LIST_ORDERS.PUBLISHER_LIST_ID.eq(publisherListId.intValue()))
        			.and(PUBLISHER_LIST_ORDERS.STATUS.eq((byte) Status.OPEN.getStatus()))
        			.fetch();
    	} catch (NamingException e) {
    		String errMsg = "NamingException selecting publisherListOrders. This is likely a configuration problem and represents a permanent issue until fixed.";
    		LOG.warn(errMsg, e);
    		throw new PonyException(errMsg, e);
		} catch (SQLException e) {
			String errMsg = "SQLException selecting publisherLists.";
			LOG.warn(errMsg, e);
			throw new PonyException(errMsg, e);
		} finally {
    		close(conn);
    	}
    }
    
    public static Result<PublisherListMembersRecord> getAllPublishers(Long publisherListId) throws PonyException {
    	Connection conn = null;
    	
    	try {
    		conn = connectX();
    		DSLContext context = DSL.using(conn);
    		
    		return context.selectFrom(PUBLISHER_LIST_MEMBERS)
    				.where(PUBLISHER_LIST_MEMBERS.PUBLISHER_LIST_ID.eq(publisherListId.intValue()))
    				.and(PUBLISHER_LIST_MEMBERS.STATUS.eq((byte) Status.OPEN.getStatus()))
    				.fetch();
    	} catch (NamingException e) {
    		String errMsg = "NamingException selecting publisherListMembers. This is likely a configuration problem and represents a permanent issue until fixed.";
    		LOG.warn(errMsg, e);
    		throw new PonyException(errMsg, e);
		} catch (SQLException e) {
			String errMsg = "SQLException selecting publisherListMembers.";
			LOG.warn(errMsg, e);
			throw new PonyException(errMsg, e);
		} finally {
    		close(conn);
    	}
    }
}
