async function setDataInResultNaturObs() {

    APP.documentData.statementRecalculationsData = await PrintFieldCardBusiness.getStatementRecalculationsDetailDataById(APP.documentData.id_list_region);
    APP.documentData.sampleListByIdListRegion = await PrintFieldCardBusiness.getSampleByIdListRegion(APP.documentData.id_list_region);

    if(APP.documentData.statementRecalculationsData.sample_region != APP.documentData.square_one_sample_area) {
        APP.documentData.square_one_sample_area = APP.documentData.statementRecalculationsData.sample_region;
    }

    if(APP.documentData.sampleListByIdListRegion.length != APP.documentData.count_sample_area) {
        APP.documentData.count_sample_area = APP.documentData.sampleListByIdListRegion.length;
    }

    document.getElementById("square_one_sample_area").value = APP.documentData.square_one_sample_area;
    document.getElementById("count_sample_area").value = APP.documentData.count_sample_area;

    APP.gpsTable = await PrintFieldCardBusiness.getGpsByListRegionId(APP.documentData.id_list_region);
    let gpsTable = document.getElementById("gpsTable");
    let newHTML = "";

    for(let i = 0; i < APP.gpsTable.length; i++) {
        newHTML += `<tr>
                        <td><input readonly type="text" style="width: 100%" value="${APP.gpsTable[i].id_sample}"></td>
                        <td><input readonly type="text" style="width: 100%" value="${APP.gpsTable[i].latitude}"></td>
                        <td><input readonly type="text" style="width: 100%" value="${APP.gpsTable[i].longitude}"></td>
                    </tr>`;
    }

    gpsTable.innerHTML = newHTML;

}