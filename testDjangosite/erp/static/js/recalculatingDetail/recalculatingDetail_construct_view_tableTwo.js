function setDataInTableTwo() {
    var table_2 = document.getElementById("table_2");
    var newHtml = "";

    for(var i = 0; i < APP.dataTable_2.length; i++) {
        newHtml = newHtml + "<tr>" +
                                "<td>" + APP.dataTable_2[i].FIO + "</td>" +
                            "</tr>";
    }
    table_2.innerHTML = newHtml;
}