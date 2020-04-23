package com.tapquality.dispositions.guess;

import com.pony.PonyException;

@SuppressWarnings("serial")
public class GuessException extends PonyException {

	public GuessException(String message, Throwable e) {
		super(message, e);
	}

}
