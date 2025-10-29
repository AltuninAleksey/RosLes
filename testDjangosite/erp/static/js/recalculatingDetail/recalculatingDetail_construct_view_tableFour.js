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
                                <td class="photo-td3"><a href=${hrefStr}>img_${i + 1}</a></td>
                                </tr>`;
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

        ShowModal('m1', 'Сохранение прошло успешно', '/static/img/check-circle-fill.svg');

        setTimeout(function() {
            let idDocument = document.getElementById("idDocument").value;
            let idParent = document.getElementById("idParent").value;
            getRecalculatingDetail(idDocument, idParent);
        }, 3000);
    });

}
