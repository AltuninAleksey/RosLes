buildDescriptionLandTbody();

async function buildDescriptionLandTbody() {
    var dataResponse = await DescriptionListLandBusiness.getAllDescriptionLandList();
    var data = [];
    for(var i = 0; i < dataResponse.length; i++) {
        for(var j = 0; j < dataResponse[i].length; j++) {
            data.push(dataResponse[i][j]);
        }
    }

    //var allForestData = await CommonBusiness.getAllForest();

    APP.userData = await CommonBusiness.getUserData();

    //APP.subjectrf = await CommonBusiness.getAllSubjectrf();
    //APP.forestly =  await CommonBusiness.getForestlyByIdSubjectrf(Number(APP.userData.id_subject_rf)); //allForestData.forestly;

    var czl = await CommonBusiness.getCZL();
    APP.subjectrf = [];
    var arrayIdSubject = [];

    arrayIdSubject.push({
        "id": czl.id_main_subject
    })
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

        arrayIdSubject.push({
            "id": Number(czl.slave_subject[i].id_subject)
        })

        APP.subjectrf.push(item_subject);
    }

    var dataForForestlyByArrayIdSubjectrf = {
        "data": arrayIdSubject
    };

    if(APP.userData.id_subject_rf == '183' || APP.userData.id_subject_rf == '27') {
        dataForForestlyByArrayIdSubjectrf = {
            "data": [{
                "id": 183
            },
            {
                "id": 27
            }]
        };
    }

    var buffer_forestly = await CommonBusiness.getForestlyByArrayIdSubjectrf(dataForForestlyByArrayIdSubjectrf);
    APP.forestly = [];

    for(var i = 0; i < buffer_forestly.length; i++) {
        for(var j = 0; j < buffer_forestly[i].forestly_data.length; j++) {
            APP.forestly.push(buffer_forestly[i].forestly_data[j]);
        }
    }


    APP.district_forestly = [];
    var arrayIdForestly = [];
    for(var i = 0; i < APP.forestly.length; i++) {
        arrayIdForestly.push({
            "id": APP.forestly[String(i)].id
        });
    }

    var dataForDistrictForestlyByArrayIdForestly = {
        "data": arrayIdForestly
    };
    var buff_district_forestly = await CommonBusiness.getDistrictForestlyByArrayIdForestly(dataForDistrictForestlyByArrayIdForestly);
    for(var i = 0; i < buff_district_forestly.length; i++) {
        for(var j = 0; j < buff_district_forestly[i].district_forestly_data.length; j++) {
            APP.district_forestly.push(buff_district_forestly[i].district_forestly_data[j]);
        }
    }


    //APP.quarter = [];
    var arrayIdDistrictForestly = [];
    for(var i = 0; i < APP.district_forestly.length; i++) {
        arrayIdDistrictForestly.push({
            "id": APP.district_forestly[String(i)].id
        });
    }

//    var dataForQuarterByArrayIdDistrictForestly = {
//        "data": arrayIdDistrictForestly
//    };
//    var buff_quarter = await CommonBusiness.getQuarterByArrayIdDistrictForestly(dataForQuarterByArrayIdDistrictForestly);
//    for(var i = 0; i < buff_quarter.length; i++) {
//        for(var j = 0; j < buff_quarter[i].quarter.length; j++) {
//            APP.quarter.push(buff_quarter[i].quarter[j]);
//        }
//    }

    //APP.subjectrf = allForestData.subjectrf;
    //APP.forestly = allForestData.forestly;
    //APP.district_forestly = allForestData.district_forestly;
    //APP.quarter = allForestData.quarter;

    APP.dataTable = data;
    APP.sortOrderTable1 = 0;

    sortByDate();

    //updateDataInDescriptionLandTbody(data);

    setEventForElementsFilter();
    setDataInProfile();
}

function updateDataInDescriptionLandTbody(data) {
    var tableBody = document.getElementById("descriptionLand_tbody");
    var newHtml = "";

    for(var i = 0; i < data.length; i++) {

        data[i].subjectrf = CommonFunction.getSubjectNameByQuarterId(APP.subjectrf, data[i].id_subject_rf);
        data[i].forestly = CommonFunction.getForestlyNameByQuarterId(APP.forestly, data[i].id_forestly);
        data[i].district_forestly = CommonFunction.getDistrictForestlyNameByQuarterId(APP.district_forestly, data[i].id_district_forestly);
        //data[i].quarter = CommonFunction.getQuarterNameByQuarterId(APP.quarter, data[i].id_quarter);

        let strGetDescriptionLandDetail = "getPlotDescription(" + data[i].id + ",0)";

        newHtml = newHtml + `<tr class="cursorPointer" onClick=${strGetDescriptionLandDetail}>
                            <td class="textAlignCenter td1">${data[i].date}</td>
                            <td class="textAlignCenter td8">${data[i].number_region}/${data[i].id}</td>
                            <td class="td2">${data[i].subjectrf}</td>
                            <td class="td3">${data[i].forestly}</td>
                            <td class="td4">${data[i].district_forestly}</td>
                            <td class="td9">${data[i].dacha == null? "" : data[i].dacha}</td>
                            <td class="textAlignCenter td5">${data[i].name_quarter == null? "" : data[i].name_quarter}</td>
                            <td class="textAlignCenter td6">${data[i].soil_lot}</td>
                            <td style="width: 10px; text-align: center;"` +  "onClick='event.stopPropagation();deleteDescriptionLand(" + data[i].id + ");'>" +
                                `<svg width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                    <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                    <g id="SVGRepo_iconCarrier">
                                        <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                    </g>
                                </svg>
                            </td>
                        </tr> \n`;
    }
    tableBody.innerHTML = newHtml;
}

async function deleteDescriptionLand(id) {

    var data = {
        id:id
    }

    await DescriptionListLandBusiness.deleteDescriptionLandById(data);

    getDescriptionListLand();
}

function sortByDate() {
    if(APP.sortOrderTable1 != 1) {
        APP.dataTable.sort(function(a, b) { return a.date > b.date? -1 : 1; });
        APP.sortOrderTable1 = 1;

        document.querySelector("#sortDateForTable1").innerHTML = "&#8593;";
    } else {
        APP.dataTable.sort(function(a, b) { return a.date > b.date? 1 : -1; });
        APP.sortOrderTable1 = -1;

        document.querySelector("#sortDateForTable1").innerHTML = "&#8595;";
    }

    updateDataInDescriptionLandTbody(APP.dataTable);

}

async function setEventForElementsFilter() {

    await setOptionInSubject();

    document
        .querySelector("#checkbox_filter_subject_rf")
        .addEventListener('click', async (e)=>{
            let subjectNode = document.querySelector("#filter_subject_rf");
            if(e.target.checked) {
                subjectNode.removeAttribute("disabled")
                subjectNode.addEventListener('change', async (e)=>{
                    await setOptionInForestly(DescriptionListLandBusiness.TypeData.BYID);
                });
                await setOptionInForestly(DescriptionListLandBusiness.TypeData.BYID);
            } else {
                subjectNode.setAttribute("disabled", "on");
                await setOptionInForestly(DescriptionListLandBusiness.TypeData.ALL);
            }
        });

    await setOptionInForestly(DescriptionListLandBusiness.TypeData.ALL);

    document
        .querySelector("#checkbox_filter_forestly")
        .addEventListener('click', async (e)=>{
            let forestlyNode = document.querySelector("#filter_forestly");
            if(e.target.checked) {
                forestlyNode.removeAttribute("disabled");
                forestlyNode.addEventListener('change', async (e)=>{
                    await setOptionInDistrictForestly(DescriptionListLandBusiness.TypeData.BYID);
                });
                await setOptionInDistrictForestly(DescriptionListLandBusiness.TypeData.BYID);
            } else {
                forestlyNode.setAttribute("disabled", "on");
                await setOptionInDistrictForestly(DescriptionListLandBusiness.TypeData.ALL);
            }
        });

    await setOptionInDistrictForestly(DescriptionListLandBusiness.TypeData.ALL);

    document
        .querySelector("#checkbox_filter_district_forestly")
        .addEventListener('click', async (e)=>{
            let districtForestly = document.querySelector("#filter_district_forestly");
            if(e.target.checked) {
                districtForestly.removeAttribute("disabled");
                districtForestly.addEventListener('change', async (e)=>{
                   // await setOptionInQuarter(DescriptionListLandBusiness.TypeData.BYID);
                });
                //await setOptionInQuarter(DescriptionListLandBusiness.TypeData.BYID);
            } else {
                districtForestly.setAttribute("disabled", "on");
                //await setOptionInQuarter(DescriptionListLandBusiness.TypeData.ALL);
            }
        });

//    await setOptionInQuarter(DescriptionListLandBusiness.TypeData.ALL);
//
//    document
//        .querySelector("#checkbox_filter_quartal")
//        .addEventListener('click', (e)=>{
//            if(e.target.checked) {
//                document
//                    .querySelector("#filter_quartal")
//                    .removeAttribute("disabled");
//            } else {
//                document
//                    .querySelector("#filter_quartal")
//                    .setAttribute("disabled", "on");
//            }
//        });

    document
        .querySelector("#checkbox_filter_date_start")
        .addEventListener('click', (e)=>{
            if(e.target.checked) {
                document
                    .querySelector("#filter_date_start")
                    .removeAttribute("readonly");
            } else {
                document
                    .querySelector("#filter_date_start")
                    .setAttribute("readonly", "on");
            }
        });

    document
        .querySelector("#checkbox_filter_date_end")
        .addEventListener('click', (e)=>{
            if(e.target.checked) {
                document
                    .querySelector("#filter_date_end")
                    .removeAttribute("readonly");
            } else {
                document
                    .querySelector("#filter_date_end")
                    .setAttribute("readonly", "on");
            }
        });

    document
        .querySelector("#checkbox_filter_soil_lot")
        .addEventListener('click', (e)=>{
            if(e.target.checked) {
                document
                    .querySelector("#filter_soil_lot")
                    .removeAttribute("readonly");
            } else {
                document
                    .querySelector("#filter_soil_lot")
                    .setAttribute("readonly", "on");
            }
        });

    document
        .querySelector("#but_filtr")
        .addEventListener('click', async (e)=>{
            await searchByFilter();
        });
}

async function setOptionInSubject() {
    //let subjects = await CommonBusiness.getAllSubjectrf();
    let subjectNode = document.querySelector("#filter_subject_rf");

    let newHtml = "";

    for(var i = 0; i < APP.subjectrf.length; i++) {
        if(i == 0) {
            newHtml = newHtml + "<option selected value=\"" + APP.subjectrf[i].id + "\">" + APP.subjectrf[i].name_subject_RF + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.subjectrf[i].id + "\">" + APP.subjectrf[i].name_subject_RF + "</option>";
        }
    }

    subjectNode.innerHTML = newHtml;

    await setOptionInForestly(DescriptionListLandBusiness.TypeData.BYID);
}

async function setOptionInForestly(status) {
    let forestlyNode = document.querySelector("#filter_forestly");
    let forestly;

    if(status == DescriptionListLandBusiness.TypeData.BYID) {
        let subjectNode = document.querySelector("#filter_subject_rf");
        forestly = await CommonBusiness.getForestlyByIdSubjectrf(subjectNode.value);
    } else {
        forestly = APP.forestly;
    }

    var newHtml = "";

    for(var i = 0; i < forestly.length; i++) {
        if(i == 0) {
            newHtml = newHtml + "<option selected value=\"" + forestly[i].id + "\">" + forestly[i].name_forestly + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + forestly[i].id + "\">" + forestly[i].name_forestly + "</option>";
        }
    }
    forestlyNode.innerHTML = newHtml;

    await setOptionInDistrictForestly(DescriptionListLandBusiness.TypeData.BYID);
}

async function setOptionInDistrictForestly(status) {
    let districtForestlyNode = document.querySelector("#filter_district_forestly");
    let districtForestly = [];

    if(status == DescriptionListLandBusiness.TypeData.BYID) {
        let forestlyNode = document.querySelector("#filter_forestly");

        if(forestlyNode.value != "" && forestlyNode.value != null && forestlyNode.value != undefined) {
            districtForestly = await CommonBusiness.getDistrictForestlyByIdForestly(forestlyNode.value);
        }
    } else {
        districtForestly = APP.district_forestly;
    }

    var newHtml = "";

    for(var i = 0; i < districtForestly.length; i++) {
        if(i == 0) {
            newHtml = newHtml + "<option selected value=\"" + districtForestly[i].id + "\">" + districtForestly[i].name_district_forestly + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + districtForestly[i].id + "\">" + districtForestly[i].name_district_forestly + "</option>";
        }
    }

    districtForestlyNode.innerHTML = newHtml;

    //await setOptionInQuarter(DescriptionListLandBusiness.TypeData.BYID);
}

async function setOptionInQuarter(status) {

    let quarterNode = document.querySelector("#filter_quartal");
    let quarter = [];

    if(status == DescriptionListLandBusiness.TypeData.BYID) {
        let districtForestlyNode = document.querySelector("#filter_district_forestly");

        if(districtForestlyNode.value != "" && districtForestlyNode.value != null && districtForestlyNode.value != undefined) {
            quarter = await CommonBusiness.getQuarterByIdDistrictForestly(districtForestlyNode.value);
        }
    } else {
        quarter = APP.quarter;
    }

    var newHtml = "";
    for(var j = 0; j < quarter.length; j++) {
        if(j == 0) {
            newHtml = newHtml + "<option selected value=\"" + quarter[j].id + "\">" + quarter[j].quarter_name + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + quarter[j].id + "\">" + quarter[j].quarter_name + "</option>";
        }
    }
    quarterNode.innerHTML = newHtml;
}

async function searchByFilter() {

    let checkboxFilterSubjectRFNode = document.querySelector("#checkbox_filter_subject_rf");
    let subjectRFNode = document.querySelector("#filter_subject_rf");
    let checkboxFilterForestlyNode = document.querySelector("#checkbox_filter_forestly");
    let forestlyNode = document.querySelector("#filter_forestly");
    let checkboxFilterDistrictForestlyNode = document.querySelector("#checkbox_filter_district_forestly");
    let districtForestlyNode = document.querySelector("#filter_district_forestly");
    //let checkboxFilterQuartalNode = document.querySelector("#checkbox_filter_quartal");
    //let quartalNode = document.querySelector("#filter_quartal")
    let checkboxFilterDateStartNode = document.querySelector("#checkbox_filter_date_start");
    let dateStartNode = document.querySelector("#filter_date_start");
    let checkboxFilterDateEnd = document.querySelector("#checkbox_filter_date_end");
    let dateEndNode = document.querySelector("#filter_date_end");
    let checkboxFilterSoilLot = document.querySelector("#checkbox_filter_soil_lot");
    let soilLot = document.querySelector("#filter_soil_lot");

    let data;

    if(checkboxFilterSubjectRFNode.checked || checkboxFilterForestlyNode.checked
    || checkboxFilterDistrictForestlyNode.checked //|| checkboxFilterQuartalNode.checked
    || checkboxFilterDateStartNode.checked || checkboxFilterDateEnd.checked
    || checkboxFilterSoilLot.checked) {

        let responseData = {
            bSubjectrf: checkboxFilterSubjectRFNode.checked,
            idSubjectrf: Number(subjectRFNode.value),
            bForestly: checkboxFilterForestlyNode.checked,
            idForestly: forestlyNode.value,
            bDistrictForestly: checkboxFilterDistrictForestlyNode.checked,
            idDistrictForestly: districtForestlyNode.value,
            //bQuarter: checkboxFilterQuartalNode.checked,
            //idQuarter: quartalNode.value,
            bDate: checkboxFilterDateStartNode.checked,
            date: dateStartNode.value,
            bDateSec: checkboxFilterDateEnd.checked,
            dateSec: dateEndNode.value,
            bSoil_lot: checkboxFilterSoilLot.checked,
            soil_lot: soilLot.value
        };


        APP.dataTable = await DescriptionListLandBusiness.getDescriptionLandListByFilter(responseData);
    } else {

        var dataResponse = await DescriptionListLandBusiness.getAllDescriptionLandList();
        var data2 = [];
        for(var i = 0; i < dataResponse.length; i++) {
            for(var j = 0; j < dataResponse[i].length; j++) {
                data2.push(dataResponse[i][j]);
            }
        }
        APP.dataTable = data2;
    }


    updateDataInDescriptionLandTbody(APP.dataTable);
}