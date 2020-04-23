package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 3/11/14
 * Time: 1:45 PM
 */
public class TestStuffs {
	private static final Log LOG = LogFactory.getLog(TestStuffs.class);

    protected static final List<String> EXCLUDED_TOKENS = new ArrayList<String>();
    protected static final Map<String, Integer> HOSTS = new HashMap<String, Integer>();

    static {
        EXCLUDED_TOKENS.add("maxRows");
        EXCLUDED_TOKENS.add("delay");
        EXCLUDED_TOKENS.add("ref");
        EXCLUDED_TOKENS.add("domtok");
        EXCLUDED_TOKENS.add("username");
        EXCLUDED_TOKENS.add("password");
        EXCLUDED_TOKENS.add("email");
        EXCLUDED_TOKENS.add("listid");

        HOSTS.put("h1", 1);
        HOSTS.put("h2", 2);
    }


    public static void main(String[] args){
        TestStuffs ts = new TestStuffs() ;
        LOG.debug(EXCLUDED_TOKENS.size());


        ts = new TestStuffs() ;
        LOG.debug(EXCLUDED_TOKENS.size());

        ts = new TestStuffs() ;
        LOG.debug(EXCLUDED_TOKENS.size());
    }
}
