package com.example.rosles.Screens

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import com.example.rosles.DBCountWood
import com.example.rosles.R
import com.example.rosles.databinding.ScreenPhotoBinding
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SelectPhoto:BaseAppClass() {


    private val db = DBCountWood(this, null)


    var latitude: Double? = 0.0
    var longitude: Double? = 0.0
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    var id_sample=98
    private val REQUEST_TAKE_PHOTO = 1
    private lateinit var binding: ScreenPhotoBinding
    private lateinit var locationManager: LocationManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScreenPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //инциализация навигации
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        // проверяем что разрешение получено

        setLocation()

        db.getphotoall()

        val view: View = supportActionBar!!.customView

        binding.toolbar.reload.visibility = View.GONE

        binding.toolbar.save.visibility = View.GONE

        binding.toolbar.addbutton.setOnClickListener {
            //     getContent.launch("image/*")
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
        binding.toolbar.delete.setOnClickListener{
            for (i in 0 .. binding.tblLayout.childCount){
                val row=binding.tblLayout.getChildAt(i)

            }

        }
    }

    private fun setLocation() {

        // проверяем что разрешение получено
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Проверяем разрешения на использование местоположения
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            return
        }

        // Получаем местоположение пользователя
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        if (location == null) {
            Toast.makeText(this, "Включите GPS", Toast.LENGTH_SHORT).show()
        } else {
            latitude = location!!.latitude
            longitude = location.longitude
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Фотка сделана, извлекаем миниатюру картинки
            val thumbnailBitmap = data?.extras?.get("data") as Bitmap


        }

        if (requestCode === 1 && resultCode === RESULT_OK) {
            // Проверяем, содержит ли результат маленькую картинку
            if (data != null) {
                if (data.hasExtra("data")) {
                    val thumbnailBitmap: Bitmap? = data.getParcelableExtra("data")
                    // Какие-то действия с миниатюрой
                    binding.test.setImageBitmap(thumbnailBitmap)
                    val temp=GPStracker(this)

                    db.writephoto(temp.bitmap_to_base(thumbnailBitmap), id_sample,latitude,longitude,LocalDateTime.now().format(formatter).toString())

                }
            }
        }
    }

    fun inittable(){

                    val tableRow = TableRow(this)

                    val text1=TextView(this)
                    val text2=TextView(this)
                    val text3=TextView(this)
                    val text4=TextView(this)

//                    text1.setText(BITMAP.toString().substringAfter('@'))
                    text2.setText(latitude.toString())
                    text3.setText(longitude.toString())
                    text4.setText(LocalDateTime.now().format(formatter).toString())

                    val textvalues=listOf(text1, text2, text3, text4)
                    textvalues.forEach {
                        it.textAlignment=View.TEXT_ALIGNMENT_CENTER
                        it.setTextColor(-0x1000000)
                        tableRow.addView(it)
                    }
                    binding.tblLayout.addView(tableRow)
    }
}


//    чтение из файлов
//    val getContent = registerForActivityResult(GetContent()) { uri: Uri? ->
//        bitmap=MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(uri.toString()))
//        val tableRow = TableRow(this)
//        val text1= TextView(this)
//        val text2= TextView(this)
//
//        var file = File(uri.toString())
//
//        text1.setText(bitmap.toString())
//        text2.setText(LocalDateTime.now().toString())
//
//        tableRow.addView(text1)
//        tableRow.addView(text2)
//        tableRow.setOnClickListener {
//            binding.test.setImageBitmap(bitmap)
//        }
//        binding.tblLayout.addView(tableRow)
//    }







