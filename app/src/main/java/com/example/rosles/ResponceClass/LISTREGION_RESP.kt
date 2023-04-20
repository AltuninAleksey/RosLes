package com.example.rosles.ResponceClass

data class LISTREGION_RESP(val get:List<LISTREGION_DATA>)

data class LISTREGION_REQUEST(val data:List<LISTREGION_DATA>)

data class LISTREGION_DATA(
    val id: Int,
    val date: String,
    val sample_region: Float?,
    val soil_lot: String,
    val mark_del: Int?,
    val mark_update: Int?,
    val id_quarter: Int
    )