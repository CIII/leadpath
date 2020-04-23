package com.tapquality.servlet.reporting;

import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Injector;
import com.pony.models.LeadReportingStatusModel;
import com.tapquality.servlet.TapQualityServletConfig;

/**
 * This class acts upon startup, due to the web.xml configuration, to create recurring tasks. A {@link Timer} is created
 * with a {@link TimerTask} anonymous class that spawns a daemon every {@link ReportingService#INTERVAL} milliseconds.
 * The task submits a {@link Callable} to to an {@link ExecutorService} on every execution, which then runs the daemon.
 * 
 */
public class ReportingService extends TapQualityServletConfig {
	private static final Log LOG = LogFactory.getLog(ReportingService.class);
	private static final Long INTERVAL = 1000L * 60 * 60L;
	
	private ExecutorService executor;
	private Timer timer;
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		super.contextDestroyed(servletContextEvent);
		timer.cancel();
		executor.shutdown();
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		ThreadFactory daemonThreadFactory = new DaemonThreadFactory();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				executor.submit(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						LOG.debug("Starting report processing.");
						try {
							Injector injector = ReportingService.this.getInjector();
							LeadReportingStatusModel model = injector.getInstance(LeadReportingStatusModel.class);

							Timestamp[] dates = model.getStartingPoint();
							Timestamp startDate = dates[0];
							Timestamp newEndDate = dates[1];
							model.migrateData(startDate);
							model.setEndTime(newEndDate);
						} catch (Throwable e) {
							LOG.error("Error processing the data for reporting. This will be reprocessed later.", e);
						}
						LOG.debug("Finished report processing.");
						
						return Boolean.TRUE;
					}
				});
			}
		},
				0L,
				INTERVAL);
		executor = Executors.newFixedThreadPool(1, daemonThreadFactory);
	}

}
