package com.pony.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 11/28/12
 * Time: 4:14 PM
 */
public class TldParser {
	private static final Log LOG = LogFactory.getLog(TldParser.class);
	
    private final List<String> TLDS = new ArrayList<String>();
    private final List<String> WILDCARDS = new ArrayList<String>();
    private final Map<String, String> EXCLUDED_TLDS = new HashMap<String, String>();
    private final List<String> EXCLUDED_SUB_DOMAINS = new ArrayList<String>();

    public TldParser() {
        init();
    }

    private List<String> getTlds() {
        return TLDS;
    }

    public static void main(String[] args) {
        TldParser p = new TldParser();
        List<String> tlds = p.getTlds();

        for (String tld : tlds) {
            LOG.debug("tld=" + tld);
        }

        LOG.debug("size=" + tlds.size());
    }

    /**
     * get the domain and sub-domain for the given host string
     *
     * @param fullDomain the host
     * @return an array with the domain (index=0) and the sub-domain (index=1); if there is no sub-domain, then the content of index 1 and 0 are identical
     */
    public String[] getDomainAndSubDomain(String fullDomain) {
        //System.out.println("getting domain and subDomain for [" + fullDomain + "]");
        String[] domains = new String[2];

        String[] labels = fullDomain.split("\\.");
        String tld = null;
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < labels.length; i++) {
            String domain = "";
            String domain2 = "";
            for (int d = i; d < labels.length; d++) {
                domain = domain + (domain.equals("") ? "" : ".") + labels[d];
                if (d > i) {
                    domain2 = domain2 + (domain2.equals("") ? "" : ".") + labels[d];
                }
            }

            if (TLDS.contains(domain)) {
                tld = domain;
            }
            else if (WILDCARDS.contains(domain2)) {
                //System.out.println("!!WildCard!!! for d=[" + domain + "] parent=[" + domain2 + "]");
                String excludedTld = EXCLUDED_TLDS.get(domain);
                if (excludedTld != null) {
                    //System.out.println(
                    //    "!!excluded WildCard!!! for d=[" + domain + "] parent=[" + domain2 + "] tld=[" + excludedTld + "]");
                    tld = excludedTld;
                    stack.push(domain);
                }
                else {
                    tld = domain;
                }
            }
            if (tld != null) {
                //System.out.println("TLD=" + tld);
                break;
            }

            stack.push(domain);
        }

        if (stack.size() > 0) {
            domains[0] = stack.pop();
        }
        if (stack.size() > 0) {
            domains[1] = stack.pop();
        }
        else {
            domains[1] = domains[0]; // domain and sub domain are the same
        }

        // filter excluded sub domains
        if (domains[1] != null) {
            String[] bs = domains[1].split("\\.");
            if (bs.length > 1 && EXCLUDED_SUB_DOMAINS.contains(bs[0])) {
                //System.out.println("Excluded sub domain for " + domains[1] + " => " + domains[0]);
                domains[1] = domains[0];
            }
        }
        return domains;
    }

    private void init() {
        EXCLUDED_SUB_DOMAINS.add("www");
        EXCLUDED_SUB_DOMAINS.add("ftp");

        //http://mxr.mozilla.org/mozilla-central/source/netwerk/dns/src/effective_tld_names.dat?raw=1

        /*
       format:
   // ac : http://en.wikipedia.org/wiki/.ac
   ac
   com.ac
   edu.ac
   gov.ac
   net.ac
   mil.ac
   org.ac

   // ad : http://en.wikipedia.org/wiki/.ad

       -> look for // +=> new tld block
       -> look for empty line => end of tld block


        */

//        try {
//            String rawData = Client.httpGet("mxr.mozilla.org", 80,
//                            "/mozilla-central/source/netwerk/dns/src/effective_tld_names.dat?raw=1");
//
//            //System.out.println("raw=" + rawData);
//
//            BufferedReader reader = new BufferedReader(new StringReader(rawData));
//            String line = null;
//            boolean httpHeaderEndFound = false;
//            boolean contentTypeFound = false;
//
//            boolean blockStartFound = false;
//            boolean blockEndFound = false;
//            while ((line = reader.readLine()) != null) {
//                line = line.trim();
//                if (line.startsWith("Content-Type:")) {
//                    contentTypeFound = true;
//                }
//
//                // the first empty line after the content-type header marks the end of the headers and the beginning of the payload
//                if ("".equals(line) && contentTypeFound && !httpHeaderEndFound) {
//                    httpHeaderEndFound = true;
//                    continue;
//                }
//
//                if (!httpHeaderEndFound) {
//                    continue;
//                }
//
//                // processing the content starts here
//                if (line.startsWith("//")) {
//                    blockStartFound = true;
//                    blockEndFound = false;
//                }
//                else if ("".equals(line)) {
//                    blockEndFound = true;
//                    blockStartFound = false;
//                }
//                else {
//                    //TODO: handle *.xxx and exclusions (!nel.uk)
//                    if (line.startsWith("*")) {
//                        // wildCards
//                        WILDCARDS.add(line.replace("*.", ""));
//                    }
//                    else if (line.startsWith("!")) {
//                        // exclusion to wildcard
//                        line = line.replace("!", "");
//                        String nextTop = line.substring(line.indexOf(".") + 1);
//                        EXCLUDED_TLDS.put(line, nextTop);
//                    }
//                    else {
//                        //simple tld
//                        TLDS.add(line);
//                    }
//                }
//            }
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
