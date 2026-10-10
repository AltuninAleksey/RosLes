package com.example.rosles.Screens.Dashboards

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.Network.SafeRequest
import com.example.rosles.Network.SourceProviderHolder
import com.example.rosles.Network.ViewModels
import com.example.rosles.Network.startScreenState
import com.example.rosles.ResponceClass.BaseResponceInterface
import com.example.rosles.ResponceClass.temp_data_userresp
import com.example.rosles.Screens.LesCulture
import com.example.rosles.databinding.DashboardLesCultBinding
import com.example.rosles.utils.getToken
import com.example.rosles.utils.getUserId
import kotlinx.coroutines.launch

/**
 * Дашборд раздела "Лесные культуры" — аналог [Dashboard] для молодняка.
 * Три кнопки: Перечетная ведомость (список [LesCulture]),
 * Обновить и Загрузить данные (обе тянут FC-данные с сервера
 * через [ViewModels.loadData]: справочник дач + fc_list_region).
 */
class DashboardLesCult : BaseActivity("Лесные культуры") {

    private val db by lazy { DBCountWood(applicationContext, null) }
    private lateinit var binding: DashboardLesCultBinding
    val viewModel by viewModels<ViewModels>()

    private fun renderState(state: startScreenState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        val enabled = !state.isLoading
        binding.perechet.isEnabled = enabled
        binding.reload.isEnabled = enabled
        binding.allDownload.isEnabled = enabled
        val alpha = if (enabled) 1f else 0.4f
        binding.perechet.alpha = alpha
        binding.reload.alpha = alpha
        binding.allDownload.alpha = alpha
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DashboardLesCultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE)
        checkSubjectNumber(sPref.getString("id", "0")?.toIntOrNull() ?: 0)

        // Подписка один раз при создании экрана, а не внутри клика.
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state -> renderState(state) }
            }
        }

        binding.perechet.setOnClickListener {
            startActivity(Intent(this, LesCulture::class.java))
        }

        // Обновить: отправка локальных изменений — сначала ведомости,
        // затем перечеты (каждый метод сам ставит/снимает isLoading).
        binding.reload.setOnClickListener {
            lifecycleScope.launch {
                val profileId = this@DashboardLesCult.getUserId().toInt()
                viewModel.saveListRegionList(
                    dbCountWood = db,
                    accessToken = this@DashboardLesCult.getToken(),
                    idProfile = profileId
                )
                viewModel.saveFCSampleList(
                    dbCountWood = db,
                    accessToken = this@DashboardLesCult.getToken(),
                    idProfile = profileId
                )
            }
        }
        binding.allDownload.setOnClickListener {
            lifecycleScope.launch { downloadData(fromCacheMessage = "Данные загружены") }
        }
    }

    private suspend fun downloadData(fromCacheMessage: String) {
        // applicationContext вместо Activity — не течёт Activity.
        // Helper закрываем в finally, чтобы не держать дескрипторы БД.
        val db = DBCountWood(applicationContext, null)
        try {
            val sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE)
            val idProfile = sPref.getString("id", "")?.toIntOrNull()
            if (idProfile == null) {
                Toast.makeText(
                    this@DashboardLesCult,
                    "Нет id профиля, перелогиньтесь",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            viewModel.loadData(db = db, aceesToken = getToken(), idProfile = idProfile)
            Toast.makeText(this@DashboardLesCult, fromCacheMessage, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("DashboardLesCult", "loadData failed", e)
            Toast.makeText(
                this@DashboardLesCult,
                "Ошибка загрузки: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        } finally {
            db.close()
        }
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }

    private fun checkSubjectNumber(id: Int) {
        val idSubject = getSharedPreferences("PreferencesName", MODE_PRIVATE)
            .getInt("id_subject", 0)
        if (idSubject > 0) return
        SafeRequest(viewModel).request(object : SafeRequest.Protection {
            override suspend fun makeRequest(): BaseResponceInterface {
                return SourceProviderHolder.sourcesProvider.getAccountsSource().getprofileid(id)
            }

            override fun ifSuccess(responce: BaseResponceInterface?) {
                if (responce != null && responce is temp_data_userresp) {
                    val sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE)
                    sPref.edit {
                        putInt("id_subject", responce.get.id_subject_rf!!)
                    }
                }
            }
        })
    }
}
