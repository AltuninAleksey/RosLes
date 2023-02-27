var documentData, subjectrf = [], forestly = [], district_forestly = [], quarter = [], states = null;
var dataTable_1 = [], dataTable_2 = [], dataTable_3 = [];
var type_reproductions, breeds, active_type_reproduction;

setDataInPage();
setEventListenerForObjects();

function setEventListenerForObjects() {
    var regions = document.getElementById("regionRF");
    regions.addEventListener('change',function() {
        changeDataSelectForestly(regions.value);
    });

    var forestlyObject = document.getElementById("lesName");
    forestlyObject.addEventListener('change',function() {
        changeDataSelectDistrictForestly(forestlyObject.value);
    });

    var districtForestly = document.getElementById("ucLesName");
    districtForestly.addEventListener('change',function() {
        changeDataSelectQuarter(districtForestly.value);
    });

    var buttonAddProba = document.getElementById("buttonAddProba");
    buttonAddProba.addEventListener('click', function() {
        addProba();
    });

    var buttonAddState = document.getElementById("buttonAddState");
    buttonAddState.addEventListener('click', function() {
        addState();
    });

    var buttonAddGps = document.getElementById("buttonAddGps");
    buttonAddGps.addEventListener('click', function() {
        addGps();
    });

}

async function setDataInPage() {

    documentData = {
        id_subjectrf : 0,
        id_forestly : 0,
        id_district_forestly : 0,
        id_quarter : 0
    };

    subjectrf = await getAllSubjectrf();
    documentData.id_subjectrf = subjectrf[0].id;

    if(subjectrf.length > 0) {
        forestly = await getForestlyByIdSubjectrf(subjectrf[0].id);

        if(forestly.length > 0) {
            documentData.id_forestly = forestly[0].id;
            district_forestly = await getDistrictForestlyByIdForestly(forestly[0].id);

            if(district_forestly.length > 0) {
                documentData.id_district_forestly = district_forestly[0].id;
                quarter = await getQuarterByIdDistrictForestly(district_forestly[0].id);

                if(quarter.length > 0) {
                    documentData.id_quarter = quarter[0].id;
                }
            }
        }
    }


    type_reproductions = await getAllTypeReproduction();
    breeds = await getAllBreeds();

    setDocumentData();
}

function setDocumentData() {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
    var yyyy = today.getFullYear();
    today = yyyy + '-' + mm + '-' + dd;

    document.getElementById("start").value = today;
    document.getElementById("soil_lot").value = 0;
    document.getElementById("sample_area").value = 0;

    drawSelectSubjectRF();
    drawSelectForestly();
    drawSelectDistrictForestly();
    drawSelectQuarter();

    setDataInTableOne(1);
    setDataInTableTwo();
    setDataInTableThree();

    setDataFormAddProba();

}

function drawSelectSubjectRF() {
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
}

function drawSelectForestly() {
    var lesName = document.getElementById("lesName");
    var newHtml = "";

    for(var i = 0; i < forestly.length; i++) {
        if(forestly[i].id == documentData.id_forestly) {
            newHtml = newHtml + "<option selected value=\"" + forestly[i].id + "\">" + forestly[i].name_forestly + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + forestly[i].id + "\">" + forestly[i].name_forestly + "</option>";
        }
    }
    lesName.innerHTML = newHtml;
}

function drawSelectDistrictForestly() {
    var ucLesName = document.getElementById("ucLesName");
    var newHtml = "";

    for(var i = 0; i < district_forestly.length; i++) {
        if(district_forestly[i].id == documentData.id_district_forestly) {
            newHtml = newHtml + "<option selected value=\"" + district_forestly[i].id + "\">" + district_forestly[i].name_district_forestly + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + district_forestly[i].id + "\">" + district_forestly[i].name_district_forestly + "</option>";
        }
    }
    ucLesName.innerHTML = newHtml;
}

function drawSelectQuarter() {
    var newHtml = "";
    for(var j = 0; j < quarter.length; j++) {
        if(quarter[j].id == documentData.id_quarter) {
            newHtml = newHtml + "<option selected value=\"" + quarter[j].id + "\">" + quarter[j].quarter_name + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + quarter[j].id + "\">" + quarter[j].quarter_name + "</option>";
        }
    }
    document.getElementById("quarter").innerHTML = newHtml;
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

        active_type_reproduction = 1;
    }

    if(switchButton == 2) {
        switchButton1.classList.remove("border-bottom-color-active");
        switchButton2.classList.add("border-bottom-color-active");
        switchButton3.classList.remove("border-bottom-color-active");

        switchButton1.classList.add("border-bottom-color-black");
        switchButton2.classList.remove("border-bottom-color-black");
        switchButton3.classList.add("border-bottom-color-black");

        active_type_reproduction = 2;
    }

    if(switchButton == 3) {
        switchButton1.classList.remove("border-bottom-color-active");
        switchButton2.classList.remove("border-bottom-color-active");
        switchButton3.classList.add("border-bottom-color-active");

        switchButton1.classList.add("border-bottom-color-black");
        switchButton2.classList.add("border-bottom-color-black");
        switchButton3.classList.remove("border-bottom-color-black");

        active_type_reproduction = 3;
    }

    var table_1 = document.getElementById("table_1");
    var newHtml = "";
    var myCount = 1;

    for(var i = 0; i < dataTable_1.length; i++) {
            if(Number(dataTable_1[i].id_type_of_reproduction) == switchButton) {

                var name_breed;

                for(var j = 0; j < breeds.length; j++) {
                    if(breeds[j].id == dataTable_1[i].breed) {
                        name_breed = breeds[j].name_breed;
                        break;
                    }
                }

                newHtml = newHtml + "<tr>" +
                                        "<td class=\"tab_1_td1\">" + myCount + "</td>" +
                                        "<td class=\"tab_1_td2\">" + name_breed + "</td>" +
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

function setDataFormAddProba() {
    var breedName_proba = document.getElementById("breedName-proba");
    var newHtml = "";

    for(var i = 0; i < breeds.length; i++) {
        newHtml = newHtml + "<option value=\"" + breeds[i].id + "\">" + breeds[i].name_breed + "</option>";
    }

    breedName_proba.innerHTML = newHtml;

}

async function changeDataSelectForestly(id) {
    forestly = await getForestlyByIdSubjectrf(id);
    drawSelectForestly();

    var forestlyObject = document.getElementById("lesName");
    changeDataSelectDistrictForestly(forestlyObject.value);
}

async function changeDataSelectDistrictForestly(id) {
    if(id != undefined && id != null && id != "") {
        district_forestly = await getDistrictForestlyByIdForestly(id);
    } else {
        district_forestly = [];
    }
    drawSelectDistrictForestly();

    var districtForestly = document.getElementById("ucLesName");
    changeDataSelectQuarter(districtForestly.value);
}

async function changeDataSelectQuarter(id) {
    if(id != undefined && id != null && id != "") {
        quarter = await getQuarterByIdDistrictForestly(id);
    } else {
        quarter = [];
    }
    drawSelectQuarter();
}

function addProba() {
    var breedName_proba = document.getElementById("breedName-proba");
    var proba_021_05 = document.getElementById("proba-0.21-0.5");
    var proba_11_15 = document.getElementById("proba-1.1-1.5");
    var proba_02 = document.getElementById("proba-0.2");
    var proba_06_10 = document.getElementById("proba-0.6-1.0");
    var proba_15 = document.getElementById("proba-1.5");
    var proba_maxheig = document.getElementById("proba-maxheig");

    var newData = {
        id_breed : breedName_proba.value,
        id_type_of_reproduction : Number(active_type_reproduction),
        to0_2 : Number(proba_02.value),
        from0_21To0_5 : Number(proba_021_05.value),
        from0_6To1_0 : Number(proba_06_10.value),
        from1_1to1_5 : Number(proba_11_15.value),
        from1_5 : Number(proba_15.value),
        max_height : Number(proba_maxheig.value)
    };

    dataTable_1.push(newData);
    setDataInTableOne(active_type_reproduction);
    closeAddForm("form-add-proba");
}

function addState() {
    var stateName = document.getElementById("stateName");
    var FIO = "";

    for(var i = 0; i < states.length; i++) {
        if(states[i].id == stateName.value) {
            FIO = states[i].FIO;
        }
    }

    var newState = {
        id : stateName.value,
        FIO : FIO
    };

    dataTable_2.push(newState);
    setDataInTableTwo();
    closeAddForm("form-add-state");

}

function addGps() {
    var latitudeAdd = document.getElementById("latitude-add");
    var longitudeAdd = document.getElementById("longitude-add");

    var newGps = {
        latitude : latitudeAdd.value,
        longitude : longitudeAdd.value,
        flag_center : 1
    }

    latitudeAdd.value = "";
    longitudeAdd.value = "";

    dataTable_3.push(newGps);
    setDataInTableThree();
    closeAddForm("form-add-gps");
}

async function openAddForm(id) {

    if(id == "form-add-state" && states == null) {
        var stateName = document.getElementById("stateName");
        var newHtml = "";

        states = await getAllProfile();

        for(var i = 0; i < states.length; i++) {
            newHtml = newHtml + "<option value=\"" + states[i].id + "\">" + states[i].FIO + "</option>";
        }

        stateName.innerHTML = newHtml;
    }

    var formAddProba = document.getElementById(id);
    formAddProba.classList.remove("display-none");

    var body = document.getElementById("body");
    body.classList.add("overflowHiddenImportant");
}

function closeAddForm(id) {
    var formAddProba = document.getElementById(id);
    formAddProba.classList.add("display-none");

    var body = document.getElementById("body");
    body.classList.remove("overflowHiddenImportant");

    if(id == "form-add-proba") {
        var breedName_proba = document.getElementById("breedName-proba");
        var proba_021_05 = document.getElementById("proba-0.21-0.5");
        var proba_11_15 = document.getElementById("proba-1.1-1.5");
        var proba_02 = document.getElementById("proba-0.2");
        var proba_06_10 = document.getElementById("proba-0.6-1.0");
        var proba_15 = document.getElementById("proba-1.5");
        var proba_maxheig = document.getElementById("proba-maxheig");

        proba_021_05.value = "";
        proba_11_15.value = "";
        proba_02.value = "";
        proba_06_10.value = "";
        proba_15.value = "";
        proba_maxheig.value = "";
    }

}

async function saveData() {
    var dateForm = document.getElementById("start");
    var regionRFForm = document.getElementById("regionRF");
    var lesNameForm = document.getElementById("lesName");
    var ucLesNameForm = document.getElementById("ucLesName");
    var quarterForm = document.getElementById("quarter");
    var soil_lotForm = document.getElementById("soil_lot");
    var sample_areaForm = document.getElementById("sample_area");

    var requestData = {
        sample: {
            date : dateForm.value,
            id_district_forestly : ucLesNameForm.value,
            id_forestly : lesNameForm.value,
            id_profile : dataTable_2[0].id,
            id_quarter : quarterForm.value,
            id_subjectrf : regionRFForm.value,
            profile : dataTable_2[0].value,
            sample_area : sample_areaForm.value,
            soil_lot : soil_lotForm.value
        },
        list_data : dataTable_1,
        gps_data : dataTable_3
    };

    await getCreateRecalculationDetailData(requestData);

    getRecalculationOnTrialAreaPage();
}
