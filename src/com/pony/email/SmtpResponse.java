package com.pony.email;

import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;

/**
 * User: martin
 * Date: 7/3/12
 * Time: 3:03 PM
 */
public class SmtpResponse {
    private final IPublisherContext context;
    private final Message message;
    private final Long externalId;

    private SmtpResponse(IPublisherContext context, Message message, Long externalId) {
        this.context = context;
        this.message = message;
        this.externalId = externalId;

    }

    public static SmtpResponse create(IPublisherContext context, Message message, Long externalId) {
        if (context == null) {
            throw new IllegalArgumentException("no context");
        }
        if (message == null) {
            throw new IllegalArgumentException("no message");
        }
        return new SmtpResponse(context, message, externalId);
    }

    public static SmtpResponse create(PublisherContext context, Message message) {
        return new SmtpResponse(context, message, null);
    }

    @Override
    public String toString() {
        return "SmtpResponse{" +
                "context=" + context +
                ", message=" + message +
                '}';
    }
}
