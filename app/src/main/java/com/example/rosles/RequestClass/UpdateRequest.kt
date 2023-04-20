package com.example.rosles.RequestClass

import okhttp3.MultipartBody
import retrofit2.http.Field



data class  UpdateRequest(var file: MultipartBody.Part,var id:Int)