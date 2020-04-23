package com.pony.models;

import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;
import com.pony.publisher.PublisherListMember;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 6/6/12
 * Time: 8:58 PM
 */
public class PublisherListMemberModel extends Model {
    private BigDecimal costPerLead;

    protected PublisherListMemberModel(Long id) {
        super(id);
    }

    public static PublisherListMember findByPublisherAndList(Long publisherId, Long publisherListId) throws NamingException, SQLException {
        Connection con = null;
        try {
            con = connectX();
            return findByPublisherAndList(con, publisherId, publisherListId);
        }
        finally {
            close(con);
        }
    }

    public static PublisherListMember findByPublisherAndList(Connection con, Long publisherId, Long publisherListId) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("select id, publisher_list_id, publisher_id, cpl, status from publisher_list_members where publisher_id = ? and publisher_list_id = ?");
            stmt.setLong(1, publisherId);
            stmt.setLong(2, publisherListId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return PublisherListMember.create(rs);
            }
        }
        finally {
            close(stmt);
        }

        return null;
    }

    public BigDecimal getCostPerLead() {
        return costPerLead;
    }

    public static void create(Publisher publisher, PublisherList publisherList) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("insert ignore into publisher_list_members (publisher_list_id, publisher_id, created_at) values(?,?,now())");
            stmt.setLong(1, publisherList.getId());
            stmt.setLong(2, publisher.getId());
            executeWithLastId(stmt);
        }
        finally {
            close(stmt);
            close(con);
        }
    }
}
