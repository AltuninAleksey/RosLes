package com.example.rosles.Screens


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.Network.SafeRequest
import com.example.rosles.Network.SourceProviderHolder
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.RequestClass.AuthRequest
import com.example.rosles.ResponceClass.AuthReSponce
import com.example.rosles.ResponceClass.BaseResponceInterface
import com.example.rosles.ResponceClass.temp_data_userresp
import com.example.rosles.TestActivity
import com.example.rosles.databinding.DashboardBinding
import com.example.rosles.sync
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


        binding.profile.setOnLongClickListener {

            startActivity(Intent(this, TestActivity::class.java))
            return@setOnLongClickListener true
        }
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)


        val sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE)
        val id_user=sPref.getString("id","0")!!.toInt()

        checksubjectnumber(id_user)

        val view: View = supportActionBar!!.customView
        val title=view.findViewById<TextView>(R.id.text)
        val back=view.findViewById<ImageView>(R.id.back)

        back.setImageResource(R.drawable.baseline_exit_to_app_24)
        val menu=view.findViewById<ImageView>(R.id.burger)
        menu.setOnClickListener{
            showpopupmenu(it)
        }
        title.setText("Главная")

        back.setOnClickListener{

            val ed = sPref.edit()
            ed.putString("id", "")
            ed.putString("FIO", "")
            ed.apply()

            var file = File("/data/data/com.example.rosles/databases/userdb.db")
            if (file.exists()) {

                file.delete()
            }

            startActivity(Intent(this, Authorization::class.java))
            finish()
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
            visibleScrool()
            var database = DBCountWood(this, null)
            database.writableDatabase


            lifecycleScope.launch {

                sync().main1(viewModel,database,this@Dashboard,id_user){
                    invisibleScrool()
                }

            }

        }



        binding.reload.setOnClickListener{

            var database = DBCountWood(this, null)
            database.writableDatabase

            visibleScrool()

                SafeRequest(viewModel).request(object : SafeRequest.Protection{

                    override suspend fun makeRequest(): BaseResponceInterface {
                        val user = SourceProviderHolder.sourcesProvider.getAccountsSource().get_user(
                            AuthRequest(
                                "slinchenkovapa@rcfh.rosleshoz.gov.ru",
                               "cnhfuf1997"
                            )
                        )
                        return user
                    }

                    override fun ifSuccess(responce: BaseResponceInterface?) {
                        lifecycleScope.launch {
                            sync().load(viewModel, db, this@Dashboard)
                            Toast.makeText(this@Dashboard, "Успех", Toast.LENGTH_SHORT).show()
                            invisibleScrool()
                        }
                    }

                    override fun ifConnectionException() {
                        Toast.makeText(this@Dashboard, "Нет подключения к интернету", Toast.LENGTH_SHORT).show()
                        invisibleScrool()
                    }

                    override fun ifAuthException() {

                    }

                })




//                delay(2000)
//                sync().main1(viewModel,database, context,value)




        }

        binding.gps.setOnClickListener{
            startActivity(Intent(this, GpxTrack::class.java))
        }
    }


    fun visibleScrool(){
        with(binding){
            progressBar.visibility=View.VISIBLE

            perechet.visibility=View.GONE
            reload.visibility=View.GONE
            gps.visibility=View.GONE
            profile.visibility=View.GONE
            ALLDOWNLOAD.visibility=View.GONE


        }
    }

    fun invisibleScrool(){
        with(binding){
            progressBar.visibility=View.GONE

            perechet.visibility=View.VISIBLE
            reload.visibility=View.VISIBLE
            gps.visibility=View.VISIBLE
            profile.visibility=View.VISIBLE
            ALLDOWNLOAD.visibility=View.VISIBLE


        }
    }

    fun checksubjectnumber(id:Int){
        var id_subject = getSharedPreferences("PreferencesName", MODE_PRIVATE)
            .getInt("id_subject",0)



        if (id_subject<=0 ){
            SafeRequest(viewModel).request(object : SafeRequest.Protection{

                override suspend fun makeRequest(): BaseResponceInterface {
                    val user = SourceProviderHolder.sourcesProvider.getAccountsSource().getprofileid(id)
                    return user
                }

                override fun ifSuccess(responce: BaseResponceInterface?) {
                    if (responce != null && responce is temp_data_userresp){
                        responce.get.id_subject_rf

                        var sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE);
                        val ed = sPref.edit()
                        ed.putInt("id_subject",  responce.get.id_subject_rf!!.toInt()!!)
                        ed.apply()
                    }
                }



            })
        }



    }



}


