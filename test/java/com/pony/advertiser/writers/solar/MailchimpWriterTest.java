package com.pony.advertiser.writers.solar;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.hamcrest.core.IsInstanceOf;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.json.JSONObject;
import org.junit.Test;

import com.pony.advertiser.Io;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.leadtypes.PonyLead;
import com.pony.publisher.IPublisherContext;

public class MailchimpWriterTest {

	@Test
	public void testConstructJSON() throws Exception {
		// Given...
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("first_name", "Jon");
		attributes.put("last_name", "Card");
		attributes.put("street", "69 Merriam St");
		attributes.put("city", "Somerville");
		attributes.put("state", "MA");
		attributes.put("zip", "02143");
		attributes.put("electric_bill", "100-150");
		attributes.put("electric_company", "eversource");
		attributes.put("phone_home", "303-916-3966");
		Mockery context = new Mockery();
		final IPublisherContext mockContext = context.mock(IPublisherContext.class);
		final Lead lead = new PonyLead(null, null, null, null, attributes);
		final UserProfile userProfile = new UserProfile(0L, "user email address", false, null);
		context.checking(new Expectations() {{
			oneOf(mockContext).getLead(); will(returnValue(lead));
			oneOf(mockContext).getUserProfile(); will(returnValue(userProfile));
		}});
		Io order = new Io(null, null, "", null, null, 0, null, "", false, null, "", "jon@tapquality.com");
		// When...
		MailchimpWriter writer = new MailchimpWriter();
		System.out.println(writer.constructJSON(mockContext, order).toString(4));
		// Then...
		// This doesn't have much tests; it's really just to exercise the code.
		context.assertIsSatisfied();
	}	
}
