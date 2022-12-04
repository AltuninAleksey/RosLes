package com.example.rosles.Screens

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rosles.Adapters.BaseInterface
import com.example.rosles.DBCountWood
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.RequestClass.PerechetRequest
import com.example.rosles.ResponceClass.ClassWood
import com.example.rosles.databinding.DialogViewBinding
import com.example.rosles.databinding.WoodBinding
import com.example.roslesdef.Adapters.WoodAdapter
import com.example.roslesdef.Models.ItemWood




class Wood : AppCompatActivity(){

    private lateinit var binding: WoodBinding
    private var bufview: View? =null
    private var kostl:Any?=null
    private var vidWood:String=""
    private  val db = DBCountWood(this,null)
    val viewModel by viewModels<ViewModels>()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = WoodBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var dialog:Dialog =  Dialog(this)
        dialog.setContentView(R.layout.dialog_view)
        var text:EditText=dialog.findViewById(R.id.NameWoodEditText)
        var buttonAD:Button=dialog.findViewById(R.id.buttonAddWood)
        var buttonclose:Button=dialog.findViewById(R.id.buttonCloseDialog)
        buttonclose.setOnClickListener{
            dialog.dismiss()
        }

        val countries = arrayOf("1", "2", "3", "4", "5")
        var arrayAdapter=ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter=arrayAdapter

        binding.imageViewAdd.setOnClickListener{
            dialog.show();
        }
        val name=intent.getStringExtra("udel")
        binding.nameudel.text=name.toString()

        binding.main.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


        var a = mutableListOf(
            ItemWood("Береза"),
            ItemWood("Сосна"),
            ItemWood("Дуб"),
            ItemWood("Елка"),
            ItemWood("Осина")
        )
        buttonAD.setOnClickListener{
            if(text.text.isNotEmpty()){
                a.add(ItemWood(text.text.toString()))
                a.forEach {
                    createTable(it.name)
                }
            }
            dialog.dismiss()
        }


        var adapter = WoodAdapter(a,object :BaseInterface{
            override fun onClick(itemView: Any) {


                UpdateTable(vidWood)
                nullValue()
                readbyporoda(itemView.toString())
                vidWood=itemView.toString()

            }
        })
        binding.WoodRecycler.adapter=adapter
        binding.save.setOnClickListener(){
            WriteBD()
        }


        // REST API
        binding.buttonPoint.setOnClickListener(){
            val db = DBCountWood(this, null)
            var wood: ClassWood =db.readByID("1")
            var perechet= PerechetRequest(wood.value02,wood.value05,wood.value06,wood.value11,wood.value15
                ,"1",1,1,1)

            viewModel.perechet(perechet,this)
            //checkBD()

        }
    }


    //ЗАпись в БД
    fun WriteBD(){
        with(binding){
            db.addName("poroda",proba.text.toString(),"estes",estes02.text.toString(),estes05.text.toString(),
                estes06.text.toString(),estes11.text.toString(),estes15.text.toString())
        }
        Toast.makeText(this,"Данные сохранены",Toast.LENGTH_SHORT).show()
    }


    //чтение из БД
    fun checkBD(){
        val db = DBCountWood(this, null)
        var wood: ClassWood =db.readByID("1")
        Toast.makeText(this,wood.proba.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(this,wood.poroda.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(this,wood.Reproduction.toString(), Toast.LENGTH_SHORT).show()
    }

    //функция для выделения нужной плитки
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
            var buffertext: TextView =findViewById(view.id)
            var value=buffertext.text.toString().toInt()
            var test=binding.spinner.selectedItem.toString()
            value=value+test.toInt()
            buffertext.text=value.toString()
        }
        binding.buttonMinus.setOnClickListener(){

            var buffertext: TextView =findViewById(view.id)
            var value=buffertext.text.toString().toInt()
            var test=binding.spinner.selectedItem.toString()
            value=value-test.toInt()
            if(value<=0)
                value=0
            buffertext.text=value.toString()
        }
    }

    private fun createTable(name:String) {

            with(binding){
            vidWood=name

            db.addName(vidWood,proba.text.toString(),"iskus",iskus02.text.toString(),iskus05.text.toString(),
                iskus06.text.toString(),iskus11.text.toString(),iskus15.text.toString())
            db.addName(vidWood,proba.text.toString(),"estes",estes02.text.toString(),estes05.text.toString(),
                estes06.text.toString(),estes11.text.toString(),estes15.text.toString())
            db.addName(vidWood,proba.text.toString(),"estestvennoe",estestvennoe02.text.toString(),estestvennoe05.text.toString(),
                estestvennoe06.text.toString(),estestvennoe11.text.toString(),estestvennoe15.text.toString())
            db.addName(vidWood,proba.text.toString(),"podlesok",podlesok02.text.toString(),podlesok05.text.toString(),
                podlesok06.text.toString(),podlesok11.text.toString(),podlesok15.text.toString())

        }
        //fromTable()
    }
    private fun UpdateTable(name:String) {
        with(binding) {
            db.updateName(vidWood,proba.text.toString(),"iskus",iskus02.text.toString(),iskus05.text.toString(),
                iskus06.text.toString(),iskus11.text.toString(),iskus15.text.toString())
            db.updateName(vidWood,proba.text.toString(),"estes",estes02.text.toString(),estes05.text.toString(),
                estes06.text.toString(),estes11.text.toString(),estes15.text.toString())
            db.updateName(vidWood,proba.text.toString(),"estestvennoe",estestvennoe02.text.toString(),estestvennoe05.text.toString(),
                estestvennoe06.text.toString(),estestvennoe11.text.toString(),estestvennoe15.text.toString())
            db.updateName(vidWood,proba.text.toString(),"podlesok",podlesok02.text.toString(),podlesok05.text.toString(),
                podlesok06.text.toString(),podlesok11.text.toString(),podlesok15.text.toString())
            vidWood = name
        }
    }




    fun readbyporoda(poroda:String){
        var wood: ClassWood =db.readbyporoda(poroda,"iskus")
        setvalueiskus(wood.value02,wood.value05,wood.value06,wood.value11,wood.value15)
        wood =db.readbyporoda(poroda,"estes")
        setvalueestes(wood.value02,wood.value05,wood.value06,wood.value11,wood.value15)
        wood =db.readbyporoda(poroda,"estestvennoe")
        setvalueestestvennoe(wood.value02,wood.value05,wood.value06,wood.value11,wood.value15)
        wood =db.readbyporoda(poroda,"podlesok")
        setvaluepodlesok(wood.value02,wood.value05,wood.value06,wood.value11,wood.value15)

    }
    fun nullValue(){
        with(binding){
            iskus02.text="0"
            iskus05.text="0"
            iskus06.text="0"
            iskus11.text="0"
            iskus15.text="0"
            estes02.text="0"
            estes05.text="0"
            estes06.text="0"
            estes11.text="0"
            estes15.text="0"
            estestvennoe02.text="0"
            estestvennoe05.text="0"
            estestvennoe06.text="0"
            estestvennoe11.text="0"
            estestvennoe15.text="0"
            podlesok02.text="0"
            podlesok05.text="0"
            podlesok06.text="0"
            podlesok11.text="0"
            podlesok15.text="0"
        }
    }

    fun setvalueiskus(v02: String, v05:String, v06:String, v11:String, v015:String){
        with(binding){
            iskus02.text=v02
            iskus05.text=v05
            iskus06.text=v06
            iskus11.text=v11
            iskus15.text=v015
        }
    }
    fun setvalueestes(v02: String, v05:String, v06:String, v11:String, v015:String){
        with(binding){
            estes02.text=v02
            estes05.text=v05
            estes06.text=v06
            estes11.text=v11
            estes15.text=v015
        }
    }
    fun setvalueestestvennoe(v02: String, v05:String, v06:String, v11:String, v015:String){
        with(binding){
            estestvennoe02.text=v02
            estestvennoe05.text=v05
            estestvennoe06.text=v06
            estestvennoe11.text=v11
            estestvennoe15.text=v015
        }
    }
    fun setvaluepodlesok(v02: String, v05:String, v06:String, v11:String, v015:String){
        with(binding){
            podlesok02.text=v02
            podlesok05.text=v05
            podlesok06.text=v06
            podlesok11.text=v11
            podlesok15.text=v015
        }
    }

}