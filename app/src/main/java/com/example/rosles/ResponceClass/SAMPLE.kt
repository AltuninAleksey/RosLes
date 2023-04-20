package com.example.rosles.ResponceClass

data class SAMPLE_RESP(val get:List<SAMPLE_DATA>)

data class SAMPLE_REQEST(val data:List<SAMPLE_DATA>)

data class SAMPLE_DATA(
    val id: Int,
    val date: String,
    val sample_area:Float?,
    var soil_lot: String,
    val width: Int?,
    val lenght: Int?,
    val square: Int?,
    val id_profile: Int,
    val id_list_region: Int,
    val id_quarter: Int,
    val mark_update:Int
    )