$(document ).ready(function(e) {
    if($("#tab").val()==='account') {
        $("#account-pay-form").delay(100).fadeIn(100);
        $("#card-pay-form").fadeOut(100);
        $('#card-transfer').removeClass('active');
        $('#account-transfer').addClass('active');
        e.preventDefault();
    }
});