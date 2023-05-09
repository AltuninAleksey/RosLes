APP = {
    idView: "printFieldCard"
}

function PrintFieldCardBusiness() {}

PrintFieldCardBusiness.getPrintFieldCardDataById = async function(id) {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/fieldcard/" + id,
      responseType: 'json'
    });

    return requestData.data.FieldCard;
}

PrintFieldCardBusiness.getPurposeforestDataById = async function(id) {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/purposeforest/" + id,
      responseType: 'json'
    });

    return requestData.data.get;
}

PrintFieldCardBusiness.getALLPurposeforestData = async function() {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/purposeforest",
      responseType: 'json'
    });

    return requestData.data.get;
}

PrintFieldCardBusiness.getAllForestProtectionCategoryData = async function() {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/forestprotectioncategory",
      responseType: 'json'
    });

    return requestData.data.get;
}

PrintFieldCardBusiness.getAllCategoryOfForestFundLands = async function() {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/fundlands",
      responseType: 'json'
    });

    return requestData.data.get;
}

PrintFieldCardBusiness.getAllMethodOfReforestation = async function() {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/methodofreforestation",
      responseType: 'json'
    });

    return requestData.data.get;
}

PrintFieldCardBusiness.getAllTypeForestGrowingConditions = async function() {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/typeforestgrowingconditions",
      responseType: 'json'
    });

    return requestData.data.get;
}

PrintFieldCardBusiness.getAllEconomy = async function() {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/economy",
      responseType: 'json'
    });

    return requestData.data.get;
}

PrintFieldCardBusiness.getPoint7TableById = async function(id) {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/point7table/" + id,
      responseType: 'json'
    });

    return requestData.data;
}

PrintFieldCardBusiness.getGpsByListRegionId = async function(id) {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/gpsbylistregion/" + id,
      responseType: 'json'
    });

    return requestData.data;
}

PrintFieldCardBusiness.getPoint7Table2SaplingById = async function(id) {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/point7sapling/" + id,
      responseType: 'json'
    });

    return requestData.data;
}