package com.example.rosles

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.rosles.Network.ViewModels

import com.example.rosles.databinding.TestLayoutBinding
import kotlinx.coroutines.launch
import java.io.File

class TestActivity : BaseActivity("Добавление") {

    private lateinit var binding: TestLayoutBinding
    private val db = DBCountWood(this, null)


    val viewModel by viewModels<ViewModels>() // Q

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TestLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivity()
    }

    fun initActivity(){

        binding.test1.setOnClickListener {

            var database = DBCountWood(this, null)
            database.writableDatabase
            lifecycleScope.launch{


                sync().load(viewModel,db,this@TestActivity)
//                delay(2000)
//                sync().main1(viewModel,database, context,value)
                Toast.makeText(this@TestActivity, "Успех", Toast.LENGTH_SHORT).show()


            }
        }
        binding.test2.setOnClickListener {

        var filePath = "/data/data/com.example.rosles/databases/userdb.db"
        var file = File(filePath)
        if (file.exists()) {

            file.delete()
        }
        }
        binding.test3.setOnClickListener {


        }
        binding.test4.setOnClickListener {


        }


    }


}