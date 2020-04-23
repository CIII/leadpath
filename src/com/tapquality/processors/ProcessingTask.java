package com.tapquality.processors;

import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherException;

/**
 * Instances of this class are intended to be chained together, modifying the publisher context in some way, including
 * the leads and/or other items in it.
 */
public interface ProcessingTask {
	/**
	 * This method follows the general pattern of functional programming, but I'm not prepared to swear that it follows
	 * those rules completely, as I am not as familiar with it as I would like. Generally, do not assume that the
	 * publisher context, or any contained items, are the same instance that was passed in, though it is likely they
	 * will be. Additionally, there may be "side affects" from calling the methods; they are not idempotent. This may
	 * include processing and refining the items in the context, or it may include persisting the items to the database
	 * or sending them to advertisers.
	 *
	 * @param context
	 * @return
	 */
	IPublisherContext process(IPublisherContext context) throws PublisherException;
}
