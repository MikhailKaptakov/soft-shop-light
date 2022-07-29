const customAjaxUrl = "/rest/admin/products";

const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: productsAjaxUrl,
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
};

getQuery = function (queryUrl, data) {
    $.ajax({
        type: "GET",
        url: queryUrl,
        data: data
        success:
    })
}