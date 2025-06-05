package com.example.rosles.Screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.location.LocationListener
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.view.size
import com.example.rosles.DBCountWood
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.ResponceClass.GPS_Data
import com.example.rosles.databinding.GpsBinding
import com.example.rosles.utils.gps.GpsManager


class gps_activity : AppCompatActivity() {

    val viewModel by viewModels<ViewModels>()
    private lateinit var binding: GpsBinding
    private val db = DBCountWood(this, null)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GpsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //инциализация навигации
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "GPS"
        //val id_sample = intent.getStringExtra("id_sample")
        val id_sample = "c7f8575e-ca38-45ce-b566-5d931c18fa9f"
        binding.toolbar.reload.visibility = View.GONE
        binding.toolbar.open.visibility = View.GONE


        val Gps_Data_List: MutableList<GPS_Data> = db.GET_Gps_Data(id_sample)




        val gpsManager = GpsManager(this, Looper.getMainLooper())


        gpsManager.init()



        var i = 0
        binding.toolbar.addbutton.setOnClickListener {
            try {
                gpsManager.updateLocation()
            } catch (illegalStateException: IllegalStateException) {
                Toast.makeText(this, illegalStateException.message, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            var tableRow = TableRow(this)
            var text1 = TextView(this)
            var text2 = TextView(this)
            var text3 = TextView(this)
            var checkBox = CheckBox(this)

            text1.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text2.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text3.textAlignment = View.TEXT_ALIGNMENT_CENTER
            checkBox.textAlignment = View.TEXT_ALIGNMENT_CENTER

            text1.setTextColor(-0x1000000)
            text2.setTextColor(-0x1000000)
            text3.setTextColor(-0x1000000)
            checkBox.setTextColor(-0x1000000)

            text1.setText(i.toString())
            text2.setText(gpsManager.longitude.toString())
            text3.setText(gpsManager.latitude.toString())

            val gpsData = GPS_Data(
                text1.toString(),
                gpsManager.latitude,
                gpsManager.longitude,
                checkBox.isChecked,
                id_sample,
                2
            )

            tableRow.addView(text1)
            tableRow.addView(text2)
            tableRow.addView(text3)
            tableRow.addView(checkBox)
            binding.tblLayout.addView(tableRow)
            i++

            Gps_Data_List.add(gpsData)


        }

        binding.toolbar.delete.setOnClickListener {
            if (binding.tblLayout.size >= 1) {
                i--
                binding.tblLayout.removeViewAt(binding.tblLayout.size - 1)


                db.Delete_Gps_Data(
                    Gps_Data_List[Gps_Data_List.lastIndex]
                )


                Gps_Data_List.removeAt(Gps_Data_List.lastIndex)


            }

        }
        binding.toolbar.saveButton.visibility = View.VISIBLE
        binding.toolbar.save.visibility = View.GONE
        binding.toolbar.saveButton.setOnClickListener {


            Gps_Data_List.forEach {
                db.Delete_Gps_Data(it)
                db.Create_Gps_Data(it)
            }

            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show()
            finish()
        }


            var count = 1



        Gps_Data_List.forEach { gps ->
                var tableRow = TableRow(this)
                var text1 = TextView(this)
                var text2 = TextView(this)
                var text3 = TextView(this)
                var checkBox = CheckBox(this)

                text1.textAlignment = View.TEXT_ALIGNMENT_CENTER
                text2.textAlignment = View.TEXT_ALIGNMENT_CENTER
                text3.textAlignment = View.TEXT_ALIGNMENT_CENTER
                checkBox.textAlignment = View.TEXT_ALIGNMENT_CENTER

                text1.setTextColor(-0x1000000)
                text2.setTextColor(-0x1000000)
                text3.setTextColor(-0x1000000)
                checkBox.setTextColor(-0x1000000)




                text1.setText(count.toString())
                text2.setText(gps.latitude.toString())
                text3.setText(gps.longitude.toString())


                tableRow.addView(text1)
                tableRow.addView(text2)
                tableRow.addView(text3)
                tableRow.addView(checkBox)
                binding.tblLayout.addView(tableRow)
                count++
            }




    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.rosles.R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.main -> {
                startActivity(Intent(this, Dashboard::class.java))
            }

            R.id.itemperechet -> {
                startActivity(Intent(this, MainActivity::class.java))
            }

            R.id.itemgps -> {
                startActivity(Intent(this, gps_activity::class.java))
                finish()
            }

            R.id.profile -> {
                startActivity(Intent(this, profile::class.java))
                finish()
            }

        }
        return true
    }
}


