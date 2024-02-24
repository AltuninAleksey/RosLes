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
    var profile = MutableLiveData<getUserResp>()
    var uploadbd=MutableLiveData<BaseResp>()





    data class State(
        val emptyEmailError: Boolean = false,
        val emptyPasswordError: Boolean = false,
        val signInInProgress: Boolean = false
    ) {
        val showProgress: Boolean get() = signInInProgress
        val enableViews: Boolean get() = !signInInProgress
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

    fun getDISTRICTFORESTLY(dbCountWood: DBCountWood,id: Int)=viewModelScope.safeLaunch {
        try {
            accountsRepository.districtbyID(id).data.forEach {
                dbCountWood.writeDISTRICT(it.id,it.name_district_forestly,it.id_forestly)
            }
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }
    fun getFORESTLY(dbCountWood: DBCountWood,id_Subject:Int)=viewModelScope.safeLaunch {
        try {
            accountsRepository.forestlubyid(id_Subject).data.forEach {
                dbCountWood.writeFORESTLY(it.id,it.name_forestly,id_Subject)

                getDISTRICTFORESTLY(dbCountWood,it.id)

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
                dbCountWood.writeLISTREGION(it.id,it.date,it.sample_region,it.id_quarter,it.soil_lot,it.mark_update,it.id_profile,it.number_region,it.id_district_forestly)
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

    fun districtbyID(id:Int,dbCountWood: DBCountWood)=viewModelScope.safeLaunch {
        try {
            accountsRepository.districtbyID(id).data.forEach{

                var asd=it.name_district_forestly
                asd
                    dbCountWood.writeDISTRICT(it.id,it.name_district_forestly,it.id_forestly)

            }
        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }

   fun delete_listregion(id:Int)=viewModelScope.safeLaunch {
       accountsRepository.delete_listregion(id)
   }
    fun delete_sample(id:Int)=viewModelScope.safeLaunch {
        accountsRepository.delete_sample(id)
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

    fun putprofile(id: Int,body: UserResp)=viewModelScope.safeLaunch{
        try {
            accountsRepository?.putprofile(id,body)

        } catch (e: EmptyFieldException) {
            processEmptyFieldException(e)
        }
    }

         fun putLISTREGION(body:LISTREGION_REQUEST)=viewModelScope.safeLaunch{

             //простыня кода нужная для сериализации ответа
             var bufer=accountsRepository.putLISTREGION(body).source().buffer.toString().toCharArray()
             bufer.set(0,'{')
             bufer.set(bufer.size-1,'}')
             var temp2=""
             bufer.forEach {
                 temp2+=it
             }
             val book = Gson().fromJson(temp2, text::class.java)
             //отправка в синглтон
              sync.temp.temp_object=book
        }


    fun putSAMPLE(body:SAMPLE_REQEST)=viewModelScope.safeLaunch{

        //простыня кода нужная для сериализации ответа
        var bufer=accountsRepository.putSAMPLE(body).source().buffer.toString().toCharArray()
        bufer.set(0,'{')
        bufer.set(bufer.size-1,'}')
        var temp2=""
        bufer.forEach {
            temp2+=it
        }
        val book = Gson().fromJson(temp2, text::class.java)
        //отправка в синглтон
        sync.temp.temp_objectsample=book
    }


    fun putLIST(body:LIST_REQEST)=viewModelScope.safeLaunch{

        body.data.get(0).id
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

