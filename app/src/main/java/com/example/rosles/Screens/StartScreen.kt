package com.example.rosles.Screens

import android.content.Intent
import android.os.Bundle
import com.example.rosles.BaseActivity
import com.example.rosles.databinding.ProfileBinding
import com.example.rosles.databinding.ScreenPhotoBinding
import com.example.rosles.databinding.StartScreenBinding

class StartScreen: BaseActivity() {

    private lateinit var binding: StartScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StartScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.les.setOnClickListener {
            startActivity(Intent(this, MolodnyakList::class.java))
        }
        binding.molodnyak.setOnClickListener {
            startActivity(Intent(this, Dashboard::class.java))
        }
    }
}