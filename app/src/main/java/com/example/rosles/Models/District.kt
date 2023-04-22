package com.example.rosles.Models

import com.example.rosles.ResponceClass.BaseRespObject

data class District(
    val id: String,
    val nameDistrictForestly: String
) : Modeldb, ToBaseRespObject {

    override fun toBaseRespObject(): BaseRespObject {
        return BaseRespObject(id.toInt(), nameDistrictForestly)
    }
}
