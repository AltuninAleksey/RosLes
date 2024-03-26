package com.example.rosles.Screens

import android.Manifest
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.view.get
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.R
import com.example.rosles.databinding.ScreenPhotoBinding
import com.example.rosles.utils.gps.GpsManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SelectPhoto:BaseActivity() {
    private val db = DBCountWood(this, null)

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    var photobuf:Bitmap?=null
    var id_sample=0
    var id_vdomost=0
    private val REQUEST_TAKE_PHOTO = 1
    private lateinit var binding: ScreenPhotoBinding
    private lateinit var locationManager: LocationManager
    private val gpsManager = GpsManager(this, Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScreenPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gpsManager.init()
        // проверяем что разрешение получено
        id_sample=intent.getIntExtra("id_sample",0)
        id_vdomost=intent.getIntExtra("id_vdomost",0)



//        db.getphotoall()

        inittable()

        val view: View = supportActionBar!!.customView

        binding.toolbar.reload.visibility = View.GONE

        binding.toolbar.save.visibility = View.GONE

        binding.toolbar.addbutton.setOnClickListener {
            //     getContent.launch("image/*")
            try {
                gpsManager.updateLocation()
            } catch (illegalStateException: IllegalStateException) {
                Toast.makeText(this, illegalStateException.message, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
        binding.toolbar.delete.visibility=View.GONE
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

                    val temp=GPStracker(this)

                    db.writephoto(temp.bitmap_to_base(thumbnailBitmap), id_sample,gpsManager.latitude,gpsManager.longitude,LocalDateTime.now().format(formatter).toString())
                    db.Mark_Update_Sample(id_sample)
                    db.Mark_Update_Listregion(id_vdomost)
                }
            }
        }
    }


    override fun onRestart() {
        val buf= binding.tblLayout.getChildAt(0)
        binding.tblLayout.removeAllViews()
        binding.tblLayout.addView(buf)
        inittable()
        super.onRestart()
    }
    fun inittable(){

        var activetableRow: TableRow? = null
        val list=db.getphoto(id_sample)

        list.forEach {
            val tableRow = TableRow(this)

            val text1=TextView(this)
            val text2=TextView(this)
            val text3=TextView(this)
            val text4=TextView(this)
            val photo=it.photo

            text1.setText(it.latitude.toString())
            text2.setText(it.longitude.toString())
            text3.setText(it.date.format(formatter).toString())
            text4.setText(it.photo.toString().substringAfter('@'))

            val textvalues=listOf(text1, text2, text3, text4)
            textvalues.forEach {
                it.textAlignment=View.TEXT_ALIGNMENT_CENTER
                it.setTextColor(-0x1000000)
                tableRow.addView(it)
            }
            tableRow.setOnClickListener{
                photobuf=photo
                activetableRow?.setBackgroundResource(R.color.color_transporent)
                tableRow.setBackgroundResource(R.color.color_transporent)
                activetableRow = tableRow
                activetableRow!!.setBackgroundResource(R.color.activecolumn)
            }
            binding.tblLayout.addView(tableRow)
        }

        binding.toolbar.open.setOnClickListener{
            if(activetableRow!=null){
                val latitude = activetableRow?.get(0) as TextView
                val longitude = activetableRow?.get(1) as TextView
                val date_value = activetableRow?.get(2) as TextView
                val dialog: Dialog = Dialog(this)
                dialog.setContentView(R.layout.view_image_dialog)
                val image = dialog.findViewById<ImageView>(R.id.image_for_photo)
                val coords = dialog.findViewById<TextView>(R.id.value_coord)
                val date = dialog.findViewById<TextView>(R.id.value_date)
                val delete = dialog.findViewById<Button>(R.id.delete)
                image.setImageBitmap(photobuf)
                coords.setText("${latitude.text} ${longitude.text}")
                date.setText("${date_value.text}")
                dialog.show()
            }
        }

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







