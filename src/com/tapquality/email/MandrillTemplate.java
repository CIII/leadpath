package com.tapquality.email;

/**
 * Data construct containing the information about a Mandrill template, who is is applicable to,
 * and what kind of delay / configuration it requires.
 * 
 * @author dmcguire
 *
 */
public class MandrillTemplate {
	private long id;
	private String name;
	private int delayMinutes;
	private long advertiserId;
	private long buyerId;
	private int order;
	private String domain;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getAdvertiserId() {
		return advertiserId;
	}
	public void setAdvertiserId(long advertiserId) {
		this.advertiserId = advertiserId;
	}
	public long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(long buyerId) {
		this.buyerId = buyerId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public int getDelayMinutes() {
		return delayMinutes;
	}
	public void setDelayMinutes(int delayMinutes) {
		this.delayMinutes = delayMinutes;
	}
}

