package com.example.rosles

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import com.example.rosles.Network.ViewModels
import com.example.rosles.RequestClass.UpdateRequest
import com.example.rosles.ResponceClass.GPS_Data_Send
import com.example.rosles.ResponceClass.LISTREGION_REQUEST
import com.example.rosles.ResponceClass.LIST_DATA
import com.example.rosles.ResponceClass.LIST_REQEST
import com.example.rosles.ResponceClass.LIST_RESP
import com.example.rosles.ResponceClass.SAMPLE_DATA
import com.example.rosles.ResponceClass.SAMPLE_REQEST
import com.example.rosles.ResponceClass.SAMPLE_RESP
import com.example.rosles.ResponceClass.id
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.UUID


class sync() {

    suspend fun main1(
        viewModels: ViewModels,
        db: DBCountWood,
        context: AppCompatActivity,
        value: Int,
        block: () -> Unit
    ) {

        if (!db.djangoForest_undergrowth())
            viewModels.getUNDER(db)

        if (!db.djangoForest_breed())
            viewModels.getBREED(db)

        if (!db.djangoForest_subjectrf())
            viewModels.getSUBJECTRF(db, value)


//            if(!db.djangoForest_forestly())
//                viewModels.getFORESTLY(db,id_subject)

        if (!db.djangoForest_listregion())
            viewModels.getLISTREGION(db, value)

        if (!db.djangoForest_sample())
            viewModels.getSAMPLE(db)

        if (!db.djangoForest_list())
            viewModels.getLIST(db) {
                block()
            }
        else {
            delay(2000)
            block()
        }


    }

//костыльный синглтон для получения ответа от сервера переделать на ретурн или клбек


    suspend fun load(viewModels: ViewModels, db: DBCountWood, context: AppCompatActivity) {


        val listregion = db.getLISTREGION()
        val sample = db.getAllSAMPLE()
        val list = db.getGLIST()



        listregion.forEach {
            if (it.dacha == "")
                it.dacha = null
        }

        viewModels.putLISTREGION(LISTREGION_REQUEST(listregion))
        delay(1000)
        viewModels.putSAMPLE(SAMPLE_REQEST(sample))
        delay(1000)
        viewModels.putLIST(LIST_REQEST(list))
        delay(10000)


        sample.forEach {
            db.getphoto( it.id).forEach {
                val wrapper = ContextWrapper(context)
                var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
                file = File(file, "${UUID.randomUUID()}.jpg")
                val stream: OutputStream = FileOutputStream(file)
                it.photo!!.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()
                val photoFile = file
                val photo = MultipartBody.Part.createFormData(
                    "photo",
                    photoFile.name,
                    photoFile.asRequestBody("image/*".toMediaType())
                )
                viewModels.upload(
                    UpdateRequest(
                        photo,
                        it.id_sample,
                        it.latitude.toDouble(),
                        it.longitude.toDouble(),
                        it.date
                    )
                )
            }
            delay(1000)

        }




        db.SEND_Gps_Data().forEach {
            var flag = 0
            if (it.flag_center) {
                flag = 1
            }
            viewModels.sendgps(
                GPS_Data_Send(
                    it.latitude,
                    it.longitude,
                    flag,
                    it.id_sample
                )
            )
        }


//TEST
        var filePath = "/data/data/com.example.rosles/databases/userdb.db"
        var file = File(filePath)
        if (file.exists()) {

           file.delete()
            delay(5000)
        }

    }
//    fun delete_values(db: DBCountWood,viewModels: ViewModels){
//        val listregion= db.get_delete_listregion()
//        val sample= db.get_delete_sample()
//
//        listregion.forEach{
//            viewModels.delete_listregion(it)
//        }
//        sample.forEach{
//            viewModels.delete_sample(it)
//        }
//
//    }


}

//    fun sendFileRequest(image: Bitmap,latitude:Double,longitude:Double,date: String,id:Int) {
//        val wrapper = ContextWrapper(context)
//        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
//        file = File(file,"${UUID.randomUUID()}.jpg")
//        val stream: OutputStream = FileOutputStream(file)
//        image.compress(Bitmap.CompressFormat.JPEG,25,stream)
//        stream.flush()
//        stream.close()
//
//        val photoFile = file
//        val photo = MultipartBody.Part.createFormData(
//            "photo",
//            photoFile.name,
//            photoFile.asRequestBody("image/*".toMediaType())
//        )
//        //viewModel.upload(UpdateRequest(photo,id,latitude,longitude,date))
//    }




