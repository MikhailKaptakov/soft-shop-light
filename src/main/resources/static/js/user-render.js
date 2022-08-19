render = {};

render.product = {};

render.product.render = function(productArray) {
    const productListContainer = render.product.renderProductListContainer("product-list");
    productListContainer.innerHTML = "";
    for (const product of productArray) {
        render.product.renderProductContainer(product, productListContainer);
    }
}

render.product.renderProductListContainer = function(containerId) {
    return document.getElementById(containerId);
}
render.product.renderProductContainer = function(product, productListContainer) {
    const productContainer = render.product.getProductContainer(product);
    const fieldContainer = render.product.getFieldContainer(product);
    const dataContainer = render.product.getDataContainer(product);
    const toCartButton = render.product.getToCartButton(product);
    const toFullDescription = render.product.getModalProduct(product);
    productListContainer.appendChild(productContainer);
    productContainer.append(fieldContainer);
    fieldContainer.append(dataContainer);
    fieldContainer.append(toFullDescription);
    fieldContainer.append(toCartButton);
}

render.product.getProductContainer = function(product) {
    const productContainer = document.createElement("li");
    productContainer.id = "p" + product.id;
    productContainer.className = "col mb-5";
    return productContainer;
}

render.product.getFieldContainer = function(product) {
    const fieldContainer = document.createElement("div");
    fieldContainer.id = "f" + product.id;
    fieldContainer.className = "card h-100";
    const image = render.product.getImage(product);
    fieldContainer.append(image);
    return fieldContainer;
}

render.product.getDataContainer = function(product) {
    const dataContainer = document.createElement("div");
    dataContainer.id = "d" + product.id;
    dataContainer.className = "card-body p-4";
    dataContainer.append(render.product.getDataRender(product));
    return dataContainer;
}

render.product.getImage = function(product) {
    const image = document.createElement("img");
    image.className="card-img-top";
    if (product.image !== undefined && product.image !== null) {
        image.src = "data:image/svg+xml;base64,"+ product.image;
        image.setAttribute("onerror","this.src='/default.svg'");
    } else {
        image.src = "/default.svg";
    }
    return image;
}

render.product.getDataRender = function(product) {
    const data = document.createElement("div");
    data.className = "text-start";
    const name = document.createElement("div");
    name.className = "text-center";
    let dataElement = document.createElement("h5");
    dataElement.className = "fw-bolder mark-product-name";
    dataElement.innerText = product.name;
    name.append(dataElement);
    dataElement = document.createElement("p");
    dataElement.innerText = product.price + "р.";
    name.append(dataElement);
    data.append(name);
    const table = document.createElement("table");
    table.className = "table";
    const tBody = document.createElement("tbody");
    table.append(tBody);
    tBody.append(render.product.getDescriptionTableLine("Страна:", product.country));
    tBody.append(render.product.getDescriptionTableLine("Вендор:", product.vendor));
    tBody.append(render.product.getDescriptionTableLine("Страна:", product.country));
    name.append(table);
    return data;
}

render.product.getDescriptionTableLine = function(text, fieldValue) {
    const tr = document.createElement("tr");
    const th = document.createElement("th");
    th.setAttribute("scope", "row");
    th.innerText = text;
    const td = document.createElement("td");
    td.innerText = fieldValue;
    tr.append(th);
    tr.append(td);
    return tr;
}

render.product.getToCartButton = function(product) {
    return render.product.createButton("ajaxApi.addProductToCart(" + product.id + ")", "В корзину");
}

render.product.getModalProduct = function(product) {
    return render.product.createButton("render.modalProduct.openProductModal(" + product.id + ")", "Описание");
}

render.product.createButton = function(functionNameString, buttonText) {
    const buttonWrapper = document.createElement("div");
    buttonWrapper.className = "card-footer p-4 pt-0 border-top-0 bg-transparent text-center";

    const button = document.createElement("button");
    button.setAttribute("onclick", functionNameString);
    button.className =  "btn text-center";
    buttonWrapper.append(button);

    const buttonLetter = document.createElement("span");
    buttonLetter.className = "btn btn-outline-dark mt-auto";
    buttonLetter.innerText = buttonText;
    button.append(buttonLetter);
    return buttonWrapper;
}

render.modalProduct = {};

render.modalProduct.openProductModal = function(productId) {
    const product = ajaxApi.productMap.get(productId);
    $('#modal-product').modal();
    $('#modal-product-name')[0].innerText = product.name;
    const img = $('#modal-product-image')[0];
    img.src = "data:image/svg+xml;base64,"+ product.image;
    img.setAttribute("onerror","this.src='/default.svg'");
    $('#modal-product-vendor')[0].innerText = product.vendor;
    $('#modal-product-country')[0].innerText = product.country;
    $('#modal-product-license')[0].innerText = product.licenseTime;
    $('#modal-product-delivery')[0].innerText = product.deliveryTimeInDays + " дней";
    $('#modal-product-nds')[0].innerText = product.ndsInclude?"Включено":"Не включено";
    $('#modal-product-tech-support')[0].innerText = product.requiredTechnicalSupport?"Есть":"Отсутствует";
    $('#modal-product-description')[0].innerText = product.description;
    $('#btn-product-add-to-cart')[0].setAttribute("onclick", "ajaxApi.addProductToCart(" + product.id + ")");
}; 

render.cart = {};

render.cart.openCart = function() {
    $('#modal-cart').modal();
    ajaxApi.getCart();
}; 


render.cart.render = function(positions) {
    const tbody = $('#modal-cart-tbody')[0];
    tbody.innerHTML = "";
    for (const position of positions) {
        const tr = render.cart.getTableRow(position);
        tbody.append(tr);
        render.cart.imageCell(position);
        render.cart.nameCell(position);
        render.cart.priceCell(position);
        render.cart.addCell(position);
        render.cart.valueCell(position);
        render.cart.minusCell(position);
        render.cart.fullPriceCell(position);
        render.cart.deleteCell(position);
    }
    const tr = render.cart.getTableRow(-1);
    tbody.append(tr);
    render.cart.getTableRowCell(-1, 5).innerText = "Итого:";
    render.cart.getTableRowCell(-1, 6).innerText =  render.cart.getTotalPrice(positions);
}

render.cart.getTotalPrice = function(positions) {
    let summ = 0;
    for (position of positions) {
        summ += render.cart.getPositionPrice(position);
    }
    return summ;
}

render.cart.getPositionPrice = function(position) {
    return position.value*position.product.price;
}

render.cart.imageCell = function(position) {
    const image = document.createElement("img");
    if (position.product.image !== undefined && position.product.image !== null) {
        image.src = "data:image/svg+xml;base64,"+ position.product.image;
        image.setAttribute("onerror","this.src='/default.svg'");
    } else {
        image.src = "/default.svg";
    }
    const cell = render.cart.getTableRowCell(position, 0);
    cell.innerHTML = "";
    cell.append(image);
}

render.cart.nameCell = function(position) {
    const name = document.createElement("p");
    name.innerText = position.product.name;
    const cell = render.cart.getTableRowCell(position, 1);
    cell.innerHTML = "";
    cell.append(name);
}

render.cart.priceCell = function(position) {
    const price = document.createElement("p");
    price.innerText = position.product.price;
    const cell = render.cart.getTableRowCell(position, 2);
    cell.innerHTML = "";
    cell.append(price);
}

render.cart.valueCell = function(position) {
    const value = document.createElement("p");
    value.innerText = position.value;
    const cell = render.cart.getTableRowCell(position, 4);
    cell.innerHTML = "";
    cell.append(value);
}

render.cart.fullPriceCell = function(position) {
    const fullPrice = document.createElement("p");
    fullPrice.innerText = render.cart.getPositionPrice(position);
    const cell = render.cart.getTableRowCell(position, 6);
    cell.innerHTML = "";
    cell.append(fullPrice);
}

render.cart.deleteCell = function(position) {
    const button = document.createElement("button");
    button.setAttribute("onclick", "ajaxApi.deleteFromCart(" + position.product.id + ");");
    button.setAttribute("type", "button");
    button.className = "btn btn-outline-dark mt-auto";
    const icon = document.createElement("span");
    icon.className = "fa fa-remove";
    button.append(icon);
    const cell = render.cart.getTableRowCell(position, 7);
    cell.innerHTML = "";
    cell.append(button);
}

render.cart.addCell = function(position) {
    const button = document.createElement("button");
    button.setAttribute("onclick", "ajaxApi.addOne(" + position.product.id + ");");
    button.setAttribute("type", "button");
    button.className = "btn btn-outline-dark mt-auto";
    const icon = document.createElement("span");
    icon.className = "fa fa-plus";
    button.append(icon);
    const cell = render.cart.getTableRowCell(position, 3);
    cell.innerHTML = "";
    cell.append(button);
}

render.cart.minusCell = function(position) {
    const button = document.createElement("button");
    button.setAttribute("onclick", "ajaxApi.minusProduct(" + position.product.id + ");");
    button.setAttribute("type", "button");
    button.className = "btn btn-outline-dark mt-auto";
    const icon = document.createElement("span");
    icon.className = "fa fa-minus";
    button.append(icon);
    const cell = render.cart.getTableRowCell(position, 5);
    cell.innerHTML = "";
    cell.append(button);
}

render.cart.updateTable = function() {
    ajaxApi.getCart();
}

render.cart.getTableRow = function(position) {
    const tr = document.createElement("tr");
    tr.id = render.cart.getTableRowId(position);
    for(let i=0; i<8; i++) {
        let td = document.createElement("td");
        td.id= render.cart.getTableRowCellId(position, i);
        tr.append(td);
    }
    return tr;
}

render.cart.getTableRowId = function(position) {
    if (typeof position === "number") {
        return "mp"+ position;
    }
    return "mp" + position.product.id;
}

render.cart.getTableRowCell = function(position, cellIndex) {
    return $("#" + render.cart.getTableRowCellId(position, cellIndex))[0];
}

render.cart.getTableRowCellId = function(position, cellIndex) {
    if (typeof position === "number") {
        return "mp"+ position + "c" + cellIndex;
    }
    return "mp"+ position.product.id + "c" + cellIndex;
}

render.order = {};

render.order.render = function(orderForm) {
    $("#firstname")[0].innerText = orderForm.firstname;
    $("#surname")[0].innerText = orderForm.surname;
    $("#secondName")[0].innerText = orderForm.secondName;
    $("#email")[0].innerText = orderForm.email;
    $("#telephoneNumber")[0].innerText = orderForm.telephoneNumber;
    $("#companyName")[0].innerText = orderForm.companyName;
    $("#address")[0].innerText = orderForm.address;
    $("#comment")[0].innerText = orderForm.comment;
}

render.order.renderVerify = function(orderData) {
    $("#modal-verify-order-text")[0].innerText = orderData[0];
}


render.alphabet = {};

render.alphabet.getProductContainer = function(product) {
    const productContainer = document.createElement("li");
    productContainer.id = "ap" + product.id;
    productContainer.className = "col mb-1";
    return productContainer;
}

render.alphabet.getFieldContainer = function(product) {
    const fieldContainer = document.createElement("div");
    fieldContainer.id = "af" + product.id;
    fieldContainer.className = "card h-100";
    return fieldContainer;
}

render.alphabet.getButton = function(product, type) {
    const button = document.createElement("button");
    button.id = "btnp" + product.id;
    button.className = "btn-outline btn";
    button.setAttribute("onclick", "render.modalProduct.openProductModal(" + product.id + ")");
    const field = document.createElement("span");
    field.className ="mark-field";
    if (type = "name") {
        field.innerText = product.name;
    } else if (type = "vendor"){
        field.innerText = product.vendor;
    }
    button.append(field);
    return button;
}

render.alphabet.render = function(productArray, type) {
    const productListContainer =$("#alphabetic-product-list")[0];
    productListContainer.innerHTML = "";
    for (const product of productArray) {
        const container = render.alphabet.getProductContainer(product);
        const fieldContainer = render.alphabet.getFieldContainer(product);
        const button = render.alphabet.getButton(product, type)
        container.append(fieldContainer);
        fieldContainer.append(button);
        productListContainer.append(container);
    }
}



function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(key) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + /*i18n[key]*/ key,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
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

function handlefailNoty(message) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;" + message + "<br>",
        type: "error",
        layout: "bottomRight"
    });
    failedNote.show()
}




//todo refactor js. noty to отдельный файл.без дублирования. Подгружать в header-tags.
//todo Весь рендер вынести в отдельные файлы имя-страницы-рендер