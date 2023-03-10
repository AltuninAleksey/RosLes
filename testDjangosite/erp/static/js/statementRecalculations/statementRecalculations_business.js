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