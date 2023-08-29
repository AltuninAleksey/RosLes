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

    var districtForestly = document.getElementById("ucLesName");
    districtForestly.addEventListener('change',function() {
        changeDataSelectQuarter(districtForestly.value);
    });

    var back_event = document.getElementById("back_event");
    back_event.addEventListener('click',function() {
        getDescriptionListLand();
    });

}

async function openPage() {

    var allForestData = await CommonBusiness.getAllForest();

    APP.subjectrf = allForestData.subjectrf;
    APP.forestly = allForestData.forestly;
    APP.district_forestly = allForestData.district_forestly;
    APP.quarter = allForestData.quarter;

    APP.documentData = {}

    await setDataInPage();
}

async function setDataInPage() {

    await setDataInHeader();

    document.getElementById("header").innerHTML  = "Новое описание участка";

    let typeReproduction = document.getElementById("typeReproduction");
    let newHtml = "";

    APP.typeReproduction = await CommonBusiness.getAllMethodofreforestation();

    for(var i = 0; i < APP.typeReproduction.length; i++) {
        newHtml = newHtml + "<option value=\"" + APP.typeReproduction[i].id + "\">" + APP.typeReproduction[i].name_of_method + "</option>";
    }

    typeReproduction.innerHTML = newHtml;
}

async function setDataInHeader() {
    drawSelectSubjectRF();
    var regions = document.getElementById("regionRF");
    changeDataSelectForestly(regions.value);
}

function drawSelectSubjectRF() {
    var regions = document.getElementById("regionRF");
    var newHtml = "";

    for(var i = 0; i < APP.subjectrf.length; i++) {
        newHtml = newHtml + "<option value=\"" + APP.subjectrf[i].id + "\">" + APP.subjectrf[i].name_subject_RF + "</option>";
    }
    regions.innerHTML = newHtml;
}

function drawSelectForestly() {
    var lesName = document.getElementById("lesName");
    var newHtml = "";

    for(var i = 0; i < APP.forestly.length; i++) {
        newHtml = newHtml + "<option value=\"" + APP.forestly[i].id + "\">" + APP.forestly[i].name_forestly + "</option>";
    }
    lesName.innerHTML = newHtml;
}

function drawSelectDistrictForestly() {
    var ucLesName = document.getElementById("ucLesName");
    var newHtml = "";

    for(var i = 0; i < APP.district_forestly.length; i++) {
        newHtml = newHtml + "<option value=\"" + APP.district_forestly[i].id + "\">" + APP.district_forestly[i].name_district_forestly + "</option>";
    }
    ucLesName.innerHTML = newHtml;
}

function drawSelectQuarter() {
    var newHtml = "";
    for(var j = 0; j < APP.quarter.length; j++) {
        newHtml = newHtml + "<option value=\"" + APP.quarter[j].id + "\">" + APP.quarter[j].quarter_name + "</option>";
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
    changeDataSelectQuarter(districtForestly.value);
}

async function changeDataSelectQuarter(id) {
    if(id != undefined && id != null && id != "") {
        APP.quarter = await getQuarterByIdDistrictForestly(id);
    } else {
        APP.quarter = [];
    }
    drawSelectQuarter();
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



    var nowDate = new Date();
    var date = nowDate.getFullYear()+'-'+(nowDate.getMonth()+1)+'-'+nowDate.getDate();

    let data = {
        id_quarter: quarter,
        date: date,
        id_district_forestly: ucLesName,
        id_forestly: lesName,
        id_subject_rf: regions,
        soil_lot: soil_lot,
        sample_region: sample_region,
        number_region: number_region,
        year_assignment_land: year_assignment_land,
        year_format_fond_trees: year_format_fond_trees,
        inf_restore_forest: inf_restore_forest,
        breed_structure_sapling_act_land: breed_structure_sapling_act_land,
        economy_act_land: economy_act_land,
        change_breed_and_structure_sapling: change_breed_and_structure_sapling,
        results_surtvey: results_surtvey,
        recommendation: recommendation,
        farm_according_data_survey: farm_according_data_survey,
        breed_composition_sapling_data_surver: breed_composition_sapling_data_surver,
        id_method_of_reforestation: typeReproduction,
        count_plants: null,
        preservation_breed: null,
        id_schema_mixing_breeds: null

    }

    if(!CommonFunction.checkMandatoryData()) {
        alert("Заполните все обязательные поля!");
        return;
    }

    await NewPlotDescriptionBusiness.createPlotDescription(data);

    getDescriptionListLand();
}