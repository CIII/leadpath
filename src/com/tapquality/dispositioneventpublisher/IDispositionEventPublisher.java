package com.tapquality.dispositioneventpublisher;

import java.util.Collection;

import com.pony.advertiser.Disposition;

/**
 * Implementations of this interface are used to publish results of the lead disposition
 * 
 * @author joncard
 *
 */
public interface IDispositionEventPublisher {
	public boolean publishDispositions(Long leadId, Collection<Disposition> dispositions);
}
