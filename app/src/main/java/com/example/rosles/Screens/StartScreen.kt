package com.example.rosles.Screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.databinding.StartScreenBinding
import com.example.rosles.utils.getToken
import kotlinx.coroutines.launch
import java.io.File
import kotlin.getValue

class StartScreen: BaseActivity() {

    private lateinit var binding: StartScreenBinding

    val viewModel by viewModels<ViewModels>()

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

        binding.download.setOnClickListener {
            lifecycleScope.launch {
                val db = DBCountWood(this@StartScreen, null)
                viewModel.getDacha(db, this@StartScreen.getToken())
                viewModel.getListRegionList(db, this@StartScreen.getToken())
            }
        }

        back.setOnClickListener {
            sPref.edit {
                putString("id", "")
                putString("FIO", "")
                putString("access_token", "")
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