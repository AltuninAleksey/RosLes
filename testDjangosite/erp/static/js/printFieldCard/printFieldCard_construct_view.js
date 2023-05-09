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
    document.getElementById("date_and_time").value = APP.documentData.date_and_time;

}
