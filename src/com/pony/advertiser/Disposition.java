package com.pony.advertiser;

import com.pony.core.PonyPhase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/12/11
 * Time: 7:10 PM
 */
public class Disposition {

    private final Status status;
    private final DispositionCategory category;
    private final PonyPhase phase;
    private final String externalId, comment;
    private final boolean requiresExclusivity;

    private BigDecimal price;
    private Long id, leadMatchId;
    private String state;

//    private BuyerDisposition buyerDisposition;

    private final List<Buyer> buyers = new ArrayList<Buyer>();

    private Disposition(PonyPhase phase, Status status, DispositionCategory category, String externalId, BigDecimal price, String comment, boolean requiresExclusivity) {
        this.externalId = ("".equals(externalId) ? null : externalId);
        this.status = status;
        this.category = category;
        this.comment = comment;
        this.price = price;
        this.phase = phase;
        this.requiresExclusivity = requiresExclusivity;
    }

    private Disposition(PonyPhase phase, Status status, DispositionCategory category, String externalId, String comment, boolean requiresExclusivity) {
        this(phase, status, category, externalId, null, comment, requiresExclusivity);
    }

    public static Disposition createPing(Status status, DispositionCategory category, String externalId, BigDecimal price, String comment) {
        return new Disposition(PonyPhase.PING, status, category, externalId, price, comment, false);
    }

    public static Disposition createPost(Status status, DispositionCategory category, String externalId, BigDecimal price, String comment) {
        return new Disposition(PonyPhase.POST, status, category, externalId, price, comment, false);
    }

    public static Disposition createPing(Status status, DispositionCategory category, String externalId, String comment) {
        return new Disposition(PonyPhase.PING, status, category, externalId, comment, false);
    }

    public static Disposition createPost(Status status, DispositionCategory category, String externalId, String comment) {
        return new Disposition(PonyPhase.POST, status, category, externalId, comment, false);
    }

    public static Disposition createPing(Status status, DispositionCategory category, String comment) {
        return new Disposition(PonyPhase.PING, status, category, null, comment, false);
    }

    public static Disposition createPost(Status status, DispositionCategory category, String comment) {
        return new Disposition(PonyPhase.POST, status, category, null, comment, false);
    }

    public static Disposition create(PonyPhase phase, Status status, DispositionCategory category) {
        return new Disposition(phase, status, category, null, null, false);
    }
    
    public static Disposition create(PonyPhase phase, Status status, DispositionCategory category, String comment) {
    	return new Disposition(phase, status, category, null, comment, false);
    }

    public static Disposition create(PonyPhase phase, Status status) {
        return new Disposition(phase, status, null, null, null, false);
    }

    public static Disposition create(PonyPhase phase, Status status, boolean requiresExclusivity) {
        return new Disposition(phase, status, null, null, null, requiresExclusivity);
    }

    public static Disposition create(PonyPhase phase, Status status, boolean requiresExclusivity, BigDecimal price, String comment) {
        return new Disposition(phase, status, null, null, price, comment, requiresExclusivity);
    }
    
    public static Disposition create(PonyPhase phase, Status status, boolean requiresExclusivity, BigDecimal price, String externalId, String comment) {
    	return new Disposition(phase, status, null, externalId, price, comment, requiresExclusivity);
    }

    public static Disposition create(PonyPhase phase, Status status, boolean requiresExclusivity, BigDecimal price) {
//        return new Disposition(phase, status, null, null, null, requiresExclusivity);
//        return new Disposition(phase, status, null, null, null, requiresExclusivity);
        return new Disposition(phase, status, null, null, price, null, requiresExclusivity);
    }

    public static Disposition create(PonyPhase phase, Status status, String comment) {
        return new Disposition(phase, status, null, null, comment, false);
    }

    public static Disposition createPing(Status status) {
        return new Disposition(PonyPhase.PING, status, null, null, null, false);
    }

    public static Disposition createPost(Status status) {
        return new Disposition(PonyPhase.POST, status, null, null, null, false);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setLeadMatchId(Long leadMatchId) {
        this.leadMatchId = leadMatchId;
    }

    public PonyPhase getPhase() {
        return phase;
    }

    public Long getLeadMatchId() {
        return leadMatchId;
    }

    public boolean isAccepted() {
        return status == Status.ACCEPTED;
    }

    public boolean isUnsold() {
        return status == Status.UNSOLD;
    }

    public Status getStatus() {
        return status;
    }

    public BigDecimal getPrice() {
        return price == null ? BigDecimal.ZERO : price;
    }

    public String getExternalId() {
        return externalId;
    }

    public DispositionCategory getCategory() {
        return category;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (Buyer buyer : buyers) {
            b.append(buyer).append(";");
        }

        return "Disposition{" +
                "status=" + status +
                ", category=" + category +
                ", id='" + id + '\'' +
                ", comment='" + comment + '\'' +
                ", price=" + price +
                ", requiresExclusivity=" + requiresExclusivity +
                ", buyers=" + buyers.size() +
                ", externalId=" + externalId +
                ": " + b.toString() +
                "}";
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean requiresExclusivity() {
        return requiresExclusivity;
    }

    public boolean hasBuyers() {
        return buyers.size() > 0;
    }

    public List<Buyer> getBuyers() {
        return buyers;
    }

    public void addBuyer(Buyer buyer) {
        buyers.add(buyer);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public static class Status {
        private final int status;

        private final static Map<Integer, Status> stats = new HashMap<Integer, Status>();
        public static final Status ACCEPTED = new Status(1);
        public static final Status REJECTED = new Status(0);
        public static final Status UNSOLD = new Status(2);
        public static final Status UNSUPPORTED = new Status(-1);
        public static final Status NO_COVERAGE = new Status(3);
        public static final Status RETURNED = new Status(4);
        /**
         * This status represents a notification that the customer that bought the lead was successful in converting the
         * lead into a sale.
         */
        public static final Status CONTACTED = new Status(5);
        @Deprecated
        public static final Status QUOTED = new Status(6);
        @Deprecated
        public static final Status APPOINTMENT_SET = new Status(7);
        @Deprecated
        public static final Status APPOINTMENT_COMPLETED = new Status(8);
        public static final Status CONTRACT_SIGNED = new Status(9);
        public static final Status INSTALLED = new Status(10);
        public static final Status LOST = new Status(11);
        public static final Status BAD_INFO = new Status(12);
        public static final Status INVALID = new Status(13);
        public static final Status APPOINTMENT_1_SET = new Status(14);
        public static final Status APPOINTMENT_2_SET = new Status(15);
        public static final Status DUPLICATE = new Status(16);
        public static final Status TEST_DETECTED = new Status(99);

        private Status(int status) {
            this.status = status;
            stats.put(status, this);
        }

        public static Status parse(int status) {
            return stats.get(status);
        }

        public int getStatus() {
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

            Status that = (Status) o;

            if (status != that.status) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return status;
        }

        @Override
        public String toString() {
            return "Status{" + status +
                    '}';
        }
    }

    /**
     * further details on rejections (rejection reasons ...)
     */
    public static class DispositionCategory {
        private final int id;
        private final String name;

        private static final Map<String, DispositionCategory> categories = new HashMap<String, DispositionCategory>();

        public static final DispositionCategory ERROR = new DispositionCategory(0, "Error");
        public static final DispositionCategory DUPLICATE = new DispositionCategory(1, "Duplicate");
        public static final DispositionCategory NO_MATCH = new DispositionCategory(2, "No Match");
        public static final DispositionCategory AWAITING = new DispositionCategory(3, "Awaiting Decision");
        public static final DispositionCategory RETRY = new DispositionCategory(4, "Retry");

        private DispositionCategory(int category, String name) {
            this.id = category;
            this.name = name;
            categories.put(name.toLowerCase(), this);
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            DispositionCategory that = (DispositionCategory) o;

            if (id != that.id) {
                return false;
            }
            if (!name.equals(that.name)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + name.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "DispositionCategory{" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        public static DispositionCategory parse(String name) {
            if (name == null) {
                return null;
            }
            return categories.get(name.toLowerCase());
        }
    }
}
