package com.pony.models;

import com.pony.PonyException;
import com.pony.publisher.Publisher;
import com.tapquality.db.tables.PublisherListOrders;
import com.tapquality.db.tables.records.PublisherListOrdersRecord;
import com.tapquality.db.tables.records.PublishersRecord;

import static com.tapquality.db.tables.Publishers.PUBLISHERS;
import static com.tapquality.db.tables.PublisherListOrders.PUBLISHER_LIST_ORDERS;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.InsertOnDuplicateStep;
import org.jooq.InsertSetMoreStep;
import org.jooq.InsertSetStep;
import org.jooq.InsertValuesStep1;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/10/11
 * Time: 8:35 PM
 */
public class PublisherModel extends Model {
	private static final Log LOG = LogFactory.getLog(PublisherModel.class);
	
    protected PublisherModel(Long id) {
        super(id);
    }

    public static Publisher find(Long id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select id, name, allow_duplicates, extended_validation from publishers where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Publisher.create(rs);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static Publisher findByNameAndPwd(String userName, String password) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select id, name, allow_duplicates, extended_validation from publishers where user_name=? and password=md5(?)");
            stmt.setString(1, userName);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Publisher.create(rs);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static Publisher findByDomainAndToken(String domain, String domainToken) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select id, name, allow_duplicates, extended_validation from publishers where domain_name=? and domain_token=md5(?)");
            stmt.setString(1, domain);
            stmt.setString(2, domainToken);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Publisher.create(rs);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static Publisher create(String name, String userName, String password, boolean allowDuplicates) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("insert ignore into publishers (name, user_name, password, allow_duplicates, created_at) values(?, ?, md5(?), ?, now())");
            stmt.setString(1, name);
            stmt.setString(2, userName);
            stmt.setString(3, password);
            stmt.setBoolean(4, allowDuplicates);

            Long id = executeWithLastId(stmt);
            if (id != null) {
                return Publisher.create(id, name, allowDuplicates, false);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;

    }
    
    public static List<Map<String, String>> getAllPublishers(boolean paused) throws PonyException {
    	Connection conn = null;
    	
    	List<Map<String, String>> returnValue = new ArrayList<>();
    	
    	try {
    		conn = connectX();
    		DSLContext context = DSL.using(conn);
    		ResultSet rs = context.select(PUBLISHERS.ID, PUBLISHERS.NAME, PUBLISHERS.DOMAIN_NAME, PUBLISHERS.EXTENDED_VALIDATION, PUBLISHERS.ALLOW_DUPLICATES, PUBLISHERS.USER_NAME)
    			.from(PUBLISHERS)
    			.orderBy(PUBLISHERS.NAME.asc())
    			.fetchResultSet();
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
    					object.put(column, value);
    				}
    			}
    			returnValue.add(object);
    		}
    	} catch (NamingException e) {
    		String errMsg = "NamingException retrieving publishers. This is likely a configuration problem and represents a permanent issue until fixed.";
    		LOG.warn(errMsg, e);
    		throw new PonyException(errMsg, e);
		} catch (SQLException e) {
			String errMsg = "SQLException retrieving advertisers.";
			LOG.warn(errMsg, e);
			throw new PonyException(errMsg, e);
		} finally {
			close(conn);
		}
    	
    	return returnValue;
    }
    
    public static PublishersRecord persistPublisher(Publisher publisher) throws PonyException {
    	Connection conn = null;
    	
    	PublishersRecord returnValue;
    	boolean isUpdate = publisher.getId() == 0 ? false : true;
    	try {
    		conn = connectX();
    		DSLContext context = DSL.using(conn);
    		if (isUpdate) {
    			UpdateSetMoreStep<PublishersRecord> queryStep = context.update(PUBLISHERS)
    					.set(PUBLISHERS.NAME, publisher.getName())
    					.set(PUBLISHERS.DOMAIN_NAME, publisher.getDomain())
    					.set(PUBLISHERS.USER_NAME, publisher.getUsername())
    					.set(PUBLISHERS.ALLOW_DUPLICATES, (byte) (publisher.allowDuplicates() ? 1 : 0))
    					.set(PUBLISHERS.EXTENDED_VALIDATION, (byte) (publisher.isExtendedValidation() ? 1 : 0));
    			if(publisher.getPassword() != null) {
    				queryStep = queryStep.set(PUBLISHERS.PASSWORD, DSL.md5(publisher.getPassword()));
    			}
    			if(publisher.getDomainToken() != null) {
    				queryStep = queryStep.set(PUBLISHERS.DOMAIN_TOKEN, DSL.md5(publisher.getDomainToken()));
    			}
    			queryStep.where(PUBLISHERS.ID.eq(publisher.getId().intValue()))
    					.execute();
    			returnValue = context.selectFrom(PUBLISHERS)
    					.where(PUBLISHERS.ID.eq(publisher.getId().intValue()))
    					.fetchOne();
    		} else {
    			if (publisher.getPassword() == null) {
    				throw new IllegalArgumentException("Password may not be null for new publishers.");
    			}
    			InsertSetStep<PublishersRecord> queryStep = context.insertInto(PUBLISHERS);
    			InsertOnDuplicateStep<PublishersRecord> nextStep;
    			if(publisher.getDomainToken() != null) {
    				nextStep = queryStep.columns(PUBLISHERS.NAME, PUBLISHERS.DOMAIN_NAME, PUBLISHERS.USER_NAME, PUBLISHERS.ALLOW_DUPLICATES, PUBLISHERS.EXTENDED_VALIDATION, PUBLISHERS.PASSWORD, PUBLISHERS.DOMAIN_TOKEN)
    						.values(publisher.getName(), publisher.getDomain(), publisher.getUsername(), (byte) (publisher.allowDuplicates() ? 1 : 0), (byte) (publisher.isExtendedValidation() ? 1 : 0), DSL.md5(publisher.getPassword()).toString(), DSL.md5(publisher.getDomainToken()).toString());
    			} else {
    				nextStep = queryStep.columns(PUBLISHERS.NAME, PUBLISHERS.DOMAIN_NAME, PUBLISHERS.USER_NAME, PUBLISHERS.ALLOW_DUPLICATES, PUBLISHERS.EXTENDED_VALIDATION, PUBLISHERS.PASSWORD)
    						.values(publisher.getName(), publisher.getDomain(), publisher.getUsername(), (byte) (publisher.allowDuplicates() ? 1 : 0), (byte) (publisher.isExtendedValidation() ? 1 : 0), DSL.md5(publisher.getPassword()).toString());
    			}
    			returnValue = nextStep.returning(PUBLISHERS.ID, PUBLISHERS.NAME, PUBLISHERS.DOMAIN_NAME, PUBLISHERS.USER_NAME, PUBLISHERS.ALLOW_DUPLICATES, PUBLISHERS.EXTENDED_VALIDATION)
    					.fetchOne();
        		// TODO: Check for errors.
    		}
    		    		
    		// TODO: Check for nulls better.
    		
    	} catch (NamingException e) {
    		String errMsg = "NamingException persisting a publisher. This is likely a configuration problem and represents a permanent issue until fixed.";
			LOG.warn(errMsg, e);
			throw new PonyException(errMsg, e);
		} catch (SQLException e) {
			String errMsg = "SQLException persisting a publisher.";
			LOG.warn(errMsg, e);
			throw new PonyException(errMsg, e);
		} finally {
			close(conn);
		}
    	
    	return returnValue;
    }
}
