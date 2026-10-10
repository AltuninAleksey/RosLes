package com.example.rosles.RequestClass

import com.google.gson.annotations.SerializedName

/**
 * PUT forestcrops/api/mobile/sample/save.
 * Ключи — как в контракте: "IdProfile" с заглавной, массив — "saveSampleItemMobile".
 */
data class SaveSampleRequest(
    @SerializedName("IdProfile") val idProfile: Int,
    @SerializedName("saveSampleItemMobile") val saveSampleItemMobile: List<SaveSampleItem>
)

data class SaveSampleItem(
    @SerializedName("number") val number: String?,
    @SerializedName("length") val length: Double?,
    @SerializedName("width") val width: Double?,
    @SerializedName("uuid") val uuid: String,
    @SerializedName("uuidListRegion") val uuidListRegion: String?
)
