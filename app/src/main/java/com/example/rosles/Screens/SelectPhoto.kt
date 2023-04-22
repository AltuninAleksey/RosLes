package com.example.rosles.Screens

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TableRow
import android.widget.TextView

import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.RequestClass.UpdateRequest
import com.example.rosles.databinding.ScreenPhotoBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDateTime
import java.util.*


class SelectPhoto:AppCompatActivity() {

    val viewModel by viewModels<ViewModels>()

    var bitmap:Bitmap?=null
    val getContent = registerForActivityResult(GetContent()) { uri: Uri? ->
        bitmap=MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(uri.toString()))
        val tableRow = TableRow(this)
        val text1= TextView(this)
        val text2= TextView(this)

        var file = File(uri.toString())

        text1.setText(bitmap.toString())
        text2.setText(LocalDateTime.now().toString())

        tableRow.addView(text1)
        tableRow.addView(text2)
        tableRow.setOnClickListener {
            binding.test.setImageBitmap(bitmap)
            sendFileRequest(bitmap!!)
        }
        binding.tblLayout.addView(tableRow)
    }

    fun sendFileRequest(image: Bitmap) {
        val wrapper = ContextWrapper(this)
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file,"${UUID.randomUUID()}.jpg")
        val stream: OutputStream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.JPEG,25,stream)
        stream.flush()
        stream.close()

        val photoFile = file
        val photo = MultipartBody.Part.createFormData(
            "photo",
            photoFile.name,
            photoFile.asRequestBody("image/*".toMediaType())
        )
        viewModel.upload(UpdateRequest(photo,98))
    }

    private lateinit var binding: ScreenPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScreenPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //инциализация навигации
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title="Фото-точки"

        binding.toolbar.reload.visibility= View.GONE
        binding.toolbar.open.visibility= View.GONE
        binding.toolbar.save.visibility= View.GONE
        binding.toolbar.addbutton.setOnClickListener {
            getContent.launch("image/*")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.rosles.R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->finish()
            R.id.main->{startActivity(Intent(this, Dashboard::class.java))}
            R.id.itemperechet->{startActivity(Intent(this, MainActivity::class.java))}
            R.id.itemgps->{startActivity(Intent(this, gps_activity::class.java))
                finish()}
            R.id.profile->{startActivity(Intent(this, profile::class.java))
                finish()}

        }
        return true
    }

}