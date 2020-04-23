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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Track Bounces
 * <p/>
 * PonyLeads 2012.
 * User: martin
 * Date: 12/6/12
 * Time: 3:59 PM
 */
public class BounceEvent extends SendGridEvent {
    private static final Log LOG = LogFactory.getLog("BounceEvent");

    BounceEvent() {
        super("bounce");
    }

    @Override
    void handle(HashMap<String, String> hash) throws SendGridException {
        if (hash.get(SendGridProvider.SENDPLEX_MESSAGE_ID) != null) {
            LOG.info("processing bounce event...");
            // insert into bounces
            try {
                String status = hash.get("status"); // 3-digit status code
                if(status != null){
                    status = status.replace(" ", "").replace(".", "");
                }
                String reason = hash.get("reason"); // Bounce reason from MTA
//                String type = hash.get("type"); // Bounce/Blocked/Expired
//                String category = hash.get("category");

                String email = hash.get(UserProfile.EMAIL);
                if (email != null) {
                    LOG.debug("bounce: email=" + email + " messageId=" + hash.get(SendGridProvider.SENDPLEX_MESSAGE_ID) + " status=" + status + " reason=" + reason);
                    UserProfile up = UserProfileModelImpl.findByEmailStatic(email);
                    if (up != null) {
                        MessageModel.bounced(up, Long.valueOf(hash.get(SendGridProvider.SENDPLEX_MESSAGE_ID)), Integer.valueOf(status), reason);
                    }
                    else {
                        LOG.error("ERROR: cannot find user_profile for [" + email + "]");
                    }
                }
                else {
                    LOG.error("ERROR: no email provided in bounce event for msg_id=" + hash.get(SendGridProvider.SENDPLEX_MESSAGE_ID));
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
            LOG.info("processing bounce event...");
            // insert into bounces
            try {
                String status = request.getParameter("status"); // 3-digit status code
                if(status != null){
                    status = status.replace(" ", "").replace(".", "");
                }
                String reason = request.getParameter("reason"); // Bounce reason from MTA
//                String type = request.getParameter("type"); // Bounce/Blocked/Expired
//                String category = request.getParameter("category");

                String email = UserProfile.parse(request);
                if (email != null) {
                    LOG.info("bounce: email=" + email + " messageId=" + request.getParameter(SendGridProvider.SENDPLEX_MESSAGE_ID) + " status=" + status + " reason=" + reason);
                    UserProfile up = UserProfileModelImpl.findByEmailStatic(email);
                    if (up != null) {
                        MessageModel.bounced(up, Long.valueOf(request.getParameter(SendGridProvider.SENDPLEX_MESSAGE_ID)), Integer.valueOf(status), reason);
                    }
                    else {
                        LOG.error("ERROR: cannot find user_profile for [" + email + "]");
                    }
                }
                else {
                    LOG.error("ERROR: no email provided in bounce event for msg_id=" + request.getParameter(SendGridProvider.SENDPLEX_MESSAGE_ID));
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

    public static void main(String[] args){
        String s = "5.1.1";
        s= s.replace(" ", "").replace(".", "");
        LOG.debug(s);

        LOG.debug(s.substring(0, (s.length() > 254 ? 254 : s.length())));
    }
}
