package com.example.roslesdef

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.example.roslesdef.databinding.WoodBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: WoodBinding
    private var bufview: View? =null
    private var kostl:Any?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = WoodBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
    fun onClickCell(view: View){
        if (bufview==null){
            bufview=view
            bufview?.setBackgroundResource(R.drawable.rounded_active)
            kostl=view.id
        }
        if (bufview!=null){
            bufview?.setBackgroundResource(R.drawable.rounded)
            bufview=view
            bufview?.setBackgroundResource(R.drawable.rounded_active)
            kostl=view.id
        }
        binding.buttonPlus.setOnClickListener(){

            var buffertext:TextView=findViewById(view.id)
            var value=buffertext.text.toString().toInt()
            value=value+1
            buffertext.text=value.toString()

        }
        binding.buttonMinus.setOnClickListener(){

            var buffertext:TextView=findViewById(view.id)
            var value=buffertext.text.toString().toInt()
            value=value-1
            if(value<=0)
                value=0
            buffertext.text=value.toString()

        }
    }
    fun onClickSumm(){

    }

}