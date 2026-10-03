package com.example.rosles.RequestClass

import com.google.gson.annotations.SerializedName

/**
 * PUT forestcrops/api/mobile/listregion/save.
 * Ключи — как в контракте: "IdProfile" с заглавной, массив — "saveListRegionRequest".
 */
data class SaveListRegionRequest(
    @SerializedName("IdProfile") val idProfile: Int,
    @SerializedName("saveListRegionRequest") val saveListRegionRequest: List<SaveListRegionItem>
)

data class SaveListRegionItem(
    @SerializedName("date") val date: String,
    @SerializedName("dacha") val dacha: String?,
    @SerializedName("idDacha") val idDacha: Int?,
    @SerializedName("nameQuarter") val nameQuarter: String?,
    @SerializedName("sampleRegion") val sampleRegion: Double?,
    @SerializedName("soilLot") val soilLot: String?,
    @SerializedName("idDistrictForestly") val idDistrictForestly: Int?,
    @SerializedName("uuid") val uuid: String
)
