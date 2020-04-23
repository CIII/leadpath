package com.tapquality.dispositions.guess;

import java.util.Map;

import org.apache.commons.csv.CSVRecord;

public interface GuessEngine {

	Guess guess(CSVRecord headers, CSVRecord record) throws GuessException;
	void recordGuess(Map<String, String> record) throws GuessException;
	boolean isInitialized();
	void shutDown();
	boolean isIndexed(String columnName);
}
