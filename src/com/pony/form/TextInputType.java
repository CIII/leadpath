package com.pony.form;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 3/5/13
 * Time: 5:03 PM
 */
public class TextInputType extends InputType {
    public TextInputType() {
        this("text");
    }
    public TextInputType(String name) {
        super(name);
    }

    @Override
    public boolean validate(String value) {
        return true;
    }
}
