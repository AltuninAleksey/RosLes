APP = {
    idView: "recalculatingDetail"
}

function RecalculatingDetailBusiness() {}

RecalculatingDetailBusiness.getRecalculatingDetailDataById = async function(id) {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/getsampledata/" + id,
      responseType: 'json'
    });

    return requestData.data.Sample_data[0];
}

RecalculatingDetailBusiness.getListData = async function(id) {
    requestData = await axios({
        method: 'get',
        url: urlGlobal + "/getlistdata/" + id,
        responseType: 'json'
    });

    return requestData.data;
}

RecalculatingDetailBusiness.getListPodlesoc = async function(id) {
    requestData = await axios({
        method: 'get',
        url: urlGlobal + "/undergrowth/" + id,
        responseType: 'json'
    });

    return requestData.data;
}

RecalculatingDetailBusiness.getPhotoPoint = async function(id) {
    requestData = await axios({
        method: 'get',
        url: urlGlobal + "/upload/" + id,
        responseType: 'json'
    });

    return requestData.data;
}

RecalculatingDetailBusiness.getUpdateSampleandother = async function(data) {
    requestData = await axios({
        method: 'put',
        url: urlGlobal + "/sampleandother",
        data: data,
        responseType: 'json'
    });

    return requestData.data;
}