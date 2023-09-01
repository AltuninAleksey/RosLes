package com.example.rosles.ResponceClass

data class LISTREGION_RESP(val get:List<LISTREGION_DATA>) : BaseResponceInterface

data class LISTREGION_REQUEST(val data:List<LISTREGION_DATA>) : BaseResponceInterface

data class LISTREGION_DATA(
    val id: Int,
    val date: String,
    val sample_region: Float?,
    var soil_lot: String = "0",
    val mark_del: Int?,
    val mark_update: Int?,
    val id_quarter: Int,
    val id_profile: Int?=0,
    val number_region:String?=null
    ) : BaseResponceInterface