package com.pony.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by martin on 10/7/16.
 */
public class MathTool {
	private static final Log LOG = LogFactory.getLog(MathTool.class);
	
    /**
     * get a big decimal instance with two decimal point precision
     *
     * @param price the price as String
     * @return
     */
    public static BigDecimal getBigDecimalPrice(String price) {
        if (price == null) {
            return null;
        }
        return new BigDecimal(price.trim()).setScale(2, RoundingMode.HALF_DOWN);
    }

    /**
     * get a big decimal instance with two decimal point precision
     *
     * @param price the price as BigDecimal
     * @return
     */
    public static BigDecimal getBigDecimalPrice(BigDecimal price) {
        if (price == null) {
            return null;
        }
        return price.setScale(2, RoundingMode.HALF_DOWN);
    }

    public static void main(String[] args) {

        // Tests
        BigDecimal a = new BigDecimal("100.01");
        BigDecimal b = new BigDecimal(100.01);

        LOG.debug("raw: " + a.equals(b));

        BigDecimal aa = getBigDecimalPrice(a);
        BigDecimal bb = getBigDecimalPrice(b);
        BigDecimal cc = getBigDecimalPrice("100.0101");

        LOG.debug("step2: " + aa.equals(bb));
        LOG.debug("step3: " + aa.equals(cc));
    }
}
