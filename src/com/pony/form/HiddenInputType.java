package com.pony.form;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 3/5/13
 * Time: 5:02 PM
 */
public class HiddenInputType extends InputType {
    public HiddenInputType() {
        super("hidden");
    }

    @Override
    public boolean validate(String value) {
        return true;
    }
}
