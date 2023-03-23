function CommonBusiness() {}

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

    return requestData.data.get;
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

    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/getquarterbydistrictid/" + id,
        responseType: 'json'
    });

    return requestData.data.data;
}