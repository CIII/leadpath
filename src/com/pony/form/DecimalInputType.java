package com.pony.form;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 3/5/13
 * Time: 5:05 PM
 */
public class DecimalInputType extends InputType {
    public DecimalInputType() {
        super("decimal");
    }

    @Override
    public boolean validate(String value) {
        return true;
    }
}
