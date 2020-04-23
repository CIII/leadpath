package com.pony.form;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 2/20/13
 * Time: 10:32 PM
 */
public class SelectValue {
    private final String key, value;
    private final Long id;
    private final boolean preSelected;

    public static SelectValue DEFAULT = new SelectValue(null, "-1", "Please select", true);

    private SelectValue(Long id, String key, String value, boolean preSelected) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.preSelected = preSelected;
    }

    public static SelectValue create(Long id, String key, String value, boolean preSelected) {
        return new SelectValue(id, key, value, preSelected);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Long getId() {
        return id;
    }

    public boolean isPreSelected() {
        return preSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SelectValue that = (SelectValue) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "SelectValue{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", id=" + id +
                '}';
    }
}
