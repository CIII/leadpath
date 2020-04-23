package com.pony.advertiser;

import java.math.BigDecimal;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 7/6/13
 * Time: 12:49 PM
 */
public class Buyer {

    private final String buyerId, name, zipcode;
    private Long id; // the advertiser disposition buyer id (i.e. our unique id of this buyer for this disposition)
    private String distance, reservationId, groupId, maxPosts, address, city, state, contactName, contactPhone, longitude, latitude, programId, buyerCode;
    private BigDecimal price;
    private boolean preferred = false;

    public static Buyer create(String buyerId, String name, String zipcode) {
        if (buyerId == null || "".equals(buyerId)) {
            throw new IllegalArgumentException("No valid buyerId provided");
        }
        return new Buyer(buyerId, name, zipcode);
    }

    private Buyer(String buyerId, String name, String zipcode) {
        this.buyerId = buyerId;
        this.name = name;
        this.zipcode = zipcode;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPrice(String price) {
        if (price == null || "".equals(price)) {
            return;
        }
        this.price = new BigDecimal(price.trim());
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setMaxPosts(String maxPosts) {
        this.maxPosts = maxPosts;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDistance() {
        return distance;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getMaxPosts() {
        return maxPosts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the advertiser_disposition_buyers.id
     */
    public Long getId() {
        return id;
    }

    public String getBuyerId() {
        return buyerId == null ? name : buyerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getState() {
        return state;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getBuyerCode() {
        return buyerCode;
    }

    public void setBuyerCode(String buyerCode) {
        this.buyerCode = buyerCode;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public boolean isExclusive() {
        if (maxPosts == null || "".equals(maxPosts)) {
            return false;
        }

        return Integer.valueOf(maxPosts) == 1;
    }

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Buyer buyer = (Buyer) o;

        boolean buyerIdEqual = buyerId.equals(buyer.buyerId);
        boolean buyerCodeEqual = buyerCode != null ? buyerCode.equals(buyer.buyerCode) : buyer.buyerCode == null;
        return buyerIdEqual && buyerCodeEqual;
    }

    @Override
    public int hashCode() {
        return buyerId.hashCode();
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "id='" + id + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", state='" + state + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
