package com.example.rosles.Screens

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.rosles.Adapters.BaseInterface
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.R
import com.example.rosles.ResponceClass.PerechetWood
import com.example.rosles.ResponceClass.PodlesokWood
import com.example.rosles.ResponceClass.ProbaWoodSimple
import com.example.rosles.databinding.WoodBinding
import com.example.rosles.setSizeRelativeCurrentWindow
import com.example.roslesdef.Adapters.WoodAdapter
import com.example.roslesdef.Models.ItemWood
import com.example.roslesdef.Models.SpinerItem
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.collections.HashMap

class Wood : BaseActivity("Пробная площадь") {

    private lateinit var binding: WoodBinding
    private var bufview: View? = null
    private var vidWood: String = ""
    private var vidWoodpodles: String = ""
    var hashbufWood=HashMap<String, ProbaWoodSimple?>()
    var podlesokhash=HashMap<String, PodlesokWood?>()
    var Get_Id_breed_Class:AddPorod?= AddPorod()

    var CountMainPorod=""
    var activeCountPorod=""

    var flaglesActive=false
    var flagpodleslesActive=false

    private val db = DBCountWood(this, null)

    @SuppressLint("Range", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Пробная площадь"
        val id_sample =  intent.getIntExtra("udel",0)
        val id_vdomost = intent.getStringExtra("proba").toString().toInt()


        invisibleplus()


        val vedom = db.getVedombyID(id_vdomost)

        binding.proba.text = intent.getStringExtra("numberOfPoroda")
        binding.lesnnich.text = vedom.nameForestly
        binding.district.text = vedom.nameDistrictForestly
        binding.quater.text =   vedom.quarterName
        binding.vudel.text =    vedom.soilLot

        binding.needvaluewood.text=intent.getStringExtra("valuewood")
        // хранение перечета В hashmap
        val vidvos = mutableListOf(
            ItemWood("iskus", 1),
            ItemWood("estes", 2),
            ItemWood("porosl", 3),
        )
        //Преключение типа перечета
        binding.lesoblast.setOnClickListener {
            binding.lesoblast.setBackgroundResource(R.color.activecolumn)
            binding.podlesoblast.setBackgroundResource(R.color.noactivecolumn)
            binding.tblLayoutpodlesok.isVisible = false
            binding.tblLayout.visibility = View.VISIBLE
            binding.WoodRecycler.isVisible = true
            binding.WoodRecyclerpodles.isVisible = false
            invisibleplus()
        }
        binding.podlesoblast.setOnClickListener {
            binding.podlesoblast.setBackgroundResource(R.color.activecolumn)
            binding.lesoblast.setBackgroundResource(R.color.noactivecolumn)
            binding.tblLayout.visibility = View.INVISIBLE
            binding.tblLayoutpodlesok.isVisible = true
            binding.WoodRecycler.isVisible = false
            binding.WoodRecyclerpodles.isVisible = true
            invisibleplus()
        }

        //Инициализация избранных пород
        //переписать ключ hashmap -><int,class>
        val sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE);
        val id_user = sPref.getString("id", "1")!!.toInt()
        val leslist = mutableListOf<ItemWood>()
        val podleslist = mutableListOf<ItemWood>()
        var favoriteLesList = db.getFavoriteLes(id_user)

        for (i in 0..favoriteLesList.size-1) {
            leslist.add(favoriteLesList[i].toItemWood())
        }

        val favoritePodlesList = db.getFavoritePodles(id_user)

        for (i in 0..favoritePodlesList.size-1) {
            podleslist.add(favoritePodlesList[i].toItemWood())
        }

        //инициализация меню
        binding.include.user.setOnClickListener {
            startActivity(Intent(this, gps_activity::class.java))
        }
        binding.include.open.setOnClickListener {
            val intent=Intent(this, SelectPhoto::class.java)
            intent.putExtra("id_sample",id_sample)
            intent.putExtra("id_vdomost",id_vdomost)
            startActivity(intent)
        }

        // Диалог добавления породы

        binding.include.addporod.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_add_porod)
            dialog.setSizeRelativeCurrentWindow(0.85, 0.6)

            val les: Spinner = dialog.findViewById(R.id.les)
            val poldes = dialog.findViewById<Spinner>(R.id.podles)
            dialog.show()
            val button = dialog.findViewById<View>(R.id.button_auto)

            val countries = db.getallporodArray()
            val countries1 = db.getallpodlesArray()

            val arrayAdapter = ArrayAdapter(
                applicationContext,
                android.R.layout.simple_spinner_dropdown_item,
                countries
            )
            val arrayAdapter1 =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, countries1)
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            les.adapter = arrayAdapter
            poldes.adapter = arrayAdapter1

            val hash = db.gethashFavoriteLes(id_user)
            val hashpodles = db.gethashFavoritepodLes(id_user)

            var flag = true
            var flagpodles = true

            button.setOnClickListener {

                val valueles=les.selectedItem as SpinerItem
                val valuepodles=poldes.selectedItem as SpinerItem
                if (valueles.id==0){
                    flag = false
                }
                hash.forEach { t, u ->
                    if (u == valueles.name) {
                        flag = false
                        Toast.makeText(this, "Такая порода уже существует", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                if (flag) {
                    leslist.add(ItemWood(valueles.name))
                    db.addlesporod(id_user, valueles.id)

                    val buf = ProbaWoodSimple(
                        db.getperechetID(id_sample,1,valueles.id),
                        db.getperechetID(id_sample,2,valueles.id),
                        db.getperechetID(id_sample,3,valueles.id))
                    hashbufWood.put(valueles.name,buf)
                }
                if (valuepodles.id==0){
                    flagpodles = false
                }
                hashpodles.forEach { t, u ->
                    if (u == valuepodles.name) {
                        flagpodles = false
                        Toast.makeText(this, "Такая порода уже существует", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                if (flagpodles) {
                    podleslist.add(ItemWood(poldes.getSelectedItem().toString()))
                    db.addpodlesporod(id_user, valuepodles.id)
                    podlesokhash.put(valuepodles.name,PodlesokWood(idbreed_under = valuepodles.id, value = 0, id = 0))
                }

                initrecycler(leslist, podleslist)
                invisibleplus()
                dialog.dismiss()

            }
        }
        //сохранение их хэш таблицы в базу данных
        binding.include.save.setOnClickListener {
            writedata(vidWood)
            writedatapodles(vidWoodpodles)
            hashbufWood.forEach { t, u ->
                if(u?.iskus?.id_prob==null){
                    db.CreateLesPorodsV2(u?.iskus, id_sample)
                }else{
                    db.UpdateLesPorodsV2(u.iskus)
                }
                if(u?.estes?.id_prob==null){
                    db.CreateLesPorodsV2(u?.estes, id_sample)
                }else{
                    db.UpdateLesPorodsV2(u.estes)
                }
                if(u?.estestvenn?.id_prob==null){
                    db.CreateLesPorodsV2(u?.estestvenn, id_sample)
                }else{
                    db.UpdateLesPorodsV2(u.estestvenn)
                }
            }
            podlesokhash.forEach { t, u ->
                if (u?.id==0){
                    db.createpodles(u,id_sample)
                }
                else{
                    db.updatepodles(u)
                }
            }
            db.updatevalue(id_vdomost)
            db.Mark_Update_Sample(id_sample)
//            finish()
            Toast.makeText(this, "Данные записаны", Toast.LENGTH_SHORT)
                .show()
        }
        //инициализация хеш мап
        leslist.forEach {
            val hashMap = HashMap<String, PerechetWood>()
            val poroda = it.name
            val poroda_id=Get_Id_breed_Class?.getidbreed(it.name, db)!!

            vidvos.forEach {

                val value= db.getperechetID(id_sample,it.id,poroda_id)
                hashMap.put(it.name, value!!)
            }
            val buf = ProbaWoodSimple(
                db.getperechetID(id_sample,1,poroda_id),
                db.getperechetID(id_sample,2,poroda_id),
                db.getperechetID(id_sample,3,poroda_id))
                hashbufWood.put(poroda,buf)
        }
        podleslist.forEach {
            podlesokhash.put(it.name,db.getperechetIDpodles(id_sample,it.id))
        }
        initrecycler(leslist, podleslist)
        initasd()
    }

    //запись данных в хеш таблицу
    fun writedata(value_param: String) {
        val value = hashbufWood.get(value_param)
        with(binding) {
            value?.iskus?.o2         = iskus02.text.toString().toInt()
            value?.iskus?.o5         = iskus05.text.toString().toInt()
            value?.iskus?.o6         = iskus06.text.toString().toInt()
            value?.iskus?.o11        = iskus11.text.toString().toInt()
            value?.iskus?.o15        = iskus15.text.toString().toInt()
            value?.iskus?.type       = 1
            value?.iskus?.id_breed   = Get_Id_breed_Class?.getidbreed(value_param,db)!!
            value?.iskus?.maxHeight  = maksHeightIskus.text.toString().toFloatOrNull() ?: 0.0f
            value?.iskus?.AVGHEight  = AvgHeightIskus.text.toString().toFloatOrNull() ?: 0.0f
            value?.iskus?.AVGdiametr = AvgDiametrIskus.text.toString().toFloatOrNull() ?: 0.0f

            value?.estes?.o2         = estes02.text.toString().toInt()
            value?.estes?.o5         = estes05.text.toString().toInt()
            value?.estes?.o6         = estes06.text.toString().toInt()
            value?.estes?.o11        = estes11.text.toString().toInt()
            value?.estes?.o15        = estes15.text.toString().toInt()
            value?.estes?.type       = 2
            value?.estes?.id_breed   = Get_Id_breed_Class?.getidbreed(value_param,db)!!
            value?.estes?.maxHeight  = maksHeightestes.text.toString().toFloatOrNull() ?: 0.0f
            value?.estes?.AVGHEight  = AvgHeightestes.text.toString().toFloatOrNull() ?: 0.0f
            value?.estes?.AVGdiametr = AvgDiametrestes.text.toString().toFloatOrNull() ?: 0.0f

            value?.estestvenn?.o2         = estestvennoe02.text.toString().toInt()
            value?.estestvenn?.o5         = estestvennoe05.text.toString().toInt()
            value?.estestvenn?.o6         = estestvennoe06.text.toString().toInt()
            value?.estestvenn?.o11        = estestvennoe11.text.toString().toInt()
            value?.estestvenn?.o15        = estestvennoe15.text.toString().toInt()
            value?.estestvenn?.type       = 3
            value?.estestvenn?.id_breed   = Get_Id_breed_Class?.getidbreed(value_param,db)!!
            value?.estestvenn?.maxHeight  = maksHeightestestvennoe.text.toString().toFloatOrNull() ?: 0.0f
            value?.estestvenn?.AVGHEight  = AvgHeightestestvennoe.text.toString().toFloatOrNull() ?: 0.0f
            value?.estestvenn?.AVGdiametr = AvgDiametrestestvennoe.text.toString().toFloatOrNull() ?: 0.0f
        }
    }
    //запись данных в хеш таблицу подлеска
    fun writedatapodles(value_param: String){
        val value = podlesokhash.get(value_param)
        value?.value=binding.kolras.text.toString().toInt()
        value?.avgHeightpodles=binding.avgHeight.text.toString().toFloat()
    }
    //чтение данных из хеш таблицы
    fun initdata(value: String) {
        with(binding) {
            val buf = hashbufWood.get(value)?.iskus
            iskus02.text = buf?.o2.toString()
            iskus05.text = buf?.o5.toString()
            iskus06.text = buf?.o6.toString()
            iskus11.text = buf?.o11.toString()
            iskus15.text = buf?.o15.toString()
            maksHeightIskus.setText(buf?.maxHeight.toString())
            AvgHeightIskus.setText(buf?.AVGHEight.toString())
            AvgDiametrIskus.setText(buf?.AVGdiametr.toString())

            val buf1 = hashbufWood.get(value)?.estes
            estes02.text = buf1?.o2.toString()
            estes05.text = buf1?.o5.toString()
            estes06.text = buf1?.o6.toString()
            estes11.text = buf1?.o11.toString()
            estes15.text = buf1?.o15.toString()
            maksHeightestes.setText(buf1?.maxHeight.toString())
            AvgHeightestes.setText(buf1?.AVGHEight.toString())
            AvgDiametrestes.setText(buf1?.AVGdiametr.toString())

            val buf2 = hashbufWood.get(value)?.estestvenn
            estestvennoe02.text = buf2?.o2.toString()
            estestvennoe05.text = buf2?.o5.toString()
            estestvennoe06.text = buf2?.o6.toString()
            estestvennoe11.text = buf2?.o11.toString()
            estestvennoe15.text = buf2?.o15.toString()
            maksHeightestestvennoe.setText(buf2?.maxHeight.toString())
            AvgHeightestestvennoe.setText(buf2?.AVGHEight.toString())
            AvgDiametrestestvennoe.setText(buf2?.AVGdiametr.toString())
        }
        initasd()
    }

    fun initdatapodles(value: String){
        val buf = podlesokhash.get(value)
        binding.kolras.text=buf?.value.toString()
        binding.avgHeight.setText(buf?.avgHeightpodles.toString())
    }

    fun initrecycler(a: MutableList<ItemWood>, podles: MutableList<ItemWood>) {
        val adapter = WoodAdapter(a, object : BaseInterface {

            override fun onClick(itemView: Any) {
                activeCountPorod=itemView.toString()
                writedata(vidWood)
                vidWood = itemView.toString()
                initdata(vidWood)
                visibleplus()
            }
            override fun onClickButton(itemView: Any) {
                CountMainPorod=itemView.toString()
                GetCountWood(itemView.toString())
                //binding.valuewood.text=binding.asd.text
            }

        })
        binding.WoodRecycler.adapter = adapter

        val adapterpodles = WoodAdapter(podles, object : BaseInterface {
            override fun onClick(itemView: Any) {
                //UpdateTable(vidWood)
                writedatapodles(vidWoodpodles)
                vidWoodpodles = itemView.toString()
                initdatapodles(vidWoodpodles)
                visibleplus()
            }
            override fun onClickButton(itemView: Any) {}
        })
        binding.WoodRecyclerpodles.adapter = adapterpodles
    }

    fun onClickCell(view: View) {
        if (bufview == null) {
            bufview = view
            bufview?.setBackgroundResource(R.drawable.rounded_active)
        }
        if (bufview != null) {
            bufview?.setBackgroundResource(R.drawable.rounded)
            bufview = view
            bufview?.setBackgroundResource(R.drawable.rounded_active)
        }

        var buffertext: TextView = findViewById(view.id)
        var value = buffertext.text.toString().toInt()

        binding.buttonPlus.setOnClickListener() {
            buffertext = findViewById(view.id)
            value = buffertext.text.toString().toInt()
            value++
            buffertext.text = value.toString()
            initasd()
            GetCountWood(CountMainPorod)
        }
        binding.buttonMinus.setOnClickListener() {

            buffertext = findViewById(view.id)
            value = buffertext.text.toString().toInt()
            value--
            if (value <= 0)
                value = 0
            buffertext.text = value.toString()
            initasd()
            GetCountWood(CountMainPorod)
        }


        val textChangedListenerIskus = object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val bufAvg: Float = binding.maksHeightIskus.text.toString().toFloatOrNull()?.let {
                    avgHeight(binding.iskus02.text.toString().toFloat(),
                        binding.iskus05.text.toString().toFloat(),
                        binding.iskus06.text.toString().toFloat(),
                        binding.iskus11.text.toString().toFloat(),
                        binding.iskus15.text.toString().toFloat(),
                        it
                    )
                } ?: 0.0f

                binding.AvgHeightIskus.text = if (bufAvg.isNaN()) "0.0" else bufAvg.toString()
            }
        }
        val textChangedListenerEstes = object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val bufAvg: Float = binding.maksHeightestes.text.toString().toFloatOrNull()?.let {
                    avgHeight(binding.iskus02.text.toString().toFloat(),
                        binding.estes05.text.toString().toFloat(),
                        binding.estes06.text.toString().toFloat(),
                        binding.estes11.text.toString().toFloat(),
                        binding.estes15.text.toString().toFloat(),
                        it
                    )
                } ?: 0.0f

                binding.AvgHeightestes.text = if (bufAvg.isNaN()) "0.0" else bufAvg.toString()
            }
        }
        val textChangedListenerEstestvennoe = object: TextWatcher{
            override fun afterTextChanged(p0: Editable?){}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val bufAvg: Float = binding.maksHeightestestvennoe.text.toString().toFloatOrNull()?.let {
                    avgHeight(binding.iskus02.text.toString().toFloat(),
                        binding.estestvennoe05.text.toString().toFloat(),
                        binding.estestvennoe06.text.toString().toFloat(),
                        binding.estestvennoe11.text.toString().toFloat(),
                        binding.estestvennoe15.text.toString().toFloat(),
                        it
                    )
                } ?: 0.0f

                binding.AvgHeightestestvennoe.text = if (bufAvg.isNaN()) "0.0" else bufAvg.toString()
            }
        }

        binding.iskus02.addTextChangedListener(textChangedListenerIskus)
        binding.iskus05.addTextChangedListener(textChangedListenerIskus)
        binding.iskus06.addTextChangedListener(textChangedListenerIskus)
        binding.iskus11.addTextChangedListener(textChangedListenerIskus)
        binding.iskus15.addTextChangedListener(textChangedListenerIskus)
        binding.maksHeightIskus.addTextChangedListener(textChangedListenerIskus)

        binding.estes02.addTextChangedListener(textChangedListenerEstes)
        binding.estes05.addTextChangedListener(textChangedListenerEstes)
        binding.estes06.addTextChangedListener(textChangedListenerEstes)
        binding.estes11.addTextChangedListener(textChangedListenerEstes)
        binding.estes15.addTextChangedListener(textChangedListenerEstes)
        binding.maksHeightestes.addTextChangedListener(textChangedListenerEstes)

        binding.estestvennoe02.addTextChangedListener(textChangedListenerEstestvennoe)
        binding.estestvennoe05.addTextChangedListener(textChangedListenerEstestvennoe)
        binding.estestvennoe06.addTextChangedListener(textChangedListenerEstestvennoe)
        binding.estestvennoe11.addTextChangedListener(textChangedListenerEstestvennoe)
        binding.estestvennoe15.addTextChangedListener(textChangedListenerEstestvennoe)
        binding.maksHeightestestvennoe.addTextChangedListener(textChangedListenerEstestvennoe)


    }

     fun avgHeight(value1:Float,value2:Float,value3:Float,value4:Float,value5:Float,valuemax:Float):Float{
        val sum=value1+value2+value3+value4+value5

         val result=((value1*0.1+value2*0.35+value3*0.8+value4*1.3+((valuemax+1.51)/2*value5))/sum).toFloat()

        return  String.format("%.2f", result).replace(',', '.').toFloat()

    }
    fun GetCountWood(value: String){
        if (activeCountPorod==value){
            writedata(value)
        }
        val buf = hashbufWood.get(value)
        if (buf == null)
            return
        val temp=
        buf.iskus!!.o2!!.toInt()+
        buf.iskus!!.o5!!.toInt()+
        buf.iskus!!.o6!!.toInt()+
        buf.iskus!!.o11!!.toInt()+
        buf.iskus!!.o15!!.toInt()+
        buf.estes!!.o2!!.toInt()+
        buf.estes!!.o5!!.toInt()+
        buf.estes!!.o6!!.toInt()+
        buf.estes!!.o11!!.toInt()+
        buf.estes!!.o15!!.toInt()+
        buf.estestvenn!!.o2!!.toInt()+
        buf.estestvenn!!.o5!!.toInt()+
        buf.estestvenn!!.o6!!.toInt()+
        buf.estestvenn!!.o11!!.toInt()+
        buf.estestvenn!!.o15!!.toInt()

        binding.valuewood.text=temp.toString()

    }
    fun initasd(){
        with(binding){
            val value:Int?=
                          iskus02.text.toString().toInt()+
                          iskus05.text.toString().toInt()+
                          iskus06.text.toString().toInt()+
                          iskus11.text.toString().toInt()+
                          iskus15.text.toString().toInt()+
                          estes02.text.toString().toInt()+
                          estes05.text.toString().toInt()+
                          estes06.text.toString().toInt()+
                          estes11.text.toString().toInt()+
                          estes15.text.toString().toInt()+
                          estestvennoe02.text.toString().toInt()+
                          estestvennoe05.text.toString().toInt()+
                          estestvennoe06.text.toString().toInt()+
                          estestvennoe11.text.toString().toInt()+
                          estestvennoe15.text.toString().toInt()
            asd.text="0"
            asd.text=value?.toString()

        }

    }
    fun invisibleplus(){
        binding.buttonPlus.isVisible=false
        binding.buttonMinus.isVisible=false
    }
    fun visibleplus(){
        binding.buttonPlus.isVisible=true
        binding.buttonMinus.isVisible=true
    }



}

