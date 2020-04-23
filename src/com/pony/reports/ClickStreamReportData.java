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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 12/12/12
 * Time: 4:32 PM
 */
public class ClickStreamReportData extends ReportData {
	private static final Log LOG = LogFactory.getLog(ClickStreamReportData.class);

    public ClickStreamReportData(String chartType, String daysBack, String threshold) {
        super(chartType, daysBack, null, null, threshold);
        HEADERS.put("Date", "date");
        HEADERS.put("Source", "string");
        HEADERS.put("Pixel Type", "string");
        HEADERS.put("Count", "number");
        HEADERS.put("CTR", "number");
    }

    @Override
    public StringBuilder getTitle() {
        StringBuilder html = new StringBuilder();
        html.append("<title>ClickSource Stats</title>");
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

        List<String> sources = new LinkedList<String>();

        try {
            con = Model.connectX();
            StringBuilder sql = new StringBuilder();
            sql.append("select date(r.created_at) day, cs.name click_source, coalesce(pf.pixel_type, -1) pixel_type, count(distinct r.id) redirects, count(distinct pf.id) fires from redirects r left join pixel_fires pf on pf.redirect_id = r.id join click_targets ct on ct.id = r.click_target_id join click_sources cs on cs.id = r.click_source_id where r.created_at > date_add(now(), interval -? day) group by r.click_source_id, pf.pixel_type, date(r.created_at) order by date(r.created_at), ct.name, pf.pixel_type");

            stmt = con.prepareStatement(sql.toString());
            stmt.setInt(1, daysBack);

            ResultSet rs = stmt.executeQuery();

            // by date => by pixel type => counts
            Map<String, Map<Long, Long>> pixelFires = new TreeMap<String, Map<Long, Long>>(String.CASE_INSENSITIVE_ORDER);

            // by date => redirects
            Map<String, Long> redirects = new TreeMap<String, Long>(String.CASE_INSENSITIVE_ORDER);

            while (rs.next()) {
                //Date, host, creative, sends, opens
                String day = rs.getString("day");

                Long dayRedirects = redirects.get(day);
                if (dayRedirects == null) {
                    dayRedirects = 0L;
                }

                Map<Long, Long> dayPixels = pixelFires.get(day);
                if (dayPixels == null) {
                    dayPixels = new HashMap<Long, Long>();
                }

                Long pixelType = rs.getLong("pixel_type");
                Long dayFires = dayPixels.get(pixelType);
                if (dayFires == null) {
                    dayFires = 0L;
                }

                String src = rs.getString("click_source");
                if (!sources.contains(src)) {
                    sources.add(src);
                }

                Long f = rs.getLong("fires");
                Long r = rs.getLong("redirects");

                dayRedirects += r;
                redirects.put(day, dayRedirects);

                dayFires += f;
                dayPixels.put(pixelType, dayFires);
                pixelFires.put(day, dayPixels);
            }

            // sort click source names

//            sortList(sources);

            // render the header (day, source name list ....)
            //        html.append("['Month', 'Bolivia', 'Ecuador', 'Madagascar', 'Papua New Guinea', 'Rwanda', 'Average'],");
            html.append("['Day', 'arrivals', 'conversions', 'redirects']\r\n");

            for (Map.Entry<String, Map<Long, Long>> entry : pixelFires.entrySet()) {
                String day = entry.getKey();
                Map<Long, Long> pixelData = entry.getValue();
                Long redirectData = redirects.get(day);
                html.append(",['").append(day).append("',");

                html.append(coalesce(pixelData.get(1L), 0L)).append(",").append(coalesce(pixelData.get(2L), 0L)).append(",").append(coalesce(redirectData, 0L));

                html.append("]\r\n");
            }
        }
        finally {
            Model.close(stmt);
            Model.close(con);
        }


        html.append("]);");

        html.append("var options = {");
        html.append("title : 'Daily Redirects and Pixel Fires',");
        html.append("vAxis: {title: \"Counts\"},");
        html.append("hAxis: {title: \"Day\"},");
        html.append("seriesType: \"bars\",");
        html.append("series: {2: {type: \"line\"}}");
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

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = Model.connectX();
            StringBuilder sql = new StringBuilder();
            // , round(100*count(distinct o.message_id)/count(distinct m.id),2) distinct_open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) distinct_click_rate
            sql.append("select date(m.created_at) day, count(distinct m.id) sends, count(distinct o.message_id) distinct_opens, count(distinct c.message_id) distinct_clicks from messages m join user_profiles up on up.id = m.user_profile_id join mail_hosts mh on mh.id = up.mail_host_id left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id  where m.created_at > date_add(now(), interval -? day)");

            if (creativeId != null) {
                sql.append(" and m.creative_id = ?");
            }
            if (hostId != null) {
                sql.append(" and m.host_id = ?");
            }

            sql.append(" group by date(m.created_at) having count(distinct m.id) > 50 order by  date(m.created_at)");

            stmt = con.prepareStatement(sql.toString());
            stmt.setInt(1, daysBack);

            int i = 2;
            if (creativeId != null) {
                stmt.setLong(i++, creativeId);
            }

            if (hostId != null) {
                stmt.setLong(i, hostId);
            }

            html.append("['Day', 'Sends', 'Opens', 'Clicks']\r\n");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // day, isp , distinct_open_rate
                String day = rs.getString("day");
                Long sends = rs.getLong("sends");
                Long opens = rs.getLong("distinct_opens");
                Long clicks = rs.getLong("distinct_clicks");
                html.append(",['").append(day).append("',").append(sends).append(",").append(opens).append(",").append(clicks).append("]\r\n");
            }
        }
        finally {
            Model.close(stmt);
            Model.close(con);
        }

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
        Long sends = 0L, opens = 0L, clicks = 0L, bounces = 0L, unsubs = 0L;
        Double or = 0.0d, cr = 0.0d, cre = 0.0d, br = 0.0d, ur = 0.0d;

        html.append("<script type='text/javascript' src='http://www.google.com/jsapi'></script>");
        html.append("<script type='text/javascript'>");
        html.append("  google.load('visualization', '1', {packages: ['table']});");
        html.append("</script>");
        html.append("<script type='text/javascript'>");
        html.append(" function drawVisualization() {");
        // Create and populate the data table.
        html.append("   var data = google.visualization.arrayToDataTable([");
        html.append("  ['Day', 'Host', 'Creative', 'Sends', 'Bounces', 'Dist.Opens', 'Dist.Clicks', 'Unsubs', 'Dist.OpenRate', 'BounceRate','Dist.ClickRate', 'ClickRateEnd2End', 'UnsubRate']\r\n");

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
            sql.append("select  date(m.created_at) day, m.host_id, m.creative_id, count(distinct m.id) sends, count(distinct o.message_id) distinct_opens, count(distinct c.message_id) distinct_clicks, count(distinct u.user_profile_id) unsubs, count(distinct b.user_profile_id) bounces, round(100*count(distinct o.message_id)/count(distinct m.id),2) open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) click_rate, round(100*count(distinct c.message_id)/count(distinct m.id),2) click_rate_end_to_end, round(100*count(distinct u.user_profile_id)/count(distinct m.id),2) unsub_rate, round(100*count(distinct b.user_profile_id)/ count(distinct m.id),2) bounce_rate from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id where m.created_at > date_add(now(), interval -? day)");

            if (creativeId != null) {
                sql.append(" and m.creative_id = ?");
            }
            if (hostId != null) {
                sql.append(" and m.host_id = ?");
            }

            sql.append(" group by m.host_id, m.creative_id, date(m.created_at) having count(distinct m.id) > 5 order by  m.host_id, m.creative_id, date(m.created_at)");

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
            long count = 0L;
            while (rs.next()) {
                html.append(",['").append(rs.getString("day")).append("','").append(rs.getString("host_id")).append("','");
                html.append(rs.getString("creative_id"));
                html.append("',").append(rs.getLong("sends")).append(",").append(rs.getLong("distinct_opens"));
                html.append(",").append(rs.getLong("bounces"));
                html.append(",").append(rs.getLong("distinct_clicks"));
                html.append(",").append(rs.getLong("unsubs"));
                html.append(",").append(rs.getDouble("open_rate"));
                html.append(",").append(rs.getDouble("bounce_rate"));
                html.append(",").append(rs.getDouble("click_rate"));
                html.append(",").append(rs.getDouble("click_rate_end_to_end"));
                html.append(",").append(rs.getDouble("unsub_rate")).append("]\r\n");

                sends += rs.getLong("sends");
                opens += rs.getLong("distinct_opens");
                clicks += rs.getLong("distinct_clicks");
                bounces += rs.getLong("bounces");
                unsubs += rs.getLong("unsubs");

                or += rs.getDouble("open_rate");
                cr += rs.getDouble("click_rate");
                cre += rs.getDouble("click_rate_end_to_end");
                br += rs.getDouble("bounce_rate");
                ur += rs.getDouble("unsub_rate");
                count++;
            }
            DecimalFormat f = new DecimalFormat("##.##");
            html.append(",['Total:','','',").append(sends).append(",").append(bounces).append(",").append(opens).append(",").append(clicks).append(",").append(unsubs).append(",").append(f.format(or / count)).append(",").append(f.format(br / count)).append(",").append(f.format(cr / count)).append(",").append(f.format(cre / count)).append(",").append(f.format(ur / count)).append("]\r\n");
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

    public StringBuilder getMotionChart() throws NamingException, SQLException {
        StringBuilder html = new StringBuilder();

        Connection con = null;
        PreparedStatement stmt = null;


        html.append("<script type=\"text/javascript\" src=\"http://www.google.com/jsapi\"></script>");
        html.append("<script type=\"text/javascript\">");
        html.append("google.load('visualization', '1', {packages: ['motionchart']});");

        html.append("function drawVisualization() {");
        html.append("var data = new google.visualization.DataTable();");

        addHeader(html, "Host");
        addHeader(html, "Date");
        addHeader(html, "Creative");
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
            sql.append("select date(m.created_at) day, m.host_id, m.creative_id, count(distinct m.id) sends, count(distinct o.message_id) distinct_opens, round(100*count(distinct o.message_id)/count(distinct m.id),2) distinct_open_rate, round(100*count(distinct c.message_id)/count(distinct o.message_id),2) distinct_click_rate from messages m left join opens o on o.message_id = m.id left join clicks c on m.id = c.message_id left join unsubscribes u on u.message_id = m.id left join bounces b on b.user_profile_id = m.user_profile_id where m.created_at > date_add(now(), interval -? day)");

            if (creativeId != null) {
                sql.append(" and m.creative_id = ?");
            }
            if (hostId != null) {
                sql.append(" and m.host_id = ?");
            }

            sql.append(" group by m.host_id, m.creative_id, date(m.created_at) having count(distinct m.id) > 5 order by  date(m.created_at), m.host_id, m.creative_id");

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
                html.append("['").append(rs.getLong("host_id")).append("', new Date(").append(cal.get(Calendar.YEAR)).append(",").append(cal.get(Calendar.MONTH)).append(",").append(cal.get(Calendar.DAY_OF_MONTH)).append(")");
                html.append(", '").append(rs.getLong("creative_id")).append("', ").append(rs.getLong("sends")).append(", ").append(rs.getDouble("distinct_open_rate")).append(", ").append(rs.getLong("distinct_opens")).append(", ").append(rs.getDouble("distinct_click_rate")).append("]\r\n");
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


    private void addHeader(StringBuilder html, String key) {
        html.append("data.addColumn('").append(HEADERS.get(key)).append("', '").append(key).append("');\r\n");
    }
}
