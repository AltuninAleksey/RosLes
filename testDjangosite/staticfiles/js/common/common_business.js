function CommonBusiness() {}

CommonBusiness.getAllForest = async function() {
    requestData = await axios({
        method: 'get',
        url: urlGlobal + "/allforest",
        responseType: 'json'
    });

    return requestData.data;
}