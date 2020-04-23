package com.pony.email;

import java.util.HashMap;
import java.util.Map;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 9/13/12
 * Time: 3:09 PM
 */
public class ResendMessagePhase {
    private static final Map<Integer, Phase> phases = new HashMap<Integer, Phase>();

    public static final Phase END = new Phase(11, null);
    public static final Phase DAY_4 = new Phase(4, END, 24);
    public static final Phase DAY_3 = new Phase(3, DAY_4, 24);
    public static final Phase DAY_2 = new Phase(2, DAY_3, 48);
    public static final Phase START = DAY_2;

    public static final Phase NIL = new Phase(99, null); // a way to flag messages to b

    public static class Phase {
        private final int phase;
        private final Phase nextPhase;
        private final int hoursToPreviousPhase;

        private Phase(int phase, Phase nextPhase) {
            this(phase, nextPhase, 24);
        }

        private Phase(int phase, Phase nextPhase, int hoursToPreviousPhase) {
            this.phase = phase;
            phases.put(phase, this);
            this.nextPhase = nextPhase;
            this.hoursToPreviousPhase = hoursToPreviousPhase;
        }

        public static Phase parse(int phase) {
            return phases.get(phase);
        }

        public Phase getNextPhase() {
            return nextPhase;
        }

        public int getHoursToPreviousPhase() {
            return hoursToPreviousPhase;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Phase phase1 = (Phase) o;

            return phase == phase1.phase;
        }

        public int getPhase() {
            return phase;
        }

        @Override
        public int hashCode() {
            return phase;
        }

        @Override
        public String toString() {
            return "Phase{" +
                    "phase=" + phase +
                    '}';
        }
    }

}
