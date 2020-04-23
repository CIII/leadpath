package com.pony.validation.whitepages;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.leadtypes.PonyLead;

@RunWith(Parameterized.class)
public class WhitepagesRejectFilterTest {
	private static final String TEST_PARAMETER_NAME = "test parameter name";

	@Parameter(0)
	public Map<String, String> attributes;
	
	@Parameter(1)
	public List<String> filterValues;
	
	@Parameter(2)
	public Boolean shouldPass;
	
	@Test
	public void testRejectable() {
		Arrival arrival = new Arrival();
		Lead lead = PonyLead.create(0L, (UserProfile)null, arrival, attributes);
		
		WhitepagesRejectFilter rejectFilter = new WhitepagesRejectFilter(TEST_PARAMETER_NAME);
		for(String value : filterValues) {
			rejectFilter.addValue(value);
		}
		
		if(shouldPass == null) {
			try {
				rejectFilter.pass(null,  null, lead, null);
				fail("Did not throw exception");
			} catch (Exception e) {
				
			}
		} else {
			boolean passed = rejectFilter.pass(null, null, lead, null);
			assertEquals(shouldPass, passed);
		}
	}

	@Parameters
	public static Collection<Object[]> values() {
		// Attribute value is in the accept list.
		Map<String, String> testAttributes1 = new HashMap<>();
		testAttributes1.put(TEST_PARAMETER_NAME, "test value 1");
		List<String> testFilterValues1 = new ArrayList<>();
		testFilterValues1.add("test value 1");
		testFilterValues1.add("test values 2");
		
		// Attribute value is not in the accept list.
		Map<String, String> testAttributes2 = new HashMap<>();
		testAttributes2.put(TEST_PARAMETER_NAME, "test value 3");
		List<String> testFilterValues2 = new ArrayList<>();
		testFilterValues2.add("test value 4");
		testFilterValues2.add("test value 5");
		
		// Attribute not in the lead
		Map<String, String> testAttributes3 = new HashMap<>();
		List<String> testFilterValues3 = new ArrayList<>();
		testFilterValues3.add("test values 6");
		testFilterValues3.add("test values 7");
		
		// Filter not populated (This condition should not exist)
		Map<String, String> testAttributes4 = new HashMap<>();
		testAttributes4.put(TEST_PARAMETER_NAME, "test value 8");
		List<String> testFilterValues4 = new ArrayList<>();
		
		return Arrays.asList( new Object[][] {
			{ testAttributes1, testFilterValues1, Boolean.FALSE },
			{ testAttributes2, testFilterValues2, Boolean.TRUE },
			{ testAttributes3, testFilterValues3, Boolean.TRUE},
			{ testAttributes4, testFilterValues4, null }
		});
	}

}
