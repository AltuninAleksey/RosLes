var subjectrf, forestly, district_forestly, quarter;

var appObject = {
    consolidationListData: [],
    activeGroupConsolidationObject: null
};

getAlllistRegions();

async function getAlllistRegions() {
    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/getalllistregion",
      responseType: 'json'
    });

    var data = requestData.data.data;

    requestData = await axios({
        method: 'get',
        url: urlGlobal + "/allforest",
        responseType: 'json'
    });

    subjectrf = requestData.data.subjectrf;
    forestly = requestData.data.forestly;
    district_forestly = requestData.data.district_forestly;
    quarter = requestData.data.quarter;

    var tableBody = document.getElementById("tbodyRecalculationOnTrialArea");
    var newHtml = "";

    for(var i = 0; i < data.length; i++) {

        for(var j = 0; j < subjectrf.length; j++) {
            if(subjectrf[j].id == data[i].id_subject_rf) {
                data[i].subjectrf = subjectrf[j].name_subject_RF;
            }
        }

        for(var j = 0; j < forestly.length; j++) {
            if(forestly[j].id == data[i].id_forestly) {
                data[i].forestly = forestly[j].name_forestly;
            }
        }

        for(var j = 0; j < district_forestly.length; j++) {
            if(district_forestly[j].id == data[i].id_district_forestly) {
                data[i].district_forestly = district_forestly[j].name_district_forestly;
            }
        }

        data[i].quarter = CommonFunction.getQuarterNameByQuarterId(quarter, data[i].id_quarter);

        newHtml = newHtml +  "<tr>"+
                                    "<td class=\"td1\">" + data[i].date + "</td>" +
                                    "<td class=\"td3\">" + data[i].subjectrf + "</td>" +
                                    "<td class=\"td4\">" + data[i].forestly + "</td>" +
                                    "<td class=\"td5\">" + data[i].district_forestly + "</td>" +
                                    "<td class=\"td6\">" + data[i].quarter + "</td>" +
                                    "<td class=\"td7\">" + data[i].soil_lot + "</td>" +
                                    "<td class=\"td8\">" + data[i].sample_region + "</td>" +
                                "</tr> \n";
    }
    tableBody.innerHTML = newHtml;
}

async function buildConsolidationListForm(typeConsolidationList) {

    if(appObject.activeGroupConsolidationObject == null
        || appObject.activeGroupConsolidationObject == undefined) {

        typeConsolidationList = 1;

        let request = await axios({
            method: 'get',
            url: urlGlobal + "/getallequallistregion",
            responseType: 'json'
        });

        appObject.consolidationListData = request.data.query;

    }

    let tbodyNode = document.getElementById('consolidation-list');
    let newHtml = "";

    if(typeConsolidationList == 1) {
        for(let i = 0; i < appObject.consolidationListData.length; i++) {

            let quarter_name;
            if(appObject.consolidationListData[i].length > 0) {
                quarter_name = CommonFunction.getQuarterNameByQuarterId(quarter, appObject.consolidationListData[i][0].id_quarter_id);
            }

            for(let j = 0; j < appObject.consolidationListData[i].length; j++) {
                newHtml += `        <tr onclick="saveIdObjectForConsolidation(${typeConsolidationList}, ${appObject.consolidationListData[i][j].id}, ${i})">
                                        <td class="td1">${appObject.consolidationListData[i][j].date}</td>
                                        <td class="td3">Брянская область</td>
                                        <td class="td4">Черкасово</td>
                                        <td class="td5">Люберцы</td>
                                        <td class="td6">${quarter_name}</td>
                                        <td class="td7">${appObject.consolidationListData[i][j].soil_lot}</td>
                                    </tr>`;
            }
        }
    } else {
        let quarter_name;
        if(appObject.consolidationListData[appObject.activeGroupConsolidationObject].length > 0) {
            quarter_name = CommonFunction.getQuarterNameByQuarterId(quarter, appObject.consolidationListData[appObject.activeGroupConsolidationObject][0].id_quarter_id)
        }

        for(let j = 0; j < appObject.consolidationListData[appObject.activeGroupConsolidationObject].length; j++) {
            if(appObject.consolidationListData[appObject.activeGroupConsolidationObject][j].id != appObject.idObjectForConsolidation_1) {
                newHtml += `        <tr onclick="saveIdObjectForConsolidation(${typeConsolidationList}, ${appObject.consolidationListData[appObject.activeGroupConsolidationObject][j].id}, ${appObject.activeGroupConsolidationObject})">
                                    <td class="td1">${appObject.consolidationListData[appObject.activeGroupConsolidationObject][j].date}</td>
                                    <td class="td3">Брянская область</td>
                                    <td class="td4">Черкасово</td>
                                    <td class="td5">Люберцы</td>
                                    <td class="td6">${quarter_name}</td>
                                    <td class="td7">${appObject.consolidationListData[appObject.activeGroupConsolidationObject][j].soil_lot}</td>
                                </tr>`;
            }
        }
    }


    tbodyNode.innerHTML = newHtml;
}

function saveIdObjectForConsolidation(typeConsolidationList, idObjectForConsolidation, activeGroupConsolidationObject) {

    let activeObject;

    for(let j = 0; j < appObject.consolidationListData[activeGroupConsolidationObject].length; j++) {
        if(appObject.consolidationListData[activeGroupConsolidationObject][j].id = idObjectForConsolidation) {
            activeObject = appObject.consolidationListData[activeGroupConsolidationObject][j];
            break;
        }
    }

    let quarter_name = CommonFunction.getQuarterNameByQuarterId(quarter, activeObject.id_quarter_id);

    if(typeConsolidationList == 1) {
        appObject.activeGroupConsolidationObject = activeGroupConsolidationObject;
        appObject.idObjectForConsolidation_1 = idObjectForConsolidation;

        let firstObjectNode = document.getElementById("firstDoc");
        firstObjectNode.value = "" + activeObject.date + " Брянская область, Черкасово, Люберцы, " + quarter_name + ", " + activeObject.soil_lot;

    } else {
        appObject.idObjectForConsolidation_2 = idObjectForConsolidation;

        let secondObjectNode = document.getElementById("secondDoc");
        secondObjectNode.value = "" + activeObject.date + " Брянская область, Черкасово, Люберцы, " + quarter_name + ", " + activeObject.soil_lot;
    }

    closeForm('consolidation-list-form');
}

async function openForm(id, typeConsolidationList) {

    if(id != 'consolidation-list-form') {
        var body = document.getElementById("body");
        body.classList.add("overflowHiddenImportant");
    } else {
        await buildConsolidationListForm(typeConsolidationList);
    }

    var formAddProba = document.getElementById(id);
    formAddProba.classList.remove("display-none");
}

function closeForm(id) {
    var formAddProba = document.getElementById(id);
    formAddProba.classList.add("display-none");

    if(id != 'consolidation-list-form') {
        var body = document.getElementById("body");
        body.classList.remove("overflowHiddenImportant");

        if(id == 'consolidation-form') {
            let firstObjectNode = document.getElementById("firstDoc");
            firstObjectNode.value = "";

            let secondObjectNode = document.getElementById("secondDoc");
            secondObjectNode.value = "";

            appObject.activeGroupConsolidationObject = null;
            appObject.consolidationListData = null;
        }

    } else {
    }

}