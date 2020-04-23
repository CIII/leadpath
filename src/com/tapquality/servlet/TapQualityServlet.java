package com.tapquality.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public abstract class TapQualityServlet extends HttpServlet {

	protected StringBuilder getBodyText(HttpServletRequest req) throws IOException {
		InputStream stream = req.getInputStream();
		Reader reader = new InputStreamReader(stream);
		StringBuilder bodyBuilder = new StringBuilder();
		char[] buffer = new char[1000];
		int position = 1;
		while ((position = reader.read(buffer, 0, 1000)) > 0) {
			bodyBuilder.append(buffer, 0, position);
		}
		return bodyBuilder;
	}

	protected abstract ObjectMapper getMapper();

	protected void writeData(HttpServletResponse resp, Object data) throws IOException {
		ObjectMapper mapper = getMapper();
		writeData(resp, mapper.writeValueAsString(data));
	}
	
	protected void writeData(HttpServletResponse resp, String data) throws IOException {
		resp.setContentType("application/json");
		resp.getWriter().write(data);
	}

}
