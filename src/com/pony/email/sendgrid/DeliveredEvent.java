package com.pony.email.sendgrid;

import com.pony.models.MessageModel;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Track message delivered events
 * PonyLeads 2012.
 * User: martin
 * Date: 12/6/12
 * Time: 3:59 PM
 */
public class DeliveredEvent extends SendGridEvent {
    DeliveredEvent() {
        super("delivered");
    }

    @Override
    void handle(HashMap<String, String> hash) throws SendGridException {
        try {
            if (hash.get(SendGridProvider.SENDPLEX_MESSAGE_ID) != null) {
                int status = MESSAGE_MAP.get(getType());
                String category = hash.get("category");
                String response = hash.get("response"); // Full response from MTA
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
                String category = request.getParameter("category");
                String response = request.getParameter("response"); // Full response from MTA
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
