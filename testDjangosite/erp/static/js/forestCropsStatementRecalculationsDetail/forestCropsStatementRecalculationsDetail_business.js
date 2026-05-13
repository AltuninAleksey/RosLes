APP = {
    idView: "forestCropsStatementRecalculationsDetail",
    limit: 1,
}

function StatementRecalculationsBusinessDetail() {}

StatementRecalculationsBusinessDetail.getStatementRecalculationsDetailDataById = async function(id) {
    var requestData = await axios({
      method: 'get',
      url: "http://92.50.227.100:58493/forestcrops/api/listregion/info?id=" + id,
      responseType: 'json'
    });

    return requestData.data;
}

StatementRecalculationsBusinessDetail.getSampleByIdListRegion = async function(id,pageNum) {
    var offset = (pageNum - 1)*APP.limit;
    var requestData = await axios({
        method: 'get',
        url: ("http://92.50.227.100:58493/forestcrops/api/sample/list?idListRegion="+id+"&offset=" + offset + "&limit=" + APP.limit),
        data: {id: id},
        responseType: 'json'
    });

    return requestData.data;
}

StatementRecalculationsBusinessDetail.getUpdateSample = async function(data) {
    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];
    var requestData = await axios({
        method: 'put',
        url: "http://92.50.227.100:58493/forestcrops/api/listregion/update",
        data: data,
        responseType: 'json',
        headers: {
          'Authorization': 'Bearer ' + token
        }
    });
}

StatementRecalculationsBusinessDetail.createSample = async function(data) {
    var requestData = await axios({
        method: 'post',
        url: "http://92.50.227.100:58493/forestcrops/api/sample/create",
        data: data,
        responseType: 'json'
    });

    return requestData.data;
}

StatementRecalculationsBusinessDetail.deleteSample = async function(id) {
    var requestData = await axios({
        method: 'DELETE',
        url: "http://92.50.227.100:58493/forestcrops/api/sample/delete?id=" + id,
        responseType: 'json'
    });
}


StatementRecalculationsBusinessDetail.generateDocx = async function(data) {
    var requestData = await axios({
      method: 'post',
      url: urlGlobal + "/form_list_region",
      data: data,
      responseType: 'json'
    });

    return requestData.data;
}