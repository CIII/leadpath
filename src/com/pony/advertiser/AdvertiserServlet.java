package com.pony.advertiser;

import com.pony.advertiser.dispositioner.Dispositioner;
import com.pony.core.PonyServer;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * Created by martin on 7/6/16.
 */
@SuppressWarnings("serial")
@Singleton
public class AdvertiserServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(AdvertiserServlet.class);
	protected PonyServer ponyServer;
	protected Set<Dispositioner> dispositioners;
	
	@Inject AdvertiserServlet(Set<Dispositioner> dispositioners, PonyServer ponyServer) {
		this.dispositioners = dispositioners;
		this.ponyServer = ponyServer;
	}
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: had coded for now
        try {

            final String path = req.getServletPath();

            if(path.equalsIgnoreCase("/disposition")) {
            	Map<String, String[]> parameters = req.getParameterMap();
            	for(Dispositioner dispositioner : dispositioners) {
            		dispositioner.checkForDispositions(parameters);
            	}
            }
            else if (path.equalsIgnoreCase("/lead-status")) {
                String sessionId = req.getParameter("session_id");
                Long publisherId = Long.parseLong(req.getParameter("publisher_id"));
                ponyServer.getAdvertiserService().getArrivalDisposition(req, resp, sessionId, publisherId);
            }
            else if (path.contains("/lead-disposition")) {
                LOG.info("Received request for disposition for lead " + path);
                String leadIdString = req.getRequestURI().replace(req.getServletPath(), "");
                leadIdString = leadIdString.replaceFirst("/","");
                try{
                    LOG.info("Received request for disposition for leadid: " + leadIdString);
                    long leadId = Long.parseLong(leadIdString);
                    ponyServer.getAdvertiserService().getLeadDisposition(leadId);
                }catch(NumberFormatException nfe){
                    LOG.error("Improper leadid format: " + leadIdString);
                }catch(Exception e){
                    LOG.error(e);
                }
            }
            else{
                throw new Exception("Unrecognized pattern for advertiserservlet: " + path);
            }
        }
        catch (SQLException e) {
            LOG.error(e);
        }
        catch (NamingException e) {
            LOG.error(e);
        }catch(Exception e){
            LOG.error(e);
        }

    }
}
