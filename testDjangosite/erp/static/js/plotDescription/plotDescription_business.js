APP = {
    idView: "plotDescription"
}

function PlotDescriptionBusiness() {}

PlotDescriptionBusiness.getPlotDescriptionDataById = async function(id) {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/descriptionregion/" + id,
      responseType: 'json'
    });

    return requestData.data.DescriptionRegion;
}

PlotDescriptionBusiness.setPlotDescriptionDataById = async function(id, data) {
    var requestData = await axios({
      method: 'put',
      url: urlGlobal + "/descriptionregion/" + id,
      data: data,
      responseType: 'json'
    });
}