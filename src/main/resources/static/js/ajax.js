const mynmspc = {
 api: {
    ctx: {
             ajaxUrl: "/admin/rest/products/"
         },
    getAll:  function() {
        $.get(mynmspc.api.ctx.ajaxUrl, mynmspc.api.getAllDataFunc)
    },

    getAllDataFunc: function(data){
        console.log(data);
    },

    get: function(id) {
                 $.get(mynmspc.api.ctx.ajaxUrl + id, mynmspc.api.getAllDataFunc)
             },

    save: function() {
        header = $("meta[name='_csrf_header']").attr("content");
         $.ajax({
                type: "POST",
                cache: 'false',
                url: mynmspc.api.ctx.ajaxUrl + "save",
                data: mynmspc.form.serialize(),
                contentType: "application/json",
            }).done(mynmspc.api.getAll());
    }
},

    form : $('#detailsForm')

    json: function(){
    }

}

$(function()) {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}
$.ajaxSetup({cache: false});