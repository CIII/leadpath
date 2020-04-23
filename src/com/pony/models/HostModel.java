package com.pony.models;

import com.pony.email.Host;
import com.pony.email.SmtpProvider;
import com.pony.publisher.Status;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Host is a physical entity to handle the message transport. A host belongs to one and only one SmtpProvider.
 * <p/>
 * PonyLeads 2012.
 * User: martin
 * Date: 7/3/12
 * Time: 3:57 PM
 */
public class HostModel extends Model {
    protected HostModel(Long id) {
        super(id);
    }

    public static SmtpProvider getSmtpProvider(Long hostId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select smtp.name, h.smtp_host_name, h.smtp_auth_user, h.smtp_auth_pwd, h.smtp_port from smtp_providers smtp join hosts h on h.smtp_provider_id = smtp.id where h.id = ?");
            stmt.setLong(1, hostId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return SmtpProvider.create(hostId, rs.getString("name"), rs.getString("smtp_host_name"), rs.getString("smtp_auth_user"), rs.getString("smtp_auth_pwd"), rs.getInt("smtp_port"));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static Host find(Long id) throws NamingException, SQLException {
        Connection con = null;

        try {
            con = connectX();
            return find(con, id);
        }
        finally {
            close(con);
        }
    }

    public static Host find(Connection con, Long id) throws NamingException, SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, smtp_provider_id, domain_name, ip_address, status, max_sends_daily from hosts where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Host.create(rs);
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    public static List<Host> findByStatus(Connection con, Status status) throws SQLException {
        List<Host> hosts = new ArrayList<Host>();

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, smtp_provider_id, domain_name, ip_address, status, max_sends_daily from hosts where status = ?");
            stmt.setInt(1, status.getStatus());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                hosts.add(Host.create(rs));
            }
        }
        finally {
            close(stmt);
        }

        return hosts;
    }

    public static List<Host> findByStatus(Status status) throws SQLException, NamingException {
        Connection con = null;
        try {
            con = connectX();
            return findByStatus(con, status);
        }
        finally {
            close(con);
        }
    }

    public static Map<Long, Host.SendCount> getHostCounts(Date startTime) throws NamingException, SQLException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Map<Long, Host.SendCount> counts = new HashMap<Long, Host.SendCount>();

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select host_id, count(*) cnt from messages where created_at > ? group by host_id");
            stmt.setString(1, df.format(startTime));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                counts.put(rs.getLong("host_id"), Host.createSendCount(rs.getLong("host_id"), rs.getLong("cnt")));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return counts;
    }
}
