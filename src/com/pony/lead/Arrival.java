package com.pony.lead;

import com.pony.publisher.PublisherChannel;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/29/11
 * Time: 9:43 PM
 */
public class Arrival {
	private static final Log LOG = LogFactory.getLog(Arrival.class);

    public static final String ASID = "asid";
    public static final String EXT_ARRIVAL_ID = "arrivalid";
    public static final String ROBOT_ID = "robot_id";
    public static final String USER_AGENT = "ua";
    public static final String IP = "ip";

    private Long id;
    private final Long publisherId;
    private final Long publisherListId;
    private Long userProfileId;
    private String ipAddress, userAgent, referrer, arrivalSourceId;
    private String robotId;

    private final String externalId;
    
    public Arrival() {
    	userAgent = null;
    	referrer = null;
    	publisherListId = null;
    	publisherId = null;
    	ipAddress = null;
    	externalId = null;
    	arrivalSourceId = null;
        robotId = null;
    };
    
    public Arrival(Long id, String externalId) {
    	this.id = id;
    	this.externalId = externalId;
    	this.publisherId = null;
    	this.publisherListId = null;
    }
    
    private Arrival(ResultSet rs) throws SQLException {
        id = rs.getLong("id");
        publisherId = rs.getLong("publisher_id");
        publisherListId = rs.getLong("publisher_list_id");
        userProfileId = rs.getLong("user_profile_id");
        ipAddress = rs.getString("ip_address");
        userAgent = rs.getString("user_agent");
        referrer = rs.getString("referrer");
        arrivalSourceId = rs.getString("arrival_source_id");
        externalId = rs.getString("external_id");
        robotId = rs.getString("robot_id");
    }

    private Arrival(PublisherChannel publisherChannel, UserProfile userProfile, String ipAddress, String userAgent, String referrer, String asid, String externalId, String robotId) {
        this.publisherId = publisherChannel.getPublisher().getId();
        this.publisherListId = publisherChannel.getPublisherList().getId();
        this.userProfileId = (userProfile == null ? null : userProfile.getId());
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.referrer = referrer;
        this.arrivalSourceId = asid;
        this.externalId = externalId;
        this.robotId = robotId;
    }

    public static Arrival parse(PublisherChannel publisherChannel, UserProfile userProfile, HttpServletRequest request) {
        String referrer = request.getHeader("referer");
        String arrivalSourceId = request.getParameter(ASID);
        String externalId = request.getParameter(EXT_ARRIVAL_ID);
        String robotId = request.getParameter(ROBOT_ID);
        String ip = request.getParameter(IP);
        String ua = request.getParameter(USER_AGENT);
        return new Arrival(publisherChannel, userProfile, ip, ua, referrer, arrivalSourceId, externalId, robotId);
    }

    public static Arrival create(ResultSet rs) throws SQLException {
        return new Arrival(rs);
    }

    public Long getId() {
        return id;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public Long getPublisherListId() {
        return publisherListId;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void updateUserProfile(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }

        if (this.userProfileId != null && this.userProfileId > 0L) {
            throw new IllegalStateException("userProfile already set!");
        }

        if (userProfile.getId() != null && userProfile.getId() > 0L) {
            this.userProfileId = userProfile.getId();
            return;
        }

        throw new IllegalArgumentException(userProfile.toString());
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getReferrer() {
        return referrer;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getArrivalSourceId() {
        return arrivalSourceId;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getRobotId(){
        return robotId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Arrival arrival = (Arrival) o;
        return id.equals(arrival.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Arrival{" +
                "id=" + id +
                ", publisherId=" + publisherId +
                ", userProfileId=" + userProfileId +
                ", ipAddress='" + ipAddress + '\'' +
                '}';
    }

    public void setId(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("id already set");
        }

        this.id = id;
    }

    public static String parseDomain(String url) {
        if (url == null || "".equals(url)) {
            return null;
        }

        // clean the string and then try to parse....
        url = url.trim();

        // protocol?
        int i = url.indexOf("://");
        if (i > 0) {
            url = url.substring(i + 3);
        }

        // path
        i = url.indexOf("/");
        String domain;
        if (i > 0) {
            domain = url.substring(0, i);
        }
        else {
            domain = url;
        }

        // user: foo@host
        i = domain.indexOf("@");
        if (i > 0) {
            domain = domain.substring(i + 1);
        }

        // port?
        i = domain.indexOf(":");
        if (i > 0) {
            // is the remainder a number?
            String port = domain.substring(i + 1);
            try {
//                int p = Integer.valueOf(port);
                domain = domain.substring(0, i);
            }
            catch (NumberFormatException e) {
                // is a user before the ':' ?
                LOG.error(e);
            }
        }

        return domain;
    }
}
