package com.example.rosles.Screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.rosles.BaseActivity
import com.example.rosles.R
import com.example.rosles.databinding.StartScreenBinding
import java.io.File
import androidx.core.content.edit

class StartScreen: BaseActivity() {

    private lateinit var binding: StartScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StartScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE)
        val view: View = supportActionBar!!.customView
        val title = view.findViewById<TextView>(R.id.text)
        val back = view.findViewById<ImageView>(R.id.back)

        back.setImageResource(R.drawable.baseline_exit_to_app_24)
        val menu = view.findViewById<ImageView>(R.id.burger)
        menu.setOnClickListener {
            showpopupmenu(it)
        }
        title.setText("Главная")

        back.setOnClickListener {
            sPref.edit {
                putString("id", "")
                putString("FIO", "")
            }

            val file = File("/data/data/com.example.rosles/databases/userdb.db")
            if (file.exists()) {
                file.delete()
            }
            startActivity(Intent(this, Authorization::class.java))
            finish()
        }


        binding.les.setOnClickListener {
            startActivity(Intent(this, MolodnyakList::class.java))
        }
        binding.molodnyak.setOnClickListener {
            startActivity(Intent(this, Dashboard::class.java))
        }
    }
}