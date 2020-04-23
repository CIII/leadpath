package com.pony.advertiser;

import com.pony.publisher.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PonyLeads 2012
 * User: martin
 * Date: 7/3/12
 * Time: 3:01 PM
 */
public class Creative {
    private final Long id, offerId;
    private final String name, subject, html, text, fromPersonal, fromAddress, externalId;
    private final Status status;

    private Creative(Long id, Long offerId, String name, String subject, String html, String text, Status status, String fromAddress, String fromPersonal, String externalId) {
        this.id = id;
        this.offerId = offerId;
        this.name = name;
        this.subject = subject;
        this.html = html;
        this.text = text;
        this.status = status;
        this.fromAddress = fromAddress;
        this.fromPersonal = fromPersonal;
        this.externalId = externalId;
    }

    public static Creative create(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Long offerId = rs.getLong("offer_id");
        String name = rs.getString("name");
        String subject = rs.getString("subject_line");
        String html = rs.getString("html_content");
        String text = rs.getString("text_content");
        int status = rs.getInt("status");
        String fromAddress = rs.getString("from_address");
        String fromPersonal = rs.getString("from_personal");
        String externalId = rs.getString("external_id");

        return create(id, offerId, name, subject, html, text, Status.parse(status), fromAddress, fromPersonal, externalId);
    }

    public static Creative create(Long id, Long offerId, String name, String subject, String html, String text, Status status, String fromAddress, String fromPersonal, String externalId) {
        return new Creative(id, offerId, name, subject, html, text, status, fromAddress, fromPersonal, externalId);
    }

    public Long getId() {
        return id;
    }

    public Long getOfferId() {
        return offerId;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getHtml() {
        return html;
    }

    public String getText() {
        return text;
    }

    public Status getStatus() {
        return status;
    }

    public String getFromPersonal() {
        return fromPersonal;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getExternalId() {
        return externalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Creative creative = (Creative) o;

        if (!id.equals(creative.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Creative{" +
                "id=" + id +
                ", offerId=" + offerId +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", html='" + html + '\'' +
                ", text='" + text + '\'' +
                ", fromPersonal='" + fromPersonal + '\'' +
                ", fromAddress='" + fromAddress + '\'' +
                ", status=" + status +
                '}';
    }
}
