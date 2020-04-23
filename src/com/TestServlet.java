package com;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 7/28/14
 * Time: 2:19 PM
 */
public class TestServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // read file and return
        resp.setContentType("text/json");
        ServletOutputStream out = resp.getOutputStream();
        File f = new File("/work/rtb/fady/rtb/src/main/resources/brandscreen/example-request-mobile.json");
        byte[] bytes = new byte[1024];
        FileInputStream in = new FileInputStream(f);
        int i = 0;
        while ((i = in.read(bytes)) > 0) {
            out.write(bytes, 0, i);
        }
        in.close();
        resp.setStatus(200);
        out.close();
    }
}
