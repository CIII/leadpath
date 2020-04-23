package com.pony.validation;

import com.pony.advertiser.Advertiser;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.models.AdvertiserModel;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;

import javax.naming.NamingException;
import java.sql.SQLException;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 1/24/14
 * Time: 9:20 AM
 */
public class PublisherFilter extends Filter {
    private final String ioCode;
    private final Advertiser advertiser;

    private PublisherFilter(String ioCode, Advertiser advertiser) {
        super();
        this.ioCode = ioCode;
        this.advertiser = advertiser;
    }

    @Override
    public boolean pass(Publisher publisher, PublisherList publisherList, Lead lead, PonyPhase phase) {
        // TODO: this is very crude just for now, and needs to be made externally configurable via db tables!
        // for Brandeis leads: only advertiser Brandeis is allowed
        if (publisher.getName().equals("Brandeis GPS")) {
            return "Brandeis".equals(advertiser.getName());
        }

        // for Coupon-Hound leads: only advertisers != Brandeis are allowed
        if (publisher.getName().equals("coupon-hound.com")) {
            return !"Brandeis".equals(advertiser.getName());
        }

        return true;
    }

    public static Filter create(String code, Long advertiserId) throws ValidationException {
        try {
            Advertiser advertiser = AdvertiserModel.find(advertiserId);
            return new PublisherFilter(code, advertiser);
        }
        catch (SQLException e) {
            throw new ValidationException(e);
        }
        catch (NamingException e) {
            throw new ValidationException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PublisherFilter that = (PublisherFilter) o;

        if (!advertiser.equals(that.advertiser)) {
            return false;
        }
        return ioCode.equals(that.ioCode);
    }

    @Override
    public int hashCode() {
        int result = ioCode.hashCode();
        result = 31 * result + advertiser.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PublisherFilter{" +
                "ioCode='" + ioCode + '\'' +
                ", advertiser=" + advertiser +
                '}';
    }
}
