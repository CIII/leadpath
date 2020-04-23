package com.pony.models;

import com.pony.advertiser.Offer;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 7/10/12
 * Time: 11:05 AM
 */
public class OfferModel extends Model {
    protected OfferModel(Long id) {
        super(id);
    }

    public static Offer find(Connection con, Long id) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, name, advertiser_id, lead_type_id, target_url, unsubscribe_url, click_url, from_address, from_personal, bcc_address, status from offers where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Offer.create(rs);
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    public static Offer find(Long id) throws SQLException, NamingException {
        Connection con = null;

        try {
            con = connectX();
            return find(con, id);
        }
        finally {
            close(con);
        }
    }
}
