package com.pony.models;

import com.pony.email.Message;
import com.pony.email.ResendCandidate;
import com.pony.email.ResendMessageLog;
import com.pony.email.ResendSequence;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 9/19/12
 * Time: 5:26 PM
 */
public class ResendMessageLogModel extends Model {
    protected ResendMessageLogModel(Long id) {
        super(id);
    }

    public static ResendMessageLog create(ResendSequence sequence, ResendCandidate candidate, Message message, long delayMinutes) throws NamingException, SQLException {
        //TODO delay minutes
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("insert into resend_message_logs (resend_sequence_id, phase, original_message_id, message_id, created_at, delay_minutes) values(?,?,?,?, now(),?)");
            stmt.setLong(1, sequence.getId());
            stmt.setLong(2, candidate.getPhase().getPhase());
            stmt.setLong(3, candidate.getFirstMessageId());
            stmt.setLong(4, message.getId());
            stmt.setLong(5, delayMinutes);

            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }
}
