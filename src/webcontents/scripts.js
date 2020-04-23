function _init_as_leads_form(formParent, formId){
    var iframe=document.createElement('iframe');
    iframe.style.width = '600px';
    iframe.style.height = '400px';
    iframe.style.border = 'none';
    document.getElementById(formParent).appendChild(iframe);
//    add_as_leads_css_link(document, '/form-styles.css');

    setTimeout(function(){
        iframe.src='http://www.tapnexus.com/form/' + formId;
        var iframedoc=iframe.contentDocument||iframe.contentWindow.document;
      }, 10);
}

 function add_as_leads_css(css){
    var head = document.getElementsByTagName('head')[0];
    var s = document.createElement('style');
    s.setAttribute('type', 'text/css');
    if (s.styleSheet) {   // IE
        s.styleSheet.cssText = css;
    } else {                // the world
        s.appendChild(document.createTextNode(css));
    }
    head.appendChild(s);
 }

 function add_as_leads_css_link(doc, css_uri){
    var head = doc.getElementsByTagName('head')[0];
    var s = doc.createElement('link');
    s.setAttribute('rel', 'stylesheet');
    s.setAttribute('type', 'text/css');
    s.setAttribute('href', css_uri);
    s.setAttribute('media', 'screen');

    head.appendChild(s);
 }


$(function () {
//    wizardListener();
    form_submitted = false;

    $(".nextPage").click(function () {
        var $form = $(this).closest('form.wizard');
        var $div = $form.find('div.wizard_page:not(:hidden)');

        var allow = true;
        $div.find(':input').each(function(){
            if($(this).valid() == 0){
                allow = false;
            };
        });

        if (!allow){
            return;
        }

        $div.fadeOut(function () {
            var $next = $(this).next('div.wizard_page');
            while($next.length > 0 && $next.attr('navigable') == 'false'){
                $next  = $next.next('div.wizard_page');
            }
            if ($next.length > 0) {
                $next.fadeIn();
            } else {
                $form.find('div#wizard_final').fadeIn("slow", function () {
                    form_submitted = true;
                    $form.submit();
                });
            };
        });
    });

    $(".previousPage").click(function () {
        if (form_submitted == true) {
            return false;
        }
        var $form = $(this).closest('form.wizard');
        var $div = $form.find('div.wizard_page:not(:hidden)');

        var allow = true;
        $div.find(':input').each(function(){
            if($(this).valid() == 0){
                allow = false;
            };
        });

        if (!allow){
            return;
        }

        $div.fadeOut(function () {
            var $prev = $(this).prev('div.wizard_page');
            while($prev.length > 0 && $prev.attr('navigable') == 'false'){
                $prev = $prev.prev('div.wizard_page')
            }

            if ($prev.length > 0) {
                $prev.fadeIn();
            }
        });
    });

//    $(document).ajaxError(
//        function (event, jqXHR, ajaxSettings, thrownError) {
//            alert('[event:' + event + '], [jqXHR:' + jqXHR + '], [ajaxSettings:' + ajaxSettings + '], [thrownError:' + thrownError + '])');
//        });
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

// this is for choice type questions (change on select...)
wizardListener = function () {
    var form_submitted = false;
    $('form.wizard div.wizard_page fieldset input[type=radio]').change(function () {
        var $form = $(this).closest('form.wizard');
        $form.find('div.wizard_page:not(:hidden)').fadeOut(function () {
            var $next = $(this).next('div.wizard_page');
            if ($next.length > 0) {
                $next.fadeIn();
            } else {
                $form.find('div#wizard_final').fadeIn("slow", function () {
                    form_submitted = true;
                    $form.submit();
                });
            }
            ;
        });
    });
};
