package com.pony.models;

import com.pony.form.Form;
import com.pony.form.FormStepAttribute;
import com.pony.form.FormStepGroup;
import com.pony.leadtypes.Attribute;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 2/20/13
 * Time: 1:08 PM
 */
public class FormStepAttributeModel extends Model {

    protected FormStepAttributeModel(Long id) {
        super(id);
    }

    public static FormStepAttribute findByStepGroupAndAttributeName(Form form, FormStepGroup formStepGroup, String attributeName) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select fsa.id, a.id attribute_id, a.name, a.input_type, required_flag, input_size, label, default_value , placeholder, validation_message, fsa.sort_order, fsa.initial_enabled_state, fsa.submit_on_change from form_step_attributes fsa join attributes a on a.id = fsa.attribute_id where fsa.form_step_group_id = ? and a.name = ?");
            stmt.setLong(1, formStepGroup.getId());
            stmt.setString(2, attributeName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Attribute attribute = Attribute.create(rs.getLong("attribute_id"), rs.getString("name"), rs.getString("input_type"));
                return FormStepAttribute.create(rs.getLong("id"), formStepGroup.getId(), formStepGroup.getFormStepId(), form.getId(), attribute, rs.getBoolean("required_flag"), rs.getInt("input_size"), rs.getString("label"), rs.getString("default_value"), rs.getString("placeholder"), rs.getString("validation_message"), rs.getInt("sort_order"), rs.getBoolean("initial_enabled_state"), rs.getBoolean("submit_on_change"));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static FormStepAttribute createAll(Form form, FormStepGroup formStepGroup, String attributeName, String attributeType, int inputSize, boolean required, String label, int sortOrder, boolean initiallyEnabled, boolean submitOnChange, String defaultValue, String placeHolder, String validationMessage) throws NamingException, SQLException {
        // do we have the attribute
        // form attribute
        // form step attribute
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();

            Attribute attribute = AttributeModel.find(con, attributeName);
            if (attribute == null) {
                attribute = AttributeModel.create(con, attributeName, attributeType);
            }

//            FormAttribute formAttribute = FormAttributeModel.find(con, form, attribute);
//            if (formAttribute == null) {
//                formAttribute = FormAttributeModel.create(con, form, attribute);
//            }

            // create the step attribute
            stmt = con.prepareStatement("insert into form_step_attributes (form_step_id, form_step_group_id, attribute_id, label, required_flag, " +
                    "sort_order, initial_enabled_state, submit_on_change, default_value, placeholder, validation_message, created_at) values(?,?,?,?,?,?,?,?,?,?,?, now())");
            stmt.setLong(1, formStepGroup.getFormStepId());
            stmt.setLong(2, formStepGroup.getId());
            stmt.setLong(3, attribute.getId());
            stmt.setString(4, label);
            stmt.setBoolean(5, required);
            stmt.setInt(6, sortOrder);
            stmt.setBoolean(7, initiallyEnabled);
            stmt.setBoolean(8, submitOnChange);
            stmt.setString(9, defaultValue);
            stmt.setString(10, placeHolder);
            stmt.setString(11, validationMessage);

            Long id = executeWithLastId(stmt);
            if (id != null) {
                return FormStepAttribute.create(id, formStepGroup.getId(), formStepGroup.getFormStepId(), form.getId(), attribute, required, inputSize, label, defaultValue, placeHolder, validationMessage, sortOrder, initiallyEnabled, submitOnChange);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static FormStepAttribute find(Long id) throws NamingException, SQLException {
        Connection con = null;
        try {
            con = connectX();
            return find(con, id);
        }
        finally {
            close(con);
        }
    }

    public static FormStepAttribute find(Connection con, Long id) throws NamingException, SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("select fsa.id, a.id attribute_id, fs.form_id, fsa.form_step_id, fsa.form_step_group_id, a.name, a.input_type, required_flag, input_size, label, default_value , placeholder, validation_message, fsa.sort_order, fsa.initial_enabled_state, fsa.submit_on_change from form_step_attributes fsa join attributes a on a.id = fsa.attribute_id join form_steps fs on fs.id = fsa.form_step_id where fsa.id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Attribute attribute = Attribute.create(rs.getLong("attribute_id"), rs.getString("name"), rs.getString("input_type"));
                return FormStepAttribute.create(rs.getLong("id"), rs.getLong("form_step_group_id"), rs.getLong("form_step_id"), rs.getLong("form_id"), attribute, rs.getBoolean("required_flag"), rs.getInt("input_size"), rs.getString("label"), rs.getString("default_value"), rs.getString("placeholder"), rs.getString("validation_message"), rs.getInt("sort_order"), rs.getBoolean("initial_enabled_state"), rs.getBoolean("submit_on_change"));
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    public static Map<Long, FormStepAttribute> findAll(Set<Long> ids) throws NamingException, SQLException {
        Map<Long, FormStepAttribute> attribs = new TreeMap<Long, FormStepAttribute>();

        if (ids.size() == 0) {
            return attribs;
        }

        Connection con = null;
        PreparedStatement stmt = null;

        String idString = setToCsv(ids);

        try {
            con = connectX();
            StringBuilder sql = new StringBuilder();
            sql.append("select fsa.id, a.id attribute_id, fsa.form_step_id, fsa.form_step_group_id, fs.form_id, a.name, a.input_type, required_flag, input_size, label, default_value , placeholder, validation_message, fsa.sort_order, fsa.initial_enabled_state, fsa.submit_on_change from form_step_attributes fsa join attributes a on a.id = fsa.attribute_id join form_steps fs on fs.id = fsa.form_step_id where fsa.id in(");
            sql.append(idString).append(")");
            stmt = con.prepareStatement(sql.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Attribute attribute = Attribute.create(rs.getLong("attribute_id"), rs.getString("name"), rs.getString("input_type"));
                attribs.put(rs.getLong("id"), FormStepAttribute.create(rs.getLong("id"), rs.getLong("form_step_group_id"), rs.getLong("form_step_id"), rs.getLong("form_id"), attribute, rs.getBoolean("required_flag"), rs.getInt("input_size"), rs.getString("label"), rs.getString("default_value"), rs.getString("placeholder"), rs.getString("validation_message"), rs.getInt("sort_order"), rs.getBoolean("initial_enabled_state"), rs.getBoolean("submit_on_change")));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return attribs;
    }
}

/*
        --
                How many
        years have
        you been
        in business
        ?
        insert ignore
        into attributes

        values(null, 'years_in_business', 'integer', 'Please provide a number', now()

        );

    insert ignore
    into form_attributes

    values(null,(select id from forms where name='ArbVenture Business Registration Form'),

    (
    select id
    from attributes
    where name = 'years_in_business'
    ),'How many years have you been in business?',1,'integer',2,null,null,1,

    now(),

    null);
    insert ignore

    into form_step_attributes(form_step_id, form_step_group_id, attribute_id, sort_order, initial_enabled_state, submit_on_change, created_at)values

    (
            (
    select id
    from form_steps
    where form_id = (select
    id from
    forms where
    name='ArbVenture Business Registration Form')
    and name = 'Tell us about your business'
    ),
            (
    select id
    from form_step_groups
    where form_step_id = (select
    id from
    form_steps where
    form_id=(
    select id
    from forms
    where name = 'ArbVenture Business Registration Form'
    )
    and name = 'Tell us about your business'
    )
    and name = 'general business'
    ),
            (
    select id
    from form_attributes
    where form_id = (select
    id from
    forms where
    name='ArbVenture Business Registration Form')
    and attribute_id = (select
    id from
    attributes where
    name='years_in_business')),
            1,1,0,

    now()

    );

*/