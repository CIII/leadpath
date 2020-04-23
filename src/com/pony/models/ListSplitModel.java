package com.pony.models;

import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.publisher.PublisherList;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ListSplits allow to configure A|B tests for offers used in the context of a specific list.
 * <p/>
 * PonyLeads 2012
 * User: martin
 * Date: 7/3/12
 * Time: 3:04 PM
 */
public class ListSplitModel extends Model {
	private static final Log LOG = LogFactory.getLog(ListSplitModel.class);
    private static final MathContext MC = new MathContext(7, RoundingMode.HALF_DOWN);

    protected ListSplitModel(Long id) {
        super(id);
    }

    public static Long determineSplitId(IPublisherContext context, PublisherList publisherList) throws NamingException, SQLException {

        // validate the arguments:
        assert (context != null);
        assert (context.getLeadType() != null);
        assert (context.getLeadType().getId() != null);
        assert (publisherList != null);
        assert (publisherList.getId() != null);

        // if this is a test request we're done: test contexts have a specific creativeid that points back to the offerid
        if (context.isTest()) {
            return null;
        }

        // read the list_splits and their percentages for this pl, and try to pick one
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            // build a list of offer ids, each offer id is represented in a ratio based on the percentage specified
            // then make a random pick
            con = connectX();
            stmt = con.prepareStatement("select ls.id split_id, ls.offer_id, ls.percentage from list_splits ls join offers o on o.id = ls.offer_id where ls.publisher_list_id = ? and ls.status = 1 and o.status = 1");
            stmt.setLong(1, publisherList.getId());
            ResultSet rs = stmt.executeQuery();
            Map<Long, BigDecimal> percentages = new HashMap<Long, BigDecimal>();
            int totalPercentage = 0;
            int minPercentage = 100;
            while (rs.next()) {
                BigDecimal percentage = rs.getBigDecimal("percentage");

                // adjust to 1% if it's set as 0.01
                if (percentage.doubleValue() < 1.0d) {
                    percentage = percentage.multiply(new BigDecimal(100d), MC);
                }
                percentages.put(rs.getLong("split_id"), percentage);
                int i = percentage.intValue();
                totalPercentage += i;
                if (i < minPercentage) {
                    minPercentage = i;
                }
            }
            close(stmt);

            return randomSplitId(percentages, minPercentage);
        }
        finally {
            close(stmt);
            close(con);
        }
    }

    private static Long randomSplitId(Map<Long, BigDecimal> percentages, int minPercentage) {
        // if there is only one offer
        if (percentages.size() == 0) {
            return null;
        }

        if (percentages.size() == 1) {
            return percentages.entrySet().iterator().next().getKey();
        }

        // otherwise make a random pick, based on the percentages
        BigDecimal min = new BigDecimal(minPercentage);

        List<Long> splitIds = new ArrayList<Long>();
        for (Map.Entry<Long, BigDecimal> entry : percentages.entrySet()) {
            int factor = entry.getValue().divide(min, MC).intValueExact();
            if (factor <= 1) {
                splitIds.add(entry.getKey());
            }
            else {
                for (int i = 0; i < factor; i++) {
                    splitIds.add(entry.getKey());
                }
            }
        }

        int pos = (new Double(Math.random() * splitIds.size()).intValue());
        if (pos >= splitIds.size()) {
            pos = splitIds.size() - 1;
        }

        return splitIds.get(pos);
    }

    // test harness
    public static void main(String[] args) {
        Map<Long, BigDecimal> percentages = new HashMap<Long, BigDecimal>();
        int totalPercentage = 0;
        int minPercentage = 100;

        percentages.put(1L, new BigDecimal(90));
        percentages.put(3L, new BigDecimal(30));
        percentages.put(2L, new BigDecimal(5));

        // run it 100 times and check if the distribution is close
        // initialize counts (per offer id) et al
        Map<Long, Integer> counts = new HashMap<Long, Integer>();
        for (Map.Entry<Long, BigDecimal> entry : percentages.entrySet()) {
            counts.put(entry.getKey(), 0);
            int value = entry.getValue().intValue();
            totalPercentage += value;
            if (value < minPercentage) {
                minPercentage = value;
            }
        }

        for (int i = 0; i < totalPercentage; i++) {
            Long offerId = randomSplitId(percentages, minPercentage);
            int c = counts.get(offerId);
            c++;
            counts.put(offerId, c);
        }

        LOG.debug("results: total=" + totalPercentage + "; min=" + minPercentage);
        for (Map.Entry<Long, Integer> entry : counts.entrySet()) {
            LOG.debug("" + entry.getKey() + "=" + entry.getValue());
        }
    }
}
