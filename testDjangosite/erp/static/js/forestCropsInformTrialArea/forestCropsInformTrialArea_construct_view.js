openPage();

async function openPage() {

    APP.deleteNullTable = [];
    APP.deleteOneTable = [];
    APP.deleteTwoTable = [];


    APP.dateUpdate = [];
    APP.dateUpdateOne = [];
    APP.dateUpdateTwo = [];

    let idDocument = document.querySelector("#idDocument").value;

    APP.userData = await CommonBusiness.getUserData();

    APP.documentData = await forestCropsInformTrialArea.getStatementRecalculationsDetailDataById(idDocument);

    var czl = await CommonBusiness.getCZL();
    APP.subjects = [];

    var item_subject = {
        id: czl.id_main_subject,
        name_subject_RF: czl.name_main_subject
    }
    APP.subjects.push(item_subject);

    for(var i = 0; i < czl.slave_subject.length; i++) {
        var item_subject = {
            id: czl.slave_subject[i].id_subject,
            name_subject_RF: czl.slave_subject[i].name_slave_subject
        }

        APP.subjects.push(item_subject);
    }
    APP.breeds = await CommonBusiness.getAllBreeds();

    var nullTableResp = await forestCropsInformTrialArea.getSampleByIdListRegion(idDocument,1);
    APP.countNullTable = nullTableResp.count;
    APP.nullTableList = nullTableResp.data;

    var oneTableResp = await forestCropsInformTrialArea.getOneTable(idDocument,1);
    APP.countOneTable = oneTableResp.count;
    APP.oneTableList = oneTableResp.data;

   var twoTableResp = await forestCropsInformTrialArea.getTwoTable(idDocument,1);
    APP.countTwoTable = twoTableResp.count;
    APP.twoTableList = twoTableResp.data;

    APP.sortOrderTable1 = 0;

    await setDetailDataIdPage();

    setEvent();
    setDataInProfile();
    setDataFormAddProba()
}

async function setDetailDataIdPage() {

    let dateStatementNode = document.querySelector("#dateStatement");
    let soilLotStatementNode = document.querySelector("#soilLotStatement");
    let sampleRegionStatementNode = document.querySelector("#sampleRegionStatement");
    let quarterStatementNode = document.querySelector("#quarterStatement");
    let dachaStatementNode = document.querySelector("#dachaStatement");
    let lengthSampleNode = document.querySelector("#lengthSample");
    let widthSampleNode = document.querySelector("#widthSample");
    let squareSampleNode = document.querySelector("#squareSample");

    let subjectStatementNode = document.querySelector("#subjectStatement");
    let forestlyStatementNode = document.querySelector("#forestlyStatement");
    let distriotForestlyNode = document.querySelector("#distriotForestlyStatement");

//    dateStatementNode.value = "Дата обследования: " + APP.documentData.listRegion.date;
//    dachaStatementNode.value = "Урочище: " + APP.documentData.listRegion.dacha;
//
//    soilLotStatementNode.value = "Выдел: " + APP.documentData.listRegion.soilLot;
//    sampleRegionStatementNode.value = "Площадь участка: " + APP.documentData.listRegion.sampleRegion;
//    quarterStatementNode.value = "Квартал: " + APP.documentData.listRegion.nameQuarter;
    dateStatementNode.value =  APP.documentData.listRegion.date;
    dachaStatementNode.value = APP.documentData.listRegion.dacha;

    soilLotStatementNode.value =  APP.documentData.listRegion.soilLot;
    sampleRegionStatementNode.value =  APP.documentData.listRegion.sampleRegion;
    quarterStatementNode.value = APP.documentData.listRegion.nameQuarter;

    for(let i = 0; i < APP.subjects.length; i++) {
        if(APP.subjects[i].id == APP.documentData.listRegion.idSubject) {
            //subjectStatementNode.value = "Субьект РФ: " + APP.subjects[i].name_subject_RF;
            subjectStatementNode.value = APP.subjects[i].name_subject_RF;
        }
    }

    APP.forestly = await CommonBusiness.getForestlyByIdSubjectrf(APP.documentData.listRegion.idSubject);

    for(let i = 0; i < APP.forestly.length; i++) {
        if(APP.forestly[i].id == APP.documentData.listRegion.idForestly) {
           // forestlyStatementNode.value = "Лесничество: " + APP.forestly[i].name_forestly;
            forestlyStatementNode.value = APP.forestly[i].name_forestly;
        }
    }

    APP.district_forestly = await CommonBusiness.getDistrictForestlyByIdForestly(APP.documentData.listRegion.idForestly);

    for(let i = 0; i < APP.district_forestly.length; i++) {
        if(APP.district_forestly[i].id == APP.documentData.listRegion.idDistrictForestly) {
           // distriotForestlyNode.value = "Участковое лесничество: " + APP.district_forestly[i].name_district_forestly;
           distriotForestlyNode.value = APP.district_forestly[i].name_district_forestly;
        }
    }

    lengthSampleNode.value = Number(APP.documentData.sample.length).toFixed(2);
    widthSampleNode.value = Number(APP.documentData.sample.width).toFixed(2);
    squareSampleNode.value = Number(APP.documentData.sample.square).toFixed(2);

    updateDataInNullTable();
    updateDataInOneTable();
    updateDataInTwoTable();
}


const field1 = document.getElementById('lengthSample');
const field2 = document.getElementById('widthSample');


function formatToTwoDecimals(input) {
  const value = parseFloat(input.value);
  if (!isNaN(value)) {
    input.value = value.toFixed(2);
  }
}

field1.addEventListener('blur', () => formatToTwoDecimals(field1));
field2.addEventListener('blur', () => formatToTwoDecimals(field2));
async function setEvent() {

    document
        .querySelector("#subjectStatement")
        .addEventListener('change', async (e)=>{
                await setForestly();
        });

    document
        .querySelector("#forestlyStatement")
        .addEventListener('change', async (e)=>{
                await setDistriotForestlyStatement();
        });

    document.getElementById("prevForestPageBtn").addEventListener("click", prevPage);
    document.getElementById("nextForestPageBtn").addEventListener("click", nextPage);

    document.getElementById("prevDiameterPageBtn").addEventListener("click", prevPage);
    document.getElementById("nextDiameterPageBtn").addEventListener("click", nextPage);

    document.getElementById("prevPodrostPageBtn").addEventListener("click", prevPage);
    document.getElementById("nextPodrostPageBtn").addEventListener("click", nextPage);

    var buttonAddProba = document.getElementById("buttonAddProba");
    buttonAddProba.addEventListener('click', function() {
        createSample();
    });

    var buttonAddDiameter = document.getElementById("buttonAddDiameter");
    buttonAddDiameter.addEventListener('click', function() {
        createOneSample();
    });

    var buttonAddPodrost = document.getElementById("buttonAddPodrost");
    buttonAddPodrost.addEventListener('click', function() {
        createTwoSample();
    });
}

function sortByDate() {
    if(APP.sortOrderTable1 != 1) {
        APP.sampleList.sort(function(a, b) { return a.date > b.date? -1 : 1; });
        APP.sortOrderTable1 = 1;

        document.querySelector("#sortDateForTable1").innerHTML = "&#8593;";
    } else {
        APP.sampleList.sort(function(a, b) { return a.date > b.date? 1 : -1; });
        APP.sortOrderTable1 = -1;

        document.querySelector("#sortDateForTable1").innerHTML = "&#8595;";
    }

}

async function openAddForm(id) {

    var formAddProba = document.getElementById(id);
    formAddProba.classList.remove("display-none");

    var body = document.getElementById("body");
    //body.classList.add("overflowHiddenImportant");
}

function closeAddForm(id) {
    var formAddProba = document.getElementById(id);
    formAddProba.classList.add("display-none");

    var body = document.getElementById("body");
    //body.classList.remove("overflowHiddenImportant");

}
function setDataFormAddProba() {
    var breed = document.getElementById("breed");

    var newHtml = "";

    for(var i = 0; i < APP.breeds.length; i++) {
        newHtml = newHtml + "<option value=\"" + APP.breeds[i].id + "\">" + APP.breeds[i].name_breed + "</option>";
    }

    breed.innerHTML = newHtml;

    var breedDiameter = document.getElementById("breedDiameter");

    var newHtml = "";

    for(var i = 0; i < APP.breeds.length; i++) {
        newHtml = newHtml + "<option value=\"" + APP.breeds[i].id + "\">" + APP.breeds[i].name_breed + "</option>";
    }

    breedDiameter.innerHTML = newHtml;

    var breedPodrost = document.getElementById("breedPodrost");

    var newHtml = "";

    for(var i = 0; i < APP.breeds.length; i++) {
        newHtml = newHtml + "<option value=\"" + APP.breeds[i].id + "\">" + APP.breeds[i].name_breed + "</option>";
    }

    breedPodrost.innerHTML = newHtml;
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

async function saveData() {
   try {
     if(!CommonFunction.checkMandatoryData()) {
        ShowModal('m1', 'Заполните все обязательные поля!', '/static/img/exclamation-circle.svg')
        return;
    }
    showLoadingModal();

    let id = document.querySelector("#idDocument").value;
    let width = document.getElementById("widthSample");
    let length = document.getElementById("lengthSample");

    let updSample = {
        id: Number(id),
        width: Number(width.value).toFixed(2),
        length: Number(length.value).toFixed(2),
    }

    await forestCropsInformTrialArea.getUpdateSample(updSample);

    if(APP.dateUpdate.length > 0) {
        var nullUpd = {
            idSample: Number(id),
            values : APP.dateUpdate
        };
        await forestCropsInformTrialArea.updateNullTable(nullUpd);
    }

    if(APP.dateUpdateOne.length > 0) {
        var oneUpd = {
            idSample: Number(id),
            values : APP.dateUpdateOne
        };
        await forestCropsInformTrialArea.updateOneTable(oneUpd);
    }

    if(APP.dateUpdateTwo.length > 0) {
        var twoUpd = {
            idSample: Number(id),
            values : APP.dateUpdateTwo
        };
        await forestCropsInformTrialArea.updateTwoTable(twoUpd);
    }

    APP.dateUpdate = [];
    APP.dateUpdateOne = [];
    APP.dateUpdateTwo = [];

    APP.nullTableList = [];
    APP.oneTableList = [];
    APP.twoTableList = [];

    resetChangesTracker();

    setTimeout(function() {
        let id = document.querySelector("#idDocument").value;
        getForestCropsInformTrialArea(id);
      }, 3000);

    hideLoadingModal();

    ShowModal('m1', 'Сохранение прошло успешно', '/static/img/check-circle-fill.svg')

    } catch (error) {
      hideLoadingModal();
      console.error('Ошибка сохранения:', error);
      showError(error.message || 'Произошла ошибка при сохранении');
    }
}