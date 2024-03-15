openPage();

async function openPage() {

    APP.userData = await CommonBusiness.getUserData();
    APP.documentData = {
       id_subject_rf: APP.userData.id_subject_rf
    };


    APP.subjects = await CommonBusiness.getAllSubjectrf();

    APP.sortOrderTable1 = 0;

    await setDetailDataIdPage();

    setEvent();
}

async function setDetailDataIdPage() {

    var now = new Date();
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);
    var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
    document.querySelector("#dateStatement").value = today;



    await setSubjectsRF();
}

async function setEvent() {

    document
        .querySelector("#subjectStatement")
        .addEventListener('change', async (e)=>{
                await setForestly();
        });

    document
        .querySelector("#forestlyStatement")
        .addEventListener('change', async (e)=>{
                await setDistriotForestlyStatement();
        });

    document
        .querySelector("#distriotForestlyStatement")
        .addEventListener('change', async (e)=>{
                await setQuarterStatement();
        });


}


async function setSubjectsRF() {
    let subjectStatementNode = document.querySelector("#subjectStatement");

    let newHtml = "";

    for(let i = 0; i < APP.subjects.length; i++) {
          if(APP.subjects[i].id == APP.documentData.id_subject_rf) {
            subjectStatementNode.value = APP.subjects[i].name_subject_RF;
            break;
          }
    }

    subjectStatementNode.innerHTML = newHtml;

    await setForestly();
}

async function setForestly() {
    let idSubject = APP.documentData.id_subject_rf;//document.querySelector("#subjectStatement").value;
    APP.forestly = await CommonBusiness.getForestlyByIdSubjectrf(idSubject);

    let newHtml = "";

    let forestlyStatementNode = document.querySelector("#forestlyStatement");

    for(let i = 0; i < APP.forestly.length; i++) {
        newHtml += '<option value="' + APP.forestly[i].id + '">' + APP.forestly[i].name_forestly + '</option>';
    }

    forestlyStatementNode.innerHTML = newHtml;

    await setDistriotForestlyStatement();
}

async function setDistriotForestlyStatement() {
    let idForestly = document.querySelector("#forestlyStatement").value;
    APP.district_forestly = [];

    if(idForestly != "" && idForestly != null && idForestly != undefined) {
        APP.district_forestly = await CommonBusiness.getDistrictForestlyByIdForestly(idForestly);
    }

    let newHtml = "";

    let distriotForestlyNode = document.querySelector("#distriotForestlyStatement");

    for(let i = 0; i < APP.district_forestly.length; i++) {
        newHtml += '<option value="' + APP.district_forestly[i].id + '">' + APP.district_forestly[i].name_district_forestly + '</option>';
    }

    distriotForestlyNode.innerHTML = newHtml;

    //await setQuarterStatement();
}

async function setQuarterStatement() {
    let idDistrictForestly = document.querySelector("#distriotForestlyStatement").value;
    APP.quarters = [];

    if(idDistrictForestly != "" && idDistrictForestly != null && idDistrictForestly != undefined) {
        APP.quarters = await CommonBusiness.getQuarterByIdDistrictForestly(idDistrictForestly);
    }

    let newHtml = "";

    let quarterStatementNode = document.querySelector("#quarterStatement");

    for(let i = 0; i < APP.quarters.length; i++) {
        newHtml += '<option value="' + APP.quarters[i].id + '">' + APP.quarters[i].quarter_name + '</option>';
    }

    quarterStatementNode.innerHTML = newHtml;
}

async function saveData() {

    let numberStatementNode = document.querySelector("#numberStatement").value;
    let dateStatementNode = document.querySelector("#dateStatement").value;
    let soilLotStatementNode = document.querySelector("#soilLotStatement").value;
    let sampleRegionStatementNode = document.querySelector("#sampleRegionStatement").value;
    let quarterStatement = document.querySelector("#quarterStatement").value;
    let dachaStatement = document.querySelector("#dachaStatement").value;
    let distriotForestlyStatement = document.querySelector("#distriotForestlyStatement").value;

    var data = {
        date: dateStatementNode,
        sample_region: sampleRegionStatementNode,
        mark_del: 0,
        mark_update: 0,
        number_region: numberStatementNode,
        id_profile: Number(APP.userData.id),
        name_quarter: quarterStatement == ""? null : quarterStatement,
        dacha: dachaStatement == ""? null : dachaStatement,
        id_district_forestly: distriotForestlyStatement,
        soil_lot: soilLotStatementNode
    };

    await StatementRecalculationsBusinessDetail.getCreateSample(data);

    getStatementRecalculations();
}