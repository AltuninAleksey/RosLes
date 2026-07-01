// Пагинации
let currentPageTwo = 1;           // Текущая страница
let totalPagesTwo = 1;

// Обновления таблицы с пагинацией
function updateDataInTwoTable() {

    totalPagesTwo = Math.ceil(APP.countTwoTable / APP.limit);

    updatePaginationTwoControls();

    //currentPageTwo = 1;
    if (totalPagesTwo > 0 && currentPageTwo > totalPagesTwo) {
        currentPageTwo = totalPagesTwo;
    }
    if (currentPageTwo < 1) {
        currentPageTwo = 1;
    }
    updatePaginationTwoInfo();
    renderTwoTablePage();
    updateButtonsTwoState();
}
function renderTwoTablePage() {
    let sampleListTwoTbodyNode = document.querySelector("#sampleListMolodnyk");
    let newHtml = "";

    for(let i = 0; i < APP.twoTableList.length; i++) {

        var newHtmlBreeds = "";

        for(var j = 0; j < APP.breeds.length; j++) {
            if(APP.twoTableList[i].idBreed == APP.breeds[j].id) {
                newHtmlBreeds = newHtmlBreeds + "<option selected value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name + "</option>";
            } else {
                newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name + "</option>";
            }
        }

                newHtml += `<tr class="cursorPointer" data-two-id="${APP.twoTableList[i].id}">
                        <td class="textAlignCenter td8"><select class="breedSelect" type="text" name="id_breed${i}" id="id_breed${i}">${newHtmlBreeds}</select></td>
                        <td class="textAlignCenter td9">${APP.twoTableList[i].to0_5}</td>
                        <td class="textAlignCenter td9">${APP.twoTableList[i].from0_6To1_5}</td>
                        <td class="textAlignCenter td9">${APP.twoTableList[i].from1_5}</td>
                        <td class="textAlignCenter td5">${APP.twoTableList[i].maxHeight}</td>
                        <td style="width: 1%; cursor: pointer; text-align: center">
                            <svg onclick="deleteLineInTwoTable(${APP.twoTableList[i].id})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </td>
                        <td style="width: 1%; cursor: pointer; text-align: center">
                            <svg onclick="event.stopPropagation();editLineInTwoTable(${APP.twoTableList[i].id})" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">
                                <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                            </svg>
                        </td>
                    </tr>`;
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
    const targetRow = document.querySelector(`tr[data-two-id="${id}"]`);
    if (!targetRow) return;

    const cells = targetRow.cells;

    // Сохраняем текущие значения
    const currentBreed = record.idBreed;
    const currentTo0_5 = record.to0_5;
    const currentFrom06 = record.from0_6To1_5;
    const currentFrom1_5 = record.from1_5;
    const currentMaxHeight = record.maxHeight;

    const selectPodrostId = `edit-breed-${id}`;
    const hiddenInputPodrost = `edit-selected-breed-podrost-${id}`;
    // Создаем select для породы
//    let breedOptions = "";
//    for (let j = 0; j < APP.breeds.length; j++) {
//        const selected = APP.breeds[j].id === currentBreed ? "selected" : "";
//        breedOptions += `<option value="${APP.breeds[j].id}" ${selected}>${APP.breeds[j].name}</option>`;
//    }
     let breedSelectHtml = `
        <div class="custom-select edit-custom-podrost-select" id="${selectPodrostId}" style="width: 100%;">
            <div class="custom-select-trigger" id="podrostTrigger" style="border: none; border-bottom: 1px solid #40E0D0; border-radius: 0; padding: 6px 12px; min-height: 35px;">
                <span class="selected-value" id="podrostSelected">
                    <span class="selected-name">Выберите породу</span>
                    <span class="selected-short"></span>
                </span>
                <span class="arrow" style="color: #40E0D0;">▼</span>
            </div>
            <div class="custom-select-options" id="podrostOptions" style="border-color: #40E0D0;top: 0; bottom: 40px;">
            </div>
        </div>
        <input type="hidden" id="${hiddenInputPodrost}" value="${currentBreed}">
    `;

    cells[0].innerHTML = breedSelectHtml;

    const selectContainer = document.getElementById(selectPodrostId);
    if (selectContainer) {

       const optionsContainerPodrost = document.getElementById('podrostOptions');
       const selectedPodrostValue = document.getElementById('podrostSelected');
       const hiddenPodrostInputId = document.getElementById(hiddenInputPodrost);

        if (optionsContainerPodrost) {
            optionsContainerPodrost.innerHTML = '';

            APP.breeds.forEach(item => {
                const option = document.createElement('div');
                option.className = 'custom-option';
                option.dataset.value = item.id;
                const isSelected = item.id === currentBreed;
                if (isSelected) {
                    option.classList.add('selected');
                }
                option.innerHTML = `
                    <div class="option-name">${item.name}</div>
                    <div class="option-short">${item.shortName || ''}</div>
                `;

                option.addEventListener('click', (function(item, selectedPodrostValue, hiddenPodrostInputId, optionsContainerPodrost, selectContainer) {
                    return function(e) {
                        e.stopPropagation();

                        // Обновляем отображение
                        if (selectedPodrostValue) {
                            selectedPodrostValue.innerHTML = `
                                <span class="selected-name">${item.name}</span>
                                <span class="selected-short">${item.shortName || ''}</span>
                            `;
                        }

                        if (hiddenPodrostInputId) {
                            hiddenPodrostInputId.value = item.id;
                        }

                        optionsContainerPodrost.querySelectorAll('.custom-option').forEach(opt => {
                            opt.classList.remove('selected');
                        });
                        option.classList.add('selected');

                        selectContainer.classList.remove('open');
                    };
                })(item, selectedPodrostValue, hiddenPodrostInputId, optionsContainerPodrost, selectContainer));

                optionsContainerPodrost.appendChild(option);
            });

            if (currentBreed) {
                const selectedBreed = APP.breeds.find(item => item.id === currentBreed);
                if (selectedBreed && selectedPodrostValue) {
                    selectedPodrostValue.innerHTML = `
                        <span class="selected-name">${selectedBreed.name}</span>
                        <span class="selected-short">${selectedBreed.shortName || ''}</span>
                    `;
                }
            }
        }

        const podrostTrigger = document.getElementById('podrostTrigger');
        if (podrostTrigger) {

            podrostTrigger.addEventListener('click', function(e) {
                e.stopPropagation();

                document.querySelectorAll('.edit-custom-podrost-select.open').forEach(el => {
                    if (el !== selectContainer) {
                        el.classList.remove('open');
                    }
                });
                selectContainer.classList.toggle('open');

                if (selectContainer.classList.contains('open')) {
                    setTimeout(() => {
                        positionDropdown(selectContainer);
                    }, 10);
                }
            });
        }
    }
    // Заменяем содержимое ячеек на поля ввода с кнопками
    //cells[0].innerHTML = `<select class="edit-breed" data-field="breed" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">${breedOptions}</select>`;
    cells[1].innerHTML = `<input type="number" class="edit-to5" data-field="to0_5" value="${currentTo0_5}"  style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;
    cells[2].innerHTML = `<input type="number" class="edit-from6" data-field="from0_6To1_5" value="${currentFrom06}" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;
    cells[3].innerHTML = `<input type="number" class="edit-from1_5" data-field="from1_5" value="${currentFrom1_5}" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;
    cells[4].innerHTML = `<input type="number" class="edit-maxHeight" data-field="maxHeight" value="${currentMaxHeight}" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;
    // Добавляем кнопки сохранить/отменить в последнюю ячейку
    cells[6].innerHTML = `
        <button onclick="saveInlineTwoEdit(${id})" style="margin-right: 5px; padding: 5px 10px; border: none; background: transparent;">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check-lg" viewBox="0 0 16 16">
              <path d="M12.736 3.97a.733.733 0 0 1 1.047 0c.286.289.29.756.01 1.05L7.88 12.01a.733.733 0 0 1-1.065.02L3.217 8.384a.757.757 0 0 1 0-1.06.733.733 0 0 1 1.047 0l3.052 3.093 5.4-6.425z"/>
            </svg>
        </button>
    `;
    document.addEventListener('click', function closeSelect(e) {
        document.querySelectorAll('.edit-custom-podrost-select.open').forEach(el => {
            if (!el.contains(e.target)) {
                el.classList.remove('open');
            }
        });
    });
}
function positionDropdown(selectContainer) {
    const trigger = selectContainer.querySelector('.custom-select-trigger');
    const options = selectContainer.querySelector('.custom-select-options');

    if (!trigger || !options) return;

    setTimeout(() => {

        const triggerRect = trigger.getBoundingClientRect();
        const optionsHeight = options.scrollHeight || 200;

        const spaceBelow = window.innerHeight - triggerRect.bottom;

        const spaceAbove = triggerRect.top;

        const fitsBelow = spaceBelow >= optionsHeight + 100;
        const fitsAbove = spaceAbove >= optionsHeight + 100;

        options.style.top = '';
        options.style.bottom = '';
        options.style.transform = '';
        options.style.maxHeight = '';

        if (fitsBelow) {
            options.style.top = '100%';
            options.style.bottom = 'auto';
            options.style.transform = 'translateY(5px)';
            options.style.maxHeight = Math.min(optionsHeight, spaceBelow - 20) + 'px';
        } else if (fitsAbove) {
            options.style.top = 'auto';
            options.style.bottom = '100%';
            options.style.transform = 'translateY(-5px)';
            options.style.maxHeight = Math.min(optionsHeight, spaceAbove - 20) + 'px';
        } else {
            // Если не помещается нигде - открываем вниз с ограничением
            options.style.top = '100%';
            options.style.bottom = 'auto';
            options.style.transform = 'translateY(5px)';
            options.style.maxHeight = Math.max(spaceBelow - 20, 150) + 'px';
        }
    }, 5);
}
function truncateTo2Decimals(value) {
    if (value === undefined || value === null || value === '') return 0;

    let str = String(value).replace(',', '.');

    let num = parseFloat(str);
    if (isNaN(num)) return 0;

    return Math.floor(num * 100) / 100;
}
function saveInlineTwoEdit(id) {

    const targetRow = document.querySelector(`tr[data-two-id="${id}"]`);
    if (!targetRow) return;

    // Получаем новые значения из полей ввода
    const hiddenInput = document.getElementById(`edit-selected-breed-podrost-${id}`);
    const newBreed = hiddenInput ? hiddenInput.value : null;
    //const newBreed = targetRow.cells[0].querySelector('.edit-breed')?.value;
    const newTo0_5 = targetRow.cells[1].querySelector('.edit-to5')?.value;
    const newFrom0_6To1_5 = targetRow.cells[2].querySelector('.edit-from6')?.value;
    const newFrom1_5 = targetRow.cells[3].querySelector('.edit-from1_5')?.value;
    const newMaxHeight = targetRow.cells[4].querySelector('.edit-maxHeight')?.value;

    if (!newBreed || !newTo0_5 === undefined || !newFrom0_6To1_5 === undefined || !newFrom1_5 === undefined || !newMaxHeight === undefined) {
        console.error('Не удалось получить значения');
        return;
    }
    const to0_5Value = truncateTo2Decimals(newTo0_5);
    const from0_6Value = truncateTo2Decimals(newFrom0_6To1_5);
    const from1_5Value = truncateTo2Decimals(newFrom1_5);
    const maxHeightValue = truncateTo2Decimals(newMaxHeight);


    APP.dateUpdateTwo.push({
        id: id,
        idBreed: Number(newBreed),
        to0_5: to0_5Value,
        from0_6To1_5: from0_6Value,
        from1_5: from1_5Value,
        maxHeight: maxHeightValue,
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
            maxHeight: maxHeightValue,
        };
    }

    updateDataInTwoTable();
}


async function deleteLineInTwoTable(del_id){
    let id = document.querySelector("#idDocument").value;
    hasUnsavedChanges = true;

    if (!APP.deleteTwoTable.includes(del_id)) {
        APP.deleteTwoTable.push(del_id);
    }
    if(APP.deleteTwoTable.length > 0) {
         var twoDell = {
            values : APP.deleteTwoTable
        };
        await forestCropsInformTrialArea.deleteSampleTwo(twoDell);
    }
    APP.deleteTwoTable = [];
    var twoTableResp = await forestCropsInformTrialArea.getTwoTable(id,currentPageTwo);
    APP.countTwoTable = twoTableResp.count;
    APP.twoTableList = twoTableResp.data

    let newTotalPagesTwo = Math.ceil(APP.countTwoTable / APP.limit);

    if (currentPageTwo > newTotalPagesTwo && newTotalPagesTwo > 0) {
        currentPageTwo = newTotalPagesTwo;
    } else if (newTotalPagesTwo === 0) {
        currentPageTwo = 1;
    }

    if (currentPageTwo < 1) {
        currentPageTwo = 1;
    }

    if (currentPageTwo !== twoTableResp.currentPageTwo) {
        var correctedResp = await forestCropsInformTrialArea.getTwoTable(id, currentPageTwo);
        APP.countTwoTable = correctedResp.count;
        APP.twoTableList = correctedResp.data;
    }

    updateDataInTwoTable();
}

 async function createTwoSample() {
    let id = document.querySelector("#idDocument").value;
    let to0_5 = document.getElementById("to0_5");
    let from0_6To1_5 = document.getElementById("from0_6To1_5");
    let from1_5 = document.getElementById("from1_5");
    let maxHeight = document.getElementById("maxHeight");

    const to0_5Value = truncateTo2Decimals(to0_5.value);
    const from0_6To1_5Value = truncateTo2Decimals(from0_6To1_5.value);
    const from1_5Value = truncateTo2Decimals(from1_5.value);

    APP.createTwoSample = [];

    let table = {
        to0_5: Number(to0_5.value),
        from0_6To1_5: Number(from0_6To1_5.value),
        from1_5: Number(from1_5.value),
        maxHeight: Number(maxHeight.value),
        idBreed: Number(APP.forestPodrostSelectId),
    }
    APP.createTwoSample.push(table);

    var data = {
        idSample: Number(id),
        values : APP.createTwoSample
    };
    APP.createTwoSample = [];
    to0_5.value = "";
    from0_6To1_5.value = "";
    maxHeight.value = "";
    from1_5.value = "";
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

    const itemsOnCurrentPage = Math.min(currentPageTwo * APP.limit, APP.countTwoTable);

    document.getElementById("totalPodrostCount").innerText = APP.countTwoTable;

    document.getElementById("pagePodrostInfo").innerText = `Страница ${currentPageTwo} из ${totalPagesTwo || 1}`;

    if (APP.countTwoTable === 0) {
        document.getElementById("itemsPodrostInfo").innerText = `Нет записей`;
    } else if (itemsOnCurrentPage != APP.countTwoTable) {
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
        nextBtn.disabled = currentPageTwo === totalPagesTwo || totalPagesTwo === 0;
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

async function nextPagePodrost() {
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

async function prevPagePodrost() {
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