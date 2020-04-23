package com.tapquality.async;

import java.util.Map;

import org.joda.time.DateTime;

import com.pony.PonyException;

/**
 * Base class for asynchronous tasks.  
 * 
 * @author dmcguire
 *
 */
public abstract class AsyncTask {
	
	public abstract void execute() throws PonyException;
	
	public final static String STATUS_PENDING = "Pending";
	public final static String STATUS_ACTIVE = "Active";
	public final static String STATUS_COMPLETE = "Complete";
	public final static String STATUS_FAILED = "Failed";
	public final static String STATUS_FAILED_COMPLETE = "Failed Complete";
	
	private Long id;
	private String label;
	private int runs;
	private boolean repeats;
	private boolean retryOnFail;
	private String status;
	private DateTime nextRun;
	private Map<String, String> attributes;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getRuns() {
		return runs;
	}
	public void setRuns(int runs) {
		this.runs = runs;
	}
	public DateTime getNextRun() {
		return nextRun;
	}
	public void setNextRun(DateTime nextRun) {
		this.nextRun = nextRun;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isRepeats() {
		return repeats;
	}
	public void setRepeats(boolean repeats) {
		this.repeats = repeats;
	}
	public boolean isRetryOnFail() {
		return retryOnFail;
	}
	public void setRetryOnFail(boolean retryOnFail) {
		this.retryOnFail = retryOnFail;
	}
	
	public static AsyncTask createTask(AsyncContext context, Long id, String className, String label, int runs, 
			boolean repeats, boolean retryOnFail, DateTime nextRun, String status, Map<String, String> attributes
		) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		AsyncTask task = (AsyncTask) context.getInjector().getInstance(Class.forName(className));
		task.setId(id);
		task.setLabel(label);
		task.setRuns(runs);
		task.setRepeats(repeats);
		task.setRetryOnFail(retryOnFail);
		task.setNextRun(nextRun);
		task.setStatus(status);
		task.setAttributes(attributes);
		return task;
	}
}
