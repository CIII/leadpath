package com.pony.models;

import com.pony.livehttp.Redirect;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 11/28/12
 * Time: 5:12 PM
 */
public class RedirectModel extends Model {
    protected RedirectModel(Long id) {
        super(id);
    }

    public static Redirect find(Long id) throws SQLException, NamingException {
        Connection con = null;
        try {
            con = connectX();
            return find(con, id);
        }
        finally {
            close(con);
        }
    }

    public static Redirect find(Connection con, Long id) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, click_source_id, click_target_id, destination_path, destination_query from redirects where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Redirect.create(rs.getLong("id"), rs.getLong("click_source_id"), rs.getLong("click_target_id"), rs.getString("destination_path"), rs.getString("destination_query"));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    public static Long create(Redirect redirect) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            // TODO: add referrer_domain_id,
            con = connectX();
            stmt = con.prepareStatement("insert into redirects(click_source_id, click_target_id, ip_address, user_agent, referrer, destination_path, destination_query, created_at) values(?,?,?,?,?,?,?,now())");
            stmt.setLong(1, redirect.getClickSourceId());
            stmt.setLong(2, redirect.getClickTargetId());
            stmt.setString(3, redirect.getIpAddress());
            stmt.setString(4, redirect.getUserAgent());
            stmt.setString(5, redirect.getReferrer());
            stmt.setString(6, redirect.getDestinationPath());
            stmt.setString(7, redirect.getDestinationQuery());
            return executeWithLastId(stmt);
        }
        finally {
            close(stmt);
            close(con);
        }
    }
}
