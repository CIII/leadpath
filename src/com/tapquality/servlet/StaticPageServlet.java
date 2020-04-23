package com.tapquality.servlet;

import java.io.IOException;

import javax.inject.Singleton;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Singleton
@SuppressWarnings("serial")
public class StaticPageServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(StaticPageServlet.class);

	@Override
	protected void doGet(final HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOG.debug("Entering doGet");
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/");
		HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(req) {
			public String getServletPath() { return "/admin/index.html"; }
		};
		dispatcher.forward(wrapper, resp);
	}

}
