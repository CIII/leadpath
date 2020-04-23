package com.pony.core;

import com.pony.models.Model;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Endpoint for external alive tests.
 * <p/>
 * PonyLeads 2012.
 * User: martin
 * Date: 12/11/12
 * Time: 10:27 AM
 */
public class AlivePingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType(MediaType.TEXT.toString());
        resp.setCharacterEncoding("utf8");
        Writer out = resp.getWriter();

        // test the db connection :
        long count = 0L;
        String dbTime = null;

        out.write("database:");
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = Model.connectX();
            stmt = con.prepareStatement("select now() now");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dbTime = rs.getString("now");
                out.write("ok");
            }
        }
        catch (SQLException e) {
            out.write(e.getMessage());
        }
        catch (NamingException e) {
            out.write(e.getMessage());
        }
        finally {
            Model.close(stmt);
            Model.close(con);
        }
        out.write("\r\n");

        // timezone:

        TimeZone tz = TimeZone.getDefault();
        out.write("timezone=" + tz.getDisplayName() + "\r\n");

        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        out.write("time=" + df.format(cal.getTime()) + "\r\n");

        if (dbTime != null) {
            try {
                out.write("database-time=" + df.parse(dbTime));
            }
            catch (ParseException e) {
                out.write("database-time=" + e.getMessage());
            }
        }
        out.write("\r\n");

        cal.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        out.write("Hour of Day in NYC: " + (cal.get(Calendar.HOUR_OF_DAY)));
        out.write("\r\n");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
