package com.example.rosles.Screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.rosles.Adapters.BaseInterface
import com.example.rosles.Adapters.ChoiceSubjectAdapter
import com.example.rosles.DBCountWood
import com.example.rosles.R
import com.example.rosles.ResponceClass.BaseRespObject
import com.example.rosles.databinding.ChoicesubjectBinding

class ChoiceDistrict:AppCompatActivity() {


    private val db = DBCountWood(this, null)

    private lateinit var binding: ChoicesubjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ChoicesubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.textView4.text="Выберите участковое лесничество"

        var id = intent.getStringExtra("id")

        initcorutine(id!!.toInt())

    }

    @SuppressLint("Range")
    fun initcorutine(id:Int){
        var a : MutableList<BaseRespObject>  = mutableListOf()

        var cursor=db.getDistrict(id)
        cursor.moveToFirst()


        for (i in 1..cursor.getCount()) {
            a.add(BaseRespObject(cursor.getString(cursor.getColumnIndex("id")).toInt(),
                cursor.getString(cursor.getColumnIndex("name_district_forestly"))))
            cursor.moveToNext()
        }
        cursor.close()
            var adapter = ChoiceSubjectAdapter(a,object : BaseInterface {

                override fun onClick(itemView: Any) {
                    start(itemView as Int)
                }

                override fun onClickButton() {

                }
            })
            if (a.isEmpty()){
                binding.GuideRecycler.emptytext.isVisible=true

            }
            binding.GuideRecycler.GuideRecycler.adapter=adapter

    }
    fun start(itemView: Int) {
        val intent1 = Intent(this, ChoiceCvartal::class.java)
        intent1.putExtra("id",itemView.toString())
        intent1.putExtra("id_Vedomost",intent.getStringExtra("id_Vedomost"))
        var a=intent.getStringExtra("id_Vedomost")
        startActivity(intent1)
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
            }
            R.id.profile -> {
                startActivity(Intent(this, profile::class.java))
            }
        }
        return true
    }

}