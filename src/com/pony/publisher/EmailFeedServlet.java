package com.pony.publisher;

import com.pony.core.PonyServer;
import com.pony.email.SmtpException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * listen to incoming http requests.
 * determine if this is an old style request and needs to be handled the old way, or if we can use the new infrastructure.
 * If it's old style, all we need to do is write to the inbound_queue table and we're done.
 * For new style, there is more to do:
 * use the PublisherContext to parse out the posted information. Check if the publisher is allowed to post to the targeted list.
 * If everything checks out, pass the post on to the EmailService to handle the smtp part.
 * <p/>
 * for transactional sends, use /email path like:
 * curl --request POST 'http://localhost:8080/email' --data 'listid=lk_auto_email1&ref=localhost&domtok=SendItNow!&email=martinholzner@gmail.com&ipaddy=66.87.66.238&first=Martin&last=Holzner&creativeid=4&hostid=2&make=Ford&model=Model A&year=1903&city=Chicago&zip=60644'
 * <p/>
 * for batch sends , use the /async_email path like:
 * http://www.car-insurance-quotes.me/async_email?listid=lk_auto_email1&ref=localhost&domtok=SendItNow!&maxRows=2
 * TODO: document the setup of the email_target_queues
 * <p/>
 * load md5 suppressions
 * mysql -u root -p pony_leads -e"set foreign_key_checks=0; set sql_log_bin=0; load data concurrent local infile '/home/ec2-user/unsubs.txt' ignore into table md5_suppressions (md5_email) set publisher_id = 9 , created_at = now()"
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 6/6/12
 * Time: 12:55 PM
 */
public class EmailFeedServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(EmailFeedServlet.class);
    private PonyServer server = null;
    private PublisherService publisherService = null;

    // Note: We do no longer support GET!!!
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doPost(req, resp);
//    }
//

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long tStart = System.currentTimeMillis();
        LOG.info(Thread.currentThread().getName() + ": new request to listId=" + req.getParameter(PublisherChannel.LIST_ID) + " from ip=" + req.getRemoteAddr());
        try {

            String listId = req.getParameter(PublisherChannel.LIST_ID);
            if (listId == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // send 401
                return;
            }

            // parse out the email, dup check it, parse out the publisher, the cpl,
            // and check if the publisher is allowed to post to this list id
            PublisherContext context = PublisherContext.parse(req);

            if (context != null) {
                PublisherResponse publisherResponse;
                if (context.isPublisherDup() || context.isEmailDup()) {
                    // (the publisher is providing) the same email again: we don't proceed
                    publisherResponse = PublisherResponse.createDupResponse(context);
                }
                else {
                    // now try to run the phase command (email)
                    publisherResponse = publisherService.execute(context);
                }

                // and then format the response
                publisherService.formatResponse(context, publisherResponse, null, resp);
                LOG.info(Thread.currentThread().getName() + ": response(" + (System.currentTimeMillis()-tStart) + ")=" + publisherResponse);
            }
            else {
                // format an error response
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // send 401
            }
        }
        catch (PublisherException e) {
            LOG.error(Thread.currentThread().getName() + ": error response:", e);
            publisherService.formatResponse(resp);
        }
        catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // send 401
        }
        catch (IllegalStateException e) {
            LOG.error(Thread.currentThread().getName() + ": error response:", e);
            publisherService.formatResponse(resp);
        }
        catch (SmtpException e) {
            LOG.error(Thread.currentThread().getName() + ": error response:", e);
            publisherService.formatResponse(resp);
        }
        finally {
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            server = new PonyServer().start();
            publisherService = server.getPublisherService();
        }
        finally {
        }
    }

    @Override
    public void destroy() {
        if (server != null) {
            server.stop();
        }

        publisherService = null;
        server = null;

        super.destroy();
    }
}

