APP = {
    idView: "plotDescription"
}

function PlotDescriptionBusiness() {}

PlotDescriptionBusiness.getPlotDescriptionDataById = async function(id) {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/descriptionregion/" + id,
      responseType: 'json',
      headers: {
        'Authorization': 'Bearer ' + token
      }
    });

    return requestData.data.DescriptionRegion;
}

PlotDescriptionBusiness.setPlotDescriptionDataById = async function(id, data) {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
      method: 'put',
      url: urlGlobal + "/descriptionregion/" + id,
      data: data,
      responseType: 'json',
      headers: {
        'Authorization': 'Bearer ' + token
      }
    });
}

PlotDescriptionBusiness.generateDocx = async function(data) {
    var requestData = await axios({
      method: 'post',
      url: urlGlobal + "/formdocxdesc",
      data: data,
      responseType: 'json'
    });

    return requestData.data;
}

function generatePDF() {
    const element = document.getElementById('invoice');
    const opt = {
        margin: [9,5,15,5],
        filename: 'plotDescript.pdf',
        html2canvas:  {  media: screen, width: 1380,  ignoreElements: (element) =>{
            if(element.id==="ignor")
            return true
        }},
        jsPDF: {format: 'A4', orientation: 'portrait'}
      };
    html2pdf().set(opt).from(element).save();
}