package com.example.rosles.Network

import com.example.rosles.RequestClass.*
import com.example.rosles.ResponceClass.*
import kotlinx.coroutines.delay
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

class RetrofitAccountsSource(
    config: RetrofitConfig
) : BaseRetrofitSource(config), AccountsSource {

    private val accountsApi = retrofit.create(API::class.java)
    override suspend fun getUNDER(): UNDER_RESP= wrapRetrofitExceptions  {
        delay(1000)
        accountsApi.getUNDER()
    }

    override suspend fun getBREED(): BREED_RESP= wrapRetrofitExceptions  {
        delay(1000)
        accountsApi.getBREED()
    }

    override suspend fun getQUATER(): QUATER_RESP = wrapRetrofitExceptions {
        delay(1000)
        accountsApi.getQUATER()
    }

    override suspend fun getDISTRICTFORESTLY(): DISTRICTFORESTLY_RESP= wrapRetrofitExceptions  {
        delay(1000)
        accountsApi.getDISTRICTFORESTLY()
    }

    override suspend fun getFORESTLY(): FORESTLY_RESP= wrapRetrofitExceptions  {
        delay(1000)
        accountsApi.getFORESTLY()
    }

    override suspend fun getSUBJECTRF(): SUBJECTRF_RESP= wrapRetrofitExceptions  {
        delay(1000)
        accountsApi.getSUBJECTRF()
    }

    override suspend fun getLISTREGION(pk_profile:Int): LISTREGION_RESP= wrapRetrofitExceptions  {
        delay(1000)
        accountsApi.getLISTREGION(pk_profile)
    }

    override suspend fun getSAMPLE(): SAMPLE_RESP = wrapRetrofitExceptions {
        delay(1000)
        accountsApi.getSAMPLE()
    }

    override suspend fun getLIST(): LIST_RESP= wrapRetrofitExceptions  {
        delay(1000)
        accountsApi.getLIST()
    }

    override suspend fun putLISTREGION(body:LISTREGION_REQUEST): ResponseBody = wrapRetrofitExceptions  {
        delay(1000)
        accountsApi.putLISTREGION(body)
    }

    override suspend fun putSAMPLE(body:SAMPLE_REQEST): ResponseBody = wrapRetrofitExceptions  {
        delay(1000)
        accountsApi.putSAMPLE(body)
    }

    override suspend fun putLIST(body:LIST_REQEST): ResponseBody = wrapRetrofitExceptions  {
        delay(1000)
        accountsApi.putLIST(body)
    }



    override suspend fun reproduction(): ReproductionResp = wrapRetrofitExceptions {
        delay(1000)
        accountsApi.reproduction()
    }

    override suspend fun upload(body: UpdateRequest): BaseResp =wrapRetrofitExceptions {
        delay(1000)
        accountsApi.upload( body.file,body.id,body.latitude,body.longitude,body.date)
    }

    override suspend fun perechet(perechetRequest: PerechetRequest): BaseResp = wrapRetrofitExceptions {
        delay(1000)
        accountsApi.perechet(perechetRequest)
    }

    override suspend fun registration(registrationReqest: RegistrationReqest): Response<RegistrationReqest> = wrapRetrofitExceptions {
        delay(1000)
        accountsApi.registration(registrationReqest)
    }


    override suspend fun getbd(): BaseResp = wrapRetrofitExceptions {
        delay(1000)
        accountsApi.getbd()
    }

    override suspend fun getrequestsubjectRF(): SubjectResp = wrapRetrofitExceptions {
        delay(1000)
        accountsApi.getrequestsubjectRF()
    }

    override suspend fun forestlubyid( id:Int): ForestlyResp = wrapRetrofitExceptions {
        delay(1000)
        accountsApi.forestlubyid(id)
    }

    override suspend fun districtbyID(id:Int): DistrictResp = wrapRetrofitExceptions {
        delay(1000)
        accountsApi.districtbyID(id)
    }

    override suspend fun quaterdistrictbyID(id:Int): CvartalResp = wrapRetrofitExceptions {
        delay(1000)
        accountsApi.quaterdistrictbyID(id)
    }
    override suspend fun getprofile(): getUserResp =wrapRetrofitExceptions{
        delay(1000)
        accountsApi.getprofile()
    }



     override suspend fun get_user(body:AuthRequest): AuthReSponce =wrapRetrofitExceptions{
        delay(1000)
        accountsApi.get_user(body)
    }

    override suspend fun getbreed(): BreedResp =wrapRetrofitExceptions{
        delay(1000)
        accountsApi.getbreed()
    }

    override suspend fun delete_listregion(id: Int): ResponseBody =wrapRetrofitExceptions{
        delay(1000)
        accountsApi.delete_listregion(id)
    }
    override suspend fun delete_sample(id: Int): ResponseBody =wrapRetrofitExceptions{
        delay(1000)
        accountsApi.delete_sample(id)
    }


    override fun getCurrentToken(): String? {
      return "s"
    }

    override fun setCurrentToken(token: String?) {

    }

//    override suspend fun roleRequst(): responceRole = wrapRetrofitExceptions {
//        delay(1000)
//        accountsApi.roleRequest()
//    }

}