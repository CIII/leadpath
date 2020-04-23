package com.pony.form;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 3/5/13
 * Time: 5:05 PM
 */
public class CheckboxInputType extends InputType {
    public CheckboxInputType() {
        super("checkbox");
    }

    @Override
    public boolean validate(String value) {
        return true;
    }
}
