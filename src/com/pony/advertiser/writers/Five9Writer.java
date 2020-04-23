package com.pony.advertiser.writers;

import com.pony.advertiser.AdvertiserService;
import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Post to Five9
 * <p/>
 * source_id should contain:
 * - arrival_pixel_id   optional SiteBrain Pixel id to track arrivals (primarily for external posts)
 * - F9domain           identify what domain to post into at F9
 * - F9list             identify the list to post to
 * - crm_column         F9 column to post the crm link to (so F9 can auto pop the record in our CRM)
 * - crm_url            optional root path ; if empty, we only post our lead_match_id
 * <p/>
 * ArbVentures 2013.
 * User: martin
 * Date: 4/24/14
 * Time: 2:07 PM
 */
public class Five9Writer extends AdvertiserWriter {
	private static final Log LOG = LogFactory.getLog(Five9Writer.class);

    @Override
    public boolean supportsPhase(PonyPhase phase) {
        return PonyPhase.POST == phase;
    }

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {

        Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());

        if (map.get(NO_SEND) != null) {
            return Disposition.createPost(Disposition.Status.ACCEPTED, Disposition.DispositionCategory.NO_MATCH, "configured to not send.");
        }

        try {
            fireLeadArrivalPixel(leadMatchId, candidate, map);

            return postToFive9(publisherContext, leadMatchId, map, candidate);
        }
        catch (IllegalArgumentException e) {
            return Disposition.createPost(Disposition.Status.REJECTED, null, e.getMessage());
        }
        catch (IOException e) {
            return Disposition.createPost(Disposition.Status.REJECTED, null, e.getMessage());
        }
    }

    private void fireLeadArrivalPixel(Long leadMatchId, RoutingCandidate candidate, Map<String, String> sourceIdMap) {
        // http get to SB
        final String arrivalPixelId = sourceIdMap.get("arrival_pixel_id");
        if (arrivalPixelId == null) {
            LOG.warn("WARNING: No arrival pixel defined for " + candidate.getIo().getCode());
        }
        else {
            Long pixelId = Long.valueOf(arrivalPixelId);
        }
    }

    private Disposition postToFive9(IPublisherContext publisherContext, Long leadMatchId, Map<String, String> sourceIdMap, RoutingCandidate candidate) throws IOException {
        String url = candidate.getIo().getTargetUrl();

        if (url == null) {
            url = "https://api.five9.com/web2campaign/AddToList";
        }

        Lead lead = publisherContext.getLead();

        // number1
        String phone = lead.getAttributeValue("cellPhone");
        if (phone == null || "".equals(phone)) {
            // mobile wins over not mobile
            phone = lead.getAttributeValue("landPhone");
        }
        if (phone == null || "".equals(phone)) {
            phone = lead.getAttributeValue("mobile_phone");
        }
        if (phone == null || "".equals(phone)) {
            phone = lead.getAttributeValue("phone");
        }
        if (phone == null) {
            phone = lead.getAttributeValue("phone_number");
        }

        if (phone == null) {
            return Disposition.createPost(Disposition.Status.REJECTED, null, null, "no valid phone number provided");
        }

        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        formParams.add(new BasicNameValuePair("number1", phone));

        // custom field for Five9: what field has our lead reference
        final String crmColumn = sourceIdMap.get("crm_column");
        if (crmColumn != null) {
            String crmLeadUrl = buildCrmLeadUrl(leadMatchId, sourceIdMap);
            formParams.add(new BasicNameValuePair(crmColumn, crmLeadUrl));

            // This will make <crmColumn> the primary field for the data being posted.
            // If you re-post new data with same <crmColumn> value, the original record will get updated.
            formParams.add(new BasicNameValuePair("F9key", crmColumn));
        }

        // Five9 identifiers
        formParams.add(new BasicNameValuePair("F9domain", sourceIdMap.get("F9domain")));
        formParams.add(new BasicNameValuePair("F9list", sourceIdMap.get("F9list")));
        formParams.add(new BasicNameValuePair("F9retResults", "1"));
        // optional: formParams.add(new BasicNameValuePair("F9CallASAP", "1"));

        // first_name
        String firstName = lead.getAttributeValue("fname");
        if (firstName == null) {
            firstName = lead.getAttributeValue("firstName");
        }
        if (firstName == null) {
            firstName = lead.getAttributeValue("first_name", "");
        }
        formParams.add(new BasicNameValuePair("first_name", firstName));

        // last_name
        String lastName = lead.getAttributeValue("lname");
        if (lastName == null) {
            lastName = lead.getAttributeValue("lastName");
        }
        if (lastName == null) {
            lastName = lead.getAttributeValue("last_name", "");
        }
        formParams.add(new BasicNameValuePair("last_name", lastName));

        // number2
        if (lead.getAttributeValue("landPhone") != null) {
            formParams.add(new BasicNameValuePair("number2", lead.getAttributeValue("landPhone")));
        }

        // State
        formParams.add(new BasicNameValuePair("state", lead.getAttributeValue("state", "")));

        // city
        formParams.add(new BasicNameValuePair("city", lead.getAttributeValue("city", "")));

        // Street
        formParams.add(new BasicNameValuePair("street", lead.getAttributeValue("address", "")));

        // Zip
        String zip = lead.getAttributeValue("zip");
        if (zip == null) {
            zip = lead.getAttributeValue("zipcode");
        }
        if (zip == null) {
            zip = lead.getAttributeValue("zip_code", "");
        }
        formParams.add(new BasicNameValuePair("zip", zip));

        Long msgId = logPostMessage(leadMatchId, url, formParams);

        String[] lines = httpPostWithStringResponse(url, formParams);
        if (lines == null || lines.length == 0) {
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to parse post response");
        }

        if (msgId != null) {
            // record the raw response
            logResponseMessage(msgId, lines);
        }

        // parse response
        for (String line : lines) {
            // do we have a success?
            if (!"".equals(line.trim())) {

                // <INPUT readonly id="F9errCode" name="F9errCode" value="0" size="10">
                int pos = line.indexOf("id=\"F9errCode\"");
                if (pos >= 0) {
                    // look for the closing tag
                    int endPos = line.indexOf(">", pos + 1);
                    int successPos = line.indexOf("value=\"0\"", pos + 1);

                    if (successPos < 0 || successPos > endPos) {
                        return Disposition.createPost(Disposition.Status.REJECTED, null, null, "F9Error > 0");
                    }
                }
            }
        }
        return Disposition.createPost(Disposition.Status.ACCEPTED, null, null, "F9 accepted");
    }

    private String buildCrmLeadUrl(Long leadMatchId, Map<String, String> sourceIdMap) {
        String crmURL = sourceIdMap.get("crm_url");
        if (crmURL == null) {
            // in case the root url is directly configured in Five9, we just send the unique lead id (and the final CRM url will be assembled inside of Five9)
            return leadMatchId.toString();
        }

        if (crmURL.contains("lpCRM")) {
            return crmURL + (crmURL.endsWith("/") ? "" : "/") + leadMatchId;
        }

        return crmURL + "/lpCRM/cc/" + leadMatchId;
    }
}
