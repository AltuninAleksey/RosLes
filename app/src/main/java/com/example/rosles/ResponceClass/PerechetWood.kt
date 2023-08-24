package com.example.rosles.ResponceClass

import java.util.*


data class probaWOODD(var data: HashMap<String, PerechetWood>) : BaseResponceInterface

data class ProbaWoodSimple(var iskus:PerechetWood?,var estes:PerechetWood?,var estestvenn:PerechetWood?) : BaseResponceInterface

data class PerechetWood(
    var namewood: String,
    var type: Int,
    var id_breed: Int = 0,
    var o2: Int? = 0,
    var o5: Int? = 0,
    var o6: Int? = 0,
    var o11: Int? = 0,
    var o15: Int? = 0,
    var maxHeight: Float? = 0f,
    var AVGHEight: Float? = 0f,
    var AVGdiametr: Float? = 0f,
    var id_prob: Int? = null,
    var allwoods:Int?=0,
    var main_porod:Boolean=false
)


data class PodlesokWood(var value:Int?,var avgHeightpodles:Float?=0f,var id:Int?,var idbreed_under:Int?=0) : BaseResponceInterface

