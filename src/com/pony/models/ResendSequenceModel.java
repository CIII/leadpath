package com.pony.models;

import com.pony.email.ResendSequence;
import com.pony.publisher.Status;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 9/13/12
 * Time: 2:59 PM
 */
public class ResendSequenceModel extends Model {

    public ResendSequenceModel(Long id) {
        super(id);
    }

    public static ResendSequence findSequence(String name) throws NamingException, SQLException {
        Connection con = null;

        try {
            con = connectX();
            return findSequence(con, name);
        }
        finally {
            close(con);
        }
    }

    public static ResendSequence findSequence(Connection con, String name) throws NamingException, SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("select id, name, status from resend_sequences where name = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return ResendSequence.create(rs.getLong("id"), name, Status.parse(rs.getInt("status")));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    public static ResendSequence findOrCreateSequence(String name) throws NamingException, SQLException {
        Connection con = null;

        try {
            con = connectX();
            ResendSequence s = findSequence(con, name);
            if (s != null) {
                return s;
            }

            return createSequence(con, name);
        }
        finally {
            close(con);
        }
    }

    private static ResendSequence createSequence(Connection con, String name) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("insert into resend_sequences (name, created_at) values (?, now())");
            stmt.setString(1, name);
            Long id = executeWithLastId(stmt);
            if (id != null) {
                return ResendSequence.create(id, name, Status.parse(0));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

}
