package com.example.rosles.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import com.example.rosles.DBCountWood
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.databinding.ActivityMainBinding
import com.example.roslesdef.Adapters.UdelAdapter
import com.example.roslesdef.Models.ItemWood
import okhttp3.internal.wait

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<ViewModels>()

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        binding.proba.setOnClickListener(){
            val intent = Intent(this, Wood::class.java)
            startActivity(intent)
        }
        binding.sync.setOnClickListener(){
            viewModel.reproduction(this)
        }
        binding.Settings.setOnClickListener(){
            RecyclerviewInit()
        }



    }

    fun RecyclerviewInit(){
        val db = DBCountWood(this,null)
        var value=db.read("REPRODUCTIONNAME")

        val a = listOf(
            ItemWood(value.get(0)),
            ItemWood(value.get(1)),
            ItemWood("Проба3")
        )

        var adapter = UdelAdapter(a)

        binding.UdelRecycler.adapter=adapter

    }


}