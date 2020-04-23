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
import java.util.TreeMap;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 2/20/13
 * Time: 10:32 PM
 */
public class SelectValueModel extends Model {
    protected SelectValueModel(Long id) {
        super(id);
    }

    public static Map<String, SelectValue> createAll(FormStepAttribute formStepAttribute, Map<String, String> selectValues) throws NamingException, SQLException {

        Map<String, SelectValue> values = new HashMap<String, SelectValue>();

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("insert ignore into select_values(form_step_attribute_id, select_key, select_value, created_at) values(?,?,?,now()) ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id)");
            stmt.setLong(1, formStepAttribute.getId());

            for (Map.Entry<String, String> entry : selectValues.entrySet()) {
                stmt.setString(2, entry.getKey());
                stmt.setString(3, entry.getValue());
                Long id = executeWithLastId(stmt);
                if (id != null) {
                    values.put(entry.getKey(), SelectValue.create(id, entry.getKey(), entry.getValue(), false));
                }
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return values;
    }

    public static void preSelect(SelectValue selectValue) throws NamingException, SQLException {
        // make this value the one that gets the selected flag by default: pre_selected = 1
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("update select_values set pre_selected = 1, updated_at = now() where id = ?");
            stmt.setLong(1, selectValue.getId());
            stmt.executeUpdate();
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    public static Map<String, SelectValue> findAll(FormStepAttribute formStepAttribute) throws NamingException, SQLException {
        Map<String, SelectValue> values = new TreeMap<String, SelectValue>();

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select id, select_key, select_value, pre_selected from select_values where form_step_attribute_id = ? order by select_key");
            stmt.setLong(1, formStepAttribute.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                values.put(rs.getString("select_key"), SelectValue.create(rs.getLong("id"), rs.getString("select_key"), rs.getString("select_value"), rs.getBoolean("pre_selected")));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return values;
    }

    public static SelectValue find(Long id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select id, select_key, select_value, pre_selected from select_values where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return SelectValue.create(rs.getLong("id"), rs.getString("select_key"), rs.getString("select_value"), rs.getBoolean("pre_selected"));
            }
        }
        finally {
            close(stmt);
            close(con);
        }
        return null;
    }
}
