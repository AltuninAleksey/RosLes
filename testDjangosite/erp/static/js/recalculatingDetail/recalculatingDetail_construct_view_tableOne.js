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
                APP.dataTable_1[i].age = APP.dataTable_1[i].age == null? 0:APP.dataTable_1[i].age;

                if(APP.dataTable_1[i].id != "") {
                    newHtml = newHtml + "<tr>" +
                                        "<td class=\"conpodles1_td_1\">" + name_breed + "</td> " +
                                        "<td class=\"conpodles1_td_2\">" + htmlMain + "</td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'to0_2'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].to0_2 + "\"></td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'from0_21To0_5'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].from0_21To0_5 + "\"></td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'from0_6To1_0'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].from0_6To1_0 + "\"></td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'from1_1to1_5'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].from1_1to1_5 + "\"></td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'from1_5'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].from1_5 + "\"></td> " +
                                        "<td class=\"conpodles1_td_4\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'max_height'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].max_height + "\"></td> " +
                                        "<td class=\"conpodles1_td_4\"><input id = \"avg_height_" + i +"\" class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'avg_height'" + ", this.value);\" type=\"text\" value=\"" +  Number(APP.dataTable_1[i].avg_height).toFixed(1) + "\"></td> " +
                                        "<td class=\"conpodles1_td_4\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'age'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].age + "\"></td> " +
                                        "<td class=\"conpodles1_td_5\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'avg_diameter'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].avg_diameter + "\"></td> " +
                                        "<td class=\"conpodles1_td_5\"><input id = \"conpodles1_td_5_" + i +"\" class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'count_of_plants'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].count_of_plants  + "\"></td>" +
                                        "<td style=\"width: 1%;\">" +
                                            "<svg onclick=\"deleteLineInTableOne(" + APP.dataTable_1[i].id + ")\" class=\"cursorPointer\" width=\"23px\" height=\"23px\" viewBox=\"0 0 24 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">" +
                                                "<g id=\"SVGRepo_bgCarrier\" stroke-width=\"0\"></g> " +
                                                "<g id=\"SVGRepo_tracerCarrier\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></g>" +
                                                "<g id=\"SVGRepo_iconCarrier\">" +
                                                    "<path d=\"M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17\" stroke=\"#000000\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path> " +
                                                "</g>" +
                                            "</svg>" +
                                        "</td>" +
                                    "</tr>";
                } else {
                    newHtml = newHtml + "<tr>" +
                                        "<td class=\"conpodles1_td_1\">" + name_breed + "</td> " +
                                        "<td class=\"conpodles1_td_2\">" + htmlMain + "</td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'to0_2'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].to0_2 + "\"></td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'from0_21To0_5'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].from0_21To0_5 + "\"></td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'from0_6To1_0'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].from0_6To1_0 + "\"></td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'from1_1to1_5'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].from1_1to1_5 + "\"></td> " +
                                        "<td class=\"conpodles1_td_3\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'from1_5'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].from1_5 + "\"></td> " +
                                        "<td class=\"conpodles1_td_4\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'max_height'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].max_height + "\"></td> " +
                                        "<td class=\"conpodles1_td_4\"><input id = \"avg_height_" + i +"\" class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'avg_height'" + ", this.value);\" type=\"text\" value=\"" + Number(APP.dataTable_1[i].avg_height).toFixed(1) + "\"></td> " +
                                        "<td class=\"conpodles1_td_4\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'age'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].age + "\"></td> " +
                                        "<td class=\"conpodles1_td_5\"><input class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'avg_diameter'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].avg_diameter + "\"></td> " +
                                        "<td class=\"conpodles1_td_5\"><input id = \"conpodles1_td_5_" + i +"\" class=\"recalculation_input\" onChange=\"updateRecalculating(" + i + ", " + "'count_of_plants'" + ", this.value);\" type=\"text\" value=\"" + APP.dataTable_1[i].count_of_plants  + "\"></td>" +
                                        "<td style=\"width: 1%;\">" +
                                            "<svg onclick=\"deleteNewLineInTableOne(" + i + ")\" class=\"cursorPointer\" width=\"23px\" height=\"23px\" viewBox=\"0 0 24 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">" +
                                                "<g id=\"SVGRepo_bgCarrier\" stroke-width=\"0\"></g> " +
                                                "<g id=\"SVGRepo_tracerCarrier\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></g>" +
                                                "<g id=\"SVGRepo_iconCarrier\">" +
                                                    "<path d=\"M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17\" stroke=\"#000000\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path> " +
                                                "</g>" +
                                            "</svg>" +
                                        "</td>" +
                                    "</tr>";
                }

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

    if(element == "to0_2" ||
        element == "from0_21To0_5" ||
        element == "from0_6To1_0" ||
        element == "from1_1to1_5" ||
        element == "from1_5" ||
        element == "max_height") {


        APP.dataTable_1[index]["avg_height"]=((APP.dataTable_1[index]["to0_2"]*0.1+
                                                APP.dataTable_1[index]["from0_21To0_5"]*0.35+
                                                APP.dataTable_1[index]["from0_6To1_0"]*0.8+
                                                APP.dataTable_1[index]["from1_1to1_5"]*1.3+
                                                ((APP.dataTable_1[index]["max_height"]+1.51)/2*APP.dataTable_1[index]["from1_5"]))/
                                                APP.dataTable_1[index]["count_of_plants"]);

        APP.dataTable_1[index]["avg_height"] = APP.dataTable_1[index]["avg_height"].toFixed(3);

        document.getElementById("avg_height_"+index).value = APP.dataTable_1[index]["avg_height"];
    }
}

function addProba() {
    var breedName_proba = document.getElementById("breedName-proba");
    var proba_021_05 = document.getElementById("proba-0.21-0.5");
    var proba_11_15 = document.getElementById("proba-1.1-1.5");
    var proba_02 = document.getElementById("proba-0.2");
    var proba_06_10 = document.getElementById("proba-0.6-1.0");
    var proba_15 = document.getElementById("proba-1.5");
    var proba_maxheig = document.getElementById("proba-maxheig").replaceAll(",", ".");
    var proba_main = document.getElementById("proba-main");
    var proba_avg_d = document.getElementById("proba-avg-d").replaceAll(",", ".");
    var proba_avg_h = document.getElementById("proba-avg-h").replaceAll(",", ".");
    var proba_age = document.getElementById("proba-age");
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
        count_of_plants: Number(proba_all_count),
        age: Number(proba_age.value)
    };

    if(newData.avg_height == 0 || newData.avg_height == null || newData.avg_height == undefined) {
        newData.avg_height = ((newData.to0_2 *0.1+
                                                newData.from0_21To0_5 *0.35+
                                                newData.from0_6To1_0 *0.8+
                                                newData.from1_1to1_5 *1.3+
                                                ((newData.max_height +1.51)/2*newData.from1_5 ))/
                                                newData.count_of_plants);
    }

    APP.dataTable_1.push(newData);
    setDataInTableOne(APP.active_type_reproduction);
    closeAddForm("form-add-proba");
}

function deleteNewLineInTableOne(del_index) {

    var dataTable_1 = APP.dataTable_1;
    APP.dataTable_1 = [];

    for(var i = 0; i < dataTable_1.length; i++) {
        if(i != del_index) {
            APP.dataTable_1.push(dataTable_1[i]);
        }
    }

    setDataInTableOne(APP.active_type_reproduction);
}

function deleteLineInTableOne(del_id) {

    APP.deleteIdTableOne.push(del_id);

    var dataTable_1 = APP.dataTable_1;
    APP.dataTable_1 = [];

    for(var i = 0; i < dataTable_1.length; i++) {
        if(dataTable_1[i].id != del_id) {
            APP.dataTable_1.push(dataTable_1[i]);
        }
    }

    setDataInTableOne(APP.active_type_reproduction);
}