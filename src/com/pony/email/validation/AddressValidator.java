package com.pony.email.validation;

import com.pony.lead.UserProfile;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * TODO:
 * BriteVerify,
 * StrikeIron
 * and LeadSpend
 * are competitors offering real-time email validation.
 * <p/>
 * FreshAddress
 * is a company using the database method.
 * All of the bounce-avoidance services also do identify some amount of spam traps, disposable addresses, role accounts and
 * the like.
 * <p/>
 * Quickie and
 * ImpressionWise
 * focus on spam traps, screamers, and other potential threats.
 * Both companies have built a database of known traps, and compliment that with a set of algorithms
 * to predict whether an email address is likely a trap or not.
 * end TODO
 * <p/>
 * PonyLeads 2012.
 * User: martin
 * Date: 12/6/12
 * Time: 11:00 AM
 */
public class AddressValidator {
	private static final Log LOG = LogFactory.getLog(AddressValidator.class);
	
    private static final List<String> STARTS_WITH = new ArrayList<String>();
    private static final List<String> CONTAINS = new ArrayList<String>();
    private static final Hashtable<String, String> ENV = new Hashtable<String, String>();
    private static final Map<String, Integer> HOSTS = new HashMap<String, Integer>();

    private static Pattern pattern;

    private static PatternSyntaxException patternError;

    static {
        ENV.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        ENV.put("com.sun.jndi.dns.timeout.initial", "1000");
        ENV.put("com.sun.jndi.dns.timeout.retries", "1");

        try {
            pattern = Pattern.compile(
                    "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
                    Pattern.CASE_INSENSITIVE);
            patternError = null;
        }
        catch (PatternSyntaxException e) {
            patternError = e;
            pattern = null;
        }
    }

    static {
        STARTS_WITH.add("none@");
        STARTS_WITH.add("yes@");
        STARTS_WITH.add("no@");
        STARTS_WITH.add("me@");
        STARTS_WITH.add("abuse@");
        STARTS_WITH.add("complaint@");
        STARTS_WITH.add("complaints@");
        STARTS_WITH.add("asdf@");
        STARTS_WITH.add("help@");
        STARTS_WITH.add("inquiry@");
        STARTS_WITH.add("news@");
        STARTS_WITH.add("newsletter@");
        STARTS_WITH.add("update@");
        STARTS_WITH.add("updates@");
        STARTS_WITH.add("foo@");
        STARTS_WITH.add("foofoo@");
        STARTS_WITH.add("fufu@");

        STARTS_WITH.add("postmaster@");
        STARTS_WITH.add("sales@");
        STARTS_WITH.add("support@");
        STARTS_WITH.add("legal@");
        STARTS_WITH.add("inquiries");

        CONTAINS.add("@none");
        CONTAINS.add("@fu.com");
        CONTAINS.add("@foo.com");
        CONTAINS.add("quote");
        CONTAINS.add("fake");
//        CONTAINS.add("test");
        CONTAINS.add("fuck");
        CONTAINS.add("shit@");
        CONTAINS.add("@shit");
        CONTAINS.add("joeblow");
        CONTAINS.add("@blow");
        CONTAINS.add("asshole");
    }

    public static boolean isValid(String email) {
        return isValid(email, false);
    }

    public static boolean isValid(String email, boolean checkDomainMxRecords) {

        if (email == null || "".equals(email)) {
            return false;
        }

        for (String s : STARTS_WITH) {
            if (email.startsWith(s)) {
                return false;
            }
        }

        for (String s : CONTAINS) {
            if (email.contains(s)) {
                return false;
            }
        }

        // Domain specific rules - recognizes the specific constraints of major ISPs
        try {
        	
        	String[] labels = UserProfile.parseMailLabels(email);
        	if (labels.length != 2) {
        		return false;
        	}

        	String domain = labels[1].toLowerCase();
        	if (domain.equals("gmail.com")) {
        		//  J_Smith@gmail.com
        		//  Gmail doesn't allow underscores
        		if (labels[0].contains("_")) {
        			return false;
        		}
        	}
        	else if (domain.equals("yahoo.com")) {
        		// Austin.K.Jordan@yahoo.com
        		// Yahoo doesn't allow multiple periods
        		int pos = labels[0].indexOf('.');
        		if (pos > 0 && labels[0].indexOf(".", pos + 1) > 0) {
        			return false;
        		}
        	}
        	else if (domain.equals("aol.com")) {
        		// 1981JohnS@aol.com
        		// AOL can't start with a number
        		if (Character.isDigit(labels[0].charAt(0))) {
        			return false;
        		}
        	}
        } catch (IllegalArgumentException e) {
        	LOG.info("Exception parsing the email.", e);
        	return false;
        }


        // generic email format match
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            if (!checkDomainMxRecords) {
                return true;
            }

            return isValidMX(email);
        }

        return false;
    }

    public static void main(String[] args) {
        String from = "martinholzner@gmail.com", to = "mholzner@aol.com";
        try {
            LOG.info(Smtp.validateSmtpRcpt(from, to));
            to = "mholzner@gmail.com";
            LOG.info(Smtp.validateSmtpRcpt(from, to));
        }
        catch (NamingException e) {
            LOG.error(e);
        }
        catch (IOException e) {
            LOG.error(e);
        }
//        String email = "test@test.com";
//        System.out.println("email=" + email + " -> " + isValid(email));
//
//        email = "d+a@foo.com";
//        System.out.println("email=" + email + " -> " + isValid(email));
//
//        email = "dmorin+t80@gmail.com";
//        System.out.println("email=" + email + " -> " + isValid(email, true));
//
//        email = "fuck@me.com";
//        System.out.println("email=" + email + " -> " + isValid(email));
//
//        email = "user1@gmal.com";
//        System.out.println("email=" + email + " -> " + isValid(email, true));
//
//        email = "one_two@gmail.com";
//        System.out.println("email=" + email + " -> " + isValid(email));
//
//        email = "one.two@yahoo.com";
//        System.out.println("email=" + email + " -> " + isValid(email, true));
//        email = "one.two.three@yahoo.com";
//        System.out.println("email=" + email + " -> " + isValid(email));
//
//        email = "one.two@aol.com";
//        System.out.println("email=" + email + " -> " + isValid(email, true));
//
//        email = "1.two@aol.com";
//        System.out.println("email=" + email + " -> " + isValid(email, true));
//
//        String[] domains =
//                {"www.secureleads.com", "secureleads.com", "www.secureleads.com", "fouyyt.org", "gmal.com", "yaho.com"};
//        for (String domain : domains) {
//            long t1 = System.currentTimeMillis();
//            try {
//                System.out.println(domain + " => " + dnsLookup(domain));
//            }
//            catch (NamingException e) {
//                e.printStackTrace();
//            }
//            System.out.println("time=" + (System.currentTimeMillis() - t1));
//        }
    }

    public static int dnsLookup(String hostName) throws NamingException {
        Integer cache = HOSTS.get(hostName.toLowerCase());
        if (cache != null) {
            return cache;
        }

//        DirContext dirContext = new InitialDirContext(ENV);
//        Attributes mxAttribs = dirContext.getAttributes(hostName, new String[]{"MX"});
        Attribute attribute = dnsLookup(hostName, new String[]{"MX"});
//        NamingEnumeration<?> enumeration = attribute.getAll();
//        while(enumeration.hasMoreElements()){
//            System.out.println("MX" + enumeration.nextElement().toString());
//        }
        cache = attribute == null ? 0 : attribute.size();
        HOSTS.put(hostName.toLowerCase(), cache);

        return cache;
    }

    public static Attribute dnsLookup(String hostName, String[] records) throws NamingException {
        DirContext dirContext = new InitialDirContext(ENV);
        Attributes mxAttribs = dirContext.getAttributes(hostName, records);

//        NamingEnumeration<?> enumeration = attribute.getAll();
//        while(enumeration.hasMoreElements()){
//            System.out.println("MX" + enumeration.nextElement().toString());
//        }

        return mxAttribs.get("MX");
    }

    public static boolean isValidMX(String emailOrHostName) {
        if (emailOrHostName == null || "".equals(emailOrHostName)) {
            return false;
        }

        String[] tokens = emailOrHostName.split("@");

        try {
            if (tokens.length == 1) {
                return dnsLookup(emailOrHostName) > 0;
            }
            else if (tokens.length == 2) {
                // use the host portion of an email address
                return dnsLookup(tokens[1]) > 0;
            }
        }
        catch (NamingException e) {
            return false;
        }

        return false;
    }

    private static class Smtp {

        private static final String CRLF = "\n";

        private DataOutputStream out;
        private DataInputStream in;
        private Socket smtpSocket;

        private Smtp(Socket smtpSocket, DataOutputStream out, DataInputStream in) {
            this.smtpSocket = smtpSocket;
            this.out = out;
            this.in = in;
        }

        public static boolean validateSmtpRcpt(String fromAddress, String emailAddress) throws NamingException, IOException {
            String name, esp;

            String[] fromTokens = UserProfile.parseMailLabels(fromAddress);
            String[] toTokens = UserProfile.parseMailLabels(emailAddress);
            if (fromTokens != null && fromTokens.length == 2 && toTokens != null && toTokens.length == 2) {
                return validateSmtpRcpt(fromTokens[0], fromTokens[1], toTokens[0], toTokens[1]);
            }

            return false;
        }

        private static boolean validateSmtpRcpt(String sender, String senderDomain, String account, String hostName) throws NamingException, IOException {
            // get the mx server
            Attribute attribute = dnsLookup(hostName, new String[]{"MX"});
            if (attribute != null) {
                NamingEnumeration<?> names = attribute.getAll();
                while (names.hasMoreElements()) {
                    String nameServer = (String) names.nextElement();
                    String[] prioAndHost = nameServer.split(" ");
                    if (prioAndHost.length == 2) {
                        String host = prioAndHost[1];

                        // if we have an MX server, check if the account is valid
                        Smtp smtp = connect(host);
                        smtp.sendCommand("HELO " + senderDomain, 250);
                        smtp.sendCommand("MAIL FROM:" + sender, 250);
                        smtp.sendCommand("RCPT TO:" + account + "@" + hostName, 250);
                        smtp.sendCommand("\n.", 250);

                        smtp.close();

                        return true;
                    }
                }
            }

            return false;
        }

        private void sendCommand(String command, int expectedReturnCode) throws IOException {
            // Write command to server
            out.writeBytes(command + CRLF);

            //read reply from server.
            String responseLine;
            while ((responseLine = in.readUTF()) != null) {
                LOG.info("Server: " + responseLine);
                if (responseLine.indexOf("Ok") != -1) {
                    break;
                }
            }
            System.err.println("Request: " + command);
            System.err.println("Return Code:" + responseLine);

            // Check that the server's reply code is the same as the parameter expectedReturnCode.
            // If not, throw an IOException.
            if (!responseLine.substring(0, 3).equals("" + expectedReturnCode)) {
                throw new IOException();
            }
        }

        private void close() throws IOException {
            sendCommand("RSET", 250);
            sendCommand("QUIT", 250);

            out.close();
            in.close();
            smtpSocket.close();
        }

        private static Smtp connect(String host) throws IOException {
            Socket s = new Socket(host, 25);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            return new Smtp(s, out, in);
        }
    }
}


/*
sample code for rcpt check:

@see http://www.javaworld.com/javaworld/jw-12-1996/jw-12-sockets.html?page=4
@see http://www.webdigi.co.uk/blog/2009/how-to-check-if-an-email-address-exists-without-sending-an-email/

String CRLF = "\n";
public void send(Envelope envelope) throws IOException
{
   connect(envelope);

   sendCommand("HELO "+ envelope.SenderDomain, 250);

   sendCommand("MAIL FROM:"+ envelope.Sender, 250);

   sendCommand("RCPT TO:" + envelope.Recipient, 250);
//    sendCommand("DATA", 354);
//    toServer.print(envelope.Message.Headers + CRLF);
//    toServer.print(envelope.Message.Body + CRLF);
//    toServer.print("." +CRLF);
   sendCommand("\n.", 250);
   close();
}

The above code calls this Function:

*/
