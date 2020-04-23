package com.pony.email;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 7/31/12
 * Time: 5:17 PM
 */
public class SmtpException extends Exception {

    public SmtpException(Throwable e) {
        super(e);
    }

    public SmtpException(String msg) {
        super(msg);
    }
}
