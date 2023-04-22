package com.example.rosles.Screens


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.rosles.DBCountWood
import com.example.rosles.Models.Poroda
import com.example.rosles.Network.ViewModels
import com.example.rosles.R
import com.example.rosles.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    //в ожидании звездного часа на синхрон
    val viewModel by viewModels<ViewModels>()

    private val db = DBCountWood(this, null)
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //инциализация навигации
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        RecyclerviewInit()

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)

        val view: View = supportActionBar!!.customView

        val title=view.findViewById<TextView>(R.id.text)
        val back=view.findViewById<ImageView>(R.id.back)
        val menu=view.findViewById<ImageView>(R.id.burger)
        title.setText("Перечетные ведомости")
        back.setOnClickListener{
            finish()
        }
        menu.setOnClickListener{
            showpopupmenu(it)
        }

    }
    fun showpopupmenu (view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.main -> {
                    startActivity(Intent(this, Dashboard::class.java))
                }
                R.id.itemperechet -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                R.id.itemgps -> {
                    startActivity(Intent(this, gps_activity::class.java))
                }
                R.id.profile -> {
                    startActivity(Intent(this, profile::class.java))
                }
            }
            true
        })
        popup.show()

    }

    @SuppressLint("Range")
    fun RecyclerviewInit() {
        val porodaList :List<Poroda> = db.readbyporoda()

        var activetableRow: TableRow? = null
        for (i in 0..porodaList.size) {
            val tableRow = TableRow(this)
            if (porodaList.size==0)
                break
            /* Порядок важен, знацения будут добавляться в колонки таблицы
            * в порядке указанном в valuesOfPoroda */
            val valuesOfPorodaList: List<String> = mutableListOf(
                porodaList[i].id,
                porodaList[i].nameForestly,
                porodaList[i].nameDistrictForestly,
                porodaList[i].quarterName,
                porodaList[i].soilLot,
                porodaList[i].date
            )

            // сборка строки для тоблицы
            for((indexOfvalue, valueOfPoroda) in valuesOfPorodaList.withIndex()) {
                val text = TextView(this)
                val img = ImageView(this)

                text.textAlignment = View.TEXT_ALIGNMENT_CENTER
                text.setTextColor(-0x1000000)
                text.text = valueOfPoroda

                if (indexOfvalue == 1)
                    text.visibility = View.VISIBLE

                if (porodaList[i].markUpdate.toInt() == 1)
                    img.setImageResource(R.drawable.reloadred)

                tableRow.addView(text, indexOfvalue)
            }

            tableRow.setOnClickListener {
                activetableRow?.setBackgroundResource(R.color.color_transporent)
                tableRow.setBackgroundResource(R.color.color_transporent)
                activetableRow = tableRow
                activetableRow!!.setBackgroundResource(R.color.activecolumn)
            }

            binding.tblLayout.addView(tableRow, i);

            val layoutParams = tableRow.layoutParams as TableLayout.LayoutParams
            layoutParams.setMargins(0, 10, 0, 10)
            tableRow.layoutParams = layoutParams
        }

        binding.toolbar.open.setOnClickListener {
            if (activetableRow != null) {
                var bufer = activetableRow?.get(0)
                val textView = bufer as TextView
                val intent = Intent(this, lisq_square::class.java)
                intent.putExtra("id_Vedomost", textView.text)

                startActivity(intent)
            }
        }

        binding.toolbar.save.setOnClickListener() {
            if (activetableRow != null) {
                val intent = Intent(this, ChangeVedomost::class.java)

                var bufer = activetableRow?.get(0)

                val textView = bufer as TextView
                intent.putExtra("id_Vedomost", textView.text)
                startActivity(intent)
            }
        }

        binding.toolbar.addbutton.setOnClickListener {
            startActivity(Intent(this, ChoiceSubject::class.java))
        }


    }
}






