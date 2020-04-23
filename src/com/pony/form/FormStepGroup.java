package com.pony.form;

import javax.naming.NamingException;
import java.sql.SQLException;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 2/20/13
 * Time: 1:02 PM
 */
public class FormStepGroup {
    private final Long id, formStepId;
    private final String name;
    private final int sortOrder;
    private final boolean initiallyEnabled;

    private FormStepGroup(Long id, Long formStepId, String name, int sortOrder, boolean initiallyEnabled) {
        this.id = id;
        this.name = name;
        this.sortOrder = sortOrder;
        this.initiallyEnabled = initiallyEnabled;
        this.formStepId = formStepId;
    }

    public static FormStepGroup create(Long id, Long formStepId, String name, int sortOrder, boolean initiallyEnabled) {
        return new FormStepGroup(id, formStepId, name, sortOrder, initiallyEnabled);
    }

    public Long getId() {
        return id;
    }

    public Long getFormStepId() {
        return formStepId;
    }

    public String getName() {
        return name;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public boolean isInitiallyEnabled() {
        return initiallyEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormStepGroup that = (FormStepGroup) o;

        if (!formStepId.equals(that.formStepId)) {
            return false;
        }
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = formStepId.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FormStepGroup{" +
                "id=" + id +
                ", formStepId=" + formStepId +
                ", name='" + name + '\'' +
                ", sortOrder=" + sortOrder +
                ", initiallyEnabled=" + initiallyEnabled +
                '}';
    }

    public StringBuilder getMarkup(Form form, FormStepGroup formStepGroup, FormImpression formImpression, FormState formState) throws NamingException, SQLException {
        StringBuilder html = new StringBuilder();

        for (FormStepAttribute attribute : formState.getFormStepAttributes(this)) {
            html.append(attribute.getMarkup(form, formImpression, formState)).append(Form.NEW_LINE);
        }

        return html;
    }
}