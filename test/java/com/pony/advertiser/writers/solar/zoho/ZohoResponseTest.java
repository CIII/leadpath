package com.pony.advertiser.writers.solar.zoho;

import static org.junit.Assert.*;

import java.io.Reader;
import java.io.StringReader;

import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.convert.Registry;
import org.simpleframework.xml.convert.RegistryStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

public class ZohoResponseTest {

	@Test
	public void testDeserializeError() throws Exception {
		String responseBody = "<response uri=\"/crm/private/xml/Leads/insertRecords\"><error><code>4600</code><message>Unable to process your request. Please verify if the name and value is appropriate for the \"xmlData\" parameter.</message></error></response>";
		Registry registry = new Registry();
		Strategy strategy = new RegistryStrategy(registry);
		registry.bind(ZohoResult.class, ZohoResultConverter.class);
		Serializer serializer = new Persister(strategy);
		Reader reader = new StringReader(responseBody);
		ZohoResponse response = serializer.read(ZohoResponse.class, reader);
		assertNotNull(response.getError());
		assertEquals(4600, response.getError().getCode());
		assertTrue(response.getError().getMessage().contains("Unable to process"));
	}
	
	@Test
	public void testDeserializeSuccess() throws Exception {
		String responseBody = "<response uri=\"/crm/private/xml/Leads/insertRecords\"><result><message>Record(s) added successfully</message><recorddetail><FL val=\"Id\">2454819000000244001</FL><FL val=\"Created Time\">2017-06-30 14:06:46</FL><FL val=\"Modified Time\">2017-06-30 14:06:46</FL><FL val=\"Created By\"><![CDATA[Justice Nguyen]]></FL><FL val=\"Modified By\"><![CDATA[Justice Nguyen]]></FL></recorddetail></result></response>";
		Registry registry = new Registry();
		Strategy strategy = new RegistryStrategy(registry);
		registry.bind(ZohoResult.class, ZohoResultConverter.class);
		Serializer serializer = new Persister(strategy);
		Reader reader = new StringReader(responseBody);
		ZohoResponse response = serializer.read(ZohoResponse.class, reader);
		assertNotNull(response.getResult());
		assertEquals("2454819000000244001", response.getResult().getId());
		assertTrue(response.getResult().getMessage().contains("Record(s) added successfully"));

	}
}
