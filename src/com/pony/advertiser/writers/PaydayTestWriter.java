package com.pony.advertiser.writers;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.leadtypes.PaydayLead;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationResponse;

import java.util.Map;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/12/11
 * Time: 11:50 PM
 */
public class PaydayTestWriter extends AdvertiserWriter {
    public static final String URL = "http://mycashfriend.com/confirmation.php";

    public PaydayTestWriter() {

    }

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {
        // make the actual call to the partner to send the lead

        Lead lead = publisherContext.getLead();
        Arrival arrival = publisherContext.getArrival();

        //System.out.println("writing lead to partner");
        //Disposition.Status status = Math.random() > 0.5 ? Disposition.ACCEPTED : Disposition.REJECTED;
        Disposition.Status status = Disposition.Status.REJECTED;

        if (lead instanceof PaydayLead) {
            PaydayLead pdLead = (PaydayLead) lead;
            if ("test".equals(pdLead.getFirstName()) && "accept".equals(pdLead.getLastName())) {
                status = Disposition.Status.ACCEPTED;
            }
        }

        //Disposition.DispositionCategory category = Disposition.DispositionCategory.DUPLICATE;
        Disposition.DispositionCategory category = null;

        Map<String, String> m = lead.toMap();
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : m.entrySet()) {
            if (query.length() == 0) {
                query.append("?");
            }
            else {
                query.append("&");
            }
            query.append(entry.getKey()).append("=").append(entry.getValue());
        }

        String comment = URL + query.toString();

        return Disposition.createPost(status, category, comment);
    }
}

