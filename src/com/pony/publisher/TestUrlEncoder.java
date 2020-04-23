package com.pony.publisher;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 8/3/12
 * Time: 10:26 PM
 */
public class TestUrlEncoder {
	private static final Log LOG = LogFactory.getLog(TestUrlEncoder.class);
	
    public static void main(String[] args) {


        //?html=%3C%21DOCTYPE+html+PUBLIC+%22-%2F%2FW3C%2F%2FDTD+XHTML+1.0+Transitional%2F%2FEN%22%0A%22http%3A%2F%2Fwww.w3.org%2FTR%2Fxhtml1%2FDTD
        //      %3C%21DOCTYPE+html+PUBLIC+%22-%2F%2FW3C%2F%2FDTD+XHTML+1.0+Transitional%2F%2FEN%22%0A%22http%3A%2F%2Fwww.w3.org%2FTR%2Fxhtml1%2FDTD%2Fxhtml1-transitional.dtd%22%3E%0A%3Chtml+xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F1999%2Fxhtml%22%3E%0A%3Chead%3E%0A%3Ctitle%3E%0AYour+Health%2C+Your+Money%2C+Your+Life%0A%3C%2Ftitle%3E%0A%3C%2Fhead%3E%0A%3Cbody%3E%0A%3Cp%3E%0ADear+%40%40firstname%40%40+%40%40lastname%40%40%2C%0A%3C%2Fp%3E%0A%3Cp%3E%0ABeing+a+smart+consumer+means+knowing+all+of+your+options.+%3Ca+href%3D%22http%3A%2F%2F%40%40url%40%40%2Faffiliate.php%3Fmessage_id%3D%40%40message_id%40%40%22%3EGetYourInsuranceQuote%3C%2Fa%3E+is+here+to+help+you+explore+more+healthcare+options++and+find+the+best+plan+for+you.%0A%3C%2Fp%3E%0A%3Cp%3EQuotes+are+free.++No+hassle%2C+no+headache+-+just+a+couple+quick+and+easy+steps+that+could+save+you+money+on+your+health+insurance.+++%3Ca+href%3D%22http%3A%2F%2F%40%40url%40%40%2Faffiliate.php%3Fmessage_id%3D%40%40message_id%40%40%22%3EGet+Started+Now%21%3C%2Fa%3E%3C%2Fp%3E%0A%3Cp%3E%0ATo+unsubscribe+from+further+emails%2C+click+here%3A+%3Ca+href%3D%22http%3A%2F%2F%40%40url%40%40%2Funsub.php%3Fmessage_id%3D%40%40message_id%40%40%22%3EUnsubscribe%3C%2Fa%3E.%0A%3C%2Fp%3E%0A+%3Cp%3E+This+email+was+sent+by+Sendplex+%3C%2Fp%3E%3Cp%3EPO+Box+3124%3C%2Fp%3E%3Cp%3EWoburn+MA+01888%3C%2Fp%3E%3Cimg+height%3D%221%22+width%3D%221%22+src%3D%22http%3A%2F%2F%40%40url%40%40%2Fpixel.php%3Fmessage_id%3D%40%40message_id%40%40%22+alt%3D%22%22%2F%3E%0A%3C%2Fbody%3E%0A%3C%2Fhtml%3E

        String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<title>\n" +
                "Your Health, Your Money, Your Life\n" +
                "</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p>\n" +
                "Dear @@firstname@@ @@lastname@@,\n" +
                "</p>\n" +
                "<p>\n" +
                "Being a smart consumer means knowing all of your options. <a href=\"http://@@url@@/affiliate.php?message_id=@@message_id@@\">GetYourInsuranceQuote</a> is here to help you explore more healthcare options  and find the best plan for you.\n" +
                "</p>\n" +
                "<p>Quotes are free.  No hassle, no headache - just a couple quick and easy steps that could save you money on your health insurance.   <a href=\"http://@@url@@/affiliate.php?message_id=@@message_id@@\">Get Started Now!</a></p>\n" +
                "<p>\n" +
                "To unsubscribe from further emails, click here: <a href=\"http://@@url@@/unsub.php?message_id=@@message_id@@\">Unsubscribe</a>.\n" +
                "</p>\n" +
                " <p> This email was sent by Sendplex </p><p>PO Box 3124</p><p>Woburn MA 01888</p><img height=\"1\" width=\"1\" src=\"http://@@url@@/pixel.php?message_id=@@message_id@@\" alt=\"\"/>\n" +
                "</body>\n" +
                "</html>";

        try {
            String encoded = URLEncoder.encode(html, "UTF-8");
            LOG.debug("ecoded=" + encoded);
        }
        catch (UnsupportedEncodingException e) {
            LOG.error(e);
        }
    }


}
