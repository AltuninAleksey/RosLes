APP = {
    idView: "forestCropsStatementRecalculationsDetail"
}

function StatementRecalculationsBusinessDetail() {}

StatementRecalculationsBusinessDetail.getStatementRecalculationsDetailDataById = async function(id) {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/listregion/" + id,
      responseType: 'json'
    });

    return requestData.data;
}

StatementRecalculationsBusinessDetail.getSampleByIdListRegion = async function(id) {
    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/getsamplefromlistregion",
        data: {id: id},
        responseType: 'json'
    });

    return requestData.data.data;
}

StatementRecalculationsBusinessDetail.getUpdateSample = async function(id, data) {
    var requestData = await axios({
        method: 'put',
        url: urlGlobal + "/listregion/update/" + id,
        data: data,
        responseType: 'json'
    });
}

StatementRecalculationsBusinessDetail.createSample = async function(data) {
    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/sample",
        data: data,
        responseType: 'json'
    });

    return requestData.data;
}

StatementRecalculationsBusinessDetail.deleteSample = async function(id) {
    var requestData = await axios({
        method: 'DELETE',
        url: urlGlobal + "/sample/" + id,
        responseType: 'json'
    });
}


StatementRecalculationsBusinessDetail.generateDocx = async function(data) {
    var requestData = await axios({
      method: 'post',
      url: urlGlobal + "/form_list_region",
      data: data,
      responseType: 'json'
    });

    return requestData.data;
}