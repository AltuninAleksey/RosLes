async function setDataInHeader() {
    drawSelectSubjectRF();
    changeDataSelectForestly(APP.documentData.id_subject_rf);
}

function drawSelectSubjectRF() {
    var regions = document.getElementById("regionRF");
    var newHtml = "";

    for(var i = 0; i < APP.subjectrf.length; i++) {
        if(APP.subjectrf[i].id == APP.documentData.id_subject_rf) {
            newHtml = newHtml + "<option selected value=\"" + APP.subjectrf[i].id + "\">" + APP.subjectrf[i].name_subject_RF + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.subjectrf[i].id + "\">" + APP.subjectrf[i].name_subject_RF + "</option>";
        }
    }
    regions.innerHTML = newHtml;
}

function drawSelectForestly() {
    var lesName = document.getElementById("lesName");
    var newHtml = "";

    for(var i = 0; i < APP.forestly.length; i++) {
        if(APP.forestly[i].id == APP.documentData.id_forestly) {
            newHtml = newHtml + "<option selected value=\"" + APP.forestly[i].id + "\">" + APP.forestly[i].name_forestly + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.forestly[i].id + "\">" + APP.forestly[i].name_forestly + "</option>";
        }
    }
    lesName.innerHTML = newHtml;
}

function drawSelectDistrictForestly() {
    var ucLesName = document.getElementById("ucLesName");
    var newHtml = "";

    for(var i = 0; i < APP.district_forestly.length; i++) {
        if(APP.district_forestly[i].id == APP.documentData.id_district_forestly) {
            newHtml = newHtml + "<option selected value=\"" + APP.district_forestly[i].id + "\">" + APP.district_forestly[i].name_district_forestly + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.district_forestly[i].id + "\">" + APP.district_forestly[i].name_district_forestly + "</option>";
        }
    }
    ucLesName.innerHTML = newHtml;
}

function drawSelectQuarter() {
    var newHtml = "";
    for(var j = 0; j < APP.quarter.length; j++) {
        if(APP.quarter[j].id == APP.documentData.id_quarter) {
            newHtml = newHtml + "<option selected value=\"" + APP.quarter[j].id + "\">" + APP.quarter[j].quarter_name + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.quarter[j].id + "\">" + APP.quarter[j].quarter_name + "</option>";
        }
    }
    document.getElementById("quarter").innerHTML = newHtml;
}

async function changeDataSelectForestly(id) {
    APP.forestly = await getForestlyByIdSubjectrf(id);
    drawSelectForestly();

    var forestlyObject = document.getElementById("lesName");
    changeDataSelectDistrictForestly(forestlyObject.value);
}

async function changeDataSelectDistrictForestly(id) {
    if(id != undefined && id != null && id != "") {
        APP.district_forestly = await getDistrictForestlyByIdForestly(id);
    } else {
        APP.district_forestly = [];
    }
    drawSelectDistrictForestly();

    var districtForestly = document.getElementById("ucLesName");
    changeDataSelectQuarter(districtForestly.value);
}

async function changeDataSelectQuarter(id) {
    if(id != undefined && id != null && id != "") {
        APP.quarter = await getQuarterByIdDistrictForestly(id);
    } else {
        APP.quarter = [];
    }
    drawSelectQuarter();
}