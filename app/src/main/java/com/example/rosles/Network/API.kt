package com.example.rosles.Network

import com.example.rosles.RequestClass.AuthRequest
import com.example.rosles.RequestClass.PerechetRequest
import com.example.rosles.RequestClass.RegistrationReqest
import com.example.rosles.ResponceClass.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface API {
    // Интерфейс для передкачи данных, здесь содержатся адреса добавленные в апи, адрес сервера добавляется автоматически
    // конечный запрос будет выглядеть так : http://90.156.208.88:8093/subjectRF
//    @POST("subjectRF")
//    suspend fun requestsubjectRF(@Body body: SubjectRF): responceSubject

    @GET("reproduction")
    suspend fun reproduction(): ReproductionResp

    @POST("registration")
    suspend fun registration(@Body body: RegistrationReqest): Response<RegistrationReqest>

    @GET("responsesqlite")
    suspend fun getbd():BaseResp


    @GET("undergrowth")
    suspend fun getUNDER():UNDER_RESP
    @GET("breed")
    suspend fun getBREED():BREED_RESP
    @GET("quarter")
    suspend fun getQUATER():QUATER_RESP
    @GET("districtforestly")
    suspend fun getDISTRICTFORESTLY():DISTRICTFORESTLY_RESP
    @GET("forestly")
    suspend fun getFORESTLY():FORESTLY_RESP
    @GET("czlbyprofile/{id}")
    suspend fun getSUBJECTRF(@Path("id") value: Int):SUBJECTRF_RESP
    @GET("listregionbyprofile/{id}")
    suspend fun getLISTREGION(@Path("id") pk_profile: Int):LISTREGION_RESP
    @GET("sample")
    suspend fun getSAMPLE():SAMPLE_RESP
    @GET("list")
    suspend fun getLIST():LIST_RESP

    @PUT("listregion")
    suspend fun putLISTREGION(@Body body:LISTREGION_REQUEST):ResponseBody

    @PUT("sample")
    suspend fun putSAMPLE(@Body body:SAMPLE_REQEST):ResponseBody

    @PUT("list")
    suspend fun putLIST(@Body body:LIST_REQEST):ResponseBody

    @PUT("profile/{id}")
    suspend fun updateprofileid(@Path("id") id: Int,@Body body:UserResp):ResponseBody



    @Multipart
    @POST("upload")
    @Headers(
        "X-Atlassian-Token: no-check",
        "Allow:POST, OPTIONS",
//        "Content-Disposition: attachment; filename=aaa.jpg"
    )
    suspend fun upload( @Part photo: MultipartBody.Part?,
                        @Part("id_sample") id_sample: Int,
                        @Part("latitude") latitude: Double,
                        @Part("longitude") longitude: Double,
                        @Part("date") date: String,): BaseResp

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


    @GET("profile/{id}")
    suspend fun  getprofileid(@Path("id") id: Int): temp_data_userresp

    @POST("gps")
    suspend fun sendgps(@Body post: GPS_Data_Send): BaseResp

    @GET("subjectRF")
    suspend fun getrequestsubjectRF(): SubjectResp

    @GET("getforestlybysubjectid/{id}")
    suspend fun forestlubyid(@Path("id") id: Int): ForestlyResp

    @GET("getdistrictbyforestly/{id}")
    suspend fun districtbyID(@Path("id") id: Int): DISTRICTFORESTLY_RESP

    @GET("getquarterbydistrictid/{id}")
    suspend fun quaterdistrictbyID(@Path("id") id: Int): CvartalResp

    @GET("breed")
    suspend fun getbreed(): BreedResp

    @DELETE("listregion/{id}")
    suspend fun delete_listregion(@Path("id") id: Int): ResponseBody

    @DELETE("sample/{id}")
    suspend fun delete_sample(@Path("id") id: Int): ResponseBody
}