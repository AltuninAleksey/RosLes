async function setDataInResultNaturObs() {

    document.getElementById("square_one_sample_area").value = "";
    document.getElementById("count_sample_area").value = "";

    APP.countLineGpsPoint = 0;
    APP.gpsTable = [];
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

function addNewLineInGpsPoint() {

    let gpsTable = document.getElementById("gpsTable");
    newHtml = "";

    for(var i = 0; i < APP.countLineGpsPoint; i++) {

        var id_sample = document.getElementById("id_sample"+i).value;
        var latitude = document.getElementById("latitude"+i).value;
        var longitude = document.getElementById("longitude"+i).value;

        newHtml += `<tr>
                        <td><input id="id_sample${i}" readonly type="text" style="width: 100%" value="${id_sample}"></td>
                        <td><input id="latitude${i}" type="text" style="width: 100%" value="${latitude}"></td>
                        <td><input id="longitude${i}" type="text" style="width: 100%" value="${longitude}"></td>
                    </tr>`;
    }


    newHtml += `<tr>
                    <td><input id="id_sample${APP.countLineGpsPoint}" readonly type="text" style="width: 100%" value=""></td>
                    <td><input id="latitude${APP.countLineGpsPoint}" type="text" style="width: 100%" value=""></td>
                    <td><input id="longitude${APP.countLineGpsPoint}" type="text" style="width: 100%" value=""></td>
                </tr>`;

    gpsTable.innerHTML = newHtml;
    APP.countLineGpsPoint += 1;
}