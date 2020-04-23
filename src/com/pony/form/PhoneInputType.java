package com.pony.form;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 3/5/13
 * Time: 5:05 PM
 */
public class PhoneInputType extends InputType {
	private static final Log LOG = LogFactory.getLog(PhoneInputType.class);
	
    private static Pattern pattern;

    private static PatternSyntaxException patternError;

    static {
        try {
            pattern = Pattern.compile(
                    "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$",
                    Pattern.CASE_INSENSITIVE);
            patternError = null;
        }
        catch (PatternSyntaxException e) {
            LOG.error(e);
            patternError = e;
            pattern = null;
        }
    }

    public PhoneInputType() {
        super("phone");
    }

    @Override
    public boolean validate(String value) {
        if (value == null || value.length() < 10) {
            return false;
        }

        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
