package com.pony.publisher;

import com.pony.core.PonyServer;
import com.pony.email.SmtpException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Copyright 2011 PonyMash LLC
 * User: Martin
 * Date: 10/29/11
 * Time: 5:22 PM
 */
@SuppressWarnings("serial")
@Singleton
public class PublisherServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(PublisherServlet.class);
	
    private PonyServer server = null;

    @Inject PublisherServlet(PonyServer server) {
    	super();
    	this.server = server;
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            // find the publisher and list,
            // make sure the publisher is allowed to post to this list,
            // parse the lead attributes,
            // determine the lead type,
            // and store the lead
            IPublisherContext publisherContext = PublisherContext.parse(req);

            if (publisherContext != null) {
                // now try to run the phase command
                PublisherService service = server.getPublisherService();

                service.execute(publisherContext);

                // and then format the response
                service.formatResponse((PublisherContext)publisherContext, publisherContext.getPublisherResponse(), null, resp);
            }
            else {
                // TODO: format an error response
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        catch (PublisherException e) {
            LOG.error(e);
            throw new ServletException("PublisherException servicing post.", e);
        }
        catch (SmtpException e) {
            LOG.error(e);
            throw new ServletException("SmtpException servicing post.", e);
        }
        catch (Throwable e) {
            LOG.error("Unknown error servicing post.", e);
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
