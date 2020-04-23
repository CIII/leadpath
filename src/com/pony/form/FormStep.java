package com.pony.form;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 2/20/13
 * Time: 12:58 PM
 */
public class FormStep {
    private final Long id, formId;
    private final int sortOrder;
    private final boolean initiallyEnabled, fadeEnabled;
    private final String name;

    private FormStep(Long id, Long formId, String name, int sortOrder, boolean initiallyEnabled, boolean fadeEnabled) {
        this.id = id;
        this.formId = formId;
        this.name = name;
        this.sortOrder = sortOrder;
        this.initiallyEnabled = initiallyEnabled;
        this.fadeEnabled = fadeEnabled;
    }

    public static FormStep create(long id, Long formId, String name, int sortOrder, boolean initiallyEnabled, boolean fadeEnabled) {
        return new FormStep(id, formId, name, sortOrder, initiallyEnabled, fadeEnabled);
    }

    public Long getId() {
        return id;
    }

    public Long getFormId() {
        return formId;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public boolean isInitiallyEnabled() {
        return initiallyEnabled;
    }

    public boolean isFadeEnabled() {
        return fadeEnabled;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormStep formStep = (FormStep) o;

        if (!formId.equals(formStep.formId)) {
            return false;
        }
        if (!name.equals(formStep.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = formId.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FormStep{" +
                "id=" + id +
                ", formId=" + formId +
                ", sortOrder=" + sortOrder +
                ", initiallyEnabled=" + initiallyEnabled +
                ", fadeEnabled=" + fadeEnabled +
                ", name='" + name + '\'' +
                '}';
    }

    public StringBuilder getMarkup(Form form, FormImpression formImpression, FormState formState) throws NamingException, SQLException {
        StringBuilder html = new StringBuilder();

        // if fade is enabled, we render all the groups;
        // TODO: make sure the needed js is injected
        if (isFadeEnabled()) {

//TODO: alternatively, we could use the accordion to switch between wizard pages
/*
 $(function() {
$( "#accordion" ).accordion();
});
// and
<div id="accordion">
<h3>Section 1</h3>
<div>
<p>...
 </p>
</div>
<h3>Section 2</h3>
<div>
<p>...
             */

            // render all groups
            List<FormStepGroup> groups = formState.getFormStepGroups(this);
            for (int i = 0; i < groups.size(); i++) {
                FormStepGroup group = groups.get(i);

                // wrap each group in a wizard_page div, only showing the current one
                html.append("<div class=\"wizard_page\"");
                html.append(" navigable=\"").append(formState.isEnabled(group) ? "true" : "false").append("\"");

                html.append(" id=\"").append(group.getId()).append("\"");

                if (!group.equals(formState.getCurrentFormStepGroup(this))) {
                    html.append(" style=\"display:none;\"");
                }
                html.append(">").append(Form.NEW_LINE);

                html.append(group.getMarkup(form, group, formImpression, formState)).append(Form.NEW_LINE);

                // add the submit button to the last group (so it stays hidden until all the other groups were shown
                if (i == groups.size() - 1) {
                    html.append("<fieldset id=\"form-submit\">");
                    html.append("<button title=\"").append(form.getName()).append("\" id=\"formSubmit\" type=\"submit\">").append(form.getSubmitText()).append("</button>");
                    html.append("</fieldset>").append(Form.NEW_LINE);
                }

                // render nav helpers
                if (groups.size() > 1) {
                    if (i == 0) {
                        // only next
                        html.append("<a href=\"#\" class=\"nextPage\">Next</a>").append(Form.NEW_LINE);
                    }
                    else if (i == groups.size() - 1) {
                        // only prev
                        html.append("<a href=\"#\" class=\"previousPage\">Previous</a>").append(Form.NEW_LINE);
                    }
                    else {
                        // both
                        html.append("<a href=\"#\" class=\"previousPage\">Previous</a> | <a href=\"#\" class=\"nextPage\">Next</a>").append(Form.NEW_LINE);
                    }
                }

                //TODO
//                // if there is more then one attribute, or if the only attribute doesn't have a js hook, render a next / previous link so the user can navigate between the groups
//                List<FormStepAttribute> formStepAttributes = formState.getFormStepAttributes(group);
//                if(formStepAttributes.size() > 1){
//                    // check if there are attributes with hook
////                    int onChangeCount = 0;
////                    for(FormStepAttribute a : formStepAttributes){
////                        if(a.isSubmitOnChange()){
////                            onChangeCount++;
////                        }
////                    }
//
//
//
//                }
                html.append("</div>");
            }
        }
        else {
            // render only the current group
            FormStepGroup group = formState.getCurrentFormStepGroup(this);
            boolean isLastGroup = formState.isLastFormStepGroup();
            html.append(group.getMarkup(form, group, formImpression, formState)).append(Form.NEW_LINE);

            html.append("<fieldset id=\"form-submit\">");
            if (!isLastGroup) {
                html.append("<input type=\"hidden\" name=\"form_step_group_id\" value=\"").append(group.getId()).append("\"></input>").append(Form.NEW_LINE);
            }
            html.append("<button title=\"").append(form.getName()).append("\" id=\"formSubmit\" type=\"submit\">");
            if (isLastGroup) {
                html.append(form.getSubmitText());
            }
            else {
                html.append(">>next");
            }

            html.append("</button>");
            html.append("</fieldset>").append(Form.NEW_LINE);

//                // render nav helpers as buttons with post action
//                List<FormStepGroup> groups = formState.getFormStepGroups(this);
//                if (groups.size() > 1) {
//                    int currentIndex = groups.get(group);
//
//                    if (currentIndex == 0) {
//                        // only next
//                        html.append("<a href=\"#\" class=\"nextPage\">Next</a>").append(Form.NEW_LINE);
//                    }
//                    else if (currentIndex == groups.size() - 1) {
//                        // only prev
//                        html.append("<a href=\"#\" class=\"previousPage\">Previous</a>").append(Form.NEW_LINE);
//                    }
//                    else {
//                        // both
//                        html.append("<a href=\"#\" class=\"previousPage\">Previous</a> | <a href=\"#\" class=\"nextPage\">Next</a>").append(Form.NEW_LINE);
//                    }
//                }
//            }
        }

        return html;
    }
}
