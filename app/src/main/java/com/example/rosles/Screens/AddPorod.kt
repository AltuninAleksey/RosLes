package com.example.rosles.Screens

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.rosles.Adapters.BaseInterface
import com.example.rosles.Adapters.ChoiceSubjectAdapter
import com.example.rosles.BaseActivity
import com.example.rosles.DBCountWood
import com.example.rosles.R
import com.example.rosles.ResponceClass.BaseRespObject
import com.example.rosles.databinding.AddPorodBinding
import com.example.rosles.databinding.AddPorodScreenBinding
import com.example.rosles.setSizeRelativeCurrentWindow
import com.example.roslesdef.Adapters.ForestAdapter
import com.example.roslesdef.Models.SpinerItem
import java.util.*
import kotlin.collections.HashMap


class AddPorod : BaseActivity("Добавление") {

    private lateinit var binding: AddPorodScreenBinding
    private val db = DBCountWood(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddPorodScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initActivity()
    }

    @SuppressLint("SuspiciousIndentation")
    fun initActivity() {
        val sPref = getSharedPreferences("PreferencesName", MODE_PRIVATE)
        val id = sPref.getString("id", "")!!.toInt()

        val countries = db.getallporodArray()
        val countries1 = db.getallpodlesArray()


        val hash = db.gethashFavoriteLes(id)
        val hashpodles = db.gethashFavoritepodLes(id)

        var les_data: List<SpinerItem>? = null
        var podles_data: List<SpinerItem>? = null


        fun addles(valueles: SpinerItem) {
            var flag = true
            hash.forEach { t, u ->
                if (u == valueles.name) {
                    flag = false
                }

            }
            if (flag && valueles.name != "не добавлять") {
                db.addlesporod(id, valueles.id)
            }
        }

        fun addpodles(valuepodles: SpinerItem) {
            var flagpodles = true

            hashpodles.forEach { t, u ->
                if (u == valuepodles.name || t == 0) {
                    flagpodles = false
                    Toast.makeText(this, "Такая порода уже существует", Toast.LENGTH_SHORT).show()
                }
            }
            if (flagpodles && valuepodles.id != 0) {
                db.addpodlesporod(id, valuepodles.id)
            }
        }




        binding.les.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_add_forest)
            dialog.setSizeRelativeCurrentWindow(1.0, 0.9)
            val recycler_dialog = dialog.findViewById<RecyclerView>(R.id.ForestRecycler)
            val textSearch = dialog.findViewById<EditText>(R.id.textSearch)
            val button = dialog.findViewById<Button>(R.id.button_add)


            var filterList = countries
            var adapterles = ForestAdapter(countries)

            recycler_dialog.adapter = adapterles


            textSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Called before the text is changed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Called when the text is changing
                    val enteredText = s.toString()

                     filterList = countries.filter { it.name.contains(enteredText,ignoreCase = true)}
                     adapterles = ForestAdapter(filterList)

                    recycler_dialog.adapter = adapterles
                }

                override fun afterTextChanged(s: Editable?) {
                    // Called after the text has changed
                }
            })



            button.setOnClickListener {

                les_data = adapterles.getArraydata()
                binding.les.setText("")

                les_data.forEach {
                    if (it.check) {
                        binding.les.append(it.name + " ")
                    }
                }
                dialog.dismiss()
            }
            dialog.show()
        }


        binding.podles.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_add_forest)
            dialog.setSizeRelativeCurrentWindow(1.0, 0.9)
            val recycler_dialog = dialog.findViewById<RecyclerView>(R.id.ForestRecycler)
            val textSearch = dialog.findViewById<EditText>(R.id.textSearch)
            val button = dialog.findViewById<Button>(R.id.button_add)




            val adapterpodles = ForestAdapter(countries1)
            recycler_dialog.adapter = adapterpodles




            var filterList = countries1
            var adapterles = ForestAdapter(countries1)

            recycler_dialog.adapter = adapterles


            textSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Called before the text is changed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Called when the text is changing
                    val enteredText = s.toString()

                    filterList = countries1.filter { it.name.contains(enteredText,ignoreCase = true)}
                    adapterles = ForestAdapter(filterList)

                    recycler_dialog.adapter = adapterles
                }

                override fun afterTextChanged(s: Editable?) {
                    // Called after the text has changed
                }
            })



            button.setOnClickListener {
                podles_data = adapterpodles.getArraydata()
                binding.podles.setText("")

                podles_data.forEach {
                    if (it.check) {
                        binding.podles.append(it.name + " ")
                    }
                    dialog.dismiss()
                }

            }
            dialog.show()
        }



        binding.buttonAuto.setOnClickListener {

            les_data?.forEach {
                if (it.check) {
                    addles(it)
                }
            }
            podles_data?.forEach {
                if (it.check) {
                    addpodles(it)
                }
            }
            finish()
        }


    }


    @SuppressLint("Range")
    fun getidbreed(value: String?, dbCountWood: DBCountWood): Int? {
        val cursor = dbCountWood.getallporod() // swap to getallporodArray()
        cursor.moveToFirst()
        val hash = HashMap<String, Int>()
        for (i in 1..cursor.getCount()) {
            hash.put(
                cursor.getString(cursor.getColumnIndex("name_breed")),
                cursor.getInt(cursor.getColumnIndex("id")) ?: 0
            )
            cursor.moveToNext()

        }
        cursor.close()
        return hash.get(value) // Q3?
    }

}


