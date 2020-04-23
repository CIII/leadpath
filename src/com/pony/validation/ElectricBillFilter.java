package com.pony.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;

public class ElectricBillFilter extends Filter {
	private static final String ELECTRIC_BILL = "electric_bill";

	private static final Log LOG = LogFactory.getLog(ElectricBillFilter.class);
	
	protected Pattern pattern = Pattern.compile("^\\$?(\\d+)[-+](\\d*)$");
	protected Integer minimum;
	protected Integer maximum;
	
	public ElectricBillFilter(Integer minimum, Integer maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
	}

	@Override
	public boolean pass(Publisher publisher, PublisherList publisherList, Lead lead, PonyPhase phase) {
		String electricBill = lead.getAttributeValue(ELECTRIC_BILL);
		if(electricBill == null) {
			LOG.info("Did not find an attribute 'electric_bill' in the lead; assuming this is a mistake and not allowing the lead to pass the filter.");
			return false;
		}

		// Request list of utility companies from redis
		LOG.debug("Electric Bill: '" + electricBill + "'");
		Matcher m = pattern.matcher(electricBill);
		if(!m.matches()) {
			LOG.info("Parsing error extracting the minimum and maximum from the electric bill attribute; assuming this is an error and not allowing the lead to pass the filter.");
			return false;
		}
		
		String leadMinimumString = m.group(1);
		String leadMaximumString = m.group(2);
		
		try {
			Integer leadMinimum = Integer.parseInt(leadMinimumString);
			Integer leadMaximum = "".equals(leadMaximumString) ? null : Integer.parseInt(leadMaximumString);
		
			// This logic looks backwards, but I want to ensure that, if the order's maximum falls into a range from our
			//   website, we still submit the lead because there are values in that range that satisfy the order's
			//   requirements.
			if((this.maximum == null && leadMaximum == null) ||
					((this.maximum == null && leadMaximum >= this.minimum) || (this.maximum != null && leadMinimum < this.maximum)) &&
					((leadMaximum == null && this.maximum >= leadMinimum) || (leadMaximum != null && leadMaximum > this.minimum))) {
				return true;
			} else {
				LOG.debug("Electric Bill filter: rejected");
				return false;
			}
		} catch (NumberFormatException e) {
			LOG.warn("Exception parsing the minimum or maximum from the electric bill attribute '" + electricBill + ". Preventing the lead from passing the filter.");
			return false;
		}
	}

}
