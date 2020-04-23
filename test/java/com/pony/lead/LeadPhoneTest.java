package com.pony.lead;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.pony.leadtypes.PonyLead;

@RunWith(Parameterized.class)
public class LeadPhoneTest {

	// Begin phone tests
	@Parameter(0)
	public String testPhoneInput;
	
	/**
	 * The following tests 
	 */
	@Test
	public void testPhone() {
		Map<String, String> attributes = new HashMap<>();
		Map<String, String> newAttributes = new HashMap<>();
		newAttributes.put("phone_home", testPhoneInput);
		Arrival testArrival = new Arrival();
		Lead testLead = PonyLead.create(0L, (UserProfile)null, testArrival, attributes);
		testLead.mergeAttributes(newAttributes);
		assertEquals("1234567890", testLead.getAttributeValue("phone_home"));
	}
	
	@Parameters
	public static Collection<Object[]> phoneData() {
		return Arrays.asList( new Object[][] {
			{ "(123) 456-7890"},
			{ "123 456-7890"},
			{ "123456-7890"},
			{ "123 456 7890"},
			{ "123-456-7890"}
		});
	}
	// End phone tests
}
