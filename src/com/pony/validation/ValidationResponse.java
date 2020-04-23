package com.pony.validation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 6:45 PM
 */
public class ValidationResponse {
    private final boolean valid;
    private final String name;
    private final int code;

    private final Map<String, ValidationScore> scores = new HashMap<String, ValidationScore>();

    private ValidationResponse(int code, String name, boolean valid) {
        this.valid = valid;
        this.name = name;
        this.code = code;
    }

    public static final ValidationResponse NOOP = ValidationResponse.create(0, "NoopValidationResponse", true);

    public static final ValidationResponse WRONG_TYPE = ValidationResponse.create(1, "wrong type");

    // email stuff
    public static final ValidationResponse SUPPRESSED = ValidationResponse.create(2, "suppressed email");

    public static final ValidationResponse KNOWN_TRAP = ValidationResponse.create(3, "known trap email");
    public static final ValidationResponse BOUNCED = ValidationResponse.create(4, "bounced email");
    public static final ValidationResponse UNSUBSCRIBED = ValidationResponse.create(5, "unsubscribed email");
    public static final ValidationResponse COMPLAINT = ValidationResponse.create(6, "complained email");
    public static final ValidationResponse DUP = ValidationResponse.create(7, "duplicate_lead");

    public static final ValidationResponse INVALID_EMAIL = ValidationResponse.create(8, "invalid_email");
    public static final ValidationResponse INVALID_MX = ValidationResponse.create(9, "no DNS MX record");
    public static final ValidationResponse SEND_LIMIT_EXCEEDED = ValidationResponse.create(10, "Host daily send limit exceeded");

    public static final ValidationResponse INVALID_LEAD_REF = ValidationResponse.create(12, "NoValidLeadReference", false);
    public static final ValidationResponse INVALID_ZIP = ValidationResponse.create(13, "invalid_zip");
    public static final ValidationResponse INVALID_STREET = ValidationResponse.create(14, "invalid_street");
    public static final ValidationResponse INVALID_ELECTRIC_BILL = ValidationResponse.create(15,  "Invalid electric bill. Required for submission to MediaAlpha.");
    public static final ValidationResponse INVALID_ELECTRIC_COMPANY = ValidationResponse.create(16, "Invalid electric company. Rquired for submission to MediaAlpha.");
    public static final ValidationResponse INVALID_LEADID_TOKEN = ValidationResponse.create(17, "LeadId token required.");
    public static final ValidationResponse INVALID_HOMEOWNERSHIP = ValidationResponse.create(18, "Homeownership status required.");
    public static final ValidationResponse INVALID_IP = ValidationResponse.create(19, "Invalid IP. Required for submission to MediaAlpha.");
    public static final ValidationResponse INVALID_LOCAL_HOUR = ValidationResponse.create(20, "Invalid local hour. Required for submission to MediaAlpha.");
    public static final ValidationResponse MISSING_EMAIL = ValidationResponse.create(21, "missing_email");
    public static final ValidationResponse INVALID_PHONE_CONTACT_SCORE = ValidationResponse.create(22,  "invalid_phone_contact_score");

    public static ValidationResponse create(int code, String name) {
        return create(code, name, false);
    }

    public static ValidationResponse create(int code, String name, boolean valid) {
        return new ValidationResponse(code, name, valid);
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean isValid() {
        return valid;
    }

    public Map<String, ValidationScore> getScores() {
        return Collections.unmodifiableMap(scores);
    }

    public void addScore(String name, ValidationScore score) {
        scores.put(name, score);
    }

    public ValidationScore getScore(String name) {
        return scores.get(name);
    }

    @Override
    public String toString() {
        return "ValidationResponse{" +
                "valid=" + valid +
                ", name='" + name + '\'' +
                ", code=" + code +
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

        ValidationResponse that = (ValidationResponse) o;

        if (code != that.code) {
            return false;
        }
        if (!name.equals(that.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + code;
        return result;
    }
}
