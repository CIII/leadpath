package com.pony.publisher;

import com.pony.core.PonyServer;
import com.pony.email.SmtpException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 9/20/12
 * Time: 8:01 PM
 */
public class ResendServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(ResendServlet.class);
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PonyServer server = null;
        try {
            server = new PonyServer().start();
            PublisherService service = server.getPublisherService();

            // findByEmail out the publisher and the cpl
            PublisherContext context = PublisherContext.parse(req);

            if (context != null) {
                // now try to run the phase command
                PublisherResponse publisherResponse = service.execute(context);
                // and then format the response
                service.formatResponse(context, publisherResponse, null, resp);
            }
            else {
                // format an error response
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        catch (PublisherException e) {
            LOG.error(e);
            throw new ServletException(e);
        }
        catch (SmtpException e) {
            LOG.error(e);
            throw new ServletException(e);
        }
        finally {
            if (server != null) {
                server.stop();
            }
        }
    }
}
