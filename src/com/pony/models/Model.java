package com.pony.models;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/10/11
 * Time: 8:31 PM
 * <p/>
 * root@mycashfriend.com
 * ip = 184.106.202.120
 * password = ponycomputer
 * <p/>
 * mysql root password = ponycomputer
 * mycashfriend_production
 */
public abstract class Model {
	private static final Log LOG = LogFactory.getLog(Model.class);
	
    public static final String PONY_DB_REF = "/jdbc/pony";
    public static final String REPORT_DB_REF = "/jdbc/report";

    private static final String javaxDb = "java:comp/env";
    
    protected static final String NAMING_EX_MSG = "Exception establishing connection to the database. This is likely a configuration error, and will inhibit the operation of the entire service.";
    protected static final String CONNECTION_EX_MSG = "Exception connecting to database. Likely a connection error. It is likely this represents an infrastructure problem.";

    private final Long id;

    protected Model(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static Connection connectX() throws NamingException, SQLException {

    	return connectX(PONY_DB_REF);
    }
    
    public static Connection connectReport() throws NamingException, SQLException {
    	return connectX(REPORT_DB_REF);
    }

    public static Connection connectX(String poolName) throws NamingException, SQLException {
        Context ctx = new InitialContext();
        javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup(javaxDb + poolName);

        return ds.getConnection();
    }

    public static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            }
            catch (SQLException e) {
                // ignore
                LOG.error(e);
            }
        }
    }

    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (SQLException e) {
                // ignore
                LOG.error(e);
            }
        }
    }

    protected static Long executeWithLastId(PreparedStatement stmt) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            if (stmt.executeUpdate() > 0) {
                // read the inserted row to get the new id
                pstmt = stmt.getConnection().prepareStatement("SELECT LAST_INSERT_ID()");
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }
        finally {
            close(pstmt);
        }
        return -1L;
    }

    public static Map<String, String> parseToMap(String source) {
        Map<String, String> map = new HashMap<String, String>();

        if (source != null) {
            String[] tokens = source.split(";");
            for (String token : tokens) {
                String[] kv = token.split("=");
                if (kv.length == 2) {
                    map.put(kv[0].trim(), kv[1].trim());
                }
            }
        }

        return map;
    }

    public static String setToCsv(Set<Long> ids) {
        StringBuilder s = new StringBuilder();
        boolean first = true;
        for (Long id : ids) {
            if (!first) {
                s.append(",");
            }
            else {
                first = false;
            }
            s.append(id);
        }

        return s.toString();
    }

    public static Set<Long> csvToSet(String csv) {
        Set<Long> ids = new HashSet<Long>();
        String[] tokens = csv.split(",");
        for (String id : tokens) {
            ids.add(Long.valueOf(id.trim()));
        }

        return ids;
    }
}
