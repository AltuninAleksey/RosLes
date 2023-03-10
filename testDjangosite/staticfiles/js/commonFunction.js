function CommonFunction() {}

CommonFunction.getQuarterNameByQuarterId = function(quarterArray, id_quarter) {
    for(var j = 0; j < quarterArray.length; j++) {
        if(quarterArray[j].id == id_quarter) {
            return quarterArray[j].quarter_name;
        }
    }

    return "";
}

CommonFunction.getSubjectNameByQuarterId = function(subjectrf, id_subjectrf) {

    for(var j = 0; j < subjectrf.length; j++) {
        if(subjectrf[j].id == id_subjectrf) {
            return subjectrf[j].name_subject_RF;
        }
    }

    return "";
}

CommonFunction.getForestlyNameByQuarterId = function(forestly, id_forestly) {
    for(var j = 0; j < forestly.length; j++) {
        if(forestly[j].id == id_forestly) {
            return forestly[j].name_forestly;
        }
    }

    return "";
}

CommonFunction.getDistrictForestlyNameByQuarterId = function(district_forestly, id_district_forestly) {
    for(var j = 0; j < district_forestly.length; j++) {
        if(district_forestly[j].id == id_district_forestly) {
            return district_forestly[j].name_district_forestly;
        }
    }

    return "";
}

