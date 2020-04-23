package com.pony.validation;

import com.pony.advertiser.Io;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;

import java.util.Calendar;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 4/23/14
 * Time: 3:59 PM
 */
public class DayFilter extends Filter {
	private static final Log LOG = LogFactory.getLog(DayFilter.class);
	
    private final Io io;
    private final boolean allow;
    private final String days;

    private DayFilter(Io io, boolean allow, String days) {
        this.io = io;
        this.allow = allow;
        this.days = days;
    }

    @Override
    public boolean pass(Publisher publisher, PublisherList publisherList, Lead lead, PonyPhase phase) {
        // format: d,d,d,d:tz
        return matchFilter(allow, days);
    }

    private static boolean matchFilter(boolean allow, String filter) {
        // get day in TZ and check against day (Sunday = 0, Monday =1, ...)
        // format: d,d,d,d,d:TZ

        String tz = "America/New_York"; // default to America/New_York
        String[] daysAndTz = filter.split(":");
        String days = daysAndTz[0];
        if (daysAndTz.length > 1) {
            tz = daysAndTz[1];
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(tz));

        int day = cal.get(Calendar.DAY_OF_WEEK);
        if (allow) {
            if (!days.contains("" + day)) {
                return false;
            }
        }
        else {
            // the times are excluded
            if (days.contains("" + day)) {
                return false;
            }
        }
        return true;
    }

    public static Filter create(Io io, boolean allow, String days) {
        return new DayFilter(io, allow, days);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DayFilter dayFilter = (DayFilter) o;
        return io.equals(dayFilter.io);
    }

    @Override
    public int hashCode() {
        return io.hashCode();
    }

    @Override
    public String toString() {
        return "DayFilter{" +
                "io=" + io +
                ", allow=" + allow +
                ", days='" + days + '\'' +
                '}';
    }

    public static void main(String[] args) {
        String filter = "2,4,6:UTC"; // Mo, We, Fr
        LOG.debug("match? " + filter + " -> " + matchFilter(true, filter));

        filter = "2,3,4,5,6:America/New_York"; // weekdays
        LOG.debug("match? " + filter + " -> " + matchFilter(true, filter));

        filter = "1,7:America/New_York";
        LOG.debug("excluded? " + filter + " -> " + matchFilter(false, filter));
    }
}
