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

}

async function openPage() {
    let idDocument = document.getElementById("idDocument").value;

    var allForestData = await CommonBusiness.getAllForest();

    APP.subjectrf = allForestData.subjectrf;
    APP.forestly = allForestData.forestly;
    APP.district_forestly = allForestData.district_forestly;
    APP.quarter = allForestData.quarter;

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

    await setDataInCharacteristic();
    await setDataInResultNaturObs();
    await setDataInCharacteristicMolodniac();
    await setConclusion();

}

async function setConclusion() {

    document.getElementById("conclusion").value = APP.documentData.conclusion;
    document.getElementById("point7date2").value = APP.documentData.point7date;
    document.getElementById("point7number2").value = APP.documentData.point7number;
    document.getElementById("point7agreed2").value = APP.documentData.point7agreed;
    document.getElementById("plot_farm_referring_land").value = APP.documentData.plot_farm_referring_land;
    document.getElementById("recomendation").value = APP.documentData.recomendation;
    document.getElementById("plot_features").value = APP.documentData.plot_features;
    document.getElementById("site_survey").value = APP.documentData.site_survey;
    document.getElementById("in_front").value = APP.documentData.in_front;
    document.getElementById("date_and_time").value = APP.documentData.date_and_time.substring(0, APP.documentData.date_and_time.length-4);;

}


async function saveFieldCard() {

    let idDocument = document.getElementById("idDocument").value;
    let idParent = document.getElementById("idParent").value;

    var regions = document.getElementById("regionRF");
    var lesName = document.getElementById("lesName");
    var ucLesName = document.getElementById("ucLesName");
    var quarter = document.getElementById("quarter");


    data = {
        id: idDocument,
        breed_composition: document.getElementById("breed_composition").value,
        completeness:  document.getElementById("completeness2").value,
        conclusion: document.getElementById("conclusion").value,
        count_sample_area: document.getElementById("count_sample_area").value,
        date: APP.documentData.date,
        date_and_time: document.getElementById("date_and_time").value == ""? APP.documentData.date_and_time:document.getElementById("date_and_time").value,
        forest_type: document.getElementById("forest_type").value == ""? null: document.getElementById("forest_type").value,
        id_category_of_forest_fund_lands: document.getElementById("category_of_forest_fund_lands").value,
        id_desc: APP.documentData.id_desc, // no
        id_district_forestly: ucLesName.value,
        id_forestly: lesName.value,
        id_quarter: quarter.value,
        id_subject_rf: regions.value,
        id_economy_sapling: document.getElementById("economy_sapling").value,
        id_forest_protection_category: document.getElementById("forest_protection_category").value,
        id_list_region: APP.documentData.id_list_region, //no
        id_method_of_reforestation: document.getElementById("method_of_reforestation").value,
        id_point7_table2_sapling: document.getElementById("id_point7_table2_sapling").value,
        id_point7table: APP.documentData.id_point7table, // no
        id_purpose_of_forests: document.getElementById("purpose_of_forests").value,
        id_type_forest_growing_conditions: document.getElementById("type_forest_growing_conditions").value,
        in_front: document.getElementById("in_front").value,
        number_region: document.getElementById("number_region").value,
        plot_farm_referring_land: document.getElementById("plot_farm_referring_land").value,
        plot_features: document.getElementById("plot_features").value,
        point7_completeness:  document.getElementById("completeness").value == ""? null: document.getElementById("completeness").value,
        point7_natural_composition: document.getElementById("point7_natural_composition").value,
        id_economy: document.getElementById("economy").value,
        point7_stock: document.getElementById("stock").value == ""? null : document.getElementById("stock").value,
        point7agreed: document.getElementById("point7agreed").value,
        point7date: document.getElementById("point7date").value,
        point7number: document.getElementById("point7number").value,
        point7year: document.getElementById("point7year").value,
        protected_areas_of_forests: document.getElementById("protected_areas_of_forests").value,
        recomendation: document.getElementById("recomendation").value,
        rent_area: document.getElementById("rent_area").value,
        sample_region: document.getElementById("sample_region").value,
        site_survey: document.getElementById("site_survey").value,
        soil_lot: document.getElementById("soil_lot").value,
        square_one_sample_area: document.getElementById("square_one_sample_area").value,
        stock: document.getElementById("stock2").value,
        time_of_reforestation: document.getElementById("time_of_reforestation").value
    };


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

    getPrintFieldCard(idDocument, idParent)
}

async function generateDocx() {

    var coeff = [];
    for(var i = 0; i < APP.countLinePoint7Table; i++) {

        var itemData = {
            ratio: document.getElementById("ratio_composition"+i).value,
            age: document.getElementById("age"+i).value,
            avg_diameter: document.getElementById("avg_diameter"+i).value,
            avg_height: document.getElementById("avg_height"+i).value,
            count_plants: document.getElementById("count_of_plants"+i).value,
            breed: CommonFunction.getBreedsName(APP.breeds, document.getElementById("id_breed"+i).value)
        }

        coeff.push(itemData);
    }

    var samples = [];
    for(var i = 0; i < APP.gpsTable.length; ++i) {

        var itemData = {
            number: APP.gpsTable[i].id_sample,
            latitude: APP.gpsTable[i].latitude,
            longitude: APP.gpsTable[i].longitude
        };

        samples.push(itemData);
    }
    var saplings = [];
    for(var i = 0; i < APP.point7Table2Sapling.length; i++) {

        var itemData = {
            ratio: APP.point7Table2Sapling[i].ratio_composition,
            age: APP.point7Table2Sapling[i].age,
            avg_diameter: APP.point7Table2Sapling[i].avg_diameter,
            avg_height: APP.point7Table2Sapling[i].avg_height,
            count_plants: APP.point7Table2Sapling[i].count_of_plants,
            breed: CommonFunction.getBreedsName(APP.breeds, APP.point7Table2Sapling[i].id_breed)
        }

        saplings.push(itemData);
    }

    var data = {
        id: Number(document.getElementById("idDocument").value),
        number_region: document.getElementById("number_region").value,
        subject_rf: CommonFunction.getSubjectNameByQuarterId(APP.subjectrf, document.getElementById("regionRF").value),
        forestly: CommonFunction.getForestlyNameByQuarterId(APP.forestly, document.getElementById("lesName").value),
        district_forestly: CommonFunction.getDistrictForestlyNameByQuarterId(APP.district_forestly, document.getElementById("ucLesName").value),
        name_quarter: CommonFunction.getQuarterNameByQuarterId(APP.quarter, document.getElementById("quarter").value),
        sample_area: document.getElementById("sample_region").value,
        soil_lot: document.getElementById("soil_lot").value,
        purpose_of_forests: document.getElementById("purpose_of_forests").options[document.getElementById("purpose_of_forests").selectedIndex].text,
        forest_protection_category: document.getElementById("forest_protection_category").options[document.getElementById("forest_protection_category").selectedIndex].text,
        protected_areas_of_forests: document.getElementById("protected_areas_of_forests").value,
        rent_area: document.getElementById("rent_area").value ? "Да" : "Нет",
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
        in_front: document.getElementById("in_front").value
    }

    await PrintFieldCardBusiness.generateDocx(data);

}
