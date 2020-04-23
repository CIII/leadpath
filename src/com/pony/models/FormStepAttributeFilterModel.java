package com.pony.models;

import com.pony.form.FormStepAttribute;
import com.pony.form.SelectValue;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 3/6/13
 * Time: 5:25 PM
 */
public class FormStepAttributeFilterModel extends Model {
    protected FormStepAttributeFilterModel(Long id) {
        super(id);
    }

    public static Map<FormStepAttribute, Boolean> findFilteredAttributes(FormStepAttribute changingAttribute, SelectValue selectedOption) throws NamingException, SQLException {
        /*
        mysql> desc form_step_attribute_filters;
        +-------------------------------+----------+------+-----+---------+----------------+
        | Field                         | Type     | Null | Key | Default | Extra          |
        +-------------------------------+----------+------+-----+---------+----------------+
        | id                            | int(11)  | NO   | PRI | NULL    | auto_increment |
        | form_id                       | int(11)  | NO   | MUL | NULL    |                |
        | select_value_id               | int(11)  | NO   | MUL | NULL    |                |
        | target_form_step_attribute_id | int(11)  | NO   | MUL | NULL    |                |
        | enabled_state                 | int(11)  | NO   |     | 0       |                |
        */
        Map<FormStepAttribute, Boolean> filteredAttributes = new HashMap<FormStepAttribute, Boolean>();
        Map<Long, Boolean> tmp = new HashMap<Long, Boolean>();
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select target_form_step_attribute_id, enabled_state from form_step_attribute_filters where form_id = ? and select_value_id = ?");
            stmt.setLong(1, changingAttribute.getFormId());
            stmt.setLong(2, selectedOption.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tmp.put(rs.getLong("target_form_step_attribute_id"), rs.getBoolean("enabled_state"));
            }

            Map<Long, FormStepAttribute> attributes = FormStepAttributeModel.findAll(tmp.keySet());
            for (Map.Entry<Long, FormStepAttribute> entry : attributes.entrySet()) {
                filteredAttributes.put(entry.getValue(), tmp.get(entry.getValue().getId()));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return filteredAttributes;
    }
}
