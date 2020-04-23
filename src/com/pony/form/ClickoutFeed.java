package com.pony.form;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 5/30/13
 * Time: 4:47 PM
 */
public class ClickoutFeed {
    public void render(FormState formState, HttpServletResponse servletResponse) throws IOException {

        // TODO:
        servletResponse.setContentType("text/plain");
        Writer out = servletResponse.getWriter();

        out.flush();
        servletResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
