package com.pony.publisher;

import com.pony.lead.Arrival;
import com.pony.lead.LeadType;
import com.pony.models.PublisherListMemberModel;
import com.pony.models.PublisherListModel;
import com.pony.models.PublisherModel;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * In the factory methods, the information to tie the request to a Publisher and PublisherList are extracted and then
 * this class becomes the DTO carrying that information around the application. The Publisher is the organization that
 * collected the lead (presumably, it means "content publisher"), and the publisher list ties the publisher to the
 * collection of orders that are filled by a particular form on the publisher. This information, therefore, ties
 * together the source of the information in a request for use in filling selling the lead, including an estimated cost-
 * per-lead expected from the combination of Publisher and PublisherList. I do not believe that this is related to VPL,
 * value-per-lead, that is a part of the order. I believe that it is essentially an "expected revenue for an incoming
 * lead" and can be used in cost accounting to estimate expected accounts receivable as a function of number of leads
 * coming in, to be adjusted at the end of the quarter to actuals. Value-per-lead is an expectation of the price to be
 * received from an order, and is useful during negotiation or in the algorithm for the submission order to advertisers.
 * 
 * This class authorizes the received request according to either a username/password pair submitted in the request, or
 * through the use of a domain token that is hashed in our database but is submitted in plain-text. I am not sure how
 * much authentication this provides, as anyone can scrape the publisher's form and submit the referrer and domain token
 * found there and "authenticate" as the content publisher. I wonder if this was supposed to be added to the form posts
 * on the server-side, in Lynx.
 * 
 * @author Martin, updated by Jonathan Card
 */
public class PublisherChannel {
	private static final Log LOG = LogFactory.getLog(PublisherChannel.class);
    public static final String LIST_ID = "listid";

    private final Publisher publisher;
    private final BigDecimal costPerLead;
    private final PublisherList publisherList;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String DOMAIN_TOKEN = "domtok";

    private PublisherChannel(Publisher publisher, PublisherList publisherList, BigDecimal costPerLead) {
        this.publisher = publisher;
        this.publisherList = publisherList;
        this.costPerLead = costPerLead;
    }

    /**
     * get the 'listid', 'username', and 'password' provided as request parameters and validate the publisher_list exists (looked up by external id),
     * and that the publisher is a valid one (check by username and password). Also validate that the publisher is allowed
     * to post to this list (via the publisher_list_member)
     *
     * @param request the http request
     * @return the parsed information including the publisher, publisher list, and the cpl (if any) the publisher is entitled to for this post.
     * @throws NamingException
     * @throws SQLException
     */
    public static PublisherChannel parse(HttpServletRequest request) throws NamingException, SQLException {
        // path is ?username=leadkarma&password=doublenickels&listid=12346

        // findByEmail the publisher and check if the listid is on the list of allowed lists to post to for this publisher
        String channelId = request.getParameter(LIST_ID);
        if (channelId == null) {
            channelId = request.getHeader(LIST_ID);
        }

        if (channelId == null) {
            throw new IllegalArgumentException("No value provided for [" + LIST_ID + "]");
        }

        // check if the list exists
        PublisherList pl = PublisherListModel.findByExternalId(channelId);
        if (pl == null) {
            throw new IllegalArgumentException("Not a valid " + LIST_ID + "[" + channelId + "]");
        }

        // read the publisher
        Publisher publisher = null;

        // username and password provided?
        String userName = request.getParameter(USERNAME);
        String password = request.getParameter(PASSWORD);

        LOG.debug("User: " + userName);
        LOG.debug("Password: " + password);
        // if no user name provided, can we validate the publisher by sending domain and unique token?
        if (userName == null || password == null) {
            String referrer = null, domain = null;
            String domainToken = request.getParameter(DOMAIN_TOKEN);
            LOG.debug("domain token: " + domainToken);
            if (domainToken != null) {
                referrer = request.getHeader("referer");
                if (referrer == null) {
                    referrer = request.getParameter("ref");
                }
                LOG.debug("referrer: " + referrer);
                domain = Arrival.parseDomain(referrer);
                LOG.debug("domain: " + domain);
            }

            if (referrer == null || domain == null) {
                throw new IllegalArgumentException("No value provided for [" + domainToken + "] and/or domain: " + domain);
            }

            publisher = PublisherModel.findByDomainAndToken(domain, domainToken);
            LOG.debug("Found? " + (publisher != null ? "yes" : "no"));
        }

        if (publisher == null && (userName == null || password == null || userName.equals(""))) {
            throw new IllegalArgumentException("No value provided for [" + USERNAME + "] and [" + PASSWORD + "]");
        }

        if (publisher == null) {
            // user/pwd was provided: check if the publisher is authenticated
            publisher = PublisherModel.findByNameAndPwd(userName, password);
            if (publisher == null) {
                throw new IllegalArgumentException("Publisher not authorized for this list");
            }
        }

        // is the publisher allowed for this list?

        LOG.debug("Test? " + publisher.isTest());
        if (publisher.isTest()) {
            // Note: if it's the test publisher, we don't check any list membership!
            return new PublisherChannel(publisher, pl, BigDecimal.ZERO);
        }
        else {
        	LOG.debug("Finding member by " + publisher.getId() + ", " + pl.getId());
            PublisherListMember member = PublisherListMemberModel.findByPublisherAndList(publisher.getId(), pl.getId());
            LOG.debug("Member? " + (member != null ? "yes" : "no"));
            if (member != null) {
                return new PublisherChannel(publisher, pl, member.getCpl());
            }
        }

        LOG.warn("Invalid publisher channel provided:" + channelId);

        return null;
    }

    public static PublisherChannel create(Publisher publisher, PublisherList publisherList, HttpServletRequest request)
            throws NamingException, SQLException {

        if (publisher.isTest()) {
            // Note: if it's the test publisher, we don't check any list membership!
            return new PublisherChannel(publisher, publisherList, BigDecimal.ZERO);
        }
        else {
            PublisherListMember member = PublisherListMemberModel.findByPublisherAndList(publisher.getId(), publisherList.getId());
            if (member != null) {
                return new PublisherChannel(publisher, publisherList, member.getCpl());
            }
        }

        LOG.warn("Invalid publisher channel provided: p=" + publisher + ": pl=" + publisherList);

        return null;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public BigDecimal getCostPerLead() {
        return costPerLead;
    }

    public String getListId() {
        return publisherList.getExternalId();
    }

    public LeadType getLeadType() {
        return publisherList.getLeadType();
    }

    public PublisherList getPublisherList() {
        return publisherList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PublisherChannel that = (PublisherChannel) o;

        if (!getListId().equals(that.getListId())) {
            return false;
        }
        if (!publisher.equals(that.publisher)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = publisher.hashCode();
        result = 31 * result + getListId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PublisherChannel{" +
                "publisher=" + publisher +
                ", costPerLead=" + costPerLead +
                ", listId='" + getListId() + '\'' +
                '}';
    }
}
