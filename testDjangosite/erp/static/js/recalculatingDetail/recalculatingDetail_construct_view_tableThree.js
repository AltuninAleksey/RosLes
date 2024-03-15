function setDataInTableThree() {
    var table_3 = document.getElementById("table_3");
    var newHtml = "";

    for(var i = 0; i < APP.dataTable_3.length; i++) {
        if(APP.dataTable_3[i].flag_center == 1) {
            newHtml = newHtml + "<tr>" +
                                    "<td class=\"gps-point-td1\">" + (i+1) + "</td>" +
                                    "<td class=\"gps-point-td2\">" + APP.dataTable_3[i].latitude + "</td>" +
                                    "<td class=\"gps-point-td2\">" + APP.dataTable_3[i].longitude + "</td>" +
                                    "<td class=\"gps-point-td2\"><input onchange=\"changeGps(" + i + ")\" type=\"checkbox\" checked></td>" +
                                "</tr>";
        } else {
            newHtml = newHtml + "<tr>" +
                                    "<td class=\"gps-point-td1\">" + (i+1) + "</td>" +
                                    "<td class=\"gps-point-td2\">" + APP.dataTable_3[i].latitude + "</td>" +
                                    "<td class=\"gps-point-td2\">" + APP.dataTable_3[i].longitude + "</td>" +
                                    "<td class=\"gps-point-td2\"><input onchange=\"changeGps(" + i + ")\" type=\"checkbox\"></td>" +
                                "</tr>";
        }
    }
    table_3.innerHTML = newHtml;
}

function changeGps(index) {
    if(APP.dataTable_3[index].flag_center == 1) {
        APP.dataTable_3[index].flag_center = 0;
    } else {
        APP.dataTable_3[index].flag_center = 1;
    }
}

function addGps() {
    var latitudeAdd = document.getElementById("latitude-add");
    var longitudeAdd = document.getElementById("longitude-add");

    var strLatitudeAdd = latitudeAdd.value.toString();
    var strLongitudeAdd = longitudeAdd.value.toString();

    if(isNaN(Number(latitudeAdd.value)) || isNaN(Number(latitudeAdd.value))
        || strLatitudeAdd[strLatitudeAdd.length - 6] != '.' || strLongitudeAdd[strLongitudeAdd.length - 6] != '.') {

        alert("Неправильный формат координат. Необходимый формат: dd.xxxxx");

        return;
    }

    var newGps = {
        id_sample : APP.documentData.id,
        latitude : latitudeAdd.value,
        longitude : longitudeAdd.value,
        flag_center : 0
    }

    latitudeAdd.value = "";
    longitudeAdd.value = "";

    APP.dataTable_3.push(newGps);
    setDataInTableThree();
    closeAddForm("form-add-gps");
}