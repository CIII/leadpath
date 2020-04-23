package com.tapquality.email.subscribe;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import com.pony.publisher.PublisherContext;
import com.pony.publisher.PublisherFormat;
import com.pony.publisher.PublisherResponse;

public class SubscriptionFormat extends PublisherFormat{

	public SubscriptionFormat(PublisherContext publisherContext) {
		super(publisherContext);
	}
	
	@Override
	public void format(PublisherResponse publisherResponse, HttpServletResponse servletResponse) throws IOException{
		try{
			servletResponse.getWriter().write(publisherResponse.toString());
		} finally {
			servletResponse.getWriter().flush();
			servletResponse.getWriter().close();
		}
	}
	
}
