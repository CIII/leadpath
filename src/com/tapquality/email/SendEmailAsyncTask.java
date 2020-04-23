package com.tapquality.email;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.json.JSONException;

import com.google.inject.Inject;
import com.pony.PonyException;
import com.pony.lead.Lead;
import com.tapquality.TapQualityException;
import com.tapquality.async.AsyncTask;
import com.tapquality.email.mandrill.MandrillClient;

/**
 * The purpose of the SendEmailAsyncTask is to send an email to a user at a scheduled date.  This is a task
 * sub-class and is processed asynchronously.  To schedule an email, see {@linkplain com.tapquality.processors.EmailSchedulingTask}.
 * Once scheduled, the task processor will pick up this task on the run_at datetime and call the {@link #execute()} method
 * @author dmcguire
 *
 */
public class SendEmailAsyncTask extends AsyncTask {
	private static final Log LOG = LogFactory.getLog(SendEmailAsyncTask.class);
	private static final String TASK_LABEL = "Send Email";
	private static final String ATTRIBUTE_EMAIL = "email";
	private static final String ATTRIBUTE_TEMPLATE = "template";
	private static final String ATTRIBUTE_CITY = "city";
	private static final String ATTRIBUTE_FIRST_NAME = "first_name";
	private static final String ATTRIBUTE_STREET = "street";
	private static final String ATTRIBUTE_PHONE = "phone_home";
	private static final String ATTRIBUTE_STATE = "state";
	
	@Inject
	private MandrillClient mandrill;
	
	protected SendEmailAsyncTask() {}
	
	/**
	 * Sends the email via {@linkplain com.tapquality.email.MandrillClient}
	 * @throws PonyException
	 */
	@Override
	public void execute() throws PonyException {
		String email = getAttributes().get(ATTRIBUTE_EMAIL);
		String template = getAttributes().get(ATTRIBUTE_TEMPLATE);
		
		if(email == null || template == null) {
			throw new PonyException("Email and template are required attributes for SendEmailAsyncTask");
		}
		
		try {
			LOG.debug(String.format("Sending email %s to %s", template, email));
			mandrill.sendMessage(email, this.getAttributes(), template);
		} catch (IOException | TapQualityException e) {
			String errMsg = "Failed to post email to Mandrill";
			LOG.error(errMsg, e);
			throw new PonyException(e);
		} catch (JSONException e) {
			String errMsg = "Failed to parse mandrill response";
			LOG.error(errMsg, e);
			throw new PonyException(e);
		}
	}
	
	/**
	 * Factory method for creating send email tasks.  Populates any required attributes from the lead.
	 * Not all attributes are always used, but there is no harm in sending extra so it's better to be
	 * exhaustive.
	 * 
	 * @param email
	 * @param lead containing information which is used as attributes for the Mandrill template
	 * @param template
	 * @return
	 */
	public static SendEmailAsyncTask createTask(String email, Lead lead, MandrillTemplate template) {
		SendEmailAsyncTask task = new SendEmailAsyncTask();
		task.setLabel(TASK_LABEL);
		task.setRepeats(false);
		task.setRetryOnFail(false);
		task.setRuns(0);
		task.setStatus(STATUS_PENDING);
		task.setNextRun(DateTime.now().plusMinutes(template.getDelayMinutes()));
		task.setAttributes(new HashMap<String, String>());
		task.getAttributes().put(ATTRIBUTE_EMAIL, email);
		task.getAttributes().put(ATTRIBUTE_TEMPLATE, template.getName());
		task.getAttributes().put(ATTRIBUTE_FIRST_NAME, lead.getAttributeValue("first_name"));
		task.getAttributes().put(ATTRIBUTE_CITY, lead.getAttributeValue("city"));
		task.getAttributes().put(ATTRIBUTE_STREET, lead.getAttributeValue("street"));
		task.getAttributes().put(ATTRIBUTE_PHONE, lead.getAttributeValue("phone_home"));
		task.getAttributes().put(ATTRIBUTE_STATE, lead.getAttributeValue("state"));
		return task;
	}
}
