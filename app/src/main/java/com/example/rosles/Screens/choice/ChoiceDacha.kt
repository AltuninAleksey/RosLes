package com.example.rosles.Screens.choice

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.rosles.Adapters.BaseInterface
import com.example.rosles.Adapters.ChoiceSubjectAdapter
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.ResponceClass.BaseRespObject
import com.example.rosles.ResponceClass.DachaData
import com.example.rosles.Screens.add.AddVedomost
import com.example.rosles.databinding.ChoicesubjectBinding

/**
 * Выбор урочища (getdacha) после участкового лесничества.
 *
 * Используется только в fc_mode (создание ведомости из Лесных культур):
 * ChoiceSubject → ChoiceLes → ChoiceDistrict → ChoiceDacha → AddVedomost.
 * Список — локальный справочник djangoForest_dacha (качается через
 * getDacha на StartScreen). Обычный молодняк идёт мимо этого экрана.
 */
class ChoiceDacha : BaseActivity("Выберите урочище") {

    private val db by lazy { DBCountWood(applicationContext, null) }

    private lateinit var binding: ChoicesubjectBinding
    private var dachaList: List<DachaData> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChoicesubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView4.text = "Выберите урочище"
        initList()
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }

    private fun initList() {
        dachaList = db.getDACHA()
        val items: MutableList<BaseRespObject> = dachaList
            .map { BaseRespObject(it.id, it.name) }
            .toMutableList()

        val adapter = ChoiceSubjectAdapter(items, object : BaseInterface {
            override fun onClick(itemView: Any) {
                start(itemView as Int)
            }

            override fun onClickButton(itemView: Any) {
            }
        })
        if (items.isEmpty()) {
            binding.GuideRecycler.emptytext.isVisible = true
            Toast.makeText(
                this,
                "Список урочищ пуст — загрузите данные на главном экране",
                Toast.LENGTH_LONG
            ).show()
        }
        binding.GuideRecycler.GuideRecycler.adapter = adapter
    }

    fun start(dachaId: Int) {
        val name = dachaList.firstOrNull { it.id == dachaId }?.name
        val intent1 = Intent(this, AddVedomost::class.java)
        // "id" — id участкового лесничества: AddVedomost ждёт его там.
        intent1.putExtra("id", intent.getStringExtra("id_district"))
        intent1.putExtra("id_Vedomost", intent.getStringExtra("id_Vedomost"))
        intent1.putExtra("id_subject", intent.getStringExtra("id_subject"))
        intent1.putExtra("fc_mode", intent.getBooleanExtra("fc_mode", false))
        intent1.putExtra("id_dacha", dachaId.toString())
        intent1.putExtra("dacha_name", name)
        startActivity(intent1)
    }
}
