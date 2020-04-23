package com.pony.advertiser.writers;

import com.arb.ws.clients.autobytel.ContactMethod;
import com.arb.ws.clients.autobytel.ContactTime;
import com.arb.ws.clients.autobytel.Customer;
import com.arb.ws.clients.autobytel.Dealer;
import com.arb.ws.clients.autobytel.DropZoneLocator;
import com.arb.ws.clients.autobytel.DropZoneSoap12Stub;
import com.arb.ws.clients.autobytel.Error;
import com.arb.ws.clients.autobytel.FinanceMethod;
import com.arb.ws.clients.autobytel.PingResult;
import com.arb.ws.clients.autobytel.PostResult;
import com.arb.ws.clients.autobytel.Provider;
import com.arb.ws.clients.autobytel.TimeFrame;
import com.arb.ws.clients.autobytel.Vehicle;
import com.arb.ws.clients.autobytel.VehicleStatus;
import com.pony.advertiser.AdvertiserWriter;
import com.pony.advertiser.Buyer;
import com.pony.advertiser.Disposition;
import com.pony.advertiser.RoutingCandidate;
import com.pony.core.PonyPhase;
import com.pony.lead.Lead;
import com.pony.publisher.IPublisherContext;
import com.pony.publisher.PublisherContext;
import com.pony.routing.PingContext;
import com.pony.validation.ValidationResponse;

import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tech Contact: Glenn Davis GlennDa@autobytel.com
 * Business Contact: Kristen Moua" KristenM@autobytel.com
 * <p/>
 * providerId=32953
 * Acquisition Labs, Inc (Dealer-Callout)
 * <p/>
 * Reporting:
 * The Affiliate Center: http://extranet.autobytel.com/affiliates/index.cfm
 * UN:  Acquisition Labs, Inc (Dealer-Callout)
 * PW:  45E8F7DB
 * <p/>
 * Details:
 * (Note:  The first 3 day after month end is reserve for scrub.  Payable numbers are best calculated on the 4th day after month end.)
 * (Timing of Scrub may vary due to weekends and holidays).
 * <p/>
 * Report Type:	Prospect
 * Vehicle Type:	Both
 * <p/>
 * Lead Types:
 * LeadType	Payable Category
 * Retail	= Retail PRI (New/Used)
 * RTL	= Retail PRI (New/Used)
 * WHL	= Wholesale PRI (New/Used)
 * Wholesale I 	= Wholesale PRI (New/Used)
 * Wholesale II	= Audi, GM, Toyota & VW PRI (New)
 * <p/>
 * Retail vs Wholesale:
 * In the ping response, we return a Program ID. Program ID 1 and 127 are retail. All other Program IDs are wholesale.
 * If you have multiple wholesale tiers (price points), Kristen can tell you which Program IDs correspond to which tiers.
 * I do see two leads successfully posted to our Staging server.
 * - Glenn S. Davis
 * <p/>
 * ArbVentures 2013.
 * generate stubs via: /work/tools/axis-1_4/wsdl2java.sh -t -o autobytel -p com.arb.ws.clients.autobytel http://leadengine.services.staging.myride.com/leadengine/DropZone.asmx?WSDL
 * User: martin
 * Date: 8/20/13
 * Time: 1:57 PM
 */
public class AutoByTelWriter extends AdvertiserWriter {
	private static final Log LOG = LogFactory.getLog(AutoByTelWriter.class);

    private static final Map<Integer, BigDecimal> PRICE_MAP = new HashMap<Integer, BigDecimal>();

    /**
     PROGRAM ID     CPL         CHANNEL
     1              $11.50      Retail NC
     127            $11.50      Retail NC
     29             $4.00       Wholesale NC
     39             $4.00       Wholesale NC
     160            $4.00       Wholesale NC
     96             $4.00       Wholesale NC
     163            $4.00       Wholesale NC
     149            $4.00       Wholesale NC
     150            $4.00       Wholesale NC
     182            $4.00       Wholesale NC
     183            $4.00       Wholesale NC
     184            $4.00       Wholesale NC
     185            $4.00       Wholesale NC
     all other not listed (1-200)
     $6.00       Wholesale NC
     */
    static {
        PRICE_MAP.put(1, new BigDecimal("11.50"));
        PRICE_MAP.put(127, new BigDecimal("11.50"));
        PRICE_MAP.put(29, new BigDecimal("4.00"));
        PRICE_MAP.put(39, new BigDecimal("4.00"));
        PRICE_MAP.put(160, new BigDecimal("4.00"));
        PRICE_MAP.put(96, new BigDecimal("4.00"));
        PRICE_MAP.put(163, new BigDecimal("4.00"));
        PRICE_MAP.put(149, new BigDecimal("4.00"));
        PRICE_MAP.put(150, new BigDecimal("4.00"));
        PRICE_MAP.put(182, new BigDecimal("4.00"));
        PRICE_MAP.put(183, new BigDecimal("4.00"));
        PRICE_MAP.put(184, new BigDecimal("4.00"));
        PRICE_MAP.put(185, new BigDecimal("4.00"));
    }

    public AutoByTelWriter() {
    }

    @Override
    public boolean supportsPhase(PonyPhase phase) {
        return phase == PonyPhase.POST || phase == PonyPhase.PING;
    }

    @Override
    public Disposition ping(Long leadMatchId, PingContext pingContext, RoutingCandidate candidate) {

        try {
            Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());
            String year = map.get("year");
            if (year == null || "".equals(year)) {
                Calendar cal = Calendar.getInstance();
                year = "" + cal.get(Calendar.YEAR);
            }

            String pingTimeout = map.get("pingTimeout");
            int ptime = 10000;
            if (pingTimeout != null && !"".equals(pingTimeout)) {
                ptime = Integer.valueOf(pingTimeout);
            }

//            providerId=32953
//            Acquisition Labs, Inc (Dealer-Callout)
            Integer providerId = 32953; //TODO: get default value
            String provider = map.get("providerId");
            if (provider != null) {
                providerId = Integer.valueOf(provider);
            }

            Lead lead = pingContext.getLead();

            DropZoneSoap12Stub binding = (DropZoneSoap12Stub) new DropZoneLocator().getDropZoneSoap12();
            // Time out after xx seconds
            binding.setTimeout(ptime);

            String leadYear = lead.getAttributeValue("year");
            if (leadYear != null && !"".equals(leadYear)) {
                year = leadYear;
            }

            String make = lead.getAttributeValue("make");
            String model = lead.getAttributeValue("model");
            String trim = lead.getAttributeValue("trim", "");
            String zip = lead.getAttributeValue("zipcode");

            // log the sent message
            StringBuilder message = new StringBuilder();
            message.append("year=").append(year).append("&make=").append(make).append("&model=").append(model).append("&trim=").append(trim).append("&zip=").append(zip);
            Long msgId = logPingMessage(leadMatchId, message.toString());

            PingResult pingResult = binding.ping(providerId, Integer.valueOf(year), make, model, trim, zip);

            // or PingEX
            //pingResult = binding.pingEx(0, 0, new java.lang.String(), new java.lang.String(), new java.lang.String(), new java.lang.String(), ppcSourceGroupID);

            if (msgId != null) {
                //TODO: check the message get rendered here (or: replace toString() with the details we need)
                StringBuilder msg = new StringBuilder();
                msg.append("coverage=").append(pingResult.isCoverage()).append(" transactionId=").append(pingResult.getTransactionID());
                // format the rest of the response
                for (Error error : pingResult.getErrors()) {
                    msg.append("; ").append(error.getCode()).append("=").append(error.getMessage());
                }

                for (Dealer dealer : pingResult.getDealers()) {
                    msg.append("; dealer:").append(dealer.getDealerID()).append("=").append(dealer.getDealerCode()).append(":").append(dealer.getName()).append(":").append(dealer.getProgramID());
                }

                logResponseMessage(msgId, msg.toString());
            }

            if (!pingResult.isCoverage()) {
                return Disposition.create(PonyPhase.PING, Disposition.Status.NO_COVERAGE, pingResult.getTransactionID());
            }

            Error[] errors = pingResult.getErrors();
            if (errors != null && errors.length > 0) {
                StringBuilder msg = new StringBuilder();
                for (Error error : errors) {
                    msg.append(error.getCode()).append("=").append(error.getMessage()).append("; ");
                }
                return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, msg.toString());
            }

            Dealer[] dealers = pingResult.getDealers();
            if (dealers != null && dealers.length > 0) {
                BigDecimal price = new BigDecimal("6.0"); // default price (unless explicitly mapped via programId)
                Disposition acceptDealerSelect = Disposition.create(PonyPhase.PING, Disposition.Status.ACCEPTED);

                for (Dealer dealer : dealers) {
                    dealer.getDealerCode(); // note: can be empty! dealerId is guaranteed

                    Buyer buyer = Buyer.create("" + dealer.getDealerID(), dealer.getName(), dealer.getZipCode());
                    buyer.setReservationId(pingResult.getTransactionID());
                    buyer.setCity(dealer.getCity());

                    // map price based on programID
                    BigDecimal tierPrice = PRICE_MAP.get(dealer.getProgramID());
                    if (tierPrice == null) {
                        buyer.setPrice(price); // default price
                    }
                    else {
                        buyer.setPrice(tierPrice);
                    }

                    buyer.setState(dealer.getState());
                    buyer.setDistance(dealer.getDistance());
                    buyer.setAddress(dealer.getAddress());
                    buyer.setBuyerCode(dealer.getDealerCode());
                    buyer.setLongitude("" + dealer.getLongitude());
                    buyer.setLatitude("" + dealer.getLatitude());
                    buyer.setProgramId("" + dealer.getProgramID());
                    buyer.setContactPhone(dealer.getPhone());

                    acceptDealerSelect.addBuyer(buyer);
                }

                return acceptDealerSelect;
            }
            return Disposition.create(PonyPhase.PING, Disposition.Status.NO_COVERAGE, "No dealers returned?");
        }
        catch (RemoteException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (IOException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (ServiceException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.PING, Disposition.Status.REJECTED, e.getMessage());
        }
    }

    @Override
    public Disposition post(Long leadMatchId, IPublisherContext publisherContext, ValidationResponse validationResponse, RoutingCandidate candidate) {
        try {
            Map<String, String> map = AdvertiserWriter.parseStringToMap(candidate.getIo().getSourceId());
            String year = map.get("year");
            if (year == null || "".equals(year)) {
                Calendar cal = Calendar.getInstance();
                year = "" + cal.get(Calendar.YEAR);
            }

            Disposition pingData = candidate.getDisposition();
//            List<Buyer> selectedBuyers = pingData.getBuyers();
            Buyer buyer = candidate.getBuyer();

            LOG.info("" + leadMatchId + ": post for AutoByTel with pingData=" + pingData + ": buyer=" + buyer);
            Lead lead = publisherContext.getLead();
            DropZoneSoap12Stub binding = (DropZoneSoap12Stub) new DropZoneLocator().getDropZoneSoap12();

            String postTimeout = map.get("postTimeout");
            int ptime = 40000;
            if (postTimeout != null && !"".equals(postTimeout)) {
                ptime = Integer.valueOf(postTimeout);
            }
            // Time out after xx seconds
            binding.setTimeout(ptime);

//            providerId=32953
//            Acquisition Labs, Inc (Dealer-Callout)
            Integer providerId = 32953; //TODO: get default value
            String pId = map.get("providerId");
            if (pId != null) {
                providerId = Integer.valueOf(pId);
            }

            com.arb.ws.clients.autobytel.Lead abtLead = new com.arb.ws.clients.autobytel.Lead();
            abtLead.setProspectID(leadMatchId.toString());
            abtLead.setRequestDate(Calendar.getInstance());

            // ==========================================
            // Provider, aka us
            // ==========================================
            Provider provider = new Provider();
            provider.setProviderID(providerId);
//            provider.setName(publisherContext.getPublisher().getName());
            provider.setName("Acquisition Labs, Inc (Dealer-Callout)");
            provider.setService("LeadPath");
            abtLead.setProvider(provider);

            // ==========================================
            // customer info
            // ==========================================
            Customer customer = new Customer();
            // TODO: defaults for values we don't have
            customer.setBestContactMethod(ContactMethod.HomePhone);
            customer.setBestContactTime(ContactTime.NoPreference);
            customer.setPurchaseTimeFrame(TimeFrame.Within14Days);

            customer.setEmailAddress(publisherContext.getUserProfile().getEmail());
            customer.setAddress1(lead.getAttributeValue("address", ""));
//            customer.setAddress2(lead.getAttributeValue("address", ""));
            customer.setCity(lead.getAttributeValue("state", ""));
            customer.setFirstName(lead.getAttributeValue("first_name", ""));
            customer.setLastName(lead.getAttributeValue("last_name", ""));
            customer.setHomePhone(lead.getAttributeValue("home_phone", "")); // mobile_phone
            customer.setWorkPhone(lead.getAttributeValue("work_phone", ""));
            customer.setState(lead.getAttributeValue("state", ""));
            customer.setZipCode(lead.getAttributeValue("zipcode", ""));

            abtLead.setCustomer(customer);

            // ==========================================
            // Vehicle information
            // ==========================================
            Vehicle vehicle = new Vehicle();
            // TODO: default: we don't have a value for that
            vehicle.setPreferedFinanceMethod(FinanceMethod.Finance);

            vehicle.setStatus(VehicleStatus.New);
            // if we have the questionaire we have info on used or new , otherwise default to new
            // not_sure, new, used, new_and_used
//            String usedNew = lead.getAttributeValue("used_new");
//            if (usedNew != null) {
//                if (!"new".equalsIgnoreCase(usedNew)) {
//                    vehicle.setStatus(VehicleStatus.Used);
//                }
//            }
//          ==> can't do that since Used requires to also provide the vehicle id!

            // ready_to_buy , still_researching , not_yet_decided
            String decided = lead.getAttributeValue("decided");
            if (decided != null && "ready_to_buy".equalsIgnoreCase(decided)) {
                customer.setPurchaseTimeFrame(TimeFrame.Within48Hours);
            }

            String leadYear = lead.getAttributeValue("year");
            if (leadYear != null && !"".equals(leadYear)) {
                year = leadYear;
            }
            vehicle.setYear(Integer.valueOf(year));
            vehicle.setMake(lead.getAttributeValue("make"));
            vehicle.setModel(lead.getAttributeValue("model"));
            vehicle.setTrim(lead.getAttributeValue("trim",""));
            abtLead.setVehicle(vehicle);

            // ==========================================
            // Dealers
            // ==========================================

            List<Dealer> dealerList = new ArrayList<Dealer>();
// Note: for now we post each buyer separately, hence we only get the buyer from the candidate (and not the selected buyers from the disposition)
//            for (Buyer buyer : selectedBuyers) {
            Dealer dealer = new Dealer();
            dealer.setDealerID(Integer.valueOf(buyer.getBuyerId()));
            dealer.setDealerCode(buyer.getBuyerCode());
            dealer.setName(buyer.getName());
            dealer.setAddress(buyer.getAddress());
            dealer.setCity(buyer.getCity());
            dealer.setDistance(buyer.getDistance());
            dealer.setLatitude(Double.valueOf(buyer.getLatitude()));
            dealer.setLongitude(Double.valueOf(buyer.getLongitude()));
            dealer.setPhone(buyer.getContactPhone());
            dealer.setProgramID(Integer.valueOf(buyer.getProgramId()));
            dealer.setState(buyer.getState());
            dealer.setZipCode(buyer.getZipcode());
            dealerList.add(dealer);
//            }

            Dealer[] dealerArray = new Dealer[dealerList.size()];
            dealerList.toArray(dealerArray);
            abtLead.setDealers(dealerArray);

            StringBuilder msg = serializeLead(abtLead);

            Long msgId = logPostMessage(leadMatchId, msg.toString());

            PostResult postResult = binding.post(abtLead);
            msg = new StringBuilder();
            msg.append("accepted=").append(postResult.isAccepted()).append("; leadId=").append(postResult.getLeadID()).append("; txId=").append(postResult.getTransactionID()).append("; dealerCnt=").append(postResult.getDealers().length).append("; errorCnt=").append(postResult.getErrors().length);

            if (msgId != null) {
                StringBuilder logMsg = new StringBuilder();
                logMsg.append(msg);
                if (postResult.getErrors().length > 0) {
                    logMsg.append("; Errors:");
                    for (Error error : postResult.getErrors()) {
                        logMsg.append(error.getCode()).append("=").append(error.getMessage());
                    }
                }

                if (postResult.getDealers().length > 0) {
                    logMsg.append("; Dealers:");
                    for (Dealer d : postResult.getDealers()) {
                        logMsg.append("id=").append(d.getDealerID()).append("; code=").append(d.getDealerCode()).append("; name=").append(d.getName());
                    }
                }

                logResponseMessage(msgId, logMsg.toString());
            }

            if (!postResult.isAccepted()) {
                return Disposition.createPost(Disposition.Status.REJECTED, null, postResult.getLeadID(), msg.toString());
            }

            return Disposition.createPost(Disposition.Status.ACCEPTED, null, postResult.getLeadID(), msg.toString());
        }
        catch (RemoteException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (IOException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
        catch (ServiceException e) {
            LOG.error(e);
            return Disposition.create(PonyPhase.POST, Disposition.Status.REJECTED, e.getMessage());
        }
    }

    private StringBuilder serializeLead(com.arb.ws.clients.autobytel.Lead l) {
        StringBuilder msg = new StringBuilder();
        msg.append("prospectId=").append(l.getProspectID()).append("; customer=").append(l.getCustomer().getEmailAddress());
        msg.append("; provider=").append(l.getProvider().getProviderID()).append("; vehicle:");
        msg.append(l.getVehicle().getYear()).append("-").append(l.getVehicle().getMake()).append("-").append(l.getVehicle().getModel()).append("-").append(l.getVehicle().getTrim());
        msg.append("; Dealers");
        for (Dealer dealer : l.getDealers()) {
            msg.append(": id=").append(dealer.getDealerID()).append("; code=").append(dealer.getDealerCode()).append("; name=").append(dealer.getName());
        }

        return msg;
    }
}
