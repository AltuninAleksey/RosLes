package com.example.rosles.ResponceClass

data class QUATER_RESP(val get:List<QUATER_DATA>): BaseResponceInterface

data class QUATER_DATA(
        val id: Int,
        val quarter_name: String,
        val id_district_forestly: Int
    ): BaseResponceInterface