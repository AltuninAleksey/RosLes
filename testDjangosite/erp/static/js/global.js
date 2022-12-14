let urlGlobal = "http://127.0.0.1:8000";

function getRecalculationOnTrialAreaPage() {
    window.location.href = urlGlobal + "/erp/documents/recalculationOnTrialArea";
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