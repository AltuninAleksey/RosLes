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
                var main;

                if(APP.dataTable_1[i].main == 0) {
                    main = "Нет";
                } else {
                    main = "Да";
                }

                newHtml = newHtml + "<tr>" +
                                        "<td class=\"conpodles1_td_1\">" + name_breed + "</td> " +
                                        "<td class=\"conpodles1_td_2\">" + main + "</td> " +
                                        "<td class=\"conpodles1_td_3\">" + APP.dataTable_1[i].to0_2 + "</td> " +
                                        "<td class=\"conpodles1_td_3\">" + APP.dataTable_1[i].from0_21To0_5 + "</td> " +
                                        "<td class=\"conpodles1_td_3\">" + APP.dataTable_1[i].from0_6To1_0 + "</td> " +
                                        "<td class=\"conpodles1_td_3\">" + APP.dataTable_1[i].from1_1to1_5 + "</td> " +
                                        "<td class=\"conpodles1_td_3\">" + APP.dataTable_1[i].from1_5 + "</td> " +
                                        "<td class=\"conpodles1_td_4\">" + APP.dataTable_1[i].max_height + "</td> " +
                                        "<td class=\"conpodles1_td_4\">" + APP.dataTable_1[i].avg_height  + "</td> " +
                                        "<td class=\"conpodles1_td_5\">" + APP.dataTable_1[i].avg_diameter  + "</td> " +
                                        "<td class=\"conpodles1_td_5\">" + APP.dataTable_1[i].count_of_plants  + "</td>" +
                                    "</tr>";
            }
    }

    table_1.innerHTML = newHtml;
}