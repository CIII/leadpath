package com.pony.lead;

import static org.junit.Assert.*;

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
public class LeadElectricBillTest {

	@Parameter(0)
	public String testElectricBillInput;
	
	@Test
	public void testElectricBill() {
		Map<String, String> attributes = new HashMap<>();
		Lead lead = new PonyLead(0L, -1L, 0L, 0L, attributes);
		Map<String, String> newAttributes = new HashMap<>();
		newAttributes.put("electric_bill", testElectricBillInput);
		lead.mergeAttributes(newAttributes);
		assertEquals("$100-151", lead.getAttributeValue("electric_bill"));
	}

	@Parameters
	public static Collection<Object[]> electricBillData() {
		return Arrays.asList( new Object[][] {
			{ "$100-151" },
			{ "$100-$151" },
			{ "%24100-151" }
		});
	}
}
