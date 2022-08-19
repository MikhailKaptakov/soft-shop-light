let form;
const i18n = [];

function initI18n() {
    i18n["addTitle"] = $("#i18-common-add")[0].innerText;
    i18n["editTitle"] = $("#i18-common-edit")[0].innerText;
    i18n["common.deleted"] = $("#i18-common-deleted")[0].innerText;
    i18n["common.saved"] = $("#i18-common-saved")[0].innerText;
    i18n["common.enabled"] = $("#i18-common-enabled")[0].innerText;
    i18n["common.disabled"] = $("#i18-common-disabled")[0].innerText;
    i18n["common.search"] = $("#i18-common-search")[0].innerText;
    i18n["common.confirm"] = $("#i18-common-confirm")[0].innerText;
};
initI18n();

function makeEditable(datatableOpts) {
    ctx.datatableApi = $("#datatable").DataTable(
        // https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN
        $.extend(true, datatableOpts,
            {
                "ajax": {
                    "url": ctx.ajaxUrl,
                    "dataSrc": ""
                },
                "paging": false,
                "info": true,
                "language": {
                    "search": i18n["common.search"]
                }
            }
        ));
    form = $('#detailsForm');
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

function add() {
    $("#modalTitle").html(i18n["addTitle"]);
    form.find(":input").val("");
    $("#editRow").modal();
}

function updateRow(id) {
    form.find(":input").val("");
    $("#modalTitle").html(i18n["editTitle"]);
    $.get(ctx.ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
            form.find("textarea[name='" + key + "']").val(value);
        });
        $('#editRow').modal();
    });
}

function setImageRow(id) {
   $('#imageRow').modal();
   $('#imageRow')[0].entityId = id;
}

function deleteRow(id) {
    if (confirm(i18n['common.confirm'])) {
        $.ajax({
            url: ctx.ajaxUrl + id,
            type: "DELETE"
        }).done(function () {
            ctx.updateTable();
            successNoty("common.deleted");
        });
    }
}

function updateTableByData(data) {
    ctx.datatableApi.clear().rows.add(data).draw();
}

function save() {
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + "save",
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        ctx.updateTable();
        successNoty("common.saved");
    });
}

function saveImage() {
    let fd = new FormData();
    let files = $('#image')[0].files[0];
    fd.append('image', files);

    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + "img/" + $('#imageRow')[0].entityId,
        data: fd,
        contentType: false,
        processData: false,
    }).done(function () {
        $("#imageRow").modal("hide");
        ctx.updateTable();
        successNoty("Изображение сохранено");
        $('.preview img').show();
    });
}

function deleteImage() {
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: productAjaxUrl + "img/delete/" + $('#imageRow')[0].entityId,
        type: "POST",
    }).done(function () {
        successNoty("Изображение удалено");
    });
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + i18n[key],
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function renderEditBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='updateRow(" + row.id + ");'><span class='fa fa-pencil'></span></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='deleteRow(" + row.id + ");'><span class='fa fa-remove'></span></a>";
    }
}

function renderImgBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='setImageRow(" + row.id + ");'><span class='fa fa-paint-brush'></span></a>";
    }
}

function failNoty(jqXHR) {
    closeNoty();
    var errorInfo = jqXHR.responseJSON;
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + errorInfo.typeMessage + "<br>" + errorInfo.details.join("<br>"),
        type: "error",
        layout: "bottomRight"
    });
    failedNote.show()
}