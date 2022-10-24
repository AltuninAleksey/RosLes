package com.example.roslesdef

import retrofit2.http.POST
import retrofit2.http.Body

interface API {

    @POST("breed")
    suspend fun testapi(@Body body :String)

}


