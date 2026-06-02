APP = {
    idView: "forestCropsStatementRecalculations",
    params: {},
    limit: 20
}

function StatementRecalculationsBusiness() {}

StatementRecalculationsBusiness.getAllStatementList = async function(pageNum) {

    var offset = (pageNum - 1)*APP.limit;

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
      method: 'get',
      url: ("http://92.50.227.100:58493/forestcrops/api/listregion/list?offset=" + offset + "&limit=" + APP.limit),
      params: APP.params,
      responseType: 'json',
      headers: {
        'Authorization': 'Bearer ' + token
      }
    });

    return requestData.data;
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
        url: "http://92.50.227.100:58493/forestcrops/api/listregion/delete?id=" + id,
        responseType: 'json'
    });
}

StatementRecalculationsBusiness.TypeData = {
    ALL: 0,
    BYID: 1
}

StatementRecalculationsBusiness.downloadExcel = async function() {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/getmains",
        responseType: 'json',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    });
    return requestData.data;
}

StatementRecalculationsBusiness.downloadAllExcel = async function(data) {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/create_fc_excel",
        data: data,
        headers: {
            'Authorization': 'Bearer ' + token
        }
    });
    return requestData.data;
}

StatementRecalculationsBusiness.dachaList = async function() {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
        method: 'get',
        url: "http://92.50.227.100:58493/forestcrops/api/shared/get-all-dacha",
        responseType: 'json',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    });
    return requestData.data;
}