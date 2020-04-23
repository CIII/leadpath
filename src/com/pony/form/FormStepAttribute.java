package com.pony.form;

import com.pony.leadtypes.Attribute;
import com.pony.models.SelectValueModel;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is an attribute assigned to a form (within a group within a step)
 * PonyLeads 2013.
 * User: martin
 * Date: 2/20/13
 * Time: 1:08 PM
 */
public class FormStepAttribute {
	private static final Log LOG = LogFactory.getLog(FormStepAttribute.class);

    private final Long id, formId, formStepId, formStepGroupId;
    private final boolean required, initiallyEnabled, submitOnChange, multiSelect;
    private final Attribute attribute;
    private final int inputSize, sortOrder;
    private final String label, defaultValue, placeholder, validationMessage;

    private InputType inputType;

    public static final String DEFAULT_VALIDATION_MESSAGE = "is invalid";

    public static final InputType TYPE_HIDDEN = new HiddenInputType();
    public static final InputType TYPE_TEXT = new TextInputType();
    public static final InputType TYPE_AREA = new TextInputType("area");
    public static final InputType TYPE_SELECT = new SelectInputType();
    public static final InputType TYPE_DATE = new DateInputType();
    public static final InputType TYPE_INTEGER = new IntegerInputType();
    public static final InputType TYPE_DECIMAL = new DecimalInputType();
    public static final InputType TYPE_CHECKBOX = new CheckboxInputType();
    public static final InputType TYPE_PHONE = new PhoneInputType();
    public static final InputType TYPE_ZIPCODE = new ZipcodeInputType();

    public static FormStepAttribute create(Long id, Long formStepGroupId, Long formStepId, Long formId, Attribute attribute, boolean required, int inputSize, String label, String defaultValue, String placeholder, String validationMessage, int sortOrder, boolean initialEnabledState, boolean submitOnChange) {
        InputType type = InputType.parse(attribute.getInputType());
        if (type == null) {
            throw new IllegalArgumentException("invalid input type");
        }
        return new FormStepAttribute(id, formStepGroupId, formStepId, formId, attribute, type, required, inputSize, label, defaultValue, placeholder, validationMessage, sortOrder, initialEnabledState, submitOnChange);
    }

    private FormStepAttribute(Long id, Long formStepGroupId, Long formStepId, Long formId, Attribute attribute, InputType inputType, boolean required, int inputSize, String label, String defaultValue, String placeholder, String validationMessage, int sortOrder, boolean initialEnabledState, boolean submitOnChange) {
        this.id = id;
        this.formId = formId;
        this.formStepId = formStepId;
        this.formStepGroupId = formStepGroupId;
        this.attribute = attribute;
        this.inputType = inputType;
        this.required = required;
        this.inputSize = inputSize;
        this.label = label;
        this.defaultValue = defaultValue;
        this.placeholder = placeholder;
        this.validationMessage = validationMessage;
        this.initiallyEnabled = initialEnabledState;
        this.sortOrder = sortOrder;
        this.submitOnChange = submitOnChange;
        this.multiSelect = false; //TODO: add to db
    }

    public String getInputName() {
        return "f_" + formId + "[" + attribute.getName() + "]";
    }

//    public static Map<String, String> getFormAttributeValues(Form form, HttpServletRequest request) {
//
//        Map<String, String> values = new HashMap<String, String>();
//
//        String formKey = "" + form.getId() + "[";
//
//        Enumeration params = request.getParameterNames();
//        while (params.hasMoreElements()) {
//            String param = (String) params.nextElement();
//            String value = request.getParameter(param);
//
//            System.out.println("parsing form params: [" + param + "] = [" + value + "]");
//
//            if (param.startsWith(formKey)) {
//                String attrib = param.substring(formKey.length(), param.lastIndexOf("]"));
//                values.put(attrib, value);
//            }
//        }
//
//        return values;
//    }

    public String getInputId() {
        return "" + formId + "_" + attribute.getName();
    }

    public boolean isSubmitOnChange() {
        return submitOnChange;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    @Override
    public String toString() {
        return "FormStepAttribute{" +
                "id=" + id +
                ", attribute='" + attribute + '\'' +
                ", inputType=" + inputType +
                ", formId=" + formId +
                ", formStepId=" + formStepId +
                ", formStepGroupId=" + formStepGroupId +
                ", required=" + required +
                ", initiallyEnabled=" + initiallyEnabled +
                ", submitOnChange=" + submitOnChange +
                ", multiSelect=" + multiSelect +
                ", sortOrder=" + sortOrder +
                ", defaultValue='" + defaultValue + '\'' +
                ", placeholder='" + placeholder + '\'' +
                ", validationMessage='" + validationMessage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormStepAttribute that = (FormStepAttribute) o;

        if (!attribute.equals(that.attribute)) {
            return false;
        }
        if (!formId.equals(that.formId)) {
            return false;
        }
        if (!formStepGroupId.equals(that.formStepGroupId)) {
            return false;
        }
        if (!formStepId.equals(that.formStepId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = formId.hashCode();
        result = 31 * result + formStepId.hashCode();
        result = 31 * result + formStepGroupId.hashCode();
        result = 31 * result + attribute.hashCode();
        return result;
    }

    public String getMarkup(Form form, FormImpression formImpression, FormState formState) throws NamingException, SQLException {

        LOG.info("render formStepAttribute: " + this);

        // render a fieldset
        StringBuilder html = new StringBuilder();
        html.append("<fieldset class=\"").append(attribute.getName()).append("\">\r\n");

        String attributeValue = formState.getAttributeValue(this);
        String errorMessage = formState.getError(this);

        if (inputType.equals(TYPE_CHECKBOX)) {
//            <fieldset id="opt-in">
//                <label for="registration_confirm">
//                <input type="checkbox" value="1" name="registration[confirm]" id="registration_confirm" class="required" checked="checked">
//                    Please check the box to receive free coupons, updates and offers targeted towards your interests, sent by Coupon-Hound.com.
//                    <a onclick="window.open(this.href,'_blank','width=590,height=700,scrollbars=yes');return false;" href="/coupons/privacy?pop=true">Privacy Policy</a>
//                </label>
//            </fieldset>

            List<String> classes = new ArrayList<String>();

            boolean checked = formState.isAttributeChecked(this);

            // TODO: add script for tooltips :
/*
            $(function() {
                $( document ).tooltip();
            });

            and add a title attribute to the elements that should show a tooltip
*/
            html.append(" <label for=\"").append(getInputId()).append("\">");
//            html.append(" <input type=\"").append(inputType).append("\" value=\"").append(defaultValue).append("\" name=\"").append(getInputName()).append("\" id=\"").append(getInputId()).append("\"");
            if (errorMessage != null) {
                classes.add("error");
                html.append("<label class=\"error\" for=\"").append(getInputId()).append("\" generated=\"true\" style=\"display: block;\">").append(errorMessage).append("</label>").append(Form.NEW_LINE);
            }
            html.append(" <input type=\"").append(inputType).append("\" name=\"").append(getInputName()).append("\" id=\"").append(getInputId()).append("\"");
            if (attributeValue == null) {
                attributeValue = "0";
            }
            html.append(" value=\"").append(attributeValue).append("\"");

            if (required) {
                classes.add("required");
            }
            html.append(" class=\"");
            for (int i = 0; i < classes.size(); i++) {
                if (i > 0) {
                    html.append(" ");
                }
                html.append(classes.get(i));
            }
            html.append("\"");

            if (checked) {
                html.append(" checked=\"checked\">\r\n");
            }
            html.append(getLabel());
            html.append("</label>");
        }
        else if (inputType.equals(TYPE_SELECT)) {
            List<String> classes = new ArrayList<String>();
            // TODO: making sure that if this attribute is target of a filter, we filter the options accordingly
            html.append(" <label for=\"").append(getInputId()).append("\">").append(getLabel()).append("</label>").append(Form.NEW_LINE);
            if (errorMessage != null) {
                classes.add("error");
                html.append("<label class=\"error\" for=\"").append(getInputId()).append("\" generated=\"true\" style=\"display: block;\">").append(errorMessage).append("</label>").append(Form.NEW_LINE);
            }
            html.append("<select name=\"").append(getInputName()).append("\" id=\"").append(getInputId()).append("\"");
            if (isMultiSelect()) {
                html.append(" multiple=\"true\"");
            }
            html.append(" class=\"");
            for (int i = 0; i < classes.size(); i++) {
                if (i > 0) {
                    html.append(" ");
                }
                html.append(classes.get(i));
            }
            html.append("\"");
            html.append(">");

            //TODO: check the state for a 'master' attribute of this attribute, and if there is a filter between those two, make sure we render the correct dependent
            Map<String, SelectValue> options = SelectValueModel.findAll(this);

            StringBuilder tmp = new StringBuilder();
            boolean preselected = false;
            for (Map.Entry<String, SelectValue> entry : options.entrySet()) {
                SelectValue value = entry.getValue();
                tmp.append("<option value=\"").append(value.getId()).append("\"");
                if (value.isPreSelected() && attributeValue == null) {
                    preselected = true;
                    tmp.append(" selected=\"selected\"");
                }
                // for select options values we use the select_values.id
                else if (value.getId().toString().equals(attributeValue)) {
                    preselected = true;
                    tmp.append(" selected=\"selected\"");
                }
                tmp.append(">").append(value.getValue());

                tmp.append("</option>").append(Form.NEW_LINE);
            }

            // if nothing is preselected, add a default option and make it preselected
            if (!preselected && options.size() > 0) {
                // add a default that we can check for on submit
                html.append("<option value=\"-1\" selected=\"selected\">");
                html.append("Please select").append("</option>").append(Form.NEW_LINE);
            }
            html.append(tmp);

            html.append("</select>").append(Form.NEW_LINE);
        }
        else if (inputType.equals(TYPE_DATE)) {
            List<String> classes = new ArrayList<String>();
/*
            <script>
                    $(function() {
                $( "#datepicker" ).datepicker();
            });
            </script>
            <p>Date: <input type="text" id="datepicker" /></p>
*/
            // use jquery-ui for date picker
            html.append("<script>").append(Form.NEW_LINE);
            html.append("    $(function() {").append(Form.NEW_LINE);
            html.append("       $( \"#").append(getInputId()).append("\" ).datepicker();").append(Form.NEW_LINE);
            html.append("    });").append(Form.NEW_LINE);
            html.append("</script>").append(Form.NEW_LINE);

            html.append(" <label for=\"").append(getInputId()).append("\">").append(getLabel()).append("</label>").append(Form.NEW_LINE);
            if (errorMessage != null) {
                classes.add("error");
                html.append("<label class=\"error\" for=\"").append(getInputId()).append("\" generated=\"true\" style=\"display: block;\">").append(errorMessage).append("</label>").append(Form.NEW_LINE);
            }
            html.append(" <input type=\"").append(TYPE_TEXT).append("\"");
            html.append(" name=\"").append(getInputName()).append("\" id=\"").append(getInputId()).append("\"");
            if (attributeValue != null) {
                html.append(" value=\"").append(attributeValue).append("\"");
            }
            if (inputSize > 0) {
                html.append(" size=\"").append(inputSize).append("\"");
            }

            if (placeholder != null && attributeValue == null) {
                html.append(" placeholder=\"").append(getPlaceholder()).append("\"");
            }

            if (required) {
                classes.add("required");
            }
            html.append(" class=\"");
            for (int i = 0; i < classes.size(); i++) {
                if (i > 0) {
                    html.append(" ");
                }
                html.append(classes.get(i));
            }
            html.append("\"");

            if (defaultValue != null || attributeValue != null) {
                //TODO: do we need encoding here ?
                html.append(" value=\"").append(attributeValue == null ? defaultValue : attributeValue).append("\"");
            }

            html.append(">"); //close the input
        }
        else if (getName().equals("email")) {
            List<String> classes = new ArrayList<String>();

            html.append(" <label for=\"").append(getInputId()).append("\">").append(getLabel()).append("</label>").append(Form.NEW_LINE);
            if (errorMessage != null) {
                classes.add("error");
                html.append("<label class=\"error\" for=\"").append(getInputId()).append("\" generated=\"true\" style=\"display: block;\">").append(errorMessage).append("</label>").append(Form.NEW_LINE);
            }
            // Note: this is HTML5! (TODO: fallback to text for older browsers?)
            html.append(" <input type=\"").append("email").append("\"");
            html.append(" name=\"").append(getInputName()).append("\" id=\"").append(getInputId()).append("\"");
            if (attributeValue != null) {
                html.append(" value=\"").append(attributeValue).append("\"");
            }

            if (inputSize > 0) {
                html.append(" size=\"").append(inputSize).append("\"");
            }
            else {
                html.append(" size=\"30\"");
            }

            if (placeholder != null && attributeValue == null) {
                html.append(" placeholder=\"").append(getPlaceholder()).append("\"");
            }

            if (required) {
                classes.add("required");
            }
            html.append(" class=\"");
            for (int i = 0; i < classes.size(); i++) {
                if (i > 0) {
                    html.append(" ");
                }
                html.append(classes.get(i));
            }
            html.append("\"");

            if (defaultValue != null || attributeValue != null) {
                //TODO: do we need encoding here ?
                html.append(" value=\"").append(attributeValue == null ? defaultValue : attributeValue).append("\"");
            }

            html.append(">"); //close the input
        }
        else if (inputType.equals(TYPE_INTEGER)) {
            List<String> classes = new ArrayList<String>();

            html.append(" <label for=\"").append(getInputId()).append("\">").append(getLabel()).append("</label>").append(Form.NEW_LINE);
            // Note: this is HTML5! (TODO: fallback to text for older browsers?)
//            <input type="number" name="quantity" min="1" max="5"> //maxlength = "3"
            if (errorMessage != null) {
                classes.add("error");
                html.append("<label class=\"error\" for=\"").append(getInputId()).append("\" generated=\"true\" style=\"display: block;\">").append(errorMessage).append("</label>").append(Form.NEW_LINE);
            }
            html.append(" <input type=\"").append("number").append("\"");
            html.append(" name=\"").append(getInputName()).append("\" id=\"").append(getInputId()).append("\"");

            if (attributeValue != null) {
                html.append(" value=\"").append(attributeValue).append("\"");
            }

            html.append(" min=\"1\"");
            if (inputSize > 0 && inputSize < 7) {
                html.append(" max=\"").append(new Double(Math.pow(10d, inputSize)).intValue() - 1).append("\"");
            }
            html.append(" maxlength=\"").append(inputSize > 0 ? inputSize : "3").append("\"");
            html.append(" size=\"").append(inputSize > 0 ? inputSize : "3").append("\"");

            if (placeholder != null && attributeValue == null) {
                html.append(" placeholder=\"").append(getPlaceholder()).append("\"");
            }

            if (required) {
                classes.add("required");
            }
            html.append(" class=\"");
            for (int i = 0; i < classes.size(); i++) {
                if (i > 0) {
                    html.append(" ");
                }
                html.append(classes.get(i));
            }
            html.append("\"");

            if (defaultValue != null || attributeValue != null) {
                //TODO: do we need encoding here ?
                html.append(" value=\"").append(attributeValue == null ? defaultValue : attributeValue).append("\"");
            }

            html.append(">"); //close the input
        }
        else if (inputType.equals(TYPE_ZIPCODE)) {
            List<String> classes = new ArrayList<String>();

            html.append(" <label for=\"").append(getInputId()).append("\">").append(getLabel()).append("</label>").append(Form.NEW_LINE);
            // Note: this is HTML5! (TODO: fallback to text for older browsers?)
            if (errorMessage != null) {
                classes.add("error");
                html.append("<label class=\"error\" for=\"").append(getInputId()).append("\" generated=\"true\" style=\"display: block;\">").append(errorMessage).append("</label>").append(Form.NEW_LINE);
            }
            html.append(" <input type=\"").append(TYPE_TEXT).append("\"");
            html.append(" name=\"").append(getInputName()).append("\" id=\"").append(getInputId()).append("\"");
            html.append(" maxlength=\"11\" size=\"11\"");

            if (attributeValue != null) {
                html.append(" value=\"").append(attributeValue).append("\"");
            }

            if (placeholder != null && attributeValue == null) {
                html.append(" placeholder=\"").append(getPlaceholder()).append("\"");
            }

            if (required) {
                classes.add("required");
            }
            html.append(" class=\"");
            for (int i = 0; i < classes.size(); i++) {
                if (i > 0) {
                    html.append(" ");
                }
                html.append(classes.get(i));
            }
            html.append("\"");

            if (defaultValue != null || attributeValue != null) {
                //TODO: do we need encoding here ?
                html.append(" value=\"").append(attributeValue == null ? defaultValue : attributeValue).append("\"");
            }

            html.append(">"); //close the input
        }
        else if (inputType.equals(TYPE_PHONE)) {
            List<String> classes = new ArrayList<String>();
            html.append(" <label for=\"").append(getInputId()).append("\">").append(getLabel()).append("</label>").append(Form.NEW_LINE);
            // Note: this is HTML5! (TODO: fallback to text for older browsers?)
            if (errorMessage != null) {
                classes.add("error");
                html.append("<label class=\"error\" for=\"").append(getInputId()).append("\" generated=\"true\" style=\"display: block;\">").append(errorMessage).append("</label>").append(Form.NEW_LINE);
            }
            html.append(" <input type=\"").append("tel").append("\"");
            html.append(" name=\"").append(getInputName()).append("\" id=\"").append(getInputId()).append("\" size=12");

            if (attributeValue != null) {
                html.append(" value=\"").append(attributeValue).append("\"");
            }

            if (placeholder != null && attributeValue == null) {
                html.append(" placeholder=\"").append(getPlaceholder()).append("\"");
            }

            if (required) {
                classes.add("required");
            }
            html.append(" class=\"");
            for (int i = 0; i < classes.size(); i++) {
                if (i > 0) {
                    html.append(" ");
                }
                html.append(classes.get(i));
            }
            html.append("\"");

            if (defaultValue != null || attributeValue != null) {
                //TODO: do we need encoding here ?
                html.append(" value=\"").append(attributeValue == null ? defaultValue : attributeValue).append("\"");
            }

            html.append(">"); //close the input
        }
        else {
            List<String> classes = new ArrayList<String>();
            html.append(" <label for=\"").append(getInputId()).append("\">").append(getLabel()).append("</label>").append(Form.NEW_LINE);
            if (errorMessage != null) {
                classes.add("error");
                html.append("<label class=\"error\" for=\"").append(getInputId()).append("\" generated=\"true\" style=\"display: block;\">").append(errorMessage).append("</label>").append(Form.NEW_LINE);
            }
            html.append(" <input type=\"").append(inputType).append("\"");
            html.append(" name=\"").append(getInputName()).append("\" id=\"").append(getInputId()).append("\"");

            if (attributeValue != null) {
                html.append(" value=\"").append(attributeValue).append("\"");
            }

            if (inputSize > 0) {
                html.append(" size=\"").append(inputSize).append("\"");
            }
            else {
                html.append(" size=\"30\"");
            }

            if (placeholder != null && attributeValue == null) {
                html.append(" placeholder=\"").append(getPlaceholder()).append("\"");
            }

            if (required) {
                classes.add("required");
            }
            html.append(" class=\"");
            for (int i = 0; i < classes.size(); i++) {
                if (i > 0) {
                    html.append(" ");
                }
                html.append(classes.get(i));
            }
            html.append("\"");

            if (defaultValue != null || attributeValue != null) {
                //TODO: do we need encoding here ?
                html.append(" value=\"").append(attributeValue == null ? defaultValue : attributeValue).append("\"");
            }

            html.append(">"); //close the input
            html.append("</input>");
        }

        html.append(Form.NEW_LINE);
        html.append("</fieldset>");

//            <fieldset id="opt-in">
//                <label for="registration_confirm">
//                <input type="checkbox" value="1" name="registration[confirm]" id="registration_confirm" class="required" checked="checked">
//                    Please check the box to receive free coupons, updates and offers targeted towards your interests, sent by Coupon-Hound.com.
//                    <a onclick="window.open(this.href,'_blank','width=590,height=700,scrollbars=yes');return false;" href="/coupons/privacy?pop=true">Privacy Policy</a>
//                </label>
//            </fieldset>

        return html.toString();
    }

    public String getLabel() {
        return label != null ? label : attribute.getName();
    }

    public String getPlaceholder() {
        return (placeholder == null ? attribute.getName() : placeholder);
    }

    public boolean isRequired() {
        return required;
    }

    public int getInputSize() {
        return inputSize;
    }

    public String getName() {
        return attribute.getName();
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public Long getFormId() {
        return formId;
    }

    public Long getFormStepId() {
        return formStepId;
    }

    public Long getFormStepGroupId() {
        return formStepGroupId;
    }

    public Long getId() {
        return id;
    }

    public InputType getInputType() {
        return inputType;
    }

    /**
     * create jquery validation rule for this attribute
     * Note: for now we only support required and email (based on the required flag and the attribute name)
     *
     * @return
     */
    public String getValidationRule() {

        if (!required && !"email".equals(attribute.getName())) {
            return null;
        }

        //TODO: if this is a type = TYPE_INTEGER or TYPE_DECIMAL add rule for { number: true }

        StringBuilder r = new StringBuilder();
        r.append("\"").append(getInputName()).append("\"").append(":{");
        if (required) {
            r.append("required: true");
        }
        if ("email".equals(attribute.getName())) {
            if (required) {
                r.append(",");
            }
            r.append("email: true");
        }
        r.append("}");
        return r.toString();
    }

    /**
     * get the jquery validation message for this attribute
     *
     * @return
     */
    public String getValidationMessage() {
        if (validationMessage == null || "".equals(validationMessage)) {
            return null;
        }
        return validationMessage;
    }

    public boolean isInitiallyEnabled() {
        return initiallyEnabled;
    }

    public boolean isMultiSelect() {
        return multiSelect;
    }


    public static void main(String[] args) {
        LOG.debug("zehn hoch 3 = " + new Double(Math.pow(10d, 3)).intValue());
    }
}
