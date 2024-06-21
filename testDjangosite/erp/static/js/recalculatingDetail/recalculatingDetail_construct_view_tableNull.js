function setDataInTableNull() {
    var table_0 = document.getElementById("table_0");
    var newHtml = "";

    for(var i = 0; i < APP.dataTable_0.length; i++) {

        var name_undergrowth = CommonFunction.getUndergrowthsName(APP.undergrowth, APP.dataTable_0[i].id_undergrowth);

        if(APP.dataTable_0[i].id != "") {
            newHtml = newHtml + `<tr>
                <td class="podles_td_1">${name_undergrowth}</td>
                <td class="podles_td_2">${APP.dataTable_0[i].count_of_plants}</td>
                <td class="podles_td_3">${APP.dataTable_0[i].avg_height}</td>` +
                "<td style=\"width: 1%;\">" +
                    "<svg onclick=\"deleteLineInTableNull(" + APP.dataTable_0[i].id + ")\" class=\"cursorPointer\" width=\"23px\" height=\"23px\" viewBox=\"0 0 24 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">" +
                        "<g id=\"SVGRepo_bgCarrier\" stroke-width=\"0\"></g> " +
                        "<g id=\"SVGRepo_tracerCarrier\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></g>" +
                        "<g id=\"SVGRepo_iconCarrier\">" +
                            "<path d=\"M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17\" stroke=\"#000000\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path> " +
                        "</g>" +
                    "</svg>" +
                "</td>" +
            `</tr>`;
        } else {
            newHtml = newHtml + `<tr>
                <td class="podles_td_1">${name_undergrowth}</td>
                <td class="podles_td_2">${APP.dataTable_0[i].count_of_plants}</td>
                <td class="podles_td_3">${APP.dataTable_0[i].avg_height}</td>` +
                "<td style=\"width: 1%;\">" +
                    "<svg onclick=\"deleteNewLineInTableNull(" + i + ")\" class=\"cursorPointer\" width=\"23px\" height=\"23px\" viewBox=\"0 0 24 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">" +
                        "<g id=\"SVGRepo_bgCarrier\" stroke-width=\"0\"></g> " +
                        "<g id=\"SVGRepo_tracerCarrier\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></g>" +
                        "<g id=\"SVGRepo_iconCarrier\">" +
                            "<path d=\"M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17\" stroke=\"#000000\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path> " +
                        "</g>" +
                    "</svg>" +
                "</td>" +
            `</tr>`;
        }

    }
    table_0.innerHTML = newHtml;
}

function addUndefground() {
    var breedName_undeground = document.getElementById("breedName-undeground");
    var count_add = document.getElementById("count-add");
    var avg_h_add = document.getElementById("avg-h-add");

    var newUndefground = {
        id: "",
        id_sample : APP.documentData.id,
        avg_height: avg_h_add.value,
        count_of_plants: count_add.value,
        id_breed: null,
        id_type_of_reproduction : Number(APP.active_type_reproduction),
        id_undergrowth : breedName_undeground.value
    }

    count_add.value = "";
    avg_h_add.value = "";

    APP.dataTable_0.push(newUndefground);
    setDataInTableNull();
    closeAddForm("form-add-undeground");
}

function deleteNewLineInTableNull(del_index) {

    var dataTable_0 = APP.dataTable_0;
    APP.dataTable_0 = [];

    for(var i = 0; i < dataTable_0.length; i++) {
        if(i != del_index) {
            APP.dataTable_0.push(dataTable_0[i]);
        }
    }

    setDataInTableNull();
}

function deleteLineInTableNull(del_id) {
    APP.deleteIdTableNull.push(del_id);

    var dataTable_0 = APP.dataTable_0;
    APP.dataTable_0 = [];

    for(var i = 0; i < dataTable_0.length; i++) {
        if(dataTable_0[i].id != del_id) {
            APP.dataTable_0.push(dataTable_0[i]);
        }
    }

    setDataInTableNull();
}