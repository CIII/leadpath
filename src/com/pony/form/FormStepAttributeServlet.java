package com.pony.form;

import com.pony.core.MediaType;
import com.pony.models.FormModel;
import com.pony.models.FormSelectFilterModel;
import com.pony.models.FormStepAttributeFilterModel;
import com.pony.models.FormStepAttributeModel;
import com.pony.models.FormStepFilterModel;
import com.pony.models.FormStepGroupFilterModel;
import com.pony.models.SelectValueModel;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 2/14/13
 * Time: 10:16 AM
 */
public class FormStepAttributeServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(FormStepAttributeServlet.class);
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // mapped to formstepattribute

        // example url: /formstepattribute/1234?v=newValuesHere

        Enumeration parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String p = parameterNames.nextElement().toString();
            LOG.debug("param:" + p + "[" + req.getParameter(p) + "]");
        }

        // get the form step attribute id from the path
        String pathInfo = req.getPathInfo();
        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }
        String[] tokens = pathInfo.split("/");
        if (tokens.length == 0) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String newValue = req.getParameter("v");

        if (newValue == null || "".equals(newValue)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        resp.setCharacterEncoding("utf8");
        resp.setContentType(MediaType.TEXT.toString());

        String formStepAttributeId = tokens[0];
        LOG.info("changing formStepAttributeId = " + formStepAttributeId);
        // TODO: we have a new value selected:
        // read the filters for the new value (if any) and apply them
        StringBuilder html = new StringBuilder();
        try {
            FormStepAttribute changingAttribute = FormStepAttributeModel.find(Long.valueOf(formStepAttributeId));
            LOG.info("changing attribute =" + changingAttribute);

            if (changingAttribute.getInputType().equals(FormStepAttribute.TYPE_SELECT)) {

                SelectValue selectedOption = SelectValue.DEFAULT;

                if (newValue != null && !"".equals(newValue) && !"-1".equals(newValue)) {
                    selectedOption = SelectValueModel.find(Long.valueOf(newValue));
                }
                LOG.info("selectedOption=" + selectedOption);

                // update form state
                FormState formState = FormState.getState(FormModel.find(changingAttribute.getFormId()), req);
                formState.setAttributeValue(changingAttribute, newValue);

                // find all filters and sync (dependent select options)
                Map<FormStepAttribute, List<SelectValue>> dependentOptions = FormSelectFilterModel.findFilteredOptions(changingAttribute, selectedOption);
                System.out.println("dependentOptions.size()= " + dependentOptions.size());

                // return a hash with:
                // key=dependent attribute name
                // value=html to inject for that attribute (select options)

                html.append("{");
                if (dependentOptions.size() > 0) {
                    html.append("\"sf\" : {");
                }
                boolean firstAttribute = true;
                for (Map.Entry<FormStepAttribute, List<SelectValue>> entry : dependentOptions.entrySet()) {
                    if (!firstAttribute) {
                        html.append(",");
                    }
                    else {
                        firstAttribute = false;
                    }
                    FormStepAttribute dependentAttribute = entry.getKey();
                    List<SelectValue> value = entry.getValue();
//                System.out.println("key=" + dependentAttribute);
//                System.out.println("value=" + value);

                    html.append("\"").append(dependentAttribute.getInputId()).append("\" : \"");
                    // reset any current state
                    formState.removeAttributeValue(dependentAttribute);

                    for (SelectValue option : value) {
                        html.append("<option value='").append(option.getId()).append("'");
                        if (option.isPreSelected()) {
                            html.append(" selected='selected'");
                            // if there is a pre-selected value, update the state to that
                            formState.setAttributeValue(dependentAttribute, option.getValue());
                        }
                        html.append(">").append(option.getValue()).append("</option>");
                    }
                    html.append("\"");
                }
                if (dependentOptions.size() > 0) {
                    html.append("}");
                }


                // check attribute filters
                Map<FormStepAttribute, Boolean> filteredAttributes = FormStepAttributeFilterModel.findFilteredAttributes(changingAttribute, selectedOption);
                formState.updateAttributeFilters(filteredAttributes);
                //TODO: format the response

                // check step filters
                Map<FormStep, Boolean> filteredSteps = FormStepFilterModel.findFilteredSteps(changingAttribute, selectedOption);
                formState.updateStepFilters(filteredSteps);
                //TODO: format the response

                // check group filters
                Map<FormStepGroup, Boolean> filteredGroups = FormStepGroupFilterModel.findFilteredGroups(changingAttribute, selectedOption);
                Map<Long, Boolean> newStates = formState.updateGroupFilters(filteredGroups);
                if (newStates.size() > 0) {
                    if (dependentOptions.size() > 0) {
                        html.append(",");
                    }

                    html.append("\"gf\" : {");
                    boolean first = true;
                    for (Map.Entry<Long, Boolean> entry : newStates.entrySet()) {
                        if (!first) {
                            html.append(",");
                        }
                        else {
                            first = false;
                        }
                        html.append("\"").append(entry.getKey()).append("\" : \"").append(entry.getValue()).append("\"");
                    }
                    html.append("}");
                }
                html.append("}").append(Form.NEW_LINE); // close outer brackets
            }
        }
        catch (NamingException e) {
            LOG.error(e);
        }
        catch (SQLException e) {
            LOG.error(e);
        }
        catch (Throwable e) {
            LOG.error(e);
        }

        LOG.info("writing response: " + html.toString());
        PrintWriter out = resp.getWriter();
        out.write(html.toString());
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
