package com.example.rosles.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import com.example.rosles.Adapters.BaseInterface
import com.example.rosles.DBCountWood
import com.example.rosles.Network.ViewModels
import com.example.rosles.databinding.ActivityMainBinding
import com.example.rosles.databinding.ItemUdelBinding
import com.example.roslesdef.Adapters.UdelAdapter
import com.example.roslesdef.Models.ItemWood


class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<ViewModels>()

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        RecyclerviewInit()
        viewModel.reproduction(this)
        binding.toolbar.main.setBackgroundColor(0xFF03DAC5.toInt())
        binding.toolbar.profile.setOnClickListener{
            startActivity(Intent(this, profile::class.java))
        }
        binding.toolbar.changeprofile.setOnClickListener{
            var sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE);
            var ed = sPref.edit().clear().commit()
            startActivity(Intent(this, Authorization::class.java))
        }

        binding.sync.setOnClickListener(){

        }



    }
    fun start(itemView: String) {
        val intent = Intent(this, Wood::class.java)
        intent.putExtra("udel",itemView)
        startActivity(intent)
    }

    fun RecyclerviewInit(){
        val db = DBCountWood(this,null)
        var value=db.read("REPRODUCTIONNAME")

        val a = listOf(
            ItemWood("Перечетная ведомость 1"),
            ItemWood("Перечетная ведомость 2 "),
            ItemWood("Перечетная ведомость 3 ")
        )

        var adapter = UdelAdapter(a,object :BaseInterface{

            override fun onClick(itemView: Any) {
                start(itemView as String)
            }
        })



        binding.UdelRecycler.adapter=adapter

    }


}