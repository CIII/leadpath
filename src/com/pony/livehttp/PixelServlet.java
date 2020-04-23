package com.pony.livehttp;

import com.pony.models.ClickSourceModel;
import com.pony.models.PixelFireModel;
import com.pony.models.RedirectModel;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 11/28/12
 * Time: 12:31 PM
 */
public class PixelServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(PixelServlet.class);
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // track the pixel type and arrival id

        try {
            // what redirect does the pixel refer to?
            String redirectId = req.getParameter(Redirect.URL_TOKEN);

            Redirect redirect = null;
            if (redirectId != null) {
                redirect = RedirectModel.find(Long.valueOf(redirectId));
            }

            if (redirect == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            ClickSource clickSource = ClickSourceModel.find(Long.valueOf(redirect.getClickSourceId()));
            assert clickSource != null;

            // now record the event
            PixelFire pixelFire = PixelFire.create(Long.valueOf(redirectId), req);
            Long pixelFireId = PixelFireModel.create(pixelFire);

            // and (for conversion pixels) redirect to the final destination (if there is one)

            if (pixelFire.getPixelType() == PixelFire.TYPE_CONVERSION && clickSource.getPixelBackUrl() != null) {
                StringBuilder redirectUrl = new StringBuilder();
                redirectUrl.append(clickSource.getPixelBackUrl());

                // append the original query if there was one
                if (redirect.getDestinationQuery() != null) {
                    if (redirectUrl.indexOf("?") <= 0) {
                        redirectUrl.append("?");
                    }
                    else {
                        redirectUrl.append("&");
                    }
                    redirectUrl.append(redirect.getDestinationQuery());
                }
                String url = redirectUrl.toString().replace("{pixel_fire_id}", pixelFireId.toString());
                url = url.replace("{redirect_id}", redirectId);

                resp.sendRedirect(url);
                return;
            }

            // if there is no redirect, 'send' the pixel (by not sending anything but a status 'ok')
            resp.setStatus(HttpServletResponse.SC_OK);
        }
        catch (SQLException e) {
            LOG.error(e);
            throw new IOException(e);
        }
        catch (NamingException e) {
            LOG.error(e);
            throw new IOException(e);
        }
    }
}
