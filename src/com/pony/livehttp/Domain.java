package com.pony.livehttp;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 11/28/12
 * Time: 5:27 PM
 */
public class Domain {
    private final Long id;
    private final String label, topLevelDomain;

    private Domain(Long id, String label, String topLevelDomain) {
        this.id = id;
        this.label = label;
        this.topLevelDomain = topLevelDomain;
    }

    public static Domain create(Long id, String label, String topLevelDomain) {
        return new Domain(id, label, topLevelDomain);
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getTopLevelDomain() {
        return topLevelDomain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Domain domain = (Domain) o;

        if (id != null ? !id.equals(domain.id) : domain.id != null) {
            return false;
        }
        if (!label.equals(domain.label)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + label.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", topLevelDomain='" + topLevelDomain + '\'' +
                '}';
    }
}
