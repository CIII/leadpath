<!DOCTYPE html>
<html>
<head>
  <title>JqExperiments</title> <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
  <script type="text/javascript" src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>
  <!--<script type="text/javascript" src="http://localhost:8080/jquery-validate.min.js"></script>-->
  <script type="text/javascript" src="/validation.js"></script>
  <script type="text/javascript" src="/scripts.js"></script>

  <style type="text/css">
      * { font-family: Verdana; font-size: 96%; }
      label { width: 10em; float: left; }
      label.error { float: none; color: red; padding-left: .5em; vertical-align: top; }
      p { clear: both; }
      .submit { margin-left: 12em; }
      em { font-weight: bold; padding-right: 1em; vertical-align: top; }
  </style>
</head>
<body>
<div id="f">
  <section class="clr" id="main-group">
    <section id="form">
      <article><h2>register</h2>

        <form class="wizard" action="" novalidate="novalidate" accept-charset="UTF-8" method="post" id="f_2">
          <div style="margin:0;padding:0;display:inline">
            <input type="hidden" name="utf8" value="✓"><input type="hidden" value="9c4f5c64-4ef6-4152-ab79-01d25255d73d" name="authenticity_token"><input type="hidden" value="2" name="form_id"><input type="hidden" value="3" name="form_impression_id">
          </div>
          <div class="wizard_page">

            <p id="ajax_result">change me...</p>

            <fieldset class="industry">
              <label for="2_industry">Industry</label>
              <select name="f_2[industry]" id="2_industry">
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
            </fieldset>

            <fieldset class="businessType">
              <label for="2_business_type">Type of Business</label>
              <select name="f_2[business_type]" id="2_business_type">
                <option value="ANY" selected>ANY</option>
              </select>
            </fieldset>

            <fieldset class="fname">
              <label for="2_fname">First Name</label>
              <input type="text" class="required" placeholder="first name here" size="100" id="2_fname" name="f_2[fname]">
            </fieldset>
            <fieldset class="lname">
              <label for="2_lname">Last Name</label>
              <input type="text" class="required" placeholder="last name here" size="100" id="2_lname" name="f_2[lname]">
            </fieldset>
            <a href="#" class="nextPage">Next</a>
          </div>
          <div class="wizard_page" style="display:none;">
            <fieldset class="email">
              <label for="2_email">Email</label>
              <input type="text" class="required" placeholder="email here" size="100" id="2_email" name="f_2[email]">
            </fieldset>
            <a href="#" class="nextPage">Next</a> | <a href="#" class="previousPage">Previous</a>
          </div>

          <div class="wizard_page" style="display:none;">
            <fieldset class="confirm">
              <label for="2_confirm">
                <input type="checkbox" checked="checked" class="required" id="2_confirm" name="f_2[confirm]">
                Please check the checkbox</label>
            </fieldset>
            <fieldset id="form-submit">
              <button type="submit" id="landingSubmit" title="my first form">please answer these questions</button>
            </fieldset>

            <a href="#" class="previousPage">Previous</a>
          </div>

          <div class="wizard_final" style="display:none;">
            <p>Your Information is being processed ...</p>
          </div>

        </form>
      </article>
    </section>
  </section>
</div>
<!-- init validation -->
<script>
    $(document).ready(function(){$("#f_2").validate();
    }
</script>
 </body>
 </html>
