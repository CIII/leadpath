package com.pony.email;

/**
 * PonyLeads 2012
 * User: martin
 * Date: 7/3/12
 * Time: 3:02 PM
 */
public class Message {

    private Long id, userProfileId, hostId, creativeId;

    public static final int CREATED = 0;
    public static final int SENT = 1;
    public static final int PROCESSED = 2;
    public static final int DEFERRED = 3;
    public static final int DELIVERED = 4;
    public static final int DROPPED = 5;

    private Message(Long id, Long userProfileId, Long hostId, Long creativeId) {
        this.id = id;
        this.userProfileId = userProfileId;
        this.hostId = hostId;
        this.creativeId = creativeId;
    }

    public static Message create(Long id, Long userProfileId, Long hostId, Long creativeId) {
        return new Message(id, userProfileId, hostId, creativeId);
    }

    public Long getId() {
        return id;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public Long getHostId() {
        return hostId;
    }

    public Long getCreativeId() {
        return creativeId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", userProfileId=" + userProfileId +
                ", hostId=" + hostId +
                ", creativeId=" + creativeId +
                '}';
    }
}
