openPage();
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

//    var districtForestly = document.getElementById("ucLesName");
//    districtForestly.addEventListener('change',function() {
//        changeDataSelectQuarter(districtForestly.value);
//    });

    let idParent = document.getElementById("idParent").value;
    var back_event = document.getElementById("back_event");
    back_event.addEventListener('click',function() {

        if(idParent == 0) {
            getDescriptionListLand();
        } else {
            getStatementRecalculationsDetail(idParent);
        }

    });

    var statementRecalculationsDetail = document.getElementById("statementRecalculationsDetail");
    statementRecalculationsDetail.addEventListener('click',function() {
        getStatementRecalculationsDetail(APP.documentData.id_list_region);
    });

    var printFieldCard = document.getElementById("printFieldCard");
    printFieldCard.addEventListener('click',function() {
        getPrintFieldCard(APP.documentData.id_field_card, idParent);
    });

}

async function openPage() {
    let idDocument = document.getElementById("idDocument").value;

    //var allForestData = await CommonBusiness.getAllForest();

    //APP.subjectrf = await CommonBusiness.getAllSubjectrf();
    //APP.subjectrf = APP.subjectrf.sort(function(a, b) { return a.name_subject_RF > b.name_subject_RF? 1 : -1; });


    //APP.subjectrf = allForestData.subjectrf.sort(function(a, b) { return a.name_subject_RF > b.name_subject_RF? 1 : -1; });
    //APP.forestly = allForestData.forestly;
    //APP.district_forestly = allForestData.district_forestly;
    //APP.quarter = allForestData.quarter;

    var czl = await CommonBusiness.getCZL();
    APP.subjectrf = [];

    var item_subject = {
        id: czl.id_main_subject,
        name_subject_RF: czl.name_main_subject
    }
    APP.subjectrf.push(item_subject);

    for(var i = 0; i < czl.slave_subject.length; i++) {
        var item_subject = {
            id: czl.slave_subject[i].id_subject,
            name_subject_RF: czl.slave_subject[i].name_slave_subject
        }

        APP.subjectrf.push(item_subject);
    }

    APP.documentData = await PlotDescriptionBusiness.getPlotDescriptionDataById(idDocument);

    await setDataInPage();
}

async function setDataInPage() {

    await setDataInHeader();

    document.getElementById("header").innerHTML  = "Описание участка №" + APP.documentData.number_region;
    document.getElementById("number_region").value = APP.documentData.number_region;
    document.getElementById("sample_region").value = APP.documentData.sample_region;
    document.getElementById("soil_lot").value = APP.documentData.soil_lot;
    document.getElementById("dacha").value = APP.documentData.dacha;
    document.getElementById("quarter").value = APP.documentData.name_quarter;
    document.getElementById("year_assignment_land").value = APP.documentData.year_assignment_land;
    document.getElementById("year_format_fond_trees").value = APP.documentData.year_format_fond_trees;
    document.getElementById("inf_restore_forest").value = APP.documentData.inf_restore_forest;
    document.getElementById("breed_structure_sapling_act_land").value = APP.documentData.breed_structure_sapling_act_land;
    document.getElementById("economy_act_land").value = APP.documentData.economy_act_land;
    document.getElementById("change_breed_and_structure_sapling").value = APP.documentData.change_breed_and_structure_sapling;
    document.getElementById("breed_composition_sapling_data_surver").value = APP.documentData.breed_composition_sapling_data_surver;
    document.getElementById("farm_according_data_survey").value = APP.documentData.farm_according_data_survey;
    document.getElementById("results_surtvey").value = APP.documentData.results_surtvey;
    document.getElementById("recommendation").value = APP.documentData.recommendation;

    let typeReproduction = document.getElementById("typeReproduction");
    let newHtml = "";

    APP.typeReproduction = await CommonBusiness.getAllMethodofreforestation();

    for(var i = 0; i < APP.typeReproduction.length; i++) {
        if(APP.typeReproduction[i].id == APP.documentData.id_method_of_reforestation) {
            newHtml = newHtml + "<option selected value=\"" + APP.typeReproduction[i].id + "\">" + APP.typeReproduction[i].name_of_method + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.typeReproduction[i].id + "\">" + APP.typeReproduction[i].name_of_method + "</option>";
        }
    }

    typeReproduction.innerHTML = newHtml;
}

async function setDataInHeader() {
    drawSelectSubjectRF();
    changeDataSelectForestly(APP.documentData.id_subject_rf);
}

function drawSelectSubjectRF() {
    var regions = document.getElementById("regionRF");
    var newHtml = "";

    for(var i = 0; i < APP.subjectrf.length; i++) {
//          if(APP.subjectrf[i].id == APP.documentData.id_subject_rf) {
//            regions.value = APP.subjectrf[i].name_subject_RF;
//            break;
//          }

        if(APP.subjectrf[i].id == APP.documentData.id_subject_rf) {
            newHtml = newHtml + "<option selected value=\"" + APP.subjectrf[i].id + "\">" + APP.subjectrf[i].name_subject_RF + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.subjectrf[i].id + "\">" + APP.subjectrf[i].name_subject_RF + "</option>";
        }
    }
    regions.innerHTML = newHtml;
}

function drawSelectForestly() {
    var lesName = document.getElementById("lesName");
    var newHtml = "";

    for(var i = 0; i < APP.forestly.length; i++) {
        if(APP.forestly[i].id == APP.documentData.id_forestly) {
            newHtml = newHtml + "<option selected value=\"" + APP.forestly[i].id + "\">" + APP.forestly[i].name_forestly + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.forestly[i].id + "\">" + APP.forestly[i].name_forestly + "</option>";
        }
    }
    lesName.innerHTML = newHtml;
}

function drawSelectDistrictForestly() {
    var ucLesName = document.getElementById("ucLesName");
    var newHtml = "";

    for(var i = 0; i < APP.district_forestly.length; i++) {
        if(APP.district_forestly[i].id == APP.documentData.id_district_forestly) {
            newHtml = newHtml + "<option selected value=\"" + APP.district_forestly[i].id + "\">" + APP.district_forestly[i].name_district_forestly + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.district_forestly[i].id + "\">" + APP.district_forestly[i].name_district_forestly + "</option>";
        }
    }
    ucLesName.innerHTML = newHtml;
}

function drawSelectQuarter() {
    var newHtml = "";
    for(var j = 0; j < APP.quarter.length; j++) {
        if(APP.quarter[j].id == APP.documentData.id_quarter) {
            newHtml = newHtml + "<option selected value=\"" + APP.quarter[j].id + "\">" + APP.quarter[j].quarter_name + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.quarter[j].id + "\">" + APP.quarter[j].quarter_name + "</option>";
        }
    }
    document.getElementById("quarter").innerHTML = newHtml;
}

async function changeDataSelectForestly(id) {
    APP.forestly = await getForestlyByIdSubjectrf(id);
    drawSelectForestly();

    var forestlyObject = document.getElementById("lesName");
    changeDataSelectDistrictForestly(forestlyObject.value);
}

async function changeDataSelectDistrictForestly(id) {
    if(id != undefined && id != null && id != "") {
        APP.district_forestly = await getDistrictForestlyByIdForestly(id);
    } else {
        APP.district_forestly = [];
    }
    drawSelectDistrictForestly();

    var districtForestly = document.getElementById("ucLesName");
    //changeDataSelectQuarter(districtForestly.value);
}

async function changeDataSelectQuarter(id) {
    if(id != undefined && id != null && id != "") {
        APP.quarter = await getQuarterByIdDistrictForestly(id);
    } else {
        APP.quarter = [];
    }
    //drawSelectQuarter();
}

async function saveData() {

    let number_region = document.getElementById("number_region").value;
    let sample_region = document.getElementById("sample_region").value;
    let soil_lot = document.getElementById("soil_lot").value;
    let year_assignment_land = document.getElementById("year_assignment_land").value;
    let year_format_fond_trees = document.getElementById("year_format_fond_trees").value;
    let inf_restore_forest = document.getElementById("inf_restore_forest").value;
    let breed_structure_sapling_act_land = document.getElementById("breed_structure_sapling_act_land").value;
    let economy_act_land = document.getElementById("economy_act_land").value;
    let change_breed_and_structure_sapling =  document.getElementById("change_breed_and_structure_sapling").value;
    let breed_composition_sapling_data_surver =document.getElementById("breed_composition_sapling_data_surver").value;
    let farm_according_data_survey = document.getElementById("farm_according_data_survey").value;
    let results_surtvey = document.getElementById("results_surtvey").value;
    let recommendation = document.getElementById("recommendation").value;
    let typeReproduction = document.getElementById("typeReproduction").value;
    let ucLesName = document.getElementById("ucLesName").value;
    let lesName = document.getElementById("lesName").value;
    let quarter = document.getElementById("quarter").value;
    let regions = document.getElementById("regionRF").value;

    let data = {
        id: APP.documentData.id,
        id_district_forestly: ucLesName,
        id_field_card: APP.documentData.id_field_card,
        id_forestly: lesName,
        id_list_region: APP.documentData.id_list_region,
        date: APP.documentData.date,
        sample_region: sample_region,
        soil_lot: soil_lot,
        id_subject_rf: regions,
        dacha: document.getElementById("dacha").value == "" ? null : document.getElementById("dacha").value,
        name_quarter: document.getElementById("quarter").value,
        count_plants: APP.documentData.count_plants,
        id_schema_mixing_breeds: APP.documentData.id_schema_mixing_breeds,
        preservation_breed: APP.documentData.preservation_breed,
        number_region: number_region,
        gps: []
    };

    if(year_assignment_land != null && year_assignment_land != undefined && year_assignment_land != "") {
        if(Number(year_assignment_land) == NaN || Number(year_assignment_land) <= 1901) {
            alert("Введите корректное значение года. Год не должен быть меньше 1901!");
            var item = document.getElementById("year_assignment_land");
            if(!item.classList.contains("mandatory_warning")) {
                item.classList.add("mandatory_warning");
            }
            return;
        } else {
            var item = document.getElementById("year_assignment_land");

            if(item.classList.contains("mandatory_warning")) {
                item.classList.remove("mandatory_warning");
            }
        }

        data.year_assignment_land = year_assignment_land;
    }

    if(year_format_fond_trees != null && year_format_fond_trees != undefined && year_format_fond_trees != "") {
        if(Number(year_format_fond_trees) == NaN || Number(year_format_fond_trees) <= 1901) {
            alert("Введите корректное значение года. Год не должен быть меньше 1901!");
            var item = document.getElementById("year_format_fond_trees")
            if(!item.classList.contains("mandatory_warning")) {
                item.classList.add("mandatory_warning");
            }
            return;
        } else {
            var item = document.getElementById("year_format_fond_trees");

            if(item.classList.contains("mandatory_warning")) {
                item.classList.remove("mandatory_warning");
            }
        }

        data.year_format_fond_trees = year_format_fond_trees;
    }

    if(inf_restore_forest != null && inf_restore_forest != undefined && inf_restore_forest != "") {
        data.inf_restore_forest = inf_restore_forest;
    }

    if(breed_structure_sapling_act_land != null && breed_structure_sapling_act_land != undefined && breed_structure_sapling_act_land != "") {
        data.breed_structure_sapling_act_land = breed_structure_sapling_act_land;
    }

    if(economy_act_land != null && economy_act_land != undefined && economy_act_land != "") {
        data.economy_act_land = economy_act_land;
    }

    if(change_breed_and_structure_sapling != null && change_breed_and_structure_sapling != undefined && change_breed_and_structure_sapling != "") {
        data.change_breed_and_structure_sapling = change_breed_and_structure_sapling;
    }

    if(results_surtvey != null && results_surtvey != undefined && results_surtvey != "") {
        data.results_surtvey = results_surtvey;
    }

    if(recommendation != null && recommendation != undefined && recommendation != "") {
        data.recommendation = recommendation;
    }

    if(farm_according_data_survey != null && farm_according_data_survey != undefined && farm_according_data_survey != "") {
        data.farm_according_data_survey = farm_according_data_survey;
    }

    if(breed_composition_sapling_data_surver != null && breed_composition_sapling_data_surver != undefined && breed_composition_sapling_data_surver != "") {
        data.breed_composition_sapling_data_surver = breed_composition_sapling_data_surver;
    }

    if(typeReproduction != null && typeReproduction != undefined && typeReproduction != "") {
        data.id_method_of_reforestation = typeReproduction;
    }


    let idDocument = document.getElementById("idDocument").value;
    let idParent = document.getElementById("idParent").value;

    if(!CommonFunction.checkMandatoryData()) {
        alert("Заполните все обязательные поля!");
        return;
    }

    await PlotDescriptionBusiness.setPlotDescriptionDataById(idDocument, data);

    ShowModal('m1');
}

function ShowModal(elId) {
    var modalAll = document.getElementById(elId);
    modalAll.style.display = "flex";
    document.body.style.overflow = 'hidden'

    setTimeout(function() {
      HideModal(modalAll);
    }, 1500);
}

function HideModal(ell) {
    if (ell.classList.contains('modal-all')) {
      ell.style.display = "none";
    }
    document.body.style.overflow = '';

    let idDocument = document.getElementById("idDocument").value;
    let idParent = document.getElementById("idParent").value;
    getPlotDescription(idDocument, idParent);
}


async function generateDocx() {

    let data = {
        id: APP.documentData.id,
        name_quarter: document.getElementById("quarter").value,
        district_forestly: document.getElementById("ucLesName").options[document.getElementById("ucLesName").selectedIndex].text,
        forestly: document.getElementById("lesName").options[document.getElementById("lesName").selectedIndex].text,
        id_subject_rf: CommonFunction.getSubjectNameByQuarterId(APP.subjectrf, document.getElementById("regionRF").value),
        soil_lot: document.getElementById("soil_lot").value,
        sample_region: document.getElementById("sample_region").value,
        date: APP.documentData.date,
        number_region: document.getElementById("number_region").value,
        year_assignment_land: document.getElementById("year_assignment_land").value,
        year_format_fond_trees: document.getElementById("year_format_fond_trees").value,
        inf_restore_forest: document.getElementById("inf_restore_forest").value,
        breed_structure_sapling_act_land: document.getElementById("breed_structure_sapling_act_land").value,
        economy_act_land: document.getElementById("economy_act_land").value,
        change_breed_and_structure_sapling: document.getElementById("change_breed_and_structure_sapling").value,
        results_surtvey: document.getElementById("results_surtvey").value,
        recommendation: document.getElementById("recommendation").value,
        count_plants: APP.documentData.count_plants,
        preservation_breed: APP.documentData.preservation_breed,
        farm_according_data_survey: document.getElementById("farm_according_data_survey").value,
        breed_composition_sapling_data_surver: document.getElementById("breed_composition_sapling_data_surver").value,
        method_of_reforestation: document.getElementById("typeReproduction").options[document.getElementById("typeReproduction").selectedIndex].text,
        name_dacha: document.getElementById("dacha").value
    }

    var urlFile = await PlotDescriptionBusiness.generateDocx(data);
    urlFile = urlFile.document;

    urlFile = urlGlobal + urlFile;

    window.open(urlFile, '_blank').focus();
}