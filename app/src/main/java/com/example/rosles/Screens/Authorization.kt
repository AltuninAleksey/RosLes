package com.example.rosles.Screens

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.rosles.databinding.AuthorizationActivityBinding


class Authorization: AppCompatActivity() {
    private lateinit var binding: AuthorizationActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = AuthorizationActivityBinding.inflate(layoutInflater)
        val view = binding.root
    }
}