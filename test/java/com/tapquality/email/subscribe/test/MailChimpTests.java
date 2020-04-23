package com.tapquality.email.subscribe.test;

import org.junit.Assert;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pony.PonyException;
import com.tapquality.email.subscribe.MailChimpClient;
import com.tapquality.email.subscribe.MailChimpMember;
import com.tapquality.email.subscribe.MailChimpClient.MailChimpList;

import static org.hamcrest.CoreMatchers.*;
import java.io.IOException;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

public class MailChimpTests {
	private ObjectMapper mapper = new ObjectMapper();
		
	@Test
	public void testSerializeMailChimpMember() throws JsonProcessingException{
		MailChimpMember newMember = getMailChimpMember();
		Assert.assertThat("MailChimpMember did not serializeCorrectly",  mapper.writeValueAsString(newMember), is(equalTo(expectedSerializeStr)));
	}
	
	private final String expectedSerializeStr = "{\"status\":\"subscribed\",\"language\":\"English\",\"vip\":false,\"email_address\":\"dave2@tapquality.com\",\"email_type\":\"text\"}";
	
	@Test(expected=IllegalArgumentException.class)
	public void testPostInvalidMailChimpMember() throws ClientProtocolException, IOException, ParseException, PonyException{
		MailChimpMember newMember = new MailChimpMember();
		MailChimpClient mcClient = new MailChimpClient();
		System.out.println("status: " + mcClient.addMemberToList(newMember, MailChimpList.EasierSolarInsights));
	}

	private MailChimpMember getMailChimpMember() {
		MailChimpMember newMember = new MailChimpMember();
		newMember.setEmailAddress("dave2@tapquality.com");
		newMember.setStatus(MailChimpMember.MemberStatus.Subscribed);
		newMember.setEmailType("text");
		newMember.setLanguage("English");
		newMember.setVip(false);
		return newMember;
	}
}
