package com.example.rosles.Network

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rosles.DBCountWood
import com.example.rosles.RequestClass.AuthRequest
import com.example.rosles.RequestClass.PerechetRequest
import com.example.rosles.RequestClass.RegistrationReqest
import com.example.rosles.RequestClass.UpdateRequest
import com.example.rosles.ResponceClass.*
import com.example.rosles.sync
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import kotlin.math.log


class ViewModels():BaseViewModel(
    accountsRepository = Singletons.accountsRepository,
    logger = LogCatLogger
){

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

    fun getUNDER(dbCountWood: DBCountWood)=viewModelScope.safeLaunch {
        try {
            accountsRepository.getUNDER().get.forEach {
                dbCountWood.writeundergrowth(it.id,it.name)
            }
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }
    fun getBREED(dbCountWood: DBCountWood)=viewModelScope.safeLaunch {
        try {
            accountsRepository.getBREED().get.forEach {
                dbCountWood.writeBREED(it.id,it.name_breed,it.is_foliar,it.is_pine,it.ShortName)
            }
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }
    fun getQUATER(dbCountWood: DBCountWood)=viewModelScope.safeLaunch {
        try {
            accountsRepository.getQUATER().get.forEach {
                dbCountWood.writeQUATER(it.id,it.quarter_name,it.id_district_forestly)
            }
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }
    fun getDISTRICTFORESTLY(dbCountWood: DBCountWood)=viewModelScope.safeLaunch {
        try {
            accountsRepository.getDISTRICTFORESTLY().get.forEach {
                dbCountWood.writeDISTRICT(it.id,it.name_district_forestly,it.id_forestly)
            }
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }
    fun getFORESTLY(dbCountWood: DBCountWood)=viewModelScope.safeLaunch {
        try {
            accountsRepository.getFORESTLY().get.forEach {
                dbCountWood.writeFORESTLY(it.id,it.name_forestly,it.id_subject_rf)
            }
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }
    fun getSUBJECTRF(dbCountWood: DBCountWood)=viewModelScope.safeLaunch {
        try {
            accountsRepository.getSUBJECTRF().get.forEach {
                dbCountWood.writeSUBJECTRF(it.id,it.name_subject_RF)
            }
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }
    fun getLISTREGION(dbCountWood: DBCountWood,pk_profile:Int)=viewModelScope.safeLaunch {
        try {
            accountsRepository.getLISTREGION(pk_profile).get.forEach {
                dbCountWood.writeLISTREGION(it.id,it.date,it.sample_region,it.id_quarter,it.soil_lot,it.mark_update,it.id_profile,it.number_region)
            }
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }
    fun getSAMPLE(dbCountWood: DBCountWood)=viewModelScope.safeLaunch {
        try {
            accountsRepository.getSAMPLE().get.forEach {
                dbCountWood.writeSAMPLE(it.id,it.date,it.sample_area,it.id_list_region,
                    it.id_profile,it.id_quarter,it.soil_lot,it.lenght,it.square,it.width)
            }
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }
    fun getLIST(dbCountWood: DBCountWood)=viewModelScope.safeLaunch {
        try {
            accountsRepository.getLIST().get.forEach {
                dbCountWood.writeLIST(it.id,it.to0_2,it.from0_21To0_5,it.from0_6To1_0,it.from1_1to1_5,
                it.from1_5,it.max_height,it.id_breed,it.id_sample,it.id_type_of_reproduction,it.avg_diameter,
                it.avg_height,it.count_of_plants,it.id_undergrowth,it.main,it.avg_height_undergrowth)
            }
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

   fun delete_listregion(id:Int)=viewModelScope.safeLaunch {
       accountsRepository.delete_listregion(id)
   }
    fun delete_sample(id:Int)=viewModelScope.safeLaunch {
        accountsRepository.delete_sample(id)
    }


    fun get_user(body:AuthRequest)=viewModelScope.safeLaunch {
        try {
            user.postValue(accountsRepository.get_user(body))
        } catch (e: Exception) {
            Log.e("asd",e.toString())
        }
    }
    fun upload(body: UpdateRequest)=viewModelScope.safeLaunch {
        try {
            accountsRepository.upload(body)
        } catch (e: EmptyFieldException){
            processEmptyFieldException(e)
        }
    }
    suspend fun registration(body: RegistrationReqest):Response<RegistrationReqest>{
       return accountsRepository.registration(body)
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

         fun putLISTREGION(body:LISTREGION_REQUEST)=viewModelScope.safeLaunch{
            var asd=accountsRepository.putLISTREGION(body)


             //простыня кода нужная для сериализации ответа
            var temp=asd.source().buffer.toString()

            var bufer=temp.toCharArray()

             bufer.set(0,'{')
             bufer.set(bufer.size-1,'}')

             var temp2=""
             bufer.forEach {
                 temp2+=it
             }

             val gson = Gson()
             val book = gson.fromJson(temp2, text::class.java)


             //отправка в синглтон
              sync.temp.temp_object=book
        }


    fun putSAMPLE(body:SAMPLE_REQEST)=viewModelScope.safeLaunch{
        accountsRepository.putSAMPLE(body)

    }
    fun putLIST(body:LIST_REQEST)=viewModelScope.safeLaunch{
        accountsRepository.putLIST(body)

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

