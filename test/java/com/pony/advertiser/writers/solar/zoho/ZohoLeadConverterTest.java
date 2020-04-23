package com.pony.advertiser.writers.solar.zoho;

import static org.junit.Assert.*;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.Registry;
import org.simpleframework.xml.convert.RegistryStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.leadtypes.PonyLead;
import com.pony.models.UserProfileModel;

public class ZohoLeadConverterTest {

	@Test
	public void testConversion() throws Exception {
		Map<String, String> attributes = new HashMap<>();
		attributes.put("first_name", "Test First");
		attributes.put("last_name", "Test Last");
		attributes.put("phone_home", "123456789");
		attributes.put("street", "test street");
		attributes.put("city", "test city");
		attributes.put("state", "test state");
		attributes.put("zip", "test zip");
		//attributes.put("electric_bill", "test bill");
		//attributes.put("electric_company", "test company");
		Lead lead = new PonyLead(1L, 2L, 3L, 4L, attributes);
		
		Registry registry = new Registry();
		Strategy strategy = new RegistryStrategy(registry);
		Serializer serializer = new Persister(strategy);

		Mockery context = new Mockery();
		final UserProfile userProfile = new UserProfile("test email", false, null);
		final UserProfileModel model = context.mock(UserProfileModel.class);
		context.checking(new Expectations() {{
			try {
				oneOf(model).find(3L); will(returnValue(userProfile));
			} catch (Exception e) {
				fail("Threw exception setting up expectations.");
			}
		}});

		
		registry.bind(PonyLead.class, new ZohoLeadConverter(model, true));
		StringWriter writer = new StringWriter();
		serializer.write(lead, writer);
		
		String result = writer.toString();
		System.out.println(writer.toString());
		assertTrue(result.contains("TapQuality"));
	}
}
