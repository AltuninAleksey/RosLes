APP = {
    idView: "statementRecalculations"
}

function StatementRecalculationsBusiness() {}

StatementRecalculationsBusiness.getAllStatementList = async function() {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/getalllistregion",
      responseType: 'json',
      headers: {
        'Authorization': 'Bearer ' + token
      }
    });

    return requestData.data.data;
}

StatementRecalculationsBusiness.getStatementListByFilter = async function(data) {
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