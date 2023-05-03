package com.example.rosles.Screens

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.rosles.DBCountWood
import com.example.rosles.R
import com.example.rosles.Screens.Sample.Companion.id_region
import com.example.rosles.databinding.AddProbBinding
import com.example.rosles.setSizeRelativeCurrentWindow
import java.time.format.DateTimeFormatter
import java.util.*

class ChangeSample:AppCompatActivity() {

    private lateinit var binding: AddProbBinding
    private val db = DBCountWood(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddProbBinding.inflate(layoutInflater)
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
        val buf:Int?=intent.getStringExtra("id_sample")?.toInt()
        val id_vdomost:Int?=intent.getStringExtra("id_vdomost")?.toInt()
        var sPref =getSharedPreferences("PreferencesName", MODE_PRIVATE);
        var id= sPref.getString("id", "1")!!.toInt()
        val cursor=db.getSAMPLE(buf!!)




        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//        = LocalDateTime.now().format(formatter).toString()


        cursor.forEach{

            binding.date.text=it.date.toString()
            binding.idCvartal.setText(it.lenght.toString())
            binding.vudel.setText(it.width.toString())
            binding.samplearea.setText(it.square.toString())

        }



        binding.buttonAuto.setOnClickListener {

            db.UpdateSample(
                buf,
                binding.date.text.toString(),
                binding.idCvartal.text.toString(),
                binding.vudel.text.toString(),
                binding.samplearea.text.toString(),
                )
            db.Mark_Update_Sample(buf)
            db.Mark_Update_Listregion(id_vdomost!!)
            val intent = Intent(this, Sample::class.java)
            intent.putExtra("id_Vedomost", id_region)
            startActivity(intent)
            finish()
        }
        binding.date.setOnClickListener {
            initDatePicker()
        }
    }

    @SuppressLint("SetTextI18n")
    fun initDatePicker(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_datepicker)
        dialog.setSizeRelativeCurrentWindow(0.85, 0.6)

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

