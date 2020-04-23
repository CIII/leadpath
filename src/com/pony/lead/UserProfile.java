package com.pony.lead;

import com.pony.email.MailHost;

import javax.servlet.http.HttpServletRequest;

/**
 * PonyLeads 2012
 * User: martin
 * Date: 7/1/12
 * Time: 11:45 AM
 */
public class UserProfile {
    public static final String EMAIL = "email";

    private Long id;
    private final String email;
    private String emailMd5;
    private final boolean isTrap;
    //    private boolean mxValidated;
    private MailHost mailHost;

    public UserProfile(String email, boolean isTrap, MailHost mailHost) {
        this.email = email;
        this.isTrap = isTrap;
        //parseMailHost(this);
        this.mailHost = mailHost;
    }

    public UserProfile(Long id, String email, boolean isTrap, MailHost mailHost) {
        this(email, isTrap, mailHost);
        this.id = id;
    }

    public static String[] parseMailLabels(String email) {

        String[] mailTokens = email.split("@");

        if (mailTokens.length == 2) {
            return mailTokens;
        }

        throw new IllegalArgumentException("invalid email address:" + email);
    }

    public static String parseMailHost(String emailOrDomain) {

        String[] mailTokens = emailOrDomain.split("@");

        if (mailTokens.length == 2) {
            return mailTokens[1];
        }

        return emailOrDomain;
    }

    public static String parseMailHost(UserProfile up) {
        if (up.getMailHost() != null) {
            return up.getMailHost().getDomain();
        }

        String[] mailTokens = up.getEmail().split("@");
        if (mailTokens.length != 2) {
            throw new IllegalArgumentException("Invalid email address: " + up.getEmail());
        }

        return mailTokens[1];
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getEmailMd5() {
        return emailMd5;
    }

    public boolean isTrap() {
        return isTrap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserProfile that = (UserProfile) o;

        return email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", isTrap=" + isTrap +
                ", mailHost=" + mailHost +
                '}';
    }

    public static String parse(HttpServletRequest request) {
        // parse the email from the request
        String email = request.getParameter(EMAIL);
        if (email != null && !"".equals(email)) {
            return email;
        }

        return null;
    }

    public static UserProfile create(Long id, String email, boolean isTrap, MailHost mailHost) {
        return new UserProfile(id, email, isTrap, mailHost);
    }

    public MailHost getMailHost() {
        return mailHost;
    }
}

