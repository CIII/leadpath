package com.pony.models;

import com.pony.form.Form;
import com.pony.form.FormStepAttribute;
import com.pony.form.SelectValue;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 2/21/13
 * Time: 7:30 PM
 */
public class FormSelectFilterModel extends Model {
	private static final Log LOG = LogFactory.getLog(FormSelectFilterModel.class);
	
    protected FormSelectFilterModel(Long id) {
        super(id);
    }

    /**
     * @param form
     * @param formStepAttribute
     * @param selectValue             the 'master' select value (that the 'child' select values will be tied to)
     * @param targetFormStepAttribute
     * @param selectValues            the options for the child    @throws NamingException
     * @throws SQLException
     */
    public static void createFilters(Form form, FormStepAttribute formStepAttribute, SelectValue selectValue, FormStepAttribute targetFormStepAttribute, Collection<SelectValue> selectValues) throws NamingException, SQLException {
        LOG.info("creating filter for selectValue=" + selectValue.getId() + " targetAtt=" + targetFormStepAttribute);
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("insert ignore into form_select_filters (form_id, form_step_attribute_id, select_value_id, target_form_step_attribute_id, target_select_value_id, created_at) values(?,?,?,?,?, now())");
            stmt.setLong(1, form.getId());
            stmt.setLong(2, formStepAttribute.getId());
            stmt.setLong(3, selectValue.getId());
            stmt.setLong(4, targetFormStepAttribute.getId());

            Iterator<SelectValue> i = selectValues.iterator();
            while (i.hasNext()) {
                SelectValue value = i.next();
                LOG.info("    creating filter:" + value);
                stmt.setLong(5, value.getId());
                stmt.executeUpdate();
            }
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    /**
     * Find all the select values , grouped by targetFormAttribute, that are allowed for the selected value (in the master formStepAttribute)
     *
     * @param changingAttribute
     * @param selectedOption
     * @return a map keyed by targetFormAttributeId
     */
    public static Map<FormStepAttribute, List<SelectValue>> findFilteredOptions(FormStepAttribute changingAttribute, SelectValue selectedOption) throws NamingException, SQLException {
/*
mysql> desc form_select_filters;
+--------------------------+----------+------+-----+---------+----------------+
| Field                    | Type     | Null | Key | Default | Extra          |
+--------------------------+----------+------+-----+---------+----------------+
| id                       | int(11)  | NO   | PRI | NULL    | auto_increment |
| form_id                  | int(11)  | NO   | MUL | NULL    |                |
| form_step_attribute_id | int(11)  | NO   | MUL | NULL    |                |
| select_value_id          | int(11)  | NO   | MUL | NULL    |                |
| target_form_step_attribute_id | int(11)  | NO   | MUL | NULL    |                |
| target_select_value_id   | int(11)  | NO   | MUL | NULL    |                |
| created_at               | datetime | YES  |     | NULL    |                |
| updated_at               | datetime | YES  |     | NULL    |                |
+--------------------------+----------+------+-----+---------+----------------+
 */
        // select id, form_id, target_form_step_attribute_id, target_select_value_id from form_select_filters where form_id = 1 and select_value_id = 3 order by target_form_step_attribute_id;
        Map<FormStepAttribute, List<SelectValue>> optionsMap = new HashMap<FormStepAttribute, List<SelectValue>>();

        Map<Long, FormStepAttribute> attributeMap = new TreeMap<Long, FormStepAttribute>();

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();

            if (selectedOption.equals(SelectValue.DEFAULT)) {
                //TODO: if the selected value is the default, get the list of dependent attributes , but forget about dependent select values
                //
                stmt = con.prepareStatement("select distinct sv.form_step_attribute_id from select_values sv join form_select_filters sf on sf.target_select_value_id = sv.id where sf.form_id = ? and sf.form_step_attribute_id = ?");
                stmt.setLong(1, changingAttribute.getFormId());
                stmt.setLong(2, changingAttribute.getId());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Long formStepAttributeId = rs.getLong("form_step_attribute_id");
                    FormStepAttribute formAttribute = FormStepAttributeModel.find(con, formStepAttributeId);

                    ArrayList<SelectValue> selectValues = new ArrayList<SelectValue>();
                    selectValues.add(SelectValue.DEFAULT);
                    optionsMap.put(formAttribute, selectValues);
                }
            }
            else {
                stmt = con.prepareStatement("select sv.id, sv.form_step_attribute_id, sv.select_key, sv.select_value, sv.pre_selected from select_values sv join form_select_filters sf on sf.target_select_value_id = sv.id where sf.form_id = ? and sf.select_value_id = ? order by target_form_step_attribute_id, select_key");
                stmt.setLong(1, changingAttribute.getFormId());
                stmt.setLong(2, selectedOption.getId());
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Long formStepAttributeId = rs.getLong("form_step_attribute_id");
                    FormStepAttribute formAttribute = attributeMap.get(formStepAttributeId);
                    if (formAttribute == null) {
                        formAttribute = FormStepAttributeModel.find(con, formStepAttributeId);
                        attributeMap.put(formStepAttributeId, formAttribute);
                    }
                    LOG.debug("formAttribute=" + formAttribute);

                    List<SelectValue> options = optionsMap.get(formAttribute);
                    if (options == null) {
                        options = new LinkedList<SelectValue>();
                        // read the form attribute:
                        optionsMap.put(formAttribute, options);
                    }
                    SelectValue selectValue = SelectValue.create(rs.getLong("id"), rs.getString("select_key"), rs.getString("select_value"), rs.getBoolean("pre_selected"));
                    options.add(selectValue);
                }
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return optionsMap;
    }
}
