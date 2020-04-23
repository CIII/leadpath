//$(document).ready(function() {
//    alert('Welcome to StarTrackr! Now no longer under police investigation!');
//});

// or short
$(function () {
//    $("#f_2").validate();
    //alert('Ready to do your bidding!');
//    wizardListener();

//    $(document).ajaxStart(function() {
//        $("#ajax_result").text("Loading...");
//    });
//
//    $(document).ajaxComplete(function() {
//        $("#ajax_result").text("");
//    });
});

//$("p").click(function () {
//    $(this).slideUp();
//});


//$(document).ready(function() {
//    alert($('#celebs tbody tr').length + ' elements!');
//});


//$('form.wizard div.wizard_page fieldset input').change(function () {
//    //$("#formResponse_container").slideDown("slow", function() {
//    $form.find('div.wizard_page:not(:hidden)').slideDown("slow", function () {
//        $("#formResponse").fadeIn("slow");
//    });
//});



$(document).ready(function(){
    $("#f_2").validate({
      "rules": {
        "f_2[fname]":{"required": true},
        "f_2[lname]":{"required": true},
        "f_2[email]":{"required": true, "email": true},
        "f_2[confirm]":{"required": true}
      },
      "errorPlacement": function(error, element) {
        error.fadeIn().insertAfter( element.parent().children('label') );
        if ( element.attr("type") == "checkbox" ) {
            error.fadeIn().insertBefore( element.parent() );
        }
      },
      "messages": {
        "f_2[fname]" : "* Please provide a name",
        "f_2[lname]" : "* Please provide a name",
        "f_2[email]" : "* Please provide your email address"
      }
    });
    var emailError = $("label.error + input[type=text]"); if ( emailError ) { emailError.addClass("error"); };
});
