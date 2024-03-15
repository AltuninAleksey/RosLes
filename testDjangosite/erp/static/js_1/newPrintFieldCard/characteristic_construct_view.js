
async function setDataInCharacteristic() {

    APP.allPurpose = await PrintFieldCardBusiness.getALLPurposeforestData();
    let purpose_of_forests = document.getElementById("purpose_of_forests");
    let newHtml = "";

    for(var i = 0; i < APP.allPurpose.length; i++) {
            newHtml = newHtml + "<option value=\"" + APP.allPurpose[i].id + "\">" + APP.allPurpose[i].name_purpose + "</option>";
    }
    purpose_of_forests.innerHTML = newHtml;


    APP.allForestProtectionCategory = await PrintFieldCardBusiness.getAllForestProtectionCategoryData ();
    let forest_protection_category = document.getElementById("forest_protection_category");
    newHtml = "";

    for(var i = 0; i < APP.allForestProtectionCategory.length; i++) {
            newHtml = newHtml + "<option value=\"" + APP.allForestProtectionCategory[i].id + "\">" + APP.allForestProtectionCategory[i].name_forest_protection_category + "</option>";
    }
    forest_protection_category.innerHTML = newHtml;


    document.getElementById("protected_areas_of_forests").value = "";


    let rent_area = document.getElementById("rent_area");
    newHtml = "";

    if(APP.documentData.rent_area == true) {
        newHtml = newHtml + "<option selected value=\"" + true + "\"> Да </option>";
        newHtml = newHtml + "<option value=\"" + false + "\"> Нет </option>";
    } else {
        newHtml = newHtml + "<option selected value=\"" + false + "\"> Нет </option>";
        newHtml = newHtml + "<option value=\"" + true + "\"> Да </option>";
    }
    rent_area.innerHTML = newHtml;


    APP.allCategoryOfForestFundLands = await PrintFieldCardBusiness.getAllCategoryOfForestFundLands();
    let category_of_forest_fund_lands = document.getElementById("category_of_forest_fund_lands");
    newHtml = "";

    for(var i = 0; i < APP.allCategoryOfForestFundLands.length; i++) {
            newHtml = newHtml + "<option value=\"" + APP.allCategoryOfForestFundLands[i].id + "\">" + APP.allCategoryOfForestFundLands[i].name_category + "</option>";
    }
    category_of_forest_fund_lands.innerHTML = newHtml;


    APP.allMethodOfReforestation = await PrintFieldCardBusiness.getAllMethodOfReforestation();
    let method_of_reforestation = document.getElementById("method_of_reforestation");
    newHtml = "";

    for(var i = 0; i < APP.allMethodOfReforestation.length; i++) {
            newHtml = newHtml + "<option value=\"" + APP.allMethodOfReforestation[i].id + "\">" + APP.allMethodOfReforestation[i].name_of_method + "</option>";
    }
    method_of_reforestation.innerHTML = newHtml;


    document.getElementById("time_of_reforestation").value = "";


    APP.allTypeForestGrowingConditions = await PrintFieldCardBusiness.getAllTypeForestGrowingConditions();
    let type_forest_growing_conditions = document.getElementById("type_forest_growing_conditions");
    newHtml = "";

    for(var i = 0; i < APP.allTypeForestGrowingConditions.length; i++) {
            newHtml = newHtml + "<option value=\"" + APP.allTypeForestGrowingConditions[i].id + "\">" + APP.allTypeForestGrowingConditions[i].type_forest_growing_conditions + "</option>";
    }
    type_forest_growing_conditions.innerHTML = newHtml;


    document.getElementById("forest_type").value = "";
    document.getElementById("point7year").value = "";
    document.getElementById("point7date").value = "";
    document.getElementById("point7number").value = "";
    document.getElementById("point7agreed").value = "";
    document.getElementById("point7_natural_composition").value = "";
    let economy = document.getElementById("economy");
    newHtml = "";

    for(var i = 0; i < APP.allEconomy.length; i++) {
        newHtml = newHtml + "<option value=\"" + APP.allEconomy[i].id + "\">" + APP.allEconomy[i].name_economy + "</option>";
    }
    economy.innerHTML = newHtml;
    document.getElementById("completeness").value = "";
    document.getElementById("stock").value = "";

    APP.point7Table = [];
    APP.countLinePoint7Table = APP.point7Table.length;
    let point7Table = document.getElementById("id_point7_table");
    newHtml = "";

    for(var i = 0; i < APP.point7Table.length; i++) {
        var newHtmlBreeds = "";

        for(var j = 0; j < APP.breeds.length; j++) {
            if(APP.point7Table[i].breed == APP.breeds[j].id) {
                newHtmlBreeds = newHtmlBreeds + "<option selected value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            } else {
                newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            }
        }

        newHtml += `<tr>
                        <input id="idLine${i}" type="hidden" name="id" value="${APP.point7Table[i].id}">
                        <td><input type="text" name="ratio_composition${i}" id="ratio_composition${i}" style="width: 100%" value="${APP.point7Table[i].ratio_composition}"></td>
                        <td><select type="text" name="id_breed${i}" id="id_breed${i}" style="width: 100%">
                        ${newHtmlBreeds}
                        </select> </td>
                        <td><input type="text" name="age${i}" id="age${i}" style="width: 100%" value="${APP.point7Table[i].age}"></td>
                        <td><input type="text" name="avg_height${i}" id="avg_height${i}" style="width: 100%" value="${APP.point7Table[i].avg_height}"></td>
                        <td><input type="text" name="avg_diameter${i}" id="avg_diameter${i}" style="width: 100%" value="${APP.point7Table[i].avg_diameter}"></td>
                        <td><input type="text" name="count_of_plants${i}" id="count_of_plants${i}" style="width: 100%" value="${APP.point7Table[i].count_plants}"></td>
                    </tr>`;
    }
    point7Table.innerHTML = newHtml;

}

function addNewLineInPoint7() {
    let point7Table = document.getElementById("id_point7_table");
    newHtml = "";

    for(var i = 0; i < APP.countLinePoint7Table; i++) {

        var idLine = document.getElementById("idLine"+i).value;
        var ratio_composition = document.getElementById("ratio_composition"+i).value;
        var id_breed = document.getElementById("id_breed"+i).value;
        var age = document.getElementById("age"+i).value;
        var avg_height = document.getElementById("avg_height"+i).value;
        var avg_diameter = document.getElementById("avg_diameter"+i).value;
        var count_of_plants = document.getElementById("count_of_plants"+i).value;

        var newHtmlBreeds = "";
        for(var j = 0; j < APP.breeds.length; j++) {
            if(id_breed == APP.breeds[j].id) {
                newHtmlBreeds = newHtmlBreeds + "<option selected value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            } else {
                newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            }
        }

        newHtml += `<tr>
                        <input id="idLine${i}" type="hidden" name="id" value="${idLine}">
                        <td><input type="text" name="ratio_composition${i}" id="ratio_composition${i}" style="width: 100%" value="${ratio_composition}"></td>
                        <td><select type="text" name="id_breed${i}" id="id_breed${i}" style="width: 100%">
                        ${newHtmlBreeds}
                        </select> </td>
                        <td><input type="text" name="age${i}" id="age${i}" style="width: 100%" value="${age}"></td>
                        <td><input type="text" name="avg_height${i}" id="avg_height${i}" style="width: 100%" value="${avg_height}"></td>
                        <td><input type="text" name="avg_diameter${i}" id="avg_diameter${i}" style="width: 100%" value="${avg_diameter}"></td>
                        <td><input type="text" name="count_of_plants${i}" id="count_of_plants${i}" style="width: 100%" value="${count_of_plants}"></td>
                    </tr>`;
    }

    var newHtmlBreeds = "";
    for(var j = 0; j < APP.breeds.length; j++) {
        newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
    }

    newHtml += `<tr>
                    <input id="idLine${APP.countLinePoint7Table}" type="hidden" name="id" value="0">
                    <td><input type="text" name="ratio_composition${APP.countLinePoint7Table}" id="ratio_composition${APP.countLinePoint7Table}" style="width: 100%" value=""></td>
                    <td><select type="text" name="id_breed${i}" id="id_breed${i}" style="width: 100%">
                        ${newHtmlBreeds}
                    </select> </td>
                    <td><input type="text" name="age${APP.countLinePoint7Table}" id="age${APP.countLinePoint7Table}" style="width: 100%" value=""></td>
                    <td><input type="text" name="avg_height${APP.countLinePoint7Table}" id="avg_height${APP.countLinePoint7Table}" style="width: 100%" value=""></td>
                    <td><input type="text" name="avg_diameter${APP.countLinePoint7Table}" id="avg_diameter${APP.countLinePoint7Table}" style="width: 100%" value=""></td>
                    <td><input type="text" name="count_of_plants${APP.countLinePoint7Table}" id="count_of_plants${APP.countLinePoint7Table}" style="width: 100%" value=""></td>
                </tr>`;

    APP.countLinePoint7Table += 1;

    point7Table.innerHTML = newHtml;

}