async function setDataInResultNaturObs() {

    document.getElementById("square_one_sample_area").value = "";
    document.getElementById("count_sample_area").value = "";

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