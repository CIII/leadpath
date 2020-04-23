package com.pony.email.sendgrid;


import com.pony.core.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;

/**
 * receive and process SendGrid Web Notifications
 * <p/>
 * PonyLeads 2012.
 * User: martin
 * Date: 12/6/12
 * Time: 2:53 PM
 */
public class SendGridWebHookServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(SendGridWebHookServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // TODO: update to V3 (which posts a multi event json formatted document)

        // check if this contains a sp_message_id (otherwise we don't know where to assign this incoming info...)
        try {
            if (req.getParameter(SendGridProvider.SENDPLEX_MESSAGE_ID) != null) {
                SendGridEvent event = SendGridEvent.get(req.getParameter("event"));
                if (event != null) {
                    LOG.info("sendgrid: processing[" + event + "]");
                    event.handle(req);
                }
                else {
                    LOG.error("sendgrid ERROR: cannot find event: " + req.getParameter("event"));
                }
            }
            else {
                // check if this is V3
                InputStream in = null;
                try {
                    in = req.getInputStream();
                    if (in != null) {
                        processInputStream(in);
                    }
                    else {
                        LOG.error("sendgrid ERROR: no message id in this request. event=" + req.getParameter("event"));
                    }
                }
                finally {
                    if (in != null) {
                        in.close();
                    }
                }
            }
            // "sp_creative_id"
            resp.setContentType(MediaType.TEXT.toString());
            resp.setCharacterEncoding("utf8");
            Writer out = resp.getWriter();
            out.write("processed sp_message_id=" + req.getParameter(SendGridProvider.SENDPLEX_MESSAGE_ID) + ":event=" + req.getParameter("event") + "\r\n");
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        catch (SendGridException e) {
            LOG.error(e);
        }

        resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

    private static void processInputStream(InputStream in) throws IOException, SendGridException {

        if (in != null) {
            HashMap<String, String> hash = new HashMap<String, String>();
            StringBuilder key = new StringBuilder(), value = new StringBuilder();

            InputStreamReader isr = new InputStreamReader(in, "UTF8");
            int ch;
            boolean inSide = false;
            boolean isKey = true;
            boolean inQuote = false;

            while ((ch = isr.read()) > -1) {
                Character c = (char) ch;
                if (c == '\n' || c == '\r') {
                    continue;
                }
                if (c == '{') {
                    inSide = true;
                    isKey = true;
                    key = new StringBuilder();
                    value = new StringBuilder();
                    continue;
                }
                else if (c == ',' && !inQuote) {
//                    System.out.println("k=" + key.toString().trim() + ":v=" + value.toString().trim());
                    hash.put(key.toString().trim(), value.toString().trim());
                    key = new StringBuilder();
                    value = new StringBuilder();
                    isKey = true;
                    continue;
                }
                else if (c == '}') {
                    hash.put(key.toString().trim(), value.toString().trim());

                    SendGridEvent event = SendGridEvent.get(hash.get("event"));
                    if (event != null) {
                        event.handle(hash);
                    }
                    else {
                        LOG.info("sendgrid: no handler for event[" + hash.get("event") + "]");
                    }

                    hash = new HashMap<String, String>();
                    inSide = false;
                    continue;
                }

                if (inSide) {
                    if (c == '\"') { // skip quotes
                        inQuote = !inQuote;
                        continue;
                    }

                    if (c == ':' && isKey) {
                        isKey = !isKey;
                        continue;
                    }
                    if (isKey) {
                        key.append(c);
                    }
                    else {
                        value.append(c);
                    }
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    public static void main(String[] args) throws IOException, SendGridException {
        processInputStream(getTestContent3());
    }

    public static InputStream getTestContent2() throws UnsupportedEncodingException {
        String content = "[{\"email\":\"martinholzner@gmail.com\",\"sp_message_id\":\"82227\",\"timestamp\":1389298152,\"ip\":\"199.168.63.226\",\"sp_creative_id\":\"14\",\"useragent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/534.57.7 (KHTML, like Gecko)\",\"event\":\"open\"}]";
        return new ByteArrayInputStream(content.getBytes("UTF-8"));
    }

    private static InputStream getTestContent() throws UnsupportedEncodingException {

        String content = "[\n" +
                "    {\n" +
                "        \"email\": \"john.doe@sendgrid.com\",\n" +
                "        \"timestamp\": 1337197600,\n" +
                "        \"smtp-id\": \"<4FB4041F.6080505@sendgrid.com>\",\n" +
                "        \"event\": \"processed\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"email\": \"john.doe@sendgrid.com\",\n" +
                "        \"timestamp\": 1337966815,\n" +
                "        \"smtp-id\": \"<4FBFC0DD.5040601@sendgrid.com>\",\n" +
                "        \"category\": \"newuser\",\n" +
                "        \"event\": \"click\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"email\": \"john.doe@sendgrid.com\",\n" +
                "        \"timestamp\": 1337969592,\n" +
                "        \"smtp-id\": \"<20120525181309.C1A9B40405B3@Example-Mac.local>\",\n" +
                "        \"event\": \"processed\"\n" +
                "    }\n" +
                "]\n";

        return new ByteArrayInputStream(content.getBytes("UTF-8"));
    }

    public static InputStream getTestContent3() throws UnsupportedEncodingException {
        return new ByteArrayInputStream("{\"sp_message_id\":\"82245\",\"status\":\"5.1.1\",\"sg_event_id\":\"r4ILspA0TaKjrrR95_9iSg\",\"reason\":\"550 5.1.1 <nmariiexo@aol.com>: Recipient address rejected: aol.com \",\"event\":\"bounce\",\"email\":\"nmariiexo@aol.com\",\"timestamp\":1389678427,\"smtp-id\":\"<2131684134.21389678425045.JavaMail.tomcat@ip-10-119-99-199>\",\"sp_creative_id\":\"15\",\"type\":\"bounce\"}".getBytes("UTF-8"));
    }
}
