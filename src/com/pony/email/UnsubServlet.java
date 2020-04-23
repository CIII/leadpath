package com.pony.email;

import com.pony.lead.UserProfile;
import com.pony.models.MessageModel;
import com.pony.models.UserProfileModelImpl;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 8/7/12
 * Time: 1:48 PM
 */
public class UnsubServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*
        $email = $_POST['email'];

$query = mysql_query("insert into unsubscribes (email) values( '$email')") or die(mysql_error());

echo "You have been ubsubscribed!";
         */
        String email = UserProfile.parse(req);
        if (email != null) {
            try {
                UserProfile up = UserProfileModelImpl.findByEmailStatic(email);
                if (up != null) {
                    boolean validMessage = true;
                    Long msgId = null;
                    String messageId = req.getParameter("message_id");
                    if (messageId != null) {
                        msgId = Long.valueOf(messageId);
                        // if a message id was posted (hidden field), make sure the email and message are connected
                        validMessage = MessageModel.validateMessage(up, msgId);
                    }

                    if (validMessage) {
                        UserProfileModelImpl.unsubscribeStatic(up, req.getHeader("User-Agent"), req.getRemoteAddr(), req.getHeader("Referer"), msgId);
                        req.setAttribute("done", true);
                    }
                }
            }
            catch (SQLException e) {
                throw new ServletException(e);
            }
            catch (NamingException e) {
                throw new ServletException(e);
            }
        }

        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/*
        <html>
<head>
</head>
<body>
<form name="input" action="unsub_commit.php" method="post">
Email: <input type="text" name="email" />
<input type="submit" value="Unsubscribe" />
</form>
</body>
</html>
         */

        resp.setContentType("text/html");
        Writer out = resp.getWriter();

        if (req.getAttribute("done") != null) {
            out.write("<html><head><title>Unsubscribed</title></head><body>You will no longer receive any messages from us.</body></html>");
        }
        else if (req.getParameter("message_id") != null) {
            Long messageId = Long.valueOf(req.getParameter("message_id"));

            // validate that this is a real person unsubscribing from a message we sent there. i.e. prevent a unsubscribe attack
            out.write("<html><head><title>Please verify your data</title></head><body><span>Please provide the data below to verify your unsubscribe request.</span><form name=\"input\" method=\"post\">");
            out.write("<label for='email'>Email:</label><input type=\"text\" name=\"email\" id='email' />");
            out.write("<input type='hidden' name='message_id' value='" + messageId + "'/>");
            out.write("<input type=\"submit\" value=\"Unsubscribe\" />");
            out.write("</form></body></html>");
        }
        else {
            out.write("<html><head></head><body><form name=\"input\" method=\"post\">");
            out.write("Email: <input type=\"text\" name=\"email\" />");
            out.write("<input type=\"submit\" value=\"Unsubscribe\" />");
            out.write("</form></body></html>");
        }
        out.flush();
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
