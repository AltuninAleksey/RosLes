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

PrintFieldCardBusiness.getUpdatePrintFieldCard = async function(id, data) {
    var requestData = await axios({
      method: 'put',
      url: urlGlobal + "/fieldcard/" + id,
      data: data,
      responseType: 'json'
    });

    return requestData.data.FieldCard;
}

PrintFieldCardBusiness.getUpdatePoint7Table = async function(data) {
    var requestData = await axios({
      method: 'post',
      url: urlGlobal + "/plotcoeff",
      data: data,
      responseType: 'json'
    });
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

PrintFieldCardBusiness.getPoint7TableByIdOld = async function(id) {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/point7table/" + id,
      responseType: 'json'
    });

    return requestData.data;
}

PrintFieldCardBusiness.getPoint7TableById = async function(id) {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/plotcoeff/fieldcard/" + id,
      responseType: 'json'
    });

    return requestData.data.get;
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

PrintFieldCardBusiness.generateDocx = async function(data) {
    var requestData = await axios({
      method: 'post',
      url: urlGlobal + "/formdocx",
      data: data,
      responseType: 'json'
    });

    return requestData.data;
}


PrintFieldCardBusiness.getStatementRecalculationsDetailDataById = async function(id) {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/listregion/" + id,
      responseType: 'json'
    });

    return requestData.data;
}

PrintFieldCardBusiness.getSampleByIdListRegion = async function(id) {
    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/getsamplefromlistregion",
        data: {id: id},
        responseType: 'json'
    });

    return requestData.data.data;
}




function generatePDF() {
    const element = document.getElementById('invoice');
    const opt = {
        margin: [9,5,15,5],
        filename: 'FieldCard.pdf',
        html2canvas:  {  media: screen, width: 1380, height: 2800, ignoreElements: (element) =>{
            if(element.id==="ignor")
            return true
        }},
        jsPDF: {format: 'A4', orientation: 'portrait'}
      };
    html2pdf().set(opt).from(element).save();
}