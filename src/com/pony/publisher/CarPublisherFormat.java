package com.pony.publisher;

import com.pony.advertiser.Buyer;
import com.pony.advertiser.Disposition;
import com.pony.core.MediaType;
import com.pony.rules.Rule;
import com.pony.tools.StringTools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 7/6/13
 * Time: 1:11 PM
 */
public class CarPublisherFormat extends PublisherFormat {
	private static final Log LOG = LogFactory.getLog(CarPublisherFormat.class);

    public CarPublisherFormat(PublisherContext context) {
        super(context);
    }

    @Override
    public void format(PublisherResponse publisherResponse, HttpServletResponse servletResponse) throws IOException {
        if (publisherContext.isPing()) {
            servletResponse.setContentType(MediaType.XML.toString());
            servletResponse.setCharacterEncoding("utf-8");

            try {
                Document doc = createDocument();
                Element pingResponse = createPingResponse(publisherResponse, doc);

                if (publisherContext.waitForResponse()) {
                    // if the endpoint is waiting for us, we most likely have more data about the pings (i.e. we don't have to wait for the poll)
                    addDealerListNormalized(publisherResponse, doc, pingResponse);
                }

                writeXMLResponse(servletResponse, doc);
            }
            catch (ParserConfigurationException e) {
                LOG.error(e);
            }
            catch (TransformerConfigurationException e) {
                LOG.error(e);
            }
            catch (TransformerException e) {
                LOG.error(e);
            }

            servletResponse.setStatus(HttpServletResponse.SC_OK);
        }
        else if (publisherContext.isPoll()) {
            // if there are ping responses to return , do so now
            servletResponse.setContentType(MediaType.XML.toString());
            servletResponse.setCharacterEncoding("utf-8");

            try {
                Document doc = createDocument();
                Element pingResponse = createPingResponse(publisherResponse, doc);

                addDealerListNormalized(publisherResponse, doc, pingResponse);

                writeXMLResponse(servletResponse, doc);
            }
            catch (ParserConfigurationException e) {
                LOG.error(e);
            }
            catch (TransformerConfigurationException e) {
                LOG.error(e);
            }
            catch (TransformerException e) {
                LOG.error(e);
            }

            servletResponse.setStatus(HttpServletResponse.SC_OK);
        }
        else if (publisherContext.isPost()) {
            // success or not
            servletResponse.setContentType(MediaType.TEXT.toString());
            servletResponse.setCharacterEncoding("utf-8");
            Writer out = servletResponse.getWriter();
            out.write(publisherResponse.toString());
            servletResponse.setStatus(HttpServletResponse.SC_OK);
        }
    }

    private void addDealerListNormalized(PublisherResponse publisherResponse, Document doc, Element pingResponse) {
        Element dealers = doc.createElement("dealers");
        pingResponse.appendChild(dealers);

        // create a master list of dealers, independent of the partner
        List<Buyer> buyers = new LinkedList<Buyer>();
        for (Disposition disposition : publisherResponse.getDispositions()) {
            if (disposition.hasBuyers()) {
                buyers.addAll(disposition.getBuyers());
            }
        }

        //
        // sort by name and zip to see dups; if we do have some remove the one with the lesser payout (the first one is the highest payout, so remove the rest with the same name

        // see if there is a preferred buyer, and if so, make sure it's in the list
        Map<String, Buyer> preferredBuyers = new HashMap<String, Buyer>();
        if (publisherResponse.getPreferredBuyers().size() > 0) {
            for (Buyer buyer : publisherResponse.getPreferredBuyers()) {
                // setup the key for later comparison
                String preferredKey = buyer.getZipcode() + "-" + buyer.getName().trim().toLowerCase();
                preferredBuyers.put(preferredKey, buyer);
            }
        }

        List<Buyer> normalizedBuyers = new LinkedList<Buyer>();
        Collections.sort(buyers, new Rule.BuyerNameZipComparator());
        Map<String, List<Buyer>> bList = new HashMap<String, List<Buyer>>();
        for (Buyer buyer : buyers) {
            String key = buyer.getZipcode() + "-" + buyer.getName().trim().toLowerCase();
            List<Buyer> bl = bList.get(key);
            if (bl == null) {
                bl = new ArrayList<Buyer>();
                bList.put(key, bl);

                // if the key points to a preferred buyer, make sure that gets inserted into the list
                Buyer preferredBuyer = preferredBuyers.get(key);
                if (preferredBuyer != null) {
                    normalizedBuyers.add(preferredBuyer);
                }
                else {
                    normalizedBuyers.add(buyer); // if there is no preferred buyer for this key, the first one is the keeper
                }
            }
            bl.add(buyer);
        }

        // now sort the remaining list by price desc and cut off at 5 entries max
        Collections.sort(normalizedBuyers, new Rule.BuyerPriceComparator(publisherResponse.getPreferredBuyers()));
        int b = 1;

        for (Buyer buyer : normalizedBuyers) {
            // stop at 5 dealers
            if (b++ > 5) {
                break;
            }

            Element dealer = doc.createElement("dealer");
            dealers.appendChild(dealer);
            dealer.setAttribute("id", "" + buyer.getId());
            dealer.setAttribute("name", StringTools.capitalize(buyer.getName()));
            dealer.setAttribute("distance", buyer.getDistance());
            dealer.setAttribute("address", buyer.getAddress());
            dealer.setAttribute("city", StringTools.capitalize(buyer.getCity()));
            dealer.setAttribute("zip", buyer.getZipcode());
            dealer.setAttribute("state", StringTools.allCaps(buyer.getState()));
            dealer.setAttribute("exclusive", "" + buyer.isExclusive());
            if (buyer.isPreferred()) {
                dealer.setAttribute("preferred", "" + true);
            }

            // TODO: update the buyer disposition to log the fact that it received an impression
            // Note: if we change the status , make sure the post can handle that (doesn't filter based on status!)
//            LeadMatchModel.updateBuyerDisposition(buyer.getId());
        }
        LOG.info("-->CarPublisherFormat: sending " + (b - 1) + " dealers");
    }

    private void writeXMLResponse(HttpServletResponse servletResponse, Document doc) throws TransformerException, IOException {
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);

        Writer out = servletResponse.getWriter();
        out.write(writer.toString());
    }

    private Element createPingResponse(PublisherResponse publisherResponse, Document doc) {
        Element pingResponse = doc.createElement("ping-response");
        doc.appendChild(pingResponse);

        Element leadRef = doc.createElement(PublisherContext.LEAD_REF);
        leadRef.setTextContent("" + publisherResponse.getLeadId());
        pingResponse.appendChild(leadRef);

        Element node = doc.createElement("valid");
        node.setTextContent("" + publisherResponse.getValidationCode());
        pingResponse.appendChild(node);
        return pingResponse;
    }

    private Document createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        return docBuilder.newDocument();
    }
}
