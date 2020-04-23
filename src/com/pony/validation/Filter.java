package com.pony.validation;

import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherList;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 6/26/13
 * Time: 2:42 PM
 */
public abstract class Filter {
    public abstract boolean pass(Publisher publisher, PublisherList publisherList, Lead lead, PonyPhase phase);
}
