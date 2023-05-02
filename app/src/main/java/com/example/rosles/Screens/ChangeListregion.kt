package com.example.rosles.Screens

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.rosles.DBCountWood
import com.example.rosles.R
import com.example.rosles.databinding.AddVedomostBinding
import java.util.*

class ChangeListregion: AppCompatActivity() {
    private lateinit var binding: AddVedomostBinding
    private val db = DBCountWood(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddVedomostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //инциализация навигации
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        val view: View = supportActionBar!!.customView
        val title=view.findViewById<TextView>(R.id.text)
        val back=view.findViewById<ImageView>(R.id.back)
        val menu=view.findViewById<ImageView>(R.id.burger)
        title.setText("Редактирование")
        back.setOnClickListener{
            finish()
        }
        menu.setOnClickListener{
            showpopupmenu(it)
        }

        RecyclerviewInit()
    }

    fun showpopupmenu (view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.main -> {
                    startActivity(Intent(this, Dashboard::class.java))
                }
                R.id.itemperechet -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                R.id.itemgps -> {
                    startActivity(Intent(this, gps_activity::class.java))
                }
                R.id.profile -> {
                    startActivity(Intent(this, profile::class.java))
                }
            }
            true
        })
        popup.show()

    }


    @SuppressLint("Range")
    fun RecyclerviewInit() {
        val idCvartal:Int?=intent.getStringExtra("id")?.toInt()
        val id_Vedomost:Int?=intent.getStringExtra("id_Vedomost")?.toInt()

        var cursor:Cursor
        var bufcvartalid:String
        var bufcvartalname:String?

        //квартал

        val vedom = db.getVedombyID(id_Vedomost)

        var bufQuarter_name = vedom.quarterName
        var bufId_quarter_id = vedom.idQuarterId

        binding.idCvartal.text = bufQuarter_name
        binding.vudel.setText(vedom.soilLot)
        binding.samplearea.setText(vedom.sampleRegion)
        binding.date.text = vedom.date

        if (idCvartal!=null){
            val quater = db.getQuaterbyID(idCvartal)

            bufId_quarter_id = quater.id
            bufQuarter_name = quater.quarterName
        }

        binding.buttonAuto.setOnClickListener {
            db.UpdateLISTREGION(
                id_Vedomost!!,
                binding.date.text.toString(),
                binding.samplearea.text.toString(),
                bufId_quarter_id.toInt(),
                binding.vudel.text.toString()
            )
            db.Mark_Update_Listregion(id_Vedomost)
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.idCvartal.setOnClickListener{
            val intent1=Intent(this, ChoiceSubject::class.java)
            intent1.putExtra("id_Vedomost",id_Vedomost.toString())

            startActivity(intent1)
        }
        binding.date.setOnClickListener {
            initDatePicker()
        }
    }
    @SuppressLint("SetTextI18n")
    fun initDatePicker(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_datepicker)

        val mInfoTextView = dialog.findViewById<TextView>(R.id.textView)
        val mDatePicker = dialog.findViewById<DatePicker>(R.id.datePicker)

        val today = Calendar.getInstance()

        mDatePicker!!.init(
            today[Calendar.YEAR], today[Calendar.MONTH],
            today[Calendar.DAY_OF_MONTH]
        ) { view, year, monthOfYear, dayOfMonth ->
            mInfoTextView!!.text = "$year-${monthOfYear + 1}-$dayOfMonth"
        }
        val changingDateButton = dialog.findViewById<View>(R.id.button)
        changingDateButton.setOnClickListener {
            binding.date.text=mInfoTextView!!.text
            dialog.dismiss()
        }
        dialog.show()

    }

}