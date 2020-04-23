package com.pony.advertiser.writers.solar;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import com.pony.advertiser.Disposition;
import com.pony.advertiser.Io;
import com.pony.advertiser.RoutingCandidate;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.leadtypes.PonyLead;
import com.pony.models.ArrivalModel;
import com.pony.models.UserProfileModel;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.publisher.PublisherContext.PostContext;
import com.pony.validation.ValidationResponse;

public class BostonSolarWriterTestSystem {

	/**
	 * Since this test is an integration test with a touch-point to another server, it is not run on every build and the
	 * testing of alternative values is usually done by changing the test in-line. A parameterized test would result in
	 * multiple calls for each run of the integration test. There is no test URL (or in this case, a SalesForce sandbox
	 * instance). Therefore, I am documenting the other testable values for some breakable parameters here:
	 * 
	 * electric_bill:
	 * 		$101-150	- A typical range. Should submit the upper bound of the range.
	 * 		$101+		- This means we are addressing the range with no upper-bound, the highest we provide. Should submit a 1000.0.
	 * 		asdf		- Garbage. It should be able to handle complete garbage and handle it gracefully with a 0.0.
	 * credit_score:
	 * 		501-600		- A typical credit score range. Should use the upper bound of the range.
	 * 		Under 500	- The lowest range option. Should use the upper bound of the range.
	 * 		Above 750	- The highest range option. Should use 850, the highest value of credit score (some use 800).
	 * 		asdf		- Garbage. Should submit 0.0.  
	 */
	@Test
	public void testSimplePost() {
		Long testLeadMatchId = 1L;
		
		Map<String, String> leadAttributes = new HashMap<>();
		leadAttributes.put("phone_home", "1234567890");
		leadAttributes.put("street", "street");
		leadAttributes.put("city", "Boston");
		leadAttributes.put("state", "MA");
		leadAttributes.put("zip", "12345");
		leadAttributes.put("first_name", "first");
		leadAttributes.put("last_name", "last");
		leadAttributes.put("electric_bill", "$101-150");
		leadAttributes.put("credit_range", "501-600");
		leadAttributes.put("property_ownership", "RENT");
		leadAttributes.put("zip", "01234");
		
		Io io = new Io(LeadType.PONY_LEAD_TYPE, 0L, "test code", 0L, new BigDecimal(0.0), 1, 0L, "test source", false, 0L, "test target");
		RoutingCandidate candidate = RoutingCandidate.create(io);
		final UserProfile userProfile = UserProfile.create(0L, "test.4@email.com", false, null);
		final Arrival arrival = new Arrival();
		arrival.setId(0L);
		arrival.setUserAgent("test user agent");
		final Lead lead = PonyLead.create(0L,  userProfile, arrival, leadAttributes);
		Mockery context = new Mockery();
		//final ArrivalModel arrivalModel = context.mock(ArrivalModel.class);
		//final UserProfileModel userProfileModel = context.mock(UserProfileModel.class);
		final IPublisherContext publisherContext = context.mock(IPublisherContext.class);
		context.checking(new Expectations() {{
			try {
				oneOf(publisherContext).getLead(); will(returnValue(lead));
				oneOf(publisherContext).getUserProfile(); will(returnValue(userProfile));
			} catch (Exception e) {
				fail("Threw exception setting up expectations.");
			}
		}});

		// I don't think I can add an expectation to post to log_posts without a DAO for that class, and it uses
		//   LeadMatchModel, which is not refactored to use an instance yet.
		String testName = "BostonSolar";
		String testUserName = "leadapionly@bostonsolar.us";
		String testPassword = "Bostonsolar123CDOf8rIbdbh2DnMkBCG3UFU0L";
		BostonSolarWriter writer = new BostonSolarWriter();
		Properties writerProperties = new Properties();
		writerProperties.setProperty(testName + BostonSolarWriter.USERNAME, testUserName);
		writerProperties.setProperty(testName + BostonSolarWriter.PASSWORD, testPassword);
		writer.setProperties(testName, writerProperties);

		Disposition disposition = writer.post(testLeadMatchId, publisherContext, ValidationResponse.NOOP, candidate);
		
		System.out.println(disposition);
		System.out.println(disposition.getExternalId());
	}
}
