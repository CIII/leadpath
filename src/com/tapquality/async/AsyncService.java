package com.tapquality.async;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tapquality.servlet.TapQualityServletConfig;
import com.tapquality.servlet.reporting.DaemonThreadFactory;

/**
 * Service to pick up asynchronous tasks and process them.  The AsyncService
 * class is scheduled to run every {@linkplain #PERIOD_MS} seconds, but only
 * tasks which are due for processing will be picked up.  This is based on the next_run
 * field for each task.  Tasks are then processed using the 
 * {@linkplain com.tapquality.async.AsyncProcessor}
 * 
 * @author dmcguire
 *
 */
public class AsyncService extends TapQualityServletConfig{
	private static final Log LOG = LogFactory.getLog(AsyncService.class);
	private static final Long DELAY_MS = 0L;
	private static final Long PERIOD_MS = 1000 * 30 * 1L;  // 30 seconds
	
	private ExecutorService executor;
	private Timer timer;
	private AsyncProcessor taskProcessor;
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		super.contextDestroyed(servletContextEvent);
		timer.cancel();
		executor.shutdown();
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		taskProcessor = new AsyncProcessor(new AsyncContext(this.getInjector()));
		ThreadFactory daemonThreadFactory = new DaemonThreadFactory();
		timer = new Timer();
		executor = Executors.newFixedThreadPool(1, daemonThreadFactory);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				executor.submit(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						try {
							taskProcessor.processTasks();
						} catch (Throwable e) {
							LOG.error("Error during task procesing.", e);
						}
						
						return Boolean.TRUE;
					}
				});
			}
		}, DELAY_MS, PERIOD_MS);
	}
}
