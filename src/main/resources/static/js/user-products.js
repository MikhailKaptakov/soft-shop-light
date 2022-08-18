let failedNote;
const ajaxApi = {};
ajaxApi.productMap;

ajaxApi.productsUrl = "/ui/products/";
ajaxApi.cartUrl = "/ui/cart/";
ajaxApi.orderUrl = "/ui/orders/";

ajaxApi.getProductList = function() {
    return Array.from(ajaxApi.productMap.values())
}

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


ajaxApi.getFavorite = function() {
    $.get(ajaxApi.productsUrl, ajaxApi.getFavoriteDataHandler);
}

ajaxApi.getFavoriteDataHandler = function(data) {
    render.product.render(data);
}

ajaxApi.getAllAvailable = function() {
    $.get(ajaxApi.productsUrl, ajaxApi.getDataMap);
}

ajaxApi.getDataMap = function(data) {
    dataMap = new Map();
    for (const product of data) {
        dataMap.set(product.id, product);
    }
    ajaxApi.productMap = dataMap;
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
const alphabetic = {};

ajaxApi.getAllAvailableToAlphabeticName = function() {
    ajaxApi.getAllAvailable();
    ajaxApi.getAllAvailableToAlphabeticNameDataHandler(ajaxApi.getProductList());
}

ajaxApi.getAllAvailableToAlphabeticNameDataHandler = function(data) {
    $("#alphabetic-product-list-nav")[0].innerHTML = "";
    render.alphabet.render(data, "name");
    $(function(){
        $('#alphabetic-product-list').listnav({
            filterSelector: '.mark-field',
            includeNums: false,
            removeDisabled: true,
        });
    });
}

alphabetic.slidePannel = function() {
    const nav = $("#alphabetic-navigator");
    if (nav[0].getAttribute("stateIsOpen") === "false") {
        nav.fadeIn();
        nav[0].setAttribute("stateIsOpen", "true");
        ajaxApi.getAllAvailableToAlphabeticName();
    } else {
        nav.fadeOut();
        nav[0].setAttribute("stateIsOpen", "false");  
    }
    alphabetic.toggleArrow();
};

alphabetic.toggleArrow = function() {
    const arrows = $("#alphabetic-nav-btn").children("i");
    arrows[0].classList.toggle("fa-arrow-down");
    arrows[0].classList.toggle("fa-arrow-up");
    arrows[1].classList.toggle("fa-arrow-down");
    arrows[1].classList.toggle("fa-arrow-up");
}

searcher = {};

searcher.slidePanel = function() {
    const nav = $("#search-panel-slide")[0];
    nav.classList.toggle("d-none");
    nav.classList.toggle("d-inline");
    searcher.toggleArrow(); 
};

searcher.toggleArrow = function() {
    const arrows = $("#search-panel-btn").children("i");
    arrows[0].classList.toggle("fa-arrow-right");
    arrows[0].classList.toggle("fa-arrow-left");
}

searcher.startSearch = function() {
    const selector = $("#search-panel-selector")[0];
    const type = selector.options[selector.selectedIndex].value;
    const value = $("#search-panel-window")[0].value; 
    searcher.search(type, value); 
}

searcher.search = function(fieldname, expectedValue) {
    ajaxApi.getAllAvailable();
    const nonFilterData = ajaxApi.getProductList();
    const filteredData = searcher.searchCore(nonFilterData, fieldname, expectedValue);
    render.product.render(filteredData);
}

searcher.searchCore = function(dataList, fieldname, expectedValue) {
    const outerData = [];
    for (const data of dataList) {
        const value = data[fieldname];
        if (value.toUpperCase().indexOf(expectedValue.toUpperCase()) >-1) {
            outerData.push(data);
        }
    }
    searcher.sort(outerData, fieldname);
    return outerData;
}

searcher.sort = function(outerData, fieldname) {
    const compare = function(a, b) {
        if (a[fieldname] < b[fieldname]) {
            return -1;
        } else if (a[fieldname] > b[fieldname]) {
            return 1;
        } else {
            return 0;
        }
    }
    outerData.sort(compare);
}
ajaxApi.getAllAvailable();
ajaxApi.getFavorite();
ajaxApi.init();



