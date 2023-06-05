package com.example.rosles.ResponceClass

data class FORESTLY_RESP(val get:List<FORESTLY_DATA>) : BaseResponceInterface

data class FORESTLY_DATA(
    val id: Int,
    val name_forestly:String,
    val id_subject_rf: Int
    ) : BaseResponceInterface