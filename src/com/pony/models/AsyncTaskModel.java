package com.pony.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

import com.pony.PonyException;
import com.tapquality.async.AsyncContext;
import com.tapquality.async.AsyncTask;

public class AsyncTaskModel extends Model{
	private static final Log LOG = LogFactory.getLog(AsyncTaskModel.class);

	protected AsyncTaskModel(Long id) {
		super(id);
	}
	
	public static List<AsyncTask> findProcessingRequired(AsyncContext context) throws PonyException {
		Connection conn = null;
		PreparedStatement stmt = null;
		List<AsyncTask> taskList = new ArrayList<>();
		try {
			conn = connectX();
			stmt = conn.prepareStatement("SELECT * FROM async_tasks WHERE next_run < now() AND status != ? AND status != ?");
			stmt.setString(1, AsyncTask.STATUS_COMPLETE);
			stmt.setString(2, AsyncTask.STATUS_FAILED_COMPLETE);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Long taskId = rs.getLong("id");
				String className = rs.getString("type");
				try {
					taskList.add(AsyncTask.createTask(
							context,
							taskId, 
							className,
							rs.getString("label"), 
							rs.getInt("runs"),
							rs.getBoolean("repeats"),
							rs.getBoolean("retry_on_fail"),
							new DateTime(rs.getTimestamp("next_run")),
							rs.getString("status"), 
							findTaskAttributes(taskId)
					));
				} catch (ReflectiveOperationException e) {
					String errMsg = String.format("Unable to instantiate class %s for async task with id %d", className, taskId);
					LOG.error(errMsg, e);
					throw new PonyException(e);
				}
			}
		} catch (NamingException e) {
    		LOG.error(CONNECTION_EX_MSG, e);
    		throw new PonyException(CONNECTION_EX_MSG, e);
    	} catch (SQLException e) {
    		LOG.error(NAMING_EX_MSG, e);
    		throw new PonyException(NAMING_EX_MSG, e);
		} finally {
    		close(stmt);
    		close(conn);
    	}
				
		return taskList;
	}
	
	private static Map<String, String> findTaskAttributes(Long taskId) throws PonyException{
		Connection conn = null;
		PreparedStatement stmt = null;
		Map<String, String> values = new HashMap<>();
		try {
			conn = connectX();
			stmt = conn.prepareStatement("SELECT attribute, value FROM async_task_attributes WHERE async_task_id = ?");
			stmt.setString(1, taskId.toString());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				values.put(rs.getString("attribute"), rs.getString("value"));
			}
		} catch (NamingException e) {
    		LOG.error(CONNECTION_EX_MSG, e);
    		throw new PonyException(CONNECTION_EX_MSG, e);
    	} catch (SQLException e) {
    		LOG.error(NAMING_EX_MSG, e);
    		throw new PonyException(NAMING_EX_MSG, e);
		} finally {
    		close(stmt);
    		close(conn);
    	}
		
		return values;
	}
	
	public static void updateTask(AsyncTask task) throws PonyException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = connectX();
			stmt = conn.prepareStatement("UPDATE async_tasks SET label = ?, runs = ?, repeats = ?, retry_on_fail = ?, status = ?, next_run = ?, updated_at = now() where id = ?");
			stmt.setString(1, task.getLabel());
			stmt.setInt(2, task.getRuns());
			stmt.setBoolean(3, task.isRepeats());
			stmt.setBoolean(4, task.isRetryOnFail());
			stmt.setString(5, task.getStatus());
			stmt.setTimestamp(6, new java.sql.Timestamp(task.getNextRun().getMillis()));
			stmt.setLong(7, task.getId());
			stmt.execute();
			updateTaskAttributes(task);
		} catch (NamingException e) {
    		LOG.error(CONNECTION_EX_MSG, e);
    		throw new PonyException(CONNECTION_EX_MSG, e);
    	} catch (SQLException e) {
    		LOG.error(NAMING_EX_MSG, e);
    		throw new PonyException(NAMING_EX_MSG, e);
		} finally {
    		close(stmt);
    		close(conn);
    	}
	}
	
	private static void updateTaskAttributes(AsyncTask task) throws PonyException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = connectX();
			for(Entry<String, String> attribute : task.getAttributes().entrySet()) {
				stmt = conn.prepareStatement("UPDATE async_task_attributes SET value = ?, updated_at = now() where async_task_id = ? and attribute = ?");
				stmt.setString(1, attribute.getValue());
				stmt.setLong(2, task.getId());
				stmt.setString(3, attribute.getKey());
				stmt.execute();
			}
		} catch (NamingException e) {
    		LOG.error(CONNECTION_EX_MSG, e);
    		throw new PonyException(CONNECTION_EX_MSG, e);
    	} catch (SQLException e) {
    		LOG.error(NAMING_EX_MSG, e);
    		throw new PonyException(NAMING_EX_MSG, e);
		} finally {
    		close(stmt);
    		close(conn);
    	}
	}
	
	public static Long insertTask(AsyncTask task) throws PonyException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = connectX();
			stmt = conn.prepareStatement("INSERT INTO async_tasks (label, type, runs, repeats, retry_on_fail, status, next_run, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, now(), now())");
			stmt.setString(1, task.getLabel());
			stmt.setString(2, task.getClass().getName());
			stmt.setInt(3, task.getRuns());
			stmt.setBoolean(4, task.isRepeats());
			stmt.setBoolean(5, task.isRetryOnFail());
			stmt.setString(6, task.getStatus());
			stmt.setTimestamp(7, new java.sql.Timestamp(task.getNextRun().getMillis()));
			Long id = executeWithLastId(stmt);
			task.setId(id);
			insertTaskAttributes(task);
			return id;
		} catch (NamingException e) {
    		LOG.error(CONNECTION_EX_MSG, e);
    		throw new PonyException(CONNECTION_EX_MSG, e);
    	} catch (SQLException e) {
    		LOG.error(NAMING_EX_MSG, e);
    		throw new PonyException(NAMING_EX_MSG, e);
		} finally {
    		close(stmt);
    		close(conn);
    	}
	}

	private static void insertTaskAttributes(AsyncTask task) throws PonyException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = connectX();
			for(Entry<String, String> attribute : task.getAttributes().entrySet()) {
				stmt = conn.prepareStatement("INSERT INTO async_task_attributes (async_task_id, attribute, value, created_at, updated_at) VALUES (?, ?, ?, now(), now())");
				stmt.setLong(1, task.getId());
				stmt.setString(2, attribute.getKey());
				stmt.setString(3, attribute.getValue());
				stmt.execute();
			}
		} catch (NamingException e) {
    		LOG.error(CONNECTION_EX_MSG, e);
    		throw new PonyException(CONNECTION_EX_MSG, e);
    	} catch (SQLException e) {
    		LOG.error(NAMING_EX_MSG, e);
    		throw new PonyException(NAMING_EX_MSG, e);
		} finally {
    		close(stmt);
    		close(conn);
    	}
	}
	
	public static void deleteTask(AsyncTask task) throws PonyException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			deleteTaskAttributes(task);
			conn = connectX();
			stmt = conn.prepareStatement("DELETE FROM async_tasks WHERE id = ?");
			stmt.setLong(1, task.getId());
			stmt.execute();
		} catch (NamingException e) {
    		LOG.error(CONNECTION_EX_MSG, e);
    		throw new PonyException(CONNECTION_EX_MSG, e);
    	} catch (SQLException e) {
    		LOG.error(NAMING_EX_MSG, e);
    		throw new PonyException(NAMING_EX_MSG, e);
		} finally {
    		close(stmt);
    		close(conn);
    	}
	}

	private static void deleteTaskAttributes(AsyncTask task) throws PonyException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = connectX();
			stmt = conn.prepareStatement("DELETE FROM async_task_attributes WHERE async_task_id = ?");
			stmt.setLong(1, task.getId());
			stmt.execute();
		} catch (NamingException e) {
    		LOG.error(CONNECTION_EX_MSG, e);
    		throw new PonyException(CONNECTION_EX_MSG, e);
    	} catch (SQLException e) {
    		LOG.error(NAMING_EX_MSG, e);
    		throw new PonyException(NAMING_EX_MSG, e);
		} finally {
    		close(stmt);
    		close(conn);
    	}
	}
}
