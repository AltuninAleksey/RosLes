package com.example.rosles.Network

import com.example.rosles.RequestClass.AuthRequest
import com.example.rosles.RequestClass.PerechetRequest
import com.example.rosles.RequestClass.RegistrationReqest
import com.example.rosles.ResponceClass.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface API {
    // Интерфейс для передкачи данных, здесь содержатся адреса добавленные в апи, адрес сервера добавляется автоматически
    // конечный запрос будет выглядеть так : http://90.156.208.88:8093/subjectRF
//    @POST("subjectRF")
//    suspend fun requestsubjectRF(@Body body: SubjectRF): responceSubject

    @GET("reproduction")
    suspend fun reproduction(): ReproductionResp

    @POST("registration")
    suspend fun registration(@Body body: RegistrationReqest): BaseResp

    @GET("responsesqlite")
    suspend fun getbd():BaseResp


    @Multipart
    @POST("upload")
    @Headers(
        "X-Atlassian-Token: no-check",
        "Allow:POST, OPTIONS",
//        "Content-Disposition: attachment; filename=aaa.jpg"
    )
    suspend fun upload( @Part photo: MultipartBody.Part?,
                        @Part("id_sample") id_sample: Int): BaseResp

    @POST("list")
    suspend fun perechet(@Body body: PerechetRequest): BaseResp
//    @GET("role")
//    suspend fun roleRequest(): responceRole

    @GET("post")
    suspend fun getpost():BaseResp

    @POST("auth")
    suspend fun get_user(@Body body: AuthRequest): AuthReSponce

    @GET("categorygroundlfinnoneaccordance")
    suspend fun getcategorygroundlfinnoneaccordance():BaseResp

    @GET("profile")
    suspend fun  getprofile(): getUserResp

    @GET("subjectRF")
    suspend fun getrequestsubjectRF(): SubjectResp

    @GET("getforestlybysubjectid/{id}")
    suspend fun forestlubyid(@Path("id") id: Int): ForestlyResp

    @GET("getdistrictbyforestly/{id}")
    suspend fun districtbyID(@Path("id") id: Int): DistrictResp

    @GET("getquarterbydistrictid/{id}")
    suspend fun quaterdistrictbyID(@Path("id") id: Int): CvartalResp

    @GET("breed")
    suspend fun getbreed(): BreedResp
}