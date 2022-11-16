package com.example.rosles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.rosles.Network.ViewModels

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<ViewModels>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.requestSubjectRF("sss")
        val intent = Intent(this, perehet::class.java)
        startActivity(intent)
    }
}