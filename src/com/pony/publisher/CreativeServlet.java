package com.pony.publisher;

import com.pony.advertiser.Creative;
import com.pony.advertiser.Offer;
import com.pony.models.CreativeModel;
import com.pony.models.OfferModel;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.sql.SQLException;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 7/9/12
 * Time: 2:31 PM
 * <p/>
 * doublenickels calls:
 * <p/>
 * response = urllib2.urlopen('http://ponyevolution.com/creative_request.php?password=okcomputer1&creative_id=%s' % (creativeid))
 * data = response.read()
 * url = urlparse(data)
 * params = dict([part.split('=') for part in url[4].split('&')])
 * subjectline = urllib.unquote_plus(params['subject_line'])
 * offerid = urllib.unquote_plus(params['offerid'])
 * unsuburl = urllib.unquote_plus(params['unsuburl'])
 * text = urllib.unquote_plus(params['alt_text'])
 * html = urllib.unquote_plus(params['html'])
 * redirecturl = urllib.unquote_plus(params['redirecturl'])
 */
public class CreativeServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(CreativeServlet.class);

    private static final String RESPONSE_ENCODING = "iso-8859-1";  // doublenickels servers are set to latin1

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO

        // check password
//        String password = request.getParameter("password");
//        if (password == null || !"okcomputer1".equals(password)) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }

        // look up the creative and the offer and get the values to send back:
        response.setContentType("text/html");
        response.setCharacterEncoding(RESPONSE_ENCODING);
        Writer out = response.getWriter();

        try {
            String creativeId = request.getParameter("creative_id");

            // check for old vs. new
            if (request.getParameter("pony") == null) {
                // old style: return it from sendplex db
                // Note: won't need this since there are no more new creatives here. all new development will be in pony leads

//                $data = '?';
//                $q2 = mysql_query("select * from creatives where id = $creativeid") or die(mysql_error());
//                while($row = mysql_fetch_assoc($q2)) {
//                    $offerid=$row['offer_id'];
//                    $data = $data.'html='.urlencode($row['html']).'&alt_text='.urlencode($row['alt_text']).'&subject_line='.urlencode($row['subject_line']);
//                }
//
//                $q2 = mysql_query("select * from offers where id = $offerid") or die(mysql_error());
//                while($row = mysql_fetch_assoc($q2)) {
//                    $data = $data.'&offerid='.urlencode($row['id']).'&redirecturl='.urlencode($row['redirect_url']).'&unsuburl='.urlencode($row['unsub_url']);
//
//                }
//
                return;
            }

            // new style: use pony_leads db
            // (BUT: still same funny RESPONSE_ENCODING to keep the other side the same)
            Creative creative = CreativeModel.find(Long.valueOf(creativeId));
            Offer offer = OfferModel.find(creative.getOfferId());

            // we keep the old format (for now), to make the transition simpler
//            $data = '?';
//            $data.'html='.urlencode($row['html']).'&alt_text='.urlencode($row['alt_text']).'&subject_line='.urlencode($row['subject_line']
            out.write("?html=");
            out.write(URLEncoder.encode(creative.getHtml(), RESPONSE_ENCODING));

            out.write("&alt_text=");
            out.write(URLEncoder.encode(creative.getText(), RESPONSE_ENCODING));

            out.write("&subject_line=");
            out.write(URLEncoder.encode(creative.getSubject(), RESPONSE_ENCODING));

//            '&offerid='.urlencode($row['id']). '&redirecturl='.urlencode($row['redirect_url']).
//            '&unsuburl='.urlencode($row['unsub_url']
            out.write("&offerid=");
            out.write(offer.getId().toString());

            out.write("&redirecturl=");
            out.write(URLEncoder.encode(offer.getTargetUrl(), RESPONSE_ENCODING));

            out.write("&unsuburl=");
            out.write(URLEncoder.encode(offer.getUnsubscribeUrl(), RESPONSE_ENCODING));

            out.flush();
            response.setStatus(HttpServletResponse.SC_OK);
        }
        catch (NamingException e) {
            LOG.error(e);
        }
        catch (SQLException e) {
            LOG.error(e);
        }


        /* php version is:

       $password = $_GET['password'];
$creativeid = $_GET['creative_id'];

if ($password != 'okcomputer1') {

echo "failfailfail";

} else {

$data = '?';

$q2 = mysql_query("select * from creatives where id = $creativeid") or die(mysql_error());
while($row = mysql_fetch_assoc($q2)) {

       $offerid=$row['offer_id'];

       $data = $data.'html='.urlencode($row['html']).'&alt_text='.urlencode($row['alt_text']).'&subject_line='.urlencode($row['subject_line']);
   }

   $q2 = mysql_query("select * from offers where id = $offerid") or die(mysql_error());
   while($row = mysql_fetch_assoc($q2)) {

       $data = $data.'&offerid='.urlencode($row['id']).'&redirecturl='.urlencode($row['redirect_url']).'&unsuburl='.urlencode($row['unsub_url']);
        */

    }
}




