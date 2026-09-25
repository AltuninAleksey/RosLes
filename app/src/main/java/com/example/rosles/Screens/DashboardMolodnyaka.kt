package com.example.rosles.Screens


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.Network.SafeRequest
import com.example.rosles.Network.SourceProviderHolder
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.RequestClass.AuthRequest
import com.example.rosles.ResponceClass.BaseResponceInterface
import com.example.rosles.ResponceClass.temp_data_userresp
import com.example.rosles.Screens.gps.GpxTrack
import com.example.rosles.TestActivity
import com.example.rosles.databinding.DashboardBinding
import com.example.rosles.sync
import kotlinx.coroutines.launch


private const val REQUEST_CODE_PERMISSIONS = 1001

class Dashboard : BaseActivity() {

    private lateinit var binding: DashboardBinding
    val viewModel by viewModels<ViewModels>()
    private var db = DBCountWood(this, null)

    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)


    private fun hasPermissions(): Boolean =
        REQUIRED_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS
        )
    }

    @SuppressLint("SdCardPath", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val viewModel by viewModels<ViewModels>() // Q

        binding = DashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (!hasPermissions()) {
            requestPermissions()
        }

        binding.profile.setOnLongClickListener {

            startActivity(Intent(this, TestActivity::class.java))
            return@setOnLongClickListener true
        }
        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)


        val sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE)
        val id_user = sPref.getString("id", "0")!!.toInt()

        checkSubjectNumber(id_user)

        val view: View = supportActionBar!!.customView
        val title = view.findViewById<TextView>(R.id.text)
        val back = view.findViewById<ImageView>(R.id.back)

        val menu = view.findViewById<ImageView>(R.id.burger)
        menu.setOnClickListener {
            showpopupmenu(it)
        }
        title.setText("Главная")

        back.setOnClickListener {
            finish()
        }

        binding.perechet.setOnClickListener {
            startActivity(Intent(this, Molodnyak::class.java))
        }
        binding.profile.setOnClickListener {
            startActivity(Intent(this, profile::class.java))
        }

        binding.allDownload.setOnClickListener {
            visibleScrool()
            val database = DBCountWood(this, null)
            database.writableDatabase


            lifecycleScope.launch {

                sync().main1(viewModel, database, this@Dashboard, id_user) {
                    invisibleScrool()
                }
            }
        }

        binding.reload.setOnClickListener {
            var database = DBCountWood(this, null)
            database.writableDatabase
            visibleScrool()
            SafeRequest(viewModel).request(object : SafeRequest.Protection {
                override suspend fun makeRequest(): BaseResponceInterface {
                    val user = SourceProviderHolder.sourcesProvider.getAccountsSource().getToken(
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
                    Toast.makeText(
                        this@Dashboard,
                        "Нет подключения к интернету",
                        Toast.LENGTH_SHORT
                    ).show()
                    invisibleScrool()
                }

                override fun ifAuthException() {
                }
            })
        }

        binding.gps.setOnClickListener {
            startActivity(Intent(this, GpxTrack::class.java))
        }
    }


    fun visibleScrool() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            perechet.visibility = View.GONE
            reload.visibility = View.GONE
            gps.visibility = View.GONE
            profile.visibility = View.GONE
            allDownload.visibility = View.GONE


        }
    }

    fun invisibleScrool() {
        with(binding) {
            progressBar.visibility = View.GONE
            perechet.visibility = View.VISIBLE
            reload.visibility = View.VISIBLE
            gps.visibility = View.VISIBLE
            profile.visibility = View.VISIBLE
            allDownload.visibility = View.VISIBLE


        }
    }

    fun checkSubjectNumber(id: Int) {
        var id_subject = getSharedPreferences("PreferencesName", MODE_PRIVATE)
            .getInt("id_subject", 0)

        if (id_subject <= 0) {
            SafeRequest(viewModel).request(object : SafeRequest.Protection {

                override suspend fun makeRequest(): BaseResponceInterface {
                    val user =
                        SourceProviderHolder.sourcesProvider.getAccountsSource().getprofileid(id)
                    return user
                }

                override fun ifSuccess(responce: BaseResponceInterface?) {
                    if (responce != null && responce is temp_data_userresp) {
                        responce.get.id_subject_rf

                        val sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE);
                        sPref.edit {
                            putInt("id_subject", responce.get.id_subject_rf!!)
                        }
                    }
                }
            })
        }
    }


}


