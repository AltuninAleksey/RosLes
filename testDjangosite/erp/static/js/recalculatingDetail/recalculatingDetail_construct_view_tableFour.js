function setDataInTableFour() {

    document.getElementById("id_sample-photo-point-add").value = APP.documentData.id;

    var table_4 = document.getElementById("table_4");
    var newHtml = "";

    for(var i = 0; i < APP.dataTable_4.length; i++) {
            var hrefStr = "" + urlGlobal + "/media/" + APP.dataTable_4[i].photo;
            var date = new Date(APP.dataTable_4[i].date);

            const formatted = date.toLocaleString('ru-RU', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric',
                hour: '2-digit',
                minute: '2-digit',
                second: '2-digit'
            });

            newHtml = newHtml + `<tr>
                                <td class="photo-td1">${i + 1}</td>
                                <td class="photo-td2">${formatted}</td>
                                <td class="photo-td3"><a href=${hrefStr}>img_${i + 1}</a></td>`;

            newHtml = newHtml + "<td style=\"width: 1%;\">" +
                                "<svg onclick=\"deleteLineInTableFour(" + APP.dataTable_4[i].id + ")\" class=\"cursorPointer\" width=\"23px\" height=\"23px\" viewBox=\"0 0 24 24\" fill=\"none\" xmlns=\"http://www.w3.org/2000/svg\">" +
                                    "<g id=\"SVGRepo_bgCarrier\" stroke-width=\"0\"></g> " +
                                    "<g id=\"SVGRepo_tracerCarrier\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></g>" +
                                    "<g id=\"SVGRepo_iconCarrier\">" +
                                        "<path d=\"M18 6L17.1991 18.0129C17.129 19.065 17.0939 19.5911 16.8667 19.99C16.6666 20.3412 16.3648 20.6235 16.0011 20.7998C15.588 21 15.0607 21 14.0062 21H9.99377C8.93927 21 8.41202 21 7.99889 20.7998C7.63517 20.6235 7.33339 20.3412 7.13332 19.99C6.90607 19.5911 6.871 19.065 6.80086 18.0129L6 6M4 6H20M16 6L15.7294 5.18807C15.4671 4.40125 15.3359 4.00784 15.0927 3.71698C14.8779 3.46013 14.6021 3.26132 14.2905 3.13878C13.9376 3 13.523 3 12.6936 3H11.3064C10.477 3 10.0624 3 9.70951 3.13878C9.39792 3.26132 9.12208 3.46013 8.90729 3.71698C8.66405 4.00784 8.53292 4.40125 8.27064 5.18807L8 6M14 10V17M10 10V17\" stroke=\"#000000\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"></path> " +
                                    "</g>" +
                                "</svg>" +
                            "</td></tr>";
    }
    table_4.innerHTML = newHtml;

    document.getElementById('form-photo-point-add').addEventListener('submit', async function(event) {
        event.preventDefault();

        const formData = new FormData(this);

        if(formData.get("photo").name == '') {
            closeAddForm('form-add-photo-point');
            ShowModal('m1', 'Ошибка! Заполните фото!', '/static/img/exclamation-circle.svg');
            return;
        }

        if(formData.get("date") == '') {
            formData.set('date', null);
        }

        if(formData.get('longitude') == '' ||
           formData.get('latitude' == ''))  {

           formData.set('longitude', "0.0");
           formData.set('latitude', "0.0");

        }

        const response = await fetch("" + urlGlobal + "/upload", {
            method: 'POST',
            body: formData
        });

        closeAddForm('form-add-photo-point');

        hasUnsavedChanges = false;

        ShowModal('m1', 'Сохранение прошло успешно', '/static/img/check-circle-fill.svg');

        setTimeout(function() {
            let idDocument = document.getElementById("idDocument").value;
            let idParent = document.getElementById("idParent").value;
            getRecalculatingDetail(idDocument, idParent);
        }, 3000);
    });
}

async function deleteLineInTableFour(del_id) {

    await RecalculatingDetailBusiness.deletePhotoPoint(del_id);

    var dataTable_4 = APP.dataTable_4;
    APP.dataTable_4 = [];

    for(var i = 0; i < dataTable_4.length; i++) {
        if(dataTable_4[i].id != del_id) {
            APP.dataTable_4.push(dataTable_4[i]);
        }
    }

    setDataInTableFour();
}
