package com.pony.advertiser.writers.solar.zoho;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import com.pony.advertiser.Disposition;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.leadtypes.PonyLead;
import com.pony.models.UserProfileModel;
import com.pony.publisher.IPublisherContext;

public class ZohoWriterTestSystem {

	@Test
	public void testSendMessage() throws Exception {
		String name = "invaleon";
		Properties properties = new Properties();
		properties.put(name + "url", "https://crm.zoho.com/crm/private/xml/Leads/insertRecords");
		properties.put(name + "authtoken", "b3b242af5f62a13b001561eef5c71ecb");
		
		Map<String, String> attributes = new HashMap<>();
		attributes.put("first_name", "Test First");
		attributes.put("last_name", "Test Last");
		attributes.put("phone_home", "123456789");
		attributes.put("street", "test street");
		attributes.put("city", "test city");
		attributes.put("state", "test state");
		attributes.put("zip", "test zip");
		attributes.put("electric_bill", "test bill");
		attributes.put("electric_company", "test company");
		final Lead lead = new PonyLead(1L, 2L, 3L, 4L, attributes);
		Mockery context = new Mockery();
		final UserProfile userProfile = new UserProfile("test email", false, null);
		final UserProfileModel model = context.mock(UserProfileModel.class);
		final IPublisherContext pubContext = context.mock(IPublisherContext.class);
		context.checking(new Expectations() {{
			try {
				oneOf(model).find(3L); will(returnValue(userProfile));
			} catch (Exception e) {
				fail("Threw exception setting up expectations.");
			}
			
			oneOf(pubContext).getLead(); will(returnValue(lead));
		}});

		ZohoWriter writer = new ZohoWriter(model);
		writer.setProperties(name, properties);
		Disposition disposition = writer.post(0L, pubContext, null, null);
		System.out.println(disposition == null ? "Null disposition" : disposition.toString());
		fail("Manual check needed.");
	}
}
