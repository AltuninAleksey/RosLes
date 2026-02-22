openPage();

async function openPage() {

    APP.deleteIdSample = [];

    let idDocument = document.querySelector("#idDocument").value;

    //var allForestData = await CommonBusiness.getAllForest();

    //APP.subjectrf = allForestData.subjectrf;
    //APP.forestly = allForestData.forestly;
    //APP.district_forestly = allForestData.district_forestly;
    //APP.quarter = allForestData.quarter;

    APP.userData = await CommonBusiness.getUserData();

    APP.documentData = await StatementRecalculationsBusinessDetail.getStatementRecalculationsDetailDataById(idDocument);
    //APP.subjects = await CommonBusiness.getAllSubjectrf();

    var czl = await CommonBusiness.getCZL();
    APP.subjects = [];

    var item_subject = {
        id: czl.id_main_subject,
        name_subject_RF: czl.name_main_subject
    }
    APP.subjects.push(item_subject);

    for(var i = 0; i < czl.slave_subject.length; i++) {
        var item_subject = {
            id: czl.slave_subject[i].id_subject,
            name_subject_RF: czl.slave_subject[i].name_slave_subject
        }

        APP.subjects.push(item_subject);
    }

    APP.sampleList = await StatementRecalculationsBusinessDetail.getSampleByIdListRegion(idDocument);

    APP.sortOrderTable1 = 0;

    await setDetailDataIdPage();

    setEvent();
    setDataInProfile();
}

async function setDetailDataIdPage() {

    let numberStatementInHeaderNode = document.querySelector("#numberStatementInHeader");
    let daterStatementInHeaderNode = document.querySelector("#dateStatementInHeader");
    let numberStatementNode = document.querySelector("#numberStatement");
    let dateStatementNode = document.querySelector("#dateStatement");
    let soilLotStatementNode = document.querySelector("#soilLotStatement");
    let sampleRegionStatementNode = document.querySelector("#sampleRegionStatement");
    let quarterStatementNode = document.querySelector("#quarterStatement");
    let dachaStatementNode = document.querySelector("#dachaStatement");

    numberStatementInHeaderNode.innerHTML = APP.documentData.number_region;
    daterStatementInHeaderNode.innerHTML = APP.documentData.date;
    numberStatementNode.value = APP.documentData.number_region;
    dateStatementNode.value = APP.documentData.date;
    soilLotStatementNode.value = APP.documentData.soil_lot;
    sampleRegionStatementNode.value = APP.documentData.sample_region;
    quarterStatementNode.value = APP.documentData.name_quarter;
    dachaStatementNode.value = APP.documentData.dacha;

    await setSubjectsRF();
    await setSampleList();
}

async function setEvent() {

    document
        .querySelector("#subjectStatement")
        .addEventListener('change', async (e)=>{
                await setForestly();
        });

    document
        .querySelector("#forestlyStatement")
        .addEventListener('change', async (e)=>{
                await setDistriotForestlyStatement();
        });

//    document
//        .querySelector("#distriotForestlyStatement")
//        .addEventListener('change', async (e)=>{
//                await setQuarterStatement();
//        });

    document
        .getElementById("plotDescription")
        .addEventListener('click',function() {
        getPlotDescription(APP.documentData.id_desc, APP.documentData.id);
    });

    document
        .getElementById("printFieldCard")
        .addEventListener('click',function() {
        getPrintFieldCard(APP.documentData.id_field_card, APP.documentData.id);
    });

}

async function setSampleList() {
    let sampleListTbodyNode = document.querySelector("#sampleListTbody");
    let newHtml = "";

    for(let i = 0; i < APP.sampleList.length; i++) {

        let strGetRecalculatingDetail = "getRecalculatingDetail(" + APP.sampleList[i].id +  "," + document.querySelector("#idDocument").value  + ")"

        newHtml += `<tr class="cursorPointer" onClick=${strGetRecalculatingDetail}>
                        <td class="textAlignCenter td1">${APP.sampleList[i].date}</td>
                        <td class="textAlignCenter td8">${APP.sampleList[i].number_sample}</td>
                        <td class="textAlignCenter td2">${CommonFunction.getSubjectNameByQuarterId(APP.subjects, APP.sampleList[i].id_subject_rf)}</td>
                        <td class="textAlignCenter td3">${CommonFunction.getForestlyNameByQuarterId(APP.forestly, APP.sampleList[i].id_forestly)}</td>
                        <td class="textAlignCenter td4">${CommonFunction.getDistrictForestlyNameByQuarterId(APP.district_forestly, APP.sampleList[i].id_district_forestly)}</td>
                        <td class="textAlignCenter td9">${APP.sampleList[i].dacha == null? "" : APP.sampleList[i].dacha}</td>
                        <td class="textAlignCenter td5">${APP.sampleList[i].name_quarter == null? "":APP.sampleList[i].name_quarter}</td>
                        <td class="textAlignCenter td6">${APP.sampleList[i].soil_lot}</td>` +
                        "<td style=\"width: 1%;\">" +
                            "<svg onclick=\"event.stopPropagation();deleteNewLineInSampleList(" + APP.sampleList[i].id + ")\" class=\"cursorPointer\" width=\"23px\" height=\"23px\" viewBox=\"0 0 24 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">" +
                                "<g id=\"SVGRepo_bgCarrier\" stroke-width=\"0\"></g> " +
                                "<g id=\"SVGRepo_tracerCarrier\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></g>" +
                                "<g id=\"SVGRepo_iconCarrier\">" +
                                    "<path d=\"M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17\" stroke=\"#000000\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path> " +
                                "</g>" +
                            "</svg>" +
                        "</td>" +
                    `</tr>`;
    }

    sampleListTbodyNode.innerHTML = newHtml;
}

function deleteNewLineInSampleList(del_id) {

    APP.deleteIdSample.push(del_id);

    let oldSampleList = APP.sampleList;
    APP.sampleList = [];

    for(var i = 0; i < oldSampleList.length; i++) {
        if(oldSampleList[i].id != del_id) {
            APP.sampleList.push(oldSampleList[i])
        }
    }

    setSampleList();
}

function sortByDate() {
    if(APP.sortOrderTable1 != 1) {
        APP.sampleList.sort(function(a, b) { return a.date > b.date? -1 : 1; });
        APP.sortOrderTable1 = 1;

        document.querySelector("#sortDateForTable1").innerHTML = "&#8593;";
    } else {
        APP.sampleList.sort(function(a, b) { return a.date > b.date? 1 : -1; });
        APP.sortOrderTable1 = -1;

        document.querySelector("#sortDateForTable1").innerHTML = "&#8595;";
    }

    setSampleList();

}


async function setSubjectsRF() {
    let subjectStatementNode = document.querySelector("#subjectStatement");

    let newHtml = "";

    for(let i = 0; i < APP.subjects.length; i++) {
//          if(APP.subjects[i].id == APP.documentData.id_subject_rf) {
//            subjectStatementNode.value = APP.subjects[i].name_subject_RF;
//            break;
//          }
        if(APP.subjects[i].id == APP.documentData.id_subject_rf) {
            newHtml += '<option selected value="' + APP.subjects[i].id + '">' + APP.subjects[i].name_subject_RF + '</option>';
        } else {
            newHtml += '<option value="' + APP.subjects[i].id + '">' + APP.subjects[i].name_subject_RF + '</option>';
        }
    }

    subjectStatementNode.innerHTML = newHtml;

    await setForestly();
}

async function setForestly() {
    let idSubject = document.querySelector("#subjectStatement").value;
    APP.forestly = await CommonBusiness.getForestlyByIdSubjectrf(idSubject);

    let newHtml = "";

    let forestlyStatementNode = document.querySelector("#forestlyStatement");

    for(let i = 0; i < APP.forestly.length; i++) {
        if(APP.forestly[i].id == APP.documentData.id_forestly) {
            newHtml += '<option selected value="' + APP.forestly[i].id + '">' + APP.forestly[i].name_forestly + '</option>';
        } else {
            newHtml += '<option value="' + APP.forestly[i].id + '">' + APP.forestly[i].name_forestly + '</option>';
        }
    }

    forestlyStatementNode.innerHTML = newHtml;

    await setDistriotForestlyStatement();
}

async function setDistriotForestlyStatement() {
    let idForestly = document.querySelector("#forestlyStatement").value;
    APP.district_forestly = [];

    if(idForestly != "" && idForestly != null && idForestly != undefined) {
        APP.district_forestly = await CommonBusiness.getDistrictForestlyByIdForestly(idForestly);
    }

    let newHtml = "";

    let distriotForestlyNode = document.querySelector("#distriotForestlyStatement");

    for(let i = 0; i < APP.district_forestly.length; i++) {
        if(APP.district_forestly[i].id == APP.documentData.id_district_forestly) {
            newHtml += '<option selected value="' + APP.district_forestly[i].id + '">' + APP.district_forestly[i].name_district_forestly + '</option>';
        } else {
            newHtml += '<option value="' + APP.district_forestly[i].id + '">' + APP.district_forestly[i].name_district_forestly + '</option>';
        }
    }

    distriotForestlyNode.innerHTML = newHtml;

    //await setQuarterStatement();
}

async function setQuarterStatement() {
    let idDistrictForestly = document.querySelector("#distriotForestlyStatement").value;
    APP.quarters = [];

    if(idDistrictForestly != "" && idDistrictForestly != null && idDistrictForestly != undefined) {
        APP.quarters = await CommonBusiness.getQuarterByIdDistrictForestly(idDistrictForestly);
    }

    let newHtml = "";

    let quarterStatementNode = document.querySelector("#quarterStatement");

    for(let i = 0; i < APP.quarters.length; i++) {
        if(APP.quarters[i].id == APP.documentData.id_quarter) {
            newHtml += '<option selected value="' + APP.quarters[i].id + '">' + APP.quarters[i].quarter_name + '</option>';
        } else {
            newHtml += '<option value="' + APP.quarters[i].id + '">' + APP.quarters[i].quarter_name + '</option>';
        }
    }

    quarterStatementNode.innerHTML = newHtml;
}

async function createSample() {

    let data = {
        date: new Date().toLocaleDateString('en-CA'),
        sample_area: 0,
        soil_lot: null,
        width: 0,
        lenght: 0,
        square: 0,
        id_profile: APP.userData.id,
        id_list_region: APP.documentData.id,
        mark_update: 0
    }

    let result = await StatementRecalculationsBusinessDetail.createSample(data);

    getRecalculatingDetail(result.id, document.querySelector("#idDocument").value);
}
let hasUnsavedChanges = false;
let ignoreFields = ['profile_fio','profile_phone','subjectStatement-profile','old_password','new_password','confirm_password'];

window.addEventListener('beforeunload', (event) => {
    if (hasUnsavedChanges) {
        event.preventDefault();
        event.returnValue = '';
    }
});

function trackChanges() {
    hasUnsavedChanges = true;
    //console.log('Изменение обнаружено');
}

function addListenersToField(field) {

    if (ignoreFields.includes(field.id) || ignoreFields.includes(field.name)) {
        return;
    }

    field.addEventListener('input', trackChanges);
    field.addEventListener('change', trackChanges);
    if (field.type === 'checkbox' || field.type === 'radio') {
        field.addEventListener('click', trackChanges);
    }

    //console.log('Отслеживание добавлено для:', field.name || field.id);
}

function trackAllFields() {
    const inputFields = document.querySelectorAll('input, textarea, select');
    inputFields.forEach(field => {
        addListenersToField(field);
    });
}

const observer = new MutationObserver((mutations) => {
    mutations.forEach(mutation => {
        mutation.addedNodes.forEach(node => {
            if (node.nodeType === 1) {
                if (node.matches && node.matches('input, textarea, select')) {
                    addListenersToField(node);
                }
                if (node.querySelectorAll) {
                    const nestedFields = node.querySelectorAll('input, textarea, select');
                    nestedFields.forEach(field => addListenersToField(field));
                }
            }
        });
    });
});

observer.observe(document.body, {
    childList: true,
    subtree: true
});

trackAllFields();

function resetChangesTracker() {
    hasUnsavedChanges = false;
    console.log('Трекер сброшен');
}
async function saveData() {

    if(!CommonFunction.checkMandatoryData()) {
        ShowModal('m1', 'Заполните все обязательные поля!', '/static/img/exclamation-circle.svg')
        return;
    }

    let id = document.querySelector("#idDocument").value;
    let numberStatementNode = document.querySelector("#numberStatement").value;
    let dateStatementNode = document.querySelector("#dateStatement").value;
    let soilLotStatementNode = document.querySelector("#soilLotStatement").value;
    let sampleRegionStatementNode = document.querySelector("#sampleRegionStatement").value;
    let quarterStatement = document.querySelector("#quarterStatement").value;
    let dachaStatement = document.querySelector("#dachaStatement").value;
    let distriotForestlyStatement = document.querySelector("#distriotForestlyStatement").value;

    var data = {
        date: dateStatementNode,
        id: id,
        sample_region: String(sampleRegionStatementNode).replace(/,/g, '.'),
        mark_del: APP.documentData.mark_del? 1:0,
        mark_update: APP.documentData.mark_update? 1:0,
        number_region: numberStatementNode,
        name_quarter: quarterStatement == ""? null : quarterStatement,
        dacha: dachaStatement == ""? null : dachaStatement,
        id_district_forestly: distriotForestlyStatement,
        soil_lot: soilLotStatementNode

    };

    await StatementRecalculationsBusinessDetail.getUpdateSample(id, data);

    for(var i = 0; i < APP.deleteIdSample.length; i++) {
        await StatementRecalculationsBusinessDetail.deleteSample(APP.deleteIdSample[i]);
    }

    ShowModal('m1', 'Сохранение прошло успешно', '/static/img/check-circle-fill.svg')
    resetChangesTracker();
    setTimeout(function() {
        let id = document.querySelector("#idDocument").value;
        getStatementRecalculationsDetail(id);
      }, 3000);
}

async function generateDocx() {

    let id = document.querySelector("#idDocument").value;

    var data = {
        id: id
    };

    var urlFile = await StatementRecalculationsBusinessDetail.generateDocx(data);
    urlFile = urlFile.document;

    urlFile = urlGlobal + urlFile;

    window.open(urlFile, '_blank').focus();
}

