package com.pony.advertiser.writers.solar;

import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.advertiser.writers.EmailWriter;
import com.pony.core.PonyPhase;
import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.UserProfile;
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
 * Created by martin on 3/24/16.
 */
public class CleanEnergyExperts extends AdvertiserWriter {
	private static final Log LOG = LogFactory.getLog(CleanEnergyExperts.class);
	
    /*
    -- DB changes for this client:
    insert into advertisers (name, created_at) values('CleanEnergyExperts', now());
    insert into advertiser_writers (lead_type_id,advertiser_id,class_name,created_at) values( -1 , (select id from advertisers where name ='CleanEnergyExperts'), 'com.pony.advertiser.writers.solar.CleanEnergyExperts', now());
    insert into orders (code, advertiser_id, lead_type_id, source_id, target_url, status, created_at) values('CleanEnergyExperts:Solar', (select id from advertisers where name ='CleanEnergyExperts'), -1, 'campid=test', 'http://receiver.ceeleads.info/leads/post2', 1, now());
    insert into publisher_list_orders (name, publisher_list_id, order_id, status, created_at) values('CEE:SolarFullForm-Link', (select id from publisher_lists where ext_list_id = 'solar_full_form'), (select id from orders where code = 'CleanEnergyExperts:Solar'), 1, now());
     */

    public CleanEnergyExperts() {
        super();
    }

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {

//        To test posting leads:
//        Use "test" as first name and "tester" as last name. Note, each test needs a unique email to be accepted (e.g. test1@none.com, test2@none.com, etc)

        /*
        campid -> Assigned Campaign ID

        first_name	[text]	25	First Name
        last_name	[text]	25	Last Name
        street	[text]	40	Street Address
        city	[text]	39	City
        state	[text]	2	State (abbreviation, e.g. CA, WA, AZ)
        zip	#####	8	Zip Code (5-digits for US)
        email	[valid email]	60	Email Address
        phone_home	##########	10	Phone (10 digits, no spaces)
        property_ownership	OWN
        RENT	12	Homeowner?
        electric_bill	$0-50
        $51-100
        $101-150
        $151-200
        $201-300
        $301-400
        $401-500
        $501-600
        $601-700
        $701-800
        $801+	20	Average Monthly Electric Bill for residential
        electricUtilityProviderText	[text]	Unlimited	Name of Electric Utility Company
        roof_shade	No Shade
        A Little Shade
        A Lot Of Shade
        Uncertain	30	Approximate shading of roof
        solar_electric	TRUE
        FALSE	-	If you are generating residential solar leads then solar_electric=TRUE
        ip_address	[text]	45	IP Address
        ___pageid___	[string]	-	Page ID
        universal_leadid	[text]	-	Valid LeadiD (www.leadid.com) associated with this lead
         */

        // example post: campid=ABC123&ip_address=192.168.100.100&first_name=TEST&last_name=TESTER&street=123+main+st&city=Chicago&state=IL&zip=92660&email=johndoe@mail.com&phone_home=9493667745&electric_bill=$151-200&roof_shade=No%20Shade&property_ownership=OWN
        // example response: {"leadId":"0968B3CC","status":"POST_VALID"}
        //                   {"leadId":null,"status":"INVALID_CAMPAIGN"}
        //                   {"leadId":null,"status":"POST_REJECT"}

        Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());

        Lead lead = publisherContext.getLead();

        String campaignId = map.get("campid");

        // test: send email
        if (campaignId == null && map.get("test") != null) {
            EmailWriter writer = new EmailWriter();
            return writer.post(leadMatchId, publisherContext, validationResponse, candidate);
        }

        // post url : http://receiver.ceeleads.info/leads/post2
        String url = candidate.getIo().getTargetUrl();

        String xxTrustedFormCertUrl = lead.getAttributeValue("xxTrustedFormCertUrl");
        if (xxTrustedFormCertUrl == null) {
            // can't send without a trusted form cert url (see http://trustedform.com/)
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "No xxTrustedFormCertUrl provided");
        }

        Arrival arrival = publisherContext.getArrival();
        UserProfile up = publisherContext.getUserProfile();
        String code = candidate.getIo().getCode();

        List<NameValuePair> formParams = new ArrayList<NameValuePair>();

        // write out the tokenized sourceId
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        formParams.add(new BasicNameValuePair("xxTrustedFormCertUrl", xxTrustedFormCertUrl));

        // Enter your affiliate/SubID code here to track
        if (code != null) {
            formParams.add(new BasicNameValuePair("subid", code));
        }

        String ip = lead.getAttributeValue("ip");
        if (ip == null) {
            ip = arrival.getIpAddress();
        }
        formParams.add(new BasicNameValuePair("ip_address", ip));

        // lead.getAttributeValue("make");
//        first_name	[text]	25	First Name
        formParams.add(new BasicNameValuePair("first_name", lead.getAttributeValue("first_name")));

//        last_name	[text]	25	Last Name
        formParams.add(new BasicNameValuePair("last_name", lead.getAttributeValue("last_name")));

//        street	[text]	40	Street Address
        formParams.add(new BasicNameValuePair("street", lead.getAttributeValue("street")));

//        city	[text]	39	City
        formParams.add(new BasicNameValuePair("city", lead.getAttributeValue("city")));

//        state	[text]	2	State (abbreviation, e.g. CA, WA, AZ)
        formParams.add(new BasicNameValuePair("state", lead.getAttributeValue("state")));

//        zip	#####	8	Zip Code (5-digits for US)
        formParams.add(new BasicNameValuePair("zip", lead.getAttributeValue("zip")));

//        email	[valid email]	60	Email Address
        formParams.add(new BasicNameValuePair("email", up.getEmail()));

//        phone_home	##########	10	Phone (10 digits, no spaces)
        formParams.add(new BasicNameValuePair("phone_home", lead.getAttributeValue("phone_home")));

//        property_ownership	OWN | RENT
        formParams.add(new BasicNameValuePair("property_ownership", lead.getAttributeValue("property_ownership")));

//        electric_bill	$0-50
        formParams.add(new BasicNameValuePair("electric_bill", lead.getAttributeValue("electric_bill")));

//        electricUtilityProviderText	[text]	Unlimited	Name of Electric Utility Company
        formParams.add(new BasicNameValuePair("electricUtilityProviderText", lead.getAttributeValue("electric_company")));

//        roof_shade	// default
        formParams.add(new BasicNameValuePair("roof_shade", "Uncertain"));

//        solar_electric	TRUE
//        FALSE	-	If you are generating residential solar leads then solar_electric=TRUE
        formParams.add(new BasicNameValuePair("solar_electric", "TRUE"));

//        ?? TODO ___pageid___	[string]	-	Page ID
//        formParams.add(new BasicNameValuePair("___pageid___", "___pageid___"));

//        universal_leadid	[text]	-	Valid LeadiD (www.leadid.com) associated with this lead
        formParams.add(new BasicNameValuePair("universal_leadid", lead.getAttributeValue("leadid_token")));

        Long msgId = logPostMessage(leadMatchId, url, formParams);

        if (map.containsKey("is_test") || ("Mickey".equalsIgnoreCase(lead.getAttributeValue("first_name")) && "Mouse".equalsIgnoreCase(lead.getAttributeValue("last_name")))) {
            LOG.info("CEE Solar: bailing test call; d=" + candidate.getDisposition());
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "test call");
        }

        try {
            String[] lines = httpPostWithStringResponse(url, formParams);
            if (lines == null || lines.length == 0) {
                return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "unable to parse post response");
            }

            if (msgId != null) {
                // record the raw response
                logResponseMessage(msgId, lines);
            }

            // example response: {"leadId":"0968B3CC","status":"POST_VALID"}
            //                   {"leadId":null,"status":"INVALID_CAMPAIGN"}
            //                   {"leadId":null,"status":"POST_REJECT"}
            String leadId = null, status = null;
            for (String line : lines) {
                if (line.trim().startsWith("{")) {
                    line = line.replace("\"", "").replace("'", "");
                    String[] t1 = line.split(",");
                    if (t1.length == 2) {
                        for (String token : t1) {
                            String[] labels = token.split(":");
                            if (labels.length == 2) {
                                if ("leadid".equals(labels[0].toLowerCase())) {
                                    leadId = labels[1];
                                }
                                else if ("status".equals(labels[0].toLowerCase())) {
                                    status = labels[1];
                                }
                                else {
                                    // unknown
                                }
                            }
                        }
                    }

                }
            }

            if (status != null) {
                if ("null".equals(leadId)) {
                    leadId = null;
                }

                if ("POST_VALID".equals(status)) {
                    return Disposition.createPost(Disposition.Status.ACCEPTED, null, leadId, null, null);
                }

                return Disposition.createPost(Disposition.Status.REJECTED, null, null, status);
            }
        }
        catch (IOException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }


        return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, "response mismatch");
    }
}
