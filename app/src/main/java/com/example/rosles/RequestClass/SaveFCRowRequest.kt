package com.example.rosles.RequestClass

import com.google.gson.annotations.SerializedName


data class SaveFCRowRequest(
    @SerializedName("IdProfile") val idProfile: Int,
    @SerializedName("saveForestCropsRowItem") val saveForestCropsRowItem: List<SaveFCRowItem>
)

data class SaveFCRowItem(
    @SerializedName("number") val number: Int?,
    @SerializedName("countLiving") val countLiving: Int?,
    @SerializedName("countDead") val countDead: Int?,
    @SerializedName("idBreed") val idBreed: Int?,
    @SerializedName("uuid") val uuid: String,
    @SerializedName("uuidSample") val uuidSample: String?
)
