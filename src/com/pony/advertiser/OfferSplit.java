package com.pony.advertiser;

/**
 * PonyLeads 2012
 * User: martin
 * Date: 7/3/12
 * Time: 3:00 PM
 */
public class OfferSplit {
    private Long creativeId;
    private Long hostId;
    private Long listSplitId;

    private OfferSplit(Long listSplitId, Long hostId, Long creativeId) {
        this.listSplitId = listSplitId;
        this.creativeId = creativeId;
        this.hostId = hostId;
    }

    public Long getCreativeId() {
        return creativeId;
    }

    public Long getHostId() {
        return hostId;
    }

    public Long getListSplitId() {
        return listSplitId;
    }

    public static OfferSplit create(Long listSplitId, Long hostId, Long creativeId) {
        return new OfferSplit(listSplitId, hostId, creativeId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OfferSplit that = (OfferSplit) o;

        if (!creativeId.equals(that.creativeId)) {
            return false;
        }
        if (!hostId.equals(that.hostId)) {
            return false;
        }
        if (!listSplitId.equals(that.listSplitId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = creativeId.hashCode();
        result = 31 * result + hostId.hashCode();
        result = 31 * result + listSplitId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OfferSplit{" +
                "creativeId=" + creativeId +
                ", hostId=" + hostId +
                ", listSplitId=" + listSplitId +
                '}';
    }
}
