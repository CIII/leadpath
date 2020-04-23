package com.pony.email.sendgrid;

import com.pony.lead.UserProfile;
import com.pony.models.MessageModel;
import com.pony.models.UserProfileModelImpl;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Track open events
 * PonyLeads 2012.
 * User: martin
 * Date: 12/6/12
 * Time: 3:59 PM
 */
public class OpenEvent extends SendGridEvent {
	private static final Log LOG = LogFactory.getLog(OpenEvent.class);
	
    OpenEvent() {
        super("open");
    }

    @Override
    void handle(HashMap<String, String> hash) throws SendGridException {
        if (hash.get(SendGridProvider.SENDPLEX_MESSAGE_ID) != null) {
            // insert into opens
            LOG.info("processing open event V3...");
            try {
                String category = hash.get("category");
                String email = hash.get(UserProfile.EMAIL);
                if (email != null) {
                    UserProfile up = UserProfileModelImpl.findByEmailStatic(email);
                    if (up != null) {
                        String userAgent = hash.get("useragent");
                        String ip = hash.get("ip");
                        LOG.info("open: email=" + email + "; userAgent=" + userAgent + "; ip=" + ip);

                        MessageModel.opened(Long.valueOf(hash.get(SendGridProvider.SENDPLEX_MESSAGE_ID)), userAgent, ip);
                    }
                    else {
                        LOG.error("ERROR: cannot find user_profile for [" + email + "]");
                    }
                }
                else {
                    LOG.error("ERROR: no email provided in open event for msg_id=" + hash.get(SendGridProvider.SENDPLEX_MESSAGE_ID));
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

    @Override
    void handle(HttpServletRequest request) throws SendGridException {
        if (request.getParameter(SendGridProvider.SENDPLEX_MESSAGE_ID) != null) {
            // insert into opens
            LOG.info("processing open event V1...");
            try {
                String category = request.getParameter("category");
                String email = UserProfile.parse(request);
                if (email != null) {
                    UserProfile up = UserProfileModelImpl.findByEmailStatic(email);
                    if (up != null) {
                        String userAgent = request.getParameter("useragent");
                        String ip = request.getParameter("ip");
                        LOG.info("open: email=" + email + "; userAgent=" + userAgent + "; ip=" + ip);

                        MessageModel.opened(Long.valueOf(request.getParameter(SendGridProvider.SENDPLEX_MESSAGE_ID)), userAgent, ip);
                    }
                    else {
                        LOG.error("ERROR: cannot find user_profile for [" + email + "]");
                    }
                }
                else {
                    LOG.error("ERROR: no email provided in open event for msg_id=" + request.getParameter(SendGridProvider.SENDPLEX_MESSAGE_ID));
                }
            }
            catch (SQLException e) {
            	LOG.warn(e);
                throw new SendGridException(e);
            }
            catch (NamingException e) {
            	LOG.warn(e);
                throw new SendGridException(e);
            }
        }
    }
}
