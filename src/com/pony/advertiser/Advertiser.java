package com.pony.advertiser;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 10/30/11
 * Time: 10:30 PM
 */
public class Advertiser {
    private final Long id;
    private final String name;

    private Advertiser(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Advertiser create(Long id, String name) {
        return new Advertiser(id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Advertiser that = (Advertiser) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (!name.equals(that.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Advertiser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
