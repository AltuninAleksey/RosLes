package com.example.rosles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.rosles.Network.ViewModels

class perehet : AppCompatActivity() {

    val viewModel by viewModels<ViewModels>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perehet)

        var a= viewModel.requestSubjectRF("RRRRRRR")
        Toast.makeText(this,a.toString(),Toast.LENGTH_SHORT).show()
        var cc= 15;
    }
}