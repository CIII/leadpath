package com.pony.email.sendgrid;

import com.pony.email.Host;
import com.pony.email.ResendCandidate;
import com.pony.email.SmtpException;
import com.pony.email.SmtpProvider;
import com.pony.email.SmtpResponse;
import com.pony.models.MessageModel;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 12/4/12
 * Time: 5:18 PM
 */
public class SendGridProvider extends SmtpProvider {
	private static final Log LOG = LogFactory.getLog(SendGridProvider.class);
	
    private static final String SMTP_HOST_NAME = "smtp.sendgrid.net";
    private static final String SMTP_AUTH_USER = "mholzner";
    private static final String SMTP_AUTH_PWD = "woodler";
    private static final int SMTP_PORT = 587;

    public static final String SENDPLEX_MESSAGE_ID = "sp_message_id";
    public static final String SENDPLEX_CREATIVE_ID = "sp_creative_id";

    private static final Logger log = Logger.getLogger("SendGridProvider");

    private Session mailSession;

    public SendGridProvider(Long hostId, String name, String smtpHostName, String smtpAuthUser, String smtpAuthPwd, int smtpPort) {
        super(hostId, name);

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", (smtpHostName == null) ? SMTP_HOST_NAME : smtpHostName);
        props.put("mail.smtp.port", smtpPort == 0 ? SMTP_PORT : smtpPort);
        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Authenticator auth = new SMTPAuthenticator(smtpAuthUser, smtpAuthPwd);
        mailSession = Session.getInstance(props, auth);
    }

    @Override
    public String toString() {
        return "SendGridProvider";
    }

    @Override
    public SmtpResponse send(Host host, Map<ResendCandidate, com.pony.email.Message> messageCandidates, long delayMinutes) throws SmtpException {

        long count = 0L;
        Transport transport = null;

        try {
            transport = mailSession.getTransport();
            transport.connect();

            for (Map.Entry<ResendCandidate, com.pony.email.Message> entry : messageCandidates.entrySet()) {
                ResendCandidate candidate = entry.getKey();
                com.pony.email.Message message = entry.getValue();
                count++;

                if ((count % 25) == 0) {
                    try {
                        // wait 10 secs to give the other end to catch up
                        Thread.sleep(10000L);
                    }
                    catch (InterruptedException e) {
                        LOG.error(e);
                    }
                }

                try {
                    MimeMessage mimeMessage = createMimeMessage(candidate, message);

                    transport.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));

                    MessageModel.processEvent(message.getId(), com.pony.email.Message.SENT);
                }
                catch (SQLException e) {
                    // ignore
                    log.log(Level.SEVERE, e.getMessage(), e);
                }
                catch (NamingException e) {
                    //ignore
                    log.log(Level.SEVERE, e.getMessage(), e);
                }
                catch (SmtpException e) {
                    log.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }
        catch (NoSuchProviderException e) {
            throw new SmtpException(e);
        }
        catch (MessagingException e) {
            throw new SmtpException(e);
        }
        finally {
            if (transport != null && transport.isConnected()) {
                try {
                    transport.close();
                }
                catch (MessagingException e) {
                    // ignore
                    log.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        }

        return null;
    }

    @Override
    public SmtpResponse send(IPublisherContext context, com.pony.email.Message message) throws SmtpException {

        try {
            // uncomment for debugging to stdout
            // mailSession.setDebug(true);
            MimeMessage mimeMessage = createMimeMessage(context, message);

            Transport transport = mailSession.getTransport();
            transport.connect();
            transport.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));
            transport.close();
            MessageModel.processEvent(message.getId(), com.pony.email.Message.SENT);
        }
        catch (SQLException e) {
            throw new SmtpException(e);
        }
        catch (NamingException e) {
            throw new SmtpException(e);
        }
        catch (AddressException e) {
            throw new SmtpException(e);
        }
        catch (NoSuchProviderException e) {
            throw new SmtpException(e);
        }
        catch (MessagingException e) {
            throw new SmtpException(e);
        }

        return SmtpResponse.create(context, message, null);
    }

    private MimeMessage createMimeMessage(ResendCandidate candidate, com.pony.email.Message message) throws NamingException, SQLException, SmtpException, MessagingException {

        LOG.info("sending " + message);
        ProviderCreative pc = createProviderCreative(candidate, message);

        return createMimeMessage(message, pc);
    }

    private MimeMessage createMimeMessage(IPublisherContext context, com.pony.email.Message message)
            throws MessagingException, SmtpException, NamingException, SQLException {

        LOG.info("sending " + message);
        ProviderCreative pc = createProviderCreative(context, message);

        return createMimeMessage(message, pc);
    }

    private MimeMessage createMimeMessage(com.pony.email.Message message, ProviderCreative pc) throws MessagingException, SmtpException {
        LOG.info("SendGrid sending:" + pc);

        // create the unique args header content:
        // {"unique_args":{"userid":"1123","template":"welcome"}}
        StringBuilder args = new StringBuilder();
        args.append("{");
        addJsonElement(args, SENDPLEX_MESSAGE_ID, message.getId().toString());
        args.append(",");
        addJsonElement(args, SENDPLEX_CREATIVE_ID, message.getCreativeId().toString());
        args.append("}");

        try {
            MimeMessage mimeMessage = new MimeMessage(mailSession);
            mimeMessage.addHeader("X-SMTPAPI", "{\"unique_args\":" + args.toString() + "}");

            Multipart multipart = new MimeMultipart("alternative");
            BodyPart part1 = new MimeBodyPart();
            part1.setText(pc.getTxtCreative());

            BodyPart part2 = new MimeBodyPart();
            part2.setContent(pc.getHtmlCreative(), "text/html");

            multipart.addBodyPart(part1);
            multipart.addBodyPart(part2);

            mimeMessage.setContent(multipart);

            mimeMessage.setFrom(new InternetAddress(pc.getFrom(), pc.getFromPersonal()));

            mimeMessage.setSubject(pc.getSubject());
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(pc.getEmail()));

            if (pc.getBcc() != null) {
                mimeMessage.addRecipient(Message.RecipientType.BCC, new InternetAddress(pc.getBcc()));
            }

            return mimeMessage;
        }
        catch (UnsupportedEncodingException e) {
            throw new SmtpException(e);
        }
    }

    private void addJsonElement(StringBuilder args, String key, String value) {
        args.append("\"").append(key).append("\":").append("\"").append(value).append("\"");
    }

    @Override
    public void syncStatistics() throws SmtpException {
        // bounces: http://sendgrid.com/docs/API%20Reference/Web%20API/bounces.html
        // blocks: http://sendgrid.com/docs/API%20Reference/Web%20API/blocks.html
        // unsubscribes: http://sendgrid.com/docs/API%20Reference/Web%20API/unsubscribes.html

        // BETTER!!! use events: http://sendgrid.com/docs/API%20Reference/Webhooks/event.html

    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        private final String user, pwd;

        private SMTPAuthenticator(String smtpAuthUser, String smtpAuthPwd) {
            user = (smtpAuthUser == null) ? SMTP_AUTH_USER : smtpAuthUser;
            pwd = (smtpAuthPwd == null) ? SMTP_AUTH_PWD : smtpAuthPwd;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, pwd);
        }
    }

    public static void main(String[] args) {
        // test harness

        String htmlCreative = "<html><head><title>This is a test</title></head><body><span>This message is meant for @@fname@@ and should provide a link back to <a href='http://@@url@@/affiliate.php?message_id=@@message_id@@'>Coupon-Hound</a></span></body></html>";
        String targetUrl = "http://www.coupon-hound.com/asid=x";
        String pattern = "http://@@url@@/affiliate.php?message_id=@@message_id@@";
        while (htmlCreative.contains(pattern)) {
            htmlCreative = htmlCreative.replace(pattern, targetUrl);
        }
        LOG.debug(htmlCreative);
    }
}
