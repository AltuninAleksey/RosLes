APP = {
    idView: "fieldCard"
}

function FieldCardBusiness() {}

FieldCardBusiness.getAllFieldCardList = async function() {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/fieldcard",
      responseType: 'json'
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

FieldCardBusiness.TypeData = {
    ALL: 0,
    BYID: 1
}