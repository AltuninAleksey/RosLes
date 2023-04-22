package com.example.rosles.ResponceClass

import com.example.rosles.Models.Modeldb
import com.example.rosles.Models.ToBaseRespObject

data class DistrictResp(var data:List<District>)

data class District(
    val id: Int,
    val nameDistrictForestly: String
) : Modeldb, ToBaseRespObject {

    override fun toBaseRespObject(): BaseRespObject {
        return BaseRespObject(id, nameDistrictForestly)
    }
}
