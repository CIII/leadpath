package com.pony.validation.whitepages;

public interface IWhitepagesFilter {

	void addValue(String acceptValue);
	String getParameterName();
	int getValuesSize();

}