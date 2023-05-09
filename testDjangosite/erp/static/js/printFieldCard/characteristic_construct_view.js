
async function setDataInCharacteristic() {

    APP.allPurpose = await PrintFieldCardBusiness.getALLPurposeforestData();
    let purpose_of_forests = document.getElementById("purpose_of_forests");
    let newHtml = "";

    for(var i = 0; i < APP.allPurpose.length; i++) {
        if(APP.allPurpose[i].id == APP.documentData.id_purpose_of_forests) {
            newHtml = newHtml + "<option selected value=\"" + APP.allPurpose[i].id + "\">" + APP.allPurpose[i].name_purpose + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.allPurpose[i].id + "\">" + APP.allPurpose[i].name_purpose + "</option>";
        }
    }
    purpose_of_forests.innerHTML = newHtml;


    APP.allForestProtectionCategory = await PrintFieldCardBusiness.getAllForestProtectionCategoryData ();
    let forest_protection_category = document.getElementById("forest_protection_category");
    newHtml = "";

    for(var i = 0; i < APP.allForestProtectionCategory.length; i++) {
        if(APP.allForestProtectionCategory[i].id == APP.documentData.id_forest_protection_category) {
            newHtml = newHtml + "<option selected value=\"" + APP.allForestProtectionCategory[i].id + "\">" + APP.allForestProtectionCategory[i].name_forest_protection_category + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.allForestProtectionCategory[i].id + "\">" + APP.allForestProtectionCategory[i].name_forest_protection_category + "</option>";
        }
    }
    forest_protection_category.innerHTML = newHtml;


    document.getElementById("protected_areas_of_forests").value = APP.documentData.protected_areas_of_forests;


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
        if(APP.allCategoryOfForestFundLands[i].id == APP.documentData.id_category_of_forest_fund_lands) {
            newHtml = newHtml + "<option selected value=\"" + APP.allCategoryOfForestFundLands[i].id + "\">" + APP.allCategoryOfForestFundLands[i].name_category + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.allCategoryOfForestFundLands[i].id + "\">" + APP.allCategoryOfForestFundLands[i].name_category + "</option>";
        }
    }
    category_of_forest_fund_lands.innerHTML = newHtml;


    APP.allMethodOfReforestation = await PrintFieldCardBusiness.getAllMethodOfReforestation();
    let method_of_reforestation = document.getElementById("method_of_reforestation");
    newHtml = "";

    for(var i = 0; i < APP.allMethodOfReforestation.length; i++) {
        if(APP.allMethodOfReforestation[i].id == APP.documentData.id_method_of_reforestation) {
            newHtml = newHtml + "<option selected value=\"" + APP.allMethodOfReforestation[i].id + "\">" + APP.allMethodOfReforestation[i].name_of_method + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.allMethodOfReforestation[i].id + "\">" + APP.allMethodOfReforestation[i].name_of_method + "</option>";
        }
    }
    method_of_reforestation.innerHTML = newHtml;


    document.getElementById("time_of_reforestation").value = APP.documentData.time_of_reforestation;


    APP.allTypeForestGrowingConditions = await PrintFieldCardBusiness.getAllTypeForestGrowingConditions();
    let type_forest_growing_conditions = document.getElementById("type_forest_growing_conditions");
    newHtml = "";

    for(var i = 0; i < APP.allTypeForestGrowingConditions.length; i++) {
        if(APP.allTypeForestGrowingConditions[i].id == APP.documentData.id_type_forest_growing_conditions) {
            newHtml = newHtml + "<option selected value=\"" + APP.allTypeForestGrowingConditions[i].id + "\">" + APP.allTypeForestGrowingConditions[i].subtypes_of_humidity + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.allTypeForestGrowingConditions[i].id + "\">" + APP.allTypeForestGrowingConditions[i].subtypes_of_humidity + "</option>";
        }
    }
    type_forest_growing_conditions.innerHTML = newHtml;


    document.getElementById("forest_type").value = APP.documentData.forest_type;
    document.getElementById("point7year").value = APP.documentData.point7year;
    document.getElementById("point7date").value = APP.documentData.point7date;
    document.getElementById("point7number").value = APP.documentData.point7number;
    document.getElementById("point7agreed").value = APP.documentData.point7agreed;
    document.getElementById("point7_natural_composition").value = APP.documentData.point7_natural_composition;
    document.getElementById("point7_natural_composition2").value = APP.documentData.point7_natural_composition2;
    document.getElementById("completeness").value = APP.documentData.point7_completeness;
    document.getElementById("stock").value = APP.documentData.point7_stock;

    APP.point7Table = await PrintFieldCardBusiness.getPoint7TableById(APP.documentData.id_list_region);
    let point7Table = document.getElementById("id_point7_table");
    newHtml = "";

    for(var i = 0; i < APP.point7Table.length; i++) {
        newHtml += `<tr>
                        <td><input type="text" name="ratio_composition${i}" id="ratio_composition${i}" style="width: 100%" value="${APP.point7Table[i].ratio_composition}"></td>
                        <td><input readonly type="text" name="id_breed${i}" id="id_breed${i}" style="width: 100%" value="${CommonFunction.getBreedsName(APP.breeds, APP.point7Table[i].id_breed)}"></td>
                        <td><input type="text" name="age${i}" id="age${i}" style="width: 100%" value="${APP.point7Table[i].age}"></td>
                        <td><input type="text" name="avg_height${i}" id="avg_height${i}" style="width: 100%" value="${APP.point7Table[i].avg_height}"></td>
                        <td><input type="text" name="avg_diameter${i}" id="avg_diameter${i}" style="width: 100%" value="${APP.point7Table[i].avg_diameter}"></td>
                        <td><input type="text" name="count_of_plants${i}" id="count_of_plants${i}" style="width: 100%" value="${APP.point7Table[i].count_of_plants}"></td>
                    </tr>`;
    }
    point7Table.innerHTML = newHtml;


}