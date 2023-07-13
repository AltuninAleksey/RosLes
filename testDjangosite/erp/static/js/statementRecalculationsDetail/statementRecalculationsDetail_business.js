APP = {
    idView: "statementRecalculationsDetail"
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