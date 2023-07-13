async function setDataInCharacteristicMolodniac() {

    document.getElementById("breed_composition").value = APP.documentData.breed_composition;

    let economy_sapling = document.getElementById("economy_sapling");
    newHtml = "";

    for(var i = 0; i < APP.allEconomy.length; i++) {
        if(APP.allEconomy[i].id == APP.documentData.id_economy_sapling) {
            newHtml = newHtml + "<option selected value=\"" + APP.allEconomy[i].id + "\">" + APP.allEconomy[i].name_economy + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.allEconomy[i].id + "\">" + APP.allEconomy[i].name_economy + "</option>";
        }
    }
    economy_sapling.innerHTML = newHtml;


    document.getElementById("completeness2").value = APP.documentData.completeness;
    document.getElementById("stock2").value = APP.documentData.stock;


    APP.point7Table2Sapling = await PrintFieldCardBusiness.getPoint7Table2SaplingById(APP.documentData.id_list_region);
    let point7Table2Sapling = document.getElementById("id_point7_table2_sapling");
    newHtml = "";

    for(var i = 0; i < APP.point7Table2Sapling.length; i++) {

        APP.point7Table2Sapling[i].ratio_composition = (APP.point7Table2Sapling[i].ratio_composition == null? 0:APP.point7Table2Sapling[i].ratio_composition);
        APP.point7Table2Sapling[i].age = (APP.point7Table2Sapling[i].age == null? 0:APP.point7Table2Sapling[i].age);
        APP.point7Table2Sapling[i].avg_height = (APP.point7Table2Sapling[i].avg_height == null? 0:APP.point7Table2Sapling[i].avg_height);
        APP.point7Table2Sapling[i].avg_diameter = (APP.point7Table2Sapling[i].avg_diameter == null? 0:APP.point7Table2Sapling[i].avg_diameter);
        APP.point7Table2Sapling[i].count_of_plants = (APP.point7Table2Sapling[i].count_of_plants == null? 0:APP.point7Table2Sapling[i].count_of_plants);

        newHtml += `<tr>
                        <td><input readonly type="text" name="ratio_composition_molodniac${i}" id="ratio_composition_molodniac${i}" style="width: 100%" value="${APP.point7Table2Sapling[i].ratio_composition}"></td>
                        <td><input readonly type="text" name="id_breed_molodniac${i}" id="id_breed_molodniac${i}" style="width: 100%" value="${CommonFunction.getBreedsName(APP.breeds, APP.point7Table2Sapling[i].id_breed)}"></td>
                        <td><input readonly type="text" name="age_molodniac${i}" id="age_molodniac${i}" style="width: 100%" value="${APP.point7Table2Sapling[i].age}"></td>
                        <td><input readonly type="text" name="avg_height_molodniac${i}" id="avg_height_molodniac${i}" style="width: 100%" value="${APP.point7Table2Sapling[i].avg_height}"></td>
                        <td><input readonly type="text" name="avg_diameter_molodniac${i}" id="avg_diameter_molodniac${i}" style="width: 100%" value="${APP.point7Table2Sapling[i].avg_diameter}"></td>
                        <td><input readonly type="text" name="count_of_plants_molodniac${i}" id="count_of_plants_molodniac${i}" style="width: 100%" value="${APP.point7Table2Sapling[i].count_of_plants}"></td>
                    </tr>`;
    }
    point7Table2Sapling.innerHTML = newHtml;

}