package com.pony.reports;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

/**
 * HowTO for now :
 * <p/>
 * /reports/<report type> / <chart type / <number of days back> ?host_id=<host id>&creative_id=<creative id>
 * examples:
 * /reports/isp/motion/1 (by ISP, Google Motion Chart for today)
 * /reports/openrate/motion/30
 * /reports/ctr/motion/7?host_id=1 (ClickThroughRate, motion chart, last 7 days, only for host_id 1
 * /reports/openrate/motion/10?creative_id=99 (open rate, motion chart / past 10 days / only creative 99)
 * <p/>
 * PonyLeads 2012.
 * User: martin
 * Date: 10/15/12
 * Time: 10:05 PM
 */
public class ReportServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(ReportServlet.class);
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // path = chart type
        // next path = report

        ReportData data = ReportData.parseParamsAndRetrieve(req);

        if (data == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try {
            resp.setCharacterEncoding("utf8");
            resp.setContentType("text/html");
            Writer out = resp.getWriter();
//            out.write(getHeader().append(data.getTitle()).append(data.getChart()).append(getFooter()).toString());
            out.write(getHeader().append(data.getHead()).append(data.getBody()).append(getFooter()).toString());
        }
        catch (NamingException e) {
            LOG.error(e);
        }
        catch (SQLException e) {
            LOG.error(e);
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private StringBuilder getHeader() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
        html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        return html;
    }

    private StringBuilder getFooter() {
        StringBuilder html = new StringBuilder();
        html.append("</html>");
        return html;
    }

    private StringBuilder getTitle() {
        StringBuilder html = new StringBuilder();
        html.append("<title>Google Visualization API Sample</title>");
        return html;
    }

    private StringBuilder getMotionChart() {
        StringBuilder html = new StringBuilder();

        html.append("<script type=\"text/javascript\" src=\"http://www.google.com/jsapi\"></script>");
        html.append("<script type=\"text/javascript\">");
        html.append("google.load('visualization', '1', {packages: ['motionchart']});");

        html.append("function drawVisualization() {");
        html.append("var data = new google.visualization.DataTable();");
        html.append("data.addColumn('string', 'Fruit');");
        html.append("data.addColumn('date', 'Date');");
        html.append("data.addColumn('number', 'Sales');");
        html.append("data.addColumn('number', 'Expenses');");
        html.append("data.addColumn('string', 'Location');");
        html.append("data.addRows([");
        html.append("['Apples', new Date(1988,0,1), 1000, 300, 'East'],");
        html.append("['Oranges', new Date(1988,0,1), 950, 200, 'West'],");
        html.append("['Bananas', new Date(1988,0,1), 300, 250, 'West'],");
        html.append("['Apples', new Date(1988,1,1), 1200, 400, 'East'],");
        html.append("['Oranges', new Date(1988,1,1), 900, 150, 'West'],");
        html.append("['Bananas', new Date(1988,1,1), 788, 617, 'West']");
        html.append("]);");

        html.append("var motionchart = new google.visualization.MotionChart(");
        html.append("document.getElementById(\"chart_div\"));");
        html.append("motionchart.draw(data, {'width': 800, 'height': 400});");
        html.append("}");


        html.append("google.setOnLoadCallback(drawVisualization);");
        html.append("</script>");
        return html;
    }

    private StringBuilder barChart() {
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
        html.append("['Month', 'Bolivia', 'Ecuador', 'Madagascar', 'Papua New Guinea', 'Rwanda', 'Average'],");
        html.append("['2004/05',  165,      938,         522,             998,           450,      614.6],");
        html.append("['2005/06',  135,      1120,        599,             1268,          288,      682],");
        html.append("['2006/07',  157,      1167,        587,             807,           397,      623],");
        html.append("['2007/08',  139,      1110,        615,             968,           215,      609.4],");
        html.append("['2008/09',  136,      691,         629,             1026,          366,      569.6]");
        html.append("]);");

        html.append("var options = {");
        html.append("title : 'Monthly Coffee Production by Country',");
        html.append("vAxis: {title: \"Cups\"},");
        html.append("hAxis: {title: \"Month\"},");
        html.append("seriesType: \"bars\",");
        html.append("series: {5: {type: \"line\"}}");
        html.append("};");

        html.append("var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));");
        html.append("chart.draw(data, options);");
        html.append("}");
        html.append("google.setOnLoadCallback(drawVisualization);");
        html.append("</script>");

        return html;
    }

    private StringBuilder lineChart() {
        StringBuilder html = new StringBuilder();
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
}
