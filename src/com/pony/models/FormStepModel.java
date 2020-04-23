package com.pony.models;

import com.pony.form.Form;
import com.pony.form.FormStep;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 2/20/13
 * Time: 12:59 PM
 */
public class FormStepModel extends Model {

    protected FormStepModel(Long id) {
        super(id);
    }

    public static FormStep findByFormAndName(Form form, String name) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select id, form_id, name, sort_order, initial_enabled_state, fade_enabled from form_steps where form_id = ? and name =?");
            stmt.setLong(1, form.getId());
            stmt.setString(2, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return FormStep.create(rs.getLong("id"), form.getId(), name, rs.getInt("sort_order"), rs.getBoolean("initial_enabled_state"), rs.getBoolean("fade_enabled"));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static FormStep create(Form form, String name, int sortOrder, boolean initiallyEnabled, boolean fadeEnabled) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("insert into form_steps (form_id, name, sort_order, initial_enabled_state, fade_enabled, created_at) values(?,?,?,?,?, now())");
            stmt.setLong(1, form.getId());
            stmt.setString(2, name);
            stmt.setInt(3, sortOrder);
            stmt.setBoolean(4, initiallyEnabled);
            stmt.setBoolean(5, fadeEnabled);

            Long id = executeWithLastId(stmt);
            if (id != null) {
                return FormStep.create(id, form.getId(), name, sortOrder, initiallyEnabled, fadeEnabled);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static List<FormStep> findAll(Set<Long> ids) throws NamingException, SQLException {

        List<FormStep> steps = new ArrayList<FormStep>();

        if (ids.size() == 0) {
            return steps;
        }

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            StringBuilder sql = new StringBuilder();
            sql.append("select id, form_id, name, sort_order, initial_enabled_state, fade_enabled from form_steps where id in(");
            sql.append(Model.setToCsv(ids));
            sql.append(")");
            stmt = con.prepareStatement(sql.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                steps.add(FormStep.create(rs.getLong("id"), rs.getLong("form_id"), rs.getString("name"), rs.getInt("sort_order"), rs.getBoolean("initial_enabled_state"), rs.getBoolean("fade_enabled")));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return steps;
    }
}
