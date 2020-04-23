package com.pony.models;

import com.pony.email.EmailTargetQueue;
import com.pony.publisher.PublisherList;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 11/26/13
 * Time: 3:42 PM
 */
public class EmailTargetQueueModel extends Model {

    private final static String COLUMNS = "id, name, from_publisher_list_id, to_publisher_list_id, max_arrival_id, status, open_within_days, send_frequency_days";

    protected EmailTargetQueueModel(Long id) {
        super(id);
    }

    public static EmailTargetQueue find(PublisherList toPublisherList) throws SQLException, NamingException {
        Connection con = null;
        try {
            con = connectX();
            return find(con, toPublisherList.getId());
        }
        finally {
            close(con);
        }
    }

    public static EmailTargetQueue find(Connection con, Long toPublisherListId) throws SQLException {
        PreparedStatement stmt = null;
        try {

            stmt = con.prepareStatement("select " + COLUMNS + " from email_target_queues where to_publisher_list_id=?");
            stmt.setLong(1, toPublisherListId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return EmailTargetQueue.create(rs.getLong("id"), rs.getString("name"), rs.getLong("from_publisher_list_id"), rs.getLong("to_publisher_list_id"), rs.getLong("max_arrival_id"), rs.getInt("status"), rs.getInt("open_within_days"), rs.getInt("send_frequency_days"));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    public static void updateMaxArrivalId(EmailTargetQueue queue, Long maxArrivalId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("update email_target_queues set max_arrival_id=?, updated_at=now() where id=?");
            stmt.setLong(1, maxArrivalId);
            stmt.setLong(2, queue.getId());
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }
}
