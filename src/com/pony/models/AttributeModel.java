package com.pony.models;

import com.pony.form.FormStepAttribute;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.leadtypes.Attribute;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User: martin
 * Date: 7/1/12
 * Time: 10:03 PM
 */
public class AttributeModel extends Model {
    private static final String COLUMNS = "id, name, input_type, is_large";
    public static final String TABLE = "attributes";

    protected AttributeModel(Long id) {
        super(id);
    }

    public static Map<String, Attribute> findAll(Connection con) throws SQLException, NamingException {
        PreparedStatement stmt = null;

        Map<String, Attribute> attributes = new HashMap<String, Attribute>();
        try {
            stmt = con.prepareStatement("select " + COLUMNS + " from " + TABLE);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                attributes.put(rs.getString("name"), Attribute.create(rs.getLong("id"), rs.getString("name"), rs.getString("input_type"), rs.getBoolean("is_large")));
            }

            return attributes;
        }
        finally {
            close(stmt);
        }
    }

    public static Attribute find(Connection con, String name) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("select " + COLUMNS + " from " + TABLE + " where name = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Attribute.create(rs.getLong("id"), rs.getString("name"), rs.getString("input_type"), rs.getBoolean("is_large"));
            }
        }
        finally {
            close(stmt);
        }
        return null;
    }

    public static void persistProfileAttributes(UserProfile userProfile, Lead lead) throws SQLException, NamingException {

        Connection con = null;

        try {
            // lookup all attributes and their ids, and put them in a map (name -> id)
            con = connectX();
            AttributesToWrite toWrite = prepareAttributesForWrite(lead, con);

            // now persist the attribute values (in one)
            persistAttributeValues(con, userProfile, toWrite);
        }
        finally {
            close(con);
        }
    }

    public static void persistPingAttributes(Lead lead) throws NamingException, SQLException {
        Connection con = null;

        try {
            // lookup all attributes and their ids, and put them in a map (name -> id)
            con = connectX();
            AttributesToWrite toWrite = prepareAttributesForWrite(lead, con);

            // now persist the attribute values (in one)
            persistAttributeValues(con, lead, toWrite);
        }
        finally {
            close(con);
        }
    }

    private static AttributesToWrite prepareAttributesForWrite(Lead lead, Connection con) throws SQLException, NamingException {
        Map<String, Attribute> attributes = findAll(con);

        Map<Long, String> toWrite = new HashMap<Long, String>();
        Map<Long, String> toWriteXL = new HashMap<Long, String>();

        //create the lead, and the lead values from the passed map of attributes
        for (Map.Entry<String, String> entry : lead.toMap().entrySet()) {
            //name value pairs
            //lookup attribute id in map with the entry.key
            //profile_attributes

            Attribute attribute = attributes.get(entry.getKey());
            // we don't know about this attribute -> create it
            if (attribute == null) {
                attribute = create(con, entry.getKey());
                attributes.put(entry.getKey(), attribute);
            }

            // for checkbox attributes, the presence in the post signifies a 'checked' state
            if (attribute.getInputType().equals(FormStepAttribute.TYPE_CHECKBOX.getName())) {
                toWrite.put(attribute.getId(), "1");
            }
            else {
                if (attribute.isLarge()) {
                    toWriteXL.put(attribute.getId(), entry.getValue());
                }
                else {
                    toWrite.put(attribute.getId(), entry.getValue());
                }
            }
        }
        return new AttributesToWrite(toWrite, toWriteXL);
    }

    private static Attribute create(Connection con, String name) throws SQLException {
        return create(con, name, "text"); // default is text
    }

    public static Attribute create(Connection con, String name, String inputType) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("insert ignore into attributes (name, input_type, created_at) values (?, ?, now()) ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id)");
            stmt.setString(1, name);
            stmt.setString(2, inputType);
            Long id = executeWithLastId(stmt);
            return Attribute.create(id, name, inputType);
        }
        finally {
            close(stmt);
        }
    }

    private static void persistAttributeValues(Connection con, UserProfile userProfile, AttributesToWrite attributes) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("insert into profile_attributes (user_profile_id, attribute_id, value, created_at, updated_at) values(?,?,?,now(), null) on duplicate key update id=LAST_INSERT_ID(id), value=?, updated_at = now()");
            stmt.setLong(1, userProfile.getId());

            for (Map.Entry<Long, String> entry : attributes.getToWrite().entrySet()) {
                stmt.setLong(2, entry.getKey());
                stmt.setString(3, entry.getValue());
                stmt.setString(4, entry.getValue());
                stmt.executeUpdate();
            }

            close(stmt);

            stmt = con.prepareStatement("insert into profile_xl_attributes (user_profile_id, attribute_id, value, created_at, updated_at) values(?,?,?,now(), null) on duplicate key update id=LAST_INSERT_ID(id), value=?, updated_at = now()");
            stmt.setLong(1, userProfile.getId());

            for (Map.Entry<Long, String> entry : attributes.getToWriteXL().entrySet()) {
                stmt.setLong(2, entry.getKey());
                stmt.setString(3, entry.getValue());
                stmt.setString(4, entry.getValue());
                stmt.executeUpdate();
            }
        }
        finally {
            close(stmt);
        }
    }

    public static Map<String, String> readPingAttributes(Long leadId) throws NamingException, SQLException {
        if (leadId == null) {
            return Collections.emptyMap();
        }

        Connection con = null;
        try {
            con = connectX();
            return readPingAttributes(con, leadId);
        }
        finally {
            close(con);
        }
    }

    public static Map<String, String> readPingAttributes(Connection con, Long leadId) throws SQLException {

        if (leadId == null) {
            return Collections.emptyMap();
        }

        Map<String, String> values = new HashMap<String, String>();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select a.name , pa.value from attributes a join ping_attributes pa on pa.attribute_id = a.id where pa.lead_id =?");
            stmt.setLong(1, leadId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                values.put(rs.getString("name"), rs.getString("value"));
            }
        }
        finally {
            close(stmt);
        }

        return values;
    }

    private static void persistAttributeValues(Connection con, Lead lead, AttributesToWrite attributes) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("insert into ping_attributes (lead_id, attribute_id, value, created_at, updated_at) values(?,?,?,now(), null) on duplicate key update id=LAST_INSERT_ID(id), value=?, updated_at = now()");
            stmt.setLong(1, lead.getId());

            // !!Note: we do not write XL attributes during a ping!! (i.e.: we ignore them here)
            for (Map.Entry<Long, String> entry : attributes.getToWrite().entrySet()) {
                stmt.setLong(2, entry.getKey());
                stmt.setString(3, entry.getValue());
                stmt.setString(4, entry.getValue());
                stmt.executeUpdate();
            }
        }
        finally {
            close(stmt);
        }
    }

    public static class AttributesToWrite {
        private final Map<Long, String> toWrite = new HashMap<Long, String>();
        private final Map<Long, String> toWriteXL = new HashMap<Long, String>();

        private AttributesToWrite(Map<Long, String> toWrite, Map<Long, String> toWriteXL) {
            this.toWrite.putAll(toWrite);
            this.toWriteXL.putAll(toWriteXL);
        }

        public Map<Long, String> getToWrite() {
            return Collections.unmodifiableMap(toWrite);
        }

        public Map<Long, String> getToWriteXL() {
            return Collections.unmodifiableMap(toWriteXL);
        }
    }
}
