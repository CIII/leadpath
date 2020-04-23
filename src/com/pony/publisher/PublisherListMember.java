package com.pony.publisher;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 6/6/12
 * Time: 9:03 PM
 */
public class PublisherListMember {
    private Long id, publisherListId, publisherId;
    private BigDecimal cpl;
    private Status status;

    private PublisherListMember(Long id, Long publisherListId, Long publisherId, BigDecimal cpl, Status status) {
        this.id = id;
        this.publisherListId = publisherListId;
        this.publisherId = publisherId;
        this.cpl = cpl;
        this.status = status;
    }

    public static PublisherListMember create(ResultSet rs) throws SQLException {
        return new PublisherListMember(rs.getLong("id"), rs.getLong("publisher_list_id"), rs.getLong("publisher_id"), rs.getBigDecimal("cpl"), Status.parse(rs.getInt("status")));
    }

    public Long getId() {
        return id;
    }

    public Long getPublisherListId() {
        return publisherListId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public BigDecimal getCpl() {
        return cpl;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PublisherListMember that = (PublisherListMember) o;

        if (!id.equals(that.id)) {
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
        return "PublisherListMember{" +
                "id=" + id +
                ", publisherListId=" + publisherListId +
                ", publisherId=" + publisherId +
                ", cpl=" + cpl +
                ", status=" + status +
                '}';
    }
}
