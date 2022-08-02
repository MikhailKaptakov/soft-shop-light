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
    const productContainer = render.product.getProductContainer();
    productContainer.id = "product" + product.id;
    productListContainer.appendChild(productContainer);

    productContainer.append(render.product.fieldData("Name", product.name));
    productContainer.append(render.product.fieldData("Vendor", product.vendor));
    productContainer.append(render.product.fieldData("Country", product.country));
    productContainer.append(render.product.fieldData("License", product.license));
    productContainer.append(render.product.fieldData("Price", product.price));
    productContainer.append(render.product.fieldData("Deliver by", product.deliveryTimeInDays));
    productContainer.append(render.product.fieldData("NDS", product.ndsInclude));
    productContainer.append(render.product.fieldData("Support", product.requiredTechnicalSupport));
    productContainer.append(render.product.fieldData("Description", product.description));
}

render.product.getProductContainer = function() {
    const productContainer = document.createElement("div");
    return productContainer;
}

render.product.fieldData = function(title, value) {
    const field = document.createElement("div");
    const p = document.createElement("p");
    p.innerText = title + " " + value;
    field.appendChild(p);
    return field;
}