package com.pony.models;

import com.pony.advertiser.OfferSplit;
import com.pony.core.TestContext;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * OfferSplits are used to A|B test Creatives and (smtp)Hosts in the context of a specific Offer.
 * (what host sends what creative for this offer)
 * PonyLeads 2012
 * User: martin
 * Date: 7/3/12
 * Time: 3:08 PM
 */
public class OfferSplitModel extends Model {
	private static final Log LOG = LogFactory.getLog(OfferSplitModel.class);
	
    protected OfferSplitModel(Long id) {
        super(id);
    }

    public static OfferSplit determineSplit(PublisherContext context, Long listSplitId) throws NamingException, SQLException {

        // tests provide the creative and host id (and optionally the offer id) to force those
        // Note: the offer id of the creative overwrites the offer id provided as parameter!
        // Also: there is no check if the provided values are valid!
        if (context.isTest()) {
            TestContext tc = context.getTestContext();
            if (tc.getCreativeId() != null && tc.getHostId() != null) {
                return OfferSplit.create(listSplitId, tc.getHostId(), tc.getCreativeId());
            }
        }
        else if (listSplitId == null) {
            return null;
        }

        Connection con = null;
        PreparedStatement stmt = null;

        int totalPercentage = 0;
        List<Long> splitIds = new ArrayList<Long>();
        Map<Long, Long[]> splitHostCreative = new HashMap<Long, Long[]>();

        try {
            // build a list of offer ids, each offer id is represented in a ratio based on the percentage specified
            // then make a random pick

//            Map<Long, Boolean> hosts = new HashMap<Long, Boolean>();

            con = connectX();
            stmt = con.prepareStatement("select id, creative_id, host_id, percentage from offer_splits where list_split_id = ? and status = 1");
            stmt.setLong(1, listSplitId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                //TODO: check host's max daily sends: add cache so this doesn't happen for each incoming message!
//                Boolean isValid = hosts.get(rs.getLong("host_id"));
//                if(isValid == null){
//                    // count messages for this host today and add results to list
//                    Host host = HostModel.find(rs.getLong("host_id"));
//                    if(host != null ){
//                        // count messages
//                        long todaysMessages = MessageModel.countForHostToday(host.getId());
//                        if(todaysMessages >= host.getMaxSendsDaily()){
//                            hosts.put(host.getId(), false);
//                        }
//                        else{
//                            hosts.put(host.getId(), true);
//                        }
//                    }
//                }
//                else if(!isValid.booleanValue()){
//                    continue;
//                }

                totalPercentage += rs.getInt("percentage");

                Long[] hostCreative = new Long[2];
                hostCreative[0] = rs.getLong("host_id");
                hostCreative[1] = rs.getLong("creative_id");
                splitHostCreative.put(rs.getLong("id"), hostCreative);

                //TODO: look at list splits
                for (int i = 0; i < rs.getInt("percentage"); i++) {
                    splitIds.add(rs.getLong("id"));
                }
            }

            int pos = (new Double(Math.random() * totalPercentage).intValue());
            if (pos > totalPercentage) {
                pos = totalPercentage;
            }

            Long splitId = splitIds.get(pos);

            Long[] hostCreative = splitHostCreative.get(splitId);

            return OfferSplit.create(listSplitId, hostCreative[0], hostCreative[1]);
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    /**
     * Create a randomized list of offer splits, weighted by the percentage defined for each split
     *
     * @param context
     * @param listSplitId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static List<OfferSplit> determineSplits(IPublisherContext context, Long listSplitId) throws NamingException, SQLException {

        List<OfferSplit> splits = new LinkedList<OfferSplit>();

        // tests provide the creative and host id (and optionally the offer id) to force those
        // Note: the offer id of the creative overwrites the offer id provided as parameter!
        // Also: there is no check if the provided values are valid!
        if (context.isTest()) {
            TestContext tc = context.getTestContext();
            if (tc.getCreativeId() != null && tc.getHostId() != null) {
                splits.add(OfferSplit.create(listSplitId, tc.getHostId(), tc.getCreativeId()));

                return splits;
            }
        }
        else if (listSplitId == null) {
            return splits;
        }

        Connection con = null;
        PreparedStatement stmt = null;

        int totalPercentage = 0;
        List<Long> splitIds = new ArrayList<Long>();
        Map<Long, Long[]> splitHostCreative = new HashMap<Long, Long[]>();

        try {
            // build a list of offer ids, each offer id is represented in a ratio based on the percentage specified
            // then make a random pick

//            Map<Long, Boolean> hosts = new HashMap<Long, Boolean>();

            con = connectX();
            stmt = con.prepareStatement("select id, creative_id, host_id, percentage from offer_splits where list_split_id = ? and status = 1");
            stmt.setLong(1, listSplitId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                totalPercentage += rs.getInt("percentage");

                Long[] hostCreative = new Long[2];
                hostCreative[0] = rs.getLong("host_id");
                hostCreative[1] = rs.getLong("creative_id");
                splitHostCreative.put(rs.getLong("id"), hostCreative);

                //TODO: look at list splits
                for (int i = 0; i < rs.getInt("percentage"); i++) {
                    splitIds.add(rs.getLong("id"));
                }
            }

            if (splitHostCreative.size() == 1) {
                Long splitId = splitIds.get(0);
                Long[] hostCreative = splitHostCreative.get(splitId);

                splits.add(OfferSplit.create(listSplitId, hostCreative[0], hostCreative[1]));
            }
            else {
                for (int i = 0; i < splitHostCreative.size(); i++) {
                    int pos = (new Double(Math.random() * totalPercentage).intValue());
                    if (pos > totalPercentage) {
                        pos = totalPercentage;
                    }

                    Long splitId = splitIds.get(pos);
                    Long[] hostCreative = splitHostCreative.get(splitId);
                    splits.add(OfferSplit.create(listSplitId, hostCreative[0], hostCreative[1]));

                    // now adjust and remove all occurrences of that splitId so we can make another random choice amongst the remaining splits
                    int before = splitIds.size();
                    List<Long> filter = new ArrayList<Long>();
                    filter.add(splitId);
                    splitIds.removeAll(filter);
                    totalPercentage -= (before - splitIds.size());
                }
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return splits;
    }

    /**
     * find the next offer split that is for the same offer and optionally host
     *
     * @param offerId the offer that we want a creative for
     * @param hostId  the host that was used (and hence the one we want to use again)
     * @return
     */
    public static OfferSplit determineSplit(Long offerId, Long hostId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select os.id, os.creative_id, os.host_id, os.percentage , os.list_split_id from offer_splits os join list_splits ls on ls.id = os.list_split_id where os.host_id = ? and ls.offer_id = ? and os.status = 1 order by rand(os.creative_id) limit 1");
            stmt.setLong(1, hostId);
            stmt.setLong(2, offerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //TODO: check host's max daily sends: add cache so this doesn't happen for each incoming message!
                return OfferSplit.create(rs.getLong("list_split_id"), hostId, rs.getLong("creative_id"));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }


    public static OfferSplit determineSplit(Long offerId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = connectX();
            stmt = con.prepareStatement("select os.id, os.creative_id, os.host_id, os.percentage , os.list_split_id from offer_splits os join list_splits ls on ls.id = os.list_split_id where ls.offer_id = ? and os.status = 1 order by rand() limit 1");
            stmt.setLong(1, offerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //TODO: check host's max daily sends: add cache so this doesn't happen for each incoming message!
                return OfferSplit.create(rs.getLong("list_split_id"), rs.getLong("host_id"), rs.getLong("creative_id"));
            }
        }
        finally {
            close(stmt);
            close(con);
        }

        return null;
    }

    public static void main(String[] args) {
        // test splits

        List<Long> splitIds = new ArrayList<Long>();
        Map<Long, Long[]> splitHostCreative = new HashMap<Long, Long[]>();


        int[] percentages = new int[]{25, 25, 50};
        Long[] hostIds = new Long[]{1L, 2L, 3L};
        Long[] creativeIds = new Long[]{10L, 20L, 30L};
        int totalPercentage = 0;

        for (int i = 0; i < percentages.length; i++) {
            totalPercentage += percentages[i];

            Long[] hostCreative = new Long[2];
            hostCreative[0] = hostIds[i];
            hostCreative[1] = creativeIds[i];
            splitHostCreative.put(Long.valueOf(i), hostCreative);

            for (int s = 0; s < percentages[i]; s++) {
                splitIds.add(Long.valueOf(i));
            }
        }


        for (int i = 0; i < splitHostCreative.size(); i++) {

            int pos = (new Double(Math.random() * totalPercentage).intValue());
            if (pos > totalPercentage) {
                pos = totalPercentage;
            }

            int before = splitIds.size();
            Long splitId = splitIds.get(pos);
            List<Long> filter = new ArrayList<Long>();
            filter.add(splitId);
            splitIds.removeAll(filter);
            totalPercentage -= (before - splitIds.size());
            Long[] hostCreative = splitHostCreative.get(splitId);

            LOG.debug("host=" + hostCreative[0] + " : creative=" + hostCreative[1]);
        }
    }
}
