package com.pony.livehttp;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 11/28/12
 * Time: 5:20 PM
 */
public class ClickTarget {
    private final Long id, advertiserId;
    private final String name, destinationUrl;

    private ClickTarget(Long id, Long advertiserId, String destinationUrl, String name) {
        this.id = id;
        this.advertiserId = advertiserId;
        this.name = name;
        this.destinationUrl = destinationUrl;
    }

    public static ClickTarget create(Long id, Long advertiserId, String destinationUrl, String name) {
        return new ClickTarget(id, advertiserId, destinationUrl, name);
    }

    public Long getId() {
        return id;
    }

    public Long getAdvertiserId() {
        return advertiserId;
    }

    public String getName() {
        return name;
    }

    public String getDestinationUrl() {
        return destinationUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClickTarget that = (ClickTarget) o;

        if (!id.equals(that.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ClickTarget{" +
                "id=" + id +
                ", advertiserId=" + advertiserId +
                ", name='" + name + '\'' +
                '}';
    }
}
