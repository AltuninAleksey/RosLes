APP = {
    idView: "forestCropsStatementRecalculations"
}

function StatementRecalculationsBusiness() {}

StatementRecalculationsBusiness.getAllStatementList = async function() {

    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
      method: 'get',
      url: "http://92.50.227.100:58493/forestcrops/api/listregion/list",
      responseType: 'json',
      headers: {
        'Authorization': 'Bearer ' + token
      }
    });

    return requestData.data;
}

StatementRecalculationsBusiness.getStatementListByFilter = async function(data) {
    var requestData = await axios({
        method: 'post',
        url: urlGlobal + "/listregionfilters",
        data: data,
        responseType: 'json'
    });

    return requestData.data.data;
}

StatementRecalculationsBusiness.deleteStatementRecalculationById = async function(id) {
    var requestData = await axios({
        method: 'delete',
        url: urlGlobal + "/listregion/" + id,
        responseType: 'json'
    });
}

StatementRecalculationsBusiness.TypeData = {
    ALL: 0,
    BYID: 1
}

StatementRecalculationsBusiness.downloadExcel = async function() {

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

StatementRecalculationsBusiness.downloadAllExcel = async function(data) {

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

StatementRecalculationsBusiness.downloadAllPhoto = async function(data) {

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