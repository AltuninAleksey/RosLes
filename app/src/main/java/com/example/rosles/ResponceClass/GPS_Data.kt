package com.example.rosles.ResponceClass

data class GPS_Data(
    val id: String,
    val latitude:Double,
    val longitude:Double,
    val flag_center:Boolean,
    val id_sample: String,
    var mark_update:Int)


data class GPS_Data_Send(
    val latitude: Double,
    val longitude: Double,
    val flag_center: Int,
    val id_sample: String
)