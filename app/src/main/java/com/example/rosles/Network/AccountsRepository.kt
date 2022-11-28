package com.example.rosles.Network

import com.example.rosles.RequestClass.PerechetRequest
import com.example.rosles.ResponceClass.BaseResp
import com.example.rosles.ResponceClass.GETReproductionResp
import com.example.rosles.ResponceClass.ReproductionResp
import com.example.rosles.ResponceClass.responceSubject
import com.squareup.moshi.Moshi
import retrofit2.Retrofit


interface AccountsSource {
    suspend fun requestSubjectRF(requestSubjectRF: String): responceSubject
    suspend fun reproduction(): ReproductionResp
    suspend fun perechet(perechetRequest: PerechetRequest): BaseResp
    //suspend fun roleRequst(): responceRole

    fun getCurrentToken(): String?
    fun setCurrentToken(token: String?)
}


class AccountsRepository( private val accountsSource: AccountsSource) {

    public fun getResponce(): String {
        return "Responce"
    }


    suspend fun requestSubjectRF(requestSubjectRF: String): String {
        var resp=try {
            accountsSource.requestSubjectRF(requestSubjectRF).post.name_subject_RF
        } catch (e: BackendException) {
            // user with such email already exists
            if (e.code == 409) throw AccountAlreadyExistsException(e)
            else throw e
        }
        return resp
    }
    suspend fun reproduction(): List<GETReproductionResp> {
        var resp=try {
            accountsSource.reproduction().get
        } catch (e: BackendException) {
            // user with such email already exists
            if (e.code == 409) throw AccountAlreadyExistsException(e)
            else throw e
        }
        return resp
    }
    suspend fun perechet(perechetRequest: PerechetRequest): BaseResp {
        var resp=try {
            accountsSource.perechet(perechetRequest)
        } catch (e: BackendException) {
            // user with such email already exists
            if (e.code == 409) throw AccountAlreadyExistsException(e)
            else throw e
        }
        return resp
    }


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
