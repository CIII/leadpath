package com.pony.advertiser.writers.solar;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.Disposition.DispositionCategory;
import com.pony.advertiser.RoutingCandidate;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.publisher.IPublisherContext;
import com.pony.validation.ValidationResponse;
import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.fault.UnexpectedErrorFault;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

/**
 * This writer communicates a lead to BostonSolar's Salesforce system, using the bostonsolar.jar and force-wsc.jar
 * libraries. It does not have a requestPrice call, so it must be prioritized by the VPL of the order settings.
 */
public class BostonSolarWriter extends AdvertiserWriter {
	private static final String PROPERTY_OWNERSHIP_PARAM = "property_ownership";
	private static final String COMPANY_VALUE = "N/A";
	private static final String LEAD_SOURCE_VALUE = "TapQuality";
	private static final String COUNTRY_VALUE = "US";
	private static final String ZIP_PARAM = "zip";
	private static final String STATE_PARAM = "state";
	private static final String CITY_PARAM = "city";
	private static final String STREET_PARAM = "street";
	private static final String PURCHASED_LEAD_VALUE = "Purchased Lead";
	private static final String PHONE_HOME_PARAM = "phone_home";
	private static final String LEAD_TYPE_VALUE = "Residential";
	private static final String LAST_NAME_PARAM = "last_name";
	private static final String FIRST_NAME_PARAM = "first_name";
	private static final String CREDIT_SCORE_PARAM = "credit_range";
	private static final String ELECTRIC_BILL_PARAM = "electric_bill";
	public static final String PASSWORD = "password";
	public static final String USERNAME = "username";
	private static Log LOG = LogFactory.getLog(BostonSolarWriter.class);
	private static Pattern electricBillPattern = Pattern.compile("\\$?\\d+[-+](\\d*)");
	private static Pattern creditScorePattern = Pattern.compile("(Under|Above|\\d+)[- ](\\d+)");

	protected ConnectorConfig config = null;
		
	protected ConnectorConfig getConfig() {
		config = new ConnectorConfig();
		config.setUsername(this.writerProperties.getProperty(this.name + USERNAME));
		config.setPassword(this.writerProperties.getProperty(this.name + PASSWORD));
		
		return config;
	}
	
	@Override
	public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse,
			RoutingCandidate candidate) {
		Disposition returnValue = null;
		Long msgId = logPostMessage(leadMatchId, "Submitting to SalesForce.");
		try {
			com.sforce.soap.enterprise.sobject.Lead salesforceLead = populateLead(publisherContext.getLead(), publisherContext.getUserProfile());
			SaveResult[] results = postToBostonSolar(salesforceLead);
		
			StringBuilder builder = new StringBuilder();
			boolean error = false;
			for(SaveResult result : results) {
				if(!result.isSuccess()) {
					error = true;
					for(com.sforce.soap.enterprise.Error soapError : result.getErrors()) {
						builder.append(soapError.toString());
						builder.append("\n");
					}
					
				} else {
					builder.append(result.getId() + ",");
				}
			}
			if(!error) {
				logResponseMessage(msgId, "Successful put to SalesForce");
				returnValue = Disposition.createPost(Disposition.Status.ACCEPTED, null, builder.toString(), candidate.getIo().getVpl(), builder.toString());
			} else {
				logResponseMessage(msgId, builder.toString());
				returnValue = Disposition.createPost(Disposition.Status.REJECTED, DispositionCategory.ERROR, builder.toString());
			}
		} catch (ConnectionException e) {
			returnValue = Disposition.createPost(Disposition.Status.REJECTED, DispositionCategory.ERROR, "Exception connecting to Salesforce: " + e.toString());
			logResponseMessage(msgId, "Exception connecting to Salesforce: " + e.toString());
			LOG.error("Exception connecting to Salesforce.", e);
		} catch (Throwable e) {
			returnValue = Disposition.createPost(Disposition.Status.REJECTED, DispositionCategory.ERROR, "Unknown exception posting lead to Salesforce: " + e.toString());
			logResponseMessage(msgId, "Unknown exception posting lead to Salesforce: " + e.toString());
			LOG.error("Unknown error posting lead to Salesforce.", e);
		}
				
		return returnValue;
	}
	
	public SaveResult[] postToBostonSolar(com.sforce.soap.enterprise.sobject.Lead salesforceLead) throws ConnectionException {
		EnterpriseConnection connection = null;
		
		SaveResult[] results;
		try {
			try {
				connection = Connector.newConnection(getConfig());
			} catch (UnexpectedErrorFault e) {
				connection = Connector.newConnection(getConfig());
			}
		
			results = connection.create(new com.sforce.soap.enterprise.sobject.Lead[] { salesforceLead });
			
		} catch (ConnectionException e) {
			LOG.error("Exception connecting to Salesforce. Logging and rethrowing error.", e);
			throw e;
		} finally {
			if (connection != null) {
				try {
					connection.logout();
				} catch (ConnectionException e) {
					LOG.warn("Exception logging out of Salesforce. Suppressing this exception.");
				}
			}
		}
			
		return results;
	}

	com.sforce.soap.enterprise.sobject.Lead populateLead(Lead lead, UserProfile userProfile) {
		com.sforce.soap.enterprise.sobject.Lead salesforceLead = new com.sforce.soap.enterprise.sobject.Lead();
		
		String electricBillRaw = lead.getAttributeValue(ELECTRIC_BILL_PARAM);
		Double electricBillValue;
		if(electricBillRaw == null) {
			electricBillValue = 0.0;
		} else {
			Matcher electricBillMatcher = electricBillPattern.matcher(electricBillRaw);
			String electricBillValueString;
			if(electricBillMatcher.matches()) {
				if(!"".equals(electricBillValueString = electricBillMatcher.group(1))) {
					electricBillValue = Double.valueOf(electricBillValueString);
				} else {
					electricBillValue = 1000.0;
				}
			} else {
				electricBillValue = 0.0;
			}
		}
		
		String creditScoreRaw = lead.getAttributeValue(CREDIT_SCORE_PARAM);
		Double creditScoreValue;
		if(creditScoreRaw == null) {
			creditScoreValue = 0.0;
		} else {
			Matcher creditScoreMatcher = creditScorePattern.matcher(creditScoreRaw);
			if(creditScoreMatcher.matches()) {
				String creditScoreValueString = creditScoreMatcher.group(1);
				switch(creditScoreValueString) {
				case "Above":
					creditScoreValue = 850.0;
					break;
				default:
					creditScoreValueString = creditScoreMatcher.group(2);
					creditScoreValue = Double.valueOf(creditScoreValueString);
				}
			} else {
				creditScoreValue = 0.0;
			}
		}
		salesforceLead.setFirstName(lead.getAttributeValue(FIRST_NAME_PARAM));
		salesforceLead.setLastName(lead.getAttributeValue(LAST_NAME_PARAM));
		salesforceLead.setType__c(LEAD_TYPE_VALUE);
		salesforceLead.setEmail(userProfile.getEmail());
		salesforceLead.setElectrical_Monthly_Bill__c(electricBillValue);
		salesforceLead.setCredit_Score__c(creditScoreValue);
		salesforceLead.setPhone(lead.getAttributeValue(PHONE_HOME_PARAM));
		salesforceLead.setLeadSource(PURCHASED_LEAD_VALUE);
		salesforceLead.setStreet(lead.getAttributeValue(STREET_PARAM));
		salesforceLead.setCity(lead.getAttributeValue(CITY_PARAM));
		salesforceLead.setState(lead.getAttributeValue(STATE_PARAM));
		salesforceLead.setPostalCode(lead.getAttributeValue(ZIP_PARAM));
		salesforceLead.setCountry(COUNTRY_VALUE);
		salesforceLead.setSecondary_Lead_Source__c(LEAD_SOURCE_VALUE);
		salesforceLead.setCompany(COMPANY_VALUE);
		salesforceLead.setProperty_Owner__c("RENT".equals(lead.getAttributeValue(PROPERTY_OWNERSHIP_PARAM)) ? false : true);
		
		
		return salesforceLead;
	}
}
