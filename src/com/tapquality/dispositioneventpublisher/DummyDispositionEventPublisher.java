package com.tapquality.dispositioneventpublisher;

import java.util.Collection;

import com.pony.advertiser.Disposition;

/**
 * This object is intended to be dead-end for events published by Leadpath. This publisher can be injected to prevent
 * communication errors in a test environment when it is known that a real publication target will not be available.
 * 
 * @author joncard
 *
 */
public class DummyDispositionEventPublisher implements IDispositionEventPublisher {

	@Override
	public boolean publishDispositions(Long leadId, Collection<Disposition> dispositions) {
		return true;
	}

}
