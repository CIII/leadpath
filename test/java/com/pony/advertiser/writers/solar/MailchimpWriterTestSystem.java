package com.pony.advertiser.writers.solar;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.json.JSONObject;
import org.junit.Test;

import com.pony.advertiser.Io;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
import com.pony.leadtypes.PonyLead;
import com.pony.publisher.IPublisherContext;

public class MailchimpWriterTestSystem {

	@Test
	public void testPostToMandrill() throws Exception {
		// Given...
		String body = "{" +
	"\"key\": \"hSaI0j3dVCSILJPVA_gEwA\"," +
	"\"template_name\": \"lead-send-template\"," +
	"\"template_content\": []," +
	"\"message\": {" +
		"\"to\": [" +
			"{" +
				"\"email\": \"jon@tapquality.com\"," +
				"\"name\": \"Jonathan Card\"" +
			"}" +
		"]," +
		"\"global_merge_vars\": [" +
			"{" +
				"\"name\": \"current_date\"," +
				"\"content\": \"01/11/2017\"" +
			"}" +
		"]," +
		"\"track_opens\": true" +
	"}" +
"}";
		Mockery context = new Mockery();
		final HttpClient client = context.mock(HttpClient.class);
		final HttpResponse response = context.mock(HttpResponse.class);
		context.checking(new Expectations() {{
			oneOf(client).execute(with(any(HttpPost.class))); will(returnValue(response));
		}});
		// When...
		MailchimpWriter writer = new MailchimpWriter();
		HttpResponse responseActual = writer.postToMandrill(body, "https://mandrillapp.com/api/1.0/messages/send-template.json", new DefaultHttpClient());
		assertEquals(response, responseActual);
		//System.out.println(EntityUtils.toString(responseActual.getEntity()));
		context.assertIsSatisfied();
	}

	@Test
	public void testReadPostToMandrill() throws Exception {
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
		JSONObject bodyObject = writer.constructJSON(mockContext, order);
		HttpResponse response = writer.postToMandrill(bodyObject.toString());
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(EntityUtils.toString(response.getEntity()));
		context.assertIsSatisfied();
	}
}
