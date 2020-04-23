package com.pony.livehttp;

import javax.servlet.http.HttpServletRequest;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 11/28/12
 * Time: 5:34 PM
 */
public class Redirect {
    private final Long id, clickSourceId, clickTargetId;
    private final String ipAddress, userAgent, referrer, destinationPath, destinationQuery;

    public static final String URL_TOKEN = "arrival_id";

    private Redirect(Long id, Long clickSourceId, Long clickTargetId, String destinationPath, String destinationQuery, String ipAddress, String userAgent, String referrer) {
        this.id = id;
        this.clickSourceId = clickSourceId;
        this.clickTargetId = clickTargetId;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.referrer = referrer;
        this.destinationPath = destinationPath;
        this.destinationQuery = destinationQuery;
    }

    public static Redirect create(Long id, Long clickSourceId, Long clickTargetId, String destinationPath, String destinationQuery) {
        return new Redirect(id, clickSourceId, clickTargetId, destinationPath, destinationQuery, null, null, null);
    }

    public static Redirect create(ClickSource clickSource, ClickTarget clickTarget, HttpServletRequest request) {
        return new Redirect(null, clickSource.getId(), clickTarget.getId(), request.getServletPath(), request.getQueryString(), request.getRemoteAddr(), request.getHeader("User-Agent"), request.getHeader("referer"));
    }

    public Long getId() {
        return id;
    }

    public Long getClickSourceId() {
        return clickSourceId;
    }

    public Long getClickTargetId() {
        return clickTargetId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getReferrer() {
        return referrer;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public String getDestinationQuery() {
        return destinationQuery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Redirect redirect = (Redirect) o;

        if (!id.equals(redirect.id)) {
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
        return "Redirect{" +
                "id=" + id +
                ", clickSourceId=" + clickSourceId +
                ", clickTargetId=" + clickTargetId +
                ", ipAddress='" + ipAddress + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", referrer='" + referrer + '\'' +
                '}';
    }
}
