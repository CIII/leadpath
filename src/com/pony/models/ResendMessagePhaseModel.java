package com.pony.models;

import com.pony.email.Message;
import com.pony.email.ResendCandidate;
import com.pony.email.ResendMessagePhase;
import com.pony.email.ResendSequence;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 9/13/12
 * Time: 2:59 PM
 */
public class ResendMessagePhaseModel extends Model {
    protected ResendMessagePhaseModel(Long id) {
        super(id);
    }


    public static Map<Long, List<ResendCandidate>> findNewCandidates(Long resendSequenceId, String externalListId, int hoursBack) throws NamingException, SQLException {

        Map<Long, List<ResendCandidate>> candidates = new HashMap<Long, List<ResendCandidate>>();

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("select distinct up.id up_id, up.email, m.id message_id, pl.lead_type_id, if(o.id is null, 0, 1) opened, m.creative_id, m.host_id, a.publisher_id, a.id arrival_id from user_profiles up join arrivals a on a.user_profile_id = up.id join publisher_lists pl on pl.id = a.publisher_list_id join messages m on m.user_profile_id = up.id left join opens o on o.message_id = m.id left join resend_message_phases mp on mp.original_message_id = m.id and mp.resend_sequence_id = ? where pl.ext_list_id = ? and mp.id is null and a.validation_code = 0 and timestampdiff(HOUR, up.created_at, now()) < ?");
            stmt.setLong(1, resendSequenceId);
            stmt.setString(2, externalListId);
            stmt.setInt(3, hoursBack);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Long hostId = rs.getLong("host_id");
                List<ResendCandidate> list = candidates.get(hostId);
                if (list == null) {
                    list = new ArrayList<ResendCandidate>();
                    candidates.put(hostId, list);
                }

                list.add(ResendCandidate.create(rs.getLong("up_id"), rs.getString("email"), rs.getLong("message_id"), rs.getBoolean("opened"), rs.getLong("creative_id"), hostId, rs.getLong("publisher_id"), ResendMessagePhase.DAY_2, rs.getLong("lead_type_id"), rs.getLong("arrival_id")));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return candidates;
    }


    public static void update(ResendSequence sequence, ResendCandidate candidate, Message newMessage) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("update resend_message_phases set phase=?, message_id=?, updated_at=now() where resend_sequence_id=? and original_message_id=?");
            stmt.setLong(1, candidate.getPhase().getPhase());
            stmt.setLong(2, newMessage.getId());
            stmt.setLong(3, sequence.getId());
            stmt.setLong(4, candidate.getFirstMessageId());
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    /**
     * check weather or not the provided message is already in process for this sequence
     *
     * @param sequenceId
     * @param originalMessageid
     * @return
     */
    public static boolean inProcess(Long sequenceId, Long originalMessageid) {
        //TODO
        return false;
    }

    public static Long create(ResendSequence sequence, ResendCandidate candidate, Message message)
            throws NamingException, SQLException {
        Connection con = null;
        try {
            con = connectX();
            return create(con, sequence, candidate, message);
        }
        finally {
            close(con);
        }

    }

    /**
     * create a new message (representing a resend message), that can be passed on to the email service for transport to the end user
     *
     * @param con
     * @param sequence
     * @param message
     * @return
     */
    public static Long create(Connection con, ResendSequence sequence, ResendCandidate candidate, Message message)
            throws NamingException, SQLException {

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("insert into resend_message_phases(resend_sequence_id, phase, original_message_id, message_id, created_at, updated_at) values(?,?,?,?,now(), now())");
            stmt.setLong(1, sequence.getId());
            stmt.setInt(2, candidate.getPhase().getPhase());
            stmt.setLong(3, candidate.getFirstMessageId());
            stmt.setLong(4, message.getId());

            return executeWithLastId(stmt);
        }
        finally {
            close(stmt);
        }
    }

    /**
     * find all candidates that are in the sequence, and have a message in the phase that was sent via host_id
     *
     * @param con
     * @param sequence
     * @param hostId
     * @param phase
     * @return
     */
    public static List<ResendCandidate> find(Connection con, ResendSequence sequence, Long hostId, ResendMessagePhase.Phase phase) throws SQLException {

        List<ResendCandidate> candidates = new ArrayList<ResendCandidate>();

        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select distinct up.id up_id, up.email, m.id message_id, pl.lead_type_id, if(o.id is null, 0, 1) opened, m.creative_id, m.host_id, a.publisher_id, a.id arrival_id from user_profiles up join arrivals a on a.user_profile_id = up.id join publisher_lists pl on pl.id = a.publisher_list_id join messages m on m.user_profile_id = up.id left join opens o on o.message_id = m.id left join resend_message_phases mp on mp.original_message_id = m.id and mp.resend_sequence_id = ? where m.host_id = ? and mp.phase = ? and a.validation_code = 0  and timestampdiff(HOUR,mp.updated_at,now()) > ?");
            stmt.setLong(1, sequence.getId());
            stmt.setLong(2, hostId);
            stmt.setInt(3, phase.getPhase());
            stmt.setInt(4, phase.getHoursToPreviousPhase());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                candidates.add(ResendCandidate.create(rs.getLong("up_id"), rs.getString("email"), rs.getLong("message_id"), rs.getBoolean("opened"), rs.getLong("creative_id"), hostId, rs.getLong("publisher_id"), ResendMessagePhase.DAY_2, rs.getLong("lead_type_id"), rs.getLong("arrival_id")));
            }
        }
        finally {
            close(stmt);
        }

        return candidates;
    }

    public static void remove(Long sequenceId, Long originalMessageId) throws NamingException, SQLException {
        // delete the entry for the sequence and original message id
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("delete from resend_message_phases where resend_sequence_id=? and original_message_id=?");
            stmt.setLong(1, sequenceId);
            stmt.setLong(2, originalMessageId);

            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }
}
