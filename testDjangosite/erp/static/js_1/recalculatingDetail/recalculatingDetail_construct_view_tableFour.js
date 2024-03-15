function setDataInTableFour() {
    var table_4 = document.getElementById("table_4");
    var newHtml = "";

    for(var i = 0; i < APP.dataTable_4.length; i++) {
            var hrefStr = "" + urlGlobal + "/media/" + APP.dataTable_4[i].photo;
            newHtml = newHtml + `<tr>
                                <td class="photo-td1">${i + 1}</td>
                                <td class="photo-td2">${APP.dataTable_4[i].date}</td>
                                <td class="photo-td3"><a href=${hrefStr}>img_${i + 1}</a></td>
                                </tr>`;
    }
    table_4.innerHTML = newHtml;
}