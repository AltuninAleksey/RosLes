package com.example.rosles.Network

import androidx.lifecycle.viewModelScope

import com.example.rosles.ResponceClass.BaseResponceInterface
import kotlinx.coroutines.launch


class SafeRequest(val viewModel: ViewModels) {

    interface Protection {
        suspend fun makeRequest() : BaseResponceInterface?

        fun ifSuccess(responce: BaseResponceInterface?)

        fun ifException() {}
        fun ifConnectionException() {}
        fun ifBackendException() {}
        fun ifAuthException() {}
    }

    var responce: BaseResponceInterface? = null
    val logger: Logger = LogCatLogger

    fun request (protection: Protection) {
        viewModel.viewModelScope.launch {
            try {
                responce = protection.makeRequest()
                protection.ifSuccess(responce)
            } catch (e: ConnectionException) {
                logger.error("", e)
                protection.ifConnectionException()
            } catch (e: BackendException) {
                logger.error("", e)
                if (e.code == 401) {
                    protection.ifAuthException()
                } else {
                    protection.ifBackendException()
                }
            } catch (e: AuthException) {
                logger.error("", e)
                protection.ifAuthException()
            } catch (e: Exception) {
                logger.error("", e)
                protection.ifException()
            }

        }
    }
}