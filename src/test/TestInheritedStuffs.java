package test;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ArbVentures 2013.
 * User: martin
 * Date: 3/11/14
 * Time: 1:47 PM
 */
public class TestInheritedStuffs extends TestStuffs {
	private static final Log LOG = LogFactory.getLog(TestInheritedStuffs.class);

    static {
        EXCLUDED_TOKENS.add("inherit1");
        EXCLUDED_TOKENS.add("inherit2");

        HOSTS.put("h1", 1);
        HOSTS.put("h2", 1);
    }
    public static void main(String[] args){

        TestInheritedStuffs tis = new TestInheritedStuffs();
        LOG.debug(EXCLUDED_TOKENS.size());
        LOG.debug(HOSTS.size());

        tis = new TestInheritedStuffs();
        LOG.debug(EXCLUDED_TOKENS.size());
        LOG.debug(HOSTS.size());
    }
}
