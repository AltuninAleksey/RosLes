APP = {
    idView: "newForestCropsStatementRecalculationsBusinessDetail"
}

function newForestCropsStatementRecalculationsBusinessDetail() {}

newForestCropsStatementRecalculationsBusinessDetail.getCreateSample = async function(data) {
    var token = document.cookie.match(/jwttoken=(.+?)(;|$)/)[1];

    var requestData = await axios({
        method: 'post',
        url: "http://92.50.227.100:58493/forestcrops/api/listregion/create",
        data: data,
        responseType: 'json',
        headers: {
          'Authorization': 'Bearer ' + token
        }
    });

    return requestData.data;
}