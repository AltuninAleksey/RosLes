package com.example.roslesdef

import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.roslesdef.Adapters.WoodAdapter
import com.example.roslesdef.Models.ItemWood
import com.example.roslesdef.databinding.WoodBinding

class Wood : AppCompatActivity() {


    private lateinit var binding: WoodBinding
    private var bufview: View? =null
    private var kostl:Any?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = WoodBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)




        val a = listOf(ItemWood("Береза"),
            ItemWood("Сосна"),
            ItemWood("ДуБаДуб")
        )

        var adapter = WoodAdapter(a)

        binding.WoodRecycler.adapter=adapter




        binding.save.setOnClickListener(){
            WriteBD()
        }
        binding.buttosan5sa2.setOnClickListener(){
            checkBD()
        }
    }

    fun WriteBD(){
        val db =DBCountWood(this,null)
        with(binding){
            db.addName("poroda","PP1","estes",estes02.text.toString(),estes05.text.toString(),
                estes06.text.toString(),estes11.text.toString(),estes15.text.toString())
        }
    }

    fun checkBD(){
        val db = DBCountWood(this, null)
        var datalist = db.read("value06")
        var wood:ClassWood=db.readByID("1")
        Toast.makeText(this,wood.proba.toString(),Toast.LENGTH_SHORT).show()
        Toast.makeText(this,wood.poroda.toString(),Toast.LENGTH_SHORT).show()
        Toast.makeText(this,wood.view.toString(),Toast.LENGTH_SHORT).show()
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
            binding.spinner.onItemSelectedListener

            //Toast.makeText(applicationContext,binding.spinner.onItemSelectedListener.toString() , Toast.LENGTH_SHORT).show()


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


//    fun onClickCell(view: View){
//       Toast.makeText(this, "Зачем вы нажали?", Toast.LENGTH_SHORT).show();
//   }

}