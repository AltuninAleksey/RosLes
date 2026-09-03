package com.example.rosles.Screens

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
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
import com.example.rosles.databinding.UchastokInfoBinding
import com.example.rosles.setSizeRelativeCurrentWindow

class UchastokInfo : BaseActivity("Информация об участке") {

    private lateinit var binding: UchastokInfoBinding

    // Мок-данные. Позже заменим на выборку из БД.
    data class Uchastok(
        val forestry: String,
        val district: String,
        val tract: String,
        val quarter: String,
        val allotment: String,
        val date: String
    )

    data class ProbaRow(val length: String, val width: String, val area: String)
    data class OtrezokRow(val length: String)

    private val info = Uchastok("Брянское", "Мичуринское", "Соловьи", "3", "25-4", "01.01.2023")

    private val probaRows: MutableList<ProbaRow> = mutableListOf(
        ProbaRow("50", "20", "0.10"),
        ProbaRow("50", "20", "0.10"),
        ProbaRow("40", "25", "0.10"),
        ProbaRow("60", "15", "0.09"),
        ProbaRow("55", "20", "0.11"),
        ProbaRow("45", "22", "0.10"),
        ProbaRow("50", "18", "0.09"),
        ProbaRow("52", "20", "0.10")
    )

    private val otrezokRows: MutableList<OtrezokRow> = mutableListOf(
        OtrezokRow("10"),
        OtrezokRow("10"),
        OtrezokRow("12"),
        OtrezokRow("8"),
        OtrezokRow("10"),
        OtrezokRow("11"),
        OtrezokRow("9"),
        OtrezokRow("10")
    )

    private enum class Mode { PROBA, OTREZOK }

    private var mode = Mode.PROBA

    private var activeRow: TableRow? = null
    private var selectedIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UchastokInfoBinding.inflate(layoutInflater)
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
        binding.date.text = info.date
    }

    private fun switchInit() {
        binding.btnProba.setOnClickListener { setMode(Mode.PROBA) }
        binding.btnOtrezok.setOnClickListener { setMode(Mode.OTREZOK) }
    }

    private fun setMode(newMode: Mode) {
        if (mode == newMode) return
        mode = newMode

        val probaActive = mode == Mode.PROBA
        binding.btnProba.setBackgroundResource(
            if (probaActive) R.color.selected_Green else R.color.unselected_Gray
        )
        binding.btnProba.setTextColor(
            if (probaActive) resources.getColor(R.color.white) else resources.getColor(R.color.gray)
        )
        binding.btnOtrezok.setBackgroundResource(
            if (probaActive) R.color.unselected_Gray else R.color.selected_Green
        )
        binding.btnOtrezok.setTextColor(
            if (probaActive) resources.getColor(R.color.gray) else resources.getColor(R.color.white)
        )

        activeRow = null
        selectedIndex = null
        renderTable()
    }

    @SuppressLint("SetTextI18n")
    private fun renderTable() {
        binding.tableHeader.removeAllViews()
        binding.tblRows.removeAllViews()

        val headers = when (mode) {
            Mode.PROBA -> listOf("№", "Длина, м", "Ширина, м", "Площадь, га")
            Mode.OTREZOK -> listOf("Номер", "Длина, м")
        }
        for (title in headers) {
            binding.tableHeader.addView(headerCell(title))
        }

        val cellPad = (5 * resources.displayMetrics.density).toInt()
        val rowCount = when (mode) {
            Mode.PROBA -> probaRows.size
            Mode.OTREZOK -> otrezokRows.size
        }

        for (i in 0 until rowCount) {
            val tableRow = TableRow(this)

            val values = when (mode) {
                Mode.PROBA -> {
                    val row = probaRows[i]
                    listOf((i + 1).toString(), row.length, row.width, row.area)
                }
                Mode.OTREZOK -> {
                    val row = otrezokRows[i]
                    listOf((i + 1).toString(), row.length)
                }
            }

            for (value in values) {
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
        binding.toolbar.addbutton.setOnClickListener {
            Toast.makeText(this, "В разработке", Toast.LENGTH_SHORT).show()
        }
        binding.toolbar.open.setOnClickListener {
            startActivity(Intent(this, ProbaInfo::class.java))
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
                when (mode) {
                    Mode.PROBA -> probaRows.removeAt(index)
                    Mode.OTREZOK -> otrezokRows.removeAt(index)
                }
                activeRow = null
                selectedIndex = null
                dialog.dismiss()
                renderTable()
            }
        }
    }
}
