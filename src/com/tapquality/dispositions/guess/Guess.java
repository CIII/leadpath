package com.tapquality.dispositions.guess;

public class Guess {

	public static enum Status {
		SUCCESS ("success"),
		CONFLICT ("conflict"),
		NO_GUESS ("no-guess");

		private final String code;
		
		private Status(String code) {
			this.code = code;
		}
		
		public String code() {
			return code;
		}
		
		@Override
		public String toString() {
			return code;
		}
	}
	
	protected Status status;
	protected String guess;
	protected String errorText;
	
	public Guess(String guess, Status status) {
		this.guess = guess;
		this.status = status;
		this.errorText = null;
	}
	
	public Guess(Status status, String errorText) {
		this.guess = null;
		this.status = status;
		this.errorText = errorText;
	}
	
	public String getGuess() {
		return this.guess;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public String getErrorText() {
		return this.errorText;
	}

}
