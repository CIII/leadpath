package com.pony.validation.whitepages;

import java.util.ArrayList;
import java.util.List;

import com.pony.validation.Filter;

public abstract class AbstractWhitepagesFilter extends Filter implements IWhitepagesFilter {

	protected List<String> values = new ArrayList<>();
	protected String parameterName;
	
	public AbstractWhitepagesFilter(String parameterName) {
		this.parameterName = parameterName;
	}
	
	@Override
	public void addValue(String value) {
		values.add(value);
	}
	
	@Override
	public String getParameterName() {
		return this.parameterName;
	}
	
	@Override
	public int getValuesSize() {
		return this.values.size();
	}

}
