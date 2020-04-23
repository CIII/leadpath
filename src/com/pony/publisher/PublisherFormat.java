package com.pony.publisher;

import com.pony.advertiser.Disposition;
import com.pony.advertiser.Io;
import com.pony.core.MediaType;
import com.pony.models.PublisherListModel;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 7/6/13
 * Time: 1:04 PM
 */
public class PublisherFormat {
	private static final Log LOG = LogFactory.getLog(PublisherFormat.class);
    protected final IPublisherContext publisherContext;

    public PublisherFormat(IPublisherContext publisherContext) {
        this.publisherContext = publisherContext;
    }

    public void format(PublisherResponse publisherResponse, HttpServletResponse servletResponse) throws IOException {
        // default format
        Writer out;

        if ("test".equals(publisherContext.getPublisher().getName())) {
            servletResponse.setContentType("text/plain");
            out = servletResponse.getWriter();
            out.write(publisherResponse.toString());
        }
        else {
            if (publisherContext.isPing() || publisherContext.isPoll()) {

                List<Io> finals = new ArrayList<Io>();

                if (publisherContext.waitForResponse()) {
                    // if the endpoint is waiting for us, we most likely have more data about the pings (i.e. we don't have to wait for the poll)
                    for (Map.Entry<Io, Disposition> entry : publisherResponse.getIoDispositions().entrySet()) {
                        if (entry.getValue().isAccepted()) {
                            finals.add(entry.getKey());
                        }
                    }
                }

                // normalize ext_list_ids across these finalist orders
                List<String> externalListIds;
                try {
                    externalListIds = normalize(finals);
                }
                catch (NamingException e) {
                    throw new IOException(e);
                }
                catch (SQLException e) {
                    throw new IOException(e);
                }

                writeResponse(servletResponse, publisherResponse, externalListIds);
            } else if (!publisherResponse.isValid()){
            	// TODO: Construct JSON response.
            }
            else {
                servletResponse.setContentType("text/plain");
                out = servletResponse.getWriter();
                if(publisherResponse.isValid()) {
                	out.write("success");
                } else {
                	LOG.error(publisherResponse.getValidationText());
                	out.write("failure");
                }
            }
        }
        servletResponse.setStatus(HttpServletResponse.SC_OK);
    }

    private List<String> normalize(List<Io> finals) throws NamingException, SQLException {

        List<Long> orderIds = new ArrayList<Long>();
        for (Io io : finals) {
            orderIds.add(io.getId());
        }

        return PublisherListModel.findExternalIdsForOrders(publisherContext.getPublisher(), publisherContext.getLeadType(), orderIds);
    }

    private void writeResponse(HttpServletResponse servletResponse, PublisherResponse publisherResponse, List<String> listIds) throws IOException {

        StringBuilder r = new StringBuilder();
        r.append("{\"lead_id\":\"").append(publisherResponse.getLeadId()).append("\",\"validation_code\":\"").append(publisherResponse.getValidationCode()).append("\",\"leadpath_listids\":[");
        boolean f = true;
        for (String list : listIds) {
            if (f) {
                f = false;
            }
            else {
                r.append(",");
            }

            r.append("\"").append(list).append("\"");
        }
        r.append("]}");

        Writer out = null;
        try {
            servletResponse.setContentType(MediaType.JSON.toString());
            servletResponse.setCharacterEncoding("utf-8");
            out = servletResponse.getWriter();
            out.write(r.toString());
            out.flush();
        }
        finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
