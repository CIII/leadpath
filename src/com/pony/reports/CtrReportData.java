package com.pony.reports;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 10/23/12
 * Time: 3:54 PM
 */
public class CtrReportData extends ReportData {
    public CtrReportData(String chartType, String daysBack, String creativeId, String hostId, String threshold) {
        super(chartType, daysBack, creativeId, hostId, threshold);
    }

    @Override
    public StringBuilder getTitle() {
        StringBuilder html = new StringBuilder();
        html.append("<title>CTR</title>");
        return html;
    }
}
