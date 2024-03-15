function setDataInTableOne(switchButton) {

    var switchButton1 = document.getElementById("switchButton1");
    var switchButton2 = document.getElementById("switchButton2");
    var switchButton3 = document.getElementById("switchButton3");

    if(switchButton == 1) {
        switchButton1.classList.add("border-bottom-color-active");
        switchButton2.classList.remove("border-bottom-color-active");
        switchButton3.classList.remove("border-bottom-color-active");

        switchButton1.classList.remove("border-bottom-color-black");
        switchButton2.classList.add("border-bottom-color-black");
        switchButton3.classList.add("border-bottom-color-black");

        APP.active_type_reproduction = 1;
    }

    if(switchButton == 2) {
        switchButton1.classList.remove("border-bottom-color-active");
        switchButton2.classList.add("border-bottom-color-active");
        switchButton3.classList.remove("border-bottom-color-active");

        switchButton1.classList.add("border-bottom-color-black");
        switchButton2.classList.remove("border-bottom-color-black");
        switchButton3.classList.add("border-bottom-color-black");

        APP.active_type_reproduction = 2;
    }

    if(switchButton == 3) {
        switchButton1.classList.remove("border-bottom-color-active");
        switchButton2.classList.remove("border-bottom-color-active");
        switchButton3.classList.add("border-bottom-color-active");

        switchButton1.classList.add("border-bottom-color-black");
        switchButton2.classList.add("border-bottom-color-black");
        switchButton3.classList.remove("border-bottom-color-black");

        APP.active_type_reproduction = 3;
    }

    var table_1 = document.getElementById("table_one");
    var newHtml = "";

    for(var i = 0; i < APP.dataTable_1.length; i++) {
            if(Number(APP.dataTable_1[i].id_type_of_reproduction) == switchButton) {

                var name_breed = CommonFunction.getBreedsName(APP.breeds, APP.dataTable_1[i].id_breed);

                var htmlMain = "<select class=\"recalculation_select\" style=\"width: 60%;\" onChange=\"updateRecalculating(" + i + ", " + "'main'" + ", this.value);\">";
                if(APP.dataTable_1[i].main == 0) {
                    htmlMain += "<option selected value=\"0\">Нет</option>";
                    htmlMain += "<option value=\"1\">Да</option>";
                } else {
                    htmlMain += "<option selected value=\"1\">Да</option>";
                    htmlMain += "<option value=\"0\">Нет</option>";
                }
                htmlMain += "</select>";

                APP.dataTable_1[i].to0_2 = APP.dataTable_1[i].to0_2 == null? 0: APP.dataTable_1[i].to0_2;
                APP.dataTable_1[i].from0_6To1_0 = APP.dataTable_1[i].from0_6To1_0 == null? 0: APP.dataTable_1[i].from0_6To1_0;
                APP.dataTable_1[i].from0_21To0_5 = APP.dataTable_1[i].from0_21To0_5 == null? 0: APP.dataTable_1[i].from0_21To0_5;
                APP.dataTable_1[i].from1_1to1_5 = APP.dataTable_1[i].from1_1to1_5 == null? 0: APP.dataTable_1[i].from1_1to1_5;
                APP.dataTable_1[i].from1_5 = APP.dataTable_1[i].from1_5 == null? 0: APP.dataTable_1[i].from1_5;
                APP.dataTable_1[i].max_height = APP.dataTable_1[i].max_height == null? 0: APP.dataTable_1[i].max_height;
                APP.dataTable_1[i].avg_height = APP.dataTable_1[i].avg_height == null? 0: APP.dataTable_1[i].avg_height;
                APP.dataTable_1[i].avg_diameter = APP.dataTable_1[i].avg_diameter == null? 0: APP.dataTable_1[i].avg_diameter;
                APP.dataTable_1[i].count_of_plants = APP.dataTable_1[i].count_of_plants == null? 0: APP.dataTable_1[i].count_of_plants;

                newHtml = newHtml + "<tr>" +
                                        "<td class=\"conpodles1_td_1\">" + name_breed + "</td> " +
                                        "<td class=\"conpodles1_td_2\">" + htmlMain + "</td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'to0_2'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].to0_2 + "\"></td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'from0_21To0_5'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].from0_21To0_5 + "\"></td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'from0_6To1_0'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].from0_6To1_0 + "\"></td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'from1_1to1_5'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].from1_1to1_5 + "\"></td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'from1_5'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].from1_5 + "\"></td> " +
                                        "<td class=\"conpodles1_td_4\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'max_height'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].max_height + "\"></td> " +
                                        "<td class=\"conpodles1_td_4\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'avg_height'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].avg_height + "\"></td> " +
                                        "<td class=\"conpodles1_td_5\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'avg_diameter'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].avg_diameter + "\"></td> " +
                                        "<td class=\"conpodles1_td_5\"><input id = \"conpodles1_td_5_" + i +"\" class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'count_of_plants'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].count_of_plants  + "\"></td>" +
                                    "</tr>";
            }
    }

    table_1.innerHTML = newHtml;
}

function updateRecalculating(index, element, val) {
    APP.dataTable_1[index][element] = Number(val);

    if(element == "to0_2" ||
        element == "from0_21To0_5" ||
        element == "from0_6To1_0" ||
        element == "from1_1to1_5" ||
        element == "from1_5") {

        APP.dataTable_1[index]["count_of_plants"] = APP.dataTable_1[index]["to0_2"] +
            APP.dataTable_1[index]["from0_21To0_5"] + APP.dataTable_1[index]["from0_6To1_0"] +
            APP.dataTable_1[index]["from1_1to1_5"] + APP.dataTable_1[index]["from1_5"];

        document.getElementById("conpodles1_td_5_"+index).value = APP.dataTable_1[index]["count_of_plants"];
     }
}

function addProba() {
    var breedName_proba = document.getElementById("breedName-proba");
    var proba_021_05 = document.getElementById("proba-0.21-0.5");
    var proba_11_15 = document.getElementById("proba-1.1-1.5");
    var proba_02 = document.getElementById("proba-0.2");
    var proba_06_10 = document.getElementById("proba-0.6-1.0");
    var proba_15 = document.getElementById("proba-1.5");
    var proba_maxheig = document.getElementById("proba-maxheig");
    var proba_main = document.getElementById("proba-main");
    var proba_avg_d = document.getElementById("proba-avg-d");
    var proba_avg_h = document.getElementById("proba-avg-h");
    var proba_all_count =  Number(proba_021_05.value) + Number(proba_11_15.value) + Number(proba_02.value) + Number(proba_06_10.value) + Number(proba_15.value); //document.getElementById("proba-all-count");


    var newData = {
        id : "",
        id_breed : breedName_proba.value,
        id_sample : APP.documentData.id,
        id_type_of_reproduction : Number(APP.active_type_reproduction),
        to0_2 : Number(proba_02.value),
        from0_21To0_5 : Number(proba_021_05.value),
        from0_6To1_0 : Number(proba_06_10.value),
        from1_1to1_5 : Number(proba_11_15.value),
        from1_5 : Number(proba_15.value),
        max_height : Number(proba_maxheig.value),
        main: proba_main.checked? 1: 0,
        avg_diameter: Number(proba_avg_d.value),
        avg_height: Number(proba_avg_h.value),
        count_of_plants: Number(proba_all_count)
    };

    APP.dataTable_1.push(newData);
    setDataInTableOne(APP.active_type_reproduction);
    closeAddForm("form-add-proba");
}