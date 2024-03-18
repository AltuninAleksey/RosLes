package com.example.rosles.ResponceClass

data class SUBJECTRF_RESP(val id_main_subject:Int,
                          val name_main_subject:String,
                          val slave_subject:List<SUBJECTRF_DATA>) : BaseResponceInterface

data class SUBJECTRF_DATA(
        val id_subject: Int,
        val name_slave_subject: String
    ) : BaseResponceInterface