package com.pony.models;

import com.pony.livehttp.ClickSource;

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
public class ClickSourceModel extends Model {
    protected ClickSourceModel(Long id) {
        super(id);
    }

    public static ClickSource find(Long id) throws SQLException, NamingException {
        Connection con = null;
        try {
            con = connectX();
            return find(con, id);
        }
        finally {
            close(con);
        }
    }

    public static ClickSource find(Connection con, Long id) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, publisher_id, name, pixel_back_url from click_sources where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return ClickSource.create(rs.getLong("id"), rs.getLong("publisher_id"), rs.getString("name"), rs.getString("pixel_back_url"));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }
}
