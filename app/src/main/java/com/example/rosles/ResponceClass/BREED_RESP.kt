package com.example.rosles.ResponceClass

data class BREED_RESP(val get:List<BREED_DATA>) : BaseResponceInterface

data class BREED_DATA(
    val id: Int,
    val name_breed: String,
    val is_pine: Boolean,
    val is_foliar: Boolean,
    val ShortName: String?
    ) : BaseResponceInterface
