package com.pony.models;

import com.pony.livehttp.ClickSource;
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
 * Time: 7:47 PM
 */
public class ClickRuleModel extends Model {
    protected ClickRuleModel(Long id) {
        super(id);
    }


    public static ClickTarget findClickTargetForSource(ClickSource clickSource) throws NamingException, SQLException {
        Connection con = null;
        try {
            con = connectX();
            return findClickTargetForSource(con, clickSource);
        }
        finally {
            close(con);
        }
    }

    public static ClickTarget findClickTargetForSource(Connection con, ClickSource clickSource) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select ct.id from click_targets ct join click_rules cr on cr.click_target_id = ct.id where cr.click_source_id = ? order by rand() limit 1");
            stmt.setLong(1, clickSource.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return ClickTargetModel.find(con, rs.getLong("id"));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }
}
