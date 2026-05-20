APP = {
    idView: "forestCropsInformTrialArea",
    limit: 1,
}

function forestCropsInformTrialArea() {}

forestCropsInformTrialArea.getStatementRecalculationsDetailDataById = async function(id) {
    var requestData = await axios({
      method: 'get',
      url: "http://92.50.227.100:58493/forestcrops/api/sample/info?id=" + id,
      responseType: 'json'
    });

    return requestData.data;
}

forestCropsInformTrialArea.getSampleByIdListRegion = async function(id,pageNum) {
    var offset = (pageNum - 1)*APP.limit;
    var requestData = await axios({
        method: 'get',
        url: ("http://92.50.227.100:58493/forestcrops/api/sample/get-forest-crops-list?idSample="+id+"&offset=" + offset + "&limit=" + APP.limit),
        data: {id: id},
        responseType: 'json'
    });

    return requestData.data;
}

forestCropsInformTrialArea.createSample = async function(data) {
    var requestData = await axios({
        method: 'post',
        url: "http://92.50.227.100:58493/forestcrops/api/sample/create-forest-crops",
        data: data,
        responseType: 'json'
    });

    return requestData.data;
}

forestCropsInformTrialArea.getUpdateSample = async function(data) {
    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];
    var requestData = await axios({
        method: 'put',
        url: "http://92.50.227.100:58493/forestcrops/api/sample/update",
        data: data,
        responseType: 'json',
        headers: {
          'Authorization': 'Bearer ' + token
        }
    });
}

forestCropsInformTrialArea.deleteSample = async function(data) {
    var requestData = await axios({
        method: 'DELETE',
        url: "http://92.50.227.100:58493/forestcrops/api/sample/delete-forest-crops",
        data: data,
        responseType: 'json'
    });
}

forestCropsInformTrialArea.updateNullTable = async function(data) {
    var requestData = await axios({
        method: 'put',
        url: "http://92.50.227.100:58493/forestcrops/api/sample/update-forest-crops",
        data: data,
        responseType: 'json',
    });
}
///таблица с диаметром
forestCropsInformTrialArea.getOneTable = async function(id,pageNum) {
    var offset = (pageNum - 1)*APP.limit;
    var requestData = await axios({
        method: 'get',
        url: ("http://92.50.227.100:58493/forestcrops/api/sample/get-plants-forest-crops-list?idSample="+id+"&offset=" + offset + "&limit=" + APP.limit),
        data: {id: id},
        responseType: 'json'
    });

    return requestData.data;
}
forestCropsInformTrialArea.deleteSampleOne = async function(data) {
    var requestData = await axios({
        method: 'DELETE',
        url: "http://92.50.227.100:58493/forestcrops/api/sample/delete-plants-forest-crops",
        data: data,
        responseType: 'json'
    });
}
forestCropsInformTrialArea.createOneSample = async function(data) {
    var requestData = await axios({
        method: 'post',
        url: "http://92.50.227.100:58493/forestcrops/api/sample/create-plants-forest-crops",
        data: data,
        responseType: 'json'
    });

    return requestData.data;
}
forestCropsInformTrialArea.updateOneTable = async function(data) {
    var requestData = await axios({
        method: 'put',
        url: "http://92.50.227.100:58493/forestcrops/api/sample/update-plants-forest-crops",
        data: data,
        responseType: 'json',
    });
}
//таблица подрост и молодняк
forestCropsInformTrialArea.getTwoTable = async function(id,pageNum) {
    var offset = (pageNum - 1)*APP.limit;
    var requestData = await axios({
        method: 'get',
        url: ("http://92.50.227.100:58493/forestcrops/api/sample/get-molod-forest-crops-list?idSample="+id+"&offset=" + offset + "&limit=" + APP.limit),
        data: {id: id},
        responseType: 'json'
    });

    return requestData.data;
}
forestCropsInformTrialArea.deleteSampleTwo = async function(data) {
    var requestData = await axios({
        method: 'DELETE',
        url: "http://92.50.227.100:58493/forestcrops/api/sample/delete-molod-forest-crops",
        data: data,
        responseType: 'json'
    });
}
forestCropsInformTrialArea.createTwoSample = async function(data) {
    var requestData = await axios({
        method: 'post',
        url: "http://92.50.227.100:58493/forestcrops/api/sample/create-molod-forest-crops",
        data: data,
        responseType: 'json'
    });

    return requestData.data;
}
forestCropsInformTrialArea.updateTwoTable = async function(data) {
    var requestData = await axios({
        method: 'put',
        url: "http://92.50.227.100:58493/forestcrops/api/sample/update-molod-forest-crops",
        data: data,
        responseType: 'json',
    });
}