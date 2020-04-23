package com.pony.leadtypes;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 2/11/13
 * Time: 5:42 PM
 */
public class Attribute {
    private Long id;
    private String name, inputType;
    private boolean isLarge = false;

    private Attribute(Long id, String name, String inputType, boolean isLarge) {
        this.id = id;
        this.name = name;
        this.inputType = inputType;
        this.isLarge = isLarge;
    }

    public static Attribute create(Long id, String name, String inputType, boolean isLarge) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        return new Attribute(id, name, inputType, isLarge);
    }

    public static Attribute create(Long id, String name, String inputType) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        return new Attribute(id, name, inputType, false);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInputType() {
        return inputType;
    }

    public boolean isLarge() {
        return isLarge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Attribute attribute = (Attribute) o;
        return name.equals(attribute.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", inputType='" + inputType + '\'' +
                '}';
    }
}
