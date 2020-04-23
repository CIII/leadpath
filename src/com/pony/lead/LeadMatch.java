package com.pony.lead;

import com.pony.advertiser.Disposition;
import com.pony.core.PonyPhase;
import com.pony.tools.MathTool;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 6/26/13
 * Time: 6:49 PM
 */
public class LeadMatch {
	private static final Log LOG = LogFactory.getLog(LeadMatch.class);
	
    public static final Status PRE_PING = new Status(0, "pre ping");
    public static final Status NOT_INTERESTED = new Status(1, "not interested");
    public static final Status INTERESTED = new Status(2, "interested");

    public static final Status PRE_ROUTE = new Status(3, "pre route");
    public static final Status ACCEPTED = new Status(4, "accepted");
    public static final Status ACTED_UPON = new Status(5, "lead sold");
    public static final Status REJECTED = new Status(-1, "rejected");

    public static final Status RETURNED = new Status(-2, "returned");

    public static final Status CALL_CENTER_VIEW = new Status(10, "call center view");
    public static final Status CALL_CENTER_OPT_IN = new Status(12, "call center opt in");
    public static final String COLUMNS = "lm.id, lm.lead_id, lm.order_id, lm.status, lm.price, lm.external_id";

    private static Map<Disposition.Status, Status> pingStates = new HashMap<Disposition.Status, Status>();
    private static Map<Disposition.Status, Status> postStates = new HashMap<Disposition.Status, Status>();
    private static Map<Disposition.Status, Status> advertiserDispositionStates = new HashMap<Disposition.Status, Status>();
    private static Map<Disposition.Status, Status> commissionStates = new HashMap<Disposition.Status, Status>();

    static {
        pingStates.put(Disposition.Status.ACCEPTED, LeadMatch.INTERESTED);
        pingStates.put(Disposition.Status.REJECTED, LeadMatch.NOT_INTERESTED);
        pingStates.put(Disposition.Status.UNSOLD, LeadMatch.NOT_INTERESTED);
        pingStates.put(Disposition.Status.UNSUPPORTED, LeadMatch.INTERESTED); // when in doubt leave it in
        pingStates.put(Disposition.Status.NO_COVERAGE, LeadMatch.NOT_INTERESTED); // when in doubt leave it in
        pingStates.put(Disposition.Status.TEST_DETECTED, LeadMatch.INTERESTED); // test are testing the positive case

        postStates.put(Disposition.Status.ACCEPTED, LeadMatch.ACCEPTED);
        postStates.put(Disposition.Status.REJECTED, LeadMatch.REJECTED);
        postStates.put(Disposition.Status.UNSOLD, LeadMatch.REJECTED);
        postStates.put(Disposition.Status.UNSUPPORTED, LeadMatch.REJECTED);
        postStates.put(Disposition.Status.TEST_DETECTED, LeadMatch.ACCEPTED); // test are testing the positive case

        advertiserDispositionStates.put(Disposition.Status.ACCEPTED, LeadMatch.ACCEPTED);
        advertiserDispositionStates.put(Disposition.Status.REJECTED, LeadMatch.REJECTED);
        advertiserDispositionStates.put(Disposition.Status.RETURNED, LeadMatch.RETURNED);
        advertiserDispositionStates.put(Disposition.Status.TEST_DETECTED, LeadMatch.ACCEPTED); // test are testing the positive case
        
        commissionStates.put(Disposition.Status.CONTACTED, LeadMatch.ACTED_UPON);
        commissionStates.put(Disposition.Status.QUOTED, LeadMatch.ACTED_UPON);
        commissionStates.put(Disposition.Status.APPOINTMENT_SET, ACTED_UPON);
        commissionStates.put(Disposition.Status.APPOINTMENT_COMPLETED, ACTED_UPON);
        commissionStates.put(Disposition.Status.CONTRACT_SIGNED, ACTED_UPON);
        commissionStates.put(Disposition.Status.INSTALLED, ACTED_UPON);
        commissionStates.put(Disposition.Status.RETURNED, LeadMatch.RETURNED);
        commissionStates.put(Disposition.Status.BAD_INFO,  LeadMatch.ACTED_UPON);
        commissionStates.put(Disposition.Status.LOST, LeadMatch.ACTED_UPON);
        commissionStates.put(Disposition.Status.INVALID, LeadMatch.ACTED_UPON);
        commissionStates.put(Disposition.Status.APPOINTMENT_1_SET, LeadMatch.ACTED_UPON);
        commissionStates.put(Disposition.Status.APPOINTMENT_2_SET, LeadMatch.ACTED_UPON);
        commissionStates.put(Disposition.Status.DUPLICATE, LeadMatch.ACTED_UPON);
    }

    private final Long id;
    private final String externalId;
    private BigDecimal price;
    private final Long orderId;
    private final Long leadId;

    private LeadMatch(Long id, String externalId, Long orderId, BigDecimal price, Long leadId) {
        this.id = id;
        this.externalId = externalId;
        this.price = price;
        this.orderId = orderId;
        this.leadId = leadId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }

    public String getExternalId() {
        return externalId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getLeadId() {
		return leadId;
	}

	public static LeadMatch create(ResultSet rs) throws SQLException {
		LOG.debug(rs.getString("lead_id"));
		LOG.debug(rs.getLong("lead_id"));
        return new LeadMatch(rs.getLong("id"), rs.getString("external_id"), rs.getLong("order_id"), MathTool.getBigDecimalPrice(rs.getBigDecimal("price")), rs.getLong("lead_id"));
    }

    public static class Status {
        private final int status;
        private final String name;

        public int getStatus() {
            return status;
        }

        public String getName() {
            return name;
        }

        private Status(int status, String name) {
            this.status = status;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Status{" +
                    "status=" + status +
                    ", name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Status status1 = (Status) o;
            return status == status1.status;
        }

        @Override
        public int hashCode() {
            return status;
        }
    }

    public static Status mapPingStateToMatchState(PonyPhase phase, Disposition.Status status) {
        if (phase.equals(PonyPhase.PING)) {
            return pingStates.get(status);
        }
        else if (phase.equals(PonyPhase.POST)) {
            return postStates.get(status);
        }
        else if (phase.equals(PonyPhase.ADVERTISER_DISPOSITION)) {
            return advertiserDispositionStates.get(status);
        }
        else if (phase.equals(PonyPhase.REQUEST_PRICE)) {
            return LeadMatch.PRE_ROUTE;
        } else if (phase.equals(PonyPhase.OUTCOME)) {
        	return commissionStates.get(status);
        }

        throw new IllegalArgumentException("do not know how to map phase=" + phase);
    }

    public String toString(){
        return String.format("Lead_Match{id: %d, lead_id: %d, order_id: %d, price %s, external_id: %s}",
                this.id, this.leadId, this.orderId, this.price, this.externalId);
    }

	public void setPrice(BigDecimal newPrice) {
		this.price = newPrice;
	}
}
