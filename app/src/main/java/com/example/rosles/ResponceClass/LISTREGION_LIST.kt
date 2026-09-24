package com.example.rosles.ResponceClass

import com.google.gson.annotations.SerializedName

data class LISTREGION_LIST_RESP(
    @SerializedName("count") val count: Int,
    @SerializedName("data") val data: List<LISTREGION_LIST_DATA>
) : BaseResponceInterface

data class LISTREGION_LIST_DATA(
    @SerializedName("id") val id: Int,
    @SerializedName("date") val date: String,
    @SerializedName("number") val number: String,
    @SerializedName("dacha") val dacha: String?,
    @SerializedName("idDacha") val idDacha: Int?,
    @SerializedName("nameQuarter") val nameQuarter: String?,
    @SerializedName("sampleRegion") val sampleRegion: Double?,
    @SerializedName("soilLot") val soilLot: String?,
    @SerializedName("idDistrictForestly") val idDistrictForestly: Int?,
    @SerializedName("idForestly") val idForestly: Int?,
    @SerializedName("idSubject") val idSubject: Int?,
    @SerializedName("uuid") val uuid: String?
) : BaseResponceInterface
