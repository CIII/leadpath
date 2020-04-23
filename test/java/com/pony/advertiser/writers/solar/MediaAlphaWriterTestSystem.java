package com.pony.advertiser.writers.solar;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import com.pony.advertiser.Buyer;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.Io;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.leadtypes.PonyLead;
import com.pony.models.ArrivalModel;
import com.pony.models.UserProfileModel;
import com.pony.validation.ValidationResponse;

public class MediaAlphaWriterTestSystem {

	@Test
	public void testSimpleRequestPrice() {
		// Given...
		Map<String, String> leadAttributes = new HashMap<>();
		leadAttributes.put("zip", "02143");
		leadAttributes.put("first_name", "Jonathan");
		leadAttributes.put("last_name", "Card");
		leadAttributes.put("phone_home", "303-916-3966");
		leadAttributes.put("street", "69 Merriam St.");
		leadAttributes.put("property_ownership", "OWN");
		leadAttributes.put("electric_bill", "$100-150");
		leadAttributes.put("electric_company", "Eversource");
		leadAttributes.put("leadid_token", "F932F87E-E56A-E5E6-1A20-7FB8A568AF4A");
		leadAttributes.put("local_hour", "15");
		leadAttributes.put("ip", "1.1.1.1");
		Io io = new Io(LeadType.PONY_LEAD_TYPE, 0L, "test code", 0L, new BigDecimal(0.0), 1, 0L, "test source", false, 0L, "test target");
		RoutingCandidate candidate = RoutingCandidate.create(io);
		final UserProfile userProfile = UserProfile.create(0L, "test@email.com", false, null);
		final Arrival arrival = new Arrival();
		arrival.setId(0L);
		arrival.setUserAgent("test user agent");
		Lead lead = PonyLead.create(0L, userProfile, arrival, leadAttributes);
		Mockery context = new Mockery();
		final ArrivalModel arrivalModel = context.mock(ArrivalModel.class);
		final UserProfileModel userProfileModel = context.mock(UserProfileModel.class);
		context.checking(new Expectations() {{
			try {
				oneOf(arrivalModel).find(0L); will(returnValue(arrival));
				oneOf(userProfileModel).find(0L); will(returnValue(userProfile));
			} catch (Exception e) {
				fail("Threw exception setting up expectations.");
			}
		}});
		
		// When...
		MediaAlphaWriter writer = new MediaAlphaWriter(arrivalModel, userProfileModel);
		writer.url = "https://home-services-test.mediaalpha.com/ping.json";
		writer.apiToken = "vsq7SElSa21Yxmr5G1cwHq";
		writer.digitalPlacementId = "L_lc07ezP5NgUsJ14I80sHXus_Q9_Q";
		
		Disposition disposition = writer.requestPrice(PonyPhase.REQUEST_PRICE, ValidationResponse.NOOP, candidate, lead, 0L);
		
		// Then...
		assertEquals(Disposition.Status.ACCEPTED, disposition.getStatus());
		assertNotNull(disposition.getExternalId());
		assertEquals(30, disposition.getExternalId().length());
		for(Buyer buyer : disposition.getBuyers()) {
			switch(buyer.getBuyerId()) {
				case "SolarCity":
					if(buyer.isExclusive()) {
						assertNotNull(buyer.getBuyerCode());
						assertEquals(30, buyer.getBuyerCode().length());
						assertEquals(18.2097, buyer.getPrice().doubleValue(), 0.00001);
					} else {
						assertNotNull(buyer.getBuyerCode());
						assertEquals(30, buyer.getBuyerCode().length());
						assertEquals(12.1296, buyer.getPrice().doubleValue(), 0.00001);
					}
					break;
				case "Sofdesk":
					assertTrue(buyer.isExclusive());
					assertNotNull(buyer.getBuyerCode());
					assertEquals(30, buyer.getBuyerCode().length());
					assertEquals(3.8, buyer.getPrice().doubleValue(), 0.00001);
					break;
			}
		}
	}

}
