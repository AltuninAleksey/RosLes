// Пагинации
let currentPageOne = 1;           // Текущая страница
let totalPagesOne = 1;

// Обновления таблицы с пагинацией
function updateDataInOneTable() {

    totalPagesOne = Math.ceil(APP.countOneTable / APP.limit);

    updatePaginationOneControls();

    currentPageOne = 1;

    updatePaginationOneInfo();
    renderOneTablePage();
    updateButtonsOne();
}
function renderOneTablePage() {
    let sampleListOneTbodyNode = document.querySelector("#sampleList");
    let newHtml = "";

    for(let i = 0; i < APP.oneTableList.length; i++) {
        const currentOneItem = APP.oneTableList[i];
        const recordOneId = currentOneItem.id;

        const isMarkedForDeleteOne = APP.deleteOneTable.includes(recordOneId);
        var newHtmlBreeds = "";

        for(var j = 0; j < APP.breeds.length; j++) {
            if(APP.oneTableList[i].idBreed == APP.breeds[j].id) {
                newHtmlBreeds = newHtmlBreeds + "<option selected value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            } else {
                newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            }
        }
        const rowClass = `cursorPointer ${isMarkedForDeleteOne ? 'highlighted' : ''}`;
                newHtml += `<tr class="${rowClass}" data-id="${recordOneId}">
                        <td class="textAlignCenter td8"><select type="text" name="id_breed${i}" id="id_breed${i}" style="width: 180px; border: none; text-align: center; background: transparent; outline: none; box-shadow: none; -webkit-appearance: none; -moz-appearance: none; appearance: none;">${newHtmlBreeds}</select></td>
                        <td class="textAlignCenter td9">${APP.oneTableList[i].diameter}</td>
                        <td class="textAlignCenter td5">${APP.oneTableList[i].height}</td>` +
                        "<td style=\"width: 1%; cursor: pointer; text-align: center\">" +
                            "<svg onclick=\"deleteLineInOneTable(" +APP.oneTableList[i].id + ")\" class=\"cursorPointer\" width=\"23px\" height=\"23px\" viewBox=\"0 0 24 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">" +
                                "<g id=\"SVGRepo_bgCarrier\" stroke-width=\"0\"></g> " +
                                "<g id=\"SVGRepo_tracerCarrier\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></g>" +
                                "<g id=\"SVGRepo_iconCarrier\">" +
                                    "<path d=\"M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17\" stroke=\"#000000\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path> " +
                                "</g>" +
                            "</svg>" +
                        "</td>" +
                        "<td style=\"width: 1%; cursor: pointer; text-align: center\">" +
                            "<svg onClick=\"event.stopPropagation();editLineInOneTable(" +APP.oneTableList[i].id + ")\" xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-pencil\" viewBox=\"0 0 16 16\">" +
                              "<path d=\"M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325\"/>"
                            "</svg>" +
                        "</td>" +
                    `</tr>`;
    }

    sampleListOneTbodyNode.innerHTML = newHtml;
}
function editLineInOneTable(id, event) {
    if (event) {
        event.stopPropagation();
    }
    const record = APP.oneTableList.find(item => item.id === id);
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
    const currentDiameter = record.diameter;
    const currentHeight = record.height;

    // Создаем select для породы
    let breedOptions = "";
    for (let j = 0; j < APP.breeds.length; j++) {
        const selected = APP.breeds[j].id === currentBreed ? "selected" : "";
        breedOptions += `<option value="${APP.breeds[j].id}" ${selected}>${APP.breeds[j].name_breed}</option>`;
    }

    // Заменяем содержимое ячеек на поля ввода с кнопками
    cells[0].innerHTML = `<select class="edit-breed" data-field="breed" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">${breedOptions}</select>`;
    cells[1].innerHTML = `<input type="number" step="0.1" min="0" class="edit-diameter" data-field="diameter" value="${currentDiameter}"  style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;
    cells[2].innerHTML = `<input type="number" step="0.1" min="0" class="edit-height" data-field="height" value="${currentHeight}" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;

    // Добавляем кнопки сохранить/отменить в последнюю ячейку
    cells[4].innerHTML = `
        <button onclick="saveInlineEdit(${id})" style="margin-right: 5px; padding: 5px 10px; border: none; background: transparent;">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check-lg" viewBox="0 0 16 16">
              <path d="M12.736 3.97a.733.733 0 0 1 1.047 0c.286.289.29.756.01 1.05L7.88 12.01a.733.733 0 0 1-1.065.02L3.217 8.384a.757.757 0 0 1 0-1.06.733.733 0 0 1 1.047 0l3.052 3.093 5.4-6.425z"/>
            </svg>
        </button>
    `;

}
function truncateTo2Decimals(value) {
    if (value === undefined || value === null || value === '') return 0;

    // Преобразуем в строку и заменяем запятую
    let str = String(value).replace(',', '.');

    // Парсим число
    let num = parseFloat(str);
    if (isNaN(num)) return 0;

    // Отсекаем до 2 знаков (НЕ округляем!)
    return Math.floor(num * 100) / 100;
}
function saveInlineEdit(id) {

    const targetRow = document.querySelector(`tr[data-id="${id}"]`);
    if (!targetRow) return;

    // Получаем новые значения из полей ввода
    const newBreed = targetRow.cells[0].querySelector('.edit-breed')?.value;
    const newDiameter = targetRow.cells[1].querySelector('.edit-diameter')?.value;
    const newHeight = targetRow.cells[2].querySelector('.edit-height')?.value;

    if (!newBreed || !newDiameter === undefined || !newHeight === undefined) {
        console.error('Не удалось получить значения');
        return;
    }
    const diameterValue = truncateTo2Decimals(newDiameter);
    const heightValue = truncateTo2Decimals(newHeight);

    APP.dateUpdateOne.push({
        id: id,
        idBreed: Number(newBreed),
        diameter: diameterValue,
        height: heightValue,
    });

    // Обновляем данные в APP.oneTableList
    const index = APP.oneTableList.findIndex(item => item.id === id);
    if (index !== -1) {
        APP.oneTableList[index] = {
            ...APP.oneTableList[index],
            idBreed: Number(newBreed),
            diameter: diameterValue,
            height: heightValue,
        };
    }

    // Перерисовываем таблицу
    updateDataInOneTable();
}

function deleteLineInOneTable(del_id) {

    hasUnsavedChanges = true;

    APP.deleteOneTable.push(del_id);

    if (!APP.deleteOneTable.includes(del_id)) {
        APP.deleteOneTable.push(del_id);
    }

    updateDataInOneTable();
}

 async function createOneSample() {
    let id = document.querySelector("#idDocument").value;
    let idBreed = document.getElementById("breedDiameter");
    let height = document.getElementById("height");
    let diameter = document.getElementById("diameter");

    const diameterValue = truncateTo2Decimals(diameter.value);
    const heightValue = truncateTo2Decimals(height.value);

    APP.createOneSample = [];

    let table = {
        idBreed: Number(idBreed.value),
        height: heightValue,
        diameter: diameterValue,
    }
    APP.createOneSample.push(table);

    var data = {
        idSample: Number(id),
        values : APP.createOneSample
    };
    APP.createOneSample = [];
    await forestCropsInformTrialArea.createOneSample(data);
    var oneTableResp = await forestCropsInformTrialArea.getOneTable(id,1);
    APP.countOneTable = oneTableResp.count;
    APP.oneTableList = oneTableResp.data

    updateDataInOneTable();
    closeAddForm("form-add-diameter");

    console.log('Добавлена запись:', data);
    console.log('Все записи:', APP.oneTableList);

}

function updatePaginationOneInfo() {

    const itemsOnCurrentPage = currentPageOne*APP.limit;

    document.getElementById("totalDiameterCount").innerText = APP.countOneTable;

    document.getElementById("pageDiameterInfo").innerText = `Страница ${currentPageOne} из ${totalPagesOne}`;

    if (APP.countOneTable === 0) {
        document.getElementById("itemsDiameterInfo").innerText = `Нет записей`;
    } else if (itemsOnCurrentPage != totalPagesOne) {
        document.getElementById("itemsDiameterInfo").innerText = `Показано ${itemsOnCurrentPage} из ${APP.countOneTable} записей`;
    } else {
        document.getElementById("itemsDiameterInfo").innerText = `Показано ${itemsOnCurrentPage} из ${APP.countOneTable} записей (последняя страница)`;
    }
}

// Обновление кнопок
function updateButtonsOne() {
    const prevBtn = document.getElementById("prevDiameterPageBtn");
    const nextBtn = document.getElementById("nextDiameterPageBtn");

    if (prevBtn) {
        prevBtn.disabled = currentPageOne === 1;
    }

    if (nextBtn) {
        nextBtn.disabled = currentPageOne === totalPagesOne;
    }
}

// Обновление элементов управления пагинацией
function updatePaginationOneControls() {
    const paginationNumbers = document.getElementById("paginationDiameterNumbers");
    if (!paginationNumbers) return;

    paginationNumbers.innerHTML = "";

    // Показываем первые 2 страницы
    if (totalPagesOne <= 5) {
        for (let i = 1; i <= totalPagesOne; i++) {
            addPageOneButton(i);
        }
    } else {
        // Всегда показываем первые 2 страницы
        addPageOneButton(1);
        addPageOneButton(2);

        // Определяем, нужно ли многоточие
        if (currentPageOne > 4) {
            addDots();  // Многоточие
        }

        // Показываем текущую страницу и соседние
        let startPage = Math.max(3, currentPageOne - 1);
        let endPage = Math.min(totalPagesOne - 2, currentPageOne + 1);

        for (let i = startPage; i <= endPage; i++) {
            if (i > 2 && i < totalPagesOne - 1) {
                addPageOneButton(i);
            }
        }

        // Определяем, нужно ли многоточие в конце
        if (currentPageOne < totalPagesOne - 3) {
            addDots();
        }

        // Показываем последние 2 страницы
        addPageOneButton(totalPagesOne - 1);
        addPageOneButton(totalPagesOne);
    }
}

function addPageOneButton(pageNum) {
    const button = document.createElement("button");
    let idDocument = document.querySelector("#idDocument").value;
    button.innerText = pageNum;
    button.classList.add("page-number");
    if (pageNum === currentPageOne) {
        button.classList.add("active");
    }
    button.addEventListener("click", async () => {
        currentPageOne = pageNum;

        var response = await forestCropsInformTrialArea.getOneTable(idDocument,pageNum);

        APP.countOneTable = response.count;
        APP.oneTableList = response.data
        renderOneTablePage();

        updateButtonsOne();
        updatePaginationOneInfo();

        updatePaginationOneControls();
    });
    document.getElementById("paginationDiameterNumbers").appendChild(button);
}

function addDots() {
    const dots = document.createElement("span");
    dots.innerText = "...";
    dots.classList.add("pagination-dots");
    document.getElementById("paginationDiameterNumbers").appendChild(dots);
}

async function nextPage() {
    let idDocument = document.querySelector("#idDocument").value;
    if (currentPageOne < totalPagesOne) {
        currentPageOne++;
        var response = await forestCropsInformTrialArea.getOneTable(idDocument,currentPageOne);
        APP.countOneTable = response.count;
        APP.oneTableList = response.data
        renderOneTablePage();

        updateButtonsOne();
        updatePaginationOneInfo();

        updatePaginationOneControls();
    }
}

async function prevPage() {
    let idDocument = document.querySelector("#idDocument").value;
    if (currentPageOne > 1) {
        currentPageOne--;

        var response = await forestCropsInformTrialArea.getOneTable(idDocument,currentPageOne);
        APP.countOneTable = response.count;
        APP.oneTableList = response.data
        renderOneTablePage();

        updateButtonsOne();
        updatePaginationOneInfo();

        updatePaginationOneControls();
    }
}