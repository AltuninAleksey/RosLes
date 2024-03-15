APP = {
    idView: "descriptionListLand"
}

function DescriptionListLandBusiness() {}

DescriptionListLandBusiness.getAllDescriptionLandList = async function() {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/descriptionregion",
      responseType: 'json',
      headers: {
        'Authorization': 'Bearer ' + token
      }
    });

    return requestData.data.get;
}

DescriptionListLandBusiness.getDescriptionLandListByFilter = async function(data) {
    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/descriptionregionfilter",
        data: data,
        responseType: 'json'
    });

    return requestData.data.data;
}

DescriptionListLandBusiness.TypeData = {
    ALL: 0,
    BYID: 1
}