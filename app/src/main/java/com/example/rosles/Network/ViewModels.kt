package com.example.rosles.Network

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope


class ViewModels():BaseViewModel(accountsRepository=Singletons.accountsRepository, logger = LogCatLogger){

    private val _state = MutableLiveData(State())
    val state = _state.share()
    var role:String?=null
    data class State(
        val emptyEmailError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
        val enableViews: Boolean get() = !signInInProgress
    }

    fun requestSubjectRF(requestSubjectRF: String) = viewModelScope.safeLaunch {
        try {
            accountsRepository?.requestSubjectRF(requestSubjectRF)
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }

    }

    private fun processEmptyFieldException(e: EmptyFieldException) {
        _state.value = _state.requireValue().copy(
            emptyEmailError = e.field == Field.Email,
            emptyPasswordError = e.field == Field.Password
        )
    }
    fun <T> LiveData<T>.requireValue(): T {
        return this.value ?: throw IllegalStateException("Value is empty")
    }
}

