APP = {
    idView: "fieldCard"
}

function FieldCardBusiness() {}

FieldCardBusiness.getAllFieldCardList = async function() {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/fieldcard",
      responseType: 'json',
      headers: {
        'Authorization': 'Bearer ' + token
      }
    });

    return requestData.data.get;
}

FieldCardBusiness.getFieldCardListByFilter = async function(data) {
    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/fieldcardfilter",
        data: data,
        responseType: 'json'
    });

    return requestData.data.data;
}

FieldCardBusiness.deleteFieldCardById = async function(data) {
    var requestData = await axios({
        method: 'delete',
        url: urlGlobal + "/deleteallbyfieldcard",
        data: data,
        responseType: 'json'
    });

    return requestData.data.data;
}

FieldCardBusiness.TypeData = {
    ALL: 0,
    BYID: 1
}

FieldCardBusiness.downloadExcel = async function() {

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

FieldCardBusiness.downloadAllExcel = async function(data) {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/create_all_excel",
        data: data,
        headers: {
            'Authorization': 'Bearer ' + token
        }
    });
    return requestData.data;
}