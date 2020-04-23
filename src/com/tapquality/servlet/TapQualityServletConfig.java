package com.tapquality.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.pony.core.PonyServer;
import com.tapquality.dispositions.guess.BostonSolarGuessEngine;
import com.tapquality.dispositions.guess.GuessEngine;
import com.tapquality.dispositions.guess.MediaAlphaLuceneGuessEngine;

public class TapQualityServletConfig extends GuiceServletContextListener {
	private static final Log LOG = LogFactory.getLog(TapQualityServletConfig.class);
	private Injector injector;
	
	@Override
	protected Injector getInjector() {
		try {
			if (injector == null) {
				injector = Guice.createInjector(new TapQualityServletModule());
			}
			return injector;
		} catch (Throwable e) {
			LOG.error("Error starting up the Guice injector.", e);
			throw e;
		}
	}
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		
		injector = getInjector();
		PonyServer server = injector.getInstance(PonyServer.class);
		if(server != null) {
			server.start();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		Injector injector = (Injector)context.getAttribute(Injector.class.getName());
		GuessEngine mediaAlphaGuessEngine = injector.getInstance(MediaAlphaLuceneGuessEngine.class);
		if (mediaAlphaGuessEngine != null) {
			mediaAlphaGuessEngine.shutDown();
		}
		GuessEngine bostonSolarGuessEngine = injector.getInstance(BostonSolarGuessEngine.class);
		if (bostonSolarGuessEngine != null) {
			bostonSolarGuessEngine.shutDown();
		}
		PonyServer server = injector.getInstance(PonyServer.class);
		if(server != null) {
			server.stop();
		}
		
		super.contextDestroyed(servletContextEvent);
	}

}
