package com.pony.form;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 3/5/13
 * Time: 5:01 PM
 */
public class DateInputType extends InputType {
    protected DateInputType() {
        super("date");
    }

    @Override
    public boolean validate(String value) {
        return true;
    }
}
