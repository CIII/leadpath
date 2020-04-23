package com.pony.email;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 9/13/12
 * Time: 3:09 PM
 */
public class ResendCandidate {
    private ResendMessagePhase.Phase phase;
    private final String email;
    private final boolean wasOpened;
    private final Long userProfileId, firstMessageId, creativeId, hostId, publisherId, leadTypeId, arrivalId;


    private ResendCandidate(Long userProfileId, String email, Long firstMessageId, boolean wasOpened, Long creativeId, Long hostId, Long publisherId, ResendMessagePhase.Phase phase, Long leadTypeId, Long arrivalId) {
        this.email = email;
        this.wasOpened = wasOpened;
        this.userProfileId = userProfileId;
        this.firstMessageId = firstMessageId;
        this.creativeId = creativeId;
        this.hostId = hostId;
        this.publisherId = publisherId;
        this.phase = phase;
        this.leadTypeId = leadTypeId;
        this.arrivalId = arrivalId;
    }

    public static ResendCandidate create(Long userProfileId, String email, Long firstMessageId, boolean wasOpened, Long creativeId, Long hostId, Long publisherId, ResendMessagePhase.Phase phase, Long leadTypeId, Long arrivalId) {
        return new ResendCandidate(userProfileId, email, firstMessageId, wasOpened, creativeId, hostId, publisherId, phase, leadTypeId, arrivalId);
    }

    public String getEmail() {
        return email;
    }

    public boolean wasOpened() {
        return wasOpened;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public Long getFirstMessageId() {
        return firstMessageId;
    }

    public Long getCreativeId() {
        return creativeId;
    }

    public Long getHostId() {
        return hostId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public Long getLeadTypeId() {
        return leadTypeId;
    }

    public ResendMessagePhase.Phase getPhase() {
        return phase;
    }

    public Long getArrivalId() {
        return arrivalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResendCandidate that = (ResendCandidate) o;

        if (!firstMessageId.equals(that.firstMessageId)) {
            return false;
        }
        if (!userProfileId.equals(that.userProfileId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = userProfileId.hashCode();
        result = 31 * result + firstMessageId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ResendCandidate{" +
                "email='" + email + '\'' +
                ", wasOpened=" + wasOpened +
                ", userProfileId=" + userProfileId +
                ", firstMessageId=" + firstMessageId +
                ", creativeId=" + creativeId +
                ", hostId=" + hostId +
                '}';
    }

    /**
     * use the existing candidate to create a new one for the next phase (based on the current phase of the candidate)
     *
     * @return
     */
    public ResendCandidate moveToNextPhase() {
        if (this.phase.getNextPhase() == null) {
            return moveToEndPhase();
        }

        this.phase = this.phase.getNextPhase();
        return this;
    }

    public ResendCandidate moveToEndPhase() {
        this.phase = ResendMessagePhase.END;
        return this;
    }
}
