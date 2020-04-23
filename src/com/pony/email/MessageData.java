package com.pony.email;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * all data of a message and related objects, such as creatives and offers.
 * This is used to make decisions on resends
 * <p/>
 * PonyLeads 2012.
 * User: martin
 * Date: 9/18/12
 * Time: 6:16 PM
 */
public class MessageData {
    private final Long userProfileId, arrivalId, publisherId, publisherListId, messageId, hostId, leadTypeId, creativeId, offerId;
    private final boolean opened, clicked;

    private MessageData(Long userProfileId, Long arrivalId, Long publisherId, Long publisherListId, Long messageId, Long hostId, Long leadTypeId, Long creativeId, Long offerId, boolean opened, boolean clicked) {
        this.userProfileId = userProfileId;
        this.arrivalId = arrivalId;
        this.publisherId = publisherId;
        this.publisherListId = publisherListId;
        this.messageId = messageId;
        this.hostId = hostId;
        this.leadTypeId = leadTypeId;
        this.creativeId = creativeId;
        this.offerId = offerId;
        this.opened = opened;
        this.clicked = clicked;
    }

    public static MessageData create(ResultSet rs) throws SQLException {
        return new MessageData(rs.getLong("profile_id"), rs.getLong("arrival_id"), rs.getLong("publisher_id"), rs.getLong("publisher_list_id"), rs.getLong("message_id"), rs.getLong("host_id"), rs.getLong("lead_type_id"), rs.getLong("creative_id"), rs.getLong("offer_id"), rs.getBoolean("opened"), rs.getBoolean("clicked"));
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public Long getArrivalId() {
        return arrivalId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public Long getPublisherListId() {
        return publisherListId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public Long getHostId() {
        return hostId;
    }

    public Long getLeadTypeId() {
        return leadTypeId;
    }

    public Long getCreativeId() {
        return creativeId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public boolean isOpened() {
        return opened;
    }

    public boolean isClicked() {
        return clicked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessageData that = (MessageData) o;

        if (!messageId.equals(that.messageId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return messageId.hashCode();
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "userProfileId=" + userProfileId +
                ", arrivalId=" + arrivalId +
                ", publisherId=" + publisherId +
                ", publisherListId=" + publisherListId +
                ", messageId=" + messageId +
                ", hostId=" + hostId +
                ", leadTypeId=" + leadTypeId +
                ", creativeId=" + creativeId +
                ", offerId=" + offerId +
                ", opened=" + opened +
                ", clicked=" + clicked +
                '}';
    }
}
