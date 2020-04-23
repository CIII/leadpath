package com.pony.models;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/12/11
 * Time: 11:35 PM
 */
public class AdvertiserWriterModel extends Model {
    protected AdvertiserWriterModel(Long id) {
        super(id);
    }

    public static String findClassName(Long leadTypeId, Long advertiserId) throws NamingException, SQLException {
        // read the class name to load for this advertiser and lead type
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select class_name from advertiser_writers where lead_type_id = ? and advertiser_id = ?");
            stmt.setLong(1, leadTypeId);
            stmt.setLong(2, advertiserId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("class_name");
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }
}
