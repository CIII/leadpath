package com.pony.email;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 11/26/13
 * Time: 3:41 PM
 */
public class EmailTargetQueue {

    private final Long id, fromPublisherListId, toPublisherListId, maxArrivalId;
    private final String name;
    private final int status;
    private final int openWithinDays, sendFrequencyDays;

    private EmailTargetQueue(Long id, String name, Long fromPublisherListId, Long toPublisherListId, Long maxArrivalId, int status, int openWithinDays, int sendFrequencyDays) {
        this.id = id;
        this.fromPublisherListId = fromPublisherListId;
        this.toPublisherListId = toPublisherListId;
        this.maxArrivalId = maxArrivalId;
        this.name = name;
        this.status = status;
        this.openWithinDays = openWithinDays;
        this.sendFrequencyDays = sendFrequencyDays;
    }

    public static EmailTargetQueue create(Long id, String name, Long fromPublisherListId, Long toPublisherListId, Long maxArrivalId, int status, int openWithinDays, int sendFrequencyDays) {
        return new EmailTargetQueue(id, name, fromPublisherListId, toPublisherListId, maxArrivalId, status, openWithinDays, sendFrequencyDays);
    }

    public Long getMaxArrivalId() {
        return maxArrivalId;
    }

    public Long getId() {
        return id;
    }

    public Long getFromPublisherListId() {
        return fromPublisherListId;
    }

    public Long getToPublisherListId() {
        return toPublisherListId;
    }

    public String getName() {
        return name;
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

        EmailTargetQueue that = (EmailTargetQueue) o;

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
        return "EmailTargetQueue{" +
                "id=" + id +
                ", fromPublisherListId=" + fromPublisherListId +
                ", toPublisherListId=" + toPublisherListId +
                ", maxArrivalId=" + maxArrivalId +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

    public int getOpenWithinDays() {
        return openWithinDays;
    }

    public int getSendFrequencyDays() {
        return sendFrequencyDays;
    }
}
