package com.example.rosles.ResponceClass

import com.google.gson.annotations.SerializedName


data class FCROW_LIST_RESP(
    @SerializedName("count") val count: Int,
    @SerializedName("data") val data: List<FCROW_LIST_DATA>?
) : BaseResponceInterface

data class FCROW_LIST_DATA(
    @SerializedName("id") val id: Int?,
    @SerializedName("number") val number: Int?,
    @SerializedName("countLiving") val countLiving: Int?,
    @SerializedName("countDead") val countDead: Int?,
    // FK на djangoForest_breed.
    @SerializedName("idBreed") val idBreed: Int?,
    @SerializedName("uuid") val uuid: String?,
    // Перечет-владелец из djangoForest_fc_sample
    // (вкладка «Пробная площадь» на экране UchastokInfo).
    @SerializedName("uuidSample") val uuidSample: String?
) : BaseResponceInterface
