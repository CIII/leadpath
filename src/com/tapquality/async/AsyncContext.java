package com.tapquality.async;

import com.google.inject.Injector;

/**
 * Contains any information used during the processing of asynchronous tasks. Holds
 * the injector for instantiating task classes.
 * 
 * @author dmcguire
 *
 */
public class AsyncContext {
	private final Injector injector;
	
	public AsyncContext(Injector injector) {
		this.injector = injector;
	}

	public Injector getInjector() {
		return injector;
	}
}
