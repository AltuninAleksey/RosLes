
buildStatementRecalculationsTbody();

async function buildStatementRecalculationsTbody() {
    var data = await StatementRecalculationsBusiness.getAllStatementList();
    var allForestData = await CommonBusiness.getAllForest();

    APP.subjectrf = allForestData.subjectrf;
    APP.forestly = allForestData.forestly;
    APP.district_forestly = allForestData.district_forestly;
    APP.quarter = allForestData.quarter;

    var tableBody = document.getElementById("statementRecalculations_tbody");
    var newHtml = "";

    for(var i = 0; i < data.length; i++) {

        data[i].subjectrf = CommonFunction.getSubjectNameByQuarterId(APP.subjectrf, data[i].id_subject_rf);
        data[i].forestly = CommonFunction.getForestlyNameByQuarterId(APP.forestly, data[i].id_forestly);
        data[i].district_forestly = CommonFunction.getDistrictForestlyNameByQuarterId(APP.district_forestly, data[i].id_district_forestly);
        data[i].quarter = CommonFunction.getQuarterNameByQuarterId(APP.quarter, data[i].id_quarter);

        newHtml = newHtml + `<tr>
                            <td class="textAlignCenter td1">${data[i].date}</td>
                            <td class="td2">${data[i].subjectrf}</td>
                            <td class="td3">${data[i].forestly}</td>
                            <td class="td4">${data[i].district_forestly}</td>
                            <td class="textAlignCenter td5">${data[i].quarter}</td>
                            <td class="textAlignCenter td6">${data[i].soil_lot}</td>
                            <td class="textAlignCenter td7">${data[i].sample_region}</td>
                        </tr> \n`;
    }
    tableBody.innerHTML = newHtml;

}


{
    bSubjectrf: true,
    idSubjectrf: 1,
    bForestly: false,
    idForestly: 0,
    bDistrictForestly: true,
    idDistrictForestly: 2,
    bQuarter: false,
    idQuarter: 0,
    bDate: false,
    date: ""
}
