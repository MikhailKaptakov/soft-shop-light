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
    productListContainer.appendChild(productContainer);
    productContainer.append(fieldContainer);
    fieldContainer.append(dataContainer);
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
    if (image !== undefined) {
        fieldContainer.append(image);
    } else {
        //добавить загрузку стандартного изображения
    }
    //здесь же импорт кнопок
    return fieldContainer;
}

render.product.getDataContainer = function(product) {
    const dataContainer = document.createElement("div");
    dataContainer.id = "d" + product.id;
    dataContainer.className = "card-body p-4";
    dataContainer.innerHTML = '<div class="text-center">' +
                                    '<h5 class="fw-bolder">' + product.name + '</h5>' +
                                    '<p>' + product.price + ' р.' + '</p>' + '<ul class="list-unstyled">' +
                                    '<li>' + product.country + '</li>' +
                                    '<li>' + 'Время лицензии ' + product.licenseTime + '</li>' +
                               '</ul></div>';
    return dataContainer;
}

render.product.getImage = function(product) {
    const image = document.createElement("img");
    image.className="card-img-top";
    image.src = "data:image/png;base64,"+ product.image;
    return image;
}