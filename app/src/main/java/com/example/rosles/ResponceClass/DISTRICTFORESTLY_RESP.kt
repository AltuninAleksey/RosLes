package com.example.rosles.ResponceClass

data class DISTRICTFORESTLY_RESP(val data:List<DISTRICTFORESTLY_DATA>) : BaseResponceInterface

data class DISTRICTFORESTLY_DATA(
    val id: Int,
    val name_district_forestly: String,
    val id_forestly: Int
    ) : BaseResponceInterface