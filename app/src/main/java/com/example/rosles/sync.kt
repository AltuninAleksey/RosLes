package com.example.rosles

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rosles.Network.ViewModels
import com.example.rosles.RequestClass.UpdateRequest
import com.example.rosles.ResponceClass.LISTREGION_DATA
import com.example.rosles.ResponceClass.LISTREGION_REQUEST
import com.example.rosles.ResponceClass.LIST_DATA
import com.example.rosles.ResponceClass.LIST_REQEST
import com.example.rosles.ResponceClass.SAMPLE_DATA
import com.example.rosles.ResponceClass.SAMPLE_REQEST
import com.example.rosles.ResponceClass.text
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.UUID


class sync() {



    fun main1( viewModels: ViewModels, db: DBCountWood,  context: AppCompatActivity,value: Int) {

            if(!db.djangoForest_undergrowth())
                viewModels.getUNDER(db)

            if(!db.djangoForest_breed())
                viewModels.getBREED(db)

            if(!db.djangoForest_quarter())
                viewModels.getQUATER(db)

            if(!db.djangoForest_districtforestly())
                viewModels.getDISTRICTFORESTLY(db)

            if (!db.djangoForest_forestly())
                viewModels.getFORESTLY(db)

            if (!db.djangoForest_subjectrf())
                viewModels.getSUBJECTRF(db)

            if (!db.djangoForest_listregion())
                viewModels.getLISTREGION(db, value)

            if (!db.djangoForest_sample())
                viewModels.getSAMPLE(db)

            if (!db.djangoForest_list())
                viewModels.getLIST(db)

        }

//костыльный синглтон для получения ответа от сервера переделать на ретурн или клбек
    object temp{
        var temp_object:text?=null
    }


    suspend fun load(viewModels: ViewModels, db: DBCountWood, context: AppCompatActivity){


        var listregion=db.getLISTREGION()
        viewModels.putLISTREGION(LISTREGION_REQUEST(listregion))
        delay(1000)
        if (temp.temp_object!=null){
             temp.temp_object!!.text.ids.forEach { temp->
                temp.obj.last
                listregion.forEach{
                    if (it.id== temp.obj.last){
                        it.id=temp.obj.new
                    }
                }
            }
        }

        listregion.forEach {
            it.id
        }

        delay(1000)
        var sample = mutableListOf<SAMPLE_DATA>()
        var list:ArrayList<LIST_DATA> = arrayListOf()
//
//
//
        listregion.forEach{
            viewModels.putSAMPLE(SAMPLE_REQEST( db.getSAMPLEbyID_Listregion(it.id)))
            db.getSAMPLEbyID_Listregion(it.id).forEach{
                sample.add(it)
            }
        }

       delay(1000)
       sample.forEach{
           sendphoto(db,it.id,context,viewModels)
           viewModels.putLIST(LIST_REQEST(db.getLIST(it.id)))
       }


        val filePath = "/data/data/com.example.rosles/databases/userdb.db"
        val file = File(filePath)
        if (file.exists()) {
            file.delete()
        }

    }
    fun delete_values(db: DBCountWood,viewModels: ViewModels){
        val listregion= db.get_delete_listregion()
        val sample= db.get_delete_sample()

        listregion.forEach{
            viewModels.delete_listregion(it)
        }
        sample.forEach{
            viewModels.delete_sample(it)
        }

    }

    fun sendphoto(db: DBCountWood,id:Int,context: Context,viewModels: ViewModels){
        db.getphotoall(id).forEach{
            val wrapper = ContextWrapper(context)
            var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
            file = File(file,"${UUID.randomUUID()}.jpg")
            val stream: OutputStream = FileOutputStream(file)
            it.photo!!.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
            val photoFile = file
            val photo = MultipartBody.Part.createFormData(
                "photo",
                photoFile.name,
                photoFile.asRequestBody("image/*".toMediaType())
            )
            viewModels.upload(UpdateRequest(photo,it.id_sample,it.latitude.toDouble(),it.longitude.toDouble(),it.date))
        }
    }
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




