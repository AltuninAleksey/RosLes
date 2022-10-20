package com.example.roslesdef

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.roslesdef.databinding.MainScreenBinding
import com.example.roslesdef.databinding.WoodBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = MainScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.main_screen)
        var button2:Button=findViewById(R.id.button2)
        button2.setOnClickListener(){
            val intent = Intent(this, Wood::class.java)
            startActivity(intent)
        }

    }

}