package com.pony.models;

import com.pony.livehttp.PixelFire;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 11/28/12
 * Time: 5:33 PM
 */
public class PixelFireModel extends Model {
    protected PixelFireModel(Long id) {
        super(id);
    }

    public static PixelFire find(String id) throws SQLException, NamingException {
        Connection con = null;
        try {
            con = connectX();
            return find(con, id);
        }
        finally {
            close(con);
        }
    }

    public static PixelFire find(Connection con, String id) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, redirect_id, pixel_type, counter, ip_address, user_agent, referrer from pixel_fires where id = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return PixelFire.create(rs.getLong("id"), rs.getLong("redirect_id"), rs.getInt("pixel_type"), rs.getInt("counter"), rs.getString("ip_address"), rs.getString("user_agent"), rs.getString("referrer"));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    public static Long create(PixelFire pixelFire) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("insert into pixel_fires(redirect_id, pixel_type, ip_address, user_agent, referrer, created_at) values(?,?,?,?,?,now())  ON DUPLICATE KEY UPDATE counter = counter +1");
            stmt.setLong(1, pixelFire.getRedirectId());
            stmt.setInt(2, pixelFire.getPixelType());
            stmt.setString(3, pixelFire.getIpAddress());
            stmt.setString(4, pixelFire.getUserAgent());
            stmt.setString(5, pixelFire.getReferrer());

            return executeWithLastId(stmt);
        }
        finally {
            close(stmt);
            close(con);
        }
    }
}
