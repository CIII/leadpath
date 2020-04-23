package com.pony.rules;

import com.pony.PonyException;
import com.pony.advertiser.*;
import com.pony.core.PonyPhase;
import com.pony.models.IoModel;
import com.pony.routing.AdvertiserPostContext;
import com.pony.routing.IAdvertiserPostContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Copyright 2011 PonyMash, LLC
 * User: Martin
 * Date: 10/30/11
 * Time: 9:32 PM
 */
public abstract class Rule {
	private static final Log LOG = LogFactory.getLog(Rule.class);
	
    public static final int SORT_ASC = 0;
    public static final int SORT_DESC = 1;
    private final String name;

    public abstract RuleResponse beforePing(PingContext pingContext, ValidationResponse validationResponse);

    public abstract RuleResponse afterPing(PingContext pingContext);

    public abstract RuleResponse beforePost(IAdvertiserPostContext postContext, ValidationResponse validationResponse) throws PonyException;

    public abstract RuleResponse afterPost(AdvertiserPostContext postContext, RoutingCandidate routingCandidate, Disposition advertiserResponse);

    protected Rule(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Rule rule = (Rule) o;

        if (!name.equals(rule.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Rule{" +
                "name='" + name + '\'' +
                '}';
    }

    /**
     * sort the provided list into a stack, so we can pop the items off and try to route them
     *
     * @param ioModels the list to sort
     * @return a Stack with the most desirable Io on top
     */
    public Stack<Io> sortOrders(List<IoModel> ioModels) {
        Stack<Io> stack = new Stack<Io>();

        // order by vpl asc
        Collections.sort(ioModels, new IoModelVplComparator());
        // then push them onto a stack in that order so that when we pop them off the stack, they are highest VPL first
        for (IoModel model : ioModels) {
            stack.push(model.getIo());
        }

        return stack;
    }

    /**
     * we are receiving a poll request after a ping request. the dispositions have been retrieved, and now the rule
     * has a chance to manipulate the list of dispositions before they are being returned.
     *
     * @param pollContext@return rule response that can contain the newly organized dispositions.
     *                           If the rule wants to be heard, it needs to return a rule response that stops the
     *                           processing #RuleResponse.stop needs to return true, and the #getResponseDispositions() method needs to return the
     *                           newly organized subset of dispositions, otherwise the changes will be ignored.
     */
    public abstract RuleResponse poll(AdvertiserPollContext pollContext);

    protected static class RoutingCandidateVplComparator implements Comparator<RoutingCandidate> {

        private final int sortOrder;

        public RoutingCandidateVplComparator(int sortOrder) {
            this.sortOrder = sortOrder;
        }

        public int compare(RoutingCandidate routingCandidate1, RoutingCandidate routingCandidate2) {
            double v1 = routingCandidate1.getIo().getVpl().doubleValue();
            double v2 = routingCandidate2.getIo().getVpl().doubleValue();

            // if we have price dispositions, they overwrite!
            final Disposition priceDisposition1 = routingCandidate1.getResponseDisposition(PonyPhase.REQUEST_PRICE);
            if (priceDisposition1 != null && priceDisposition1.getPrice() != null) {
                v1 = priceDisposition1.getPrice().doubleValue();
            }

            final Disposition priceDisposition2 = routingCandidate2.getResponseDisposition(PonyPhase.REQUEST_PRICE);
            if (priceDisposition2 != null && priceDisposition2.getPrice() != null) {
                v2 = priceDisposition2.getPrice().doubleValue();
            }

//            return new IoVplComparator(sortOrder).compare(routingCandidate.getIo(), routingCandidate1.getIo());
            return new PriceComparator(sortOrder).compare(v1, v2);
        }
    }

    protected static class IoModelVplComparator implements Comparator<IoModel> {

        // order by vpl asc
        public int compare(IoModel io, IoModel io1) {

            if (io.getIo().getVpl().equals(io1.getIo().getVpl())) {
                return 0;
            }
            else if (io.getIo().getVpl().doubleValue() > io1.getIo().getVpl().doubleValue()) {
                return 1;
            }
            return -1;
        }
    }

    protected static class PriceComparator implements Comparator<Double> {

        private final int sortOrder;

        private PriceComparator() {
            this.sortOrder = SORT_ASC; // default to ASC order
        }

        private PriceComparator(int sortOrder) {
            this.sortOrder = sortOrder;
        }

        // order by vpl asc
        public int compare(Double price, Double price1) {

            if (price.equals(price1)) {
                return 0;
            }
            else if (price > price1) {
                return (sortOrder == SORT_ASC ? 1 : -1);
            }
            return (sortOrder == SORT_ASC ? -1 : 1);
        }
    }

    protected static class IoVplComparator implements Comparator<Io> {

        private final int sortOrder;

        private IoVplComparator() {
            this.sortOrder = SORT_ASC; // default to ASC order
        }

        private IoVplComparator(int sortOrder) {
            this.sortOrder = sortOrder;
        }

        // order by vpl asc
        public int compare(Io io, Io io1) {

            if (io.getVpl().equals(io1.getVpl())) {
                return 0;
            }
            else if (io.getVpl().doubleValue() > io1.getVpl().doubleValue()) {
                return (sortOrder == SORT_ASC ? 1 : -1);
            }
            return (sortOrder == SORT_ASC ? -1 : 1);
        }
    }

    // sort list of dispositions
    protected static class DispositionPriceComparator implements Comparator<Disposition> {

        private final int sortOrder;

        protected DispositionPriceComparator() {
            this.sortOrder = SORT_DESC; // default to DESC order
        }

        protected DispositionPriceComparator(int sortOrder) {
            this.sortOrder = sortOrder;
        }

        // order by price desc
        public int compare(Disposition disposition, Disposition disposition1) {
            //
            BigDecimal p = disposition.getPrice();
            if (disposition.getBuyers().size() > 0) {
                p = new BigDecimal(0);
                for (Buyer buyer : disposition.getBuyers()) {
                    if (buyer.getPrice() != null) {
                        p = p.add(buyer.getPrice());
                    }
                }
            }

            BigDecimal p1 = disposition1.getPrice();
            if (disposition1.getBuyers().size() > 0) {
                p1 = new BigDecimal(0);
                for (Buyer buyer : disposition1.getBuyers()) {
                    if (buyer.getPrice() != null) {
                        p1 = p1.add(buyer.getPrice());
                    }
                }
            }

            if (p.equals(p1)) {
                return 0;
            }
            else if (p.doubleValue() > p1.doubleValue()) {
                return (sortOrder == SORT_ASC ? 1 : -1);
            }
            return (sortOrder == SORT_ASC ? -1 : 1);
        }
    }

    public static class BuyerNameZipComparator implements Comparator<Buyer> {

        private final int sortOrder;

        public BuyerNameZipComparator() {
            this(SORT_DESC); // default to DESC order
        }

        protected BuyerNameZipComparator(int sortOrder) {
            this.sortOrder = sortOrder;
        }

        // order by price desc
        public int compare(Buyer buyer, Buyer buyer1) {
            //
            BigDecimal p = buyer.getPrice();
            if (p == null) {
                p = BigDecimal.ZERO;
            }

            BigDecimal p1 = buyer1.getPrice();
            if (p1 == null) {
                p1 = BigDecimal.ZERO;
            }

            String z = buyer.getZipcode();
            String z1 = buyer1.getZipcode();
            if (z.equals(z1)) {
                String b = buyer.getName().trim().toLowerCase();
                String b1 = buyer1.getName().trim().toLowerCase();

                if (b.equals(b1)) {
                    // now it's up to price
                    if (p.equals(p1)) {
                        return 0;
                    }
                    else if (p.doubleValue() > p1.doubleValue()) {
                        return (sortOrder == SORT_ASC ? 1 : -1);
                    }
                    return (sortOrder == SORT_ASC ? -1 : 1);
                }
                else {
                    return sortOrder == SORT_ASC ? b.compareTo(b1) : b1.compareTo(b);
                }
            }
            else {
                return sortOrder == SORT_ASC ? z.compareTo(z1) : z1.compareTo(z);
            }
        }
    }

    // sort list of buyers
    public static class BuyerPriceComparator implements Comparator<Buyer> {

        private final int sortOrder;
        private final List<Buyer> preferredBuyers = new ArrayList<Buyer>();

        public BuyerPriceComparator() {
            this(SORT_DESC); // default to DESC order
        }

        protected BuyerPriceComparator(int sortOrder) {
            this.sortOrder = sortOrder;
        }

        public BuyerPriceComparator(List<Buyer> preferredBuyers) {
            this.sortOrder = SORT_DESC;
            if (preferredBuyers != null) {
                this.preferredBuyers.addAll(preferredBuyers);
            }
        }

        // order by price desc
        public int compare(Buyer buyer, Buyer buyer1) {
            // we want the preferred buyers on top
            if (buyer.isPreferred() && !buyer1.isPreferred()) {
                return (sortOrder == SORT_ASC ? 1 : -1);
            }
            else if (buyer1.isPreferred() && !buyer.isPreferred()) {
                return (sortOrder == SORT_ASC ? -1 : 1);
            }

            // if neither is preferred, or both are,
            // sort by price
            BigDecimal p = buyer.getPrice();
            if (p == null) {
                p = BigDecimal.ZERO;
            }

            BigDecimal p1 = buyer1.getPrice();
            if (p1 == null) {
                p1 = BigDecimal.ZERO;
            }

            if (p.equals(p1)) {
                return 0;
            }
            else if (p.doubleValue() > p1.doubleValue()) {
                return (sortOrder == SORT_ASC ? 1 : -1);
            }
            return (sortOrder == SORT_ASC ? -1 : 1);
        }
    }

//    public static void main(String[] args) {
//        // test order
//
//        List<Io> ios = new ArrayList<Io>();
//        Io io1 = Io.create(1L, 1L, "code1", 1L, new BigDecimal("10"), 1, 100L, "srcId1", false, null);
//        Io io2 = Io.create(1L, 2L, "code1", 2L, new BigDecimal("2"), 1, 100L, "srcId2", false, null);
//        Io io3 = Io.create(1L, 3L, "code1", 3L, new BigDecimal("12"), 1, 100L, "srcId3", false, null);
//        ios.add(io1);
//        ios.add(io3);
//        ios.add(io2);
//
//        Collections.sort(ios, new IoVplComparator());
//
//
//        Stack<Io> ioStack = new Stack<Io>();
//
//        for (Io io : ios) {
//            System.out.println("ordered: " + io);
//            ioStack.push(io);
//        }
//
//        while (!ioStack.empty()) {
//            System.out.println("io from stack: " + ioStack.pop());
//        }
//
//        // now reverse order
//        System.out.println("now sort reverse : desc");
//        Collections.sort(ios, new IoVplComparator(SORT_DESC));
//        for (Io io : ios) {
//            System.out.println("reversed (no Stack): " + io);
//        }
//    }

    public static void main(String[] args) {

        // test normalizing buyers
        List<Buyer> buyers = new LinkedList<Buyer>();

        Buyer b1 = Buyer.create("id1", "name1", "zip1");
        b1.setReservationId("c");
        b1.setPrice("8.00");
        buyers.add(b1);

        Buyer b2 = Buyer.create("id2", "name2", "zip1");
        b2.setPrice("5");
        buyers.add(b2);

        buyers.add(Buyer.create("id3", "name3", "zip1"));

        Buyer b3 = Buyer.create("id4", "name1", "zip1");
        b3.setPrice("9.99");
        buyers.add(b3);
        buyers.add(Buyer.create("id5", "name1", "zip2"));
        Buyer buyer6 = Buyer.create("id6", "name4", "zip3");
        buyers.add(buyer6);

        Buyer buyer7 = Buyer.create("id7", "name5", "zip4");
        buyers.add(buyer7);

        // sort by zip and name (the unique criterion), and within that group by price desc
        Collections.sort(buyers, new BuyerNameZipComparator());

        for (Buyer b : buyers) {
            LOG.debug(b);
        }

        LOG.debug("\r\n");

        List<Buyer> normalizedBuyers = new LinkedList<Buyer>();
        Map<String, List<Buyer>> bList = new HashMap<String, List<Buyer>>();
        for (Buyer buyer : buyers) {
            String key = buyer.getZipcode() + "-" + buyer.getName().trim().toLowerCase();
            List<Buyer> bl = bList.get(key);
            if (bl == null) {
                bl = new ArrayList<Buyer>();
                bList.put(key, bl);
                normalizedBuyers.add(buyer); // the first one is the keeper
            }
            bl.add(buyer);
        }

        // now sort the remaining list by price desc and cut off at 5 entries max
        Collections.sort(normalizedBuyers, new Rule.BuyerPriceComparator());
        for (Buyer buyer : normalizedBuyers) {
            LOG.debug(buyer);
        }

        // now set a preferred buyer and sort again (so that the preferred buyer is on top)
        LOG.debug("now with preferred buyer: " + b2.getName());
        List<Buyer> preferredBuyers = new ArrayList<Buyer>();
//        preferredBuyers.add(buyer7);
//        preferredBuyers.add(buyer6);
        buyer6.setPreferred(true);
        b3.setPreferred(true);
//        buyer7.setPreferred(true);

        Collections.sort(normalizedBuyers, new Rule.BuyerPriceComparator(preferredBuyers));
        for (Buyer buyer : normalizedBuyers) {
            LOG.debug(buyer);
        }
    }
}
