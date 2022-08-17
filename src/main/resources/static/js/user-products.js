let failedNote;
const ajaxApi = {};
ajaxApi.productMap;

ajaxApi.productsUrl = "/ui/products/";
ajaxApi.cartUrl = "/ui/cart/";
ajaxApi.orderUrl = "/ui/orders/";

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
        render.cart.updateTable();
    });
}

ajaxApi.getCart = function() {
    $.get(ajaxApi.cartUrl, ajaxApi.getCartDataHandler);
}

ajaxApi.getCartDataHandler = function(data) {
    render.cart.render(data.positions);
}

ajaxApi.goToOrderForm = function() {
    const func = function(data) {
        if (data) {
            handlefailNoty("Корзина пуста");
        } else {
            $("#modal-cart").modal("hide");
            $("#modal-order").modal();
            $.get(ajaxApi.cartUrl + "form", ajaxApi.getUserOrderFormDataHandler);
        }
    };
    ajaxApi.isEmptyCart(func);
}

ajaxApi.isEmptyCart = function(func) {
    $.get(ajaxApi.cartUrl + "check", func);
}

ajaxApi.getUserOrderFormDataHandler = function(data) {
    render.order.render(data);
}

ajaxApi.sendOrderForm = function() {
    $.ajax({
        type: "POST",
        url: ajaxApi.cartUrl + "form",
        data: $("#modal-order-form").serialize()
    }).done(function () {
        ajaxApi.verificationModal();
    });
}

ajaxApi.verificationModal = function() {
    $("#modal-order").modal("hide");
    $("#modal-verify-order").modal();
    ajaxApi.getOrderString();
    //todo запросы на заполнение открытие и рендер формы верификации
}

ajaxApi.getOrderString = function() {
    $.get(ajaxApi.orderUrl, render.order.renderVerify);
}

ajaxApi.sendOrderFinal = function() {
    $.ajax({
        url: ajaxApi.orderUrl,
        type: "POST"
    }).done(function () {
        $("#modal-verify-order").modal("hide");
        successNoty("Заказ отправлен");
    });
}

ajaxApi.getAllAvailable();
ajaxApi.init();



