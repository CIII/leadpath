package com.pony.publisher;

import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import com.pony.lead.Arrival;

public class PostErrorFormatTest {

	@Test
	public void test() throws Exception {
		Mockery context = new Mockery();
		Long leadId = 111L;
		final Arrival arrival = new Arrival(222L, "externalId");
		PublisherResponse response = PublisherResponse.createPostResponseNoWait(leadId);
		final PublisherException exception = new PublisherException("Test exception");
		StringWriter stringWriter = new StringWriter();
		final PrintWriter writer = new PrintWriter(stringWriter);
		exception.addCode("test_code", "This is a test code");
		final IPublisherContext publisherContext = context.mock(IPublisherContext.class);
		final HttpServletResponse servletResponse = context.mock(HttpServletResponse.class);
		context.checking(new Expectations(){{
			oneOf(publisherContext).getException(); will(returnValue(exception));
			oneOf(publisherContext).getArrival(); will(returnValue(arrival));
			
			oneOf(servletResponse).getWriter(); will(returnValue(writer));
		}});
		
		PublisherFormat format = new PostErrorFormat(publisherContext);
		format.format(response, servletResponse);
		
		writer.flush();
		System.out.println(stringWriter.toString());
		context.assertIsSatisfied();
		fail("Not implemented.");
	}

}
