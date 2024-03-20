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

CommonBusiness.getForestlyByArrayIdSubjectrf = async function(data) {

    var requestData = await axios({
        method: 'post',
        data: data,
        url: urlGlobal + "/byarray/getforestlybysubject",
        responseType: 'json'
    });

    return requestData.data.data;
}

CommonBusiness.getDistrictForestlyByArrayIdForestly = async function(data) {

    var requestData = await axios({
        method: 'post',
        data: data,
        url: urlGlobal + "/byarray/getdistrictbyforestly",
        responseType: 'json'
    });

    return requestData.data.data;
}

CommonBusiness.getQuarterByArrayIdDistrictForestly = async function(data) {


    var requestData = await axios({
        method: 'post',
        data: data,
        url: urlGlobal + "/byarray/getquarterbydistrictid",
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

CommonBusiness.getAllUndergrowth = async function() {
    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/undergrowth",
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

CommonBusiness.getUserData = async function() {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/aboutuser",
        responseType: 'json',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    });

    return requestData.data.data;
}

CommonBusiness.getCZL = async function() {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/getczl",
        responseType: 'json',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    });

    return requestData.data;
}




CommonBusiness.checkCookie = async function() {
    if(document.cookie.match(/jwttoken=(.+?)(;|$)/) == null ||
        document.cookie.match(/jwttoken=(.+?)(;|$)/) == "" ||
         document.cookie.match(/jwttoken=(.+?)(;|$)/) == undefined) {
        getAvtorization();
    }

    data = {
        token: document.cookie.match(/jwttoken=(.+?)(;|$)/)[1]
    }

    try {
        var requestData = await axios({
            method: 'post',
            data: data,
            url: urlGlobal + "/v2/verify",
            responseType: 'json'
        });
    } catch (error) {
        getAvtorization();
    }
}


CommonBusiness.getLogout();
CommonBusiness.checkCookie();