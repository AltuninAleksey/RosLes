function CommonBusiness() {}

CommonBusiness.getLogout = async function() {
    requestData = await axios({
        method: 'get',
        url: urlGlobal + "/logout",
        responseType: 'json'
    });
}

CommonBusiness.getAllForest = async function() {
    requestData = await axios({
        method: 'get',
        url: urlGlobal + "/allforest",
        responseType: 'json'
    });

    return requestData.data;
}

CommonBusiness.getAllSubjectrf = async function() {

    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/subjectRF",
        responseType: 'json'
    });

    return requestData.data.get.sort(function(a, b) { return a.name_subject_RF > b.name_subject_RF? 1 : -1; });
}

CommonBusiness.getForestlyByIdSubjectrf = async function(id) {

    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/getforestlybysubjectid/" + id,
        responseType: 'json'
    });

    return requestData.data.data;
}

CommonBusiness.getDistrictForestlyByIdForestly = async function(id) {

    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/getdistrictbyforestly/" + id,
        responseType: 'json'
    });

    return requestData.data.data;

}

CommonBusiness.getQuarterByIdDistrictForestly = async function(id) {

    if(id == "") {
        return [];
    }

    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/getquarterbydistrictid/" + id,
        responseType: 'json'
    });

    return requestData.data.data;
}

CommonBusiness.getAllBreeds = async function() {
    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/breed",
        responseType: 'json'
    });

    return requestData.data.get;
}

CommonBusiness.getAllTypeReproduction = async function() {
    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/reproduction",
        responseType: 'json'
    });

    return requestData.data.get;
}

CommonBusiness.getAllMethodofreforestation = async function() {
    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/methodofreforestation",
        responseType: 'json'
    });

    return requestData.data.get;
}

CommonBusiness.getLogout();