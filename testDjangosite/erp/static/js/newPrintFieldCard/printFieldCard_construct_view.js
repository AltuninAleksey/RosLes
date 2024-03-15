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
        getFieldCard();
    });

    var statementRecalculationsDetail = document.getElementById("statementRecalculationsDetail");
    statementRecalculationsDetail.addEventListener('click',function() {
        getStatementRecalculationsDetail(APP.documentData.id_list_region);
    });

    var plotDescription = document.getElementById("plotDescription");
    plotDescription.addEventListener('click',function() {
        getPlotDescription(APP.documentData.id_desc, idParent);
    });

    var addRedButtonPoint7 = document.getElementById("add-red-button-point-7");
    addRedButtonPoint7.addEventListener('click',function() {
        addNewLineInPoint7();
    });

    var addRedButtonGpsPoint = document.getElementById("add-red-button-gps-point");
    addRedButtonGpsPoint.addEventListener('click',function() {
        addNewLineInGpsPoint();
    });

    let plot_farm = document.getElementById('plot_farm_referring_land').disabled = true;
    var conclusion = document.getElementById('conclusion');
    var respond_farm = document.getElementById('respond_farm');

    conclusion.onchange = function() {
       var accord = document.getElementById("conclusion").value;
       var respond_farm_value = respond_farm.value;

       if (accord == "Соответствует" && respond_farm_value == "Соответствует") {
        document.getElementById('plot_farm_referring_land').disabled = true;
       } else {
        document.getElementById('plot_farm_referring_land').disabled = false;
       }
    }

    respond_farm.onchange = function() {
       var accord = document.getElementById("conclusion").value;
       var respond_farm_value = respond_farm.value;

       if (accord == "Соответствует" && respond_farm_value == "Соответствует") {
            document.getElementById('plot_farm_referring_land').disabled = true;
       } else {
            document.getElementById('plot_farm_referring_land').disabled = false;
       }
    }

    var completeness = document.getElementById('completeness');
    completeness.onchange = function() {
        if(Number(completeness.value) > 1) {
            completeness.value = 1;
        }

        if(Number(completeness.value) < 0) {
            completeness.value = 0;
        }
    }

    var completeness2 = document.getElementById('completeness2');
    completeness2.onchange = function() {
        if(Number(completeness2.value) > 1) {
            completeness2.value = 1;
        }

        if(Number(completeness2.value) < 0) {
            completeness2.value = 0;
        }
    }

    var number_order = document.getElementById('number_order');
    number_order.onchange = function() {
        var number_order_2 = document.getElementById('number_order_2');

        number_order_2.innerHTML = ( number_order.options[number_order.selectedIndex] == undefined)? "": number_order.options[number_order.selectedIndex].text
    }

    let category_of_forest_fund_lands_other = document.getElementById('category_of_forest_fund_lands_other').style.display = 'none';
    var category_of_forest_fund_lands = document.getElementById('category_of_forest_fund_lands');

    category_of_forest_fund_lands.onchange = function() {
       var accord = document.getElementById("category_of_forest_fund_lands").value;
       if (accord === "6") {
            document.getElementById('category_of_forest_fund_lands_other').style.display = 'block';
            document.getElementById('r1').style.height='240px';
            document.getElementById('lands_other').onchange = function() {
                document.getElementById('category_of_forest_fund_lands_2').innerHTML = document.getElementById('lands_other').value;
            }
       } else {
            document.getElementById('category_of_forest_fund_lands_other').style.display = 'none';
            document.getElementById('r1').style.height='200px';
       }

       if(accord === "6") {
            document.getElementById('category_of_forest_fund_lands_2').innerHTML =
            document.getElementById('lands_other').value == undefined? "":document.getElementById('lands_other').value;
       } else {
           for(var i = 0; i < APP.allCategoryOfForestFundLands.length; i++) {
               if(APP.allCategoryOfForestFundLands[i].id == accord) {
                   document.getElementById('category_of_forest_fund_lands_2').innerHTML = APP.allCategoryOfForestFundLands[i].name_category;
                   break;
               }
           }
       }
     }

    var point7date = document.getElementById('point7date');
    point7date.onchange = function() {
        document.getElementById('point7date2').value = document.getElementById('point7date').value;
    }

    var point7number = document.getElementById('point7number');
    point7number.onchange = function() {
        document.getElementById('point7number2').value = document.getElementById('point7number').value;
    }

    var point7agreed = document.getElementById('point7agreed');
    point7agreed.onchange = function() {
        document.getElementById('point7agreed2').value = document.getElementById('point7agreed').value;
    }

}

async function openPage() {

    //var allForestData = await CommonBusiness.getAllForest();

    APP.userData = await CommonBusiness.getUserData();

    APP.subjectrf = await CommonBusiness.getAllSubjectrf();
    APP.subjectrf = APP.subjectrf.sort(function(a, b) { return a.name_subject_RF > b.name_subject_RF? 1 : -1; });

    //APP.subjectrf = allForestData.subjectrf.sort(function(a, b) { return a.name_subject_RF > b.name_subject_RF? 1 : -1; });
    //APP.forestly = allForestData.forestly;
    //APP.district_forestly = allForestData.district_forestly;
    //APP.quarter = allForestData.quarter;

    APP.documentData = {};

    APP.breeds = await CommonBusiness.getAllBreeds();

    APP.allEconomy = await PrintFieldCardBusiness.getAllEconomy();

    await setDataInPage();
}

async function setDataInPage() {
    await setDataInHeader();

    document.getElementById("headerStr").innerHTML = "Новая полевая карточка";
    document.getElementById("number_region").value = "";
    document.getElementById("sample_region").value = "";
    document.getElementById("soil_lot").value = "";

    await setDataInCharacteristic();
    await setDataInResultNaturObs();
    await setDataInCharacteristicMolodniac();
    await setConclusion();

}

async function setConclusion() {

    document.getElementById("respond_farm").value = "Соответствует";
    document.getElementById("conclusion").value = "Соответствует";
    document.getElementById("point7date2").value = "";
    document.getElementById("point7number2").value = "";
    document.getElementById("point7agreed2").value = "";
    document.getElementById("number_order").value = "188";
    document.getElementById("plot_farm_referring_land").value = "";
    document.getElementById("recomendation").value = "";
    document.getElementById("plot_features").value = "";
    document.getElementById("site_survey").value = "";
    document.getElementById("in_front").value = "";
    document.getElementById("date_and_time").value = "";

    var number_order = document.getElementById('number_order');
    var number_order_2 = document.getElementById('number_order_2');
    number_order_2.innerHTML = (number_order.options[number_order.selectedIndex] == undefined)? 1:number_order.options[number_order.selectedIndex].text;
}


async function saveFieldCard() {

    var regions = document.getElementById("regionRF");
    var lesName = document.getElementById("lesName");
    var ucLesName = document.getElementById("ucLesName");
    var quarter = document.getElementById("quarter");

    var nowDate = new Date();
    var date = nowDate.getFullYear()+'-'+(nowDate.getMonth()+1)+'-'+nowDate.getDate();

    var gps = [];

    for(var i = 0; i < APP.countLineGpsPoint; i++) {

        var id_sample = document.getElementById("id_sample"+i).value;
        var latitude = document.getElementById("latitude"+i).value;
        var longitude = document.getElementById("longitude"+i).value;

        var item = {
            longitude: longitude,
            latitude: latitude,
            flag_center: 1
        }

        gps.push(item);
    }


    data = {
        date: date,
        id_district_forestly: ucLesName.value,
        id_forestly: lesName.value,
        id_quarter: quarter.value,
        id_subject_rf: Number(APP.userData.id_subject_rf),//regions.value,
        number_region: document.getElementById("number_region").value,
        sample_region: document.getElementById("sample_region").value,
        soil_lot: document.getElementById("soil_lot").value,
        dacha: document.getElementById("dacha").value == ""? null : document.getElementById("dacha").value,
        name_quarter: document.getElementById("quarter").value == ""? null : document.getElementById("quarter").value,
        gps: gps,
        id_profile: APP.userData.id,
        width: null,
        lenght: null,
        square: null,
        sample_area: 0
    };


    if(document.getElementById("point7year").value != undefined && document.getElementById("point7year").value != null && document.getElementById("point7year").value != "") {
        if(Number(document.getElementById("point7year").value) == NaN || Number(document.getElementById("point7year").value) <= 1901) {
            alert("Введите корректное значение года. Год не должен быть меньше 1901!");
            var item = document.getElementById("point7year");
            if(!item.classList.contains("mandatory_warning")) {
                item.classList.add("mandatory_warning");
            }
            return;
        } else {
            var item = document.getElementById("point7year");

            if(item.classList.contains("mandatory_warning")) {
                item.classList.remove("mandatory_warning");
            }
        }

        data.point7year = document.getElementById("point7year").value;
    }

    if(checkData(document.getElementById("breed_composition").value)) {
        data.breed_composition = document.getElementById("breed_composition").value;
    }

    if(checkData(document.getElementById("lands_other").value)) {
        data.lands_other = document.getElementById("lands_other").value;
    }

    if(checkData(document.getElementById("completeness2").value)) {
        data.completeness = document.getElementById("completeness2").value;
    }

    if(checkData(document.getElementById("conclusion").value)) {
        data.conclusion = document.getElementById("conclusion").value;
    }

    if(checkData(document.getElementById("respond_farm").value)) {
        data.respond_farm = document.getElementById("respond_farm").value == "Соответствует"? true:false;
    }

    if(checkData(document.getElementById("count_sample_area").value)) {
        data.count_sample_area = document.getElementById("count_sample_area").value;
    }

    if(checkData(document.getElementById("date_and_time").value == ""? APP.documentData.date_and_time:document.getElementById("date_and_time").value)) {
        data.date_and_time = document.getElementById("date_and_time").value == ""? APP.documentData.date_and_time:document.getElementById("date_and_time").value;
    }

    if(checkData(document.getElementById("forest_type").value == ""? null: document.getElementById("forest_type").value)) {
        data.forest_type = document.getElementById("forest_type").value == ""? null: document.getElementById("forest_type").value;
    }

    if(checkData(document.getElementById("category_of_forest_fund_lands").value)) {
        data.id_category_of_forest_fund_lands = document.getElementById("category_of_forest_fund_lands").value;
    }

    if(checkData(document.getElementById("economy_sapling").value)) {
        data.id_economy_sapling = document.getElementById("economy_sapling").value;
    }

    if(checkData(document.getElementById("forest_protection_category").value)) {
        data.id_forest_protection_category = document.getElementById("forest_protection_category").value;
    }

    if(checkData(document.getElementById("method_of_reforestation").value)) {
        data.id_method_of_reforestation = document.getElementById("method_of_reforestation").value;
    }

    if(checkData(document.getElementById("id_point7_table2_sapling").value)) {
        data.id_point7_table2_sapling = document.getElementById("id_point7_table2_sapling").value;
    }

    if(checkData(document.getElementById("purpose_of_forests").value)) {
        data.id_purpose_of_forests = document.getElementById("purpose_of_forests").value;
    }

    if(checkData(document.getElementById("type_forest_growing_conditions").value)) {
        data.id_type_forest_growing_conditions = document.getElementById("type_forest_growing_conditions").value;
    }

    if(checkData(document.getElementById("in_front").value)) {
        data.in_front = document.getElementById("in_front").value;
    }

    if(checkData(document.getElementById("plot_farm_referring_land").value)) {
        data.plot_farm_referring_land = document.getElementById("plot_farm_referring_land").value;
    }

    if(checkData(document.getElementById("plot_features").value)) {
        data.plot_features = document.getElementById("plot_features").value;
    }

    if(checkData(document.getElementById("completeness").value == ""? null: document.getElementById("completeness").value)) {
        data.point7_completeness = document.getElementById("completeness").value == ""? null: document.getElementById("completeness").value;
    }

    if(checkData(document.getElementById("point7_natural_composition").value)) {
        data.point7_natural_composition = document.getElementById("point7_natural_composition").value;
    }

    if(checkData(document.getElementById("economy").value)) {
        data.id_economy = document.getElementById("economy").value;
    }

    if(checkData(document.getElementById("stock").value == ""? null : document.getElementById("stock").value)) {
        data.point7_stock = document.getElementById("stock").value == ""? null : document.getElementById("stock").value;
    }

    if(checkData(document.getElementById("point7agreed").value)) {
        data.point7agreed = document.getElementById("point7agreed").value;
    }

    if(checkData(document.getElementById("point7date").value)) {
        data.point7date = document.getElementById("point7date").value;
    }


    if(checkData(document.getElementById("point7number").value)) {
        data.point7number = document.getElementById("point7number").value;
    }

    if(checkData(document.getElementById("protected_areas_of_forests").value)) {
        data.protected_areas_of_forests = document.getElementById("protected_areas_of_forests").value;
    }

    if(checkData(document.getElementById("recomendation").value)) {
        data.recomendation = document.getElementById("recomendation").value;
    }

    if(checkData(document.getElementById("rent_area").value )) {
        data.rent_area = document.getElementById("rent_area").value;
    }

    if(checkData(document.getElementById("site_survey").value )) {
        data.site_survey = document.getElementById("site_survey").value;
    }

    if(checkData(document.getElementById("square_one_sample_area").value )) {
        data.square_one_sample_area = document.getElementById("square_one_sample_area").value;
    }


    if( checkData(document.getElementById("stock2").value)) {
        data.stock = document.getElementById("stock2").value;
    }

    if( checkData(document.getElementById("time_of_reforestation").value)) {
        data.time_of_reforestation = document.getElementById("time_of_reforestation").value;
    }

    if(checkData(document.getElementById("number_order").value)) {
        data.number_order = document.getElementById("number_order").value;
    }


    if(!CommonFunction.checkMandatoryData()) {
        alert("Заполните все обязательные поля!");
        return;
    }

    var createData = await PrintFieldCardBusiness.getCreatePrintFieldCard(data);

    var point7Date = [];

    for(var i = 0; i < APP.countLinePoint7Table; i++) {

        var itemData = {
            id: Number(document.getElementById("idLine"+i).value),
            ratio_composition: document.getElementById("ratio_composition"+i).value,
            age: document.getElementById("age"+i).value,
            avg_diameter: document.getElementById("avg_diameter"+i).value,
            avg_height: document.getElementById("avg_height"+i).value,
            count_plants: document.getElementById("count_of_plants"+i).value,
            breed: document.getElementById("id_breed"+i).value,
            id_field_card: createData.id
        }

        point7Date.push(itemData);
    }

    await PrintFieldCardBusiness.getUpdatePoint7Table(point7Date);

    getFieldCard();
}

function checkData(data) {
    return  data != undefined && data != null && data != "";
}
