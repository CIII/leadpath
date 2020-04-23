package com.pony.validation;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.leadtypes.Pony;
import com.pony.leadtypes.PonyLead;
import com.pony.publisher.IPublisherContext;

public class ValidationServiceTest {

	@Test
	public void testSuccess() throws Exception {
		Mockery context = new Mockery();
		final UserProfile profile = new UserProfile("test@email.com", false, null);
		final LeadType leadType = new Pony();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("zip", "01234");
		attributes.put("street", "test street");
		final Lead lead = new PonyLead(0L, -1L, 2L, 3L, attributes);
		final IPublisherContext publisherContext = context.mock(IPublisherContext.class);
		context.checking(new Expectations() {{
			oneOf(publisherContext).getLeadType(); will(returnValue(leadType));
			oneOf(publisherContext).getUserProfile(); will(returnValue(profile));
			oneOf(publisherContext).isPost(); will(returnValue(true));
			oneOf(publisherContext).getLead(); will(returnValue(lead));
			never(publisherContext).addErrorCode(with(any(String.class)), with(any(String.class)));
		}});
		ValidationService service = new ValidationService();
		service.post(publisherContext);
		context.assertIsSatisfied();
	}
	
	@Test
	public void testBadEmail() throws Exception {
		Mockery context = new Mockery();
		final UserProfile profile = new UserProfile("test@email", false, null);
		final LeadType leadType = new Pony();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("zip", "01234");
		attributes.put("street", "test street");
		final Lead lead = new PonyLead(0L, -1L, 2L, 3L, attributes);
		final IPublisherContext publisherContext = context.mock(IPublisherContext.class);
		context.checking(new Expectations() {{
			oneOf(publisherContext).getLeadType(); will(returnValue(leadType));
			oneOf(publisherContext).getUserProfile(); will(returnValue(profile));
			oneOf(publisherContext).isPost(); will(returnValue(true));
			oneOf(publisherContext).getLead(); will(returnValue(lead));
			oneOf(publisherContext).addErrorCode(with(equal(ValidationResponse.INVALID_EMAIL.getName())), with(any(String.class)));
		}});
		ValidationService service = new ValidationService();
		service.post(publisherContext);
		context.assertIsSatisfied();
	}

	@Test
	public void testNoZip() throws Exception {
		Mockery context = new Mockery();
		final UserProfile profile = new UserProfile("test@email.com", false, null);
		final LeadType leadType = new Pony();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("street", "test street");
		final Lead lead = new PonyLead(0L, -1L, 2L, 3L, attributes);
		final IPublisherContext publisherContext = context.mock(IPublisherContext.class);
		context.checking(new Expectations() {{
			oneOf(publisherContext).getLeadType(); will(returnValue(leadType));
			oneOf(publisherContext).getUserProfile(); will(returnValue(profile));
			oneOf(publisherContext).isPost(); will(returnValue(true));
			oneOf(publisherContext).getLead(); will(returnValue(lead));
			oneOf(publisherContext).addErrorCode(with(equal(ValidationResponse.INVALID_ZIP.getName())), with(any(String.class)));
		}});
		ValidationService service = new ValidationService();
		service.post(publisherContext);
		context.assertIsSatisfied();
	}

	@Test
	public void testNoStreet() throws Exception {
		Mockery context = new Mockery();
		final UserProfile profile = new UserProfile("test@email.com", false, null);
		final LeadType leadType = new Pony();
		Map<String, String> attributes = new HashMap<>();
		attributes.put("zip", "01234");
		final Lead lead = new PonyLead(0L, -1L, 2L, 3L, attributes);
		final IPublisherContext publisherContext = context.mock(IPublisherContext.class);
		context.checking(new Expectations() {{
			oneOf(publisherContext).getLeadType(); will(returnValue(leadType));
			oneOf(publisherContext).getUserProfile(); will(returnValue(profile));
			oneOf(publisherContext).isPost(); will(returnValue(true));
			oneOf(publisherContext).getLead(); will(returnValue(lead));
			oneOf(publisherContext).addErrorCode(with(equal(ValidationResponse.INVALID_STREET.getName())), with(any(String.class)));
		}});
		ValidationService service = new ValidationService();
		service.post(publisherContext);
		context.assertIsSatisfied();
	}


}
