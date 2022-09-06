render = {};

render.logo = function(data,type,row) {
    if (type === "display" && data!=="" && data !== undefined && data!==null) {
        return '<img id="logo' + row.id +
         '" src="data:image/svg+xml;base64,'
         + data +'"' + ' onclick="context.openEditArticleLogo('+ row.id +')">';
    }
    return '<img id="logo' + row.id +
    '" src="/default-article.svg" onclick="context.openEditArticleLogo('+ row.id +')">';
}

render.available = function (data,type,row) {
    if (type === "display") {
        return "<input type='checkbox' " + (data ? "checked" : "") 
        + " onclick='ajax.setAvailable($(this)," + row.id + ");'/>";
    }
    return data;
}

render.jpeg = function(data,type,row){
    if (type === "display") {
        return '<button type="button" class="btn" onclick="context.openEditArticleImage(' + row.id + ')">' +
        '<span class="fa fa-paint-brush text-white"></span></button>';
    }
    return "";
}

render.text = function(data,type,row){
    if (type === "display") {
        return '<button type="button" class="btn" onclick="context.openArticleText(' + row.id + ')">' +
        '<span class="fa fa-file-text-o text-white"></span></button>';
    }
    return "";
}

render.editBtn = function(data, type, row) {
    if (type === "display") {
        return "<a onclick='context.updateRow(" + row.id + ");'><span class='fa fa-pencil text-white'></span></a>";
    }
}

render.deleteBtn = function(data, type, row) {
    if (type === "display") {
        return "<a onclick='context.deleteRow(" + row.id + ");'><span class='fa fa-remove text-white'></span></a>";
    }
}

render.updateEditArticleImage = function(data) {
    if (data.image !== undefined || data.image !== null) {
        const img = $('#edit-article-image-preview')[0];
        img.src = "data:image/jpeg;base64,"+ data.image;
        img.setAttribute("onerror","this.src='/default.jpg'");
    }
}

render.updateEditArticleLogo = function(data) {
    if (data.image !== undefined || data.image !== null) {
        const img = $('#edit-article-logo-preview')[0];
        img.src = "data:image/svg+xml;base64,"+ data.image;
        img.setAttribute("onerror",'this.src="/default-article.svg"');
    }
}

render.setArticleText = function(data) {
        $('#article-text-header')[0].innerText = data.header;
        $('#article-text-body')[0].innerHTML = data.text;
        for (element of $('.input-article-image-here')) {
            render.getArticleImage(element, data);
        }
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