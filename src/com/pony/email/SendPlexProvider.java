package com.pony.email;

import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.models.ArrivalModelImpl;
import com.pony.models.HostModel;
import com.pony.models.MessageModel;
import com.pony.models.PublisherListModel;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.publisher.PublisherList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import javax.naming.NamingException;
import javax.net.ssl.SSLHandshakeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 7/31/12
 * Time: 5:20 PM
 * <p/>
 * Note:
 * required changes to standard sendplex install:
 * mysql> alter table messages add column pony_id int(11) after creative_id, add key(pony_id);
 * mysql> alter table opens add column open_count int(11) not null default 0 after message_id;
 * mysql> alter table opens add column updated_at datetime after created_at;
 * # get all opens into the first row for each message
 * mysql> update opens o, (select message_id, min(id) min, count(*) cnt, max(created_at) max from opens group by message_id)x set o.open_count = x.cnt, o.updated_at = x.max where o.id = x.min;
 * #delete all others (for more then 1 open per message)
 * mysql> delete from opens where open_count = 0;
 * <p/>
 * mysql> alter table clicks add column click_count  int(11) not null default 1 after message_id;
 * mysql> alter table clicks add column updated_at datetime after created_at;
 * mysql> alter table clicks add unique key(message_id); // if this fails, use the same approach as for opens to normalize the clicks to the first row per message
 * [[insert into tmp (select message_id from clicks group by message_id having count(*) > 1); ]]
 * [[ update clicks c,  (select message_id, min(id) min, count(*) cnt, max(created_at) max from clicks group by message_id)x set c.click_count = x.cnt, c.updated_at = x.max where c.id = x.min;]]
 * [[delete from clicks where message_id in (select id from tmp) and updated_at is null and click_count =1;]]
 * <p/>
 * <p/>
 * inbound.php:
 * add:
 * $message_id = $_POST['message_id'];
 * change:
 * mysql_query("insert into messages (user_profile_id, creative_id, send_time, pony_id) values ($userid,$creativeid,now() + interval $delaymin minute,$message_id)") or die(mysql_error());
 * and add an echo at the end of the file : echo "message_id=$messageid"
 * <p/>
 * pixel.php:
 * $v = mysql_query("insert into opens (message_id,user_profile_id,open_count,ip_address,user_agent,referrer) values ('$messageid','$uid', 1, '$i', '$agent', '$ref') ON DUPLICATE KEY UPDATE open_count=open_count+1, updated_at=now()") or die(mysql_error());
 * <p/>
 * affiliate.php:
 * check for opens for this messageid, and create an open if none exist, with open_count=0
 * <p/>
 * reference: look at simplewillow.com /srv/www/simplewillow.com/public_html (other updated hosts: almondinkwell.com
 * <p/>
 * changes for send:
 * /root/doublenickels/python/smtp_message_queue.py
 * add &pony=true to creative url:
 * response = urllib2.urlopen('http://ponyevolution.com/creative_request.php?password=okcomputer1&pony=true&creative_id=%s' % (creativeid))
 * <p/>
 * for apache2:
 * sudo apt-get install apache2-prefork-dev
 * <p/>
 * =================================
 * get latest gcc (and dev packages)
 * =================================
 * sudo apt-get update
 * sudo apt-get upgrade
 * sudo apt-get install build-essential
 * =================================
 * <p/>
 * do not ! install mod_jk
 * No : http://tomcat.apache.org/connectors-doc/webserver_howto/apache.html
 * <p/>
 * what worked is: http://stackoverflow.com/questions/956361/apache-tomcat-using-mod-proxy-instead-of-ajp (in httpd.conf)
 * plus: load the modules needed:
 * LoadModule proxy_module  /usr/lib/apache2/modules/mod_proxy.so
 * LoadModule proxy_ftp_module /usr/lib/apache2/modules/mod_proxy_ftp.so
 * LoadModule proxy_http_module /usr/lib/apache2/modules/mod_proxy_http.so
 * LoadModule proxy_connect_module /usr/lib/apache2/modules/mod_proxy_connect.so
 * <p/>
 * <VirtualHost *:80>
 * ServerName ponyevolution.com
 * <p/>
 * ProxyRequests Off
 * ProxyPreserveHost On
 * <p/>
 * <Proxy *>
 * Order deny,allow
 * Allow from all
 * </Proxy>
 * <p/>
 * ProxyPass / http://localhost:8081/
 * ProxyPassReverse / http://localhost:8081/
 * </VirtualHost>
 * <p/>
 * // ------------------
 * // loading data for suppression list
 * mysql -u root -p pony_leads -e"set foreign_key_checks=0; set sql_log_bin=0; load data concurrent local infile '/root/unsubs.txt' ignore into table md5_suppressions (md5_email) set publisher_id = 9 , created_at = now()"
 * //
 * // copy newly loaded suppression email addresses to old sendplex table
 * insert ignore into sendplex.md5_suppressions_2(select null, 22, md5_email, created_at from md5_suppressions where created_at > '2012-08-05');
 * //
 * <p/>
 * // reports:
 * // per host and creative
 * select m.host_id, m.creative_id, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate_open_cnt, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open_sum, round(100*sum(c.click_count)/count(distinct m.id),2) click_rate_send, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id group by m.host_id, m.creative_id order by  m.host_id, m.creative_id
 * //
 * // per day
 * select  date(m.created_at), m.host_id, m.creative_id, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate_open_cnt, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open_sum, round(100*sum(c.click_count)/count(distinct m.id),2) click_rate_send, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id group by m.host_id, m.creative_id, date(m.created_at) order by  m.host_id, date(m.created_at), m.creative_id;
 * //
 * // last 24 hours per ISP
 * select mh.name, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate_open_cnt, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open_sum, round(100*sum(c.click_count)/count(distinct m.id),2) click_rate_send, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id join user_profiles up on up.id = m.user_profile_id join mail_hosts mh on mh.id = up.mail_host_id where timestampdiff(HOUR, up.created_at, now()) < 24 group by mh.id having count(distinct(m.id)) > 10 order by count(distinct(m.id));
 * <p/>
 * daily reports to generate:
 * total: daily(host,creative:total): mysql -u root -p pony_leads -e"select m.host_id, m.creative_id, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate_open_cnt, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open_sum, round(100*sum(c.click_count)/count(distinct m.id),2) click_rate_send, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id group by m.host_id, m.creative_id having count(distinct m.id) > 5 order by  m.host_id, m.creative_id" > reports.txt
 * past 7 days: daily(day,host,creative:total): mysql -u root -p pony_leads -e"select  date(m.created_at), m.host_id, m.creative_id, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate_open_cnt, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open_sum, round(100*sum(c.click_count)/count(distinct m.id),2) click_rate_send, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id where datediff(now(), m.created_at) < 7 group by m.host_id, m.creative_id, date(m.created_at) having count(distinct m.id) > 5 order by  m.host_id, date(m.created_at), m.creative_id" > reports.txt
 * past 24 hours by ISP: daily(ISP: last 24 hours): mysql -u root -p pony_leads -e"select mh.name, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate_open_cnt, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open_sum, round(100*sum(c.click_count)/count(distinct m.id),2) click_rate_send, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id join user_profiles up on up.id = m.user_profile_id join mail_hosts mh on mh.id = up.mail_host_id where timestampdiff(HOUR, up.created_at, now()) < 24 group by mh.id having count(distinct(m.id)) > 10 order by count(distinct(m.id))"  > reports.txt
 * past 24 hours by list id: daily(listid: last 24 hours): mysql -u root -p pony_leads -e"select pl.ext_list_id, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate_open_cnt, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open_sum, round(100*sum(c.click_count)/count(distinct m.id),2) click_rate_send, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id join user_profiles up on up.id = m.user_profile_id join arrivals a on a.user_profile_id = up.id join publisher_lists pl on pl.id = a.publisher_list_id where timestampdiff(HOUR, m.created_at, now()) < 24 group by pl.ext_list_id having count(distinct(m.id)) > 10 order by count(distinct(m.id))" > reports.txt
 * past 24 hours by creative: daily(creative: last 24 hours): mysql -u root -p pony_leads -e"select m.creative_id, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate_open_cnt, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open_sum, round(100*sum(c.click_count)/count(distinct m.id),2) click_rate_send, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id join user_profiles up on up.id = m.user_profile_id join mail_hosts mh on mh.id = up.mail_host_id where timestampdiff(HOUR, up.created_at, now()) < 24 group by m.creative_id having count(distinct(m.id)) > 10 order by m.creative_id" > reports.txt
 * resends total: daily(all resends): mysql -u root -p pony_leads -e"select m.host_id, m.creative_id, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate_open_cnt, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open_sum, round(100*sum(c.click_count)/count(distinct m.id),2) click_rate_send, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id join resend_message_logs ml on ml.message_id = m.id group by m.host_id, m.creative_id having count(distinct m.id) > 5 order by  m.host_id, m.creative_id" >reports.txt
 * past 24 hours: sends by publisher and external list id:  select p.name, pl.ext_list_id, count(*) from arrivals a join publisher_lists pl on pl.id = a.publisher_list_id join publishers p on p.id = a.publisher_id where publisher_list_id in (3,4,5,6,7,8) and a.created_at > '2012-10-01' group by publisher_id, publisher_list_id;
 * opens and clicks by list, offer name, and day:
 * select pl.ext_list_id, of.name offer_name, date(m.created_at), count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate, round(100*count(distinct c.message_id)/count(distinct m.id),2) click_rate_end_to_end, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id join user_profiles up on up.id = m.user_profile_id join arrivals a on a.user_profile_id = up.id join publisher_lists pl on pl.id = a.publisher_list_id join creatives cr on cr.id = m.creative_id join offers of on of.id = cr.offer_id where timestampdiff(HOUR, m.created_at, now()) < 24 group by pl.ext_list_id, of.name, date(m.created_at) having count(distinct(m.id)) > 10 order by pl.ext_list_id, of.name, date(m.created_at)
 * <p/>
 * ========================
 * test url: http://ponyevolution.com/inbound.php?username=foo&password=goo&email=martinholzner@gmail.com&listid=999&ipaddy=66.87.66.238fname=Mart&lname=Ho&creativeid=108&hostid=14
 * ========================
 */
class SendPlexProvider extends SmtpProvider {
	private static final Log LOG = LogFactory.getLog(SmtpProvider.class);

    public SendPlexProvider(Long hostId, String providerName) {
        super(hostId, providerName);
    }

    @Override
    public SmtpResponse send(Host host, Map<ResendCandidate, Message> messageCandidates, long delayMinutes) throws SmtpException {
        Long externalMessageId = null;

        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 3) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    // Retry if the server dropped connection on us
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {
                    // Do not retry on SSL handshake exception
                    return false;
                }
                HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }

        };

        httpClient.setHttpRequestRetryHandler(myRetryHandler);

        long delay = delayMinutes;
        long count = 0L;

        for (Map.Entry<ResendCandidate, Message> entry : messageCandidates.entrySet()) {
            ResendCandidate candidate = entry.getKey();
            Message message = entry.getValue();
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
                LeadType leadType = LeadType.find(candidate.getLeadTypeId());
                Arrival arrival = ArrivalModelImpl.findStatic(candidate.getArrivalId());
                Lead lead = leadType.readLead(candidate.getUserProfileId(), candidate.getArrivalId());
                PublisherList publisherList = PublisherListModel.find(arrival.getPublisherListId());

                Map<String, String> attributes = lead.toMap();

                List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                formParams.add(new BasicNameValuePair("username", "master"));
                formParams.add(new BasicNameValuePair("password", "okcomputer"));
                formParams.add(new BasicNameValuePair("email", candidate.getEmail()));
                formParams.add(new BasicNameValuePair("listid", (publisherList == null ? "default" : publisherList.getExternalId())));
                formParams.add(new BasicNameValuePair("ipaddy", arrival.getIpAddress()));
                formParams.add(new BasicNameValuePair("fname", attributes.get("fname")));
                formParams.add(new BasicNameValuePair("lname", attributes.get("lname")));
                formParams.add(new BasicNameValuePair("creativeid", message.getCreativeId().toString()));
                formParams.add(new BasicNameValuePair("delaymin", "" + delay));
                formParams.add(new BasicNameValuePair("message_id", message.getId().toString())); // pass on the local message id for reference

                externalMessageId = httpSend(httpClient, message, externalMessageId, host, formParams);

                // increase the delay only every 10 messages (to batch them up a bid more)
                if ((count % 10) == 0) {
                    delay += 10 * delayMinutes;
                }
            }
            catch (SQLException e) {
                throw new SmtpException(e);
            }
            catch (IOException e) {
                throw new SmtpException(e);
            }
            catch (NamingException e) {
                throw new SmtpException(e);
            }
        }

//        httpClient.getConnectionManager().shutdown();

        return null;
    }

    @Override
    public SmtpResponse send(IPublisherContext context, Message message) throws SmtpException {
        // sending to doublenickels
        // create url with params to pass on, then make an http call
        Long externalMessageId = null;
        try {
//            Creative creative = CreativeModel.find(message.getCreativeId());
            Host host = HostModel.find(message.getHostId());
            Arrival arrival = context.getArrival();
            UserProfile userProfile = context.getUserProfile();
            PublisherList list = context.getPublisherList();
            Lead lead = context.getLead();
            Map<String, String> attributes = lead.toMap();

            // Note: add new param "messageid" to allow the link back from the doublenickels message to the pony message
            /*
                           url = "http://"+domain+"/inbound.php"
               values = {'username':'master','password':'okcomputer','email':email,'listid':listid,'ipaddy':userip,'fname':fname,'lname':lname,'creativeid':creative_id,'delaymin':delaymin}
               data = urllib.urlencode(values)
               req = urllib2.Request(url,data)
            */

            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            formParams.add(new BasicNameValuePair("username", "master"));
            formParams.add(new BasicNameValuePair("password", "okcomputer"));
            formParams.add(new BasicNameValuePair("email", userProfile.getEmail()));
            formParams.add(new BasicNameValuePair("listid", list.getExternalId()));
            formParams.add(new BasicNameValuePair("ipaddy", arrival.getIpAddress()));
            formParams.add(new BasicNameValuePair("creativeid", message.getCreativeId().toString()));
            formParams.add(new BasicNameValuePair("delaymin", "1"));
            formParams.add(new BasicNameValuePair("message_id", message.getId().toString())); // pass on the local message id for reference

            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

                public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                    if (executionCount >= 3) {
                        // Do not retry if over max retry count
                        return false;
                    }
                    if (exception instanceof NoHttpResponseException) {
                        // Retry if the server dropped connection on us
                        return true;
                    }
                    if (exception instanceof SSLHandshakeException) {
                        // Do not retry on SSL handshake exception
                        return false;
                    }
                    HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
                    boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                    if (idempotent) {
                        // Retry if the request is considered idempotent
                        return true;
                    }
                    return false;
                }

            };

            httpClient.setHttpRequestRetryHandler(myRetryHandler);

            externalMessageId = httpSend(httpClient, message, externalMessageId, host, formParams);
        }
        catch (SQLException e) {
            throw new SmtpException(e);
        }
        catch (NamingException e) {
            throw new SmtpException(e);
        }
        catch (ClientProtocolException e) {
            throw new SmtpException(e);
        }
        catch (UnsupportedEncodingException e) {
            throw new SmtpException(e);
        }
        catch (IOException e) {
            throw new SmtpException(e);
        }

        return SmtpResponse.create(context, message, externalMessageId);
    }

    private Long httpSend(HttpClient httpClient, Message message, Long externalMessageId, Host host, List<NameValuePair> formParams) throws IOException, NamingException, SQLException {
        UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
        HttpPost httpPost = new HttpPost("http://" + host.getDomainName() + "/inbound.php");
        httpPost.setEntity(requestEntity);

//                Long postId = log(lead.getId(), candidate, httppost.getURI(), formParams);


        // Execute the request
        HttpResponse response = httpClient.execute(httpPost);

        int httpStatus = response.getStatusLine().getStatusCode();

        // Get hold of the response entity and read out the response to clear the socket
        HttpEntity entity = response.getEntity();

        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
        // read the remote message id, created for this new message (if one is returned)
        // and update the local message.external_id with it
        String line;
        while ((line = reader.readLine()) != null) {
            //System.out.println("read:" + line + "\r\n");
            if (line.contains("message_id=")) {
                externalMessageId = Long.valueOf(line.substring(line.indexOf("message_id=") + 11));
                MessageModel.link(message, externalMessageId);
            }
        }
        reader.close();

//        httpclient.getConnectionManager().shutdown();

        return externalMessageId;
    }

//    public SmtpResponse send(UserProfile userProfile, Arrival arrival, PublisherList publisherList, Lead lead, Message message) throws SmtpException {
//        // sending to doublenickels
//        // create url with params to pass on, then make an http call
//        Long externalMessageId = null;
//        try {
////            Creative creative = CreativeModel.find(message.getCreativeId());
//            Host host = HostModel.find(message.getHostId());
//            Map<String, String> attributes = lead.toMap();
//
//            // Note: add new param "messageid" to allow the link back from the doublenickels message to the pony message
//            /*
//                           url = "http://"+domain+"/inbound.php"
//               values = {'username':'master','password':'okcomputer','email':email,'listid':listid,'ipaddy':userip,'fname':fname,'lname':lname,'creativeid':creative_id,'delaymin':delaymin}
//               data = urllib.urlencode(values)
//               req = urllib2.Request(url,data)
//            */
//
//            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
//            formParams.add(new BasicNameValuePair("username", "master"));
//            formParams.add(new BasicNameValuePair("password", "okcomputer"));
//            formParams.add(new BasicNameValuePair("email", userProfile.getEmail()));
//            formParams.add(new BasicNameValuePair("listid", publisherList.getExternalId()));
//            formParams.add(new BasicNameValuePair("ipaddy", arrival.getIpAddress()));
//            formParams.add(new BasicNameValuePair("fname", attributes.get("fname")));
//            formParams.add(new BasicNameValuePair("lname", attributes.get("lname")));
//            formParams.add(new BasicNameValuePair("creativeid", message.getCreativeId().toString()));
//            formParams.add(new BasicNameValuePair("delaymin", "1"));
//            formParams.add(new BasicNameValuePair("message_id", message.getId().toString())); // pass on the local message id for reference
//
//
//            UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
//            HttpPost httppost = new HttpPost("http://" + host.getDomainName() + "/inbound.php");
//            httppost.setEntity(requestEntity);
//
////                Long postId = log(lead.getId(), candidate, httppost.getURI(), formParams);
//
//            DefaultHttpClient httpclient = new DefaultHttpClient();
//
//            HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
//
//                public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
//                    if (executionCount >= 3) {
//                        // Do not retry if over max retry count
//                        return false;
//                    }
//                    if (exception instanceof NoHttpResponseException) {
//                        // Retry if the server dropped connection on us
//                        return true;
//                    }
//                    if (exception instanceof SSLHandshakeException) {
//                        // Do not retry on SSL handshake exception
//                        return false;
//                    }
//                    HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
//                    boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
//                    if (idempotent) {
//                        // Retry if the request is considered idempotent
//                        return true;
//                    }
//                    return false;
//                }
//
//            };
//
//            httpclient.setHttpRequestRetryHandler(myRetryHandler);
//            // Execute the request
//            HttpResponse response = httpclient.execute(httppost);
//
//            // Examine the response status
////                log(lead.getId(), response.getStatusLine());
//
//            int httpStatus = response.getStatusLine().getStatusCode();
//
//            // Get hold of the response entity and read out the response to clear the socket
//            HttpEntity entity = response.getEntity();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
//            // read the remote message id, created for this new message (if one is returned)
//            // and update the local message.external_id with it
//            String line;
//            while ((line = reader.readLine()) != null) {
//                //System.out.println("read:" + line + "\r\n");
//                if (line.contains("message_id=")) {
//                    externalMessageId = Long.valueOf(line.substring(line.indexOf("message_id=") + 11));
//                    MessageModel.link(message, externalMessageId);
//                }
//            }
//            reader.close();
//        }
//        catch (SQLException e) {
//            throw new SmtpException(e);
//        }
//        catch (NamingException e) {
//            throw new SmtpException(e);
//        }
//        catch (ClientProtocolException e) {
//            throw new SmtpException(e);
//        }
//        catch (UnsupportedEncodingException e) {
//            throw new SmtpException(e);
//        }
//        catch (IOException e) {
//            throw new SmtpException(e);
//        }
//
//        return SmtpResponse.create(context, message, externalMessageId);
//    }

    @Override
    public void syncStatistics() throws SmtpException {
        // Note: @see sendplex/doublenickels/data_sync_pony.py for the actual sync code
    }
}
