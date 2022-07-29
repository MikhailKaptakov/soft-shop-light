const productAjaxUrl = "/admin/rest/products/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: productAjaxUrl,
    updateTable: function () {
        $.get(productAjaxUrl, updateTableByData);
    }
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
                "data": "description"
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
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
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
});