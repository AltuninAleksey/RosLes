function setDataInTableFour() {

    document.getElementById("id_sample-photo-point-add").value = APP.documentData.id;

    var table_4 = document.getElementById("table_4");
    var newHtml = "";

    for(var i = 0; i < APP.dataTable_4.length; i++) {
            var hrefStr = "" + urlGlobal + "/media/" + APP.dataTable_4[i].photo;
            newHtml = newHtml + `<tr>
                                <td class="photo-td1">${i + 1}</td>
                                <td class="photo-td2">${APP.dataTable_4[i].date}</td>
                                <td class="photo-td3"><a href=${hrefStr}>img_${i + 1}</a></td>
                                </tr>`;
    }
    table_4.innerHTML = newHtml;

    document.getElementById('form-photo-point-add').addEventListener('submit', async function(event) {
        event.preventDefault();

        const response = await fetch("" + urlGlobal + "/upload", {
            method: 'POST',
            body: new FormData(this)
        });

        closeAddForm('form-add-photo-point');

        ShowModal('m1', 'Сохранение прошло успешно', '/static/img/check-circle-fill.svg')

        setTimeout(function() {
            let idDocument = document.getElementById("idDocument").value;
            let idParent = document.getElementById("idParent").value;
            getRecalculatingDetail(idDocument, idParent);
        }, 3000);
    });

}
