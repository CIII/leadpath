package com.pony.form;

import com.pony.core.MediaType;
import com.pony.core.PonyServer;
import com.pony.email.SmtpException;
import com.pony.models.DiscoveryAdvisorFormData;
import com.pony.models.FormImpressionModel;
import com.pony.models.FormModel;
import com.pony.models.PublisherListModel;
import com.pony.models.PublisherModel;
import com.pony.publisher.Publisher;
import com.pony.publisher.PublisherChannel;
import com.pony.publisher.PublisherContext;
import com.pony.publisher.PublisherException;
import com.pony.publisher.PublisherList;
import com.pony.publisher.PublisherResponse;
import com.pony.publisher.PublisherService;
import com.pony.validation.ValidationException;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 2/6/13
 * Time: 3:36 PM
 */
public class FormServlet extends HttpServlet {
	private static final Log LOG = LogFactory.getLog(FormServlet.class);

    private PonyServer server = null;
    private PublisherService publisherService = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // render the form for the given id
        String pathInfo = req.getPathInfo();
        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }
        String[] tokens = pathInfo.split("/");
        if (tokens.length == 0) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        resp.setCharacterEncoding("utf8");
        resp.setContentType(MediaType.HTML.toString());

        StringBuilder html = new StringBuilder();

        // find form
        try {
            if ("create".equals(tokens[0])) {
                DiscoveryAdvisorFormData.createData();
                return;
            }

            Long formId = Long.valueOf(tokens[0]);
            Form f = FormModel.find(formId);

            if (f != null) {
                FormState formState = FormState.getState(f, req);

                FormImpression fi = FormImpressionModel.createOrUpdate(f, formState, req);

                html.append("<!DOCTYPE html>\n<html lang=\"en\">\n<head><title>").append(f.getName()).append("</title>");
                html.append("<link rel=\"stylesheet\" href=\"/styles.css\" type=\"text/css\" media=\"all\"></link>").append(Form.NEW_LINE);
                html.append("<link rel=\"stylesheet\" href=\"http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css\" />").append(Form.NEW_LINE);
                html.append("<script type=\"text/javascript\" src=\"http://code.jquery.com/jquery-1.9.1.min.js\"></script>").append(Form.NEW_LINE);
                html.append("<script type=\"text/javascript\" src=\"http://code.jquery.com/ui/1.10.1/jquery-ui.js\"></script>").append(Form.NEW_LINE);
                html.append("<script type=\"text/javascript\" src=\"/jquery.validate.min.js\"></script>").append(Form.NEW_LINE);
                html.append("<script type=\"text/javascript\" src=\"/validation.js\"></script>").append(Form.NEW_LINE);
                html.append("<script type=\"text/javascript\" src=\"/scripts.js\"></script>").append(Form.NEW_LINE);

                html.append("</head>");
                html.append("<body><div id='f'>");

                html.append(f.getMarkup(fi, formState));

                html.append("</div>").append(Form.NEW_LINE);
            }
            else {
                html.append("<!DOCTYPE html>\n<html lang=\"en\">\n<head><title>form test</title><link rel=\"stylesheet\" href=\"/styles.css\"></head>\n");
                html.append("<body><p id='form'>...form [" + tokens[0] + "] goes here</p>");
            }

            html.append("</body></html>");
        }
        catch (NumberFormatException e) {
            LOG.error(e);
            throw new ServletException("Invalid formId");
        }
        catch (SQLException e) {
            LOG.error(e);
            throw new ServletException(e);
        }
        catch (NamingException e) {
            LOG.error(e);
            throw new ServletException(e);
        }

        Writer out = resp.getWriter();
        out.write(html.toString());
        out.flush();

        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            // get the form and form impression if this is in reference to
            String form_id = req.getParameter(Form.FORM_ID);
            String form_impression_id = req.getParameter(Form.FORM_IMPRESSION_ID);

            Form form = null;
            FormImpression impression = null;

            if (form_id != null) {
                Long formId = Long.valueOf(form_id);
                form = FormModel.find(formId);
            }

            if (form_impression_id != null) {
                Long formImpressionId = Long.valueOf(form_impression_id);
                impression = FormImpressionModel.find(formImpressionId);
            }

            if (form == null || impression == null) {
                throw new ServletException("no valid post: invalid form(impression) reference[" + form_id + "," + form_impression_id + "]");
            }

            // check authenticity
            String postedToken = req.getParameter("authenticity_token");
            if (!impression.getUUID().equals(postedToken)) {
                throw new ServletException("no valid post: authenticity mismatch");
            }

            // determine publisher_list and publisher,
            Publisher publisher = PublisherModel.find(form.getPublisherId());
            PublisherList publisherList = PublisherListModel.find(form.getPublisherListId());
            PublisherChannel channel = PublisherChannel.create(publisher, publisherList, req);

            // parse the posted form params (form fields)
            FormState formState = FormState.getState(form, req);

            try {
                formState.updateFromRequest(req);

                // was a form step group posted (only that part)?
                if (req.getParameter("form_step_group_id") != null) {
                    FormStepGroup group = formState.getCurrentFormStepGroup();
                    if (group != null && group.getId().toString().equals(req.getParameter("form_step_group_id"))) {
                        // advance to the next group and render again.
                        formState.nextFormStepGroup();
                        doGet(req, resp);
                        return;
                    }
                }

                // create/update user_profile, arrival, lead and profile_attributes
                // TODO: what to do when there is an impression that already has an arrival_id ??
                PublisherContext publisherContext = PublisherContext.parse(channel, req, formState.getAttributeValueMap());

                // link the arrival to the form_impression
                FormImpressionModel.link(impression, publisherContext.getArrival());

                // now try to route the posted data
                PublisherResponse publisherResponse = publisherService.execute(publisherContext);

                // and then format the response
                publisherService.formatResponse(publisherContext, publisherResponse, formState, resp);

                LOG.info(Thread.currentThread().getName() + ": response=" + publisherResponse);
            }
            catch (ValidationException e) {
                //TODO: handle validation errors and re-render with state, if an error occurred

                LOG.error(e);

                doGet(req, resp);
            }
        }
        catch (SQLException e) {
            LOG.error(e);
            throw new ServletException(e);
        }
        catch (NamingException e) {
            LOG.error(e);
            throw new ServletException(e);
        }
        catch (PublisherException e) {
            LOG.error(e);
            throw new ServletException(e);
        }
        catch (SmtpException e) {
            LOG.error(e);
            throw new ServletException(e);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            server = new PonyServer().start();
            publisherService = server.getPublisherService();
        }
        finally {
        }
    }

    @Override
    public void destroy() {
        if (server != null) {
            server.stop();
        }

        publisherService = null;
        server = null;

        super.destroy();
    }
}


/*

-- script from getitfree.us --

<script src="https://extras.getitfree.us/path/register.getitfree.us/js/zgoffers.js" type="text/javascript">
var _pms = {};
_pms.popup_attrs = ''; // ie: 'toolbar=0,location=0,menubar=0,status=1,resizable=1,scrollbars=1'
_pms.init = function() {
_pms.bindClickListener();
_pms.bindQuizListener();
};
_pms.bindClickListener = function() {
$('a[target="_blank"]').not('.nofollow').click(function() {
_pms.doPopup($(this));
return false;
});
};
_pms.bindQuizListener = function() {
var form_submitted = false;
$('form#path-quiz div.questionContainer ul li input').change(function() {
var $form = $(this).closest('form.quiz');
$form.find('div.questionContainer:not(:hidden)').fadeOut(function() {
var $next = $(this).next('div.questionContainer');
if ($next.length > 0) {
$next.fadeIn();
} else {
$form.find('div#quizLoader').fadeIn(function() {
form_submitted = true;
$form.submit();
});
}
});
});
// if on quiz page and form hasn't been submitted, submit it through ajax
window.onbeforeunload = function (e) {
if ($('form#path-quiz').length > 0 && form_submitted === false) {
var $form = $('form#path-quiz');
$.ajax({
type: 'POST',
data: $form.serialize(),
url: survey_submit_url,
success: function() {
form_submitted = true;
}
});
}
}
};
_pms.track = function(data) {
switch (data[0]) {
case 'click':
_pms.track.click(data[1]);
break;
}
};
_pms.track.click = function(data) {
$.ajax({
'type': 'POST',
'url': script_url + 'track/click',
'data': 'id=' + data.id
});
return true;
};
_pms.doPopup = function($anchor) {
var popup_link = $anchor.attr('href'),
popup_name = '_blank';
if (typeof _pms.impression_id !== 'undefined') {
popup_name = 'z_ad_' + _pms.impression_id;
}
var popup = window.open(popup_link, popup_name, _pms.popup_attrs);
// Detect popup blocker
setTimeout(function() {
// popup was blocked
if (!popup || popup.closed || parseInt(popup.innerWidth) == 0) {
// close popup for chrome (chrome hides popups that were blocked)
popup && popup.close();
$('#dialog')
.attr('title', 'Freebie sample blocked!').html('Please disable your popup blocker so that you can claim your free samples, then click on the button below to claim!')
.dialog({
modal: true,
width: 800,
draggable: false,
resizable: false,
buttons: {
'Click now!!': function() {
$('#dialog').dialog('close');
_pms.doPopup($anchor);
}
}
});
} else {
// they don't have a popup blocker, lets do work!
_pms.redirectToUrl(next_url);
}
}, 500);
};
_pms.redirectToUrl = function(url) {
window.location = url;
};
</script>

-- form validation
<script src="https://extras.getitfree.us/path/getitfree.us/templates/NEWPMS/js/form-validation.js" type="text/javascript">
$(function() {
$('form').bind('submit', function() {
var $inputs = $(this).find(':input, :checkbox, :radio'), errors = new Array();
$inputs.each(function() {
var $el = $(this);
var splitclass = $el.attr('class');
var classes = splitclass ? splitclass.split(/\s+/) : '';
//var classes = $el.attr('class').split(/\s+/);
for (var i = 0; i < classes.length; i++) {
if (typeof validate[classes[i]] === 'function') {
var valid = validate[classes[i]]($el);
if (!valid[0]) {
var form_field = $el.attr('title') !== 'undefined' && $el.attr('title') !== false ? $el.attr('title') : $el.attr('name');
errors[errors.length++] = form_field + ' ' + valid[1];
}
}
}
});
for (var i = 0; i < errors.length; i++) {
alert(errors[i]);
break;
}
// return false if there are errors
return errors.length <= 0;
});
// validation methods
var validate = {
is_required: function ($el) {
var v = $el.val();
return [v.length != 0, 'is required!'];
},
is_email: function ($el) {
var v = $el.val();
return [/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(v), 'is an invalid email!'];
},
is_phone: function($el) {
var $el2 = $el.clone();
$el2.attr('value', $el2.val().replace(/\D/g,''));
return [this.min_length(10, $el2)[0] && this.max_length(10, $el2)[0], 'must be exactly 10 digits!'];
},
is_numeric: function($el) {
var v = $el.val();
return [!isNaN(parseFloat(v)) && isFinite(v), 'must be numeric!'];
},
is_checked: function ($el) {
return [$el.is(':checked'), 'must be checked'];
},
is_zip: function($el) {
var v = $el.val();
return [this.is_numeric($el)[0] && v.length === 5, 'is an invalid zip!'];
},
min_length: function (min, $el) {
var v = $el.val();
return [v.length >= min, 'cannot contain less than ' + min + ' characters!'];
},
max_length: function (max, $el) {
var v = $el.val();
return [v.length <= max, 'cannot exceed ' + max + ' characters!'];
}
};
});
</script>


<script src="https://pathmanager-production-cdn.s3.amazonaws.com/getitfree.us/js/jquery.maskedinput-1.2.2.min.js" type="text/javascript"></script>

*/

/*
    // industry selector
        <select id="categorySelect" name="category" size="1"  class="validate[required]" >
        	          <option value="">select industry type</option>
                      <option value="1">Manufacturing</option>
                      <option value="2">Professional Office</option>
        			  <option value="3">Restaurants</option>
        			  <option value="4">Retail</option>
        			  <option value="5">Service Providers</option>
        			  <option value="6">Specialty Trade Contractors</option>
        			  <option value="7">Transportation & Trucking</option>
        			  <option value="8">Wholesale & Warehousing</option>
        			  <option value="9">Other</option>
                  </select>

    //

<script type="text/javascript">
 <!--
 $(function () {
     var cat = $('#categorySelect');
     var el = $('#elementSelect');
     var attr = $('#attributeSelect');

     el.selectChain({
         target: attr,
         url: '/type',
	    type: 'post',
         data: { ajax: true, anotherval: "anotherAction" }
     });

     // note that we're assigning in reverse order
     // to allow the chaining change trigger to work
     cat.selectChain({
         target: el,
         url: '/type',
	    type: 'post',
         data: "ajax=true"
     }).trigger('change');

 });
 //-->
 </script>


  <option value="1">Manufacturing</option>
  => ANY

  <option value="2">Professional Office</option>
  <select class="validate[required]" size="1" name="class" id="elementSelect"><option value="undefined" selected="selected">select business type</option><option value="8810">Accountants - in office</option><option value="8803">Accountants - travel</option><option value="8810">Advertising Agency</option><option value="8601">Architect</option><option value="8810">Bank</option><option value="8859">Computer Programming</option><option value="8839">Dentists</option><option value="8601">Engineer</option><option value="8810">Graphic Design</option><option value="8822">Insurance Agent/Broker</option><option value="8820">Law Office</option><option value="8838">Museums</option><option value="8013">Optometrists</option><option value="8810">Other Professional Office</option><option value="8834">Physical/Occupational Therapist</option><option value="8834">Physicians</option><option value="9015">Property Management - non-professional</option><option value="8740">Property Management - professional</option><option value="8741">Real Estate Agents</option><option value="8742">Sales Office</option><option value="8831">Veterinarians</option></select>

  <option value="3">Restaurants</option>
  <select class="validate[required]" size="1" name="class" id="elementSelect"><option value="undefined" selected="selected">select business type</option><option value="2003">Bagel Shop - bakery</option><option value="8017">Bagel Shop - no food service</option><option value="2003">Bakery</option><option value="8017">Coffee Shop / Cyber Cafe</option><option value="8006">Delicatessen</option><option value="2003">Donut Shop (baking on premises)</option><option value="9079">Donut Shop (no baking on premises)</option><option value="9079">Fast Food Restaurant (Counter Service)</option><option value="8078">Ice Cream Shop</option><option value="9999">Other Restaurants</option><option value="9079">Restaurant (With Table Service)</option></select>

  <option value="4">Retail</option>
  <select class="validate[required]" size="1" name="class" id="elementSelect"><option value="undefined" selected="selected">select business type</option><option value="8046">Automobile Parts &amp; Accessories</option><option value="2003">Bakery</option><option value="8810">Bank</option><option value="8071">Book or Music Store</option><option value="8008">Clothing Stores</option><option value="8061">Convenience Store</option><option value="8039">Department Store</option><option value="8017">Drug Store</option><option value="2589">Dry Cleaning</option><option value="8017">Electronics Store</option><option value="8031">Fish, Meat or Poultry Retail Store</option><option value="8042">Floor Covering Store</option><option value="8001">Florists</option><option value="8015">Furniture Store</option><option value="8017">Gift Shops</option><option value="8017">Hardware Store</option><option value="8078">Ice Cream Store</option><option value="8013">Jewelry Store</option><option value="8017">Laundromat - Self Service</option><option value="8004">Lawn &amp; Garden Center</option><option value="8060">Liquor Stores</option><option value="8064">Office Supply Store</option><option value="8013">Optical Store</option><option value="8017">Other Retail Store</option><option value="8065">Paint/Wallpaper Store</option><option value="8017">Pet &amp; Pet Supply Store</option><option value="8017">Photo Supply Store</option><option value="8111">Plumbing Supply Store</option><option value="8008">Shoe Stores</option><option value="8017">Sporting Goods Store</option><option value="8006">Supermarket</option><option value="8017">Tailors</option><option value="8017">Variety/Dollar Store</option></select>

  <option value="5">Service Providers</option>
  <select class="validate[required]" size="1" name="class" id="elementSelect"><option value="undefined" selected="selected">select business type</option><option value="7605">Alarm Installation &amp; Service</option><option value="9519">Appliance Repair</option><option value="8393">Auto Body Repair Shop</option><option value="8391">Automobile Rental</option><option value="8389">Automobile Service &amp; Repair</option><option value="9586">Barber Shop</option><option value="9586">Beauty Salon</option><option value="9092">Bowling Lanes</option><option value="8387">Car Wash</option><option value="2584">Carpet &amp; Upholstery Cleaning</option><option value="4299">Commercial Printer</option><option value="5191">Computer Repair</option><option value="9060">Country Club / Golf Courses</option><option value="2589">Dry Cleaning</option><option value="9155">Entertainers &amp; Musicians</option><option value="9053">Fitness Center</option><option value="9620">Funeral Homes</option><option value="9050">Hotel/Motel</option><option value="9008">Janitorial Service</option><option value="8831">Kennels/Doggy Day Care</option><option value="42">Landscape Gardener</option><option value="9015">Lawn Care/Maintenance</option><option value="4304">Newspaper Publishing</option><option value="9999">Other Service Provider</option><option value="8831">Pet Groomers</option><option value="8017">Photo Processing</option><option value="4361">Photographer</option><option value="8019">Quick Printer/Copy Shop</option><option value="9101">Schools - non-professional</option><option value="8868">Schools - professional</option><option value="7605">Security System Installation</option><option value="8017">Self-Serve Gas Station</option><option value="9402">Snowplowing</option><option value="9999">Social Services</option><option value="8810">Tax Preparation Service</option><option value="8810">Travel Agency</option></select>

  <option value="6">Specialty Trade Contractors</option>
  <select class="validate[required]" size="1" name="class" id="elementSelect"><option value="undefined" selected="selected">select business type</option><option value="5403">Carpentry - Commercial</option><option value="5645">Carpentry - Residential</option><option value="9521">Carpet, Vinyl or Linoleum Tile Installation</option><option value="5205">Concrete &amp; Asphalt Work</option><option value="5201">Driveway Paving or Repaving</option><option value="5190">Electrical Contractors</option><option value="5403">Finish Carpentry Contractors</option><option value="5146">Furniture/Fixture Installation</option><option value="5538">HVAC</option><option value="5403">Hardwood Floor Installation or Refinishing</option><option value="9008">Janitorial Service</option><option value="42">Landscape Gardener</option><option value="5028">Masonry Contractors</option><option value="9999">Other Specialty Trade Contractors</option><option value="5474">Painting &amp; Wall Covering Contractors</option><option value="5485">Plastering</option><option value="5183">Plumbing Contractors</option><option value="5645">Siding Contractors (Commercial)</option><option value="5645">Siding Contractors (Residential)</option><option value="9402">Snowplowing</option><option value="5348">Tile, Marble or Stone Contractors</option></select>

  <option value="7">Transportation & Trucking</option>
  ANY

  <option value="8">Wholesale & Warehousing</option>
  ANY

  <option value="9">Other</option>
  ANY


->

*/