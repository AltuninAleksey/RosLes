package com.example.rosles.Network

import com.example.rosles.RequestClass.SubjectRF
import com.example.rosles.ResponceClass.responceSubject
import kotlinx.coroutines.delay

class RetrofitAccountsSource(
    config: RetrofitConfig
) : BaseRetrofitSource(config), AccountsSource {

    private val accountsApi = retrofit.create(API::class.java)

    override suspend fun requestSubjectRF(requestSubjectRF: String): responceSubject = wrapRetrofitExceptions {
        delay(1000)
        val signInRequestEntity = SubjectRF(requestSubjectRF)
        accountsApi.requestsubjectRF(signInRequestEntity)
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