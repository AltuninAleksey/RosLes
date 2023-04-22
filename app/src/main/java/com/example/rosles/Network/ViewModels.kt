package com.example.rosles.Network

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rosles.DBCountWood
import com.example.rosles.RequestClass.AuthRequest
import com.example.rosles.RequestClass.PerechetRequest
import com.example.rosles.RequestClass.RegistrationReqest
import com.example.rosles.RequestClass.UpdateRequest
import com.example.rosles.ResponceClass.*


class ViewModels():BaseViewModel(accountsRepository=Singletons.accountsRepository, logger = LogCatLogger, ){

    private lateinit var accountsSource: AccountsSource

    private val _state = MutableLiveData(State())
    var guide = MutableLiveData<List<GETReproductionResp>>()
    var profile = MutableLiveData<getUserResp>()
    var subject = MutableLiveData<SubjectResp>()
    var forestly = MutableLiveData<ForestlyResp>()
    var district = MutableLiveData<DistrictResp>()
    var cvartal = MutableLiveData<CvartalResp>()
    var breed = MutableLiveData<BreedResp>()
    var user=MutableLiveData<AuthReSponce>()
    var uploadbd=MutableLiveData<BaseResp>()


    data class State(
        val emptyEmailError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
        val enableViews: Boolean get() = !signInInProgress
    }

    fun getprofile()=viewModelScope.safeLaunch {
        try {
            profile.postValue(accountsRepository?.getprofile() )
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }



    fun getrequestsubjectRF()=viewModelScope.safeLaunch {
        try {
            subject.postValue(accountsRepository.getrequestsubjectRF())
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }
    fun forestlubyid(id:Int)=viewModelScope.safeLaunch {
        try {
            forestly.postValue(accountsRepository.forestlubyid(id))
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }
    fun districtbyID(id:Int)=viewModelScope.safeLaunch {
        try {
            district.postValue(accountsRepository.districtbyID(id))
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }
    fun quaterdistrictbyID(id:Int)=viewModelScope.safeLaunch {
        try {
            cvartal.postValue(accountsRepository.quaterdistrictbyID(id))
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }

    fun getbreed()=viewModelScope.safeLaunch {
        try {
            breed.postValue(accountsRepository.getbreed())
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }

    fun getbd()=viewModelScope.safeLaunch {
        uploadbd.postValue(accountsRepository.getbd())
    }

    fun get_user(body:AuthRequest)=viewModelScope.safeLaunch {
        try {
            user.postValue(accountsRepository.get_user(body))
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }
    fun upload(body: UpdateRequest)=viewModelScope.safeLaunch {
        try {
            accountsRepository.upload(body)
        } catch (e: EmptyFieldException){
            processEmptyFieldException(e)
        }
    }
    fun registration(body: RegistrationReqest)=viewModelScope.safeLaunch {
        accountsRepository.registration(body)
    }

    fun reproduction(context: Context)=viewModelScope.safeLaunch{
        try {
            var resp=accountsRepository?.reproduction()
            //guide.postValue(resp)
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

