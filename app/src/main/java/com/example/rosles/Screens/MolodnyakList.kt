package com.example.rosles.Screens

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import com.example.rosles.BaseActivity
import com.example.rosles.R
import com.example.rosles.databinding.MolodnyakListBinding
import com.example.rosles.setSizeRelativeCurrentWindow

class MolodnyakList : BaseActivity("Учёт молодняка") {

    private lateinit var binding: MolodnyakListBinding

    // Мок-данные. Позже заменим на выборку из БД.
    data class MolodnyakRow(
        val forestry: String,
        val district: String,
        val tract: String,
        val quarter: String,
        val allotment: String,
        val date: String,
        val square: String
    )

    private val rows: MutableList<MolodnyakRow> = mutableListOf(
        MolodnyakRow("Брянское", "Мичуринское", "Соловьи", "3", "25-4", "01.01.2023", "1.2"),
        MolodnyakRow("Брянское", "Мичуринское", "Соловьи", "3", "26-1", "12.03.2023", "0.8"),
        MolodnyakRow("Брянское", "Выгоничское", "Лопушь", "14", "5-2", "05.04.2023", "2.5"),
        MolodnyakRow("Брянское", "Выгоничское", "Лопушь", "14", "5-3", "05.04.2023", "1.1"),
        MolodnyakRow("Клетнянское", "Мужиновское", "Задубравье", "48", "12-1", "18.05.2023", "3.7"),
        MolodnyakRow("Клетнянское", "Мужиновское", "Задубравье", "48", "12-4", "18.05.2023", "0.9"),
        MolodnyakRow("Клетнянское", "Строительная Слобода", "Лутна", "71", "3-6", "22.06.2023", "1.6"),
        MolodnyakRow("Навлинское", "Алтуховское", "Бяково", "102", "7-2", "03.07.2023", "2.0"),
        MolodnyakRow("Навлинское", "Алтуховское", "Бяково", "102", "7-5", "03.07.2023", "1.4"),
        MolodnyakRow("Навлинское", "Пролысовское", "Гладь", "88", "19-1", "14.08.2023", "0.6"),
        MolodnyakRow("Трубчевское", "Белоберёзковское", "Кветунь", "55", "9-3", "09.09.2023", "4.2"),
        MolodnyakRow("Трубчевское", "Белоберёзковское", "Кветунь", "55", "9-7", "09.09.2023", "1.8"),
        MolodnyakRow("Трубчевское", "Городецкое", "Рёвны", "63", "11-2", "27.09.2023", "2.9"),
        MolodnyakRow("Суземское", "Кокоревское", "Смелиж", "120", "4-1", "10.10.2023", "1.0"),
        MolodnyakRow("Суземское", "Кокоревское", "Смелиж", "120", "4-2", "10.10.2023", "0.7")
    )

    private var activeRow: TableRow? = null
    private var selectedIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MolodnyakListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tableInit()
        toolbarInit()
    }

    override fun onRestart() {
        binding.tblLayout3.removeAllViews()
        activeRow = null
        selectedIndex = null
        tableInit()
        super.onRestart()
    }

    @SuppressLint("SetTextI18n")
    private fun tableInit() {
        val colWidth = resources.getDimensionPixelSize(R.dimen.table_col_width)
        val numWidth = resources.getDimensionPixelSize(R.dimen.table_col_num_width)
        val cellPad = (5 * resources.displayMetrics.density).toInt()

        for (i in rows.indices) {
            val row = rows[i]
            val tableRow = TableRow(this)

            val values = listOf(
                (i + 1).toString(),
                row.forestry,
                row.district,
                row.tract,
                row.quarter,
                row.allotment,
                row.date,
                row.square
            )

            for (col in values.indices) {
                val cell = TextView(this)
                cell.text = values[col]
                cell.textAlignment = View.TEXT_ALIGNMENT_CENTER
                cell.gravity = android.view.Gravity.CENTER
                cell.setTextColor(-0x1000000)
                cell.setPadding(cellPad, cellPad, cellPad, cellPad)
                val cellParams = TableRow.LayoutParams(
                    if (col == 0) numWidth else colWidth,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
                tableRow.addView(cell, col, cellParams)
            }

            tableRow.setOnClickListener {
                activeRow?.setBackgroundResource(R.color.color_transporent)
                activeRow = tableRow
                selectedIndex = i
                tableRow.setBackgroundResource(R.color.activecolumn)
            }

            binding.tblLayout3.addView(tableRow, i)

            val layoutParams = tableRow.layoutParams as TableLayout.LayoutParams
            layoutParams.setMargins(0, 10, 0, 10)
            tableRow.layoutParams = layoutParams
        }
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
