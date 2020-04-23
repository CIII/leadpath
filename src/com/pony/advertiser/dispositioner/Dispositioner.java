package com.pony.advertiser.dispositioner;

import java.util.Map;

public abstract class Dispositioner {

	class TimePeriod {

		String begin, end;

		TimePeriod(String begin, String end){
			this.begin = begin;
			this.end = end;
		}
	}

	public abstract void checkForDispositions(Map<String, String[]> parameter) throws Exception;

	TimePeriod getTimePeriod(Map<String, String[]> parameters) throws Exception{

		String[] begin_entries = parameters.get("begin");
		String[] end_entries = parameters.get("end");

		if(begin_entries.length == 0 || end_entries.length == 0)
			throw new Exception("");

		return new TimePeriod(begin_entries[0],end_entries[0]);
	}

}