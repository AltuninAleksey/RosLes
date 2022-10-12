package com.example.roslesdef

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.example.roslesdef.databinding.WoodBinding

class Wood : Activity() {
    private lateinit var binding: WoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = WoodBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }

   fun onClickCell(view: View){
       Toast.makeText(this, "Зачем вы нажали?", Toast.LENGTH_SHORT).show();
   }

}