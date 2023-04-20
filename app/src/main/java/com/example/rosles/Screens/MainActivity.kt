package com.example.rosles.Screens


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.rosles.DBCountWood
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
        var cursor = db.readbyporoda()
        cursor.moveToFirst()


        var activetableRow: TableRow? = null

        for (i in 1..cursor.getCount()) {
            val tableRow = TableRow(this)

            val text0 = TextView(this)
            val text1 = TextView(this)
            val text2 = TextView(this)
            val text3 = TextView(this)
            val text4 = TextView(this)
            val text5 = TextView(this)
            val text6 = ImageView(this)

            text0.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text1.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text2.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text3.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text4.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text5.textAlignment = View.TEXT_ALIGNMENT_CENTER


            text0.setTextColor(-0x1000000)
            text1.setTextColor(-0x1000000)
            text2.setTextColor(-0x1000000)
            text3.setTextColor(-0x1000000)
            text4.setTextColor(-0x1000000)
            text5.setTextColor(-0x1000000)


            text5.visibility=View.GONE

            text0.setText(cursor.getString(cursor.getColumnIndex("name_forestly")))
            text1.setText(cursor.getString(cursor.getColumnIndex("name_district_forestly")))
            text2.setText(cursor.getString(cursor.getColumnIndex("quarter_name")))
            text3.setText(cursor.getString(cursor.getColumnIndex("soil_lot")))
            text4.setText(cursor.getString(cursor.getColumnIndex("date")))
            text5.setText(cursor.getString(cursor.getColumnIndex("id")))

            if((cursor.getString(cursor.getColumnIndex("mark_update")))=="1"){
                text6.setImageResource(R.drawable.reloadred)
            }


            tableRow.setOnClickListener {
                activetableRow?.setBackgroundResource(R.color.color_transporent)
                tableRow.setBackgroundResource(R.color.color_transporent)
                activetableRow = tableRow
                activetableRow!!.setBackgroundResource(R.color.activecolumn)
            }
            tableRow.addView(text5,0)
            tableRow.addView(text0, 1)
            tableRow.addView(text1, 2)
            tableRow.addView(text2, 3)
            tableRow.addView(text3, 4)
            tableRow.addView(text4, 5)
            tableRow.addView(text6, 6)

            binding.tblLayout.addView(tableRow, i);

            val layoutParams = tableRow.layoutParams as TableLayout.LayoutParams
            layoutParams.setMargins(0, 10, 0, 10)
            tableRow.layoutParams = layoutParams
            cursor.moveToNext()
        }
        cursor.close()

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






