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

function addUndefground() {
    var breedName_undeground = document.getElementById("breedName-undeground");
    var count_add = document.getElementById("count-add");
    var avg_h_add = document.getElementById("avg-h-add");

    var newUndefground = {
        id_sample : APP.documentData.id,
        avg_height: avg_h_add.value,
        count_of_plants: count_add.value,
        id_breed: breedName_undeground.value,
        id_type_of_reproduction : Number(APP.active_type_reproduction),
        id_undergrowth : 0
    }

    count_add.value = "";
    avg_h_add.value = "";

    APP.dataTable_0.push(newUndefground);
    setDataInTableNull();
    closeAddForm("form-add-undeground");


}