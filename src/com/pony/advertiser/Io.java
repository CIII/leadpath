package com.pony.advertiser;

import com.pony.core.PonyPhase;
import com.pony.models.IoModel;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.validation.Filter;
import com.pony.validation.PublisherFilter;
import com.pony.validation.ValidationException;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 11/10/11
 * Time: 9:20 PM
 */
public class Io {
    private static final Log LOG = LogFactory.getLog(Io.class);
    
    private Long id;
    private final String code;
    private final Long advertiserId, leadTypeId;
    private final int status;
    private final BigDecimal vpl;
    private final Long capDaily;
    private final Long capMonthly;
    private final Long capTotal;
    private final String sourceId;
    private final boolean isExclusive;
    private final Long pixelId;
    private final String targetUrl;
    private final int weight;
    private final String email;

    /**
     * I am making this public as all the static factory methods are driving me crazy. [jec:2017/01/12]
     * @param leadTypeId
     * @param id
     * @param code
     * @param advertiserId
     * @param vpl
     * @param status
     * @param capDaily
     * @param sourceId
     * @param isExclusive
     * @param pixelId
     * @param targetUrl
     * @param email
     */
    public Io(Long leadTypeId, Long id, String code, Long advertiserId, BigDecimal vpl, int status, Long capDaily, String sourceId, boolean isExclusive, Long pixelId, String targetUrl, String email)  {
    	this(leadTypeId, id, code, advertiserId, vpl, status, capDaily, sourceId, isExclusive, pixelId, targetUrl, 100, email);
    }
    
    public Io(Long leadTypeId, Long id, String code, Long advertiserId, BigDecimal vpl, int status, Long capDaily, String sourceId, boolean isExclusive, Long pixelId, String targetUrl)  {
    	this(leadTypeId, id, code, advertiserId, vpl, status, capDaily, sourceId, isExclusive, pixelId, targetUrl, 100, "");
    }
    
    public Io(Long leadTypeId, Long id, String code, Long advertiserId, BigDecimal vpl, int status, Long capDaily, String sourceId, boolean isExclusive, Long pixelId, String targetUrl, int weight, String email)  {
    	this(leadTypeId, id, code, advertiserId, vpl, status, capDaily, 0L, 0L, sourceId, isExclusive, pixelId, targetUrl, weight, email);
    }
    
    public Io(Long leadTypeId, Long id, String code, Long advertiserId, BigDecimal vpl, int status, Long capDaily, Long capMonthly, Long capTotal, String sourceId, boolean isExclusive, Long pixelId, String targetUrl, int weight, String email) {
        this.id = id;
        this.code = code;
        this.advertiserId = advertiserId;
        this.vpl = vpl;
        this.status = status;
        this.leadTypeId = leadTypeId;
        this.capDaily = capDaily;
        this.capMonthly = capMonthly;
        this.capTotal = capTotal;
        this.sourceId = sourceId;
        this.isExclusive = isExclusive;
        this.pixelId = pixelId;
        this.targetUrl = targetUrl;
        this.weight = weight;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public Long getCapDaily() {
        return capDaily;
    }
    
    public Long getCapMonthly() {
    	return capMonthly;
    }
    
    public Long getCapTotal() {
    	return capTotal;
    }

    public Long getAdvertiserId() {
        return advertiserId;
    }

    public Long getLeadTypeId() {
        return leadTypeId;
    }

    public int getStatus() {
        return status;
    }

    public BigDecimal getVpl() {
        return (vpl == null ? BigDecimal.ZERO : vpl);
    }

    public String getSourceId() {
        return sourceId;
    }
    
    public String getEmail() {
    	return email;
    }

    public boolean isExclusive() {
        return isExclusive;
    }

    public static Io create(Long leadTypeId, ResultSet rs) throws SQLException {
        return create(leadTypeId, rs.getLong("id"), rs.getString("code"), rs.getLong("advertiser_id"), rs.getBigDecimal("vpl"), rs.getInt("status"), rs.getLong("cap_daily"), rs.getString("source_id"), rs.getBoolean("is_exclusive"), rs.getLong("pixel_id"), rs.getString("target_url"), rs.getInt("weight"), rs.getString("email"));
    }

    public static Io create(Long leadTypeId, Long id, String code, Long advertiserId, BigDecimal vpl, int status, Long capDaily, String sourceId, boolean isExclusive, Long pixelId, String targetUrl, int weight, String email)  {
        return new Io(leadTypeId, id, code, advertiserId, vpl, status, capDaily, sourceId, isExclusive, pixelId, targetUrl, weight, email);
    }
    
    public static Io create(Long leadTypeId, Long id, String code, Long advertiserId, BigDecimal vpl, int status, Long capDaily, Long capMonthly, Long capTotal, String sourceId, boolean isExclusive, Long pixelId, String targetUrl, int weight, String email)  {
        return new Io(leadTypeId, id, code, advertiserId, vpl, status, capDaily, capMonthly, capTotal, sourceId, isExclusive, pixelId, targetUrl, weight, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Io io = (Io) o;

        if (!advertiserId.equals(io.advertiserId)) {
            return false;
        }
        if (!code.equals(io.code)) {
            return false;
        }
        if (!leadTypeId.equals(io.leadTypeId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + advertiserId.hashCode();
        result = 31 * result + leadTypeId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Io{" +
                "code='" + code + '\'' +
                ", advertiserId=" + advertiserId +
                ", leadTypeId=" + leadTypeId +
                ", status=" + status +
                ", vpl=" + vpl +
                '}';
    }

    public boolean matchLead(IPublisherContext publisherContext, PonyPhase phase) {
        try {
            List<Filter> filters = IoModel.getFilters(this, phase);

            filters.add(PublisherFilter.create(code, advertiserId));

            LOG.debug("Applying filters to order: " + this.id);
            for (Filter filter : filters) {
                if (!filter.pass(publisherContext.getPublisher(), publisherContext.getPublisherList(), publisherContext.getLead(), phase)) {
                    return false;
                }
            }
            return true;
        }
        catch (ValidationException e) {
            LOG.error(e);
        }
        catch (SQLException e) {
            LOG.error(e);
        }
        catch (NamingException e) {
            LOG.error(e);
        }

        return false;
    }

    public Long getPixelId() {
        return pixelId == null ? 0L : pixelId;
    }

	public int getWeight() {
		return weight;
	}
}
