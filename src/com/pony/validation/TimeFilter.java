package com.pony.validation;

import com.pony.advertiser.Io;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 6/26/13
 * Time: 2:42 PM
 */
public class TimeFilter extends Filter {
	private static final Log LOG = LogFactory.getLog(TimeFilter.class);
	
    private final Io io;
    private final boolean allow;
    private final String times;

    private TimeFilter(Io io, boolean allow, String times) {
        this.io = io;
        this.allow = allow;
        this.times = times;
    }

    @Override
    public boolean pass(Publisher publisher, PublisherList publisherList, Lead lead, PonyPhase phase) {
        // format: HH-HH:TZ,HH-HH:TZ (and'ed)
        String[] filters = times.split(",");
        for (String filter : filters) {
            if (!matchFilter(allow, filter)) {
                return false;
            }
        }

        return true;
    }

    private static boolean matchFilter(boolean allow, String filter) {
        // for each filter, get time in TZ and check against interval
        // format: HH-HH:TZ,HH-HH:TZ (and'ed)
        String from = null, to = null, tz = "America/New_York";  // default to America/New_YORK
        StringBuilder b = new StringBuilder();
        for (char c : filter.toCharArray()) {
            if (c == '-') {
                from = b.toString();
                b = new StringBuilder();
                continue;
            }
            if (c == ':') {
                to = b.toString();
                b = new StringBuilder();
                continue;
            }
            b.append(c);
        }
        if (b.length() > 0) {
            if (to == null) {
                to = b.toString();
            }
            else {
                tz = b.toString();
            }
        }
        int low = Integer.valueOf(from);
        int high = Integer.valueOf(to);

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(tz));
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if (allow) {
            if (hour < low || hour >= high) {
                return false;
            }
        }
        else {
            // the times are excluded
            if (hour >= low && hour < high) {
                return false;
            }
        }
        return true;
    }

    public static Filter create(Io io, boolean allow, String times) {
        return new TimeFilter(io, allow, times);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TimeFilter that = (TimeFilter) o;
        return io.equals(that.io);
    }

    @Override
    public int hashCode() {
        return io.hashCode();
    }

    @Override
    public String toString() {
        return "TimeFilter{" +
                "io=" + io +
                ", allow=" + allow +
                ", times='" + times + '\'' +
                '}';
    }

    public static void main(String[] args) {
        String filter = "9-17:EST";
        LOG.debug("include? " + filter + ":" + matchFilter(true, filter));
        filter = "9-17:America/New_York";
        LOG.debug("include? " + filter + ":" + matchFilter(true, filter));
        filter = "9-17:UTC";
        LOG.debug("include? " + filter + ":" + matchFilter(true, filter));
        filter = "9-17";
        LOG.debug("include? " + filter + ":" + matchFilter(true, filter));
        filter = "9-17:PST";
        LOG.debug("include? " + filter + ":" + matchFilter(true, filter));

        filter = "9-17:PST";
        LOG.debug("exclude? " + filter + ":" + matchFilter(false, filter));

        filter = "9-17:UTC";
        LOG.debug("exclude? " + filter + ":" + matchFilter(false, filter));
    }
}
