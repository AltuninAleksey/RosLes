// Пагинации
let currentPage = 1;           // Текущая страница
let totalPages = 1;

// Обновления таблицы с пагинацией
function updateDataInNullTable() {

    totalPages = Math.ceil(APP.countNullTable / APP.limit);

    updatePaginationControls();

   // currentPage = 1;
    if (totalPages > 0 && currentPage > totalPages) {
        currentPage = totalPages;
    }
    if (currentPage < 1) {
        currentPage = 1;
    }
    updatePaginationInfo();
    renderTablePage();
    updateButtonsState();
}
function renderTablePage() {

    let sampleListTbodyNode = document.querySelector("#sampleListTbody");
    let newHtml = "";
    for(let i = 0; i < APP.nullTableList.length; i++) {
//        const breed = APP.breeds.find(b => b.id === APP.nullTableList[i].idBreed);
//        const breedName = breed ? breed.name : 'Неизвестно';
//        <td class="textAlignCenter td8 breed-display">${breedName}</td>
        var newHtmlBreeds = "";

        for(var j = 0; j < APP.breeds.length; j++) {
            if(APP.nullTableList[i].idBreed == APP.breeds[j].id) {
                newHtmlBreeds = newHtmlBreeds + "<option selected value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name + "</option>";
            } else {
                newHtmlBreeds = newHtmlBreeds + "<option value=\"" + APP.breeds[j].id + "\">" + APP.breeds[j].name + "</option>";
            }
        }

                newHtml += `<tr class="cursorPointer" data-id="${APP.nullTableList[i].id}">
                        <td class="textAlignCenter td8"><select class="breedSelect" type="text" name="id_breed${i}" id="id_breed${i}">${newHtmlBreeds}</select></td>
                        <td class="textAlignCenter td9">${APP.nullTableList[i].countLiving}</td>
                        <td class="textAlignCenter td5">${APP.nullTableList[i].countDead}</td>
                        <td style="width: 1%; cursor: pointer; text-align: center">
                            <svg onclick="deleteLineInNullTable(${APP.nullTableList[i].id})" class="cursorPointer" width="23px" height="23px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </td>
                        <td style="width: 1%; cursor: pointer; text-align: center">
                            <svg onclick="event.stopPropagation();editLineInNullTable(${APP.nullTableList[i].id})" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">
                                <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                            </svg>
                        </td>
                    </tr>`;
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

    const selectForestId = `edit-breed-${id}`;
    const hiddenInputForest = `edit-selected-breed-forest-${id}`;
    const forestTriggerId = `edit-forest-trigger-${id}`;
    const forestSelected = `edit-forest-select-${id}`;
    const forestOptions = `edit-forest-options-${id}`;
    const forestSearchInput = `edit-forest-search-input-${id}`;

     let breedSelectHtml = `
        <div class="custom-select edit-custom-forest-select" id="${selectForestId}" style="width: 100%;position: relative;">
            <div class="custom-select-trigger" id="${forestTriggerId}" style="padding: 6px 12px; border: none; border-radius: 0; border-bottom: 1px solid #40E0D0; cursor: pointer; min-height: 35px;">
                 <input type="text" id="${forestSearchInput}"
                         placeholder="Поиск"
                         style="flex: 1; border: none; outline: none; font-size: 14px; padding: 4px 0; background: transparent;">
                <span class="selected-value" id="${forestSelected}" style="display: none;">
                    <span class="selected-name">Выберите породу</span>
                    <span class="selected-short"></span>
                </span>
                <span class="arrow" style="color: #40E0D0; font-size: 12px;">▼</span>
            </div>
            <div class="custom-select-options" id="${forestOptions}" style="margin-top: 4px; border-color: #40E0D0">
            </div>
        </div>
        <input type="hidden" id="${hiddenInputForest}" value="${currentBreed}">
    `;

    cells[0].innerHTML = breedSelectHtml;

    const selectContainer = document.getElementById(selectForestId);
    if (selectContainer) {

       const optionsContainerForest = document.getElementById(forestOptions);
       const selectedForestValue = document.getElementById(forestSelected);
       const hiddenForestInputId = document.getElementById(hiddenInputForest);
       const searchInput = document.getElementById(forestSearchInput);
       const forestTrigger = document.getElementById(forestTriggerId);

       let currentData = APP.breeds || [];
       let selectedId = currentBreed || null;
       let hasChanges = false;

       const originalBreedId = currentBreed;

       function restoreOriginalValue() {
           if (originalBreedId) {
               const originalBreed = currentData.find(item => item.id === originalBreedId);
               if (originalBreed) {
                   selectedId = originalBreedId;
                   selectedForestValue.innerHTML = `
                       <span class="selected-name">${originalBreed.name}</span>
                       <span class="selected-short">${originalBreed.shortName || ''}</span>
                   `;
                   selectedForestValue.classList.add('active');
                   searchInput.style.display = 'none';
                   selectedForestValue.style.display = 'block';
                   hiddenInput.value = originalBreedId;
                   hasChanges = false;
                   return true;
               }
           } else {
               selectedId = null;
               selectedForestValue.classList.remove('active');
               selectedForestValue.style.display = 'none';
               searchInput.style.display = 'block';
               searchInput.value = '';
               hiddenInput.value = '';
               hasChanges = false;
               return false;
           }
       }

       function checkAndRestore() {
           if (!hasChanges && selectedId !== originalBreedId) {
               restoreOriginalValue();
               return true;
           }
           if (!selectedId && originalBreedId) {
               restoreOriginalValue();
               return true;
           }
           return false;
       }

       function showSelectedMode() {
            if (selectedId) {
                const selectedItem = currentData.find(item => item.id === selectedId);
                if (selectedItem) {
                    selectedForestValue.innerHTML = `
                        <span class="selected-name">${selectedItem.name}</span>
                        <span class="selected-short">${selectedItem.shortName || ''}</span>
                    `;
                    selectedForestValue.classList.add('active');
                    selectedForestValue.style.display = 'block';
                    searchInput.style.display = 'none';
                    searchInput.value = '';
                    return;
                }
            } else {
                selectedForestValue.style.display = 'none';
                selectedForestValue.classList.remove('active');
                searchInput.style.display = 'block';
                searchInput.value = '';
            }
        }

       function filterOptions(searchText) {
            const search = searchText.toLowerCase().trim();

            if (!search) {
                renderOptions(currentData);
                return;
            }

            const filtered = currentData.filter(item =>
                item.name.toLowerCase().includes(search) ||
                (item.shortName && item.shortName.toLowerCase().includes(search))
            );

            renderOptions(filtered, search);
        }

       function renderOptions(options, searchText = '') {
            optionsContainerForest.innerHTML = '';
            if (!options || options.length === 0) {
                const empty = document.createElement('div');
                empty.className = 'custom-option empty';
                empty.textContent = searchText ? 'Ничего не найдено' : 'Нет доступных пород';
                optionsContainerForest.appendChild(empty);
                return;
            }
            options.forEach(item => {
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

                option.addEventListener('click', function(e) {
                        e.stopPropagation();
                        selectedId = item.id;
                        hasChanges = true;
                        // Обновляем отображение
                        if (selectedForestValue) {
                            selectedForestValue.innerHTML = `
                                <span class="selected-name">${item.name}</span>
                                <span class="selected-short">${item.shortName || ''}</span>
                            `;
                        }

                        if (hiddenForestInputId) {
                            hiddenForestInputId.value = item.id;
                        }
                        selectedForestValue.classList.add('active');

                        searchInput.style.display = 'none';
                        selectedForestValue.style.display = 'block';
                        optionsContainerForest.querySelectorAll('.custom-option').forEach(opt => {
                            opt.classList.remove('selected');
                        });
                        option.classList.add('selected');

                        selectContainer.classList.remove('open');
                    });

                optionsContainerForest.appendChild(option);
            });

       }
       if (currentBreed) {
            const selectedBreed = APP.breeds.find(item => item.id === currentBreed);
            if (selectedBreed) {
                selectedId = currentBreed;
                selectedForestValue.innerHTML = `
                    <span class="selected-name">${selectedBreed.name}</span>
                    <span class="selected-short">${selectedBreed.shortName || ''}</span>
                `;
                selectedForestValue.classList.add('active');
                searchInput.style.display = 'none';
                selectedForestValue.style.display = 'block';
                hiddenForestInputId.value = currentBreed;
            }
       } else {
            searchInput.style.display = 'block';
            selectedForestValue.style.display = 'none';
       }
       renderOptions(currentData);

       searchInput.addEventListener('input', function(e) {
            e.stopPropagation();
            const value = this.value;

            if (value.trim()) {
                selectContainer.classList.add('open');
                filterOptions(value);
            } else {
                renderOptions(currentData);
            }
       });

       if (forestTrigger) {
            forestTrigger.addEventListener('click', function(e) {
                e.stopPropagation();
                if (e.target === searchInput) {
                    return;
                }
                const wasOpen = selectContainer.classList.contains('open');
                if (wasOpen) {
                    selectContainer.classList.remove('open');
                    if (!hasChanges && selectedId !== originalBreedId) {
                        restoreOriginalValue();
                    } else {
                        showSelectedMode();
                    }
                    return;
                }

                if (selectedId) {
                    selectedForestValue.classList.remove('active');
                    selectedForestValue.style.display = 'none';
                    searchInput.style.display = 'block';
                    searchInput.value = '';
                    searchInput.focus();
                    renderOptions(currentData);
                } else {
                    searchInput.style.display = 'block';
                    searchInput.value = '';
                    selectedForestValue.style.display = 'none';
                    searchInput.focus();
                    renderOptions(currentData);
                }
                document.querySelectorAll('.edit-custom-forest-select.open').forEach(el => {
                    if (el !== selectContainer) {
                        el.classList.remove('open');
                    }
                });
                selectContainer.classList.toggle('open');
            });
       }
       document.addEventListener('click', function closeSelect(e) {
            if (!selectContainer.contains(e.target) && selectContainer.classList.contains('open')) {
                selectContainer.classList.remove('open');
                if (!hasChanges && selectedId !== originalBreedId) {
                    restoreOriginalValue();
                } else {
                    showSelectedMode();
                }
            }
       });
    }
    // Заменяем содержимое ячеек на поля ввода с кнопками
    //cells[0].innerHTML = `<select class="edit-breed" data-field="breed" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">${breedOptions}</select>`;
    cells[1].innerHTML = `<input type="number" class="edit-living" data-field="living" value="${currentLiving}"  style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;
    cells[2].innerHTML = `<input type="number" class="edit-dead" data-field="dead" value="${currentDead}" style="width: 100%; padding: 6px 12px; border: none; background: transparent; border-bottom: 1px solid #40E0D0;">`;

    // Добавляем кнопки сохранить/отменить в последнюю ячейку
    cells[4].innerHTML = `
        <button onclick="saveInlineNullEdit(${id})" style="padding: 5px 10px; border:none; z-index: 1000; background: transparent;">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check-lg" viewBox="0 0 16 16">
              <path d="M12.736 3.97a.733.733 0 0 1 1.047 0c.286.289.29.756.01 1.05L7.88 12.01a.733.733 0 0 1-1.065.02L3.217 8.384a.757.757 0 0 1 0-1.06.733.733 0 0 1 1.047 0l3.052 3.093 5.4-6.425z"/>
            </svg>
        </button>
    `;
}
function saveInlineNullEdit(id) {

    const targetRow = document.querySelector(`tr[data-id="${id}"]`);
    if (!targetRow) return;

    // Получаем новые значения из полей ввода
    const hiddenInput = document.getElementById(`edit-selected-breed-forest-${id}`);
    const newBreed = hiddenInput ? hiddenInput.value : null;
    //const newBreed = targetRow.cells[0].querySelector('.edit-breed')?.value;
    const newLiving = targetRow.cells[1].querySelector('.edit-living')?.value;
    const newDead = targetRow.cells[2].querySelector('.edit-dead')?.value;

    if (!newBreed || !newLiving === undefined || !newDead === undefined) {
        console.error('Не удалось получить значения');
        return;
    }

    // Сохраняем изменения в APP.dateUpdate для отправки на сервер
    APP.dateUpdate.push({
        id: id,
        countDead: Number(newDead),
        countLiving: Number(newLiving),
        idBreed: Number(newBreed),
    });

    // Обновляем данные в APP.nullTableList
    const index = APP.nullTableList.findIndex(item => item.id === id);
    if (index !== -1) {
        APP.nullTableList[index] = {
            ...APP.nullTableList[index],
            idBreed: Number(newBreed),
            countDead: Number(newDead),
            countLiving: Number(newLiving),
        };
    }

    // Перерисовываем таблицу
    updateDataInNullTable();
}
async function deleteLineInNullTable(del_id) {
    let id = document.querySelector("#idDocument").value;
    hasUnsavedChanges = true;

    if (!APP.deleteNullTable.includes(del_id)) {
        APP.deleteNullTable.push(del_id);
    }
    if(APP.deleteNullTable.length > 0) {
         var nullDell = {
            values : APP.deleteNullTable
        };
        await forestCropsInformTrialArea.deleteSample(nullDell);
    }
    APP.deleteNullTable = [];
    var nullTableResp = await forestCropsInformTrialArea.getSampleByIdListRegion(id,currentPage);
    APP.countNullTable = nullTableResp.count;
    APP.nullTableList = nullTableResp.data

    let newTotalPages = Math.ceil(APP.countNullTable / APP.limit);

    if (currentPage > newTotalPages && newTotalPages > 0) {
        currentPage = newTotalPages;
    } else if (newTotalPages === 0) {
        currentPage = 1;
    }

    if (currentPage < 1) {
        currentPage = 1;
    }

    if (currentPage !== nullTableResp.currentPage) {
        var correctedResp = await forestCropsInformTrialArea.getSampleByIdListRegion(id, currentPage);
        APP.countNullTable = correctedResp.count;
        APP.nullTableList = correctedResp.data;
    }
    updateDataInNullTable();
}

function replaceSpaces(inputId) {
    const input = document.getElementById(inputId);

    input.addEventListener('input', function() {
        const curPos = this.selectionStart;
        const original = this.value;
        let newValue = original.replace(/\s/g, ';');
        newValue = newValue.replace(/;+/g, ';');
        if (original !== newValue) {
            this.value = newValue;
            // корректируем позицию курсора, если она сместилась из‑за замены
            const diff = newValue.length - original.length;
            this.setSelectionRange(curPos + diff, curPos + diff);
        }
    });
}

// Использование:
replaceSpaces('countLiving');
replaceSpaces('countDead');
// Функция для разбора строки с разделителем ; в массив чисел
function parseValues(inputString) {
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
        .filter(v => v !== null && v > 0);

    return values;
}
async function createSample() {
    try {
        let id = document.querySelector("#idDocument").value;
        let countDead = document.getElementById("countDead");
        let countLiving = document.getElementById("countLiving");
        APP.createSample = [];

        const countDeadValues = parseValues(countDead.value);
        const countLivingValues = parseValues(countLiving.value);

        if (countDeadValues.length === 0 || countLivingValues.length === 0) {
            alert('Пожалуйста, заполните оба поля!');
            return;
        }
        if (countDeadValues.length !== countLivingValues.length) {
            const maxLen = Math.max(countDeadValues.length, countLivingValues.length);
            while (countDeadValues.length < maxLen) countDeadValues.push(0);
            while (countLivingValues.length < maxLen) countLivingValues.push(0);
        }

        for (let i = 0; i < countDeadValues.length; i++) {
             APP.createSample.push({
                 countDead: countDeadValues[i],
                 countLiving: countLivingValues[i],
                 idBreed: Number(APP.forestBreedSelectId),
             });
        }

        var data = {
            idSample: Number(id),
            values : APP.createSample
        };
        APP.createSample = [];
        countDead.value = "";
        countLiving.value = "";
        await forestCropsInformTrialArea.createSample(data);
        var nullTableResp = await forestCropsInformTrialArea.getSampleByIdListRegion(id,1);
        APP.countNullTable = nullTableResp.count;
        APP.nullTableList = nullTableResp.data

        updateDataInNullTable();
        closeAddForm("form-add-proba");

    } catch (error) {
        console.error('Ошибка при добавлении:', error);
        alert('Ошибка при добавлении записей');
    }
}

function updatePaginationInfo() {

    const itemsOnCurrentPage = Math.min(currentPage * APP.limit, APP.countNullTable);

    document.getElementById("totalForestCount").innerText = APP.countNullTable;

    document.getElementById("pageForestInfo").innerText = `Страница ${currentPage} из ${totalPages || 1}`;

    if (APP.countNullTable === 0) {
        document.getElementById("itemsForestInfo").innerText = `Нет записей`;
    } else if (itemsOnCurrentPage != APP.countNullTable) {
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
        nextBtn.disabled = currentPage === totalPages || totalPages === 0;
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

async function nextPageForest() {
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

async function prevPageForest() {
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