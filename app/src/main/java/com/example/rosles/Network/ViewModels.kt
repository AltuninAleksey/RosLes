package com.example.rosles.Network

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rosles.DBCountWood
import com.example.rosles.RequestClass.PerechetRequest
import com.example.roslesdef.Adapters.UdelAdapter
import com.example.roslesdef.Models.ItemWood
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext


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

    fun requestSubjectRF(requestSubjectRF: String,context: Context) = viewModelScope.safeLaunch {
        try {
            accountsRepository?.requestSubjectRF(requestSubjectRF)
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }

    }

    fun reproduction(context: Context)=viewModelScope.safeLaunch{
            try {
                var resp=accountsRepository?.reproduction()

                val db = DBCountWood(context,null)
                db.addReproduction(resp?.get(0)?.name_reproduction.toString())
                db.addReproduction(resp?.get(1)?.name_reproduction.toString())


            } catch (e: EmptyFieldException) {
                processEmptyFieldException(e)
            }
    }
    fun perechet(perechetRequest: PerechetRequest,context: Context)=viewModelScope.safeLaunch{
        try {
            accountsRepository?.perechet(perechetRequest)
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

