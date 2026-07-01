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
    //setDataFormAddProba()
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

    dateStatementNode.value =  APP.documentData.listRegion.date;

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

    APP.dacha = await CommonBusiness.getDachaStatementByIdDistrictForestly(APP.documentData.listRegion.idDistrictForestly);

    for(let i = 0; i < APP.dacha.length; i++) {
        if(APP.dacha[i].id == APP.documentData.listRegion.idDacha) {
           dachaStatementNode.value = APP.dacha[i].name;
        }
    }

    lengthSampleNode.value = Number(APP.documentData.sample.length).toFixed(2);
    widthSampleNode.value = Number(APP.documentData.sample.width).toFixed(2);
    squareSampleNode.value = Number(APP.documentData.sample.square).toFixed(4);

    updateDataInNullTable();
    updateDataInOneTable();
    updateDataInTwoTable();
}


const field1 = document.getElementById('lengthSample');
const field2 = document.getElementById('widthSample');
const resultField = document.getElementById('squareSample');

function calculateAndSetSquare() {

    const length = parseFloat(field1.value.replace(',', '.'));
    const width = parseFloat(field2.value.replace(',', '.'));

    if (!isNaN(length) && !isNaN(width) && length > 0 && width > 0) {
        const square = length * width / 10000;
        resultField.value = square.toFixed(4);
    } else {
        resultField.value = '';
    }
}

field1.addEventListener('input', calculateAndSetSquare);
field2.addEventListener('input', calculateAndSetSquare);
field1.addEventListener('change', calculateAndSetSquare);
field2.addEventListener('change', calculateAndSetSquare);

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

    document.getElementById("prevForestPageBtn").addEventListener("click", prevPageForest);
    document.getElementById("nextForestPageBtn").addEventListener("click", nextPageForest);

    document.getElementById("prevDiameterPageBtn").addEventListener("click", prevPageDiameter);
    document.getElementById("nextDiameterPageBtn").addEventListener("click", nextPageDiameter);

    document.getElementById("prevPodrostPageBtn").addEventListener("click", prevPagePodrost);
    document.getElementById("nextPodrostPageBtn").addEventListener("click", nextPagePodrost);

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

    let idParent = document.getElementById("idParent").value;
    var back_event = document.getElementById("back_event");
    back_event.addEventListener('click',function() {
        getForestCropsRecalculationsDetail(idParent);
    });
    back_event.setAttribute('data-custom-action', 'back_event');

    var buttonSaveСhang = document.getElementById("buttonSaveСhang");
    buttonSaveСhang.addEventListener('click', function() {
        saveAndExit();
    });

    var buttonExit = document.getElementById("buttonExit");
    buttonExit.addEventListener('click', function() {
        exitWithoutSave();
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
//function setDataFormAddProba() {
//    var breed = document.getElementById("breed");
//
//    var newHtml = "";
//
//    for(var i = 0; i < APP.breeds.length; i++) {
//        newHtml = newHtml + "<option value=\"" + APP.breeds[i].id + "\">" + APP.breeds[i].name + "</option>";
//    }
//
//    breed.innerHTML = newHtml;
//
//    var breedDiameter = document.getElementById("breedDiameter");
//
//    var newHtml = "";
//
//    for(var i = 0; i < APP.breeds.length; i++) {
//        newHtml = newHtml + "<option value=\"" + APP.breeds[i].id + "\">" + APP.breeds[i].name + "</option>";
//    }
//
//    breedDiameter.innerHTML = newHtml;
//
//    var breedPodrost = document.getElementById("breedPodrost");
//
//    var newHtml = "";
//
//    for(var i = 0; i < APP.breeds.length; i++) {
//        newHtml = newHtml + "<option value=\"" + APP.breeds[i].id + "\">" + APP.breeds[i].name + "</option>";
//    }
//
//    breedPodrost.innerHTML = newHtml;
//}

function initCustomSelectForest(containerId, data, onSelect) {
   const container = document.getElementById(containerId);
   if (!container) return;

   const forestTrigger = document.getElementById('forestTrigger');
   const optionsContainerForest = document.getElementById('forestOptions');
   const selectedForestValue = document.getElementById('forestSelected');
   const hiddenInputForest = document.getElementById('selectBreedId');

   data.forEach(item => {
       const option = document.createElement('div');
       option.className = 'custom-option';
       option.dataset.value = item.id;
       option.innerHTML = `
           <div class="option-name">${item.name}</div>
           <div class="option-short">${item.shortName || ''}</div>
       `;
       option.addEventListener('click', () => {
           // Обновляем отображение
           selectedForestValue.innerHTML = `
               <span class="selected-name">${item.name}</span>
               <span class="selected-short">${item.shortName || ''}</span>
           `;

           // Обновляем скрытое поле
           if (hiddenInputForest) {
               hiddenInputForest.value = item.id;
           }

           // Вызываем callback
           if (onSelect) {
               onSelect(item.id, item);
           }

           // Закрываем список
           container.classList.remove('open');
       });
       optionsContainerForest.appendChild(option);
   });

   forestTrigger.addEventListener('click', (e) => {
         e.stopPropagation();
        container.classList.toggle('open');
   });

    document.addEventListener('click', () => {
        container.classList.remove('open');
    });

    return {
       getValue: () => hiddenInputForest ? hiddenInputForest.value : null,
       getSelected: () => {
            const id = hiddenInputForest ? hiddenInputForest.value : null;
           return data.find(item => item.id == id) || null;
       }
    };
}

function initCustomSelectDiameter(containerId, data, onSelect) {
   const container = document.getElementById(containerId);
   if (!container) return;

   const trigger = container.querySelector('.custom-select-trigger');
   const optionsContainer = container.querySelector('.custom-select-options');
   const selectedValue = container.querySelector('.selected-value');
   const hiddenInput = document.getElementById('selectedBreedId');

   data.forEach(item => {
       const option = document.createElement('div');
       option.className = 'custom-option';
       option.dataset.value = item.id;
       option.innerHTML = `
           <div class="option-name">${item.name}</div>
           <div class="option-short">${item.shortName || ''}</div>
       `;
       option.addEventListener('click', () => {
           // Обновляем отображение
           selectedValue.innerHTML = `
               <span class="selected-name">${item.name}</span>
               <span class="selected-short">${item.shortName || ''}</span>
           `;

           // Обновляем скрытое поле
           if (hiddenInput) {
               hiddenInput.value = item.id;
           }

           // Вызываем callback
           if (onSelect) {
               onSelect(item.id, item);
           }

           // Закрываем список
           container.classList.remove('open');
       });
       optionsContainer.appendChild(option);
   });

   trigger.addEventListener('click', (e) => {
         e.stopPropagation();
        container.classList.toggle('open');
   });

    document.addEventListener('click', () => {
        container.classList.remove('open');
    });

    return {
       getValue: () => hiddenInput ? hiddenInput.value : null,
       getSelected: () => {
            const id = hiddenInput ? hiddenInput.value : null;
           return data.find(item => item.id == id) || null;
       }
    };
}

function initCustomSelectPodrost(containerId, data, onSelect) {
   const container = document.getElementById(containerId);
   if (!container) return;

   const podrostTrigger = document.getElementById('podrostTrigger');
   const optionsContainerPodrost = document.getElementById('podrostOptions');
   const selectedPodrostValue = document.getElementById('podrostSelected');
   const hiddenInputPodrost = document.getElementById('selectPodrostId');

   data.forEach(item => {
       const option = document.createElement('div');
       option.className = 'custom-option';
       option.dataset.value = item.id;
       option.innerHTML = `
           <div class="option-name">${item.name}</div>
           <div class="option-short">${item.shortName || ''}</div>
       `;
       option.addEventListener('click', () => {
           // Обновляем отображение
           selectedPodrostValue.innerHTML = `
               <span class="selected-name">${item.name}</span>
               <span class="selected-short">${item.shortName || ''}</span>
           `;

           // Обновляем скрытое поле
           if (hiddenInputPodrost) {
               hiddenInputPodrost.value = item.id;
           }

           // Вызываем callback
           if (onSelect) {
               onSelect(item.id, item);
           }

           // Закрываем список
           container.classList.remove('open');
       });
       optionsContainerPodrost.appendChild(option);
   });

   podrostTrigger.addEventListener('click', (e) => {
         e.stopPropagation();
        container.classList.toggle('open');
   });

    document.addEventListener('click', () => {
        container.classList.remove('open');
    });

    return {
       getValue: () => hiddenInputPodrost ? hiddenInputPodrost.value : null,
       getSelected: () => {
            const id = hiddenInputPodrost ? hiddenInputPodrost.value : null;
           return data.find(item => item.id == id) || null;
       }
    };
}
async function initApp() {
    try {
        var responseBreeds = await forestCropsInformTrialArea.getAllBreeds();
        APP.breeds = responseBreeds.data;
        APP.countBreeds = responseBreeds.count;

        const breedSelector = initCustomSelectDiameter('breedSelect', APP.breeds, (id, item) => {
            APP.breedSelectId = id;
        });
        const forestBreedSelector = initCustomSelectForest('breed', APP.breeds, (id, item) => {
            APP.forestBreedSelectId = id;
        });
        const forestPodrostSelector = initCustomSelectPodrost('breedPodrost', APP.breeds, (id, item) => {
            APP.forestPodrostSelectId = id;
        });

    } catch (error) {
        console.error('Ошибка инициализации:', error);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    initApp();
});

let hasUnsavedChanges = false;
let pendingNavigation = null;
let ignoreFields = ['profile_fio','profile_phone','subjectStatement-profile','old_password','new_password','confirm_password'];

//window.addEventListener('beforeunload', (event) => {
//    if (hasUnsavedChanges) {
//        event.preventDefault();
//        event.returnValue = '';
//    }
//});

function trackChanges() {
    hasUnsavedChanges = true;
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

document.addEventListener('click', function(e) {
    const clickable = e.target.closest('[onclick], [data-custom-action]');
    if (!clickable) return;
    let pendingFn = null;

    if (clickable.hasAttribute('onclick')) {
        const onclickAttr = clickable.getAttribute('onclick');
        if (onclickAttr &&
            !onclickAttr.includes('closeAddForm') &&
            !onclickAttr.includes('openAddForm') &&
            !onclickAttr.includes('editLineInNullTable') &&
            !onclickAttr.includes('deleteLineInNullTable') &&
            !onclickAttr.includes('downloadDocument') &&
            !onclickAttr.includes('saveInlineNullEdit') &&
            !onclickAttr.includes('saveInlineOneEdit') &&
            !onclickAttr.includes('editLineInOneTable') &&
            !onclickAttr.includes('deleteLineInOneTable') &&
            !onclickAttr.includes('saveInlineTwoEdit') &&
            !onclickAttr.includes('editLineInTwoTable') &&
            !onclickAttr.includes('deleteLineInTwoTable') &&
            !onclickAttr.includes('hideStatusModal') &&
            !onclickAttr.includes('HideModalProfile') &&
            !onclickAttr.includes('saveDataInProfile') &&
            !onclickAttr.includes('openChangePassword') &&
            !onclickAttr.includes('saveNewPassword') &&
            !onclickAttr.includes('saveData') &&
            !onclickAttr.includes('getAvtorization'))
            {
            try {
                pendingFn = () => eval(onclickAttr);
            } catch (error) {
                console.error(error);
            }
        }
    } else if (clickable.hasAttribute('data-custom-action')) {
        const action = clickable.getAttribute('data-custom-action');
        if (action === 'back_event'){
            const currentId = document.getElementById("idParent").value;
            pendingFn = () => getForestCropsRecalculationsDetail(currentId);
        }
    }
    if (pendingFn && hasUnsavedChanges) {
        e.preventDefault();
        e.stopPropagation();
        pendingNavigation = pendingFn;
        openAddForm('form-load-noSave');
    }
}, true);

function exitWithoutSave() {
    closeAddForm('form-load-noSave');
    hasUnsavedChanges = false;

    if (typeof pendingNavigation === 'function') {
        pendingNavigation();
        pendingNavigation = null;
    }
}

function saveAndExit() {
    saveData();

    closeAddForm('form-load-noSave');
    hasUnsavedChanges = false;

    if (typeof pendingNavigation === 'function') {
        pendingNavigation();
        pendingNavigation = null;
    }
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
        let idParent = document.querySelector("#idParent").value;
        //getForestCropsInformTrialArea(id, idParent);
        getForestCropsRecalculationsDetail(idParent);
      }, 3000);

    hideLoadingModal();

    ShowModal('m1', 'Сохранение прошло успешно', '/static/img/check-circle-fill.svg')

    } catch (error) {
      hideLoadingModal();
      console.error('Ошибка сохранения:', error);
      showError(error.message || 'Произошла ошибка при сохранении');
    }
}