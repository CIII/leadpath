package com.pony.email.validation;

/**
 * Checking back lists for references of our ip addresses.
 *
 * PonyLeads 2012.
 * User: martin
 * Date: 12/6/12
 * Time: 10:39 AM
 */

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class DNSBL {
	private static final Log LOG = LogFactory.getLog(DNSBL.class);
	
    private static String[] RECORD_TYPES = {"A", "TXT"};

    private DirContext ictx;
    private List<String> lookupServices = new ArrayList();


    public DNSBL() throws NamingException {
        StringBuilder dnsServers = new StringBuilder("");
        List nameservers = sun.net.dns.ResolverConfiguration.open().nameservers();
        for (Object dns : nameservers) {
            dnsServers.append("dns://").append(dns).append(" ");
        }

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
        env.put("com.sun.jndi.dns.timeout.initial", "3000");
        env.put("com.sun.jndi.dns.timeout.retries", "1");
        env.put(Context.PROVIDER_URL, dnsServers.toString());

        ictx = new InitialDirContext(env);
    }

    public void addLookupService(String service) {
        lookupServices.add(service);
    }

    public void check(String ip) throws DNSBLException {
        String[] parts = ip.split("\\.");
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            buffer.insert(0, '.');
            buffer.insert(0, parts[i]);
        }

        ip = buffer.toString();


        Attribute attribute;
        Attributes attributes;


        String lookupHost;
        for (String service : lookupServices) {
            System.out.print(service + "  ");
            lookupHost = ip + service;
            try {
                attributes = ictx.getAttributes(lookupHost, RECORD_TYPES);
                attribute = attributes.get("TXT");

                if (attribute != null) {
                    throw new DNSBLException(lookupHost + ": " + attribute.get());
                }
            }
            catch (NameNotFoundException e) {
                //this is good
                //LogWriter.system.log(getClass(),e);
            }
            catch (NamingException e) {
                //LogWriter.system.log(getClass(),e);
            }
        }
        LOG.debug("");
    }


    public static void main(String[] args) {


        try {
            DNSBL dnsBL = new DNSBL();

            dnsBL.addLookupService("blackholes.easynet.nl");
            dnsBL.addLookupService("cbl.abuseat.org");
            dnsBL.addLookupService("proxies.blackholes.wirehub.net");
            dnsBL.addLookupService("bl.spamcop.net");
            dnsBL.addLookupService("sbl.spamhaus.org");
            dnsBL.addLookupService("dnsbl.njabl.org");
            dnsBL.addLookupService("list.dsbl.org");
            dnsBL.addLookupService("multihop.dsbl.org");
            dnsBL.addLookupService("cbl.abuseat.org");

            //TODO: make this dynamic and read our hosts table
            //String[] ips = {"201.223.47.44", "38.100.193.183", "189.94.149.137", "220.227.113.25"};

            String[] ips = {"50.56.117.201", "50.56.83.143", "50.56.82.27", "50.56.97.96", "50.56.102.153", "50.56.100.109", "50.56.107.199", "50.56.107.128", "50.56.100.79", "50.56.107.194", "50.56.101.165"};

//            String [] ips = {"68.232.200.211","199.122.124.128","199.122.124.129"};

            for (String ip : ips) {
                try {
                    long t1 = System.currentTimeMillis();
                    LOG.debug("checking " + ip);
                    dnsBL.check(ip);
                    LOG.debug("time=" + (System.currentTimeMillis() - t1));
                }
                catch (DNSBLException se) {
                    LOG.error(se.getMessage());
                }
            }
        }
        catch (Exception e) {
            LOG.error(e);
        }

        System.exit(0);
    }
}

class DNSBLException extends Exception {
    public DNSBLException(String message) {
        super(message);
    }
}

