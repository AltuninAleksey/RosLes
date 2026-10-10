package com.example.rosles.ResponceClass

import com.google.gson.annotations.SerializedName


data class FCSAMPLE_LIST_RESP(
    @SerializedName("count") val count: Int,
    @SerializedName("data") val data: List<FCSAMPLE_LIST_DATA>?
) : BaseResponceInterface

data class FCSAMPLE_LIST_DATA(
    @SerializedName("number") val number: String?,
    @SerializedName("length") val length: Double?,
    @SerializedName("width") val width: Double?,
    @SerializedName("uuid") val uuid: String?,
    // Ведомость-владелец из djangoForest_fc_list_region.
    @SerializedName("uuidListRegion") val uuidListRegion: String?
) : BaseResponceInterface
