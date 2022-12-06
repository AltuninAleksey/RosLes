// MPO 01.12.22: Set data page recalculationOnTrialAreaDetail

var idDocument = document.getElementById("idDocument").value;
var documentData, subjectrf, forestly, district_forestly, quarter;
var dataTable_1, dataTable_2, dataTable_3;
var table_1_active_group;

setDataInPage();

async function setDataInPage() {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/getsampledata/" + idDocument,
      responseType: 'json'
    });

    documentData = requestData.data.Sample_data[0];

    requestData = await axios({
        method: 'get',
        url: urlGlobal + "/allforest",
        responseType: 'json'
    });

    subjectrf = requestData.data.subjectrf;
    forestly = requestData.data.forestly;
    district_forestly = requestData.data.district_forestly;
    quarter = requestData.data.quarter;


    requestData = await axios({
        method: 'get',
        url: urlGlobal + "/getlistdata/" + idDocument,
        responseType: 'json'
    });

    requestData = requestData.data;

    dataTable_1 = requestData.list_data;
    dataTable_2 = requestData.post_data;
    dataTable_3 = requestData.gps_data;

    setDocumentData();
}

function setDocumentData() {
    document.getElementById("start").value = documentData.date;

    for(var j = 0; j < quarter.length; j++) {
        if(quarter[j].id == documentData.id_quarter) {
            documentData.quarter = quarter[j].quarter_name;
        }
    }

    document.getElementById("quarter").value = documentData.quarter;
    document.getElementById("soil_lot").value = documentData.soil_lot;
    document.getElementById("sample_area").value = documentData.sample_area;

    var regions = document.getElementById("regionRF");
    var newHtml = "";

    for(var i = 0; i < subjectrf.length; i++) {
        if(subjectrf[i].id == documentData.id_subjectrf) {
            newHtml = newHtml + "<option selected value=\"" + subjectrf[i].id + "\">" + subjectrf[i].name_subject_RF + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + subjectrf[i].id + "\">" + subjectrf[i].name_subject_RF + "</option>";
        }
    }
    regions.innerHTML = newHtml;

    var lesName = document.getElementById("lesName");
    newHtml = "";

    for(var i = 0; i < forestly.length; i++) {
        if(forestly[i].id == documentData.id_forestly) {
            newHtml = newHtml + "<option selected value=\"" + forestly[i].id + "\">" + forestly[i].name_forestly + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + forestly[i].id + "\">" + forestly[i].name_forestly + "</option>";
        }
    }
    lesName.innerHTML = newHtml;

    var ucLesName = document.getElementById("ucLesName");
    newHtml = "";

    for(var i = 0; i < district_forestly.length; i++) {
        if(district_forestly[i].id == documentData.id_district_forestly) {
            newHtml = newHtml + "<option selected value=\"" + district_forestly[i].id + "\">" + district_forestly[i].name_district_forestly + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + district_forestly[i].id + "\">" + district_forestly[i].name_district_forestly + "</option>";
        }
    }
    ucLesName.innerHTML = newHtml;

    setDataInTableOne(1);
    setDataInTableTwo();
    setDataInTableThree();
}

function setDataInTableOne(switchButton) {

    var switchButton1 = document.getElementById("switchButton1");
    var switchButton2 = document.getElementById("switchButton2");
    var switchButton3 = document.getElementById("switchButton3");

    if(switchButton == 1) {
        switchButton1.classList.add("border-bottom-color-active");
        switchButton2.classList.remove("border-bottom-color-active");
        switchButton3.classList.remove("border-bottom-color-active");

        switchButton1.classList.remove("border-bottom-color-black");
        switchButton2.classList.add("border-bottom-color-black");
        switchButton3.classList.add("border-bottom-color-black");

        table_1_active_group = "Искусственное восстановление";
    }

    if(switchButton == 2) {
        switchButton1.classList.remove("border-bottom-color-active");
        switchButton2.classList.add("border-bottom-color-active");
        switchButton3.classList.remove("border-bottom-color-active");

        switchButton1.classList.add("border-bottom-color-black");
        switchButton2.classList.remove("border-bottom-color-black");
        switchButton3.classList.add("border-bottom-color-black");

        table_1_active_group = "Естественное восстановление(семенное)";
    }

    if(switchButton == 3) {
        switchButton1.classList.remove("border-bottom-color-active");
        switchButton2.classList.remove("border-bottom-color-active");
        switchButton3.classList.add("border-bottom-color-active");

        switchButton1.classList.add("border-bottom-color-black");
        switchButton2.classList.add("border-bottom-color-black");
        switchButton3.classList.remove("border-bottom-color-black");

        table_1_active_group = "Естественное восстановление (вегетативное)";
    }

    var table_1 = document.getElementById("table_1");
    var newHtml = "";
    var myCount = 1;

    for(var i = 0; i < dataTable_1.length; i++) {
            if(dataTable_1[i].id_type_of_reproduction == table_1_active_group) {
                newHtml = newHtml + "<tr>" +
                                        "<td class=\"tab_1_td1\">" + myCount + "</td>" +
                                        "<td class=\"tab_1_td2\">" + dataTable_1[i].breed + "</td>" +
                                        "<td class=\"tab_1_td3\">" + dataTable_1[i].to0_2 + "</td>" +
                                        "<td class=\"tab_1_td4\">" + dataTable_1[i].from0_21To0_5 + "</td>" +
                                        "<td class=\"tab_1_td5\">" + dataTable_1[i].from0_6To1_0 + "</td>" +
                                        "<td class=\"tab_1_td6\">" + dataTable_1[i].from1_1to1_5 + "</td>" +
                                        "<td class=\"tab_1_td7\">" + dataTable_1[i].from1_5 + "</td>" +
                                        "<td class=\"tab_1_td8\">" + dataTable_1[i].max_height + "</td>" +
                                    "</tr>";

                myCount = myCount + 1;
            }
    }

    table_1.innerHTML = newHtml;
}

function setDataInTableTwo() {
    var table_2 = document.getElementById("table_2");
    var newHtml = "";

    for(var i = 0; i < dataTable_2.length; i++) {
        newHtml = newHtml + "<tr>" +
                                "<td class=\"tab_2_td1\">" + (i+1) + "</td>" +
                                "<td>" + dataTable_2[i].FIO + "</td>" +
                            "</tr>";
    }
    table_2.innerHTML = newHtml;
}

function setDataInTableThree() {
    var table_3 = document.getElementById("table_3");
    var newHtml = "";

    for(var i = 0; i < dataTable_3.length; i++) {
        if(dataTable_3[i].flag_center == 1) {
            newHtml = newHtml + "<tr>" +
                                    "<td class=\"tab_2_td1\">" + (i+1) + "</td>" +
                                    "<td class=\"tab_3_td2\">" + dataTable_3[i].latitude + "</td>" +
                                    "<td class=\"tab_3_td3\">" + dataTable_3[i].longitude + "</td>" +
                                    "<td class=\"tab_3_td4\"><input type=\"checkbox\" checked></td>" +
                                "</tr>";
        } else {
            newHtml = newHtml + "<tr>" +
                                    "<td class=\"tab_2_td1\">" + (i+1) + "</td>" +
                                    "<td class=\"tab_3_td2\">" + dataTable_3[i].latitude + "</td>" +
                                    "<td class=\"tab_3_td3\">" + dataTable_3[i].longitude + "</td>" +
                                    "<td class=\"tab_3_td4\"><input type=\"checkbox\"></td>" +
                                "</tr>";
        }
    }
    table_3.innerHTML = newHtml;
}

function saveData() {
    var dateForm = document.getElementById("start");
    var regionRFForm = document.getElementById("regionRF");
    var lesNameForm = document.getElementById("lesName");
    var ucLesNameForm = document.getElementById("ucLesName");
    var quarterForm = document.getElementById("quarter");
    var soil_lotForm = document.getElementById("soil_lot");
    var sample_areaForm = document.getElementById("sample_area");

    var requestData = {
        date : dateForm.value,
        id : idDocument,
        id_district_forestly : ucLesNameForm.value,
        id_forestly : lesNameForm.value,
        id_profile : dataTable_2[0].id,
        id_quarter : quarterForm.value,
        id_subjectrf : regionRFForm.value,
        profile : dataTable_2[0].value,
        sample_area : sample_areaForm.value,
        soil_lot : soil_lotForm.value,
        list_data : dataTable_1,
        gps_data : dataTable_3
    };

    console.log(JSON.stringify(requestData));

}