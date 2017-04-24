$(document).on("click", ".btn-danger", function(){
    var but=$(this).attr('id').split('_')[0];

    $.ajax({
        type: "POST",
        url: "/cards/block",
        data: {
            cardID:but
           },
        success: function(){
            var selector = "#"+but + "_button";
            $(selector).attr("disabled",true);
            alert('Card was successfully blocked');
        },
        error:function () {
            alert("Can't block card");
        }
    });
});