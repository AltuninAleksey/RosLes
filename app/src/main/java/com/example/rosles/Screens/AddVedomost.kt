package com.example.rosles.Screens

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.rosles.DBCountWood
import com.example.rosles.R
import com.example.rosles.databinding.AddVedomostBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddVedomost:AppCompatActivity() {

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
        title.setText("Добавление перечетной ведомости")
        back.setOnClickListener{
            finish()
        }
        menu.setOnClickListener{
            showpopupmenu(it)
        }
        RecyclerviewInit()
        InitClick()
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
        val buf:Int?=intent.getStringExtra("id")?.toInt()
        val cursor=db.getQuaterbyID(buf)
        cursor.moveToFirst()
        binding.idCvartal.text =cursor.getString(cursor.getColumnIndex("quarter_name"))
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        binding.date.text = LocalDateTime.now().format(formatter).toString()
    }
    fun InitClick() {
        binding.buttonAuto.setOnClickListener {
            db.createvedom(
                binding.date.text.toString(),
                binding.samplearea.text.toString(),
                intent.getStringExtra("id")!!.toInt(),
                binding.vudel.text.toString())
            Toast.makeText(this,"Данные добавлены",Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
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


