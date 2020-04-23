package com.pony.email.sendgrid;

import com.pony.lead.UserProfile;
import com.pony.models.MessageModel;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Track Deferred sends
 * PonyLeads 2012.
 * User: martin
 * Date: 12/6/12
 * Time: 3:58 PM
 */
public class DeferredEvent extends SendGridEvent {
	private static final Log LOG = LogFactory.getLog(DeferredEvent.class);
	
    DeferredEvent() {
        super("deferred");
    }

    @Override
    void handle(HashMap<String, String> hash) throws SendGridException {
        try {
            if (hash.get(SendGridProvider.SENDPLEX_MESSAGE_ID) != null) {
                int status = MESSAGE_MAP.get(getType());
//                String category = request.getParameter("category");
                String response = hash.get("response"); // Full response from MTA
                String attempt = hash.get("attempt"); // Delivery attempt #
                String email = hash.get(UserProfile.EMAIL);
                LOG.debug(">> deferred email=" + email + "; response=" + response + "; attempt=" + attempt);

                MessageModel.processEvent(Long.valueOf(hash.get(SendGridProvider.SENDPLEX_MESSAGE_ID)), status);
            }
        }
        catch (SQLException e) {
            throw new SendGridException(e);
        }
        catch (NamingException e) {
            throw new SendGridException(e);
        }
    }

    @Override
    void handle(HttpServletRequest request) throws SendGridException {
        try {
            if (request.getParameter(SendGridProvider.SENDPLEX_MESSAGE_ID) != null) {
                int status = MESSAGE_MAP.get(getType());
//                String category = request.getParameter("category");
                String response = request.getParameter("response"); // Full response from MTA
                String attempt = request.getParameter("attempt"); // Delivery attempt #
                String email = UserProfile.parse(request);
                LOG.debug(">> deferred email=" + email + "; response=" + response + "; attempt=" + attempt);

                MessageModel.processEvent(Long.valueOf(request.getParameter(SendGridProvider.SENDPLEX_MESSAGE_ID)), status);
            }
        }
        catch (SQLException e) {
            throw new SendGridException(e);
        }
        catch (NamingException e) {
            throw new SendGridException(e);
        }
    }
}
