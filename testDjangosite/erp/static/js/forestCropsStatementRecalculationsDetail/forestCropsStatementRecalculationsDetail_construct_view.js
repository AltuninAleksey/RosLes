openPage();

async function openPage() {

    APP.deleteIdSample = [];

    let idDocument = document.querySelector("#idDocument").value;
    //var allForestData = await CommonBusiness.getAllForest();

    //APP.subjectrf = allForestData.subjectrf;
    //APP.forestly = allForestData.forestly;
    //APP.district_forestly = allForestData.district_forestly;
    //APP.quarter = allForestData.quarter;

    APP.userData = await CommonBusiness.getUserData();

    APP.documentData = await StatementRecalculationsBusinessDetail.getStatementRecalculationsDetailDataById(idDocument);
    //APP.subjects = await CommonBusiness.getAllSubjectrf();

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

    var sampleResp = await StatementRecalculationsBusinessDetail.getSampleByIdListRegion(idDocument,1);
    APP.countPageDetail = sampleResp.count;
    APP.sampleList = sampleResp.data
    APP.sortOrderTable1 = 0;

    await setDetailDataIdPage();

    setEvent();
    setDataInProfile();
}

async function setDetailDataIdPage() {

    let dateStatementNode = document.querySelector("#dateStatement");
    let soilLotStatementNode = document.querySelector("#soilLotStatement");
    let sampleRegionStatementNode = document.querySelector("#sampleRegionStatement");
    let quarterStatementNode = document.querySelector("#quarterStatement");
    let dachaStatementNode = document.querySelector("#dachaStatement");

    dateStatementNode.value = APP.documentData.date;
    dachaStatementNode.value = APP.documentData.dacha;

    soilLotStatementNode.value = APP.documentData.soilLot;
    sampleRegionStatementNode.value = APP.documentData.sampleRegion;
    quarterStatementNode.value = APP.documentData.nameQuarter;


    await setSubjectsRF();
    //await setSampleList();
    updateDataInStatementRecalculationsTbody();
}

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

    document
        .getElementById("printFieldCard")
        .addEventListener('click',function() {
        getPrintFieldCard(APP.documentData.id_field_card, APP.documentData.id);
    });

    document.getElementById("prevPageBtn").addEventListener("click", prevPage);
    document.getElementById("nextPageBtn").addEventListener("click", nextPage);

    var buttonAddProba = document.getElementById("buttonAddProba");
    buttonAddProba.addEventListener('click', function() {
        createSample();
    });
}

// Пагинации
let currentPage = 1;           // Текущая страница
let rowsPerPage = 3;          // Количество строк на странице
let totalPages = 1;

// Обновления таблицы с пагинацией
function updateDataInStatementRecalculationsTbody() {

    totalPages = Math.ceil(APP.countPageDetail / APP.limit);

    updatePaginationControls();

    currentPage = 1;

    updatePaginationInfo();
    renderTablePage();
    updateButtonsState();
}

function renderTablePage() {
    let sampleListTbodyNode = document.querySelector("#sampleListTbody");
    let newHtml = "";

    for(let i = 0; i < APP.sampleList.length; i++) {


        let strGetRecalculatingDetail = "getForestCropsInformTrialArea(" + APP.sampleList[i].id + ")"
        newHtml += `<tr class="cursorPointer" onClick=${strGetRecalculatingDetail}>
                        <td class="textAlignCenter td8">${APP.sampleList[i].number}</td>
                        <td class="textAlignCenter td9">${APP.sampleList[i].length}</td>
                        <td class="textAlignCenter td5">${APP.sampleList[i].width}</td>
                        <td class="textAlignCenter td6">${APP.sampleList[i].square}</td>` +
                        "<td style=\"width: 1%; cursor: pointer;\">" +
                            "<svg onclick=\"event.stopPropagation();deleteNewLineInSampleList(" +APP.sampleList[i].id + ")\" class=\"cursorPointer\" width=\"23px\" height=\"23px\" viewBox=\"0 0 24 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">" +
                                "<g id=\"SVGRepo_bgCarrier\" stroke-width=\"0\"></g> " +
                                "<g id=\"SVGRepo_tracerCarrier\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></g>" +
                                "<g id=\"SVGRepo_iconCarrier\">" +
                                    "<path d=\"M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17\" stroke=\"#000000\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path> " +
                                "</g>" +
                            "</svg>" +
                        "</td>" +
                    `</tr>`;
    }

    sampleListTbodyNode.innerHTML = newHtml;
}
async function deleteNewLineInSampleList(del_id) {
    let idDocument = document.querySelector("#idDocument").value;
    hasUnsavedChanges = true;
    await StatementRecalculationsBusinessDetail.deleteSample(del_id);
    var sampleResp = await StatementRecalculationsBusinessDetail.getSampleByIdListRegion(idDocument,1);
    APP.countPageDetail = sampleResp.count;
    APP.sampleList = sampleResp.data
    updateDataInStatementRecalculationsTbody();
}
function updatePaginationInfo() {

    const itemsOnCurrentPage = currentPage*APP.limit;

    document.getElementById("totalCount").innerText = APP.countPageDetail;

    document.getElementById("pageInfo").innerText = `Страница ${currentPage} из ${totalPages}`;

    if (APP.countPageDetail === 0) {
        document.getElementById("itemsInfo").innerText = `Нет записей`;
    } else if (itemsOnCurrentPage != totalPages) {
        document.getElementById("itemsInfo").innerText = `Показано ${itemsOnCurrentPage} из ${APP.countPageDetail} записей`;
    } else {
        document.getElementById("itemsInfo").innerText = `Показано ${itemsOnCurrentPage} из ${APP.countPageDetail} записей (последняя страница)`;
    }
}

// Обновление кнопок
function updateButtonsState() {
    const prevBtn = document.getElementById("prevPageBtn");
    const nextBtn = document.getElementById("nextPageBtn");

    if (prevBtn) {
        prevBtn.disabled = currentPage === 1;
    }

    if (nextBtn) {
        nextBtn.disabled = currentPage === totalPages;
    }
}

// Обновление элементов управления пагинацией
function updatePaginationControls() {
    const paginationNumbers = document.getElementById("paginationNumbers");
    if (!paginationNumbers) return;

    paginationNumbers.innerHTML = "";

    // Показываем первые 2 страницы
    if (totalPages <= 5) {
        for (let i = 1; i <= totalPages; i++) {
            addPageButton(i);
        }
    } else {
        // Всегда показываем первые 2 страницы
        addPageButton(1);
        addPageButton(2);

        // Определяем, нужно ли многоточие
        if (currentPage > 4) {
            addDots();  // Многоточие
        }

        // Показываем текущую страницу и соседние
        let startPage = Math.max(3, currentPage - 1);
        let endPage = Math.min(totalPages - 2, currentPage + 1);

        for (let i = startPage; i <= endPage; i++) {
            if (i > 2 && i < totalPages - 1) {
                addPageButton(i);
            }
        }

        // Определяем, нужно ли многоточие в конце
        if (currentPage < totalPages - 3) {
            addDots();
        }

        // Показываем последние 2 страницы
        addPageButton(totalPages - 1);
        addPageButton(totalPages);
    }
}

function addPageButton(pageNum) {
    const button = document.createElement("button");
    let idDocument = document.querySelector("#idDocument").value;
    button.innerText = pageNum;
    button.classList.add("page-number");
    if (pageNum === currentPage) {
        button.classList.add("active");
    }
    button.addEventListener("click", async () => {
        currentPage = pageNum;

        var response = await StatementRecalculationsBusinessDetail.getSampleByIdListRegion(idDocument,pageNum);

        APP.countPageDetail = response.count;
        APP.sampleList = response.data
        renderTablePage();

        updateButtonsState();
        updatePaginationInfo();

        updatePaginationControls();
    });
    document.getElementById("paginationNumbers").appendChild(button);
}

function addDots() {
    const dots = document.createElement("span");
    dots.innerText = "...";
    dots.classList.add("pagination-dots");
    document.getElementById("paginationNumbers").appendChild(dots);
}

async function nextPage() {
    let idDocument = document.querySelector("#idDocument").value;
    if (currentPage < totalPages) {
        currentPage++;
        var response = await StatementRecalculationsBusinessDetail.getSampleByIdListRegion(idDocument,currentPage);
        APP.countPageDetail = response.count;
        APP.sampleList = response.data
        renderTablePage();

        updateButtonsState();
        updatePaginationInfo();

        updatePaginationControls();
    }
}

async function prevPage() {
    let idDocument = document.querySelector("#idDocument").value;
    if (currentPage > 1) {
        currentPage--;

        var response = await StatementRecalculationsBusinessDetail.getSampleByIdListRegion(idDocument,currentPage);
        APP.countPageDetail = response.count;
        APP.sampleList = response.data
        renderTablePage();

        updateButtonsState();
        updatePaginationInfo();

        updatePaginationControls();
    }
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


async function setSubjectsRF() {
    let subjectStatementNode = document.querySelector("#subjectStatement");

    let newHtml = "";

    for(let i = 0; i < APP.subjects.length; i++) {
        if(APP.subjects[i].id == APP.documentData.idSubject) {
            newHtml += '<option selected value="' + APP.subjects[i].id + '">' + APP.subjects[i].name_subject_RF + '</option>';
        } else {
            newHtml += '<option value="' + APP.subjects[i].id + '">' + APP.subjects[i].name_subject_RF + '</option>';
        }
    }

    subjectStatementNode.innerHTML = newHtml;

    await setForestly();
}

async function setForestly() {
    let idSubject = document.querySelector("#subjectStatement").value;
    APP.forestly = await CommonBusiness.getForestlyByIdSubjectrf(idSubject);

    let newHtml = "";

    let forestlyStatementNode = document.querySelector("#forestlyStatement");

    for(let i = 0; i < APP.forestly.length; i++) {
        if(APP.forestly[i].id == APP.documentData.idForestly) {
            newHtml += '<option selected value="' + APP.forestly[i].id + '">' + APP.forestly[i].name_forestly + '</option>';
        } else {
            newHtml += '<option value="' + APP.forestly[i].id + '">' + APP.forestly[i].name_forestly + '</option>';
        }
    }

    forestlyStatementNode.innerHTML = newHtml;

    await setDistriotForestlyStatement();
}

async function setDistriotForestlyStatement() {
    let idForestly = document.querySelector("#forestlyStatement").value;
    APP.district_forestly = [];

    if(idForestly != "" && idForestly != null && idForestly != undefined) {
        APP.district_forestly = await CommonBusiness.getDistrictForestlyByIdForestly(idForestly);
    }

    let newHtml = "";

    let distriotForestlyNode = document.querySelector("#distriotForestlyStatement");

    for(let i = 0; i < APP.district_forestly.length; i++) {
        if(APP.district_forestly[i].id == APP.documentData.idDistrictForestly) {
            newHtml += '<option selected value="' + APP.district_forestly[i].id + '">' + APP.district_forestly[i].name_district_forestly + '</option>';
        } else {
            newHtml += '<option value="' + APP.district_forestly[i].id + '">' + APP.district_forestly[i].name_district_forestly + '</option>';
        }
    }

    distriotForestlyNode.innerHTML = newHtml;

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
const nameLength = document.getElementById('nameLength');
const nameWidth = document.getElementById('nameWidth');


function formatToTwoDecimals(input) {
  const value = parseFloat(input.value);
  if (!isNaN(value)) {
    input.value = value.toFixed(2);
  }
}

nameLength.addEventListener('blur', () => formatToTwoDecimals(field1));
nameWidth.addEventListener('blur', () => formatToTwoDecimals(field2));

async function createSample() {
    let id = document.querySelector("#idDocument").value;
    let width = document.getElementById("nameWidth");
    let length = document.getElementById("nameLength");
    let data = {
        idListRegion: Number(id),
        width: Number(width.value).toFixed(2),
        length: Number(length.value).toFixed(2),
    }

    let result = await StatementRecalculationsBusinessDetail.createSample(data);
    var sampleResp = await StatementRecalculationsBusinessDetail.getSampleByIdListRegion(id,1);
    APP.countPageDetail = sampleResp.count;
    APP.sampleList = sampleResp.data
    updateDataInStatementRecalculationsTbody();
    closeAddForm("form-add-proba");
    //getRecalculatingDetail(result.id, document.querySelector("#idDocument").value);
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
    let dateStatementNode = document.querySelector("#dateStatement").value;
    let soilLotStatementNode = document.querySelector("#soilLotStatement").value;
    let sampleRegionStatementNode = document.querySelector("#sampleRegionStatement").value;
    let quarterStatement = document.querySelector("#quarterStatement").value;
    let dachaStatement = document.querySelector("#dachaStatement").value;
    let distriotForestlyStatement = document.querySelector("#distriotForestlyStatement").value;

    var data = {
        date: dateStatementNode,
        id: Number(id),
        sampleRegion: Number(sampleRegionStatementNode),
        nameQuarter: quarterStatement == ""? null : quarterStatement,
        dacha: dachaStatement == ""? null : dachaStatement,
        idDistrictForestly: Number(distriotForestlyStatement),
        soilLot: soilLotStatementNode,
    };

    await StatementRecalculationsBusinessDetail.getUpdateSample(data);

    resetChangesTracker();

    setTimeout(function() {
        let id = document.querySelector("#idDocument").value;
        getForestCropsRecalculationsDetail(id);
      }, 3000);

    hideLoadingModal();

    ShowModal('m1', 'Сохранение прошло успешно', '/static/img/check-circle-fill.svg')

    } catch (error) {
      hideLoadingModal();
      console.error('Ошибка сохранения:', error);
      showError(error.message || 'Произошла ошибка при сохранении');
    }
}

async function generateDocx() {

    let id = document.querySelector("#idDocument").value;

    var data = {
        id: id
    };

    var urlFile = await StatementRecalculationsBusinessDetail.generateDocx(data);
    urlFile = urlFile.document;

    urlFile = urlGlobal + urlFile;

    window.open(urlFile, '_blank').focus();
}

