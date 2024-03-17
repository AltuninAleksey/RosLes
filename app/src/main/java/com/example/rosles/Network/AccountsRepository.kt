package com.example.rosles.Network

import com.example.rosles.RequestClass.AuthRequest
import com.example.rosles.RequestClass.PerechetRequest
import com.example.rosles.RequestClass.RegistrationReqest
import com.example.rosles.RequestClass.UpdateRequest
import com.example.rosles.ResponceClass.*
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET


interface AccountsSource {


    suspend fun getUNDER():UNDER_RESP
    suspend fun getBREED():BREED_RESP
    suspend fun getQUATER():QUATER_RESP
    suspend fun getDISTRICTFORESTLY():DISTRICTFORESTLY_RESP
    suspend fun getFORESTLY():FORESTLY_RESP
    suspend fun getSUBJECTRF(value: Int):SUBJECTRF_RESP
    suspend fun getLISTREGION(pk_profile:Int):LISTREGION_RESP
    suspend fun getSAMPLE():SAMPLE_RESP
    suspend fun getLIST():LIST_RESP


    suspend fun reproduction(): ReproductionResp
    suspend fun perechet(perechetRequest: PerechetRequest): BaseResp
    suspend fun registration(registrationReqest: RegistrationReqest): Response<RegistrationReqest>
    suspend fun getrequestsubjectRF(): SubjectResp
    suspend fun forestlubyid(id:Int): ForestlyResp
    suspend fun districtbyID(id:Int): DISTRICTFORESTLY_RESP
    suspend fun quaterdistrictbyID(id:Int): CvartalResp
    suspend fun getprofile():getUserResp

    suspend fun getprofileid(id:Int):temp_data_userresp
    suspend fun getbreed():BreedResp
    suspend fun getbd():BaseResp
    suspend fun get_user(body:AuthRequest):AuthReSponce
    suspend fun upload(body: UpdateRequest): BaseResp

    suspend fun putprofile(id: Int,body: UserResp): ResponseBody


    suspend fun putLISTREGION(body: LISTREGION_REQUEST): ResponseBody

    suspend fun putSAMPLE(body: SAMPLE_REQEST): ResponseBody

    suspend fun putLIST(body: LIST_REQEST): ResponseBody

    suspend fun delete_listregion(id: Int): ResponseBody

    suspend fun delete_sample(id: Int): ResponseBody


    //suspend fun roleRequst(): responceRole

    fun getCurrentToken(): String?
    fun setCurrentToken(token: String?)
}


class AccountsRepository( private val accountsSource: AccountsSource) {


    suspend fun getUNDER():UNDER_RESP=accountsSource.getUNDER()
    suspend fun getBREED():BREED_RESP=accountsSource.getBREED()
    suspend fun getQUATER():QUATER_RESP=accountsSource.getQUATER()
    suspend fun getDISTRICTFORESTLY():DISTRICTFORESTLY_RESP=accountsSource.getDISTRICTFORESTLY()
    suspend fun getFORESTLY():FORESTLY_RESP=accountsSource.getFORESTLY()
    suspend fun getSUBJECTRF(value: Int):SUBJECTRF_RESP=accountsSource.getSUBJECTRF(value)
    suspend fun getLISTREGION(pk_profile:Int):LISTREGION_RESP=accountsSource.getLISTREGION(pk_profile)
    suspend fun getSAMPLE():SAMPLE_RESP=accountsSource.getSAMPLE()
    suspend fun getLIST():LIST_RESP=accountsSource.getLIST()



    suspend fun putLISTREGION(body: LISTREGION_REQUEST):ResponseBody = accountsSource.putLISTREGION(body)

    suspend fun putSAMPLE(body: SAMPLE_REQEST):ResponseBody = accountsSource.putSAMPLE(body)

    suspend fun putLIST(body: LIST_REQEST):ResponseBody = accountsSource.putLIST(body)

    suspend fun putprofile(id:Int,body: UserResp):ResponseBody = accountsSource.putprofile(id,body)


    suspend fun reproduction(): List<GETReproductionResp> = accountsSource.reproduction().get

    suspend fun perechet(perechetRequest: PerechetRequest): BaseResp =  accountsSource.perechet(perechetRequest)

    suspend fun registration (registrationReqest: RegistrationReqest): Response<RegistrationReqest> = accountsSource.registration(registrationReqest)

    suspend fun getprofile(): getUserResp =  accountsSource.getprofile()


    suspend fun getprofileid(id: Int): temp_data_userresp =  accountsSource.getprofileid(id)

    suspend fun getrequestsubjectRF():SubjectResp=accountsSource.getrequestsubjectRF()

    suspend fun forestlubyid(id:Int):ForestlyResp=accountsSource.forestlubyid(id)

    suspend fun districtbyID(id:Int):DISTRICTFORESTLY_RESP=accountsSource.districtbyID(id)

    suspend fun quaterdistrictbyID(id:Int):CvartalResp=accountsSource.quaterdistrictbyID(id)

    suspend fun getbreed():BreedResp=accountsSource.getbreed()

    suspend fun get_user(body: AuthRequest):AuthReSponce=accountsSource.get_user(body)

    suspend fun upload(body: UpdateRequest):BaseResp=accountsSource.upload(body)

    suspend fun getbd():BaseResp=accountsSource.getbd()

    suspend fun delete_listregion(id: Int):ResponseBody=accountsSource.delete_listregion(id)

    suspend fun delete_sample(id: Int):ResponseBody=accountsSource.delete_sample(id)






//    suspend fun roleRequest():List<GetResp> {
//        var resp=try {
//            accountsSource.roleRequst().get
//        } catch (e: BackendException) {
//            // user with such email already exists
//            if (e.code == 409) throw AccountAlreadyExistsException(e)
//            else throw e
//        }
//        return resp
//    }
}

