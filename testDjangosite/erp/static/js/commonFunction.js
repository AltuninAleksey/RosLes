function CommonFunction() {}

CommonFunction.getQuarterNameByQuarterId = function(quarterArray, id_quarter) {
    for(var j = 0; j < quarterArray.length; j++) {
        if(quarterArray[j].id == id_quarter) {
            return quarterArray[j].quarter_name;
        }
    }

    return "";
}