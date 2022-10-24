package com.example.roslesdef

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.roslesdef.Adapters.UdelAdapter
import com.example.roslesdef.Adapters.WoodAdapter
import com.example.roslesdef.Models.ItemWood
import com.example.roslesdef.databinding.MainScreenBinding
import com.example.roslesdef.databinding.WoodBinding
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainScreenBinding

        fun mainfun()= runBlocking{
        val loggininteractor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val gson = Gson()
        val client = OkHttpClient.Builder().addInterceptor(loggininteractor).build()
        val moshi = Moshi.Builder().build()
        val moshicoverter = MoshiConverterFactory.create(moshi)
        val retrofit= Retrofit.Builder().baseUrl("http://127.0.0.1:8000/")
            .client(client)
            .addConverterFactory(moshicoverter)
            .build()
        val api = retrofit.create(API::class.java)

//        val request = testClass(
//            to0_2 = "10",
//            from0_21To0_5 ="10",
//            from0_6To1_0 ="10",
//            from1_1to1_5 ="10",
//            from1_5 ="10",
//            max_height ="10",
//            id_breed_id ="1",
//            id_sample_id ="1",
//            id_type_of_reproduction_id ="1"
//        )
//
        val contentType = "application/json; charset=utf-8".toMediaType()

        val requestBody=testClass2(name_breed = "jjjjjj")
        val requestString=gson.toJson(requestBody)
        val okHttpRequestBody = requestString.toRequestBody(contentType)


        val request = Request.Builder()
            .post(okHttpRequestBody)
            .url("http://127.0.0.1:8000/breed")
            .build()

            val call =client.newCall(request)
            val responce=call.execute()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = MainScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val a = listOf(ItemWood("Проба1"),
            ItemWood("Проба2"),
            ItemWood("Проба3")
        )

        var adapter = UdelAdapter(a)

        binding.UdelRecycler.adapter=adapter



        binding.button2.setOnClickListener(){
            val intent = Intent(this, Wood::class.java)
            startActivity(intent)
        }
        binding.button3.setOnClickListener(){
           // this.mainfun()
            Toast.makeText(this,"Запрос отправлен", Toast.LENGTH_SHORT).show()
        }



    }

}