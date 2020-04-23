package com.tapquality.processors;

import com.pony.PonyException;
import com.pony.lead.Lead;
import com.pony.models.LeadModel;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherException;
import com.pony.validation.ValidationResponse;
import com.tapquality.lead.duplicates.DuplicateLead;

public class DetectDuplicates implements ProcessingTask {

	@Override
	public IPublisherContext process(IPublisherContext context) throws PublisherException {

		Lead newLead;
		if(context.isEmailDup()) {
			String errMsg = "Duplicate lead detected";
			context.setValidationResponse(ValidationResponse.DUP);
			context.addErrorCode(ValidationResponse.DUP.getName(), errMsg);
			throw context.getException();
		} else {
			try {
				newLead = LeadModel.checkDuplicateLead(context.getLead());
			} catch (PonyException e) {
				String errMsg = "Exception checking for duplicates.";
				context.setValidationResponse(ValidationResponse.DUP);
				context.addErrorCode(ValidationResponse.DUP.getName(), errMsg);
				throw context.getException();
			}
		}
		context.setLead(newLead);

    	return context;
	}

}
