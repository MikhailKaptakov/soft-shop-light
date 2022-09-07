context = {};
ajaxApi = {};

context.url = "/ui/article/";
context.articleUrl = "/article/";

context.init = function() {
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });
    $.ajaxSetup({cache: false});
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    context.previewContainer = $("#preview-list")[0];
    setActiveRef("home-page-id");
    $.get(context.url + "preview", ajaxApi.articlesPreviewHandler);
}



ajaxApi.articlesPreviewHandler = function (data) {
    context.articles = data;
    for (const article of data) {
        context.previewContainer.append(render.getPreviewCard(article));
    }
}

render = {};

render.getPreviewCard = function(data) {
    const container = render.elementByProperties("div", "row p-2", [{key:"id", value:"article" + data.id}]);
    const logoWrapper = render.elementByProperties("div", "col-1");
    container.append(logoWrapper);
    const img = render.getImage(data);
    const imgWrapper = render.elementByProperties("a", "", 
    [{key:"href", value: context.articleUrl + data.id}]);
    imgWrapper.append(img);
    logoWrapper.append(imgWrapper);
    const textWrapper = render.elementByProperties("div", "col-11");
    container.append(textWrapper);
    const headerWrapper = document.createElement("h5");
    const headerLink = render.elementByProperties("a", "", 
        [{key:"href", value: context.articleUrl + data.id}]);
    headerLink.innerText = data.header;
    headerWrapper.append(headerLink);
    textWrapper.append(headerWrapper);
    const preview = render.elementByProperties("p", "text-justify");
    preview.innerText = data.preview;
    textWrapper.append(preview);
    return container;
}

render.elementByProperties = function(type, className,  attributesArray = undefined) {
    const element = document.createElement(type);
    element.className = className;
    if (attributesArray !== undefined) {
        for (const prop of attributesArray) {
            element.setAttribute(prop.key, prop.value);
        }
    }
    return element;
}

render.getImage = function(data) {
    const image = document.createElement("img");
    image.className="img-thumbnail img-fluid";
    if (data.logo !== undefined && data.logo !== null) {
        image.src = "data:image/svg+xml;base64,"+ data.logo;
        image.setAttribute("onerror","this.src='/default.svg'");
    } else {
        image.src = "/default-article.svg";
    }
    return image;
};

context.init();