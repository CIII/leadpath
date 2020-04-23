package com.pony.models;

import com.pony.form.Form;
import com.pony.form.FormStepAttribute;
import com.pony.form.FormStepGroup;
import com.pony.form.SelectValue;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 2/28/13
 * Time: 2:33 PM
 */
public class FormStepGroupFilterModel extends Model {
    protected FormStepGroupFilterModel(Long id) {
        super(id);
    }


    public static void createFilter(Form form, SelectValue selectValue, Long targetGroupId, boolean targetState) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("insert ignore into form_step_group_filters (form_id, select_value_id, target_form_step_group_id, enabled_state, created_at) values(?,?,?,?, now())");
            stmt.setLong(1, form.getId());
            stmt.setLong(2, selectValue.getId());
            stmt.setLong(3, targetGroupId);
            stmt.setBoolean(4, targetState);
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    public static Map<FormStepGroup, Boolean> findFilteredGroups(FormStepAttribute changingAttribute, SelectValue selectedOption) throws NamingException, SQLException {
        /*
       mysql> desc form_step_group_filters;
       +---------------------------+----------+------+-----+---------+----------------+
       | Field                     | Type     | Null | Key | Default | Extra          |
       +---------------------------+----------+------+-----+---------+----------------+
       | id                        | int(11)  | NO   | PRI | NULL    | auto_increment |
       | form_id                   | int(11)  | NO   | MUL | NULL    |                |
       | select_value_id           | int(11)  | NO   | MUL | NULL    |                |
       | target_form_step_group_id | int(11)  | NO   | MUL | NULL    |                |
       | enabled_state             | int(11)  | NO   |     | 0       |                |
        */
        Map<FormStepGroup, Boolean> stepGroups = new HashMap<FormStepGroup, Boolean>();
        Map<Long, Boolean> tmp = new HashMap<Long, Boolean>();

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select target_form_step_group_id, enabled_state from form_step_group_filters where form_id = ? and select_value_id = ?");
            stmt.setLong(1, changingAttribute.getFormId());
            stmt.setLong(2, selectedOption.getId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tmp.put(rs.getLong("target_form_step_group_id"), rs.getBoolean("enabled_state"));
            }

            List<FormStepGroup> groups = FormStepGroupModel.findAll(tmp.keySet());

            for (FormStepGroup group : groups) {
                stepGroups.put(group, tmp.get(group.getId()));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return stepGroups;
    }
}
