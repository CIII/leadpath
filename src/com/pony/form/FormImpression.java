package com.pony.form;

import java.util.UUID;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 2/7/13
 * Time: 10:38 AM
 */
public class FormImpression {
    private final Long id, formId, formStepId, formStepGroupId, arrivalId;

    private int impressionCount;
    private String uuid, ipAddress, userAgent, referrer;

    private FormImpression(Long id, Long formId, Long formStepId, Long formStepGroupId, String ipAddress, Long arrivalId, int impressionCount, String uuid, String userAgent, String referrer) {
        this.id = id;
        this.formId = formId;
        this.formStepId = formStepId;
        this.formStepGroupId = formStepGroupId;
        this.ipAddress = ipAddress;
        this.arrivalId = arrivalId;
        this.impressionCount = impressionCount;
        this.uuid = uuid;
        this.userAgent = userAgent;
        this.referrer = referrer;
    }

    public static FormImpression create(Long id, Long formId, Long formStepId, Long formStepGroupId, String ipAddress, Long arrivalId, int impressionCount, String uuid, String userAgent, String referrer) {
        return new FormImpression(id, formId, formStepId, formStepGroupId, ipAddress, arrivalId, impressionCount, uuid, userAgent, referrer);
    }

    public String touch() {
        uuid = FormImpression.newUUID().toString();
        return uuid;
    }

    public Long getId() {
        return id;
    }

    public String getUUID() {
        return uuid;
    }

    public static UUID newUUID() {
        return UUID.randomUUID();
    }

    public Long getFormId() {
        return formId;
    }

    public Long getArrivalId() {
        return arrivalId;
    }

    public int getImpressionCount() {
        return impressionCount;
    }

    public String getUuid() {
        return uuid;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getReferrer() {
        return referrer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormImpression that = (FormImpression) o;

        if (!uuid.equals(that.uuid)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return "FormImpression{" +
                "id=" + id +
                ", formId=" + formId +
                ", arrivalId=" + arrivalId +
                ", impressionCount=" + impressionCount +
                ", uuid='" + uuid + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", referrer='" + referrer + '\'' +
                '}';
    }
}
