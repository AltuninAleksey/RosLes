package com.example.rosles.Models

import com.example.rosles.ResponceClass.BaseRespObject

data class Lesnich(
    val id: String,
    val nameForestly: String
) : Modeldb, ToBaseRespObject {

    override fun toBaseRespObject(): BaseRespObject {
        return  BaseRespObject(id.toInt(), nameForestly)
    }
}
