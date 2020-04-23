package com.pony.email;

import com.pony.advertiser.Creative;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.models.CreativeModel;
import com.pony.models.HostModel;
import com.pony.models.MessageModel;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.publisher.PublisherList;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.Map;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 7/31/12
 * Time: 5:20 PM
 */
class TestProvider extends SmtpProvider {
	private static final Log LOG = LogFactory.getLog(TestProvider.class);

    private static long count = 1L;

    public TestProvider(Long hostId, String providerName) {
        super(hostId, providerName);
    }

    @Override
    public SmtpResponse send(Host host, Map<ResendCandidate, Message> messageCandidates, long delayMinutes) {
        //TODO
        return null;
    }

    @Override
    public SmtpResponse send(IPublisherContext context, Message message) throws SmtpException {
        // sending to nowhere
        // create url with params to pass on, then make an http call
        try {
            Creative creative = CreativeModel.find(message.getCreativeId());
            Host host = HostModel.find(message.getHostId());
            Arrival arrival = context.getArrival();
            UserProfile userProfile = context.getUserProfile();
            PublisherList list = context.getPublisherList();
            Lead lead = context.getLead();
            Map<String, String> attributes = lead.toMap();

            LOG.info("TestSend for message-id=" + message.getId() + " to[" + userProfile.getEmail() + "] using[" + host + "] sending creative[" + creative + "]");
            LOG.debug(lead.toString());

            long externalMessageId = count++;
            MessageModel.link(message, externalMessageId);

            return SmtpResponse.create(context, message, externalMessageId);
        }
        catch (SQLException e) {
            throw new SmtpException(e);
        }
        catch (NamingException e) {
            throw new SmtpException(e);
        }
        finally {

        }

        //return SmtpResponse.create(context, message);
    }

    @Override
    public void syncStatistics() throws SmtpException {

    }
}
