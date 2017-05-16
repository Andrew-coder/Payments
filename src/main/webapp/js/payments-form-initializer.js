$(document ).ready(function(e) {
    if($("#tab").val()==='account') {
        $("#account-form-pay").delay(100).fadeIn(100);
        $("#card-pay-form").fadeOut(100);
        $('#card-transfer').removeClass('active');
        $('#account-transfer').addClass('active');
        $('input:radio[name=optradio]')[1].checked = true;
        e.preventDefault();
    }
});