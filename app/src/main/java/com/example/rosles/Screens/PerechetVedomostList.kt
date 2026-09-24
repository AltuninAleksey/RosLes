package com.example.rosles.Screens

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rosles.Adapters.VedomostListAdapter
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.R
import com.example.rosles.ResponceClass.DachaData
import com.example.rosles.databinding.PerechetVedomostListBinding
import com.example.rosles.setSizeRelativeCurrentWindow

class PerechetVedomostList : BaseActivity("Перечётная ведомость") {

    private lateinit var binding: PerechetVedomostListBinding
    private val db by lazy { DBCountWood(this, null) }

    private val rows: MutableList<DachaData> = mutableListOf()

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
        rows.clear()
        rows.addAll(db.getDACHA())
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
                db.deleteDACHA(rows[index].id)
                rows.removeAt(index)
                selectedIndex = null
                dialog.dismiss()
                listInit()
            }
        }
    }
}
