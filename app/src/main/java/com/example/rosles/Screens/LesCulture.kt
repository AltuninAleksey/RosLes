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
import com.example.rosles.Screens.choice.ChoiceSubject
import com.example.rosles.databinding.LesCultureListBinding
import com.example.rosles.setSizeRelativeCurrentWindow

class LesCulture : BaseActivity("Лесные культуры") {

    private lateinit var binding: LesCultureListBinding


            private val db by lazy { DBCountWood(applicationContext, null) }

    private val rows: MutableList<LISTREGION_LIST_DATA> = mutableListOf()

    private var activeRow: TableRow? = null
    private var selectedIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LesCultureListBinding.inflate(layoutInflater)
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

        // Имена лесничеств/участковых одним запросом по всем idDistrictForestly,
        // чтобы не делать N+1 поштучных запросов в цикле.
        val chains = db.getForestryChains(rows.mapNotNull { it.idDistrictForestly }.toSet())
        // Имена урочищ из справочника getdacha: у серверных строк dacha может
        // быть пустым, а idDacha — заполнен.
        val dachaNames = db.getDACHA().associate { it.id to it.name }

        val colWidth = resources.getDimensionPixelSize(R.dimen.table_col_width)
        val numWidth = resources.getDimensionPixelSize(R.dimen.table_col_num_width)
        val cellPad = (5 * resources.displayMetrics.density).toInt()

        for (i in rows.indices) {
            val row = rows[i]
            val tableRow = TableRow(this)

            val chain = row.idDistrictForestly?.let { chains[it] }
            val values = listOf(
                (i + 1).toString(),
                row.number,
                chain?.forestlyName ?: "—",
                chain?.districtName ?: "—",
                row.idDacha?.let { dachaNames[it] } ?: row.dacha ?: "—",
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
            // Создание ведомости — как в MainActivity: идём в цепочку
            // ChoiceSubject → ChoiceLes → ChoiceDistrict → ...,
            // id_Vedomost null = создание новой (выбор существующей — через open).
            val idSubject = getSharedPreferences("PreferencesName", MODE_PRIVATE)
                .getInt("id_subject", 0)
            val intent1 = Intent(this, ChoiceSubject::class.java)
            intent1.putExtra("id", idSubject.toString())
            intent1.putExtra("id_Vedomost", intent.getStringExtra("id_Vedomost"))
            // fc_mode: цепочка создаёт запись в fc_list_region (молодняк),
            // а не в старом listregion (MainActivity).
            intent1.putExtra("fc_mode", true)

            startActivity(intent1)
        }
        binding.toolbar.open.setOnClickListener {
            // Открываем карточку именно выбранной строки — без uuid
            // UchastokInfo не знает, чьи idDacha/idDistrictForestly резолвить.
            val index = selectedIndex
            if (index == null) {
                Toast.makeText(this, "Выберите участок", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val uuid = rows[index].uuid
            if (uuid == null) {
                Toast.makeText(this, "У участка нет идентификатора", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startActivity(Intent(this, UchastokInfo::class.java).putExtra("uuid", uuid))
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
