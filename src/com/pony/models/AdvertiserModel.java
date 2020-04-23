package com.pony.models;

import com.pony.PonyException;
import com.pony.advertiser.Advertiser;
import com.pony.advertiser.Io;
import com.pony.callcenter.Broker;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 10/4/12
 * Time: 3:54 PM
 */
public class AdvertiserModel extends Model {
	private static final Log LOG = LogFactory.getLog(AdvertiserModel.class);
	
    protected AdvertiserModel(Long id) {
        super(id);
    }

    public static Advertiser find(Long id) throws SQLException, NamingException {
        Connection con = null;
        try {
            con = connectX();
            return find(con, id);
        }
        finally {
            close(con);
        }
    }

    public static Advertiser find(Connection con, Long id) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, name from advertisers where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Advertiser.create(rs.getLong("id"), rs.getString("name"));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    public static Advertiser findByName(String name) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select id, name from advertisers where name = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Advertiser.create(rs.getLong("id"), rs.getString("name"));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static Broker findBrokerForLead(Long advertiserId, String state) throws NamingException, SQLException {

        if (advertiserId == null || state == null) {
            return null;
        }

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select b.id, first_name, last_name, email, phone_number, broker_id from callcenter_brokers b join state_brokers sb on sb.callcenter_broker_id = b.id join states s on s.id = sb.state_id where b.advertiser_id = ? and s.state_code = ?");
            stmt.setLong(1, advertiserId);
            stmt.setString(2, state);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Broker.create(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("phone_number"), rs.getString("broker_id"));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }
    
    public static Map<String, String> getWithOrder(Long advertiserId, Long orderId) throws PonyException {
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	Map<String, String> returnValue = new HashMap<>();
    	
    	try {
    		conn = connectX();
    		String query = "SELECT advertisers.*, orders.* FROM advertisers JOIN orders ON advertisers.id = orders.advertiser_id WHERE advertisers.id = ?";
    		if (orderId != null) {
    			query += " AND orders.id = ?";
    		}
    		stmt = conn.prepareStatement(query);
    		stmt.setLong(1, advertiserId);
    		if (orderId != null) {
    			stmt.setLong(2, orderId);
    		}
    		ResultSet rs = stmt.executeQuery();
    		
    		if (rs.next()) {
    			ResultSetMetaData metadata = rs.getMetaData();
    			int columnCount = metadata.getColumnCount();
    			for (int i = 1; i <= columnCount; i++) {
    				String column = metadata.getColumnName(i);
    				String value = rs.getString(i);
    				if (returnValue.get(column) == null) {
    					returnValue.put(column, value);
    				} else {
    					returnValue.put("order_" + column, value);
    				}
    			}
    		}
    	} catch (NamingException e) {
    		throw new PonyException("NamingException retrieving advertiser " + advertiserId + ".", e);
    	} catch (SQLException e) {
    		throw new PonyException("SQLException retrieving advertier " + advertiserId + ".", e);
    	} finally {
    		close(stmt);
    		close(conn);
    	}
    	
    	return returnValue;
    }
    
    public static List<Map<String, String>> getAllWithOrders(boolean deleted) throws PonyException {
    	Connection con = null;
    	PreparedStatement stmt = null;
    	List<Map<String, String>> returnValue = new ArrayList<>();
    	
    	try {
    		con = connectX();
    		stmt = con.prepareStatement("SELECT advertisers.*, orders.* FROM advertisers JOIN orders ON advertisers.id = orders.advertiser_id WHERE status = 1 OR status = ?");
    		if (deleted) {
    			stmt.setInt(1, 0);
    		} else {
    			stmt.setInt(1,  1);
    		}
    		ResultSet rs = stmt.executeQuery();
    		
    		while (rs.next()) {
    			ResultSetMetaData metadata = rs.getMetaData();
    			int columnCount = metadata.getColumnCount();
    			Map<String, String> object = new HashMap<>();
    			for (int i = 1; i <= columnCount; i++) {
    				String column = metadata.getColumnName(i);
    				String value = rs.getString(i);
    				if (object.get(column) == null) {
    					object.put(column, value);
    				} else {
    					object.put("order_" + column, value);
    				}
    			}
    			returnValue.add(object);
    		}
    	} catch (NamingException e) {
    		throw new PonyException("NamingException retrieving advertisers.", e);
    	} catch (SQLException e) {
    		throw new PonyException("SQLException retrieving advertiers.", e);
    	} finally {
    		close(stmt);
    		close(con);
    	}
    	
    	return returnValue;
    }

    public static void toggleActivation(Long advertiserId) throws PonyException {
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	
    	try {
    		conn = connectX();
    		stmt = conn.prepareStatement("UPDATE orders SET status = !status WHERE advertiser_id = ?");
    		stmt.setLong(1, advertiserId);
    		stmt.execute();
    		if(stmt.getUpdateCount() != 1) {
    			LOG.warn("" + stmt.getUpdateCount() + " rows were updated. This is unexpected.");
    		}
    	} catch (NamingException e) {
    		throw new PonyException("NamingException toggling the activation of advertiser " + advertiserId);
    	} catch (SQLException e) {
    		throw new PonyException("SQLException toggling the activation of advertiser " + advertiserId);
    	} finally {
    		close(stmt);
    		close(conn);
    	}
    }
    
    public static Map<String, String> persistAdvertiser(Advertiser advertiser, Io io) throws PonyException {
    	Connection conn = null;
    	PreparedStatement advertiserStatement = null;
    	PreparedStatement orderStatement = null;

    	boolean isUpdate = advertiser.getId() == 0 ? false : true;
    	try {
    		conn = connectX();
    		if (isUpdate) {
    			advertiserStatement = conn.prepareStatement("UPDATE advertisers SET name=? WHERE id = ?");
    		} else {
    			advertiserStatement = conn.prepareStatement("INSERT INTO advertisers (name, created_at, updated_at) VALUES(?, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());", Statement.RETURN_GENERATED_KEYS);

    		}
    		advertiserStatement.setString(1, advertiser.getName());
    		if (isUpdate) {
    			advertiserStatement.setLong(2, advertiser.getId());
    		}
    		advertiserStatement.execute();
    		if(advertiserStatement.getUpdateCount() != 1) {
    			// TODO: Not sure if I should abandon the effort if this is not 1.
    		}
    		
    		Long advertiserId;
    		if(!isUpdate) {
        		ResultSet set = advertiserStatement.getGeneratedKeys();
    			if(set.next()) {
        			advertiserId = set.getLong(1);
    			} else {
        			throw new PonyException("Unable to retrieve the created advertiser's ID. Aborting before creating the order.");
    			}
    		} else {
    			advertiserId = advertiser.getId();
    		}
    		
    		if(isUpdate) {
    			orderStatement = conn.prepareStatement("UPDATE orders SET code=?, advertiser_id=?, lead_type_id=?, vpl=?, source_id=?, is_exclusive=?, status=?, cap_daily=?, cap_monthly=?, cap_total=?, pixel_id=?, target_url=?, weight=?, email=? WHERE id = ?");
    		} else {
    			orderStatement = conn.prepareStatement("INSERT INTO orders (code, advertiser_id, lead_type_id, vpl, source_id, is_exclusive, status, cap_daily, cap_monthly, cap_total, pixel_id, target_url, weight, email, created_at, updated_at)" +
    					" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());", Statement.RETURN_GENERATED_KEYS);
    		}
    		orderStatement.setString(1, io.getCode());
    		orderStatement.setLong(2, advertiserId);
    		orderStatement.setLong(3, io.getLeadTypeId());
    		orderStatement.setBigDecimal(4, io.getVpl());
    		orderStatement.setString(5, io.getSourceId());
    		orderStatement.setBoolean(6, io.isExclusive());
    		orderStatement.setInt(7, io.getStatus());
    		orderStatement.setLong(8, io.getCapDaily());
    		orderStatement.setLong(9,  io.getCapMonthly());
    		orderStatement.setLong(10, io.getCapTotal());
    		orderStatement.setLong(11, io.getPixelId());
    		orderStatement.setString(12,  io.getTargetUrl());
    		orderStatement.setInt(13, io.getWeight());
    		orderStatement.setString(14, io.getEmail());
    		if (isUpdate) {
    			orderStatement.setLong(15, io.getId());
    		}
    		orderStatement.execute();
    		if(orderStatement.getUpdateCount() != 1) {
    			throw new PonyException("Failure to persist the order associated with the advertiser. Modified " + orderStatement.getUpdateCount() + " orders");
    		}
    		Long orderId;
    		if(!isUpdate) {
        		ResultSet set = orderStatement.getGeneratedKeys();
        		if(set.next()) {
        			orderId = set.getLong(1);
        		} else {
        			throw new PonyException("Unable to retrieve the persisted order's ID. Aborting, though the advertiser and order has been created.");
        		}
    		} else {
    			orderId = io.getId();
    		}

    		return AdvertiserModel.getWithOrder(advertiserId, orderId);
    	} catch (NamingException e) {
    		throw new PonyException("NamingException creating the advertiser " + advertiser.getId(), e);
    	} catch (SQLException e) {
    		throw new PonyException("SQLException creating the advertiser " + advertiser.getId(), e);
    	} finally {
    		close(advertiserStatement);
    		close(orderStatement);
    		close(conn);
    	}
    }
}
