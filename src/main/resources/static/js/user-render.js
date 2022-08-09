render = {};

render.product = {};

render.product.render = function(productArray) {
    const productListContainer = render.product.renderProductListContainer("product-list");
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
    const button = document.createElement("div");
    button.className = "card-footer p-4 pt-0 border-top-0 bg-transparent";
    const textElem = document.createElement("div");
    textElem.className = "text-center";
    button.append(textElem);
    const reference = document.createElement("a");
    reference.className = "btn btn-outline-dark mt-auto";
    reference.href = "#"; 
    reference.innerText = "В корзину"; //todo i18n
    textElem.append(reference);
    return button;
}

render.product.getModalProduct = function(product) {
    const buttonWrapper = document.createElement("div");
    buttonWrapper.className = "card-footer p-4 pt-0 border-top-0 bg-transparent text-center";

    const button = document.createElement("button");
    button.setAttribute("onclick", "render.modalProduct.openProductModal(" + product.id + ")");
    button.className =  "btn text-center";
    buttonWrapper.append(button);

    const buttonText = document.createElement("span");
    buttonText.className = "btn btn-outline-dark mt-auto";
    buttonText.innerText = "Описание"; //todo i18n
    button.append(buttonText);
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
    //todo добавить add to cart с тем же рендером и вызовом метода, что и на основной странице
}; 

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


//todo add success noty to user-products page js
//todo refactor js. noty to отдельный файл.без дублирования. Подгружать в header-tags.
//todo Весь рендер вынести в отдельные файлы имя-страницы-рендер