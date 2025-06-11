package com.example.rosles.RequestClass

import okhttp3.MultipartBody


data class  UpdateRequest(
    var file: MultipartBody.Part,
    val id_sample: String,
    val latitude: Double,
    val longitude: Double,
    val date: String
)