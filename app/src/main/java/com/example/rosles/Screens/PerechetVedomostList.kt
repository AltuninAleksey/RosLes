package com.example.rosles.Screens

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rosles.Adapters.VedomostListAdapter
import com.example.rosles.BaseActivity
import com.example.rosles.R
import com.example.rosles.databinding.PerechetVedomostListBinding
import com.example.rosles.setSizeRelativeCurrentWindow

class PerechetVedomostList : BaseActivity("Перечётная ведомость") {

    private lateinit var binding: PerechetVedomostListBinding

    // Мок-данные. Позже заменим на выборку из БД.
    data class VedomostRow(
        val forestry: String,
        val district: String,
        val tract: String,
        val quarter: String,
        val allotment: String,
        val square: String,
        val date: String
    )

    private val rows: MutableList<VedomostRow> = mutableListOf(
        VedomostRow("Брянское", "Мичуринское", "Соловьи", "3", "25-4", "1.2", "01.01.2023"),
        VedomostRow("Брянское", "Выгоничское", "Лопушь", "14", "5-2", "2.5", "05.04.2023"),
        VedomostRow("Клетнянское", "Мужиновское", "Задубравье", "48", "12-1", "3.7", "18.05.2023"),
        VedomostRow("Навлинское", "Алтуховское", "Бяково", "102", "7-2", "2.0", "03.07.2023"),
        VedomostRow("Трубчевское", "Белоберёзковское", "Кветунь", "55", "9-3", "4.2", "09.09.2023")
    )

    private var selectedIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PerechetVedomostListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listInit()
        toolbarInit()
    }

    override fun onRestart() {
        selectedIndex = null
        listInit()
        super.onRestart()
    }

    private fun listInit() {
        binding.vedomostRecycler.GuideRecycler.layoutManager = LinearLayoutManager(this)
        binding.vedomostRecycler.GuideRecycler.adapter =
            VedomostListAdapter(rows) { selectedIndex = it }
        binding.vedomostRecycler.emptytext.visibility =
            if (rows.isEmpty()) View.VISIBLE else View.INVISIBLE
    }

    private fun toolbarInit() {
        binding.toolbar.addbutton.setOnClickListener {
            Toast.makeText(this, "В разработке", Toast.LENGTH_SHORT).show()
        }
        binding.toolbar.open.setOnClickListener {
            Toast.makeText(this, "В разработке", Toast.LENGTH_SHORT).show()
        }
        binding.toolbar.save.setOnClickListener {
            Toast.makeText(this, "В разработке", Toast.LENGTH_SHORT).show()
        }
        binding.toolbar.delete.setOnClickListener {
            val index = selectedIndex ?: return@setOnClickListener

            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_delete)
            dialog.setSizeRelativeCurrentWindow(0.85, 0.6)

            val close = dialog.findViewById<Button>(R.id.close)
            val delete = dialog.findViewById<Button>(R.id.delete)
            dialog.show()

            close.setOnClickListener { dialog.dismiss() }
            delete.setOnClickListener {
                rows.removeAt(index)
                dialog.dismiss()
                onRestart()
            }
        }
    }
}
