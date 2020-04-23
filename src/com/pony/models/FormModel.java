package com.pony.models;

import com.pony.form.Form;
import com.pony.form.FormStep;
import com.pony.form.FormStepAttribute;
import com.pony.form.FormStepGroup;
import com.pony.leadtypes.Attribute;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 2/7/13
 * Time: 10:21 AM
 */
public class FormModel extends Model {
    protected FormModel(Long id) {
        super(id);
    }

    public static Form find(Long formId) throws NamingException, SQLException {

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select id, name, submit_text, call_to_action, publisher_list_id, publisher_id from forms where id = ?");
            stmt.setLong(1, formId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Form.create(rs.getLong("id"), rs.getString("name"), rs.getString("submit_text"), rs.getString("call_to_action"), rs.getLong("publisher_list_id"), rs.getLong("publisher_id"));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static Form findByPublisherListAndName(PublisherList publisherList, String name) throws NamingException, SQLException {

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select id, name, submit_text, call_to_action, publisher_list_id, publisher_id from forms where publisher_list_id = ? and name = ?");
            stmt.setLong(1, publisherList.getId());
            stmt.setString(2, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Form.create(rs.getLong("id"), rs.getString("name"), rs.getString("submit_text"), rs.getString("call_to_action"), rs.getLong("publisher_list_id"), rs.getLong("publisher_id"));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static Form create(String name, String callToAction, String submitText, PublisherList publisherList, Publisher publisher) throws NamingException, SQLException {

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("insert ignore into forms (publisher_id, publisher_list_id, name, call_to_action, submit_text, created_at) values(?,?,?,?,?,now())");
            stmt.setLong(1, publisher.getId());
            stmt.setLong(2, publisherList.getId());
            stmt.setString(3, name);
            stmt.setString(4, callToAction);
            stmt.setString(5, submitText);
            Long id = executeWithLastId(stmt);
            if (id != null) {
                return Form.create(id, name, submitText, callToAction, publisherList.getId(), publisher.getId());
            }
        }
        finally {
            close(stmt);
            close(con);
        }
        return null;
    }

    /**
     * read the form steps and their initial state for the provided form
     *
     * @param form
     * @return
     */
    public static List<FormStep> getFormSteps(Form form) throws NamingException, SQLException {
        List<FormStep> formSteps = new LinkedList<FormStep>();

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select id, name, sort_order, initial_enabled_state, fade_enabled from form_steps where form_id = ? order by sort_order");
            stmt.setLong(1, form.getId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                formSteps.add(FormStep.create(rs.getLong("id"), form.getId(), rs.getString("name"), rs.getInt("sort_order"), rs.getBoolean("initial_enabled_state"), rs.getBoolean("fade_enabled")));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return formSteps;
    }

    public static List<FormStepGroup> getFormStepGroups(FormStep step) throws NamingException, SQLException {
        List<FormStepGroup> groups = new LinkedList<FormStepGroup>();

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select id, name, sort_order, initial_enabled_state from form_step_groups where form_step_id = ? order by sort_order");
            stmt.setLong(1, step.getId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                groups.add(FormStepGroup.create(rs.getLong("id"), step.getId(), rs.getString("name"), rs.getInt("sort_order"), rs.getBoolean("initial_enabled_state")));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return groups;
    }

    public static List<FormStepAttribute> getFormStepGroupAttributes(FormStepGroup group) throws NamingException, SQLException {
        List<FormStepAttribute> attribs = new LinkedList<FormStepAttribute>();

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select fsa.id, fs.form_id,  a.id attribute_id, a.name, a.input_type, fsa.label, fsa.required_flag, fsa.sort_order, fsa.initial_enabled_state, fsa.submit_on_change, fsa.default_value, fsa.placeholder, fsa.validation_message, fsa.input_size from form_step_attributes fsa join form_steps fs on fs.id = fsa.form_step_id join attributes a on a.id = fsa.attribute_id and fsa.form_step_id = fs.id where fsa.form_step_group_id = ? order by fsa.sort_order");
            stmt.setLong(1, group.getId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Attribute attribute = Attribute.create(rs.getLong("attribute_id"), rs.getString("name"), rs.getString("input_type"));
                attribs.add(FormStepAttribute.create(rs.getLong("id"), group.getId(), group.getFormStepId(), rs.getLong("form_id"), attribute, rs.getBoolean("required_flag"), rs.getInt("input_size"), rs.getString("label"), rs.getString("default_value"), rs.getString("placeholder"), rs.getString("validation_message"), rs.getInt("sort_order"), rs.getBoolean("initial_enabled_state"), rs.getBoolean("submit_on_change")));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return attribs;
    }
}
