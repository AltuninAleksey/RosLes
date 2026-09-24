package com.example.rosles.Screens

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.R
import com.example.rosles.ResponceClass.LISTREGION_LIST_DATA
import com.example.rosles.databinding.MolodnyakListBinding
import com.example.rosles.setSizeRelativeCurrentWindow

class MolodnyakList : BaseActivity("Учёт молодняка") {

    private lateinit var binding: MolodnyakListBinding
    private val db by lazy { DBCountWood(this, null) }

    private val rows: MutableList<LISTREGION_LIST_DATA> = mutableListOf()

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
        rows.clear()
        rows.addAll(db.getFCListRegion())

        val colWidth = resources.getDimensionPixelSize(R.dimen.table_col_width)
        val numWidth = resources.getDimensionPixelSize(R.dimen.table_col_num_width)
        val cellPad = (5 * resources.displayMetrics.density).toInt()

        for (i in rows.indices) {
            val row = rows[i]
            val tableRow = TableRow(this)

            val values = listOf(
                (i + 1).toString(),
                row.number,
                row.dacha ?: "—",
                row.nameQuarter ?: "—",
                row.soilLot ?: "—",
                row.sampleRegion?.toString() ?: "—",
                row.date
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
            startActivity(Intent(this, PerechetVedomostList::class.java))
        }
        binding.toolbar.open.setOnClickListener {
            startActivity(Intent(this,UchastokInfo::class.java))
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
                rows[index].uuid?.let { db.deleteFCListRegion(it) }
                dialog.dismiss()
                onRestart()
            }
        }
    }
}
