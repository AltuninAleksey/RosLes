package com.example.rosles.ResponceClass

data class UNDER_RESP(val get:List<UNDER_DATA>) : BaseResponceInterface

data class UNDER_DATA(
        val id: Int,
        val name: String
    ) : BaseResponceInterface
