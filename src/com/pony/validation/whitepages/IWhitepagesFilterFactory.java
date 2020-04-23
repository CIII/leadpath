package com.pony.validation.whitepages;

public interface IWhitepagesFilterFactory {

	IWhitepagesFilter getWhitepagesFilter(String acceptValue, String rejectValue, String parameterName);

}