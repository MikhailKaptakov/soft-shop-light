ajax = {};
ajax.url = "/admin/ui/article/";
context = {};
context.editArticleForm;

ajax.updateTable = function() {
    $.get(ajax.url, ajax.updateTableByData);
}

ajax.updateTableByData = function(data) {
    ajax.datatableApi.clear().rows.add(data).draw();
}

function makeEditable(datatableOpts) {
    ajax.datatableApi = $("#datatable").DataTable(
        // https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN
        $.extend(true, datatableOpts,
            {
                "ajax": {
                    "url": ajax.url,
                    "dataSrc": ""
                },
                "paging": false,
                "info": true,
                "language": {
                    "search": i18n["common.search"]
                }
            }
        ));
        context.editArticleForm = $('#edit-article-details');
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}

ajax.save = function() {
    $.ajax({
        type: "POST",
        url: ajax.url + "save",
        data: context.editArticleForm.serialize()
    }).done(function () {
        $("#edit-article").modal("hide");
        ajax.updateTable();
        successNoty("common.saved");
    });
}

ajax.setAvailable = function(chkbox, id) {
    var isAvailable = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: ajax.url + "available/" + id,
        type: "POST",
        data: "available=" + isAvailable
    }).done(function () {
        chkbox.closest("tr").attr("data-product-available", isAvailable);
        successNoty(isAvailable ? "Добавлено" : "Скрыто");
    }).fail(function () {
        $(chkbox).prop("checked", !isAvailable);
    });
}

ajax.deleteImage = function(id) {
    $.ajax({
        url: ajax.url + "image/delete/" + id,
        type: "POST",
    }).done(function () {
        $("#edit-article-image").modal("hide");
        successNoty("Изображение удалено");
        ajax.updateTable();
    });
}

ajax.saveImage = function(id) {
    let fd = new FormData();
    let files = $('#image')[0].files[0];
    fd.append('image', files);

    $.ajax({
        type: "POST",
        url: ajax.url + "image/" + id,
        data: fd,
        contentType: false,
        processData: false,
    }).done(function () {
        $("#edit-article-image").modal("hide");
        ajax.updateTable();
        successNoty("Изображение сохранено");
    });
}

ajax.deleteLogo = function(id) {
    $.ajax({
        url: ajax.url + "logo/delete/" + id,
        type: "POST",
    }).done(function () {
        $("#edit-article-logo").modal("hide");
        successNoty("Изображение удалено");
        ajax.updateTable();
    });
}

ajax.saveLogo = function(id) {
    let fd = new FormData();
    let files = $('#logo')[0].files[0];
    fd.append('logo', files);

    $.ajax({
        type: "POST",
        url: ajax.url + "logo/" + id,
        data: fd,
        contentType: false,
        processData: false,
    }).done(function () {
        $("#edit-article-logo").modal("hide");
        ajax.updateTable();
        successNoty("Изображение сохранено");
    });
}

$(function () {
    makeEditable({
        "columns": [
            {
            "data": "id"
            },
            {
                "data": "header"
            },
             {
                "data": "preview"
             },
             {
                "data": "text",
                "render": render.text
             },
             {
                "data": "logo",
                "render": render.logo
             },
             {
                "data": "image",
                "render": render.jpeg
             },
             {
                "data": "available",
                "render": render.available
             },
            {
                "orderable": false,
                "defaultContent": "",
                "render": render.editBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": render.deleteBtn
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            if (!data.available) {
                $(row).attr("data-product-available", false);
            }
        }
    });
});

context.add = function() {
    context.editArticleForm.find(":input").val("");
    $("#edit-article").modal();
}

context.updateRow = function(id) {
    context.editArticleForm.find(":input").val("");
    $("#edit-article").html(i18n["edit-article"]);
    $.get(ajax.url + id, function (data) {
        $.each(data, function (key, value) {
            context.editArticleForm.find("input[name='" + key + "']").val(value);
            context.editArticleForm.find("textarea[name='" + key + "']").val(value);
        });
        $('#edit-article').modal();
    });
}

context.deleteRow = function(id) {
    if (confirm(i18n['common.confirm'])) {
        $.ajax({
            url: ajax.url + id,
            type: "DELETE"
        }).done(function () {
            ajax.updateTable();
            successNoty("common.deleted");
        });
    }
}

context.openEditArticleImage = function(id) {
    $('#edit-article-image').modal();
    $('#edit-article-image-delete-btn')[0].setAttribute('onclick','ajax.deleteImage(' + id + ')');
    $('#edit-article-image-save-btn')[0].setAttribute('onclick','ajax.saveImage(' + id + ')');
    ajax.updateEditArticleImage(id);
}

context.openEditArticleLogo = function(id){
    $('#edit-article-logo').modal();
    $('#edit-article-logo-delete-btn')[0].setAttribute('onclick','ajax.deleteLogo(' + id + ')');
    $('#edit-article-logo-save-btn')[0].setAttribute('onclick','ajax.saveLogo(' + id + ')');
    ajax.updateEditArticleLogo(id);
} 

context.openArticleText = function(id){
    $('#article-text').modal();
    ajax.setArticleText(id);
} 

ajax.setArticleText = function(id) {
    $.get(ajax.url + id, render.setArticleText);
}

ajax.updateEditArticleImage = function(id) {
    $.get(ajax.url + id, render.updateEditArticleImage);
}

ajax.updateEditArticleLogo = function(id) {
    $.get(ajax.url + id, render.updateEditArticleLogo);
}


