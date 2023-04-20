package com.example.rosles.RequestClass

data class listregionrequest(val data:List<listregionData>)

data class listregionData(
    val id: Int,
    val date: String,
    val soil_lot: Float?,
    val id_quarter_id: Int,
    val mark_del: Int?,
    val sample_region: Float?,
    val mark_update: Int
)