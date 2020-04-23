package com.pony.advertiser.factory;

import java.math.BigDecimal;

import com.pony.PonyException;
import com.pony.advertiser.Disposition;
import com.pony.core.PonyPhase;
import com.pony.lead.LeadMatch;

public abstract class AbstractDispositionParser implements DispositionParser {
	static {
		TYPES.put("contacted", Disposition.Status.CONTACTED);
		TYPES.put("quoted", Disposition.Status.QUOTED); // -> Contacted
		TYPES.put("appointment-set", Disposition.Status.APPOINTMENT_SET); // ->
		TYPES.put("appointment-completed", Disposition.Status.APPOINTMENT_COMPLETED); //
		TYPES.put("contract-signed", Disposition.Status.CONTRACT_SIGNED);
		TYPES.put("installed", Disposition.Status.INSTALLED);
		TYPES.put("returned", Disposition.Status.RETURNED);
		TYPES.put("lost", Disposition.Status.LOST);
		TYPES.put("bad-info", Disposition.Status.BAD_INFO);
		TYPES.put("invalid", Disposition.Status.INVALID);
		TYPES.put("appointment-1-set", Disposition.Status.APPOINTMENT_1_SET);
		TYPES.put("appointment-2-set", Disposition.Status.APPOINTMENT_2_SET);
		TYPES.put("duplicate", Disposition.Status.DUPLICATE);
	}

	protected Disposition createNewDisposition(String externalId, BigDecimal price, String comment, Disposition.Status status, LeadMatch leadMatch)
			throws PonyException {
				Disposition newDisposition;
				assert status != null : "A status is required to create a new disposition for the OUTCOME phase.";
			
				if (status == Disposition.Status.RETURNED) {
					BigDecimal newPrice = leadMatch.getPrice().multiply(new BigDecimal(-1.0));
					newDisposition = Disposition.create(PonyPhase.OUTCOME, Disposition.Status.RETURNED, false, newPrice,
							externalId, comment);
					newDisposition.setLeadMatchId(leadMatch.getId());
				} else {
					newDisposition = Disposition.create(PonyPhase.OUTCOME, status, false, price, externalId, comment);
					newDisposition.setLeadMatchId(leadMatch.getId());
				}
				return newDisposition;
			}

}
