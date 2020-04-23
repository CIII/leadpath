package com.pony.publisher;

import com.pony.advertiser.*;
import com.pony.email.Host;
import com.pony.email.SmtpResponse;
import com.pony.lead.Lead;
import com.pony.validation.ValidationResponse;

import java.util.*;

/**
 * Copyright 2011 PonyMash LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 5:22 PM
 */
public class PublisherResponse {
    public static final PublisherResponse NO_VALID_LEAD_REFERENCE = new PublisherResponse(ValidationResponse.INVALID_LEAD_REF);

    private final ValidationResponse validationResponse;
    //    private final Map<RoutingCandidate, Disposition> dispositions = new HashMap<RoutingCandidate, Disposition>();
    private final List<Disposition> dispositions = new ArrayList<com.pony.advertiser.Disposition>();
    private final Map<Io, Disposition> ioDispositions = new HashMap<Io, Disposition>();
    private final Long leadId;

    private Status status;
    private List<Buyer> preferredBuyers = new ArrayList<Buyer>();
    private PublisherContext context;

    public static final String NO_SMTP_PROVIDER = "No smtp provider";
    public static final String NO_OFFER_AVAILABLE = "No offer available";
    public static final String NO_OFFER_SPLIT = "No offer split found for this offer";

    private PublisherResponse(ValidationResponse vResponse) {
        this(vResponse, null, null, null);
    }

    private PublisherResponse(ValidationResponse vResponse, Map<Io, Disposition> dispositions, Long leadId, List<Buyer> preferredBuyers) {
        this.validationResponse = vResponse;
        this.leadId = leadId;
        if (dispositions != null) {
            this.dispositions.addAll(dispositions.values());
            this.ioDispositions.putAll(dispositions);
        }

        if (preferredBuyers != null) {
            this.preferredBuyers.addAll(preferredBuyers);
        }
    }

    private PublisherResponse(Long leadId) {
        this(null, null, leadId, null);

    }

    public static PublisherResponse createNoopResponse(IPublisherContext context, long messageCount) {
        return new NoopResponse(context.toString() + ": message count=" + messageCount);
    }

    public static PublisherResponse createPostResponseNoWait(Long leadId) {
        return new PublisherResponse(leadId);
    }

    public static PublisherResponse createPostResponseNotValid(ValidationResponse vResponse) {
        return new PublisherResponse(vResponse);
    }

    public static PublisherResponse createPostResponse(ValidationResponse vResponse, Map<RoutingCandidate, Disposition> advertiserResponses) {
        // map from Candidate to Io for map param
        Map<Io, Disposition> ioDispositions = new HashMap<Io, Disposition>();

        for (Map.Entry<RoutingCandidate, Disposition> entry : advertiserResponses.entrySet()) {
            ioDispositions.put(entry.getKey().getIo(), entry.getValue());
        }

        return new PublisherResponse(vResponse, ioDispositions, null, null);
    }

    public static PublisherResponse createValidationResponse(IPublisherContext context) {
        //todo: this is for a validation request
        throw new UnsupportedOperationException("validation requests are not yet supported");
    }

    public static PublisherResponse createEmailResponse(IPublisherContext context, SmtpResponse smtpResponse) {
        return new MailResponse(context, smtpResponse);
    }

    public static PublisherResponse createEmailResponseNotValid(IPublisherContext context, ValidationResponse vResponse) {
        return new ErrorResponse(context, vResponse);
    }

    public static PublisherResponse createDupResponse(IPublisherContext context) {
        return new DupResponse(context.getUserProfile().getEmail());
    }

    public static PublisherResponse createErrorResponse(IPublisherContext context, String error) {
        return new ErrorResponse(context.getUserProfile().getEmail(), error);
    }

    public static PublisherResponse createLimitExceededResponse(Host host, Long count) {
        return new SendLimitsExceeded(host, count);
    }

    public static PublisherResponse createPingResponse(Lead lead, ValidationResponse vResponse, Map<RoutingCandidate, Disposition> responses) {
        // map from Candidate to Io for map param
        Map<Io, Disposition> ioDispositions = new HashMap<Io, Disposition>();

        for (Map.Entry<RoutingCandidate, Disposition> entry : responses.entrySet()) {
            ioDispositions.put(entry.getKey().getIo(), entry.getValue());
        }
        return new PublisherResponse(vResponse, ioDispositions, lead.getId(), null);
    }

    public static PublisherResponse createPingResponseNoWait(IPublisherContext publisherContext, ValidationResponse vResponse) {
        return new PublisherResponse(vResponse, null, publisherContext.getLead().getId(), null);
    }

    public static PublisherResponse createPollResponse(ValidationResponse validationResponse, Long leadId, PollResponse pollResponse) {
        if (pollResponse == null) {
            return new PublisherResponse(validationResponse, null, leadId, null);
        }
        return new PublisherResponse(validationResponse, pollResponse.getOrderDispositions(), leadId, pollResponse.getPreferredBuyers());
    }

    public static PublisherResponse createSubscriptionResponse(String mailChimpResponse){
    	return new SubscriptionResponse(mailChimpResponse);
    }
    
    public Long getLeadId() {
        return leadId;
    }

    public List<Disposition> getDispositions() {
        return dispositions;
    }

    public Map<Io, Disposition> getIoDispositions() {
        return ioDispositions;
    }

    public Status getStatus() {
        return status;
    }

    public List<Buyer> getPreferredBuyers() {
        return Collections.unmodifiableList(preferredBuyers);
    }

    @Override
    public String toString() {
        //TODO: add case !?
        return "status=" + status + ":leadId=" + leadId;
    }

    public boolean isValid() {
        return validationResponse == null ? true : validationResponse.isValid(); // If there is no validation response, it's because we moved passed the validation and didn't include it.
    }

    public int getValidationCode() {
        return validationResponse == null ? -1 : validationResponse.getCode();
    }
    
    public String getValidationText() {
    	return validationResponse == null ? null : validationResponse.getName();
    }

    private static class SendLimitsExceeded extends PublisherResponse {

        private final Host host;
        private final Long count;

        private SendLimitsExceeded(Host host, Long count) {
            super((Long) null);

            this.host = host;
            this.count = count;
        }

        @Override
        public String toString() {
            return "SendLimitsExceeded{" +
                    "host=" + host +
                    ", count=" + count +
                    '}';
        }
    }

    private static class NoopResponse extends PublisherResponse {

        private final String message;

        private NoopResponse(String message) {
            super((Long) null);
            this.message = message;
        }

        @Override
        public String toString() {
            return "NOOP response:" + message;
        }
    }

    private static class DupResponse extends PublisherResponse {
        private final String email;

        private DupResponse(String email) {
            super((Long) null);

            this.email = email;
        }

        @Override
        public String toString() {
            return "Duplicate for email='" + email + "'";
        }
    }

    private static class ErrorResponse extends PublisherResponse {
        private final String email, error;

        private ErrorResponse(String email, String error) {
            super((Long) null);
            this.email = email;
            this.error = error;
        }

        public ErrorResponse(IPublisherContext context, ValidationResponse vResponse) {
            super((Long) null);
            this.email = context.getUserProfile().getEmail();
            this.error = vResponse.toString();
        }

        @Override
        public String toString() {
            return "ErrorResponse{" +
                    "email:'" + email + '\'' +
                    ", error:'" + error + '\'' +
                    '}';
        }
    }

    private static class MailResponse extends PublisherResponse {
        private final Long messageId;
        private final SmtpResponse smtpResponse;

        private MailResponse(IPublisherContext context, SmtpResponse smtpResponse) {
            super(context.getLead().getId());
            //TODO
            //smtpResponse
            this.messageId = context.getMessageId();
            this.smtpResponse = smtpResponse;
        }

        @Override
        public String toString() {
            return "MailResponse{" +
                    "messageId=" + messageId +
                    ", smtpResponse=" + smtpResponse +
                    '}';
        }
    }
    
    private static class SubscriptionResponse extends PublisherResponse {
    	private String mailChimpResponse;
        private SubscriptionResponse(String mailChimpResponse) {
            super((Long)null);
            this.mailChimpResponse = mailChimpResponse;
        }

        @Override
        public String toString() {
            return mailChimpResponse;
        }
    }
}
