openPage();
setEventListenerForObjects();

function setEventListenerForObjects() {
    var regions = document.getElementById("regionRF");
    regions.addEventListener('change',function() {
        changeDataSelectForestly(regions.value);
    });

    var forestlyObject = document.getElementById("lesName");
    forestlyObject.addEventListener('change',function() {
        changeDataSelectDistrictForestly(forestlyObject.value);
    });

    var districtForestly = document.getElementById("ucLesName");
    districtForestly.addEventListener('change',function() {
        changeDataSelectQuarter(districtForestly.value);
    });

    let idParent = document.getElementById("idParent").value;
    var back_event = document.getElementById("back_event");
    back_event.addEventListener('click',function() {
        getStatementRecalculationsDetail(idParent);
    });

    var buttonAddProba = document.getElementById("buttonAddProba");
    buttonAddProba.addEventListener('click', function() {
        addProba();
    });

    var buttonAddState = document.getElementById("buttonAddState");
    buttonAddState.addEventListener('click', function() {
        addState();
    });

    var buttonAddGps = document.getElementById("buttonAddGps");
        buttonAddGps.addEventListener('click', function() {
            addGps();
    });

    var buttonAddUndefground = document.getElementById("buttonAddUndefground");
        buttonAddUndefground.addEventListener('click', function() {
            addUndefground();
    });




}

async function openPage() {
    let idDocument = document.getElementById("idDocument").value;

    APP.deleteIdTableNull = [];
    APP.deleteIdTableOne = [];
    APP.deleteIdTableThree = [];


    //var allForestData = await CommonBusiness.getAllForest();

    //APP.subjectrf = await CommonBusiness.getAllSubjectrf();
    //APP.subjectrf = APP.subjectrf.sort(function(a, b) { return a.name_subject_RF > b.name_subject_RF? 1 : -1; });
    //APP.forestly = allForestData.forestly;
    //APP.district_forestly = allForestData.district_forestly;
    //APP.quarter = allForestData.quarter;

    APP.userData = await CommonBusiness.getUserData();

    var czl = await CommonBusiness.getCZL();
    APP.subjectrf = [];

    var item_subject = {
        id: czl.id_main_subject,
        name_subject_RF: czl.name_main_subject
    }
    APP.subjectrf.push(item_subject);

    for(var i = 0; i < czl.slave_subject.length; i++) {
        var item_subject = {
            id: czl.slave_subject[i].id_subject,
            name_subject_RF: czl.slave_subject[i].name_slave_subject
        }

        APP.subjectrf.push(item_subject);
    }

    APP.breeds = await CommonBusiness.getAllBreeds();
    APP.undergrowth = await CommonBusiness.getAllUndergrowth();

    APP.documentData = await RecalculatingDetailBusiness.getRecalculatingDetailDataById(idDocument);
    let listData = await RecalculatingDetailBusiness.getListData(idDocument);
    APP.dataTable_1 = listData.list_data;
    APP.dataTable_2 = listData.post_data;
    APP.dataTable_3 = listData.gps_data;
    APP.dataTable_0 = listData.non_null_list_data;

    APP.dataTable_4 = await RecalculatingDetailBusiness.getPhotoPoint(idDocument);

    await setDataInHeader();
    await setDataInTableNull();
    await setDataInTableOne(1);
    await setDataInTableTwo();
    await setDataInTableThree();
    await setDataInTableFour();

    setDataFormAddProba();

    setDataInProfile();
}

function setDataFormAddProba() {
    var breedName_proba = document.getElementById("breedName-proba");
    var breedName_undeground = document.getElementById("breedName-undeground");
    var newHtml = "";

    for(var i = 0; i < APP.breeds.length; i++) {
        newHtml = newHtml + "<option value=\"" + APP.breeds[i].id + "\">" + APP.breeds[i].name_breed + "</option>";
    }

    breedName_proba.innerHTML = newHtml;

    newHtml = "";
    for(var i = 0; i < APP.undergrowth.length; i++) {
        newHtml = newHtml + "<option value=\"" + APP.undergrowth[i].id + "\">" + APP.undergrowth[i].name + "</option>";
    }

    breedName_undeground.innerHTML = newHtml;
}

async function openAddForm(id) {

    if(id == "form-add-state" && (APP.states == null || APP.states == undefined)) {
        var stateName = document.getElementById("stateName");
        var newHtml = "";

        APP.states = await getAllProfile();

        for(var i = 0; i < APP.states.length; i++) {
            newHtml = newHtml + "<option value=\"" + APP.states[i].id + "\">" + APP.states[i].FIO + "</option>";
        }

        stateName.innerHTML = newHtml;
    }

    var formAddProba = document.getElementById(id);
    formAddProba.classList.remove("display-none");

    var body = document.getElementById("body");
    body.classList.add("overflowHiddenImportant");
}

function closeAddForm(id) {
    var formAddProba = document.getElementById(id);
    formAddProba.classList.add("display-none");

    var body = document.getElementById("body");
    body.classList.remove("overflowHiddenImportant");

    if(id == "form-add-proba") {
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
        var proba_age = document.getElementById("proba-age");
        //var proba_all_count = document.getElementById("proba-all-count");

        proba_021_05.value = "";
        proba_11_15.value = "";
        proba_02.value = "";
        proba_06_10.value = "";
        proba_15.value = "";
        proba_maxheig.value = "";
        proba_main.checked = false;
        proba_avg_d.value = "";
        proba_avg_h.value = "";
        proba_age.value = "";
        //proba_all_count.value = "";
    }

}
let hasUnsavedChanges = false;
let ignoreFields = ['profile_fio','profile_phone','subjectStatement-profile','old_password','new_password','confirm_password'];

window.addEventListener('beforeunload', (event) => {
    if (hasUnsavedChanges) {
        event.preventDefault();
        event.returnValue = '';
    }
});

function trackChanges() {
    hasUnsavedChanges = true;
    //console.log('Изменение обнаружено');
}

function addListenersToField(field) {

    if (ignoreFields.includes(field.id) || ignoreFields.includes(field.name)) {
        return;
    }

    field.addEventListener('input', trackChanges);
    field.addEventListener('change', trackChanges);
    if (field.type === 'checkbox' || field.type === 'radio') {
        field.addEventListener('click', trackChanges);
    }

    //console.log('Отслеживание добавлено для:', field.name || field.id);
}

function trackAllFields() {
    const inputFields = document.querySelectorAll('input, textarea, select');
    inputFields.forEach(field => {
        addListenersToField(field);
    });
}

const observer = new MutationObserver((mutations) => {
    mutations.forEach(mutation => {
        mutation.addedNodes.forEach(node => {
            if (node.nodeType === 1) {

                if (node.matches && node.matches('input, textarea, select')) {
                    addListenersToField(node);
                }

                if (node.querySelectorAll) {
                    const nestedFields = node.querySelectorAll('input, textarea, select');
                    nestedFields.forEach(field => addListenersToField(field));
                }
            }
        });
    });
});

observer.observe(document.body, {
    childList: true,
    subtree: true
});

trackAllFields();

function resetChangesTracker() {
    hasUnsavedChanges = false;
    console.log('Трекер сброшен');
}
async function saveRecalculating() {
    try {
        if(!CommonFunction.checkMandatoryData()) {
        ShowModal('m1', 'Заполните все обязательные поля!', '/static/img/exclamation-circle.svg')
        return;
    }
    showLoadingModal();
    let idDocument = document.getElementById("idDocument").value;
    let idParent = document.getElementById("idParent").value;
    var regionRFForm = document.getElementById("regionRF");
    var lesNameForm = document.getElementById("lesName");
    var ucLesNameForm = document.getElementById("ucLesName");
    var quarterForm = document.getElementById("quarter");
    var soil_lotForm = document.getElementById("soil_lot");

    var requestData = {
        sample: {
            id: APP.documentData.id,
            id_list_region: idParent,
            date: document.getElementById("date_recalculation").value,
            id_district_forestly: ucLesNameForm.value,
            id_forestly: lesNameForm.value,
            id_profile: APP.documentData.id_profile,
            name_quarter: quarterForm.value,
            dacha: document.getElementById("dacha").value == ""? null:document.getElementById("dacha").value,
            square: document.getElementById("square_recalculation").value,
            soil_lot: document.getElementById("soil_lot_recalculating").value,
            number_region: document.getElementById("number_region").value
        },
        list_data : APP.dataTable_1,
        gps_data : APP.dataTable_3,
        non_null_list_data : APP.dataTable_0
    };


    await RecalculatingDetailBusiness.getUpdateSampleandother(requestData);

    for(var i = 0; i <  APP.deleteIdTableOne.length; i++) {
        await RecalculatingDetailBusiness.deleteListById(APP.deleteIdTableOne[i]);
    }

    for(var i = 0; i <  APP.deleteIdTableNull.length; i++) {
        await RecalculatingDetailBusiness.deleteListById(APP.deleteIdTableNull[i]);
    }

    for(var i = 0; i < APP.deleteIdTableThree.length; i++) {
        await RecalculatingDetailBusiness.deleteGpsPoint(APP.deleteIdTableThree[i]);
    }


    resetChangesTracker();
    hideLoadingModal();
    ShowModal('m1', 'Сохранение прошло успешно', '/static/img/check-circle-fill.svg')
    setTimeout(function() {
        let idParent = document.getElementById("idParent").value;
        getStatementRecalculationsDetail(idParent);
      }, 3000);

    } catch (error) {
      hideLoadingModal();
      console.error('Ошибка сохранения:', error);
      showError(error.message || 'Произошла ошибка при сохранении');
    }
}
