let form;

const productAjaxUrl = "/admin/ui/products/";
// https://stackoverflow.com/a/5064235/548473

const ctx = {
    ajaxUrl: productAjaxUrl,
    updateTable: function () {
        $.get(productAjaxUrl, updateTableByData);
    }
}

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

function setAvailable(chkbox, id) {
    var isAvailable = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: productAjaxUrl + "available/" + id,
        type: "POST",
        data: "available=" + isAvailable
    }).done(function () {
        chkbox.closest("tr").attr("data-product-available", isAvailable);
        successNoty(isAvailable ? "common.enabled" : "common.disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !isAvailable);
    });
}

function setFavorite(chkbox, id) {
    var isFavorite = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: productAjaxUrl + "favorite/" + id,
        type: "POST",
        data: "favorite=" + isFavorite
    }).done(function () {
        chkbox.closest("tr").attr("data-product-favorite", isFavorite);
        successNoty(isFavorite ? "common.enabled" : "common.disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !isFavorite);
    });
}

function setNds(chkbox, id) {
    var isNds = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: productAjaxUrl + "nds/" + id,
        type: "POST",
        data: "isNds=" + isNds
    }).done(function () {
        successNoty(isNds ? "common.enabled" : "common.disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !isNds);
    });
}

function setTechSupport(chkbox, id) {
    var isTechSupport = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: productAjaxUrl + "techSupport/" + id,
        type: "POST",
        data: "isTechSupport=" + isTechSupport
    }).done(function () {
        successNoty(isTechSupport ? "common.enabled" : "common.disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !isTechSupport);
    });
}

// $(document).ready(function () {
$(function () {
    makeEditable({
        "columns": [
            {
            "data": "id"
            },
            {
                "data": "name"
            },
             {
                "data": "vendor"
             },
             {
                "data": "country"
             },
             {
                "data": "licenseTime"
             },
             {
                "data": "price"
             },
             {
                "data": "description",
                "render": function (data, type, row) {
                    if (row.description.length > 50) {
                        return row.description.substr(0,50) + "...";
                    } 
                    return data;
                }
             },
             {
                "data": "deliveryTimeInDays"
             },
             {
                "data": "ndsInclude",
                "render": function (data, type, row) {
                    if (type === "display") {
                        return "<input type='checkbox' " + (data ? "checked" : "") + " onclick='setNds($(this)," + row.id + ");'/>";
                    }
                    return data;
                }
             },
             {
                "data": "requiredTechnicalSupport",
                "render": function (data, type, row) {
                    if (type === "display") {
                        return "<input type='checkbox' " + (data ? "checked" : "") + " onclick='setTechSupport($(this)," + row.id + ");'/>";
                    }
                    return data;
                }
             },
             {
                "data": "available",
                "render": function (data, type, row) {
                    if (type === "display") {
                        return "<input type='checkbox' " + (data ? "checked" : "") + " onclick='setAvailable($(this)," + row.id + ");'/>";
                    }
                    return data;
                }
             },
             {
                "data": "favorite",
                "render": function (data, type, row) {
                    if (type === "display") {
                        return "<input type='checkbox' " + (data ? "checked" : "") + " onclick='setFavorite($(this)," + row.id + ");'/>";
                    }
                    return data;
                }
             },
             {
                "data": "image",
                "render": function (data, type, row) {
                    if (row.id!==null && row.image !== null && row.image !== undefined) {
                        return '<img id="img' + row.id + '" src="data:image/svg+xml;base64,'+ row.image +'">';
                    }
                    return "";
                }
             },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderImgBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
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
    setActiveRef("admin-products-page-id");
    
});

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
        ctx.updateTable();
    });
}

