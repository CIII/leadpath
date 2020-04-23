package com.pony.livehttp;

import com.pony.models.ClickRuleModel;
import com.pony.models.ClickSourceModel;
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
 * Time: 12:28 PM
 */
public class RedirectServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(RedirectServlet.class);
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // who sent the click?
            ClickSource clickSource = null;
            String src = req.getParameter(ClickSource.SRC_TOKEN);
            if (src != null) {
                clickSource = ClickSourceModel.find(Long.valueOf(src));
            }

            // they did not identify themselves or we don't know them
            if (clickSource == null) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            // find the target to direct the click to
            ClickTarget clickTarget = ClickRuleModel.findClickTargetForSource(clickSource);

            // now record the event
            Redirect redirect = Redirect.create(clickSource, clickTarget, req);
            Long redirectId = RedirectModel.create(redirect);

            // and redirect to the final destination
            String redirectUrl = "http://www.acquisition-sciences.com?" + Redirect.URL_TOKEN + "={redirect_id}";

            if (clickTarget != null && clickTarget.getDestinationUrl() != null) {
                redirectUrl = clickTarget.getDestinationUrl();
            }

            redirectUrl = redirectUrl.replace("{redirect_id}", redirectId.toString());
            resp.sendRedirect(redirectUrl);
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
