package com.example.rosles.Screens

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import com.example.rosles.BaseActivity
import com.example.rosles.R
import com.example.rosles.databinding.ProbaInfoBinding
import com.example.rosles.setSizeRelativeCurrentWindow

class ProbaInfo : BaseActivity("Информация о пробной площади") {

    private lateinit var binding: ProbaInfoBinding

    // Мок-данные. Позже заменим на выборку из БД.
    data class Uchastok(
        val forestry: String,
        val district: String,
        val tract: String,
        val quarter: String,
        val allotment: String,
        val area: String,
        val date: String
    )

    data class CultureRow(val breed: String, val alive: String, val dead: String)
    data class DiameterRow(val diameter: String, val height: String)
    data class UndergrowthRow(
        val breed: String,
        val h05: String,
        val h15: String,
        val hMore: String,
        val maxHeight: String
    )

    private val info = Uchastok("Брянское", "Мичуринское", "Соловьи", "3", "25-4", "1.2", "01.01.2023")

    private val cultureRows: MutableList<CultureRow> = mutableListOf(
        CultureRow("Сосна", "1200", "150"),
        CultureRow("Ель", "900", "80"),
        CultureRow("Дуб", "400", "60"),
        CultureRow("Берёза", "300", "20"),
        CultureRow("Сосна", "1100", "130"),
        CultureRow("Ель", "850", "70"),
        CultureRow("Лиственница", "500", "40"),
        CultureRow("Дуб", "350", "55")
    )

    private val diameterRows: MutableList<DiameterRow> = mutableListOf(
        DiameterRow("0.8", "12"),
        DiameterRow("1.0", "15"),
        DiameterRow("0.7", "10"),
        DiameterRow("1.2", "18"),
        DiameterRow("0.9", "13"),
        DiameterRow("1.1", "16"),
        DiameterRow("0.6", "9"),
        DiameterRow("1.0", "14")
    )

    private val undergrowthRows: MutableList<UndergrowthRow> = mutableListOf(
        UndergrowthRow("Сосна", "20", "15", "8", "2.1"),
        UndergrowthRow("Ель", "18", "12", "6", "1.9"),
        UndergrowthRow("Дуб", "10", "7", "3", "1.5"),
        UndergrowthRow("Берёза", "25", "10", "5", "2.4"),
        UndergrowthRow("Сосна", "22", "16", "9", "2.2"),
        UndergrowthRow("Ель", "17", "11", "7", "1.8"),
        UndergrowthRow("Осина", "14", "9", "4", "2.0"),
        UndergrowthRow("Дуб", "9", "6", "2", "1.4")
    )

    private enum class Mode { CULTURES, DIAMETER, UNDERGROWTH }

    private var mode = Mode.CULTURES

    private var activeRow: TableRow? = null
    private var selectedIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProbaInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindInfo()
        renderTable()
        switchInit()
        toolbarInit()
    }

    private fun bindInfo() {
        binding.lesnich.text = info.forestry
        binding.district.text = info.district
        binding.tract.text = info.tract
        binding.quarter.text = info.quarter
        binding.allotment.text = info.allotment
        binding.area.text = info.area
        binding.date.text = info.date
    }

    private fun switchInit() {
        binding.btnCultures.setOnClickListener { setMode(Mode.CULTURES) }
        binding.btnDiameter.setOnClickListener { setMode(Mode.DIAMETER) }
        binding.btnUndergrowth.setOnClickListener { setMode(Mode.UNDERGROWTH) }
    }

    private fun setMode(newMode: Mode) {
        if (mode == newMode) return
        mode = newMode

        paintTab(binding.btnCultures, mode == Mode.CULTURES)
        paintTab(binding.btnDiameter, mode == Mode.DIAMETER)
        paintTab(binding.btnUndergrowth, mode == Mode.UNDERGROWTH)

        binding.btnSave.visibility = if (mode == Mode.UNDERGROWTH) View.VISIBLE else View.GONE

        activeRow = null
        selectedIndex = null
        renderTable()
    }

    private fun paintTab(tab: TextView, active: Boolean) {
        tab.setBackgroundResource(if (active) R.color.selected_Green else R.color.unselected_Gray)
        tab.setTextColor(resources.getColor(if (active) R.color.white else R.color.gray))
    }

    @SuppressLint("SetTextI18n")
    private fun renderTable() {
        binding.tableHeader.removeAllViews()
        binding.tblRows.removeAllViews()

        val headers = when (mode) {
            Mode.CULTURES -> listOf("Порода", "Живые, шт", "Погибшие, шт")
            Mode.DIAMETER -> listOf("Диаметр корневой шейки", "Высота корневой шейки")
            Mode.UNDERGROWTH -> listOf(
                "Порода", "До 0.5, шт", "от 0.51 до 1.5, шт", "Более 1.5, шт", "Макс. высота"
            )
        }
        for (title in headers) {
            binding.tableHeader.addView(headerCell(title))
        }

        val cellPad = (5 * resources.displayMetrics.density).toInt()
        val rows: List<List<String>> = when (mode) {
            Mode.CULTURES -> cultureRows.map { listOf(it.breed, it.alive, it.dead) }
            Mode.DIAMETER -> diameterRows.map { listOf(it.diameter, it.height) }
            Mode.UNDERGROWTH -> undergrowthRows.map {
                listOf(it.breed, it.h05, it.h15, it.hMore, it.maxHeight)
            }
        }

        for (i in rows.indices) {
            val tableRow = TableRow(this)

            for (value in rows[i]) {
                val cell = TextView(this)
                cell.text = value
                cell.textAlignment = View.TEXT_ALIGNMENT_CENTER
                cell.gravity = Gravity.CENTER
                cell.setTextColor(-0x1000000)
                cell.setPadding(cellPad, cellPad, cellPad, cellPad)
                tableRow.addView(cell)
            }

            val index = i
            tableRow.setOnClickListener {
                activeRow?.setBackgroundResource(R.color.color_transporent)
                activeRow = tableRow
                selectedIndex = index
                tableRow.setBackgroundResource(R.color.activecolumn)
            }

            binding.tblRows.addView(tableRow, i)

            val layoutParams = tableRow.layoutParams as TableLayout.LayoutParams
            layoutParams.setMargins(0, 10, 0, 10)
            tableRow.layoutParams = layoutParams
        }
    }

    private fun headerCell(title: String): TextView {
        val cell = TextView(this)
        cell.text = title
        cell.gravity = Gravity.CENTER
        cell.textAlignment = View.TEXT_ALIGNMENT_CENTER
        cell.setTextColor(resources.getColor(R.color.white))
        cell.textSize = 13f
        val pad = (5 * resources.displayMetrics.density).toInt()
        cell.setPadding(pad, pad, pad, pad)
        cell.layoutParams = LinearLayout.LayoutParams(
            0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
        )
        return cell
    }

    private fun toolbarInit() {
        binding.toolbar.open.visibility = View.GONE

        binding.toolbar.addbutton.setOnClickListener {
            Toast.makeText(this, "В разработке", Toast.LENGTH_SHORT).show()
        }
        binding.toolbar.save.setOnClickListener {
            Toast.makeText(this, "В разработке", Toast.LENGTH_SHORT).show()
        }
        binding.btnSave.setOnClickListener {
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
                when (mode) {
                    Mode.CULTURES -> cultureRows.removeAt(index)
                    Mode.DIAMETER -> diameterRows.removeAt(index)
                    Mode.UNDERGROWTH -> undergrowthRows.removeAt(index)
                }
                activeRow = null
                selectedIndex = null
                dialog.dismiss()
                renderTable()
            }
        }
    }
}
