package com.tapquality.async;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pony.PonyException;
import com.pony.models.AsyncTaskModel;

/**
 * Processes asynchronous tasks based on their state.
 * 
 * @author dmcguire
 *
 */
public class AsyncProcessor {
	private static final Log LOG = LogFactory.getLog(AsyncProcessor.class);
	private final AsyncContext context;
	
	public AsyncProcessor(AsyncContext context) {
		this.context = context;
	}
	
	/**
	 * <p>
	 * Find any tasks who require processing and process them.  Tasks are deemed
	 * processing required if their next_run is < now() and they are not complete.
	 * <p>
	 * Tasks are processed based on state, where the execute() method is called for each
	 * task that is pending or active.  Previously failed tasks will be processed if they
	 * have the retry_on_fail flag set to true.  If an exception is thrown during task
	 * processing, its status will be set to 'Failed'
	 * <p>
	 * next_run is not updated for tasks, and should be handled within the execute() 
	 * method by the task itself.  Run count is always incremented, and the task will
	 * always be updated upon processing completion.
	 */
	public void processTasks() throws PonyException{
		List<AsyncTask> taskList = AsyncTaskModel.findProcessingRequired(context);
		if(taskList.size() > 0) {
			LOG.debug(String.format("Processing %d tasks", taskList.size()));
		}
		
		for(AsyncTask task : taskList) {
			switch(task.getStatus()) {
			
			// Pending tasks have not been run, so process it.
			case AsyncTask.STATUS_PENDING:
				processTask(task);
				break;
				
			// Active tasks are those which repeat, so process it
			case AsyncTask.STATUS_ACTIVE:
				processTask(task);
				break;
				
			// For failed tasks, if they specify to retry on fail then retry.  Otherwise
			// mark it as failed complete.
			case AsyncTask.STATUS_FAILED:
				if(task.isRetryOnFail()) {
					processTask(task);
				} else {
					task.setStatus(AsyncTask.STATUS_FAILED_COMPLETE);
					AsyncTaskModel.updateTask(task);
				}
				
				break;
				
			// Don't do anything for other states.  The only remaining are complete and
			// failed complete, which shouldn't ever get pulled.
			default:
				break;
			}
		}
	}
	
	private void processTask(AsyncTask task) throws PonyException {
		try {
			task.setStatus(AsyncTask.STATUS_ACTIVE);
			task.execute();
			if(!task.isRepeats()) {
				task.setStatus(AsyncTask.STATUS_COMPLETE);
			}
		} catch (PonyException e) {
			LOG.error(String.format("Task processing failed for task: %s %d", task.getLabel(), task.getId()), e);
			task.setStatus(AsyncTask.STATUS_FAILED);
		} finally {
			task.setRuns(task.getRuns() + 1);
			AsyncTaskModel.updateTask(task);
		}
	}

}
