package com.pony.email;

import com.pony.advertiser.Advertiser;
import com.pony.models.AdvertiserModel;
import com.pony.models.UserProfileModelImpl;
import com.pony.publisher.PublisherChannel;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 10/4/12
 * Time: 3:30 PM
 */
public class SuppressionServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(SuppressionServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // get user name, pwd , and email hash and advertiser , test and insert

        try {
            // check if the sending party is valid and allowed to send to us
            // get the listid that the suppression is for
            PublisherChannel channel = PublisherChannel.parse(req);
            if (channel != null) {
                // get the advertiser id (for the suppression list)
                String advertiserId = req.getParameter("advertiserid");
                Advertiser a = AdvertiserModel.find(Long.valueOf(advertiserId));
                if (a == null) {
                    // error
                    resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE); //406
                    return;
                }

                // get the email md5 and store it
                String emailMd5 = req.getParameter("md5");
                if (emailMd5 != null && !"".equals(emailMd5)) {
                    UserProfileModelImpl.createSuppressionStatic(emailMd5, a);
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
//                UserProfile up = UserProfileModel.findByEmailMd5(emailMd5);
            }
        }
        catch (NamingException e) {
            LOG.error(e);
        }
        catch (SQLException e) {
            LOG.error(e);
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
