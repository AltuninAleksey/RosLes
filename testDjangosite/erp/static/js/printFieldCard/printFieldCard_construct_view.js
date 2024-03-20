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

    let idParent = document.getElementById("idParent").value;
    var back_event = document.getElementById("back_event");
    back_event.addEventListener('click',function() {

        if(idParent == 0) {
            getFieldCard();
        } else {
            getStatementRecalculationsDetail(idParent);
        }

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

        number_order_2.innerHTML = number_order.options[number_order.selectedIndex].text
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

}

async function openPage() {
    let idDocument = document.getElementById("idDocument").value;

    //var allForestData = await CommonBusiness.getAllForest();

    APP.userData = await CommonBusiness.getUserData();

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


    APP.documentData = await PrintFieldCardBusiness.getPrintFieldCardDataById(idDocument);

    APP.breeds = await CommonBusiness.getAllBreeds();
    APP.allEconomy = await PrintFieldCardBusiness.getAllEconomy();

    await setDataInPage();
}

async function setDataInPage() {
    await setDataInHeader();

    document.getElementById("headerStr").innerHTML = "Полевая карточка №" + APP.documentData.number_region;
    document.getElementById("number_region").value = APP.documentData.number_region;
    document.getElementById("sample_region").value = APP.documentData.sample_region;
    document.getElementById("soil_lot").value = APP.documentData.soil_lot;
    document.getElementById("dacha").value = APP.documentData.dacha;
    document.getElementById("quarter").value = APP.documentData.name_quarter;

    await setDataInCharacteristic();
    await setDataInResultNaturObs();
    await setDataInCharacteristicMolodniac();
    await setConclusion();

}

async function setConclusion() {

    document.getElementById("respond_farm").value = APP.documentData.respond_farm == null || APP.documentData.respond_farm == true? "Соответствует" : "Не соответствует";
    document.getElementById("conclusion").value = APP.documentData.conclusion == "" || APP.documentData.conclusion == null? "Соответствует": APP.documentData.conclusion;
    document.getElementById("point7date2").value = APP.documentData.point7date;
    document.getElementById("point7number2").value = APP.documentData.point7number;
    document.getElementById("point7agreed2").value = APP.documentData.point7agreed;
    document.getElementById("number_order").value = APP.documentData.number_order == null? "188" : APP.documentData.number_order;
    document.getElementById("plot_farm_referring_land").value = APP.documentData.plot_farm_referring_land;
    document.getElementById("recomendation").value = APP.documentData.recomendation;
    document.getElementById("plot_features").value = APP.documentData.plot_features;
    document.getElementById("site_survey").value = APP.documentData.site_survey;
    document.getElementById("in_front").value = APP.documentData.in_front;
    if(APP.documentData.date_and_time != null) {
        document.getElementById("date_and_time").value = APP.documentData.date_and_time.substring(0, APP.documentData.date_and_time.length-4);;
    } else {
        document.getElementById("date_and_time").value = "";
    }


    var number_order = document.getElementById('number_order');
    var number_order_2 = document.getElementById('number_order_2');
    number_order_2.innerHTML = (number_order.options[number_order.selectedIndex] == undefined)? "":number_order.options[number_order.selectedIndex].text;

    var conclusion = document.getElementById('conclusion');
    var respond_farm = document.getElementById('respond_farm');

    var accord = document.getElementById("conclusion").value;
    var respond_farm_value = respond_farm.value;

    if (accord == "Соответствует" && respond_farm_value == "Соответствует") {
        document.getElementById('plot_farm_referring_land').disabled = true;
    } else {
        document.getElementById('plot_farm_referring_land').disabled = false;
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


async function saveFieldCard() {

    let idDocument = document.getElementById("idDocument").value;
    let idParent = document.getElementById("idParent").value;

    var regions = document.getElementById("regionRF");
    var lesName = document.getElementById("lesName");
    var ucLesName = document.getElementById("ucLesName");
    var quarter = document.getElementById("quarter");

    var gps = [];

    var startIndexForSaveGps = APP.gpsTable == undefined? 0 : APP.gpsTable.length;

    for(var i = startIndexForSaveGps; i < APP.countLineGpsPoint; i++) {
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
        id: idDocument,
        id_district_forestly: ucLesName.value,
        id_forestly: lesName.value,
        id_quarter: quarter.value,
        id_subject_rf: APP.documentData.id_subject_rf, //regions.value,
        sample_region: document.getElementById("sample_region").value,
        soil_lot: document.getElementById("soil_lot").value,
        id_list_region: APP.documentData.id_list_region, //no
        date: APP.documentData.date,
        id_point7table: APP.documentData.id_point7table, // no
        number_region: document.getElementById("number_region").value,
        id_desc: APP.documentData.id_desc, // no
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
        data.respond_farm = document.getElementById("respond_farm").value == "Соответствует"? true: false;
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

    await PrintFieldCardBusiness.getUpdatePrintFieldCard(idDocument, data);

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
            id_field_card: APP.documentData.id
        }

        point7Date.push(itemData);
    }

    await PrintFieldCardBusiness.getUpdatePoint7Table(point7Date);

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
    getPrintFieldCard(idDocument, idParent);
}

async function generateDocx() {

    var coeff = [];
    for(var i = 0; i < APP.countLinePoint7Table; i++) {

        var itemData = {
            ratio: document.getElementById("ratio_composition"+i).value,
            age: document.getElementById("age"+i).value,
            avg_diametr: document.getElementById("avg_diameter"+i).value,
            avg_height: document.getElementById("avg_height"+i).value,
            count_plants: document.getElementById("count_of_plants"+i).value,
            breed: CommonFunction.getBreedsName(APP.breeds, document.getElementById("id_breed"+i).value)
        }

        coeff.push(itemData);
    }

    var samples = [];

    for(var i = 0; i < APP.countLineGpsPoint; i++) {

        var id_sample = document.getElementById("id_sample"+i).value;
        var latitude = document.getElementById("latitude"+i).value;
        var longitude = document.getElementById("longitude"+i).value;

        var item = {
            number: id_sample,
            longitude: longitude,
            latitude: latitude
        }

        samples.push(item);
    }

    var saplings = [];
    if(APP.point7Table2Sapling != undefined) {
        for(var i = 0; i < APP.point7Table2Sapling.length; i++) {

            var itemData = {
                ratio: APP.point7Table2Sapling[i].ratio_composition,
                age: APP.point7Table2Sapling[i].age,
                diameter: APP.point7Table2Sapling[i].avg_diameter,
                avg_height: APP.point7Table2Sapling[i].avg_height,
                count_plants: APP.point7Table2Sapling[i].count_of_plants,
                breed: CommonFunction.getBreedsName(APP.breeds, APP.point7Table2Sapling[i].id_breed)
            }

            saplings.push(itemData);
        }
    }


    var data = {
        id: Number(document.getElementById("idDocument").value),
        number_region: document.getElementById("number_region").value,
        subject_rf: CommonFunction.getSubjectNameByQuarterId(APP.subjectrf, document.getElementById("regionRF").value),
        forestly: CommonFunction.getForestlyNameByQuarterId(APP.forestly, document.getElementById("lesName").value),
        district_forestly: CommonFunction.getDistrictForestlyNameByQuarterId(APP.district_forestly, document.getElementById("ucLesName").value),
        name_quarter: document.getElementById("quarter").value,
        sample_area: document.getElementById("sample_region").value,
        soil_lot: document.getElementById("soil_lot").value,
        purpose_of_forests: document.getElementById("purpose_of_forests").options[document.getElementById("purpose_of_forests").selectedIndex].text,
        forest_protection_category: document.getElementById("forest_protection_category").options[document.getElementById("forest_protection_category").selectedIndex].text,
        protected_areas_of_forests: document.getElementById("protected_areas_of_forests").value,
        rent_area: document.getElementById("rent_area").value == 'false' ?  "Нет" : "Да",
        category_of_forest_fund_lands: document.getElementById("category_of_forest_fund_lands").options[document.getElementById("category_of_forest_fund_lands").selectedIndex].text,
        method_of_reforestation: document.getElementById("method_of_reforestation").options[document.getElementById("method_of_reforestation").selectedIndex].text,
        time_of_reforestation: document.getElementById("time_of_reforestation").value,
        forest_conditions: document.getElementById("type_forest_growing_conditions").options[document.getElementById("type_forest_growing_conditions").selectedIndex].text,
        forest_type: document.getElementById("forest_type").value,
        point7year: document.getElementById("point7year").value,
        point7date: document.getElementById("point7date").value,
        point7number: document.getElementById("point7number").value,
        point7agreed: document.getElementById("point7agreed").value,
        point7_natural_composition: document.getElementById("point7_natural_composition").value,
        economy: document.getElementById("economy").options[document.getElementById("economy").selectedIndex].text,
        completeness: document.getElementById("completeness").value,
        point7_stock: document.getElementById("stock").value,
        coeff: coeff,
        square_one_sample_area: document.getElementById("square_one_sample_area").value,
        count_sample_area: document.getElementById("count_sample_area").value,
        samples: samples,
        breed_composition: document.getElementById("breed_composition").value,
        economy_sapling: document.getElementById("economy_sapling").options[document.getElementById("economy_sapling").selectedIndex].text,
        completeness_sapling: document.getElementById("completeness2").value,
        stock_sapling: document.getElementById("stock2").value,
        saplings: saplings,
        conclusion: document.getElementById("conclusion").value,
        date_and_time:  document.getElementById("date_and_time").value == ""? APP.documentData.date_and_time:document.getElementById("date_and_time").value,
        recomendation: document.getElementById("recomendation").value,
        plot_features: document.getElementById("plot_features").value,
        site_survey: document.getElementById("site_survey").value,
        in_front: document.getElementById("in_front").value,
        lands_other:  (document.getElementById("category_of_forest_fund_lands").value == 6) ? document.getElementById("lands_other").value : null,
        number_order: document.getElementById("number_order").options[number_order.selectedIndex] != null? document.getElementById("number_order").options[number_order.selectedIndex].text : "",
        name_dacha: document.getElementById("dacha").value,
        respond_farm: document.getElementById("respond_farm").value
    }

    if(!document.getElementById('plot_farm_referring_land').disabled) {
        data.plot_farm_referring_land = document.getElementById("plot_farm_referring_land").value;
    }


    var urlFile = await PrintFieldCardBusiness.generateDocx(data);
    urlFile = urlFile.document;

    urlFile = urlGlobal + urlFile;

    window.open(urlFile, '_blank').focus();
}

function checkData(data) {
    return  data != undefined && data != null && data != "";
}
