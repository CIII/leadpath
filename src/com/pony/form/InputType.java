package com.pony.form;

import java.util.HashMap;
import java.util.Map;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 3/5/13
 * Time: 4:56 PM
 */
public abstract class InputType {

    private static final Map<String, InputType> types = new HashMap<String, InputType>();

    private final String name;

    protected InputType(String name) {
        this.name = name;
        types.put(name, this);
    }

    public String getName() {
        return name;
    }

    public static InputType parse(String name) {
        return types.get(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InputType inputType = (InputType) o;
        return name.equals(inputType.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    public abstract boolean validate(String value);
//
//        if (name.equals("email")) {
//            if (AddressValidator.isValid(value, true)) {
//                return null;
//            }
//        }
//        else if (name.equals("phone")) {
//            if (PhoneNumberValidator.isValid(value)) {
//                return null;
//            }
}