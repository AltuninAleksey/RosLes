package com.example.rosles.ResponceClass

data class RESPONCE(val list :List<ClassWood>)

data class ClassWood(
    val id :Int=0,
    var proba :String,
    var poroda :String,
    var Reproduction :String,
    var value02 :String,
    var value05 :String,
    var value06 :String,
    var value11 :String,
    var value15 :String,
)
data class Reproduction(
    val id :String,
    var REPRODUCTIONNAME :String
)