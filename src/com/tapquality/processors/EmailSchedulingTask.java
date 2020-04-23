package com.tapquality.processors;

import java.sql.SQLException;
import java.util.Collection;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.PonyException;
import com.pony.advertiser.Buyer;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.Io;
import com.pony.lead.Lead;
import com.pony.models.AsyncTaskModel;
import com.pony.models.IoModel;
import com.pony.models.MandrillTemplateModel;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherException;
import com.tapquality.email.MandrillTemplate;
import com.tapquality.email.SendEmailAsyncTask;

/**
 * Task processor to schedule an email to be sent to a lead after being sold.
 * 
 * @author dmcguire
 *
 */
public class EmailSchedulingTask {
	private static final Log LOG = LogFactory.getLog(EmailSchedulingTask.class);
	
	/**
	 * Given a lead in the context, schedule emails to be sent to them.  The email templates
	 * and time to send are based on the advertiser/buyer that the lead is sold to.
	 */
	public void scheduleEmailsForDispositions(IPublisherContext context, Collection<Disposition> dispositions) throws PonyException {
		String email = context.getUserProfile().getEmail();
		String domain = context.getArrival().getArrivalSourceId();
		
		LOG.debug(String.format("Scheduling emails for lead/domain: %s/%s", email, domain));
		if(dispositions == null || dispositions.size() <= 0) {
			LOG.debug(String.format("Not scheduling emails for lead: %s - no dispositions found", email));
		}
		
		// Iterate over each disposition and schedule emails based on buyer or advertiser.  If the disposition
		// has buyers, then templates will be retrieved and emails scheduled for each.  If not, then the templates for the
		// advertisers will be used. 
		for(Disposition disposition : dispositions) {
			if(disposition.isAccepted()) {
				if(disposition.hasBuyers()) {
					for(Buyer buyer : disposition.getBuyers()) {
						try {
							MandrillTemplate template = MandrillTemplateModel.getTemplateForBuyer(buyer.getId(), domain);
							if(template != null) {
								scheduleEmail(email, context.getLead(), template);
								LOG.debug(String.format("Not scheduling email for lead/buyer - No template found for buyer Id %d", buyer.getId()));
							}
						} catch (PonyException e) {
							LOG.error("Failed to schedule email task for lead: " + email, e);
							throw new PonyException(e);
						}
						
					}
				} else {
					try {
						IoModel ioModel = IoModel.findByLeadMatchId(disposition.getLeadMatchId());
						Io order = ioModel.getIo();
						MandrillTemplate template = MandrillTemplateModel.getTemplateForAdvertiser(order.getAdvertiserId(), domain);
						if(template != null) {
							scheduleEmail(email, context.getLead(), template);
						} else {
							LOG.debug(String.format("Not scheduling email for lead/advertiser - No template found for advertiser Id %d", order.getAdvertiserId()));
						}
					} catch (PonyException | NamingException | SQLException e) {
						LOG.error("Failed to schedule email task for lead: " + email, e);
						throw new PonyException(e);
					}
				}
			} else {
				LOG.debug(String.format("Not scheduling email for lead - Disposition not accepted.  Status is %s", disposition.getStatus().toString()));
			}
		}
	}

	/**
	 * Schedule an email to be sent at some time in the future. The template specifies the time at which the email is sent,
	 * and the lead is used to fill out the required attributes for the template.  The sending of the email will be handled
	 * by the Async Task Service.
	 * 
	 * @param email to send to
	 * @param lead populates the template with required attributes
	 * @param template specifies the mandrill template to be used, as well as how long from now to schedule the email
	 * @throws PublisherException
	 * @see com.tapquality.AsyncService
	 * @see com.tapquality.AsyncProcessor
	 */
	private void scheduleEmail(String email, Lead lead, MandrillTemplate template) throws PonyException {
		try {
			SendEmailAsyncTask sendTask = SendEmailAsyncTask.createTask(email, lead, template);
			LOG.debug(String.format("Scheduling email task for %s / %s at %s", email, template.getName(), sendTask.getNextRun().toString()));
			AsyncTaskModel.insertTask(sendTask);
		} catch (PonyException e) {
			String err = "Unable to persist SendEmailAsyncTask";
			LOG.error(err, e);
			throw new PonyException(e);
		}
		
	}
}
