package com.example.rosles.Models

import com.example.rosles.ResponceClass.BaseRespObject

data class Quater(
    val id: String,
    val quarterName: String
) : Modeldb, ToBaseRespObject {

    override fun toBaseRespObject(): BaseRespObject {
        return BaseRespObject(id.toInt(), quarterName)
    }
}
