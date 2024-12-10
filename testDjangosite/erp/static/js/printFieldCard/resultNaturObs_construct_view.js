async function setDataInResultNaturObs() {

    APP.documentData.statementRecalculationsData = await PrintFieldCardBusiness.getStatementRecalculationsDetailDataById(APP.documentData.id_list_region);
    APP.documentData.sampleListByIdListRegion = await PrintFieldCardBusiness.getSampleByIdListRegion(APP.documentData.id_list_region);

//    if(APP.documentData.statementRecalculationsData.sample_region != APP.documentData.square_one_sample_area) {
//        APP.documentData.square_one_sample_area = APP.documentData.statementRecalculationsData.sample_region;
//    }

//    if(APP.documentData.sampleListByIdListRegion.length != APP.documentData.count_sample_area) {
//        APP.documentData.count_sample_area = APP.documentData.sampleListByIdListRegion.length;
//    }

    document.getElementById("square_one_sample_area").value = APP.documentData.square_one_sample_area;
    document.getElementById("count_sample_area").value = APP.documentData.count_sample_area;

    APP.gpsTable = await PrintFieldCardBusiness.getGpsByListRegionId(APP.documentData.id_list_region);
    let gpsTable = document.getElementById("gpsTable");
    let newHTML = "";

    for(let i = 0; i < APP.gpsTable.length; i++) {
        newHTML += `<tr>
                        <td><input readonly id="id_sample${i}" type="text" style="width: 100%" value="${APP.gpsTable[i].id_sample}"></td>
                        <td><input readonly id="latitude${i}" type="text" style="width: 100%" value=` + "\"" + Number(APP.gpsTable[i].latitude).toFixed(6) + "\"" + `></td>
                        <td><input readonly id="longitude${i}" type="text" style="width: 100%" value=` + "\"" + Number(APP.gpsTable[i].longitude).toFixed(6) + "\"" + `></td>
                        <td style="width: 50px;">
                            <svg onclick="deleteGpsPointInMasterData(${APP.gpsTable[i].id})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                <g id="SVGRepo_iconCarrier">
                                    <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                </g>
                            </svg>
                        </td>
                    </tr>`;
    }

    gpsTable.innerHTML = newHTML;

    APP.countLineGpsPoint = APP.gpsTable.length;

}

function addNewLineInGpsPoint() {

    let gpsTable = document.getElementById("gpsTable");
    newHtml = "";

    for(var i = 0; i < APP.countLineGpsPoint; i++) {

        var id_sample = document.getElementById("id_sample"+i).value;
        var latitude = document.getElementById("latitude"+i).value;
        var longitude = document.getElementById("longitude"+i).value;

        if(i < APP.gpsTable.length) {
            newHtml += `<tr>
                        <td><input id="id_sample${i}" readonly type="text" style="width: 100%" value="${id_sample}"></td>
                        <td><input id="latitude${i}" readonly type="text" style="width: 100%" value="${latitude}"></td>
                        <td><input id="longitude${i}" readonly type="text" style="width: 100%" value="${longitude}"></td>
                        <td style="width: 50px;">
                            <svg onclick="deleteGpsPointInMasterData(${APP.gpsTable[i].id})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                <g id="SVGRepo_iconCarrier">
                                    <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                </g>
                            </svg>
                        </td>
                    </tr>`;
        } else {
            newHtml += `<tr>
                        <td><input id="id_sample${i}" readonly type="text" style="width: 100%" value="${id_sample}"></td>
                        <td><input id="latitude${i}" type="text" style="width: 100%" value="${latitude}"></td>
                        <td><input id="longitude${i}" type="text" style="width: 100%" value="${longitude}"></td>
                        <td style="width: 50px;">
                            <svg onclick="deleteNewLineInGpsPoint(${i})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                <g id="SVGRepo_iconCarrier">
                                    <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                </g>
                            </svg>
                        </td>
                    </tr>`;
        }

    }


    newHtml += `<tr>
                    <td><input id="id_sample${APP.countLineGpsPoint}" readonly type="text" style="width: 100%" value=""></td>
                    <td><input id="latitude${APP.countLineGpsPoint}" type="text" style="width: 100%" value=""></td>
                    <td><input id="longitude${APP.countLineGpsPoint}" type="text" style="width: 100%" value=""></td>
                    <td style="width: 50px;">
                            <svg onclick="deleteNewLineInGpsPoint(${APP.countLineGpsPoint})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                <g id="SVGRepo_iconCarrier">
                                    <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                </g>
                            </svg>
                        </td>
                </tr>`;

    gpsTable.innerHTML = newHtml;
    APP.countLineGpsPoint += 1;
}

async function deleteGpsPointInMasterData(del_id) {

    let gpsTable = document.getElementById("gpsTable");
    newHtml = "";

    var index = 0;
    for(var i = 0; i < APP.countLineGpsPoint; i++) {

        var id_sample = document.getElementById("id_sample"+i).value;
        var latitude = document.getElementById("latitude"+i).value;
        var longitude = document.getElementById("longitude"+i).value;

        if(i < APP.gpsTable.length) {

            if(del_id == APP.gpsTable[i].id) {
                continue;
            }

            newHtml += `<tr>
                        <td><input id="id_sample${index}" readonly type="text" style="width: 100%" value="${id_sample}"></td>
                        <td><input id="latitude${index}" readonly type="text" style="width: 100%" value="${latitude}"></td>
                        <td><input id="longitude${index}" readonly type="text" style="width: 100%" value="${longitude}"></td>
                        <td style="width: 50px;">
                            <svg onclick="deleteGpsPointInMasterData(${APP.gpsTable[i].id})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                <g id="SVGRepo_iconCarrier">
                                    <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                </g>
                            </svg>
                        </td>
                    </tr>`;
        } else {
            newHtml += `<tr>
                        <td><input id="id_sample${index}" readonly type="text" style="width: 100%" value="${id_sample}"></td>
                        <td><input id="latitude${index}" type="text" style="width: 100%" value="${latitude}"></td>
                        <td><input id="longitude${index}" type="text" style="width: 100%" value="${longitude}"></td>
                        <td style="width: 50px;">
                            <svg onclick="deleteNewLineInGpsPoint(${index})"class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                <g id="SVGRepo_iconCarrier">
                                    <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                </g>
                            </svg>
                        </td>
                    </tr>`;
        }
        index += 1;
    }


    gpsTable.innerHTML = newHtml;
    APP.countLineGpsPoint -= 1;

    APP.delIdGpsPoint.push(del_id);

}

function deleteNewLineInGpsPoint(del_index) {

    let gpsTable = document.getElementById("gpsTable");
    newHtml = "";

    var index = 0;
    for(var i = 0; i < APP.countLineGpsPoint; i++) {

        if(i == del_index) {
            continue;
        }

        var id_sample = document.getElementById("id_sample"+i).value;
        var latitude = document.getElementById("latitude"+i).value;
        var longitude = document.getElementById("longitude"+i).value;

        if(i < APP.gpsTable.length) {
            newHtml += `<tr>
                        <td><input id="id_sample${index}" readonly type="text" style="width: 100%" value="${id_sample}"></td>
                        <td><input id="latitude${index}" readonly type="text" style="width: 100%" value="${latitude}"></td>
                        <td><input id="longitude${index}" readonly type="text" style="width: 100%" value="${longitude}"></td>
                        <td style="width: 50px;">
                            <svg onclick="deleteGpsPointInMasterData(${APP.gpsTable[i].id})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                <g id="SVGRepo_iconCarrier">
                                    <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                </g>
                            </svg>
                        </td>
                    </tr>`;
        } else {
            newHtml += `<tr>
                        <td><input id="id_sample${index}" readonly type="text" style="width: 100%" value="${id_sample}"></td>
                        <td><input id="latitude${index}" type="text" style="width: 100%" value="${latitude}"></td>
                        <td><input id="longitude${index}" type="text" style="width: 100%" value="${longitude}"></td>
                        <td style="width: 50px;">
                            <svg onclick="deleteNewLineInGpsPoint(${index})"class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                <g id="SVGRepo_iconCarrier">
                                    <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                </g>
                            </svg>
                        </td>
                    </tr>`;
        }
        index += 1;
    }


    gpsTable.innerHTML = newHtml;
    APP.countLineGpsPoint -= 1;
}