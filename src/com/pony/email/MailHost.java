package com.pony.email;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 12/11/12
 * Time: 12:14 PM
 */
public class MailHost {

    private final boolean mxValidated;
    private final String domain;
    private final Long id;

    private MailHost(Long id, String domain, boolean mxValidated) {
        this.mxValidated = mxValidated;
        this.domain = domain;
        this.id = id;
    }

    public static MailHost create(Long id, String domain, boolean mxValidated) {
        return new MailHost(id, domain, mxValidated);
    }

    public boolean isMxValidated() {
        return mxValidated;
    }

    public String getDomain() {
        return domain;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "MailHost{" +
                "mxValidated=" + mxValidated +
                ", domain='" + domain + '\'' +
                ", id=" + id +
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

        MailHost mailHost = (MailHost) o;

        if (!domain.equals(mailHost.domain)) {
            return false;
        }
        return !(id != null ? !id.equals(mailHost.id) : mailHost.id != null);
    }

    @Override
    public int hashCode() {
        int result = domain.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
