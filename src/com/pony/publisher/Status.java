package com.pony.publisher;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/10/11
 * Time: 9:55 PM
 */
public final class Status {
    private final int status;
    private final String name;

    private static final Map<Integer, Status> stats = new HashMap<Integer, Status>();

    public static final Status OPEN = new Status(1, "open");
    public static final Status PAUSED = new Status(0, "paused");
    public static final Status RETIRED = new Status(-1, "retired");

    private Status(int status, String name) {
        this.status = status;
        this.name = name;

        stats.put(status, this);
    }

    public int getStatus() {
        return status;
    }

    public static Status parse(int status) {
        return stats.get(status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Status status1 = (Status) o;

        if (status != status1.status) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return status;
    }

    @Override
    public String toString() {
        return "Status{" +
                "status=" + status +
                ", name='" + name + '\'' +
                '}';
    }
}
