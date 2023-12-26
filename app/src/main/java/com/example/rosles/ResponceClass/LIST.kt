package com.example.rosles.ResponceClass

data class LIST_RESP(val get:List<LIST_DATA>) : BaseResponceInterface

data class LIST_REQEST(val data:List<LIST_DATA>) : BaseResponceInterface

data class LIST_DATA(
    val id: Int,
    val to0_2: Int,
    val from0_21To0_5: Int,
    val from0_6To1_0: Int,
    val from1_1to1_5: Int,
    val from1_5: Int,
    val max_height: Float?,
    val avg_diameter: Float?,
    val count_of_plants: Int,
    val avg_height: Float?,
    val avg_height_undergrowth: Float?,
    val main: Int?,
    val id_sample: Int,
    val id_breed: Int?,
    val id_type_of_reproduction: Int?,
    val id_undergrowth: Int?,
    val mark_update: Int

) : BaseResponceInterface