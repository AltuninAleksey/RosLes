package com.example.rosles.Screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.rosles.Adapters.BaseInterface
import com.example.rosles.Adapters.ChoiceSubjectAdapter
import com.example.rosles.DBCountWood
import com.example.rosles.R
import com.example.rosles.ResponceClass.BaseRespObject
import com.example.rosles.databinding.ChoicesubjectBinding

class ChoiceCvartal:AppCompatActivity() {


    private val db = DBCountWood(this, null)

    private lateinit var binding: ChoicesubjectBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ChoicesubjectBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        var id = intent.getStringExtra("id")
        initcorutine(id!!.toInt())
        binding.textView4.text="Выберите квартал"
    }

    @SuppressLint("Range")
    fun initcorutine(id:Int){
        var a : MutableList<BaseRespObject>  = mutableListOf()
        var quaterList = db.getQuater(id)

        for (i in 0..quaterList.size-1) {
            a.add(quaterList[i].toBaseRespObject())
        }

            var adapter = ChoiceSubjectAdapter(a, object : BaseInterface {

                override fun onClick(itemView: Any) {
                    start(itemView as Int)
                }

                override fun onClickButton(itemView: TextView) {
                    return
                }
            })
            if (a.isEmpty()){
                binding.GuideRecycler.emptytext.isVisible=true
            }


            binding.GuideRecycler.GuideRecycler.adapter=adapter

    }
    fun start(itemView: Int) {
        var buf=intent.getStringExtra("id_Vedomost")
        if(buf!=null){
            val intent1 = Intent(this, ChangeVedomost::class.java)
            intent1.putExtra("id",itemView.toString())
            intent1.putExtra("id_Vedomost",buf)
            startActivity(intent1)
        }else{
            val intent = Intent(this, AddVedomost::class.java)
            intent.putExtra("id",itemView.toString())
            startActivity(intent)
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
            }
            R.id.profile -> {
                startActivity(Intent(this, profile::class.java))
            }
        }
        return true
    }
}