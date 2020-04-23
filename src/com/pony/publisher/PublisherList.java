package com.pony.publisher;

import java.util.List;

import com.pony.lead.LeadType;

/**
 * The PublisherList controls what publisher is allowed to post to what publisher list, and what LeadType the data is meant to be fore.
 * <p/>
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 6/6/12
 * Time: 10:31 PM
 */
public class PublisherList {
    private Long id;
    private LeadType leadType;
    private String name;

    /**
     * the listid parameter to share with publishers (what they send on the url when they post to us)
     */
    private String externalId;
    private Status status;
    private int maxLeadUnits;
    private boolean direct;
    private List<Integer> orders;
    private List<Integer> publishers;
    
    public PublisherList(Long id, LeadType leadType, String name, String externalId, Status status, int maxLeadUnits, boolean direct, List<Integer> orders, List<Integer> publishers) {
        this.id = id;
        this.leadType = leadType;
        this.name = name;
        this.externalId = externalId;
        this.status = status;
        this.maxLeadUnits = maxLeadUnits;
        this.direct = direct;
        this.orders = orders;
        this.publishers = publishers;
    }

    public static PublisherList create(Long id, LeadType leadType, String name, String externalId, Status status, int maxLeadUnits, boolean direct) {
        return new PublisherList(id, leadType, name, externalId, status, maxLeadUnits, direct, null, null);
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }

    public LeadType getLeadType() {
        return leadType;
    }

    public String getName() {
        return name;
    }

    public String getExternalId() {
        return externalId;
    }

    public Status getStatus() {
        return status;
    }

    public int getMaxLeadUnits() {
        return maxLeadUnits;
    }

    public boolean isDirect() {
        return direct;
    }

    /**
     * This is not necessarily implemented in most of the application. If this is null, it indicates that the order list
     * was not populated, not that there are no orders.
     * @return
     */
    public List<Integer> getOrders() {
		return orders;
	}

    /**
     * This is not necessarily implemented in most of the application. If this is null, it indicates that the publisher
     * list was not populated, not that there are no publishers.
     * @return
     */
	public List<Integer> getPublishers() {
		return publishers;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PublisherList that = (PublisherList) o;

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
        return "PublisherList{" +
                "id=" + id +
                ", leadType=" + leadType +
                ", name='" + name + '\'' +
                ", externalId='" + externalId + '\'' +
                ", status=" + status +
                ", direct=" + direct + '}';
    }
}
