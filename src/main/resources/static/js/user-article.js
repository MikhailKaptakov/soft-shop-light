const ajax = {};

ajax.url = {};

ajax.url.user = "/ui/article/";

ajax.init = function() {
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

ajax.initial = function(id) {
    $.get(ajax.url.user + id, ajax.getHandler);
}


ajax.getHandler = function(data) {
    render.inputImages(data);
}

render = {};

render.init = function() {
    const id = $('#article-id')[0].innerText;
    ajax.initial(id);
}

render.inputImages = function(data) {
    for (element of $('.input-article-image-here')) {
        render.getArticleImage(element, data);
    }
}

render.inputLogo = function(data) {
    for (element of $('.input-article-logo-here')) {
        render.getArticleLogo(element, data);
    }
}

render.image = {};

render.getArticleLogo = function(element, data) {
    const strId = "inserted-image-" + data.id;
    const defaultImg = "/default-article.svg";
    const dataType = "svg+xml";
    if (element.nodeName === "IMG") {
        render.image.imgTagChange(element, data.logo, dataType, defaultImg , strId); 
    } else {
        const image = render.image.imgTag(data.logo, dataType, defaultImg , strId); 
        element.append(image);
    }
}

render.getArticleImage = function(element, data) {
    const strId = "inserted-image-" + data.id;
    const defaultImg = "/default.jpg";
    const dataType = "jpeg";
    if (element.nodeName === "IMG") {
        render.image.imgTagChange(element, data.image, dataType, defaultImg , strId); 
    } else {
        const image = render.image.imgTag(data.image, dataType, defaultImg , strId); 
        element.append(image);
    }
}

render.image.imgTagChange = function(element, data, dataType, defaultSrc, id) {
    if (data !== undefined && data !== null) {
        element.setAttribute("id", id);
        element.src = "data:image/"+ dataType +";base64,"+ data;
        element.setAttribute("onerror","this.src='" + defaultSrc + "'");
    } else {
        element.src = defaultSrc;
    }
}

render.image.imgTag = function(data, dataType, defaultSrc, id) {
    const image = document.createElement("img");
    if (data !== undefined && data !== null) {
        image.src = "data:image/"+ dataType +";base64,"+ data;
        image.setAttribute("onerror","this.src='" + defaultSrc + "'");
    } else {
        image.src = defaultSrc;
    }
    image.setAttribute("id", id);
    return image;
}

ajax.init();
render.init();

//todo скопированно, вынести этот код из админки и здесь в отдельный файл;
