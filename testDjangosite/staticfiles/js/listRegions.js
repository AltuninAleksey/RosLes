var subjectrf, forestly, district_forestly, quarter;

getAlllistRegions();

async function getAlllistRegions() {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/getalllistregion",
      responseType: 'json'
    });

    var data = requestData.data.data;

    requestData = await axios({
        method: 'get',
        url: urlGlobal + "/allforest",
        responseType: 'json'
    });

    subjectrf = requestData.data.subjectrf;
    forestly = requestData.data.forestly;
    district_forestly = requestData.data.district_forestly;
    quarter = requestData.data.quarter;

    var tableBody = document.getElementById("tbodyRecalculationOnTrialArea");
    var newHtml = "";

    for(var i = 0; i < data.length; i++) {

        for(var j = 0; j < subjectrf.length; j++) {
            if(subjectrf[j].id == data[i].id_subject_rf) {
                data[i].subjectrf = subjectrf[j].name_subject_RF;
            }
        }

        for(var j = 0; j < forestly.length; j++) {
            if(forestly[j].id == data[i].id_forestly) {
                data[i].forestly = forestly[j].name_forestly;
            }
        }

        for(var j = 0; j < district_forestly.length; j++) {
            if(district_forestly[j].id == data[i].id_district_forestly) {
                data[i].district_forestly = district_forestly[j].name_district_forestly;
            }
        }

        for(var j = 0; j < quarter.length; j++) {
            if(quarter[j].id == data[i].id_quarter) {
                data[i].quarter = quarter[j].quarter_name;
            }
        }

        newHtml = newHtml +  "<tr>"+
                                    "<td class=\"td1\">" + data[i].date + "</td>" +
                                    "<td class=\"td3\">" + data[i].subjectrf + "</td>" +
                                    "<td class=\"td4\">" + data[i].forestly + "</td>" +
                                    "<td class=\"td5\">" + data[i].district_forestly + "</td>" +
                                    "<td class=\"td6\">" + data[i].quarter + "</td>" +
                                    "<td class=\"td7\">" + data[i].soil_lot + "</td>" +
                                    "<td class=\"td8\">" + data[i].sample_region + "</td>" +
                                "</tr> \n";
    }
    tableBody.innerHTML = newHtml;
}

async function openForm(id, typeConsolidationList) {

    var formAddProba = document.getElementById(id);
    formAddProba.classList.remove("display-none");

    if(id != 'consolidation-list-form') {
        var body = document.getElementById("body");
        body.classList.add("overflowHiddenImportant");
    } else {



    }
}

function closeForm(id) {
    var formAddProba = document.getElementById(id);
    formAddProba.classList.add("display-none");

    if(id != 'consolidation-list-form') {
        var body = document.getElementById("body");
        body.classList.remove("overflowHiddenImportant");
    }

}