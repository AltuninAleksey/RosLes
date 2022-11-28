package com.example.rosles.Screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rosles.DBCountWood
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.RequestClass.PerechetRequest
import com.example.rosles.ResponceClass.ClassWood
import com.example.rosles.databinding.WoodBinding
import com.example.roslesdef.Adapters.WoodAdapter
import com.example.roslesdef.Models.ItemWood


class Wood : AppCompatActivity() {


    private lateinit var binding: WoodBinding
    private var bufview: View? =null
    private var kostl:Any?=null


    val viewModel by viewModels<ViewModels>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = WoodBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.main.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }




        val a = listOf(
            ItemWood("Береза"),
            ItemWood("Сосна"),
            ItemWood("ДуБаДуб")
        )

        var adapter = WoodAdapter(a)

        binding.WoodRecycler.adapter=adapter


        binding.save.setOnClickListener(){
            WriteBD()
        }


        binding.buttonPoint.setOnClickListener(){
            val db = DBCountWood(this, null)
            var wood: ClassWood =db.readByID("1")
            var perechet= PerechetRequest(wood.value02,wood.value05,wood.value06,wood.value11,wood.value15
                ,"1",1,1,1)

            viewModel.perechet(perechet,this)
            //checkBD()

        }
    }

    fun WriteBD(){
        val db = DBCountWood(this,null)
        with(binding){
            db.addName("poroda","PP1","estes",estes02.text.toString(),estes05.text.toString(),
                estes06.text.toString(),estes11.text.toString(),estes15.text.toString())
        }
        Toast.makeText(this,"Данные сохранены",Toast.LENGTH_SHORT).show()
    }


    //чтение из БД
    fun checkBD(){
        val db = DBCountWood(this, null)
        var wood: ClassWood =db.readByID("1")
        Toast.makeText(this,wood.proba.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(this,wood.poroda.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(this,wood.Reproduction.toString(), Toast.LENGTH_SHORT).show()
    }

    //функция для выделения нужной плитки
    fun onClickCell(view: View){
        if (bufview==null){
            bufview=view
            bufview?.setBackgroundResource(R.drawable.rounded_active)
            kostl=view.id
        }
        if (bufview!=null){
            bufview?.setBackgroundResource(R.drawable.rounded)
            bufview=view
            bufview?.setBackgroundResource(R.drawable.rounded_active)
            kostl=view.id
        }
        binding.buttonPlus.setOnClickListener(){
            var buffertext: TextView =findViewById(view.id)
            var value=buffertext.text.toString().toInt()
            value=value+1
            buffertext.text=value.toString()
        }
        binding.buttonMinus.setOnClickListener(){

            var buffertext: TextView =findViewById(view.id)
            var value=buffertext.text.toString().toInt()
            value=value-1
            if(value<=0)
                value=0
            buffertext.text=value.toString()

        }
    }

}