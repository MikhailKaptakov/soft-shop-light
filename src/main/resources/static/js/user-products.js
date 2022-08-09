let failedNote;
const ajaxApi = {};
ajaxApi.productMap;

ajaxApi.url = "/ui/products/";

ajaxApi.getAllAvailable = function() {
    $.get(ajaxApi.url, ajaxApi.getAllDataHandler);
}

ajaxApi.getAllDataHandler = function(data) {
    render.product.render(data);
    ajaxApi.productMap = ajaxApi.getDataMap(data);
    $(function(){
        $('#product-list').listnav({
            filterSelector: '.mark-product-name',
            includeNums: false,
            removeDisabled: true,
        });
    });
}

ajaxApi.getDataMap = function(data) {
    dataMap = new Map();
    for (const product of data) {
        dataMap.set(product.id, product);
    }
    return dataMap;
}

ajaxApi.getAllAvailable();



