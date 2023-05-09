function setDataInTableThree() {
    var table_3 = document.getElementById("table_3");
    var newHtml = "";

    for(var i = 0; i < APP.dataTable_3.length; i++) {
        if(APP.dataTable_3[i].flag_center == 1) {
            newHtml = newHtml + "<tr>" +
                                    "<td class=\"gps-point-td1\">" + (i+1) + "</td>" +
                                    "<td class=\"gps-point-td2\">" + APP.dataTable_3[i].latitude + "</td>" +
                                    "<td class=\"gps-point-td2\">" + APP.dataTable_3[i].longitude + "</td>" +
                                    "<td class=\"gps-point-td2\"><input type=\"checkbox\" checked></td>" +
                                "</tr>";
        } else {
            newHtml = newHtml + "<tr>" +
                                    "<td class=\"gps-point-td1\">" + (i+1) + "</td>" +
                                    "<td class=\"gps-point-td2\">" + APP.dataTable_3[i].latitude + "</td>" +
                                    "<td class=\"gps-point-td2\">" + APP.dataTable_3[i].longitude + "</td>" +
                                    "<td class=\"gps-point-td2\"><input type=\"checkbox\"></td>" +
                                "</tr>";
        }
    }
    table_3.innerHTML = newHtml;
}