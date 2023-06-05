package com.example.rosles.Screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.isGone
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.R
import com.example.rosles.databinding.ListSquareBinding

class lisq_square : BaseActivity("Список пробных площадей") {

    private lateinit var binding: ListSquareBinding

    private val db = DBCountWood(this, null)
    var id_vdomost:Int?=0
    var id_sample = 0
    var numberOfSelectPoroda: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListSquareBinding.inflate(layoutInflater)
        setContentView(binding.root)
        RecyclerviewInit()
    }

    companion object{
        var id_region=0
        fun setregion(value:Int?){
            if(value!=null)
                id_region=value
        }
    }

    override fun onRestart() {
       binding.tblLayout3.removeAllViews()
       RecyclerviewInit()
       super.onRestart()
   }

    @SuppressLint("Range")
    fun RecyclerviewInit() {
        setregion(intent.getStringExtra("id_Vedomost")?.toInt())
        id_vdomost = id_region

        val vedom = db.getVedombyID(id_vdomost!!.toInt())
        binding.lesnnich.text = vedom.nameForestly
        binding.district.text = vedom.nameDistrictForestly
        binding.quater.text =   vedom.quarterName
        binding.vudel.text =    vedom.soilLot
        binding.date.text =     vedom.date
        binding.square.text =   vedom.sampleRegion
        binding.resultprob.text =
            vedom.sampleRegion.toFloatOrNull()?.let { valueprob(it).toString() }
        val bufer_quater_id = vedom.idQuarterId


        val squareList = db.getlistsquare(id_vdomost!!.toInt())

        var activetableRow: TableRow? = null

        for (i in 0..squareList.size-1) {

            val tableRow = TableRow(this)

            val text0 = TextView(this)
            val text1 = TextView(this)
            val text2 = TextView(this)
            val text3 = TextView(this)
            val text4 = TextView(this)
            val text5 = TextView(this)

            text0.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text1.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text2.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text3.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text4.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text5.textAlignment = View.TEXT_ALIGNMENT_CENTER

            text0.setTextColor(-0x1000000)
            text1.setTextColor(-0x1000000)
            text2.setTextColor(-0x1000000)
            text3.setTextColor(-0x1000000)
            text4.setTextColor(-0x1000000)
            text5.setTextColor(-0x1000000)

            tableRow.setOnClickListener {
                activetableRow?.setBackgroundResource(R.color.color_transporent)
                tableRow.setBackgroundResource(R.color.color_transporent)
                activetableRow = tableRow
                numberOfSelectPoroda = (tableRow.getChildAt(0) as TextView).text.toString()
                activetableRow!!.setBackgroundResource(R.color.activecolumn)
                id_sample = squareList[i].id.toInt()
            }

            text0.setText((i+1).toString())
            text1.setText(squareList[i].lenght)
            text2.setText(squareList[i].width)
            text3.setText(squareList[i].square)
            text4.setText(squareList[i].date)

            tableRow.addView(text0, 0)
            tableRow.addView(text1, 1)
            tableRow.addView(text2, 2)
            tableRow.addView(text3, 3)
            tableRow.addView(text4, 4)

            binding.tblLayout3.addView(tableRow, i);

            val layoutParams = tableRow.layoutParams as TableLayout.LayoutParams
            layoutParams.setMargins(0, 10, 0, 10)
            tableRow.layoutParams = layoutParams
        }

        binding.toolbar.save.setOnClickListener{
            val intent = Intent(this, ChangeSample::class.java)
            intent.putExtra("id_sample", id_sample.toString())
            intent.putExtra("id_vdomost", bufer_quater_id)
            startActivity(intent)
        }
        binding.toolbar.addbutton.setOnClickListener {
            val intent = Intent(this, AddSample::class.java)
            intent.putExtra("id", id_vdomost.toString())
            intent.putExtra("id_quater", bufer_quater_id)
            startActivity(intent)
        }
        var defaultvalue=100
        if(binding.resultprob.text=="1"){
            defaultvalue=150
        }
        binding.toolbar.open.setOnClickListener {
            if (activetableRow != null) {
                val bufer = activetableRow?.get(0)
                val textView = bufer as TextView
                val intent = Intent(this, Wood::class.java)
                intent.putExtra("udel", id_sample)
                intent.putExtra("proba", id_vdomost.toString())
                intent.putExtra("numberOfPoroda", numberOfSelectPoroda)
                intent.putExtra("valuewood", defaultvalue.toString())
                startActivity(intent)
            }

        }
    }

    fun valueprob(value: Float): Int {
        var result = 1
        result = when (value) {
            in 0.0..5.0 -> 1
            in 5.0..10.0 -> 2
            in 10.0..20.0 -> 2
            in 20.0..30.0 -> 3
            in 30.0..40.0 -> 4
            in 40.0..50.0 -> 5
            in 50.0..9999999.0 -> (value / 15.0).toInt()

            else -> {
                1
            }
        }
        return result
    }

}

