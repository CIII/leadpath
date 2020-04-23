package com.pony.form;

import com.pony.core.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 2/6/13
 * Time: 10:31 AM
 * <p/>
 * here is the code for the WordPress plugin:
 */

// this plugin will render a script tag into the head tag of any WP site
// <?php
// /*
// Plugin Name: as-leads scripts
// Plugin URI: www.tapnexus.com
// Description: Add custom header link to as-leads java script
// Author: Martin Holzner
// Version: 1.0
// Author URI:
// */
//
//function asl_header_content() {
//        ?>
//        <script type='text/javascript' src='http://www.tapnexus.com/scripts.js'></script>
//        <?php
//        }
//        add_action( 'wp_head', 'asl_header_content' );
//        ?>


/*
 *
<script>
 _init_as_leads_form('formParent', '99');
</script>
  */

public class IframeFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // render an iframe:

        resp.setCharacterEncoding("utf8");
        resp.setContentType(MediaType.HTML.toString());

        StringBuilder html = new StringBuilder();

        html.append("<html><head><title>iframe tests</title><script type='text/javascript' src='/scripts.js'></script>");
        html.append("<link rel='stylesheet' type='text/css' href='/styles.css' media='screen'/></head>");

        html.append("<body><p id='formParent'>some initial text</p><div id='main'>");

        html.append("</div><p>some more text after the iframe</p>");

        html.append("<script>");
        html.append(" _init_as_leads_form('formParent', '99');");
        html.append("</script>");

        html.append("</body></html>");

        Writer out = resp.getWriter();
        out.write(html.toString());
        out.flush();

        resp.setStatus(HttpServletResponse.SC_OK);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: handle post from the form in the iframe

    }

//    @Override
//    public void destroy() {
//        super.destroy();
//    }
//
//    @Override
//    public void init() throws ServletException {
//        super.init();
//    }
}
