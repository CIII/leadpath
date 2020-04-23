package com.pony.validation;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;


import org.junit.Test;

import com.pony.core.PonyPhase;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.leadtypes.PonyLead;

public class ElectricBillFilterTest {

	@Test
	public void testAboveMaximum() throws Exception {
		Arrival arrival = new Arrival();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("electric_bill", "$151-200");
		Lead lead = PonyLead.create(1L, (UserProfile)null, arrival, attributes);
		
		Filter utilityFilter = new ElectricBillFilter(50, 100);
		assertFalse(utilityFilter.pass(null, null, lead, PonyPhase.POST));
	}
	
	@Test
	public void testBelowMinimum() throws Exception {
		Arrival arrival = new Arrival();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("electric_bill", "$151-200");
		Lead lead = PonyLead.create(1L, (UserProfile)null, arrival, attributes);
		
		Filter utilityFilter = new ElectricBillFilter(250, 300);
		assertFalse(utilityFilter.pass(null, null, lead, PonyPhase.POST));	
	}
	
	@Test
	public void testOverlapLow() throws Exception {
		Arrival arrival = new Arrival();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("electric_bill", "$151-200");
		Lead lead = PonyLead.create(1L, (UserProfile)null, arrival, attributes);
		
		Filter utilityFilter = new ElectricBillFilter(175, 300);
		assertTrue(utilityFilter.pass(null, null, lead, PonyPhase.POST));
	}
	
	@Test
	public void testOverlapHigh() throws Exception {
		Arrival arrival = new Arrival();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("electric_bill", "$151-200");
		Lead lead = PonyLead.create(1L, (UserProfile)null, arrival, attributes);
		
		Filter utilityFilter = new ElectricBillFilter(100, 175);
		assertTrue(utilityFilter.pass(null, null, lead, PonyPhase.POST));
	}
	
	@Test
	public void testOverlapInside() throws Exception {
		Arrival arrival = new Arrival();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("electric_bill", "$151-200");
		Lead lead = PonyLead.create(1L, (UserProfile)null, arrival, attributes);
		
		Filter utilityFilter = new ElectricBillFilter(100, 250);
		assertTrue(utilityFilter.pass(null, null, lead, PonyPhase.POST));
	}
	
	@Test
	public void testOverlapAround() throws Exception {
		Arrival arrival = new Arrival();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("electric_bill", "$151-200");
		Lead lead = PonyLead.create(1L, (UserProfile)null, arrival, attributes);
		
		Filter utilityFilter = new ElectricBillFilter(160, 190);
		assertTrue(utilityFilter.pass(null, null, lead, PonyPhase.POST));
	}
	
	@Test
	public void testLeadToInfinityBelow() throws Exception {
		Arrival arrival = new Arrival();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("electric_bill", "$151+");
		Lead lead = PonyLead.create(1L, (UserProfile)null, arrival, attributes);
		
		Filter utilityFilter = new ElectricBillFilter(150, 200);
		assertTrue(utilityFilter.pass(null, null, lead, PonyPhase.POST));
	}

	@Test
	public void testLeadToInfinityAbove() throws Exception {
		Arrival arrival = new Arrival();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("electric_bill", "$151+");
		Lead lead = PonyLead.create(1L, (UserProfile)null, arrival, attributes);
		
		Filter utilityFilter = new ElectricBillFilter(200, 300);
		assertTrue(utilityFilter.pass(null, null, lead, PonyPhase.POST));
	}
	
	@Test 
	public void testFilterToInifinityBelow() throws Exception {
		Arrival arrival = new Arrival();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("electric_bill", "$151-200");
		Lead lead = PonyLead.create(1L, (UserProfile)null, arrival, attributes);
		
		Filter utilityFilter = new ElectricBillFilter(150, null);
		assertTrue(utilityFilter.pass(null, null, lead, PonyPhase.POST));
	}
	
	@Test
	public void testFilterToInifinityAbove() throws Exception {
		Arrival arrival = new Arrival();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("electric_bill", "$151-200");
		Lead lead = PonyLead.create(1L, (UserProfile)null, arrival, attributes);
		
		Filter utilityFilter = new ElectricBillFilter(175, null);
		assertTrue(utilityFilter.pass(null, null, lead, PonyPhase.POST));
	}
	
	@Test
	public void testLeadToInfinityFalse() throws Exception {
		Arrival arrival = new Arrival();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("electric_bill", "$801+");
		Lead lead = PonyLead.create(1L, (UserProfile)null, arrival, attributes);
		
		Filter utilityFilter = new ElectricBillFilter(50, 100);
		assertFalse(utilityFilter.pass(null, null, lead, PonyPhase.POST));	
	}
	
	@Test
	public void testFilterToInfinityFalse() throws Exception {
		Arrival arrival = new Arrival();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("electric_bill", "$151-200");
		Lead lead = PonyLead.create(1L, (UserProfile)null, arrival, attributes);
		
		Filter utilityFilter = new ElectricBillFilter(300, null);
		assertFalse(utilityFilter.pass(null, null, lead, PonyPhase.POST));
	}
	
	@Test
	public void testFilterToInfinityBoth() throws Exception {
		Arrival arrival = new Arrival();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("electric_bill", "$801+");
		Lead lead = PonyLead.create(1L, (UserProfile)null, arrival, attributes);
		
		Filter utilityFilter = new ElectricBillFilter(300, null);
		assertTrue(utilityFilter.pass(null, null, lead, PonyPhase.POST));
	}
}
