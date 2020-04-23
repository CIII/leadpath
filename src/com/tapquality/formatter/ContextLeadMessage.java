package com.tapquality.formatter;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.advertiser.Advertiser;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.models.AdvertiserModel;
import com.pony.models.UserProfileModel;
import com.pony.models.UserProfileModelImpl;

public class ContextLeadMessage {
	private static Log LOG = LogFactory.getLog(ContextLeadMessage.class);

	protected Lead lead;
	protected Map<RoutingCandidate, Disposition> dispositions;
	
	public ContextLeadMessage(Lead lead, Map<RoutingCandidate, Disposition> dispositions) {
		this.lead = lead;
		this.dispositions = dispositions;
	}
	
	public String getFormattedMessage() {
		StringBuilder emailBody = new StringBuilder();
		if(lead.getAttributeValue("first_name") != null || lead.getAttributeValue("last_name") != null) {
			emailBody.append("Name: ");
			if(lead.getAttributeValue("first_name") != null) emailBody.append(lead.getAttributeValue("first_name"));
			if(lead.getAttributeValue("first_name") != null && lead.getAttributeValue("last_name") != null) emailBody.append(" ");
			if(lead.getAttributeValue("last_name") != null) emailBody.append(lead.getAttributeValue("last_name"));
			emailBody.append("\n");
		}
		if(
				lead.getAttributeValue("street") != null ||
				lead.getAttributeValue("city") != null ||
				lead.getAttributeValue("state") != null ||
				lead.getAttributeValue("zip") != null
				) {
			emailBody.append("Address: ");
			if(lead.getAttributeValue("street") != null) {
				emailBody.append(lead.getAttributeValue("street"));
				emailBody.append(" ");
			}
			if(lead.getAttributeValue("city") != null) {
				emailBody.append(lead.getAttributeValue("city"));
				emailBody.append(" ");
			}
			if(lead.getAttributeValue("state") != null) {
				emailBody.append(lead.getAttributeValue("state"));
				emailBody.append(" ");
			}
			if(lead.getAttributeValue("zip") != null) {
				emailBody.append(lead.getAttributeValue("zip"));
				emailBody.append(" ");
			}
			emailBody.append("\n");
		}
		if(lead.getAttributeValue("electric_company") != null) {
			emailBody.append("Electric company:");
			emailBody.append(lead.getAttributeValue("electric_company"));
			emailBody.append("\n");
		}
		if(lead.getAttributeValue("electric_bill") != null) {
			emailBody.append("Electric bill: ");
			emailBody.append(lead.getAttributeValue("electric_bill"));
			emailBody.append("\n");
		}
		if(lead.getAttributeValue("phone_home") != null) {
			emailBody.append("Phone: ");
			emailBody.append(lead.getAttributeValue("phone_home"));
			emailBody.append("\n");
		}
		UserProfile userProfile;
    	try {
    		if(lead.getUserProfileId() != null) {
    			userProfile = UserProfileModelImpl.findStatic(lead.getUserProfileId());
    		} else {
    			LOG.warn("Failure to find a user profile ID for lead. Proceding without an email, which may indicate we delivered a bad lead.");
    			userProfile = null;
    		}
    	} catch (Throwable e) {
    		LOG.error("Failure to find a user profile with ID: " + lead.getUserProfileId() + ". This may indicate we delivere a bad lead.", e);
    		userProfile = null;
    	}
		if(userProfile != null) {
			emailBody.append("Email: ");
			emailBody.append(userProfile.getEmail());
			emailBody.append("\n");
		}
	    emailBody.append("\n");
	    for(Map.Entry<RoutingCandidate, Disposition> entry : dispositions.entrySet()) {
	    	emailBody.append("\n");
	    	Disposition disposition = entry.getValue();
	    	RoutingCandidate candidate = entry.getKey();
	    	Advertiser advertiser;
	    	try {
	    		if(candidate.getIo() != null && candidate.getIo().getAdvertiserId() != null) {
	    			advertiser = AdvertiserModel.find(candidate.getIo().getAdvertiserId());
	    		} else {
	    			advertiser = null;
	    		}
	    	} catch (Throwable e) {
	    		LOG.error("Error retrieving advertiser formatting the internal email.", e);
	    		advertiser = null;
	    	}
	        if(advertiser != null) {
	        	emailBody.append("Advertiser: ");
		        emailBody.append(advertiser.getName());
		        emailBody.append("\n");
	        }
	        if(candidate.getBuyer() != null) {
	        	emailBody.append("Buyer: ");
	        	emailBody.append(candidate.getBuyer().getName());
	        	emailBody.append("\n");
	        }
	        if(disposition.getStatus() != null) {
	        	emailBody.append("Status: ");
	        	switch(disposition.getStatus().getStatus()) {
	        	case 1:
	        		emailBody.append("Accepted");
	        		break;
	        	case 0:
	        		emailBody.append("Rejected");
	        		break;
	        	case 99:
	        		emailBody.append("Test Detected. This should not have gotten as far as this; tests are supposed to be screened out earlier now.");
	        		break;
	        	case 3:
	        		emailBody.append("No Coverage");
	        		break;
	        	case 4:
	        		emailBody.append("Returned");
	        		break;
	        	case 2:
	        		emailBody.append("Unsold");
	        		break;
	        	case -1:
	        		emailBody.append("Unsupported");
	        		break;
	        	default:
	        		emailBody.append("Unknown");
	        	}
	        	emailBody.append("\n");
	        }
	        if(disposition.getPrice() != null) {
	        	emailBody.append("Price: ");
	        	emailBody.append(disposition.getPrice());
	        	emailBody.append("\n");
	        }
	    }

		return emailBody.toString();
	}
	
	@Override
	public String toString() {
		return getFormattedMessage();
	}
}
