package com.pony.validation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.advertiser.Io;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 4/23/14
 * Time: 4:08 PM
 */
public class ZipFilter extends Filter {
	private static final Log LOG = LogFactory.getLog(ZipFilter.class);
	
    private final Io io;
    private final boolean allow;
    private final String zipCodes;

    private ZipFilter(Io io, boolean allow, String zipCodes) {
        this.io = io;
        this.allow = allow;
        this.zipCodes = zipCodes;
    }

    @Override
    public boolean pass(Publisher publisher, PublisherList publisherList, Lead lead, PonyPhase phase) {
        String zip = lead.getAttributeValue("zip");
        boolean returnValue;
        if (zip == null) {
            zip = lead.getAttributeValue("zip_code");
        }

        if (zip == null) {
            returnValue = !allow;
        }

        if (allow) {
            returnValue = zipCodes.contains(zip);
        }
        else {
            returnValue = !zipCodes.contains(zip);
        }
        
        if(!returnValue) {
        	LOG.debug("Zip filter: rejected");
        }
        
        return returnValue;
    }

    public static Filter create(Io io, boolean allow, String zipCodes) {
        return new ZipFilter(io, allow, zipCodes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ZipFilter zipFilter = (ZipFilter) o;

        if (!io.equals(zipFilter.io)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return io.hashCode();
    }

    @Override
    public String toString() {
        return "ZipFilter{" +
                "io=" + io +
                ", allow=" + allow +
                ", zipCodes='" + zipCodes + '\'' +
                '}';
    }
}
