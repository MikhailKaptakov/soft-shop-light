let failedNote;
const ajaxApi = {};
ajaxApi.productMap;

ajaxApi.productsUrl = "/ui/products/";
ajaxApi.cartUrl = "/ui/cart/";

ajaxApi.init = function() {
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });
    $.ajaxSetup({cache: false});
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}

ajaxApi.getAllAvailable = function() {
    $.get(ajaxApi.productsUrl, ajaxApi.getAllDataHandler);
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

ajaxApi.addProductToCart = function(id) {
    $.ajax({
        url: ajaxApi.cartUrl + "add/" + id,
        type: "POST",
    }).done(function () {
            successNoty("Добавлено в корзину");
    });
}

ajaxApi.addOne = function(id) {
    $.ajax({
        url: ajaxApi.cartUrl + "add/" + id,
        type: "POST",
    }).done(function () {
            render.cart.updateTable();
    });
}

ajaxApi.deleteFromCart = function(id) {
    $.ajax({
        url: ajaxApi.cartUrl + "delete/" + id,
        type: "POST",
    }).done(function () {
            successNoty("Товар удалён из корзины");
            render.cart.updateTable();
    });
}

ajaxApi.clearCart = function() {
    $.ajax({
        url: ajaxApi.cartUrl + "clear/",
        type: "POST",
    }).done(function () {
            successNoty("Корзина очищена");
            render.cart.updateTable();
    });
}

ajaxApi.minusProduct = function(id) {
    $.ajax({
        url: ajaxApi.cartUrl + "minus/" + id,
        type: "POST",
    }).done(function () {
            render.cart.updateTable();
    });
}

ajaxApi.setProductValueToCart = function(id, value) {
    $.ajax({
        url: ajaxApi.cartUrl + "set/" + id,
        type: "POST",
        data: value,
    }).done(function () {
            //todo
    });
}

ajaxApi.getCart = function() {
    $.get(ajaxApi.cartUrl, ajaxApi.getCartDataHandler);
}

ajaxApi.getCartDataHandler = function(data) {
    render.cart.render(data.positions);
}

ajaxApi.getAllAvailable();
ajaxApi.init();



