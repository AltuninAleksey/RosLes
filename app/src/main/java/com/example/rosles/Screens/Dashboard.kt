package com.example.rosles.Screens


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.databinding.DashboardBinding
import com.example.rosles.sync
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File


class Dashboard: BaseActivity() {

    private lateinit var binding: DashboardBinding
    val viewModel by viewModels<ViewModels>()
    private var db = DBCountWood(this, null)

    @SuppressLint("SdCardPath", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val viewModel by viewModels<ViewModels>() // Q

        binding = DashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)


        val sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE)

        val view: View = supportActionBar!!.customView
        val title=view.findViewById<TextView>(R.id.text)
        val back=view.findViewById<ImageView>(R.id.back)
        val menu=view.findViewById<ImageView>(R.id.burger)
        title.setText("Главная")
        back.setOnClickListener{

            val ed = sPref.edit()
            ed.putString("id", "")
            ed.putString("FIO", "")
            ed.apply()
            startActivity(Intent(this, Authorization::class.java))
            finish()
        }
        menu.setOnClickListener{
            showpopupmenu(it)
        }


//        binding.perechet.image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.keyboard))
//        binding.perechet.text.setText("Перечетная ведомость")
//        binding.gps.image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.location))
//        binding.gps.text.setText("GPS трекер")
//        binding.profile.image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.man))
//        binding.profile.text.setText("Профиль")
//        binding.ALLDOWNLOAD.image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.download))
//        binding.ALLDOWNLOAD.text.setText("Загрузка данных")

        binding.perechet.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.profile.setOnClickListener{
            startActivity(Intent(this, profile::class.java))
        }

        binding.ALLDOWNLOAD.setOnClickListener{
            val value=sPref.getString("id","0")!!.toInt()
            val context=this
            var database = DBCountWood(this, null)
            database.writableDatabase
            lifecycleScope.launch {
                sync().main1(viewModel,database,context,value)
                delay(2000)
                Toast.makeText(context, "Данные обновленны", Toast.LENGTH_SHORT).show()
            }

            viewModel.uploadbd.observe(this){
                Log.v(it.msg.toString(),"")
            }
            // загрузка ВСЕХ справочников
//            val inputBase=File("/data/data/com.example.rosles/databases/userdb.db")
//            val txtFile = resources.openRawResource(R.raw.db_sqlite3)
//            GPStracker.copy(txtFile,inputBase)
//            Toast.makeText(this, "Данные обновленны", Toast.LENGTH_SHORT).show()
        }



        binding.reload.setOnClickListener{
// ТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬТВАРЬ
            val context=this
            val value=sPref.getString("id","0")!!.toInt()
            var database = DBCountWood(this, null)
            database.writableDatabase
            lifecycleScope.launch{
                sync().load(viewModel,db,context)
//                delay(2000)
//                sync().main1(viewModel,database, context,value)
                Toast.makeText(context, "Успех", Toast.LENGTH_SHORT).show()
            }

        }

        binding.gps.setOnClickListener{
            startActivity(Intent(this, GpxTrack::class.java))
        }
    }



}


