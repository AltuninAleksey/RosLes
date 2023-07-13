function setDataInTableTwo() {
    var table_2 = document.getElementById("table_2");
    var newHtml = "";

    for(var i = 0; i < APP.dataTable_2.length; i++) {
        newHtml = newHtml + "<tr>" +
                                "<td>" + APP.dataTable_2[i].FIO + "</td>" +
                            "</tr>";
    }
    table_2.innerHTML = newHtml;
}

function addState() {
    var stateName = document.getElementById("stateName");
    var FIO = "";

    for(var i = 0; i < APP.states.length; i++) {
        if(APP.states[i].id == stateName.value) {
            FIO = APP.states[i].FIO;
        }
    }

    var newState = {
        id : stateName.value,
        FIO : FIO
    };

    APP.dataTable_2.push(newState);
    setDataInTableTwo();
    closeAddForm("form-add-state");
}