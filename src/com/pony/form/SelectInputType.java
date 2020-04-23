package com.pony.form;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 3/5/13
 * Time: 5:03 PM
 */
public class SelectInputType extends InputType {
    public SelectInputType() {
        super("select");
    }

    @Override
    public boolean validate(String value) {
        return true;
    }
}
