package com.pony.email.sendgrid;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 12/6/12
 * Time: 6:30 PM
 */
public class SendGridException extends Exception {
    public SendGridException(Throwable t) {
        super(t);
    }

    public SendGridException(String msg) {
        super(msg);
    }
}
