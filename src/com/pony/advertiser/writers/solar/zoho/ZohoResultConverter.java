package com.pony.advertiser.writers.solar.zoho;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

public class ZohoResultConverter implements Converter<ZohoResult> {

	@Override
	public ZohoResult read(InputNode node) throws Exception {
		InputNode childNode = node.getNext("message");
		String message = childNode.getValue();
		String id = null;
		
		InputNode details = node.getNext("recorddetail");
		while((childNode = details.getNext()) != null) {
			if ("Id".equals(childNode.getAttribute("val").getValue())) {
				id = childNode.getValue();
			}
		}
		
		return new ZohoResult(id, message);
		
	}

	@Override
	public void write(OutputNode arg0, ZohoResult arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
