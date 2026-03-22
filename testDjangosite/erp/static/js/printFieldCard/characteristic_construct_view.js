
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

    document.getElementById("lands_other").value = APP.documentData.lands_other;

    if (category_of_forest_fund_lands.value === "6") {
        document.getElementById('category_of_forest_fund_lands_other').style.display = 'block';
        document.getElementById('r1').style.height='240px';
    } else {
        document.getElementById('category_of_forest_fund_lands_other').style.display = 'none';
        document.getElementById('r1').style.height='200px';
    }



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
            newHtml = newHtml + "<option selected value=\"" + APP.allTypeForestGrowingConditions[i].id + "\">" + APP.allTypeForestGrowingConditions[i].type_forest_growing_conditions + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.allTypeForestGrowingConditions[i].id + "\">" + APP.allTypeForestGrowingConditions[i].type_forest_growing_conditions + "</option>";
        }
    }
    type_forest_growing_conditions.innerHTML = newHtml;


    document.getElementById("forest_type").value = APP.documentData.forest_type;
    document.getElementById("point7year").value = APP.documentData.point7year;
    document.getElementById("point7date").value = APP.documentData.point7date;
    document.getElementById("point7number").value = APP.documentData.point7number;
    document.getElementById("point7agreed").value = APP.documentData.point7agreed;
    document.getElementById("point7_natural_composition").value = APP.documentData.point7_natural_composition;

    let economy = document.getElementById("economy");
    newHtml = "";

    for(var i = 0; i < APP.allEconomy.length; i++) {
        if(APP.allEconomy[i].id == APP.documentData.id_economy) {
            newHtml = newHtml + "<option selected value=\"" + APP.allEconomy[i].id + "\">" + APP.allEconomy[i].name_economy + "</option>";
        } else {
            newHtml = newHtml + "<option value=\"" + APP.allEconomy[i].id + "\">" + APP.allEconomy[i].name_economy + "</option>";
        }
    }
    economy.innerHTML = newHtml;

    document.getElementById("completeness").value = APP.documentData.point7_completeness;
    document.getElementById("stock").value = APP.documentData.point7_stock;

    //APP.point7Table = await PrintFieldCardBusiness.getPoint7TableByIdOld(APP.documentData.id_list_region);
    APP.point7Table = await PrintFieldCardBusiness.getPoint7TableById(APP.documentData.id);
    //APP.point7Table = [];
    APP.countLinePoint7Table = APP.point7Table.length;
    APP.countNewLinePoint7Table = 0;
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
                        <td style="width: 50px;">
                            <svg onclick="deleteLinePoint7Table(${APP.point7Table[i].id})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                <g id="SVGRepo_iconCarrier">
                                    <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                </g>
                            </svg>
                        </td>
                    </tr>`;
    }
    point7Table.innerHTML = newHtml;

}

function addNewLineInPoint7() {

    hasUnsavedChanges = true;

    let point7Table = document.getElementById("id_point7_table");
    newHtml = "";

    var oldIndex = 0;
    var newIndex = 0;
    for(var i = 0; i < APP.point7Table.length; i++) {

        if(APP.delIdPoint7.includes(APP.point7Table[i].id)) {
            continue;
        }

        var idLine = document.getElementById("idLine"+oldIndex).value;
        var ratio_composition = document.getElementById("ratio_composition"+oldIndex).value;
        var id_breed = document.getElementById("id_breed"+oldIndex).value;
        var age = document.getElementById("age"+oldIndex).value;
        var avg_height = document.getElementById("avg_height"+oldIndex).value;
        var avg_diameter = document.getElementById("avg_diameter"+oldIndex).value;
        var count_of_plants = document.getElementById("count_of_plants"+oldIndex).value;

        oldIndex += 1;

        var newHtmlBreeds = "";
        for(var j = 0; j < APP.breeds.length; j++) {
            if(id_breed == APP.breeds[j].id) {
                newHtmlBreeds = newHtmlBreeds + "<option selected value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            } else {
                newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            }
        }

        newHtml += `<tr>
                    <input id="idLine${newIndex}" type="hidden" name="id" value="${APP.point7Table[i].id}">
                    <td><input type="text" name="ratio_composition${newIndex}" id="ratio_composition${newIndex}" style="width: 100%" value="${ratio_composition}"></td>
                    <td><select type="text" name="id_breed${newIndex}" id="id_breed${newIndex}" style="width: 100%">
                    ${newHtmlBreeds}
                    </select> </td>
                    <td><input type="text" name="age${newIndex}" id="age${newIndex}" style="width: 100%" value="${age}"></td>
                    <td><input type="text" name="avg_height${newIndex}" id="avg_height${newIndex}" style="width: 100%" value="${avg_height}"></td>
                    <td><input type="text" name="avg_diameter${newIndex}" id="avg_diameter${newIndex}" style="width: 100%" value="${avg_diameter}"></td>
                    <td><input type="text" name="count_of_plants${newIndex}" id="count_of_plants${newIndex}" style="width: 100%" value="${count_of_plants}"></td>
                    <td style="width: 50px;">
                        <svg onclick="deleteLinePoint7Table(${APP.point7Table[i].id})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                            <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                            <g id="SVGRepo_iconCarrier">
                                <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                            </g>
                        </svg>
                    </td>
                </tr>`;


        newIndex +=1;
    }

    for(var i = 0; i < APP.countNewLinePoint7Table; i++) {
        var idLine = document.getElementById("idLine"+oldIndex).value;
        var ratio_composition = document.getElementById("ratio_composition"+oldIndex).value;
        var id_breed = document.getElementById("id_breed"+oldIndex).value;
        var age = document.getElementById("age"+oldIndex).value;
        var avg_height = document.getElementById("avg_height"+oldIndex).value;
        var avg_diameter = document.getElementById("avg_diameter"+oldIndex).value;
        var count_of_plants = document.getElementById("count_of_plants"+oldIndex).value;

        oldIndex += 1;

        var newHtmlBreeds = "";
        for(var j = 0; j < APP.breeds.length; j++) {
            if(id_breed == APP.breeds[j].id) {
                newHtmlBreeds = newHtmlBreeds + "<option selected value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            } else {
                newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            }
        }

        newHtml += `<tr>
                    <input id="idLine${newIndex}" type="hidden" name="id" value="0">
                    <td><input type="text" name="ratio_composition${newIndex}" id="ratio_composition${newIndex}" style="width: 100%" value="${ratio_composition}"></td>
                    <td><select type="text" name="id_breed${newIndex}" id="id_breed${newIndex}" style="width: 100%">
                    ${newHtmlBreeds}
                    </select> </td>
                    <td><input type="text" name="age${newIndex}" id="age${newIndex}" style="width: 100%" value="${age}"></td>
                    <td><input type="text" name="avg_height${newIndex}" id="avg_height${newIndex}" style="width: 100%" value="${avg_height}"></td>
                    <td><input type="text" name="avg_diameter${newIndex}" id="avg_diameter${newIndex}" style="width: 100%" value="${avg_diameter}"></td>
                    <td><input type="text" name="count_of_plants${newIndex}" id="count_of_plants${newIndex}" style="width: 100%" value="${count_of_plants}"></td>
                    <td style="width: 50px;">
                        <svg onclick="deleteNewLineInPoint7(${newIndex})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                            <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                            <g id="SVGRepo_iconCarrier">
                                <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                            </g>
                        </svg>
                    </td>
                </tr>`;

        newIndex +=1;

    }

    var newHtmlBreeds = "";
    for(var j = 0; j < APP.breeds.length; j++) {
        newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
    }

    newHtml += `<tr>
                    <input id="idLine${APP.countLinePoint7Table}" type="hidden" name="id" value="0">
                    <td><input type="text" name="ratio_composition${APP.countLinePoint7Table}" id="ratio_composition${APP.countLinePoint7Table}" style="width: 100%" value=""></td>
                    <td><select type="text" name="id_breed${APP.countLinePoint7Table}" id="id_breed${APP.countLinePoint7Table}" style="width: 100%">
                        ${newHtmlBreeds}
                    </select> </td>
                    <td><input type="text" name="age${APP.countLinePoint7Table}" id="age${APP.countLinePoint7Table}" style="width: 100%" value=""></td>
                    <td><input type="text" name="avg_height${APP.countLinePoint7Table}" id="avg_height${APP.countLinePoint7Table}" style="width: 100%" value=""></td>
                    <td><input type="text" name="avg_diameter${APP.countLinePoint7Table}" id="avg_diameter${APP.countLinePoint7Table}" style="width: 100%" value=""></td>
                    <td><input type="text" name="count_of_plants${APP.countLinePoint7Table}" id="count_of_plants${APP.countLinePoint7Table}" style="width: 100%" value=""></td>
                    <td style="width: 50px;">
                            <svg onclick="deleteNewLineInPoint7(${APP.countLinePoint7Table})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                <g id="SVGRepo_iconCarrier">
                                    <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                                </g>
                            </svg>
                        </td>
                </tr>`;

    APP.countNewLinePoint7Table += 1;
    APP.countLinePoint7Table += 1;

    point7Table.innerHTML = newHtml;
}

function deleteNewLineInPoint7(del_index) {
    let point7Table = document.getElementById("id_point7_table");
    newHtml = "";

    var oldIndex = 0;
    var newIndex = 0;
    for(var i = 0; i < APP.point7Table.length; i++) {

        if(APP.delIdPoint7.includes(APP.point7Table[i].id)) {
            continue;
        }

        var idLine = document.getElementById("idLine"+oldIndex).value;
        var ratio_composition = document.getElementById("ratio_composition"+oldIndex).value;
        var id_breed = document.getElementById("id_breed"+oldIndex).value;
        var age = document.getElementById("age"+oldIndex).value;
        var avg_height = document.getElementById("avg_height"+oldIndex).value;
        var avg_diameter = document.getElementById("avg_diameter"+oldIndex).value;
        var count_of_plants = document.getElementById("count_of_plants"+oldIndex).value;

        oldIndex += 1;

        var newHtmlBreeds = "";
        for(var j = 0; j < APP.breeds.length; j++) {
            if(id_breed == APP.breeds[j].id) {
                newHtmlBreeds = newHtmlBreeds + "<option selected value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            } else {
                newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            }
        }

        newHtml += `<tr>
                    <input id="idLine${newIndex}" type="hidden" name="id" value="${APP.point7Table[i].id}">
                    <td><input type="text" name="ratio_composition${newIndex}" id="ratio_composition${newIndex}" style="width: 100%" value="${ratio_composition}"></td>
                    <td><select type="text" name="id_breed${newIndex}" id="id_breed${newIndex}" style="width: 100%">
                    ${newHtmlBreeds}
                    </select> </td>
                    <td><input type="text" name="age${newIndex}" id="age${newIndex}" style="width: 100%" value="${age}"></td>
                    <td><input type="text" name="avg_height${newIndex}" id="avg_height${newIndex}" style="width: 100%" value="${avg_height}"></td>
                    <td><input type="text" name="avg_diameter${newIndex}" id="avg_diameter${newIndex}" style="width: 100%" value="${avg_diameter}"></td>
                    <td><input type="text" name="count_of_plants${newIndex}" id="count_of_plants${newIndex}" style="width: 100%" value="${count_of_plants}"></td>
                    <td style="width: 50px;">
                        <svg onclick="deleteLinePoint7Table(${APP.point7Table[i].id})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                            <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                            <g id="SVGRepo_iconCarrier">
                                <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                            </g>
                        </svg>
                    </td>
                </tr>`;


        newIndex +=1;
    }

    for(var i = 0; i < APP.countNewLinePoint7Table; i++) {
        var idLine = document.getElementById("idLine"+oldIndex).value;
        var ratio_composition = document.getElementById("ratio_composition"+oldIndex).value;
        var id_breed = document.getElementById("id_breed"+oldIndex).value;
        var age = document.getElementById("age"+oldIndex).value;
        var avg_height = document.getElementById("avg_height"+oldIndex).value;
        var avg_diameter = document.getElementById("avg_diameter"+oldIndex).value;
        var count_of_plants = document.getElementById("count_of_plants"+oldIndex).value;

        if(oldIndex == del_index) {
            oldIndex += 1;
            continue;
        }

        oldIndex += 1;

        var newHtmlBreeds = "";
        for(var j = 0; j < APP.breeds.length; j++) {
            if(id_breed == APP.breeds[j].id) {
                newHtmlBreeds = newHtmlBreeds + "<option selected value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            } else {
                newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            }
        }

        newHtml += `<tr>
                    <input id="idLine${newIndex}" type="hidden" name="id" value="0">
                    <td><input type="text" name="ratio_composition${newIndex}" id="ratio_composition${newIndex}" style="width: 100%" value="${ratio_composition}"></td>
                    <td><select type="text" name="id_breed${newIndex}" id="id_breed${newIndex}" style="width: 100%">
                    ${newHtmlBreeds}
                    </select> </td>
                    <td><input type="text" name="age${newIndex}" id="age${newIndex}" style="width: 100%" value="${age}"></td>
                    <td><input type="text" name="avg_height${newIndex}" id="avg_height${newIndex}" style="width: 100%" value="${avg_height}"></td>
                    <td><input type="text" name="avg_diameter${newIndex}" id="avg_diameter${newIndex}" style="width: 100%" value="${avg_diameter}"></td>
                    <td><input type="text" name="count_of_plants${newIndex}" id="count_of_plants${newIndex}" style="width: 100%" value="${count_of_plants}"></td>
                    <td style="width: 50px;">
                        <svg onclick="deleteNewLineInPoint7(${newIndex})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                            <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                            <g id="SVGRepo_iconCarrier">
                                <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                            </g>
                        </svg>
                    </td>
                </tr>`;

        newIndex +=1;

    }

    APP.countNewLinePoint7Table -= 1;
    APP.countLinePoint7Table -= 1;
    point7Table.innerHTML = newHtml;

}

async function deleteLinePoint7Table(del_id) {

    hasUnsavedChanges = true;

    let point7Table = document.getElementById("id_point7_table");
    newHtml = "";

    var oldIndex = 0;
    var newIndex = 0;
    for(var i = 0; i < APP.point7Table.length; i++) {

        if(APP.delIdPoint7.includes(APP.point7Table[i].id)) {
            continue;
        }

        var idLine = document.getElementById("idLine"+oldIndex).value;
        var ratio_composition = document.getElementById("ratio_composition"+oldIndex).value;
        var id_breed = document.getElementById("id_breed"+oldIndex).value;
        var age = document.getElementById("age"+oldIndex).value;
        var avg_height = document.getElementById("avg_height"+oldIndex).value;
        var avg_diameter = document.getElementById("avg_diameter"+oldIndex).value;
        var count_of_plants = document.getElementById("count_of_plants"+oldIndex).value;

        oldIndex += 1;

        var newHtmlBreeds = "";
        for(var j = 0; j < APP.breeds.length; j++) {
            if(id_breed == APP.breeds[j].id) {
                newHtmlBreeds = newHtmlBreeds + "<option selected value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            } else {
                newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            }
        }

        if(APP.point7Table[i].id == del_id) {
            continue;
        }


        newHtml += `<tr>
                    <input id="idLine${newIndex}" type="hidden" name="id" value="${APP.point7Table[i].id}">
                    <td><input type="text" name="ratio_composition${newIndex}" id="ratio_composition${newIndex}" style="width: 100%" value="${ratio_composition}"></td>
                    <td><select type="text" name="id_breed${newIndex}" id="id_breed${newIndex}" style="width: 100%">
                    ${newHtmlBreeds}
                    </select> </td>
                    <td><input type="text" name="age${newIndex}" id="age${newIndex}" style="width: 100%" value="${age}"></td>
                    <td><input type="text" name="avg_height${newIndex}" id="avg_height${newIndex}" style="width: 100%" value="${avg_height}"></td>
                    <td><input type="text" name="avg_diameter${newIndex}" id="avg_diameter${newIndex}" style="width: 100%" value="${avg_diameter}"></td>
                    <td><input type="text" name="count_of_plants${newIndex}" id="count_of_plants${newIndex}" style="width: 100%" value="${count_of_plants}"></td>
                    <td style="width: 50px;">
                        <svg onclick="deleteLinePoint7Table(${APP.point7Table[i].id})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                            <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                            <g id="SVGRepo_iconCarrier">
                                <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                            </g>
                        </svg>
                    </td>
                </tr>`;


        newIndex +=1;
    }

    for(var i = 0; i < APP.countNewLinePoint7Table; i++) {
        var idLine = document.getElementById("idLine"+oldIndex).value;
        var ratio_composition = document.getElementById("ratio_composition"+oldIndex).value;
        var id_breed = document.getElementById("id_breed"+oldIndex).value;
        var age = document.getElementById("age"+oldIndex).value;
        var avg_height = document.getElementById("avg_height"+oldIndex).value;
        var avg_diameter = document.getElementById("avg_diameter"+oldIndex).value;
        var count_of_plants = document.getElementById("count_of_plants"+oldIndex).value;

        oldIndex += 1;

        var newHtmlBreeds = "";
        for(var j = 0; j < APP.breeds.length; j++) {
            if(id_breed == APP.breeds[j].id) {
                newHtmlBreeds = newHtmlBreeds + "<option selected value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            } else {
                newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            }
        }

        newHtml += `<tr>
                    <input id="idLine${newIndex}" type="hidden" name="id" value="0">
                    <td><input type="text" name="ratio_composition${newIndex}" id="ratio_composition${newIndex}" style="width: 100%" value="${ratio_composition}"></td>
                    <td><select type="text" name="id_breed${newIndex}" id="id_breed${newIndex}" style="width: 100%">
                    ${newHtmlBreeds}
                    </select> </td>
                    <td><input type="text" name="age${newIndex}" id="age${newIndex}" style="width: 100%" value="${age}"></td>
                    <td><input type="text" name="avg_height${newIndex}" id="avg_height${newIndex}" style="width: 100%" value="${avg_height}"></td>
                    <td><input type="text" name="avg_diameter${newIndex}" id="avg_diameter${newIndex}" style="width: 100%" value="${avg_diameter}"></td>
                    <td><input type="text" name="count_of_plants${newIndex}" id="count_of_plants${newIndex}" style="width: 100%" value="${count_of_plants}"></td>
                    <td style="width: 50px;">
                        <svg onclick="deleteNewLineInPoint7(${newIndex})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                            <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                            <g id="SVGRepo_iconCarrier">
                                <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                            </g>
                        </svg>
                    </td>
                </tr>`;

        newIndex +=1;

    }

    APP.countLinePoint7Table -= 1;
    point7Table.innerHTML = newHtml;

    APP.delIdPoint7.push(del_id);
}