package com.pony.models;

import com.pony.email.Message;
import com.pony.email.sendgrid.SendGridException;
import com.pony.lead.UserProfile;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PonyLeads 2012
 * User: martin
 * Date: 7/3/12
 * Time: 3:02 PM
 */
public class MessageModel extends Model {
	private static final Log LOG = LogFactory.getLog(MessageModel.class);
	
    protected MessageModel(Long id) {
        super(id);
    }

    public static Message create(Long userProfileId, Long hostId, Long creativeId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("insert into messages(user_profile_id, host_id, creative_id, created_at) values( ?,?,?,now())");
            stmt.setLong(1, userProfileId);
            stmt.setLong(2, hostId);
            stmt.setLong(3, creativeId);

            Long id = executeWithLastId(stmt);
            return Message.create(id, userProfileId, hostId, creativeId);
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    public static void link(Message message, Long externalMessageId) throws NamingException, SQLException {
        if (message == null || message.getId() == null) {
            throw new IllegalArgumentException("no valid message (id) provided:" + message);
        }

        if (externalMessageId == null) {
            throw new IllegalArgumentException("no valid EXTERNAL message (id) provided:" + message);
        }

        LOG.info("linking messageId=" + message.getId() + " to " + externalMessageId);
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();

            stmt = con.prepareStatement("update messages set external_id = ?, status=?, updated_at=now() where id = ?");
            stmt.setLong(1, externalMessageId);
            stmt.setInt(2, Message.SENT);
            stmt.setLong(3, message.getId());

            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    /**
     * count messages for the host id for today
     *
     * @param hostId
     * @return
     */
    public static long countForHostToday(Long hostId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();

            stmt = con.prepareStatement("select count(*) cnt from messages where host_id = ? and date(created_at) = curdate()");
            stmt.setLong(1, hostId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("cnt");
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return 0L;
    }

    public static void processEvent(Long messageId, int status) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("update messages set status=?, updated_at=now() where id = ?");
            stmt.setInt(1, status);
            stmt.setLong(2, messageId);
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    public static void bounced(UserProfile userProfile, Long messageId, int code, String reason) throws NamingException, SQLException, SendGridException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select host_id from messages where id = ?");
            stmt.setLong(1, messageId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Long hostId = rs.getLong("host_id");
                close(stmt);

                stmt = con.prepareStatement("insert into bounces (user_profile_id, message_id, host_id, status_code, message, created_at) values(?,?,?,?,?,now())");
                stmt.setLong(1, userProfile.getId());
                stmt.setLong(2, messageId);
                stmt.setLong(3, hostId);
                stmt.setInt(4, code);
                stmt.setString(5, reason == null ? null : reason.substring(0, reason.length() > 254 ? 254 : reason.length()));
                stmt.executeUpdate();
            }
            else {
                // error !
                throw new SendGridException("Bounced: message not found for id=" + messageId);
            }
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    public static void clicked(Long messageId, String userAgent, String ipAddress) throws SendGridException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();

            // need to look at opens first (create with open count = 0 and do not update count!)
            opened(con, messageId, userAgent, ipAddress, 0);

            stmt = con.prepareStatement("insert into clicks (message_id, click_count, created_at) values (?, 1, now()) ON DUPLICATE KEY UPDATE click_count = click_count+1");
            stmt.setLong(1, messageId);
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    private static void opened(Connection con, Long messageId, String userAgent, String ipAddress, int openCount) throws SQLException {
        PreparedStatement stmt = null;
        try {
            // need to look at opens first (create with open count = 0 and do not update count!)
            stmt = con.prepareStatement("insert into opens (message_id, ip_address, user_agent, open_count, created_at) values(?,?,?,?,now()) ON DUPLICATE KEY UPDATE open_count = open_count+1");
            stmt.setLong(1, messageId);
            stmt.setString(2, ipAddress);
            stmt.setString(3, (userAgent != null && userAgent.length() > 254) ? userAgent.substring(0, 254) : userAgent);
            stmt.setInt(4, openCount);

            stmt.executeUpdate();
        }
        finally {
            close(stmt);
        }
    }

    public static void opened(Long messageId, String userAgent, String ipAddress) throws NamingException, SQLException {
        Connection con = null;
        try {
            con = connectX();
            opened(con, messageId, userAgent, ipAddress, 1);
        }
        finally {
            close(con);
        }
    }

    public static void complained(UserProfile userProfile, Long messageId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            // need to look at opens first (create with open count = 0 and do not update count!)
            stmt = con.prepareStatement("insert into complaints(user_profile_id, message_id, created_at) values(?,?, now())");
            stmt.setLong(1, userProfile.getId());
            stmt.setLong(2, messageId);
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    /**
     * Ensure that the provided message id was sent to UserProfile (also provided)
     * This is a utility for functions such as unsubscribe to ensure nobody can just run through numbers and unsubscribe
     *
     * @param userProfile
     * @param messageId
     * @return
     */
    public static boolean validateMessage(UserProfile userProfile, Long messageId) throws NamingException, SQLException {

        if (messageId == null || userProfile == null || userProfile.getId() == null) {
            throw new IllegalArgumentException("invalid params: messageId=" + messageId + "; userProfile=" + userProfile);
        }

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select user_profile_id from messages where id=?");
            stmt.setLong(1, messageId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return userProfile.getId().equals(rs.getLong("user_profile_id"));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return false;
    }
}
