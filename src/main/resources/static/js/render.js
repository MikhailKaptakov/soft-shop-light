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
    const toFullDescription = render.product.getFullDescription(product);
    productListContainer.appendChild(productContainer);
    productContainer.append(fieldContainer);
    fieldContainer.append(dataContainer);
    fieldContainer.append(toFullDescription);
    fieldContainer.append(toCartButton);
}

render.product.getProductContainer = function(product) {
    const productContainer = document.createElement("div");
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
    dataElement.className = "fw-bolder";
    dataElement.innerText = product.name;
    name.append(dataElement);
    dataElement = document.createElement("p");
    dataElement.innerText = product.price + "р.";
    name.append(dataElement);
    dataElement = document.createElement("p");
    dataElement.innerText = product.country;
    name.append(dataElement);
    data.append(name);
    dataElement = document.createElement("p");
    dataElement.innerText = "Лицензия на " + product.licenseTime;
    data.append(dataElement);
    dataElement = document.createElement("p");
    let ans = product.ndsInclude?"ДА":"НЕТ";
    dataElement.innerText = "НДС: " + ans;
    data.append(dataElement);
    dataElement = document.createElement("p");
    ans = product.requiredTechnicalSupport?"ДА":"НЕТ";
    dataElement.innerText = "Тех.поддержка: " + ans;
    data.append(dataElement);
    return data;
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
    reference.innerText = "Add to cart"; 
    textElem.append(reference);
    return button;
}

render.product.getFullDescription = function(product) {
    const button = document.createElement("div");
    button.className = "card-footer p-4 pt-0 border-top-0 bg-transparent";
    const textElem = document.createElement("div");
    textElem.className = "text-center";
    button.append(textElem);
    const reference = document.createElement("a");
    reference.className = "btn btn-outline-dark mt-auto";
    reference.href = "#";
    reference.innerText = "About"; 
    textElem.append(reference);
    return button;
}