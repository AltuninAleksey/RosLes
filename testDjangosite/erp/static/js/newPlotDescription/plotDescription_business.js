APP = {
    idView: "newPlotDescription"
}

function NewPlotDescriptionBusiness() {}

NewPlotDescriptionBusiness.createPlotDescription = async function(data) {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
      method: 'post',
      url: urlGlobal + "/createlistregionbydescreg",
      data: data,
      responseType: 'json',
      headers: {
        'Authorization': 'Bearer ' + token
      }
    });
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