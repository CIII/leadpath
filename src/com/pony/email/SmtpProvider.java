package com.pony.email;

import com.pony.advertiser.Creative;
import com.pony.advertiser.Offer;
import com.pony.email.sendgrid.SendGridProvider;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.models.ArrivalModelImpl;
import com.pony.models.CreativeModel;
import com.pony.models.OfferModel;
import com.pony.models.PublisherListModel;
import com.pony.models.UserProfileModelImpl;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.publisher.PublisherList;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 'Wedge' is an appliance agnostic email sending platform with a template and rules based message assembly.
 * It also features a central reporting facility. Transactional and bulk emails can take advantage of central data
 * and services to dynamically decide the best message content, transport, and physical host.
 * Incoming data can be validated against existing data and internal services.
 * External validation and data augmentation services (such as RapLeaf) can be easily added.
 * Wedge can take advantage of your existing smtp hosts and ip addresses with burned in history and reputation,
 * or start from scratch using our internal sending infrastructure, or any other smtp appliance.
 * <p/>
 * <p/>
 * PonyLeads 2012
 * User: martin
 * Date: 7/3/12
 * Time: 3:02 PM
 */
public abstract class SmtpProvider {
	private static final Log LOG = LogFactory.getLog(SmtpProvider.class);
	
    private final Long hostId;
    private final String name;

    protected SmtpProvider(Long hostId, String name) {
        this.hostId = hostId;
        this.name = name;
    }

    public abstract SmtpResponse send(Host host, Map<ResendCandidate, Message> messageCandidates, long delayMinutes) throws SmtpException;

    public abstract SmtpResponse send(IPublisherContext context, Message message) throws SmtpException;

    //	"select max(native_id) from opens where host_id = %s and domain_id = %s" % (hostid,domainid))
    public abstract void syncStatistics() throws SmtpException;

    public static SmtpProvider create(Long hostId, String providerName, String smtpHostName, String smtpAuthUser, String smtpAuthPwd, int smtpPort) {
        if ("doublenickels".equals(providerName)) {
            return new SendPlexProvider(hostId, providerName);
        }
        else if ("testProvider".equals(providerName)) {
            return new TestProvider(hostId, providerName);
        }
        else if ("SendGrid".equals(providerName)) {
            return new SendGridProvider(hostId, providerName, smtpHostName, smtpAuthUser, smtpAuthPwd, smtpPort);
        }

        return null;
    }


    protected final ProviderCreative createProviderCreative(ResendCandidate candidate, com.pony.email.Message message) throws NamingException, SQLException {
        LeadType leadType = LeadType.find(candidate.getLeadTypeId());
        Arrival arrival = ArrivalModelImpl.findStatic(candidate.getArrivalId());
        Lead lead = leadType.readLead(candidate.getUserProfileId(), candidate.getArrivalId());
        UserProfile userProfile = UserProfileModelImpl.findStatic(candidate.getUserProfileId());
        Creative creative = CreativeModel.find(message.getCreativeId());
        Offer offer = OfferModel.find(creative.getOfferId());
        PublisherList publisherList = PublisherListModel.find(arrival.getPublisherListId());
        Map<String, String> attributes = lead.toMap();

        return createProviderCreative(message, userProfile, attributes, creative, offer, publisherList);
    }

    protected final ProviderCreative createProviderCreative(IPublisherContext context, com.pony.email.Message message) throws NamingException, SQLException {

        try {
            UserProfile userProfile = context.getUserProfile();
            Lead lead = context.getLead();
            Map<String, String> attributes = lead.toMap();
            Creative creative = CreativeModel.find(message.getCreativeId());
            Offer offer = OfferModel.find(creative.getOfferId());
            PublisherList publisherList = context.getPublisherList();

            // merge any additional context params into the lead attributes (mainly for newsletter shared attributes)
            if (context.getParams().size() > 0) {
                attributes = new HashMap<String, String>();
                attributes.putAll(lead.toMap());
                attributes.putAll(context.getParams());
            }

            return createProviderCreative(message, userProfile, attributes, creative, offer, publisherList);
        }
        catch (RuntimeException e) {
            LOG.error(e);
            throw e;
        }
    }

    private ProviderCreative createProviderCreative(Message message, UserProfile userProfile, Map<String, String> attributes, Creative creative, Offer offer, PublisherList publisherList) {
        assert (userProfile != null);
        assert (message != null);
        assert (attributes != null);
        assert (offer != null);
        assert (creative != null);
        assert (publisherList != null);


        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String date = df.format(Calendar.getInstance().getTime());

        String txtCreative = creative.getText();
        String htmlCreative = creative.getHtml();
        String subject = creative.getSubject();
        String targetUrl = offer.getTargetUrl();
        String clickUrl = offer.getClickUrl();

        // replace the email template token
        txtCreative = txtCreative.replace("@@email@@", userProfile.getEmail());
        htmlCreative = htmlCreative.replace("@@email@@", userProfile.getEmail());

        txtCreative = txtCreative.replace("@@date@@", date);
        htmlCreative = htmlCreative.replace("@@date@@", date);
        subject = subject.replace("@@date@@", date);

        // loop through the users attributes and replace any templates in the content with the actual values
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            String key = "@@" + entry.getKey() + "@@";
//                String value = StringTools.capitalize(entry.getValue());
            String value = entry.getValue();

            txtCreative = txtCreative.replace(key, value);
            htmlCreative = htmlCreative.replace(key, value);
            subject = subject.replace(key, value);
            targetUrl = targetUrl.replace(key, value);
            if (clickUrl != null) {
                clickUrl = clickUrl.replace(key, value);
            }
        }

        if (publisherList.getExternalId() != null) {
            targetUrl = targetUrl.replace("@@list_id@@", publisherList.getExternalId());
            if (clickUrl != null) {
                clickUrl = clickUrl.replace("@@list_id@@", publisherList.getExternalId());
            }

            while (htmlCreative.contains("@@list_id@@")) {
                htmlCreative = htmlCreative.replace("@@list_id@@", publisherList.getExternalId());
            }
            while (txtCreative.contains("@@list_id@@")) {
                txtCreative = txtCreative.replace("@@list_id@@", publisherList.getExternalId());
            }
        }

        while (htmlCreative.contains("@@creative_id@@")) {
            htmlCreative = htmlCreative.replace("@@creative_id@@", creative.getId().toString());
        }

        targetUrl = targetUrl.replace("@@creative_id@@", creative.getId().toString());
        if (creative.getExternalId() != null) {
            targetUrl = targetUrl.replace("@@creative_external_id@@", creative.getExternalId());
            if (clickUrl != null) {
                clickUrl = clickUrl.replace("@@creative_external_id@@", creative.getExternalId());
            }
        }

        // make sure the message_id is in the destination url for all links embedded in the email
        if (targetUrl.contains("@@message_id@@")) {
            targetUrl = targetUrl.replace("@@message_id@@", message.getId().toString());
        }
//            else {
//                if (targetUrl.contains("?")) {
//                    targetUrl = targetUrl + "&";
//                }
//                else {
//                    targetUrl = targetUrl + "?";
//                }
//                targetUrl = targetUrl + "message_id=" + message.getId().toString();
//            }

        if (clickUrl != null) {
            if (clickUrl.contains("@@message_id@@")) {
                clickUrl = clickUrl.replace("@@message_id@@", message.getId().toString());
            }
            else {
                if (clickUrl.contains("?")) {
                    clickUrl = clickUrl + "&";
                }
                else {
                    clickUrl = clickUrl + "?";
                }
                clickUrl = clickUrl + "message_id=" + message.getId().toString();
            }
        }

        // replace templates for the target url (of clicks)
        while (htmlCreative.contains("@@target_url@@")) {
            htmlCreative = htmlCreative.replace("@@target_url@@", targetUrl);
        }
        while (txtCreative.contains("@@target_url@@")) {
            txtCreative = txtCreative.replace("@@target_url@@", targetUrl);
        }

        if (clickUrl != null) {
            // replace templates for the target url (of clicks)
            while (htmlCreative.contains("@@click_url@@")) {
                htmlCreative = htmlCreative.replace("@@click_url@@", clickUrl);
            }
            while (txtCreative.contains("@@click_url@@")) {
                txtCreative = txtCreative.replace("@@click_url@@", clickUrl);
            }
        }

        // also replace any unsub link
        String unsubscribeUrl = offer.getUnsubscribeUrl();
        if (unsubscribeUrl != null) {
            unsubscribeUrl = unsubscribeUrl.replace("@@message_id@@", message.getId().toString());
            unsubscribeUrl = unsubscribeUrl.replace("@@email@@", userProfile.getEmail());

            while (htmlCreative.contains("@@unsubscribe@@")) {
                htmlCreative = htmlCreative.replace("@@unsubscribe@@", unsubscribeUrl);
            }
            while (txtCreative.contains("@@unsubscribe@@")) {
                txtCreative = txtCreative.replace("@@unsubscribe@@", unsubscribeUrl);
            }
        }

        while (htmlCreative.contains("@@message_id@@")) {
            htmlCreative = htmlCreative.replace("@@message_id@@", message.getId().toString());
        }
        while (txtCreative.contains("@@message_id@@")) {
            txtCreative = txtCreative.replace("@@message_id@@", message.getId().toString());
        }

        String from = creative.getFromAddress();
        if (from == null) {
            from = offer.getFromAddress();
        }

        String fromPersonal = creative.getFromPersonal();
        if (fromPersonal == null) {
            fromPersonal = offer.getFromPersonal();
        }

        return new ProviderCreative(userProfile.getEmail(), creative.getId(), from, fromPersonal, subject, htmlCreative, txtCreative, unsubscribeUrl, clickUrl, targetUrl, offer.getBcc());
    }

    protected class ProviderCreative {
        private final Long creativeId;
        private final String email, from, fromPersonal, subject, htmlCreative, txtCreative, unsubscribeUrl, clickUrl, targetUrl, bcc;

        private ProviderCreative(String email, Long creativeId, String from, String fromPersonal, String subject, String htmlCreative, String txtCreative, String unsubscribeUrl, String clickUrl, String targetUrl, String bcc) {
            this.email = email;
            this.creativeId = creativeId;
            this.from = from;
            this.fromPersonal = fromPersonal;
            this.subject = subject;
            this.htmlCreative = htmlCreative;
            this.txtCreative = txtCreative;
            this.unsubscribeUrl = unsubscribeUrl;
            this.clickUrl = clickUrl;
            this.targetUrl = targetUrl;
            this.bcc = bcc;
        }

        public String getEmail() {
            return email;
        }

        public String getFrom() {
            return from;
        }

        public String getFromPersonal() {
            return fromPersonal;
        }

        public String getSubject() {
            return subject;
        }

        public String getHtmlCreative() {
            return htmlCreative;
        }

        public String getTxtCreative() {
            return txtCreative;
        }

        public String getUnsubscribeUrl() {
            return unsubscribeUrl;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public String getTargetUrl() {
            return targetUrl;
        }

        public String getBcc() {
            return bcc;
        }

        @Override
        public String toString() {
            return "ProviderCreative{creativeId=" + creativeId +
                    ", from='" + from + '\'' +
                    ", fromPersonal='" + fromPersonal + '\'' +
                    ", subject='" + subject + '\'' +
                    ", targetUrl='" + targetUrl + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ProviderCreative that = (ProviderCreative) o;

            if (!creativeId.equals(that.creativeId)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return creativeId.hashCode();
        }
    }

    public static final String testTemplate = "<body style=\"margin: 0;\">\n" +
            "\t<style>\n" +
            "\t\t* { -webkit-font-smoothing: antialiased; }\n" +
            "\t</style>\n" +
            "\t<table id=\"bodyTable\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\">\n" +
            "\t\t<tr>\n" +
            "\t\t\t<td align=\"center\" valign=\"top\">\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<table id=\"header\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
            "\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
            "\t\t\t\t\t\t\t<table class=\"container\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"padding: 10px\">\n" +
            "\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t<td valign=\"top\" align=\"left\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<img src=\"http://coupon-hound.com/images/email/logo_email.png\" height=\"30\" width=\"150\" alt=\"CouponHound\" style=\"font-family: Arial, Helvetica, sans-serif; color: #333333; font-size: 16px; margin: 0 0 0 10px;\" />\n" +
            "\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t</tr>\n" +
            "\t\t\t\t</table>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<table id=\"banner\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
            "\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
            "\t\t\t\t\t\t\t<table class=\"container\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" bgcolor=\"#9ac6e2\">\n" +
            "\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<img src=\"http://coupon-hound.com/images/email/banner_welcome.jpg\" height=\"180\" width=\"600\" alt=\"Welcome.\" style=\"color: #FFFFFF; font-family: Arial, Helvetica, sans-serif; font-size: 60px; font-weight: bold;\"/>\n" +
            "\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t</tr>\n" +
            "\t\t\t\t</table>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<table id=\"message\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
            "\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
            "\t\t\t\t\t\t\t<table class=\"container\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"padding: 20px 20px 10px 20px\">\n" +
            "\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t<td valign=\"top\" align=\"left\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<p style=\"color: #555555; font-family: Arial, Helvetica, sans-serif; font-size: 20px; margin: 0 0 10px 0;\">Hi @@first_name@@,</p>\n" +
            "\t\t\t\t\t\t\t\t\t\t<p style=\"color: #555555; font-family: Arial, Helvetica, sans-serif; font-size: 14px; margin: 0 0 20px 0;\">Thanks for signing up for Coupon Hound -- the best source for personalized samples and coupons.  Check back often to find the latest savings and offers.  And best of all, Coupon Hound is the smartest samples and coupons site around.  The more you use it and the more you save, the smarter we get!</p>\n" +
            "\t\t\t\t\t\t\t\t\t\t<h2 style=\"color: #4696c9; font-family: Arial, Helvetica, sans-serif; font-size: 20px; font-weight: normal; margin: 0; \">Here are some offers we picked just for you:</h2>\n" +
            "\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t</tr>\n" +
            "\t\t\t\t</table>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<table id=\"deals\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
            "\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
            "\t\t\t\t\t\t\t<table class=\"container\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"padding: 0 20px 20px 20px;\">\n" +
            "\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"560\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"171\" style=\"background: #FFFFFF; border: 1px solid #EAEAEA;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"image\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"171\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"center\" align=\"center\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"@@ad_img_url_1@@\" />\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"summary\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"min-height: 100px; padding: 10px;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h1 style=\"color: #4696c9; font-family: Arial, Helvetica, sans-serif; font-size: 24px; font-weight: normal; margin: 0 0 10px 0;\">@@ad_headline_1@@</h1>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h2 style=\"color: gray; font-family: Arial, Helvetica, sans-serif; font-size: 14px; font-weight: normal; margin: 0 0 10px 0;\">@@ad_text_1@@</h2>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"view_deal\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"171\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding: 10px;\" width=\"171\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" bgcolor=\"#59b4cf\" style=\"background: #aecc54; color: #fff; padding: 10px; text-decoration: none; font-family: Helvetica, Arial, sans-serif; display: block;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"@@target_url@@/ad_clicked?asid=@@list_id@@&@@ad_click_url_1@@\" style=\"color: #fff; text-decoration: none;\">Get Deal</a>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"20\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"171\" style=\"background: #FFFFFF; border: 1px solid #EAEAEA;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"image\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"171\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"center\" align=\"center\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"@@ad_img_url_2@@\" />\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"summary\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"min-height: 100px; padding: 10px;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h1 style=\"color: #4696c9; font-family: Arial, Helvetica, sans-serif; font-size: 24px; font-weight: normal; margin: 0 0 10px 0;\">@@ad_headline_2@@</h1>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h2 style=\"color: gray; font-family: Arial, Helvetica, sans-serif; font-size: 14px; font-weight: normal; margin: 0 0 10px 0;\">@@ad_text_2@@</h2>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"view_deal\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"171\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding: 10px;\" width=\"171\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" bgcolor=\"#59b4cf\" style=\"background: #aecc54; color: #fff; padding: 10px; text-decoration: none; font-family: Helvetica, Arial, sans-serif; display: block;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"@@target_url@@/ad_clicked?asid=@@list_id@@&@@ad_click_url_2@@\" style=\"color: #fff; text-decoration: none;\">Get Deal</a>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"20\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"171\" style=\"background: #FFFFFF; border: 1px solid #EAEAEA;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"image\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"171\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"center\" align=\"center\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\"@@ad_img_url_3@@\" />\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"summary\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"min-height: 100px; padding: 10px;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h1 style=\"color: #4696c9; font-family: Arial, Helvetica, sans-serif; font-size: 24px; font-weight: normal; margin: 0 0 10px 0;\">@@ad_headline_3@@</h1>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h2 style=\"color: gray; font-family: Arial, Helvetica, sans-serif; font-size: 14px; font-weight: normal; margin: 0 0 10px 0;\">@@ad_text_3@@</h2>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table class=\"view_deal\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"171\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding: 10px;\" width=\"171\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" bgcolor=\"#59b4cf\" style=\"background: #aecc54; color: #fff; padding: 10px; text-decoration: none; font-family: Helvetica, Arial, sans-serif; display: block;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"@@target_url@@/ad_clicked?asid=@@list_id@@&@@ad_click_url_3@@\" style=\"color: #fff; text-decoration: none;\">Get Deal</a>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</td>\t\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t</tr>\n" +
            "\t\t\t\t</table>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<table id=\"cta\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
            "\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
            "\t\t\t\t\t\t\t<table class=\"container\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\">\n" +
            "\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t<td valign=\"top\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"padding: 0 20px 10px 20px\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\" align=\"center\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<h2 style=\"color: #4696c9; font-family: Arial, Helvetica, sans-serif; font-size: 20px; font-weight: normal; margin: 0; \">Find even more great offers on Coupon Hound.</h2>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"padding: 0 20px 20px 20px\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t<td valign=\"top\" align=\"center\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" bgcolor=\"#59b4cf\" style=\"background: #aecc54; color: #fff; padding: 15px; text-decoration: none; font-family: Helvetica, Arial, sans-serif; display: block; width: 200px;\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"@@target_url@@/home?asid=@@list_id@@\" style=\"color: #fff; font-size: 20px; text-decoration: none;\">Get Your Savings</a>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t\t\t\t</tr>\n" +
            "\t\t\t\t\t\t\t</table>\n" +
            "\t\t\t\t\t\t</td>\n" +
            "\t\t\t\t\t</tr>\n" +
            "\t\t\t\t</table>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t\n" +
            "\t\t\t</td>\n" +
            "\t\t</tr>\n" +
            "\t</table>\n" +
            "</body>";

    public static void main(String[] args) {
        Map<String, String> t = new HashMap<String, String>();
        t.put("target_url", "http://coupon_hound.com/coupons");
        t.put("list_id", "ch_welcome");
        t.put("ad_click_url_1", "cid=39749&cimpid=4");
        t.put("ad_click_url_2", "cid=39876&cimpid=5");
        t.put("ad_click_url_3", "cid=39905&cimpid=6");
        t.put("ad_headline_1", "M&M's");
        t.put("ad_headline_2", "HORMEL");
        t.put("ad_headline_3", "HORMEL");
        t.put("ad_img_url_1", "http://cdn.coupons.com/insight.coupons.com/COS20/_Cache/_ImageCache/389/18139389.gif");
        t.put("ad_img_url_2", "http://cdn.coupons.com/insight.coupons.com/COS20/_Cache/_ImageCache/381/18041381.gif");
        t.put("ad_img_url_3", "http://cdn.coupons.com/insight.coupons.com/COS20/_Cache/_ImageCache/158/18041158.gif");
        t.put("ad_text_1", "$2.50 off any one (1) bag of M&M's 15oz. or larger");
        t.put("ad_text_2", "$2.00 off HORMEL CURE 81 Boneless Ham");
        t.put("ad_text_3", "$2.00 off one HORMEL Party Tray product");

        String m = testTemplate;
        for (Map.Entry<String, String> entry : t.entrySet()) {
            LOG.debug(entry.getKey());
            m = m.replace("@@" + entry.getKey() + "@@", entry.getValue());
        }

        LOG.debug(m);
    }
}
