APP = {
    idView: "fieldCard"
}

function FieldCardBusiness() {}

FieldCardBusiness.getAllFieldCardList = async function() {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
      method: 'get',
      url: urlGlobal + "/fieldcard",
      responseType: 'json',
      headers: {
        'Authorization': 'Bearer ' + token
      }
    });

    return requestData.data.get;
}

FieldCardBusiness.getFieldCardListByFilter = async function(data) {
    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/fieldcardfilter",
        data: data,
        responseType: 'json'
    });

    return requestData.data.data;
}

FieldCardBusiness.deleteFieldCardById = async function(data) {
    var requestData = await axios({
        method: 'delete',
        url: urlGlobal + "/deleteallbyfieldcard",
        data: data,
        responseType: 'json'
    });

    return requestData.data.data;
}

FieldCardBusiness.TypeData = {
    ALL: 0,
    BYID: 1
}

FieldCardBusiness.downloadExcel = async function() {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
        method: 'get',
        url: urlGlobal + "/getmains",
        responseType: 'json',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    });
    return requestData.data;
}

FieldCardBusiness.downloadAllExcel = async function(data) {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/create_all_excel",
        data: data,
        headers: {
            'Authorization': 'Bearer ' + token
        }
    });
    return requestData.data;
}

FieldCardBusiness.downloadAllPhoto = async function(data) {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    try {
        const response = await axios({
            method: 'get',
            url: urlGlobal + "/zip_photos/" + data,
            headers: {
                'Authorization': 'Bearer ' + token
            },
            responseType: 'blob' // Важно! Указываем, что ожидаем бинарные данные
        });

        // Создаем ссылку на скачивание
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;

        // Получаем имя файла из заголовков (если сервер его передает)
        const contentDisposition = response.headers['content-disposition'];
        let filename = 'archive.zip'; // имя по умолчанию

        if (contentDisposition) {
            const filenameMatch = contentDisposition.match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/);
            if (filenameMatch && filenameMatch[1]) {
                filename = filenameMatch[1].replace(/['"]/g, '');
            }
        }

        link.setAttribute('download', filename);
        document.body.appendChild(link);
        link.click();

        // Очищаем
        link.remove();
        window.URL.revokeObjectURL(url);

    } catch (error) {
        console.error('Ошибка при скачивании файла:', error);
    }
}