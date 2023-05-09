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
        getStatementRecalculationsDetail(idParent);
    });

}

async function openPage() {
    let idDocument = document.getElementById("idDocument").value;

    var allForestData = await CommonBusiness.getAllForest();

    APP.subjectrf = allForestData.subjectrf;
    APP.forestly = allForestData.forestly;
    APP.district_forestly = allForestData.district_forestly;
    APP.quarter = allForestData.quarter;

    APP.breeds = await CommonBusiness.getAllBreeds();

    APP.documentData = await RecalculatingDetailBusiness.getRecalculatingDetailDataById(idDocument);
    let listData = await RecalculatingDetailBusiness.getListData(idDocument);
    APP.dataTable_1 = listData.list_data;
    APP.dataTable_2 = listData.post_data;
    APP.dataTable_3 = listData.gps_data;
    APP.dataTable_0 = listData.non_null_list_data;

    APP.dataTable_4 = await RecalculatingDetailBusiness.getPhotoPoint(idDocument);

    await setDataInHeader();
    await setDataInTableNull();
    await setDataInTableOne(1);
    await setDataInTableTwo();
    await setDataInTableThree();
    await setDataInTableFour();

}

