package com.pony.livehttp;

import javax.servlet.http.HttpServletRequest;

/**
 * These PixelFires are linked to Redirects (@see RedirectServlet)
 * The arrival_id token in the url is a reference back to the redirects.id
 * <p/>
 * PonyLeads 2012.
 * User: martin
 * Date: 11/28/12
 * Time: 5:40 PM
 */
public class PixelFire {
    private final Long id, redirectId;
    private final int pixelType, counter;
    private final String ipAddress, userAgent, referrer;

    public static final int TYPE_ARRIVAL = 1;
    public static final int TYPE_CONVERSION = 2;

    private PixelFire(Long id, Long redirectId, int pixelType, int counter, String ipAddress, String userAgent, String referrer) {
        this.id = id;
        this.redirectId = redirectId;
        this.pixelType = pixelType;
        this.counter = counter;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.referrer = referrer;
    }

    public static PixelFire create(Long id, Long redirectId, int pixelType, int counter, String ipAddress, String userAgent, String referrer) {
        return new PixelFire(id, redirectId, pixelType, counter, ipAddress, userAgent, referrer);
    }


    public static PixelFire create(Long redirectId, HttpServletRequest request) {
        // parse the pixel type based on the servlet path
        // /arrivals/pixel.png
        // /conversions/pixel.png
        int type = TYPE_ARRIVAL;
        if (request.getServletPath().startsWith("/conversions")) {
            type = TYPE_CONVERSION;
        }
//        else if (request.getServletPath().startsWith("/arrivals")) {
//            type = TYPE_ARRIVAL;
//        }

        return new PixelFire(null, redirectId, type, 1, request.getRemoteAddr(), request.getHeader("User-Agent"), request.getHeader("referer"));
    }

    public Long getId() {
        return id;
    }

    public Long getRedirectId() {
        return redirectId;
    }

    public int getPixelType() {
        return pixelType;
    }

    public int getCounter() {
        return counter;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getReferrer() {
        return referrer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PixelFire pixelFire = (PixelFire) o;

        if (!id.equals(pixelFire.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "PixelFire{" +
                "id=" + id +
                ", redirectId=" + redirectId +
                ", pixelType=" + pixelType +
                ", ipAddress='" + ipAddress + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", referrer='" + referrer + '\'' +
                '}';
    }
}
