package com.pony.advertiser.writers.solar.zoho;

import java.util.HashMap;
import java.util.Map;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.models.UserProfileModel;

public class ZohoLeadConverter implements Converter<Lead> {
	protected static Map<String, String> attributes = new HashMap<>();
	protected final UserProfileModel userProfileModel;
	protected final boolean isTest;
	
	static {
		attributes.put("First Name", "first_name");
		attributes.put("Last Name", "last_name");
		attributes.put("Phone", "phone_home");
		attributes.put("Street", "street");
		attributes.put("City", "city");
		attributes.put("State", "state");
		attributes.put("Zip Code", "zip");
	}
	
	public ZohoLeadConverter(UserProfileModel model, boolean isTest) {
		this.userProfileModel = model;
		this.isTest = isTest;
	}

	@Override
	public Lead read(InputNode arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void write(OutputNode node, Lead lead) throws Exception {

		node.setName("Leads");
		OutputNode rowNode = node.getChild("row");
		rowNode.getAttributes().put("no", "1");
		
		OutputNode attributeNode = createNode(rowNode, "Lead Source", "TapQuality");
		
		UserProfile userProfile = userProfileModel.find(lead.getUserProfileId());
		attributeNode = createNode(rowNode, "Email", userProfile.getEmail());
		
		for(String val : attributes.keySet()) {
			String value = lead.getAttributeValue(attributes.get(val));
			if (value == null) throw new NullPointerException(value + " is null in the lead. Invalid lead values.");
			attributeNode = createNode(rowNode, val, value);
		}
		
		String electricBill = lead.getAttributeValue("electric_bill");
		electricBill = electricBill != null ? electricBill : "N/A";
		String electricCompany = lead.getAttributeValue("electric_company");
		electricCompany = electricCompany != null ? electricCompany : "N/A";
		attributeNode = createNode(rowNode, "Description", "Bill: " + electricBill + ", Utility: " + electricCompany + (isTest ? " This is test data from TapQuality. Please disregard." : ""));
		
		attributeNode.commit();
		rowNode.commit();
		node.commit();
	}

	private OutputNode createNode(OutputNode rowNode, String val, String value) throws Exception {
		OutputNode attributeNode;
		attributeNode = rowNode.getChild("FL");
		attributeNode.getAttributes().put("val", val);
		attributeNode.setValue(value);
		return attributeNode;
	}

}
