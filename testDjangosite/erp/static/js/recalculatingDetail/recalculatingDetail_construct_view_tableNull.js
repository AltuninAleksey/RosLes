function setDataInTableNull() {
    var table_0 = document.getElementById("table_0");
    var newHtml = "";

    for(var i = 0; i < APP.dataTable_0.length; i++) {

        var name_breed = CommonFunction.getBreedsName(APP.breeds, APP.dataTable_0[i].id_breed);

        newHtml = newHtml + `<tr>
            <td class="podles_td_1">${name_breed}</td>
            <td class="podles_td_2">${APP.dataTable_0[i].count_of_plants}</td>
            <td class="podles_td_3">${APP.dataTable_0[i].avg_height}</td>
        </tr>`;
    }
    table_0.innerHTML = newHtml;
}