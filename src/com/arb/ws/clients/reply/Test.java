package com.arb.ws.clients.reply;

import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 7/5/13
 * Time: 5:55 PM
 */
public class Test {
	private static final Log LOG = LogFactory.getLog(Test.class);

    public static void main(String[] args) {

        Test test = new Test();
        test.ping(new Lead());

    }

    private void ping(Lead lead) {
        IPingService1Stub binding = null;
        try {
            binding = (IPingService1Stub) new PingServiceLocator().getIPingService1();

            assert (binding != null);

            // Time out after 10 seconds
            binding.setTimeout(10000);

            AutoRequest autoRequest = new AutoRequest();
            AutoRequestUser user = new AutoRequestUser();
            user.setUsername("jeremy");
            user.setEmail("jeremy@acquisition-labs.com");
            user.setPassword("Reply2011");

            AutomotiveLead autoLead = new AutomotiveLead();
            autoLead.setLeadType(AutomotiveLeadLeadType.New);
            autoLead.setMake(lead.get("make"));
            autoLead.setModel(lead.get("model"));
//            autoLead.setTrim(lead.get("trim"));
            autoLead.setZipcode(lead.get("zipcode"));

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR_OF_DAY, -2);
            autoLead.setCollectionDate(cal);

            autoRequest.setReservationToken("");



            autoRequest.setUser(user);
            autoRequest.setAutomotiveLead(autoLead);

            AutoResponse response = binding.pingAuto(autoRequest);

            response.getAutomotiveLead();

        }
        catch (ServiceException e) {
            LOG.error(e);
        }
        catch (RemoteException e) {
            LOG.error(e);
        }

        assert (binding != null);

        // Time out after 10 seconds
        binding.setTimeout(10000);

    }

    private void post(Lead lead) {
        try {
            IPostService1Stub binding = (IPostService1Stub) new PostServiceLocator().getIPostService1();

            assert (binding != null);

            // Time out after 10 seconds
            binding.setTimeout(10000);

            // Test operation
            AutoRequestUser user = new AutoRequestUser();
            user.setUsername("jeremy");
            user.setEmail("jeremy@acquisition-labs.com");
            user.setPassword("Reply2011");

            AutomotiveLead autoLead = new AutomotiveLead();
            autoLead.setLeadType(AutomotiveLeadLeadType.New);
            autoLead.setMake(lead.get("make"));
            autoLead.setModel(lead.get("model"));
            autoLead.setTrim(lead.get("trim"));
            autoLead.setZipcode(lead.get("zipcode"));

            AutoRequest autoRequest = new AutoRequest();
            autoRequest.setUser(user);
            autoRequest.setAutomotiveLead(autoLead);

            AutoResponse response = binding.directPostAuto(autoRequest);
            // TBD - validate results
            response.getAutomotiveLead().getLeadType();

        }
        catch (javax.xml.rpc.ServiceException jre) {
            if (jre.getLinkedCause() != null) {
                jre.getLinkedCause().printStackTrace();
            }
            throw new Error("JAX-RPC ServiceException caught: " + jre);
        }
        catch (RemoteException e) {
            LOG.error(e);
        }


    }

    private static class Lead {

        static Map<String, String> attributes = new HashMap<String, String>();

        static {
            attributes.put("ip_address", "66.94.234.13");
            attributes.put("year", "2013");
            attributes.put("make", "Audi");
            attributes.put("model", "A4");
            attributes.put("trim", "");
            attributes.put("zipcode", "48187");

            attributes.put("first_name", "Joe");
            attributes.put("last_name", "Tester");
            attributes.put("email", "joe@test.com");
        }

        public String get(String attributeName) {
            return attributes.get(attributeName);
        }

        public String get(String attributeName, String defaultValue) {
            String value = get(attributeName);
            if (value != null) {
                return value;
            }
            return defaultValue;
        }
    }
}
