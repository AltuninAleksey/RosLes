APP = {
    idView: "statementRecalculations"
}

function StatementRecalculationsBusiness() {}

StatementRecalculationsBusiness.getAllStatementList = async function() {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/getalllistregion",
      responseType: 'json'
    });

    return requestData.data.data;
}

StatementRecalculationsBusiness.getCreateRecalculationDetailData = async function(data) {
    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/listregionfilters",
        data: data,
        responseType: 'json'
    });

    return requestData.data.data;
}

StatementRecalculationsBusiness.TypeData = {
    ALL: 0,
    BYID: 1
}