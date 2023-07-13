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

}

async function openPage() {

    var allForestData = await CommonBusiness.getAllForest();

    APP.subjectrf = allForestData.subjectrf;
    APP.forestly = allForestData.forestly;
    APP.district_forestly = allForestData.district_forestly;
    APP.quarter = allForestData.quarter;

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

    document.getElementById("conclusion").value = "";
    document.getElementById("point7date2").value = "";
    document.getElementById("point7number2").value = "";
    document.getElementById("point7agreed2").value = "";
    document.getElementById("plot_farm_referring_land").value = "";
    document.getElementById("recomendation").value = "";
    document.getElementById("plot_features").value = "";
    document.getElementById("site_survey").value = "";
    document.getElementById("in_front").value = "";
    document.getElementById("date_and_time").value = "";

}


async function saveFieldCard() {

    var regions = document.getElementById("regionRF");
    var lesName = document.getElementById("lesName");
    var ucLesName = document.getElementById("ucLesName");
    var quarter = document.getElementById("quarter");

    var nowDate = new Date();
    var date = nowDate.getFullYear()+'-'+(nowDate.getMonth()+1)+'-'+nowDate.getDate();

    data = {
        breed_composition: document.getElementById("breed_composition").value,
        completeness:  document.getElementById("completeness2").value,
        conclusion: document.getElementById("conclusion").value,
        count_sample_area: document.getElementById("count_sample_area").value,
        date: date,
        date_and_time: document.getElementById("date_and_time").value == ""? APP.documentData.date_and_time:document.getElementById("date_and_time").value,
        forest_type: document.getElementById("forest_type").value == ""? null: document.getElementById("forest_type").value,
        id_category_of_forest_fund_lands: document.getElementById("category_of_forest_fund_lands").value,
        id_district_forestly: ucLesName.value,
        id_forestly: lesName.value,
        id_quarter: quarter.value,
        id_subject_rf: regions.value,
        id_economy_sapling: document.getElementById("economy_sapling").value,
        id_forest_protection_category: document.getElementById("forest_protection_category").value,
        id_method_of_reforestation: document.getElementById("method_of_reforestation").value,
        id_point7_table2_sapling: document.getElementById("id_point7_table2_sapling").value,
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
