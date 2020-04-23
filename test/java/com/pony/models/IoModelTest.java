package com.pony.models;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.core.IsInstanceOf;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import static org.junit.Assert.*;

import com.pony.advertiser.Io;
import com.pony.core.PonyPhase;
import com.pony.validation.whitepages.IWhitepagesFilter;
import com.pony.validation.whitepages.IWhitepagesFilterFactory;
import com.pony.validation.whitepages.WhitepagesAcceptFilter;
import com.pony.validation.whitepages.WhitepagesRejectFilter;

public class IoModelTest {

	// TODO: Refactor into a parameterized test.
	@Test
	public void testGetWhitepagesFilters() throws Exception {
		Io io = new Io(-1L, 0L, "", 0L, new BigDecimal(0.0), 0, 0L, "", false, 0L, "");
		final PonyPhase phase = PonyPhase.POST;
		Mockery context = new Mockery();
		final Connection con = context.mock(Connection.class);
		final PreparedStatement stmt = context.mock(PreparedStatement.class);
		final ResultSet rs = context.mock(ResultSet.class);
		final IWhitepagesFilterFactory factory = context.mock(IWhitepagesFilterFactory.class);
		context.checking(new Expectations() {{
			oneOf(con).prepareStatement(with(any(String.class))); will(returnValue(stmt));
			oneOf(stmt).executeQuery(); will(returnValue(rs));
			oneOf(stmt).setLong(1, 0L);
			oneOf(stmt).setInt(2, phase.getCode());
			
			oneOf(rs).next(); will(returnValue(true));
			oneOf(rs).getString("parameter_name"); will(returnValue("parameter_name_1"));
			oneOf(rs).getString("accept_value"); will(returnValue(null));
			oneOf(rs).wasNull(); will(returnValue(true));
			oneOf(rs).getString("reject_value"); will(returnValue("reject value 1"));
			oneOf(rs).wasNull(); will(returnValue(false));
			oneOf(factory).getWhitepagesFilter(null, "reject value 1", "parameter_name_1"); will(returnValue(new WhitepagesRejectFilter("parameter_name_1")));
			
			oneOf(rs).next(); will(returnValue(true));
			oneOf(rs).getString("parameter_name"); will(returnValue("parameter_name_1"));
			oneOf(rs).getString("accept_value"); will(returnValue(null));
			oneOf(rs).wasNull(); will(returnValue(true));
			oneOf(rs).getString("reject_value"); will(returnValue("reject value 2"));
			oneOf(rs).wasNull(); will(returnValue(false));
			
			oneOf(rs).next(); will(returnValue(true));
			oneOf(rs).getString("parameter_name"); will(returnValue("parameter_name_2"));
			oneOf(rs).getString("accept_value"); will(returnValue(null));
			oneOf(rs).wasNull(); will(returnValue(true));
			oneOf(rs).getString("reject_value"); will(returnValue(null));
			oneOf(rs).wasNull(); will(returnValue(true));
			
			oneOf(rs).next(); will(returnValue(true));
			oneOf(rs).getString("parameter_name"); will(returnValue("parameter_name_2"));
			oneOf(rs).getString("accept_value"); will(returnValue("accept value 1"));
			oneOf(rs).wasNull(); will(returnValue(false));
			oneOf(rs).getString("reject_value"); will(returnValue(null));
			oneOf(rs).wasNull(); will(returnValue(true));
			oneOf(factory).getWhitepagesFilter("accept value 1", null, "parameter_name_2"); will(returnValue(new WhitepagesAcceptFilter("parameter_name_2")));
			
			oneOf(rs).next(); will(returnValue(true));
			oneOf(rs).getString("parameter_name"); will(returnValue("parameter_name_2"));
			oneOf(rs).getString("accept_value"); will(returnValue("accept value 2"));
			oneOf(rs).wasNull(); will(returnValue(false));
			oneOf(rs).getString("reject_value"); will(returnValue(null));
			oneOf(rs).wasNull(); will(returnValue(true));
			
			oneOf(rs).next(); will(returnValue(true));
			oneOf(rs).getString("parameter_name"); will(returnValue("parameter_name_3"));
			oneOf(rs).getString("accept_value"); will(returnValue("accept value 3"));
			oneOf(rs).wasNull(); will(returnValue(false));
			oneOf(rs).getString("reject_value"); will(returnValue(null));
			oneOf(rs).wasNull(); will(returnValue(true));
			oneOf(factory).getWhitepagesFilter("accept value 3", null, "parameter_name_3"); will(returnValue(new WhitepagesAcceptFilter("parameter_name_3")));
			
			oneOf(rs).next(); will(returnValue(true));
			oneOf(rs).getString("parameter_name"); will(returnValue("parameter_name_3"));
			oneOf(rs).getString("accept_value"); will(returnValue("accept value 4"));
			oneOf(rs).wasNull(); will(returnValue(false));
			oneOf(rs).getString("reject_value"); will(returnValue("reject value 3"));
			oneOf(rs).wasNull(); will(returnValue(false));
			
			oneOf(rs).next(); will(returnValue(true));
			oneOf(rs).getString("parameter_name"); will(returnValue("parameter_name_3"));
			oneOf(rs).getString("accept_value"); will(returnValue(null));
			oneOf(rs).wasNull(); will(returnValue(true));
			oneOf(rs).getString("reject_value"); will(returnValue("reject value 4"));
			oneOf(rs).wasNull(); will(returnValue(false));
			
			oneOf(rs).next(); will(returnValue(false));
			oneOf(stmt).close();
		}});
		
		// | parameter_name_1 | NULL			| reject value 1	|
		// | parameter_name_1 | NULL			| reject value 2	|
		// | parameter_name_2 | NULL			| NULL				|
		// | parameter_name_2 | accept value 1	| NULL				|
		// | parameter_name_2 | accept value 2	| NULL				|
		// | parameter_name_3 | accept value 3	| NULL				|
		// | parameter_name_3 | accept value 4	| reject value 3	|
		// | parameter_name_3 | NULL			| reject value 4	|
		
		List<IWhitepagesFilter> filters = IoModel.getWhitepagesFilters(con, io, phase, factory);
		Map<String, IWhitepagesFilter> testResults = new HashMap<>();
		for(IWhitepagesFilter filter : filters) {			
			testResults.put(filter.getParameterName(), filter);
		}
		
		//   parameter_name_1, reject filter with 2 values
		IWhitepagesFilter relatedFilter = testResults.get("parameter_name_1");
		assertNotNull(relatedFilter);
		assertTrue(relatedFilter instanceof WhitepagesRejectFilter);
		assertEquals(2, relatedFilter.getValuesSize());

			//   parameter_name_2, accept filter with 2 values
		relatedFilter = testResults.get("parameter_name_2");
		assertNotNull(relatedFilter);
		assertTrue(relatedFilter instanceof WhitepagesAcceptFilter);
		assertEquals(2, relatedFilter.getValuesSize());
		
		//   parameter_name_3, accept filters with 2 values
		relatedFilter = testResults.get("parameter_name_3");
		assertNotNull(relatedFilter);
		assertTrue(relatedFilter instanceof WhitepagesAcceptFilter);
		assertEquals(2, relatedFilter.getValuesSize());
	}
}
