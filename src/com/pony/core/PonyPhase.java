package com.pony.core;

import java.util.HashMap;
import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 6/19/13
 * Time: 4:15 PM
 */
public class PonyPhase {
    private final String name;
    private final int code;

    private final static Map<Integer, PonyPhase> phases = new HashMap<Integer, PonyPhase>();

    public static final PonyPhase POST = new PonyPhase(3, "Post");
    public static final PonyPhase PING = new PonyPhase(1, "Ping");
    public static final PonyPhase POLL = new PonyPhase(2, "Poll");

    public static final PonyPhase FORM = new PonyPhase(4, "Form");
    public static final PonyPhase REQUEST_PRICE = new PonyPhase(5, "Request_Price");
    public static final PonyPhase ADVERTISER_DISPOSITION = new PonyPhase(6, "Advertiser Disposition");
    public static final PonyPhase OUTCOME = new PonyPhase(7, "Commission");

    private PonyPhase(int f, String phaseName) {
        this.code = f;
        this.name = phaseName;
        phases.put(code, this);
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static PonyPhase parse(int code) {
        return phases.get(code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PonyPhase ponyPhase = (PonyPhase) o;

        return name.equals(ponyPhase.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "PonyPhase{" + code + "[" + name + "]}";
    }
}
