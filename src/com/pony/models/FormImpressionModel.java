package com.pony.models;

import com.pony.form.Form;
import com.pony.form.FormImpression;
import com.pony.form.FormState;
import com.pony.lead.Arrival;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 2/7/13
 * Time: 10:21 AM
 */
public class FormImpressionModel extends Model {
    protected FormImpressionModel(Long id) {
        super(id);
    }

    //    public static FormImpression create(Form form, HttpServletRequest request) throws NamingException, SQLException {
//        Connection con = null;
//
//        try {
//            con = connectX();
//            return create(con, form, request);
//        }
//        finally {
//            close(con);
//        }
//    }
//
    public static FormImpression create(Connection con, Form form, FormState formState, HttpServletRequest request) throws SQLException {
        // Note: there is no arrival id at this point, so we'll ignore that for now
        Long formStepId = null;
        Long formStepGroupId = null;
        if (formState != null) {
            formStepId = formState.getCurrentFormStep().getId();
            formStepGroupId = formState.getCurrentFormStepGroup().getId();
        }

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("insert into form_impressions(form_id, form_step_id, form_step_group_id, impression_count, ip_address, user_agent, referrer, last_uuid, created_at, last_seen_at) values(?,?,?,1,?,?,?,?,now(),now()) ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id), impression_count=impression_count+1, last_seen_at=now()");
            stmt.setLong(1, form.getId());
            stmt.setLong(2, formStepId);
            stmt.setLong(3, formStepGroupId);
            stmt.setString(4, request.getRemoteAddr());
            stmt.setString(5, request.getHeader("User-Agent"));
            stmt.setString(6, request.getHeader("referer"));
            String uuid = FormImpression.newUUID().toString();
            stmt.setString(7, uuid);

            Long id = Model.executeWithLastId(stmt);
            return FormImpression.create(id, form.getId(), formStepId, formStepGroupId, request.getRemoteAddr(), null, 1, uuid, request.getHeader("User-Agent"), request.getHeader("referer"));
        }
        finally {
            close(stmt);
        }
    }

    public static FormImpression find(Long id) throws NamingException, SQLException {
        Connection con = null;

        try {
            con = connectX();
            return find(con, id);
        }
        finally {
            close(con);
        }
    }

    public static FormImpression find(Connection con, Long id) throws NamingException, SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, form_id, form_step_id, form_step_group_id, arrival_id, impression_count, ip_address, user_agent, referrer, last_uuid from form_impressions where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return FormImpression.create(rs.getLong("id"), rs.getLong("form_id"), rs.getLong("form_step_id"), rs.getLong("form_step_group_id"), rs.getString("ip_address"), rs.getLong("arrival_id"), rs.getInt("impression_count"), rs.getString("last_uuid"), rs.getString("user_agent"), rs.getString("referrer"));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    public static FormImpression createOrUpdate(Form form, FormState formState, HttpServletRequest request) throws NamingException, SQLException {

        Connection con = null;
        try {
            con = connectX();

            FormImpression fi = find(con, form, formState, request);

            if (fi == null) {
                fi = create(con, form, formState, request);
            }
            else {
                String uuid = fi.touch();
                update(con, fi, uuid);
            }

            return fi;
        }
        finally {
            close(con);
        }
    }

    private static void update(Connection con, FormImpression formImpression, String uuid) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("update form_impressions set last_seen_at = now(), last_uuid = ?, impression_count = impression_count+1 where id = ?");
            stmt.setString(1, uuid);
            stmt.setLong(2, formImpression.getId());
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
        }
    }

    /**
     * Link the impression to the arrival
     *
     * @param impression
     * @param arrival
     */
    public static void link(FormImpression impression, Arrival arrival) throws SQLException, NamingException {

        if (impression == null) {
            throw new IllegalArgumentException();
        }

        if (arrival == null) {
            return; //NOOP
        }

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = connectX();
            stmt = con.prepareStatement("update form_impressions set arrival_id = ? where ip_address = ? and form_id = ? and arrival_id is null");
            stmt.setLong(1, arrival.getId());
            stmt.setString(2, impression.getIpAddress());
            stmt.setLong(3, impression.getFormId());

            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    public static FormImpression find(Connection con, Form form, FormState formState, HttpServletRequest request) throws NamingException, SQLException {
        PreparedStatement stmt = null;

        StringBuilder sql = new StringBuilder();
        sql.append("select id, form_id, form_step_id, form_step_group_id, arrival_id, impression_count, ip_address, user_agent, referrer, last_uuid from form_impressions where form_id = ? and ip_address= ?");

        Long formStepId = null, formStepGroupId = null;
        if (formState != null) {
            formStepId = formState.getCurrentFormStep().getId();
            formStepGroupId = formState.getCurrentFormStepGroup().getId();
            sql.append(" and form_step_id = ? and form_step_group_id = ?");
        }

        try {
            stmt = con.prepareStatement(sql.toString());
            stmt.setLong(1, form.getId());
            stmt.setString(2, request.getRemoteAddr());

            if (formState != null) {
                stmt.setLong(3, formStepId);
                stmt.setLong(4, formStepGroupId);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return FormImpression.create(rs.getLong("id"), rs.getLong("form_id"), rs.getLong("form_step_id"), rs.getLong("form_step_group_id"), rs.getString("ip_address"), rs.getLong("arrival_id"), rs.getInt("impression_count"), rs.getString("last_uuid"), rs.getString("user_agent"), rs.getString("referrer"));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    public static FormImpression find(Form form, HttpServletRequest request) throws NamingException, SQLException {
        Connection con = null;

        try {
            con = connectX();
            return find(con, form, null, request);
        }
        finally {
            close(con);
        }
    }
}
