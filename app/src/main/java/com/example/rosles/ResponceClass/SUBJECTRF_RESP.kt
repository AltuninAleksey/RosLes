package com.example.rosles.ResponceClass

data class SUBJECTRF_RESP(val get:List<SUBJECTRF_DATA>) : BaseResponceInterface

data class SUBJECTRF_DATA(
        val id: Int,
        val name_subject_RF: String
    ) : BaseResponceInterface