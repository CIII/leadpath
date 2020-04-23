package com.pony.form;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 3/5/13
 * Time: 5:04 PM
 */
public class IntegerInputType extends InputType {
    public IntegerInputType() {
        super("integer");
    }

    @Override
    public boolean validate(String value) {
        try {
            Double dblValue = Double.valueOf(value);
            if (Double.isNaN(dblValue)) {
                return false;
            }
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
