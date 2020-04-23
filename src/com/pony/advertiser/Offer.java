package com.pony.advertiser;

import com.pony.publisher.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PonyLeads 2012
 * User: martin
 * Date: 7/3/12
 * Time: 3:00 PM
 */
public class Offer {
    private final Long id, advertiserId, leadTypeId;
    private final String name, targetUrl, unsubscribeUrl, fromAddress, fromPersonal, clickUrl, bcc;
    private final Status status;

    private Offer(Long id, Long advertiserId, Long leadTypeId, String name, String targetUrl, String unsubscribeUrl, String fromAddress, String fromPersonal, String bcc, String clickUrl, Status status) {
        this.id = id;
        this.advertiserId = advertiserId;
        this.leadTypeId = leadTypeId;
        this.name = name;
        this.targetUrl = targetUrl;
        this.unsubscribeUrl = unsubscribeUrl;
        this.fromAddress = fromAddress;
        this.fromPersonal = fromPersonal;
        this.bcc = bcc;
        this.clickUrl = clickUrl;
        this.status = status;
    }

    public static Offer create(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Long advertiserId = rs.getLong("advertiser_id");
        Long leadTypeId = rs.getLong("lead_type_id");
        String name = rs.getString("name");
        String targetUrl = rs.getString("target_url");
        String unsubUrl = rs.getString("unsubscribe_url");
        int status = rs.getInt("status");
        String fromAddress = rs.getString("from_address");
        String fromPersonal = rs.getString("from_personal");
        String clickUrl = rs.getString("click_url");
        String bcc = rs.getString("bcc_address");
        return create(id, advertiserId, leadTypeId, name, targetUrl, unsubUrl, fromAddress, fromPersonal, bcc, clickUrl, Status.parse(status));
    }

    public static Offer create(Long id, Long advertiserId, Long leadTypeId, String name, String targetUrl, String unsubscribeUrl, String fromAddress, String fromPersonal, String bcc, String clickUrl, Status status) {
        return new Offer(id, advertiserId, leadTypeId, name, targetUrl, unsubscribeUrl, fromAddress, fromPersonal, bcc, clickUrl, status);
    }

    public Long getId() {
        return id;
    }

    public Long getAdvertiserId() {
        return advertiserId;
    }

    public Long getLeadTypeId() {
        return leadTypeId;
    }

    public String getName() {
        return name;
    }

    /**
     * get the url to embed into the mail message (the url for the user to click on)
     * Note: the #targetUrl is the final url to direct the user to, once she clicks on a click url
     * Also Note: the click url is only necessary if we handle click tracking
     *
     * @return
     */
    public String getClickUrl() {
        return clickUrl;
    }

    /**
     * get the url to redirect the user's browser to (, in case the user clicks on a link in the email;
     * for the situations where the smtp provider handles the encoding of click urls into the message,
     * we can use this target url to embed into the message we send to the provider).
     *
     * @return
     */
    public String getTargetUrl() {
        return targetUrl;
    }

    public String getUnsubscribeUrl() {
        return unsubscribeUrl;
    }

    public Status getStatus() {
        return status;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getFromPersonal() {
        return fromPersonal;
    }

    public String getBcc() {
        return bcc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Offer offer = (Offer) o;

        if (!id.equals(offer.id)) {
            return false;
        }
        if (!name.equals(offer.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", advertiserId=" + advertiserId +
                ", leadTypeId=" + leadTypeId +
                ", name='" + name + '\'' +
                ", targetUrl='" + targetUrl + '\'' +
                ", unsubscribeUrl='" + unsubscribeUrl + '\'' +
                ", fromAddress='" + fromAddress + '\'' +
                ", clickUrl='" + clickUrl + '\'' +
                ", status=" + status +
                '}';
    }
}
