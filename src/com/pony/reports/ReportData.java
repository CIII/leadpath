package com.pony.reports;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 10/23/12
 * Time: 3:00 PM
 */
public abstract class ReportData {
    protected final String chartType;
    protected final Long creativeId, hostId;
    protected final int daysBack, threshold;
    protected static final String DEFAULT_DAYS_BACK = "7";
    protected static final String DEFAULT_THRESHOLD = "50";

    protected final Map<String, String> HEADERS = new TreeMap<String, String>();

    public ReportData(String chartType, String daysBack, String creativeId, String hostId, String threshold) {
        this.chartType = chartType;
        this.daysBack = Integer.valueOf(daysBack);
        this.threshold = Integer.valueOf(threshold);

        this.creativeId = creativeId == null ? null : Long.valueOf(creativeId);
        this.hostId = hostId == null ? null : Long.valueOf(hostId);
    }

    public static ReportData parseParamsAndRetrieve(HttpServletRequest request) {
        // parse report type and chart type from the path: /reports/motion/openrate

        if (request.getPathInfo() == null || request.getPathInfo().length() < 1) {
            return null;
        }

        String[] tokens = request.getPathInfo().substring(1).split("/");
        if (tokens.length < 2) {
            return null;
        }
        String t1 = tokens[0].trim();
        String t2 = tokens[1].trim();
        String t3 = DEFAULT_DAYS_BACK; // default to 7 (days or hours, depending on the type of report)
        if (tokens.length >= 3) {
            t3 = tokens[2].trim();
        }

        // is there any filter for hostid or creativeid?
        String creativeId = request.getParameter("creative_id");
        String hostId = request.getParameter("host_id");
        String threshold = request.getParameter("threshold");
        if (threshold == null) {
            threshold = DEFAULT_THRESHOLD;
        }
        return mapPathToData(t1, t2, t3, creativeId, hostId, threshold);
    }

    private static ReportData mapPathToData(String reportType, String chartType, String daysBack, String creativeId, String hostId, String threshold) {
        if ("openrate".equals(reportType)) {
            return new OpenRateReportData(chartType, daysBack, creativeId, hostId, threshold);
        }
        else if ("ctr".equals(reportType)) {
            return new CtrReportData(chartType, daysBack, creativeId, hostId, threshold);
        }
        else if ("isp".equals(reportType)) {
            return new IspOpenRateReportData(chartType, daysBack, creativeId, hostId, threshold);
        }
        else if ("clickstream".equals(reportType)) {
            return new ClickStreamReportData(chartType, daysBack, threshold);
        }
        return null;
    }

    public StringBuilder getChart() throws NamingException, SQLException {
        if ("motion".equals(chartType)) {
            return getMotionChart();
        }
        else if ("line".equals(chartType)) {
            return getLineChart();
        }
        else if ("bar".equals(chartType)) {
            return getBarChart();
        }
        else if ("table".equals(chartType)) {
            return getTableChart();
        }

        return null;
    }

    public abstract StringBuilder getTitle();

    public StringBuilder getMotionChart() throws NamingException, SQLException {
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

    public StringBuilder getBarChart() throws NamingException, SQLException {
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

    public StringBuilder getLineChart() throws NamingException, SQLException {
        StringBuilder html = new StringBuilder();

        html.append("<script type=\"text/javascript\" src=\"http://www.google.com/jsapi\"></script>");
        html.append("<script type=\"text/javascript\">");
        html.append("google.load('visualization', '1', {packages: ['corechart']});");

        html.append("google.setOnLoadCallback(drawChart);");
        html.append(" function drawChart() {");
        html.append(" var data = google.visualization.arrayToDataTable([");


        html.append("var options = {");
        html.append("       title: 'Company Performance'");
        html.append("};");

        html.append("['Year', 'Sales', 'Expenses'],");
        html.append("['2004',  1000,      400],");
        html.append("['2007',  1030,      540]");

        html.append("]);");

        html.append("var chart = new google.visualization.LineChart(document.getElementById('chart_div'));");
        html.append(" chart.draw(data, options);");
        html.append(" }");
        html.append(" </script>");

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
        html.append("<script type=\"text/javascript\" src=\"http://www.google.com/jsapi\"></script>");
        html.append("<script type=\"text/javascript\">");
        html.append("google.load('visualization', '1', {packages: ['motionchart']});");

        html.append("function drawVisualization() {");
        html.append("var data = new google.visualization.DataTable();");

        html.append(" function drawVisualization() {");
        // Create and populate the data table.
        html.append(" var data = google.visualization.arrayToDataTable([");

        //html.append("  ['Name', 'Height', 'Smokes'],");
        //['Tong Ning mu', 174, true],
        //['Teng nu', 86, true]

        html.append(" ]);");

        // Create and draw the visualization.
        html.append("visualization = new google.visualization.Table(document.getElementById('table'));");
        html.append("visualization.draw(data, null);");
        html.append("}");


        return html;
    }

    public StringBuilder getHead() throws NamingException, SQLException {
        StringBuilder html = new StringBuilder();
        html.append("<head>");
        html.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>");
        html.append(getTitle());

        html.append(getChart());

        html.append("</head>\r\n");
        return html;
    }

    public StringBuilder getBody() {
        StringBuilder html = new StringBuilder();
        if ("table".equals(chartType)) {
            html.append("<body style=\"font-family: Arial;border: 0 none;\">");
            html.append("<div id=\"table\"></div>");
        }
        else if ("line".equals(chartType)) {
            html.append("<body style='font-family: Arial;border: 0 none;'>");
            html.append("<div id='visualization' style='width: 500px; height: 400px;'></div>");
        }
        else {
            html.append("<body>");
            html.append("<div id=\"chart_div\" style=\"width: 900px; height: 500px;\"></div>");
        }

        html.append("</body>");
        return html;
    }

    protected String coalesce(Double v1, String v2) {
        if (v1 == null) {
            return v2;
        }

        return v1.toString();
    }

    protected String coalesce(Long v1, String v2) {
        if (v1 == null) {
            return v2;
        }

        return v1.toString();
    }

    protected Long coalesce(Long v1, Long v2) {
        if (v1 == null) {
            return v2;
        }

        return v1;
    }

    protected void sortList(List<String> aItems) {
        Collections.sort(aItems, String.CASE_INSENSITIVE_ORDER);
    }

}
