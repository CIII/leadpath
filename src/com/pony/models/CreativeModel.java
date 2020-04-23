package com.pony.models;

import com.pony.advertiser.Creative;
import com.pony.email.MessageTemplate;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 7/9/12
 * Time: 12:03 PM
 */
public class CreativeModel extends Model {
    protected CreativeModel(Long id) {
        super(id);
    }

    public static Creative find(Connection con, Long id) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select c.id, c.offer_id, c.name, sl.subject_line, mt.html_content, mt.text_content, c.status, fa.from_address, fa.from_personal, c.external_id from creatives c left join subject_lines sl on sl.id = c.subject_line_id left join message_templates mt on mt.id = c.message_template_id left join from_addresses fa on fa.id = c.from_address_id where c.id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Creative.create(rs);
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    public static Creative find(Long id) throws NamingException, SQLException {
        Connection con = null;
        try {
            con = connectX();
            return find(con, id);
        }
        finally {
            close(con);
        }
    }

    public static List<MessageTemplate> findTemplates(boolean withContent) throws NamingException, SQLException {
        List<MessageTemplate> templates = new ArrayList<MessageTemplate>();

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            StringBuffer sql = new StringBuffer();
            sql.append("select id, name ");
            if (withContent) {
                sql.append(", html_content, text_content ");
            }

            sql.append("from message_templates");

            stmt = con.prepareStatement(sql.toString());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                templates.add(MessageTemplate.create(rs, withContent));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return templates;
    }

    public static MessageTemplate findTemplate(Long id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select id, name, html_content, text_content from message_templates where id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return MessageTemplate.create(rs, true);
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static Long createTemplate(String name, String html, String txt) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("insert into message_templates (name, html_content, text_content, created_at) values(?,?,?,now()) ");
            stmt.setString(1, name);
            stmt.setString(2, html);
            stmt.setString(3, txt);

            return executeWithLastId(stmt);
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    public static boolean updateTemplate(Long id, String name, String html, String txt) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("update message_templates set name=?, html_content=?, text_content=?, updated_at=now() where id=?");
            stmt.setString(1, name);
            stmt.setString(2, html);
            stmt.setString(3, txt);
            stmt.setLong(4, id);
            return stmt.executeUpdate() == 1;
        }
        finally {
            close(stmt);
            close(con);
        }
    }
}
