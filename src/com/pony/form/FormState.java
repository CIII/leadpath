package com.pony.form;

import com.pony.models.FormModel;
import com.pony.validation.ValidationException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class to hold the users form data
 * PonyLeads 2013.
 * User: martin
 * Date: 2/24/13
 * Time: 8:19 PM
 */
public class FormState {
	private static final Log LOG = LogFactory.getLog(FormState.class);
	
    // actual attribute values
    // TODO: that should maybe be in the database?
    private final Form form;

    private final Map<FormStepAttribute, String> attributeValues = new HashMap<FormStepAttribute, String>();

    // keep track of enabled states
    private final Map<FormStep, Boolean> stepState = new HashMap<FormStep, Boolean>();
    private final Map<FormStepGroup, Boolean> groupState = new HashMap<FormStepGroup, Boolean>();
    private final Map<FormStepAttribute, Boolean> attributeState = new HashMap<FormStepAttribute, Boolean>();

    // keep track of the structure
    private final List<FormStep> formSteps = new LinkedList<FormStep>();
    private final Map<FormStep, List<FormStepGroup>> formStepGroups = new HashMap<FormStep, List<FormStepGroup>>();
    private final Map<FormStepGroup, List<FormStepAttribute>> formStepGroupAttributes = new HashMap<FormStepGroup, List<FormStepAttribute>>();
    private final Map<String, FormStepAttribute> attributeMap = new HashMap<String, FormStepAttribute>();
    private final Map<FormStepAttribute, String> errors = new HashMap<FormStepAttribute, String>();

    // TODO: form steps, form step groups with order and current state

    private FormStep formStep; // the current formStep
    private FormStepGroup formStepGroup; // the current form step group

    private FormState(Form form, List<FormStep> formSteps, Map<FormStep, Boolean> stepState, Map<FormStepGroup, Boolean> groupState, Map<FormStepAttribute, Boolean> attributeState, Map<FormStep, List<FormStepGroup>> formStepGroups, Map<FormStepGroup, List<FormStepAttribute>> formStepGroupAttributes, Map<FormStepAttribute, String> attributeValues) {
        this.form = form;
        this.stepState.putAll(stepState);
        this.groupState.putAll(groupState);
        this.attributeState.putAll(attributeState);

        this.formSteps.addAll(formSteps);
        this.formStepGroups.putAll(formStepGroups);
        this.formStepGroupAttributes.putAll(formStepGroupAttributes);
        this.attributeValues.putAll(attributeValues);

        // determine the first form step
        for (FormStep step : formSteps) {
            if (step.isInitiallyEnabled()) {
                formStep = step;
                break;
            }
        }

        // determine the current form step group
        if (formStep != null) {
            for (FormStepGroup group : formStepGroups.get(formStep)) {
                if (group.isInitiallyEnabled()) {
                    formStepGroup = group;
                    break;
                }
            }
        }

        // map attribute names
        for (Map.Entry<FormStepAttribute, Boolean> state : attributeState.entrySet()) {
            FormStepAttribute key = state.getKey();
            attributeMap.put(key.getName(), key);
        }
    }

    // create and initialize
    public static FormState create(Form form) throws NamingException, SQLException {
        //TODO : do not duplicate this: add readers to the form to get these once, and then hold on to them here

        Map<FormStep, Boolean> stepState = new HashMap<FormStep, Boolean>();
        Map<FormStepGroup, Boolean> groupState = new HashMap<FormStepGroup, Boolean>();
        Map<FormStepAttribute, Boolean> attributeState = new HashMap<FormStepAttribute, Boolean>();

        Map<FormStep, List<FormStepGroup>> formStepGroups = new HashMap<FormStep, List<FormStepGroup>>();
        Map<FormStepGroup, List<FormStepAttribute>> formStepGroupAttributes = new HashMap<FormStepGroup, List<FormStepAttribute>>();
        Map<FormStepAttribute, String> attributeValues = new HashMap<FormStepAttribute, String>();

        List<FormStep> steps = FormModel.getFormSteps(form);

        for (FormStep step : steps) {
            stepState.put(step, step.isInitiallyEnabled());

            List<FormStepGroup> stepGroups = FormModel.getFormStepGroups(step);
            formStepGroups.put(step, stepGroups);

            for (FormStepGroup group : stepGroups) {
                groupState.put(group, group.isInitiallyEnabled());

                List<FormStepAttribute> groupAttributes = FormModel.getFormStepGroupAttributes(group);
                formStepGroupAttributes.put(group, groupAttributes);

                for (FormStepAttribute attribute : groupAttributes) {
                    attributeState.put(attribute, attribute.isInitiallyEnabled());
                    // initialize the attribute values (the ones that have a default value)
                    if (attribute.getDefaultValue() != null) {
                        attributeValues.put(attribute, attribute.getDefaultValue());
                    }
                }
            }
        }

        return new FormState(form, steps, stepState, groupState, attributeState, formStepGroups, formStepGroupAttributes, attributeValues);
    }

    public FormStep getCurrentFormStep() {
        return formStep;
    }

    public FormStep getNextFormStep() {
        if (formSteps.size() == 0 || formStep == null) {
            return null;
        }

        int current = formSteps.indexOf(formStep);
        if (current < 0 && formSteps.size() > 0) {
            formStep = formSteps.get(0);
        }
        else {
            while (++current < formSteps.size()) {
                FormStep step = formSteps.get(current);
                if (stepState.get(step)) {
                    formStep = step;
                    break;
                }
            }
        }

        // determine the current form step group
        if (formStep != null) {
            for (FormStepGroup group : formStepGroups.get(formStep)) {
                if (groupState.get(group)) {
                    formStepGroup = group;
                    break;
                }
            }
        }

        return formStep;
    }

    public FormStep getPreviousFormStep() {
        if (formSteps.size() == 0 || formStep == null) {
            return null;
        }

        int current = formSteps.indexOf(formStep);
        if (current < 0 && formSteps.size() > 0) {
            formStep = formSteps.get(0);
        }
        else {
            while (--current >= 0) {
                FormStep step = formSteps.get(current);
                if (stepState.get(step)) {
                    formStep = step;
                    break;
                }
            }
        }

        // determine the current form step group
        if (formStep != null) {
            for (FormStepGroup group : formStepGroups.get(formStep)) {
                if (groupState.get(group)) {
                    formStepGroup = group;
                    break;
                }
            }
        }

        return formStep;
    }

    public FormStepGroup getNextFormStepGroup() {
        if (formStep != null && formStepGroup != null) {
            List<FormStepGroup> fsGroups = formStepGroups.get(formStep);
            int current = fsGroups.indexOf(formStepGroup);
            // when in doubt, go back to start
            if (current < 0 && fsGroups.size() > 0) {
                formStepGroup = fsGroups.get(0);
                return formStepGroup;
            }

            while (++current < fsGroups.size()) {
                FormStepGroup fsg = fsGroups.get(current);
                if (groupState.get(fsg)) {
                    formStepGroup = fsg;
                    return formStepGroup;
                }
            }
        }

        return null;
    }

    public FormStepGroup getPreviousFormStepGroup() {
        if (formStep != null && formStepGroup != null) {
            List<FormStepGroup> fsGroups = formStepGroups.get(formStep);
            int current = fsGroups.indexOf(formStepGroup);
            if (current < 0 && fsGroups.size() > 0) {
                formStepGroup = fsGroups.get(0);
                return formStepGroup;
            }

            while (--current > 0) {
                FormStepGroup fsg = fsGroups.get(current);
                if (groupState.get(fsg)) {
                    formStepGroup = fsg;
                    return formStepGroup;
                }
            }
        }

        return null;
    }

    public void removeAttributeValue(FormStepAttribute attribute) {
        attributeValues.remove(attribute);
    }

    public void setAttributeValue(FormStepAttribute attribute, String value) {
        attributeValues.put(attribute, value);
    }

    public String getAttributeValue(FormStepAttribute attribute) {
        return attributeValues.get(attribute);
    }

    public List<FormStepGroup> getFormStepGroups(FormStep formStep) {
        return formStepGroups.get(formStep);
    }

    public FormStepGroup getCurrentFormStepGroup(FormStep formStep) {
        if (formStep.getId().equals(formStepGroup.getFormStepId())) {
            return formStepGroup;
        }

        return null; // TODO:
    }

    /**
     * check weather or not the current group is the last one in its step
     *
     * @return
     */
    public boolean isLastFormStepGroup() {
        if (formStep != null && formStepGroup != null) {

            List<FormStepGroup> groups = formStepGroups.get(formStep);

            boolean foundCurrent = false;
            int index = -1;
            for (int i = 0; i < groups.size(); i++) {
                FormStepGroup group = groups.get(i);
                if (group.equals(formStepGroup)) {
                    foundCurrent = true;
                    index = i;
                    break;
                }
            }

            if (foundCurrent && index == (groups.size() - 1)) {
                return true;
            }
        }

        return false;
    }

    public FormStepGroup getCurrentFormStepGroup() {
        return formStepGroup;
    }

    public void nextFormStepGroup() {
        // determine the current form step group
        if (formStep != null && formStepGroup != null) {

            List<FormStepGroup> groups = formStepGroups.get(formStep);

            boolean foundCurrent = false;
            int index = -1;
            for (int i = 0; i < groups.size(); i++) {
                FormStepGroup group = groups.get(i);
                if (group.equals(formStepGroup)) {
                    foundCurrent = true;
                    continue;
                }
                if (foundCurrent && groupState.get(group)) {
                    formStepGroup = group;
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                // we didn't find it, or it was the last one (and there is no next group in this step)
                // TODO: do we want to advance to the next step (and the first group there?)
            }
        }
//        // determine the first form step
//        for (FormStep step : formSteps) {
//            if (step.isInitiallyEnabled()) {
//                formStep = step;
//                break;
//            }
//        }


        // map attribute names
        for (Map.Entry<FormStepAttribute, Boolean> state : attributeState.entrySet()) {
            FormStepAttribute key = state.getKey();
            attributeMap.put(key.getName(), key);
        }

    }

    public boolean isEnabled(FormStepGroup formStepGroup) {
        return groupState.get(formStepGroup);
    }

    public List<FormStepAttribute> getFormStepAttributes(FormStepGroup formStepGroup) {
        return formStepGroupAttributes.get(formStepGroup);
    }


    public Map<String, String> getAttributeValueMap() {
        Map<String, String> values = new HashMap<String, String>();

        for (Map.Entry<FormStepAttribute, String> entry : attributeValues.entrySet()) {
            values.put(entry.getKey().getName(), entry.getValue());
        }

        return values;
    }

    public Form getForm() {
        return form;
    }

    /**
     * check what attributes get posted in the request, and update their values, then generate a complete list of all attributes and their current value
     *
     * @param request
     * @return
     */
    public Map<String, String> updateFromRequest(HttpServletRequest request) throws ValidationException {

        errors.clear();

        //TODO
        Map<String, String> values = new HashMap<String, String>();

        String formKey = "f_" + form.getId() + "[";

        Enumeration params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = (String) params.nextElement();
            String value = request.getParameter(param);

            LOG.info("parsing form params: [" + param + "] = [" + value + "]");

            if (param.startsWith(formKey)) {
                String attrib = param.substring(formKey.length(), param.lastIndexOf("]"));
                values.put(attrib, value);
                FormStepAttribute formStepAttribute = attributeMap.get(attrib);
                if (formStepAttribute != null) {
                    // validate!
                    // if it's a select and the default is posted ...
                    if (formStepAttribute.getInputType().equals(FormStepAttribute.TYPE_SELECT) && value.equals(SelectValue.DEFAULT.getKey())) {
                        if (formStepAttribute.isRequired()) {
                            String validationMessage = formStepAttribute.getValidationMessage();
                            errors.put(formStepAttribute, validationMessage == null ? "Please select" : validationMessage);
                        }
                    }
                    else {
                        if (!formStepAttribute.getInputType().validate(value)) {
                            String validationMessage = formStepAttribute.getValidationMessage();
                            if (validationMessage == null) {
                                validationMessage = FormStepAttribute.DEFAULT_VALIDATION_MESSAGE;
                            }
                            errors.put(formStepAttribute, validationMessage);
                        }
                        attributeValues.put(formStepAttribute, value);
                    }
                }
            }
        }

        if (!errors.isEmpty()) {
            LOG.error("validation errors:");
            for (Map.Entry<FormStepAttribute, String> error : errors.entrySet()) {
                LOG.error("a=" + error.getKey() + ": " + error.getValue());
            }
            throw new ValidationException();
        }

        return values;
    }

    /**
     * special handler for boolean atributes (render as checkboxes)
     *
     * @param formStepAttribute
     * @return
     */
    public boolean isAttributeChecked(FormStepAttribute formStepAttribute) {
        if (formStepAttribute.getInputType().equals(FormStepAttribute.TYPE_CHECKBOX)) {
            String value = attributeValues.get(formStepAttribute);
            if (value != null && "1".equals(value)) {
                return true;
            }
        }
        return false;
    }


    /**
     * get the java script needed by this form (to be injected as <script></script> tag into the markup.
     *
     * @return
     */
    public String getScript() {
        StringBuilder html = new StringBuilder();

        html.append("<script>$(document).ready(function(){").append(Form.NEW_LINE);

        html.append("   $(\"#f_" + form.getId() + "\").validate(");

        String rules = getValidationRule();
        String messages = getValidationMessage();

        if (rules.length() > 0 || messages.length() > 0) {
//            html.append("{");
//        html.append();
//
//        html.append(",\r\n");
//
//        html.append(" errorPlacement: function(error, element) {\r\n");
//        // base errors
//        html.append("   error.fadeIn().insertAfter( element.parent().children('label') );\r\n");
//        // checkbox (registration) errors
//        html.append("   if ( element.attr(\"type\") == \"checkbox\" ) {");
//        html.append("       error.fadeIn().insertBefore( element.parent() );");
//        html.append("   }");
//        html.append(" },\r\n"); // end of errorPlacement
//
//        html.append(getValidationMessage());
//
//            html.append("}");
        }

        html.append(");").append(Form.NEW_LINE); // closing validate()

        // backend triggered validation error style
        html.append(" var emailError = $(\"label.error + input[type=text]\");");
        html.append(" if ( emailError ) {");
        html.append("    emailError.addClass(\"error\");");
        html.append(" };").append(Form.NEW_LINE);

        for (Map.Entry<FormStepAttribute, Boolean> entry : attributeState.entrySet()) {
            // only enabled ones
            if (entry.getValue()) {
                if (entry.getKey().isSubmitOnChange()) {
                    html.append("$(\"#").append(entry.getKey().getInputId()).append("\").change(function() {");
                    html.append("    $.get(");
                    html.append("       '/formstepattribute/").append(entry.getKey().getId()).append("',");
                    html.append("       {v:$(this).val()}");
                    html.append(", ");
                    html.append("    function(data) {");
//                    html.append("       for (var key in data){");
//                    html.append("           $('#' + key).html(data[key]);");
//                    html.append("       }");

                    html.append("       for(var key in data){");
                    html.append("          att = data[key];");
                    html.append("          if(key == 'sf'){");
                    html.append("             for(var sfKey in att){");
                    html.append("                $('#' + sfKey).html(att[sfKey]);");
                    html.append("             }");
                    html.append("          }");
                    html.append("          if(key == 'gf'){");
                    html.append("             for(var gfKey in att){");
                    html.append("                $('#' + gfKey).attr('navigable', att[gfKey]);");
//                    html.append("                if(att[gfKey] == 'false'){");
//                    html.append("                   $('#' + gfKey).attr('style', 'display:none;');");
//                    html.append("                }");
//                    html.append("                else{");
//                    html.append("                   $('#' + gfKey).removeAttr('style');");
//                    html.append("                }");
                    html.append("             }");
                    html.append("          }");
                    html.append("       }");
                    html.append("    },");
                    html.append("    \"json\"");
                    html.append("    );");
                    html.append("});");
                }
            }
        }

        html.append("});</script>").append(Form.NEW_LINE); // closing ready()

        return html.toString();
    }

    private String getValidationRule() {
        StringBuilder rules = new StringBuilder();
        rules.append(" rules: {");
        boolean isFirst = true;

        for (FormStepAttribute fa : formStepGroupAttributes.get(formStepGroup)) {
            String rule = fa.getValidationRule();
            if (rule == null) {
                continue;
            }

            if (isFirst) {
                isFirst = false;
            }
            else {
                rules.append(",");
            }

            rules.append(rule);
        }

        rules.append("}"); // end rules

        return rules.toString();
    }

    private String getValidationMessage() {
        StringBuilder messages = new StringBuilder();
        messages.append(" messages: {");
        boolean isFirst = true;
        for (FormStepAttribute fa : formStepGroupAttributes.get(formStepGroup)) {
            String msg = fa.getValidationMessage();
            if (msg == null) {
                continue;
            }

            if (isFirst) {
                isFirst = false;
            }
            else {
                messages.append(",");
            }

            messages.append(msg);
        }
        messages.append("}"); // closing messages

        return messages.toString();
    }

    private static final String FORM_STATE = "form_state";

    public static FormState getState(Form form, HttpServletRequest request) throws NamingException, SQLException {
        String sessionKey = FORM_STATE + "_" + form.getId();
        FormState formState = (FormState) request.getSession(true).getAttribute(sessionKey);
        if (formState == null) {
            formState = FormState.create(form);
            request.getSession().setAttribute(sessionKey, formState);
        }

        return formState;
    }

    public String getError(FormStepAttribute attribute) {
        return errors.get(attribute);
    }

    public Map<Long, Boolean> updateAttributeFilters(Map<FormStepAttribute, Boolean> filteredAttributes) {
        Map<Long, Boolean> states = new HashMap<Long, Boolean>();
        for (Map.Entry<FormStepAttribute, Boolean> attribute : filteredAttributes.entrySet()) {
            attributeState.put(attribute.getKey(), attribute.getValue());
            states.put(attribute.getKey().getId(), attribute.getValue());
        }
        return states;
    }

    public Map<Long, Boolean> updateStepFilters(Map<FormStep, Boolean> filteredSteps) {
        Map<Long, Boolean> states = new HashMap<Long, Boolean>();
        for (Map.Entry<FormStep, Boolean> entry : filteredSteps.entrySet()) {
            stepState.put(entry.getKey(), entry.getValue());
            states.put(entry.getKey().getId(), entry.getValue());
        }
        return states;
    }

    public Map<Long, Boolean> updateGroupFilters(Map<FormStepGroup, Boolean> filteredGroups) {
        Map<Long, Boolean> states = new HashMap<Long, Boolean>();
        for (Map.Entry<FormStepGroup, Boolean> entry : filteredGroups.entrySet()) {
            groupState.put(entry.getKey(), entry.getValue());
            states.put(entry.getKey().getId(), entry.getValue());
        }
        return states;
    }
}
