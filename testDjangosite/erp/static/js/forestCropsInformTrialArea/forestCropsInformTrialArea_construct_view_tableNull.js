// Пагинации
let currentPage = 1;           // Текущая страница
let rowsPerPage = 3;          // Количество строк на странице
let totalPages = 1;

// Обновления таблицы с пагинацией
function updateDataInNullTable() {
    console.log(APP.nullTableList)
    totalPages = Math.ceil(APP.countNullTable / APP.limit);

    updatePaginationControls();

    currentPage = 1;

    updatePaginationInfo();
    renderTablePage();
    updateButtonsState();
}
function renderTablePage() {
    console.log("Отрисовка таблицы лесных культур вызвана")
    let sampleListTbodyNode = document.querySelector("#sampleListTbody");
    let newHtml = "";

    for(let i = 0; i < APP.nullTableList.length; i++) {
        const currentItem = APP.nullTableList[i];
        const recordId = currentItem.id;

        const isMarkedForDelete = APP.deleteNullTable.includes(recordId);
        var newHtmlBreeds = "";

        for(var j = 0; j < APP.breeds.length; j++) {
            if(currentItem.idBreed == APP.breeds[j].id) {
                newHtmlBreeds = newHtmlBreeds + "<option selected value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            } else {
                newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            }
        }

         const rowClass = `cursorPointer ${isMarkedForDelete ? 'highlighted' : ''}`;

                newHtml += `<tr class="${rowClass}" data-id="${recordId}">
                        <td class="textAlignCenter td8"><select type="text" name="id_breed${i}" id="id_breed${i}" style="width: 180px; border: none; text-align: center; background: transparent; outline: none; box-shadow: none; -webkit-appearance: none; -moz-appearance: none; appearance: none;">${newHtmlBreeds}</select></td>
                        <td class="textAlignCenter td9">${APP.nullTableList[i].countLiving}</td>
                        <td class="textAlignCenter td5">${APP.nullTableList[i].countDead}</td>` +
                        "<td style=\"width: 1%; cursor: pointer; text-align: center\">" +
                            "<svg onclick=\"deleteLineInNullTable(" +APP.nullTableList[i].id + ")\" class=\"cursorPointer\" width=\"23px\" height=\"23px\" viewBox=\"0 0 24 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">" +
                                "<g id=\"SVGRepo_bgCarrier\" stroke-width=\"0\"></g> " +
                                "<g id=\"SVGRepo_tracerCarrier\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></g>" +
                                "<g id=\"SVGRepo_iconCarrier\">" +
                                    "<path d=\"M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17\" stroke=\"#000000\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path> " +
                                "</g>" +
                            "</svg>" +
                        "</td>" +
                        "<td style=\"width: 1%; cursor: pointer; text-align: center\">" +
                            "<svg onClick=\"event.stopPropagation();editLineInNullTable(" +APP.nullTableList[i].id + ")\" xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-pencil\" viewBox=\"0 0 16 16\">" +
                              "<path d=\"M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325\"/>"
                            "</svg>" +
                        "</td>" +
                    `</tr>`;
    }

    sampleListTbodyNode.innerHTML = newHtml;
}
function editLineInNullTable(id, event) {
    if (event) {
        event.stopPropagation();
    }
    const record = APP.nullTableList.find(item => item.id === id);
    if (!record) {
        console.error('Запись не найдена, id:', id);
        return;
    }

    // Находим строку по ID
    const targetRow = document.querySelector(`tr[data-id="${id}"]`);
    if (!targetRow) return;

    const cells = targetRow.cells;

    // Сохраняем текущие значения
    const currentBreed = record.idBreed;
    const currentLiving = record.countLiving;
    const currentDead = record.countDead;

    // Создаем select для породы
    let breedOptions = "";
    for (let j = 0; j < APP.breeds.length; j++) {
        const selected = APP.breeds[j].id === currentBreed ? "selected" : "";
        breedOptions += `<option value="${APP.breeds[j].id}" ${selected}>${APP.breeds[j].name_breed}</option>`;
    }

    // Заменяем содержимое ячеек на поля ввода с кнопками
    cells[0].innerHTML = `<select class="edit-breed" data-field="breed" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">${breedOptions}</select>`;
    cells[1].innerHTML = `<input type="number" class="edit-living" data-field="living" value="${currentLiving}"  style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;
    cells[2].innerHTML = `<input type="number" class="edit-dead" data-field="dead" value="${currentDead}" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;

    // Добавляем кнопки сохранить/отменить в последнюю ячейку
    cells[4].innerHTML = `
        <button onclick="saveInlineEdit(${id})" style="margin-right: 5px; padding: 5px 10px; border: none; background: transparent;">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check-lg" viewBox="0 0 16 16">
              <path d="M12.736 3.97a.733.733 0 0 1 1.047 0c.286.289.29.756.01 1.05L7.88 12.01a.733.733 0 0 1-1.065.02L3.217 8.384a.757.757 0 0 1 0-1.06.733.733 0 0 1 1.047 0l3.052 3.093 5.4-6.425z"/>
            </svg>
        </button>
    `;
}
function saveInlineEdit(id) {

    const targetRow = document.querySelector(`tr[data-id="${id}"]`);
    if (!targetRow) return;

    // Получаем новые значения из полей ввода
    const newBreed = targetRow.cells[0].querySelector('.edit-breed')?.value;
    const newLiving = targetRow.cells[1].querySelector('.edit-living')?.value;
    const newDead = targetRow.cells[2].querySelector('.edit-dead')?.value;

    if (!newBreed || !newLiving === undefined || !newDead === undefined) {
        console.error('Не удалось получить значения');
        return;
    }

    // Сохраняем изменения в APP.dateUpdate для отправки на сервер
    APP.dateUpdate.push({
        id: id,
        idBreed: Number(newBreed),
        countLiving: Number(newLiving),
        countDead: Number(newDead),
    });

    // Обновляем данные в APP.nullTableList
    const index = APP.nullTableList.findIndex(item => item.id === id);
    if (index !== -1) {
        APP.nullTableList[index] = {
            ...APP.nullTableList[index],
            idBreed: Number(newBreed),
            countLiving: Number(newLiving),
            countDead: Number(newDead),
        };
    }

    // Перерисовываем таблицу
    updateDataInNullTable();
}
function deleteLineInNullTable(del_id) {

    hasUnsavedChanges = true;

    APP.deleteNullTable.push(del_id);

    if (!APP.deleteNullTable.includes(del_id)) {
        APP.deleteNullTable.push(del_id);
    }

    updateDataInNullTable();
}

 async function createSample() {
    let id = document.querySelector("#idDocument").value;
    let idBreed = document.getElementById("breed");
    let countDead = document.getElementById("countDead");
    let countLiving = document.getElementById("countLiving");
    APP.createSample = [];

    let table = {
        idBreed: Number(idBreed.value),
        countDead: Number(countDead.value),
        countLiving: Number(countLiving.value),
    }
    APP.createSample.push(table);
    //APP.nullTableList.push(table);
    var data = {
        idSample: Number(id),
        values : APP.createSample
    };
    APP.createSample = [];
    await forestCropsInformTrialArea.createSample(data);
    var nullTableResp = await forestCropsInformTrialArea.getSampleByIdListRegion(id,1);
    APP.countNullTable = nullTableResp.count;
    APP.nullTableList = nullTableResp.data

    updateDataInNullTable();
    closeAddForm("form-add-proba");

    console.log('Добавлена запись:', data);
    console.log('Все записи:', APP.nullTableList);

}

function updatePaginationInfo() {

    const itemsOnCurrentPage = currentPage*APP.limit;

    document.getElementById("totalForestCount").innerText = APP.countNullTable;

    document.getElementById("pageForestInfo").innerText = `Страница ${currentPage} из ${totalPages}`;

    if (APP.countNullTable === 0) {
        document.getElementById("itemsForestInfo").innerText = `Нет записей`;
    } else if (itemsOnCurrentPage != totalPages) {
        document.getElementById("itemsForestInfo").innerText = `Показано ${itemsOnCurrentPage} из ${APP.countNullTable} записей`;
    } else {
        document.getElementById("itemsForestInfo").innerText = `Показано ${itemsOnCurrentPage} из ${APP.countNullTable} записей (последняя страница)`;
    }
}

// Обновление кнопок
function updateButtonsState() {
    const prevBtn = document.getElementById("prevForestPageBtn");
    const nextBtn = document.getElementById("nextForestPageBtn");

    if (prevBtn) {
        prevBtn.disabled = currentPage === 1;
    }

    if (nextBtn) {
        nextBtn.disabled = currentPage === totalPages;
    }
}

// Обновление элементов управления пагинацией
function updatePaginationControls() {
    const paginationNumbers = document.getElementById("paginationForestNumbers");
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

        var response = await forestCropsInformTrialArea.getSampleByIdListRegion(idDocument,pageNum);

        APP.countNullTable = response.count;
        APP.nullTableList = response.data
        renderTablePage();

        updateButtonsState();
        updatePaginationInfo();

        updatePaginationControls();
    });
    document.getElementById("paginationForestNumbers").appendChild(button);
}

function addDots() {
    const dots = document.createElement("span");
    dots.innerText = "...";
    dots.classList.add("pagination-dots");
    document.getElementById("paginationForestNumbers").appendChild(dots);
}

async function nextPage() {
    let idDocument = document.querySelector("#idDocument").value;
    if (currentPage < totalPages) {
        currentPage++;
        var response = await forestCropsInformTrialArea.getSampleByIdListRegion(idDocument,currentPage);
        APP.countNullTable = response.count;
        APP.nullTableList = response.data
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

        var response = await forestCropsInformTrialArea.getSampleByIdListRegion(idDocument,currentPage);
        APP.countNullTable = response.count;
        APP.nullTableList = response.data
        renderTablePage();

        updateButtonsState();
        updatePaginationInfo();

        updatePaginationControls();
    }
}