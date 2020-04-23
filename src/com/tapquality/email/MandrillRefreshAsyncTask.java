package com.tapquality.email;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.utils.URLEncodedUtils;
import org.joda.time.DateTime;

import com.google.inject.Inject;
import com.pony.PonyException;
import com.pony.lead.UserProfile;
import com.pony.models.MandrillEmailModel;
import com.pony.models.UserProfileModelImpl;
import com.tapquality.TapQualityException;
import com.tapquality.async.AsyncTask;
import com.tapquality.email.mandrill.MandrillClient;
import com.tapquality.email.mandrill.MandrillEmail;
import com.tapquality.email.mandrill.MandrillEmailList;

/**
 * The MandrillRefreshAsyncTask is used to gather information about emails that we have sent 
 * via Mandrill.  When we send emails using {@linkplain com.tapquality.email.MandrillClient} it
 * will insert a record into the mandrill_emails table with the Mandrill id.  This task will take
 * the id and call the Mandrill info API endpoint, and refresh any metrics such as opens, clicks, etc.
 * 
 * @author dmcguire
 *
 */
public class MandrillRefreshAsyncTask extends AsyncTask{
	private static final Log LOG = LogFactory.getLog(MandrillRefreshAsyncTask.class);
	private static final int INTERVAL_HRS = 12;
	private static final int HISTORY_DAYS = 30;
	
	@Inject
	MandrillClient mandrill;
	
	@Override
	public void execute() throws PonyException {
		LOG.info(URLEncodedUtils.class.getPackage().getSpecificationVersion());
		LOG.info(URLEncodedUtils.class.getResource("URLEncodedUtils.class"));
		LOG.debug(String.format("Running Mandrill data refresh for last %d days", HISTORY_DAYS));
		try {
			MandrillEmailList emails = mandrill.searchMessages(DateTime.now().minusDays(HISTORY_DAYS), DateTime.now());
			for(MandrillEmail email : emails) {
				UserProfile user = null;
				try {
					user = UserProfileModelImpl.findByEmailStatic(email.getEmail());
				} catch (SQLException | NamingException e) {
					String errMsg = String.format("Error retrieving user profile for email %s - inserting email with null user profile", email.getEmail());
					LOG.error(errMsg, e);
				}
				
				MandrillEmailModel.insertEmail(email, user, true);
			}
		} catch (IOException | TapQualityException e) {
			String errMsg = "Failed to retrieve emails from Mandrill";
			LOG.error(errMsg, e);
			throw new PonyException(errMsg, e);
		}
		
		// Update next run to be 12 hours from now.
		this.setNextRun(DateTime.now().plusHours(INTERVAL_HRS));
		LOG.debug("Finished refreshing email data");
	}
}
