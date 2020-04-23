package com.pony.form;

import javax.naming.NamingException;
import java.sql.SQLException;

/**
 * PonyLeads 2013.
 * User: martin
 * Date: 2/7/13
 * Time: 10:21 AM
 */
public class Form {
    private final Long id, publisherListId, publisherId;

    private String name, submitText, callToAction;

    public static final String FORM_ID = "form_id";
    public static final String FORM_IMPRESSION_ID = "form_impression_id";
    public static final String NEW_LINE = "\r\n";
    private ClickoutFeed clickoutFeed;

    private Form(Long id, String name, String submitText, String callToAction, Long publisherListId, Long publisherId) {
        this.id = id;
        this.name = name;
        this.submitText = submitText;
        this.callToAction = callToAction;
        this.publisherListId = publisherListId;
        this.publisherId = publisherId;
    }

    public static Form create(Long id, String name, String submitText, String callToAction, Long publisherListId, Long publisherId) throws NamingException, SQLException {
        return new Form(id, name, submitText, callToAction, publisherListId, publisherId);
    }

    public Long getId() {
        return id;
    }

    public Long getPublisherListId() {
        return publisherListId;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public String getName() {
        return name;
    }

    public String getSubmitText() {
        return submitText;
    }

    public String getCallToAction() {
        return callToAction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Form form = (Form) o;

        if (id != null ? !id.equals(form.id) : form.id != null) {
            return false;
        }
        if (!name.equals(form.name)) {
            return false;
        }
        return publisherListId.equals(form.publisherListId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + publisherListId.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", publisherListId=" + publisherListId +
                ", name='" + name + '\'' +
                ", submitText='" + submitText + '\'' +
                ", callToAction='" + callToAction + '\'' +
                '}';
    }

    public String getMarkup(FormImpression fi, FormState state) throws NamingException, SQLException {
        StringBuilder html = new StringBuilder();
        html.append(" <section id=\"main-group\" class=\"clr\">");
        html.append(" <section id=\"form\">");
        html.append(" <article>");

        html.append(" <h2>").append(getCallToAction()).append("</h2>\r\n");

        html.append("<form class=\"wizard\" id=\"f_").append(getId()).append("\" method=\"post\" accept-charset=\"UTF-8\" novalidate=\"novalidate\" action=\"\">");
        // render form id and form impression id as hidden fields
        html.append("<div style=\"margin:0;padding:0;display:inline\">");
        html.append("    <input type=\"hidden\" value=\"✓\" name=\"utf8\">");
        html.append("<input type=\"hidden\" name=\"authenticity_token\" value=\"" + fi.getUUID() + "\">");
        html.append("<input type=\"hidden\" name=\"" + FORM_ID + "\" value=\"" + getId() + "\">");
        html.append("<input type=\"hidden\" name=\"" + FORM_IMPRESSION_ID + "\" value=\"" + fi.getId() + "\">");
        html.append("</div>").append(Form.NEW_LINE);

        html.append(state.getCurrentFormStep().getMarkup(this, fi, state));

        html.append("</form>").append(Form.NEW_LINE);

        html.append("</article></section></section>").append(Form.NEW_LINE);

        html.append(state.getScript()).append(Form.NEW_LINE);

        return html.toString();
    }

    public ClickoutFeed getClickoutFeed() {
        return clickoutFeed;
    }
}
