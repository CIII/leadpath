package com.pony.models;

import com.pony.livehttp.ClickTarget;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 11/28/12
 * Time: 5:11 PM
 */
public class ClickTargetModel extends Model {
    protected ClickTargetModel(Long id) {
        super(id);
    }

    public static ClickTarget find(Long id) throws SQLException, NamingException {
        Connection con = null;
        try {
            con = connectX();
            return find(con, id);
        }
        finally {
            close(con);
        }
    }

    public static ClickTarget find(Connection con, Long id) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, advertiser_id, destination_url, name from click_targets where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return ClickTarget.create(rs.getLong("id"), rs.getLong("advertiser_id"), rs.getString("destination_url"), rs.getString("name"));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

}
