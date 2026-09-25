package com.example.rosles.Screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.Network.ViewModels
import com.example.rosles.Network.startScreenState
import com.example.rosles.R
import com.example.rosles.Screens.auth.Authorization
import com.example.rosles.databinding.StartScreenBinding
import com.example.rosles.utils.getToken
import kotlinx.coroutines.launch
import java.io.File
import kotlin.getValue

class StartScreen: BaseActivity() {

    private lateinit var binding: StartScreenBinding

    val viewModel by viewModels<ViewModels>()


    private fun renderState(state: startScreenState) {
        // Управляем видимостью прогресс-бара + блокируем кнопку,
        // чтобы повторные нажатия не запускали параллельные загрузки.
        binding.reload.isVisible = state.isLoading
        binding.download.isEnabled = !state.isLoading
        binding.download.alpha = if (state.isLoading) 0.4f else 1f
    }

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

        // Подписываемся ОДИН раз при создании экрана, а не внутри клика.
        // Раньше collect() вызывался ПОСЛЕ loadData(), поэтому isLoading=true
        // никто не видел и renderState срабатывал только когда всё уже загрузилось.
        // repeatOnLifecycle: сборка идёт только пока экран в STARTED, в фоне отменяется.
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state -> renderState(state) }
            }
        }

        binding.download.setOnClickListener {
            // Только запускаем загрузку. Флаг isLoading выставляется внутри
            // loadData() ДО сетевых запросов, коллектор выше видит его сразу.
            lifecycleScope.launch {
                // applicationContext вместо this@StartScreen — не течёт Activity.
                // Helper закрываем после загрузки, чтобы не держать дескрипторы БД.
                val db = DBCountWood(applicationContext, null)
                try {
                    viewModel.loadData(db = db, getToken())
                } catch (e: Exception) {
                    Log.e("StartScreen", "loadData failed", e)
                    Toast.makeText(
                        this@StartScreen,
                        "Ошибка загрузки: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                } finally {
                    db.close()
                }
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
            startActivity(Intent(this, LesCulture::class.java))
        }
        binding.molodnyak.setOnClickListener {
            startActivity(Intent(this, Dashboard::class.java))
        }
    }
}