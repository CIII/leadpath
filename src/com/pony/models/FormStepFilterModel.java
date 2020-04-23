package com.pony.models;

import com.pony.form.FormStep;
import com.pony.form.FormStepAttribute;
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
 * Date: 3/6/13
 * Time: 5:27 PM
 */
public class FormStepFilterModel extends Model {
    protected FormStepFilterModel(Long id) {
        super(id);
    }

    public static Map<FormStep, Boolean> findFilteredSteps(FormStepAttribute changingAttribute, SelectValue selectedOption) throws NamingException, SQLException {
        /*
       mysql> desc form_step_filters;
       +---------------------+----------+------+-----+---------+----------------+
       | Field               | Type     | Null | Key | Default | Extra          |
       +---------------------+----------+------+-----+---------+----------------+
       | id                  | int(11)  | NO   | PRI | NULL    | auto_increment |
       | form_id             | int(11)  | NO   | MUL | NULL    |                |
       | select_value_id     | int(11)  | NO   | MUL | NULL    |                |
       | target_form_step_id | int(11)  | NO   | MUL | NULL    |                |
       | enabled_state       | int(11)  | NO   |     | 0       |                |
        */
        Map<FormStep, Boolean> steps = new HashMap<FormStep, Boolean>();
        Map<Long, Boolean> tmp = new HashMap<Long, Boolean>();

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select target_form_step_id, enabled_state from form_step_filters where form_id = ? and select_value_id = ?");
            stmt.setLong(1, changingAttribute.getFormId());
            stmt.setLong(2, selectedOption.getId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tmp.put(rs.getLong("target_form_step_id"), rs.getBoolean("enabled_state"));
            }

            List<FormStep> formSteps = FormStepModel.findAll(tmp.keySet());
            for (FormStep step : formSteps) {
                steps.put(step, tmp.get(step.getId()));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return steps;
    }
}
