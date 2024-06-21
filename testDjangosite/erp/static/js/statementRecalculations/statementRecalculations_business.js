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

StatementRecalculationsBusiness.deleteStatementRecalculationById = async function(id) {
    var requestData = await axios({
        method: 'delete',
        url: urlGlobal + "/listregion/" + id,
        responseType: 'json'
    });
}

StatementRecalculationsBusiness.TypeData = {
    ALL: 0,
    BYID: 1
}