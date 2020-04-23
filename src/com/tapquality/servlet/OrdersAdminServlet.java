package com.tapquality.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Singleton;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jooq.Result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pony.leadtypes.Pony;
import com.pony.models.IoModel;
import com.pony.validation.ValidationException;
import com.tapquality.db.serializers.OrdersRecordSerializer;
import com.tapquality.db.tables.records.OrdersRecord;

@SuppressWarnings("serial")
@Singleton
public class OrdersAdminServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(OrdersAdminServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getRequestURI().replace(req.getServletPath(), "");
		
		switch (path) {
		case "":
		case "/true":
			try {
				ObjectMapper mapper = new ObjectMapper();
				SimpleModule module = new SimpleModule();
				module.addSerializer(OrdersRecord.class, new OrdersRecordSerializer());
				mapper.registerModule(module);
				Result<OrdersRecord> orders = IoModel.findAll(new Pony(), true);
				writeData(resp, mapper.writeValueAsString(orders));
			} catch (NamingException e) {
				String errMsg = "NamingException retrieving orders. This is likely a configuration problem.";
				LOG.error(errMsg, e);
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (SQLException e) {
				String errMsg = "SQLException retrieving orders.";
				LOG.error(errMsg, e);
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} catch (ValidationException e) {
				String errMsg = "Unknown ValidationException retrieving orders.";
				LOG.error(errMsg, e);
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}
	
	private void writeData(HttpServletResponse resp, String publisherData) throws IOException {		
		resp.setContentType("application/json");
		resp.getWriter().write(publisherData);
	}

}
