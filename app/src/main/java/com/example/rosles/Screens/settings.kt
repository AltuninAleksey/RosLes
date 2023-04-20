package com.example.rosles.Screens

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rosles.Network.ViewModels
import com.example.rosles.databinding.UserSettingBinding

class settings:AppCompatActivity() {

    val viewModel by viewModels<ViewModels>()

    private lateinit var binding: UserSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = UserSettingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.back.setOnClickListener{
            startActivity(Intent(this, profile::class.java))
        }
    }

}