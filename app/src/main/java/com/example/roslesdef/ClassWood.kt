package com.example.roslesdef


data class RESPONCE(val list :List<ClassWood>)

data class ClassWood(
    val id :String,
    var proba :String,
    var poroda :String,
    var view :String,
    var value02 :String,
    var value05 :String,
    var value06 :String,
    var value11 :String,
    var value15 :String,
) {
}