package com.example.rosles.Network

import com.example.rosles.RequestClass.PerechetRequest
import com.example.rosles.RequestClass.SubjectRF
import com.example.rosles.ResponceClass.BaseResp
import com.example.rosles.ResponceClass.ReproductionResp
import com.example.rosles.ResponceClass.responceSubject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {
    // Интерфейс для передкачи данных, здесь содержатся адреса добавленные в апи, адрес сервера добавляется автоматически
    // конечный запрос будет выглядеть так : http://10.0.2.2:8000/subjectRF
    @POST("subjectRF")
    suspend fun requestsubjectRF(@Body body: SubjectRF): responceSubject

    @GET("reproduction")
    suspend fun reproduction(): ReproductionResp

    @POST("list")
    suspend fun perechet(@Body body: PerechetRequest): BaseResp
//    @GET("role")
//    suspend fun roleRequest(): responceRole
}