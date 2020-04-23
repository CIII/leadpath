package com.pony.advertiser;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 7/9/13
 * Time: 6:39 PM
 */
public class BuyerDisposition {
    private final Long id, orderId, leadMatchId;
    private final String reservationId, buyerId, price;
    protected boolean exclusive;

    private BuyerDisposition(Long id, Long orderId, Long leadMatchId, String buyerId, String reservationId, String price, boolean exclusive) {
        this.id = id;
        this.orderId = orderId;
        this.leadMatchId = leadMatchId;
        this.reservationId = (reservationId == null ? buyerId : reservationId);
        this.buyerId = buyerId;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getPrice() {
        return price;
    }

    public Long getLeadMatchId() {
        return leadMatchId;
    }

    public boolean isExclusive() {
		return exclusive;
	}

	public void setExclusive(boolean exclusive) {
		this.exclusive = exclusive;
	}

	public static BuyerDisposition create(ResultSet rs) throws SQLException {
        return new BuyerDisposition(rs.getLong("id"), rs.getLong("order_id"), rs.getLong("lead_match_id"), rs.getString("buyer_id"), rs.getString("reservation_id"), rs.getString("price"), rs.getBoolean("exclusive"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuyerDisposition that = (BuyerDisposition) o;

        if (!leadMatchId.equals(that.leadMatchId)) {
            return false;
        }
        if (!orderId.equals(that.orderId)) {
            return false;
        }
        return reservationId.equals(that.reservationId);
    }

    @Override
    public int hashCode() {
        int result = orderId.hashCode();
        result = 31 * result + leadMatchId.hashCode();
        result = 31 * result + reservationId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BuyerDisposition{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", leadMatchId=" + leadMatchId +
                ", reservationId='" + reservationId + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
