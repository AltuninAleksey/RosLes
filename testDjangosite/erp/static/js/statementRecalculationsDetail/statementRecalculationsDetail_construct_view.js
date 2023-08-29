openPage();

async function openPage() {

    let idDocument = document.querySelector("#idDocument").value;

    var allForestData = await CommonBusiness.getAllForest();

    APP.subjectrf = allForestData.subjectrf;
    APP.forestly = allForestData.forestly;
    APP.district_forestly = allForestData.district_forestly;
    APP.quarter = allForestData.quarter;

    APP.documentData = await StatementRecalculationsBusinessDetail.getStatementRecalculationsDetailDataById(idDocument);
    APP.subjects = await CommonBusiness.getAllSubjectrf();
    APP.sampleList = await StatementRecalculationsBusinessDetail.getSampleByIdListRegion(idDocument);

    await setDetailDataIdPage();

    setEvent();
}

async function setDetailDataIdPage() {

    let numberStatementInHeaderNode = document.querySelector("#numberStatementInHeader");
    let daterStatementInHeaderNode = document.querySelector("#dateStatementInHeader");
    let numberStatementNode = document.querySelector("#numberStatement");
    let dateStatementNode = document.querySelector("#dateStatement");
    let soilLotStatementNode = document.querySelector("#soilLotStatement");
    let sampleRegionStatementNode = document.querySelector("#sampleRegionStatement");

    numberStatementInHeaderNode.innerHTML = APP.documentData.number_region;
    daterStatementInHeaderNode.innerHTML = APP.documentData.date;
    numberStatementNode.value = APP.documentData.number_region;
    dateStatementNode.value = APP.documentData.date;
    soilLotStatementNode.value = APP.documentData.soil_lot;
    sampleRegionStatementNode.value = APP.documentData.sample_region;

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

    document
        .querySelector("#distriotForestlyStatement")
        .addEventListener('change', async (e)=>{
                await setQuarterStatement();
        });

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
                        <td class="td1">${APP.sampleList[i].date}</td>
                        <td class="td8">${APP.sampleList[i].id}</td>
                        <td class="td2">${CommonFunction.getSubjectNameByQuarterId(APP.subjectrf, APP.sampleList[i].id_subject_rf)}</td>
                        <td class="td3">${CommonFunction.getForestlyNameByQuarterId(APP.forestly, APP.sampleList[i].id_forestly)}</td>
                        <td class="td4">${CommonFunction.getDistrictForestlyNameByQuarterId(APP.district_forestly, APP.sampleList[i].id_district_forestly)}</td>
                        <td class="td5">${CommonFunction.getQuarterNameByQuarterId(APP.quarter, APP.sampleList[i].id_quarter)}</td>
                        <td class="td6">${APP.sampleList[i].soil_lot}</td>
                        <td class="td7">${APP.sampleList[i].sample_area}</td>
                    </tr>`;
    }

    sampleListTbodyNode.innerHTML = newHtml;
}

async function setSubjectsRF() {
    let subjectStatementNode = document.querySelector("#subjectStatement");

    let newHtml = "";

    for(let i = 0; i < APP.subjects.length; i++) {
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
    APP.distriotForestly = [];

    if(idForestly != "" && idForestly != null && idForestly != undefined) {
        APP.distriotForestly = await CommonBusiness.getDistrictForestlyByIdForestly(idForestly);
    }

    let newHtml = "";

    let distriotForestlyNode = document.querySelector("#distriotForestlyStatement");

    for(let i = 0; i < APP.distriotForestly.length; i++) {
        if(APP.distriotForestly[i].id == APP.documentData.id_distriot_forestly) {
            newHtml += '<option selected value="' + APP.distriotForestly[i].id + '">' + APP.distriotForestly[i].name_district_forestly + '</option>';
        } else {
            newHtml += '<option value="' + APP.distriotForestly[i].id + '">' + APP.distriotForestly[i].name_district_forestly + '</option>';
        }
    }

    distriotForestlyNode.innerHTML = newHtml;

    await setQuarterStatement();
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

async function saveData() {

    let id = document.querySelector("#idDocument").value;
    let numberStatementNode = document.querySelector("#numberStatement").value;
    let dateStatementNode = document.querySelector("#dateStatement").value;
    let soilLotStatementNode = document.querySelector("#soilLotStatement").value;
    let sampleRegionStatementNode = document.querySelector("#sampleRegionStatement").value;
    let quarterStatement = document.querySelector("#quarterStatement").value;

    var data = {
       date: dateStatementNode,
        sample_region: sampleRegionStatementNode,
        soil_lot: soilLotStatementNode,
        mark_del: APP.documentData.mark_del? 1:0,
        mark_update: APP.documentData.mark_update? 1:0,
        number_region: numberStatementNode,
        id_quarter: quarterStatement
    };

    await StatementRecalculationsBusinessDetail.getUpdateSample(id, data);

    getStatementRecalculationsDetail(id);
}