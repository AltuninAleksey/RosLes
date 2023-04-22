package com.example.rosles.Models

import com.example.rosles.ResponceClass.BaseRespObject

data class Subject(
    val id: String,
    val nameSubjectRF: String
) : Modeldb, ToBaseRespObject {

    override fun toBaseRespObject(): BaseRespObject {
        return BaseRespObject(id.toInt(), nameSubjectRF)
    }
}
