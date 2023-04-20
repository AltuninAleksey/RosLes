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
                    startActivity(Intent(this, Listregion::class.java))
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

        cursor= db.getVedombyID(id_Vedomost)
        cursor.moveToFirst()
        var quarter_name =cursor.getString(cursor.getColumnIndex("quarter_name"))
        val soil_lot =cursor.getString(cursor.getColumnIndex("soil_lot"))
        val sample_region =cursor.getString(cursor.getColumnIndex("sample_region"))
        val date =cursor.getString(cursor.getColumnIndex("date"))
        var id_quarter_id =cursor.getString(cursor.getColumnIndex("id_quarter_id"))
        cursor.close()


        if (idCvartal!=null){
            cursor=db.getQuaterbyID(idCvartal)
            cursor.moveToFirst()
            id_quarter_id=cursor.getString(cursor.getColumnIndex("id"))
            quarter_name=cursor.getString(cursor.getColumnIndex("quarter_name"))
            cursor.close()
        }




        binding.idCvartal.text =quarter_name
        binding.vudel.setText(soil_lot)
        binding.samplearea.setText(sample_region)
        binding.date.text = date

        binding.buttonAuto.setOnClickListener {
            db.UpdateLISTREGION(
                id_Vedomost!!,
                binding.date.text.toString(),
                binding.samplearea.text.toString(),
                id_quarter_id.toInt(),
                binding.vudel.text.toString()
            )
            db.Mark_Update_Listregion(id_Vedomost)
            startActivity(Intent(this, Listregion::class.java))
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