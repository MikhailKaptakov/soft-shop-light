const ajaxApi = {};

ajaxApi.url = "/ui/products/";

ajaxApi.getAllAvailable = function() {
    $.get(ajaxApi.url, ajaxApi.getAllDataHandler);
}

ajaxApi.getAllDataHandler = function(data) {
    render.product.render(data);
}

ajaxApi.getAllAvailable();