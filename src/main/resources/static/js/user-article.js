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

render = {};

render.init = function() {
   render.articleText.inputImage();
   render.articleText.inputLogo();
   render.articleText.inputBackgroundImg();
   render.articleText.setClassesToOuterContainer();
   render.articleText.setClassesToBody();

}

render.articleText = {};

render.articleText.setClassesToOuterContainer = function() {
    const outSettings = $("#set-outer-div-classes")[0];
    const container = $("#article-container")[0];
    if (outSettings !== undefined) {
        for (const cl of outSettings.classList) {
            container.classList.add(cl);
        }
        outSettings.remove();
    }
} 

render.articleText.setClassesToBody = function() {
    const outSettings = $("#set-body-classes")[0];
    const container = $(document.body)[0];
    if (outSettings !== undefined) {
        for (const cl of outSettings.classList) {
            container.classList.add(cl);
        }
        outSettings.remove();
    }
}


render.articleText.inputImage = function() {
    render.articleText.inputImgTag("article-image", "input-article-image-here");
}

render.articleText.inputLogo = function() {
    render.articleText.inputImgTag("article-logo", "input-article-logo-here");
}

render.articleText.inputImgTag = function(prototipeId, elementsClass) {
    const img = $("#"+prototipeId)[0];
    for (element of $('.'+elementsClass)) {
        render.articleText.appendImagesToArticle(element, img.getAttribute("src"), img.getAttribute("onerror"));
    }
}

render.articleText.appendImagesToArticle = function(element, src, onerror) {
    if (element.nodeName === "IMG") {
        render.articleText.insertSrcToImgTag(element, src, onerror); 
    } else {
        render.articleText.appendImgToElement(element, src, onerror);
    }
}

render.articleText.insertSrcToImgTag = function(element, src, onerror) {
    element.src = src;
    element.setAttribute("onerror", onerror);
}

render.articleText.appendImgToElement = function(element, src, onerror) {
    const image = document.createElement("img");
    image.src = src;
    image.setAttribute("onerror", onerror);
    element.append(image);
}

render.articleText.inputBackgroundImg = function() {
    for (element of $('.input-back-article-image-here')) {
        render.articleText.insertBackground(element, $("#article-image")[0].getAttribute("src"));
    }
    for (element of $('.input-back-article-logo-here')) {
        render.articleText.insertBackground(element, $("#article-logo")[0].getAttribute("src"));
    }
}

render.articleText.insertBackground = function(element, src) {
    element.setAttribute("style","background-image:url(" + src + ")");
}


ajax.init();
render.init();

//todo скопированно, вынести этот код из админки и здесь в отдельный файл;
