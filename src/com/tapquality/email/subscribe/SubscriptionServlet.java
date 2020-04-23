package com.tapquality.email.subscribe;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.pony.core.PonyServer;
import com.pony.email.SmtpException;
import com.pony.publisher.PublisherChannel;
import com.pony.publisher.PublisherContext;
import com.pony.publisher.PublisherException;
import com.pony.publisher.PublisherResponse;
import com.pony.publisher.PublisherService;


@Singleton
public class SubscriptionServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(SubscriptionServlet.class);

    private PonyServer server = null;
    private PublisherService publisherService = null;    
    
    @Inject 
    SubscriptionServlet(PonyServer server) {
    	super();
    	this.server = server;
    }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{

        try {
			String listId = req.getParameter(PublisherChannel.LIST_ID);
	        if (listId == null) {
	            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // send 401
	            return;
	        }
        
		    // parse out the email, dup check it, parse out the publisher, the cpl,
		    // and check if the publisher is allowed to post to this list id
			PublisherContext context = PublisherContext.parse(req);
			if(context != null){
				PublisherResponse publisherResponse = publisherService.execute(context);
                publisherService.formatResponse(context, publisherResponse, null, resp);
			}
			
			
		} catch (PublisherException | SmtpException | IOException e) {
            LOG.error(Thread.currentThread().getName() + ": error response:", e);
            publisherService.formatResponse(resp, e);
		}
        
	}

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        publisherService = server.getPublisherService();
    }

    @Override
    public void destroy() {
    	publisherService = null;
        server = null;

        super.destroy();
    }
}
