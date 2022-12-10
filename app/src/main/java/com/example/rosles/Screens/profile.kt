package com.example.rosles.Screens

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rosles.Network.ViewModels
import com.example.rosles.databinding.ProfileBinding

class profile:AppCompatActivity() {

    val viewModel by viewModels<ViewModels>()

    private lateinit var binding: ProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel.reproduction(this)
        binding.toolbar.profile.setBackgroundColor(0xFF03DAC5.toInt())
        binding.toolbar.main.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.toolbar.changeprofile.setOnClickListener{
            var sPref =getPreferences(MODE_PRIVATE)
            sPref.edit().clear()
            startActivity(Intent(this, Authorization::class.java))
        }
        binding.toolbar.changeprofile.setOnClickListener{
            var sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE);
            var ed = sPref.edit().clear().commit()
            startActivity(Intent(this, Authorization::class.java))
        }



        binding.settings.setOnClickListener(){


            //viewModel.guide.value.toString()

            viewModel.guide.observe(this){
                Toast.makeText(this,it.get(0).name_reproduction.toString(),Toast.LENGTH_SHORT).show()

            }
        }




    }
}