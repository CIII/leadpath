package com.pony.email;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 12/2/13
 * Time: 9:30 PM
 */
public class MessageTemplate {
    private final Long id;
    private final String name, html, text;

    private MessageTemplate(Long id, String name, String html, String text) {
        this.id = id;
        this.name = name;
        this.html = html;
        this.text = text;
    }

    public static MessageTemplate create(ResultSet rs, boolean withContent) throws SQLException {
        return new MessageTemplate(rs.getLong("id"), rs.getString("name"), withContent ? rs.getString("html_content") : null, withContent ? rs.getString("text_content") : null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessageTemplate that = (MessageTemplate) o;

        if (!id.equals(that.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHtml() {
        return html;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "MessageTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", html='" + html + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
