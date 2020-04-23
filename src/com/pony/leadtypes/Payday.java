package com.pony.leadtypes;

import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationResponse;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;

/**
 * Copyright 2011 PonyMash LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 6:13 PM
 */
public class Payday extends LeadType {
    private static final String name = "Payday";

    public Payday(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    @Override
    public Lead parseLead(UserProfile userProfile, Arrival arrival, HttpServletRequest request, Map<String, String> params) {
        Map<String, String> attributes = parseLeadAttributes(request, params);
        //return PaydayLead.create(getId(), userProfile, arrival, attributes);
        //TODO
        return null;
    }

    @Override
    public Lead parseLead(UserProfile userProfile, Arrival arrival, HttpServletRequest request) {
        return parseLead(userProfile, arrival, request, null);
    }

    @Override
    public Lead persistLead(UserProfile userProfile, Arrival arrival, Lead lead) throws SQLException {
        if (!(lead instanceof PaydayLead)) {
            throw new IllegalArgumentException("not a payday lead");
        }
        //return LeadModel.persist(lead);
        return null;
    }

    @Override
    public Lead findLead(Long leadId) {
        // TODO
        return null;
    }

    @Override
    public ValidationResponse validate(IPublisherContext publisherContext) {
        //todo
        if (publisherContext.isPost()) {
            publisherContext.getLead();
            publisherContext.getChannel();
        }

        return ValidationResponse.NOOP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Payday payday = (Payday) o;

        if (!getId().equals(payday.getId())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + getId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Payday{" +
                "name='" + name + '\'' +
                ", id=" + getId() +
                '}';
    }
}
