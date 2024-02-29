package com.example.rosles.ResponceClass

data class LISTREGION_RESP(val get:List<LISTREGION_DATA>) : BaseResponceInterface

data class LISTREGION_REQUEST(val data:List<LISTREGION_DATA>) : BaseResponceInterface

data class LISTREGION_DATA(
    var id: Int,
    val date: String,
    val sample_region: Float?,
    val soil_lot: String,
    val mark_del: Int?,
    val mark_update: Int?,
    val name_quarter: Int,
    val id_profile: Int?=0,
    val number_region:String?=null,
    val id_district_forestly:Int

    ) : BaseResponceInterface