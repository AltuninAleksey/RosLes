package com.example.rosles.ResponceClass

import com.google.gson.annotations.SerializedName

data class DachaResp(
    @SerializedName("count") val count: Int,
    // data может прийти null — обрабатываем в ViewModels.getDacha.
    @SerializedName("data") val data: List<DachaData>?
) : BaseResponceInterface

data class DachaData(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    // Участковое лесничество, к которому привязано урочище (может отсутствовать).
    @SerializedName("idDistrictForestly") val idDistrictForestly: Int?
) : BaseResponceInterface
