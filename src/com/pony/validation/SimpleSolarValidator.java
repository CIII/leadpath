package com.pony.validation;

import com.pony.lead.Lead;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;

/**
 * 
 *
 */
public class SimpleSolarValidator {
	public static ValidationResponse validate(IPublisherContext context) {
		Lead lead = context.getLead();
		
		ValidationResponse response = ValidationResponse.NOOP;
		
		String zip = lead.getAttributeValue("zip");
		if(zip == null) {
			context.addErrorCode(ValidationResponse.INVALID_ZIP.getName(), "Zip code is missing.");
			response = ValidationResponse.INVALID_ZIP;
		}
		String street = lead.getAttributeValue("street");
		if(street == null) {
			context.addErrorCode(ValidationResponse.INVALID_STREET.getName(), "Street is missing");
			response = ValidationResponse.INVALID_STREET;
		}
		
		return response;
	}
}
