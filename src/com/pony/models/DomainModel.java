package com.pony.models;

import com.pony.livehttp.Domain;

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
public class DomainModel extends Model {
    protected DomainModel(Long id) {
        super(id);
    }

    public static Domain find(String id) throws SQLException, NamingException {
        Connection con = null;
        try {
            con = connectX();
            return find(con, id);
        }
        finally {
            close(con);
        }
    }

    public static Domain find(Connection con, String id) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, domain_label, top_level_domain from domains where id = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Domain.create(rs.getLong("id"), rs.getString("domain_label"), rs.getString("top_level_domain"));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

}
