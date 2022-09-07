
const i18n = [];

function initI18n() {
    i18n["addTitle"] = $("#i18-common-add")[0].innerText;
    i18n["editTitle"] = $("#i18-common-edit")[0].innerText;
    i18n["common.deleted"] = $("#i18-common-deleted")[0].innerText;
    i18n["common.saved"] = $("#i18-common-saved")[0].innerText;
    i18n["common.enabled"] = $("#i18-common-enabled")[0].innerText;
    i18n["common.disabled"] = $("#i18-common-disabled")[0].innerText;
    i18n["common.search"] = $("#i18-common-search")[0].innerText;
    i18n["common.confirm"] = $("#i18-common-confirm")[0].innerText;
};
initI18n();


let failedNote;

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


function setActiveRef(id) {
    const elem = $('#' + id)[0];
    elem.classList.toggle("active");
    elem.setAttribute("aria-current","page");
}