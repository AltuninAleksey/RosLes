APP = {
    idView: "mergeStatementRecalculations",
    idActiveItemTr: []
}

function MergeStatementRecalculationsBusiness() {}

MergeStatementRecalculationsBusiness.getAllStatementList = async function() {

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

MergeStatementRecalculationsBusiness.getCreateRecalculationDetailData = async function(data) {
    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/listregionfilters",
        data: data,
        responseType: 'json'
    });

    return requestData.data.data;
}

MergeStatementRecalculationsBusiness.mergeStatementRecalculations = async function(data) {
    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/unionlistregion",
        data: data,
        responseType: 'json'
    });

    return requestData;
}

MergeStatementRecalculationsBusiness.TypeData = {
    ALL: 0,
    BYID: 1
}