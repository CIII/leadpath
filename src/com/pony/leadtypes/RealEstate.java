package com.pony.leadtypes;

import com.pony.lead.Arrival;
import com.pony.lead.Lead;
import com.pony.lead.LeadType;
import com.pony.lead.UserProfile;
import com.pony.models.AttributeModel;
import com.pony.models.LeadModel;
import com.pony.models.UserProfileModelImpl;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.validation.ValidationException;
import com.pony.validation.ValidationResponse;
import com.pony.validation.ValidationScore;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * ArbVentures 2014.
 * test string:
 * http://localhost:8080/post?email=martinholzner@gmail.com&listid=hff_2_score&domtok=homeAtLast&ref=homefindify.com&home_budget=250000.00&time_range=Just%20browsing&target_zip_code_1=0000&target_distance_1=25&xl_bag=fooofoofofofoofo
 * <p/>
 * User: martin
 * Date: 9/18/14
 * Time: 4:21 PM
 */
public class RealEstate extends LeadType {
	private static final Log LOG = LogFactory.getLog(LeadType.class);
	
    private static final Map<String, Integer> COMMUNITY_INDEX = new HashMap<String, Integer>();

/*

* list details
listid=hff_2_score
domtok=homeAtLast
ref=homefindify.com

 * lead details
first_name: Diane
last_name: Hunter
email: QtArmyMom@yahoo.com
zip_code: 80501
phone_number: 720-329-1605
home_budget: Up to $250,000
time_range: Just browsing
created_at: 2014-07-28 16:20:04 UTC

?? should we use the target area name here instead of # ?
target_zip_code_1=00000
target_distance_1=25

target_zip_code_2=00000
target_distance_2=45

xl_bag:
* AGGREGATE STATS FOR VIEWED LISTINGS *
* *****************************************
* Prices: $103,000.00 to $350,000.00
* Beds: 2 to 5
* Baths: 1.0 to 4.0
* Number of homes viewed by city:
* Firestone, CO 13
* Frederick, CO 4
* *****************************************
* Keyword: firestone-co
* Search: firestone-co
* Search: firestone-co
* -------------------------------------------
* Address: 6352 Sage Ave, Firestone, CO, 80504
* Family Type: Single-Family
* Home Price: $315,000.00
* Beds: 4 Baths: 4.0
* -------------------------------------------
* Address: 5302 Buttsfield St, Firestone, CO, 80504
* Family Type: Single-Family
* Home Price: $265,000.00
* Beds: 3
* Baths: 2.0
* ....
 */

    private static final Map<String, String> LABEL_MAP = new HashMap<String, String>();

    static {
        EXCLUDED_ATTRIBUTES.add("ref");
        EXCLUDED_ATTRIBUTES.add("domtok");
        EXCLUDED_ATTRIBUTES.add("username");
        EXCLUDED_ATTRIBUTES.add("password");
        EXCLUDED_ATTRIBUTES.add("email");
        EXCLUDED_ATTRIBUTES.add("listid");
        EXCLUDED_ATTRIBUTES.add("WAIT_FOR_RESPONSE");
        EXCLUDED_ATTRIBUTES.add("lead_ref");
        EXCLUDED_ATTRIBUTES.add("arrivalid");
        EXCLUDED_ATTRIBUTES.add("asid");

        COMMUNITY_INDEX.put("Terrain", 1);
        COMMUNITY_INDEX.put("Leyden Rock", 2);
        COMMUNITY_INDEX.put("Platt Park North", 3);

        LABEL_MAP.put("first_name", "First Name");
        LABEL_MAP.put("last_name", "Last Name");
        LABEL_MAP.put("phone_number", "Phone");
        LABEL_MAP.put("zip_code", "Zip");
        LABEL_MAP.put("created_at", "Created");

        LABEL_MAP.put("time_range", "Time To Buy");
        LABEL_MAP.put("home_budget", "Budget");

        LABEL_MAP.put("target_zip_code_1", "Terrain Zip");
        LABEL_MAP.put("target_distance_1", "Distance from Terrain");

        LABEL_MAP.put("target_zip_code_2", "Leyden Rock Zip");
        LABEL_MAP.put("target_distance_2", "Distance from Leyden Rock");

        LABEL_MAP.put("target_zip_code_3", "Platt Park North Zip");
        LABEL_MAP.put("target_distance_3", "Distance from Platt Park North");

        LABEL_MAP.put("xl_bag", "Full Record Data");
    }

    protected RealEstate(Long id) {
        super(id);
    }

    public static LeadType create(Long id) {
        return new RealEstate(id);
    }

    public String toString() {
        return "LeadType:RealEstate(" + getId() + ")";
    }

    @Override
    public ValidationResponse validate(IPublisherContext publisherContext) throws ValidationException {
        LOG.debug("ctx=" + publisherContext);

        if (!(publisherContext.getLeadType() instanceof RealEstate)) {
            return ValidationResponse.WRONG_TYPE;
        }

        // if this is a poll for a previous ping request, we just need to check the leadid is present
        if (publisherContext.isPoll()) {
            return publisherContext.getLeadId() == null ? ValidationResponse.create(11, "No reference for poll") : ValidationResponse.NOOP;
        }

        Lead lead = publisherContext.getLead();

        if (lead == null) {
            return ValidationResponse.create(12, "No valid lead data provided");
        }

        if (publisherContext.isPost()) {
            return scoreLead(publisherContext);
        }

        return ValidationResponse.NOOP;
    }

    private ValidationResponse scoreLead(IPublisherContext publisherContext) {
        /*
        Point scale - 0-100 points
        3 criteria
        */

        ValidationResponse response = ValidationResponse.create(15, "RealEstate Scored Response", true);
        Lead lead = publisherContext.getLead();

        // TODO: validate the email address and give it a score
//        if (publisherContext.getUserProfile() != null) {
//            Long userProfileId = publisherContext.getUserProfile().getId();
//            String email = publisherContext.getUserProfile().getEmail();
//            try {
////                boolean isValidEmail = Email.isValid(userProfileId, email, publisherContext.getPublisher().getId());
//                boolean isValidAddress = AddressValidator.isValid(email, true);
//                if (isValidAddress) {
//                    Email.bounced(userProfileId);
//                    Email.knownExternalTrap(email);
//                }
//                //TODO: score that ??
//            }
//            catch (SQLException e) {
//                e.printStackTrace();
//            }
//            catch (NamingException e) {
//                e.printStackTrace();
//            }
//        }

        // for now we have three fixed targets we calculate scores for:
//        HomeBudget-Terrain
//        HomeBudget-Leyden Rock
//        HomeBudget-Platt Park North
        if (publisherContext.getParams().get("detailed_scores") != null) {
            calculateHomeBudgetScore(response, lead);
            calculateTimeHorizonScore(response, lead);
            calculateZipCodeScore(response, lead);
        }
        else {
            String community = "Terrain";
            calculateCommunityScore(response, lead, community);

            community = "Leyden Rock";
            calculateCommunityScore(response, lead, community);

            community = "Platt Park North";
            calculateCommunityScore(response, lead, community);
        }

        return response;
    }

    private void calculateCommunityScore(ValidationResponse response, Lead lead, String community) {
        final String time = lead.getAttributeValue("time_range", "n/a");
        ValidationScore thScore = TimeHorizonScore.create(time);

        final String budget = lead.getAttributeValue("home_budget", "0");
        ValidationScore hbScore = HomeBudgetScore.create(budget, community);

        // distance score
//        1 = Terrain
//        2 = Leyden
//        3 = Platt
        int index = COMMUNITY_INDEX.get(community);

        final String zip = lead.getAttributeValue("zip_code");
        final String targetCommunity = lead.getAttributeValue("target_zip_code_" + index, "n/a");
        final String targetDistance = lead.getAttributeValue("target_distance_" + index);
        final ValidationScore zdScore = ZipCodeDistanceScore.create(zip, targetCommunity, targetDistance);

        // calculate total score
        response.addScore(community, CommunityScore.create(community, thScore, hbScore, zdScore));
    }

    public static void main(String[] args) {
        final String time = "1-3 months";
//        final String time = "Just browsing";

        ValidationScore thScore = TimeHorizonScore.create(time);

        final String budget = "$400,000";
        String community = "Leyden Rock";
        ValidationScore hbScore = HomeBudgetScore.create(budget, community);

        // distance score
//        1 = Terrain
//        2 = Leyden
//        3 = Platt
        int index = COMMUNITY_INDEX.get(community);

        final String zip = "80601";
        final String targetCommunity = "80007";
        final String targetDistance = null;
        final ValidationScore zdScore = ZipCodeDistanceScore.create(zip, targetCommunity, targetDistance);

        // calculate total score
        ValidationScore score = CommunityScore.create(community, thScore, hbScore, zdScore);
        LOG.debug(score);


//        double tScore = 14.5d;
//        int maxScore = 17;
//        String community = "Terrain";
//        System.out.println(CommunityScore.create(community, tScore, maxScore));
//
//        tScore = 7.0d;
//        community = "Leyden Rock";
//        System.out.println(CommunityScore.create(community, tScore, maxScore));
//
//        tScore = 17.0d;
//        community = "Platt Park North";
//        System.out.println(CommunityScore.create(community, tScore, maxScore));
    }

    private void calculateZipCodeScore(ValidationResponse response, Lead lead) {
        final String zip = lead.getAttributeValue("zip_code");

        if (zip == null || "".equals(zip)) {
            return;
        }

        processDistanceScore(zip, response, lead, 1);
        processDistanceScore(zip, response, lead, 2);
        processDistanceScore(zip, response, lead, 3);
    }

    private void processDistanceScore(String zip, ValidationResponse response, Lead lead, int index) {
        final String targetCommunity = lead.getAttributeValue("target_zip_code_" + index);
        final String targetDistance = lead.getAttributeValue("target_distance_" + index);
        if (targetCommunity != null && !"".equals(targetCommunity) && targetDistance != null && !"".equals(targetDistance)) {
            response.addScore("ZipCodeDistance #" + index, ZipCodeDistanceScore.create(zip, targetCommunity, targetDistance));
        }
    }

    private void calculateTimeHorizonScore(ValidationResponse response, Lead lead) {
        final String time = lead.getAttributeValue("time_range");
        if (time != null && !"".equals(time)) {
            response.addScore("TimeHorizon", TimeHorizonScore.create(time));
        }
    }

    private void calculateHomeBudgetScore(ValidationResponse response, Lead lead) {
        final String budget = lead.getAttributeValue("home_budget");
        if (budget != null && !"".equals(budget)) {
            response.addScore("HomeBudget-Terrain", HomeBudgetScore.create(budget, "Terrain"));
            response.addScore("HomeBudget-Leyden Rock", HomeBudgetScore.create(budget, "Leyden Rock"));
            response.addScore("HomeBudget-Platt Park North", HomeBudgetScore.create(budget, "Platt Park North"));
        }
    }

    @Override
    public Lead parseLead(UserProfile userProfile, Arrival arrival, HttpServletRequest request, Map<String, String> params) {
        Map<String, String> attributes = parseLeadAttributes(request, params);
        return RealEstateLead.create(null, getId(), userProfile, arrival, attributes);
    }

    @Override
    public Lead parseLead(UserProfile userProfile, Arrival arrival, HttpServletRequest request) {
        return parseLead(userProfile, arrival, request, null);
    }

    @Override
    public Lead persistLead(UserProfile userProfile, Arrival arrival, Lead lead) throws SQLException, NamingException {
        if (!(lead instanceof RealEstateLead)) {
            throw new IllegalArgumentException("not a realEstate lead");
        }

        Long id = LeadModel.persist(lead);

        Lead realEstateLead = RealEstateLead.create(id, getId(), userProfile, arrival, lead.toMap());

        if (userProfile != null && userProfile.getId() != null) {
            AttributeModel.persistProfileAttributes(userProfile, lead);
        }
//        else {
//            // this must be a ping and we don't have an email yet, so store the ping values separately.
//            AttributeModel.persistPingAttributes(lead);
//        }

        return realEstateLead;
    }

    @Override
    public Lead findLead(Long leadId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select id, user_profile_id, arrival_id, lead_type_id from leads where id =?");
            stmt.setLong(1, leadId);

            Long userProfileId = null, arrivalId = null, leadTypeId;
            UserProfile up = null;
            Map<String, String> attributes = new HashMap<String, String>();

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userProfileId = rs.getLong("user_profile_id");
                arrivalId = rs.getLong("arrival_id");
                leadTypeId = rs.getLong("lead_type_id");

                if (!this.getId().equals(leadTypeId)) {
                    throw new IllegalArgumentException("leadId=" + leadId + " is not of this lead type! (" + getId() + ")");
                }
            }

            // if there is a user profile, read the profile attributes and merge them into the attribute map
            if (userProfileId != null) {
                up = UserProfileModelImpl.findStatic(userProfileId);
                if (up != null) {
                    attributes.putAll(UserProfileModelImpl.readProfileAttributesStatic(userProfileId));
                }
            }
            if (up == null) {
                // see if there are ping Attributes
                attributes.putAll(AttributeModel.readPingAttributes(leadId));
            }

            return new RealEstateLead(leadId, getId(), up, arrivalId, attributes);
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    @Override
    public String labelLookup(String attributeName) {
        String v = LABEL_MAP.get(attributeName);
        if (v != null) {
            return v;
        }
        return attributeName;
    }

    public static class HomeBudgetScore extends ValidationScore {
        private final String target;

        private HomeBudgetScore(String target, int score) {
            super(Integer.valueOf(score).doubleValue());
            this.target = target;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }

            HomeBudgetScore that = (HomeBudgetScore) o;

            return target.equals(that.target);
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + target.hashCode();
            return result;
        }

        private static ValidationScore create(String budget, String target) {
            String num = budget.replace("$", "").replace(",", "");
            BigDecimal dn = new BigDecimal(num);
            double d = dn.doubleValue();

            if (d <= 60000d) {
                return new HomeBudgetScore(target, 1);
            }

            if (d <= 100000d) {
                if ("Terrain".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 8);
                }

                if ("Leyden Rock".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 5);
                }
                if ("Platt Park North".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 3);
                }
            }
            if (d <= 250000d) {
                if ("Terrain".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 20);
                }
                if ("Leyden Rock".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 16);
                }
                if ("Platt Park North".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 12);
                }
            }
            if (d <= 400000d) {
                if ("Terrain".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 23);
                }
                if ("Leyden Rock".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 25);
                }
                if ("Platt Park North".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 18);
                }
            }
            if (d <= 600000d) {
                if ("Terrain".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 19);
                }
                if ("Leyden Rock".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 22);
                }
                if ("Platt Park North".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 25);
                }
            }
            if (d <= 800000d) {
                if ("Terrain".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 15);
                }
                if ("Leyden Rock".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 19);
                }
                if ("Platt Park North".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 20);
                }
            }
            if (d <= 1000000d) {
                if ("Terrain".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 12);
                }
                if ("Leyden Rock".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 14);
                }
                if ("Platt Park North".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 16);
                }
            }
            if (d <= 2000000d) {
                if ("Terrain".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 10);
                }
                if ("Leyden Rock".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 12);
                }
                if ("Platt Park North".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 11);
                }
            }
            if (d > 2000000d) {
                if ("Terrain".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 8);
                }
                if ("Leyden Rock".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 4);
                }
                if ("Platt Park North".equalsIgnoreCase(target)) {
                    return new HomeBudgetScore(target, 9);
                }
            }

            return new HomeBudgetScore(target, 1); //default
        }
        /*
      #1 - Home budget
      Users select this upon registration.  We're going with the prices from Leyden Rock at the moment, which is one of 4 communities we're doing for a pilot with a Colorado homebuilder.  Prices are $350s-$450s.
              Budget is represented by a series of choices in the system.  They're not saved out as numbers.

Terrain
Home budget up to             Points	Points
60k	1
100k	1
250k	8
400k	10
600k	9
800k	7
1m	5
2m	2
2m+	2

Leyden Rock
Home budget up to                	Points
60k	1
100k	1
250k	6
400k	10
600k	9
800k	7
1m	5
2m	2
2m+	2

Platt Park North
Home budget up to                	Points
60k	1
100k	1
250k	5
400k	7
600k	10
800k	9
1m	6
2m	4
2m+	2
        */

        public String toString() {
            return "HomeBudgetScore for " + target + ": " + getScore();
        }
    }

    private static class TimeHorizonScore extends ValidationScore {

        private final String timeRange;

        private TimeHorizonScore(String timeRange, double score) {
            super(score);
            this.timeRange = timeRange;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            TimeHorizonScore that = (TimeHorizonScore) o;
            return timeRange.equals(that.timeRange);
        }

        @Override
        public int hashCode() {
            return timeRange.hashCode();
        }

        private static ValidationScore create(String timeRange) {
            if ("1-3 months".equals(timeRange.toLowerCase()) ||
                    "4-6 months".equals(timeRange.toLowerCase()) ||
                    "7-9 months".equals(timeRange.toLowerCase()) ||
                    "10-12 months".equals(timeRange.toLowerCase())) {
                return new TimeHorizonScore(timeRange, 2.0);
            }
            if ("13-18 months".equals(timeRange.toLowerCase())) {
                return new TimeHorizonScore(timeRange, 1.5);
            }
            if ("18+ months".equals(timeRange.toLowerCase())) {
                return new TimeHorizonScore(timeRange, 1.0);
            }
            return new TimeHorizonScore(timeRange, 0.0);

            /*
           #2 - Time horizon
"1-3 months",
 "4-6 months",
 "7-9 months",
 "10-12 months",
 "13-18 months",
 "18+ months",
 "Just browsing"]
             */
        }

        public String toString() {
            return "TimeHorizonScore:" + getScore();
        }
    }

    private static class CommunityScore extends ValidationScore {
        private final String community;
        private final int totalScore;

        private CommunityScore(String name, double score, int totalScore) {
            super(score);
            this.community = name;
            this.totalScore = totalScore;
        }

        private static ValidationScore create(String name, ValidationScore timeHorizonScore, ValidationScore homeBudgetScore, ValidationScore distanceScore) {
            final int maxScore = 32;
            double tScore = timeHorizonScore.getScore() + homeBudgetScore.getScore() + distanceScore.getScore();
            if (timeHorizonScore.getScore() <= 0.0d) {
                tScore = tScore / 2.0;
            }
            return new CommunityScore(name, tScore, maxScore);
        }

        @Override
        public String toString() {
            MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
            BigDecimal score = new BigDecimal(getScore()).divide(new BigDecimal(totalScore), mc).multiply(new BigDecimal(100));
            return "CommunityScore for " + community + ": " + (score.setScale(0).toString()) + "/100";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            if (!super.equals(o)) {
                return false;
            }

            CommunityScore that = (CommunityScore) o;
            return community.equals(that.community);
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + community.hashCode();
            return result;
        }
    }

    private static class ZipCodeDistanceScore extends ValidationScore {
        private final String zip, target;

        public String toString() {
            return "ZipCodeDistanceScore zip=[" + zip + "] target=[" + target + "] : " + getScore();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ZipCodeDistanceScore that = (ZipCodeDistanceScore) o;

            if (!target.equals(that.target)) {
                return false;
            }
            if (!zip.equals(that.zip)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = zip.hashCode();
            result = 31 * result + target.hashCode();
            return result;
        }

        private ZipCodeDistanceScore(String zip, String target, double score) {
            super(score);
            this.zip = (zip == null) ? "" : zip;
            this.target = (target == null) ? "n/a" : target;
        }

        public static ValidationScore create(String zip, String target, String distance) {

            final ZipCodeDistanceScore defaultScore = new ZipCodeDistanceScore(zip, target, 0.5);
            if (zip == null || distance == null || "".equals(zip) || "".equals(distance)) {
                return defaultScore;
            }

            String d = distance.replace("+", "");
            int di = Integer.valueOf(d);
            if (di <= 25) {
                return new ZipCodeDistanceScore(zip, target, 5.0);
            }
            if (di <= 50) {
                return new ZipCodeDistanceScore(zip, target, 4.0);
            }
            if (di <= 100) {
                return new ZipCodeDistanceScore(zip, target, 2.0);
            }
            if (di <= 200) {
                return new ZipCodeDistanceScore(zip, target, 1.0);
            }

            return defaultScore;

            /*
           Up to X miles   Points
               25	    	5
               50		    4
               100		    2
               200		    1
               200+	    	0.5
            */
        }
    }
}
