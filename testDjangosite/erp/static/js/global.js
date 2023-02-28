let urlGlobal = "http://127.0.0.1:8000";
//let urlGlobal = "http://90.156.208.88:8093";

function getRecalculationOnTrialAreaPage() {
    window.location.href = urlGlobal + "/erp/documents/recalculationOnTrialArea";
}

function getListRegions() {
    window.location.href = urlGlobal + "/erp/documents/listRegions";
}

function getGeneralDoc() {
    window.location.href = urlGlobal + "/general_docs";
}

async function getAllSubjectrf() {

    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/subjectRF",
        responseType: 'json'
    });

    return requestData.data.get;
}

async function getForestlyByIdSubjectrf(id) {

    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/getforestlybysubjectid/" + id,
        responseType: 'json'
    });

    return requestData.data.data;
}

async function getDistrictForestlyByIdForestly(id) {

    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/getdistrictbyforestly/" + id,
        responseType: 'json'
    });

    return requestData.data.data;

}

async function getQuarterByIdDistrictForestly(id) {

    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/getquarterbydistrictid/" + id,
        responseType: 'json'
    });

    return requestData.data.data;
}

async function getAllTypeReproduction() {
    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/reproduction",
        responseType: 'json'
    });

    return requestData.data.get;
}

async function getAllBreeds() {
    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/breed",
        responseType: 'json'
    });

    return requestData.data.get;
}

async function getAllProfile() {
    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/profile",
        responseType: 'json'
    });

    return requestData.data.get;
}

async function getUpdateRecalculationDetailData(data) {
    var requestData = await axios({
        method: 'put',
        url: urlGlobal + "/sampleandother",
        data: data,
        responseType: 'json'
    });

}

async function getCreateRecalculationDetailData(data) {
    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/sampleandother",
        data: data,
        responseType: 'json'
    });

}