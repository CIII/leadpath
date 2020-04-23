package com.tapquality.servlet.reporting;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * This class acts as a factory for creating threads that are initialized to be daemons. This is generic for use to
 * create such a thread and is not reporting specific.
 * 
 * I cannot remember the source for this code.
 *
 */
public class DaemonThreadFactory implements ThreadFactory {

	private final ThreadFactory factory;
	
	public DaemonThreadFactory() {
		this(Executors.defaultThreadFactory());
	}
	
	public DaemonThreadFactory(ThreadFactory factory) {
		if(factory == null)
			throw new NullPointerException("DaemonThreadFactory must be initialized with a ThreadFactory.");
		this.factory = factory;
	}
	
	@Override
	public Thread newThread(Runnable r) {
		final Thread t = factory.newThread(r);
		t.setDaemon(true);
		return t;
	}

}
