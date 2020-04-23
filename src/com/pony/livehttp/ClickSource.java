package com.pony.livehttp;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 11/28/12
 * Time: 5:15 PM
 */
public class ClickSource {
    private final Long id, publisherId;
    private final String name, pixelBackUrl;

    public static final String SRC_TOKEN = "src";

    private ClickSource(Long id, Long publisherId, String name, String pixelBackUrl) {
        this.id = id;
        this.publisherId = publisherId;
        this.name = name;
        this.pixelBackUrl = pixelBackUrl;
    }

    public static ClickSource create(Long id, Long publisherId, String name, String pixelBackUrl) {
        return new ClickSource(id, publisherId, name, pixelBackUrl);
    }

    public Long getId() {
        return id;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public String getName() {
        return name;
    }

    public String getPixelBackUrl() {
        return pixelBackUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClickSource that = (ClickSource) o;

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
        return "ClickSource{" +
                "id=" + id +
                ", publisherId=" + publisherId +
                ", name='" + name + '\'' +
                '}';
    }

}
