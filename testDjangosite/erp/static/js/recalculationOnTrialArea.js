// MPO 01.12.22: Set data page recalculationOnTrialArea

var subjectrf, forestly, district_forestly;

getAllRecalculationOnTrialArea();

async function getAllRecalculationOnTrialArea() {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/listregion",
      responseType: 'json'
    });

    var data = requestData.data;

    requestData = await axios({
        method: 'get',
        url: urlGlobal + "/allforest",
        responseType: 'json'
    });

    subjectrf = requestData.data.subjectrf;
    forestly = requestData.data.forestly;
    district_forestly = requestData.data.district_forestly;

    var tableBody = document.getElementById("tbodyRecalculationOnTrialArea");
    var newHtml = "";

    for(var i = 0; i < data.length; i++) {

        for(var j = 0; j < subjectrf.length; j++) {
            if(subjectrf[j].id == data[i].id_subjectrf) {
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

        newHtml = newHtml +  "<tr onclick=\"getRecalculationOnTrialAreaDetail("+ data[i].id +")\">"+
                                    "<td class=\"td1\">" + data[i].date + "</td>" +
                                    "<td class=\"td3\">" + data[i].subjectrf + "</td>" +
                                    "<td class=\"td4\">" + data[i].forestly + "</td>" +
                                    "<td class=\"td5\">" + data[i].district_forestly + "</td>" +
                                    "<td class=\"td6\">" + data[i].id_quarter + "</td>" +
                                    "<td class=\"td7\">" + data[i].soil_lot + "</td>" +
                                    "<td class=\"td8\">" + data[i].sample_region + "</td>" +
                                    "<td class=\"td9\"></td>"+
                                "</tr> \n";
    }
    tableBody.innerHTML = newHtml;
}

function getRecalculationOnTrialAreaDetail(id) {
    window.location.href = urlGlobal + "/erp/documents/recalculationOnTrialAreaDetail?id=" + id;
}