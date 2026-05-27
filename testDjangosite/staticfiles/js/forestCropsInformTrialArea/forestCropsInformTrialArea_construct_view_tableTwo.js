// Пагинации
let currentPageTwo = 1;           // Текущая страница
let totalPagesTwo = 1;

// Обновления таблицы с пагинацией
function updateDataInTwoTable() {

    totalPagesTwo = Math.ceil(APP.countTwoTable / APP.limit);

    updatePaginationTwoControls();

    currentPageTwo = 1;

    updatePaginationTwoInfo();
    renderTwoTablePage();
    updateButtonsTwoState();
}
function renderTwoTablePage() {
    let sampleListTwoTbodyNode = document.querySelector("#sampleListMolodnyk");
    let newHtml = "";

    for(let i = 0; i < APP.twoTableList.length; i++) {
        const currentItemTwo = APP.twoTableList[i];
        const recordIdTwo = currentItemTwo.id;

        const isMarkedForDeleteTwo = APP.deleteTwoTable.includes(recordIdTwo);
        var newHtmlBreeds = "";

        for(var j = 0; j < APP.breeds.length; j++) {
            if(APP.twoTableList[i].idBreed == APP.breeds[j].id) {
                newHtmlBreeds = newHtmlBreeds + "<option selected value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            } else {
                newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name_breed + "</option>";
            }
        }
        const rowClass = `cursorPointer ${isMarkedForDeleteTwo ? 'highlighted' : ''}`;

                newHtml += `<tr class="${rowClass}" data-id="${recordIdTwo}">
                        <td class="textAlignCenter td8"><select type="text" name="id_breed${i}" id="id_breed${i}" style="width: 180px; border: none; text-align: center; background: transparent; outline: none; box-shadow: none; -webkit-appearance: none; -moz-appearance: none; appearance: none;">${newHtmlBreeds}</select></td>
                        <td class="textAlignCenter td9">${APP.twoTableList[i].to0_5}</td>
                        <td class="textAlignCenter td9">${APP.twoTableList[i].from0_6To1_5}</td>
                        <td class="textAlignCenter td5">${APP.twoTableList[i].from1_5}</td>` +
                        "<td style=\"width: 1%; cursor: pointer; text-align: center\">" +
                            "<svg onclick=\"deleteLineInTwoTable(" +APP.twoTableList[i].id + ")\" class=\"cursorPointer\" width=\"23px\" height=\"23px\" viewBox=\"0 0 24 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">" +
                                "<g id=\"SVGRepo_bgCarrier\" stroke-width=\"0\"></g> " +
                                "<g id=\"SVGRepo_tracerCarrier\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></g>" +
                                "<g id=\"SVGRepo_iconCarrier\">" +
                                    "<path d=\"M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17\" stroke=\"#000000\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path> " +
                                "</g>" +
                            "</svg>" +
                        "</td>" +
                        "<td style=\"width: 1%; cursor: pointer; text-align: center\">" +
                            "<svg onClick=\"event.stopPropagation();editLineInTwoTable(" +APP.twoTableList[i].id + ")\" xmlns=\"http://www.w3.org/2000/svg\" width=\"16\" height=\"16\" fill=\"currentColor\" class=\"bi bi-pencil\" viewBox=\"0 0 16 16\">" +
                              "<path d=\"M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325\"/>"
                            "</svg>" +
                        "</td>" +
                    `</tr>`;
    }

    sampleListTwoTbodyNode.innerHTML = newHtml;
}
function editLineInTwoTable(id, event) {
    if (event) {
        event.stopPropagation();
    }
    const record = APP.twoTableList.find(item => item.id === id);
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
    const currentTo0_5 = record.to0_5;
    const currentFrom06 = record.from0_6To1_5;
    const currentFrom1_5 = record.from1_5;

    // Создаем select для породы
    let breedOptions = "";
    for (let j = 0; j < APP.breeds.length; j++) {
        const selected = APP.breeds[j].id === currentBreed ? "selected" : "";
        breedOptions += `<option value="${APP.breeds[j].id}" ${selected}>${APP.breeds[j].name_breed}</option>`;
    }

    // Заменяем содержимое ячеек на поля ввода с кнопками
    cells[0].innerHTML = `<select class="edit-breed" data-field="breed" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">${breedOptions}</select>`;
    cells[1].innerHTML = `<input type="number" class="edit-to5" data-field="to0_5" value="${currentTo0_5}"  style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;
    cells[2].innerHTML = `<input type="number" class="edit-from6" data-field="from0_6To1_5" value="${currentFrom06}" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;
    cells[3].innerHTML = `<input type="number" class="edit-from1_5" data-field="from1_5" value="${currentFrom1_5}" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;

    // Добавляем кнопки сохранить/отменить в последнюю ячейку
    cells[5].innerHTML = `
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
    const newTo0_5 = targetRow.cells[1].querySelector('.edit-to5')?.value;
    const newFrom0_6To1_5 = targetRow.cells[2].querySelector('.edit-from6')?.value;
    const newFrom1_5 = targetRow.cells[3].querySelector('.edit-from1_5')?.value;

    if (!newBreed || !newTo0_5 === undefined || !newFrom0_6To1_5 === undefined || !newFrom1_5 === undefined) {
        console.error('Не удалось получить значения');
        return;
    }
    const to0_5Value = truncateTo2Decimals(newTo0_5);
    const from0_6Value = truncateTo2Decimals(newFrom0_6To1_5);
    const from1_5Value = truncateTo2Decimals(newFrom1_5);


    APP.dateUpdateTwo.push({
        id: id,
        idBreed: Number(newBreed),
        to0_5: to0_5Value,
        from0_6To1_5: from0_6Value,
        from1_5: from1_5Value,
    });

    // Обновляем данные в APP.twoTableList
    const index = APP.twoTableList.findIndex(item => item.id === id);
    if (index !== -1) {
        APP.twoTableList[index] = {
            ...APP.twoTableList[index],
            idBreed: Number(newBreed),
            to0_5: to0_5Value,
            from0_6To1_5: from0_6Value,
            from1_5: from1_5Value,
        };
    }

    updateDataInTwoTable();
}


function deleteLineInTwoTable(del_id){

    hasUnsavedChanges = true;

    APP.deleteTwoTable.push(del_id);

    if (!APP.deleteTwoTable.includes(del_id)) {
        APP.deleteTwoTable.push(del_id);
    }

    updateDataInTwoTable();
}

 async function createTwoSample() {
    let id = document.querySelector("#idDocument").value;
    let idBreed = document.getElementById("breedPodrost");
    let to0_5 = document.getElementById("to0_5");
    let from0_6To1_5 = document.getElementById("from0_6To1_5");
    let from1_5 = document.getElementById("from1_5");

    const to0_5Value = truncateTo2Decimals(to0_5.value);
    const from0_6To1_5Value = truncateTo2Decimals(from0_6To1_5.value);
    const from1_5rValue = truncateTo2Decimals(from1_5.value);

    APP.createTwoSample = [];

    let table = {
        to0_5: Number(to0_5.value),
        from0_6To1_5: Number(from0_6To1_5.value),
        from1_5: Number(from1_5.value),
        idBreed: Number(idBreed.value),
    }
    APP.createTwoSample.push(table);

    var data = {
        idSample: Number(id),
        values : APP.createTwoSample
    };
    APP.createTwoSample = [];
    await forestCropsInformTrialArea.createTwoSample(data);
    var twoTableResp = await forestCropsInformTrialArea.getTwoTable(id,1);
    APP.countTwoTable = twoTableResp.count;
    APP.twoTableList = twoTableResp.data

    updateDataInTwoTable();
    closeAddForm("form-add-Podrost");

    console.log('Добавлена запись:', data);
    console.log('Все записи:', APP.twoTableList);

}

function updatePaginationTwoInfo() {

    const itemsOnCurrentPage = currentPageTwo*APP.limit;

    document.getElementById("totalPodrostCount").innerText = APP.countTwoTable;

    document.getElementById("pagePodrostInfo").innerText = `Страница ${currentPageTwo} из ${totalPagesTwo}`;

    if (APP.countTwoTable === 0) {
        document.getElementById("itemsPodrostInfo").innerText = `Нет записей`;
    } else if (itemsOnCurrentPage != totalPagesTwo) {
        document.getElementById("itemsPodrostInfo").innerText = `Показано ${itemsOnCurrentPage} из ${APP.countTwoTable} записей`;
    } else {
        document.getElementById("itemsPodrostInfo").innerText = `Показано ${itemsOnCurrentPage} из ${APP.countTwoTable} записей (последняя страница)`;
    }
}

// Обновление кнопок
function updateButtonsTwoState() {
    const prevBtn = document.getElementById("prevPodrostPageBtn");
    const nextBtn = document.getElementById("nextPodrostPageBtn");

    if (prevBtn) {
        prevBtn.disabled = currentPageTwo === 1;
    }

    if (nextBtn) {
        nextBtn.disabled = currentPageTwo === totalPagesTwo;
    }
}

// Обновление элементов управления пагинацией
function updatePaginationTwoControls() {
    const paginationNumbers = document.getElementById("paginationPodrostNumbers");
    if (!paginationNumbers) return;

    paginationNumbers.innerHTML = "";

    // Показываем первые 2 страницы
    if (totalPagesTwo <= 5) {
        for (let i = 1; i <= totalPagesTwo; i++) {
            addPageTwoButton(i);
        }
    } else {
        // Всегда показываем первые 2 страницы
        addPageTwoButton(1);
        addPageTwoButton(2);

        // Определяем, нужно ли многоточие
        if (currentPageTwo > 4) {
            addDots();  // Многоточие
        }

        // Показываем текущую страницу и соседние
        let startPage = Math.max(3, currentPageTwo - 1);
        let endPage = Math.min(totalPagesTwo - 2, currentPageTwo + 1);

        for (let i = startPage; i <= endPage; i++) {
            if (i > 2 && i < totalPagesTwo - 1) {
                addPageTwoButton(i);
            }
        }

        // Определяем, нужно ли многоточие в конце
        if (currentPageTwo < totalPagesTwo - 3) {
            addDots();
        }

        // Показываем последние 2 страницы
        addPageTwoButton(totalPagesTwo - 1);
        addPageTwoButton(totalPagesTwo);
    }
}

function addPageTwoButton(pageNum) {
    const button = document.createElement("button");
    let idDocument = document.querySelector("#idDocument").value;
    button.innerText = pageNum;
    button.classList.add("page-number");
    if (pageNum === currentPageTwo) {
        button.classList.add("active");
    }
    button.addEventListener("click", async () => {
        currentPageTwo = pageNum;

        var response = await forestCropsInformTrialArea.getTwoTable(idDocument,pageNum);

        APP.countTwoTable = response.count;
        APP.twoTableList = response.data
        renderTwoTablePage();

        updateButtonsTwoState();
        updatePaginationTwoInfo();

        updatePaginationTwoControls();
    });
    document.getElementById("paginationPodrostNumbers").appendChild(button);
}

function addDots() {
    const dots = document.createElement("span");
    dots.innerText = "...";
    dots.classList.add("pagination-dots");
    document.getElementById("paginationPodrostNumbers").appendChild(dots);
}

async function nextPage() {
    let idDocument = document.querySelector("#idDocument").value;
    if (currentPageTwo < totalPagesTwo) {
        currentPageTwo++;
        var response = await forestCropsInformTrialArea.getTwoTable(idDocument,currentPageTwo);
        APP.countTwoTable = response.count;
        APP.twoTableList = response.data
        renderTwoTablePage();

        updateButtonsTwoState();
        updatePaginationTwoInfo();

        updatePaginationTwoControls();
    }
}

async function prevPage() {
    let idDocument = document.querySelector("#idDocument").value;
    if (currentPage > 1) {
        currentPage--;

        var response = await forestCropsInformTrialArea.getTwoTable(idDocument,currentPageTwo);
        APP.countTwoTable = response.count;
        APP.twoTableList = response.data
        renderTwoTablePage();

        updateButtonsTwoState();
        updatePaginationTwoInfo();

        updatePaginationTwoControls();
    }
}