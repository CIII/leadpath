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
 * track message dropped events
 * PonyLeads 2012.
 * User: martin
 * Date: 12/6/12
 * Time: 3:59 PM
 */
public class DroppedEvent extends SendGridEvent {
	private static final Log LOG = LogFactory.getLog(DroppedEvent.class);
	
    DroppedEvent() {
        super("dropped");
    }

    @Override
    void handle(HashMap<String, String> hash) throws SendGridException {
        try {
            if (hash.get(SendGridProvider.SENDPLEX_MESSAGE_ID) != null) {
                int status = MESSAGE_MAP.get(getType());
//                String reason = hash.get("reason"); // Drop reason
//                String category = hash.get("category");
                String email = hash.get(UserProfile.EMAIL);
                LOG.info(">> dropped: email=" + email);

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
//                String reason = request.getParameter("reason"); // Drop reason
//                String category = request.getParameter("category");
                String email = UserProfile.parse(request);
                LOG.info(">> dropped: email=" + email);

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
