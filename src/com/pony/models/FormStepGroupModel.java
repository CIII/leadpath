package com.pony.models;

import com.pony.form.FormStep;
import com.pony.form.FormStepGroup;

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
 * Time: 1:02 PM
 */
public class FormStepGroupModel extends Model {

    protected FormStepGroupModel(Long id) {
        super(id);
    }

    public static FormStepGroup findByFormStepAndName(FormStep formStep, String name) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select id, form_step_id, name, sort_order, initial_enabled_state from form_step_groups where form_step_id = ? and name = ?");
            stmt.setLong(1, formStep.getId());
            stmt.setString(2, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return FormStepGroup.create(rs.getLong("id"), formStep.getId(), name, rs.getInt("sort_order"), rs.getBoolean("initial_enabled_state"));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static FormStepGroup create(FormStep formStep, String name, int sortOrder, boolean initiallyEnabled) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("insert ignore into form_step_groups (form_step_id, name, sort_order, initial_enabled_state, created_at) values(?,?,?,?,now())");
            stmt.setLong(1, formStep.getId());
            stmt.setString(2, name);
            stmt.setInt(3, sortOrder);
            stmt.setBoolean(4, initiallyEnabled);

            Long id = executeWithLastId(stmt);
            if (id != null) {
                return FormStepGroup.create(id, formStep.getId(), name, sortOrder, initiallyEnabled);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static List<FormStepGroup> findAll(Set<Long> ids) throws NamingException, SQLException {
        List<FormStepGroup> groups = new ArrayList<FormStepGroup>();

        if (ids.size() == 0) {
            return groups;
        }

        Connection con = null;
        PreparedStatement stmt = null;

        StringBuilder sql = new StringBuilder();
        sql.append("select id, form_step_id, name, sort_order, initial_enabled_state from form_step_groups where id in(");
        sql.append(Model.setToCsv(ids)).append(")");

        try {
            con = connectX();
            stmt = con.prepareStatement(sql.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                groups.add(FormStepGroup.create(rs.getLong("id"), rs.getLong("form_step_id"), rs.getString("name"), rs.getInt("sort_order"), rs.getBoolean("initial_enabled_state")));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return groups;
    }
}
