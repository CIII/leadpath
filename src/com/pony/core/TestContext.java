package com.pony.core;

import javax.servlet.http.HttpServletRequest;

/**
 * A context to hold test parameters (for email posts only!)
 * <p/>
 * PonyLeads 2012.
 * <p/>
 * User: martin
 * Date: 9/5/12
 * Time: 5:18 PM
 */
public class TestContext {

    public static final String CREATIVE_ID = "creativeid", HOST_ID = "hostid", REFRESH_CACHE = "refresh";

    private final Long creativeId, hostId;
    private final boolean refresh;

    private TestContext(Long creativeId, Long hostId, boolean refresh) {
        this.creativeId = creativeId;
        this.hostId = hostId;
        this.refresh = refresh;
    }

    public static TestContext create(HttpServletRequest request) {
        // look for offer_id, creative_id, host_id parameters
        String creativeId = request.getParameter(CREATIVE_ID);
        String hostId = request.getParameter(HOST_ID);
        String refresh = request.getParameter(REFRESH_CACHE);

        boolean freshTheCache = false;
        if (refresh != null) {
            freshTheCache = true;
        }

        try {
            Long creativeIdL = null, hostIdL = null;

            if (creativeId != null) {
                creativeIdL = Long.valueOf(creativeId);
            }
            if (hostId != null) {
                hostIdL = Long.valueOf(hostId);
            }
            if (creativeIdL != null && hostIdL != null) { // we do allow the offer id to be null (since it's implicitly given via the creative id)
                return new TestContext(creativeIdL, hostIdL, freshTheCache);
            }
        }
        catch (NumberFormatException e) {
            return null;
        }

        return null;
    }

    public Long getCreativeId() {
        return creativeId;
    }

    public Long getHostId() {
        return hostId;
    }

    public boolean isRefresh() {
        return refresh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TestContext that = (TestContext) o;

        if (creativeId != null ? !creativeId.equals(that.creativeId) : that.creativeId != null) {
            return false;
        }
        if (hostId != null ? !hostId.equals(that.hostId) : that.hostId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = creativeId != null ? creativeId.hashCode() : 0;
        result = 31 * result + (hostId != null ? hostId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TestContext{" +
                "creativeId=" + creativeId +
                ", hostId=" + hostId +
                '}';
    }
}
