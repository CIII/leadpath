package com.pony.validation.whitepages;

public class WhitepagesFilterFactory implements IWhitepagesFilterFactory {
	@Override
	public IWhitepagesFilter getWhitepagesFilter(String acceptValue, String rejectValue, String parameterName) {
		IWhitepagesFilter filter;
		if(acceptValue != null) {
			filter = new WhitepagesAcceptFilter(parameterName);
		} else {
			filter = new WhitepagesRejectFilter(parameterName);
		}
		return filter;
	}
}
