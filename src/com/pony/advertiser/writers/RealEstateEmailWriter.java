package com.pony.advertiser.writers;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.email.validation.AddressValidator;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.models.LeadMatchModel;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationResponse;
import com.pony.validation.ValidationScore;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Properties;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 9/23/14
 * Time: 1:56 PM
 */
public class RealEstateEmailWriter extends EmailWriter {
	private static final Log LOG = LogFactory.getLog(RealEstateEmailWriter.class);
	
    private static final String EMAIL_USER = "u";
    private static final String PASSWORD = "p";
    private static final String FROM = "from";
    public static final String TO = "to";
    public static final String BCC = "bcc";

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {

        Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());

        if (map.get(NO_SEND) != null) {
            return Disposition.createPost(Disposition.Status.ACCEPTED, Disposition.DispositionCategory.NO_MATCH, "configured to not send.");
        }

        Lead lead = publisherContext.getLead();
        UserProfile up = publisherContext.getUserProfile();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        String userName = map.get(EMAIL_USER);
        if (userName == null) {
            userName = "leads";   // default
        }

        String password = map.get(PASSWORD);
        if (password == null) {
            password = "ABC!!456"; // default
        }

        String from = map.get(FROM);
        if (from == null) {
            from = "leads@acquisition-labs.com"; // default
        }

        String to = map.get(TO);
        if (to == null) {
            to = candidate.getIo().getTargetUrl();
            if (to == null || !AddressValidator.isValid(to, true)) {
                to = "martin@arbventures.com"; // default
            }
        }

        String bcc = map.get(BCC);

        try {
            Session session = Session.getInstance(props, new MailAuthenticator(userName, password));

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            if (bcc != null) {
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
            }
            StringBuilder msg = new StringBuilder();
            String subject = candidate.getIo().getCode();
            msg.append("email: ").append(up.getEmail()).append("\r\n");
            subject = subject.replace("{email}", up.getEmail());
            subject = subject.replace("{lead_match_id}", leadMatchId.toString());

            final LeadType leadType = publisherContext.getChannel().getLeadType();

            StringBuilder postWriteMsg = new StringBuilder();

            for (Map.Entry<String, String> entry : lead.toMap().entrySet()) {

                // replace any tokens with the value for the corresponding attribute in the subject line
                subject = subject.replace("{" + entry.getKey() + "}", entry.getValue());

                if ("xl_bag".equals(entry.getKey())) {
                    postWriteMsg.append(leadType.labelLookup(entry.getKey())).append(":\r\n").append(entry.getValue()).append("\r\n");
                }
                else {
                    msg.append(leadType.labelLookup(entry.getKey())).append(": ");
                    if ("phone_number".equals(entry.getKey())) {
                        msg.append(formatPhone(entry.getValue().trim())).append("\r\n");
                    }
                    else if ("home_budget".equals(entry.getKey())) {
                        msg.append(formatDollars(entry.getValue().trim())).append("\r\n");
                    }
                    else {
                        msg.append(entry.getValue()).append("\r\n");
                    }
                }

            }
            message.setSubject(subject);

            // add scores and their values, if we have any
            if (validationResponse.getScores().size() > 0) {
                msg.append("\r\n-------------  scores  -------------\r\n");
                for (Map.Entry<String, ValidationScore> scoreEntry : validationResponse.getScores().entrySet()) {
                    msg.append(scoreEntry.getValue()).append("\r\n");
                }
                msg.append("----------   end scores -----------\r\n");
            }

            if (postWriteMsg.length() > 0) {
                msg.append("\r\n").append(postWriteMsg);
            }

            message.setText(candidate.getIo().getCode() + ":\r\n" + msg.toString());

            try {
                LeadMatchModel.persistMessage(leadMatchId, PonyPhase.POST, "from=" + from + "; to=" + to + ";\n subject=" + subject + ";\n data=" + msg.toString());
            }
            catch (NamingException e) {
                LOG.error(e);
            }
            catch (SQLException e) {
                LOG.error(e);
            }

            Transport.send(message);
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return Disposition.createPost(Disposition.Status.ACCEPTED);
    }

    protected static String formatDollars(String raw) {
        if (raw == null || "".equals(raw)) {
            return raw;
        }

        return formatDollars(Double.valueOf(raw.trim().replace(" ", "").replace(",", "").replace("$", "")));
    }

    protected static String formatDollars(Double raw) {
        DecimalFormat nf = new DecimalFormat("$##,###,###.00");
        return nf.format(raw);
    }

    private static String formatPhone(String rawPhone) {
        if (rawPhone == null) {
            return rawPhone;
        }
        String phone = rawPhone.trim().replace(".", "").replace("-", "").replace("(", "").replace("(", "");
        if (phone.length() < 10) {
            return rawPhone;
        }

        java.text.MessageFormat phoneMsgFmt = new java.text.MessageFormat("({0})-{1}-{2}");
        String[] phoneNumArr = {phone.substring(0, 3), phone.substring(3, 6), phone.substring(6)};
        return phoneMsgFmt.format(phoneNumArr);
    }

    public static void main(String[] args) {
        String phoneRawString = "1231231234";

        LOG.debug(formatPhone(phoneRawString));

        LOG.debug(formatDollars("$1,845.67"));
        LOG.debug(formatDollars("1845.67"));
        LOG.debug(formatDollars("$1,123,567"));
    }
}
