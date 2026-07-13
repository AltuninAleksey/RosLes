// Пагинации
let currentPageOne = 1;           // Текущая страница
let totalPagesOne = 1;

// Обновления таблицы с пагинацией
function updateDataInOneTable() {

    totalPagesOne = Math.ceil(APP.countOneTable / APP.limit);

    updatePaginationOneControls();

    //currentPageOne = 1;
    if (totalPagesOne > 0 && currentPageOne > totalPagesOne) {
        currentPageOne = totalPagesOne;
    }
    if (currentPageOne < 1) {
        currentPageOne = 1;
    }
    updatePaginationOneInfo();
    renderOneTablePage();
    updateButtonsOne();
}
function renderOneTablePage() {
    let sampleListOneTbodyNode = document.querySelector("#sampleList");
    let newHtml = "";

    for(let i = 0; i < APP.oneTableList.length; i++) {

        var newHtmlBreeds = "";

        for(var j = 0; j < APP.breeds.length; j++) {
            if(APP.oneTableList[i].idBreed == APP.breeds[j].id) {
                newHtmlBreeds = newHtmlBreeds + "<option selected value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name + "</option>";
            } else {
                newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name + "</option>";
            }
        }

                newHtml += `<tr class="cursorPointer" data-one-id="${APP.oneTableList[i].id}">
                        <td class="textAlignCenter td8"><select class="breedSelect" type="text" name="id_breed${i}" id="id_breed${i}">${newHtmlBreeds}</select></td>
                        <td class="textAlignCenter td9">${APP.oneTableList[i].diameter}</td>
                        <td class="textAlignCenter td5">${APP.oneTableList[i].height}</td>
                        <td style="width: 1%; cursor: pointer; text-align: center">
                            <svg onclick="deleteLineInOneTable(${APP.oneTableList[i].id})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </td>
                        <td style="width: 1%; cursor: pointer; text-align: center">
                            <svg onclick="event.stopPropagation();editLineInOneTable(${APP.oneTableList[i].id})" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">
                                <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                            </svg>
                        </td>
                    </tr>`;
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

    const targetRow = document.querySelector(`tr[data-one-id="${id}"]`);
    if (!targetRow) return;

    const cells = targetRow.cells;

    const currentBreed = record.idBreed;
    const currentDiameter = record.diameter;
    const currentHeight = record.height;

    const selectId = `edit-breed-${id}`;
    const hiddenInputId = `edit-selected-breed-${id}`;
    const triggerId = `edit-trigger-${id}`;
    const breedSearchInput = `edit-breed-search-input-${id}`;
    const breedOptions = `edit-breed-options-${id}`;
    const breedSelected = `edit-breed-select-${id}`;

    let breedSelectHtml = `
        <div class="custom-select edit-custom-select" id="${selectId}" style="width: 100%;">
            <div class="custom-select-trigger" id="${triggerId}" style="border: none; cursor: pointer; border-bottom: 1px solid #40E0D0; border-radius: 0; padding: 6px 12px; min-height: 35px;">
                <input type="text" id="${breedSearchInput}"
                       placeholder="Поиск"
                       style="flex: 1; border: none; outline: none; font-size: 14px; padding: 4px 0; background: transparent;">
                <span class="selected-value" id="${breedSelected}" style="display: none;">
                    <span class="selected-name">Выберите породу</span>
                    <span class="selected-short"></span>
                </span>
                <span class="arrow" style="color: #40E0D0; font-size: 12px">▼</span>
            </div>
            <div class="custom-select-options" id="${breedOptions}" style="margin-top: 4px; border-color: #40E0D0;">
            </div>
        </div>
        <input type="hidden" id="${hiddenInputId}" value="${currentBreed}">
    `;

    cells[0].innerHTML = breedSelectHtml;

    const selectContainer = document.getElementById(selectId);
    if (selectContainer) {
        const optionsContainer = document.getElementById(breedOptions);
        const selectedValue = document.getElementById(breedSelected);
        const hiddenInput = document.getElementById(hiddenInputId);
        const searchBreedInput = document.getElementById(breedSearchInput);
        const trigger = document.getElementById(triggerId);

        let currentDataBreed = APP.breeds || [];
        let selectedIdBreed = currentBreed || null;
        let hasChangesBreed = false;

        const originalBreedId = currentBreed;

        function restoreOriginalBreedValue() {
            if (originalBreedId) {
                const originalBreed = currentDataBreed.find(item => item.id === originalBreedId);
                if (originalBreed) {
                    selectedIdBreed = originalBreedId;
                    selectedValue.innerHTML = `
                        <span class="selected-name">${originalBreed.name}</span>
                        <span class="selected-short">${originalBreed.shortName || ''}</span>
                    `;
                    selectedValue.classList.add('active');
                    searchBreedInput.style.display = 'none';
                    selectedValue.style.display = 'block';
                    hiddenInput.value = originalBreedId;
                    hasChangesBreed = false;
                    return true;
                }
            } else {
                selectedIdBreed = null;
                selectedValue.classList.remove('active');
                selectedValue.style.display = 'none';
                searchBreedInput.style.display = 'block';
                searchBreedInput.value = '';
                hiddenInput.value = '';
                hasChangesBreed = false;
                return false;
            }
        }

        function checkAndRestoreBreed() {
            if (!hasChangesBreed && selectedIdBreed !== originalBreedId) {
                restoreOriginalBreedValue();
                return true;
            }
            if (!selectedIdBreed && originalBreedId) {
                restoreOriginalBreedValue();
                return true;
            }
            return false;
        }

        function showSelectedBreedMode() {
            if (selectedIdBreed) {
                const selectedItem = currentDataBreed.find(item => item.id === selectedIdBreed);
                if (selectedItem) {
                    selectedValue.innerHTML = `
                        <span class="selected-name">${selectedItem.name}</span>
                        <span class="selected-short">${selectedItem.shortName || ''}</span>
                    `;
                    selectedValue.classList.add('active');
                    selectedValue.style.display = 'block';
                    searchBreedInput.style.display = 'none';
                    searchBreedInput.value = '';
                    return;
                }
            } else {
                selectedValue.style.display = 'none';
                selectedValue.classList.remove('active');
                searchBreedInput.style.display = 'block';
                searchBreedInput.value = '';
            }
        }

        function filterOptionsBreed(searchText) {
            const search = searchText.toLowerCase().trim();
            if (!search) {
                renderBreedOptions(currentDataBreed);
                return;
            }
            const filtered = currentDataBreed.filter(item =>
                item.name.toLowerCase().includes(search) ||
                (item.shortName && item.shortName.toLowerCase().includes(search))
            );
            renderBreedOptions(filtered, search);
        }

        function renderBreedOptions(options, searchText = '') {
            optionsContainer.innerHTML = '';

            if (!options || options.length === 0) {
                const empty = document.createElement('div');
                empty.className = 'custom-option empty';
                empty.textContent = searchText ? 'Ничего не найдено' : 'Нет доступных пород';
                optionsContainer.appendChild(empty);
                return;
            }

            options.forEach(item => {
                const option = document.createElement('div');
                option.className = 'custom-option';
                option.dataset.value = item.id;
                const isSelected = item.id === selectedIdBreed;
                if (isSelected) {
                    option.classList.add('selected');
                }
                option.innerHTML = `
                    <div class="option-name">${item.name}</div>
                    <div class="option-short">${item.shortName || ''}</div>
                `;

                option.addEventListener('click', function(e) {
                    e.stopPropagation();
                    selectedIdBreed = item.id;
                    hasChangesBreed = true;

                    if (selectedValue) {
                        selectedValue.innerHTML = `
                            <span class="selected-name">${item.name}</span>
                            <span class="selected-short">${item.shortName || ''}</span>
                        `;
                    }

                    if (hiddenInput) {
                        hiddenInput.value = item.id;
                    }

                    selectedValue.classList.add('active');
                    searchBreedInput.style.display = 'none';
                    selectedValue.style.display = 'block';

                    optionsContainer.querySelectorAll('.custom-option').forEach(opt => {
                        opt.classList.remove('selected');
                    });
                    option.classList.add('selected');

                    selectContainer.classList.remove('open');
                });

                optionsContainer.appendChild(option);
            });
        }

        if (currentBreed) {
            const selectedBreed = currentDataBreed.find(item => item.id === currentBreed);
            if (selectedBreed) {
                selectedIdBreed = currentBreed;
                selectedValue.innerHTML = `
                    <span class="selected-name">${selectedBreed.name}</span>
                    <span class="selected-short">${selectedBreed.shortName || ''}</span>
                `;
                selectedValue.classList.add('active');
                searchBreedInput.style.display = 'none';
                selectedValue.style.display = 'block';
                hiddenInput.value = currentBreed;
            }
        } else {
            searchBreedInput.style.display = 'block';
            selectedValue.style.display = 'none';
        }

        renderBreedOptions(currentDataBreed);

        searchBreedInput.addEventListener('input', function(e) {
            e.stopPropagation();
            const value = this.value;
            if (value.trim()) {
                selectContainer.classList.add('open');
                filterOptionsBreed(value);
            } else {
                renderBreedOptions(currentDataBreed);
            }
        });

        if (trigger) {
            trigger.addEventListener('click', function(e) {
                e.stopPropagation();
                if (e.target === searchBreedInput) {
                    return;
                }
                const wasOpen = selectContainer.classList.contains('open');
                if (wasOpen) {
                    selectContainer.classList.remove('open');
                    if (!hasChangesBreed && selectedIdBreed !== originalBreedId) {
                        restoreOriginalBreedValue();
                    } else {
                        showSelectedBreedMode();
                    }
                    return;
                }

                if (selectedIdBreed) {
                    searchBreedInput.style.display = 'block';
                    searchBreedInput.value = '';
                    selectedValue.style.display = 'none';
                    selectedValue.classList.remove('active');
                    searchBreedInput.focus();
                    renderBreedOptions(currentDataBreed);
                } else {
                    searchBreedInput.style.display = 'block';
                    searchBreedInput.value = '';
                    selectedValue.style.display = 'none';
                    searchBreedInput.focus();
                    renderBreedOptions(currentDataBreed);
                }

                document.querySelectorAll('.edit-custom-select.open').forEach(el => {
                    if (el !== selectContainer) {
                        el.classList.remove('open');
                    }
                });

                selectContainer.classList.add('open');
            });
        }
        document.addEventListener('click', function closeSelect(e) {
        if (!selectContainer.contains(e.target) && selectContainer.classList.contains('open')) {
            selectContainer.classList.remove('open');
            if (!hasChangesBreed && selectedIdBreed !== originalBreedId) {
                restoreOriginalBreedValue();
            } else {
                showSelectedBreedMode();
            }
        }
    });
    }

    //    // Заменяем содержимое ячеек на поля ввода с кнопками
    //cells[0].innerHTML = `<select class="edit-breed" data-field="breed" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">${breedOptions}</select>`;
    cells[1].innerHTML = `<input type="number" step="0.1" min="0" class="edit-diameter" data-field="diameter" value="${currentDiameter}"  style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;
    cells[2].innerHTML = `<input type="number" step="0.1" min="0" class="edit-height" data-field="height" value="${currentHeight}" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;

    // Добавляем кнопки сохранить/отменить в последнюю ячейку
    cells[4].innerHTML = `
        <button onclick="saveInlineOneEdit(${id})" style="margin-right: 5px; padding: 5px 10px; border: none; background: transparent;">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check-lg" viewBox="0 0 16 16">
              <path d="M12.736 3.97a.733.733 0 0 1 1.047 0c.286.289.29.756.01 1.05L7.88 12.01a.733.733 0 0 1-1.065.02L3.217 8.384a.757.757 0 0 1 0-1.06.733.733 0 0 1 1.047 0l3.052 3.093 5.4-6.425z"/>
            </svg>
        </button>
    `;
}

function truncateTo2Decimals(value) {
    if (value === undefined || value === null || value === '') return 0;

    let str = String(value).replace(',', '.');

    let num = parseFloat(str);
    if (isNaN(num)) return 0;

    return Math.floor(num * 100) / 100;
}

const trigger = document.querySelector('.custom-select-trigger');
const options = document.querySelector('.custom-select-options');

function positionDropdown() {
  if (!trigger || !options) return;

  const triggerRect = trigger.getBoundingClientRect();
  const spaceBelow = window.innerHeight - triggerRect.bottom;
  const neededHeight = Math.min(250, options.scrollHeight); // max-height из CSS

  if (spaceBelow < neededHeight) {
    // Открываем вверх
    options.style.bottom = `${window.innerHeight - triggerRect.top}px`;
    options.style.top = 'auto';
  } else {
    // Открываем вниз
    options.style.top = `${triggerRect.bottom + window.scrollY}px`;
    options.style.bottom = 'auto';
  }
}
function saveInlineOneEdit(id) {

    const targetRow = document.querySelector(`tr[data-one-id="${id}"]`);
    if (!targetRow) return;

    // Получаем новые значения из полей ввода
    const hiddenInput = document.getElementById(`edit-selected-breed-${id}`);
    const newBreed = hiddenInput ? hiddenInput.value : null;
    //const newBreed = targetRow.cells[0].querySelector('.edit-breed')?.value;
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

async function deleteLineInOneTable(del_id) {
    let id = document.querySelector("#idDocument").value;
    hasUnsavedChanges = true;

    if (!APP.deleteOneTable.includes(del_id)) {
        APP.deleteOneTable.push(del_id);
    }
    if(APP.deleteOneTable.length > 0) {
         var oneDell = {
            values : APP.deleteOneTable
        };
        await forestCropsInformTrialArea.deleteSampleOne(oneDell);
    }
    APP.deleteOneTable = [];
    var oneTableResp = await forestCropsInformTrialArea.getOneTable(id,currentPageOne);
    APP.countOneTable = oneTableResp.count;
    APP.oneTableList = oneTableResp.data

    let newTotalPagesOne = Math.ceil(APP.countOneTable / APP.limit);

    if (currentPageOne > newTotalPagesOne && newTotalPagesOne > 0) {
        currentPageOne = newTotalPagesOne;
    } else if (newTotalPagesOne  === 0) {
        currentPageOne = 1;
    }

    if (currentPageOne < 1) {
        currentPageOne = 1;
    }

    if (currentPageOne !== oneTableResp.currentPageOne) {
        var correctedResp = await forestCropsInformTrialArea.getOneTable(id, currentPageOne);
        APP.countOneTable = correctedResp.count;
        APP.oneTableList = correctedResp.data;
    }

    updateDataInOneTable();
}

//async function createOneSample() {
//    let id = document.querySelector("#idDocument").value;
//    let idBreed = document.getElementById("breedDiameter");
//    let height = document.getElementById("height");
//    let diameter = document.getElementById("diameter");
//
//    const diameterValue = truncateTo2Decimals(diameter.value);
//    const heightValue = truncateTo2Decimals(height.value);
//
//    APP.createOneSample = [];
//
//    let table = {
//        idBreed: Number(idBreed.value),
//        height: heightValue,
//        diameter: diameterValue,
//    }
//    APP.createOneSample.push(table);
//
//    var data = {
//        idSample: Number(id),
//        values : APP.createOneSample
//    };
//    APP.createOneSample = [];
//    height.value = "";
//    diameter.value = "";
//    await forestCropsInformTrialArea.createOneSample(data);
//    var oneTableResp = await forestCropsInformTrialArea.getOneTable(id,1);
//    APP.countOneTable = oneTableResp.count;
//    APP.oneTableList = oneTableResp.data
//
//    updateDataInOneTable();
//    closeAddForm("form-add-diameter");
//}

function replaceSpacesWithSemicolon(inputId) {
    const input = document.getElementById(inputId);

    input.addEventListener('input', function() {
        const cursorPos = this.selectionStart;
        const originalValue = this.value;
        let newValue = originalValue.replace(/\s/g, ';');
        newValue = newValue.replace(/;+/g, ';');
        if (originalValue !== newValue) {
            this.value = newValue;
            // корректируем позицию курсора, если она сместилась из‑за замены
            const diff = newValue.length - originalValue.length;
            this.setSelectionRange(cursorPos + diff, cursorPos + diff);
        }
    });
}

// Использование:
replaceSpacesWithSemicolon('height');
replaceSpacesWithSemicolon('diameter');
// Функция для разбора строки с разделителем ; в массив чисел
function parseMultipleValues(inputString) {
    if (!inputString || inputString.trim() === '') {
        return [];
    }
    // Разделяем по ; и преобразуем в числа
    const values = inputString.split(';')
        .map(v => v.trim()) // Убираем пробелы вокруг
        .filter(v => v !== '') // Убираем пустые значения
        .map(v => {
            // Заменяем запятую на точку и преобразуем в число
            const num = parseFloat(v.replace(',', '.'));
            // Проверяем на валидность
            return isNaN(num) ? null : truncateTo2Decimals(num);
        })
        .filter(v => v !== null && v > 0); // Убираем некорректные значения

    return values;
}

//// Основная функция создания записей
async function createOneSample() {
    try {
        let id = document.querySelector("#idDocument").value;
        let idBreed = document.getElementById("breedDiameter");
        let height = document.getElementById("height");
        let diameter = document.getElementById("diameter");

        // Получаем значения и разбираем их
        const heightValues = parseMultipleValues(height.value);
        const diameterValues = parseMultipleValues(diameter.value);

        // Проверяем, что массивы не пустые
        if (heightValues.length === 0 || diameterValues.length === 0) {
            alert('Пожалуйста, заполните оба поля!');
            return;
        }

//        // Проверяем, что количество значений совпадает
//        if (heightValues.length !== diameterValues.length) {
//            alert(`Количество значений не совпадает!\nВысота: ${heightValues.length} шт\nДиаметр: ${diameterValues.length} шт\nДолжно быть одинаковое количество.`);
//            return;
//        }
        if (heightValues.length !== diameterValues.length) {
            const maxLen = Math.max(heightValues.length, diameterValues.length);
            while (heightValues.length < maxLen) heightValues.push(0);
            while (diameterValues.length < maxLen) diameterValues.push(0);
        }
        // Создаем массив записей
        APP.createOneSample = [];
        for (let i = 0; i < heightValues.length; i++) {
            APP.createOneSample.push({
                idBreed: Number(APP.breedSelectId),
                diameter: diameterValues[i],
                height: heightValues[i],
            });
        }

        var data = {
            idSample: Number(id),
            values: APP.createOneSample
        };
        await forestCropsInformTrialArea.createOneSample(data);

        height.value = "";
        diameter.value = "";
        APP.createOneSample = [];

        var oneTableResp = await forestCropsInformTrialArea.getOneTable(id, 1);
        APP.countOneTable = oneTableResp.count;
        APP.oneTableList = oneTableResp.data;

        updateDataInOneTable();
        closeAddForm("form-add-diameter");

    } catch (error) {
        console.error('Ошибка при добавлении:', error);
        alert('Ошибка при добавлении записей');
    }

}

function updatePaginationOneInfo() {

    const itemsOnCurrentPage = Math.min(currentPageOne * APP.limit, APP.countOneTable);

    document.getElementById("totalDiameterCount").innerText = APP.countOneTable;

    document.getElementById("pageDiameterInfo").innerText = `Страница ${currentPageOne} из ${totalPagesOne || 1}`;

    if (APP.countOneTable === 0) {
        document.getElementById("itemsDiameterInfo").innerText = `Нет записей`;
    } else if (itemsOnCurrentPage !=  APP.countOneTable) {
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
        nextBtn.disabled = currentPageOne === totalPagesOne || totalPagesOne === 0;
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

async function nextPageDiameter() {
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

async function prevPageDiameter() {
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