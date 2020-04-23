package com.pony.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 10/21/13
 * Time: 4:21 PM
 */
public class StringTools {
	private static final Log LOG = LogFactory.getLog(StringTools.class);
	
    public static String capitalize(String arg) {
        if (arg == null) {
            return null;
        }
        StringBuilder res = new StringBuilder();

        boolean wordStart = true;
        for (char c : arg.toCharArray()) {
            if (c == ' ') {
                if (res.length() > 0 && res.charAt(res.length() - 1) != ' ') {
                    res.append(c);
                }
                wordStart = true;
            }
            else if (wordStart) {
                res.append(Character.toUpperCase(c));
                wordStart = false;
            }
            else {
                res.append(Character.toLowerCase(c));
            }
        }
        return res.toString();
    }

    public static String allCaps(String arg) {
        if (arg == null) {
            return null;
        }

        StringBuilder res = new StringBuilder();
        for (char c : arg.toCharArray()) {
            if (c == ' ') {
                if (res.length() > 0 && res.charAt(res.length() - 1) != ' ') {
                    res.append(Character.toUpperCase(c));
                }
            }
            else {
                res.append(Character.toUpperCase(c));
            }
        }
        return res.toString();
    }

    public static void main(String[] args) {
        String s1 = " somCasesAreConFUSing", s2 = "ORAREthey ", s3 = "Bernardi Hyundai of Brockton ", s4 = " HYUNDAI VILLAGE", s5 = "Bernardi   Hyundai  of Brockton";
        LOG.debug(s1 + "=>" + allCaps(s1));
        LOG.debug(s1 + "=>" + capitalize(s1));

        LOG.debug(s2 + "=>" + allCaps(s2));
        LOG.debug(s2 + "=>" + capitalize(s2));

        LOG.debug(s3 + "=>" + allCaps(s3));
        LOG.debug(s3 + "=>" + capitalize(s3));

        LOG.debug(s4 + "=>" + allCaps(s4));
        LOG.debug(s4 + "=>" + capitalize(s4));

        LOG.debug(s5 + "=>" + allCaps(s5));
        LOG.debug(s5 + "=>" + capitalize(s5));

        String s6 = "OLDSMOBILE", s7 = "INTRIGUE GLS", s8 ="02421";
        LOG.debug(s6 + "=>" + capitalize(s6));
        LOG.debug(s7 + "=>" + capitalize(s7));
        LOG.debug(s8 + "=>" + capitalize(s8));
    }
}
