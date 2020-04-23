package com.tapquality.formatter;

import com.pony.lead.Lead;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationResponse;

public class ValidationFailureMessage {
	protected PublisherContext context;
	protected ValidationResponse response;
	
	public ValidationFailureMessage(PublisherContext context, ValidationResponse response) {
		this.context = context;
		this.response = response;
	}
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		if(context.getArrival() != null) {
			output.append("Arrival: " + context.getArrival().getId() + "\n");
		} else {
			output.append("No arrival assigned yet.\n");
		}
		if(context.getLeadId() != null) {
			output.append("Lead: ");
			output.append(context.getLeadId());
			output.append("\n");
		} else {
			output.append("No lead assigned yet.\n");
		}
		
		output.append("Validation failure: ");
		output.append(this.response.toString());
		
		return output.toString();
	}
}
