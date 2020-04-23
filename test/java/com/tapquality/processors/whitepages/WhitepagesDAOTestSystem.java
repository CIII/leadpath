package com.tapquality.processors.whitepages;

import static org.junit.Assert.*;

import org.junit.Test;

public class WhitepagesDAOTestSystem {

	@Test
	public void test() throws Throwable {
		WhitepagesRequest request = new WhitepagesRequest(null, "Jonathan", "Card", "3039163966", "joncard93@hotmail.com", "1.2.3.4", "69 Merriam St.", "Apt 3", "Somerville", "02143", "MA", "US");
		
		WhitepagesDAO dao = new WhitepagesDAO("a886caea14324be1a8770c10d916cd2f");
		WhitepagesResponse response = dao.query(request);
		fail("Check this manually. This should not run automatically.");
	}

}
