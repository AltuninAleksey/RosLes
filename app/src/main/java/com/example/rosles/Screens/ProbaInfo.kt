package com.example.rosles.Screens

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.R
import com.example.rosles.databinding.ProbaInfoBinding
import com.example.rosles.setSizeRelativeCurrentWindow
import com.example.roslesdef.Adapters.ForestAdapter
import com.example.roslesdef.Models.SpinerItem

class ProbaInfo : BaseActivity("Информация о пробной площади") {

    private lateinit var binding: ProbaInfoBinding
    private val db by lazy { DBCountWood(applicationContext, null) }

    // Перечет, для которого открыт экран, и его ведомость.
    private var uuidSample: String? = null
    private var vedomostUuid: String? = null

    data class Uchastok(
        val forestry: String,
        val district: String,
        val tract: String,
        val quarter: String,
        val allotment: String,
        val area: String,
        val date: String
    )

    data class CultureRow(
        val uuid: String?,
        var idBreed: Int?,
        val number: String,
        var breed: String,
        var alive: String,
        var dead: String
    )
    data class DiameterRow(val diameter: String, val height: String)
    data class UndergrowthRow(
        val breed: String,
        val h05: String,
        val h15: String,
        val hMore: String,
        val maxHeight: String
    )

    private var info = Uchastok("—", "—", "—", "—", "—", "—", "—")

    // Лесные культуры выбранного перечета (djangoForest_fc_row).
    private val cultureRows: MutableList<CultureRow> = mutableListOf()

    // Последняя выбранная на экране порода — подставляется в новые строки.
    private var lastBreedId: Int? = null

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
        uuidSample = intent.getStringExtra("uuidSample")
        vedomostUuid = intent.getStringExtra("uuid")
        bindInfo()
        renderTable()
        switchInit()
        toolbarInit()
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }

    /**
     * Шапка — из ведомости (djangoForest_fc_list_region) перечета,
     * вкладка «Лесные культуры» — строки djangoForest_fc_row по uuidSample.
     */
    private fun bindInfo() {
        val region = vedomostUuid?.let { db.getFCListRegionByUuid(it) }
        if (region != null) {
            val chain = region.idDistrictForestly?.let { db.getForestryChain(it) }
            val tract = region.idDacha?.let { db.getDachaNameById(it) } ?: region.dacha ?: "—"
            info = Uchastok(
                forestry = chain?.forestlyName ?: "—",
                district = chain?.districtName ?: "—",
                tract = tract,
                quarter = region.nameQuarter ?: "—",
                allotment = "—",
                area = region.sampleRegion?.toString() ?: "—",
                date = region.date
            )
        }

        binding.lesnich.text = info.forestry
        binding.district.text = info.district
        binding.tract.text = info.tract
        binding.quarter.text = info.quarter
        binding.allotment.text = info.allotment
        binding.area.text = info.area
        binding.date.text = info.date

        // Строки лесных культур именно этого перечета.
        loadCultureRows()
    }

    private fun loadCultureRows() {
        cultureRows.clear()
        uuidSample?.let { sample ->
            cultureRows.addAll(
                db.getFCRowsForDisplay(sample).map {
                    CultureRow(
                        uuid = it.uuid,
                        idBreed = it.idBreed,
                        number = it.number?.toString() ?: "—",
                        breed = it.breedName ?: "Выберите породу",
                        alive = it.countLiving?.toString() ?: "",
                        dead = it.countDead?.toString() ?: ""
                    )
                }
            )
        }
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
            Mode.CULTURES -> listOf("№", "Порода", "Живые, шт", "Погибшие, шт")
            Mode.DIAMETER -> listOf("Диаметр корневой шейки", "Высота корневой шейки")
            Mode.UNDERGROWTH -> listOf(
                "Порода", "До 0.5, шт", "от 0.51 до 1.5, шт", "Более 1.5, шт", "Макс. высота"
            )
        }
        for (title in headers) {
            binding.tableHeader.addView(headerCell(title))
        }

        if (mode == Mode.CULTURES) {
            cultureRows.forEachIndexed { i, row -> addCultureRow(i, row) }
            return
        }

        val rows: List<List<String>> = when (mode) {
            Mode.CULTURES -> emptyList()
            Mode.DIAMETER -> diameterRows.map { listOf(it.diameter, it.height) }
            Mode.UNDERGROWTH -> undergrowthRows.map {
                listOf(it.breed, it.h05, it.h15, it.hMore, it.maxHeight)
            }
        }

        for (i in rows.indices) {
            val tableRow = TableRow(this)

            for (value in rows[i]) {
                tableRow.addView(textCell(value))
            }

            attachSelection(tableRow, i)
            binding.tblRows.addView(tableRow, i)

            val layoutParams = tableRow.layoutParams as TableLayout.LayoutParams
            layoutParams.setMargins(0, 10, 0, 10)
            tableRow.layoutParams = layoutParams
        }
    }

    private fun addCultureRow(index: Int, row: CultureRow) {
        val tableRow = TableRow(this)

        tableRow.addView(textCell(row.number))

        val breedCell = textCell(row.breed)
        breedCell.setOnClickListener {
            showBreedPicker(row.idBreed) { picked ->
                row.idBreed = picked.id
                row.breed = picked.name
                row.uuid?.let { db.updateFCRowBreed(it, picked.id) }
                renderTable()
            }
        }
        tableRow.addView(breedCell)

        tableRow.addView(countCell(row, true))
        tableRow.addView(countCell(row, false))

        attachSelection(tableRow, index)
        binding.tblRows.addView(tableRow, index)

        val layoutParams = tableRow.layoutParams as TableLayout.LayoutParams
        layoutParams.setMargins(0, 10, 0, 10)
        tableRow.layoutParams = layoutParams
    }

    private fun textCell(value: String): TextView {
        val cell = TextView(this)
        cell.text = value
        cell.textAlignment = View.TEXT_ALIGNMENT_CENTER
        cell.gravity = Gravity.CENTER
        cell.setTextColor(-0x1000000)
        val pad = (5 * resources.displayMetrics.density).toInt()
        cell.setPadding(pad, pad, pad, pad)
        return cell
    }

    private fun countCell(row: CultureRow, isLiving: Boolean): EditText {
        val pad = (5 * resources.displayMetrics.density).toInt()
        val cell = EditText(this)
        cell.inputType = InputType.TYPE_CLASS_NUMBER
        cell.gravity = Gravity.CENTER
        cell.setTextColor(-0x1000000)
        cell.textSize = 14f
        cell.minWidth = 0
        cell.setPadding(pad, 0, pad, 0)
        cell.setText(if (isLiving) row.alive else row.dead)
        cell.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) cell.selectAll()
        }
        cell.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val value = s?.toString() ?: ""
                if (isLiving) row.alive = value else row.dead = value
                row.uuid?.let {
                    db.updateFCRowCounts(
                        it,
                        row.alive.toIntOrNull() ?: 0,
                        row.dead.toIntOrNull() ?: 0
                    )
                }
            }
        })
        return cell
    }

    private fun attachSelection(tableRow: TableRow, index: Int) {
        tableRow.setOnClickListener {
            activeRow?.setBackgroundResource(R.color.color_transporent)
            activeRow = tableRow
            selectedIndex = index
            tableRow.setBackgroundResource(R.color.activecolumn)
        }
    }

    private fun addCultureRow() {
        if (mode != Mode.CULTURES) {
            Toast.makeText(this, "Добавление доступно на вкладке «Лесные культуры»", Toast.LENGTH_SHORT).show()
            return
        }
        val sample = uuidSample
        if (sample == null) {
            Toast.makeText(this, "Перечет не выбран", Toast.LENGTH_SHORT).show()
            return
        }
        val number = db.nextFCRowNumber(sample)
        db.insertFCRowLocal(sample, number, lastBreedId, null, null)
        loadCultureRows()
        activeRow = null
        selectedIndex = null
        renderTable()
    }

    private fun showBreedPicker(currentId: Int?, onPick: (SpinerItem) -> Unit) {
        val breeds = db.getallporodArray().filter { it.id != 0 }
        if (breeds.isEmpty()) {
            Toast.makeText(this, "Справочник пород пуст, загрузите данные", Toast.LENGTH_SHORT).show()
            return
        }
        breeds.forEach { it.check = it.id == currentId }

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_forest)
        dialog.setSizeRelativeCurrentWindow(0.9, 0.85)

        val recycler = dialog.findViewById<RecyclerView>(R.id.ForestRecycler)
        val search = dialog.findViewById<EditText>(R.id.textSearch)
        val pick = dialog.findViewById<Button>(R.id.button_add)
        pick.text = "Выбрать"

        fun bind(query: String) {
            val filtered = breeds.filter { it.name.contains(query, ignoreCase = true) }
            recycler.layoutManager = LinearLayoutManager(this)
            recycler.adapter = ForestAdapter(filtered) { checked ->
                breeds.forEach { it.check = it.id == checked.id && checked.check }
                recycler.adapter?.notifyDataSetChanged()
            }
        }

        bind("")
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                bind(s?.toString() ?: "")
            }
        })

        pick.setOnClickListener {
            val picked = breeds.lastOrNull { it.check }
            if (picked == null) {
                Toast.makeText(this, "Выберите породу", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lastBreedId = picked.id
            onPick(picked)
            dialog.dismiss()
        }

        dialog.show()
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
            addCultureRow()
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
                    Mode.CULTURES -> {
                        cultureRows.getOrNull(index)?.uuid?.let { db.deleteFCRow(it) }
                        loadCultureRows()
                    }
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
