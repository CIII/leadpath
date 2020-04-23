package com.pony.core;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 11/28/12
 * Time: 4:14 PM
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import models.Domain;

/**
 * <p/>
 * Copyright (C) 2009 ClickHarmonics, Inc. All rights reserved.
 * THIS PROGRAM IS AN UNPUBLISHED WORK AND IS CONSIDERED A TRADE SECRET AND
 * CONFIDENTIAL INFORMATION BELONGING TO VESTMARK, INC.
 * ANY UNAUTHORIZED USE IS STRICTLY PROHIBITED.
 * <p/>
 * User: Martin Holzner (mailto:mholzner@clickharmonics.com)
 * Date: Sep 24, 2009
 * Time: 4:36:38 PM
 */
public class Domains {
	private static final Log LOG = LogFactory.getLog(Domains.class);
	
    private final TldParser tld;
    int initialCapacity = 100;
    float loadFactor = 0.75f;
    private Map<String, Long> cache = new ConcurrentHashMap<String, Long>(initialCapacity, loadFactor);
    private final Connection con;

    public Domains(String server) throws SQLException, ClassNotFoundException {
        tld = new TldParser();
//        con = Domain.connect(server);
        con = null;
    }

    public String[] getDomainAndSubDomain(String url) {
        //System.out.println("getting domain and subDomain for [" + url + "]");

        if ("http://about/".equals(url.trim()) || "http://about".equals(url.trim())) {
            return new String[]{"-1", "-1"};
        }

        String fullDomain = parseDomain(url);

        if (isIpAddress(fullDomain)) {
            return new String[]{fullDomain, fullDomain};
        }

        Validation validation = validate(fullDomain);
        if (validation.isPass()) {
            return tld.getDomainAndSubDomain(fullDomain);
        }
        else {
            return validation.getErrors();
        }
    }

    private Validation validate(String domain) {
        // RFCs mandate that a hostname's labels may contain only the ASCII letters 'a' through 'z' (case-insensitive),
        // the digits '0' through '9', and the hyphen.
        // Hostname labels cannot begin or end with a hyphen.
        // No other symbols, punctuation characters, or blank spaces are permitted.
        for (Character c : domain.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                // is it a dash?
                if (c != "-".charAt(0) && c != ".".charAt(0)) {
                    return new Validation(domain + ": contains invalid char[" + c + "]", false);
                }
            }
        }
        return new Validation();
    }

    public static String parseDomain(String url) {
        if (url == null || "".equals(url)) {
            throw new IllegalArgumentException("No url provided");
        }
        // clean the string and then try to parse....
        url = url.trim();

        // protocol?
        int i = url.indexOf("://");
        if (i > 0) {
            url = url.substring(i + 3);
        }

        // path
        i = url.indexOf("/");
        String domain;
        if (i > 0) {
            domain = url.substring(0, i);
        }
        else {
            domain = url;
        }

        // user: foo@host
        i = domain.indexOf("@");
        if (i > 0) {
            domain = domain.substring(i + 1);
        }

        // port?
        i = domain.indexOf(":");
        if (i > 0) {
            // is the remainder a number?
            String port = domain.substring(i + 1);
            try {
                int p = Integer.valueOf(port);
                domain = domain.substring(0, i);
            }
            catch (NumberFormatException e) {
                // is is a user before the ':' ?
                //??? TODO ??
            }
        }

        return domain;
    }

    private boolean isIpAddress(String domain) {
        String[] labels = domain.split("\\.");
        if (labels.length != 4) {
            return false;
        }
        for (String label : labels) {
            for (Character c : label.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // test parsing
        Domains d = new Domains("localhost");
        //d.test("one.com");
        //d.test("http://one.com");
        //d.test("http://www.one.com");
        //d.test("www.one.com");
        //d.test("https://one.com/path");
        //d.test("gtp://a.b.c.one.com/path/topw/three");

        d.test("protocol://a.b.c.uri.arpa/path");

        d.test("p://1.2.3.gallery.museum/path");
        d.test("p://1.2.3.parliament.uk/path");
        d.test("p://www.bbc.co.uk/path");

        d.test("svn+ssh://mholzner@venkman.click.com/home/click_svn/repo1");
        //d.test("http://mholzner:venkman.click.com/home/click_svn/repo1");
        d.test("http://mholzner@venkman.click.com:9080/home/click_svn/repo1");
        //d.test("http://mholzner:venkman.click.com:8080/home/click_svn/repo1");
        d.test("http://127.0.0.1/path");
        d.test("httpd://127.0.0.1:6565/path");
        d.test("http://localhost:9090/path");
    }

    private void test(String url) {
        String domain = parseDomain(url);
        LOG.debug("testing parse of [" + url + "] -> " + domain + " : isIP=" + isIpAddress(domain));

        //String[] domains = getDomainAndSubDomain(url);
        //System.out.println("url=" + url + " ==> domain=" + domains[0] + "; sub=" + domains[1]);
    }

    Long[] cacheLookup(String[] domainNames) throws SQLException, ClassNotFoundException {
        //System.out.println("Domains: cache-size=" + cache.size());
        Long[] ids = new Long[domainNames.length];

        // 1: cache lookup
        // 2: db lookup
        // 3: insert in db and cache
        if (cacheLookup(domainNames, ids)) {
            return ids;
        }

        Map<String, Long> updateMap = new HashMap<String, Long>();
        // read the missing ones from db, and populate the cache
//        updateMap.putAll(readDomains(con, domainNames, ids));
//        if (updateMap.size() > 0) {
//            cacheUpdate(updateMap);
//        }

        // if we don't have them ,now is the time to create them
        updateMap.clear();
//        updateMap.putAll(insertDomains(con, domainNames, ids));
//        if (updateMap.size() > 0) {
//            cacheUpdate(updateMap);
//        }

        return ids;
    }

//    private Map<String, Long> insertDomains(Connection con, String[] domainNames, Long[] ids) throws SQLException
//    {
//        Map<String, Long> cacheUpdates = new HashMap<String, Long>();
//
//        for (int i = 0; i < domainNames.length; i++) {
//            if (ids[i] == null) {
//                if (i == 0) {
//                    // insert one and get the id for it
//                    ids[i] = Domain.createDomain(con, domainNames[i]);
//                    if (domainNames[i].equals(domainNames[i + 1])) {
//                        ids[i + 1] = ids[i];
//                    }
//                }
//                else {
//                    ids[i] = Domain.createSubDomain(con, domainNames[i], ids[0]);
//                }
//                cacheUpdates.put(domainNames[i], ids[i]);
//            }
//        }
//
//        return cacheUpdates;
//    }

//    private Map<String, Long> readDomains(Connection con, String[] domainNames, Long[] ids)
//            throws ClassNotFoundException, SQLException
//    {
//        Map<String, Long> cacheUpdates = new HashMap<String, Long>();
//
//        // read the ids for the domains that have -1 as id
//
//        for (int i = 0; i < domainNames.length; i++) {
//            if (ids[i] == null) {
//                // read it
//                if (i == 0) {
//                    ids[i] = Domain.findDomainId(con, domainNames[i]);
//                    if (domainNames[i].equals(domainNames[i + 1])) {
//                        ids[i + 1] = ids[i];
//                    }
//                }
//                else {
//                    ids[i] = Domain.findSubDomainId(con, domainNames[i]);
//                }
//
//                if (ids[i] != null) {
//                    cacheUpdates.put(domainNames[i], ids[i]);
//                }
//            }
//        }
//        return cacheUpdates;
//    }

    private void cacheUpdate(Map<String, Long> updates) {
        for (Map.Entry<String, Long> entry : updates.entrySet()) {
            cache.put(entry.getKey(), entry.getValue());
        }
    }

    private boolean cacheLookup(String[] domainNames, Long[] ids) {
        boolean allInCache = true;
        for (int i = 0; i < domainNames.length; i++) {
            ids[i] = cache.get(domainNames[i]);
            if (ids[i] == null) {
                allInCache = false;
            }
        }

        return allInCache;
    }

//    public void closeConnection()
//    {
//        Domain.close(con);
//    }

    private class Validation {
        private final String error;
        private final boolean pass;

        private Validation(String error, boolean pass) {
            this.error = error;
            this.pass = pass;
        }

        private Validation() {
            this.error = "ok";
            this.pass = true;
        }

        public String[] getErrors() {
            return new String[]{error};
        }

        public boolean isPass() {
            return pass;
        }
    }
}
