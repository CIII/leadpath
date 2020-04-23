package com.pony.email.sendgrid;

import com.pony.lead.UserProfile;
import com.pony.models.UserProfileModelImpl;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * track unsubscribe events
 * PonyLeads 2012.
 * User: martin
 * Date: 12/6/12
 * Time: 4:00 PM
 */
public class UnsubscribeEvent extends SendGridEvent {
	private static final Log LOG = LogFactory.getLog(UnsubscribeEvent.class);
	
    UnsubscribeEvent() {
        super("unsubscribe");
    }

    @Override
    void handle(HashMap<String, String> hash) throws SendGridException {
        if (hash.get(SendGridProvider.SENDPLEX_MESSAGE_ID) != null) {
            Long msgId = Long.valueOf(hash.get(SendGridProvider.SENDPLEX_MESSAGE_ID));

            try {
                String category = hash.get("category");
                String email = hash.get(UserProfile.EMAIL);
                if (email != null) {
                    UserProfile up = UserProfileModelImpl.findByEmailStatic(email);
                    if (up != null) {
                        String userAgent = hash.get("useragent");
                        String ip = hash.get("ip");
                        LOG.info(">>unsubscribe: email=" + email + "; userAgent=" + userAgent + "; ip=" + ip);

                        // we unsubscribe the user, not just the message (no need to record user agent, ip , and referrer: they are the same for all from sendgrid)
                        UserProfileModelImpl.unsubscribeStatic(up, userAgent, ip, null, msgId);
                    }
                    else {
                        LOG.error("ERROR: cannot find user_profile for [" + email + "]");
                    }
                }
                else {
                    LOG.error("ERROR: no email provided in unsubscribe event for msg_id=" + hash.get(SendGridProvider.SENDPLEX_MESSAGE_ID));
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
            Long msgId = Long.valueOf(request.getParameter(SendGridProvider.SENDPLEX_MESSAGE_ID));

            try {
                String category = request.getParameter("category");
                String email = UserProfile.parse(request);
                if (email != null) {
                    UserProfile up = UserProfileModelImpl.findByEmailStatic(email);
                    if (up != null) {
                        String userAgent = request.getParameter("useragent");
                        String ip = request.getParameter("ip");
                        LOG.info(">>unsubscribe: email=" + email + "; userAgent=" + userAgent + "; ip=" + ip);

                        // we unsubscribe the user, not just the message (no need to record user agent, ip , and referrer: they are the same for all from sendgrid)
                        UserProfileModelImpl.unsubscribeStatic(up, userAgent, ip, null, msgId);
                    }
                    else {
                        LOG.error("ERROR: cannot find user_profile for [" + email + "]");
                    }
                }
                else {
                    LOG.error("ERROR: no email provided in unsubscribe event for msg_id=" + request.getParameter(SendGridProvider.SENDPLEX_MESSAGE_ID));
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
}
