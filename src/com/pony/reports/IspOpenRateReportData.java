package com.pony.reports;

import com.pony.models.Model;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 10/23/12
 * Time: 3:54 PM
 */
public class IspOpenRateReportData extends ReportData {
	private static final Log LOG = LogFactory.getLog(IspOpenRateReportData.class);
	
    public IspOpenRateReportData(String chartType, String daysBack, String creativeId, String hostId, String threshold) {
        super(chartType, daysBack, creativeId, hostId, threshold);
        HEADERS.put("Date", "date");
        HEADERS.put("Host", "string");
        HEADERS.put("ISP", "string");
        HEADERS.put("Sends", "number");
        HEADERS.put("Open Rate", "number");
        HEADERS.put("Opens", "number");
        HEADERS.put("CTR", "number");
    }

    @Override
    public StringBuilder getTitle() {
        StringBuilder html = new StringBuilder();
        html.append("<title>ISP Open Rate</title>");
        return html;
    }

    public StringBuilder getMotionChart() throws NamingException, SQLException {
        StringBuilder html = new StringBuilder();

        Connection con = null;
        PreparedStatement stmt = null;


        html.append("<script type=\"text/javascript\" src=\"http://www.google.com/jsapi\"></script>");
        html.append("<script type=\"text/javascript\">");
        html.append("google.load('visualization', '1', {packages: ['motionchart']});");

        html.append("function drawVisualization() {");
        html.append("var data = new google.visualization.DataTable();");

        addHeader(html, "ISP");
        addHeader(html, "Date");
        addHeader(html, "Host");
        addHeader(html, "Sends");
        addHeader(html, "Open Rate");
        addHeader(html, "Opens");
        addHeader(html, "CTR");

        html.append("data.addRows([");

        try {
            Calendar cal = Calendar.getInstance();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            con = Model.connectX();
//            stmt = con.prepareStatement("select  date(m.created_at) day, m.host_id, m.creative_id, count(distinct m.id) sends, count(distinct o.message_id) distint_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate_open_cnt, round(100*sum(c.click_count)/sum(o.open_count),2) click_rate_open_sum, round(100*sum(c.click_count)/count(distinct m.id),2) click_rate_send, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id where datediff(now(), m.created_at) < 7 group by m.host_id, m.creative_id, date(m.created_at) having count(distinct m.id) > 5 order by  m.host_id, date(m.created_at), m.creative_id");
            StringBuilder sql = new StringBuilder();
            sql.append("select date(m.created_at) day, m.host_id, mh.name, count(distinct m.id) sends, count(distinct o.message_id) distinct_opens, round(100*count(distinct o.message_id)/count(distinct m.id),2) distinct_open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) distinct_click_rate from messages m join user_profiles up on up.id = m.user_profile_id join mail_hosts mh on mh.id = up.mail_host_id left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id  where m.created_at > date_add(now(), interval -? day)");

            if (creativeId != null) {
                sql.append(" and m.creative_id = ?");
            }
            if (hostId != null) {
                sql.append(" and m.host_id = ?");
            }

            sql.append(" group by m.host_id, mh.name, date(m.created_at) having count(distinct m.id) > ? order by  date(m.created_at), m.host_id, mh.name");

            stmt = con.prepareStatement(sql.toString());
            stmt.setInt(1, daysBack);

            int i = 2;
            if (creativeId != null) {
                stmt.setLong(i++, creativeId);
            }

            if (hostId != null) {
                stmt.setLong(i++, hostId);
            }

            stmt.setInt(i, threshold);
            ResultSet rs = stmt.executeQuery();
            boolean isFirst = true;
            while (rs.next()) {
                //Date, host, creative, sends, opens
                Date day = df.parse(rs.getString("day"));
                cal.setTime(day);
                if (isFirst) {
                    isFirst = false;
                }
                else {
                    html.append(",");
                }
                html.append("['").append(rs.getString("name")).append("', new Date(").append(cal.get(Calendar.YEAR)).append(",").append(cal.get(Calendar.MONTH)).append(",").append(cal.get(Calendar.DAY_OF_MONTH)).append(")");
                html.append(", '").append(rs.getLong("host_id")).append("', ").append(rs.getLong("sends")).append(", ").append(rs.getDouble("distinct_open_rate")).append(", ").append(rs.getLong("distinct_opens")).append(", ").append(rs.getDouble("distinct_click_rate")).append("]\r\n");
            }
        }
        catch (ParseException e) {
            LOG.error(e);
        }
        finally {
            Model.close(stmt);
            Model.close(con);
        }

        html.append("]);");

        html.append("var motionchart = new google.visualization.MotionChart(");
        html.append("document.getElementById(\"chart_div\"));");
        html.append("motionchart.draw(data, {'width': 800, 'height': 400});");
        html.append("}");


        html.append("google.setOnLoadCallback(drawVisualization);");
        html.append("</script>");

        return html;
    }

    public StringBuilder getBarChart() throws SQLException, NamingException {
        StringBuilder html = new StringBuilder();
        // bar chart

        html.append("<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>");
        html.append("<script type=\"text/javascript\">");
        html.append("google.load('visualization', '1', {packages: ['corechart']});");
        html.append("</script>");
        html.append("<script type=\"text/javascript\">");
        html.append("function drawVisualization() {");
        // Some raw data (not necessarily accurate)
        html.append("var data = google.visualization.arrayToDataTable([");

        Connection con = null;
        PreparedStatement stmt = null;
        List<String> ispNames = new LinkedList<String>();

        try {
            con = Model.connectX();
            StringBuilder sql = new StringBuilder();
            sql.append("select date(m.created_at) day, m.host_id, mh.name isp, count(distinct m.id) sends, count(distinct o.message_id) distinct_opens, round(100*count(distinct o.message_id)/count(distinct m.id),2) distinct_open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) distinct_click_rate from messages m join user_profiles up on up.id = m.user_profile_id join mail_hosts mh on mh.id = up.mail_host_id left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id  where m.created_at > date_add(now(), interval -? day)");

            if (creativeId != null) {
                sql.append(" and m.creative_id = ?");
            }
            if (hostId != null) {
                sql.append(" and m.host_id = ?");
            }

            sql.append(" group by m.host_id, mh.name, date(m.created_at) having count(distinct m.id) > ? order by  date(m.created_at), m.host_id, mh.name");

            stmt = con.prepareStatement(sql.toString());
            stmt.setInt(1, daysBack);

            int i = 2;
            if (creativeId != null) {
                stmt.setLong(i++, creativeId);
            }

            if (hostId != null) {
                stmt.setLong(i++, hostId);
            }

            stmt.setInt(i, threshold);

            ResultSet rs = stmt.executeQuery();

            Map<String, Map<String, Double>> bars = new TreeMap<String, Map<String, Double>>(String.CASE_INSENSITIVE_ORDER);
            Map<String, Long> daySendCounts = new TreeMap<String, Long>(String.CASE_INSENSITIVE_ORDER);
            while (rs.next()) {
                //Date, host, creative, sends, opens
                String day = rs.getString("day");
                Map<String, Double> dayStats = bars.get(day);
                if (dayStats == null) {
                    dayStats = new Hashtable<String, Double>();
                    bars.put(day, dayStats);
                    daySendCounts.put(day, new Long(0));
                }

                Long count = daySendCounts.get(day);
                count += rs.getLong("sends");
                daySendCounts.put(day, count);

                // day, isp , distinct_open_rate
                String isp = rs.getString("isp");
                Double rate = rs.getDouble("distinct_open_rate");

                dayStats.put(isp, rate);
                if (!ispNames.contains(isp)) {
                    ispNames.add(isp);
                }
            }

            // sort isp names

            sortList(ispNames);

            // render the header (day, isp name list ....)
            //        html.append("['Month', 'Bolivia', 'Ecuador', 'Madagascar', 'Papua New Guinea', 'Rwanda', 'Average'],");
            html.append("['Day'");
            for (String name : ispNames) {
                html.append(",'").append(name).append("'");
            }
            html.append(",'Daily Sends(K)']\r\n");

            for (Map.Entry<String, Map<String, Double>> entry : bars.entrySet()) {
                String day = entry.getKey();
                Map<String, Double> ispData = entry.getValue();
                html.append(",['").append(day).append("'");
                for (String name : ispNames) {
                    html.append(",").append(coalesce(ispData.get(name), "0"));
                }
                html.append(",").append(daySendCounts.get(day) / 1000).append("]\r\n");
            }
        }
        finally {
            Model.close(stmt);
            Model.close(con);
        }


        html.append("]);");

        html.append("var options = {");
        html.append("title : 'Daily Open Rate by ISP',");
        html.append("vAxis: {title: \"Open Rate\"},");
        html.append("hAxis: {title: \"Day\"},");
        html.append("seriesType: \"bars\",");
        html.append("series: {" + ispNames.size() + ": {type: \"line\"}}");
        html.append("};");

        html.append("var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));");
        html.append("chart.draw(data, options);");
        html.append("}");
        html.append("google.setOnLoadCallback(drawVisualization);");
        html.append("</script>");

        return html;
    }

    public StringBuilder getLineChart() throws SQLException, NamingException {
        StringBuilder html = new StringBuilder();

        html.append("<script type=\"text/javascript\" src=\"http://www.google.com/jsapi\"></script>");
        html.append("<script type=\"text/javascript\">");
        html.append("google.load('visualization', '1', {packages: ['corechart']});");
        html.append("</script>");

        html.append("<script type=\"text/javascript\">");
        html.append(" function drawVisualization() {");
        // Create and populate the data table.

        html.append(" var data = google.visualization.arrayToDataTable([");

//        Connection con = null;
//        PreparedStatement stmt = null;
//
//        try {
//            con = Model.connectX();
//            StringBuilder sql = new StringBuilder();
//            sql.append("select m.host_id, mh.name isp, count(distinct m.id) sends, count(distinct o.message_id) distinct_opens, round(100*count(distinct o.message_id)/count(distinct m.id),2) distinct_open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) distinct_click_rate from messages m join user_profiles up on up.id = m.user_profile_id join mail_hosts mh on mh.id = up.mail_host_id left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id  where datediff(now(), m.created_at) < ? ");
//
//            if (creativeId != null) {
//                sql.append(" and m.creative_id = ?");
//            }
//            if (hostId != null) {
//                sql.append(" and m.host_id = ?");
//            }
//
//            sql.append(" group by m.host_id having count(distinct m.id) > 50 order by  mh.name");
//
//            stmt = con.prepareStatement(sql.toString());
//            stmt.setInt(1, daysBack);
//
//            int i = 2;
//            if (creativeId != null) {
//                stmt.setLong(i++, creativeId);
//            }
//
//            if (hostId != null) {
//                stmt.setLong(i, hostId);
//            }
//
//            html.append("['ISP', 'Sends', 'Open Rate']\r\n");
//
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                // day, isp , distinct_open_rate
//                String isp = rs.getString("isp");
//                Long sends = rs.getLong("sends");
//                Double rate = rs.getDouble("distinct_open_rate");
//
//                html.append(",['").append(isp).append("',").append(sends).append(",").append(rate).append("]\r\n");
//            }
//        }
//        finally {
//            Model.close(stmt);
//            Model.close(con);
//        }

        html.append("]);\r\n");

        html.append("new google.visualization.LineChart(document.getElementById('visualization')).");
        html.append("draw(data, {curveType: \"function\",");
        html.append("        width: 500, height: 400,");
        html.append("        vAxis: {maxValue: 10}}");
        html.append(");");
        html.append("}\r\n");

        html.append("google.setOnLoadCallback(drawVisualization);");
        html.append("</script>");

        /**
         <script type="text/javascript" src="https://www.google.com/jsapi"></script>
         <script type="text/javascript">
         google.load("visualization", "1", {packages:["corechart"]});
         google.setOnLoadCallback(drawChart);
         function drawChart() {
         var data = google.visualization.arrayToDataTable([
         ['Year', 'Sales', 'Expenses'],
         ['2004',  1000,      400],
         ['2005',  1170,      460],
         ['2006',  660,       1120],
         ['2007',  1030,      540]
         ]);

         var options = {
         title: 'Company Performance'
         };

         var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
         chart.draw(data, options);
         }
         </script>
         */


        return html;
    }

    public StringBuilder getTableChart() throws SQLException, NamingException {
        StringBuilder html = new StringBuilder();

        html.append("<script type='text/javascript' src='http://www.google.com/jsapi'></script>");
        html.append("<script type='text/javascript'>");
        html.append("  google.load('visualization', '1', {packages: ['table']});");
        html.append("</script>");
        html.append("<script type='text/javascript'>");
        html.append(" function drawVisualization() {");
        // Create and populate the data table.
        html.append("   var data = google.visualization.arrayToDataTable([");
        html.append("  ['Day', 'ISP', 'Sends', 'Dist.Opens', 'Dist.Clicks', 'Unsubs', 'Bounces', 'Dist.OpenRate','Dist.ClickRate', 'UnsubRate','BounceRate']\r\n");

//        html.append("    ['Name', 'Height', 'Smokes'],");
//        html.append("    ['Tong Ning mu', 174, true],");
//        html.append("    ['Huang Ang fa', 523, false],");
//        html.append("    ['Teng nu', 86, true]");
//        html.append("    ]);");

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = Model.connectX();
            StringBuilder sql = new StringBuilder();
            sql.append(" select date(m.created_at) day,  mh.name, count(distinct m.id) sends, count(distinct o.message_id) distinct_opens, sum(o.open_count) sum_opens, count(distinct c.message_id) distinct_clicks, sum(c.click_count) sum_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id join user_profiles up on up.id = m.user_profile_id join mail_hosts mh on mh.id = up.mail_host_id where m.created_at > date_add(now(), interval -? day)");

            if (creativeId != null) {
                sql.append(" and m.creative_id = ?");
            }
            if (hostId != null) {
                sql.append(" and m.host_id = ?");
            }

            sql.append(" group by m.host_id, mh.name, date(m.created_at) having count(distinct m.id) > 5 order by  date(m.created_at), m.host_id, mh.name");

            stmt = con.prepareStatement(sql.toString());
            stmt.setInt(1, daysBack);

            int i = 2;
            if (creativeId != null) {
                stmt.setLong(i++, creativeId);
            }

            if (hostId != null) {
                stmt.setLong(i, hostId);
            }

            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                html.append(",['").append(rs.getString("day")).append("','").append(rs.getString("name")).append("'");
                html.append(",").append(rs.getLong("sends")).append(",").append(rs.getLong("distinct_opens"));
                html.append(",").append(rs.getLong("distinct_clicks"));
                html.append(",").append(rs.getLong("unsubs"));
                html.append(",").append(rs.getLong("bounces"));
                html.append(",").append(rs.getDouble("open_rate"));
                html.append(",").append(rs.getDouble("click_rate"));
                html.append(",").append(rs.getDouble("unsub_rate"));
                html.append(",").append(rs.getDouble("bounce_rate")).append("]\r\n");
            }
        }
        finally {
            Model.close(stmt);
            Model.close(con);
        }

        html.append("    ]);");

        // Create and draw the visualization.
        html.append("    visualization = new google.visualization.Table(document.getElementById('table'));");
        html.append("    visualization.draw(data, null);");
        html.append("}");


        html.append("google.setOnLoadCallback(drawVisualization);");
        html.append("</script>");

        return html;
    }

    private void addHeader(StringBuilder html, String key) {
        html.append("data.addColumn('").append(HEADERS.get(key)).append("', '").append(key).append("');\r\n");
    }
}